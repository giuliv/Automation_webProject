package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.DriverManager;
import com.applause.auto.util.helper.EnvironmentHelper;

@Implementation(is = EditAccountInformationPage.class, on = Platform.WEB)
public class EditAccountInformationPage extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(css = "#firstname", on = Platform.WEB)
  private TextBox getFirstNameField;

  @Locate(css = "#email", on = Platform.WEB)
  private TextBox getEmailField;

  @Locate(css = "button.button.btn-save", on = Platform.WEB)
  private Button getSaveButton;

  @Locate(css = "#current_password", on = Platform.WEB)
  private TextBox getCurrentPasswordField;

  @Locate(css = "label[for='change_password']", on = Platform.WEB)
  private Button getChangeCurrentPasswordButton;

  @Locate(css = "#password", on = Platform.WEB)
  private TextBox getNewPasswordTextBox;

  @Locate(css = "#confirmation", on = Platform.WEB)
  private TextBox getNewPasswordConfirmationTextBox;

  /* -------- Actions -------- */

  /**
   * Enter First Name
   *
   * @param firstName
   */
  public void enterFirstName(String firstName) {
    logger.info("Entering First Name");
    getFirstNameField.clearText();
    getFirstNameField.sendKeys(firstName);
  }

  /**
   * Enter Email
   *
   * @param email
   */
  public void enterEmail(String email) {
    logger.info("Entering Email");
    getEmailField.clearText();
    getEmailField.sendKeys(email);
  }

  /**
   * Enter Email By Browser
   *
   * @param email
   * @param safariEmail
   */
  public void enterEmailByBrowser(String email, String safariEmail) {
    logger.info("Entering Email");
    if (EnvironmentHelper.isSafari(DriverManager.getDriver())) {
      enterEmail(safariEmail);
    } else {
      enterEmail(email);
    }
  }

  /**
   * Enter Password
   *
   * @param password
   */
  public void enterCurrentPassword(String password) {
    logger.info("Entering Current Password");
    getCurrentPasswordField.sendKeys(password);
  }

  /**
   * Click Save Button
   *
   * @return MyAccountPage
   */
  public MyAccountPage clickSave() {
    logger.info("Clicking Save");
    getSaveButton.click();
    return ComponentFactory.create(MyAccountPage.class);
  }

  /** Change current password. */
  public void changeCurrentPassword() {
    logger.info("Check current password checkbox");
    getChangeCurrentPasswordButton.click();
  }

  /**
   * Enter new password.
   *
   * @param password the password
   */
  public void enterNewPassword(String password) {
    logger.info("Typing new password: " + password);
    getNewPasswordTextBox.sendKeys(password);
  }

  /**
   * Enter confirm password.
   *
   * @param password the password
   */
  public void enterConfirmPassword(String password) {
    logger.info("Typing confirmation password: " + password);
    getNewPasswordConfirmationTextBox.sendKeys(password);
  }
}
