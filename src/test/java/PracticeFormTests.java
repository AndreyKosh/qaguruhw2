import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byClassName;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;


public class PracticeFormTests {

    static Logger log = LoggerFactory.getLogger(PracticeFormTests.class);

    @BeforeAll
    static void setup() {
        log.info("@BeforeAll");
        Configuration.browser = "chrome";
        Configuration.startMaximized = true;
    }

    @Test
    void checkForm() {
        open("https://demoqa.com/automation-practice-form");

        $("#firstName").setValue("FirstName");
        $("#lastName").setValue("LastName");
        $("#userEmail").setValue("email@email.em");
        $(byText("Male")).click();
        $("#userNumber").setValue("3242534534");

        //выбор даты рождения
        $("[id=dateOfBirthInput]").click();     //календарь
        $(byClassName("react-datepicker__month-select")).click();   //список месяцев
        $(byClassName("react-datepicker__month-select")).selectOptionByValue("10");
        $(byClassName("react-datepicker__year-select")).click();    //список с годом
        $(byClassName("react-datepicker__year-select")).selectOptionByValue("1995");
        $(byClassName("react-datepicker__day--019")).click();   //выбор дня

        $("#subjectsInput").setValue("Chemistry").pressEnter();
        $("#hobbies-checkbox-1").click();
        $("#uploadPicture").uploadFile(new File("src/test/resources/cat.jpg"));
        $("#currentAddress").setValue("CurrentAddress");
        $("#state").scrollIntoView(true).click();
        $(byText("NCR")).click();
        $("#react-select-4-input").setValue("Delhi").pressEnter();

        $("#submit").click();

        //проверка значений в таблице
        $("tbody tr", 0).shouldHave(text("Student Name"), text("FirstName"), text("LastName"));
        $("tbody tr", 1).shouldHave(text("Student Email"), text("email@email.em"));
        $("tbody tr", 2).shouldHave(text("Gender"), text("Male"));
        $("tbody tr", 3).shouldHave(text("Mobile"), text("3242534534"));
        $("tbody tr", 4).shouldHave(text("Date of Birth"), text("19 November,1995"));
        $("tbody tr", 5).shouldHave(text("Subjects"), text("Chemistry"));
        $("tbody tr", 6).shouldHave(text("Hobbies"), text("Sports"));
        $("tbody tr", 7).shouldHave(text("Picture"), text("cat.jpg"));
        $("tbody tr", 8).shouldHave(text("Address"), text("CurrentAddress"));
        $x("//td[text()='State and City']/following::td[1]").shouldHave(text("NCR Delhi"));
    }

    @AfterEach
    void closeBrowser() {
        log.info("@AfterEach");
        closeWebDriver();
    }
}