package com.yazilimtestodevi;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Test;
import org.json.JSONObject;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ReqresAPITests {
    private static final String API_KEY = "reqres_dea273da06144ccbb603f46960e1abb1";

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";  //
    }

    @Test
    public void getSingleUser() {
        given()
                .log().all()
                .header("X-Api-Key", API_KEY)
                .when()
                .get("/users/2")
                .then()
                .log().all()
                .statusCode(200)
                .body("data.id", equalTo(2))
                .body("data.email", equalTo("janet.weaver@reqres.in"))
                .time(lessThan(5000L), TimeUnit.MILLISECONDS);
    }

    @Test
    public void postCreateUser() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("name", "Rüveyda Nur");
        requestBody.put("job", "Yazılım Müh.");

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .header("X-Api-Key", API_KEY)
                .body(requestBody.toString())
                .when()
                .post("/users")
                .then()
                .log().all()
                .statusCode(201)
                .body("name", equalTo("Rüveyda Nur"))
                .body("job", equalTo("Yazılım Müh."))
                .body("id", notNullValue())
                .time(lessThan(5000L), TimeUnit.MILLISECONDS);
    }
}
