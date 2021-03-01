package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.helper.sync.Until;
import io.appium.java_client.android.AndroidDriver;
import java.time.Duration;
import org.openqa.selenium.ScreenOrientation;

/** The type Privacy policy view. */
@Implementation(is = AndroidPrivacyPolicyView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = PrivacyPolicyView.class, on = Platform.MOBILE_IOS)
public class PrivacyPolicyView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(
      xpath = "//XCUIElementTypeOther[contains(@name,\"PRIVACY POLICY\")][1]",
      on = Platform.MOBILE_IOS)
  @Locate(xpath = "//*[contains(@text, \"PRIVACY POLICY\")]", on = Platform.MOBILE_ANDROID)
  protected Text getHeadingText;

  @Locate(id = "android:id/button_once", on = Platform.MOBILE_ANDROID)
  protected Text chromeBrowserOptionButton;

  @Locate(id = "com.android.chrome:id/positive_button", on = Platform.MOBILE_ANDROID)
  protected Text allowLocationToBrowser;

  @Locate(
      xpath = "//android.widget.Button[@text='Allow only while using the app']",
      on = Platform.MOBILE_ANDROID)
  protected Text allowLocationToBrowser2;

  @Locate(xpath = "//*[text()='Accept']", on = Platform.MOBILE_ANDROID)
  protected Button acceptCookiesButton;

  @Locate(xpath = "//*[text()='Close & Continue']", on = Platform.MOBILE_ANDROID)
  protected Button closeAndContinueButton;

  @Locate(xpath = "//XCUIElementTypeButton[@name=\"Done\"]", on = Platform.MOBILE_IOS)
  protected Text doneButton;

  @Override
  public void afterInit() {
    getSyncHelper()
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
    return this.create(LegalInfoView.class);
  }
}

class AndroidPrivacyPolicyView extends PrivacyPolicyView {

  @Override
  public void afterInit() {
    try {
      chromeBrowserOptionButton.click();
    } catch (Throwable th) {
      logger.info("No browser popup overlay found");
    }
    AndroidDriver androidDriver = ((AndroidDriver) getDriver());
    logger.info("Orientation: " + androidDriver.getOrientation());
    logger.info("Orientation: Forcing to PORTRAIT");
    androidDriver.rotate(ScreenOrientation.PORTRAIT);
    getSyncHelper().sleep(5000);
    logger.info("Orientation: " + androidDriver.getOrientation());
    try {
      allowLocationToBrowser.click();
    } catch (Throwable th) {
      logger.info("No location popup overlay found");
    }
    try {
      allowLocationToBrowser2.click();
    } catch (Throwable th) {
      logger.info("No location popup overlay found");
    }
    try {
      acceptCookiesButton.click();
    } catch (Throwable th) {
      logger.info("No cookies popup overlay found");
    }
    try {
      closeAndContinueButton.click();
    } catch (Throwable th) {
      logger.info("No cookies popup overlay found");
    }
    getSyncHelper()
        .wait(Until.uiElement(getHeadingText).present().setTimeout(Duration.ofSeconds(30)));
  }

  /* -------- Lifecycle Methods -------- */

  @Override
  public LegalInfoView done() {
    logger.info("Tap 'Done' button");
    MobileHelper.tapAndroidDeviceBackButton();
    return this.create(LegalInfoView.class);
  }
}
