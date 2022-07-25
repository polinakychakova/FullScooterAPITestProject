import client.OrderAPI;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.junit.Before;
import org.junit.Test;

import static net.serenitybdd.rest.RestRequests.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class ReturnOrderListTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @DisplayName("Get full order list")
    @Description("Возращает полный список всех заказов. Запрос без параметров")
    @Test
    public void OrderFullListTest1(){
        OrderAPI orderAPI = new OrderAPI();
        ValidatableResponse response = (ValidatableResponse)
              orderAPI.OrderFullListTest().assertThat().body("orders", notNullValue())
                        .and().statusCode(200);

//        System.out.println(response.extract().body().asPrettyString());
    }
//    @DisplayName("Get order by ID")
//    @Description("Возращает информацию о заказе по переданному ID")
//    @Test
//    public void OrderByIDTest(){
//        ValidatableResponse response = (ValidatableResponse)
//                given()
//                        .param("t", 818083)
//                        .get("/api/v1/orders/track")
//                        .then().assertThat().body("order", notNullValue())
//                        .and().statusCode(200);
//        ;
//        System.out.println(response.extract().body().asPrettyString());
//    }
//
}
