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
import screens.*;

import java.util.Arrays;
import java.util.List;

import static helper.PropertiesReader.getProperty;
import static helper.RandomUtils.*;
import static helper.RandomUtils.generateString;

public class EditContactTests extends AppiumConfig {
    UserDtoLombok user = UserDtoLombok.builder()
            .username(getProperty("data.properties", "email"))
            .password(getProperty("data.properties", "password"))
            .build();
    ContactsScreen contactsScreen;

    @BeforeMethod(alwaysRun = true)
    public void loginAndGoToAddNewContactScreen() {
        new SplashScreen(driver).goToAuthScreen(5);
        AuthenticationScreen authenticationScreen = new AuthenticationScreen(driver);
        authenticationScreen.typeAuthenticationForm(user);
        authenticationScreen.clickBtnLogin();
        contactsScreen = new ContactsScreen(driver);
    }

    @Test
    public void editContactPositiveTest(){
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name("edit-" + generateString(2))
                .lastName("edit-"+generateString(10))
                .email("edit-"+generateEmail(10))
                .phone(generatePhone(12))
                .address("edit-"+generateString(8) + " app." + generatePhone(2))
                .description(generateString(15))
                .build();
        contactsScreen.goToEditScreen();
        new EditContactScreen(driver).typeEditContactForm(contact);
        Assert.assertTrue(new ContactsScreen(driver).validatePopMessage("Contact was updated!"));
    }

    @Test(groups = "smoke")
    public void editContactPositiveTestValidateApi(){
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name("edit-" + generateString(2))
                .lastName("edit-"+generateString(10))
                .email("edit-"+generateEmail(10))
                .phone(generatePhone(12))
                .address("edit-"+generateString(8) + " app." + generatePhone(2))
                .description(generateString(15))
                .build();
        contactsScreen.goToEditScreen();
        new EditContactScreen(driver).typeEditContactForm(contact);
        HelperApiMobile helperApiMobile = new HelperApiMobile();
        helperApiMobile.login(user.getUsername(), user.getPassword());
        Response responseGet = helperApiMobile.getUserContactsResponse();
        ContactsDto contactsDto = responseGet.as(ContactsDto.class);
        List<ContactDtoLombok> contactList = Arrays.asList(contactsDto.getContacts());
        Assert.assertTrue(contactList.contains(contact));
    }

    @Test
    public void editContactNegativeTest_emptyName(){
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name("")
                .lastName("edit-"+generateString(10))
                .email("edit-"+generateEmail(10))
                .phone(generatePhone(12))
                .address("edit-"+generateString(8) + " app." + generatePhone(2))
                .description(generateString(15))
                .build();
        contactsScreen.goToEditScreen();
        new EditContactScreen(driver).typeEditContactForm(contact);
        Assert.assertTrue(new ErrorScreen(driver).validateErrorMessage("must not be blank",5));
    }
}
