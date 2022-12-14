import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class RestAssuredTest {
    private static String indexOne;
    private static String indexOneCountry;
    private static String indexFive;
    private static String indexFiveCountry;


    @Test
    public static void extractCircuits() {

        indexOne = given().
                when()
                .get("http://ergast.com/api/f1/2017/circuits.json")
                .then()
                .extract()
                .path("MRData.CircuitTable.Circuits[1].circuitId");

        indexOneCountry = given().
                when()
                .get("http://ergast.com/api/f1/2017/circuits.json")
                .then()
                .extract()
                .path("MRData.CircuitTable.Circuits[1].Location.country");

        indexFive = given().
                when()
                .get("http://ergast.com/api/f1/2017/circuits.json")
                .then()
                .extract()
                .path("MRData.CircuitTable.Circuits[5].circuitId");

        indexFiveCountry = given().
                when()
                .get("http://ergast.com/api/f1/2017/circuits.json")
                .then()
                .extract()
                .path("MRData.CircuitTable.Circuits[5].Location.country");

    }

    @Test(dataProvider = "circuits")
    public void parametrization(String id, String country) {
        given().
                pathParam("circuitId", id)
                .when()
                .get("http://ergast.com/api/f1/circuits/{circuitId}.json")
                .then()
                .assertThat()
                .body("MRData.CircuitTable.Circuits.Location[0].country", equalTo(country));
    }

    @DataProvider
    private Object[][] circuits() {
        return new Object[][]{{indexOne, indexOneCountry},{indexFive, indexFiveCountry}};
    }

}
