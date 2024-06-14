package com.automation.tests;

import com.automation.util.DriverType;
import org.openqa.selenium.WebDriver;

import java.time.Duration;
import java.util.Optional;

public class ExecutionContext {

    private String browser = "CHROME_LOCAL";
    private WebDriver webDriver;
    private static final long DEFAULT_TIMEOUT_IN_SECONDS = 5;

    public ExecutionContext() {
        browser = Optional.ofNullable(System.getenv("BROWSER")).orElse(browser);
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public void initializeWebDriver() {
        webDriver = createDriver();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(DEFAULT_TIMEOUT_IN_SECONDS));
        webDriver.manage().window().maximize();
    }

    private WebDriver createDriver() {
        return DriverType.valueOf(browser).createWebDriver();
    }

    public void shutDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }
}