package com.applause.auto.web.views;

import com.applause.auto.common.data.Constants;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.pageframework.util.webDrivers.BrowserType;
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
import com.applause.auto.util.helper.SyncHelper;
import com.applause.auto.web.helpers.WebHelper;
import java.lang.invoke.MethodHandles;

@Implementation(is = CheckoutPaymentMethodPage.class, on = Platform.WEB_DESKTOP)
@Implementation(is = CheckoutPaymentMethodPage.class, on = Platform.WEB_MOBILE_TABLET)
@Implementation(is = CheckoutPaymentMethodPage.class, on = Platform.WEB_MOBILE_PHONE)
public class CheckoutPaymentMethodPage extends BaseComponent {

	WebHelper webHelper = new WebHelper();

	/*
	 * Public Actions
	 */

	/**
	 * Continue after entering Peets Card info
	 *
	 */
	public CheckoutPlaceOrderPage continueAfterFillingPeetsCardInfo(String amount) {
		logger.info("Clicking Continue after filling Peets Card info");
		selectPeetsCardOption();
		fillPeetsCardInfo(amount);
		fillEmailField();
		continueAfterBillingInfo();
		return ComponentFactory.create(CheckoutPlaceOrderPage.class);
	}

	/**
	 * Select Peets Card as payment option
	 *
	 */
	public void selectPeetsCardOption() {
		logger.info("Selecting the Peets card Checkbox");
		if (!getPeetsCardCheckbox.isSelected()) {
			getPeetsCardCheckbox.click();
		}
	}

	/**
	 * Fill Peets Card Billing Info
	 *
	 */
	public void fillPeetsCardInfo(String amount) {
		logger.info("Filling Peets Card info");

		String peetsCardNumber = (env.getBrowserType() == BrowserType.SAFARI)
				? Constants.TestData.PEETS_CARD_NUMBER_SAFARI_1 : Constants.TestData.PEETS_CARD_NUMBER_CHROME_1;
		getPeetsCardNumberTextBox.sendKeys(peetsCardNumber);
		String peetsCardPin = (env.getBrowserType() == BrowserType.SAFARI)
				? Constants.TestData.PEETS_CARD_PIN_SAFARI_1 : Constants.TestData.PEETS_CARD_PIN_CHROME_1;
		getPeetsCardPinTextBox.sendKeys(peetsCardPin);
		// Peets card loads its balance after clicking outside the Peets Card fields
		getPeetsCardNumberTextBox.click();
		// Waits for an animation while the element is displayed
		SyncHelper.sleep(2000);
		SyncHelper.waitUntilElementPresent(getLocator(this, "getPeetsCardAmountTextBox"));
		getPeetsCardAmountTextBox.sendKeys(amount);
	}

	/**
	 * Fill Credit Card PIN and continue
	 *
	 */
	public CheckoutPlaceOrderPage continueAfterEnteringPIN() {
		logger.info("Entering Credit Card PIN");
		getValidateCSCTextBox.sendKeys(Constants.TestData.VISA_CC_SECURITY_CODE);
		getContinuePaymentButton.exists();
		getContinuePaymentButton.click();
		return ComponentFactory.create(CheckoutPlaceOrderPage.class);
	}

	/**
	 * Continue after entering required Billing info
	 * 
	 */
	public CheckoutPlaceOrderPage continueAfterFillingRequiredBillingInfo() {
		logger.info("Clicking Continue after filling Billing info");
		selectDebitCreditCardOption();
		fillBillingInfo();
		fillEmailField();
		continueAfterBillingInfo();
		return ComponentFactory.create(CheckoutPlaceOrderPage.class);
	}

	/**
	 * Continue after entering Credit Card Billing info
	 *
	 */
	public CheckoutPlaceOrderPage continueAfterCrediCardBillingInfo() {
		logger.info("Clicking Continue after filling Credit Card Billing info");
		selectDebitCreditCardOption();
		fillBillingInfo();
		getContinuePaymentButton.click();
		return ComponentFactory.create(CheckoutPlaceOrderPage.class);
	}

	/**
	 * Enter an email-alias based on email seed
	 *
	 */
	public void fillEmailField() {
		long timeStamp = System.currentTimeMillis();
		String email = String.format(Constants.TestData.EMAIL, timeStamp);
		getEmailTextBox.sendKeys(email);
	}

	/**
	 * Select Debit/Credit Card as payment option
	 *
	 */
	public void selectDebitCreditCardOption() {
		logger.info("Selecting the Debit/Credit card Checkbox");
		if (!getDebitCreditCardCheckbox.isSelected()) {
			getDebitCreditCardCheckbox.click();
		}
	}

