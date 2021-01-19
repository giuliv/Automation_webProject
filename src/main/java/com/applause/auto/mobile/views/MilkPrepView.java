package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;

@Implementation(is = MilkPrepView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = MilkPrepView.class, on = Platform.MOBILE_IOS)
public class MilkPrepView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(xpath = "//XCUIElementTypeNavigationBar/XCUIElementTypeButton", on = Platform.MOBILE_IOS)
  @Locate(accessibilityId = "Navigate up", on = Platform.MOBILE_ANDROID)
  protected Button navigateBackButton;

  @Locate(accessibilityId = "Navigate up", on = Platform.MOBILE_ANDROID)
  protected Button chooseMilkMenuItemButton;

  @Locate(accessibilityId = "Navigate up", on = Platform.MOBILE_ANDROID)
  protected Button milkTypeButton;

  /* -------- Actions -------- */
  public void chooseMilk(String milkType) {
    chooseMilkMenuItemButton.click();
    milkTypeButton.click();
  }
}
