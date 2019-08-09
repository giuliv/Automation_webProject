package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;

@Implementation(is = CheckoutConfirmationPage.class, on = Platform.WEB)
public class CheckoutConfirmationPage extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(css = "h2.sub-title", on = Platform.WEB)
  private Text getPageSubtitleText;

  @Locate(css = ".default-page-text strong", on = Platform.WEB)
  private Text getOrderNumberText;

  @Locate(
      css = ".default-page-text .disc > li > a[href*='recurring_profile/view/profile']",
      on = Platform.WEB)
  private Text getSubscriptionNumberText;

  @Locate(css = "#email_address", on = Platform.WEB)
  private TextBox getCreateAccountEmailTextBox;

  @Locate(css = "#password", on = Platform.WEB)
  private TextBox getCreateAccountPasswordTextBox;

  @Locate(css = "#confirmation", on = Platform.WEB)
  private TextBox getCreateAccountConfirmPasswordTextBox;

  @Locate(css = "button[title='Create an Account']", on = Platform.WEB)
  private Button getCreateAccountButton;

  /* -------- Actions -------- */

  /**
   * Get Confirmation message
   *
   * @return String
   */
  public String getConfirmationMessage() {
    logger.info("Getting confirmation message");
    // Set to upper case as Safari shows the message Capitalized while Chrome not and this does
    // not affect the test
    return getPageSubtitleText.getText().toUpperCase();
  }

  /**
   * Get Order Number
   *
   * @return String
   */
  public String getOrderNumber() {
    logger.info("Getting order number");
    return getOrderNumberText.getText();
  }

  /**
   * Enter password.
   *
   * @param password the password
   */
  public void enterPassword(String password) {
    logger.info("Entering password: " + password);
    getCreateAccountPasswordTextBox.sendKeys(password);
  }

  /**
   * Enter confirm password.
   *
   * @param password the password
   */
  public void enterConfirmPassword(String password) {
    logger.info("Entering password confirmation: " + password);
    getCreateAccountConfirmPasswordTextBox.sendKeys(password);
  }

  /**
   * Create account my account page.
   *
   * @return the my account page
   */
  public MyAccountPage createAccount() {
    logger.info("Click on Create Account button");
    getCreateAccountButton.click();
    return ComponentFactory.create(MyAccountPage.class);
  }

  /**
   * Gets subscription number.
   *
   * @return the subscription number
   */
  public String getSubscriptionNumber() {
    return getSubscriptionNumberText.getText().trim();
  }
}
