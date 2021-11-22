package com.applause.auto.new_web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.new_web.views.my_account.MyAccountPage;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Link;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import io.qameta.allure.Step;
import java.time.Duration;

@Implementation(is = SignInPage.class, on = Platform.WEB)
public class SignInPage extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(id = "Email", on = Platform.WEB)
  private TextBox getEmailTextBox;

  @Locate(id = "Password", on = Platform.WEB)
  private TextBox getPasswordTextBox;

  @Locate(css = "input[value='Sign In']", on = Platform.WEB)
  private Button getSignInButton;

  @Locate(css = "input[value='Create Account']", on = Platform.WEB)
  private Button getCreateAccountButton;

  @Locate(css = ".text-danger", on = Platform.WEB)
  private Text getErrorMessage;

  @Locate(css = ".resetPW", on = Platform.WEB)
  private Link getForgotPasswordLink;

  /* -------- Actions -------- */

  /**
   * Enter Text into email field
   *
   * @param text
   */
  @Step("Enter email")
  public void enterEmail(String text) {
    logger.info("Enter email : " + text);
    SdkHelper.getSyncHelper().wait(Until.uiElement(getEmailTextBox).visible());
    getEmailTextBox.sendKeys(text);
    SdkHelper.getSyncHelper().sleep(1000); // Wait for action
  }

  /**
   * Enter Text into Password field
   *
   * @param text
   */
  @Step("Enter password")
  public void enterPassword(String text) {
    logger.info("Enter Password : " + text);
    getPasswordTextBox.sendKeys(text);
    SdkHelper.getSyncHelper().sleep(1000); // Wait for action
  }

  /**
   * Click on Login Button on Login Page
   *
   * @return My Account page
   */
  @Step("Click Sign in button")
  public MyAccountPage clickOnSignInButton() {
    return clickOnSignInButton(MyAccountPage.class);
  }

  @Step("Click Create account button")
  public CreateAccountPage clickOnCreateAccountButton() {
    logger.info("Click on create account button");
    SdkHelper.getSyncHelper().wait(Until.uiElement(getCreateAccountButton).clickable());
    getCreateAccountButton.click();
    SdkHelper.getSyncHelper().sleep(1000);

    return SdkHelper.create(CreateAccountPage.class);
  }

  /**
   * Click on Login Button on Login Page
   *
   * @param expectedClass
   * @return expectedClass
   */
  @Step("Click Sign in button")
  public <T extends BaseComponent> T clickOnSignInButton(Class<T> expectedClass) {
    logger.info("Click on sign in button");
    SdkHelper.getSyncHelper().wait(Until.uiElement(getSignInButton).clickable());
    getSignInButton.click();
    SdkHelper.getSyncHelper().sleep(1000); // Wait for action

    return SdkHelper.create(expectedClass);
  }

  @Step("Verify error message is displayed")
  public boolean errorMessageIsDisplayed() {
    try {
      SdkHelper.getSyncHelper()
          .wait(Until.uiElement(getErrorMessage).visible().setTimeout(Duration.ofSeconds(20)));
    } catch (Exception e) {
      logger.info("Error message isn't displayed");
      return false;
    }

    return !getErrorMessage.getText().isEmpty();
  }

  @Step("Click Forgot password link")
  public ResetPasswordPage clickForgotPasswordLink() {
    logger.info("Clicking Forgot password link");
    getForgotPasswordLink.click();
    return SdkHelper.create(ResetPasswordPage.class);
  }
}
