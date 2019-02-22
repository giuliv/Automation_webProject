package com.applause.auto.pageframework.pages;

import java.lang.invoke.MethodHandles;

import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.framework.pageframework.web.AbstractPage;
import com.applause.auto.framework.pageframework.web.PageFactory;
import com.applause.auto.framework.pageframework.web.WebElementLocator;
import com.applause.auto.framework.pageframework.web.factory.WebDesktopImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebPhoneImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebTabletImplementation;
import com.applause.auto.framework.pageframework.webcontrols.BaseHtmlElement;
import com.applause.auto.framework.pageframework.webcontrols.Button;
import com.applause.auto.framework.pageframework.webcontrols.EditField;
import com.applause.auto.framework.pageframework.webcontrols.Text;
import com.applause.auto.pageframework.helpers.WebHelper;

@WebDesktopImplementation(CheckoutPlaceOrderPage.class)
@WebTabletImplementation(CheckoutPlaceOrderPage.class)
@WebPhoneImplementation(CheckoutPlaceOrderPage.class)
public class CheckoutPlaceOrderPage extends AbstractPage {

	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	@Override
	protected void waitUntilVisible() {
		WebHelper.waitForDocument();
		syncHelper.waitForElementToAppear(getViewSignature());
	}

	/*
	 * Public Actions
	 */

	/**
	 * Click Place Order Button
	 * 
	 * @return CheckoutConfirmationPage
	 */
	public CheckoutConfirmationPage placeOrder() {
		LOGGER.info("Click Place Order Button");
		syncHelper.waitForElementToAppear(getLocator(this, "getPlaceOrderButton"));
		syncHelper.suspend(7000); // Required time to trigger spinner animation if shown

		getPlaceOrderButton().click();
		syncHelper.suspend(2000); // Required time to trigger spinner animation if shown
		syncHelper.waitForElementToDisappear(getLocator(this, "getPlaceOrderSpinner"));
		return PageFactory.create(CheckoutConfirmationPage.class);
	}

	/**
	 * Click Place Order Button without payment method selected
	 * 
	 * @return CheckoutPaymentMethodPage
	 */
	public CheckoutPaymentMethodPage placeOrderMissingPayment() {
		LOGGER.info("Click Place Order Button");
		getPlaceOrderButton().click();
		return PageFactory.create(CheckoutPaymentMethodPage.class);
	}

	/**
	 * Get Gift Order Message
	 *
	 * @return String
	 */
	public String getGiftMessage() {
		LOGGER.info("Getting gift Message");
		return getGiftMessageEditField().getText();
	}

	/**
	 * Get Product Name
	 *
	 * @return String
	 */
	public String getProductName() {
		LOGGER.info("Getting product name");
		return getProductNameText().getStringValue();
	}

	/*
	 * Protected Getters
	 */

	@WebElementLocator(webDesktop = "h2#checkout-title-opc-review.active")
	protected Text getViewSignature() {
		return new Text(this, getLocator(this, "getViewSignature"));
	}

	@WebElementLocator(webDesktop = "button[title='Place Order']")
	protected Button getPlaceOrderButton() {
		return new Button(this, getLocator(this, "getPlaceOrderButton"));
	}

	@WebElementLocator(webDesktop = "#gift-message-whole-message")
	protected EditField getGiftMessageEditField() {
		return new EditField(this, getLocator(this, "getGiftMessageEditField"));
	}

	@WebElementLocator(webDesktop = "#cart-table-standard > tbody > tr > td.product-info-cell.last > h3 > a")
	protected Text getProductNameText() {
		return new Text(this, getLocator(this, "getProductNameText"));
	}

	@WebElementLocator(webDesktop = "span#opc-please-wait.please-wait-review")
	protected BaseHtmlElement getPlaceOrderSpinner() {
		return new BaseHtmlElement(this, getLocator(this, "getPlaceOrderSpinner"));
	}

}
