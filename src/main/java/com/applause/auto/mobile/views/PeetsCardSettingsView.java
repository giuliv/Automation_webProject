package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;

@Implementation(is = PeetsCardSettingsView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = PeetsCardSettingsView.class, on = Platform.MOBILE_IOS)
public class PeetsCardSettingsView extends BaseComponent {

  /* -------- Elements -------- */

  //  @Locate(xpath = "//XCUIElementTypeOther[@name=\"CARD SETTINGS\"]", on = Platform.MOBILE_IOS)
  // //Commented[15.01.2021]
  @Locate(accessibilityId = "Settings", on = Platform.MOBILE_IOS)
  @Locate(xpath = "//android.widget.TextView[@text='CARD SETTINGS']", on = Platform.MOBILE_ANDROID)
  protected Text getViewSignature;

  //  @Locate(id = "button back", on = Platform.MOBILE_IOS) //Commented[15.01.2021]
  @Locate(iOSClassChain = "**/XCUIElementTypeButton[`label == \"Back\"`]", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/backBtn", on = Platform.MOBILE_ANDROID)
  protected Button getBackButton;

  /* -------- Actions -------- */

  /**
   * Click Back Button
   *
   * @return PaymentMethodsView
   */
  public PaymentMethodsView clickBackButton() {
    logger.info("Click Back Button");
    getBackButton.click();
    return SdkHelper.create(PaymentMethodsView.class);
  }
}
