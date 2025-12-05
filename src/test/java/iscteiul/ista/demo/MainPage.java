package iscteiul.ista.demo;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class MainPage {
    public SelenideElement seeDeveloperToolsButton = $x("//*[@data-test-marker='Developer Tools']");
    public SelenideElement findYourToolsButton = $x("//*[@data-test='suggestion-link']");
    public SelenideElement toolsMenu = $x("//div[@data-test='main-menu-item' and @data-test-marker = 'Developer Tools']");
    public SelenideElement searchButton = $("[data-test='site-header-search-action']");
    public SelenideElement cookiesAcceptButton =
            $x("//button[contains(., 'Accept') or contains(., 'OK') or contains(., 'Allow')]");
    public SelenideElement cookiesDeclineButton =
            $x("//button[contains(., 'Decline') or contains(., 'Reject')]");

    public void clickFindYourTools() {
        findYourToolsButtons.findBy(visible).click();
    }

    public void acceptCookiesIfPresent() {
        if (cookiesAcceptButton.is(visible)) {
            cookiesAcceptButton.click();
            cookiesAcceptButton.should(disappear);
        } else if (cookiesDeclineButton.is(visible)) {
            cookiesDeclineButton.click();
            cookiesDeclineButton.should(disappear);
        }
    }
}
