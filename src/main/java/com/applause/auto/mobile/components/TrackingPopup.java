package com.applause.auto.mobile.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import io.qameta.allure.Step;

@Implementation(is = TrackingPopup.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = TrackingPopup.class, on = Platform.MOBILE_IOS)
public class TrackingPopup extends BaseComponent {

  @Locate(id = "Allow", on = Platform.MOBILE_IOS)
  @Locate(androidUIAutomator = "new UiSelector().text(\"Allow\")", on = Platform.MOBILE_ANDROID)
  protected Button allowButton;

  @Step("Tap on 'Allow' to track web site")
  public void allowTrackingIfDisplayed() {
    if (MobileHelper.isElementDisplayed(allowButton, 5)) {
      logger.info("Tapping on 'Allow' to track web site");
      allowButton.click();
    } else {
      logger.info("Tracking popup didn't appear");
    }
  }
}
