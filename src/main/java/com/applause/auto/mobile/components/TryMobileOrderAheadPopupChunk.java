package com.applause.auto.mobile.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;

@Implementation(is = TryMobileOrderAheadPopupChunk.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = TryMobileOrderAheadPopupChunk.class, on = Platform.MOBILE_IOS)
public class TryMobileOrderAheadPopupChunk extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(xpath = "//XCUIElementTypeButton[@name='Dismiss']", on = Platform.MOBILE_IOS)
  @Locate(xpath = "//android.widget.Button", on = Platform.MOBILE_ANDROID)
  protected Button dismissButton;

  /* -------- Actions -------- */

  /** Click dismiss button */
  public void clickDismissButton() {
    logger.info("Click on Try Mobile Order Ahead Popup Dismiss button");
    dismissButton.click();
  }

  /**
   * Verify if Dismiss button is displayed
   *
   * @return boolean
   */
  public boolean isDismissButtonDisplayed() {
    return MobileHelper.isDisplayed(dismissButton);
  }
}
