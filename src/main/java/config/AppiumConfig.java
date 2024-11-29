package config;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.net.MalformedURLException;
import java.net.URL;

public class AppiumConfig {
    public static AppiumDriver<AndroidElement> driver;
    public int height = 0, width = 0;
    static String type_dc;

    public AppiumConfig() {
        type_dc = System.getProperty("type_dc", "Nex5");
    }

    // "platformName": "Android",
//         "deviceName": "Nex5",
//         "platformVersion": "8.0",
//         "appPackage": "com.sheygam.contactapp",
//         "appActivity": ".SplashActivity"
    @BeforeMethod(alwaysRun = true)
    public void setup() {
        DesiredCapabilities desiredCapabilitiesNex5 = new DesiredCapabilities();
        desiredCapabilitiesNex5.setCapability("platformName", "Android");
        desiredCapabilitiesNex5.setCapability("deviceName", "Nex5");
        desiredCapabilitiesNex5.setCapability("platformVersion", "8.0");
        desiredCapabilitiesNex5.setCapability("appPackage", "com.sheygam.contactapp");
        desiredCapabilitiesNex5.setCapability("appActivity", ".SplashActivity");
        desiredCapabilitiesNex5.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Appium");
        String urlNex5 = "http://localhost:4723/wd/hub";

        DesiredCapabilities desiredCapabilitiesA35 = new DesiredCapabilities();
        desiredCapabilitiesA35.setCapability("platformName", "Android");
        desiredCapabilitiesA35.setCapability("deviceName", "A35");
        desiredCapabilitiesA35.setCapability("platformVersion", "13");
        desiredCapabilitiesA35.setCapability("appPackage", "com.sheygam.contactapp");
        desiredCapabilitiesA35.setCapability("appActivity", ".SplashActivity");
        desiredCapabilitiesA35.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Appium");
        String urlA35 = "http://localhost:4723/wd/hub";

        try {
            if (type_dc.equals("Nex5")) {
                System.out.println("========start Nex5===============");
                driver = new AppiumDriver<>(new URL(urlNex5), desiredCapabilitiesNex5);
            } else if (type_dc.equals("A35")) {
                System.out.println("========start A35===============");
                driver = new AppiumDriver<>(new URL(urlA35), desiredCapabilitiesA35);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        height = driver.manage().window().getSize().getHeight();
        width = driver.manage().window().getSize().getWidth();
        System.out.println(width + "X" + height);
    }


    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }
}
