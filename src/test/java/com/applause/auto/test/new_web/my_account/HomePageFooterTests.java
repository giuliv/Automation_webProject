package com.applause.auto.test.new_web.my_account;

import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.common.data.enums.FooterOptions;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.new_web.components.FooterComponent;
import com.applause.auto.new_web.views.HomePage;
import com.applause.auto.test.new_web.BaseTest;
import java.util.List;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class HomePageFooterTests extends BaseTest {

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.HOME_PAGE_FOOTER},
      description = "11107429")
  public void homepageFooterLogoTest() {
    logger.info("1. Navigate to Home page");
    HomePage homePage = navigateToHome();
    softAssert.assertNotNull(homePage, "Failed to navigate to the Home page.");

    logger.info("Verify company logo is displayed");
    FooterComponent footer = homePage.getFooterComponent();
    softAssert.assertTrue(footer.isCompanyLogoDisplayed(), "Company logo isn't displayed");
    softAssert.assertTrue(footer.isCompanyEstDisplayed(), "Company Est isn't displayed");
    softAssert.assertAll();
  }

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.HOME_PAGE_FOOTER},
      description = "11107430")
  public void homepageFooterHelpCenterTest() {
    logger.info("1. Navigate to Home page");
    HomePage homePage = navigateToHome();
    softAssert.assertNotNull(homePage, "Failed to navigate to the Home page.");

    logger.info("Verify the user is shown with al expected options under HELP CENTER");
    FooterComponent footer = homePage.getFooterComponent();
    List<FooterOptions> options = FooterOptions.getHelpCenterOptions();
    areFooterOptionsDisplayed(footer, softAssert, options);

    logger.info("2. Click on footer options and check page URL");
    clickFooterOptionsAndCheckUrl(homePage, softAssert, options);
    softAssert.assertAll();
  }

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.HOME_PAGE_FOOTER},
      description = "11107431")
  public void homepageFooterCompanyTest() {
    logger.info("1. Navigate to Home page");
    HomePage homePage = navigateToHome();
    softAssert.assertNotNull(homePage, "Failed to navigate to the Home page.");

    logger.info("Verify the user is shown with al expected options under COMPANY");
    FooterComponent footer = homePage.getFooterComponent();
    List<FooterOptions> options = FooterOptions.getCompanyOptions();
    areFooterOptionsDisplayed(footer, softAssert, options);

    logger.info("2. Click on footer options and check page URL");
    clickFooterOptionsAndCheckUrl(homePage, softAssert, options);
    softAssert.assertAll();
  }

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.HOME_PAGE_FOOTER},
      description = "11107432")
  public void homepageFooterGiftsCardsTest() {
    logger.info("1. Navigate to Home page");
    HomePage homePage = navigateToHome();
    softAssert.assertNotNull(homePage, "Failed to navigate to the Home page.");

    logger.info("Verify the user is shown with al expected options under GIFTS");
    FooterComponent footer = homePage.getFooterComponent();
    List<FooterOptions> options = FooterOptions.getGiftsOptions();
    areFooterOptionsDisplayed(footer, softAssert, options);

    logger.info("2. Click on footer options and check page URL");
    clickFooterOptionsAndCheckUrl(homePage, softAssert, options);
    softAssert.assertAll();
  }

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.HOME_PAGE_FOOTER},
      description = "11107432")
  public void homepageFooterOffersTest() {
    logger.info("1. Navigate to Home page");
    HomePage homePage = navigateToHome();
    softAssert.assertNotNull(homePage, "Failed to navigate to the Home page.");

    logger.info("Verify the user is shown with al expected options under Offers");
    FooterComponent footer = homePage.getFooterComponent();
    List<FooterOptions> options = FooterOptions.getOffersOptions();
    areFooterOptionsDisplayed(footer, softAssert, options);

    logger.info("2. Click on footer options and check page URL");
    clickFooterOptionsAndCheckUrl(homePage, softAssert, options);
    softAssert.assertAll();
  }

  private static void areFooterOptionsDisplayed(
      FooterComponent footer, SoftAssert softAssert, List<FooterOptions> options) {
    logger.info("Verify all expected footer options are displayed");
    for (FooterOptions option : options) {
      softAssert.assertTrue(footer.isOptionDisplayed(option), "Footer option [{}] isn't displayed");
    }
  }

  private static void clickFooterOptionsAndCheckUrl(
      HomePage homePage, SoftAssert softAssert, List<FooterOptions> options) {
    FooterComponent footer;
    for (FooterOptions option : options) {
      footer = homePage.getFooterComponent();
      footer.clickOption(option);

      logger.info("Verify expected page URL is displayed");
      softAssert.assertEquals(
          SdkHelper.getDriver().getCurrentUrl(),
          option.getUrl(),
          String.format("Wrong URL is displayed for option [%s]", option.getOption()));

      logger.info("Navigate back to Home page");
      BaseTest baseTest = new BaseTest();
      homePage = baseTest.navigateToHome();
    }
  }
}