	/**
	 * Fill Required Fields for Billing Info
	 *
	 */
	public void fillBillingInfo() {
		logger.info("Filling Billing info");
		getCardNumberTextBox.sendKeys(Constants.TestData.AMEX_CC_NUM);
		getCardSecurityCodeTextBox.sendKeys(Constants.TestData.AMEX_CC_CODE);
		// Cant select the month value due its content. Workaround was to select its value
		webHelper.jsSelectByValue(getCardExpMonthSelectList.getWebElement(), Constants.TestData.AMEX_CC_MONTH);
		webHelper.jsSelect(getCardExpYearSelectList.getWebElement(), Constants.TestData.AMEX_CC_YEAR);
		getNameOnCardTextBox.sendKeys(Constants.TestData.VISA_CC_NAME);
	}

	/**
	 * Select Billing Address same as Shipping Address
	 *
	 */
	public void selectBilligShippingAddress() {
		logger.info("Select Billing Address Same as Shipping Address");
		if (!getBillShippingAddressCheckbox.isSelected()) {
			getBillShippingAddressCheckbox.click();
		}
	}

	/**
	 * Continue after entering Peets and Credit Card Billing Info
	 *
	 */
	public CheckoutPlaceOrderPage continueAfterFillingPeetsAndCreditInfo() {
		logger.info("Clicking Continue after filling Peets Card and Credit Card info");
		selectPeetsCardOption();
		fillPeetsCardInfo(Constants.TestData.PEETS_CARD_LOWEST_AMOUNT);
		selectDebitCreditCardOption();
		fillBillingInfo();
		fillEmailField();
		continueAfterBillingInfo();
		return ComponentFactory.create(CheckoutPlaceOrderPage.class);
	}

	/**
	 * Click continue after billing info section
	 *
	 */
	public void continueAfterBillingInfo() {
		logger.info("Click Continue on billing section");
		getContinueButton.click();
	}

	/*
	 * Protected Getters
	 */

	@Locate(jQuery = "h2#checkout-title-opc-billing.active", on = Platform.WEB_DESKTOP)
	protected Text getViewSignature;

	@Locate(jQuery = "#pc-title input", on = Platform.WEB_DESKTOP)
	protected Checkbox getPeetsCardCheckbox;

	@Locate(jQuery = "#custompayment_pc_number", on = Platform.WEB_DESKTOP)
	protected TextBox getPeetsCardNumberTextBox;

	@Locate(jQuery = "#custompayment_pc_pin", on = Platform.WEB_DESKTOP)
	protected TextBox getPeetsCardPinTextBox;

	@Locate(jQuery = "#custompayment_pc_amount", on = Platform.WEB_DESKTOP)
	protected TextBox getPeetsCardAmountTextBox;

	@Locate(jQuery = "#cc_checkbox", on = Platform.WEB_DESKTOP)
	protected Checkbox getDebitCreditCardCheckbox;

	@Locate(jQuery = "#custompayment_cc_number", on = Platform.WEB_DESKTOP)
	protected TextBox getCardNumberTextBox;

	@Locate(jQuery = "#custompayment_cc_cid", on = Platform.WEB_DESKTOP)
	protected TextBox getCardSecurityCodeTextBox;

	@Locate(jQuery = "input[id*='stored_cc_cid'][class*='validate-cc-cvn']", on = Platform.WEB_DESKTOP)
	protected TextBox getValidateCSCTextBox;

	@Locate(jQuery = "#custompayment_expiration", on = Platform.WEB_DESKTOP)
	protected SelectList getCardExpMonthSelectList;

	@Locate(jQuery = "#custompayment_expiration_yr", on = Platform.WEB_DESKTOP)
	protected SelectList getCardExpYearSelectList;

	@Locate(jQuery = "#custompayment_cc_owner", on = Platform.WEB_DESKTOP)
	protected TextBox getNameOnCardTextBox;

	@Locate(xpath = "//input[contains(@id,'same_as_shipping')]", on = Platform.WEB_DESKTOP)
	protected Checkbox getBillShippingAddressCheckbox;

	@Locate(jQuery = "input[id='billing:email']", on = Platform.WEB_DESKTOP)
	protected TextBox getEmailTextBox;

	@Locate(jQuery = "#billing-container button", on = Platform.WEB_DESKTOP)
	protected Button getContinueButton;

	@Locate(jQuery = "#payment-button-continue", on = Platform.WEB_DESKTOP)
	protected Button getContinuePaymentButton;

	@Locate(jQuery = "opc-please-wait", on = Platform.WEB_DESKTOP)
	protected ContainerElement getBillingLoadingSpinner;

	@Locate(jQuery = "peets-card-please-wait", on = Platform.WEB_DESKTOP)
	protected ContainerElement getPeetsCardLoadingSpinner;

}
