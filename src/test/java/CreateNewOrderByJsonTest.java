import client.OrderAPI;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.assertEquals;


@RunWith(Parameterized.class)
public class CreateNewOrderByJsonTest {
    private final String fileName;
    private OrderAPI orderAPI = new OrderAPI();
    private int track;


    public CreateNewOrderByJsonTest(String fileName) {
        this.fileName = fileName;
    }

    @Parameterized.Parameters //для цвета самоката
    public static String[] getColor() {
        return new String[] {
                "src\\test\\resources\\orderFormBlack.json",
                "src\\test\\resources\\orderFormGrey.json",
                "src\\test\\resources\\orderForm2Colors.json",
                "src\\test\\resources\\orderFormNOColor.json",
        };
    }


    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        orderAPI = new OrderAPI();
    }

    @DisplayName("Create new order.")
    @Description("Параметрический тест создание заказа. Параметр используется в графе выбора цвета самоката. " +
            "Тест проверяет, что в ответе возвращается track-номер нового заказа. Заказ может быть создан без передачи какого-либо параметра." +
            "В конце теста заказ завершаеся. так как серсвис отмены не работает")
    @Test
    public void NewOrderTest() {
        ValidatableResponse response= orderAPI.CreateNewOrderByFile(fileName);
//        ValidatableResponse response= orderAPI.CreateNewOrderW(color); // передача только строки с цветом
        assertEquals(201, response.extract().statusCode());
        track = response.extract().path("track"); //получение трек-номера
    }

    @After
    public void tearDown() {
        assertEquals(400, orderAPI.CancelOrder(track).extract().statusCode()); // сервер не работает
    }
}