package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.mobile.components.BottomNavigationMenuChunk;
import com.applause.auto.mobile.components.tooltips.CheckInTooltipComponent;
import com.applause.auto.mobile.components.tooltips.PointsTurnIntoRewardsTooltipComponent;
import com.applause.auto.mobile.components.tooltips.ReorderTooltipComponent;
import com.applause.auto.mobile.components.tooltips.SwipeTooltipComponent;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import io.qameta.allure.Step;
import java.time.Duration;
import lombok.Getter;

@Implementation(is = HomeView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = HomeView.class, on = Platform.MOBILE_IOS)
public class HomeView extends BaseComponent {

  @Getter @Locate ReorderTooltipComponent reorderTooltipComponent;
  @Getter @Locate CheckInTooltipComponent checkInTooltipComponent;
  @Getter @Locate PointsTurnIntoRewardsTooltipComponent pointsTurnIntoRewardsTooltipComponent;
  @Getter @Locate SwipeTooltipComponent swipeTooltipComponent;
  @Getter @Locate BottomNavigationMenuChunk bottomNavigationMenuChunk;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeStaticText[`label == 'More'`]",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/moreButton\")",
      on = Platform.MOBILE_ANDROID)
  protected Button moreButton;

  @Locate(
      xpath =
          "//XCUIElementTypeStaticText[@value = 'Swipe left or tap the icon to scan your QR code in-store.']|//XCUIElementTypeButton[@label = 'QR Code']",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/dashboard_motion_layout\")",
      on = Platform.MOBILE_ANDROID)
  protected TextBox signature;

  @Locate(accessibilityId = "More", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/moreButton", on = Platform.MOBILE_ANDROID)
  protected Button getMoreScreenButton;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(signature).present().setTimeout(Duration.ofSeconds(60)));
  }

  @Step("Tap on 'More' button")
  public MoreOptionsView tapOnMoreButton() {
    logger.info("Tapping on 'More' button");
    moreButton.click();
    return SdkHelper.create(MoreOptionsView.class);
  }

  @Step(" Gets account profile menu.")
  public MoreOptionsView getAccountProfileMenu() {
    logger.info("Open account profile menu");
    getMoreScreenButton.initialize();
    MobileHelper.tapOnElementCenter(getMoreScreenButton);
    SdkHelper.getSyncHelper().sleep(5000);
    return SdkHelper.create(MoreOptionsView.class);
  }
}
