package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.helpers.sync.Until;
import io.appium.java_client.android.AndroidDriver;
import java.time.Duration;
import com.applause.auto.framework.SdkHelper;
import org.openqa.selenium.ScreenOrientation;

@Implementation(
    is = AndroidPeetnikRewardsTermsAndConditionsView.class,
    on = Platform.MOBILE_ANDROID)
@Implementation(is = PeetnikRewardsTermsAndConditionsView.class, on = Platform.MOBILE_IOS)
public class PeetnikRewardsTermsAndConditionsView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(
      xpath = "//XCUIElementTypeStaticText[@name=\"PEETNIK REWARDS TERMS & CONDITIONS\"]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath = "(//*[contains(@text, \"PEETNIK REWARDS TERMS & CONDITIONS\")])[2]",
      on = Platform.MOBILE_ANDROID)
  protected Text getHeadingText;

  @Locate(xpath = "//XCUIElementTypeButton[@name=\"Done\"]", on = Platform.MOBILE_IOS)
  protected Button doneButton;

  @Locate(id = "com.android.chrome:id/positive_button", on = Platform.MOBILE_ANDROID)
  protected Text allowLocationToBrowser;

  @Locate(
      xpath = "//android.widget.Button[@text='Allow only while using the app']",
      on = Platform.MOBILE_ANDROID)
  protected Text allowLocationToBrowser2;

  @Locate(xpath = "//android.widget.Button[@text='allow cookies']", on = Platform.MOBILE_ANDROID)
  protected Button allowCookiesButton;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper().sleep(5000);
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(getHeadingText).present().setTimeout(Duration.ofSeconds(60)));
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

class AndroidPeetnikRewardsTermsAndConditionsView extends PeetnikRewardsTermsAndConditionsView {

  public void afterInit() {
    SdkHelper.getSyncHelper().sleep(5000);
    AndroidDriver androidDriver = ((AndroidDriver) SdkHelper.getDriver());
    logger.info("Orientation: " + androidDriver.getOrientation());
    logger.info("Orientation: Forcing to PORTRAIT");
    androidDriver.rotate(ScreenOrientation.PORTRAIT);
    SdkHelper.getSyncHelper().sleep(5000);
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
      allowCookiesButton.click();
    } catch (Throwable th) {
      logger.info("No cookies popup overlay found");
    }

    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(getHeadingText).present().setTimeout(Duration.ofSeconds(12)));
  }

  /* -------- Lifecycle Methods -------- */

  @Override
  public LegalInfoView done() {
    logger.info("Tap 'Done' button");
    MobileHelper.tapAndroidDeviceBackButton();
    return SdkHelper.create(LegalInfoView.class);
  }
}
