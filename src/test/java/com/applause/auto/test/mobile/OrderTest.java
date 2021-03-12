package com.applause.auto.test.mobile;

import static com.applause.auto.test.mobile.helpers.TestHelper.openOrderMenuForRecentCoffeeBar;

import java.lang.invoke.MethodHandles;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.applause.auto.common.data.Constants.MyAccountTestData;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.integrations.annotation.testidentification.ApplauseTestCaseId;
import com.applause.auto.mobile.components.AllowLocationServicesPopupChunk;
import com.applause.auto.mobile.components.CoffeeStoreContainerChuck;
import com.applause.auto.mobile.components.CoffeeStoreItemChuck;
import com.applause.auto.mobile.components.RemoveFromOrderChunk;
import com.applause.auto.mobile.helpers.ItemOptions;
import com.applause.auto.mobile.views.CheckoutView;
import com.applause.auto.mobile.views.DashboardView;
import com.applause.auto.mobile.views.FindACoffeeBarView;
import com.applause.auto.mobile.views.LandingView;
import com.applause.auto.mobile.views.NearbySelectCoffeeBarView;
import com.applause.auto.mobile.views.NewOrderView;
import com.applause.auto.mobile.views.OrderConfirmationView;
import com.applause.auto.mobile.views.OrderView;
import com.applause.auto.mobile.views.ProductDetailsView;
import com.applause.auto.pageobjectmodel.factory.LazyList;
import com.applause.auto.test.mobile.helpers.TestHelper;

public class OrderTest extends BaseTest {

