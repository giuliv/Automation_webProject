package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.util.helper.SyncHelper;
import com.applause.auto.util.helper.sync.Until;
import java.time.Duration;

@Implementation(is = AndroidPrivacyPolicyView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = PrivacyPolicyView.class, on = Platform.MOBILE_IOS)
public class PrivacyPolicyView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(
      xpath = "//XCUIElementTypeOther[contains(@name,\"PRIVACY POLICY\")][1]",
      on = Platform.MOBILE_IOS)
  @Locate(xpath = "//*[contains(@text, \"PRIVACY POLICY\")]", on = Platform.MOBILE_ANDROID)
  protected Text getHeadingText;
}

class AndroidPrivacyPolicyView extends PrivacyPolicyView {

  /* -------- Lifecycle Methods -------- */

  @Override
  public void afterInit() {
    SyncHelper.sleep(5000);
    // throw new RuntimeException("Not Yet Implemeted. Blocked by WEB context switching issue");
    SyncHelper.wait(Until.uiElement(getHeadingText).present().setTimeout(Duration.ofSeconds(12)));
  }
}
