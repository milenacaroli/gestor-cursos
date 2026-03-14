package br.com.milenacaroli;

import br.com.milenacaroli.dto.CourseRequest;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
public class CourseResourceTest {

    @Test
    public void shouldCreateCourse() {
        CourseRequest request = new CourseRequest("Java Basics");

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/courses")
                .then()
                .statusCode(201)
                .header("Location", notNullValue())
                .body("name", equalTo("Java Basics"));
    }

    @Test
    public void shouldNotCreateCourseWithEmptyName() {
        CourseRequest request = new CourseRequest("");

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/courses")
                .then()
                .statusCode(400);
    }

}
