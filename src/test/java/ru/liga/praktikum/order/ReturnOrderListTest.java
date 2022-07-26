package ru.liga.praktikum.order;

import ru.liga.praktikum.client.OrderAPI;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static net.serenitybdd.rest.RestRequests.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;

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
        ValidatableResponse response = (ValidatableResponse) orderAPI.OrderFullListTest();
        response.assertThat().body("orders", notNullValue()); // возвращается список заказов
        response.assertThat().body("orders.id", notNullValue()); // список заказов не пустой
        assertEquals(SC_OK, response.extract().statusCode()); //проеврка статуса ответа
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
