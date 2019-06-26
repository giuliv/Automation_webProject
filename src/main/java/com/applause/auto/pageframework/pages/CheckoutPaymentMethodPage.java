package com.applause.auto.pageframework.pages;

import java.lang.invoke.MethodHandles;

import com.applause.auto.framework.pageframework.util.drivers.BrowserType;
import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.framework.pageframework.web.AbstractPage;
import com.applause.auto.framework.pageframework.web.PageFactory;
import com.applause.auto.framework.pageframework.web.WebElementLocator;
import com.applause.auto.framework.pageframework.web.factory.WebDesktopImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebPhoneImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebTabletImplementation;
import com.applause.auto.framework.pageframework.webcontrols.BaseHtmlElement;
import com.applause.auto.framework.pageframework.webcontrols.Button;
import com.applause.auto.framework.pageframework.webcontrols.Checkbox;
import com.applause.auto.framework.pageframework.webcontrols.Dropdown;
import com.applause.auto.framework.pageframework.webcontrols.EditField;
import com.applause.auto.framework.pageframework.webcontrols.Text;
import com.applause.auto.pageframework.helpers.WebHelper;
import com.applause.auto.pageframework.testdata.TestConstants;

@WebDesktopImplementation(CheckoutPaymentMethodPage.class)
@WebTabletImplementation(CheckoutPaymentMethodPage.class)
@WebPhoneImplementation(CheckoutPaymentMethodPage.class)
public class CheckoutPaymentMethodPage extends AbstractPage {

	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());
	WebHelper webHelper = new WebHelper();

	@Override
	protected void waitUntilVisible() {
		WebHelper.waitForDocument();
		syncHelper.waitForElementToAppear(getViewSignature());
	}

	/*
	 * Public Actions
	 */

	/**
	 * Continue after entering Peets Card info
	 *
	 */
	public CheckoutPlaceOrderPage continueAfterFillingPeetsCardInfo(String amount) {
		LOGGER.info("Clicking Continue after filling Peets Card info");
		selectPeetsCardOption();
		fillPeetsCardInfo(amount);
		fillEmailField();
		continueAfterBillingInfo();
		return PageFactory.create(CheckoutPlaceOrderPage.class);
	}

	/**
	 * Select Peets Card as payment option
	 *
	 */
	public void selectPeetsCardOption() {
		LOGGER.info("Selecting the Peets card Checkbox");
		if (!getPeetsCardCheckbox().isSelected()) {
			getPeetsCardCheckbox().check();
		}
	}

	/**
	 * Fill Peets Card Billing Info
	 *
	 */
	public void fillPeetsCardInfo(String amount) {
		LOGGER.info("Filling Peets Card info");

		String peetsCardNumber = (env.getBrowserType() == BrowserType.SAFARI)
				? TestConstants.TestData.PEETS_CARD_NUMBER_SAFARI_1 : TestConstants.TestData.PEETS_CARD_NUMBER_CHROME_1;
		getPeetsCardNumberEditField().setText(peetsCardNumber);
		String peetsCardPin = (env.getBrowserType() == BrowserType.SAFARI)
				? TestConstants.TestData.PEETS_CARD_PIN_SAFARI_1 : TestConstants.TestData.PEETS_CARD_PIN_CHROME_1;
		getPeetsCardPinEditField().setText(peetsCardPin);
		// Peets card loads its balance after clicking outside the Peets Card fields
		getPeetsCardNumberEditField().click();
		// Waits for an animation while the element is displayed
		syncHelper.suspend(2000);
		syncHelper.waitForElementToAppear(getLocator(this, "getPeetsCardAmountEditField"));
		getPeetsCardAmountEditField().setText(amount);
	}

	/**
	 * Fill Credit Card PIN and continue
	 *
	 */
	public CheckoutPlaceOrderPage continueAfterEnteringPIN() {
		LOGGER.info("Entering Credit Card PIN");
		getValidateCSCEditField().setText(TestConstants.TestData.VISA_CC_SECURITY_CODE);
		getContinuePaymentButton().exists();
		getContinuePaymentButton().click();
		return PageFactory.create(CheckoutPlaceOrderPage.class);
	}

	/**
	 * Continue after entering required Billing info
	 * 
	 */
	public CheckoutPlaceOrderPage continueAfterFillingRequiredBillingInfo() {
		LOGGER.info("Clicking Continue after filling Billing info");
		selectDebitCreditCardOption();
		fillBillingInfo();
		fillEmailField();
		continueAfterBillingInfo();
		return PageFactory.create(CheckoutPlaceOrderPage.class);
	}

	/**
	 * Continue after entering Credit Card Billing info
	 *
	 */
	public CheckoutPlaceOrderPage continueAfterCrediCardBillingInfo() {
		LOGGER.info("Clicking Continue after filling Credit Card Billing info");
		selectDebitCreditCardOption();
		fillBillingInfo();
		getContinuePaymentButton().click();
		return PageFactory.create(CheckoutPlaceOrderPage.class);
	}

	/**
	 * Enter an email-alias based on email seed
	 *
	 */
	public void fillEmailField() {
		long timeStamp = System.currentTimeMillis();
		String email = String.format(TestConstants.TestData.EMAIL, timeStamp);
		getEmailEditField().setText(email);
	}

	/**
	 * Select Debit/Credit Card as payment option
	 *
	 */
	public void selectDebitCreditCardOption() {
		LOGGER.info("Selecting the Debit/Credit card Checkbox");
		if (!getDebitCreditCardCheckbox().isSelected()) {
			getDebitCreditCardCheckbox().check();
		}
	}

	/**
	 * Fill Required Fields for Billing Info
	 *
	 */
	public void fillBillingInfo() {
		LOGGER.info("Filling Billing info");
		getCardNumberEditField().setText(TestConstants.TestData.AMEX_CC_NUM);
		getCardSecurityCodeEditField().setText(TestConstants.TestData.AMEX_CC_CODE);
		// Cant select the month value due its content. Workaround was to select its value
		webHelper.jsSelectByValue(getCardExpMonthDropdown().getWebElement(), TestConstants.TestData.AMEX_CC_MONTH);
		webHelper.jsSelect(getCardExpYearDropdown().getWebElement(), TestConstants.TestData.AMEX_CC_YEAR);
		getNameOnCardEditField().setText(TestConstants.TestData.VISA_CC_NAME);
	}

	/**
	 * Select Billing Address same as Shipping Address
	 *
	 */
	public void selectBilligShippingAddress() {
		LOGGER.info("Select Billing Address Same as Shipping Address");
		if (!getBillShippingAddressCheckbox().isSelected()) {
			getBillShippingAddressCheckbox().check();
		}
	}

	/**
	 * Continue after entering Peets and Credit Card Billing Info
	 *
	 */
	public CheckoutPlaceOrderPage continueAfterFillingPeetsAndCreditInfo() {
		LOGGER.info("Clicking Continue after filling Peets Card and Credit Card info");
		selectPeetsCardOption();
		fillPeetsCardInfo(TestConstants.TestData.PEETS_CARD_LOWEST_AMOUNT);
		selectDebitCreditCardOption();
		fillBillingInfo();
		fillEmailField();
		continueAfterBillingInfo();
		return PageFactory.create(CheckoutPlaceOrderPage.class);
	}

	/**
	 * Click continue after billing info section
	 *
	 */
	public void continueAfterBillingInfo() {
		LOGGER.info("Click Continue on billing section");
		getContinueButton().click();
	}

	/*
	 * Protected Getters
	 */

	@WebElementLocator(webDesktop = "h2#checkout-title-opc-billing.active")
	protected Text getViewSignature() {
		return new Text(this, getLocator(this, "getViewSignature"));
	}

	@WebElementLocator(webDesktop = "#pc-title input")
	protected Checkbox getPeetsCardCheckbox() {
		return new Checkbox(this, getLocator(this, "getPeetsCardCheckbox"));
	}

	@WebElementLocator(webDesktop = "#custompayment_pc_number")
	protected EditField getPeetsCardNumberEditField() {
		return new EditField(this, getLocator(this, "getPeetsCardNumberEditField"));
	}

	@WebElementLocator(webDesktop = "#custompayment_pc_pin")
	protected EditField getPeetsCardPinEditField() {
		return new EditField(this, getLocator(this, "getPeetsCardPinEditField"));
	}

	@WebElementLocator(webDesktop = "#custompayment_pc_amount")
	protected EditField getPeetsCardAmountEditField() {
		return new EditField(this, getLocator(this, "getPeetsCardAmountEditField"));
	}

	@WebElementLocator(webDesktop = "#cc_checkbox")
	protected Checkbox getDebitCreditCardCheckbox() {
		return new Checkbox(this, getLocator(this, "getDebitCreditCardCheckbox"));
	}

	@WebElementLocator(webDesktop = "#custompayment_cc_number")
	protected EditField getCardNumberEditField() {
		return new EditField(this, getLocator(this, "getCardNumberEditField"));
	}

	@WebElementLocator(webDesktop = "#custompayment_cc_cid")
	protected EditField getCardSecurityCodeEditField() {
		return new EditField(this, getLocator(this, "getCardSecurityCodeEditField"));
	}

	@WebElementLocator(webDesktop = "input[id*='stored_cc_cid'][class*='validate-cc-cvn']")
	protected EditField getValidateCSCEditField() {
		return new EditField(this, getLocator(this, "getValidateCSCEditField"));
	}

	@WebElementLocator(webDesktop = "#custompayment_expiration")
	protected Dropdown getCardExpMonthDropdown() {
		return new Dropdown(this, getLocator(this, "getCardExpMonthDropdown"));
	}

	@WebElementLocator(webDesktop = "#custompayment_expiration_yr")
	protected Dropdown getCardExpYearDropdown() {
		return new Dropdown(this, getLocator(this, "getCardExpYearDropdown"));
	}

	@WebElementLocator(webDesktop = "#custompayment_cc_owner")
	protected EditField getNameOnCardEditField() {
		return new EditField(this, getLocator(this, "getNameOnCardEditField"));
	}

	@WebElementLocator(webDesktop = "//input[contains(@id,'same_as_shipping')]")
	protected Checkbox getBillShippingAddressCheckbox() {
		return new Checkbox(this, getLocator(this, "getBillShippingAddressCheckbox"));
	}

	@WebElementLocator(webDesktop = "input[id='billing:email']")
	protected EditField getEmailEditField() {
		return new EditField(this, getLocator(this, "getEmailEditField"));
	}

	@WebElementLocator(webDesktop = "#billing-container button")
	protected Button getContinueButton() {
		return new Button(this, getLocator(this, "getContinueButton"));
	}

	@WebElementLocator(webDesktop = "#payment-button-continue")
	protected Button getContinuePaymentButton() {
		return new Button(this, getLocator(this, "getContinuePaymentButton"));
	}

	@WebElementLocator(webDesktop = "opc-please-wait")
	protected BaseHtmlElement getBillingLoadingSpinner() {
		return new BaseHtmlElement(this, getLocator(this, "getBillingLoadingSpinner"));
	}

	@WebElementLocator(webDesktop = "peets-card-please-wait")
	protected BaseHtmlElement getPeetsCardLoadingSpinner() {
		return new BaseHtmlElement(this, getLocator(this, "getPeetsCardLoadingSpinner"));
	}

}
