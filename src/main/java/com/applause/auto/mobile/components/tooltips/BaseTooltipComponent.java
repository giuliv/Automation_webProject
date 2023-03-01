package com.applause.auto.mobile.components.tooltips;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import io.qameta.allure.Step;

@Implementation(is = BaseTooltipComponent.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = BaseTooltipComponent.class, on = Platform.MOBILE_IOS)
public class BaseTooltipComponent extends BaseComponent {

  @Locate(
      iOSClassChain = "**/XCUIElementTypeButton[`label == 'button cross'`]",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/close.*\")",
      on = Platform.MOBILE_ANDROID)
  protected Button closeButton;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeButton[`label == \"View Reward\"`]",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/close.*\")",
      on = Platform.MOBILE_ANDROID)
  protected Button viewRewardsButton;

  @Step("Close Tooltip")
  public <T extends BaseComponent> T closeAnyTooltipIfDisplayed(
      int countOfTooltips, Class<T> clazz) {
    for (int i = 0; i < countOfTooltips; i++) {
      if (MobileHelper.isElementDisplayed(closeButton, 10)) {
        closeButton.click();
      } else {
        logger.info("Tooltip didn't appear");
      }
    }
    return SdkHelper.create(clazz);
  }

  @Step("Close Tooltip")
  public void closeAnyTooltipIfDisplayed(int countOfTooltips) {
    for (int i = 0; i < countOfTooltips; i++) {
      if (MobileHelper.isElementDisplayed(closeButton, 5)) {
        closeButton.click();
      } else {
        logger.info("Tooltip didn't appear");
      }
    }
  }

  @Step("Review if view rewards banner is displayed")
  public boolean isViewRewardsBannerDisplayed() {
    return MobileHelper.isElementDisplayed(viewRewardsButton, 8);
  }
}
