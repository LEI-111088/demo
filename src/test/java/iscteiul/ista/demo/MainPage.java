package iscteiul.ista.demo;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

// page_url = https://www.jetbrains.com/
public class MainPage {
    public SelenideElement searchButton = $("[data-test='site-header-search-action']");
    public SelenideElement devButton = $x("//*[ @data-test='main-menu-item-action' and text()='Developer Tools' ]");
    public SelenideElement findYourToolsButton = $x("//*[@data-test='suggestion-link']");
}
