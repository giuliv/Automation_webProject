package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.util.helper.SyncHelper;
import com.applause.auto.util.helper.sync.Until;
import java.time.Duration;

@Implementation(is = AndroidTermsAndConditionsView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = TermsAndConditionsView.class, on = Platform.MOBILE_IOS)
public class TermsAndConditionsView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(
      xpath = "//XCUIElementTypeOther[@name=\"THE PEETNIK REWARDS PROGRAM TERMS AND CONDITIONS\"]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath = "//*[contains(@text, 'THE PEETNIK REWARDS PROGRAM TERMS AND CONDITIONS')]",
      on = Platform.MOBILE_ANDROID)
  protected Text getHeadingText;

  /* -------- Actions -------- */

  /**
   * Get the text value of the heading
   *
   * @return
   */
  public String getHeadingTextValue() {
    return getHeadingText.getText();
  }
}

class AndroidTermsAndConditionsView extends TermsAndConditionsView {

  /* -------- Lifecycle Methods -------- */

  @Override
  public void afterInit() {
    SyncHelper.sleep(5000);
    // throw new RuntimeException("Not Yet Implemeted. Blocked by WEB context switching issue");
    SyncHelper.wait(Until.uiElement(getHeadingText).present().setTimeout(Duration.ofSeconds(12)));
  }
}