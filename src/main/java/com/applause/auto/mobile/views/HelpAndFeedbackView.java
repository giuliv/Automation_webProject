package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.ScreenOrientation;

@Implementation(is = AndroidHelpAndFeedbackView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = HelpAndFeedbackView.class, on = Platform.MOBILE_IOS)
public class HelpAndFeedbackView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(accessibilityId = "button back", on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//*[contains(@content-desc, 'Navigate up') or contains(@content-desc, 'Revenir en arrière')]",
      on = Platform.MOBILE_ANDROID)
  protected Button backArrow;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeNavigationBar[`name == \"HELP & FEEDBACK\"`]",
      on = Platform.MOBILE_IOS)
  @Locate(xpath = "//*[contains(@content-desc, 'Navigate up')]", on = Platform.MOBILE_ANDROID)
  protected Text helpAndFeedbackHeader;

  @Locate(accessibilityId = "View Our FAQs", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/faqs", on = Platform.MOBILE_ANDROID)
  protected Button viewOurFAQs;

  @Locate(accessibilityId = "Contact Customer Service", on = Platform.MOBILE_IOS)
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

  /* -------- Actions -------- */

  /**
   * Checking page is displayed correctly with all appropriate elements
   *
   * @return correctness of page visibility
   */
  public boolean isPageDisplayedCorrectly() {
    logger.info("Checking Help & Feedback view");
    if (!MobileHelper.isElementDisplayed(backArrow, 30)) {
      logger.info("Back arrow element is not present");
      return false;
    }

    if (!helpAndFeedbackHeader.isEnabled()) {
      logger.info("HELP & FEEDBACK header is not present");
      return false;
    }

    if (!viewOurFAQs.isEnabled()) {
      logger.info("View Our FAQs element is not present");
      return false;
    }

    if (!contactCustomerService.isEnabled()) {
      logger.info("Contact Customer Service element is not present");
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
    MobileHelper.initMobileBrowser();
    // wait till the page load, before it ios is not switched back to app
    SdkHelper.getSyncHelper().sleep(10000);
    return SdkHelper.create(PeetnikRewardsLandingView.class);
  }

  /**
   * Contact customer service customer support screen view.
   *
   * @return the customer support screen view
   */
  public CustomerSupportScreenView contactCustomerService() {
    logger.info("Click Customer Support");
    contactCustomerService.click();
    SdkHelper.getSyncHelper().sleep(10000);
    MobileHelper.initMobileBrowser();
    return SdkHelper.create(CustomerSupportScreenView.class);
  }
}

class AndroidHelpAndFeedbackView extends HelpAndFeedbackView {
  public PeetnikRewardsLandingView clickViewOurFAQs() {
    logger.info("Click View Our FAQs");
    viewOurFAQs.click();
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

    MobileHelper.initMobileBrowser();
    // wait till the page load, before it ios is not switched back to app
    SdkHelper.getSyncHelper().sleep(10000);
    return SdkHelper.create(PeetnikRewardsLandingView.class);
  }
}
