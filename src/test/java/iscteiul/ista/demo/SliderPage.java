package iscteiul.ista.demo;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.text;

public class SliderPage {
    public SelenideElement slider = $("input[type='range']");
    public SelenideElement value = $("#range");

    public void setSliderValue(String target) {
        while (!value.getText().equals(target)) {
            slider.sendKeys(org.openqa.selenium.Keys.ARROW_RIGHT);
        }
    }

    public void verifyValue(String expected) {
        value.shouldHave(text(expected));
    }
}
