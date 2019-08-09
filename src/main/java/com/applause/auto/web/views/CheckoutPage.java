package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;

@Implementation(is = CheckoutPage.class, on = Platform.WEB)
public class CheckoutPage extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(css = "div.default-message-page.login div.page-title h1", on = Platform.WEB)
  private Text getViewSignature;

  @Locate(css = "div.new-users button", on = Platform.WEB)
  private Button getClickContinueAsGuestButton;

  /* -------- Actions -------- */

  /**
   * Click continue as Guest
   *
   * @return CheckoutShippingInfoPage
   */
  public CheckoutShippingInfoPage clickContinueAsGuest() {
    logger.info("Click Continue as Guest");
    getClickContinueAsGuestButton.click();
    return ComponentFactory.create(CheckoutShippingInfoPage.class);
  }
}
