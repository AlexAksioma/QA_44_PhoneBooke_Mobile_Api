package mobile_tests;

import config.AppiumConfig;
import dto.UserDtoLombok;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import screens.AuthenticationScreen;
import screens.ContactsScreen;
import screens.DatePickerScreen;
import screens.SplashScreen;

import static helper.PropertiesReader.getProperty;

public class DatePickerTests extends AppiumConfig {

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
        contactsScreen.goToDatePicker();
    }

    @Test
    public void datePickerTest(){
        new DatePickerScreen(driver).typeDate("11 April 2023");
    }
}
