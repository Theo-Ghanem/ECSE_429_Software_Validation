package org.example;

import org.junit.jupiter.api.BeforeAll;
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

    @BeforeAll
    static void setUp() {
        for (int i = 1; i <= 5; i++) {
            String jsonInputString = String.format("""
                    {
                      "title": "Project %d",
                      "completed": false,
                      "active": true,
                      "description": "Description of project %d"
                    }""", i, i);
            executeRequest(PROJECTS_URL, "POST", jsonInputString, 201);
        }
    }

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

    @Test
    void testPOSTProjectsWithMalformedJSON() {
        String malformedJsonInputString = """
                {
                  "title": "Malformed JSON Project",
                  "completed": false,
                  "active": true,
                  "description": "Description of the new project
                }"""; // Missing closing quote
        executeRequest(PROJECTS_URL, "POST", malformedJsonInputString, 400);
    }

    @Test
    void testPOSTProjectsWithMalformedXML() {
        String malformedXmlInputString = """
                <project>
                  <title>Malformed XML Project/title>
                  <completed>false</completed>
                  <active>true</active>
                  <description>Description of the new project</description
                </project>"""; // Missing closing tag
        executeRequestWithXML(PROJECTS_URL, "POST", malformedXmlInputString, 400);
    }

    @Test
    void testDELETEProjectAlreadyDeleted() {
        try {
            String firstProjectId = getFirstProjectId(PROJECTS_URL);
            String url = PROJECTS_URL + "/" + firstProjectId;
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
    private static void executeRequest(String url, String method, String jsonInputString, int expectedResponseCode) {
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