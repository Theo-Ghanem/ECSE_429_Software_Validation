package stepdefinitions;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.path.json.JsonPath;

import static org.junit.Assert.*;

import java.util.List;

public class HelperMethods extends ServerSetup {
    public static String errorMsg;
    public static int httpStatus;
    public static JsonPath response;
    public static JsonPath initialTodoList;
    public static List<Object> tasksArray;
    public static int taskCounter;

    public static JsonPath getTodoByTitle(String title) {
        Response response = RestAssured.get("/todos");
        List<Object> todos = response.jsonPath().getList("todos");
        for (Object todo : todos) {
            JsonPath todoJson = new JsonPath(todo.toString());
            if (todoJson.getString("title").equals(title)) {
                return todoJson;
            }
        }
        return null;
    }

    public static JsonPath getProjectByTitle(String title) {
        Response response = RestAssured.get("/projects");
        List<Object> projects = response.jsonPath().getList("projects");
        for (Object project : projects) {
            JsonPath projectJson = new JsonPath(project.toString());
            if (projectJson.getString("title").equals(title)) {
                return projectJson;
            }
        }
        return null;
    }

    public static JsonPath fetchAllProjects() {
        Response response = RestAssured.get("/projects");
        return response.jsonPath();
    }

    public static JsonPath getCategoryByTitle(String title) {
        Response response = RestAssured.get("/categories");
        List<Object> categories = response.jsonPath().getList("categories");
        for (Object category : categories) {
            JsonPath categoryJson = new JsonPath(category.toString());
            if (categoryJson.getString("title").equals(title)) {
                return categoryJson;
            }
        }
        return null;
    }

    public static List<Object> fetchProjectTasks(String title) {
        JsonPath project = getProjectByTitle(title);
        if (project == null) return null;
        int projectId = project.getInt("id");
        Response response = RestAssured.get("/projects/" + projectId + "/tasks");
        return response.jsonPath().getList("todos");
    }

    public static int getTodoIdByTitle(String title) {
        JsonPath todo = getTodoByTitle(title);
        if (todo == null) return -1;
        return todo.getInt("id");
    }

    public static int getCategoryIdByTodoTitle(String categoryTitle, String todoTitle) {
        Response response = RestAssured.get("/todos");
        List<Object> todos = response.jsonPath().getList("todos");
        for (Object todo : todos) {
            JsonPath todoJson = new JsonPath(todo.toString());
            if (todoJson.getString("title").equals(todoTitle)) {
                int todoId = todoJson.getInt("id");
                Response categoryResponse = RestAssured.get("/todos/" + todoId + "/categories");
                List<Object> categories = categoryResponse.jsonPath().getList("categories");
                for (Object category : categories) {
                    JsonPath categoryJson = new JsonPath(category.toString());
                    if (categoryJson.getString("title").equals(categoryTitle)) {
                        return categoryJson.getInt("id");
                    }
                }
            }
        }
        return -1;
    }

//    public JsonPath createTodoFromRow(List<String> columns) {
//        String jsonBody = "{ \"title\": \"" + columns.get(0) + "\", \"doneStatus\": " + columns.get(1) + ", \"description\": \"" + columns.get(2) + "\" }";
//        Response response = RestAssured.given().contentType("application/json").body(jsonBody).post("/todos");
//        if (columns.size() == 4) {
//            assignPriorityToTask(columns.get(0), columns.get(3));
//        }
//        return response.jsonPath();
//    }

//    public static void verifyDoneStatus(JsonPath todo, boolean expectedStatus) {
//        assertNotNull(todo);
//        assertEquals(String.valueOf(expectedStatus), todo.getString("doneStatus"));
//    }
//
//    public static void checkProjectForIncompleteTasks(String projectTitle, boolean checkIncomplete) {
//        List<Object> tasks = fetchProjectTasks(projectTitle);
//        for (Object task : tasks) {
//            JsonPath taskJson = new JsonPath(task.toString());
//            int taskId = taskJson.getInt("id");
//            Response response = RestAssured.get("/todos/" + taskId);
//            JsonPath todo = response.jsonPath().getList("todos").get(0);
//            if (!checkIncomplete || todo.getString("doneStatus").equalsIgnoreCase("false")) {
//                return;
//            }
//        }
//        fail();
//    }

//    public void assignPriorityToTask(String taskTitle, String priority) {
//        int taskId = getTodoIdByTitle(taskTitle.replace("\"", ""));
//        Response response = RestAssured.post("/todos/" + taskId + "/categories")
//                .body("{ \"title\": \"" + priority.replace("\"", "") + "\" }").asJson();
//        httpStatus = response.getStatusCode();
//        if (httpStatus != 200 && httpStatus != 201) {
//            errorMsg = response.jsonPath().getList("errorMessages").get(0).toString();
//        }
//    }
}
