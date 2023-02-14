package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.mobile.components.tooltips.CheckInTooltipComponent;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import io.qameta.allure.Step;
import lombok.Getter;

import java.time.Duration;

public class PeetnikRewardsHelpView extends BaseView {

  @Getter @Locate CheckInTooltipComponent checkInTooltipComponent;

  /* -------- Elements -------- */

  @Locate(
      xpath =
          "//XCUIElementTypeStaticText[@name=\"How much do we love Peetnik Rewards? Let us count the ways.\"]",
      on = Platform.MOBILE_IOS)
  @Locate(xpath = "//androidx.appcompat.widget.LinearLayoutCompat", on = Platform.MOBILE_ANDROID)
  protected Button mainContent;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeButton[`label == \"button cross\"`]",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/cross_button\")",
      on = Platform.MOBILE_ANDROID)
  protected Button closeButton;

  /* -------- Actions -------- */
  public void afterInit() {
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(mainContent).present().setTimeout(Duration.ofSeconds(15)));
  }

  @Step("Close Peetnik Rewards Help Tooltip")
  public <T extends BaseComponent> T close(Class<T> clazz) {
    if (MobileHelper.isElementDisplayed(closeButton, 10)) {
      logger.info("Closing peetnik rewards help");
      closeButton.click();
      getCheckInTooltipComponent().closeCheckInTooltipIfDisplayed(clazz);
    } else {
      logger.info("Peetnik rewards help Tooltip didn't appear");
    }
    return SdkHelper.create(clazz);
  }
}
