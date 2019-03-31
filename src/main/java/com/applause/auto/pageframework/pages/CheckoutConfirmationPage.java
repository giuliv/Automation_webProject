package com.applause.auto.pageframework.pages;

import java.lang.invoke.MethodHandles;

import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.framework.pageframework.web.AbstractPage;
import com.applause.auto.framework.pageframework.web.WebElementLocator;
import com.applause.auto.framework.pageframework.web.factory.WebDesktopImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebPhoneImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebTabletImplementation;
import com.applause.auto.framework.pageframework.webcontrols.Text;
import com.applause.auto.pageframework.helpers.WebHelper;

@WebDesktopImplementation(CheckoutConfirmationPage.class)
@WebTabletImplementation(CheckoutConfirmationPage.class)
@WebPhoneImplementation(CheckoutConfirmationPage.class)
public class CheckoutConfirmationPage extends AbstractPage {

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
	 * Get Confirmation message
	 * 
	 * @return String
	 */
	public String getConfirmationMessage() {
		LOGGER.info("Getting confirmation message");
		// Set to upper case as Safari shows the message Capitalized while Chrome not and this does
		// not affect the test
		return getPageSubtitleText().getStringValue().toUpperCase();
	}

	/**
	 * Get Order Number
	 * 
	 * @return String
	 */
	public String getOrderNumber() {
		LOGGER.info("Getting order number");
		return getOrderNumberText().getStringValue();
	}

	public String getSubscriptionNumber() {
		return getSubscriptionNumberText().getText();
	}

	/*
	 * Protected Getters
	 */

	@WebElementLocator(webDesktop = ".default-page-text strong,.default-page-text .disc > li > a[href*='recurring_profile/view/profile']")
	protected Text getViewSignature() {
		return new Text(this, getLocator(this, "getViewSignature"));
	}

	@WebElementLocator(webDesktop = "h2.sub-title")
	protected Text getPageSubtitleText() {
		return new Text(this, getLocator(this, "getPageSubtitleText"));
	}

	@WebElementLocator(webDesktop = ".default-page-text strong")
	protected Text getOrderNumberText() {
		return new Text(this, getLocator(this, "getOrderNumberText"));
	}

	@WebElementLocator(webDesktop = ".default-page-text .disc > li > a[href*='recurring_profile/view/profile']")
	protected Text getSubscriptionNumberText() {
		return new Text(this, getLocator(this, "getSubscriptionNumberText"));
	}
}
