package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;

@Implementation(is = SyrupsAndSaucesView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = SyrupsAndSaucesView.class, on = Platform.MOBILE_IOS)
public class SyrupsAndSaucesView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(xpath = "//XCUIElementTypeNavigationBar/XCUIElementTypeButton", on = Platform.MOBILE_IOS)
  @Locate(accessibilityId = "Navigate up", on = Platform.MOBILE_ANDROID)
  protected Button navigateBackButton;

  @Locate(accessibilityId = "Navigate up", on = Platform.MOBILE_ANDROID)
  protected Button syrupItemButton;

  @Locate(accessibilityId = "Navigate up", on = Platform.MOBILE_ANDROID)
  protected Button saveChangesButton;

  /* -------- Actions -------- */
  public SyrupsAndSaucesView selectSyrup(String syrup) {
    syrupItemButton.format(syrup);
    return this;
  }

  public <T extends BaseComponent> T saveChanges(Class<T> clazz) {
    saveChangesButton.click();
    return this.create(clazz);
  }
}
