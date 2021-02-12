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

  @Locate(xpath = "//android.widget.TextView[@text='%s']", on = Platform.MOBILE_ANDROID)
  @Locate(
      xpath =
          "//XCUIElementTypeStaticText[@name=\"%s\"]/following-sibling::XCUIElementTypeButton|//XCUIElementTypeCell/XCUIElementTypeStaticText[@name=\"%s\"]",
      on = Platform.MOBILE_IOS)
  protected Button syrupItemButton;

  @Locate(
      xpath = "//XCUIElementTypeCell/XCUIElementTypeStaticText[@name=\"%s\" and @visible='true']",
      on = Platform.MOBILE_IOS)
  @Locate(xpath = "//android.widget.TextView[@text='%s']", on = Platform.MOBILE_ANDROID)
  protected Button syrupOptionButton;

  @Locate(
      id = "com.wearehathway.peets.development:id/saveChangesButton",
      on = Platform.MOBILE_ANDROID)
  @Locate(
      iOSClassChain = "**/XCUIElementTypeStaticText[`label == \"Save Changes\"`]",
      on = Platform.MOBILE_IOS)
  protected Button saveChangesButton;

  /* -------- Actions -------- */
  public SyrupsAndSaucesView selectSyrup(String syrup) {
    syrupItemButton.format(syrup, syrup).click();
    return this;
  }

  public SyrupsAndSaucesView selectOption(String syrup) {
    syrupOptionButton.format(syrup).click();
    return this;
  }

  public <T extends BaseComponent> T saveChanges(Class<T> clazz) {
    saveChangesButton.click();
    return this.create(clazz);
  }
}
