package iscteiul.ista.demo;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class MainPageTest {

    MainPage mainPage = new MainPage();

    @BeforeAll
    public static void setUpAll() {
        Configuration.browserSize = "1280x800";
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void setUp() {
        open("https://www.jetbrains.com/");
    }

    @Test
    public void search() {
        wait(3);
        mainPage.searchButton.click();
        wait(1);
        $("[data-test-id='search-input']").sendKeys("Selenium");
        wait(1);
        $("button[data-test='full-search-button']").click();
        wait(1);
        $("input[data-test-id='search-input']").shouldHave(attribute("value", "Selenium"));
    }

    @Test
    public void toolsMenu() {
        mainPage.devButton.click();
        wait(1);
        $("div[data-test='main-submenu-suggestions']").shouldBe(visible);
    }

    @Test
    public void navigationToAllTools() {
        mainPage.devButton.click();
        wait(1);
        mainPage.findYourToolsButton.click();
        $("#products-page").shouldBe(visible);
        assertEquals("All Developer Tools and Products by JetBrains", Selenide.title());
    }

    public void wait(int sec){
        int mili = sec * 1000;
        try{
            Thread.sleep(mili);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
