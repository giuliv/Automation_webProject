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

import org.openqa.selenium.ScreenOrientation;

import io.appium.java_client.android.AndroidDriver;

@Implementation(is = AndroidHelpAndFeedbackView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = HelpAndFeedbackView.class, on = Platform.MOBILE_IOS)
public class HelpAndFeedbackView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(iOSNsPredicate = "name == 'button back'", on = Platform.MOBILE_IOS)
  @Locate(xpath = "//*[contains(@content-desc, 'Navigate up')]", on = Platform.MOBILE_ANDROID)
  protected Button backArrow;

  @Locate(iOSNsPredicate = "name == 'View Our FAQs'", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/faqs", on = Platform.MOBILE_ANDROID)
  protected Button viewOurFAQs;

  @Locate(iOSNsPredicate = "name == 'Contact Customer Service'", on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.wearehathway.peets.development:id/contactCustomer",
      on = Platform.MOBILE_ANDROID)
  protected Button contactCustomerService;

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

  /* -------- Actions -------- */

  /**
   * Checking page is displayed correctly with all appropriate elements
   *
   * @return correctness of page visibility
   */
  public boolean isPageDisplayedCorrectly() {
    logger.info("Checking Help & Feedback view");
    if (!backArrow.isEnabled()) {
      logger.info("Back arrow element is not present");
      return false;
    }

    if (!viewOurFAQs.isEnabled()) {
      logger.info("View our FAQs element is not present");
      return false;
    }

    if (!contactCustomerService.isEnabled()) {
      logger.info("Contact Customer service element is not present");
      return false;
    }

    return true;
  }

  /**
   * Click View Our FAQs
   *
   * @return PeetnikRewardsLandingView
   */
  public PeetnikRewardsLandingView clickViewOurFAQs() {
    logger.info("Click View Our FAQs");
    viewOurFAQs.click();
    SyncHelper.sleep(5000);
    MobileHelper.initMobileBrowser();
    // wait till the page load, before it ios is not switched back to app
    SyncHelper.sleep(10000);
    return ComponentFactory.create(PeetnikRewardsLandingView.class);
  }

  /**
   * Contact customer service customer support screen view.
   *
   * @return the customer support screen view
   */
  public CustomerSupportScreenView contactCustomerService() {
    logger.info("Click Customer Support");
    contactCustomerService.click();
    SyncHelper.sleep(10000);
    MobileHelper.initMobileBrowser();
    return ComponentFactory.create(CustomerSupportScreenView.class);
  }
}

class AndroidHelpAndFeedbackView extends HelpAndFeedbackView {
  public PeetnikRewardsLandingView clickViewOurFAQs() {
    logger.info("Click View Our FAQs");
    viewOurFAQs.click();
    SyncHelper.sleep(5000);
    AndroidDriver androidDriver = ((AndroidDriver) DriverManager.getDriver());
    logger.info("Orientation: " + androidDriver.getOrientation());
    logger.info("Orientation: Forcing to PORTRAIT");
    androidDriver.rotate(ScreenOrientation.PORTRAIT);
    SyncHelper.sleep(5000);
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

    MobileHelper.initMobileBrowser();
    // wait till the page load, before it ios is not switched back to app
    SyncHelper.sleep(10000);
    return ComponentFactory.create(PeetnikRewardsLandingView.class);
  }
}
