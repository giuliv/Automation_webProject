package com.applause.auto.web.views;

import com.applause.auto.common.data.Constants;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.integrations.helpers.SdkHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Checkbox;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.SelectList;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;



import com.applause.auto.pageobjectmodel.helper.sync.Until;
import com.applause.auto.web.helpers.WebHelper;

@Implementation(is = CheckoutPaymentMethodPage.class, on = Platform.WEB)
public class CheckoutPaymentMethodPage extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(css = "h2#checkout-title-opc-billing.active", on = Platform.WEB)
  private Text getViewSignature;

  @Locate(css = "#pc-title > label", on = Platform.WEB)
  private Checkbox getPeetsCardCheckbox;

  @Locate(css = "#custompayment_pc_number", on = Platform.WEB)
  private TextBox getPeetsCardNumberTextBox;

  @Locate(css = "#custompayment_pc_pin", on = Platform.WEB)
  private TextBox getPeetsCardPinTextBox;

  @Locate(css = "#custompayment_pc_amount", on = Platform.WEB)
  private TextBox getPeetsCardAmountTextBox;

  @Locate(xpath = "//input[contains(@id, 'stored_pc_amount')]")
  private TextBox getStoredPeetsCardAmountTextBox;

  @Locate(css = "#cc-title > label", on = Platform.WEB)
  private Checkbox getDebitCreditCardCheckbox;

  @Locate(css = "#custompayment_cc_number", on = Platform.WEB)
  private TextBox getCardNumberTextBox;

  @Locate(css = "#custompayment_cc_cid", on = Platform.WEB)
  private TextBox getCardSecurityCodeTextBox;

  @Locate(css = "input[id*='stored_cc_cid'][class*='validate-cc-cvn']", on = Platform.WEB)
  private TextBox getValidateCSCTextBox;

  @Locate(css = "#custompayment_expiration", on = Platform.WEB)
  private SelectList getCardExpMonthSelectList;

  @Locate(css = "#custompayment_expiration_yr", on = Platform.WEB)
  private SelectList getCardExpYearSelectList;

  @Locate(css = "#custompayment_cc_owner", on = Platform.WEB)
  private TextBox getNameOnCardTextBox;

  @Locate(xpath = "//input[contains(@id,'same_as_shipping')]", on = Platform.WEB)
  private Checkbox getBillShippingAddressCheckbox;

  @Locate(css = "input[id='billing:email']", on = Platform.WEB)
  private TextBox getEmailTextBox;

  @Locate(css = "#opc-billing button[title='Continue']", on = Platform.WEB)
  private Button getContinueButton;

  @Locate(css = "#payment-button-continue", on = Platform.WEB)
  private Button getContinuePaymentButton;

  @Locate(css = "opc-please-wait", on = Platform.WEB)
  private ContainerElement getBillingLoadingSpinner;

  @Locate(css = "peets-card-please-wait", on = Platform.WEB)
  private ContainerElement getPeetsCardLoadingSpinner;

  @Locate(css = "#cc-container", on = Platform.WEB)
  private ContainerElement creditCardContainer;

  @Locate(css = "div.billing-address-item.highlight")
  private ContainerElement selectedBillingAddress;

  @Locate(xpath = "div.billing-address-item:first-child")
  private ContainerElement firstBillingAddress;

  @Locate(css = "#shopping-cart-totals-table .total-price .price")
  private ContainerElement cartTotalPrice;

  /* -------- Actions -------- */

  /** Continue after entering Peets Card info */
  public CheckoutPlaceOrderPage continueAfterFillingPeetsCardInfo(String amount) {
    logger.info("Clicking Continue after filling Peets Card info");
    selectPeetsCardOption();
    fillPeetsCardInfo(amount);
    fillEmailField();
    continueAfterBillingInfo();
    return this.create(CheckoutPlaceOrderPage.class);
  }

  /** Select Peets Card as payment option */
  public void selectPeetsCardOption() {
    logger.info("Selecting the Peets card Checkbox");
    if (!getPeetsCardCheckbox.isChecked()) {
      getPeetsCardCheckbox.click();
    }
  }

  /** Fill Peets Card Billing Info */
  public void fillPeetsCardInfo(String amount) {
    logger.info("Filling Peets Card info");
    String peetsCardNumber =
        (SdkHelper.getEnvironmentHelper().isSafari())
            ? Constants.TestData.PEETS_CARD_NUMBER_SAFARI
            : Constants.TestData.PEETS_CARD_NUMBER_CHROME;
    getPeetsCardNumberTextBox.sendKeys(peetsCardNumber);
    String peetsCardPin =
        (SdkHelper.getEnvironmentHelper().isSafari())
            ? Constants.TestData.PEETS_CARD_PIN_SAFARI
            : Constants.TestData.PEETS_CARD_PIN_CHROME;
    getPeetsCardPinTextBox.sendKeys(peetsCardPin);

    // Peets card loads its balance after clicking outside the Peets Card fields
    getPeetsCardNumberTextBox.click();

    // Waits for an animation while the element is displayed
    getSyncHelper().wait(Until.uiElement(getPeetsCardAmountTextBox).present());
    getPeetsCardAmountTextBox.sendKeys(amount);
  }

  /** Fill Credit Card PIN and continue */
  public CheckoutPlaceOrderPage continueAfterEnteringPIN() {
    logger.info("Entering Credit Card PIN");
    getValidateCSCTextBox.sendKeys(Constants.TestData.VISA_CC_SECURITY_CODE);
    clickOnFirstBillingAddress();
    getContinuePaymentButton.exists();
    getContinuePaymentButton.click();
    return this.create(CheckoutPlaceOrderPage.class);
  }

  /** Continue after entering required Billing info */
  public CheckoutPlaceOrderPage continueAfterFillingRequiredBillingInfo() {
    logger.info("Clicking Continue after filling Billing info");
    selectDebitCreditCardOption();
    fillBillingInfo();
    fillEmailField();
    continueAfterBillingInfo();
    return this.create(CheckoutPlaceOrderPage.class);
  }

  /** Continue after entering required Billing info */
  public CheckoutPlaceOrderPage continueAfterFillingRequiredBillingInfoLoggedIn() {
    logger.info("Clicking Continue after filling Billing info");
    fillBillingInfo();
    fillEmailField();
    continueAfterBillingInfo();
    return this.create(CheckoutPlaceOrderPage.class);
  }

  /** Continue after entering Credit Card Billing info */
  public CheckoutPlaceOrderPage continueAfterCrediCardBillingInfo() {
    logger.info("Clicking Continue after filling Credit Card Billing info");
    selectDebitCreditCardOption();
    fillBillingInfo();
    getContinuePaymentButton.click();
    return this.create(CheckoutPlaceOrderPage.class);
  }

  /** Enter an email-alias based on email seed */
  public void fillEmailField() {
    long timeStamp = System.currentTimeMillis();
    String email = String.format(Constants.TestData.EMAIL, timeStamp);
    getEmailTextBox.sendKeys(email);
  }

  /** Select Debit/Credit Card as payment option */
  public void selectDebitCreditCardOption() {
    logger.info("Selecting the Debit/Credit card Checkbox");
    if (!creditCardContainer.isDisplayed()) {
      getDebitCreditCardCheckbox.click();
    }
  }

  /** Fill Required Fields for Billing Info */
  public void fillBillingInfo() {
    logger.info("Filling Billing info");
    getCardNumberTextBox.sendKeys(Constants.TestData.AMEX_CC_NUM);
    getCardSecurityCodeTextBox.sendKeys(Constants.TestData.AMEX_CC_CODE);
    // Cant select the month value due its content. Workaround was to select its value
    WebHelper.jsSelectByValue(
        getCardExpMonthSelectList.getWebElement(), Constants.TestData.AMEX_CC_MONTH);
    WebHelper.jsSelect(getCardExpYearSelectList.getWebElement(), Constants.TestData.AMEX_CC_YEAR);
    getNameOnCardTextBox.sendKeys(Constants.TestData.VISA_CC_NAME);
  }

  /** Select Billing Address same as Shipping Address */
  public void selectBilligShippingAddress() {
    logger.info("Select Billing Address Same as Shipping Address");
    if (!getBillShippingAddressCheckbox.isChecked()) {
      getBillShippingAddressCheckbox.click();
    }
  }

  /** Continue after entering Peets and Credit Card Billing Info */
  public CheckoutPlaceOrderPage continueAfterFillingPeetsAndCreditInfo() {
    logger.info("Clicking Continue after filling Peets Card and Credit Card info");
    selectPeetsCardOption();
    // Using Peet's Card lowest amount, including order total decimals, to avoid payment issues with
    // credit card
    String totalPriceDecimals = cartTotalPrice.getText().split("\\.")[1];
    String peetsAmount =
        Constants.TestData.PEETS_CARD_LOWEST_AMOUNT.concat("." + totalPriceDecimals);
    fillPeetsCardInfo(peetsAmount);
    getSyncHelper().sleep(5000);
    selectDebitCreditCardOption();
    fillBillingInfo();
    fillEmailField();
    continueAfterBillingInfo();
    return this.create(CheckoutPlaceOrderPage.class);
  }

  /** Continue after entering Peets and Credit Card Billing Info */
  public CheckoutPlaceOrderPage continueAfterFillingPeetsAndCreditInfoLoggedIn() {
    logger.info("Clicking Continue after filling Peets Card and Credit Card info");
    selectPeetsCardOption();
    // Using Peet's Card lowest amount, including order total decimals, to avoid payment issues with
    // credit card
    String totalPriceDecimals = cartTotalPrice.getText().split("\\.")[1];
    String peetsAmount =
        Constants.TestData.PEETS_CARD_LOWEST_AMOUNT.concat("." + totalPriceDecimals);
    getSyncHelper().wait(Until.uiElement(getStoredPeetsCardAmountTextBox).present());
    getStoredPeetsCardAmountTextBox.sendKeys(peetsAmount);
    selectDebitCreditCardOption();
    continueAfterEnteringPIN();
    return this.create(CheckoutPlaceOrderPage.class);
  }

  /** Click continue after billing info section */
  public void continueAfterBillingInfo() {
    logger.info("Click Continue on billing section");
    getContinueButton.click();
  }

  public void clickOnFirstBillingAddress() {
    if (!selectedBillingAddress.isDisplayed()) {
      logger.info("Click on first billing address");
      getSyncHelper().wait(Until.uiElement(firstBillingAddress).clickable()).click();
    }
  }
}
