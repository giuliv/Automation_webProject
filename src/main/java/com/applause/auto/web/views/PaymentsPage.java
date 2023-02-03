package com.applause.auto.web.views;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.dto.BillingAddressDto;
import com.applause.auto.common.data.dto.CreditCardDto;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.RadioButton;
import com.applause.auto.pageobjectmodel.elements.SelectList;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.factory.LazyList;
import com.applause.auto.web.helpers.WebHelper;
import io.qameta.allure.Step;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Implementation(is = PaymentsPage.class, on = Platform.WEB)
@Implementation(is = PaymentsPageAndroid.class, on = Platform.WEB_MOBILE_PHONE)
@Implementation(is = PaymentsPageIOS.class, on = Platform.WEB_IOS_PHONE)
public class PaymentsPage extends Base {

  @Locate(xpath = "(//button[@id='continue_button'])[1]", on = Platform.WEB)
  protected ContainerElement mainContainer;

  @Locate(css = "iframe[id*='card-fields-number']", on = Platform.WEB)
  protected ContainerElement iFrameCardNumber;

  @Locate(css = "input[name='number']", on = Platform.WEB)
  @Locate(
      iOSClassChain = "**/XCUIElementTypeTextField[`value == \"Card number\"`]",
      on = Platform.WEB_IOS_PHONE)
  protected TextBox cardNumber;

  @Locate(css = "iframe[id*='card-fields-name']", on = Platform.WEB)
  private ContainerElement iFrameCardName;

  @Locate(css = "input[name='name']", on = Platform.WEB)
  @Locate(
      iOSClassChain = "**/XCUIElementTypeTextField[`value == \"Name on card\"`]",
      on = Platform.WEB_IOS_PHONE)
  protected TextBox cardName;

  @Locate(css = "iframe[id*='card-fields-expiry']", on = Platform.WEB)
  private ContainerElement iFrameCardExpiration;

  @Locate(css = "input[name='expiry']", on = Platform.WEB)
  @Locate(
      iOSClassChain = "**/XCUIElementTypeTextField[`value == \"Expiration date (MM / YY)\"`]",
      on = Platform.WEB_IOS_PHONE)
  protected TextBox cardExpiration;

  @Locate(css = "iframe[id*='card-fields-verification']", on = Platform.WEB)
  protected ContainerElement iFrameCVV;

  @Locate(css = "input[name*='verification']", on = Platform.WEB)
  @Locate(
      iOSClassChain = "**/XCUIElementTypeTextField[`value == \"Security code\"`]",
      on = Platform.WEB_IOS_PHONE)
  protected TextBox cvv;

