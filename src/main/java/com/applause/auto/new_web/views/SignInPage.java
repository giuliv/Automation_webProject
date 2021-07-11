package com.applause.auto.new_web.views;

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

  @Locate(css = "input[value='Create Account']", on = Platform.WEB)
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
    SdkHelper.getSyncHelper().sleep(1000); // Wait for action
  }

  /**
   * Enter Text into Password field
   *
   * @param text
   */
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
  public MyAccountPage clickOnSignInButton() {
    logger.info("Click on sign in button");
    SdkHelper.getSyncHelper().wait(Until.uiElement(getSignInButton).clickable());
    getSignInButton.click();

    return SdkHelper.create(MyAccountPage.class);
  }

  public CreateAccountPage clickOnCreateAccountButton() {
    logger.info("Click on create account button");
    SdkHelper.getSyncHelper().wait(Until.uiElement(getCreateAccountButton).clickable());
    getCreateAccountButton.click();
    return SdkHelper.create(CreateAccountPage.class);
  }
}
