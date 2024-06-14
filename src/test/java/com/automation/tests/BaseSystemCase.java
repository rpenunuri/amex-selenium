package com.automation.tests;

import com.automation.pages.SeleniumPageFactory;
import com.automation.pages.HomePage;
import org.testng.annotations.*;

public abstract class BaseSystemCase {

    private ExecutionContext executionContext;
    private static int threadCounter = 0;
    private String baseUrl;
    private static final ThreadLocal<Integer> THREAD_INDEX = new InheritableThreadLocal<>();
    private static final String DEFAULT_BASE_URL = "https://www.americanexpress.com/us";

    @BeforeMethod
    public void beforeMethod() {
        setupExecutionContext();
        executionContext.initializeWebDriver();
        String envBaseUrl = System.getenv("BASE_URL");
        baseUrl = (envBaseUrl != null && !envBaseUrl.isEmpty()) ? envBaseUrl : DEFAULT_BASE_URL;
    }

    @AfterMethod
    public void afterMethod() {
        executionContext.shutDown();
    }

    protected ExecutionContext getExecutionContext() {
        return executionContext;
    }

    protected void setupExecutionContext() {
        if (THREAD_INDEX.get() == null) {
            synchronized (THREAD_INDEX) {
                THREAD_INDEX.set(threadCounter++);
            }
        }
        executionContext = new ExecutionContext();
    }

    public SeleniumPageFactory page() {
        return new SeleniumPageFactory(executionContext.getWebDriver());
    }

    protected HomePage goToAmexHomePage() {
        navigateTo(executionContext, "/");
        return page().amexHome();
    }

    protected void navigateTo(ExecutionContext executionContext, String path) {
        String navigateToUrl = baseUrl + path;
        executionContext.getWebDriver().navigate().to(navigateToUrl);
    }
}