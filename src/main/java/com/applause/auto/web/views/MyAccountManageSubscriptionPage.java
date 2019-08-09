package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.web.helpers.WebHelper;
import java.lang.invoke.MethodHandles;

@Implementation(is = MyAccountManageSubscriptionPage.class, on = Platform.WEB_DESKTOP)
@Implementation(is = MyAccountManageSubscriptionPage.class, on = Platform.WEB_MOBILE_TABLET)
@Implementation(is = MyAccountManageSubscriptionPage.class, on = Platform.WEB_MOBILE_PHONE)
public class MyAccountManageSubscriptionPage extends BaseComponent {

	// Public actions

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
	@Locate(jQuery = "body > div.wrapper > div > div.main-container.col2-left-layout > div > div.col-main > div > div.page-title > h1", on = Platform.WEB_DESKTOP)
	protected Text getViewSignature;

	@Locate(jQuery = ".recurring-profile-title a", on = Platform.WEB_DESKTOP)
	protected Button getPauseCancelSubscriptionButton;

	@Locate(jQuery = "div.shipment-title a", on = Platform.WEB_DESKTOP)
	protected Button getManageShipmentButton;

	@Locate(xpath = "//div[div[contains(.,'Billing Address')]]/address", on = Platform.WEB_DESKTOP)
	protected Text getBillingAddressText;

}
