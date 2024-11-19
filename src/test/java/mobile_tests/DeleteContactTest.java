package mobile_tests;

import config.AppiumConfig;
import dto.ContactsDto;
import dto.UserDtoLombok;
import helper.HelperApiMobile;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import screens.AddNewContactsScreen;
import screens.AuthenticationScreen;
import screens.ContactsScreen;
import screens.SplashScreen;

import static helper.PropertiesReader.getProperty;

public class DeleteContactTest extends AppiumConfig {
    UserDtoLombok user = UserDtoLombok.builder()
            .username(getProperty("data.properties", "email"))
            .password(getProperty("data.properties", "password"))
            .build();
    ContactsScreen contactsScreen;

    @BeforeMethod
    public void loginAndGoToAddNewContactScreen() {
        new SplashScreen(driver).goToAuthScreen(5);
        AuthenticationScreen authenticationScreen = new AuthenticationScreen(driver);
        authenticationScreen.typeAuthenticationForm(user);
        authenticationScreen.clickBtnLogin();
        contactsScreen = new ContactsScreen(driver);
    }

    @Test
    public void deleteContactPositiveTest() {
        HelperApiMobile helperApiMobile = new HelperApiMobile();
        helperApiMobile.login(user.getUsername(), user.getPassword());
        Response responseGet = helperApiMobile.getUserContactsResponse();
        ContactsDto contactsDto = responseGet.as(ContactsDto.class);
        int quantityContactsBeforeDelete = contactsDto.getContacts().length;
        contactsScreen.deleteContact();
        contactsScreen.clickBtnYes();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Response responseGetAfter = helperApiMobile.getUserContactsResponse();
        ContactsDto contactsDtoAfter = responseGetAfter.as(ContactsDto.class);
        int quantityContactsAfterDelete = contactsDtoAfter.getContacts().length;
        System.out.println("quantity --> "+quantityContactsBeforeDelete +"X"+quantityContactsAfterDelete);
        Assert.assertEquals(quantityContactsBeforeDelete-1, quantityContactsAfterDelete);
    }
}