  @Locate(
      iOSClassChain =
          "**/XCUIElementTypeOther[`label == \"Field container for: Security code\"`][1]",
      on = Platform.WEB_IOS_PHONE)
  protected TextBox cvv2;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeOther[`label == \"Security code\"`][1]",
      on = Platform.WEB_IOS_PHONE)
  protected TextBox cvv3;

  @Locate(css = "fieldset div[data-same-billing-address] input", on = Platform.WEB)
  private RadioButton sameAddress;

  @Locate(css = "fieldset div[data-different-billing-address] input", on = Platform.WEB)
  private RadioButton diffAddress;

  @Locate(xpath = "(//button[@id='continue_button'])[1]", on = Platform.WEB)
  protected Button payNowButton;

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

  @Locate(xpath = "//a[contains(@class, 'previous')]", on = Platform.WEB)
  protected Button returnToShippingButton;

  @Locate(xpath = "//label[contains(., 'PayPal (Subscriptions)')]", on = Platform.WEB)
  protected Button payPalButton;

  @Locate(css = "div[data-gateway-name=credit_card]", on = Platform.WEB)
  protected Button creditCardContained;

  @Locate(css = "#order-summary  button[name=\"checkout[submit]\"]", on = Platform.WEB)
  private Button promoApplyButton;

  @Locate(css = "tr[data-giftcard-success]", on = Platform.WEB)
  private Text peetsCardSuccessMessage;

  @Locate(
      xpath = "//form//span[@class='reduction-code__text' and text()='FREESHIP']",
      on = Platform.WEB)
  private Text promoCodeSuccessMessage;

  @Locate(
      xpath = "(//form//span[@class='reduction-code__text' and text()='NEWSUB30'])[2]",
      on = Platform.WEB)
  private Text subPromoCodeSuccessMessage;

  @Locate(css = "li.reduction-code span", on = Platform.WEB)
  protected Text discountsMessage;

  @Locate(css = "li.reduction-code span", on = Platform.WEB)
  protected List<Text> discountsMessageList;

  @Locate(css = "span.order-summary-toggle__text--show > span", on = Platform.WEB_MOBILE_PHONE)
  protected Text showSummary;

  @Locate(xpath = "//a[.//span[text()='Change shipping method']]", on = Platform.WEB)
  private Button changeShippingMethodButton;

  @Locate(xpath = "//div[@data-review-section='shipping-cost']", on = Platform.WEB)
  protected Text methodType;

  @Locate(
      xpath = "//div[contains(@class, 'notice--error') and not(contains(@class, 'hidden'))]//p",
      on = Platform.WEB)
  protected Text errorMessage;

  @Locate(xpath = "//p[contains(@id, 'error-for')]", on = Platform.WEB)
  private List<Text> errorMessagesList;

  @Locate(id = "checkout_billing_address_first_name", on = Platform.WEB)
  private TextBox firstName;

  @Locate(id = "checkout_billing_address_last_name", on = Platform.WEB)
  private TextBox lastName;

  @Locate(id = "checkout_billing_address_address1", on = Platform.WEB)
  private TextBox address;

  @Locate(id = "checkout_billing_address_city", on = Platform.WEB)
  private TextBox city;

  @Locate(id = "checkout_billing_address_phone", on = Platform.WEB)
  private TextBox phone;

  @Locate(css = "select#checkout_billing_address_country", on = Platform.WEB)
  private SelectList country;

  @Locate(css = "select#checkout_billing_address_province", on = Platform.WEB)
  private SelectList stateDropdown;

  @Locate(id = "checkout_billing_address_zip", on = Platform.WEB)
  private TextBox zip;

  @Locate(id = "checkout_billing_address_zip", on = Platform.WEB)
  private Button useBillingAddressSameAsShippingButton;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper()
        .wait(
            Until.uiElement(iFrameCardNumber)
                .present()
                .setTimeout(Duration.ofSeconds(50))); // Increased time, requested by Bernadette
    SdkHelper.getSyncHelper().wait(Until.uiElement(mainContainer).visible());
    logger.info("Payments Page URL: " + getDriver().getCurrentUrl());
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

    typeCvv(Constants.WebTestData.CREDIT_CARD_CVV);
  }

  @Step("Set payment data")
  public PaymentsPage setPaymentData(CreditCardDto card) {
    logger.info("Setting payments data...");
    SdkHelper.getSyncHelper().wait(Until.uiElement(iFrameCardNumber).present());

    WebHelper.switchToIFrame(iFrameCardNumber);
    SdkHelper.getSyncHelper().wait(Until.uiElement(cardNumber).clickable());
    /**
     * TODO
     *
     * <p>Split CC number by 4 symbols
     */
    List<String> cardNumberList =
        IntStream.iterate(0, i -> i + 4)
            .limit((int) Math.ceil(card.getCardNumber().length() / 4.0))
            .mapToObj(
                i ->
                    card.getCardNumber()
                        .substring(i, Math.min(i + 4, card.getCardNumber().length())))
            .collect(Collectors.toList());
    cardNumberList.stream()
        .forEach(
            number -> {
              cardNumber.sendKeys(number);
            });

    SdkHelper.getDriver().switchTo().defaultContent();
    SdkHelper.getSyncHelper().sleep(3000); // Waits for iFrame switch ends

    WebHelper.switchToIFrameAndSetData(iFrameCardName, cardName, card.getNameOnCard());

    WebHelper.switchToIFrame(iFrameCardExpiration);
    SdkHelper.getSyncHelper().wait(Until.uiElement(cardExpiration).clickable());
    cardExpiration.sendKeys(card.getExpirationMonth());
    cardExpiration.sendKeys(card.getExpirationYear());

    SdkHelper.getDriver().switchTo().defaultContent();
    SdkHelper.getSyncHelper().sleep(3000); // Waits for iFrame switch ends

    typeCvv(card.getSecurityCode());
    return SdkHelper.create(PaymentsPage.class);
  }

  @Step("Click continue to payments")
  public AcceptancePage clickPayNow() {
    return clickOnPayNowButton(AcceptancePage.class);
  }

  @Step("Click continue to payments using payPal")
  public PayPalPage clickContinueToPaymentsPayPal() {
    return clickOnPayNowButton(PayPalPage.class);
  }

  @Step("Click payPal option")
  public void clickPayPal() {
    logger.info("Clicking to choose payPal as payment option");
    SdkHelper.getSyncHelper().wait(Until.uiElement(iFrameCardNumber).present());
    SdkHelper.getSyncHelper().wait(Until.uiElement(payPalButton).clickable()).click();
  }

  @Step("Click 'Pay Now' button")
  public <T extends BaseComponent> T clickOnPayNowButton(Class<T> clazz) {
    if (WebHelper.getTestEnvironment().equalsIgnoreCase("production")) {
      logger.info("Testcase needs to stop, running on prod[2nd Alert]");
    } else {
      logger.info("Clicking Pay Now");
      SdkHelper.getSyncHelper().wait(Until.uiElement(payNowButton).visible());
      payNowButton.click();
    }
    return SdkHelper.create(clazz);
  }

  @Step("Review same address is selected")
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

    enterGiftCard(Constants.WebTestData.PEETS_CARD);
    SdkHelper.getSyncHelper().sleep(1000); // Wait for action

    SdkHelper.getSyncHelper().wait(Until.uiElement(peetsCardNip).clickable());
    peetsCardNip.sendKeys(Constants.WebTestData.PEETS_CARD_NIP);
    SdkHelper.getSyncHelper().sleep(1000); // Wait for action

    peetsCardApplyButton.click();
    SdkHelper.getSyncHelper().sleep(1000); // Wait for action
    SdkHelper.getSyncHelper().wait(Until.uiElement(peetsCardSuccessMessage).visible());
  }

  @Step("Enter Gift Card")
  public void enterGiftCard(String giftCard) {
    SdkHelper.getSyncHelper().wait(Until.uiElement(peetsCardCode).clickable());
    logger.info("Typing [{}] into the Gift card field");
    peetsCardCode.sendKeys(giftCard);
  }

  @Step("Set free shipping promoCode")
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

  @Step("Set subscription promoCode")
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

  @Step("Check if Payment page is displayed")
  public boolean isDisplayed() {
    return WebHelper.isDisplayed(mainContainer);
  }

  @Step("Click on 'Change shipping method'")
  public ShippingPage clickOnChangeShippingMethod() {
    logger.info("Clicking on 'Change shipping method'");
    changeShippingMethodButton.click();
    return SdkHelper.create(ShippingPage.class);
  }

  @Step("Check if Peet's Card / Gift Card 'Apply' button is enabled")
  public boolean isPeetsCardApplyButtonEnabled() {
    boolean isEnabled = peetsCardApplyButton.isEnabled();
    logger.info("Apply button for Peet's Card / Gift Card is enabled - [{}]", isEnabled);
    return isEnabled;
  }

  @Step("Get Shipping Method")
  public String getShippingMethod() {
    String method =
        WebHelper.cleanString(methodType.getText().replaceAll("·", "").replaceAll("  ", " "));
    logger.info("Ship method - [{}]", method);
    return method;
  }

  @Step("Click on 'Return To Shipping' Button")
  public ShippingPage clickOnReturnToShippingButton() {
    logger.info("Clicking on 'Return To Shipping' Button");
    returnToShippingButton.click();
    return SdkHelper.create(ShippingPage.class);
  }

  @Step("Get Error Message")
  public String getErrorMessage() {
    String message = WebHelper.cleanString(errorMessage.getText());
    logger.info("Error message - [{}]", message);
    return message;
  }

  @Step("Get list of error messages")
  public List<String> getListOfErrorMessageForMandatoryFields() {
    ((LazyList<?>) errorMessagesList).initialize();
    return errorMessagesList.stream()
        .map(error -> WebHelper.cleanString(error.getText()))
        .collect(Collectors.toList());
  }

  @Step("Set checkout data")
  public PaymentsPage typeBillingAddress(BillingAddressDto addressDto) {
    logger.info("Click on Use a different billing address");
    diffAddress.click();

    logger.info("Typing [{}] first name", addressDto.getFirstName());
    firstName.clearText();
    firstName.sendKeys(addressDto.getFirstName());

    logger.info("Typing [{}] last name", addressDto.getLastName());
    lastName.clearText();
    lastName.sendKeys(addressDto.getLastName());

    typeAddress(addressDto.getStreetAddress());

    logger.info("Typing [{}] city", addressDto.getCity());
    city.clearText();
    city.sendKeys(addressDto.getCity());

    logger.info("Typing [{}] phone number", addressDto.getPhoneNumber());
    phone.clearText();
    phone.sendKeys(addressDto.getPhoneNumber());

    if (addressDto.getCountry() != null) {
      logger.info("Selecting [{}] country", addressDto.getCountry());
      country.select(Constants.WebTestData.COUNTRY);
    }

    if (addressDto.getState() != null) {
      logger.info("Selecting [{}] state", addressDto.getState());
      stateDropdown.select(addressDto.getState());
    }

    logger.info("Typing [{}] zip code", addressDto.getZipCode());
    zip.clearText();
    zip.sendKeys(addressDto.getZipCode());
    return this;
  }

  @Step("Type user Email")
  public void typeAddress(String userAddress) {
    SdkHelper.getSyncHelper().wait(Until.uiElement(address).visible());
    logger.info("Typing [{}] address", userAddress);
    address.clearText();
    address.sendKeys(userAddress);
    SdkHelper.getSyncHelper().sleep(500); // Wait for action
  }

  @Step("Type Credit Card CVV")
  public void typeCvv(String cvvCode) {
    WebHelper.switchToIFrameAndSetData(iFrameCVV, cvv, cvvCode);
  }

  @Step("Verify PayPal method is displayed")
  public boolean isPayPalMethodDisplayed() {
    return WebHelper.isDisplayed(payPalButton);
  }

  @Step("Verify CC method is displayed")
  public boolean isCreditCardMethodDisplayed() {
    return WebHelper.isDisplayed(creditCardContained);
  }

  @Step("Set Same as shipping address for billing address")
  public PaymentsPage setUseBillingAddressSameAsShippingButton() {
    WebHelper.scrollToElement(useBillingAddressSameAsShippingButton);
    useBillingAddressSameAsShippingButton.click();
    return this;
  }
}

