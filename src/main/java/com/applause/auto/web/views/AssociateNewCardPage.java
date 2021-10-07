package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.TextBox;

@Implementation(is = AssociateNewCardPage.class, on = Platform.WEB)
public class AssociateNewCardPage extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(css = "#card_number", on = Platform.WEB)
  private TextBox getCardNumberField;

  @Locate(css = "#pin_number", on = Platform.WEB)
  private TextBox getPinNumberField;

  @Locate(css = "button.btn-dark", on = Platform.WEB)
  private Button getAssociateCardButton;

  /* -------- Actions -------- */

  /**
   * Enter Peets Card Number
   *
   * @param cardNum
   */
  public void enterCardNumber(String cardNum) {
    logger.info("Entering card number");
    getCardNumberField.sendKeys(cardNum);
  }

  /**
   * Enter Peets PIN Number
   *
   * @param pinNum
   */
  public void enterPinNumber(String pinNum) {
    logger.info("Entering PIN number");
    getPinNumberField.sendKeys(pinNum);
  }

  /**
   * Click Associate Card
   *
   * @return PaymentMethodsPage
   */
  public PaymentMethodsPage clickAssociateCard() {
    logger.info("Clicking Associate Card");
    getAssociateCardButton.click();
    return SdkHelper.create(PaymentMethodsPage.class);
  }
}
