package iscteiul.ista.demo;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Condition.selected;

public class CheckboxPage {
    public SelenideElement checkbox1 = $$("input[type='checkbox']").get(0);
    public SelenideElement checkbox2 = $$("input[type='checkbox']").get(1);

    public void selectFirstCheckbox() {
        checkbox1.setSelected(true);
    }

    public void unselectSecondCheckbox() {
        checkbox2.setSelected(false);
    }

    public void verifySelections() {
        checkbox1.shouldBe(selected);
        checkbox2.shouldNotBe(selected);
    }
}
