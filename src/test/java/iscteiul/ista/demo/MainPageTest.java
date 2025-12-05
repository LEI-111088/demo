package iscteiul.ista.demo;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class MainPageTest {

    MainPage mainPage = new MainPage();
    //WebDriver driver = new ChromeDriver();

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
        wait(5000);
        mainPage.searchButton.click();
        wait(3000);
        $("[data-test-id='search-input']").sendKeys("Selenium");
        wait(3000);
        $("button[data-test='full-search-button']").click();
        wait(3000);
        $("input[data-test-id='search-input']").shouldHave(attribute("value", "Selenium"));
    }

    @Test
    public void toolsMenu() {
        mainPage.toolsMenu.click();
        wait(3000);
        $("div[data-test='main-submenu-suggestions']").shouldBe(visible);
    }

    @Test
    public void navigationToAllTools() {
        mainPage.seeDeveloperToolsButton.click();
        wait(3000);
        mainPage.findYourToolsButton.click();
        wait(3000);
        $("#products-page").shouldBe(visible);
        assertEquals("All Developer Tools and Products by JetBrains", Selenide.title());
    }

    public void wait(int numMillis) {
        try{
            Thread.sleep(numMillis);
        }
        catch (Exception e){
            System.out.println("Ignoring exception during sleep: " + e.getMessage());
        }
    }
}
