package com.applause.auto.mobile.components.tooltips;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import io.qameta.allure.Step;

@Implementation(is = ReorderTooltipComponent.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = ReorderTooltipComponent.class, on = Platform.MOBILE_IOS)
public class ReorderTooltipComponent extends BaseTooltipComponent {

  @Locate(
      iOSClassChain = "**/XCUIElementTypeButton[`label == 'button cross'`]",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/closeReorderTooltipButton\")",
      on = Platform.MOBILE_ANDROID)
  protected Button closeButton;

  @Step("Close Reorder Tooltip")
  public <T extends BaseComponent> T closeReorderTooltipIfDisplayed(Class<T> clazz) {
    if (MobileHelper.isElementDisplayed(closeButton, 15)) {
      logger.info("Closing Reorder Tooltip");
      closeButton.click();
    } else {
      logger.info("Reorder Tooltip didn't appear");
    }
    return SdkHelper.create(clazz);
  }

  @Step("Close Reorder Tooltip")
  public ReorderTooltipComponent closeReorderTooltipIfDisplayed() {
    if (MobileHelper.isElementDisplayed(closeButton, 15)) {
      logger.info("Closing Reorder Tooltip");
      closeButton.click();
    } else {
      logger.info("Reorder Tooltip didn't appear");
    }
    return this;
  }
}
