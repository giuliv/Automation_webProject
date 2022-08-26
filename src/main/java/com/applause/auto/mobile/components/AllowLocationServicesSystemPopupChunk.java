package com.applause.auto.mobile.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;

/** The type Allow location services system popup chunk. */
@Implementation(is = AllowLocationServicesSystemPopupChunk.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = AllowLocationServicesSystemPopupChunk.class, on = Platform.MOBILE_IOS)
public class AllowLocationServicesSystemPopupChunk extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(
      xpath =
          "//XCUIElementTypeStaticText[@name=\"Allow “Peets-Sandbox” to access your location while you are using the app?\"]",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.android.packageinstaller:id/permission_message", on = Platform.MOBILE_ANDROID)
  protected Text getImageAltTextText;

  @Locate(xpath = "//*[@label='Allow While Using App']", on = Platform.MOBILE_IOS)
  @Locate(xpath = "//*[contains(@resource-id,'permission_allow')]", on = Platform.MOBILE_ANDROID)
  protected Button getAllowButton;

  @Locate(id = "Don’t Allow", on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.android.packageinstaller:id/permission_deny_button",
      on = Platform.MOBILE_ANDROID)
  protected Button getDoNotAllowButton;

  /* -------- Actions -------- */

  /** Allow. */
  public void allow() {
    logger.info("Tap Allow button");
    getAllowButton.click();
  }
}
