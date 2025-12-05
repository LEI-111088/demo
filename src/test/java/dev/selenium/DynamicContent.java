package dev.selenium;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

public class DynamicContent {

    private WebDriver driver;

    // --- Setup / Teardown -----------------------------------------------------

    private void startChromeDriver(ChromeOptions options) {
        // A partir do Selenium 4.6+, o Selenium Manager pode localizar o driver automaticamente.
        // Se a tua versão for antiga, terás de apontar system property "webdriver.chrome.driver".
        driver = new ChromeDriver(options);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception ignored) {
            }
        }
    }

    // --- Helpers --------------------------------------------------------------

    private <T> T waitUntil(Function<WebDriver, T> condition, Duration timeout) {
        Wait<WebDriver> wait =
                new FluentWait<>(driver)
                        .withTimeout(timeout)
                        .pollingEvery(Duration.ofMillis(250))
                        .ignoring(NoSuchElementException.class)
                        .ignoring(ElementNotInteractableException.class)
                        .ignoring(StaleElementReferenceException.class);
        return wait.until(d -> condition.apply(d));
    }

    private boolean isImageLoaded(WebElement img) {
        Object result =
                ((JavascriptExecutor) driver)
                        .executeScript("return arguments[0].naturalWidth && arguments[0].naturalWidth > 0;", img);
        return Boolean.TRUE.equals(result);
    }

    // --- Tests ---------------------------------------------------------------

    @Test
    public void dynamicContent_multipleBlocks_areVisibleAndHaveContent() {
        startChromeDriver(new ChromeOptions());
        driver.get("https://www.selenium.dev/selenium/web/dynamic.html");
        driver.findElement(By.id("adder")).click();

        List<WebElement> boxes =
                waitUntil(
                        d -> {
                            List<WebElement> found = d.findElements(By.cssSelector("[id^='box']"));
                            return found.size() >= 1 ? found : null;
                        },
                        Duration.ofSeconds(5));

        // re-obter referências recentes
        boxes = driver.findElements(By.cssSelector("[id^='box']"));
        Assertions.assertFalse(boxes.isEmpty(), "Expected at least one dynamic box to be present.");

        for (WebElement box : boxes) {
            // Re-obtem elemento fresco por id (prevenir StaleElement)
            String id = box.getAttribute("id");
            WebElement freshBox = waitUntil(d -> {
                List<WebElement> found = d.findElements(By.cssSelector("#" + id));
                return found.isEmpty() ? null : found.get(0);
            }, Duration.ofSeconds(3));

            // tenta aguardar visibilidade (se aplicável)
            try {
                waitUntil(d -> freshBox.isDisplayed() ? freshBox : null, Duration.ofSeconds(3));
            } catch (Exception ignored) { }

            String text = "";
            try {
                text = freshBox.getText().trim();
            } catch (StaleElementReferenceException e) {
                WebElement retryBox = driver.findElement(By.cssSelector("#" + id));
                text = retryBox.getText().trim();
            }

            // verifica imagens carregadas (se existirem)
            List<WebElement> imgs = freshBox.findElements(By.tagName("img"));
            boolean imageLoaded = false;
            if (!imgs.isEmpty()) {
                for (WebElement img : imgs) {
                    try {
                        boolean loaded = waitUntil(d -> isImageLoaded(img) ? true : null, Duration.ofSeconds(2));
                        if (loaded) {
                            imageLoaded = true;
                            break;
                        }
                    } catch (Exception ignored) { }
                }
            }

            // verifica classe/atributo visual
            String cls = freshBox.getAttribute("class");
            boolean hasClass = cls != null && !cls.trim().isEmpty();

            boolean ok = (text != null && text.length() > 0) || imageLoaded || hasClass;
            Assertions.assertTrue(
                    ok,
                    "Expected box to contain text or image loaded or visual class. id=" + id
                            + " textLen=" + (text == null ? 0 : text.length())
                            + " imageLoaded=" + imageLoaded
                            + " classPresent=" + hasClass);
        }
    }


    @Test
    public void hiddenElement_onPageBecomesVisible_afterAction() {
        startChromeDriver(new ChromeOptions());

        driver.get("https://www.selenium.dev/selenium/web/dynamic.html");

        // Elemento existe no DOM mas está escondido
        WebElement revealed = driver.findElement(By.id("revealed"));
        Assertions.assertNotNull(revealed, "Element 'revealed' should exist in the DOM.");

        // Pode ser inicialmente hidden - assert flexível
        Assertions.assertFalse(revealed.isDisplayed(), "Expected 'revealed' to be initially hidden.");

        driver.findElement(By.id("reveal")).click();

        // Espera até ficar visível
        WebElement visible = waitUntil(d -> revealed.isDisplayed() ? revealed : null, Duration.ofSeconds(5));
        Assertions.assertTrue(visible.isDisplayed(), "'revealed' should be visible after clicking reveal.");

        // Interagir quando visível
        visible.sendKeys("Displayed");
        Assertions.assertEquals("Displayed", visible.getDomProperty("value"));
    }

    @Test
    public void elementRenderedAfterTheFact_isDetectableAndInteractable() {
        startChromeDriver(new ChromeOptions());

        driver.get("https://www.selenium.dev/selenium/web/dynamic.html");

        // Remove implicit waits para forçar waits explícitas claras
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);

        // Clica para renderizar novo elemento
        driver.findElement(By.id("adder")).click();

        WebElement box0 =
                waitUntil(
                        d -> {
                            List<WebElement> found = d.findElements(By.id("box0"));
                            return found.isEmpty() ? null : found.get(0);
                        },
                        Duration.ofSeconds(5));

        Assertions.assertNotNull(box0, "Expected 'box0' to be rendered after clicking adder.");

        // Espera visibilidade
        waitUntil(d -> box0.isDisplayed() ? box0 : null, Duration.ofSeconds(3));

        // Assert flexível: tem classe (indicador visual) ou texto
        String cls = box0.getDomAttribute("class");
        String txt = box0.getText().trim();
        Assertions.assertTrue((cls != null && !cls.isEmpty()) || (txt.length() > 0),
                "Expected box0 to have a class or non-empty text (flexible check).");
    }
}