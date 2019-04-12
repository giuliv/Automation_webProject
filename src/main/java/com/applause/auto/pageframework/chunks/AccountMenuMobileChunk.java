package com.applause.auto.pageframework.chunks;

import java.lang.invoke.MethodHandles;

import com.applause.auto.framework.pageframework.device.AbstractDeviceChunk;
import com.applause.auto.framework.pageframework.device.DeviceViewFactory;
import com.applause.auto.framework.pageframework.device.MobileElementLocator;
import com.applause.auto.framework.pageframework.device.factory.AndroidImplementation;
import com.applause.auto.framework.pageframework.device.factory.IosImplementation;
import com.applause.auto.framework.pageframework.devicecontrols.Button;
import com.applause.auto.framework.pageframework.util.logger.LogController;
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

	/*
	 * Protected Getters
	 */

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/profileDetails", iOS = "//XCUIElementTypeStaticText[@name=\"Profile Details\"]")
	protected Button getProfileDetailsButton() {
		return new Button(getLocator(this, "getProfileDetailsButton"));
	}

}
