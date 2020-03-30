package com.applause.auto.test.mobile;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.mobile.views.DashboardView;
import com.applause.auto.mobile.views.HelpAndFeedbackView;
import com.applause.auto.mobile.views.LandingView;
import com.applause.auto.mobile.views.PeetnikRewardsLandingView;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

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
}
