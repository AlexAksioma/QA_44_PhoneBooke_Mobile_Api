package config;

import dto.TokenDto;
import dto.UserDtoLombok;
import interfaces.BaseApi;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeSuite;

import static helper.PropertiesReader.getProperty;
import static io.restassured.RestAssured.given;

public class ContactController implements BaseApi {
    protected TokenDto tokenDto;

    @BeforeSuite
    public void login() {
        UserDtoLombok user = UserDtoLombok.builder()
                .username(getProperty("data.properties", "email"))
                .password(getProperty("data.properties", "password"))
                .build();
        Response response = given()
                .body(user)
                .contentType(ContentType.JSON)
                .when()
                .post(BASE_URL + LOGIN_PATH)
                .thenReturn();
        if (response.getStatusCode() == 200) {
            tokenDto = response.as(TokenDto.class);
        }else {
            System.out.println("Something went wrong, status code -->"+response.getStatusCode());
        }
    }

    protected Response getUserContactsResponse(String token){
        return given()
                .header("Authorization", token)
                .when()
                .get(BASE_URL+GET_ALL_CONTACTS_PATH)
                .thenReturn();

    }
}
