package com.dwf.switchstore.sf.client;

import com.dwf.switchstore.sf.model.Users;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * This class is a client to authenticate users
 */
public class AuthClient implements Serializable {

    private static final String BASE_URI = "http://localhost:8080/switchstore-1.0-SNAPSHOT/api/auth";
    private final HttpClient client = HttpClient.newHttpClient(); // Send HTTP requests
    private final ObjectMapper objectMapper = new ObjectMapper(); // Convert JSON to Java objects

    /**
     * Login to the application
     * @param username the username to authenticate
     * @param password the password to authenticate
     * @return JWT token
     * @throws IOException if the request fails
     * @throws InterruptedException if the request is interrupted
     */
    public Users login(String username, String password) throws IOException, InterruptedException {
        Users loginUser = new Users(username, password);
        String requestBody = objectMapper.writeValueAsString(loginUser);

        // Create a POST request to the login endpoint
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URI + "/login"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        // Send the request and get the response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Check if the response is successful
        if (response.statusCode() == 200) {
            JsonNode jsonNode = objectMapper.readTree(response.body());
            String token = jsonNode.get("token").asText();
            Users user = objectMapper.treeToValue(jsonNode.get("user"), Users.class);
            user.setToken(token);
            return user;
        } else {
            JsonNode jsonNode = objectMapper.readTree(response.body());
            throw new IOException(jsonNode.get("message").asText());
        }
    }

    /**
     * Register a new user
     * @param user the user to register
     * @throws IOException if the request fails
     * @throws InterruptedException if the request is interrupted
     */
    public void register(Users user) throws IOException, InterruptedException {
        String requestBody = objectMapper.writeValueAsString(user);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URI + "/register"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 201) {
            JsonNode jsonNode = objectMapper.readTree(response.body());
            throw new IOException(jsonNode.get("message").asText());
        }
    }
}
