package iscteiul.ista.demo;

import java.io.File;
import java.time.Duration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class FileUploadTest{
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.setImplicitWaitTimeout(Duration.ofSeconds(1));
        driver = new ChromeDriver(options);
    }

    @AfterEach
    public void quit() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void fileUploadTest() {
        driver.get("https://the-internet.herokuapp.com/upload");
        try{
            Thread.sleep(2000); //check se abriu
        }
        catch(Exception e){}

        File uploadFile = new File("src/test/resources/selenium-logo.png");
        try{
            Thread.sleep(2000); //check se existe
        }
        catch(Exception e){}

        WebElement fileInput = driver.findElement(By.cssSelector("input[type=file]"));
        fileInput.sendKeys(uploadFile.getAbsolutePath());
        try{
            Thread.sleep(2000); //check se escreveu path
        }
        catch(Exception e){}

        driver.findElement(By.id("file-submit")).click();

        WebElement fileName = driver.findElement(By.id("uploaded-files"));
        Assertions.assertEquals("selenium-logo.png", fileName.getText());
    }
}
