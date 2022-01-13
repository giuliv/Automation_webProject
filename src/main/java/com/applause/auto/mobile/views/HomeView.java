package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.mobile.components.CheckInTooltipComponent;
import com.applause.auto.mobile.components.PointsTurnIntoRewardsTooltipComponent;
import com.applause.auto.mobile.components.ReorderTooltipComponent;
import com.applause.auto.mobile.components.SwipeTooltipComponent;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import io.qameta.allure.Step;
import lombok.Getter;

@Implementation(is = HomeView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = HomeView.class, on = Platform.MOBILE_IOS)
public class HomeView extends BaseComponent {

  @Getter @Locate ReorderTooltipComponent reorderTooltipComponent;
  @Getter @Locate CheckInTooltipComponent checkInTooltipComponent;
  @Getter @Locate PointsTurnIntoRewardsTooltipComponent pointsTurnIntoRewardsTooltipComponent;
  @Getter @Locate SwipeTooltipComponent swipeTooltipComponent;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeStaticText[`label == 'Home'`]",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/homeButton\")",
      on = Platform.MOBILE_ANDROID)
  protected Button homeButton;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeStaticText[`label == 'Menu'`]",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/menuButton\")",
      on = Platform.MOBILE_ANDROID)
  protected Button menuButton;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeStaticText[`label == 'More'`]",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/moreButton\")",
      on = Platform.MOBILE_ANDROID)
  protected Button moreButton;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeButton[`label == 'QR Icon'`]",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/dashboard_motion_layout\")",
      on = Platform.MOBILE_ANDROID)
  protected TextBox signature;

  public void afterInit() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(signature).present());
  }

  @Step("Tap on 'More' button")
  public MoreOptionsView tapOnMoreButton() {
    logger.info("Tapping on 'More' button");
    moreButton.click();
    return SdkHelper.create(MoreOptionsView.class);
  }
}
