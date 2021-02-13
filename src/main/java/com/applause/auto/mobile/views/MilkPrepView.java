package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;

@Implementation(is = MilkPrepView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = IosMilkPrepView.class, on = Platform.MOBILE_IOS)
public class MilkPrepView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(xpath = "//XCUIElementTypeNavigationBar/XCUIElementTypeButton", on = Platform.MOBILE_IOS)
  @Locate(accessibilityId = "Navigate up", on = Platform.MOBILE_ANDROID)
  protected Button navigateBackButton;

  @Locate(xpath = "//android.widget.TextView[@text='Choose Milk']", on = Platform.MOBILE_ANDROID)
  @Locate(accessibilityId = "Choose Milk", on = Platform.MOBILE_IOS)
  protected Button chooseMilkMenuItemButton;

  @Locate(xpath = "//android.widget.TextView[@text='%s']", on = Platform.MOBILE_ANDROID)
  @Locate(
      iOSClassChain = "**/XCUIElementTypeStaticText[`label == \"%s\"`]",
      on = Platform.MOBILE_IOS)
  protected Button chooseMilkMenuOptionsButton;

  @Locate(
      id = "com.wearehathway.peets.development:id/saveChangesButton",
      on = Platform.MOBILE_ANDROID)
  @Locate(
      iOSClassChain = "**/XCUIElementTypeStaticText[`label == \"Save Changes\"`]",
      on = Platform.MOBILE_IOS)
  protected Button saveChangesButton;

  /* -------- Actions -------- */
  public MilkPrepView chooseMilk(String milkType) {
    chooseMilkMenuItemButton.click();
    chooseMilkMenuOptionsButton.format(milkType).click();
    return this;
  }

  public <T extends BaseComponent> T saveChanges(Class<T> clazz) {
    saveChangesButton.click();
    return this.create(clazz);
  }
}

class IosMilkPrepView extends MilkPrepView {
  @Override
  public MilkPrepView chooseMilk(String milkType) {
    chooseMilkMenuItemButton.click();
    chooseMilkMenuOptionsButton.format(milkType.equals("2% Milk") ? "Whole Milk" : "2% Milk");
    MobileHelper.scrollElementIntoView(chooseMilkMenuOptionsButton);
    getSyncHelper().sleep(1000);
    chooseMilkMenuItemButton.click();
    chooseMilkMenuOptionsButton.format(milkType).initialize();
    MobileHelper.scrollElementIntoView(chooseMilkMenuOptionsButton);
    return this;
  }
}
