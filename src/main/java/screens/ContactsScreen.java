package screens;

import dto.ContactDtoLombok;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class ContactsScreen extends BaseScreen {
    public ContactsScreen(AppiumDriver<AndroidElement> driver) {
        super(driver);
    }

    @FindBy(xpath = "//android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]/android.view.ViewGroup/android.widget.TextView")
    AndroidElement headerContactsScreen;
    @FindBy(id = "com.sheygam.contactapp:id/add_contact_btn")
    AndroidElement btnAddNewContact;
    @FindBy(xpath = "/hierarchy/android.widget.Toast")
    AndroidElement popUpMessage;

    @FindBy(xpath = "//*[@resource-id='com.sheygam.contactapp:id/rowContainer']")
    AndroidElement firstElementContactsList;
    @FindBy(id = "android:id/button1")
    AndroidElement popUpBtnYes;

    public boolean validateHeader() {
        return textInElementPresent(headerContactsScreen, "Contact list", 5);
    }

    public void clickBtnAddNewContact() {
        //btnAddNewContact.click();
        clickWait(btnAddNewContact, 5);
    }

    public boolean validatePopMessage() {
        return textInElementPresent(popUpMessage, "Contact was added!", 5);
    }

    public void deleteContact() {
        pause(3);
        int xLeftUpCorner = firstElementContactsList.getLocation().getX();
        int yLeftUpCorner = firstElementContactsList.getLocation().getY();
        int heightElement = firstElementContactsList.getSize().getHeight();
        int wightElement = firstElementContactsList.getSize().getWidth();
        System.out.println("y --> " + firstElementContactsList.getLocation().getY());
        System.out.println("x --> " + firstElementContactsList.getLocation().getX());
        System.out.println("h --> " + firstElementContactsList.getSize().getHeight());
        System.out.println("w --> " + firstElementContactsList.getSize().getWidth());
        TouchAction<?> touchAction = new TouchAction(driver);
//        touchAction.longPress(PointOption.point(wightElement/6,(yLeftUpCorner+heightElement/2)))
//                .moveTo(PointOption.point(wightElement/6*5, (yLeftUpCorner+heightElement/2)))
//                        .release().perform();
        touchAction.longPress(PointOption.point(wightElement / 6, (yLeftUpCorner + heightElement / 2)))
                .moveTo(PointOption.point(wightElement / 6 * 5, (yLeftUpCorner + heightElement / 2)))
                .release().perform();

    }

    public void clickBtnYes() {
        popUpBtnYes.click();
    }

    public boolean validateUIListContact(ContactDtoLombok contact) {
        String nameFamily = contact.getName() + " " + contact.getLastName();
        List<AndroidElement> listContactsOnScreen = new ArrayList<>();

        System.out.println("list size --> " + listContactsOnScreen.size());
        boolean flagEqualsNameFamily = false;
        boolean flagEndOfList = false;

        while (!flagEndOfList) {
            listContactsOnScreen = driver.findElements(By.xpath("//*[@resource-id='com.sheygam.contactapp:id/rowName']"));
            for (AndroidElement e : listContactsOnScreen) {
                System.out.println(e.getText());
                if (e.getText().equals(nameFamily)) {
                    flagEqualsNameFamily = true;
                    flagEndOfList = true;
                    break;
                } else {
                    scrollUp();
                    flagEndOfList = true;
                }
            }
        }

        return true;
    }

    private void scrollUp() {
        int height = driver.manage().window().getSize().getHeight();
        TouchAction<?> touchAction = new TouchAction<>(driver);
        touchAction.longPress(PointOption.point(10, height/8*7))
                .moveTo(PointOption.point(10, height/8))
                .release().perform();
    }
}
