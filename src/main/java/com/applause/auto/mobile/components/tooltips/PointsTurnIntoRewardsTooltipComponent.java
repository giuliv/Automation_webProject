package com.applause.auto.mobile.components.tooltips;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import io.qameta.allure.Step;

@Implementation(is = PointsTurnIntoRewardsTooltipComponent.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = PointsTurnIntoRewardsTooltipComponent.class, on = Platform.MOBILE_IOS)
public class PointsTurnIntoRewardsTooltipComponent extends BaseTooltipComponent {

  @Locate(
      iOSClassChain = "**/XCUIElementTypeButton[`label == 'button cross'`]",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/closeRewardsTooltipButton\")",
      on = Platform.MOBILE_ANDROID)
  protected Button closeButton;

  @Step("Close Points Turn Into Rewards Tooltip")
  public <T extends BaseComponent> T closeTooltipIfDisplayed(Class<T> clazz) {
    if (MobileHelper.isElementDisplayed(closeButton, 10)) {
      logger.info("Closing Points Turn Into Rewards Tooltip");
      closeButton.click();
    } else {
      logger.info("Points Turn Into Rewards Tooltip didn't appear");
    }
    return SdkHelper.create(clazz);
  }
}
