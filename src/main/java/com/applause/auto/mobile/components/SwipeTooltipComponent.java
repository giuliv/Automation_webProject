package com.applause.auto.mobile.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import io.qameta.allure.Step;

@Implementation(is = SwipeTooltipComponent.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = SwipeTooltipComponent.class, on = Platform.MOBILE_IOS)
public class SwipeTooltipComponent extends BaseComponent {

  @Locate(
      iOSClassChain = "**/XCUIElementTypeButton[`label == 'button cross'`]",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/close.*\")",
      on = Platform.MOBILE_ANDROID)
  protected Button closeButton;

  @Step("Close Swipe Tooltip")
  public <T extends BaseComponent> T closeTooltipIfDisplayed(Class<T> clazz) {
    if (MobileHelper.isElementDisplayed(closeButton, 10)) {
      logger.info("Closing Swipe Tooltip");
      closeButton.click();
    } else {
      logger.info("Swipe Tooltip didn't appear");
    }
    return SdkHelper.create(clazz);
  }
}
