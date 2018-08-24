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
import com.applause.auto.framework.pageframework.webcontrols.Text;

@WebDesktopImplementation(CheckoutPlaceOrderPage.class)
@WebTabletImplementation(CheckoutPlaceOrderPage.class)
@WebPhoneImplementation(CheckoutPlaceOrderPage.class)
public class CheckoutPlaceOrderPage extends AbstractPage {

	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	@Override
	protected void waitUntilVisible() {
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
		getPlaceOrderButton().click();
		return PageFactory.create(CheckoutConfirmationPage.class);
	}

	/*
	 * Protected Getters
	 */

	@WebElementLocator(webDesktop = "//div[@id='step-title-section' and contains(.,'Review & Place Order')]")
	protected Text getViewSignature() {
		return new Text(this, getLocator(this, "getViewSignature"));
	}

	@WebElementLocator(webDesktop = "#checkout-bottom-button-load button")
	protected Button getPlaceOrderButton() {
		return new Button(this, getLocator(this, "getPlaceOrderButton"));
	}

}
