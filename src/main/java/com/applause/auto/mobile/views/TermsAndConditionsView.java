package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.mobile.components.BrowserPopup;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Text;
import java.time.Duration;

@Implementation(is = AndroidTermsAndConditionsView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = TermsAndConditionsView.class, on = Platform.MOBILE_IOS)
public class TermsAndConditionsView extends BaseComponent {

  @Locate(
      xpath = "//XCUIElementTypeOther[@name=\"Terms And Conditions | Peet's Coffee\"]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath = "//*[contains(@text, \"Terms & Conditions | Peet's Coffee\")][1]",
      on = Platform.MOBILE_ANDROID)
  protected Text getHeadingText;

  @Locate BrowserPopup browserPopup;

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
    browserPopup.closeBrowserOptionPopup();
    browserPopup.closeLocationPopup();

    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(getHeadingText).present().setTimeout(Duration.ofSeconds(60)));
  }
}
