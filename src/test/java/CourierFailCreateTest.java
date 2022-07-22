import client.CourierAPI;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import model.Courier;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.awt.*;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CourierFailCreateTest {

    @Parameterized.Parameters // создания аккаунта курьера
    public static Object[] getCourierInfo() {
        return new Object[][]{
                {"", "123456", "тест"},
                {"test_login", "", "test"}
        };
    }
    private CourierAPI courierAPI;
    private Courier courier;

    public CourierFailCreateTest(String login, String password, String firstName) {
        this.courier = new Courier(login, password, firstName);
        this.courierAPI = new CourierAPI();
    }

    @Before
    public void setUp() {
        courierAPI = new CourierAPI();
        courier = new Courier("polina_login12", "ВилкинИложкин_202277", "Василий");
    }


    @DisplayName("Try to create courier without login or password.")
    @Description("Попытка создания курьера, не передав логин или пароль (параметризованный тест).")
    @Test
    public void CreateCourierWithoutOneParameterTest()  {
        courier = new Courier("", "ВилкинИложкин_202277", "Василий");
        ValidatableResponse response = (ValidatableResponse) courierAPI.create(courier);
        assertEquals(400, response.extract().statusCode()); // проверка статуса Об успешном создании нового аккаунта

    }

}
