package stepdefinitions;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;

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

    public static int findIdFromProjectName(String todoName) {
        JSONObject todo = findProjectByName(todoName);
        return todo != null ? todo.getInt("id") : -1;
    }



}