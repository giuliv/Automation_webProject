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

@WebDesktopImplementation(ShoppingCartPage.class)
@WebTabletImplementation(ShoppingCartPage.class)
@WebPhoneImplementation(ShoppingCartPage.class)
public class ShoppingCartPage extends AbstractPage {

	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	@Override
	protected void waitUntilVisible() {
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
		getOrderAsGiftCheckCheckbox().check();
	}

	/**
	 * Enter Gift Message
	 * 
	 */
	public void enterGiftMessage(String giftMessage) {
		LOGGER.info("Enter a Gift Message");
		getGiftMessageText().setText(giftMessage);
	}

	/**
	 * Click Proceed to Checkout button
	 * 
	 * @return CheckoutPage
	 */
	public CheckoutPage clickProceedToCheckout() {
		LOGGER.info("Click Proceed to Checkout button");
		getProceedToCheckoutButton().hover();
		syncHelper.suspend(1000);
		getProceedToCheckoutButton().click();
		syncHelper.suspend(1000);
		getProceedToCheckoutButton().click();
		return PageFactory.create(CheckoutPage.class);
	}

	/**
	 * Click Proceed to Checkout button for a signed user
	 * 
	 * @return PlaceOrderPage
	 */
	public CheckoutPlaceOrderPage checkoutSignedUser() {
		LOGGER.info("Click Proceed to Checkout button");
		getProceedToCheckoutButton().click();
		syncHelper.suspend(2000);
		getProceedToCheckoutButton().click();
		return PageFactory.create(CheckoutPlaceOrderPage.class);
	}

	/*
	 * Protected Getters
	 */

	@WebElementLocator(webDesktop = "//div[@class='page-title']/h1[contains(.,'Shopping Cart')]")
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

}
