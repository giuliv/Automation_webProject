package com.applause.auto.mobile.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import io.qameta.allure.Step;

public class QrScanChunk extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(
      iOSClassChain = "**/XCUIElementTypeImage[`label == \"QR Code\"`]",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/qrCode\")",
      on = Platform.MOBILE_ANDROID)
  protected ContainerElement qrCode;

  /* -------- Actions -------- */

  /**
   * Is Qr code displayed?
   *
   * @return Boolean
   */
  @Step("Qr code is displayed")
  public Boolean isQrCodeDisplayed() {
    logger.info("Qr code is displayed");
    return qrCode.isDisplayed();
  }
}