class PaymentsPageAndroid extends PaymentsPage {

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper()
        .wait(
            Until.uiElement(iFrameCardNumber)
                .present()
                .setTimeout(Duration.ofSeconds(50))); // Increased time, requested by Bernadette
    SdkHelper.getSyncHelper().wait(Until.uiElement(mainContainer).visible());
    logger.info("Payments Page URL: " + getDriver().getCurrentUrl());

    if (WebHelper.isDisplayed(showSummary)) {
      WebHelper.scrollToElement(showSummary.getWebElement());
      logger.info("Open Order Summary Section");
      showSummary.click();
      SdkHelper.getSyncHelper().sleep(1000); // Wait for action
    }
  }

  @Override
  @Step("Type Credit Card CVV")
  public void typeCvv(String cvvCode) {
    WebHelper.switchToIFrameAndSetData(iFrameCVV, cvv, cvvCode);
  }

  @Override
  @Step("Click 'Pay Now' button")
  public <T extends BaseComponent> T clickOnPayNowButton(Class<T> clazz) {
    if (WebHelper.getTestEnvironment().equalsIgnoreCase("production")) {
      logger.info("Testcase needs to stop, running on prod[2nd Alert]");
    } else {
      logger.info("Clicking Pay Now");
      SdkHelper.getSyncHelper().wait(Until.uiElement(payNowButton).visible());
      SdkHelper.getSyncHelper().wait(Until.uiElement(payNowButton).clickable());
      WebHelper.jsClick(payNowButton.getWebElement());
    }
    return SdkHelper.create(clazz);
  }
}

