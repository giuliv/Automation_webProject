package com.applause.auto.mobile.components.tooltips;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import io.qameta.allure.Step;

@Implementation(is = CheckInTooltipComponent.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = CheckInTooltipComponent.class, on = Platform.MOBILE_IOS)
public class CheckInTooltipComponent extends BaseTooltipComponent {

  @Locate(
      iOSClassChain = "**/XCUIElementTypeButton[`label == 'button cross'`]",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/closeCheckInTooltipButton\")",
      on = Platform.MOBILE_ANDROID)
  protected Button closeButton;

  @Step("Close CheckIn Tooltip")
  public <T extends BaseComponent> T closeCheckInTooltipIfDisplayed(Class<T> clazz) {
    if (MobileHelper.isElementDisplayed(closeButton, 10)) {
      logger.info("Closing CheckIn Tooltip");
      closeButton.click();
    } else {
      logger.info("CheckIn Tooltip didn't appear");
    }
    return SdkHelper.create(clazz);
  }

  public void closeCheckInTooltipIfDisplayed() {
    if (MobileHelper.isElementDisplayed(closeButton, 10)) {
      logger.info("Closing CheckIn Tooltip");
      closeButton.click();
    } else {
      logger.info("CheckIn Tooltip didn't appear");
    }
  }
}
