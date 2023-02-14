package com.applause.auto.mobile.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.web.helpers.WebHelper;
import io.qameta.allure.Step;

@Implementation(is = ConfirmationPopup.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = ConfirmationPopup.class, on = Platform.MOBILE_IOS)
public class ConfirmationPopup extends BaseComponent {

  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/message\")",
      on = Platform.MOBILE_ANDROID)
  @Locate(
      iOSClassChain =
          "**/XCUIElementTypeAlert/**/XCUIElementTypeScrollView[1]/**/XCUIElementTypeStaticText[2]",
      on = Platform.MOBILE_IOS)
  protected Text message;

  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/button1\")",
      on = Platform.MOBILE_ANDROID)
  @Locate(
      iOSClassChain = "**/XCUIElementTypeAlert/**/XCUIElementTypeButton[`name =[cd] 'OK'`]",
      on = Platform.MOBILE_IOS)
  protected Button okayButton;

  @Locate(id = "com.wearehathway.peets.development:id/confirmButton", on = Platform.MOBILE_ANDROID)
  @Locate(
      iOSClassChain = "**/XCUIElementTypeButton[`label == \"Confirm\"`]",
      on = Platform.MOBILE_IOS)
  protected Button confirmStoreButton;

  @Step("Get Popup message")
  public String getPopupMessage() {
    String text = WebHelper.cleanString(message.getText());
    logger.info("Popup message - [{}]", text);
    return text;
  }

  @Step("Tap Okay to dismiss error message")
  public <T extends BaseComponent> T tapOnOkay(Class<T> clazz) {
    logger.info("Taping Okay to dismiss error message");
    okayButton.click();
    return SdkHelper.create(clazz);
  }

  @Step("Confirm coffee bar")
  public <T extends BaseComponent> T confirmCoffeeBar(Class<T> clazz) {
    if (MobileHelper.isElementDisplayed(confirmStoreButton, 10)) {
      logger.info("Confirm coffee bar");
      confirmStoreButton.click();
    } else {
      logger.info("Confirm coffee bar didn't appear");
    }
    return SdkHelper.create(clazz);
  }
}
