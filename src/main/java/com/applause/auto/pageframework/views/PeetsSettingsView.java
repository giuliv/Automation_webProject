package com.applause.auto.pageframework.views;

import java.lang.invoke.MethodHandles;

import com.applause.auto.framework.pageframework.device.AbstractDeviceView;
import com.applause.auto.framework.pageframework.device.DeviceViewFactory;
import com.applause.auto.framework.pageframework.device.MobileElementLocator;
import com.applause.auto.framework.pageframework.device.factory.AndroidImplementation;
import com.applause.auto.framework.pageframework.device.factory.IosImplementation;
import com.applause.auto.framework.pageframework.devicecontrols.Button;
import com.applause.auto.framework.pageframework.devicecontrols.TextBox;
import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.pageframework.helpers.MobileHelper;

@AndroidImplementation(AndroidPeetsSettingsView.class)
@IosImplementation(PeetsSettingsView.class)
public class PeetsSettingsView extends AbstractDeviceView {

	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	@Override
	protected void waitUntilVisible() {
		syncHelper.waitForElementToAppear(getSignature());
	}

	/**
	 * Open location.
	 */
	public void openLocation() {
		LOGGER.info("Open Location menu");
		getLocationButton().tap();
	}

	/**
	 * Select never.
	 */
	public void selectNever() {
		syncHelper.suspend(5000);
		LOGGER.info("Select Never");

		getNeverButton().tap();
	}

	/**
	 * Back to app general settings view.
	 *
	 * @return the general settings view
	 */
	public GeneralSettingsView backToApp() {
		LOGGER.info("Returning to application");
		MobileHelper.activateApp();
		return DeviceViewFactory.create(GeneralSettingsView.class);
	}

	/*
	 * Protected Getters
	 */

	@MobileElementLocator(android = "com.android.settings:id/main_content", iOS = "//XCUIElementTypeApplication[@name=\"Settings\"]")
	protected TextBox getSignature() {
		return new TextBox(getLocator(this, "getSignature"));
	}

	@MobileElementLocator(android = "com.android.settings:id/switch_widget", iOS = "//XCUIElementTypeCell[@name=\"Location\"]")
	protected Button getLocationButton() {
		return new Button(getLocator(this, "getLocationButton"));
	}

	@MobileElementLocator(android = "//android.widget.Button[@text='CLOSE']", iOS = "//XCUIElementTypeCell[@name=\"Never\"]")
	protected Button getNeverButton() {
		return new Button(getLocator(this, "getNeverButton"));
	}
}

class AndroidPeetsSettingsView extends PeetsSettingsView {
	@Override
	public GeneralSettingsView backToApp() {
		LOGGER.info("Returning to application");
		MobileHelper.tapAndroidDeviceBackButton();
		return DeviceViewFactory.create(GeneralSettingsView.class);
	}

}
