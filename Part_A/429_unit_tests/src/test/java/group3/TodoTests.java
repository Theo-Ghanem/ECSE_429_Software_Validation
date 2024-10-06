package group3;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class TodoTests {

    @Test
    public void testGetTodos() {
        given()
            .when()
            .get("http://localhost:4567/todos")
            .then()
            .statusCode(200)
            .body("size()", greaterThan(0));
    }
}