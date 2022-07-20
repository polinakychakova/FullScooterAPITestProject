import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.cucumber.messages.JSON;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import jdk.jfr.Description;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static net.serenitybdd.rest.RestRequests.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class CourierCreateTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }


    @DisplayName("Create new courier. Successful try.")
    @Description("Корректное создание курьера с ранее несуществовавшим логином. В конце теста новый аккаунт курьера удален")
    @Test
    public void CreateNewCourierTest() {
        Courier courier = new Courier("polina_liga_test", "123456", "FullSuccess");
        Response response = CreateNewCourier(courier);
//        System.out.println("Прошло создание нового курьера");
        CheckAns(response, 200);
        deleteNewCourier(getIdCourier(courier));
    }


    @Step("Создание нового курьера")
    public Response CreateNewCourier(Courier courier){
        Response response = (Response) given()
//                .header()
                .body(courier)
                .when()
                .post("/api/v1/courier");
        return response;
    }

    @Step("Проверка ответа на запрос")
    public void CheckAns(Response response, int code){
        response.then().assertThat().statusCode(code);
    }

    @Step("Получение id новосозданного курьера. Из процесса авторизации")
    public int getIdCourier(Courier courier){
        int id = 0;
        String json = "{ \"login\": "+ courier.getLogin() +", \"password\": "+courier.getPassword()+"}";
        Response response = (Response) given()
                .body(json)
                .when()
                .post("/api/v1/courier/login");
        response.then().assertThat().statusCode(200);
//        response.then().body("id").;
//        return response.body(id, equalTo(95679)).asString();
        return id;
    }

    @Step("Удаление ранее созданного аккаунта курьера")
    public void deleteNewCourier(int id){
        //Запрос на удаление курьера по Id
        // проверка ответа сервера
    }
}

