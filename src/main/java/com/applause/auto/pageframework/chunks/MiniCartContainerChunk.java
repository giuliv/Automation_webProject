package com.applause.auto.pageframework.chunks;

import java.lang.invoke.MethodHandles;

import com.applause.auto.framework.pageframework.UIData;
import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.framework.pageframework.web.AbstractPageChunk;
import com.applause.auto.framework.pageframework.web.PageFactory;
import com.applause.auto.framework.pageframework.web.WebElementLocator;
import com.applause.auto.framework.pageframework.web.factory.WebDesktopImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebPhoneImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebTabletImplementation;
import com.applause.auto.framework.pageframework.webcontrols.Button;
import com.applause.auto.framework.pageframework.webcontrols.Link;
import com.applause.auto.framework.pageframework.webcontrols.Text;
import com.applause.auto.pageframework.helpers.WebHelper;
import com.applause.auto.pageframework.pages.CheckoutPage;
import com.applause.auto.pageframework.pages.CheckoutPlaceOrderPage;
import com.applause.auto.pageframework.pages.CheckoutShippingInfoPage;
import com.applause.auto.pageframework.pages.ShoppingCartPage;
import com.applause.auto.pageframework.pages.SignInPage;

@WebDesktopImplementation(MiniCartContainerChunk.class)
@WebTabletImplementation(MiniCartContainerChunk.class)
@WebPhoneImplementation(MiniCartContainerChunk.class)
public class MiniCartContainerChunk extends AbstractPageChunk {
	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	/**
	 * Constructor.
	 *
	 * @param selector
	 *            the selector of the chunk
	 */
	public MiniCartContainerChunk(UIData parent, String selector) {
		super(parent, selector);
	}

	@Override
	protected void waitUntilVisible() {
		WebHelper.waitForDocument();
		syncHelper.waitForElementToAppear(getViewSignature());
	}

	/*
	 * Public actions
	 */

	/**
	 * Click Checkout Button
	 *
	 * @return CheckoutPage
	 */
	public CheckoutPage clickCheckout() {
		LOGGER.info("Clicking Checkout Button");
		getCheckoutButton().click();
		return PageFactory.create(CheckoutPage.class);
	}

	/**
	 * Click Checkout Button with signed in user
	 *
	 * @return CheckoutPlaceOrderPage
	 */
	public CheckoutPlaceOrderPage checkoutSignedInUser() {
		LOGGER.info("Clicking Checkout Button");
		getCheckoutButton().click();
		return PageFactory.create(CheckoutPlaceOrderPage.class);
	}

	/**
	 * Click Checkout Button after user signed
	 *
	 * @return CheckoutShippingInfoPage
	 */
	public CheckoutShippingInfoPage clickSignedInCheckout() {
		LOGGER.info("Clicking Checkout Button");
		getCheckoutButton().click();
		return PageFactory.create(CheckoutShippingInfoPage.class);
	}

	/**
	 * Click Checkout Peets Card Button
	 *
	 * @return SignInPage
	 */
	public SignInPage checkoutPeetsCard() {
		LOGGER.info("Clicking Checkout Button on Peets Card Page");
		getCheckoutButton().click();
		return PageFactory.create(SignInPage.class);
	}

	/**
	 * Click Edit Cart link
	 *
	 * @return ShoppingCartPage
	 */
	public ShoppingCartPage clickEditCart() {
		LOGGER.info("Clicking Edit Button");
		getEditCartLink().click();
		return PageFactory.create(ShoppingCartPage.class);
	}

	/*
	 * Protected Getters
	 */
	@WebElementLocator(webDesktop = "#minicart-container")
	protected Text getViewSignature() {
		return new Text(this, getLocator(this, "getViewSignature"));
	}

	@WebElementLocator(webDesktop = "#minicart-container > div.block-subtitle > a.cart-link")
	protected Link getEditCartLink() {
		return new Link(this, getLocator(this, "getEditCartLink"));
	}

	@WebElementLocator(webDesktop = "a[title='Checkout']")
	protected Button getCheckoutButton() {
		return new Button(this, getLocator(this, "getCheckoutButton"));
	}

}
