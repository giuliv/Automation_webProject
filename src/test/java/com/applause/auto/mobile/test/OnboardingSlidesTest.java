package com.applause.auto.mobile.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.applause.auto.framework.pageframework.device.DeviceViewFactory;
import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.pageframework.testdata.TestConstants;
import com.applause.auto.pageframework.views.AuthenticationView;
import com.applause.auto.pageframework.views.ExploreOffersView;
import com.applause.auto.pageframework.views.LandingView;
import com.applause.auto.pageframework.views.OrderAheadView;
import com.applause.auto.pageframework.views.PayFasterView;

/**
 * This is a sample test that verifies the project is setup correctly and can
 * execute a simple test.
 */
public class OnboardingSlidesTest extends BaseTest {

	private LogController LOGGER = new LogController(OnboardingSlidesTest.class);

	@Test(groups = { TestConstants.TestNGGroups.ONBOARDING }, description = "625878")
	public void onboardingSlidesTest() {

		LOGGER.info("Launch the app and arrive at the first onboarding screen view");
		LandingView landingView = DeviceViewFactory.create(LandingView.class);
		Assert.assertEquals(landingView.getHeadingTextValue(), "Earn Rewards.",
				"First screen text value is not correct");

		LOGGER.info("Swipe left and verify Explore Offers screen has correct title");
		ExploreOffersView exploreOffersView = landingView.swipeLeftOnScreen();
		Assert.assertEquals(exploreOffersView.getHeadingTextValue(), "Explore Offers.",
				"First screen text value is not correct");

		LOGGER.info("Swipe left and verify Pay Faster screen has correct title");
		PayFasterView payFasterView = exploreOffersView.swipeLeftOnScreen();
		Assert.assertEquals(payFasterView.getHeadingTextValue(), "Pay Faster.",
				"First screen text value is not correct");

		LOGGER.info("Swipe left and verify Order Ahead screen has correct title");
		OrderAheadView orderAheadView = payFasterView.swipeLeftOnScreen();
		Assert.assertEquals(orderAheadView.getHeadingTextValue(), "Order Ahead.",
				"First screen text value is not correct");

		LOGGER.info("Press Get Started button and verify Peetnik Rewards title");
		AuthenticationView authenticationView = orderAheadView.clickGetStartedButton();
		Assert.assertEquals(authenticationView.getRewardTitleTextValue(), "Peetnik Rewards",
				"Reward title text value is not correct");

	}
}
