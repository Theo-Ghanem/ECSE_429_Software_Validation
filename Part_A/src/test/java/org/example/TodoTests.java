package org.example;

import org.junit.jupiter.api.Test;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class TodoTests {

    @Test
    void testGetTodo() {
        String url = "http://localhost:4567/todos";
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            assertEquals(200, responseCode);

            connection.disconnect();
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    @Test
    void testCreateTodo() {
        String url = "http://localhost:4567/todos";
        String jsonInputString = """
                {
                  "title": "Complete 429 Project",
                  "doneStatus": false,
                  "description": "Complete exploratory testing Part A"
                }""";
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            assertEquals(201, responseCode);

            connection.disconnect();
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }
}