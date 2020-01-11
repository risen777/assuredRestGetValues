import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.path.json.JsonPath.from;

/**
 * Created by Sergey
 */
public class GetValuesOfResponseTest {

    @Test
    public void getYear() {
        System.out.println("First test");
        Response response =
                given()
                        .log().all()
                        .header("Content-Type", "application/json")
                        .when().get("https://reqres.in/api/unknown");
        String jsonResponse = response.getBody().prettyPrint();

        String expr = "data.min {it.year}.year";
        String jsonValue = JsonPath.from(jsonResponse).getString(expr);
        System.out.println(jsonValue);
    }

    @Test
    public void listYears() {
        System.out.println("Second test");
        Response response2 =
                given()
                        .log().all()
                        .header("Content-Type", "application/json")
                        .when().get("https://reqres.in/api/unknown");
        String jsonResponse2 = response2.getBody().asString();
        List<String> jsonValue2 = from(jsonResponse2).getList("data.findAll { it.year >2000 }.year");
        System.out.println(jsonValue2);


    }

    @Test
    public void listNamesAndYears() {
        System.out.println("Third test");
        Response response3 =
                given()
                        .log().all()
                        .header("Content-Type", "application/json")
                        .when().get("https://reqres.in/api/unknown");

        String JsonResponse3 = response3.getBody().prettyPrint();
        List<HashMap<String, Object>> valuesList = JsonPath.from(JsonResponse3).getList("data");
        System.out.println(valuesList);
        for (HashMap<String, Object> singleBucket : valuesList) {
            String firstValue = (String) singleBucket.get("name");
            Integer secondValue = (Integer) singleBucket.get("year");
            System.out.println(firstValue);
            System.out.println(secondValue);
            System.out.println(singleBucket);

        }

    }
}
