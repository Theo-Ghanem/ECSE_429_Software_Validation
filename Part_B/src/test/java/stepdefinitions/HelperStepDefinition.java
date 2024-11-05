package stepdefinitions;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class HelperStepDefinition extends ServerSetup {
    public static String errorMessage;
    public static int statusCode;

    private static final String TODOS_ENDPOINT = "/todos";
    private static final String PROJECTS_ENDPOINT = "/projects";

    public static JSONObject findTodoByName(String todoName) {
        return findByName(TODOS_ENDPOINT, todoName);
    }

    public static JSONObject findProjectByName(String projectName) {
        return findByName(PROJECTS_ENDPOINT, projectName);
    }

    private static JSONObject findByName(String endpoint, String name) {
        Response response = RestAssured.get(endpoint);
        JSONArray items = new JSONArray(response.getBody().asString());
        for (Object item : items) {
            JSONObject obj = (JSONObject) item;
            if (obj.getString("title").equals(name)) {
                return obj;
            }
        }
        return null;
    }

    public static JSONObject getAllProjects() {
        Response response = RestAssured.get(PROJECTS_ENDPOINT);
        return new JSONObject(response.getBody().asString());
    }

    public static JSONArray getProjectTasks(String projectName) {
        JSONObject project = findProjectByName(projectName);
        if (project == null) return null;
        int id = project.getInt("id");
        Response response = RestAssured.get(PROJECTS_ENDPOINT + "/" + id + "/tasks");
        return new JSONArray(response.getBody().asString());
    }

    public static int findIdFromTodoName(String todoName) {
        JSONObject todo = findTodoByName(todoName);
        return todo != null ? todo.getInt("id") : -1;
    }


    public JSONObject addTodoByRow(List<String> columns) {
        String body = String.format("{\"title\":\"%s\",\"doneStatus\":%s,\"description\":\"%s\"}",
                columns.get(0), columns.get(1), columns.get(2));
        Response response = RestAssured.given().body(body).post(TODOS_ENDPOINT);
        JSONObject todoObj = new JSONObject(response.getBody().asString());
        if (columns.size() == 4) {
            requestPriorityForTodo(columns.get(0), columns.get(3));
        }
        return todoObj;
    }

    public void requestPriorityForTodo(String todoTitle, String priorityToAssign) {
        int id = findIdFromTodoName(todoTitle.replace("\"", ""));
        String body = String.format("{\"title\":\"%s\"}", priorityToAssign.replace("\"", ""));
        Response response = RestAssured.given().body(body).post(TODOS_ENDPOINT + "/" + id + "/categories");
        statusCode = response.getStatusCode();
        if (statusCode != 200 && statusCode != 201) {
            errorMessage = new JSONObject(response.getBody().asString()).getJSONArray("errorMessages").getString(0);
        }
    }
}