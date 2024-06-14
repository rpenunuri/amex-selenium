package com.automation.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BaseWebPageEntity {

    @FindBy(css = "#personalcxo")
    private WebElement personalTabMenuButton;

    @FindBys({
        @FindBy(css = "#personalcxo-tab"),
        @FindBy(css = ".product-nav-items")
    })
    private List<WebElement> personalTabPanelProductItems;

    public HomePage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected ExpectedCondition<Boolean> readyCondition() {
        return ExpectedConditions.titleContains("American Express Credit Cards, Rewards & Banking");
    }

    public HomePage clickPersonalTabMenuButton() {
        click(personalTabMenuButton);
        return this;
    }

    public List<WebElement> getPersonalTabPanelProductItems() {
        return personalTabPanelProductItems;
    }
}
