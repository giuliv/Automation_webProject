package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.control.DeviceControl;
import com.applause.auto.util.helper.SyncHelper;
import com.applause.auto.util.helper.sync.Until;

import java.time.Duration;

@Implementation(is = CreditCardDetailsView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = IosCreditCardDetailsView.class, on = Platform.MOBILE_IOS)
public class CreditCardDetailsView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(id = "Save Card", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/saveCardButton", on = Platform.MOBILE_ANDROID)
  protected Button getSaveCardButton;

  @Locate(iOSNsPredicate = "name='MOBILE TEST'", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/title", on = Platform.MOBILE_ANDROID)
  protected Text getHeaderText;

  @Locate(xpath = "//XCUIElementTypeStaticText[@name=\"MOBILE TEST\"]", on = Platform.MOBILE_IOS)
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
    DeviceControl.hideKeyboard();
    getSaveCardButton.click();
    return ComponentFactory.create(PaymentMethodsView.class);
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
    return ComponentFactory.create(CreditCardDetailsView.class);
  }

  /**
   * Confirm Deleting Payment Method
   *
   * @return PaymentMethodsView
   */
  public PaymentMethodsView clickDeleteYes() {
    logger.info("Confirming Deletion of Card");
    getDeleteYesButton.click();
//    SyncHelper.sleep(5000);
    SyncHelper.wait(Until.uiElement(getDeleteYesButton).present().setTimeout(Duration.ofSeconds(10)));
    return ComponentFactory.create(PaymentMethodsView.class);
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
}

class IosCreditCardDetailsView extends CreditCardDetailsView {

  /* -------- Elements -------- */

  @Locate(id = "Done", on = Platform.MOBILE_IOS)
  protected Button getKeyboardDoneButton;

  @Locate(id = "button back", on = Platform.MOBILE_IOS)
  protected Button getBackButton;

  /* -------- Actions -------- */

  public PaymentMethodsView saveCard() {
    logger.info("Save Payment Method");
    getKeyboardDoneButton.click();
    getSaveCardButton.click();
    getBackButton.click();
    return ComponentFactory.create(PaymentMethodsView.class);
  }
}
