package com.automation.tests.homepage;
import com.automation.pages.HomePage;
import com.automation.tests.BaseSystemCase;
import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class PersonalTabMenuShowsAllAvailableProductsTestIT extends BaseSystemCase {

    @Test
    public void testCase() {
        HomePage homePage = goToAmexHomePage().clickPersonalTabMenuButton();

        assertThat(homePage.getPersonalTabPanelProductItems())
                .hasSize(4)
                .extracting("text")
                .contains("Credit Cards", "High Yield Savings", "Personal Loans", "Checking Account");
    }
}
