package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.mobile.components.FreeDeliveryModalChunk;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;

@Implementation(is = AndroidCheckInView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = CheckInView.class, on = Platform.MOBILE_IOS)
public class CheckInView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(
      xpath =
          "(//XCUIElementTypeStaticText[(@name=\"Check In\" or @name=\"Add Value to My Peet's Card\") and @visible=\"true\"])[1]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//android.widget.TextView[@text=\"Check In\" or @text=\"Add Value to My Peet's Card\"]",
      on = Platform.MOBILE_ANDROID)
  protected Text getSignature;

  @Locate(xpath = "//XCUIElementTypeButton[@name='Add Value']", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/addValue", on = Platform.MOBILE_ANDROID)
  protected Button getAddValueButton;

  @Locate(
      xpath =
          "//XCUIElementTypeStaticText[@name=\"Your Peet’s Card Balance\"]/following-sibling::XCUIElementTypeStaticText[starts-with(@name,'$')]",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/amount", on = Platform.MOBILE_ANDROID)
  protected Text getBalanceText;

  @Locate(id = "Confirm Value", on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.wearehathway.peets.development:id/confirmChangesButton",
      on = Platform.MOBILE_ANDROID)
  protected Button getConfirmButton;

  @Locate(xpath = "//XCUIElementTypeButton[@name=\"Edit\"]", on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.wearehathway.peets.development:id/editCreditCardBtn",
      on = Platform.MOBILE_ANDROID)
  protected Button getPencilIconButton;

  @Locate(
      xpath = "//XCUIElementTypeOther/XCUIElementTypeButton[@name='%s']",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath = "//android.widget.LinearLayout/android.widget.Button[@text='%s']",
      on = Platform.MOBILE_ANDROID)
  protected Button getAmountButton;

  /* -------- Actions -------- */

  public void afterInit() {
    SdkHelper.create(FreeDeliveryModalChunk.class).dismissFreeDelivery();
    SdkHelper.getSyncHelper().wait(Until.uiElement(getSignature).present());
  }

  /** Add value. */
  public void addValue() {
    logger.info("Tap on Add Value");
    SdkHelper.getSyncHelper().sleep(5000);
    int retryCounter = 0;
    while (getAddValueButton.exists() && getAddValueButton.isDisplayed() && retryCounter++ < 5) {
      getAddValueButton.initialize();
      MobileHelper.tapByCoordinatesOnElementCenter(getAddValueButton);
      SdkHelper.getSyncHelper().sleep(5000);
    }
  }

  public <T extends BaseComponent> T addValue(Class<T> clazz) {
    logger.info("Tap on Add Value");
    addValue();
    return SdkHelper.create(clazz);
  }

  /**
   * Edit payment methods view.
   *
   * @return the payment methods view
   */
  public PaymentMethodsView edit() {
    logger.info("Tap pencil icon");
    getPencilIconButton.initialize();
    getPencilIconButton.click();
    return SdkHelper.create(PaymentMethodsView.class);
  }

  /**
   * Gets balance.
   *
   * @return the balance
   */
  public String getBalance() {
    String rawBalance = getBalanceText.getText();
    int decimalPosition = rawBalance.indexOf(".");
    return rawBalance.substring(0, decimalPosition).replace("$", "");
  }

  /**
   * Confirm peets cards view.
   *
   * @return the peets cards view
   */
  public CheckInView confirm() {
    logger.info("Tap on confirm button");
    getConfirmButton.click();

    if (SdkHelper.getEnvironmentHelper().isiOS()) {
      // need this sleep since iOS is updating view very slowly
      SdkHelper.getSyncHelper().sleep(5000);
    }

    return SdkHelper.create(CheckInView.class);
  }
}

class AndroidCheckInView extends CheckInView {

  @Override
  public void afterInit() {
    if (getLovePeetsYesButton.exists()) getLovePeetsYesButton.click();
    if (getReviewNoThanksButton.exists()) getReviewNoThanksButton.click();
    SdkHelper.getSyncHelper().wait(Until.uiElement(getSignature).present());
  }

  public String getBalance() {
    return getBalanceText.getText();
  }

  @Locate(id = "com.wearehathway.peets.development:id/yes", on = Platform.MOBILE_ANDROID)
  protected Button getLovePeetsYesButton;

  @Locate(id = "com.wearehathway.peets.development:id/decline", on = Platform.MOBILE_ANDROID)
  protected Button getReviewNoThanksButton;
}
