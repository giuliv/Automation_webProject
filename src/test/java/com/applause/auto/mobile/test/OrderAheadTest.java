package com.applause.auto.mobile.test;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.applause.auto.framework.pageframework.device.DeviceViewFactory;
import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.pageframework.chunks.mobile.AllowLocationServicesPopupChunk;
import com.applause.auto.pageframework.testdata.TestConstants;
import com.applause.auto.pageframework.views.DashboardView;
import com.applause.auto.pageframework.views.LandingView;
import com.applause.auto.pageframework.views.NewOrderView;
import com.applause.auto.pageframework.views.OrderAheadView;
import com.applause.auto.pageframework.views.ProductDetailsView;
import com.applause.auto.pageframework.views.SearchResultsView;
import com.applause.auto.pageframework.views.SelectCoffeeBarView;

public class OrderAheadTest extends BaseTest {

	private LogController LOGGER = new LogController(OrderAheadTest.class);

	@Test(enabled = true, groups = { TestConstants.TestNGGroups.ORDER_AHEAD }, description = "625889")
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
		Assert.assertTrue(
				allowLocationServicesPopupChunk.getFormattedMessage().matches(
						"Location Services will: Allow Location Services to help you find nearby Peet(’|')s Coffeebars Only use your location while using th(e|is) app Not share your locations? or information Pinpoint the coffeebars closest to you"),
				"Unexpected text: ");
		LOGGER.info("[Button] Not Now [Button] Allow");
		Assert.assertTrue(allowLocationServicesPopupChunk.isAllowButtonDisplayed(), "Allow button does not displayed");
		Assert.assertTrue(allowLocationServicesPopupChunk.isNotNowButtonDisplayed(),
				"Not Now button does not displayed");

		LOGGER.info("Tap Allow and complete");
		selectCoffeeBarView = allowLocationServicesPopupChunk.allow();

		LOGGER.info(
				"User should see loading dial, nearby stores should appear under nearby stores tab and user should be able to select a store");
		Assert.assertTrue(selectCoffeeBarView.isStoresDisplayed(), "No near stores returned");
	}

	@Test(groups = { TestConstants.TestNGGroups.ORDER_AHEAD }, description = "625890")
	public void browseTheMenu() {
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

		LOGGER.info("Select a store from:\n" + "\n" + "Nearby\n" + "\n" + "Recent\n" + "\n" + "Favorites\n" + "\n"
				+ "OR\n" + "\n" + "by using search function\n");
		selectCoffeeBarView.search("94608");
		NewOrderView newOrderView = selectCoffeeBarView.openCoffeebarFromSearchResults(1);

		LOGGER.info("Tap a category");
		newOrderView.selectCategory("Espresso Beverages");

		LOGGER.info("Sub-categories should expand downward");
		List<String> items = newOrderView.getCategoryItems("Espresso Beverages");
		Assert.assertTrue(items.size() > 0, "Sub categories does not expand");

		LOGGER.info("Select a sub-category");
		newOrderView.selectSubCategory("Espresso Beverages", items.get(0));

		LOGGER.info("Select a product");
		ProductDetailsView productDetail = newOrderView.selectProduct("Iced Espresso");

		LOGGER.info("User should be taken to product details page");
		Assert.assertNotNull(productDetail, "User des not taken to product detail page");

		LOGGER.info("Scroll down PDP and select a modifiers");
		productDetail = productDetail.selectModifiers("Ice", "Light Ice");

		LOGGER.info("Return to main order menu screen");
		newOrderView = productDetail.navigateBack(NewOrderView.class);

		LOGGER.info("Tap on category header again");
		newOrderView.selectCategory("Espresso Beverages");

		LOGGER.info("Category should collapse");
		items = newOrderView.getCategoryItems("Espresso Beverages");
		Assert.assertTrue(items.size() == 0, "Categories does not collapsed");

		LOGGER.info("Tap on search icon");
		LOGGER.info("Search menu field should appear at top of screen\n" + "\n"
				+ "User should see a list of recent products (if applicable) populate below search field\n");
		LOGGER.info("Tap into search field and manually enter a search term (i.e. mocha)");
		SearchResultsView searchResultsView = newOrderView.search("mocha");

		LOGGER.info("Make sure items appear");
		Assert.assertTrue(searchResultsView.getResults().get(0).toLowerCase().contains("mocha"),
				"No relevant search results");

		LOGGER.info("Tap on an item");
		ProductDetailsView productDetailsView = searchResultsView.selectSearchResultByIndex(0);

		LOGGER.info("User should be taken to product details page:");
		Assert.assertNotNull(productDetailsView, "Product detail view does not displayed");

		LOGGER.info("Tap back arrow on PDP");
		searchResultsView = productDetailsView.navigateBack(SearchResultsView.class);

		LOGGER.info("User should be taken back to search menu screen");
		Assert.assertNotNull(searchResultsView, "User does not taken back to search menu screen");
	}

	@Test(groups = { TestConstants.TestNGGroups.ORDER_AHEAD }, description = "625897")
	public void checkoutTest() {
		LOGGER.info("Launch the app and arrive at the first on boarding screen view");
		LandingView landingView = DeviceViewFactory.create(LandingView.class);
		DashboardView dashboardView = peetsMobileHelper.signIn(landingView, TestConstants.MyAccountTestData.EMAIL,
				TestConstants.MyAccountTestData.PASSWORD, DashboardView.class);
		Assert.assertNotNull(dashboardView, "Dashboard View does not displayed");

		LOGGER.info("Tap Order icon on the bottom nav bar");
		SelectCoffeeBarView selectCoffeeBarView = dashboardView.getBottomNavigationMenu()
				.order(SelectCoffeeBarView.class);

		selectCoffeeBarView.search("94608");
		NewOrderView newOrderView = selectCoffeeBarView.openCoffeebarFromSearchResults(1);

		LOGGER.info("Tap a category");
		newOrderView.selectCategory("Espresso Beverages");

		LOGGER.info("Sub-categories should expand downward");
		List<String> items = newOrderView.getCategoryItems("Espresso Beverages");
		Assert.assertTrue(items.size() > 0, "Sub categories does not expand");

		LOGGER.info("Select a sub-category");
		newOrderView.selectSubCategory("Espresso Beverages", items.get(0));

		LOGGER.info("Select a product");
		ProductDetailsView productDetail = newOrderView.selectProduct("Iced Espresso");

		LOGGER.info("User should be taken to product details page");
		Assert.assertNotNull(productDetail, "User des not taken to product detail page");

		LOGGER.info("Scroll down PDP and select a modifiers");
		productDetail = productDetail.selectModifiers("Ice", "Light Ice");

		LOGGER.info("Return to main order menu screen");
		newOrderView = productDetail.navigateBack(NewOrderView.class);

		LOGGER.info("Tap on category header again");
		newOrderView.selectCategory("Espresso Beverages");

	}

}
