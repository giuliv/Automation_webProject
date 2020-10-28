package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.DriverManager;
import com.applause.auto.util.helper.SyncHelper;
import com.applause.auto.util.helper.sync.Until;

import org.openqa.selenium.ScreenOrientation;

import java.time.Duration;

import io.appium.java_client.android.AndroidDriver;

@Implementation(is = AndroidCustomerSupportScreenView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = CustomerSupportScreenView.class, on = Platform.MOBILE_IOS)
public class CustomerSupportScreenView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(
      xpath = "//XCUIElementTypeOther[@name=\"Contact us | Peet's Coffee\"]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//*[@text=\"Feel free to contact us using the email form. We'll respond to you shortly.\"]",
      on = Platform.MOBILE_ANDROID)
  protected Text headingText;

  @Locate(xpath = "//XCUIElementTypeButton[@name=\"Done\"]", on = Platform.MOBILE_IOS)
  protected Text doneButton;

  @Locate(id = "com.android.chrome:id/positive_button", on = Platform.MOBILE_ANDROID)
  protected Text allowLocationToBrowser;

  @Locate(
      id = "com.android.permissioncontroller:id/permission_allow_foreground_only_button",
      on = Platform.MOBILE_ANDROID)
  protected Text allowLocationToBrowser2;

  @Locate(xpath = "//XCUIElementTypeButton[@name=\"Close\"]", on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "(//android.webkit.WebView//android.widget.Button[@text='Close'])[1] | //*[@resource-id='svg-close-button'] | //span[@class='close-button']",
      on = Platform.MOBILE_ANDROID)
  protected Button closeAdvPopUpButton;

  /* -------- Actions -------- */

  public void afterInit() {
    SyncHelper.wait(Until.uiElement(headingText).present().setTimeout(Duration.ofSeconds(12)));
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
    return ComponentFactory.create(HelpAndFeedbackView.class);
  }
}

class AndroidCustomerSupportScreenView extends CustomerSupportScreenView {
  public void afterInit() {
    AndroidDriver androidDriver = ((AndroidDriver) DriverManager.getDriver());
    logger.info("Orientation: " + androidDriver.getOrientation());
    logger.info("Orientation: Forcing to PORTRAIT");
    androidDriver.rotate(ScreenOrientation.PORTRAIT);
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
    logger.info("Close popup");
    closeAdvPopUpButton.click();
    SyncHelper.wait(Until.uiElement(headingText).present().setTimeout(Duration.ofSeconds(12)));
  }

  @Override
  public HelpAndFeedbackView done() {
    logger.info("Tap 'Done' button");
    MobileHelper.tapAndroidDeviceBackButton();
    return ComponentFactory.create(HelpAndFeedbackView.class);
  }
}
