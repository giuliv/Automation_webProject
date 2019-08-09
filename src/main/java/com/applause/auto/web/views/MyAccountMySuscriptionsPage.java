package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.web.helpers.WebHelper;
import java.lang.invoke.MethodHandles;

@Implementation(is = MyAccountMySuscriptionsPage.class, on = Platform.WEB)
public class MyAccountMySuscriptionsPage extends BaseComponent {

	/* -------- Elements -------- */

	/* -------- Actions -------- */


	/**
	 * Verify Subscription Name is Displayed
	 *
	 * @return boolean
	 */
	public boolean isSubscriptionNameDisplayed() {
		logger.info("Verifying Subscription Name is displayed");
		return getSubscriptionNameText.isDisplayed();
	}

	/**
	 * Verify Next Shipment Date is Displayed
	 *
	 * @return boolean
	 */
	public boolean isNextShipmentDateDisplayed() {
		logger.info("Verifying Next Shipment Date is displayed");
		return getNextShipmentDateText.isDisplayed();
	}

	/**
	 * Verify Subscription Frequency is Displayed
	 *
	 * @return boolean
	 */
	public boolean isSubscriptionFrequencyDateDisplayed() {
		logger.info("Verifying Subscription frequency is displayed");
		return getSubscriptionFrequencyDateText.isDisplayed();
	}

	/**
	 * Verify Manage Subscription button is Displayed
	 *
	 * @return boolean
	 */
	public boolean isManageSubscriptionButtonDisplayed() {
		logger.info("Verifying Manage Subscription button is displayed");
		return getManageSubscriptionButton.isDisplayed();
	}

	/**
	 * Click Manage Subscription
	 *
	 * @return MyAccountManageSubscriptionPage
	 */
	public MyAccountManageSubscriptionPage clickManageSubscription() {
		logger.info("Clicking Manage Subscription");
		getManageSubscriptionButton.click();
		return ComponentFactory.create(MyAccountManageSubscriptionPage.class);
	}

	/**
	 * Verify Shipping Address is Displayed
	 *
	 * @return boolean
	 */
	public boolean isShippingAddressDisplayed() {
		logger.info("Verifying Shipping Address is displayed");
		return getShippingAddressText.isDisplayed();
	}

	/**
	 * Verify Shipping Method is Displayed
	 *
	 * @return boolean
	 */
	public boolean isShippingMethodDisplayed() {
		logger.info("Verifying Shipping Method is displayed");
		return getShippingMethodText.isDisplayed();
	}

	/**
	 * Verify Subscribed Product is Displayed
	 *
	 * @return boolean
	 */
	public boolean isSubscribedProductDisplayed() {
		logger.info("Verifying SubscribedProduct is displayed");
		return getSubscribedProductText.isDisplayed();
	}

	// Protected getters
	@Locate(css = "body > div.wrapper > div > div.main-container.col2-left-layout > div > div.col-main > div > div.page-title > h1", on = Platform.WEB)
	protected Text getViewSignature;

	@Locate(css = "div.sub-id h2", on = Platform.WEB)
	protected Text getSubscriptionNameText;

	@Locate(css = ".sub-shipment span.number", on = Platform.WEB)
	protected Text getNextShipmentDateText;

	@Locate(css = ".sub-frequency span.number", on = Platform.WEB)
	protected Text getSubscriptionFrequencyDateText;

	@Locate(css = "div.recurring-profile-title a", on = Platform.WEB)
	protected Button getManageSubscriptionButton;

	@Locate(xpath = "//div[div[contains(.,'Ship To')]]/address", on = Platform.WEB)
	protected Text getShippingAddressText;

	@Locate(xpath = "//div[div[contains(.,'Shipping Method')]]/p", on = Platform.WEB)
	protected Text getShippingMethodText;

	@Locate(css = "body > div.wrapper > div > div.main-container.col2-left-layout > div > div.col-main > div > div.recurring-profile-section.account-container > div.wrapper-items > div.items > table > tbody", on = Platform.WEB)
	protected Text getSubscribedProductText;

}
