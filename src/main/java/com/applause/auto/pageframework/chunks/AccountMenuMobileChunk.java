package com.applause.auto.pageframework.chunks;

import java.lang.invoke.MethodHandles;

import com.applause.auto.framework.pageframework.device.AbstractDeviceChunk;
import com.applause.auto.framework.pageframework.device.DeviceViewFactory;
import com.applause.auto.framework.pageframework.device.MobileElementLocator;
import com.applause.auto.framework.pageframework.device.factory.AndroidImplementation;
import com.applause.auto.framework.pageframework.device.factory.IosImplementation;
import com.applause.auto.framework.pageframework.devicecontrols.Button;
import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.pageframework.helpers.MobileHelper;
import com.applause.auto.pageframework.views.AuthenticationView;
import com.applause.auto.pageframework.views.GeneralSettingsView;
import com.applause.auto.pageframework.views.ProfileDetailsView;

@AndroidImplementation(AccountMenuMobileChunk.class)
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
		MobileHelper.scrollDown(5);
		getSignOutButton().pressButton();
		getLogOutButton().pressButton();
		return DeviceViewFactory.create(AuthenticationView.class);
	}

	/*
	 * Protected Getters
	 */

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/profileDetails", iOS = "//XCUIElementTypeStaticText[@name=\"Profile Details\"]")
	protected Button getProfileDetailsButton() {
		return new Button(getLocator(this, "getProfileDetailsButton"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/logoutButton", iOS = "Sign Out")
	protected Button getSignOutButton() {
		return new Button(getLocator(this, "getSignOutButton"));
	}

	@MobileElementLocator(android = "android:id/button1", iOS = "Log Out")
	protected Button getLogOutButton() {
		return new Button(getLocator(this, "getLogOutButton"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/generalSettings", iOS = "//XCUIElementTypeStaticText[@name=\"General Settings\"]")
	protected Button getGeneralSettingsButton() {
		return new Button(getLocator(this, "getGeneralSettingsButton"));
	}

}
