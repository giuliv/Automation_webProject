package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;

@Implementation(is = CheckoutPlaceOrderPage.class, on = Platform.WEB)
public class CheckoutPlaceOrderPage extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(css = "button[title='Place Order']", on = Platform.WEB)
  private Button getPlaceOrderButton;

  @Locate(css = "#gift-message-whole-message", on = Platform.WEB)
  private TextBox getGiftMessageTextBox;

  @Locate(
      css = "#cart-table-standard > tbody > tr > td.product-info-cell.last > h3 > a",
      on = Platform.WEB)
  private Text getProductNameText;

  @Locate(css = "span#opc-please-wait.please-wait-review", on = Platform.WEB)
  private ContainerElement getPlaceOrderSpinner;

  @Locate(
      css =
          "body > div.campaign.is-email-layout > div > div.popup-close.is-solid.js-offer-close.ac-offer-close",
      on = Platform.WEB)
  private Button getClosePopupButton;

  /* -------- Actions -------- */

  /**
   * Click Place Order Button
   *
   * @return CheckoutConfirmationPage
   */
  public CheckoutConfirmationPage placeOrder() {
    logger.info("Click Place Order Button");
    SdkHelper.getSyncHelper().wait(Until.uiElement(getPlaceOrderButton).present());
    SdkHelper.getSyncHelper().sleep(7000); // Required time to trigger spinner animation if shown
    getPlaceOrderButton.click();
    SdkHelper.getSyncHelper().sleep(2000); // Required time to trigger spinner animation if shown
    return SdkHelper.create(CheckoutConfirmationPage.class);
  }

  /**
   * Click Place Order Button without payment method selected
   *
   * @return CheckoutPaymentMethodPage
   */
  public CheckoutPaymentMethodPage placeOrderMissingPayment() {
    logger.info("Click Place Order Button");
    getPlaceOrderButton.click();
    return SdkHelper.create(CheckoutPaymentMethodPage.class);
  }

  /**
   * Get Gift Order Message
   *
   * @return String
   */
  public String getGiftMessage() {
    logger.info("Getting gift Message");
    return getGiftMessageTextBox.getCurrentText();
  }

  /**
   * Get Product Name
   *
   * @return String
   */
  public String getProductName() {
    logger.info("Getting product name");
    return getProductNameText.getText();
  }
}
