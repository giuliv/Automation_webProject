package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import java.time.Duration;

public class CartView extends BaseView {

  /* -------- Elements -------- */

  @Locate(
      iOSClassChain = "**/XCUIElementTypeButton[`label == \"Add Items\"`]",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/addItemButton\")",
      on = Platform.MOBILE_ANDROID)
  protected ContainerElement addItemButton;

  /* -------- Actions -------- */
  public void afterInit() {
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(addItemButton).present().setTimeout(Duration.ofSeconds(15)));
  }
}
