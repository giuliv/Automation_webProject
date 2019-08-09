package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.web.helpers.WebHelper;
import java.lang.invoke.MethodHandles;

@Implementation(is = MyAccountManageSubscriptionPage.class, on = Platform.WEB)
public class MyAccountManageSubscriptionPage extends BaseComponent {

	/* -------- Elements -------- */

	/* -------- Actions -------- */


	/**
	 * Verify Pause Cancel Subscription button is Displayed
	 *
	 * @return boolean
	 */
	public boolean isPauseCancelSubscriptionButtonDisplayed() {
		logger.info("Verifying Pause/Cancel Subscription button is displayed");
		return getPauseCancelSubscriptionButton.isDisplayed();
	}

	/**
	 * Verify Manage Shipment is Displayed
	 *
	 * @return boolean
	 */
	public boolean isManageShipmentButtonDisplayed() {
		logger.info("Verifying Manage Shipment Button is displayed");
		return getManageShipmentButton.isDisplayed();
	}

	/**
	 * Verify Billing Address is Displayed
	 *
	 * @return boolean
	 */
	public boolean isBillingAddressDisplayed() {
		logger.info("Verifying Billing Address is displayed");
		return getBillingAddressText.isDisplayed();
	}

	// Protected getters
	@Locate(css = "body > div.wrapper > div > div.main-container.col2-left-layout > div > div.col-main > div > div.page-title > h1", on = Platform.WEB)
	protected Text getViewSignature;

	@Locate(css = ".recurring-profile-title a", on = Platform.WEB)
	protected Button getPauseCancelSubscriptionButton;

	@Locate(css = "div.shipment-title a", on = Platform.WEB)
	protected Button getManageShipmentButton;

	@Locate(xpath = "//div[div[contains(.,'Billing Address')]]/address", on = Platform.WEB)
	protected Text getBillingAddressText;

}
