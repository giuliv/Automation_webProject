package com.applause.auto.mobile.views;

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

	/**
	 * Gets sub header text value.
	 *
	 * @return the sub header text value
	 */
	public String getSubHeaderTextValue() {
		return getSubHeaderText().getStringValue();
	}

	/**
	 * Is participating coffeebars displayed boolean.
	 *
	 * @return the boolean
	 */
	public boolean isParticipatingCoffeebarsDisplayed() {
		return syncHelper.isElementDisplayed(getLocator(this, "getParticipatingCoffeebarsButton"));
	}

	/**
	 * Participating coffeebars select coffee bar view.
	 *
	 * @return the select coffee bar view
	 */
	public SelectCoffeeBarView participatingCoffeebars() {
		LOGGER.info("Tap See Participating Coffeebars");
		getParticipatingCoffeebarsButton().pressButton();
		return DeviceViewFactory.create(SelectCoffeeBarView.class);
	}

	/*
	 * Protected Getters
	 */

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/seeCoffeebarsButton", iOS = "See Participating Coffeebars")
	protected Button getParticipatingCoffeebarsButton() {
		return new Button(getLocator(this, "getParticipatingCoffeebarsButton"));
	}

	@MobileElementLocator(android = "new UiSelector().textContains(\"Order Ahead\")", iOS = "Order Ahead.")
	protected Text getHeadingText() {
		return new Text(getLocator(this, "getHeadingText"));
	}

	@MobileElementLocator(android = "//*[@resource-id='com.wearehathway.peets.development:id/topContainer']/android.widget.TextView[2]", iOS = "Bypass the line and proceed to great coffee.")
	protected Text getSubHeaderText() {
		return new Text(getLocator(this, "getSubHeaderText"));
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
