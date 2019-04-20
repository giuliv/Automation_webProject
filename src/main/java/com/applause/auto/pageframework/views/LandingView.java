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

@AndroidImplementation(AndroidLandingView.class)
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

	/**
	 * Create account create account view.
	 *
	 * @return the create account view
	 */
	public CreateAccountView createAccount() {
		LOGGER.info("Tap on create account button");
		getCreateAccountButton().pressButton();
		return DeviceViewFactory.create(CreateAccountView.class);
	}

	/**
	 * Skip offer.
	 */
	public void skipOffer() {
		LOGGER.info("Swipe left and verify Explore Offers screen has correct title");
		MobileHelper.scrollDown(3);
		ExploreOffersView exploreOffersView = swipeLeftOnScreen();
		PayFasterView payFasterView = exploreOffersView.swipeLeftOnScreen();
		OrderAheadView orderAheadView = payFasterView.swipeLeftOnScreen();
		AuthenticationView authenticationView = orderAheadView.clickGetStartedButton();
	}

	/**
	 * Sign in sign in view.
	 *
	 * @return the sign in view
	 */
	public SignInView signIn() {
		LOGGER.info("Click on Sign In button");
		getSignInButton().pressButton();
		return DeviceViewFactory.create(SignInView.class);
	}

	/**
	 * Get the text value of the heading
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

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/signUp", iOS = "Create Account")
	protected Button getCreateAccountButton() {
		return new Button(getLocator(this, "getCreateAccountButton"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/logIn", iOS = "Sign In")
	protected Button getSignInButton() {
		return new Button(getLocator(this, "getSignInButton"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/onBoardingViewPager", iOS = "//XCUIElementTypeOther[1]/XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther")
	protected ScrollView getViewPager() {
		return new ScrollView(getLocator(this, "getViewPager"));
	}
}

class AndroidLandingView extends LandingView {

}
