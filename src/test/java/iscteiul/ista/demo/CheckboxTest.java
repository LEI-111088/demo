package iscteiul.ista.demo;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;

public class CheckboxTest {

    CheckboxPage checkboxPage = new CheckboxPage();

    @BeforeAll
    public static void setupAll() {
        Configuration.browserSize = "1280x800";
    }

    @BeforeEach
    public void openPage() {
        open("https://the-internet.herokuapp.com/checkboxes");
    }

    @Test
    public void selectAndVerifyCheckboxes() {
        checkboxPage.selectFirstCheckbox();
        checkboxPage.unselectSecondCheckbox();
        checkboxPage.verifySelections();
    }
}
