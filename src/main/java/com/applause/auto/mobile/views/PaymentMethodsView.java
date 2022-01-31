package com.applause.auto.mobile.views;

import com.applause.auto.common.data.Constants.TestData;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Image;
import com.applause.auto.pageobjectmodel.elements.Text;
import io.qameta.allure.Step;
import java.time.Duration;

@Implementation(is = AndroidPaymentMethodsView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = PaymentMethodsView.class, on = Platform.MOBILE_IOS)
public class PaymentMethodsView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(xpath = "//*[contains(@name,\"Payment Methods\")][1]", on = Platform.MOBILE_IOS)
  @Locate(
      xpath = "//android.widget.TextView[@content-desc='Payment Methods']",
      on = Platform.MOBILE_ANDROID)
  protected Text getViewSignature;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeNavigationBar/XCUIElementTypeButton",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//android.widget.ImageButton[contains(@content-desc,\"Navigate up\") or contains(@content-desc,\"Nach oben\")]",
      on = Platform.MOBILE_ANDROID)
  protected Button getBackButton;

  @Locate(
      xpath = "//XCUIElementTypeStaticText[@name=\"Your Peet's Card\"]",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/sectionHeading\")",
      on = Platform.MOBILE_ANDROID)
  protected Text getPeetsCardHeaderText;

  @Locate(id = "Peet's Card", on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/creditCardView\")",
      on = Platform.MOBILE_ANDROID)
  protected Button getPeetsCard;

  @Locate(
      xpath = "//XCUIElementTypeNavigationBar//XCUIElementTypeStaticText[@value='PAYMENT METHODS']",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath = "//android.widget.TextView[@content-desc='Payment Methods']",
      on = Platform.MOBILE_ANDROID)
  protected Text getSavedPaymentMethodHeaderText;

  @Locate(xpath = "//XCUIElementTypeTable/XCUIElementTypeCell[1]", on = Platform.MOBILE_IOS)
  @Locate(
      xpath = "//*[contains(@resource-id, 'id/creditCardView')][1]",
      on = Platform.MOBILE_ANDROID)
  protected Button getSavedPaymentMethod2Button;

  @Locate(xpath = "//*[contains(@name,'%s')]", on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//android.widget.TextView[contains(@resource-id, 'id/cardName') and contains(@text,'%s')]",
      on = Platform.MOBILE_ANDROID)
  protected Button getSavedPaymentMethodButton;

  @Locate(id = "", on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.wearehathway.peets.development:id/saveChangesButton",
      on = Platform.MOBILE_ANDROID)
  protected Button getSaveChangesButton;

  // @Locate(
  // xpath = "(//XCUIElementTypeStaticText[@name=\"Add New Payment\"])[last()]",
  // on = Platform.MOBILE_IOS) //Commented[15.01.2021]
  @Locate(
      iOSClassChain = "**/XCUIElementTypeStaticText[`label == \"Add New Payment\"`][-1]",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/addPaymentView", on = Platform.MOBILE_ANDROID)
  protected Button getAddNewPaymentButton;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeStaticText[`label == \"Peet's Card\"`]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//android.widget.RelativeLayout[contains(@content-desc,\"Peet's Card\")]//android.widget.ImageView[contains(@resource-id,\"cardImage\")]",
      on = Platform.MOBILE_ANDROID)
  protected Button getPeetsCardImage;

  @Locate(
      xpath = "//XCUIElementTypeStaticText[@label=\"Peet's Card\"]/../XCUIElementTypeStaticText[2]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//android.widget.RelativeLayout[contains(@content-desc,\"Peet's Card\")]//android.widget.TextView[contains(@resource-id,\"cardNumber\")]",
      on = Platform.MOBILE_ANDROID)
  protected Button getPeetsCardNumber;

  @Locate(xpath = "//XCUIElementTypeImage[@value=\"Logo image\"]", on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//android.widget.RelativeLayout[contains(@content-desc,\"Visa card\")]//android.widget.ImageView[contains(@resource-id,\"cardImage\")]",
      on = Platform.MOBILE_ANDROID)
  protected Image getSavedPaymentCardImage;

  @Locate(
      xpath = "//XCUIElementTypeStaticText[contains(@label,\"**** \")]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//android.widget.RelativeLayout[contains(@content-desc,\"Visa card\")]//android.widget.TextView[contains(@resource-id,\"cardNumber\")]",
      on = Platform.MOBILE_ANDROID)
  protected Text getSavedPaymentCardNumber;

  /* -------- Actions -------- */

  /**
   * Check for Peets Card Header
   *
   * @return String
   */
  public String getPeetsCardHeader() {
    logger.info("Checking for Peets Card Header");
    return getPeetsCardHeaderText.getText();
  }

  /**
   * Check for Saved Payment Method Header
   *
   * @return String
   */
  public String getSavedPaymentHeader() {
    logger.info("Getting Saved Payment Method Header");
    return getSavedPaymentMethodHeaderText.getText().toLowerCase();
  }

  /**
   * Click Peets Card
   *
   * @return PeetsCardSettingsView
   */
  public PeetsCardSettingsView clickPeetsCard() {
    logger.info("Clicking Peets Card");
    getPeetsCard.click();
    return SdkHelper.create(PeetsCardSettingsView.class);
  }

  /** Click the Back Button */
  public void clickBackButtonTwiceOnIos() {
    logger.info("Clicking the back button");
    SdkHelper.getDeviceControl().tapElementCenter(getBackButton);
    SdkHelper.getDeviceControl().tapElementCenter(getBackButton);
    SdkHelper.getSyncHelper().sleep(5000);
  }

  public void clickBackButton() {
    logger.info("Clicking the back button");
    SdkHelper.getDeviceControl().tapElementCenter(getBackButton);
    SdkHelper.getSyncHelper().sleep(5000);
  }

  /**
   * Click Payment Method
   *
   * @return CreditCardDetailsView
   */
  public <T extends BaseComponent> T clickSavedPaymentMethod(Class<T> clazz, String methodName) {
    logger.info("Clicking Payment Method");
    getSavedPaymentMethodButton.format(methodName).initialize();
    getSavedPaymentMethodButton.click();
    return SdkHelper.create(clazz);
  }

  /**
   * Click saved payment method 2 t.
   *
   * @param <T> the type parameter
   * @param clazz the clazz
   * @return the t
   */
  public <T extends BaseComponent> T clickSavedPaymentMethod2(Class<T> clazz) {
    logger.info("Clicking Payment Method");
    getSavedPaymentMethod2Button.click();
    return SdkHelper.create(clazz);
  }

  /**
   * Click Payment Method and Save Changes
   *
   * @return CreditCardDetailsView
   */
  public <T extends BaseComponent> T clickSavedPaymentMethodAndSaveChanges(
      Class<T> clazz, String methodName) {
    clickSavedPaymentMethod(clazz, methodName);
    return SdkHelper.create(clazz);
  }

  /**
   * Click Add New Payment Button
   *
   * @return AddNewCardView
   */
  public AddNewCardView clickAddNewPayment() {
    logger.info("Clicking Add New Payment");
    SdkHelper.getSyncHelper().sleep(5000);
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(getAddNewPaymentButton).visible().setTimeout(Duration.ofSeconds(40)));
    getAddNewPaymentButton.click();
    return SdkHelper.create(AddNewCardView.class);
  }

  /**
   * Check whether test card is added
   *
   * @return boolean
   */
  public boolean isPaymentMethodTestCardAdded(String method) {
    logger.info("Checking Test Card is added");
    try {
      getSavedPaymentMethodButton.format(method);
      return MobileHelper.isDisplayed(getSavedPaymentMethodButton);
    } catch (Throwable throwable) {
      logger.error("Test Card is not displayed");
      return false;
    }
  }

  @Step("Verify PAYMENT METHODS header is displayed")
  public boolean isHeaderDisplayed() {
    return MobileHelper.isDisplayed(getViewSignature);
  }

  @Step("Verify Back button is displayed")
  public boolean isBackButtonDisplayed() {
    return MobileHelper.isDisplayed(getBackButton);
  }

  @Step("Verify (+) Add new payment  button is displayed")
  public boolean isAddNewPaymentButtonDisplayed() {
    return MobileHelper.isDisplayed(getAddNewPaymentButton);
  }

  @Step("Verify Peet's card image and card number is displayed")
  public boolean isPeetsCardDisplayed() {
    logger.info("Checking Peet's Card is displayed with image and number");
    if (!MobileHelper.isDisplayed(getPeetsCardImage)) {
      logger.error("Peet's Card image is not displayed");
      return false;
    }

    if (!MobileHelper.isDisplayed(getPeetsCardNumber)) {
      logger.error("Peet's Card number is not displayed");
      return false;
    }

    return true;
  }

  @Step("Verify Saved payment card image and **** **** **** xxxx number is displayed")
  public boolean isSavedPaymentCardDisplayed(String cardNumber) {
    logger.info("Checking Saved payment card image and **** **** **** xxxx number is displayed");
    SdkHelper.getSyncHelper().sleep(6000);
    System.out.println(SdkHelper.getDriver().getPageSource());
    if (!MobileHelper.isDisplayed(getSavedPaymentCardImage)) {
      logger.error("Saved payment card image is not displayed");
      return false;
    }

    if (!MobileHelper.isDisplayed(getSavedPaymentCardNumber)) {
      logger.error("Saved payment card number is not displayed");
      return false;
    }

    String actualCardNumber = getSavedPaymentCardNumber.getText();
    String expectedCardNumber =
        String.format(
            TestData.HIDDEN_CREDIT_CARD_NUMBER_TEMPLATE,
            cardNumber.substring(cardNumber.length() - 4));
    if (!actualCardNumber.equals(expectedCardNumber)) {
      logger.error(
          "Saved payment card number is not displayed properly. Expected [%s]. Actual [%s]",
          expectedCardNumber, actualCardNumber);
      return false;
    }

    return true;
  }
}

