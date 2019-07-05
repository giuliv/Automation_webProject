package com.applause.auto.pageframework.chunks.mobile;

import java.lang.invoke.MethodHandles;

import com.applause.auto.framework.pageframework.device.AbstractDeviceChunk;
import com.applause.auto.framework.pageframework.device.MobileElementLocator;
import com.applause.auto.framework.pageframework.device.factory.AndroidImplementation;
import com.applause.auto.framework.pageframework.device.factory.IosImplementation;
import com.applause.auto.framework.pageframework.devicecontrols.BaseDeviceControl;
import com.applause.auto.framework.pageframework.devicecontrols.Button;
import com.applause.auto.framework.pageframework.devicecontrols.Text;
import com.applause.auto.framework.pageframework.util.logger.LogController;

/**
 * The type Allow location services system popup chunk.
 */
@AndroidImplementation(AllowLocationServicesSystemPopupChunk.class)
@IosImplementation(AllowLocationServicesSystemPopupChunk.class)
public class AllowLocationServicesSystemPopupChunk extends AbstractDeviceChunk {
	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	public AllowLocationServicesSystemPopupChunk(String selector) {
		super(selector);
	}

	@Override
	protected void waitUntilVisible() {
		syncHelper.waitForElementToAppear(getLocator(this, "getSignature"));
	}

	/**
	 * Gets title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return getTitleText().getStringValue();
	}

	/**
	 * Gets formatted message.
	 *
	 * @return the formatted message
	 */
	public String getFormattedMessage() {
		return getSubTitleText().getStringValue();
	}

	/**
	 * Is allow button displayed boolean.
	 *
	 * @return the boolean
	 */
	public boolean isAllowButtonDisplayed() {
		return syncHelper.isElementDisplayed(getLocator(this, "getAllowButton"));
	}

	/**
	 * Is do not allow button displayed boolean.
	 *
	 * @return the boolean
	 */
	public boolean isDoNotAllowButtonDisplayed() {
		return syncHelper.isElementDisplayed(getLocator(this, "getDoNotAllowButton"));
	}

	/**
	 * Allow.
	 */
	public void allow() {
		LOGGER.info("Tap Allow button");
		getAllowButton().pressButton();
	}

	/*
	 * Protected Getters
	 */

	@MobileElementLocator(android = "android:id/message", iOS = "//XCUIElementTypeAlert[@name=\"Allow “Peets-Sandbox” to access your location while you are using the app?\"]")
	protected BaseDeviceControl getSignature() {
		return new BaseDeviceControl(getLocator(this, "getSignature"));
	}

	@MobileElementLocator(android = "android:id/message", iOS = "//XCUIElementTypeStaticText[@name=\"Allow “Peets-Sandbox” to access your location while you are using the app?\"]")
	protected Text getTitleText() {
		return new Text(getLocator(this, "getTitleText"));
	}

	@MobileElementLocator(android = "android:id/button1", iOS = "Allow")
	protected Button getAllowButton() {
		return new Button(getLocator(this, "getAllowButton"));
	}

	@MobileElementLocator(android = "TBD", iOS = "Don’t Allow")
	protected Button getDoNotAllowButton() {
		return new Button(getLocator(this, "getDoNotAllowButton"));
	}

	@MobileElementLocator(android = "//android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.TextView[2]", iOS = "//XCUIElementTypeStaticText[@name=\"Location Services will only use your location while using the app, and will not share your location or information. Your location will be used to find the nearest coffeebar and place a mobile order.\"]")
	protected Text getSubTitleText() {
		return new Text(getLocator(this, "getSubTitleText"));
	}
}
