package api_tests;

import config.AuthenticationController;
import dto.ErrorMessageDto;
import dto.TokenDto;
import dto.UserDtoLombok;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.LocalDate;

import static helper.PropertiesReader.*;

public class LoginTests extends AuthenticationController {

    SoftAssert softAssert = new SoftAssert();

    @Test(groups = "api_tests")
    public void loginPositiveTest() {
        UserDtoLombok user = UserDtoLombok.builder()
                .username(getProperty("data.properties", "email"))
                .password(getProperty("data.properties", "password"))
                .build();
        //System.out.println(requestRegLogin(user, LOGIN_PATH).getStatusCode());
        Response response = requestRegLogin(user, LOGIN_PATH);
        //TokenDto tokenDto = response.as(TokenDto.class);
        softAssert.assertEquals(response.getStatusCode(), 200);
        softAssert.assertTrue(response.getBody().print().contains("token"));
        softAssert.assertAll();
    }

    @Test
    public void loginNegativeTest_wrongPassword() {
        UserDtoLombok user = UserDtoLombok.builder()
                .username(getProperty("data.properties", "email"))
                .password("passsword")
                .build();
        Response response = requestRegLogin(user, LOGIN_PATH);
        ErrorMessageDto message = ErrorMessageDto.builder().build();
        if (response.getStatusCode() == 401) {
            message = response.as(ErrorMessageDto.class);
        }
        softAssert.assertEquals(response.getStatusCode(), 401);
        softAssert.assertTrue(message.getMessage().toString().equals("Login or Password incorrect"));
        System.out.println("--> " + message.getTimestamp());
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate.toString());
        softAssert.assertEquals(message.getTimestamp().split("T")[0], localDate.toString());
        softAssert.assertAll();
    }
}
