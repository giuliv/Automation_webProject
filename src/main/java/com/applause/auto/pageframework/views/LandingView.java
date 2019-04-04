package com.applause.auto.pageframework.views;

import java.lang.invoke.MethodHandles;

import com.applause.auto.framework.pageframework.device.AbstractDeviceView;
import com.applause.auto.framework.pageframework.device.DeviceViewFactory;
import com.applause.auto.framework.pageframework.device.MobileElementLocator;
import com.applause.auto.framework.pageframework.device.factory.AndroidImplementation;
import com.applause.auto.framework.pageframework.device.factory.IosImplementation;
import com.applause.auto.framework.pageframework.devicecontrols.Button;
import com.applause.auto.framework.pageframework.devicecontrols.ScrollView;
import com.applause.auto.framework.pageframework.devicecontrols.Text;
import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.pageframework.helpers.MobileHelper;

@AndroidImplementation(LandingView.class)
@IosImplementation(LandingView.class)
public class LandingView extends AbstractDeviceView {

	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	@Override
	protected void waitUntilVisible() {
		syncHelper.waitForElementToAppear(getHeadingText());
	}

	/*
	 * Public Actions
	 */
	/**
	 * Swipe left on tutorial view and expect to arrive at next view
	 * 
	 * @return
	 */
	public ExploreOffersView swipeLeftOnScreen() {
		LOGGER.info("Swiping left to get to next tutorial view");
		MobileHelper.swipeLeft(getViewPager().getLocation().y);
		return DeviceViewFactory.create(ExploreOffersView.class);
	}

	public CreateAccountView createAccount() {
		LOGGER.info("Tap on create account button");
		getCreateAccountButton().pressButton();
		return DeviceViewFactory.create(CreateAccountView.class);
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

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/headingText", iOS = "Earn Rewards.")
	protected Text getHeadingText() {
		return new Text(getLocator(this, "getHeadingText"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/skipTextView", iOS = "Skip")
	protected Button getSkipButton() {
		return new Button(getLocator(this, "getSkipButton"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/skipTextView", iOS = "Create Account")
	protected Button getCreateAccountButton() {
		return new Button(getLocator(this, "getCreateAccountButton"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/onBoardingViewPager", iOS = "//XCUIElementTypeOther[1]/XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther")
	protected ScrollView getViewPager() {
		return new ScrollView(getLocator(this, "getViewPager"));
	}

}
