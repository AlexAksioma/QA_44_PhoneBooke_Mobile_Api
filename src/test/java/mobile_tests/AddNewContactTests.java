package mobile_tests;

import config.AppiumConfig;
import data_provider.ContactDP;
import dto.ContactDtoLombok;
import dto.ContactsDto;
import dto.UserDtoLombok;
import helper.HelperApiMobile;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import screens.*;

import java.util.Arrays;

import static helper.PropertiesReader.getProperty;
import static helper.RandomUtils.*;

public class AddNewContactTests extends AppiumConfig {
    SoftAssert softAssert = new SoftAssert();
    UserDtoLombok user = UserDtoLombok.builder()
            .username(getProperty("data.properties", "email"))
            .password(getProperty("data.properties", "password"))
            .build();
    AddNewContactsScreen addNewContactsScreen;
    ContactsScreen contactsScreen;

    @BeforeMethod
    public void loginAndGoToAddNewContactScreen() {
        new SplashScreen(driver).goToAuthScreen(5);
        AuthenticationScreen authenticationScreen = new AuthenticationScreen(driver);
        authenticationScreen.typeAuthenticationForm(user);
        authenticationScreen.clickBtnLogin();
        contactsScreen = new ContactsScreen(driver);
        contactsScreen.clickBtnAddNewContact();
        addNewContactsScreen = new AddNewContactsScreen(driver);
    }

    @Test
    public void addNewContactPositiveTest() {
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name("!!!!" + generateString(5))
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
//        boolean flag = false;
//        for (ContactDtoLombok c : contactsDto.getContacts()) {
//            if (c.equals(contact)) {
//                flag = true;
//                break;
//            }
//        }
//        System.out.println("--> " + flag);
        int numberContact = Arrays.asList(contactsDto.getContacts()).indexOf(contact);
        System.out.println(numberContact);
        Assert.assertTrue(numberContact != -1);
    }

    @Test(dataProvider = "addNewContactDPFile", dataProviderClass = ContactDP.class)
    public void addNewContactNegativeTest_emptyField(ContactDtoLombok contact) {
        addNewContactsScreen.typeContactForm(contact);
        addNewContactsScreen.clickBtnCreateContact();
        Assert.assertTrue(new ErrorScreen(driver).validateErrorMessage("must not be blank", 5)
                || new ErrorScreen(driver).validateErrorMessage("well-formed email address", 5)
                || new ErrorScreen(driver).validateErrorMessage("phone number must contain", 5));
    }

    @Test
    public void addNewContactNegativeTest_duplicateContact() {
        HelperApiMobile helperApiMobile = new HelperApiMobile();
        helperApiMobile.login(user.getUsername(), user.getPassword());
        Response responseGet = helperApiMobile.getUserContactsResponse();
        if (responseGet.getStatusCode() == 200) {
            ContactsDto contactsDto = responseGet.as(ContactsDto.class);
            ContactDtoLombok contactApi = contactsDto.getContacts()[0];
            ContactDtoLombok contact = ContactDtoLombok.builder()
                    .name(contactApi.getName())
                    .lastName(contactApi.getLastName())
                    .email(contactApi.getEmail())
                    .phone(contactApi.getPhone())
                    .address(contactApi.getAddress())
                    .description(contactApi.getDescription())
                    .build();
            addNewContactsScreen.typeContactForm(contact);
            addNewContactsScreen.clickBtnCreateContact();
            Assert.assertTrue(new ErrorScreen(driver).validateErrorMessage("duplicate contact", 5));
        }

    }

    @Test
    public void addNewContactPositiveTestValidateUIListContact() {
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name("!!!!" + generateString(2))
                .lastName(generateString(10))
                .email(generateEmail(10))
                .phone(generatePhone(12))
                .address(generateString(8) + " app." + generatePhone(2))
                .description(generateString(15))
                .build();
        addNewContactsScreen.typeContactForm(contact);
        addNewContactsScreen.clickBtnCreateContact();
        softAssert.assertTrue(new ContactsScreen(driver).validatePopMessage());

        contactsScreen.validateUIListContact(contact);


        softAssert.assertAll();
    }
}
