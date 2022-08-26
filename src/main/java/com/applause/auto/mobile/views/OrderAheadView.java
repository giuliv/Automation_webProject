package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;

@Implementation(is = OrderAheadView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = OrderAheadView.class, on = Platform.MOBILE_IOS)
public class OrderAheadView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(id = "Order Ahead.", on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().textContains(\"Order Ahead\")",
      on = Platform.MOBILE_ANDROID)
  protected Text getHeadingText;

  @Locate(id = "Bypass the line and proceed to great coffee.", on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//*[@resource-id='com.wearehathway.peets.development:id/topContainer']/android.widget.TextView[2]",
      on = Platform.MOBILE_ANDROID)
  protected Text getSubHeaderText;

  @Locate(id = "Get Started", on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.wearehathway.peets.development:id/getStartedButton",
      on = Platform.MOBILE_ANDROID)
  protected Button getGetStartedButton;

  /**
   * Get the text vaalue of the heading
   *
   * @return
   */
  public String getHeadingTextValue() {
    return getHeadingText.getText();
  }
}
