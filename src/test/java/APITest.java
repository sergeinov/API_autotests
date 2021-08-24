import api.AuthApi;
import api.BookingApi;
import io.restassured.response.Response;
import org.junit.Test;
import payloads.AuthPayload;
import payloads.AuthResponsePayload;
import payloads.BookingDatesPayload;
import payloads.BookingPayLoad;

import java.util.Date;

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

        @Test
        // send request for a new booking
        public void postBookingReturn200(){
                BookingDatesPayload bookingDatesPayload = new BookingDatesPayload(new Date(), new Date());
                BookingPayLoad bookingPayload = new BookingPayLoad("Itan", "Test", 3500, false, bookingDatesPayload, "Need smoking room!");
              Response response = BookingApi.postBooking(bookingPayload);

              assertThat(response.getStatusCode(), equalTo(200));
        }

        @Test
        // Creates a new auth token to use for access to the PUT and DELETE /booking
        // we need to send
        //        POST \
        //        https://restful-booker.herokuapp.com/auth \
        //                -H 'Content-Type: application/json' \
        //                -d '{
        //                "username" : "admin",
        //                "password" : "password123"
        //              }'
        //     and get a token for delete

        public void deleteBookingReturn201(){
                // Login in and get token
                AuthPayload authPayload = new AuthPayload("admin", "password123");
                Response authResponse = AuthApi.postAuth(authPayload);
                System.out.println(authResponse.getBody().prettyPrint());

                String tokenObject = authResponse.as(AuthResponsePayload.class).getToken(); // to return json into java object
                System.out.println(tokenObject);

                // Use token to delete booking
                Response bookingResponse = BookingApi.deleteBooking("1", tokenObject);

                assertThat(bookingResponse.getStatusCode(), equalTo(201));
        }
}
