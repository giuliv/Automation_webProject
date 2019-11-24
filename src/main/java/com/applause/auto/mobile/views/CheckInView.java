package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.mobile.components.BottomNavigationMenuChunk;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.helper.SyncHelper;
import com.applause.auto.util.helper.sync.Until;

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

  @Locate(id = "Add Value", on = Platform.MOBILE_IOS)
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

  @Locate(id = "button edit pen", on = Platform.MOBILE_IOS)
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

  /** Add value. */
  public void addValue() {
    logger.info("Tap on Add Value");
    getAddValueButton.click();
  }

  /**
   * Edit payment methods view.
   *
   * @return the payment methods view
   */
  public PaymentMethodsView edit() {
    logger.info("Tap pencil icon");
    getPencilIconButton.click();
    return ComponentFactory.create(PaymentMethodsView.class);
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
   * Is amount selected boolean.
   *
   * @param amount the amount
   * @return the boolean
   */
  public boolean isAmountSelected(String amount) {
    getAmountButton.initializeWithFormat(amount);
    return (getAmountButton.getAttributeValue("value") != null);
  }

  /**
   * Confirm peets cards view.
   *
   * @return the peets cards view
   */
  public CheckInView confirm() {
    logger.info("Tap on confirm button");
    getConfirmButton.click();
    return ComponentFactory.create(CheckInView.class);
  }

  /**
   * Gets bottom navigation menu.
   *
   * @return the bottom navigation menu
   */
  public BottomNavigationMenuChunk getBottomNavigationMenu() {
    return ComponentFactory.create(BottomNavigationMenuChunk.class);
  }
}

class AndroidCheckInView extends CheckInView {

  @Override
  public void afterInit() {
    if (getLovePeetsYesButton.exists()) getLovePeetsYesButton.click();
    if (getReviewNoThanksButton.exists()) getReviewNoThanksButton.click();
    SyncHelper.wait(Until.uiElement(getSignature).present());
  }

  @Override
  public boolean isAmountSelected(String amount) {
    getAmountButton.initializeWithFormat(amount);
    return getAmountButton.getMobileElement().isSelected();
  }

  public String getBalance() {
    return getBalanceText.getText();
  }

  @Locate(id = "com.wearehathway.peets.development:id/yes", on = Platform.MOBILE_ANDROID)
  protected Button getLovePeetsYesButton;

  @Locate(id = "com.wearehathway.peets.development:id/decline", on = Platform.MOBILE_ANDROID)
  protected Button getReviewNoThanksButton;
}
