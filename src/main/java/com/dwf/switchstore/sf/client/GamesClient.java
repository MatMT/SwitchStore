package com.dwf.switchstore.sf.client;

import com.dwf.switchstore.sf.model.Games;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class GamesClient implements Serializable {

    private static final String BASE_URI = "http://localhost:8080/switchstore-1.0-SNAPSHOT/api/games";
    private final HttpClient client = HttpClient.newHttpClient(); // Send HTTP requests
    private final ObjectMapper objectMapper = new ObjectMapper(); // Convert JSON to Java objects

    private HttpRequest addAuthHeader(HttpRequest.Builder builder, String token) {
        return builder.header("Authorization", "Bearer " + token).build();
    }

    public ArrayList<Games> getAllGames(String token) throws IOException, InterruptedException {
        HttpRequest request = addAuthHeader(HttpRequest.newBuilder()
                .uri(URI.create(BASE_URI))
                .header("Accept", "application/json")
                .GET(), token);

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return objectMapper.readValue(response.body(), new TypeReference<ArrayList<Games>>() {}); // Convert JSON to ArrayList of Games
    }

    public Games getGame(int id, String token) throws IOException, InterruptedException {
        HttpRequest request = addAuthHeader(HttpRequest.newBuilder()
                .uri(URI.create(BASE_URI + "/" + id))
                .header("Accept", "application/json")
                .GET(), token);

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return objectMapper.readValue(response.body(), Games.class);
    }

    public void createGame(Games game, String token) throws IOException, InterruptedException {
        String requestBody = objectMapper.writeValueAsString(game);

        HttpRequest request = addAuthHeader(HttpRequest.newBuilder()
                .uri(URI.create(BASE_URI))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody)), token);

        client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public void updateGame(int id, Games game, String token) throws IOException, InterruptedException {
        String requestBody = objectMapper.writeValueAsString(game);

        HttpRequest request = addAuthHeader(HttpRequest.newBuilder()
                .uri(URI.create(BASE_URI + "/" + id))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody)), token);

        client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public void deleteGame(int id, String token) throws IOException, InterruptedException {
        HttpRequest request = addAuthHeader(HttpRequest.newBuilder()
                .uri(URI.create(BASE_URI + "/" + id))
                .DELETE(), token);

        client.send(request, HttpResponse.BodyHandlers.ofString());
    }

}
