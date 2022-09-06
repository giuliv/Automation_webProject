package com.applause.auto.test.new_web.home_page;

import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.common.data.enums.FooterOptions;
import com.applause.auto.new_web.components.FooterComponent;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.new_web.views.HomePage;
import com.applause.auto.test.new_web.BaseTest;
import java.util.List;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class FooterTests extends BaseTest {

  @Test(
      groups = {TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.HOME_PAGE},
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
      groups = {TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.HOME_PAGE},
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
      groups = {TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.HOME_PAGE},
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
      groups = {TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.HOME_PAGE},
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
      groups = {TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.HOME_PAGE},
      description = "11107433")
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

  @Test(
      groups = {TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.HOME_PAGE},
      description = "11107434")
  public void homepageFooterPartnerWithPeetsTest() {
    logger.info("1. Navigate to Home page");
    HomePage homePage = navigateToHome();
    softAssert.assertNotNull(homePage, "Failed to navigate to the Home page.");

    logger.info("Verify the user is shown with al expected options under Partner with peet's");
    FooterComponent footer = homePage.getFooterComponent();
    List<FooterOptions> options = FooterOptions.getPartnerWithPeetsOptions();
    areFooterOptionsDisplayed(footer, softAssert, options);

    logger.info("2. Click on footer options and check page URL");
    clickFooterOptionsAndCheckUrl(homePage, softAssert, options);
    softAssert.assertAll();
  }

  @Test(
      groups = {TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.HOME_PAGE},
      description = "11107435")
  public void homepageFooterBlogTest() {
    logger.info("1. Navigate to Home page");
    HomePage homePage = navigateToHome();
    softAssert.assertNotNull(homePage, "Failed to navigate to the Home page.");

    logger.info("Verify the user is shown with al expected options under Blog");
    FooterComponent footer = homePage.getFooterComponent();
    List<FooterOptions> options = FooterOptions.getBlogOptions();
    areFooterOptionsDisplayed(footer, softAssert, options);

    logger.info("2. Click on footer options and check page URL");
    clickFooterOptionsAndCheckUrl(homePage, softAssert, options);
    softAssert.assertAll();
  }

  @Test(
      groups = {TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.HOME_PAGE},
      description = "11107436")
  public void homepageFooterPeetsAppTest() {
    logger.info("1. Navigate to Home page");
    HomePage homePage = navigateToHome();
    softAssert.assertNotNull(homePage, "Failed to navigate to the Home page.");

    logger.info("2. Click on 'APP Store'");
    FooterComponent footer = homePage.getFooterComponent();
    footer.clickAppStore();

    logger.info("Verify App store page is displayed");
    String currentUrl = WebHelper.getCurrentUrl().toLowerCase();
    if (!WebHelper.isDesktop()) {
      softAssert.assertTrue(
          currentUrl.contains("apps.apple.com"), "App store page isn't displayed");
      softAssert.assertTrue(currentUrl.contains("peets"), "Peet's App store page isn't displayed");
    }

    logger.info("2. Click on 'Google play'");
    footer = navigateToHome().getFooterComponent();
    footer.clickGooglePlay();

    logger.info("Verify Google play page is displayed");
    currentUrl = WebHelper.getCurrentUrl().toLowerCase();
    softAssert.assertTrue(
        currentUrl.contains("play.google.com"), "Google play page isn't displayed");
    softAssert.assertTrue(currentUrl.contains("peets"), "Peet's Google play page isn't displayed");

    softAssert.assertAll();
  }

  @Test(
      groups = {TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.HOME_PAGE},
      description = "11107437")
  public void homepageFooterSocialMediaIconsTest() {
    logger.info("1. Navigate to Home page");
    HomePage homePage = navigateToHome();
    softAssert.assertNotNull(homePage, "Failed to navigate to the Home page.");

    logger.info("Verify the user is shown with al expected social media options");
    FooterComponent footer = homePage.getFooterComponent();
    List<FooterOptions> options = FooterOptions.getSocialMediaLinks();
    areFooterSocialMediaLinksDisplayed(footer, softAssert, options);

    logger.info("2. Click on footer social media options and check page URL");
    clickSocialMediaLinkAndCheckUrl(homePage, softAssert, options);
    softAssert.assertAll();
  }

  @Test(
      groups = {TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.HOME_PAGE},
      description = "11107438")
  public void homepageFooterPeetsCoffeeEndTest() {
    logger.info("1. Navigate to Home page");
    HomePage homePage = navigateToHome();
    softAssert.assertNotNull(homePage, "Failed to navigate to the Home page.");

    logger.info("Verify the user is shown with al expected footer end sub options");
    FooterComponent footer = homePage.getFooterComponent();
    List<FooterOptions> options = FooterOptions.getEndSubLinks();
    areFooterEndSubLinksDisplayed(footer, softAssert, options);

    logger.info("2. Click on footer end sub options and check page URL");
    clickEndSubLinkAndCheckUrl(homePage, softAssert, options);
    softAssert.assertAll();
  }

  private static void areFooterOptionsDisplayed(
      FooterComponent footer, SoftAssert softAssert, List<FooterOptions> options) {
    logger.info("Verify all expected footer options are displayed");
    for (FooterOptions option : options) {
      softAssert.assertTrue(
          footer.isOptionDisplayed(option),
          String.format("Footer option [%s] isn't displayed", option));
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
          WebHelper.getCurrentUrl(),
          option.getUrl(),
          String.format("Wrong URL is displayed for option [%s]", option.getOption()));

      logger.info("Navigate back to Home page");
      BaseTest baseTest = new BaseTest();
      homePage = baseTest.navigateToHome();
    }
  }

  private static void areFooterSocialMediaLinksDisplayed(
      FooterComponent footer, SoftAssert softAssert, List<FooterOptions> options) {
    logger.info("Verify all expected footer social media links are displayed");
    for (FooterOptions option : options) {
      softAssert.assertTrue(
          footer.isSocialMediaLinkDisplayed(option), "Footer social media [{}] isn't displayed");
    }
  }

  private static void clickSocialMediaLinkAndCheckUrl(
      HomePage homePage, SoftAssert softAssert, List<FooterOptions> options) {
    FooterComponent footer;
    for (FooterOptions option : options) {
      footer = homePage.getFooterComponent();
      footer.clickSocialMediaLink(option);

      logger.info("Verify expected page URL is displayed");
      String currentUrl = WebHelper.getCurrentUrl();
      softAssert.assertTrue(
          currentUrl.contains(option.getUrl()),
          String.format(
              "The URL [%s] doesn't contain expected parameter [%s] for option [%s]",
              currentUrl, option.getUrl(), option.getOption()));

      logger.info("Navigate back to Home page");
      BaseTest baseTest = new BaseTest();
      homePage = baseTest.navigateToHome();
    }
  }

  private static void areFooterEndSubLinksDisplayed(
      FooterComponent footer, SoftAssert softAssert, List<FooterOptions> options) {
    logger.info("Verify all expected footer end sub links are displayed");
    for (FooterOptions option : options) {
      if (option.getUrl().isEmpty()) {
        softAssert.assertTrue(
            footer.isFooterEndTextDisplayed(option),
            String.format("Footer end sub text [%s] isn't displayed", option.getOption()));
      } else {
        softAssert.assertTrue(
            footer.isEndSubLinkDisplayed(option),
            String.format("Footer end sub link [%s] isn't displayed", option.getOption()));
      }
    }
  }

  private static void clickEndSubLinkAndCheckUrl(
      HomePage homePage, SoftAssert softAssert, List<FooterOptions> options) {
    FooterComponent footer;
    for (FooterOptions option : options) {
      footer = homePage.getFooterComponent();
      if (!option.getUrl().isEmpty()) {
        footer.clickEndSubLink(option);

        logger.info("Verify expected page URL is displayed");
        String currentUrl = WebHelper.getCurrentUrl();
        softAssert.assertTrue(
            currentUrl.contains(option.getUrl()),
            String.format(
                "Wrong URL is displayed for option [%s]. Current URL [%s]. Expected parameter [%s]",
                option.getOption(), currentUrl, option.getUrl()));

        logger.info("Navigate back to Home page");
        BaseTest baseTest = new BaseTest();
        homePage = baseTest.navigateToHome();
      }
    }
  }
}
