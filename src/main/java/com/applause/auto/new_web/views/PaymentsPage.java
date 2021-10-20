package com.applause.auto.new_web.views;

import com.applause.auto.common.data.Constants;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.RadioButton;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import io.qameta.allure.Step;

import java.util.List;

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

  @Locate(id = "checkout_reduction_code", on = Platform.WEB)
  @Locate(xpath = "(//input[@data-discount-field])[2]", on = Platform.WEB_MOBILE_PHONE)
  private TextBox promoCode;

  @Locate(id = "checkout_pin_code", on = Platform.WEB)
  private TextBox peetsCardNip;

  @Locate(id = "giftCardApply", on = Platform.WEB)
  private Button peetsCardApplyButton;

  @Locate(css = ".order-summary__section--discount button[name='button']", on = Platform.WEB)
  //  @Locate(
  //      xpath =
  // "//input[@id='checkout_reduction_code_mobile']/parent::div/following-sibling::button",
  //      on = Platform.WEB_MOBILE_PHONE)
  private Button promoApplyButton;

  @Locate(css = "tr[data-giftcard-success]", on = Platform.WEB)
  private Text peetsCardSuccessMessage;

  @Locate(
      xpath = "//form//span[@class='reduction-code__text' and text()='FREESHIP']",
      on = Platform.WEB)
  private Text promoCodeSuccessMessage;

  @Locate(
      xpath = "//form//span[@class='reduction-code__text' and text()='NEWSUB30']",
      on = Platform.WEB)
  private Text subPromoCodeSuccessMessage;

  @Locate(css = "li.reduction-code span", on = Platform.WEB)
  protected Text discountsMessage;

  @Locate(css = "li.reduction-code span", on = Platform.WEB)
  protected List<Text> discountsMessageList;

  @Locate(css = "span.order-summary-toggle__text--show > span", on = Platform.WEB_MOBILE_PHONE)
  protected Text showSummary;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(iFrameCardNumber).present());
    SdkHelper.getSyncHelper().wait(Until.uiElement(mainContainer).visible());
    logger.info("Payments Page URL: " + getDriver().getCurrentUrl());

    if (!WebHelper.isDesktop()) {
      SdkHelper.getSyncHelper().wait(Until.uiElement(showSummary).visible());
      WebHelper.scrollToElement(showSummary.getWebElement());
      logger.info("Open Order Summary Section");

      showSummary.click();
      SdkHelper.getSyncHelper().sleep(1000); // Wait for action
    }
  }

  /* -------- Actions -------- */

  @Step("Set payment data")
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

  @Step("Click continue to payments")
  public AcceptancePage clickContinueToPayments() {
    if (WebHelper.getTestEnvironment().equalsIgnoreCase("production")) {
      logger.info("Testcase needs to stop, running on prod[2nd Alert]");
    } else {
      logger.info("Clicking Pay Now");
      SdkHelper.getSyncHelper().wait(Until.uiElement(payNowButton).visible());
      payNowButton.click();

      SdkHelper.getSyncHelper().wait(Until.uiElement(payNowButton).notPresent());
    }

    return SdkHelper.create(AcceptancePage.class);
  }

  @Step("Review same addres is selected")
  public boolean isSameAddressSelected() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(sameAddress).present());
    return sameAddress.isSelected();
  }

  @Step("Get total price")
  public String getTotalPrice() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(totalPrice).present());
    logger.info("Total price: " + totalPrice.getText());

    return totalPrice.getText();
  }

  @Step("Get discount message")
  public String getDiscountMessage() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(discountsMessage).present());
    logger.info("Discount message: " + discountsMessage.getText());

    return discountsMessage.getText();
  }

  @Step("Get discount message list")
  public String getDiscountMessageByIndex(int index) {
    SdkHelper.getSyncHelper().wait(Until.uiElement(discountsMessageList.get(index)).visible());
    logger.info("[Payment Page] Discount Message: " + discountsMessageList.get(index).getText());

    return discountsMessageList.get(index).getText();
  }

  @Step("Get discount")
  public boolean isDiscountPresent() {
    return discountsMessage.exists();
  }

  @Step("Set card discount")
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

  @Step("Set free shipping promocode")
  public void setFreeShippingPromoCodeDiscount() {
    logger.info("Setting up PromoCode data");
    SdkHelper.getSyncHelper().sleep(10000); // Wait for peet's card are ready

    SdkHelper.getSyncHelper().wait(Until.uiElement(promoCode).clickable());
    promoCode.sendKeys(Constants.WebTestData.PROMO_CODE_FREE_SHIPPING);
    SdkHelper.getSyncHelper().sleep(1000); // Wait for action

    promoApplyButton.click();
    SdkHelper.getSyncHelper().sleep(1000); // Wait for action
    SdkHelper.getSyncHelper().wait(Until.uiElement(promoCodeSuccessMessage).present());
  }

  @Step("Set subscription promocode")
  public void setSubscriptionPromoCodeDiscount() {
    logger.info("Setting up PromoCode data");
    SdkHelper.getSyncHelper().sleep(10000); // Wait for peet's card are ready

    SdkHelper.getSyncHelper().wait(Until.uiElement(promoCode).clickable());
    promoCode.sendKeys(Constants.WebTestData.PROMO_CODE_SUBSCRIPTION_30);
    SdkHelper.getSyncHelper().sleep(1000); // Wait for action

    promoApplyButton.click();
    SdkHelper.getSyncHelper().sleep(1000); // Wait for action
    SdkHelper.getSyncHelper().wait(Until.uiElement(subPromoCodeSuccessMessage).present());
  }
}
