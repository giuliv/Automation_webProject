package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;

@Implementation(is = PeetsCardSettingsView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = PeetsCardSettingsView.class, on = Platform.MOBILE_IOS)
public class PeetsCardSettingsView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(xpath = "//XCUIElementTypeOther[@name=\"CARD SETTINGS\"]", on = Platform.MOBILE_IOS)
  @Locate(xpath = "//android.widget.TextView[@text='CARD SETTINGS']", on = Platform.MOBILE_ANDROID)
  protected Text getViewSignature;

  @Locate(id = "button back", on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//android.widget.ImageButton[contains(@content-desc,\"Navigate up\") or contains(@content-desc,\"Nach oben\")]",
      on = Platform.MOBILE_ANDROID)
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
    return ComponentFactory.create(PaymentMethodsView.class);
  }
}
