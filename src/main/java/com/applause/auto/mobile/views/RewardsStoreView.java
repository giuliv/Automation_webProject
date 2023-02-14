package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.mobile.components.QrScanChunk;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import lombok.Getter;

import java.time.Duration;

public class RewardsStoreView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(xpath = "//XCUIElementTypeStaticText[@name=\"Rewards Store\"]", on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/rewards_and_details_pager\")",
      on = Platform.MOBILE_ANDROID)
  protected ContainerElement mainElement;

  @Getter @Locate QrScanChunk qrScanChunk;

  /* -------- Actions -------- */
  public void afterInit() {
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(mainElement).present().setTimeout(Duration.ofSeconds(15)));
  }
}
