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

  @Locate(accessibilityId = "Navigate up", on = Platform.MOBILE_ANDROID)
  protected Button shotPrepButton;

  @Locate(accessibilityId = "Navigate up", on = Platform.MOBILE_ANDROID)
  protected Button shotPrepOptionButton;

  @Locate(accessibilityId = "Navigate up", on = Platform.MOBILE_ANDROID)
  protected Button saveChangesButton;

  /* -------- Actions -------- */
  public void selectShotPrep(String shotPrep) {
    shotPrepButton.click();
    shotPrepOptionButton.format(shotPrep);
  }

  public <T extends BaseComponent> T save(Class<T> clazz) {
    saveChangesButton.click();
    return this.create(clazz);
  }
}
