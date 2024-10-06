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

class ProjectsTests {

    private static final String PROJECTS_URL = "http://localhost:4567/projects";
    private static final String CONTENT_TYPE_JSON = "application/json; utf-8";
    private static final String ACCEPT_JSON = "application/json";

    @Test
    void testGETProjects() {
        executeRequest(PROJECTS_URL, "GET", null, 200);
    }

    @Test
    void testHEADProjects() {
        executeRequest(PROJECTS_URL, "HEAD", null, 200);
    }

    @Test
    void testPOSTProjects() {
        String jsonInputString = """
                {
                  "title": "New Project",
                  "completed": false,
                  "active": true,
                  "description": "Description of the new project"
                }""";
        executeRequest(PROJECTS_URL, "POST", jsonInputString, 201);
    }

    @Test
    void testOPTIONSProjects() {
        executeRequest(PROJECTS_URL, "OPTIONS", null, 200);
    }

    @Test
    void testGETProjectById() {
        try {
            String firstProjectId = getFirstProjectId(PROJECTS_URL);
            String projectUrl = PROJECTS_URL + "/" + firstProjectId;
            executeRequest(projectUrl, "GET", null, 200);
        } catch (IOException e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    @Test
    void testHEADProjectById() {
        try {
            String firstProjectId = getFirstProjectId(PROJECTS_URL);
            String projectUrl = PROJECTS_URL + "/" + firstProjectId;
            executeRequest(projectUrl, "HEAD", null, 200);
        } catch (IOException e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    @Test
    void testPOSTProjectById() {
        String jsonInputString = """
                {
                  "title": "Updated Project Title",
                  "completed": false,
                  "active": true,
                  "description": "Updated description with a POST request"
                }""";
        try {
            String firstProjectId = getFirstProjectId(PROJECTS_URL);
            String url = PROJECTS_URL + "/" + firstProjectId;
            executeRequest(url, "POST", jsonInputString, 200);
        } catch (IOException e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    @Test
    void testPUTProjectById() {
        String jsonInputString = """
                {
                  "title": "Updated Project Title by PUT",
                  "completed": false,
                  "active": true,
                  "description": "Updated description with a PUT request"
                }""";
        try {
            String firstProjectId = getFirstProjectId(PROJECTS_URL);
            String url = PROJECTS_URL + "/" + firstProjectId;
            executeRequest(url, "PUT", jsonInputString, 200);
        } catch (IOException e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    @Test
    void testDELETEProjectById() {
        try {
            String firstProjectId = getFirstProjectId(PROJECTS_URL);
            String url = PROJECTS_URL + "/" + firstProjectId;
            executeRequest(url, "DELETE", null, 200);
        } catch (IOException e) {
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

    private static String getFirstProjectId(String baseUrl) throws IOException {
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