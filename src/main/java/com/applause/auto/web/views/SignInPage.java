package com.applause.auto.web.views;

import com.applause.auto.common.data.Constants;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.TextBox;

@Implementation(is = SignInPage.class, on = Platform.WEB)
public class SignInPage extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(id = "Email", on = Platform.WEB)
  private TextBox getEmailTextBox;

  @Locate(id = "Password", on = Platform.WEB)
  private TextBox getPasswordTextBox;

  @Locate(css = "input[value='Sign In']", on = Platform.WEB)
  private Button getSignInButton;

  @Locate(css = ".new-users button", on = Platform.WEB)
  private Button getCreateAccountButton;

  /* -------- Actions -------- */

  /**
   * Enter Text into email field
   *
   * @param text
   */
  public void enterEmail(String text) {
    logger.info("Enter email : " + text);
    SdkHelper.getSyncHelper().wait(Until.uiElement(getEmailTextBox).visible());
    getEmailTextBox.sendKeys(text);
  }

  /**
   * Enter Text into Email Field
   *
   * @param email
   * @param safariEmail
   */
  public void enterEmailByBrowser(String email, String safariEmail) {
    logger.info("Enter email");
    if (SdkHelper.getEnvironmentHelper().isSafari()) getEmailTextBox.sendKeys(safariEmail);
    else getEmailTextBox.sendKeys(email);
  }

  /**
   * Enter Text into Password field
   *
   * @param text
   */
  public void enterPassword(String text) {
    logger.info("Enter Password : " + text);
    getPasswordTextBox.sendKeys(text);
  }

  /**
   * Click on Login Button on Login Page
   *
   * @return My Account page
   */
  public MyAccountPage clickOnSignInButton() {
    logger.info("Click on sign in button");
    getSignInButton.click();
    return SdkHelper.create(MyAccountPage.class);
  }

  /**
   * Main User Login
   *
   * @return MyAccountPage
   */
  public MyAccountPage mainUserLogin() {
    logger.info("Login with main user");
    String username =
        (SdkHelper.getEnvironmentHelper().isSafari())
            ? Constants.TestData.USERNAME_SAFARI
            : Constants.TestData.USERNAME;
    performUserLogin(username, Constants.TestData.PASSWORD);
    return SdkHelper.create(MyAccountPage.class);
  }

  /**
   * Checkout User Login
   *
   * @return MyAccountPage
   */
  public MyAccountPage checkoutUserLogin() {
    logger.info("Login with checkout user");
    String username =
        (SdkHelper.getEnvironmentHelper().isSafari())
            ? Constants.CheckoutUserTestData.USERNAME_SAFARI
            : Constants.CheckoutUserTestData.USERNAME;
    performUserLogin(username, Constants.CheckoutUserTestData.PASSWORD);
    return SdkHelper.create(MyAccountPage.class);
  }

  /**
   * User login my account page.
   *
   * @param email the email
   * @param password the password
   * @return the my account page
   */
  public MyAccountPage userLogin(String email, String password) {
    logger.info("Login with main user");
    performUserLogin(email, password);
    return SdkHelper.create(MyAccountPage.class);
  }

  /**
   * Click on Create Account Button on Login Page
   *
   * @return SignUppage
   */
  public SignUpPage clickonCreateAccountButton() {
    logger.info("Click on Create Account button");
    getCreateAccountButton.click();
    return SdkHelper.create(SignUpPage.class);
  }

  /**
   * Perfom user login
   *
   * @param email the email
   * @param password the password
   */
  private void performUserLogin(String email, String password) {
    enterEmail(email);
    enterPassword(password);
    SdkHelper.getSyncHelper().wait(Until.uiElement(getSignInButton).clickable()).click();
  }
}
