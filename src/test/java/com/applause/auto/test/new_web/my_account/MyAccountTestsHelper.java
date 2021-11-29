package com.applause.auto.test.new_web.my_account;

import com.applause.auto.common.data.Constants.MyAccountTestData;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.new_web.views.SignInPage;
import com.applause.auto.new_web.views.my_account.MyAccountPage;
import com.applause.auto.test.new_web.BaseTest;
import io.qameta.allure.Step;
import org.testng.Assert;

public class MyAccountTestsHelper extends BaseTest {

  @Step("Verify that updated payment information popup is displayed")
  public static void isPaymentInformationUpdated() {
    logger.info("Verify that updated payment information popup is displayed");
    String alertText = null;
    try {
      alertText = SdkHelper.getDriver().switchTo().alert().getText();
    } catch (Exception e) {
      SdkHelper.getSyncHelper().sleep(2000);
      alertText = SdkHelper.getDriver().switchTo().alert().getText();
    }

    Assert.assertFalse(alertText.isEmpty(), "Alert isn't displayed");
    Assert.assertEquals(
        alertText,
        MyAccountTestData.UPDATED_PAYMENT_DATA_SUCCESSFULLY_ALERT,
        "Alert text isn't correct");
  }

  /**
   * Navigate to My Account page and login
   *
   * @param signInPage
   * @param email
   * @param password
   * @return MyAccountPage
   */
  public static MyAccountPage navigateToMyAccountPage(
      SignInPage signInPage, String email, String password) {
    Assert.assertNotNull(signInPage, "Failed to navigate to the Sign in page.");

    logger.info("2. Enter valid credentials.");
    signInPage.enterEmail(email);
    signInPage.enterPassword(password);

    logger.info("3. Click on Sign in");
    return signInPage.clickOnSignInButton();
  }
}
