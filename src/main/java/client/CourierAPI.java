package client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.Courier;
import io.restassured.response.ValidatableResponse;
import model.CourierLogin;

import javax.swing.*;

import static net.serenitybdd.rest.RestRequests.given;

public class CourierAPI extends BaseHttpClient{
    private  static  final  String COURIER_PATH = "/api/v1/courier/";
    private static final String ID_PATH = "/api/v1/courier/login";
    private static final String DEL_PATH = "/api/v1/courier/";
    @Step("Send POST request to /api/v1/courier : {courier}")
    public ValidatableResponse create(Courier courier) {
        return given()
                .spec(baseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then()
                ;
//                .log().ifError();
    }

    @Step("Send POST request to /api/v1/courier/login to gey CourierId")
    public Response getID(CourierLogin courier){
        return given()
                .spec(baseSpec())
                .body(courier)
//                .log().all()
                .when()
                .post(ID_PATH);
//                .then();
//        .extract().path("id");
    }

    @Step("Send POST request to /api/v1/courier/login to gey CourierId")
    public Response deleteCourier(int id){
        return given()
                .spec(baseSpec())
//                .body(courier)
//                .log().all()
                .when()
                .delete(DEL_PATH + id);
//                .then();
    }

}