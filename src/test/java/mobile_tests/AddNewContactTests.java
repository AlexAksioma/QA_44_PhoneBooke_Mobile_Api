package mobile_tests;

import config.AppiumConfig;
import dto.ContactDtoLombok;
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
import static helper.RandomUtils.*;

public class AddNewContactTests extends AppiumConfig {
    UserDtoLombok user = UserDtoLombok.builder()
            .username(getProperty("data.properties", "email"))
            .password(getProperty("data.properties", "password"))
            .build();
    AddNewContactsScreen addNewContactsScreen;

    @BeforeMethod
    public void loginAndGoToAddNewContactScreen() {
        new SplashScreen(driver).goToAuthScreen(5);
        AuthenticationScreen authenticationScreen = new AuthenticationScreen(driver);
        authenticationScreen.typeAuthenticationForm(user);
        authenticationScreen.clickBtnLogin();
        new ContactsScreen(driver).clickBtnAddNewContact();
        addNewContactsScreen = new AddNewContactsScreen(driver);
    }

    @Test
    public void addNewContactPositiveTest() {
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .email(generateEmail(10))
                .phone(generatePhone(12))
                .address(generateString(8) + " app." + generatePhone(2))
                .description(generateString(15))
                .build();
        addNewContactsScreen.typeContactForm(contact);
        addNewContactsScreen.clickBtnCreateContact();
        Assert.assertTrue(new ContactsScreen(driver).validatePopMessage());
    }

    @Test
    public void addNewContactPositiveTestValidateContactApi() {
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .email(generateEmail(10))
                .phone(generatePhone(12))
                .address(generateString(8) + " app." + generatePhone(2))
                .description(generateString(15))
                .build();
        addNewContactsScreen.typeContactForm(contact);
        addNewContactsScreen.clickBtnCreateContact();
        //Assert.assertTrue(new ContactsScreen(driver).validatePopMessage());
        HelperApiMobile helperApiMobile = new HelperApiMobile();
        helperApiMobile.login(user.getUsername(), user.getPassword());
        Response responseGet = helperApiMobile.getUserContactsResponse();
        ContactsDto contactsDto = responseGet.as(ContactsDto.class);
        boolean flag = false;
        for (ContactDtoLombok c : contactsDto.getContacts()) {
            if (c.equals(contact)) {
                flag = true;
                break;
            }
        }
        System.out.println("--> " + flag);
        Assert.assertTrue(flag);
    }


}