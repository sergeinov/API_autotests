import api.BookingApi;
import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;         // given - requires static method
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class APITest {

        @Test
        public void getBookingReturn200(){
                Response response = BookingApi.getBooking();

                assertThat(response.getStatusCode(), equalTo(200));
        }

        @Test
        public void getBookingIDReturn200(){
                Response response = BookingApi.getBookingId("1", "application/json");   //  application/json - right type of header

                assertThat(response.getStatusCode(), equalTo(200));
        }
        @Test
        public void getBookingIDReturn418(){
                Response response = BookingApi.getBookingId("1", "text/plain");

                assertThat(response.getStatusCode(), equalTo(418));     // 418 -  I’m a teapot (joke)
        }

}