	private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().getClass());

	// updated according to https://appauto.testrail.net/index.php?/cases/view/625889
	// ToDo: Disabled until order section is updated
	@Test(enabled = false, groups = { TestNGGroups.ORDER }, description = "625889")
	@ApplauseTestCaseId({ "674196", "674195" })
	public void locationServicesNotEnabled() {
		logger.info("Launch the app and arrive at the first on boarding screen view");
		LandingView landingView = this.create(LandingView.class);

		// since autoGrantPermissions are set to true in capabilities, we have to deny location in this
		// test method to get Allow location pop up
		TestHelper.denyLocationServices();
		DashboardView dashboardView = testHelper.signIn(landingView, MyAccountTestData.EMAIL,
				MyAccountTestData.PASSWORD, DashboardView.class);
		Assert.assertNotNull(dashboardView, "Dashboard View does not displayed");

		logger.info("Tap Order icon on the bottom nav bar");
		OrderView orderView = dashboardView.getBottomNavigationMenu().order(AllowLocationServicesPopupChunk.class)
				.allow(OrderView.class);

		logger.info("Header: Order");
		Assert.assertEquals(orderView.getHeadingTextValue().toLowerCase().toUpperCase(), "ORDER", "Incorrect header");

		logger.info("[Pin] Locate Coffeebars");
		Assert.assertTrue(orderView.isLocateCoffeeBarsDisplayed(), "Locate Coffeebars pin does not displayed");

		logger.info("Tap Locate Coffeebars");
		AllowLocationServicesPopupChunk allowLocationServicesPopupChunk = orderView
				.locateCoffeebars(AllowLocationServicesPopupChunk.class);

		logger.info("Make sure Peet's branded Location Services alert appears:");
		logger.info("Title: Allow Location Services to help you find nearby Peet's Coffeebars.");
		Assert.assertEquals(allowLocationServicesPopupChunk.getTitle(),
				"Allow Location Services to help you find nearby Peet's Coffeebars",
				"'Allow Location Services to help you find nearby Peet's Coffeebars' title does not found");

		logger.info("Text: Location Services will:\n" + "\n" + "* Only use your location while using the app\n" + "\n"
				+ "* Not share your locations or information\n" + "\n" + "* Pinpoint the coffeebars closest to you");
		Assert.assertTrue(allowLocationServicesPopupChunk.getFormattedMessage().matches(
				"Location Services will: Allow Location Services to help you find nearby Peet(’|')s Coffeebars Only use your location while using th(e|is) app Not share your locations? or information Pinpoint the coffeebars closest to you"),
				"Unexpected text: ");
		logger.info("[Button] Not Now [Button] Allow");
		Assert.assertTrue(allowLocationServicesPopupChunk.isAllowButtonDisplayed(), "Allow button does not displayed");
		Assert.assertTrue(allowLocationServicesPopupChunk.isNotNowButtonDisplayed(),
				"Not Now button does not displayed");

		logger.info("Tap Allow and complete");
		NearbySelectCoffeeBarView nearbySelectCoffeeBarView = allowLocationServicesPopupChunk.allow();
		CoffeeStoreContainerChuck coffeeStoreContainerChuck = nearbySelectCoffeeBarView.getCoffeeStoreContainerChuck();

		logger.info(
				"User should see loading dial, nearby stores should appear under nearby stores tab and user should be able to select a store");
		Assert.assertTrue(coffeeStoreContainerChuck.isStorePresent(), "No near stores returned");

		String currentCoffeeStoreContainerName1 = coffeeStoreContainerChuck.getStoreName();
		Assert.assertNotNull(currentCoffeeStoreContainerName1, "Store name was not fetch");

		// page sources are not being refreshed and
		// after swiping left/right always first displayed element on the screen is only detected
		// coffeeStoreContainerChuck.swipeCoffeStoreContainer(SwipeDirection.LEFT);
		String currentCoffeeStoreContainerName2 = coffeeStoreContainerChuck.getStoreName();
		Assert.assertNotNull(currentCoffeeStoreContainerName2, "Store name was not fetch");

		// page sources are not being refreshed and
		// after swiping left/right always first displayed element on the screen is only detected
		// coffeeStoreContainerChuck.swipeCoffeStoreContainer(SwipeDirection.RIGHT);
		// String currentCoffeeStoreContainerName3 = coffeeStoreContainerChuck.getStoreName();
		// Assert.assertNotNull(currentCoffeeStoreContainerName3, "Store name was not fetch");

		orderView = coffeeStoreContainerChuck.clickOrderButton();
		logger.info("Header: Order");
		Assert.assertEquals(orderView.getHeadingTextValue().toLowerCase().toUpperCase(), "ORDER", "Incorrect header");
	}

	// TODO should be rewritten due to test case/UI changes
	// ToDo: Disabled until order section is updated
	@Test(groups = { TestNGGroups.ORDER }, description = "625890", enabled = false)
	@ApplauseTestCaseId({ "674198", "674197" })
	public void browseTheMenu() {
		logger.info("Launch the app and arrive at the first on boarding screen view");
		LandingView landingView = this.create(LandingView.class);
		DashboardView dashboardView = testHelper.signIn(landingView, MyAccountTestData.EMAIL,
				MyAccountTestData.PASSWORD, DashboardView.class);
		Assert.assertNotNull(dashboardView, "Dashboard View does not displayed");

		logger.info("Tap Order icon on the bottom nav bar");
		OrderView orderView = dashboardView.getBottomNavigationMenu().order(AllowLocationServicesPopupChunk.class)
				.allow(OrderView.class);

		logger.info("Checking if Allow Location Services Popup is displayed");
		AllowLocationServicesPopupChunk allowLocationServicesPopupChunk = this
				.create(AllowLocationServicesPopupChunk.class);
		if (allowLocationServicesPopupChunk.isNotNowButtonDisplayed()) {
			logger.info("Allow Location Services Popup displayed");
			// TODO: allowing location services is not working for iOS, so skipping for now
			allowLocationServicesPopupChunk.notNow();
			allowLocationServicesPopupChunk.clickCancelButton();

			logger.info("Tap Order icon on the bottom nav bar");
			orderView = dashboardView.getBottomNavigationMenu().order(AllowLocationServicesPopupChunk.class)
					.allow(OrderView.class);
		}

		logger.info("Header: Order");
		Assert.assertEquals(orderView.getHeadingTextValue().toUpperCase(), "ORDER", "Incorrect header");

		logger.info("[Pin] Locate Coffeebars");
		Assert.assertTrue(orderView.isLocateCoffeeBarsDisplayed(), "Locate Coffeebars pin does not displayed");

		logger.info("Sub-header: Seasonal Favorites");
		Assert.assertEquals(orderView.getOrderMenuChunck().getSeasonalFavoritesSubHeaderTextValue(),
				"Seasonal Favorites", "Incorrect Favorites sub-header");

		logger.info("Sub-header: Menu");
		Assert.assertEquals(orderView.getOrderMenuChunck().getMenuSubheaderTextValue(), "Menu",
				"Incorrect Menu sub-header");
		//
		// logger.info("Tap Locate Coffeebars");
		// NearbySelectCoffeeBarView nearbySelectCoffeeBarView =
		// orderView.locateCoffeebars(NearbySelectCoffeeBarView.class);
		//
		// logger.info(
		// "Select a store from:\n"
		// + "\n"
		// + "Nearby\n"
		// + "\n"
		// + "Recents\n"
		// + "\n"
		// + "Favorites\n"
		// + "\n"
		// + "OR\n"
		// + "\n"
		// + "by using search function\n");
		// nearbySelectCoffeeBarView.search("94608");
		// NewOrderView newOrderView = nearbySelectCoffeeBarView.openCoffeebarFromSearchResults(1);
		//
		// logger.info("Tap a category");
		// newOrderView.selectCategory("Espresso Beverages");
		//
		// logger.info("Sub-categories should expand downward");
		// List<String> items = newOrderView.getCategoryItems("Espresso Beverages");
		// Assert.assertTrue(items.size() > 0, "Sub categories does not expand");
		//
		// logger.info("Select a sub-category");
		// newOrderView.selectSubCategory("Espresso Beverages", items.get(0));
		//
		// logger.info("Select a product");
		// ProductDetailsView productDetail = newOrderView.selectProduct("Iced Espresso");
		//
		// logger.info("User should be taken to product details page");
		// Assert.assertNotNull(productDetail, "User des not taken to product detail page");
		//
		// logger.info("Scroll down PDP and select a modifiers");
		// productDetail = productDetail.selectModifiers("Ice", "Light Ice");
		//
		// logger.info("Return to main order menu screen");
		// newOrderView = productDetail.navigateBack(NewOrderView.class);
		//
		// logger.info("Tap on category header again");
		// newOrderView.selectCategory("Espresso Beverages");
		//
		// logger.info("Category should collapse");
		// items = newOrderView.getCategoryItems("Espresso Beverages");
		// Assert.assertTrue(items.size() == 0, "Categories does not collapsed");
		//
		// logger.info("Tap on search icon");
		// logger.info(
		// "Search menu field should appear at top of screen\n"
		// + "\n"
		// + "User should see a list of recent products (if applicable) populate below
		// search
		// field\n");
		// logger.info("Tap into search field and manually enter a search term (i.e. mocha)");
		// SearchResultsView searchResultsView = newOrderView.search("mocha");
		//
		// logger.info("Make sure items appear");
		// Assert.assertTrue(
		// searchResultsView.getResults().get(0).toLowerCase().contains("mocha"),
		// "No relevant search results");
		//
		// logger.info("Tap on an item");
		// ProductDetailsView productDetailsView = searchResultsView.selectSearchResultByIndex(0);
		//
		// logger.info("User should be taken to product details page:");
		// Assert.assertNotNull(productDetailsView, "Product detail view does not displayed");
		//
		// logger.info("Tap back arrow on PDP");
		// searchResultsView = productDetailsView.navigateBack(SearchResultsView.class);
		//
		// logger.info("User should be taken back to search menu screen");
		// Assert.assertNotNull(searchResultsView, "User does not taken back to search menu
		// screen");
	}

	// ToDo: Disabled until order section is updated
	@Test(groups = { TestNGGroups.ORDER }, description = "625897", enabled = false)
	@ApplauseTestCaseId({ "674214", "674213" })
	public void checkoutTest() {
		logger.info("Launch the app and arrive at the first on boarding screen view");
		LandingView landingView = this.create(LandingView.class);
		DashboardView dashboardView = testHelper.signIn(landingView, MyAccountTestData.EMAIL_PEETS_REWARDS,
				MyAccountTestData.PASSWORD, DashboardView.class);

		Assert.assertNotNull(dashboardView, "Dashboard View does not displayed");

		NewOrderView newOrderView = openOrderMenuForRecentCoffeeBar(dashboardView);

		logger.info("Tap a category and subcategory");
		newOrderView.selectCategoryAndSubCategory("Espresso Beverages", "Espresso");

		logger.info("Select a product");
		ProductDetailsView productDetail = newOrderView.selectProduct("Iced Espresso");

		logger.info("User should be taken to product details page");
		Assert.assertNotNull(productDetail, "User des not taken to product detail page");

		logger.info("Scroll down PDP and select a modifiers");
		productDetail = productDetail.selectModifiers("Ice", "Light Ice");

		logger.info("Add to Order");
		newOrderView = productDetail.addToOrder(NewOrderView.class);

		logger.info("Proceed to Checkout");
		CheckoutView checkout = newOrderView.checkout();

		logger.info("Navigate to Available Rewards");
		checkout = checkout.clickOnAwardItem("Free Beverage");

		logger.info("Verify - Order Summary is '$0.00'");
		Assert.assertEquals(checkout.getOrderTotal(), "$0.00", "Order total was not changed by reward");

		logger.info("Place Order");
		OrderConfirmationView orderConfirmationView = checkout.placeOrder(OrderConfirmationView.class);

		logger.info("Verify - Order Confirmation displayed");
		Assert.assertNotNull(orderConfirmationView, "Something happened during order placement");
	}

	// ToDo: Disabled until order section is updated
	@Test(groups = { TestNGGroups.ORDER }, description = "2879930", enabled = false)
	@ApplauseTestCaseId({ "674356", "674355" })
	public void orderAhead() {
		logger.info("Launch the app and arrive at the first on boarding screen view");
		LandingView landingView = this.create(LandingView.class);
		DashboardView dashboardView = testHelper.signIn(landingView, MyAccountTestData.EMAIL,
				MyAccountTestData.PASSWORD, DashboardView.class);

		Assert.assertNotNull(dashboardView, "Dashboard View does not displayed");

		NewOrderView newOrderView = openOrderMenuForRecentCoffeeBar(dashboardView);

		logger.info("Tap a category and subcategory");
		newOrderView.selectCategoryAndSubCategory("Beans & Espresso Capsules", "Espresso");

		logger.info("Select a product");
		ProductDetailsView productDetail = newOrderView.selectProduct("Iced Espresso");

		logger.info("User should be taken to product details page");
		Assert.assertNotNull(productDetail, "User des not taken to product detail page");

		logger.info("Scroll down PDP and select a modifiers");
		productDetail = productDetail.selectModifiers("Ice", "Light Ice");

		logger.info("Add to Order");
		newOrderView = productDetail.addToOrder(NewOrderView.class);

		logger.info("Proceed to Checkout");
		CheckoutView checkout = newOrderView.checkout();

		logger.info("Navigate to You might also like section. Tap on a product.");
		productDetail = checkout.clickYouMightAlsoLikeItem();
		Assert.assertNotNull(productDetail, "User does not taken to product detail page");

		logger.info("Add to Order");
		newOrderView = productDetail.addToOrder(NewOrderView.class);

		logger.info("Proceed to Checkout");
		checkout = TestHelper.checkoutOnItemsYouMightLike(newOrderView);

		logger.info("Checking total order items count");
		Assert.assertEquals(checkout.getOrderedItemsCount(), 2, "Ordered items count is incorrect");

		logger.info("Place Order");
		OrderConfirmationView orderConfirmationView = checkout.placeOrder(OrderConfirmationView.class);

		logger.info("Verify - Order Confirmation displayed");
		Assert.assertNotNull(orderConfirmationView, "Something happened during order placement");
	}

	@Test(groups = { TestNGGroups.ORDER, TestNGGroups.DAILY,
			TestNGGroups.REGRESSION }, description = "1687255", enabled = true)
	@ApplauseTestCaseId({ "674356", "674355" })
	public void recentsFavoriteOrdersEmptyStateTest() {
		logger.info("Precondition: User is already signed in to app\n"
				+ "User is on main order screen and menu tab is highlighted\n" + "User has no recent orders\n"
				+ "User has no favorite orders");

		logger.info("Launch the app and arrive at the first on boarding screen view");
		LandingView landingView = this.create(LandingView.class);
		DashboardView dashboardView = testHelper.createNewAccountWithDefaults(landingView);

		NewOrderView order;
		if (getEnvironmentHelper().isMobileIOS()) {
			logger.info("Tap on store locator icon in top right corner");
			NearbySelectCoffeeBarView nearbySelectCoffeeBarView = dashboardView
					.location(AllowLocationServicesPopupChunk.class).allowIfRequestDisplayed();

			logger.info("STEP - Search for any store either by nearby, recent tabs, or by zip code");
			nearbySelectCoffeeBarView.search("78717");

			order = dashboardView.getBottomNavigationMenu().order(NewOrderView.class);
		} else {
			order = dashboardView.getBottomNavigationMenu().order(AllowLocationServicesPopupChunk.class)
					.allowIfRequestDisplayed(NearbySelectCoffeeBarView.class).close(DashboardView.class)
					.getBottomNavigationMenu().order(NewOrderView.class);
		}

		logger.info("STEP 1. Tap on Recents tab");
		order = order.recents();

		logger.info("EXPECTED 1. Recents tab is highlighted in gold");
		logger.info("No recent orders card is displayed:\n" + "* Clock icon\n" + "* Title: No Recent Orders\n"
				+ "* Text: Your recent orders will appear here to quickly order again.\n"
				+ "* [Button] Start New Order");
		// TODO Gold color verification
		// TODO Clock icon verification
		Assert.assertTrue(order.isTitleNoRecentOrdersDisplayed(), "Title <No recent orders> does not displayed");
		Assert.assertTrue(order.isMessageDisplayed("Your recent orders will appear here to quickly order again."),
				"Expected message <Your recent orders will appear here to quickly order again.> does not displayed");
		Assert.assertTrue(order.isStartNewOrderRecentsButtonDisplayed(), "Button <Start New Order> does not found>");

		logger.info("STEP 2. Tap Start New Order button");
		order = order.startNewOrderRecents();

		logger.info("EXPECTED 2. User returns to menu tab on order screen and can view the menu categories");
		Assert.assertTrue(order.isMenuCategoriesDisplayed(), "User does not returned to menu");

		logger.info("STEP 3. Tap on Favorites tab");
		order = order.favorites();

		logger.info("EXPECTED 3. Favorites tab is highlighted in gold\n" + "No favorited orders card is displayed:\n"
				+ "* Heart icon* Title: No Favorited Orders\n"
				+ "* Text: Favorite an order to save customizations and make your next coffee run even faster!\n"
				+ "* [Button] Start New Order");

		// TODO Gold color verification
		// TODO Heart icon verification

		Assert.assertTrue(
				order.isMessageDisplayed(
						"Favorite an order to save customizations and make your next coffee run even faster!"),
				"Expected message <Favorite an order to save customizations and make your next coffee run even faster!> does not displayed");
		Assert.assertTrue(order.isTitleNoFavoriteOrdersDisplayed(), "Title <No favorite orders> does not displayed");
		Assert.assertTrue(order.isStartNewOrderFavoritesButtonDisplayed(), "Button <Start New Order> does not found>");

		logger.info("STEP 4. Tap Start New Order button");
		order = order.startNewOrderFavorites();

		logger.info("EXPECTED 4. User returns to menu tab on order screen and can view the menu categories");
		Assert.assertTrue(order.isMenuCategoriesDisplayed(), "User does not returned to menu");
	}

	@Test(groups = { TestNGGroups.ORDER, TestNGGroups.DAILY,
			TestNGGroups.REGRESSION }, description = "625930", enabled = true)
	public void recentsFavoriteCoffeebarsEmptyStateTest() {
		logger.info("Precondition: User is already signed in to app\n"
				+ "User is on main order screen and menu tab is highlighted\n" + "User has no recent orders\n"
				+ "User has no favorite orders\n" + "Launch the app and arrive at the first on boarding screen view");
		LandingView landingView = this.create(LandingView.class);
		DashboardView dashboardView = testHelper.createNewAccountWithDefaults(landingView);

		// OrderView orderView =
		// dashboardView
		// .location(AllowLocationServicesPopupChunk.class)
		// .allowIfRequestDisplayed(OrderView.class);

		logger.info("STEP 1. Tap on store locator icon in top right corner");
		NearbySelectCoffeeBarView nearbySelectCoffeeBarView = dashboardView
				.location(AllowLocationServicesPopupChunk.class).allowIfRequestDisplayed();

		logger.info("User is taken to find a coffeebar screen:\n" + "" + "* Header: FIND A COFFEEBAR"
				+ "* X at top left corner to close screen" + "* Search field      " + "o Text: Enter Zip or City, State"
				+ "* Search icon on the right of the text" + "* Current location icon on the map"
				+ "* Three tabs: Nearby (default selected in gold), Recents, Favorites"
				+ "* Map indicating nearest coffeebar with gold pin"
				+ "Coffeebar location details (Scroll horizontally to see all nearby stores)\n" + ""
				+ "* Coffeebar name* xx.x miles away on the right side of the store name"
				+ "* Store Address* Open until x:xx PM" + "* [Button] Order");
		Assert.assertEquals(nearbySelectCoffeeBarView.getTitle(), "FIND A COFFEEBAR",
				"<FIND A COFFEEBAR> title does not displayed");
		Assert.assertTrue(nearbySelectCoffeeBarView.isCloseButtonDisplayed(), "Close button does not displayed");
		Assert.assertTrue(nearbySelectCoffeeBarView.isSearchFieldDisplayed(), "Search field does not displayed");
		Assert.assertTrue(nearbySelectCoffeeBarView.isSearchIconDisplayed(), "Search icon does not displayed");

		Assert.assertTrue(nearbySelectCoffeeBarView.isNearbyTabDisplayed(), "Nearby Tab does not displayed");
		Assert.assertTrue(nearbySelectCoffeeBarView.isRecentsTabDisplayed(), "Recents Tab does not displayed");
		Assert.assertTrue(nearbySelectCoffeeBarView.isFavoritesTabDisplayed(), "Favorites does not displayed");

		// if (getEnvironmentHelper().isMobileIOS()) {
		logger.info("STEP - Search for any store either by nearby, recent tabs, or by zip code");
		nearbySelectCoffeeBarView.search("78717").cancelSearch();
		// }

		CoffeeStoreItemChuck store = nearbySelectCoffeeBarView.getCoffeeStoreContainerChucks().get(0);

		// TODO Order button related to working hours
		// store.isCoffeebarOrderButtonDisplayed();
		String storeName = store.getStoreName();

		Assert.assertFalse(storeName.isEmpty(), "Store name is empty");

		if (!getEnvironmentHelper().isMobileIOS()) {
			// Todo: ask Peets, only works for android flow
			Assert.assertTrue(store.isCoffeebarDistanceDisplayed(), "Distance  does not displayed");
		}
		Assert.assertTrue(store.isCoffeebarOpenHoursDisplayed(), "Open hours does not displayed");

		logger.info("STEP 2. Tap on current location icon on the map");
		nearbySelectCoffeeBarView.location(AllowLocationServicesPopupChunk.class).allowIfRequestDisplayed();
		logger.info("User sees blue dot on map and nearby store locations are indicated on the map as brown pins");
		Assert.assertTrue(nearbySelectCoffeeBarView.getPinsCount() > 0, "User does not see PINS on the map");

		logger.info("STEP 3. Swipe left to view more nearby store location cards");
		int x = store.getXposition();
		LazyList<CoffeeStoreItemChuck> stores = nearbySelectCoffeeBarView.getCoffeeStoreContainerChucks();
		if (stores.size() > 1) {
			store = stores.get(0).swipeLeft();
			int x1 = store.getXposition();
			stores = nearbySelectCoffeeBarView.getCoffeeStoreContainerChucks();
			CoffeeStoreItemChuck store2 = stores.get(1);
			int x2 = store2.getXposition();
			logger.info("User sees the store location update on the map as user swipes through the nearby stores");
			Assert.assertNotEquals(x, x1, "Store does not changed, initial store remains on the screen");
			Assert.assertEquals(x2, x,
					"Store does not changed, second store does not displayed on same position as was for first");
		} else {
			logger.warn("Only one store found, cannot validate swiping");
		}

		logger.info("User can see displayed coffeebar location on the map indicated by the gold pin on the map");
		// TODO Unable to validate change pin colour

		logger.info("STEP 4. Tap on a brown pin on the map");
		logger.info("Brown pin on map turns to a gold pin and store location card updates to the selected store");

		// TODO Blocked because order button visibility relay on working hours
		// logger.info("STEP 5. Tap Order button");
		// OrderView order = store2.clickOrderButton();
		// logger.info("User is taken to main order screen");
		// Assert.assertNotNull(order, "User does not taken to main order screen");
		//
		// logger.info("STEP 6. Tap back arrow");
		// dashboardView = order.back(DashboardView.class);
		// logger.info("User is taken to home screen");
		// Assert.assertNotNull(nearbySelectCoffeeBarView, "User does not taken to home ");
		//
		// logger.info("STEP 7. Tap on store locator icon again");
		// nearbySelectCoffeeBarView = dashboardView.location(NearbySelectCoffeeBarView.class);
		// logger.info("User is taken to find a coffeebar screen");
		// Assert.assertNotNull(nearbySelectCoffeeBarView, "User does not taken to find coffeebar
		// screen");

		logger.info("STEP 8. Tap on Recents tab");
		FindACoffeeBarView findACoffeeBarView = nearbySelectCoffeeBarView.openRecentTab();
		logger.info("Recents tab is highlighted in gold and the empty state shows:\n" + "" + "* Clock symbol"
				+ "* Title: No recent coffeebars."
				+ "* Text: Once you place your first order, you'll find the coffeebar here.");
		Assert.assertTrue(findACoffeeBarView.isTitleNoRecentCoffeebarsDisplayed(),
				"<Title: No recent coffeebars> does not displayed");
		Assert.assertTrue(
				findACoffeeBarView
						.isMessageDisplayed("Once you place your first order, you'll find the coffeebar here."),
				"Message <Once you place your first order, you'll find the coffeebar here.> does not displayed on the recent tab");

		logger.info("STEP 8. Tap on Favorites tab");
		findACoffeeBarView = findACoffeeBarView.openFavoritesTab();
		logger.info("Favorites tab is highlighted in gold and the empty state shows:\n" + "" + "* Heart symbol"
				+ "* Title: No favorited coffeebars."
				+ "* Text: Find a coffeebar and click on the heart to save it for later.");
		Assert.assertTrue(findACoffeeBarView.isTitleNoFavoriteCoffeebarsDisplayed(),
				"<Title: No favourite coffeebars> does not displayed");
		Assert.assertTrue(
				findACoffeeBarView.isMessageDisplayed("Find a coffeebar and click on the heart to save it for later."),
				"Message <Find a coffeebar and click on the heart to save it for later.> does not displayed on the favorites tab");
	}

	@Test(groups = { TestNGGroups.ORDER, TestNGGroups.DAILY,
			TestNGGroups.REGRESSION }, description = "625891", enabled = true)
	public void customizeOrderBeveragesTest() {
		logger.info("Precondition: User is already signed in to app\n"
				+ "User is on main order screen and pickup order mode is default selected\n"
				+ "User has no items in basket");
		LandingView landingView = this.create(LandingView.class);
		DashboardView dashboardView = testHelper.createNewAccountWithDefaults(landingView);

		NewOrderView orderView = dashboardView.getBottomNavigationMenu().order(AllowLocationServicesPopupChunk.class)
				.allowIfRequestDisplayed(NearbySelectCoffeeBarView.class).search("78717").openDefault();

		logger.info("Step 1. Tap on Hot Coffee category to expand");
		logger.info("Expected 1. Sub-categories should expand downward");
		logger.info("Step 2. Select sub-category Lattes");
		logger.info("Expected 2. Lattes menu should open up");
		orderView.selectCategoryAndSubCategory("Hot Coffee", "Lattes");

		//////////////
		//// Maple - Large - Vanilla Syrup - 2% Milk - Short Pull - Sugar 5 - Qty 2
		//////////////

		logger.info("Step 3. Select a beverage");
		ProductDetailsView productDetailsView = orderView.selectProduct("Maple Latte");

		logger.info("Expected 3. User is taken to PDP");
		Assert.assertNotNull(productDetailsView, "PDP does not displayed");

		logger.info(
				"Step 4. Scroll down PDP and customize beverage by selecting modifiers (where applicable based on item selected):\n"
						+ "" + "* Size" + "* Milk Prep / Add Milk" + "* Shot Options" + "* Ice" + "* Syrups & Sauces"
						+ "* Add Sweeteners" + "* Add Toppings" + "* Quantity");
		// String defaultSize = productDetailsView.getSize();
		// String cost = productDetailsView.getCost();
		productDetailsView.selectSize("Large");
		productDetailsView.selectSyrups().selectSyrup("Vanilla Syrup")
				// .selectOption("Extra")
				.saveChanges(ProductDetailsView.class);

		productDetailsView.selectMilkPrep().chooseMilk("2% Milk").saveChanges(ProductDetailsView.class);

		productDetailsView.selectShotOptions().selectShotPrep("Short Pull").saveChanges(ProductDetailsView.class);

		productDetailsView.selectSweeteners().setRawSugarAmount("5").saveChanges(ProductDetailsView.class);

		productDetailsView.selectToppings().setWhippedCream().saveChanges(ProductDetailsView.class);

		productDetailsView.selectQuantity("2");

		logger.info("Expected 4. f applicable:\n" + ""
				+ "* Make sure for multiple-sized items medium size is default selected"
				+ "* Make sure tapping different sizes changes the price and calorie information for the product"
				+ "* User should be able to select and save different modifiers and it should be reflected on the PDP under the modifier selection"
				+ "* The price on PDP should reflect any additional costs from customizing the beverage");

		logger.info("Step 5. Tap Add to Order button");
		orderView = orderView.addToOrders();

		logger.info(
				"Expected 5. User is returned to main order screen and item added to order appears in the FAB (floating action button) with the correct quantity displayed in the cup icon");
		String fabAmount = orderView.getFabAmount();

		//////////////
		//// Snowcap Iced Mint Matcha Latte - Small - Chocolate Sauce: Extra - Whole Milk - Long Pull -
		// Sugar 3 -
		// Qty 3
		//////////////

		logger.info("Step 6. Swipe through Seasonal Favorites category and select a beverage");
		orderView.selectSeasonalFavorites("Snowcap Iced Mint Matcha Latte");
		logger.info("Expected 6. User is taken to PDP");
		logger.info(
				"Step 7. Scroll down PDP and customize beverage by selecting modifiers (where applicable based on item selected):\n"
						+ "" + "* Size" + "* Milk Prep / Add Milk" + "* Shot Options" + "* Ice* Syrups & Sauces"
						+ "* Add Sweeteners" + "* Add Toppings" + "* Quantity ");
		// defaultSize = productDetailsView.getSize();
		// cost = productDetailsView.getCost();
		productDetailsView.selectSize("Small");
		productDetailsView.selectSyrups().selectSyrup("Chocolate Sauce").selectOption("Extra")
				.saveChanges(ProductDetailsView.class);

		productDetailsView.selectMilkPrep().chooseMilk("Whole Milk").saveChanges(ProductDetailsView.class);

		productDetailsView.selectShotOptions().selectShotPrep("Long Pull").saveChanges(ProductDetailsView.class);

		productDetailsView.selectSweeteners().setRawSugarAmount("3").saveChanges(ProductDetailsView.class);

		// Disabled because not available
		// productDetailsView.selectToppings().setWhippedCream().saveChanges(ProductDetailsView.class);

		productDetailsView.selectQuantity("2");

		logger.info(
				"Expected 7. User should be able to select and save different modifiers and it should be reflected on the PDP under the modifier selection");

		logger.info("Step 8. Tap Add to Order button");
		orderView = orderView.addToOrders();

		logger.info(
				"Expected 8. User is returned to main order screen and the FAB updates with the correct quantity of item(s)");
		fabAmount = orderView.getFabAmount();

		logger.info("Step 9. Tap on Signature Beverages category");
		logger.info("Step 10. Select sub-cateogry Cold Brew Black Tie");
		orderView.selectCategoryAndSubCategory("Signature Beverages", "Cold Brew Black Tie");

		logger.info("Expected 9. Sub-categories should expand downward");

		//////////////
		//// The Black Tie - Medium - Coconut Syrup: No - Nonfat Milk - Long Pull -
		// Sugar 4 -
		// Qty 4
		//////////////

		logger.info("Step 11. Select a beverage");
		productDetailsView = orderView.selectProduct("The Black Tie");

		logger.info("Expected 11. User is taken to PDP");
		logger.info(
				"Step 12. Scroll down PDP and customize beverage by selecting modifiers (where applicable based on item selected):\n"
						+ "" + "* Size" + "* Room for Milk" + "* 1/2 Decaf" + "* Shot Options* Syrups & Sauces"
						+ "* Add Milk" + "* Add Sweeteners" + "* Add Toppings" + "* Cup" + "* Quantity");
		// defaultSize = productDetailsView.getSize();
		// cost = productDetailsView.getCost();
		productDetailsView.selectSize("Medium");
		productDetailsView.selectSyrups().selectSyrup("Coconut Syrup").selectOption("No")
				.saveChanges(ProductDetailsView.class);

		productDetailsView.selectMilkPrep().chooseMilk("Nonfat Milk").saveChanges(ProductDetailsView.class);

		productDetailsView.selectShotOptions().selectShotPrep("Short Pull").saveChanges(ProductDetailsView.class);

		productDetailsView.selectSweeteners().setRawSugarAmount("4").saveChanges(ProductDetailsView.class);

		productDetailsView.selectToppings().setWhippedCream().saveChanges(ProductDetailsView.class);

		productDetailsView.selectQuantity("2");

		logger.info(
				"Expected 12. User should be able to select and save different modifiers and it should be reflected on the PDP under the modifier selection");

		logger.info("Step 13. Tap Add to Order button");
		orderView = orderView.addToOrders();

		logger.info(
				"Expected 13. User is returned to main order screen and the FAB updates with the correct quantity of item(s)");
		fabAmount = orderView.getFabAmount();

		logger.info("Step 14. Tap on the FAB");
		CheckoutView checkoutView = orderView.checkoutAtom(CheckoutView.class);

		// disabled because we forcing change store on previous step
		// logger.info(
		// "Expected 14. User sees confirm coffeebar location UI alert:\n"
		// + ""
		// + "* Location pin icon"
		// + "* Title: Confirm Coffeebar"
		// + "* Text: [Coffeebar name]"
		// + "* [Button] Change [Button] Confirm");
		// Assert.assertTrue(
		// orderView.isChangeStoreButtonDisplayed(),
		// "User does not sees confirm coffeebar location UI alert change button");
		// Assert.assertTrue(
		// orderView.isConfirmStoreButtonDisplayed(),
		// "User does not sees confirm coffeebar location UI alert confirm button");
		//
		// logger.info("Step 15. Tap Confirm button");
		// CheckoutView checkoutView = orderView.confirmStore();

		logger.info("Expected 15. User is taken to checkout screen");
		Assert.assertNotNull(checkoutView, "User does not taken to checkout screen");

		logger.info("Step 15. Review beverage order details on checkout screen");
		ItemOptions maple = checkoutView.getItemOptions("Maple Latte");
		checkoutView = checkoutView.refreshView();
		ItemOptions snowcap = checkoutView.getItemOptions("Snowcap Iced Mint Matcha Latte");
		checkoutView = checkoutView.refreshView();
		ItemOptions blackTie = checkoutView.getItemOptions("The Black Tie");

		logger.info("Expected 15. Make sure beverage customizations flow through correctly to checkout screen");
		//////////////
		//// Maple - Large - Vanilla Syrup - 2% Milk - Short Pull - Sugar 5 - Qty 2
		//// Snowcap Iced Mint Matcha Latte - Small - Chocolate Sauce: Extra - Whole Milk - Long Pull -
		// Sugar 3 -
		// Qty 3
		//// The Black Tie - Medium - Coconut Syrup: No - Nonfat Milk - Long Pull -
		// Sugar 4 -
		// Qty 4
		//////////////
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertTrue(maple.contains("Large"), "Mapple Drink have wrong cup size: Large expected");
		softAssert.assertTrue(maple.contains("Maple Syrup: Regular"),
				"Mapple Drink have wrong base syrup: Maple Syrup: Regular");
		softAssert.assertTrue(maple.contains("Vanilla Syrup"), "Mapple Drink have wrong syrup addon: Vanilla Syrup");
		softAssert.assertTrue(maple.contains("Choose Milk: 2% Milk"),
				"Mapple Drink have wrong milk: Choose Milk: 2% Milk");
		softAssert.assertTrue(maple.contains("Shot Prep: Short Pull"),
				"Mapple Drink have wrong shop prep: Shot Prep: Short Pull");
		softAssert.assertTrue(maple.contains("Milk Temp: Regular"),
				"Mapple Drink have wrong milk temp: Milk Temp: Regular");
		softAssert.assertTrue(maple.contains("Foam: Regular Foam"), "Mapple Drink have wrong foam: Foam: Regular Foam");
		softAssert.assertTrue(maple.contains("Raw Sugar (x5)"), "Mapple Drink have wrong raw sugar: Raw Sugar (x5)");
		softAssert.assertTrue(maple.contains("Whipped Cream"), "Mapple Drink have wrong cream: Whipped Cream");
		softAssert.assertTrue(maple.contains("Qty: 2"), "Mapple Drink have wrong Qty: Qty: 2");

		softAssert.assertTrue(snowcap.contains("Small"), "Snowcap Iced Mint Drink have wrong cup size: Small expected");
		softAssert.assertTrue(snowcap.contains("Peppermint Syrup: Regular"),
				"Snowcap Iced Mint have wrong base syrup: Peppermint Syrup: Regular");
		softAssert.assertTrue(snowcap.contains("Chocolate Sauce: Extra"),
				"Snowcap Iced Mint have wrong syrup addon: Chocolate Sauce: Extra");
		softAssert.assertTrue(snowcap.contains("Choose Milk: Whole Milk"),
				"Snowcap Iced Mint have wrong milk: Choose Milk: Whole Milk");
		softAssert.assertTrue(snowcap.contains("Shot Prep: Long Pull"),
				"Snowcap Iced Mint have wrong shop prep: Shot Prep: Long Pull");
		// softAssert.assertTrue(
		// snowcap.contains("Milk Temp: Regular"),
		// "Snowcap Iced Mint Matcha Latte have wrong milk temp: Milk Temp: Regular");
		softAssert.assertTrue(snowcap.contains("Foam: Regular Foam"),
				"Snowcap Iced Mint have wrong foam: Foam: Regular Foam");
		softAssert.assertTrue(snowcap.contains("Raw Sugar (x3)"),
				"Snowcap Iced Mint have wrong raw sugar: Raw Sugar (x3)");
		softAssert.assertTrue(snowcap.contains("Whipped Cream"), "Snowcap Iced Mint have wrong cream: Whipped Cream");
		softAssert.assertTrue(snowcap.contains("Qty: 2"), "Snowcap Iced Mint have wrong Qty: 2");

		softAssert.assertTrue(blackTie.contains("Medium"), "The Black Tie Drink have wrong cup size: Medium expected");
		softAssert.assertTrue(blackTie.contains("Chicory Syrup: Regular"),
				"The Black Tie have wrong base syrup: Chicory Syrup: Regular");
		softAssert.assertTrue(blackTie.contains("Coconut Syrup: No"),
				"The Black Tie have wrong syrup addon: Coconut Syrup: No");
		softAssert.assertTrue(blackTie.contains("Choose Milk: Nonfat Milk"),
				"The Black Tie have wrong milk: Nonfat Milk");
		softAssert.assertTrue(blackTie.contains("Shot Prep: Short Pull"),
				"The Black Tie have wrong shop prep: Shot Prep: Short Pull");
		// softAssert.assertTrue(
		// blackTie.contains("Milk Temp: Regular"),
		// "The Black Tie have wrong milk temp: Milk Temp: Regular");
		softAssert.assertTrue(blackTie.contains("Regular Ice"), "The Black Tie have wrong foam: Ice: Regular Ice");
		softAssert.assertTrue(blackTie.contains("Raw Sugar (x4)"),
				"The Black Tie have wrong raw sugar: Raw Sugar (x4)");
		softAssert.assertTrue(blackTie.contains("Whipped Cream"), "The Black Tie have wrong cream: Whipped Cream");
		softAssert.assertTrue(blackTie.contains("Qty: 2"), "The Black Tie have wrong Qty: 2");
		softAssert.assertAll();
		logger.info("Step 16. Tap X at top left corner of Checkout screen to return to main order screen");
		orderView = checkoutView.close();

		logger.info("Expected 16. User is taken back to main order screen");
		Assert.assertNotNull(orderView, "User does not taken back to main order screen");
	}

	@Test(groups = { TestNGGroups.ORDER, TestNGGroups.DAILY,
			TestNGGroups.REGRESSION }, description = "625892", enabled = true)
	public void customizeOrderFoodTest() {
		logger.info("User is already signed in to app\n"
				+ "User is on main order screen and pickup order mode is default selected\n"
				+ "User continues this test case from previous test case (so user will have items added to order already)");
		customizeOrderBeveragesTest();
		NewOrderView orderView = this.create(NewOrderView.class);

		/////
		logger.info("STEP 1. Tap on Food category to expand");
		logger.info("STEP 2. Select sub-category Baked Goods");
		orderView.selectCategoryAndSubCategory("Food", "Baked Goods");

		logger.info("EXPECTED 1. Sub-categories should expand downward");
		logger.info(
				"EXPECTED 2. Make sure FAB is displayed on sub-category screen and shows correct quantity of item(s)");
		logger.info("EXPECTED 2. User is taken to Baked Goods Menu");

		logger.info("STEP 3. Select any baked goods item");
		ProductDetailsView productDetailsView = orderView.selectProduct("Plain Bagel");

		logger.info("EXPECTED 3. User is taken to PDP");
		Assert.assertNotNull(productDetailsView, " User does not taken to PDP");

		logger.info("STEP 4. Scroll down PDP and customize item by selecting modifiers:\n" + "* Warm" + "* Quantity");
		productDetailsView = productDetailsView.warming().warm().saveChanges(ProductDetailsView.class)
				.selectQuantity("2");
		logger.info(
				"EXPECTED 4. User should be able to select warm modifier and it should be reflected on the PDP under the modifier selection");

		logger.info("STEP 5. Tap Add to Order button");
		orderView = productDetailsView.addToOrder(NewOrderView.class);

		logger.info(
				"EXPECTED 5. User is returned to main order screen and the FAB updates with the correct quantity of item(s)");
		Assert.assertNotNull(orderView, "User does not returned to main order screen");
		logger.info("EXPECTED 5. User should also feel haptics feedback on device as item is added to basket");

		logger.info("STEP 6. Select sub-category Warm Breakfast");
		orderView.selectSubCategoryUnderCategory("Food", "Warm Breakfast");

		logger.info(
				"EXPECTED 6. Make sure FAB is displayed on sub-category screen and shows correct quantity of item(s)");
		logger.info("EXPECTED 6. User is taken to Warm Breakfast menu");

		logger.info("STEP 7. Select any warm breakfast item");
		productDetailsView = orderView.selectProduct("Oatmeal");

		logger.info("EXPECTED 7. User is taken to PDP");
		Assert.assertNotNull(productDetailsView, "User does not taken to PDP");

		logger.info(
				"STEP 8. Scroll down PDP and customize item by selecting modifiers (where applicable based on item selected):\n"
						+ "* Oatmeal toppings" + "* Quantity");
		productDetailsView = productDetailsView.selectOatmealToppings().decreaseCount("Brown Sugar", "0")
				.incereaseCount("Almonds", "2").saveChanges(ProductDetailsView.class).selectQuantity("2");

		logger.info(
				"EXPECTED 8. User should be able to select modifier and it should be reflected on the PDP under the modifier selection");

		logger.info("STEP 9. Tap Add to Order button");
		orderView = productDetailsView.addToOrder(NewOrderView.class);
		logger.info(
				"EXPECTED 9. User is returned to main order screen and the FAB updates with the correct quantity of item(s)");
		Assert.assertNotNull(orderView, "User does not returned to main order screen");
		logger.info("EXPECTED 9. User should also feel haptics feedback on device as item is added to basket");

		logger.info("STEP 10. Tap on the FAB");
		CheckoutView checkoutView = orderView.checkoutAtom(CheckoutView.class);

		logger.info("EXPECTED 10. User is taken to checkout screen");
		Assert.assertNotNull(checkoutView, "User does not returned to checkout screen");

		logger.info("STEP 11. Review food order details on checkout screen");
		ItemOptions plainBagel = checkoutView.getItemOptions("Plain Bagel");
		checkoutView = checkoutView.refreshView();
		ItemOptions oatmeal = checkoutView.getItemOptions("Oatmeal");
		logger.info("EXPECTED 11. Make sure food customizations flow through correctly to checkout screen");
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertTrue(plainBagel.contains("Qty: 2"), "Plain Bagel: Wrong quantity: Expected Qty:2");
		softAssert.assertTrue(plainBagel.contains("Warm"), "Plain Bagel: Wrong warm option: Expected Warm");

		softAssert.assertTrue(oatmeal.contains("Qty: 2"), "Oatmeal: Wrong quantity: Expected Qty: 2");
		softAssert.assertTrue(oatmeal.contains("Almonds (x2)"), "Oatmeal: Wrong topping option: Expected Almonds (x2)");
		softAssert.assertTrue(oatmeal.contains("Wild Blueberries"),
				"Oatmeal: Wrong topping option: Expected Wild Blueberries");

		softAssert.assertAll();
	}

	@Test(groups = { TestNGGroups.ORDER, TestNGGroups.DAILY,
			TestNGGroups.REGRESSION }, description = "625893", enabled = true)
	public void editOrderTest() {
		logger.info("User is already signed in to app\n" + "User is on the checkout screen\n"
				+ "User continues this test case from previous test case (so user will have items added to order already)");
		customizeOrderFoodTest();
		CheckoutView checkoutView = this.create(CheckoutView.class);

		String productName = "Snowcap Iced Mint Matcha Latte";

		logger.info("STEP 1. Tap on an item under Your Order section");
		ProductDetailsView productDetailsView = checkoutView.selectProduct(productName);
		logger.info("EXPECTED 1. User is directed to PDP for that item:\n"
				+ "EXPECTED 1a. * Back arrow to close/exit the screen at top left corner\n"
				+ "EXPECTED 1b. * Garbage can icon at top right corner to delete the item\n"
				+ "EXPECTED 1c. * Customizations selected\n" + "EXPECTED 1d *[Button] Update Order");
		Assert.assertNotNull(productDetailsView, "User does not directed to PDP");
		Assert.assertTrue(productDetailsView.isBackButtonDisplayed(),
				"Back arrow to close/exit the screen at top left corner does not displayed");
		Assert.assertTrue(productDetailsView.isGarbageIconDisplayed(),
				"Garbage can icon at top right corner does not displayed");
		// Assert.assertEquals(
		// productDetailsView.getModifies("Milk Prep"), "Choose Milk: Whole Milk
		// Foam: Regular Foam", "Customizations does not
		// displayed");
		Assert.assertTrue(productDetailsView.isUpdateOrderButtonDisplayed(), "Update Order does not displayed");

		logger.info("STEP 2. Edit/update order by selecting different modifiers and/or adding new modifiers");
		String oldPrice = productDetailsView.getCost();
		productDetailsView = productDetailsView.selectMilkPrep().chooseMilk("Soy Milk")
				.saveChanges(ProductDetailsView.class);
		String newPrice = productDetailsView.getCost();

		logger.info(
				"EXPECTED 2. The price on PDP should reflect any additional or deducted costs (depending on the changes made to the order)");
		Assert.assertNotEquals(oldPrice, newPrice,
				"The price on PDP does not reflect any additional or deducted costs");

		logger.info("STEP 3. Tap Update Order button");
		checkoutView = productDetailsView.updateOrder();

		logger.info("EXPECTED 3. User is directed back to checkout screen with updated item reflected correctly");
		Assert.assertNotNull(checkoutView, "User does not directed back to checkout screen");
		Assert.assertEquals(checkoutView.costOf(productName), newPrice, "Updated item does not reflected correctly");

		if (getEnvironmentHelper().isMobileIOS()) {
			logger.info("STEP 4. To delete item(s) from your order:\n" + "THIS FLOW APPLICABLE ONLY ON IOS:\n"
					+ "* Swipe left on the item in the basket\n" + "* Tap red delete button");

			logger.info("EXPECTED 4. \n" + "* User sees red delete button to the right of the item\n"
					+ "* Item is removed from basket");
		}

		logger.info("STEP 5. To delete item(s) from your order:\n" + "* Tap on item in basket to go to PDP\n"
				+ "* Tap on garbage can icon\n" + "* Tap remove");
		productDetailsView = checkoutView.selectProduct("Snowcap Iced Mint Matcha Latte");
		RemoveFromOrderChunk removeFromOrderChunk = productDetailsView.delete();

		logger.info("EXPECTED 5. * User sees garbage can icon at top right corner of screen* User sees a UI alert:\n"
				+ "Title: Remove from Order\n" + "Text: Are you sure you like to remove this product from your order?\n"
				+ "[Cancel] [Remove]\n" + "* Item is removed from basket");
		Assert.assertNotNull(removeFromOrderChunk, "Remove from order dialog does not displayed");
		Assert.assertTrue(removeFromOrderChunk.isTitleDisplayed(), "Title: Remove from Order does not displayed");
		Assert.assertEquals(removeFromOrderChunk.messageText(),
				"Are you sure you would like to remove this product from your order?", "Wrong text message displayed");
		Assert.assertTrue(removeFromOrderChunk.isCancelButtonDisplayed(), "Cancel button does not displayed");
		Assert.assertTrue(removeFromOrderChunk.isRemoveButtonDisplayed(), "Remove button does not displayed");

		checkoutView = removeFromOrderChunk.remove();
		Assert.assertFalse(checkoutView.isProductDisplayed(productName), "Product remains in the cart and not deleted");

		if (getEnvironmentHelper().isMobileIOS()) {

			logger.info("STEP 6. To delete item(s) from your order::\n"
					+ "* Tap edit link at the top right corner of the CHECKOUT screen\n"
					+ "* Tap red (-) delete icon to the left of item\n"
					+ "* THIS STEP IS APPLICABLE ONLY ON IOS: Tap red delete button\n"
					+ "* Tap done at top right corner\n");

			logger.info("EXPECTED 6. " + "* User sees red (-) delete icon to the left of the items in basket\n"
					+ "* THIS IS APPLICABLE ONLY ON IOS: User sees red delete button to the right of the item* Item is removed from basket");
		}
	}
}
