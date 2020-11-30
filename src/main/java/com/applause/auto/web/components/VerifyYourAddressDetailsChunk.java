package com.applause.auto.web.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.web.views.CheckoutShippingInfoPage;

@Implementation(is = VerifyYourAddressDetailsChunk.class, on = Platform.WEB)
public class VerifyYourAddressDetailsChunk extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(css = ".verification-form", on = Platform.WEB)
  private Text getViewSignature;

  @Locate(css = "button[title='Use Address As Entered *']", on = Platform.WEB)
  private Button getUseEnteredAddressButton;

  /* -------- Actions -------- */

  /**
   * Click Use Entered Address button
   *
   * @return CheckoutShippingInfoPage
   */
  public CheckoutShippingInfoPage clickEnteredAddressButton() {
    logger.info("Clicking Use Entered Address Button");
    getUseEnteredAddressButton.click();
    return this.create(CheckoutShippingInfoPage.class);
  }
}