class PaymentsPageIOS extends PaymentsPage {
  @Override
  @Step("Set payment data")
  public void setPaymentData() {
    logger.info("Setting payments data...");
    SdkHelper.getSyncHelper().wait(Until.uiElement(iFrameCardNumber).present());
    WebHelper.scrollToElement(iFrameCardNumber);

    String oldContext = WebHelper.getCurrentContext();
    WebHelper.switchToNativeContext();
    SdkHelper.getSyncHelper().sleep(3000); // Extra wait due to context change

    SdkHelper.getSyncHelper().wait(Until.uiElement(cardNumber).clickable());
    cardNumber.sendKeys(
        Constants.WebTestData.CREDIT_CARD_NUMBER_1
            + Constants.WebTestData.CREDIT_CARD_NUMBER_2
            + Constants.WebTestData.CREDIT_CARD_NUMBER_3
            + Constants.WebTestData.CREDIT_CARD_NUMBER_4);

    SdkHelper.getSyncHelper().wait(Until.uiElement(cardName).clickable());
    cardName.sendKeys(Constants.WebTestData.CREDIT_CARD_NAME);

    SdkHelper.getSyncHelper().wait(Until.uiElement(cardExpiration).clickable());
    cardExpiration.sendKeys(
        Constants.WebTestData.CREDIT_CARD_EXPIRATION_MONTH
            + Constants.WebTestData.CREDIT_CARD_EXPIRATION_YEAR);

    SdkHelper.getSyncHelper().wait(Until.uiElement(cvv).clickable());
    SdkHelper.getDeviceControl().tapElementCenter(cvv);
    cvv.sendKeys(Constants.WebTestData.CREDIT_CARD_CVV);

    WebHelper.switchToWeb(oldContext);
    SdkHelper.getSyncHelper().sleep(3000); // Extra wait due to context change
    WebHelper.hideKeyboard();
  }
}
