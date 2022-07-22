import client.CourierAPI;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import io.restassured.response.Validatable;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import model.Courier;
import model.CourierLogin;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static net.serenitybdd.rest.RestRequests.given;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CourierLoginTest {
    private CourierAPI courierAPI;
    private CourierLogin courierLogin;
    private int expCode;

//    private Courier courier;
    public CourierLoginTest(String login, String password) {
//        this.courier = new Courier(login, password, firstName);
        this.courierAPI = new CourierAPI();
        this.courierLogin = new CourierLogin(login, password);
        if ((login == "") | (password == ""))
            expCode = 400;
        else expCode = 404;
    }

    @Parameterized.Parameters // для передачи в
    public static Object[] getLoginInfo() {
        return new Object[][]{
                {"", "123456"},
                {"polina_login12", ""},
                {"polina_login12", "ВилкинИложкин_20227"}, // неверный пароль
                {"polina_log", "ВилкинИложкин_20227"}, // неверный логин
        };
    }


    @DisplayName("Try login. Fail try.")
    @Description("Неудачные попытки авторизации с некорректными данными ( или без части данных). Параметризованный тест. " +
            "Удачная попытка авторизации проверена на этапе тестов Создания нового аккаунта для курьера")
    @Test
    public void FailTryToLogin()  {
        Response response = courierAPI.getID(courierLogin);
        assertEquals(expCode, response.then().extract().statusCode());
    }

}
