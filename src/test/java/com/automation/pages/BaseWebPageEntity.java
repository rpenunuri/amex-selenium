package com.automation.pages;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;

public abstract class BaseWebPageEntity {
    private final WebDriver webDriver;
    private final SeleniumPageFactory seleniumPageFactory;
    private static final long DEFAULT_TIMEOUT_IN_SECONDS = 10;
    private static final long DEFAULT_SLEEP_TIME_IN_SECONDS = 1;
    private Actions actions;

    protected abstract ExpectedCondition<?> readyCondition();

    protected BaseWebPageEntity(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.seleniumPageFactory = new SeleniumPageFactory(webDriver);
        waitUntil(readyCondition());
    }

    protected void click(WebElement element) {
        waitUntil(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }

    protected void mouseOver(WebElement element) {
        scrollToElement(element);
        waitUntil(ExpectedConditions.visibilityOf(element));
        actions = new Actions(webDriver);
        actions.moveToElement(element).perform();
    }

    protected void selectByVisibleText(WebElement element, String text) {
        waitUntil(ExpectedConditions.elementToBeClickable(element));
        new Select(element).selectByVisibleText(text);
    }

    protected void sendKeys(WebElement element, String text) {
        waitUntil(ExpectedConditions.elementToBeClickable(element));
        element.sendKeys(text);
    }

    protected String getText(WebElement element) {
        waitUntil(ExpectedConditions.visibilityOf(element));
        return element.getText();
    }

    protected int getNumberOfElements(List<WebElement> webElements) {
        waitUntil(ExpectedConditions.visibilityOfAllElements(webElements));
        return webElements.size();
    }

    protected void scrollToElement(WebElement element) {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    protected void switchToNewTabWithUrl(String expectedUrl) {
        waitUntil(ExpectedConditions.numberOfWindowsToBe(2));
        String currentWindowHandle = webDriver.getWindowHandle();

        for (String windowHandle : webDriver.getWindowHandles()) {
            if (!windowHandle.equals(currentWindowHandle)) {
                webDriver.switchTo().window(windowHandle);
                waitUntil(ExpectedConditions.urlContains(expectedUrl));
                return;
            }
        }
    }

    protected SeleniumPageFactory page() {
        return seleniumPageFactory;
    }

    protected boolean isElementClickable(WebElement element) {
        try {
            waitUntil(ExpectedConditions.elementToBeClickable(element));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected void waitUntil(ExpectedCondition<?> expectedCondition) {
        new FluentWait<>(webDriver)
                .withTimeout(Duration.ofSeconds(DEFAULT_TIMEOUT_IN_SECONDS))
                .pollingEvery(Duration.ofSeconds(DEFAULT_SLEEP_TIME_IN_SECONDS))
                .ignoring(NoSuchElementException.class)
                .until(expectedCondition);
    }
}
