package com.applause.auto.web.views;

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
import com.applause.auto.web.helpers.WebHelper;

@WebDesktopImplementation(CheckoutPage.class)
@WebTabletImplementation(CheckoutPage.class)
@WebPhoneImplementation(CheckoutPage.class)
public class CheckoutPage extends AbstractPage {

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
	 * Click continue as Guest
	 * 
	 * @return CheckoutShippingInfoPage
	 */
	public CheckoutShippingInfoPage clickContinueAsGuest() {
		LOGGER.info("Click Continue as Guest");
		getClickContinueAsGuestButton().click();
		return PageFactory.create(CheckoutShippingInfoPage.class);
	}

	/*
	 * Protected Getters
	 */

	@WebElementLocator(webDesktop = "div.default-message-page.login div.page-title h1")
	protected Text getViewSignature() {
		return new Text(this, getLocator(this, "getViewSignature"));
	}

	@WebElementLocator(webDesktop = "div.new-users button")
	protected Button getClickContinueAsGuestButton() {
		return new Button(this, getLocator(this, "getClickContinueAsGuestButton"));
	}

}
