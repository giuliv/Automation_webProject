package com.applause.auto.web.components;

import java.lang.invoke.MethodHandles;

import com.applause.auto.framework.pageframework.device.AbstractDeviceChunk;
import com.applause.auto.framework.pageframework.device.AbstractDeviceView;
import com.applause.auto.framework.pageframework.device.DeviceViewFactory;
import com.applause.auto.framework.pageframework.device.MobileElementLocator;
import com.applause.auto.framework.pageframework.device.factory.AndroidImplementation;
import com.applause.auto.framework.pageframework.device.factory.IosImplementation;
import com.applause.auto.framework.pageframework.devicecontrols.Button;
import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.mobile.views.AccountHistoryView;
import com.applause.auto.mobile.views.AuthenticationView;
import com.applause.auto.mobile.views.GeneralSettingsView;
import com.applause.auto.mobile.views.PaymentMethodsView;
import com.applause.auto.mobile.views.ProfileDetailsView;

@AndroidImplementation(AndroidAccountMenuMobileChunk.class)
@IosImplementation(AccountMenuMobileChunk.class)
public class AccountMenuMobileChunk extends AbstractDeviceChunk {
	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	/**
	 * Constructor.
	 *
	 * @param selector
	 *            the selector of the chunk
	 */
	public AccountMenuMobileChunk(String selector) {
		super(selector);
	}

	@Override
	protected void waitUntilVisible() {
	}

	/*
	 * Public actions
	 */

	/**
	 * Profile details profile details view.
	 *
	 * @return the profile details view
	 */
	public ProfileDetailsView profileDetails() {
		LOGGER.info("Click on Profile Details button");
		getProfileDetailsButton().pressButton();
		return DeviceViewFactory.create(ProfileDetailsView.class);
	}

	/**
	 * General settings general settings view.
	 *
	 * @return the general settings view
	 */
	public GeneralSettingsView generalSettings() {
		LOGGER.info("Click on General Settings button");
		getGeneralSettingsButton().pressButton();
		return DeviceViewFactory.create(GeneralSettingsView.class);
	}

	/**
	 * Sign out authentication view.
	 *
	 * @return the authentication view
	 */
	public AuthenticationView signOut() {
		LOGGER.info("Click on Sign Out button");
		MobileHelper.scrollToBottom(5);
		getSignOutButton().pressButton();
		getDriver().switchTo().alert().accept();
		return DeviceViewFactory.create(AuthenticationView.class);
	}

	/**
	 * Click Payment Methods
	 *
	 * @return PaymentMethodsView
	 */
	public PaymentMethodsView clickPaymentMethods() {
		LOGGER.info("Click Payment Methods");
		getPaymentMethodsButton().pressButton();
		return DeviceViewFactory.create(PaymentMethodsView.class);
	}

	/**
	 * Sign out t.
	 *
	 * @param <T>
	 *            the type parameter
	 * @param clazz
	 *            the clazz
	 * @return the t
	 */
	public <T extends AbstractDeviceView> T signOut(Class<T> clazz) {
		LOGGER.info("Click on Sign Out button");
		MobileHelper.scrollToBottom(5);
		getSignOutButton().pressButton();
		return DeviceViewFactory.create(clazz);
	}

	/**
	 * Account history account history view.
	 *
	 * @return the account history view
	 */
	public AccountHistoryView accountHistory() {
		LOGGER.info("Click Account History");
		getAccountHistoryButton().pressButton();
		return DeviceViewFactory.create(AccountHistoryView.class);
	}

	/*
	 * Protected Getters
	 */

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/profileDetails", iOS = "//XCUIElementTypeStaticText[@name=\"Profile Details\" and @visible='true']")
	protected Button getProfileDetailsButton() {
		return new Button(getLocator(this, "getProfileDetailsButton"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/logoutButton", iOS = "Sign Out")
	protected Button getSignOutButton() {
		return new Button(getLocator(this, "getSignOutButton"));
	}

	@MobileElementLocator(android = "android:id/button1", iOS = "//XCUIElementTypeButton[@value='Log Out']")
	protected Button getLogOutButton() {
		return new Button(getLocator(this, "getLogOutButton"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/generalSettings", iOS = "//XCUIElementTypeStaticText[@name=\"General Settings\"]")
	protected Button getGeneralSettingsButton() {
		return new Button(getLocator(this, "getGeneralSettingsButton"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/accountActivity", iOS = "//XCUIElementTypeStaticText[@name=\"Account History\"]")
	protected Button getAccountHistoryButton() {
		return new Button(getLocator(this, "getAccountHistoryButton"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/paymentMethods", iOS = "Payment Methods")
	protected Button getPaymentMethodsButton() {
		return new Button(getLocator(this, "getPaymentMethodsButton"));
	}
}

class AndroidAccountMenuMobileChunk extends AccountMenuMobileChunk {

	/**
	 * Constructor.
	 *
	 * @param selector
	 *            the selector of the chunk
	 */
	public AndroidAccountMenuMobileChunk(String selector) {
		super(selector);
	}

	public AuthenticationView signOut() {
		LOGGER.info("Click on Sign Out button");
		MobileHelper.scrollToBottom(5);
		getSignOutButton().pressButton();
		getLogOutButton().pressButton();
		return DeviceViewFactory.create(AuthenticationView.class);
	}

	/**
	 * Sign out t.
	 *
	 * @param <T>
	 *            the type parameter
	 * @param clazz
	 *            the clazz
	 * @return the t
	 */
	public <T extends AbstractDeviceView> T signOut(Class<T> clazz) {
		LOGGER.info("Click on Sign Out button");
		MobileHelper.scrollToBottom(5);
		getSignOutButton().pressButton();
		getLogOutButton().pressButton();
		return DeviceViewFactory.create(clazz);
	}

}
