package com.applause.auto.mobile.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.applause.auto.framework.pageframework.device.DeviceViewFactory;
import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.pageframework.chunks.mobile.AllowLocationServicesPopupChunk;
import com.applause.auto.pageframework.chunks.mobile.AllowLocationServicesSystemPopupChunk;
import com.applause.auto.pageframework.testdata.TestConstants;
import com.applause.auto.pageframework.views.DashboardView;
import com.applause.auto.pageframework.views.LandingView;
import com.applause.auto.pageframework.views.OrderAheadView;
import com.applause.auto.pageframework.views.SelectCoffeeBarView;

public class OrderAheadTest extends BaseTest {

	private LogController LOGGER = new LogController(OrderAheadTest.class);

	@Test(groups = { TestConstants.TestNGGroups.ORDER_AHEAD }, description = "625889")
	public void locationServicesNotEnabled() {
		LOGGER.info("Launch the app and arrive at the first on boarding screen view");
		LandingView landingView = DeviceViewFactory.create(LandingView.class);
		DashboardView dashboardView = peetsMobileHelper.signIn(landingView, TestConstants.MyAccountTestData.EMAIL,
				TestConstants.MyAccountTestData.PASSWORD, DashboardView.class);
		Assert.assertNotNull(dashboardView, "Dashboard View does not displayed");

		LOGGER.info("Tap Order icon on the bottom nav bar");
		OrderAheadView orderAhead = dashboardView.getBottomNavigationMenu().order();

		LOGGER.info("Header: Order Ahead");
		Assert.assertEquals(orderAhead.getHeadingTextValue(), "Order Ahead", "Incorrect header");

		LOGGER.info("Sub-header: Bypass the line and proceed to great coffee.");
		Assert.assertEquals(orderAhead.getSubHeaderTextValue(), "Bypass the line and proceed to great coffee.",
				"Incorrect sub-header");

		LOGGER.info("[Button] See Participating Coffeebars");
		Assert.assertTrue(orderAhead.isParticipatingCoffeebarsDisplayed(),
				"See Participating Coffeebars does not displayed");

		LOGGER.info("Tap See Participating Coffeebars");
		SelectCoffeeBarView selectCoffeeBarView = orderAhead.participatingCoffeebars();

		LOGGER.info("User should be taken to Select Coffeebar screen:");
		Assert.assertNotNull(selectCoffeeBarView, "User does not taken to Select Coffeebar screen");

		LOGGER.info("Allow Location Services to help you find nearby Peet's Coffeebars.");
		Assert.assertEquals(selectCoffeeBarView.getEnableLocationDescription(),
				"Allow Location Services to help you find nearby Peet's Coffeebars.",
				"'Allow Location Services to help you find nearby Peet's Coffeebars.' text does not displayed");

		LOGGER.info("[Button] Enable Location");
		Assert.assertTrue(selectCoffeeBarView.isEnableLocationButtonDisplayed(),
				"[Button] Enable Location does not displayed");

		LOGGER.info("Tap enable location");
		AllowLocationServicesPopupChunk allowLocationServicesPopupChunk = selectCoffeeBarView.enableLocation();

		LOGGER.info("Make sure Peet's branded Location Services alert appears:");
		LOGGER.info("Title: Allow Location Services to help you find nearby Peet's Coffeebars.");
		Assert.assertEquals(allowLocationServicesPopupChunk.getTitle(),
				"Allow Location Services to help you find nearby Peet's Coffeebars",
				"'Allow Location Services to help you find nearby Peet's Coffeebars' title does not found");

		LOGGER.info("Text: Location Services will:\n" + "\n" + "* Only use your location while using the app\n" + "\n"
				+ "* Not share your locations or information\n" + "\n" + "* Pinpoint the coffeebars closest to you");
		// Assert.assertEquals(allowLocationServicesPopupChunk.getFormattedMessage(),
		// "Location Services will: Allow Location Services to help you find nearby Peet’s
		// Coffeebars Only use your location while using the app Not share your location or
		// information Pinpoint the coffeebars closest to you",
		// "Unexpected text: ");
		LOGGER.info("[Button] Not Now [Button] Allow");
		Assert.assertTrue(allowLocationServicesPopupChunk.isAllowButtonDisplayed(), "Allow button does not displayed");
		Assert.assertTrue(allowLocationServicesPopupChunk.isNotNowButtonDisplayed(),
				"Not Now button does not displayed");

		LOGGER.info("Tap Allow");
		AllowLocationServicesSystemPopupChunk allowLocationServicesSystemPopupChunk = allowLocationServicesPopupChunk
				.allow();

		LOGGER.info("Make sure user sees another UI alert:\n" + "\n"
				+ "Title: Allow \"Peet's\" to access your location while you are using the app?");
		Assert.assertEquals(allowLocationServicesSystemPopupChunk.getTitle(),
				"Allow “Peets-Sandbox” to access your location while you are using the app?",
				"Title: Allow \"Peet's\" to access your location while you are using the app? doe not displayed");
		LOGGER.info(
				"Text: Location services will only use your location while using the app, and will not share your location or information. Your location will be used to find the nearest coffeebar and place a mobile order.");
		Assert.assertEquals(allowLocationServicesSystemPopupChunk.getFormattedMessage(),
				"Location Services will only use your location while using the app, and will not share your location or information. Your location will be used to find the nearest coffeebar and place a mobile order.",
				"Text: 'Location Services will only use your location while using the app, and will not share your location or information. Your location will be used to find the nearest coffeebar and place a mobile order.' does not displayed");

		LOGGER.info("[Don't Allow] [Allow]");
		Assert.assertTrue(allowLocationServicesSystemPopupChunk.isAllowButtonDisplayed(),
				"Allow button does not displayed");
		Assert.assertTrue(allowLocationServicesSystemPopupChunk.isDoNotAllowButtonDisplayed(),
				"Don't Allow button does not displayed");

		LOGGER.info("Tap Allow");
		allowLocationServicesSystemPopupChunk.allow();

		LOGGER.info(
				"User should see loading dial, nearby stores should appear under nearby stores tab and user should be able to select a store");
	}

}
