package com.huacai.springbootdemo.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(SimpleHttpClient.class)
@Import(RestClientConfig.class) /// Optionally use @SpringBootTest if the context needs it
public class SimpleHttpClientTest {

    @Autowired
    private SimpleHttpClient simpleHttpClient;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;


    @BeforeEach
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void testPostWithStringBodyAndHeaders() {
        // Setup the expected request and response on mock server
        String url = "https://httpbin.org/post";
        mockServer.expect(requestTo(url))
                .andExpect(method(HttpMethod.POST))
                .andExpect(header("Custom-Header", "value"))
                .andExpect(content().string("{\"key\":\"value\"}"))
                .andRespond(withSuccess("{\"key\":\"value\"}", MediaType.APPLICATION_JSON));

        // Perform the call to the actual method
        Map<String, String> headers = new HashMap<>();
        headers.put("Custom-Header", "value");
        String body = "{\"key\":\"value\"}";
        String response = simpleHttpClient.post(url, body, headers);

        // Verify the call was successful
        assertEquals("{\"key\":\"value\"}", response);

        // Verify that all expectations were met
        mockServer.verify();
    }

    @Test
    public void testPostWithObjectBodyAndCustomResponseType() {
        String url = "https://httpbin.org/post";
        // Setup our mocked server response
        mockServer.expect(requestTo(url))
                .andExpect(content().string("{\"jsonKey\":\"jsonValue\"}"))
                .andRespond(withSuccess("{\"jsonKey\":\"jsonValue\"}", MediaType.APPLICATION_JSON));

        // Prepare headers and body
        Map<String, String> headers = new HashMap<>();
        headers.put("Another-Custom-Header", "anotherValue");
        Map<String, Object> body = new HashMap<>();
        body.put("jsonKey", "jsonValue");

        // Call the method to test
        String response = simpleHttpClient.post(url, body, headers, String.class);

        // Assert the response was as expected
        assertEquals("{\"jsonKey\":\"jsonValue\"}", response);

        // Verify that all expectations were met
        mockServer.verify();
    }

    @Test
    public void testPostWithObjectBodyNoHeaders() {
        String url = "https://httpbin.org/post";
        // Setup the mock server
        mockServer.expect(requestTo(url))
                .andExpect(content().string("{\"simpleKey\":\"simpleValue\"}"))
                .andRespond(withSuccess("{\"simpleKey\":\"simpleValue\"}", MediaType.APPLICATION_JSON));

        // Body without headers
        Map<String, Object> body = new HashMap<>();
        body.put("simpleKey", "simpleValue");

        // Call method
        String response = simpleHttpClient.post(url, body);

        // Validation
        assertEquals("{\"simpleKey\":\"simpleValue\"}", response);

        // Verify mock server
        mockServer.verify();
    }
}