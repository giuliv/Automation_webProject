package com.applause.auto.pageframework.pages;

import java.lang.invoke.MethodHandles;

import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.framework.pageframework.web.AbstractPage;
import com.applause.auto.framework.pageframework.web.PageFactory;
import com.applause.auto.framework.pageframework.web.WebElementLocator;
import com.applause.auto.framework.pageframework.web.factory.WebDesktopImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebPhoneImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebTabletImplementation;
import com.applause.auto.framework.pageframework.webcontrols.Button;
import com.applause.auto.framework.pageframework.webcontrols.Checkbox;
import com.applause.auto.framework.pageframework.webcontrols.EditField;
import com.applause.auto.framework.pageframework.webcontrols.Text;
import com.applause.auto.pageframework.helpers.WebHelper;

@WebDesktopImplementation(ShoppingCartPage.class)
@WebTabletImplementation(ShoppingCartPage.class)
@WebPhoneImplementation(ShoppingCartPage.class)
public class ShoppingCartPage extends AbstractPage {

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
	 * Select Order is a Gift Checkbox
	 * 
	 * @return CheckoutPage
	 */
	public void selectOrderAsGift() {
		LOGGER.info("Check the Order is a Gift");
		syncHelper.waitForElementToAppear(getLocator(this, "getOrderAsGiftCheckCheckbox"));
		getOrderAsGiftCheckCheckbox().check();
	}

	/**
	 * Enter Gift Message
	 * 
	 */
	public void enterGiftMessage(String giftMessage) {
		LOGGER.info("Enter a Gift Message");
		syncHelper.waitForElementToAppear(getLocator(this, "getGiftMessageText"));
		getGiftMessageText().setText(giftMessage);
	}

	/**
	 * Click Proceed to Checkout button
	 * 
	 * @return CheckoutPage
	 */
	public CheckoutPage clickProceedToCheckout() {
		LOGGER.info("Click Proceed to Checkout button");
		getProceedToCheckoutButton().click();
		syncHelper.suspend(2000);
		webHelper.jsClick(getProceedToCheckoutButton().getWebElement());
		return PageFactory.create(CheckoutPage.class);
	}

	/**
	 * Click Proceed to Checkout button for a signed user
	 *
	 * @return CheckoutPlaceOrderPage
	 */
	public CheckoutPlaceOrderPage checkoutSignedUser() {
		LOGGER.info("Click Proceed to Checkout button");
		getProceedToCheckoutButton().click();
		syncHelper.suspend(2000);
		webHelper.jsClick(getProceedToCheckoutButton().getWebElement());
		return PageFactory.create(CheckoutPlaceOrderPage.class);
	}

	/**
	 * Proceed to Shipping page via Checkout button for a signed user
	 *
	 * @return CheckoutShippingInfoPage
	 */
	public CheckoutShippingInfoPage defineShippingSignedUser() {
		LOGGER.info("Click Proceed to Checkout button");
		getProceedToCheckoutButton().click();
		syncHelper.suspend(2000);
		webHelper.jsClick(getProceedToCheckoutButton().getWebElement());
		return PageFactory.create(CheckoutShippingInfoPage.class);
	}

	/**
	 * Click Pay with Paypal Button
	 *
	 * @return PaypalLoginPage
	 */
	public PaypalLoginPage clickPayWithPaypal() {
		LOGGER.info("Clicking Pay with Paypal");
		getPaypalButton().click();
		syncHelper.suspend(5000);
		getPaypalButton().click();
		return PageFactory.create(PaypalLoginPage.class);
	}

	/**
	 * Click Pay with Paypal Button for a signed user
	 *
	 * @return CheckoutPlaceOrderPage
	 */
	public CheckoutPlaceOrderPage clickPayWithPaypalSignedUser() {
		LOGGER.info("Clicking Pay with Paypal for Signed User");
		getPaypalButton().click();
		syncHelper.suspend(5000); // Required due a change of focus after leaving gift-message
		webHelper.jsClick(getPaypalButton().getWebElement());
		return PageFactory.create(CheckoutPlaceOrderPage.class);
	}

	/*
	 * Protected Getters
	 */

	@WebElementLocator(webDesktop = "div.cart.display-single-price div.page-title h1")
	protected Text getViewSignature() {
		return new Text(this, getLocator(this, "getViewSignature"));
	}

	@WebElementLocator(webDesktop = ".add-gift-message input")
	protected Checkbox getOrderAsGiftCheckCheckbox() {
		return new Checkbox(this, getLocator(this, "getOrderAsGiftCheckCheckbox"));
	}

	@WebElementLocator(webDesktop = "#gift-message-whole-message")
	protected EditField getGiftMessageText() {
		return new EditField(this, getLocator(this, "getGiftMessageText"));
	}

	@WebElementLocator(webDesktop = "#action-checkout")
	protected Button getProceedToCheckoutButton() {
		return new Button(this, getLocator(this, "getProceedToCheckoutButton"));
	}

	@WebElementLocator(webDesktop = "div#shopping-cart-actions-additional img[title='Checkout with PayPal']")
	protected Button getPaypalButton() {
		LOGGER.info(">>>>>" + getDriver().getPageSource());
		return new Button(this, getLocator(this, "getPaypalButton"));
	}

}
