package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.helper.SyncHelper;
import com.applause.auto.util.helper.sync.Until;
import java.time.Duration;

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
  @Override
  public HelpAndFeedbackView done() {
    logger.info("Tap 'Done' button");
    MobileHelper.tapAndroidDeviceBackButton();
    return ComponentFactory.create(HelpAndFeedbackView.class);
  }
}
