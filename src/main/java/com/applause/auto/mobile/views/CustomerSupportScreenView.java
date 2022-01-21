package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import io.appium.java_client.android.AndroidDriver;
import java.time.Duration;
import org.openqa.selenium.ScreenOrientation;

@Implementation(is = AndroidCustomerSupportScreenView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = CustomerSupportScreenView.class, on = Platform.MOBILE_IOS)
public class CustomerSupportScreenView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(
      xpath = "//XCUIElementTypeOther[@name=\"Contact Us | Peet's Coffee\"]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath = "//android.webkit.WebView[@text=\"Contact Us | Peet's Coffee\"]",
      on = Platform.MOBILE_ANDROID)
  protected Text headingText;

  @Locate(xpath = "//XCUIElementTypeButton[@name=\"Done\"]", on = Platform.MOBILE_IOS)
  protected Text doneButton;

  @Locate(id = "com.android.chrome:id/positive_button", on = Platform.MOBILE_ANDROID)
  protected Text allowLocationToBrowser;

  @Locate(
      xpath = "//android.widget.Button[@text='Allow only while using the app']",
      on = Platform.MOBILE_ANDROID)
  protected Text allowLocationToBrowser2;

  @Locate(xpath = "//XCUIElementTypeButton[@name=\"Close\"]", on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "(//android.webkit.WebView//android.widget.Button[@text='Close'])[1] | //*[@resource-id='svg-close-button'] | //span[@class='close-button']",
      on = Platform.MOBILE_ANDROID)
  protected Button closeAdvPopUpButton;

  @Locate(
      xpath = "(//XCUIElementTypeStaticText[@label='HELP CENTER'])[2]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath = "//a[@class='footer__nav-link' and normalize-space()='Help Center']",
      on = Platform.MOBILE_ANDROID)
  protected Button helpCenterFooterButton;

  @Locate(xpath = "//XCUIElementTypeButton[@label='Accept']", on = Platform.MOBILE_IOS)
  protected Button cookiesAcceptButton;

  @Locate(xpath = "//XCUIElementTypeButton[@label='NO THANKS']", on = Platform.MOBILE_IOS)
  protected Button adsNoThanksButton;

  /* -------- Actions -------- */

  public void afterInit() {
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(headingText).present().setTimeout(Duration.ofSeconds(12)));
    closeDiscountAds();
    acceptCookies();
  }

  /**
   * Gets header.
   *
   * @return the header
   */
  public String getHeader() {
    return headingText.getText();
  }

  /**
   * Done legal info view.
   *
   * @return the legal info view
   */
  public HelpAndFeedbackView done() {
    logger.info("Tap 'Done' button");
    doneButton.click();
    return SdkHelper.create(HelpAndFeedbackView.class);
  }

  /**
   * Tap on the 'Help Center'
   *
   * @return PeetnikMainFaqView
   */
  public PeetnikMainFaqView clickHelpCenterFooterButton() {
    logger.info("Taping on the 'Help Center'");
    MobileHelper.scrollDownToElementCloseToMiddle(helpCenterFooterButton, 30);
    MobileHelper.tapByCoordinatesOnElementCenter(helpCenterFooterButton);
    return SdkHelper.create(PeetnikMainFaqView.class);
  }

  /** Accept cookies */
  private void acceptCookies() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(cookiesAcceptButton).present());
    logger.info("Accepting cookies");
    cookiesAcceptButton.click();
  }

  /** Close discount ads */
  private void closeDiscountAds() {
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(cookiesAcceptButton).present().setTimeout(Duration.ofSeconds(20)));
    adsNoThanksButton.click();
  }
}

class AndroidCustomerSupportScreenView extends CustomerSupportScreenView {

  @Override
  public void afterInit() {
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
    //    logger.info("Close popup");
    //    closeAdvPopUpButton.click();
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(headingText).present().setTimeout(Duration.ofSeconds(12)));
  }

  @Override
  public HelpAndFeedbackView done() {
    logger.info("Tap 'Done' button");
    MobileHelper.tapAndroidDeviceBackButton();
    return SdkHelper.create(HelpAndFeedbackView.class);
  }

  @Override
  public PeetnikMainFaqView clickHelpCenterFooterButton() {
    logger.info("Taping on the 'Help Center'");
    MobileHelper.switchToChromeWebView();
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(helpCenterFooterButton).present().setTimeout(Duration.ofSeconds(45)));
    SdkHelper.getBrowserControl().jsClick(helpCenterFooterButton);
    MobileHelper.switchToNativeContext();
    return SdkHelper.create(PeetnikMainFaqView.class);
  }
}
