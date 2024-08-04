package com.dwf.switchstore.sf.client;

import com.dwf.switchstore.sf.model.Estudiantes;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class EstudiantesClient {

    private static final String BASE_URI = "http://localhost:8080/switchstore-1.0-SNAPSHOT/api/estudiantes";
    private final HttpClient client = HttpClient.newHttpClient(); // Send HTTP requests
    private final ObjectMapper objectMapper = new ObjectMapper(); // ObjectMapper para JSON

    public ArrayList<Estudiantes> getAllEstudiantes() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URI))
                .header("Accept", "application/json")
                .GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Response: " + response.body());

        return objectMapper.readValue(response.body(), new TypeReference<ArrayList<Estudiantes>>() {}); // Convert JSON to ArrayList of Estudiantes
    }

    public Estudiantes getEstudiante(int id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URI + "/" + id))
                .header("Accept", "application/json")
                .GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return objectMapper.readValue(response.body(), Estudiantes.class);
    }

    public void createEstudiante(Estudiantes estudiante) throws IOException, InterruptedException {
        String requestBody = objectMapper.writeValueAsString(estudiante);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URI))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public void updateEstudiante(int id, Estudiantes estudiante) throws IOException, InterruptedException {
        String requestBody = objectMapper.writeValueAsString(estudiante);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URI + "/" + id))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public void deleteEstudiante(int id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URI + "/" + id))
                .DELETE()
                .build();

        client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
