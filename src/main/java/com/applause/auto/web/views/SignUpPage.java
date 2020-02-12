package com.applause.auto.web.views;

import com.applause.auto.common.data.Constants;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Checkbox;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.helper.SyncHelper;

@Implementation(is = SignUpPage.class, on = Platform.WEB)
public class SignUpPage extends BaseComponent {

  /* -------- Variables -------- */

  public String email;

  /* -------- Elements -------- */

  @Locate(css = ".account-create-form", on = Platform.WEB)
  private Text getViewSignature;

  @Locate(css = "#firstname", on = Platform.WEB)
  private TextBox getFirstNameTextBox;

  @Locate(css = "#lastname", on = Platform.WEB)
  private TextBox getLastNameTextBox;

  @Locate(css = "#email_address", on = Platform.WEB)
  private TextBox getEmailTextBox;

  @Locate(css = "#confirm_email", on = Platform.WEB)
  private TextBox getConfirmEmailTextBox;

  @Locate(css = "#password", on = Platform.WEB)
  private TextBox getPasswordTextBox;

  @Locate(css = "#confirmation", on = Platform.WEB)
  private TextBox getConfirmPasswordTextBox;

  @Locate(css = "#zipcode", on = Platform.WEB)
  private TextBox getZipCodeTextBox;

  @Locate(css = "#is_subscribed", on = Platform.WEB)
  private Checkbox getSendEmailsCheckbox;

  @Locate(css = "#remember-me-box input", on = Platform.WEB)
  private Checkbox getRememberMeCheckbox;

  @Locate(css = ".buttons-set button", on = Platform.WEB)
  private Button getSubmitButton;

  @Locate(css = "opc-please-wait", on = Platform.WEB)
  private ContainerElement getShippingLoadingSpinner;

  /* -------- Actions -------- */

  /** Continue after entering required Shipping info */
  public <T extends BaseComponent> T submitSignUpInfo(Class<T> clazz) {
    logger.info("Clicking Submit after filling sign-up info");
    fillSignUpInfo();
    clickSubmit();
    return ComponentFactory.create(clazz);
  }

  /** Fill Required Fields for Shipping */
  public void fillSignUpInfo() {
    logger.info("Filling Sign up info");
    SyncHelper.sleep(5000);
    long timeStamp = System.currentTimeMillis();
    email = String.format(Constants.TestData.EMAIL, timeStamp);
    getFirstNameTextBox.sendKeys(Constants.TestData.FIRST_NAME);
    getLastNameTextBox.sendKeys(Constants.TestData.LAST_NAME);
    getEmailTextBox.sendKeys(email);
    getConfirmEmailTextBox.sendKeys(email);
    getPasswordTextBox.sendKeys(Constants.TestData.PASSWORD);
    getConfirmPasswordTextBox.sendKeys(Constants.TestData.PASSWORD);
    getZipCodeTextBox.sendKeys(Constants.TestData.ZIP_CODE);
    // TODO: Fix up and add back (tests fail when these are uncommented)
    // disableSendEmail();
    // disableRememberMe();
  }

  /** Disable Send Email Checkbox */
  public void disableSendEmail() {
    logger.info("Disable Send Email Checkbox");
    if (getSendEmailsCheckbox.isChecked()) {
      getSendEmailsCheckbox.click();
    }
  }

  /** Disable Remember me Checkbox */
  public void disableRememberMe() {
    logger.info("Disable Remember me Checkbox");
    if (getRememberMeCheckbox.isChecked()) {
      getRememberMeCheckbox.click();
    }
  }

  /** Click Submit */
  public void clickSubmit() {
    logger.info("Click Submit button");
    getSubmitButton.click();
  }
}
