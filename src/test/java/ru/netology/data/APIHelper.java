package ru.netology.data;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class APIHelper {

    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setBasePath("/api/v1")
            .setPort(8080)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private APIHelper() {
    }

    public static String getStatus(DataHelper.CardInfo cardInfo, String path) {
        Response response =
                given()
                        .spec(requestSpec)
                        .body(cardInfo)
                        .post(path)
                        .then()
                        .contentType(ContentType.JSON)
                        .statusCode(200)
                        .extract().response();
        return response.jsonPath().getString("status");
    }

    public static void request(DataHelper.CardInfo cardInfo, String path, int statusCode) {
        given()
                .spec(requestSpec)
                .body(cardInfo)
                .post(path)
                .then()
                .statusCode(statusCode);
    }
}
