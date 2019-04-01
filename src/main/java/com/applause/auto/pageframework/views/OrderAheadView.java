package com.applause.auto.pageframework.views;

import java.lang.invoke.MethodHandles;

import com.applause.auto.framework.pageframework.device.AbstractDeviceView;
import com.applause.auto.framework.pageframework.device.DeviceViewFactory;
import com.applause.auto.framework.pageframework.device.MobileElementLocator;
import com.applause.auto.framework.pageframework.device.factory.AndroidImplementation;
import com.applause.auto.framework.pageframework.device.factory.IosImplementation;
import com.applause.auto.framework.pageframework.devicecontrols.Button;
import com.applause.auto.framework.pageframework.devicecontrols.Text;
import com.applause.auto.framework.pageframework.util.logger.LogController;

@AndroidImplementation(OrderAheadView.class)
@IosImplementation(OrderAheadView.class)
public class OrderAheadView extends AbstractDeviceView {

	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	@Override
	protected void waitUntilVisible() {
		syncHelper.waitForElementToAppear(getHeadingText());
	}

	/*
	 * Public Actions
	 */
	/**
	 * Press Get Started button
	 * 
	 * @return
	 */
	public AuthenticationView clickGetStartedButton() {
		LOGGER.info("Pressing Get Started button and expected to land at Peetnik Rewards auth screen");
		getGetStartedButton().pressButton();
		return DeviceViewFactory.create(AuthenticationView.class);
	}

	/**
	 * Get the text vaalue of the heading
	 * 
	 * @return
	 */
	public String getHeadingTextValue() {
		return getHeadingText().getStringValue();
	}

	/*
	 * Protected Getters
	 */

	@MobileElementLocator(android = "new UiSelector().textContains(\"Order Ahead\")", iOS = "Order Ahead.")
	protected Text getHeadingText() {
		return new Text(getLocator(this, "getHeadingText"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/getStartedButton", iOS = "Get Started")
	protected Button getGetStartedButton() {
		return new Button(getLocator(this, "getGetStartedButton"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/skipTextView", iOS = "//XCUIElementTypeOther[1]/XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther")
	protected Button getSkipButton() {
		return new Button(getLocator(this, "getSkipButton"));
	}

}
