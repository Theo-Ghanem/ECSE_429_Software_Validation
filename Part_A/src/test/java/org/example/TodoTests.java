package org.example;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class TodoTests {

    private static final String TODO_URL = "http://localhost:4567/todos";
    private static final String CONTENT_TYPE_JSON = "application/json; utf-8";
    private static final String ACCEPT_JSON = "application/json";

    @Test
    void testGETTodo() {
        executeRequest(TODO_URL, "GET", null, 200);
    }

    @Test
    void testHEADTodo() {
        executeRequest(TODO_URL, "HEAD", null, 200);
    }

    @Test
    void testPOSTTodo() {
        String jsonInputString = """
                {
                  "title": "Complete 429 Project",
                  "doneStatus": false,
                  "description": "Complete exploratory testing Part A"
                }""";
        executeRequest(TODO_URL, "POST", jsonInputString, 201);
    }

    @Test
    void testOPTIONSTodo() {
        executeRequest(TODO_URL, "OPTIONS", null, 200);
    }

    @Test
    void testGETTodoById() {
        try {
            String firstTodoId = getFirstTodoId(TODO_URL);
            String todoUrl = TODO_URL + "/" + firstTodoId;
            executeRequest(todoUrl, "GET", null, 200);
        } catch (IOException e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    @Test
    void testHEADTodoById() {
        try {
            String firstTodoId = getFirstTodoId(TODO_URL);
            String todoUrl = TODO_URL + "/" + firstTodoId;
            executeRequest(todoUrl, "HEAD", null, 200);
        } catch (IOException e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    @Test
    void testPOSTTodoByID() {
        String jsonInputString = """
                {
                  "title": "This title was Updated by POST",
                  "doneStatus": false,
                  "description": "This description was updated with a POST request"
                }""";
        try {
            String firstTodoId = getFirstTodoId(TODO_URL);
            String url = TODO_URL + "/" + firstTodoId;
            executeRequest(url, "POST", jsonInputString, 200);
        } catch (IOException e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    @Test
    void testPUTTodoByID() {
        testPOSTTodo();
        String jsonInputString = """
                {
                  "title": "This title was Updated by PUT",
                  "doneStatus": false,
                  "description": "This description was updated with a PUT request"
                }""";
        try {
            String firstTodoId = getFirstTodoId(TODO_URL);
            String url = TODO_URL + "/" + firstTodoId;
            executeRequest(url, "PUT", jsonInputString, 200);
        } catch (IOException e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    @Test
    void testDELETETodoByID() {
        try {
            String firstTodoId = getFirstTodoId(TODO_URL);
            String url = TODO_URL + "/" + firstTodoId;
            executeRequest(url, "DELETE", null, 200);
        } catch (IOException e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    @Test
    void testPOSTTodoWithMalformedJSON() {
        String malformedJsonInputString = """
                {
                  "title": "Malformed JSON Todo",
                  "doneStatus": false,
                  "description": "Description of the new todo
                }"""; // Missing closing quote
        executeRequest(TODO_URL, "POST", malformedJsonInputString, 400);
    }

    @Test
    void testPOSTTodoWithMalformedXML() {
        String malformedXmlInputString = """
                <todo>
                  <title>Malformed XML Todo</title>
                  <doneStatus>false</doneStatus>
                  <description>Description of the new todo</description
                </todo>"""; // Missing closing tag
        executeRequestWithXML(TODO_URL, "POST", malformedXmlInputString, 400);
    }

    @Test
    void testDELETETodoAlreadyDeleted() {
        try {
            String firstTodoId = getFirstTodoId(TODO_URL);
            String url = TODO_URL + "/" + firstTodoId;
            executeRequest(url, "DELETE", null, 200);
            // Attempt to delete again
            executeRequest(url, "DELETE", null, 404);
        } catch (IOException e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    // Helper method for XML requests
    private void executeRequestWithXML(String url, String method, String xmlInputString, int expectedResponseCode) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod(method);
            connection.setRequestProperty("Accept", "application/xml");
            if (xmlInputString != null) {
                connection.setRequestProperty("Content-Type", "application/xml; utf-8");
                connection.setDoOutput(true);
                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = xmlInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }
            }
            connection.connect();
            int responseCode = connection.getResponseCode();
            assertEquals(expectedResponseCode, responseCode);
            connection.disconnect();
        } catch (IOException e) {
            fail("IOException occurred: " + e.getMessage());
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    // Helper methods

    private void executeRequest(String url, String method, String jsonInputString, int expectedResponseCode) {
        try {
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
        } catch (IOException e) {
            fail("IOException occurred: " + e.getMessage());
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    private static String getFirstTodoId(String baseUrl) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(baseUrl).openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        int responseCode = connection.getResponseCode();
        assertEquals(200, responseCode);

        StringBuilder content = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
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