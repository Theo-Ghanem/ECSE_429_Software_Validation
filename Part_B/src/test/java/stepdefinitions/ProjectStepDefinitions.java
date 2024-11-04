package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ProjectStepDefinitions {

    private CloseableHttpClient httpClient = HttpClients.createDefault();
    private String baseUrl = "http://localhost:4567";
    private CloseableHttpResponse response;

    @Given("a project with title {string} exists")
    public void a_project_with_title_exists(String title) throws IOException {
        JSONObject requestParams = new JSONObject();
        requestParams.put("title", title);
        requestParams.put("completed", false);
        requestParams.put("active", true);
        requestParams.put("description", "Sample Project Description");

        HttpPost request = new HttpPost(baseUrl + "/projects");
        request.setHeader("Content-Type", "application/json");
        request.setEntity(new StringEntity(requestParams.toString()));

        response = httpClient.execute(request);
        assertEquals(201, response.getStatusLine().getStatusCode());
        response.close();
    }

    @When("I delete the project with title {string}")
    public void i_delete_the_project_with_title(String title) throws IOException {
        int id = getProjectIdByTitle(title);
        HttpDelete request = new HttpDelete(baseUrl + "/projects/" + id);

        response = httpClient.execute(request);
        assertEquals(200, response.getStatusLine().getStatusCode());
        response.close();
    }

    @Then("the project with title {string} should not exist")
    public void the_project_with_title_should_not_exist(String title) throws IOException {
        int id = getProjectIdByTitle(title);
        HttpGet request = new HttpGet(baseUrl + "/projects/" + id);

        response = httpClient.execute(request);
        assertEquals(404, response.getStatusLine().getStatusCode());
        response.close();
    }

    @When("I create a project with title {string} and description {string}")
    public void i_create_a_project_with_title_and_description(String title, String description) throws IOException {
        JSONObject requestParams = new JSONObject();
        requestParams.put("title", title);
        requestParams.put("description", description);
        requestParams.put("completed", false);
        requestParams.put("active", true);

        HttpPost request = new HttpPost(baseUrl + "/projects");
        request.setHeader("Content-Type", "application/json");
        request.setEntity(new StringEntity(requestParams.toString()));

        response = httpClient.execute(request);
        assertEquals(201, response.getStatusLine().getStatusCode());
        response.close();
    }

    @Then("a project with title {string} should exist")
    public void a_project_with_title_should_exist(String title) throws IOException {
        int id = getProjectIdByTitle(title);
        HttpGet request = new HttpGet(baseUrl + "/projects/" + id);

        response = httpClient.execute(request);
        assertEquals(200, response.getStatusLine().getStatusCode());
        response.close();
    }

    @When("I update the description of the project with title {string} to {string}")
    public void i_update_the_description_of_the_project_with_title_to(String title, String newDescription) throws IOException {
        int id = getProjectIdByTitle(title);
        JSONObject requestParams = new JSONObject();
        requestParams.put("description", newDescription);

        HttpPut request = new HttpPut(baseUrl + "/projects/" + id);
        request.setHeader("Content-Type", "application/json");
        request.setEntity(new StringEntity(requestParams.toString()));

        response = httpClient.execute(request);
        assertEquals(200, response.getStatusLine().getStatusCode());
        response.close();
    }

    @Then("the project with title {string} should have description {string}")
    public void the_project_with_title_should_have_description(String title, String description) throws IOException {
        int id = getProjectIdByTitle(title);
        HttpGet request = new HttpGet(baseUrl + "/projects/" + id);

        response = httpClient.execute(request);
        String responseBody = EntityUtils.toString(response.getEntity());
        JSONObject jsonResponse = new JSONObject(responseBody);

        assertEquals(200, response.getStatusLine().getStatusCode());
        assertEquals(description, jsonResponse.getString("description"));
        response.close();
    }

    private int getProjectIdByTitle(String title) throws IOException {
        HttpGet request = new HttpGet(baseUrl + "/projects");
        response = httpClient.execute(request);

        String responseBody = EntityUtils.toString(response.getEntity());
        response.close();

        // Assuming the response is a JSON array
        JSONArray projects = new JSONArray(responseBody);
        for (int i = 0; i < projects.length(); i++) {
            JSONObject project = projects.getJSONObject(i);
            if (project.getString("title").equals(title)) {
                return project.getInt("id");
            }
        }
        return -1; // or throw an exception if not found
    }
}