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

  @Locate(xpath = "//XCUIElementTypeButton[@name=\"Done\"]", on = Platform.MOBILE_IOS)
  protected Text doneButton;

  @Override
  public void afterInit() {
    SyncHelper.sleep(5000);
    SyncHelper.wait(Until.uiElement(getHeadingText).present().setTimeout(Duration.ofSeconds(30)));
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

class AndroidPrivacyPolicyView extends PrivacyPolicyView {

  /* -------- Lifecycle Methods -------- */

  @Override
  public LegalInfoView done() {
    logger.info("Tap 'Done' button");
    MobileHelper.tapAndroidDeviceBackButton();
    return ComponentFactory.create(LegalInfoView.class);
  }
}
