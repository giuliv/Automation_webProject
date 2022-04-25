package com.applause.auto.mobile.views;

import com.applause.auto.common.data.Constants.TestData;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Image;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;

import io.qameta.allure.Step;

@Implementation(is = CreditCardDetailsView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = IosCreditCardDetailsView.class, on = Platform.MOBILE_IOS)
public class CreditCardDetailsView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(
      iOSClassChain = "**/XCUIElementTypeButton[`label == \"Save Card\"`]",
      on = Platform.MOBILE_IOS)
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

  @Locate(
      iOSClassChain = "**/XCUIElementTypeStaticText[`label == \"Set as Default Payment\"`]",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/defaultText", on = Platform.MOBILE_ANDROID)
  protected Text getDefaultCardText;

  @Locate(iOSClassChain = "**/XCUIElementTypeTextField", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/expiry", on = Platform.MOBILE_ANDROID)
  protected TextBox getExpDateField;

  @Locate(
      id =
          "//XCUIElementTypeButton[@name=\"Delete Card\"]/preceding-sibling::XCUIElementTypeTextField",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/expiry", on = Platform.MOBILE_ANDROID)
  protected Text getExpDateFieldForText;

  @Locate(xpath = "//XCUIElementTypeButton[@name=\"Delete Card\"]", on = Platform.MOBILE_IOS)
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

  @Locate(
      iOSClassChain = "**/XCUIElementTypeButton[`label == \"button back\"`]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//android.widget.ImageButton[contains(@content-desc,\"Navigate up\") or contains(@content-desc,\"Nach oben\")]",
      on = Platform.MOBILE_ANDROID)
  protected Button getBackButton;

  @Locate(id = "Done", on = Platform.MOBILE_IOS)
  protected Button getKeyboardDoneButton;

  @Locate(xpath = "//XCUIElementTypeImage[@value=\"Logo image\"]", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/cardImage", on = Platform.MOBILE_ANDROID)
  protected Image getCCLogoImage;

  @Locate(
      xpath = "//XCUIElementTypeStaticText[contains(@label,\"**** \")]",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/cardNumber", on = Platform.MOBILE_ANDROID)
  protected Text getCCLogoNumber;

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
  @Step("Verify efault Card text is displayed")
  public Boolean isDefaultCardTextPresent() {
    logger.info("Checking for Default Card text");
    return MobileHelper.isDisplayed(getDefaultCardText);
  }

  /**
   * Update Exp Date
   *
   * @param expDate
   */
  public void enterExpDate(String expDate) {
    logger.info("Entering Expiration Date [{}]", expDate);
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
    getExpDateFieldForText.initialize();
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

  @Step("Verify CC logo image is displayed")
  public boolean isCCLogoImageDisplayed() {
    logger.info("Checking CC logo image is displayed");
    return MobileHelper.isDisplayed(getCCLogoImage);
  }

  @Step("Verify CC number is displayed")
  public boolean isCCNumberDisplayed(String ccNumber) {
    if (!MobileHelper.isDisplayed(getCCLogoNumber)) {
      logger.error("CC number is not displayed");
      return false;
    }

    String actualCardNumber = getCCLogoNumber.getText();
    String expectedCardNumber =
        String.format(
            TestData.HIDDEN_CREDIT_CARD_NUMBER_TEMPLATE, ccNumber.substring(ccNumber.length() - 4));
    if (!actualCardNumber.equals(expectedCardNumber)) {
      logger.error(
          "CC number is not displayed properly. Expected [%s]. Actual [%s]",
          expectedCardNumber, actualCardNumber);
      return false;
    }
    return true;
  }

  @Step("Verify Expiration date is displayed")
  public boolean isExpDateDisplayed() {
    return MobileHelper.isDisplayed(getExpDateField);
  }

  @Step("Verify Delete card link is displayed")
  public boolean isDeleteCardLinkIsDisplayed() {
    return MobileHelper.isDisplayed(getDeleteButton);
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
    return SdkHelper.create(PaymentMethodsView.class);
  }

  @Override
  public String getExpDate() {
    getExpDateField.initialize();
    return getExpDateField.getAttributeValue("value");
  }
}
