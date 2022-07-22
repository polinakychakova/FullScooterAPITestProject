import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import io.restassured.response.Response;
import model.CourierLogin;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import client.CourierAPI;
import model.Courier;
public class CourierCreateTest {

    private CourierAPI courierAPI;
    private Courier courier;
    @Before
    public void setUp() {
        courierAPI = new CourierAPI();
        courier = new Courier("polina_login12", "ВилкинИложкин_202277", "Василий");
    }



    @DisplayName("Create new courier. Successful try.")
    @Description("Корректное создание курьера с ранее несуществовавшим логином. В конце теста новый аккаунт курьера удален")
    @Test
    public void CreateNewCourierTest()  {
        ValidatableResponse response = (ValidatableResponse) courierAPI.create(courier);
        assertEquals(201, response.extract().statusCode()); // проверка статуса
        assertEquals(response.extract().path("ok"), true); // проверка выведенного сообщения
    }

    @DisplayName("Create 2 couriers with identical parameters.")
    @Description("Попытка создания курьера с уже существующим логином. Создается тестовый аккаунт курьера, потом создается 2 экземпляр. Получаем сообщение об ошибке создания 2 курьера и удаление 1.")
    @Test
    public void CreateTwoSameCourierTest()  {
        ValidatableResponse response = (ValidatableResponse) courierAPI.create(courier); // создание 1 курьера
//        assertEquals(201, response.extract().statusCode()); // проверка статуса Об успешном создании нового аккаунта
//        assertEquals(response.extract().path("ok"), true); // проверка выведенного сообщения
        ValidatableResponse FailResponse = (ValidatableResponse) courierAPI.create(new Courier("polina_login12", "ВилкинИложкин_202277", "Василий"));
        assertEquals(409, FailResponse.extract().statusCode()); // проверка статуса Об ошибке при создании дублирующего аккаунта
    }


    @After
    public void tearDown() {
        CourierLogin courierLogin = new CourierLogin(courier.getLogin(), courier.getPassword());
        Response response = courierAPI.deleteCourier(courierAPI.getID(courierLogin).then().extract().path("id"));
        assertThat(response.then().extract().statusCode(), equalTo(SC_OK));
    }



}

