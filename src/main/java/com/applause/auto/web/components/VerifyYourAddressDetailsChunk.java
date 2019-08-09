package com.applause.auto.web.components;

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
import com.applause.auto.framework.pageframework.webcontrols.Text;
import com.applause.auto.web.helpers.WebHelper;
import com.applause.auto.web.views.CheckoutShippingInfoPage;

@WebDesktopImplementation(VerifyYourAddressDetailsChunk.class)
@WebTabletImplementation(VerifyYourAddressDetailsChunk.class)
@WebPhoneImplementation(VerifyYourAddressDetailsChunk.class)
public class VerifyYourAddressDetailsChunk extends AbstractPageChunk {
	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	/**
	 * Constructor.
	 *
	 * @param selector
	 *            the selector of the chunk
	 */
	public VerifyYourAddressDetailsChunk(UIData parent, String selector) {
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
	 * Click Use Entered Address button
	 *
	 * @return CheckoutShippingInfoPage
	 */
	public CheckoutShippingInfoPage clickEnteredAddressButton() {
		LOGGER.info("Clicking Use Entered Address Button");
		getUseEnteredAddressButton().click();
		return PageFactory.create(CheckoutShippingInfoPage.class);
	}

	/*
	 * Protected Getters
	 */
	@WebElementLocator(webDesktop = ".verification-form")
	protected Text getViewSignature() {
		return new Text(this, getLocator(this, "getViewSignature"));
	}

	@WebElementLocator(webDesktop = "button[title='Use Address As Entered *']")
	protected Button getUseEnteredAddressButton() {
		return new Button(this, getLocator(this, "getUseEnteredAddressButton"));
	}
}
