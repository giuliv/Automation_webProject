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
import com.applause.auto.mobile.helpers.ItemOptions;
import com.applause.auto.mobile.views.*;
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

	@Test(groups = { TestNGGroups.ORDER, TestNGGroups.REGRESSION }, description = "1687255", enabled = true)
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

	@Test(groups = { TestNGGroups.ORDER, TestNGGroups.REGRESSION }, description = "625930", enabled = true)
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
		nearbySelectCoffeeBarView.search("78717");
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

	@Test(groups = { TestNGGroups.ORDER, TestNGGroups.REGRESSION }, description = "11051164", enabled = true)
	public void customizeOrderBeveragesTest() {
		logger.info("Precondition: User is already signed in to app\n"
				+ "User is on main order screen and pickup order mode is default selected\n"
				+ "User has no items in basket" + "peets_order_beverages_ios@gmail.com/P@ssword1!");
		LandingView landingView = this.create(LandingView.class);
		DashboardView dashboardView = testHelper.signIn(landingView, "peets.auto01@gmail.com", "p4ssword!",
				DashboardView.class);
		// testHelper.signIn(
		// landingView, "peets_order_beverages_ios@gmail.com", "P@ssword1!", DashboardView.class);

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
						+ "" + "* Size" + "* Milk Prep / Add Milk");
		String cost = productDetailsView.getCost();
		productDetailsView.selectSize("Large");
		String costAfterSizeChanged = productDetailsView.getCost();
		Assert.assertNotEquals(cost, costAfterSizeChanged, "Price does not updated");

		productDetailsView.selectMilkPrep().chooseMilk("2% Milk").saveChanges(ProductDetailsView.class);

		logger.info("Expected 4. f applicable:\n" + ""
				+ "* Make sure for multiple-sized items medium size is default selected"
				+ "* Make sure tapping different sizes changes the price and calorie information for the product"
				+ "* User should be able to select and save different modifiers and it should be reflected on the PDP under the modifier selection"
				+ "* The price on PDP should reflect any additional costs from customizing the beverage");

		logger.info("Step 5. Tap Add to Order button");
		orderView = orderView.addToOrders();

		logger.info("Step 6. Tap on the FAB");
		CheckoutView checkoutView = orderView.checkoutAtom(CheckoutView.class);

		logger.info("Expected 6. User is taken to checkout screen");
		Assert.assertNotNull(checkoutView, "User does not taken to checkout screen");

		logger.info("Step 7. Review beverage order details on checkout screen");
		ItemOptions maple = checkoutView.getItemOptions("Maple Latte");

		logger.info("Expected 7. Make sure beverage customizations flow through correctly to checkout screen");
		//////////////
		//// Maple - Large - Vanilla Syrup - 2% Milk - Short Pull - Sugar 5 - Qty 2
		//////////////
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertTrue(maple.contains("Large"), "Mapple Drink have wrong cup size: Large expected");
		softAssert.assertTrue(maple.contains("Choose Milk: 2% Milk"),
				"Mapple Drink have wrong milk: Choose Milk: 2% Milk");

		softAssert.assertAll();
		logger.info("Step 16. Tap X at top left corner of Checkout screen to return to main order screen");
		orderView = checkoutView.close();

		logger.info("Expected 16. User is taken back to main order screen");
		Assert.assertNotNull(orderView, "User does not taken back to main order screen");
	}

	@Test(groups = { TestNGGroups.ORDER, TestNGGroups.REGRESSION }, description = "11051165", enabled = true)
	public void customizeOrderFoodTest() {
		logger.info("User is already signed in to app\n"
				+ "User is on main order screen and pickup order mode is default selected\n"
				+ "User continues this test case from previous test case (so user will have items added to order already)");
		LandingView landingView = this.create(LandingView.class);
		DashboardView dashboardView = testHelper.signIn(landingView, "peets.auto01@gmail.com", "p4ssword!",
				DashboardView.class);
		// testHelper.signIn(
		// landingView, "peets_order_beverages_ios@gmail.com", "P@ssword1!", DashboardView.class);

		NewOrderView orderView = dashboardView.getBottomNavigationMenu().order(AllowLocationServicesPopupChunk.class)
				.allowIfRequestDisplayed(NearbySelectCoffeeBarView.class).search("78717").openDefault();

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

		String cost = productDetailsView.getCost();
		logger.info("STEP 4. Add cream");
		productDetailsView = productDetailsView.selectAddCreamCheese().incereaseCount("Add Cream Cheese", "2")
				.saveChanges(ProductDetailsView.class);
		String costAfterSizeChanged = productDetailsView.getCost();
		Assert.assertNotEquals(cost, costAfterSizeChanged, "Price does not updated");

		logger.info(" STEP 5. Select warming");
		productDetailsView = productDetailsView.warming().warm().saveChanges(ProductDetailsView.class);
		logger.info("EXPECTED 4. price should be updated");

		logger.info("STEP 5. Tap Add to Order button");
		orderView = productDetailsView.addToOrder(NewOrderView.class);

		logger.info("STEP 10. Tap on the FAB");
		CheckoutView checkoutView = orderView.checkoutAtom(CheckoutView.class);

		logger.info("EXPECTED 10. User is taken to checkout screen");
		Assert.assertNotNull(checkoutView, "User does not returned to checkout screen");

		logger.info("STEP 11. Review food order details on checkout screen");
		ItemOptions plainBagel = checkoutView.getItemOptions("Plain Bagel");

		logger.info("EXPECTED 11. Make sure food customizations flow through correctly to checkout screen");
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertTrue(plainBagel.contains("Add Cream Cheese (x2)"),
				"Plain Bagel: Wrong quantity: Expected Qty:1");
		softAssert.assertTrue(plainBagel.contains("Warm"), "Plain Bagel: Wrong warm option: Expected Warm");

		softAssert.assertAll();
	}

	@Test(groups = { TestNGGroups.ORDER, TestNGGroups.REGRESSION }, description = "11051166", enabled = true)
	public void editOrderTest() {
		logger.info("User is already signed in to app\n" + "User is on the checkout screen\n"
				+ "User continues this test case from previous test case (so user will have items added to order already)");

		LandingView landingView = this.create(LandingView.class);
		DashboardView dashboardView = testHelper.signIn(landingView, "peets.auto01@gmail.com", "p4ssword!",
				DashboardView.class);

		NewOrderView orderView = dashboardView.getBottomNavigationMenu().order(AllowLocationServicesPopupChunk.class)
				.allowIfRequestDisplayed(NearbySelectCoffeeBarView.class).search("78717").openDefault();

		/////
		logger.info("STEP 1. Tap on Food category to expand");
		logger.info("STEP 2. Select sub-category Baked Goods");
		String productName = "Plain Bagel";
		orderView.selectCategoryAndSubCategory("Food", "Baked Goods");
		ProductDetailsView productDetailsView = orderView.selectProduct(productName);

		logger.info("STEP 3. Tap Add to Order button");
		orderView = productDetailsView.addToOrder(NewOrderView.class);

		logger.info("Add Drink");
		orderView.selectCategoryAndSubCategory("Hot Coffee", "Lattes");
		orderView = orderView.selectProduct("Maple Latte").addToOrder(NewOrderView.class);

		logger.info("Add drink #2");
		orderView.selectCategoryAndSubCategory("Food", "Warm Breakfast");
		productDetailsView = orderView.selectProduct("Oatmeal");

		logger.info("STEP 9. Tap Add to Order button");
		orderView = productDetailsView.addToOrder(NewOrderView.class);

		logger.info("Step Tap FAB");
		CheckoutView checkoutView = orderView.checkout();

		logger.info("STEP 3. Tap Update Order button");
		checkoutView = productDetailsView.updateOrder();

		logger.info("STEP Click item from your order > Edit size > Update Order > price changes");
		String oldTotal = checkoutView.getOrderTotal();
		productDetailsView = checkoutView.selectProduct("Maple Latte");
		productDetailsView.selectSize("Large");
		checkoutView = productDetailsView.updateOrder();

		Assert.assertNotEquals(oldTotal, checkoutView.getOrderTotal(), "Order total does not reflected on item update");

		if (getEnvironmentHelper().isMobileIOS()) {
			logger.info("STEP 4. To delete item(s) from your order:\n" + "THIS FLOW APPLICABLE ONLY ON IOS:\n"
					+ "* Swipe left on the item in the basket\n" + "* Tap red delete button");

			logger.info("EXPECTED 4. \n" + "* User sees red delete button to the right of the item\n"
					+ "* Item is removed from basket");
		}

		logger.info("STEP 5. To delete item(s) from your order:\n" + "* Tap on item in basket to go to PDP\n"
				+ "* Tap on garbage can icon\n" + "* Tap remove");
		productDetailsView = checkoutView.selectProduct(productName);
		checkoutView = productDetailsView.delete().remove();

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
