package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.TextBox;

@Implementation(is = AndroidPeetsSettingsView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = PeetsSettingsView.class, on = Platform.MOBILE_IOS)
public class PeetsSettingsView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(xpath = "//XCUIElementTypeApplication[@name=\"Settings\"]", on = Platform.MOBILE_IOS)
  @Locate(id = "com.android.settings:id/main_content", on = Platform.MOBILE_ANDROID)
  protected TextBox getSignature;

  @Locate(xpath = "//XCUIElementTypeStaticText[@name=\"Peet's\"]", on = Platform.MOBILE_IOS)
  @Locate(id = "", on = Platform.MOBILE_ANDROID)
  protected TextBox getPeetsAppMenuItem;

  @Locate(xpath = "//XCUIElementTypeCell[@name=\"Location\"]", on = Platform.MOBILE_IOS)
  @Locate(id = "com.android.settings:id/switch_widget", on = Platform.MOBILE_ANDROID)
  protected Button getLocationButton;

  @Locate(xpath = "//XCUIElementTypeCell[@name=\"Never\"]", on = Platform.MOBILE_IOS)
  @Locate(xpath = "//android.widget.Button[@text='CLOSE']", on = Platform.MOBILE_ANDROID)
  protected Button getNeverButton;

  /* -------- Actions -------- */

  /** Open location. */
  public void openLocation() {
    if (SdkHelper.getQueryHelper().elementCount(getLocationButton.getLocator()) == 0) {
      MobileHelper.scrollDownToElementCloseToMiddle(getPeetsAppMenuItem, 30);
      getPeetsAppMenuItem.click();
    }
    logger.info("Open Location menu");
    getLocationButton.click();
  }

  /** Select never. */
  public void selectNever() {
    SdkHelper.getSyncHelper().sleep(5000);
    logger.info("Select Never");

    getNeverButton.click();
  }

  /**
   * Back to app general settings view.
   *
   * @return the general settings view
   */
  public GeneralSettingsView backToApp() {
    logger.info("Returning to application");
    MobileHelper.activateApp();
    return SdkHelper.create(GeneralSettingsView.class);
  }
}

class AndroidPeetsSettingsView extends PeetsSettingsView {

  /* -------- Actions -------- */

  @Override
  public void openLocation() {
    logger.info("Open Location menu");
    getLocationButton.click();
  }

  @Override
  public GeneralSettingsView backToApp() {
    logger.info("Returning to application");
    MobileHelper.tapAndroidDeviceBackButton();
    return SdkHelper.create(GeneralSettingsView.class);
  }
}
