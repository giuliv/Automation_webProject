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

@WebDesktopImplementation(MyAccountMySuscriptionsPage.class)
@WebTabletImplementation(MyAccountMySuscriptionsPage.class)
@WebPhoneImplementation(MyAccountMySuscriptionsPage.class)
public class MyAccountMySuscriptionsPage extends AbstractPage {
	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	@Override
	protected void waitUntilVisible() {
		syncHelper.waitForElementToAppear(getViewSignature());
	}

	// Public actions

	/**
	 * Verify Subscription Name is Displayed
	 *
	 * @return boolean
	 */
	public boolean isSubscriptionNameDisplayed() {
		LOGGER.info("Verifying Subscription Name is displayed");
		return getSubscriptionNameText().isDisplayed();
	}

	/**
	 * Verify Next Shipment Date is Displayed
	 *
	 * @return boolean
	 */
	public boolean isNextShipmentDateDisplayed() {
		LOGGER.info("Verifying Next Shipment Date is displayed");
		return getNextShipmentDateText().isDisplayed();
	}

	/**
	 * Verify Subscription Frequency is Displayed
	 *
	 * @return boolean
	 */
	public boolean isSubscriptionFrequencyDateDisplayed() {
		LOGGER.info("Verifying Subscription frequency is displayed");
		return getSubscriptionFrequencyDateText().isDisplayed();
	}

	/**
	 * Verify Manage Subscription button is Displayed
	 *
	 * @return boolean
	 */
	public boolean isManageSubscriptionButtonDisplayed() {
		LOGGER.info("Verifying Manage Subscription button is displayed");
		return getManageSubscriptionButton().isDisplayed();
	}

	/**
	 * Click Manage Subscription
	 *
	 * @return MyAccountManageSubscriptionPage
	 */
	public MyAccountManageSubscriptionPage clickManageSubscription() {
		LOGGER.info("Clicking Manage Subscription");
		getManageSubscriptionButton().click();
		return PageFactory.create(MyAccountManageSubscriptionPage.class);
	}

	/**
	 * Verify Shipping Address is Displayed
	 *
	 * @return boolean
	 */
	public boolean isShippingAddressDisplayed() {
		LOGGER.info("Verifying Shipping Address is displayed");
		return getShippingAddressText().isDisplayed();
	}

	/**
	 * Verify Shipping Method is Displayed
	 *
	 * @return boolean
	 */
	public boolean isShippingMethodDisplayed() {
		LOGGER.info("Verifying Shipping Method is displayed");
		return getShippingMethodText().isDisplayed();
	}

	/**
	 * Verify Subscribed Product is Displayed
	 *
	 * @return boolean
	 */
	public boolean isSubscribedProductDisplayed() {
		LOGGER.info("Verifying SubscribedProduct is displayed");
		return getSubscribedProductText().isDisplayed();
	}

	// Protected getters
	@WebElementLocator(webDesktop = "body > div.wrapper > div > div.main-container.col2-left-layout > div > div.col-main > div > div.page-title > h1")
	protected Text getViewSignature() {
		return new Text(this, getLocator(this, "getViewSignature"));
	}

	@WebElementLocator(webDesktop = "div.sub-id h2")
	protected Text getSubscriptionNameText() {
		return new Text(this, getLocator(this, "getSubscriptionNameText"));
	}

	@WebElementLocator(webDesktop = ".sub-shipment span.number")
	protected Text getNextShipmentDateText() {
		return new Text(this, getLocator(this, "getNextShipmentDateText"));
	}

	@WebElementLocator(webDesktop = ".sub-frequency span.number")
	protected Text getSubscriptionFrequencyDateText() {
		return new Text(this, getLocator(this, "getSubscriptionFrequencyDateText"));
	}

	@WebElementLocator(webDesktop = "div.recurring-profile-title a")
	protected Button getManageSubscriptionButton() {
		return new Button(this, getLocator(this, "getManageSubscriptionButton"));
	}

	@WebElementLocator(webDesktop = "//div[div[contains(.,'Ship To')]]/address")
	protected Text getShippingAddressText() {
		return new Text(this, getLocator(this, "getShippingAddressText"));
	}

	@WebElementLocator(webDesktop = "//div[div[contains(.,'Shipping Method')]]/p")
	protected Text getShippingMethodText() {
		return new Text(this, getLocator(this, "getShippingMethodText"));
	}

	@WebElementLocator(webDesktop = "body > div.wrapper > div > div.main-container.col2-left-layout > div > div.col-main > div > div.recurring-profile-section.account-container > div.wrapper-items > div.items > table > tbody")
	protected Text getSubscribedProductText() {
		return new Text(this, getLocator(this, "getSubscribedProductText"));
	}

}
