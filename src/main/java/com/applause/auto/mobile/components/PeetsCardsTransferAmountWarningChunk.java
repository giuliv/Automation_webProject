package com.applause.auto.mobile.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;

@Implementation(is = PeetsCardsTransferAmountWarningChunk.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = IOSPeetsCardsTransferAmountWarningChunk.class, on = Platform.MOBILE_IOS)
public class PeetsCardsTransferAmountWarningChunk extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(id = "One last thing", on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.TextView[1]",
      on = Platform.MOBILE_ANDROID)
  protected Text getMainTitleText;

  @Locate(id = "When you transfer a card into the app, you will:", on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.TextView[2]",
      on = Platform.MOBILE_ANDROID)
  protected Text getImageAltTextText;

  @Locate(
      xpath =
          "(//XCUIElementTypeStaticText[@name=\"One last thing\"]/following-sibling::XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeStaticText)[1]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView[1]",
      on = Platform.MOBILE_ANDROID)
  protected Text getMessageText1Text;

  @Locate(
      xpath =
          "(//XCUIElementTypeStaticText[@name=\"One last thing\"]/following-sibling::XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeStaticText)[2]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView[2]",
      on = Platform.MOBILE_ANDROID)
  protected Text getMessageText2Text;

  @Locate(
      xpath =
          "(//XCUIElementTypeStaticText[@name=\"One last thing\"]/following-sibling::XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeStaticText)[3]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView[3]",
      on = Platform.MOBILE_ANDROID)
  protected Text getMessageText3Text;

  @Locate(id = "We couldn’t process your transfer.", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/textView3", on = Platform.MOBILE_ANDROID)
  protected Text getMainTitleCouldNotProcessText;

  @Locate(
      xpath =
          "(//XCUIElementTypeStaticText[@name=\"We couldn’t process your transfer.\"]/following-sibling::XCUIElementTypeOther//XCUIElementTypeStaticText)[1]",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/textView5", on = Platform.MOBILE_ANDROID)
  protected Text getMessageText1CouldNotProcessText;

  @Locate(
      xpath =
          "(//XCUIElementTypeStaticText[@name=\"We couldn’t process your transfer.\"]/following-sibling::XCUIElementTypeOther//XCUIElementTypeStaticText)[2]",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/textView6", on = Platform.MOBILE_ANDROID)
  protected Text getMessageText2CouldNotProcessText;

  @Locate(
      xpath =
          "(//XCUIElementTypeStaticText[@name=\"We couldn’t process your transfer.\"]/following-sibling::XCUIElementTypeOther//XCUIElementTypeStaticText)[3]",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/textView7", on = Platform.MOBILE_ANDROID)
  protected Text getMessageText3CouldNotProcessText;

  @Locate(id = "Continue", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/continueButton", on = Platform.MOBILE_ANDROID)
  protected Button getContinueButton;

  @Locate(id = "Cancel", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/cancelButton", on = Platform.MOBILE_ANDROID)
  protected Button getCancelCouldNotProcessButton;

  @Locate(id = "Try Again", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/tryAgainButton", on = Platform.MOBILE_ANDROID)
  protected Button getTryAgainCouldNotProcessButton;

  @Locate(id = "Cancel", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/cancel", on = Platform.MOBILE_ANDROID)
  protected Button getCancelButton;

  @Locate(id = "One last thing", on = Platform.MOBILE_IOS)
  @Locate(xpath = "//android.widget.TextView[@text='One last thing']", on = Platform.MOBILE_ANDROID)
  protected ContainerElement getSignature;

  /* -------- Actions -------- */

  /**
   * Gets formatted message.
   *
   * @return the formatted message
   */
  public String getFormattedMessage() {
    return String.format(
        "%s %s %s %s %s",
        getMainTitleText.getText(),
        getImageAltTextText.getText(),
        getMessageText1Text.getText(),
        getMessageText2Text.getText(),
        getMessageText3Text.getText());
  }

  /**
   * Gets valid message.
   *
   * @return the valid message
   */
  public String getValidMessage() {
    return "One last thing When you transfer a card into the app, you will: Not be able to transfer the value back to the original card No longer be able to add funds to your physical card Be able to access the new value with your digital Peet's Card located in the app";
  }

  /**
   * Gets formatted message could not process.
   *
   * @return the formatted message could not process
   */
  public String getFormattedMessageCouldNotProcess() {
    return String.format(
        "%s %s %s %s",
        getMainTitleCouldNotProcessText.getText(),
        getMessageText1CouldNotProcessText.getText(),
        getMessageText2CouldNotProcessText.getText(),
        getMessageText3CouldNotProcessText.getText());
  }

  /**
   * Gets valid message could not process.
   *
   * @return the valid message could not process
   */
  public String getValidMessageCouldNotProcess() {
    return "We couldn't process your transfer Please check your card number and pin code and try again. If there's no value remaining on the card you are trying to transfer, you won't be able to transfer value. If this issue persist, please contact Peet's customer serviceat cs@peets.com.";
  }

  /**
   * Is continue button displayed boolean.
   *
   * @return the boolean
   */
  public boolean isContinueButtonDisplayed() {
    return getContinueButton.isDisplayed();
  }

  /**
   * Is try again button could not process displayed boolean.
   *
   * @return the boolean
   */
  public boolean isTryAgainButtonCouldNotProcessDisplayed() {
    return getTryAgainCouldNotProcessButton.isDisplayed();
  }

  /**
   * Is cancel button could not process displayed boolean.
   *
   * @return the boolean
   */
  public boolean isCancelButtonCouldNotProcessDisplayed() {
    return getCancelCouldNotProcessButton.isDisplayed();
  }

  /**
   * Is cancel button displayed boolean.
   *
   * @return the boolean
   */
  public boolean isCancelButtonDisplayed() {
    return getCancelButton.isDisplayed();
  }

  /** Tap continue. */
  public void tapContinue() {
    logger.info("Tap Continue button");
    getContinueButton.click();
  }

  /** Tap try again. */
  public <T extends BaseComponent> T tapTryAgain(Class<T> clazz) {
    logger.info("Tap Try again button");
    getTryAgainCouldNotProcessButton.click();
    return this.create(clazz);
  }
}

class IOSPeetsCardsTransferAmountWarningChunk extends PeetsCardsTransferAmountWarningChunk {

  /* -------- Actions -------- */

  public String getValidMessage() {
    return "One last thing When you transfer a card into the app, you will: Not be able to transfer the value back to the original card No longer be able to add funds to your physical card Be able to access the new value with your digital Peet’s Card located in the app.";
  }

  public String getValidMessageCouldNotProcess() {
    return "We couldn’t process your transfer. Please check your card number and pin code and try again. If there's no value remaining on the card you are trying to transfer, you won't be able to transfer value. If this issue persists, please contact Peet's customer service at cs@peets.com.";
  }
}
