package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.helper.sync.Until;
import java.time.Duration;

@Implementation(is = AndroidTermsAndConditionsView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = TermsAndConditionsView.class, on = Platform.MOBILE_IOS)
public class TermsAndConditionsView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(
      xpath = "//XCUIElementTypeOther[@name=\"Terms And Conditions | Peet's Coffee\"]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath = "//*[contains(@text, \"Terms And Conditions | Peet's Coffee\")]",
      on = Platform.MOBILE_ANDROID)
  protected Text getHeadingText;

  @Locate(id = "android:id/button_once", on = Platform.MOBILE_ANDROID)
  protected Text chromeBrowserOptionButton;

  @Locate(id = "com.android.chrome:id/positive_button", on = Platform.MOBILE_ANDROID)
  protected Text allowLocationToBrowser;

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
    try {
      chromeBrowserOptionButton.click();
    } catch (Throwable th) {
      logger.info("No browser popup overlay found");
    }
    try {
      allowLocationToBrowser.click();
    } catch (Throwable th) {
      logger.info("No location popup overlay found");
    }
    getSyncHelper().sleep(5000);
    getSyncHelper()
        .wait(Until.uiElement(getHeadingText).present().setTimeout(Duration.ofSeconds(12)));
  }
}
