package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;

@Implementation(is = CreditCardDetailsView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = IosCreditCardDetailsView.class, on = Platform.MOBILE_IOS)
public class CreditCardDetailsView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(id = "Save Card", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/saveCardButton", on = Platform.MOBILE_ANDROID)
  protected Button getSaveCardButton;

  @Locate(
      xpath = "//XCUIElementTypeNavigationBar/XCUIElementTypeStaticText",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/title", on = Platform.MOBILE_ANDROID)
  protected Text getHeaderText;

  @Locate(xpath = "//XCUIElementTypeOther/XCUIElementTypeStaticText", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/cardName", on = Platform.MOBILE_ANDROID)
  protected Text getCCNameText;

  @Locate(id = "Set as Default Payment", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/defaultText", on = Platform.MOBILE_ANDROID)
  protected Text getDefaultCardText;

  @Locate(id = "com.wearehathway.peets.development:id/expiry", on = Platform.MOBILE_ANDROID)
  @Locate(
      xpath =
          "//XCUIElementTypeButton[@name=\"Delete Card\"]/preceding-sibling::XCUIElementTypeTextField",
      on = Platform.MOBILE_IOS)
  protected TextBox getExpDateField;

  @Locate(id = "com.wearehathway.peets.development:id/expiry", on = Platform.MOBILE_ANDROID)
  @Locate(
      xpath =
          "//XCUIElementTypeButton[@name=\"Delete Card\"]/preceding-sibling::XCUIElementTypeTextField",
      on = Platform.MOBILE_IOS)
  protected Text getExpDateFieldForText;

  @Locate(iOSNsPredicate = "name='Delete Card'", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/deleteCardBtn", on = Platform.MOBILE_ANDROID)
  protected Button getDeleteButton;

  @Locate(id = "No", on = Platform.MOBILE_IOS)
  @Locate(id = "android:id/button2", on = Platform.MOBILE_ANDROID)
  protected Button getDeleteNoButton;

  @Locate(id = "Yes", on = Platform.MOBILE_IOS)
  @Locate(id = "android:id/button1", on = Platform.MOBILE_ANDROID)
  protected Button getDeleteYesButton;

  @Locate(iOSClassChain = "**/XCUIElementTypeSwitch", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/defaultSwitch", on = Platform.MOBILE_ANDROID)
  protected Button getDefaultToggle;

  @Locate(id = "button back", on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//android.widget.ImageButton[contains(@content-desc,\"Navigate up\") or contains(@content-desc,\"Nach oben\")]",
      on = Platform.MOBILE_ANDROID)
  protected Button getBackButton;

  @Locate(id = "Done", on = Platform.MOBILE_IOS)
  protected Button getKeyboardDoneButton;

  /* -------- Actions -------- */

  /**
   * Get Header Text
   *
   * @return String
   */
  public String getHeader() {
    logger.info("Getting Header Text");
    return getHeaderText.getText().trim();
  }

  /**
   * Get Credit Card Name
   *
   * @return String
   */
  public String getCreditCardName() {
    logger.info("Getting Credit Card Name");
    return getCCNameText.getText().trim();
  }

  /**
   * Checking for Default Card text
   *
   * @return Boolean
   */
  public Boolean isDefaultCardTextPresent() {
    logger.info("Checking for Default Card text");
    return getDefaultCardText.isDisplayed();
  }

  /**
   * Update Exp Date
   *
   * @param expDate
   */
  public void enterExpDate(String expDate) {
    logger.info("Entering Expiration Date");
    getExpDateField.clearText();
    getExpDateField.sendKeys(expDate);
  }

  /**
   * Save Payment Method
   *
   * @return PaymentMethodsView
   */
  public PaymentMethodsView saveCard() {
    logger.info("Save Payment Method");
    SdkHelper.getDeviceControl().hideKeyboard();
    getSaveCardButton.click();
    SdkHelper.getSyncHelper().sleep(15000);
    return SdkHelper.create(PaymentMethodsView.class);
  }

  /** Click Delete Card */
  public void clickDeleteCard() {
    logger.info("Clicking Delete Card");
    getDeleteButton.click();
  }

  /**
   * Cancel Deleting Payment Method
   *
   * @return CreditCardDetailsView
   */
  public CreditCardDetailsView clickDeleteNo() {
    logger.info("Cancel Deleting Payment Method");
    getDeleteNoButton.click();
    return SdkHelper.create(CreditCardDetailsView.class);
  }

  /**
   * Confirm Deleting Payment Method
   *
   * @return PaymentMethodsView
   */
  public PaymentMethodsView clickDeleteYes() {
    logger.info("Confirming Deletion of Card");
    getDeleteYesButton.click();
    SdkHelper.getSyncHelper().sleep(15000);
    // SdkHelper.getSyncHelper().wait(
    // Until.uiElement(getDeleteYesButton).present().setTimeout(Duration.ofSeconds(10)));
    return SdkHelper.create(PaymentMethodsView.class);
  }

  /**
   * Get Exp Date
   *
   * @return String
   */
  public String getExpDate() {
    // TODO - investigate why getCurrentText() is not working;
    // workaround is to declare a text element for the expiration
    // date field
    // return getExpDateField.getCurrentText();
    return getExpDateFieldForText.getText();
  }

  public void setDefault() {
    logger.info("Set CC as default");
    if (!isDefaultSelected()) {
      logger.info("Click CC switch to default");
      getDefaultToggle.click();
    } else {
      logger.info("Already enabled");
    }
  }

  public boolean isDefaultSelected() {
    logger.info("Checking if default selected");
    return getDefaultToggle.getAttributeValue("text").equals("ON");
  }

  /** Click the Back Button */
  public PaymentMethodsView clickBackButton() {
    logger.info("Clicking the back button");
    getBackButton.click();
    SdkHelper.getSyncHelper().sleep(5000);
    return SdkHelper.create(PaymentMethodsView.class);
  }
}

class IosCreditCardDetailsView extends CreditCardDetailsView {

  /* -------- Elements -------- */

  @Locate(id = "Done", on = Platform.MOBILE_IOS)
  protected Button getKeyboardDoneButton;

  @Locate(id = "button back", on = Platform.MOBILE_IOS)
  protected Button getBackButton;

  /* -------- Actions -------- */

  public boolean isDefaultSelected() {
    logger.info("Checking if default selected");
    return getDefaultToggle.getAttributeValue("value").equals("1");
  }

  public PaymentMethodsView saveCard() {
    logger.info("Save Payment Method");
    try {
      getKeyboardDoneButton.click();
    } catch (Throwable th) {
      logger.info("No Done button found");
    }
    getSaveCardButton.click();
    SdkHelper.getSyncHelper().sleep(10000);
    getBackButton.click();
    SdkHelper.getSyncHelper().sleep(5000);
    return SdkHelper.create(PaymentMethodsView.class);
  }
}
