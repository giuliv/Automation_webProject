package com.applause.auto.pageframework.pages;

import java.lang.invoke.MethodHandles;

import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.framework.pageframework.web.AbstractPage;
import com.applause.auto.framework.pageframework.web.WebElementLocator;
import com.applause.auto.framework.pageframework.web.factory.WebDesktopImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebPhoneImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebTabletImplementation;
import com.applause.auto.framework.pageframework.webcontrols.Text;

@WebDesktopImplementation(CheckoutConfirmationPage.class)
@WebTabletImplementation(CheckoutConfirmationPage.class)
@WebPhoneImplementation(CheckoutConfirmationPage.class)
public class CheckoutConfirmationPage extends AbstractPage {

	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	@Override
	protected void waitUntilVisible() {
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
		return getPageSubtitleText().getStringValue();
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

	/*
	 * Protected Getters
	 */

	@WebElementLocator(webDesktop = "//div[@id='step-title-section' and contains(.,'Review & Place Order')]")
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

}
