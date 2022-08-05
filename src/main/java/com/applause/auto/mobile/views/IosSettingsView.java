package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import java.time.Duration;

public class IosSettingsView extends BaseComponent {

  @Locate(xpath = "//XCUIElementTypeNavigationBar[@name=\"Settings\"]", on = Platform.MOBILE_IOS)
  protected TextBox signature;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeCell[`label BEGINSWITH \"%s\"`]",
      on = Platform.MOBILE_IOS)
  protected TextBox menuItemButton;

  @Locate(xpath = "//XCUIElementTypeNavigationBar/XCUIElementTypeButton", on = Platform.MOBILE_IOS)
  protected TextBox backButton;

  public IosSettingsView init() {
    logger.info("Waiting for Settings Page");
    if (MobileHelper.isElementDisplayed(backButton, 5)) {
      int attempts = 5;
      while (backButton.exists() && attempts-- > 0) {
        logger.info("Navigating back on settings:" + attempts);
        backButton.click();
      }
    }
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(signature).visible().setTimeout(Duration.ofSeconds(30)));
    return this;
  }

  public <T extends BaseComponent> T openMenuItem(String item, Class<T> clazz) {
    logger.info("Open menu item: " + item);
    menuItemButton.format(item).click();
    return SdkHelper.create(clazz);
  }
}
