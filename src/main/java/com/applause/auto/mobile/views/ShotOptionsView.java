package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;

@Implementation(is = ShotOptionsView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = ShotOptionsView.class, on = Platform.MOBILE_IOS)
public class ShotOptionsView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(xpath = "//XCUIElementTypeNavigationBar/XCUIElementTypeButton", on = Platform.MOBILE_IOS)
  @Locate(accessibilityId = "Navigate up", on = Platform.MOBILE_ANDROID)
  protected Button navigateBackButton;

  @Locate(xpath = "//android.widget.TextView[@text='Shot Prep']", on = Platform.MOBILE_ANDROID)
  protected Button shotPrepButton;

  @Locate(xpath = "//android.widget.TextView[@text='%s']", on = Platform.MOBILE_ANDROID)
  protected Button shotPrepOptionButton;

  @Locate(
      id = "com.wearehathway.peets.development:id/saveChangesButton",
      on = Platform.MOBILE_ANDROID)
  protected Button saveChangesButton;

  /* -------- Actions -------- */
  public ShotOptionsView selectShotPrep(String shotPrep) {
    shotPrepButton.click();
    shotPrepOptionButton.format(shotPrep).click();
    return this;
  }

  public <T extends BaseComponent> T saveChanges(Class<T> clazz) {
    saveChangesButton.click();
    return this.create(clazz);
  }
}
