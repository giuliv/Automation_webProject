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
import com.applause.auto.framework.pageframework.webcontrols.Image;
import com.applause.auto.pageframework.helpers.WebHelper;

@WebDesktopImplementation(PaypalReviewYourPurchasePage.class)
@WebTabletImplementation(PaypalReviewYourPurchasePage.class)
@WebPhoneImplementation(PaypalReviewYourPurchasePage.class)
public class PaypalReviewYourPurchasePage extends AbstractPage {
	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	@Override
	protected void waitUntilVisible() {
		WebHelper.waitForDocument();
		syncHelper.waitForElementToAppear(getViewSignature());
	}

	// Public actions

	/**
	 * Click Agree and Continue
	 *
	 * @return CheckoutPlaceOrderPage
	 */
	public CheckoutPlaceOrderPage clickAgreeAndContinue() {
		LOGGER.info("Clicking Agree and Continue");
		syncHelper.suspend(5000);
		getAgreeAndContinueButton().click();
		return PageFactory.create(CheckoutPlaceOrderPage.class);
	}

	// Protected getters
	@WebElementLocator(webDesktop = "//*[@id=\"paypalLogo\"]")
	protected Image getViewSignature() {
		return new Image(this, getLocator(this, "getViewSignature"));
	}

	@WebElementLocator(webDesktop = "//*[@id=\"confirmButtonTop\"]")
	protected Button getAgreeAndContinueButton() {
		return new Button(this, getLocator(this, "getAgreeAndContinueButton"));
	}
}
