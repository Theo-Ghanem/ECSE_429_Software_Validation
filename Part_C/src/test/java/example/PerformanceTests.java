package example;

import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static example.ServerSetup.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PerformanceTests {

    private static final String TODO_URL = "http://localhost:4567/todos";
    private static final String PROJECTS_URL = "http://localhost:4567/projects";
    private static final String CONTENT_TYPE_JSON = "application/json; utf-8";
    private static final String ACCEPT_JSON = "application/json";

    @BeforeAll
    public static void initialize() {
        // Initialize the server and other setup tasks
        RestAssured.baseURI = BASE_URL;
        startServer();
        System.out.println("Server started");

    }

    @AfterAll
    public static void after() {
        stopServer();
        System.out.println("Server stopped");
    }

    @Test
    public void performanceTest() {
        int[] objectCounts = {10, 50, 100, 200, 500, 1000, 5000};
        for (int count : objectCounts) {
            measurePerformanceForTodos(count);
            measurePerformanceForProjects(count);

        }
    }

    private void measurePerformanceForTodos(int count) {
        System.out.println("Measuring performance for TODOs with " + count + " objects:");
        try {
            // Populate objects
            int i;
            for ( i = 0; i < count; i++) {
                createTodo(i);
            }

            // Measure time for create operation
            long startTime = System.nanoTime();
            createTodo(i);
            long endTime = System.nanoTime();
            System.out.println("Time to create TODO: " + (endTime - startTime)/ 1_000_000  + " ms");

            // Measure time for delete operation
            startTime = System.nanoTime();
            deleteTodo();
            endTime = System.nanoTime();
            System.out.println("Time to delete TODO: " + (endTime - startTime)/ 1_000_000  + " ms");

            // Measure time for update operation
            startTime = System.nanoTime();
            updateTodo();
            endTime = System.nanoTime();
            System.out.println("Time to update TODO: " + (endTime - startTime)/ 1_000_000  + " ms");

            // Track CPU usage and memory
            // Use appropriate tools or libraries to measure CPU and memory usage during the above operations

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void measurePerformanceForProjects(int count) {
        System.out.println("Measuring performance for Projects with " + count + " objects:");
        try {
            // Populate objects
            for (int i = 0; i < count; i++) {
                createProject();
            }

            // Measure time for create operation
            long startTime = System.nanoTime();
            createProject();
            long endTime = System.nanoTime();
            System.out.println("Time to create Project: " + (endTime - startTime)/ 1_000_000  + " ms");

            // Measure time for delete operation
            startTime = System.nanoTime();
            deleteProject();
            endTime = System.nanoTime();
            System.out.println("Time to delete Project: " + (endTime - startTime)/ 1_000_000  + " ms");

            // Measure time for update operation
            startTime = System.nanoTime();
            updateProject();
            endTime = System.nanoTime();
            System.out.println("Time to update Project: " + (endTime - startTime)/ 1_000_000  + " ms");

            // Track CPU usage and memory
            // Use appropriate tools or libraries to measure CPU and memory usage during the above operations

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createTodo(int count) throws Exception {
        String jsonInputString = """
                {
                  "title": "Performance Test Todo %d",
                  "doneStatus": false,
                  "description": "This is a performance test todo"
                }""".formatted(count);
        executeRequest(TODO_URL, "POST", jsonInputString, 201);
    }

    private void deleteTodo() throws Exception {
        // Get the first todo ID and delete it
        String firstTodoId = getFirstTodoId(TODO_URL);
        String url = TODO_URL + "/" + firstTodoId;
        executeRequest(url, "DELETE", null, 200);
    }

    private void updateTodo() throws Exception {
        // Get the first todo ID and update it
        String firstTodoId = getFirstTodoId(TODO_URL);
        String url = TODO_URL + "/" + firstTodoId;
        String jsonInputString = """
                {
                  "title": "Updated Performance Test Todo",
                  "doneStatus": false,
                  "description": "This is an updated performance test todo"
                }""";
        executeRequest(url, "PUT", jsonInputString, 200);
    }

    private void createProject() throws Exception {
        String jsonInputString = """
                {
                  "title": "Performance Test Project",
                  "completed": false,
                  "active": true,
                  "description": "This is a performance test project"
                }""";
        executeRequest(PROJECTS_URL, "POST", jsonInputString, 201);
    }

    private void deleteProject() throws Exception {
        // Get the first project ID and delete it
        String firstProjectId = getFirstProjectId(PROJECTS_URL);
        String url = PROJECTS_URL + "/" + firstProjectId;
        executeRequest(url, "DELETE", null, 200);
    }

    private void updateProject() throws Exception {
        // Get the first project ID and update it
        String firstProjectId = getFirstProjectId(PROJECTS_URL);
        String url = PROJECTS_URL + "/" + firstProjectId;
        String jsonInputString = """
                {
                  "title": "Updated Performance Test Project",
                  "completed": false,
                  "active": true,
                  "description": "This is an updated performance test project"
                }""";
        executeRequest(url, "PUT", jsonInputString, 200);
    }

    // Reuse executeRequest and getFirstTodoId methods from Part A

    private void executeRequest(String url, String method, String jsonInputString, int expectedResponseCode) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod(method);
        connection.setRequestProperty("Accept", ACCEPT_JSON);
        if (jsonInputString != null) {
            connection.setRequestProperty("Content-Type", CONTENT_TYPE_JSON);
            connection.setDoOutput(true);
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
        }
        connection.connect();
        int responseCode = connection.getResponseCode();
        assertEquals(expectedResponseCode, responseCode);
        connection.disconnect();
    }

    private String getFirstTodoId(String baseUrl) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL(baseUrl).openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        StringBuilder content = new StringBuilder();
        try (var in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
        }
        connection.disconnect();
        String response = content.toString();
        return response.split("\"id\":")[1].split(",")[0].replace("\"", "").trim();
    }

    private String getFirstProjectId(String baseUrl) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL(baseUrl).openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        StringBuilder content = new StringBuilder();
        try (var in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
        }
        connection.disconnect();
        String response = content.toString();
        return response.split("\"id\":")[1].split(",")[0].replace("\"", "").trim();
    }
}
