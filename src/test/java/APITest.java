import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;         // given - requires static method
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class APITest {

        @Test
        public void getBookingsReturn200(){
                Response response = given().get("https://restful-booker.herokuapp.com/booking/");

                assertThat(response.getStatusCode(), equalTo(200));
        }

}
