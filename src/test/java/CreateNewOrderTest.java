//import client.OrderAPI;
//import io.qameta.allure.junit4.DisplayName;
//import io.restassured.RestAssured;
//import io.restassured.response.ValidatableResponse;
//import jdk.jfr.Description;
//import model.Order;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.Parameterized;
//
//import java.util.Arrays;
//
//import static net.serenitybdd.rest.RestRequests.given;
//import static org.junit.Assert.assertEquals;
//
//@RunWith(Parameterized.class)
//public class CreateNewOrderTest {
////    private final String fileName;
//    private final String[] color;
//    private OrderAPI orderAPI = new OrderAPI();
//    private int track;
//
//    public CreateNewOrderTest(String[] color) {
//        this.color = color;
//        System.out.println(Arrays.toString(color));
//    }
//
////    public CreateNewOrderTest(String fileName) {
////        this.fileName = fileName;
////    }
//
////    @Parameterized.Parameters //для цвета самоката
////    public static String[] getColor() {
////        return new String[] {
////                "src\\test\\resources\\orderFormBlack.json",
////                "src\\test\\resources\\orderFormGrey.json",
////                "src\\test\\resources\\orderForm2Colors.json",
////                "src\\test\\resources\\orderFormNOColor.json",
////        };
////    }
//
//    @Parameterized.Parameters //для цвета самоката
//    public static String[][] getColor() {
//        return new String[][]{
//                {"GREY", ""}, {"BLACK", ""}, {"GREY", "BLACK"}, {"", ""}
//        };
//
//    }
//
//    @Before
//    public void setUp() {
//        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
//        orderAPI = new OrderAPI();
//    }
//
//    @DisplayName("Create new order.")
//    @Description("Параметрический тест создание заказа. Параметр используется в графе выбора цвета самоката. " +
//            "Тест проверяет, что в ответе возвращается track-номер нового заказа. Заказ может быть создан без передачи какого-либо параметра." +
//            "В конце теста заказ завершаеся. так как серсвис отмены не работает")
//    @Test
//    public void NewOrderTest() {
////        String str = Arrays.toString(color);
////        System.out.println(str);
//        Order order = new Order("firstTestName",
//                "lastTestName",
//                "address Test",
//                3,
//                "+7 800 355 35 35",
//                2,
//                "2023-01-01",
//                " ",
//                color);
//        ValidatableResponse response= orderAPI.CreateNewOrder(order);
////        ValidatableResponse response= orderAPI.CreateNewOrderW(color); // передача только строки с цветом
//        assertEquals(201, response.extract().statusCode());
//
//        track = response.extract().path("track"); //получение трек-номера
//
//    }
//
//    @After
//    public void tearDown() {
////        int id = orderAPI.GetOrderID(track);
////        ValidatableResponse response = orderAPI.FinishOrder(id);
////        assertEquals(409, response.extract().statusCode()); // всегда возращает "нельзя завершить заказ"
//        assertEquals(400, orderAPI.CancelOrder(track).extract().statusCode()); // сервер не работает
//    }
//}
//
