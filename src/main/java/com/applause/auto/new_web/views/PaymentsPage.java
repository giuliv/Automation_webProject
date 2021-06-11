package com.applause.auto.new_web.views;

import com.applause.auto.common.data.Constants;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.*;

@Implementation(is = PaymentsPage.class, on = Platform.WEB)
@Implementation(is = PaymentsPage.class, on = Platform.WEB_MOBILE_PHONE)
public class PaymentsPage extends Base {

  @Locate(xpath = "(//button[@id='continue_button'])[1]", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Locate(css = "iframe[id*='card-fields-number']", on = Platform.WEB)
  private ContainerElement iFrameCardNumber;

  @Locate(css = "input[name='number']", on = Platform.WEB)
  private TextBox cardNumber;

  @Locate(css = "iframe[id*='card-fields-name']", on = Platform.WEB)
  private ContainerElement iFrameCardName;

  @Locate(css = "input[name='name']", on = Platform.WEB)
  private TextBox cardName;

  @Locate(css = "iframe[id*='card-fields-expiry']", on = Platform.WEB)
  private ContainerElement iFrameCardExpiration;

  @Locate(css = "input[name='expiry']", on = Platform.WEB)
  private TextBox cardExpiration;

  @Locate(css = "iframe[id*='card-fields-verification']", on = Platform.WEB)
  private ContainerElement iFrameCVV;

  @Locate(css = "input[name*='verification']", on = Platform.WEB)
  private TextBox cvv;

  @Locate(css = "fieldset div[data-same-billing-address] input", on = Platform.WEB)
  private RadioButton sameAddress;

  @Locate(css = "fieldset div[data-different-billing-address] input", on = Platform.WEB)
  private RadioButton diffAddress;

  @Locate(xpath = "(//button[@id='continue_button'])[1]", on = Platform.WEB)
  private Button payNowButton;

  @Locate(css = ".total-line .payment-due__price", on = Platform.WEB)
  @Locate(css = "span[class*='final-price']", on = Platform.WEB_MOBILE_PHONE)
  private Text totalPrice;

  @Locate(id = "checkout_giftcard_code", on = Platform.WEB)
  private TextBox peetsCardCode;

  @Locate(id = "checkout_pin_code", on = Platform.WEB)
  private TextBox peetsCardNip;

  @Locate(id = "giftCardApply", on = Platform.WEB)
  private Button peetsCardApplyButton;

  @Locate(css = "tr[data-giftcard-success]", on = Platform.WEB)
  private Text peetsCardSuccessMessage;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(mainContainer).visible());
    logger.info("Payments Page URL: " + getDriver().getCurrentUrl());
  }

  /* -------- Actions -------- */

  public void setPaymentData() {
    logger.info("Setting payments data...");
    SdkHelper.getSyncHelper().wait(Until.uiElement(iFrameCardNumber).present());

    WebHelper.switchToIFrame(iFrameCardNumber);
    SdkHelper.getSyncHelper().wait(Until.uiElement(cardNumber).clickable());
    cardNumber.sendKeys(Constants.WebTestData.CREDIT_CARD_NUMBER_1);
    cardNumber.sendKeys(Constants.WebTestData.CREDIT_CARD_NUMBER_2);
    cardNumber.sendKeys(Constants.WebTestData.CREDIT_CARD_NUMBER_3);
    cardNumber.sendKeys(Constants.WebTestData.CREDIT_CARD_NUMBER_4);

    SdkHelper.getDriver().switchTo().defaultContent();
    SdkHelper.getSyncHelper().sleep(3000); // Waits for iFrame switch ends

    WebHelper.switchToIFrameAndSetData(
        iFrameCardName, cardName, Constants.WebTestData.CREDIT_CARD_NAME);

    WebHelper.switchToIFrame(iFrameCardExpiration);
    SdkHelper.getSyncHelper().wait(Until.uiElement(cardExpiration).clickable());
    cardExpiration.sendKeys(Constants.WebTestData.CREDIT_CARD_EXPIRATION_MONTH);
    cardExpiration.sendKeys(Constants.WebTestData.CREDIT_CARD_EXPIRATION_YEAR);

    SdkHelper.getDriver().switchTo().defaultContent();
    SdkHelper.getSyncHelper().sleep(3000); // Waits for iFrame switch ends

    WebHelper.switchToIFrameAndSetData(iFrameCVV, cvv, Constants.WebTestData.CREDIT_CARD_CVV);
  }

  public AcceptancePage clickContinueToPayments() {
    logger.info("Clicking Pay Now");
    SdkHelper.getSyncHelper().wait(Until.uiElement(payNowButton).visible());
    payNowButton.click();

    return SdkHelper.create(AcceptancePage.class);
  }

  public boolean isSameAddressSelected() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(sameAddress).present());
    return sameAddress.isSelected();
  }

  public String getTotalPrice() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(totalPrice).present());
    logger.info("Total price: " + totalPrice.getText());

    return totalPrice.getText();
  }

  public void setPeetsCardDiscount() {
    logger.info("Setting up Peet's Card data");
    SdkHelper.getSyncHelper().sleep(10000); // Wait for peet's card are ready

    SdkHelper.getSyncHelper().wait(Until.uiElement(peetsCardCode).clickable());
    peetsCardCode.sendKeys(Constants.WebTestData.PEETS_CARD);
    SdkHelper.getSyncHelper().sleep(1000); // Wait for action

    SdkHelper.getSyncHelper().wait(Until.uiElement(peetsCardNip).clickable());
    peetsCardNip.sendKeys(Constants.WebTestData.PEETS_CARD_NIP);
    SdkHelper.getSyncHelper().sleep(1000); // Wait for action

    peetsCardApplyButton.click();
    SdkHelper.getSyncHelper().sleep(1000); // Wait for action
    SdkHelper.getSyncHelper().wait(Until.uiElement(peetsCardSuccessMessage).visible());
  }
}
