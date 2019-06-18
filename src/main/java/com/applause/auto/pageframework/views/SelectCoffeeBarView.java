package com.applause.auto.pageframework.views;

import java.lang.invoke.MethodHandles;

import com.applause.auto.framework.pageframework.device.AbstractDeviceView;
import com.applause.auto.framework.pageframework.device.DeviceChunkFactory;
import com.applause.auto.framework.pageframework.device.MobileElementLocator;
import com.applause.auto.framework.pageframework.device.factory.AndroidImplementation;
import com.applause.auto.framework.pageframework.device.factory.IosImplementation;
import com.applause.auto.framework.pageframework.devicecontrols.BaseDeviceControl;
import com.applause.auto.framework.pageframework.devicecontrols.Button;
import com.applause.auto.framework.pageframework.devicecontrols.Text;
import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.pageframework.chunks.mobile.AllowLocationServicesPopupChunk;

@AndroidImplementation(AndroidSelectCoffeeBarView.class)
@IosImplementation(SelectCoffeeBarView.class)
public class SelectCoffeeBarView extends AbstractDeviceView {

	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	@Override
	protected void waitUntilVisible() {
		syncHelper.waitForElementToAppear(getSignature());
	}

	/*
	 * Public Actions
	 */

	/**
	 * Gets enable location description.
	 *
	 * @return the enable location description
	 */
	public String getEnableLocationDescription() {
		return getEnableLocationDescriptionText().getStringValue();
	}

	/**
	 * Is enable location button displayed boolean.
	 *
	 * @return the boolean
	 */
	public boolean isEnableLocationButtonDisplayed() {
		return syncHelper.isElementDisplayed(getLocator(this, "getEnableLocationButton"));
	}

	/**
	 * Enable location allow location services popup chunk.
	 *
	 * @return the allow location services popup chunk
	 */
	public AllowLocationServicesPopupChunk enableLocation() {
		LOGGER.info("Tap enable location button");
		getEnableLocationButton().pressButton();
		return DeviceChunkFactory.create(AllowLocationServicesPopupChunk.class, "");
	}

	/*
	 * Protected Getters
	 */

	@MobileElementLocator(android = "//android.widget.TextView[@resource-id='Select Coffeebar']", iOS = "//XCUIElementTypeNavigationBar[@name='Select Coffeebar']")
	protected BaseDeviceControl getSignature() {
		return new BaseDeviceControl(getLocator(this, "getSignature"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/noStoreMessage", iOS = "//XCUIElementTypeButton[@name=\"Enable Location\"]/preceding-sibling::*")
	protected Text getEnableLocationDescriptionText() {
		return new Text(getLocator(this, "getEnableLocationDescriptionText"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/enableLocationServiceButton", iOS = "Enable Location")
	protected Button getEnableLocationButton() {
		return new Button(getLocator(this, "getEnableLocationButton"));
	}

}

class AndroidSelectCoffeeBarView extends SelectCoffeeBarView {
	@Override
	protected void waitUntilVisible() {
		// Workaround for Automator hang
		DeviceChunkFactory.create(AllowLocationServicesPopupChunk.class, "").notNow();
		syncHelper.waitForElementToAppear(getSignature());
	}
}