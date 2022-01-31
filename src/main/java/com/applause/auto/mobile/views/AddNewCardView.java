package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;

@Implementation(is = AddNewCardView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = IosAddNewCardView.class, on = Platform.MOBILE_IOS)
public class AddNewCardView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(xpath = "//XCUIElementTypeOther[@name=\"Add New Card\"]", on = Platform.MOBILE_IOS)
  @Locate(xpath = "//android.widget.TextView[@text='Add New Card']", on = Platform.MOBILE_ANDROID)
  protected Text getViewSignature;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeTextField[`value == 'Card Number'`]",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/cardNumber", on = Platform.MOBILE_ANDROID)
  protected TextBox getCardNumberTextBox;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeTextField[`value == 'MM/YY'`]",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/expiryDate", on = Platform.MOBILE_ANDROID)
  protected TextBox getExpDateTextBox;

  @Locate(iOSClassChain = "**/XCUIElementTypeTextField[`value == 'CVV'`]", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/cvv", on = Platform.MOBILE_ANDROID)
  protected TextBox getCvvTextBox;

  @Locate(iOSClassChain = "**/XCUIElementTypeTextField[`value == 'Zip'`]", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/zip", on = Platform.MOBILE_ANDROID)
  protected TextBox getZipTextBox;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeTextField[`value == 'Card Name'`]",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/cardName", on = Platform.MOBILE_ANDROID)
  protected TextBox getCardNameTextBox;

  @Locate(iOSClassChain = "**/XCUIElementTypeSwitch", on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.wearehathway.peets.development:id/isDefaultSwitch",
      on = Platform.MOBILE_ANDROID)
  protected Button getDefaultToggle;

  @Locate(id = "Save Card", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/saveCardButton", on = Platform.MOBILE_ANDROID)
  protected Button getSaveCardButton;

  /* -------- Actions -------- */

  /**
   * Add new card payment methods view.
   *
   * @param ccNumber the cc number
   * @param ccSecurityCode the cc security code
   * @param ccNameOnCard the cc name on card
   * @param ccExpDate the cc exp date
   * @param ccZip the cc zip
   * @return the payment methods view
   */
  public PaymentMethodsView addNewCard(
      String ccNumber,
      String ccSecurityCode,
      String ccNameOnCard,
      String ccExpDate,
      String ccZip,
      boolean setAsDefault) {
    enterCardNumber(ccNumber);
    enterExpDate(ccExpDate);
    enterCvvCode(ccSecurityCode);
    enterZipCode(ccZip);
    enterCardName(ccNameOnCard);
    if (setAsDefault) {
      selectMakeDefault();
    }
    return saveCard();
  }

  /**
   * Enter Card Number
   *
   * @param cardNum
   */
  public void enterCardNumber(String cardNum) {
    logger.info("Entering Card Number");
    getCardNumberTextBox.sendKeys(cardNum);
  }

  /**
   * Enter Expiration Date
   *
   * @param expDate
   */
  public void enterExpDate(String expDate) {
    logger.info("Entering Exp Date");
    getExpDateTextBox.sendKeys(expDate);
  }

  /**
   * Enter CVV Code
   *
   * @param cvv
   */
  public void enterCvvCode(String cvv) {
    logger.info("Entering CVV Code");
    getCvvTextBox.sendKeys(cvv);
  }

  /**
   * Enter Zip Code
   *
   * @param zipCode
   */
  public void enterZipCode(String zipCode) {
    logger.info("Entering Zip Code");
    getZipTextBox.sendKeys(zipCode);
  }

  /**
   * Enter Card Name
   *
   * @param cardName
   */
  public void enterCardName(String cardName) {
    logger.info("Entering Card Name");
    getCardNameTextBox.sendKeys(cardName);
  }

  /** Make Payment Method Default */
  public void selectMakeDefault() {
    logger.info("Making Card Default");
    SdkHelper.getDeviceControl().hideKeyboard();
    getDefaultToggle.click();
  }

  /**
   * Save Payment Method
   *
   * @return PaymentMethodsView
   */
  public PaymentMethodsView saveCard() {
    logger.info("Saving Payment Method");
    getSaveCardButton.click();
    SdkHelper.getSyncHelper().sleep(15000);
    return SdkHelper.create(PaymentMethodsView.class);
  }
}

class IosAddNewCardView extends AddNewCardView {

  /* -------- Elements -------- */

  @Locate(id = "Done", on = Platform.MOBILE_IOS)
  protected Button getKeyboardDoneButton;

  /* -------- Actions -------- */

  public void selectMakeDefault() {
    logger.info("Making Card Default");
    getKeyboardDoneButton.click();
    getDefaultToggle.click();
  }

  public PaymentMethodsView saveCard() {
    logger.info("Saving Payment Method");
    getSaveCardButton.click();
    SdkHelper.getSyncHelper().sleep(15000);
    return SdkHelper.create(PaymentMethodsView.class);
  }
}
