package screens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class DatePickerScreen extends BaseScreen {
    public static void main(String[] args) {
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate.getYear());
        SimpleDateFormat formater = new SimpleDateFormat("dd MMMM yyyy");
        System.out.println(formater.format("01 November 2024"));
    }

    public DatePickerScreen(AppiumDriver<AndroidElement> driver) {
        super(driver);
    }

    @FindBy(id = "com.sheygam.contactapp:id/dateBtn")
    AndroidElement btnChangeDate;
    @FindBy(id = "android:id/prev")
    AndroidElement btnPrevMonth;
    @FindBy(id = "android:id/next")
    AndroidElement btnNextMonth;
    @FindBy(id = "android:id/date_picker_header_year")
    AndroidElement btnPickerYear;
    @FindBy(id = "android:id/button1")
    AndroidElement btnOk;


    public void typeDate(String date) {  //29 November 2024   01 November 2024
        btnChangeDate.click();

        String[] arrayDate = date.split(" ");
        LocalDate localDate = LocalDate.now();
        //year ======================================
        if (localDate.getYear() != Integer.parseInt(arrayDate[2])) {
            btnPickerYear.click();
            driver.findElement(By.xpath("//*[@text='" + arrayDate[2] + "']")).click();
        }
        btnPrevMonth.click(); // bug local date
        //month ======================================
        if (localDate.getMonth().getValue() != numberMonth(arrayDate[1])) {
            if (numberMonth(arrayDate[1]) > localDate.getMonth().getValue()) {
                int num = numberMonth(arrayDate[1]) - localDate.getMonth().getValue();
                System.out.println("num --> " + num);
                for (int i = 0; i < num; i++) {
                    btnNextMonth.click();
                }
            } else {
                int num = localDate.getMonth().getValue() - numberMonth(arrayDate[1]);
                System.out.println("num --> " + num);
                for (int i = 0; i < num; i++) {
                    btnPrevMonth.click();
                }
            }
        }
        //month ======================================
        driver.findElement(By.xpath("//android.view.View[@content-desc='" + date + "']")).click();


//        AndroidElement pickerDate = driver.findElement(By.xpath("//*[@content-desc='"+date+"']"));
//        pickerDate.click();
    }

    private int numberMonth(String month) {
        int m = 0;
        switch (month) {
            case "November":
                m = 11;
                break;
            case "December":
                m = 12;
                break;
            case "January":
                m = 1;
                break;
            case "February":
                m = 2;
                break;
            case "March":
                m = 3;
                break;
            case "April":
                m = 4;
                break;
            case "May":
                m = 5;
                break;
            case "June":
                m = 6;
                break;
            case "July":
                m = 7;
                break;
            case "August":
                m = 8;
                break;
            case "September":
                m = 9;
                break;
            case "October":
                m = 10;
                break;
        }
        return m;
    }

}
