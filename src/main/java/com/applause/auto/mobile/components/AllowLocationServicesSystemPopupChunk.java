package com.applause.auto.mobile.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;

/** The type Allow location services system popup chunk. */
@Implementation(is = AllowLocationServicesSystemPopupChunk.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = AllowLocationServicesSystemPopupChunk.class, on = Platform.MOBILE_IOS)
public class AllowLocationServicesSystemPopupChunk extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(
      xpath =
          "//XCUIElementTypeAlert[@name=\"Allow “Peets-Sandbox” to access your location while you are using the app?\"]",
      on = Platform.MOBILE_IOS)
  @Locate(id = "android:id/message", on = Platform.MOBILE_ANDROID)
  protected ContainerElement getSignature;

  @Locate(
      xpath =
          "//XCUIElementTypeStaticText[@name=\"Allow “Peets-Sandbox” to access your location while you are using the app?\"]",
      on = Platform.MOBILE_IOS)
  @Locate(id = "android:id/message", on = Platform.MOBILE_ANDROID)
  protected Text getImageAltTextText;

  @Locate(id = "Allow", on = Platform.MOBILE_IOS)
  @Locate(id = "android:id/button1", on = Platform.MOBILE_ANDROID)
  protected Button getAllowButton;

  @Locate(id = "Don’t Allow", on = Platform.MOBILE_IOS)
  @Locate(id = "TBD", on = Platform.MOBILE_ANDROID)
  protected Button getDoNotAllowButton;

  @Locate(
      xpath =
          "//XCUIElementTypeStaticText[@name=\"Location Services will only use your location while using the app, and will not share your location or information. Your location will be used to find the nearest coffeebar and place a mobile order.\"]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.TextView[2]",
      on = Platform.MOBILE_ANDROID)
  protected Text getSubTitleText;

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
   * Gets formatted message.
   *
   * @return the formatted message
   */
  public String getFormattedMessage() {
    return getSubTitleText.getText();
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
