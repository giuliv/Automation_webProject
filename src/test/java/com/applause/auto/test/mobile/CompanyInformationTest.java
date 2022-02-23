package com.applause.auto.test.mobile;

import com.applause.auto.common.data.Constants.MyAccountTestData;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.data.enums.SwipeDirection;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.mobile.views.CustomerSupportScreenView;
import com.applause.auto.mobile.views.HelpAndFeedbackView;
import com.applause.auto.mobile.views.HomeView;
import com.applause.auto.mobile.views.LegalInfoView;
import com.applause.auto.mobile.views.MoreOptionsView;
import com.applause.auto.mobile.views.PeetnikMainFaqView;
import com.applause.auto.mobile.views.PeetnikRewardsLandingView;
import com.applause.auto.mobile.views.PeetnikRewardsTermsAndConditionsView;
import com.applause.auto.mobile.views.PrivacyPolicyView;
import com.applause.auto.test.mobile.helpers.TestHelper;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CompanyInformationTest extends BaseTest {

  private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().getClass());

  @Test(
      groups = {TestNGGroups.COMPANY_INFORMATION, TestNGGroups.REGRESSION, TestNGGroups.WEB_UI},
      description = "625936")
  public void faqsTest() {
    logger.info("Launch the app and log in");
    HomeView homeView =
        TestHelper.skipOnboardingAndLogin(MyAccountTestData.EMAIL, MyAccountTestData.PASSWORD);
    Assert.assertNotNull(homeView, "Home View does not displayed");

    logger.info("Tap Help & Feedback field");
    HelpAndFeedbackView helpAndFeedbackView = homeView.getAccountProfileMenu().helpAndFeedback();
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

    //    logger.info("Navigate back on device");
    //    MobileHelper.activateApp();
  }

  @Test(
      groups = {TestNGGroups.COMPANY_INFORMATION, TestNGGroups.REGRESSION, TestNGGroups.WEB_UI},
      description = "625938")
  public void termsAndPrivacyPolicyTest() {
    HomeView homeView =
        TestHelper.skipOnboardingAndLogin(MyAccountTestData.EMAIL, MyAccountTestData.PASSWORD);
    Assert.assertNotNull(homeView, "Home View does not displayed");

    logger.info("STEP - Tap on ... at top right of home screen");
    MoreOptionsView accountMenuMobileChunk = homeView.getAccountProfileMenu();

    logger.info("STEP - Tap on Terms and Privacy Policy field/row");
    LegalInfoView legalInfoView = accountMenuMobileChunk.termsAndPrivacyPolicy();

    logger.info("VERIFY - User is taken to LEGAL INFO screen");
    Assert.assertNotNull(legalInfoView, "User does not taken to LEGAL INFO screen");

    logger.info("VERIFY - Header: Legal Info");
    Assert.assertEquals(
        legalInfoView.getHeader(), "LEGAL INFO", "Header: Legal Info does not found");

    logger.info("VERIFY - Back arrow at op left corner");
    Assert.assertTrue(
        legalInfoView.isBackArrowDisplayed(), "Back arrow at op left corner does not displayed");

    logger.info("VERIFY - Fields: Peetnik Rewards Terms & Conditions");
    Assert.assertTrue(
        legalInfoView.isPeetnikRewardsTermsAndConditionsDisplayed(),
        "Fields: Peetnik Rewards Terms & Conditions does not displayed");

    logger.info("VERIFY - Fields: Privacy Policy");
    Assert.assertTrue(
        legalInfoView.isPrivacyPolicyDisplayed(), "Fields: Privacy Policy does not displayed");

    logger.info("STEP - Tap on Peetnik Rewards Terms & Conditions field/arrow");
    PeetnikRewardsTermsAndConditionsView peetnikRewardsTermsAndConditionsView =
        legalInfoView.peetnikRewardsTermsAndConditions();

    logger.info(
        "VERIFY - User is taken to The Peetnik Rewards Program Terms and Conditions screen on Peet's mobile site");
    Assert.assertNotNull(
        peetnikRewardsTermsAndConditionsView,
        "User does not taken to The Peetnik Rewards Program Terms and Conditions screen on Peet's mobile site");

    logger.info("STEP - Tap done");
    legalInfoView = peetnikRewardsTermsAndConditionsView.done();

    logger.info("VERIFY - Browser closes and user is back on legal info screen of app");
    Assert.assertNotNull(legalInfoView, "User does not on Legal Info view");

    logger.info("STEP - Tap on Privacy Policy field/row");
    PrivacyPolicyView privacyPolicyView = legalInfoView.privacyPolicy();

    logger.info(
        "VERIFY - User is taken to Peet's Coffee Privacy Policy screen on Peet's mobile site");
    Assert.assertNotNull(
        privacyPolicyView,
        "User does not taken to Peet's Coffee Privacy Policy screen on Peet's mobile site");

    logger.info("STEP - Tap done");
    legalInfoView = privacyPolicyView.done();

    logger.info("VERIFY - Browser closes and user is back on legal info screen of app");
    Assert.assertNotNull(legalInfoView, "User does not on Legal Info view");
  }

  @Test(
      groups = {TestNGGroups.COMPANY_INFORMATION, TestNGGroups.REGRESSION},
      description = "625933")
  public void moreScreenTest() {
    HomeView homeView =
        TestHelper.skipOnboardingAndLogin(MyAccountTestData.EMAIL, MyAccountTestData.PASSWORD);
    Assert.assertNotNull(homeView, "Home View does not displayed");

    logger.info("STEP - Tap on ... at top right of home screen");
    MoreOptionsView accountMenuMobileChunk = homeView.getAccountProfileMenu();

    logger.info("VERIFY - Header: Hi, \"FIRST NAME\"");
    Assert.assertTrue(
        accountMenuMobileChunk.getTitle().matches("HI, TEST"),
        "Header: Hi, \"TEST\" does not displayed");

    logger.info("VERIFY - X at top left to close screen");
    Assert.assertTrue(
        accountMenuMobileChunk.isCloseButtonDisplayed(), "X at top left does not displayed");

    logger.info("VERIFY - Sub header: Account Settings");
    Assert.assertTrue(
        accountMenuMobileChunk.isAccountSettingsSubHeaderDisplayed(),
        "Sub header: Account Settings does not displayed");

    logger.info("VERIFY - Profile Details");
    Assert.assertTrue(
        accountMenuMobileChunk.isProfileDetailsMenuItemDisplayed(),
        "Profile Details does not displayed");

    logger.info("VERIFY - Personal Settings");
    Assert.assertTrue(
        accountMenuMobileChunk.isPersonalSettingsMenuItemDisplayed(),
        "Personal Settings does not displayed");

    logger.info("VERIFY - Payment Methods");
    Assert.assertTrue(
        accountMenuMobileChunk.isPaymentsMethodsMenuItemDisplayed(),
        "Payment Methods does not displayed");

    logger.info("VERIFY - Account Activity");
    Assert.assertTrue(
        accountMenuMobileChunk.isAccountActivityMenuItemDisplayed(),
        "Account Activity does not displayed");

    logger.info("VERIFY - Sub header: Peet's Coffee");
    Assert.assertTrue(
        accountMenuMobileChunk.isSubHeaderPeetsCoffeeDisplayed(),
        "Sub header: Peet's Coffee does not displayed");

    SdkHelper.getDeviceControl().swipeAcrossScreenWithDirection(SwipeDirection.UP);

    logger.info("VERIFY - Send a Gift");
    Assert.assertTrue(
        accountMenuMobileChunk.isSendGiftDisplayed(), "Send a Gift does not displayed");

    logger.info("VERIFY - About Us");
    Assert.assertTrue(
        accountMenuMobileChunk.isAboutUsMenuItemDisplayed(), "About Us does not displayed");

    logger.info("VERIFY - Help & Feedback");
    Assert.assertTrue(
        accountMenuMobileChunk.isHelpAndFeedback(), "Help & Feedback does not displayed");

    logger.info("VERIFY - Terms and Privacy Policy");
    Assert.assertTrue(
        accountMenuMobileChunk.isTermsAndPrivacyPolicy(),
        "Terms and Privacy Policy does not displayed");

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

  @Test(
      groups = {TestNGGroups.COMPANY_INFORMATION, TestNGGroups.REGRESSION, TestNGGroups.WEB_UI},
      description = "625937")
  public void contactCustomerServiceTest() {
    HomeView homeView =
        TestHelper.skipOnboardingAndLogin(MyAccountTestData.EMAIL, MyAccountTestData.PASSWORD);
    Assert.assertNotNull(homeView, "Home View does not displayed");

    logger.info("Tap Help & Feedback field");
    HelpAndFeedbackView helpAndFeedbackView = homeView.getAccountProfileMenu().helpAndFeedback();
    Assert.assertTrue(
        helpAndFeedbackView.isPageDisplayedCorrectly(), "Help & Feedback view is incorrect");

    logger.info("STEP - Tap on Contact Customer Service field");
    CustomerSupportScreenView customerSupportScreenView =
        helpAndFeedbackView.contactCustomerService();

    logger.info("VERIFY - User is taken to Customer Support screen on Peet's mobile site");
    Assert.assertNotNull(
        customerSupportScreenView,
        "User does not taken to Customer Support screen on Peet's mobile site");

    logger.info("#STEP - Navigate down page until user sees 'Help Center' link at the footer");
    PeetnikMainFaqView peetnikMainFaqView = customerSupportScreenView.clickHelpCenterFooterButton();

    logger.info("STEP - Tap on “Peetnik Rewards & Order Ahead” section");
    PeetnikRewardsLandingView peetnikRewardsLandingView =
        peetnikMainFaqView.tapPeetnikRewardsAndOrderAhead();
    Assert.assertNotNull(
        peetnikRewardsLandingView, "Peetnik Rewards Landing View does not displayed");

    logger.info("STEP - Tap done");
    helpAndFeedbackView = peetnikRewardsLandingView.done();

    logger.info("VERIFY - In-app browser closes");
    Assert.assertNotNull(helpAndFeedbackView, "Browser does not closed in proper way");
  }
}
