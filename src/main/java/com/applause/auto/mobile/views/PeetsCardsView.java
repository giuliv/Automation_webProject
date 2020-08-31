package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.mobile.components.BottomNavigationMenuChunk;
import com.applause.auto.mobile.components.PeetsCardsTransferAmountChunk;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.helper.SyncHelper;
import com.applause.auto.util.helper.sync.Until;

import java.time.Duration;

@Implementation(is = AndroidPeetsCardsView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = PeetsCardsView.class, on = Platform.MOBILE_IOS)
public class PeetsCardsView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(
      xpath =
          "(//XCUIElementTypeStaticText[(@name=\"Peet's Card\" or @name=\"Add Value to My Peet's Card\") and @visible=\"true\"])[1]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//android.widget.TextView[@text=\"Peet's Card\" or @text=\"Add Value to My Peet's Card\"][1]",
      on = Platform.MOBILE_ANDROID)
  protected Text getSignature;

  @Locate(id = "Add Value", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/addValue", on = Platform.MOBILE_ANDROID)
  protected Button getAddValueButton;

  @Locate(id = "Transfer Value", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/transferValue", on = Platform.MOBILE_ANDROID)
  protected Button getTransferButton;

  @Locate(
      xpath =
          "//XCUIElementTypeStaticText[@name=\"$\"]/following-sibling::XCUIElementTypeStaticText[1]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//android.widget.TextSwitcher[@resource-id='com.wearehathway.peets.development:id/textSwitcherInteger']/android.widget.TextView",
      on = Platform.MOBILE_ANDROID)
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

  public String getBalance() {
    SyncHelper.wait(Until.uiElement(getBalanceText).visible().setTimeout(Duration.ofSeconds(30)));
    return getBalanceText.getText();
  }

  /**
   * Is amount selected boolean.
   *
   * @param amount the amount
   * @return the boolean
   */
  public boolean isAmountSelected(String amount) {
    getAmountButton.initializeWithFormat(amount);
    return getAmountButton.getAttributeValue("value") != null;
  }

  /**
   * Confirm peets cards view.
   *
   * @return the peets cards view
   */
  public PeetsCardsView confirm() {
    logger.info("Tap on confirm button");
    SyncHelper.wait(
        Until.uiElement(getConfirmButton).clickable().setTimeout(Duration.ofSeconds(30)));
    getConfirmButton.click();
    return ComponentFactory.create(PeetsCardsView.class);
  }

  /**
   * Gets bottom navigation menu.
   *
   * @return the bottom navigation menu
   */
  public BottomNavigationMenuChunk getBottomNavigationMenu() {
    return ComponentFactory.create(BottomNavigationMenuChunk.class);
  }

  public PeetsCardsTransferAmountChunk transferValue() {
    logger.info("Tap on transfer value button");
    getTransferButton.click();
    return ComponentFactory.create(PeetsCardsTransferAmountChunk.class);
  }
}

class AndroidPeetsCardsView extends PeetsCardsView {
  @Override
  public boolean isAmountSelected(String amount) {
    getAmountButton.initializeWithFormat(amount);
    return getAmountButton.getMobileElement().isSelected();
  }
}
