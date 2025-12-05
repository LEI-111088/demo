package iscteiul.ista.demo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.time.Duration;
public class BasicElementTest {
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
    public void dropdown(){
        // select option 1 from dropdown
        driver.get("https://the-internet.herokuapp.com/dropdown");
        wait(2);
        driver.findElement(By.id("dropdown")).click();
        wait(2);
        driver.findElement(By.cssSelector("#dropdown > option:nth-child(2)")).click();
        wait(2);
    }

    @Test
    public void input(){
        // inputs 122701
        driver.get("https://the-internet.herokuapp.com/inputs");
        wait(2);
        driver.findElement(By.tagName("input")).sendKeys("122701");
        wait(2);
    }

    public void wait(int sec){
        int mili = sec * 1000;

        try{
            Thread.sleep(mili);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
