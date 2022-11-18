package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.mobile.components.BrowserPopup;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.SupportsContextSwitching;
import java.time.Duration;
import org.openqa.selenium.ScreenOrientation;

/** The type Privacy policy view. */
@Implementation(is = AndroidPrivacyPolicyView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = PrivacyPolicyView.class, on = Platform.MOBILE_IOS)
public class PrivacyPolicyView extends BaseComponent {

  @Locate(
      xpath = "//XCUIElementTypeOther[contains(@name,\"PRIVACY POLICY\")][1]",
      on = Platform.MOBILE_IOS)
  @Locate(xpath = "//*[contains(@text, \"Privacy Policy\")]", on = Platform.MOBILE_ANDROID)
  protected Text getHeadingText;

  @Locate(xpath = "//*[text()='Close & Continue']", on = Platform.MOBILE_ANDROID)
  protected Button closeAndContinueButton;

  @Locate(xpath = "//XCUIElementTypeButton[@name=\"Done\"]", on = Platform.MOBILE_IOS)
  protected Text doneButton;

  @Locate BrowserPopup browserPopup;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(getHeadingText).present().setTimeout(Duration.ofSeconds(30)));
  }

  /**
   * Done legal info view.
   *
   * @return the legal info view
   */
  public LegalInfoView done() {
    logger.info("Tap 'Done' button");
    doneButton.click();
    return SdkHelper.create(LegalInfoView.class);
  }
}

class AndroidPrivacyPolicyView extends PrivacyPolicyView {

  @Override
  public void afterInit() {
    browserPopup.closeBrowserOptionPopup();

    AndroidDriver androidDriver = ((AndroidDriver) SdkHelper.getDriver());
    logger.info("Orientation: " + androidDriver.getOrientation());
    logger.info("Orientation: Forcing to PORTRAIT");
    androidDriver.rotate(ScreenOrientation.PORTRAIT);
    SdkHelper.getSyncHelper().sleep(5000);
    logger.info("Orientation: " + androidDriver.getOrientation());

    browserPopup.closeLocationPopup();

    browserPopup.acceptCookies();

    if (MobileHelper.isElementDisplayed(closeAndContinueButton, 5)) {
      closeAndContinueButton.click();
    } else {
      logger.info("No close and Continue button found");
    }

    try {
      SdkHelper.getSyncHelper()
          .wait(Until.uiElement(getHeadingText).present().setTimeout(Duration.ofSeconds(30)));
    } catch (Exception e) {
      logger.info(
          "Contexts: " + ((SupportsContextSwitching) SdkHelper.getDriver()).getContextHandles());
      logger.info("Switching to WebContext");
      ((SupportsContextSwitching) SdkHelper.getDriver()).context("WEBVIEW_chrome");
      SdkHelper.getSyncHelper()
          .wait(Until.uiElement(getHeadingText).present().setTimeout(Duration.ofSeconds(30)));
      ((SupportsContextSwitching) SdkHelper.getDriver()).context("NATIVE_APP");
    }
  }

  /* -------- Lifecycle Methods -------- */

  @Override
  public LegalInfoView done() {
    logger.info("Tap 'Done' button");
    MobileHelper.tapAndroidDeviceBackButton();
    MobileHelper.tapAndroidDeviceBackButton();
    return SdkHelper.create(LegalInfoView.class);
  }
}
