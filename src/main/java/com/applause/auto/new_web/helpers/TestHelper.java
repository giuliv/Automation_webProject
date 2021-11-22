package com.applause.auto.new_web.helpers;

import com.applause.auto.common.data.Constants;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.new_web.views.CreateAccountPage;
import com.applause.auto.new_web.views.HomePage;
import com.applause.auto.new_web.views.my_account.MyAccountPage;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

public class TestHelper {
  private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().getClass());

  public MyAccountPage createAccount(HomePage homePage, String mail) {
    CreateAccountPage createAccountPage =
        homePage.getHeader().clickAccountButton().clickOnCreateAccountButton();
    Assert.assertTrue(
        SdkHelper.getDriver().getCurrentUrl().contains("/Registration"),
        "Registration URL is not correct!");

    MyAccountPage myAccountPage =
        createAccountPage.createAccount(
            Constants.WebTestData.FIRST_NAME,
            Constants.WebTestData.LAST_NAME,
            mail,
            Constants.TestData.WEB_PASSWORD,
            Constants.TestData.WEB_PASSWORD);

    Assert.assertTrue(myAccountPage.getWelcomeMessage().contains("welcome"), "User is not created");
    return myAccountPage;
  }
}
