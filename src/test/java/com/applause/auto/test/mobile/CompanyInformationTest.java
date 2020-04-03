package com.applause.auto.test.mobile;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.data.enums.SwipeDirection;
import com.applause.auto.mobile.components.AccountMenuMobileChunk;
import com.applause.auto.mobile.views.DashboardView;
import com.applause.auto.mobile.views.HelpAndFeedbackView;
import com.applause.auto.mobile.views.LandingView;
import com.applause.auto.mobile.views.PeetnikRewardsLandingView;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.control.DeviceControl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.invoke.MethodHandles;

public class CompanyInformationTest extends BaseTest {

  private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().getClass());

  @Test(
      groups = {TestNGGroups.COMPANY_INFORMATION},
      description = "625936")
  public void faqsTest() {
    logger.info("Launch the app and arrive at the first on boarding screen view");
    LandingView landingView = ComponentFactory.create(LandingView.class);
    DashboardView dashboardView =
        testHelper.signIn(
            landingView,
            Constants.MyAccountTestData.EMAIL,
            Constants.MyAccountTestData.PASSWORD,
            DashboardView.class);

    Assert.assertNotNull(dashboardView, "Dashboard View does not displayed");

    logger.info("Tap Help & Feedback field");
    HelpAndFeedbackView helpAndFeedbackView =
        dashboardView.getAccountProfileMenu().helpAndFeedback();
    Assert.assertTrue(
        helpAndFeedbackView.isPageDisplayedCorrectly(), "Help & Feedback view is incorrect");

    logger.info("Tap on View Our FAQs");
    PeetnikRewardsLandingView peetnikRewardsLandingView =
        helpAndFeedbackView.clickViewOurFAQs().closeReportAProblemPopUpDisplayed();
    Assert.assertNotNull(
        peetnikRewardsLandingView, "Peetnik Rewards Landing View does not displayed");

    logger.info(
        "Navigate down Peetnik Rewards page until user sees Have questions section. \n"
            + "Tap on Get Answers button. \n"
            + "Tap on Peetnik Rewards & Order Ahead section. \n"
            + "Tap on a question to view the answer");
    peetnikRewardsLandingView =
        peetnikRewardsLandingView.clickGetAnswers().clickGetPeetnikRewardsAndOrderAheadQuestion();
    Assert.assertTrue(
        peetnikRewardsLandingView.isQuestionAnswerDisplayed(),
        "Peetnik Rewards question answer is not displayed");

    // logger.info("Navigate back on device");
    // MobileHelper.activateApp();
  }

  @Test(
      groups = {TestNGGroups.COMPANY_INFORMATION},
      description = "625933")
  public void moreScreenTest() {
    logger.info("Launch the app and arrive at the first on boarding screen view");
    LandingView landingView = ComponentFactory.create(LandingView.class);
    DashboardView dashboardView =
        testHelper.signIn(
            landingView,
            Constants.MyAccountTestData.EMAIL,
            Constants.MyAccountTestData.PASSWORD,
            DashboardView.class);

    Assert.assertNotNull(dashboardView, "Dashboard View does not displayed");

    logger.info("STEP - Tap on ... at top right of home screen");
    AccountMenuMobileChunk accountMenuMobileChunk = dashboardView.getAccountProfileMenu();

    logger.info("VERIFY - Header: Hi, \"FIRST NAME\"");
    Assert.assertTrue(
        accountMenuMobileChunk.getTitle().matches("HI, APPLAUSE"),
        "Header: Hi, \"FIRST NAME\" does not displayed");

    logger.info("VERIFY - X at top left to close screen");
    Assert.assertTrue(
        accountMenuMobileChunk.isCloseButtonDisplayed(), "X at top left does not displayed");

    logger.info("VERIFY - Sub header: Account Settings");
    Assert.assertTrue(
        accountMenuMobileChunk.isAccountSettingsSubHeaderDisplayed(),
        "Sub header: Account Settings does not displayed");

    logger.info("VERIFY - Profile Details ");
    Assert.assertTrue(
        accountMenuMobileChunk.isProfileDetailsMenuItemDisplayed(),
        "Profile Details does not displayed");

    logger.info("VERIFY - General Settings");
    Assert.assertTrue(
        accountMenuMobileChunk.isGeneralSettingsMenuItemDisplayed(),
        "General Settings does not displayed");

    logger.info("VERIFY - Payment Methods");
    Assert.assertTrue(
        accountMenuMobileChunk.isPaymentsMethodsMenuItemDisplayed(),
        "Payment Methods does not displayed");

    logger.info("VERIFY - Account History");
    Assert.assertTrue(
        accountMenuMobileChunk.isAccountHistoryMenuItemDisplayed(),
        "Account History does not displayed");

    logger.info("VERIFY - Sub header: Peet's Coffee");
    Assert.assertTrue(
        accountMenuMobileChunk.isSubHeaderPeetsCoffeeDisplayed(),
        "Sub header: Peet's Coffee does not displayed");

    DeviceControl.swipeAcrossScreenWithDirection(SwipeDirection.UP);

    logger.info("VERIFY - About Us");
    Assert.assertTrue(
        accountMenuMobileChunk.isAboutUsMenuItemDisplayed(), "About Us does not displayed");

    logger.info("VERIFY - Help & Feedback");
    Assert.assertTrue(
        accountMenuMobileChunk.isHelpAndFeedback(), "Help & Feedback does not displayed");

    logger.info("VERIFY - Facebook icon");
    Assert.assertTrue(
        accountMenuMobileChunk.isFacebookIconDisplayed(), "Facebook icon does not displayed");

    logger.info("VERIFY - Instagram icon");
    Assert.assertTrue(
        accountMenuMobileChunk.isInstagramIconDisplayed(), "Instagram icon does not displayed");

    logger.info("VERIFY - Twitter icon");
    Assert.assertTrue(
        accountMenuMobileChunk.isTwitterIconDisplayed(), "Twitter icon does not displayed");

    logger.info("VERIFY - Text: Peetnik Rewards\n" + "Version xx.x.x.xxx");
    Assert.assertTrue(
        accountMenuMobileChunk.getVersion().matches("Peetnik Rewards Version \\d+\\.\\d+\\.\\d+.*"),
        "Text: Peetnik Rewards\\n\" + \"Version xx.x.x.xxx does not displayed");

    logger.info("VERIFY - [Button] Sign Out");
    Assert.assertTrue(
        accountMenuMobileChunk.isSignOutButtonDisplayed(), "[Button] Sign Out does not displayed");
  }
}
