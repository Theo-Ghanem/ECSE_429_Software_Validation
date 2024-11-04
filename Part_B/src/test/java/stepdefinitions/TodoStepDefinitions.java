package stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TodoStepDefinitions {

    private CloseableHttpClient httpClient = HttpClients.createDefault();
    private String baseUrl = "http://localhost:4567";
    private CloseableHttpResponse response;

    @Given("the API server is running")
    public void the_api_server_is_running() throws IOException {
        HttpGet request = new HttpGet(baseUrl + "/todos");
        response = httpClient.execute(request);
        assertEquals(200, response.getStatusLine().getStatusCode());
        response.close();
    }

    @When("a user create a todo with a title, a doneStatus a description:")
    public void a_user_create_a_todo_with_a_title_a_done_status_a_description(DataTable dataTable) throws IOException {
        for (Map<String, String> row : dataTable.asMaps(String.class, String.class)) {
            JSONObject requestParams = new JSONObject();
            requestParams.put("title", row.get("title"));
            requestParams.put("doneStatus", Boolean.parseBoolean(row.get("doneStatus")));
            requestParams.put("description", row.get("description"));

            HttpPost request = new HttpPost(baseUrl + "/todos");
            request.setHeader("Content-Type", "application/json");
            request.setEntity(new StringEntity(requestParams.toString()));

            response = httpClient.execute(request);
            assertEquals(201, response.getStatusLine().getStatusCode());
            response.close();
        }
    }

    @Then("the following todos shall exist in the system:")
    public void the_following_todos_shall_exist_in_the_system(DataTable dataTable) throws IOException {
        for (Map<String, String> row : dataTable.asMaps(String.class, String.class)) {
            int id = getTodoIdByTitle(row.get("title"));
            HttpGet request = new HttpGet(baseUrl + "/todos/" + id);
            response = httpClient.execute(request);

            String responseBody = EntityUtils.toString(response.getEntity());
            JSONObject jsonResponse = new JSONObject(responseBody);

            assertEquals(200, response.getStatusLine().getStatusCode());
            assertEquals(row.get("title"), jsonResponse.getString("title"));
            assertEquals(Boolean.parseBoolean(row.get("doneStatus")), jsonResponse.getBoolean("doneStatus"));
            assertEquals(row.get("description"), jsonResponse.getString("description"));
            response.close();
        }
    }

    @When("a user create a todo with a title, a doneStatus an empty description")
    public void a_user_create_a_todo_with_a_title_a_done_status_an_empty_description(DataTable dataTable) throws IOException {
        for (Map<String, String> row : dataTable.asMaps(String.class, String.class)) {
            JSONObject requestParams = new JSONObject();
            requestParams.put("title", row.get("title"));
            requestParams.put("doneStatus", Boolean.parseBoolean(row.get("doneStatus")));
            requestParams.put("description", "");

            HttpPost request = new HttpPost(baseUrl + "/todos");
            request.setHeader("Content-Type", "application/json");
            request.setEntity(new StringEntity(requestParams.toString()));

            response = httpClient.execute(request);
            assertEquals(201, response.getStatusLine().getStatusCode());
            response.close();
        }
    }

    @Then("the following todos shall still exist in the system")
    public void the_following_todos_shall_still_exist_in_the_system(DataTable dataTable) throws IOException {
        for (Map<String, String> row : dataTable.asMaps(String.class, String.class)) {
            int id = getTodoIdByTitle(row.get("title"));
            HttpGet request = new HttpGet(baseUrl + "/todos/" + id);
            response = httpClient.execute(request);

            String responseBody = EntityUtils.toString(response.getEntity());
            JSONObject jsonResponse = new JSONObject(responseBody);

            assertEquals(200, response.getStatusLine().getStatusCode());
            assertEquals(row.get("title"), jsonResponse.getString("title"));
            assertEquals(Boolean.parseBoolean(row.get("doneStatus")), jsonResponse.getBoolean("doneStatus"));
            assertEquals("", jsonResponse.getString("description"));
            response.close();
        }
    }

    @When("a user create a todo with an empty title")
    public void a_user_create_a_todo_with_an_empty_title(DataTable dataTable) throws IOException {
        for (Map<String, String> row : dataTable.asMaps(String.class, String.class)) {
            JSONObject requestParams = new JSONObject();
            requestParams.put("title", "");
            requestParams.put("doneStatus", Boolean.parseBoolean(row.get("doneStatus")));
            requestParams.put("description", "");

            HttpPost request = new HttpPost(baseUrl + "/todos");
            request.setHeader("Content-Type", "application/json");
            request.setEntity(new StringEntity(requestParams.toString()));

            response = httpClient.execute(request);
            assertEquals(400, response.getStatusLine().getStatusCode());
            response.close();
        }
    }

    @Then("the following todos with the title shall not exist")
    public void the_following_todos_with_the_title_shall_not_exist(DataTable dataTable) throws IOException {
        for (Map<String, String> row : dataTable.asMaps(String.class, String.class)) {
            int id = getTodoIdByTitle(row.get("title"));
            HttpGet request = new HttpGet(baseUrl + "/todos/" + id);

            response = httpClient.execute(request);
            assertEquals(404, response.getStatusLine().getStatusCode());
            response.close();
        }
    }

    @Then("the server will raise an error")
    public void the_server_will_raise_an_error(DataTable dataTable) throws IOException {
        for (Map<String, String> row : dataTable.asMaps(String.class, String.class)) {
            JSONObject requestParams = new JSONObject();
            requestParams.put("title", row.get("title"));
            requestParams.put("doneStatus", Boolean.parseBoolean(row.get("doneStatus")));
            requestParams.put("description", row.get("description"));

            HttpPost request = new HttpPost(baseUrl + "/todos");
            request.setHeader("Content-Type", "application/json");
            request.setEntity(new StringEntity(requestParams.toString()));

            response = httpClient.execute(request);
            assertEquals(400, response.getStatusLine().getStatusCode());
            response.close();
        }
    }


    private int getTodoIdByTitle(String title) throws IOException {
    HttpGet request = new HttpGet(baseUrl + "/todos");
    response = httpClient.execute(request);

    String responseBody = EntityUtils.toString(response.getEntity());
    response.close();

    JSONObject jsonResponse = new JSONObject(responseBody);
    JSONArray todos = jsonResponse.getJSONArray("todos");

    for (int i = 0; i < todos.length(); i++) {
        JSONObject todo = todos.getJSONObject(i);
        if (todo.getString("title").equals(title)) {
            return todo.getInt("id");
        }
    }
    return -1; // or throw an exception if not found
}
}