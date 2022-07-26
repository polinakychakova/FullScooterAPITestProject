package ru.liga.praktikum.courier;

import ru.liga.praktikum.client.CourierAPI;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import ru.liga.praktikum.model.Courier;
import ru.liga.praktikum.model.CourierLogin;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static net.serenitybdd.rest.RestRequests.given;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CourierLoginTest {
    private CourierAPI courierAPI;
    private CourierLogin courierLogin;
    private Courier courier;
    private int expCode;
    public CourierLoginTest(String login, String password, int code) {
        this.courierLogin = new CourierLogin(login, password);
        this.expCode = code;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        courierAPI = new CourierAPI();
        courier = new Courier("polina_login12", "ВилкинИложкин_202277", "Test");
        courierAPI.create(courier);
    }

    @Parameterized.Parameters
    public static Object[] getLoginInfo() {
        return new Object[][]{
                {"", "", SC_BAD_REQUEST},
                {"", "ВилкинИложкин_20227", SC_BAD_REQUEST},
                {"polina_login12", "", SC_BAD_REQUEST},
                {"polina_login12", "ВилкинИложкин_20227", SC_NOT_FOUND}, // неверный пароль
                {"polina_log", "ВилкинИложкин_202277", SC_NOT_FOUND}, // неверный логин
                {"polina_login12", "ВилкинИложкин_202277", SC_OK} //верный набор данных
        };
    }

    @After
    public void tearDown() {
        courierLogin = new CourierLogin(courier.getLogin(), courier.getPassword());
        courierAPI.deleteCourier(courierAPI.getID(courierLogin).then().extract().path("id"));
    }

    @DisplayName("Try login.")
    @Description("Неудачные попытки авторизации с некорректными данными ( или без части данных). Параметризованный тест. " +
            "Удачная попытка авторизации проверена на этапе тестов Создания нового аккаунта для курьера")
    @Test
    public void TryToLogin()  {
        Response response = courierAPI.getID(courierLogin);
        assertEquals(expCode, response.then().extract().statusCode());
    }

}
