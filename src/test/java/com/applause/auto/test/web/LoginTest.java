package com.applause.auto.test.web;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.web.views.Landing;
import com.applause.auto.web.views.MyAccountPage;
import com.applause.auto.web.views.SignInPage;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

  private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().getClass());

  @Test(
      groups = {TestNGGroups.LOGIN, TestNGGroups.NEW_WEB_CASES},
      description = "10351")
  public void loginTest() {

    logger.info("1. Navigate to landing page");
    Landing landing = navigateToLanding();
    Assert.assertNotNull(landing, "Failed to navigate to the landing page.");

    logger.info("2. Click on Sign In");
    SignInPage signInPage = landing.clickSignInButton();

    logger.info("3. Enter username and password");
    signInPage.enterEmail(Constants.TestData.WEB_USERNAME);
    signInPage.enterPassword(Constants.TestData.WEB_PASSWORD);
    MyAccountPage myAccountPage = signInPage.clickOnSignInButton();

    logger.info("Verify user is signed in");
    Assert.assertTrue(
        myAccountPage.getWelcomeMessage().contains("welcome, web"),
        "User is not signed in or welcome name is wrong");
  }
}
