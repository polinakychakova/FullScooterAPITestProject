package client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.PreemptiveAuthSpec;
import model.Order;

import java.io.File;

import static net.serenitybdd.rest.RestRequests.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderAPI extends BaseHttpClient{
    private static final String ORDERS_PATH = "/api/v1/orders";
    private static final String FINISH_ORDER_PATH = "/api/v1/orders/finish/";
    private static final String GET_ORDER_ID_PATH = "/api/v1/orders/track";
    private static final String CANCEL_ORDER_PATH = "/api/v1/orders/cancel";

    @Step("Get full list of orders")
    public ValidatableResponse OrderFullListTest(){
        return
                given()
                        .spec(baseSpec())
                        .get(ORDERS_PATH).then()
                ;
    }

    @Step("Creating a new order")
    public ValidatableResponse CreateNewOrder(Order order){
//    public ValidatableResponse CreateNewOrder(String fileName){
//        File json = new File (fileName);
        ValidatableResponse response =   (ValidatableResponse) given()
                .spec(baseSpec())
//                .header("Content-type", "application/json")
                .body(order)
//                .log().all()
                .when()
                .post(ORDERS_PATH)
                .then()
                ;
        return response;

    }

    @Step("Creating a new order")
    public ValidatableResponse CreateNewOrderByFile(String fileName){
        File json = new File (fileName);
        ValidatableResponse response =   (ValidatableResponse) given()
                .spec(baseSpec())
                .header("Content-type", "application/json")
                .body(json)
                .log().all()
                .when()
                .post(ORDERS_PATH)
                .then()
                ;
        return response;

    }
    @Step("Creating a new order only with color")
    public ValidatableResponse CreateNewOrderW(String color){
//        System.out.println(order);
        ValidatableResponse response =   (ValidatableResponse) given()
                .spec(baseSpec())
                .body(color)
                .when()
                .post(ORDERS_PATH)
                .then()
                ;
        return response;

    }

//    @Step("Finishing the order")
//    public ValidatableResponse FinishOrder(int id)
//    {
//        return (ValidatableResponse) given()
//                .spec(baseSpec())
//                .when()
//                .put(FINISH_ORDER_PATH+id)// отмена заказа
//                .then()
//                .log().ifError();
//    }

//    @Step("Get order ID")
//    public int GetOrderID(int track)
//    {
//        String trackStr = "?t="+ track;
//        ValidatableResponse response = given()
//                .spec(baseSpec())
//                .when()
//                .get(GET_ORDER_ID_PATH + trackStr)// отмена заказа
//                .then();
//
//        response.assertThat().statusCode(200);
//        return response.extract().path("order.id");
//    }

    @Step("Cancel order")
    public ValidatableResponse CancelOrder(int track)
    {
        String trackStr = "{\"track\": "+ track +" }";
        ValidatableResponse response = given()
                .spec(baseSpec())
                .body(trackStr)
                .when()
//                .log().all()
                .put(CANCEL_ORDER_PATH)// отмена заказа
                .then();

//        response.assertThat().statusCode(200);
        return response;
    }

}
