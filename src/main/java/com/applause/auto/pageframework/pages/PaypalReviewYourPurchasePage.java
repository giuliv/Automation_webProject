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
	protected static String winHandleBefore = "";

	@Override
	protected void waitUntilVisible() {
		// Switch to new window opened
		long endTime = System.currentTimeMillis() + 60000;
		while ((getDriver().getWindowHandles().size() == 1) && (endTime < System.currentTimeMillis())) {
			LOGGER.info("Wait for new window");
		}
		winHandleBefore = getDriver().getWindowHandle();
		for (String winHandle : getDriver().getWindowHandles()) {
			if (!winHandle.equals(winHandleBefore)) {
				getDriver().switchTo().window(winHandle);
			}
		}

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
		syncHelper.suspend(25000); // just waiting sandbox to completed
		new WebHelper().jsClick(getContinueButton().getWebElement());
		syncHelper.suspend(25000); // just waiting sandbox to completed
		getAgreeAndContinueButton().click();
		getDriver().switchTo().window(winHandleBefore);
		syncHelper.suspend(25000); // just waiting sandbox to completed
		return PageFactory.create(CheckoutPlaceOrderPage.class);
	}

	// Protected getters
	@WebElementLocator(webDesktop = "//*[@id=\"paypalLogo\"]")
	protected Image getViewSignature() {
		return new Image(this, getLocator(this, "getViewSignature"));
	}

	@WebElementLocator(webDesktop = "//*[contains(@class,'confirmButton')]")
	protected Button getContinueButton() {
		return new Button(this, getLocator(this, "getContinueButton"));
	}

	@WebElementLocator(webDesktop = "//*[@id='confirmButtonTop' or @class='confirmButton']")
	protected Button getAgreeAndContinueButton() {
		return new Button(this, getLocator(this, "getAgreeAndContinueButton"));
	}
}
