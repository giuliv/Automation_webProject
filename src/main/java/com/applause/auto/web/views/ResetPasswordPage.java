package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Link;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import io.qameta.allure.Step;

@Implementation(is = ResetPasswordPage.class, on = Platform.WEB)
public class ResetPasswordPage extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(css = "#Email", on = Platform.WEB)
  private TextBox getEmailTextBox;

  @Locate(css = "input[type='submit']", on = Platform.WEB)
  private Link getSubmitButton;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(getSubmitButton).present());
  }

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
   * Click Submit button
   *
   * @return PasswordRecoveryPage
   */
  @Step("Click Submit button")
  public PasswordRecoveryPage clickSubmitButton() {
    logger.info("Clicking Submit button");
    SdkHelper.getSyncHelper().wait(Until.uiElement(getSubmitButton).visible());
    getSubmitButton.click();
    SdkHelper.getSyncHelper().sleep(1000); // Wait for action
    return SdkHelper.create(PasswordRecoveryPage.class);
  }
}
