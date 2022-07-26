package ru.liga.praktikum.courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import io.restassured.response.Response;
import ru.liga.praktikum.model.CourierLogin;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import ru.liga.praktikum.client.CourierAPI;
import ru.liga.praktikum.model.Courier;

public class Create2SameCouriersTest {

    private CourierAPI courierAPI;
    private Courier courier;

    @Before
    public void setUp() {
        courier = new Courier("polina_login12", "ВилкинИложкин_202277", "Василий");
        courierAPI = new CourierAPI();
    }

    @DisplayName("Create 2 couriers with identical parameters.")
    @Description("Попытка создания курьера с уже существующим логином. Создается тестовый аккаунт курьера, потом создается 2 экземпляр. Получаем сообщение об ошибке создания 2 курьера и удаление 1.")
    @Test
    public void CreateTwoSameCourierTest()  {
        ValidatableResponse response = (ValidatableResponse) courierAPI.create(courier); // создание 1 курьера
        assertEquals(SC_CREATED, response.extract().statusCode()); // проверка статуса Об успешном создании нового аккаунта
        assertEquals(response.extract().path("ok"), true); // проверка выведенного сообщения
        ValidatableResponse FailResponse = (ValidatableResponse) courierAPI.create(new Courier("polina_login12", "ВилкинИложкин_202277", "Василий"));
        assertEquals(SC_CONFLICT, FailResponse.extract().statusCode()); // проверка статуса Об ошибке при создании дублирующего аккаунта
    }
    @After
    public void tearDown() {
        CourierLogin courierLogin = new CourierLogin(courier.getLogin(), courier.getPassword());
        Response response = courierAPI.deleteCourier(courierAPI.getID(courierLogin).then().extract().path("id"));
//        assertThat(response.then().extract().statusCode(), equalTo(SC_OK));
    }
}