class AndroidPaymentMethodsView extends PaymentMethodsView {

  /* -------- Actions -------- */

  public <T extends BaseComponent> T clickSavedPaymentMethod2(Class<T> clazz) {
    logger.info("Selecting Saved card");
    getSavedPaymentMethod2Button.click();
    getSaveChangesButton.click();
    return SdkHelper.create(clazz);
  }

  public void clickBackButtonTwiceOnIos() {
    logger.info("Click back button once on android");
    getBackButton.click();
    SdkHelper.getSyncHelper().sleep(5000);
  }

  public void clickBackButton() {
    logger.info("Does not click back button on andorid");
  }

  /**
   * Click Payment Method and Save Changes
   *
   * @return CreditCardDetailsView
   */
  public <T extends BaseComponent> T clickSavedPaymentMethodAndSaveChanges(
      Class<T> clazz, String methodName) {
    clickSavedPaymentMethod(clazz, methodName);
    getSaveChangesButton.click();
    return SdkHelper.create(clazz);
  }

  public AddNewCardView clickAddNewPayment() {
    logger.info("Clicking Add New Payment");
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(getAddNewPaymentButton).visible().setTimeout(Duration.ofSeconds(40)));
    getAddNewPaymentButton.click();
    return SdkHelper.create(AddNewCardView.class);
  }
}
