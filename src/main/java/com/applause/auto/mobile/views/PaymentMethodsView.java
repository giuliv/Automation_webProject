package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.helper.SyncHelper;

@Implementation(is = AndroidPaymentMethodsView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = PaymentMethodsView.class, on = Platform.MOBILE_IOS)
public class PaymentMethodsView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(xpath = "//*[contains(@name,\"Payment Method\")][1]", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/title", on = Platform.MOBILE_ANDROID)
  protected Text getViewSignature;

  @Locate(id = "button back", on = Platform.MOBILE_IOS)
  @Locate(
      xpath = "//android.widget.ImageButton[@content-desc=\"Navigate up\"]",
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
      xpath = "//XCUIElementTypeStaticText[@name=\"Saved Payment Methods\"]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath = "//android.widget.TextView[@text='Saved Payment Methods']",
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

  @Locate(
      xpath = "(//XCUIElementTypeStaticText[@name=\"Add New Payment\"])[last()]",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/addCardText", on = Platform.MOBILE_ANDROID)
  protected Button getAddNewPaymentButton;

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
    return getSavedPaymentMethodHeaderText.getText();
  }

  /**
   * Click Peets Card
   *
   * @return PeetsCardSettingsView
   */
  public PeetsCardSettingsView clickPeetsCard() {
    logger.info("Clicking Peets Card");
    getPeetsCard.click();
    return ComponentFactory.create(PeetsCardSettingsView.class);
  }

  /** Click the Back Button */
  public void clickBackButton() {
    logger.info("Clicking the back button");
    getBackButton.click();
    SyncHelper.sleep(5000);
  }

  /**
   * Click Payment Method
   *
   * @return CreditCardDetailsView
   */
  public <T extends BaseComponent> T clickSavedPaymentMethod(Class<T> clazz, String methodName) {
    logger.info("Clicking Payment Method");
    getSavedPaymentMethodButton.format(methodName);
    getSavedPaymentMethodButton.click();
    return ComponentFactory.create(clazz);
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
    return ComponentFactory.create(clazz);
  }

  /**
   * Click Add New Payment Button
   *
   * @return AddNewCardView
   */
  public AddNewCardView clickAddNewPayment() {
    logger.info("Clicking Add New Payment");
    getAddNewPaymentButton.click();
    return ComponentFactory.create(AddNewCardView.class);
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
      return getSavedPaymentMethodButton.isDisplayed();
    } catch (Throwable throwable) {
      logger.error("Test Card is not displayed");
      return false;
    }
  }
}

class AndroidPaymentMethodsView extends PaymentMethodsView {

  /* -------- Actions -------- */

  public <T extends BaseComponent> T clickSavedPaymentMethod2(Class<T> clazz) {
    logger.info("Selecting Saved card");
    getSavedPaymentMethod2Button.click();
    getSaveChangesButton.click();
    return ComponentFactory.create(clazz);
  }
}
