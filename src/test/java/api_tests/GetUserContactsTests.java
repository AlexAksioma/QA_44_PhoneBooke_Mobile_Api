package api_tests;

import config.ContactController;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class GetUserContactsTests extends ContactController {
    SoftAssert softAssert = new SoftAssert();

    @Test
    public void getUserContactsPositiveTest() {
        Response response = getUserContactsResponse(tokenDto.getToken());
        softAssert.assertEquals(response.getStatusCode(), 200);
        softAssert.assertAll();
    }
}
