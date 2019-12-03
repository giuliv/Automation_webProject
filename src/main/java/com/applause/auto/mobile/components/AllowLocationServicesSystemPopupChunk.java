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

  @Locate(id = "Allow", on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.android.packageinstaller:id/permission_allow_button",
      on = Platform.MOBILE_ANDROID)
  protected Button getAllowButton;

  @Locate(id = "Don’t Allow", on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.android.packageinstaller:id/permission_deny_button",
      on = Platform.MOBILE_ANDROID)
  protected Button getDoNotAllowButton;

  /* -------- Actions -------- */

  /**
   * Gets title.
   *
   * @return the title
   */
  public String getImageAltText() {
    return getImageAltTextText.getText();
  }

  /**
   * Is allow button displayed boolean.
   *
   * @return the boolean
   */
  public boolean isAllowButtonDisplayed() {
    return getAllowButton.isDisplayed();
  }

  /**
   * Is do not allow button displayed boolean.
   *
   * @return the boolean
   */
  public boolean isDoNotAllowButtonDisplayed() {
    return getDoNotAllowButton.isDisplayed();
  }

  /** Allow. */
  public void allow() {
    logger.info("Tap Allow button");
    getAllowButton.click();
  }
}
