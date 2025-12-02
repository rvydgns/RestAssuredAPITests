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

    // aldığım apı anahtarı
    private static final String API_KEY = "reqres_dea273da06144ccbb603f46960e1abb1"; // SİZİN KEYİNİZ!

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }

    // GET
    @Test
    public void getSingleUser_ShouldReturnCorrectDataAndPerformChecks() {
        given()
                .log().all()
                // 🛠️ Düzeltme: X-Api-Key ve Bearer öneki kaldırıldı
                .header("X-Api-Key", API_KEY)
                .when()
                .get("/users/2")
                .then()
                .log().all()
                .statusCode(200)
                .body("data.id", equalTo(2))
                .body("data.email", equalTo("janet.weaver@reqres.in"))
                .time(lessThan(2000L), TimeUnit.MILLISECONDS);
    }

    // POST
    @Test
    public void postCreateUser_ShouldCreateUserAndReturnCorrectStatusAndBody() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("name", "Mert");
        requestBody.put("job", "Software Test Engineer");

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
                .body("name", equalTo("Mert"))
                .body("job", equalTo("Software Test Engineer"))
                .body("id", notNullValue())
                .time(lessThan(2000L), TimeUnit.MILLISECONDS);
    }
}