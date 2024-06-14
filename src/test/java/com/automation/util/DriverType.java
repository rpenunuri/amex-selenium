package com.automation.util;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public enum DriverType {
    CHROME_REMOTE {
        @Override
        public WebDriver createWebDriver() {
                // Set desired capabilities for Chrome browser
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--headless=new");

                // Create a RemoteWebDriver instance
                WebDriver driver = null;
                try {
                    driver = new RemoteWebDriver(new URL(GRID_URL), options);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                return driver;
            }
        },
    FIREFOX_REMOTE {
            @Override
            public WebDriver createWebDriver() {
                    // Set desired capabilities for Firefox browser
                    FirefoxOptions options = new FirefoxOptions();
                    options.addArguments("--headless");
                    
                    // Create a RemoteWebDriver instance
                    WebDriver driver = null;
                    try {
                        driver = new RemoteWebDriver(new URL(GRID_URL), options);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    return driver;
                }
            },
    CHROME_LOCAL {
        @Override
        public WebDriver createWebDriver() {
            ChromeOptions options = new ChromeOptions();
            System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
            return new ChromeDriver(options);
        }
    };

    private static final String GRID_URL = "http://selenium-hub:4444/wd/hub";
    public abstract WebDriver createWebDriver();
}
