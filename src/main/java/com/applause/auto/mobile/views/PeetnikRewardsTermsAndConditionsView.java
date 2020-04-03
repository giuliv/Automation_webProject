package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.helper.SyncHelper;
import com.applause.auto.util.helper.sync.Until;
import java.time.Duration;

@Implementation(
    is = AndroidPeetnikRewardsTermsAndConditionsView.class,
    on = Platform.MOBILE_ANDROID)
@Implementation(is = PeetnikRewardsTermsAndConditionsView.class, on = Platform.MOBILE_IOS)
public class PeetnikRewardsTermsAndConditionsView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(
      xpath =
          "//XCUIElementTypeStaticText[@name=\"THE PEETNIK REWARDS PROGRAM TERMS AND CONDITIONS\"]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath = "//*[contains(@text, \"THE PEETNIK REWARDS PROGRAM TERMS AND CONDITIONS\")]",
      on = Platform.MOBILE_ANDROID)
  protected Text getHeadingText;

  @Locate(xpath = "//XCUIElementTypeButton[@name=\"Done\"]", on = Platform.MOBILE_IOS)
  protected Button doneButton;

  @Override
  public void afterInit() {
    SyncHelper.sleep(5000);
    SyncHelper.wait(Until.uiElement(getHeadingText).present().setTimeout(Duration.ofSeconds(12)));
  }

  /**
   * Done legal info view.
   *
   * @return the legal info view
   */
  public LegalInfoView done() {
    logger.info("Tap 'Done' button");
    doneButton.click();
    return ComponentFactory.create(LegalInfoView.class);
  }
}

class AndroidPeetnikRewardsTermsAndConditionsView extends PeetnikRewardsTermsAndConditionsView {

  /* -------- Lifecycle Methods -------- */

  @Override
  public LegalInfoView done() {
    logger.info("Tap 'Done' button");
    MobileHelper.tapAndroidDeviceBackButton();
    return ComponentFactory.create(LegalInfoView.class);
  }
}
