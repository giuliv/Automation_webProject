package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;

@Implementation(is = PeetsCardSettingsView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = PeetsCardSettingsView.class, on = Platform.MOBILE_IOS)
public class PeetsCardSettingsView extends BaseComponent {

  /* -------- Elements -------- */

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
