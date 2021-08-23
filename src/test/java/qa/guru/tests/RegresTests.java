package qa.guru.tests;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

public class RegresTests {

    @Test
    void checkUsersTotalTest(){

        get("https://reqres.in/api/users?page=2")
                .then()
                .body("total", is(12));
    }

    @Test
    void checkFirstUserEmailTest(){

        get("https://reqres.in/api/users?page=2")
                .then()
                .body("data[0].email", is("michael.lawson@reqres.in"));
    }

    @Test
    void checkStringUserTest(){

        String response= get("https://reqres.in/api/users?page=2")
                .then()
                .extract().path("data[0].email");

        assertThat(response).isEqualTo("michael.lawson@reqres.in");

    }

    @Test
    void checkListUserTest(){

        List<String> response= get("https://reqres.in/api/users?page=2")
                .then()
                .extract().path("data.email");

        for (String res:response){
            System.out.println("Email is -> " + res);
        }

    }

    @Test
    void checkLoginWithRightJsonTest(){

        Map<String, Object> json = new HashMap<>();
        json.put("email", "eve.holt@reqres.in");
        json.put("password", "cityslicka");

        given().
                contentType(JSON).
                body(json).
                when().
                post("https://reqres.in/api/login").
                then().
                statusCode(200)
               .body("token", is("QpwL5tke4Pnpja7X4"));

    }

}
