package ru.liga.praktikum.courier;

import ru.liga.praktikum.client.CourierAPI;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import ru.liga.praktikum.model.Courier;
import ru.liga.praktikum.model.CourierLogin;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CourierCreateTest {
    @Parameterized.Parameters // создания аккаунта курьера
    public static Object[] getCourierInfo() {
        return new Object[][]{
                {"", "", "", SC_BAD_REQUEST},
                {"", "", "тест", SC_BAD_REQUEST},
                {"", "ВилкинИложкин_202277", "", SC_BAD_REQUEST},
                {"", "ВилкинИложкин_202277", "Василий", SC_BAD_REQUEST},
                {"polina_login12", "", "", SC_BAD_REQUEST},
                {"polina_login12", "", "Василий", SC_BAD_REQUEST},
                {"polina_login12", "ВилкинИложкин_202277", "", SC_CREATED},
                {"polina_login12", "ВилкинИложкин_202277", "Василий", SC_CREATED}
        };
    }
    private CourierAPI courierAPI;
    private Courier courier;
    private int expStCode;
    private int actStCode;
    public CourierCreateTest(String login, String password, String firstName, int code ) {
        this.courier = new Courier(login, password, firstName);
        this.courierAPI = new CourierAPI();
        this.expStCode = code;
    }
    @DisplayName("Try to create courier.")
    @Description("Попытка создания курьера. В программу передаются параметры строкового типо(логин, пароль и имя) ")
    @Test
    public void CreateCourierWithoutOneParameterTest()  {
        ValidatableResponse response = (ValidatableResponse) courierAPI.create(courier);
        actStCode = response.extract().statusCode();
        assertEquals(expStCode, actStCode); // проверка статуса Об ожидаемом создании нового аккаунта
    }

    @After
    public void tearDown() {
        CourierLogin courierLogin = new CourierLogin(courier.getLogin(), courier.getPassword());
        ValidatableResponse loginResponse = courierAPI.getID(courierLogin).then();
        if (actStCode==SC_CREATED) { //если курьер был успешно ранее создан
            Response response = courierAPI.deleteCourier(loginResponse.extract().path("id"));
        }
        else
            assertThat(loginResponse.extract().statusCode(), equalTo(SC_BAD_REQUEST)); //неудачные попытки авторизации, то проверяем, что Id курьера не возвращается по методу getID()
    }
}
