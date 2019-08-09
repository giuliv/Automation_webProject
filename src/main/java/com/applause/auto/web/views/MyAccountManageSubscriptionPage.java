package com.applause.auto.web.views;

import java.lang.invoke.MethodHandles;

import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.framework.pageframework.web.AbstractPage;
import com.applause.auto.framework.pageframework.web.WebElementLocator;
import com.applause.auto.framework.pageframework.web.factory.WebDesktopImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebPhoneImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebTabletImplementation;
import com.applause.auto.framework.pageframework.webcontrols.Button;
import com.applause.auto.framework.pageframework.webcontrols.Text;
import com.applause.auto.web.helpers.WebHelper;

@WebDesktopImplementation(MyAccountManageSubscriptionPage.class)
@WebTabletImplementation(MyAccountManageSubscriptionPage.class)
@WebPhoneImplementation(MyAccountManageSubscriptionPage.class)
public class MyAccountManageSubscriptionPage extends AbstractPage {
	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	@Override
	protected void waitUntilVisible() {
		WebHelper.waitForDocument();
		syncHelper.waitForElementToAppear(getViewSignature());
	}

	// Public actions

	/**
	 * Verify Pause Cancel Subscription button is Displayed
	 *
	 * @return boolean
	 */
	public boolean isPauseCancelSubscriptionButtonDisplayed() {
		LOGGER.info("Verifying Pause/Cancel Subscription button is displayed");
		return getPauseCancelSubscriptionButton().isDisplayed();
	}

	/**
	 * Verify Manage Shipment is Displayed
	 *
	 * @return boolean
	 */
	public boolean isManageShipmentButtonDisplayed() {
		LOGGER.info("Verifying Manage Shipment Button is displayed");
		return getManageShipmentButton().isDisplayed();
	}

	/**
	 * Verify Billing Address is Displayed
	 *
	 * @return boolean
	 */
	public boolean isBillingAddressDisplayed() {
		LOGGER.info("Verifying Billing Address is displayed");
		return getBillingAddressText().isDisplayed();
	}

	// Protected getters
	@WebElementLocator(webDesktop = "body > div.wrapper > div > div.main-container.col2-left-layout > div > div.col-main > div > div.page-title > h1")
	protected Text getViewSignature() {
		return new Text(this, getLocator(this, "getViewSignature"));
	}

	@WebElementLocator(webDesktop = ".recurring-profile-title a")
	protected Button getPauseCancelSubscriptionButton() {
		return new Button(this, getLocator(this, "getPauseCancelSubscriptionButton"));
	}

	@WebElementLocator(webDesktop = "div.shipment-title a")
	protected Button getManageShipmentButton() {
		return new Button(this, getLocator(this, "getManageShipmentButton"));
	}

	@WebElementLocator(webDesktop = "//div[div[contains(.,'Billing Address')]]/address")
	protected Text getBillingAddressText() {
		return new Text(this, getLocator(this, "getBillingAddressText"));
	}

}
