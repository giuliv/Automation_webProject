package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.helper.SyncHelper;

@Implementation(is = HelpAndFeedbackView.class, on = Platform.MOBILE_ANDROID)
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
    SyncHelper.sleep(10000);
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
