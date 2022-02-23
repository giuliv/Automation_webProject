package com.applause.auto.test.mobile;

import static com.applause.auto.test.mobile.helpers.TestHelper.openOrderMenuForRecentCoffeeBar;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.Constants.MobileTestData;
import com.applause.auto.common.data.Constants.MyAccountTestData;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.common.data.enums.OrderMenuCategory;
import com.applause.auto.common.data.enums.OrderMenuSubCategory;
import com.applause.auto.common.data.enums.Products;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.mobile.components.AllowLocationServicesPopupChunk;
import com.applause.auto.mobile.components.CoffeeStoreContainerChuck;
import com.applause.auto.mobile.components.CoffeeStoreItemChuck;
import com.applause.auto.mobile.helpers.ItemOptions;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.mobile.views.CheckoutView;
import com.applause.auto.mobile.views.FindACoffeeBarView;
import com.applause.auto.mobile.views.HomeView;
import com.applause.auto.mobile.views.LandingView;
import com.applause.auto.mobile.views.NearbySelectCoffeeBarView;
import com.applause.auto.mobile.views.OnboardingView;
import com.applause.auto.mobile.views.OrderConfirmationView;
import com.applause.auto.mobile.views.OrderView;
import com.applause.auto.mobile.views.ProductDetailsView;
import com.applause.auto.mobile.views.SearchResultsView;
import com.applause.auto.mobile.views.SubCategoryView;
import com.applause.auto.pageobjectmodel.factory.LazyList;
import com.applause.auto.test.mobile.helpers.TestHelper;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class OrderTest extends BaseTest {

  private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().getClass());

  @Test(
      groups = {TestNGGroups.ORDER},
      description = "625889")
  public void locationServicesNotEnabled() {
    logger.info("Launch the app and arrive at the first onboarding screen view");
    OnboardingView onboardingView = openApp();

    // since autoGrantPermissions are set to true in capabilities, we have to deny location in this
    // test method to get Allow location pop up
    TestHelper.denyLocationServices();

    LandingView landingView = onboardingView.skipOnboarding();
    HomeView homeView =
        testHelper.login(landingView, MyAccountTestData.EMAIL, MyAccountTestData.PASSWORD);

    logger.info("Tap Order icon on the bottom nav bar");
    OrderView orderView = homeView.getBottomNavigationMenuChunk().tapMenu(OrderView.class);

    logger.info("Header: Order");
    Assert.assertEquals(
        orderView.getHeadingTextValue().toLowerCase().toUpperCase(), "ORDER", "Incorrect header");

    logger.info("[Pin] Locate Coffeebars");
    Assert.assertTrue(
        orderView.isLocateCoffeeBarsDisplayed(), "Locate Coffeebars pin does not displayed");

    logger.info("Tap Locate Coffeebars");
    AllowLocationServicesPopupChunk allowLocationServicesPopupChunk =
        orderView.locateCoffeebars(AllowLocationServicesPopupChunk.class);

    logger.info("Make sure Peet's branded Location Services alert appears:");
    logger.info("Title: Allow Location Services to help you find nearby Peet's Coffeebars.");
    Assert.assertEquals(
        allowLocationServicesPopupChunk.getTitle(),
        MobileTestData.ALLOW_LOCATION_SERVICE_MESSAGE,
        "'Allow Location' title does not found");

    logger.info(
        "Text: Location Services will:\n"
            + "\n"
            + "* Only use your location while using the app\n"
            + "\n"
            + "* Not share your locations or information\n"
            + "\n"
            + "* Pinpoint the coffeebars closest to you");
    String formattedMessage = allowLocationServicesPopupChunk.getFormattedMessage();
    Assert.assertEquals(
        formattedMessage,
        MobileTestData.ALLOW_LOCATION_SERVICE_MESSAGE
            + " Location Services will: Only use your location while using the app.",
        "Unexpected text");
    logger.info("[Button] Not Now [Button] Allow");
    Assert.assertTrue(
        allowLocationServicesPopupChunk.isAllowButtonDisplayed(),
        "Allow button does not displayed");
    Assert.assertTrue(
        allowLocationServicesPopupChunk.isNotNowButtonDisplayed(),
        "Not Now button does not displayed");

    logger.info("Tap Allow and complete");
    NearbySelectCoffeeBarView nearbySelectCoffeeBarView = allowLocationServicesPopupChunk.allow();
    CoffeeStoreContainerChuck coffeeStoreContainerChuck =
        nearbySelectCoffeeBarView.getCoffeeStoreContainerChuck();

    logger.info(
        "User should see loading dial, nearby stores should appear under nearby stores tab and user should be able to select a store");
    Assert.assertTrue(coffeeStoreContainerChuck.isStorePresent(), "No near stores returned");

    String currentCoffeeStoreContainerName1 = coffeeStoreContainerChuck.getStoreName();
    Assert.assertNotNull(currentCoffeeStoreContainerName1, "Store name was not fetch");

    String currentCoffeeStoreContainerName2 = coffeeStoreContainerChuck.getStoreName();
    Assert.assertNotNull(currentCoffeeStoreContainerName2, "Store name was not fetch");

    orderView = coffeeStoreContainerChuck.clickOrderButton();
    logger.info("Header: Order");
    Assert.assertEquals(
        orderView.getHeadingTextValue().toLowerCase().toUpperCase(), "ORDER", "Incorrect header");
  }

  @Test(
      groups = {TestNGGroups.ORDER},
      description = "625890")
  public void browseTheMenu() {
    logger.info("Launch the app and arrive at the first on boarding screen view");
    HomeView homeView =
        testHelper.skipOnboardingAndLogin(MyAccountTestData.EMAIL, MyAccountTestData.PASSWORD);

    logger.info("Tap Order icon on the bottom nav bar");
    OrderView orderView =
        homeView
            .getBottomNavigationMenuChunk()
            .tapMenu(AllowLocationServicesPopupChunk.class)
            .allowIfRequestDisplayed(OrderView.class);

    logger.info("Checking if Allow Location Services Popup is displayed");
    AllowLocationServicesPopupChunk allowLocationServicesPopupChunk =
        SdkHelper.create(AllowLocationServicesPopupChunk.class);
    if (allowLocationServicesPopupChunk.isNotNowButtonDisplayed()) {
      logger.info("Allow Location Services Popup displayed");
      // TODO: allowing location services is not working for iOS, so skipping for now
      allowLocationServicesPopupChunk.notNow();
      allowLocationServicesPopupChunk.clickCancelButton();

      logger.info("Tap Order icon on the bottom nav bar");
      orderView =
          homeView
              .getBottomNavigationMenuChunk()
              .tapMenu(AllowLocationServicesPopupChunk.class)
              .allow(OrderView.class);
    }

    logger.info("Header: Order");
    Assert.assertEquals(orderView.getHeadingTextValue().toUpperCase(), "ORDER", "Incorrect header");

    logger.info("[Pin] Locate Coffeebars");
    Assert.assertTrue(
        orderView.isLocateCoffeeBarsDisplayed(), "Locate Coffeebars pin does not displayed");

    logger.info("Sub-header: Seasonal Favorites");
    Assert.assertEquals(
        orderView.getOrderMenuChunk().getFavoritesSubHeaderTextValue(),
        "Favorites",
        "Incorrect Favorites sub-header");

    logger.info("Sub-header: Menu");
    Assert.assertEquals(
        orderView.getOrderMenuChunk().getMenuSubheaderTextValue(),
        "Menu",
        "Incorrect Menu sub-header");

    logger.info("Tap a category nd then Select a sub-category");
    SubCategoryView subCategoryView =
        orderView
            .selectCategory(OrderMenuCategory.FOOD)
            .selectSubCategory(OrderMenuSubCategory.WARM_BREAKFAST);
    Assert.assertEquals(
        subCategoryView.getTitle().toLowerCase().trim(),
        OrderMenuSubCategory.WARM_BREAKFAST.getSubCategory().toLowerCase(),
        "Page title is wrong");

    logger.info("Select a product");
    ProductDetailsView productDetailsView = subCategoryView.selectProduct(1);

    logger.info("Verify that 'Add to Order' button is displayed");
    Assert.assertTrue(
        productDetailsView.isAddToOrderButtonDisplayed(), "Add to order button isn't displayed");

    logger.info("Tap back arrow until back on main order screen");
    orderView =
        productDetailsView.navigateBack(SubCategoryView.class).navigateBack(OrderView.class);

    logger.info("Tap on search icon at top right corner of order screen");
    SearchResultsView searchResultsView = orderView.tapOnSearchButton();

    logger.info("Tap into search field and manually enter a search term (i.e. mocha)");
    productDetailsView = searchResultsView.search("mocha").selectSearchResultByIndex(1);

    logger.info("Verify that 'Add to Order' button is displayed");
    Assert.assertTrue(
        productDetailsView.isAddToOrderButtonDisplayed(), "Add to order button isn't displayed");

    logger.info("Tap back arrow on PDP");
    searchResultsView = productDetailsView.navigateBack(SearchResultsView.class);

    logger.info("Tap cancel link next to search menu field");
    orderView = searchResultsView.cancel(OrderView.class);

    logger.info("Verify that User returns to main order screen");
    Assert.assertNotNull(orderView, "Order view didn't appear");
  }

  @Test(
      groups = {TestNGGroups.ORDER},
      description = "625897",
      enabled = false)
  public void checkoutTest() {
    logger.info("Launch the app and arrive at the first on boarding screen view");
    HomeView homeView =
        testHelper.skipOnboardingAndLogin(MyAccountTestData.EMAIL, MyAccountTestData.PASSWORD);

    logger.info("Tap Order icon on the bottom nav bar");
    OrderView orderView =
        homeView
            .getBottomNavigationMenuChunk()
            .tapMenu(AllowLocationServicesPopupChunk.class)
            .allowIfRequestDisplayed(OrderView.class);

    logger.info("Tap a category and subcategory");
    SubCategoryView subCategoryView =
        orderView
            .selectCategory(OrderMenuCategory.HOT_COFFEE)
            .selectSubCategory(OrderMenuSubCategory.ESPRESSO);

    logger.info("Select a product");
    ProductDetailsView productDetail = subCategoryView.selectProduct(1);

    logger.info("User should be taken to product details page");
    Assert.assertNotNull(productDetail, "User des not taken to product detail page");

    logger.info("Scroll down PDP and select a modifiers");
    productDetail = productDetail.selectModifiers("Add Milk", "Splash of Half & Half", "None");

    logger.info("Add to Order");
    orderView = productDetail.addToOrder(OrderView.class);

    logger.info("Proceed to Checkout");
    CheckoutView checkout = orderView.checkout();

    logger.info("Navigate to Available Rewards");
    checkout = checkout.clickOnAwardItem("Free Beverage");

    logger.info("Verify - Order Summary is '$0.00'");
    Assert.assertEquals(checkout.getOrderTotal(), "$0.00", "Order total was not changed by reward");

    logger.info("Place Order");
    OrderConfirmationView orderConfirmationView = checkout.placeOrder(OrderConfirmationView.class);

    logger.info("Verify - Order Confirmation displayed");
    Assert.assertNotNull(orderConfirmationView, "Something happened during order placement");
  }

  @Test(
      groups = {TestNGGroups.ORDER},
      description = "2879930",
      enabled = false)
  public void orderAhead() {
    logger.info("Launch the app and arrive at the first on boarding screen view");
    HomeView homeView =
        testHelper.skipOnboardingAndLogin(MyAccountTestData.EMAIL, MyAccountTestData.PASSWORD);

    OrderView orderView = openOrderMenuForRecentCoffeeBar(homeView);

    logger.info("Tap a category and subcategory");
    SubCategoryView subCategoryView =
        orderView
            .selectCategory(OrderMenuCategory.HOT_COFFEE)
            .selectSubCategory(OrderMenuSubCategory.ESPRESSO);

    logger.info("Select a product");
    ProductDetailsView productDetail =
        subCategoryView.selectProduct(Products.ICED_ESPRESSO.getValue());

    logger.info("User should be taken to product details page");
    Assert.assertNotNull(productDetail, "User des not taken to product detail page");

    logger.info("Scroll down PDP and select a modifiers");
    productDetail = productDetail.selectModifiers("Ice", "Light Ice", "None");

    logger.info("Add to Order");
    orderView = productDetail.addToOrder(OrderView.class);

    logger.info("Proceed to Checkout");
    CheckoutView checkout = orderView.checkout();

    logger.info("Navigate to You might also like section. Tap on a product.");
    productDetail = checkout.clickYouMightAlsoLikeItem();
    Assert.assertNotNull(productDetail, "User does not taken to product detail page");

    logger.info("Add to Order");
    orderView = productDetail.addToOrder(OrderView.class);

    logger.info("Proceed to Checkout");
    checkout = TestHelper.checkoutOnItemsYouMightLike(orderView);

    logger.info("Checking total order items count");
    Assert.assertEquals(checkout.getOrderedItemsCount(), 2, "Ordered items count is incorrect");

    logger.info("Place Order");
    OrderConfirmationView orderConfirmationView = checkout.placeOrder(OrderConfirmationView.class);

    logger.info("Verify - Order Confirmation displayed");
    Assert.assertNotNull(orderConfirmationView, "Something happened during order placement");
  }

  @Test(
      groups = {TestNGGroups.ORDER, TestNGGroups.REGRESSION},
      description = "1687255")
  public void recentsFavoriteOrdersEmptyStateTest() {
    logger.info(
        "Precondition: User is already signed in to app\n"
            + "User is on main order screen and menu tab is highlighted\n"
            + "User has no recent orders\n"
            + "User has no favorite orders");

    logger.info("Launch the app and arrive at the first on boarding screen view");
    HomeView homeView = testHelper.createNewAccountWithDefaults();
    Assert.assertNotNull(homeView, "Home View does not displayed");

    OrderView orderView =
        homeView
            .getBottomNavigationMenuChunk()
            .tapMenu(AllowLocationServicesPopupChunk.class)
            .allowIfRequestDisplayed(NearbySelectCoffeeBarView.class)
            .search("78717")
            .openDefault();

    logger.info("STEP 1. Tap on Recents tab");
    orderView = orderView.recents();

    logger.info("EXPECTED 1. Recents tab is highlighted in gold");
    logger.info(
        "No recent orders card is displayed:\n"
            + "* Clock icon\n"
            + "* Title: No Recent Orders\n"
            + "* Text: Your recent orders will appear here to quickly order again.\n"
            + "* [Button] Start New Order");
    Assert.assertTrue(
        orderView.isTitleNoRecentOrdersDisplayed(), "Title <No recent orders> does not displayed");
    Assert.assertTrue(
        orderView.isMessageDisplayed("Your recent orders will appear here to quickly order again."),
        "Expected message <Your recent orders will appear here to quickly order again.> does not displayed");
    Assert.assertTrue(
        orderView.isStartNewOrderRecentsButtonDisplayed(),
        "Button <Start New Order> does not found>");

    logger.info("STEP 2. Tap Start New Order button");
    orderView = orderView.startNewOrderRecents();

    logger.info(
        "EXPECTED 2. User returns to menu tab on order screen and can view the menu categories");
    Assert.assertTrue(orderView.isMenuCategoriesDisplayed(), "User does not returned to menu");

    logger.info("STEP 3. Tap on Favorites tab");
    orderView = orderView.favorites();

    logger.info(
        "EXPECTED 3. Favorites tab is highlighted in gold\n"
            + "No favorited orders card is displayed:\n"
            + "* Heart icon* Title: No Favorited Orders\n"
            + "* Text: Favorite an order to save customizations and make your next coffee run even faster!\n"
            + "* [Button] Start New Order");
    Assert.assertTrue(
        orderView.isMessageDisplayed(
            "Favorite an order to save customizations and make your next coffee run even faster!"),
        "Expected message <Favorite an order to save customizations and make your next coffee run even faster!> does not displayed");
    Assert.assertTrue(
        orderView.isTitleNoFavoriteOrdersDisplayed(),
        "Title <No favorite orders> does not displayed");
    Assert.assertTrue(
        orderView.isStartNewOrderFavoritesButtonDisplayed(),
        "Button <Start New Order> does not found>");

    logger.info("STEP 4. Tap Start New Order button");
    orderView = orderView.startNewOrderFavorites();

    logger.info(
        "EXPECTED 4. User returns to menu tab on order screen and can view the menu categories");
    Assert.assertTrue(orderView.isMenuCategoriesDisplayed(), "User does not returned to menu");
  }

  @Test(
      groups = {TestNGGroups.ORDER, TestNGGroups.REGRESSION},
      description = "625930")
  public void recentsFavoriteCoffeebarsEmptyStateTest() {
    logger.info(
        "Precondition: User is already signed in to app\n"
            + "User is on main order screen and menu tab is highlighted\n"
            + "User has no recent orders\n"
            + "User has no favorite orders\n"
            + "Launch the app and arrive at the first on boarding screen view");
    HomeView homeView = testHelper.createNewAccountWithDefaults();
    Assert.assertNotNull(homeView, "Home View does not displayed");

    logger.info("STEP 1. Tap on store locator icon in top right corner");
    NearbySelectCoffeeBarView nearbySelectCoffeeBarView =
        homeView
            .getBottomNavigationMenuChunk()
            .tapMenu(AllowLocationServicesPopupChunk.class)
            .allowIfRequestDisplayed(NearbySelectCoffeeBarView.class);

    logger.info(
        "User is taken to find a coffeebar screen:\n"
            + ""
            + "* Header: FIND A COFFEEBAR"
            + "* X at top left corner to close screen"
            + "* Search field      "
            + "o Text: Enter Zip or City, State"
            + "* Search icon on the right of the text"
            + "* Current location icon on the map"
            + "* Three tabs: Nearby (default selected in gold), Recents, Favorites"
            + "* Map indicating nearest coffeebar with gold pin"
            + "Coffeebar location details (Scroll horizontally to see all nearby stores)\n"
            + ""
            + "* Coffeebar name* xx.x miles away on the right side of the store name"
            + "* Store Address* Open until x:xx PM"
            + "* [Button] Order");
    Assert.assertEquals(
        nearbySelectCoffeeBarView.getTitle(),
        "FIND A COFFEEBAR",
        "<FIND A COFFEEBAR> title does not displayed");
    Assert.assertTrue(
        nearbySelectCoffeeBarView.isCloseButtonDisplayed(), "Close button does not displayed");
    Assert.assertTrue(
        nearbySelectCoffeeBarView.isSearchFieldDisplayed(), "Search field does not displayed");
    Assert.assertTrue(
        nearbySelectCoffeeBarView.isSearchIconDisplayed(), "Search icon does not displayed");

    Assert.assertTrue(
        nearbySelectCoffeeBarView.isNearbyTabDisplayed(), "Nearby Tab does not displayed");
    Assert.assertTrue(
        nearbySelectCoffeeBarView.isRecentsTabDisplayed(), "Recents Tab does not displayed");
    Assert.assertTrue(
        nearbySelectCoffeeBarView.isFavoritesTabDisplayed(), "Favorites does not displayed");

    logger.info("STEP 2 - Search for any store either by nearby, recent tabs, or by zip code");
    nearbySelectCoffeeBarView.search("78717");

    CoffeeStoreItemChuck store = nearbySelectCoffeeBarView.getCoffeeStoreContainerChucks().get(0);
    String storeName = store.getStoreName();

    Assert.assertFalse(storeName.isEmpty(), "Store name is empty");
    Assert.assertTrue(store.isCoffeebarOpenHoursDisplayed(), "Open hours does not displayed");

    logger.info(
        "User sees blue dot on map and nearby store locations are indicated on the map as brown pins");
    Assert.assertTrue(
        nearbySelectCoffeeBarView.getPinsCount() > 0, "User does not see PINS on the map");

    logger.info("STEP 3. Swipe left to view more nearby store location cards");
    int x = store.getXposition();
    LazyList<CoffeeStoreItemChuck> stores =
        nearbySelectCoffeeBarView.getCoffeeStoreContainerChucks();
    if (stores.size() > 1) {
      store = stores.get(0).swipeLeft();
      int x1 = store.getXposition();
      stores = nearbySelectCoffeeBarView.getCoffeeStoreContainerChucks();
      CoffeeStoreItemChuck store2 = stores.get(1);
      int x2 = store2.getXposition();
      logger.info(
          "User sees the store location update on the map as user swipes through the nearby stores");
      Assert.assertNotEquals(x, x1, "Store does not changed, initial store remains on the screen");
      Assert.assertEquals(
          x2,
          x,
          "Store does not changed, second store does not displayed on same position as was for first");
    } else {
      logger.warn("Only one store found, cannot validate swiping");
    }

    logger.info("STEP 4. Tap Order button");
    OrderView order = store.clickOrderButton();

    logger.info("User is taken to main order screen");
    Assert.assertNotNull(order, "User does not taken to main order screen");

    logger.info("STEP 6. Tap back arrow");
    homeView =
        order
            .back(HomeView.class)
            .getReorderTooltipComponent()
            .closeAnyTooltipIfDisplayed(2, HomeView.class);

    logger.info("User is taken to home screen");
    Assert.assertNotNull(homeView, "User does not taken to home ");

    logger.info("STEP 7. Tap on store locator icon again");
    nearbySelectCoffeeBarView =
        homeView
            .getBottomNavigationMenuChunk()
            .tapMenu(AllowLocationServicesPopupChunk.class)
            .allowIfRequestDisplayed(NearbySelectCoffeeBarView.class);

    logger.info("User is taken to find a coffeebar screen");
    Assert.assertNotNull(nearbySelectCoffeeBarView, "User does not taken to find coffeebar screen");

    logger.info("STEP 8. Tap on Recents tab");
    nearbySelectCoffeeBarView.cancelSearch();
    FindACoffeeBarView findACoffeeBarView = nearbySelectCoffeeBarView.openRecentTab();
    logger.info(
        "Recents tab is highlighted in gold and the empty state shows:\n"
            + ""
            + "* Clock symbol"
            + "* Title: No recent coffeebars."
            + "* Text: Once you place your first order, you'll find the coffeebar here.");
    Assert.assertTrue(
        findACoffeeBarView.isTitleNoRecentCoffeebarsDisplayed(),
        "<Title: No recent coffeebars> does not displayed");
    Assert.assertTrue(
        findACoffeeBarView.isMessageDisplayed(
            "Once you place your first order, you'll find the coffeebar here."),
        "Message <Once you place your first order, you'll find the coffeebar here.> does not displayed on the recent tab");

    logger.info("STEP 8. Tap on Favorites tab");
    findACoffeeBarView = findACoffeeBarView.openFavoritesTab();
    logger.info(
        "Favorites tab is highlighted in gold and the empty state shows:\n"
            + ""
            + "* Heart symbol"
            + "* Title: No favorited coffeebars."
            + "* Text: Find a coffeebar and click on the heart to save it for later.");
    Assert.assertTrue(
        findACoffeeBarView.isTitleNoFavoriteCoffeebarsDisplayed(),
        "<Title: No favourite coffeebars> does not displayed");
    Assert.assertTrue(
        findACoffeeBarView.isMessageDisplayed(
            "Find a coffeebar and click on the heart to save it for later."),
        "Message <Find a coffeebar and click on the heart to save it for later.> does not displayed on the favorites tab");
  }

  @Test(
      groups = {TestNGGroups.ORDER, TestNGGroups.REGRESSION},
      description = "11051164")
  public void customizeOrderBeveragesTest() {
    logger.info(
        "Precondition: User is already signed in to app\n"
            + "User is on main order screen and pickup order mode is default selected\n"
            + "User has no items in basket"
            + "peets_order_beverages_ios@gmail.com/P@ssword1!");
    Constants.UserTestData account = MyAccountTestData.CHECKOUT_ACCOUNT;
    HomeView homeView =
        testHelper.skipOnboardingAndLogin(account.getUsername(), account.getPassword());
    Assert.assertNotNull(homeView, "Home View does not displayed");

    OrderView orderView =
        homeView
            .getBottomNavigationMenuChunk()
            .tapMenu(AllowLocationServicesPopupChunk.class)
            .allowIfRequestDisplayed(NearbySelectCoffeeBarView.class)
            .search("78717")
            .openDefault();

    logger.info("Step 1. Tap on Hot Coffee category to expand");
    logger.info("Expected 1. Sub-categories should expand downward");
    logger.info("Step 2. Select sub-category Lattes");
    logger.info("Expected 2. Lattes menu should open up");
    SubCategoryView subCategoryView =
        orderView
            .selectCategory(OrderMenuCategory.HOT_COFFEE)
            .selectSubCategory(OrderMenuSubCategory.LATTES);

    logger.info("Step 3. Select a beverage");
    ProductDetailsView productDetailsView =
        subCategoryView.selectProduct(Products.MAPLE_LATTE.getValue());

    logger.info("Expected 3. User is taken to PDP");
    Assert.assertNotNull(productDetailsView, "PDP does not displayed");

    logger.info(
        "Step 4. Scroll down PDP and customize beverage by selecting modifiers (where applicable based on item selected):\n"
            + ""
            + "* Size"
            + "* Milk Prep / Add Milk");
    String cost = productDetailsView.getCost();
    productDetailsView.selectSize("Large");
    String costAfterSizeChanged = productDetailsView.getCost();
    Assert.assertNotEquals(cost, costAfterSizeChanged, "Price does not updated");

    productDetailsView.selectMilkPrep().chooseMilk("2% Milk").saveChanges(ProductDetailsView.class);

    logger.info(
        "Expected 4. f applicable:\n"
            + ""
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

    logger.info(
        "Expected 7. Make sure beverage customizations flow through correctly to checkout screen");
    SoftAssert softAssert = new SoftAssert();
    softAssert.assertTrue(
        maple.contains("Large"), "Mapple Drink have wrong cup size: Large expected");
    softAssert.assertTrue(
        maple.contains("Choose Milk: 2% Milk"),
        "Mapple Drink have wrong milk: Choose Milk: 2% Milk");

    softAssert.assertAll();
    logger.info(
        "Step 16. Tap X at top left corner of Checkout screen to return to main order screen");
    orderView = checkoutView.close();

    logger.info("Expected 16. User is taken back to main order screen");
    Assert.assertNotNull(orderView, "User does not taken back to main order screen");
  }

  @Test(
      groups = {TestNGGroups.ORDER, TestNGGroups.REGRESSION},
      description = "11051165")
  public void customizeOrderFoodTest() {
    logger.info(
        "User is already signed in to app\n"
            + "User is on main order screen and pickup order mode is default selected\n"
            + "User continues this test case from previous test case (so user will have items added to order already)");
    Constants.UserTestData account = MyAccountTestData.CHECKOUT_ACCOUNT;
    HomeView homeView =
        testHelper.skipOnboardingAndLogin(account.getUsername(), account.getPassword());
    Assert.assertNotNull(homeView, "Home View does not displayed");

    OrderView orderView =
        homeView
            .getBottomNavigationMenuChunk()
            .tapMenu(AllowLocationServicesPopupChunk.class)
            .allowIfRequestDisplayed(NearbySelectCoffeeBarView.class)
            .search("78717")
            .openDefault();

    logger.info("STEP 1. Tap on Food category to expand");
    logger.info("STEP 2. Select sub-category Baked Goods");
    SubCategoryView subCategoryView =
        orderView
            .selectCategory(OrderMenuCategory.FOOD)
            .selectSubCategory(OrderMenuSubCategory.BAKED_GOODS);

    logger.info("EXPECTED 1. Sub-categories should expand downward");
    logger.info(
        "EXPECTED 2. Make sure FAB is displayed on sub-category screen and shows correct quantity of item(s)");
    logger.info("EXPECTED 2. User is taken to Baked Goods Menu");

    logger.info("STEP 3. Select any baked goods item");
    String productName = Products.ICED_CINNAMON_ROLL.getValue();
    ProductDetailsView productDetailsView = subCategoryView.selectProduct(productName);

    logger.info("EXPECTED 3. User is taken to PDP");
    Assert.assertNotNull(productDetailsView, " User does not taken to PDP");

    String cost = productDetailsView.getCost();
    logger.info("STEP 4. Add cream");
    productDetailsView = productDetailsView.selectQuantity("2");
    String costAfterSizeChanged = productDetailsView.getCost();
    Assert.assertNotEquals(cost, costAfterSizeChanged, "Price does not updated");

    logger.info(" STEP 5. Select warming");
    productDetailsView = productDetailsView.warming().warm().saveChanges(ProductDetailsView.class);
    logger.info("EXPECTED 4. price should be updated");

    logger.info("STEP 5. Tap Add to Order button");
    orderView = productDetailsView.addToOrder(OrderView.class);

    logger.info("STEP 10. Tap on the FAB");
    CheckoutView checkoutView = orderView.checkoutAtom(CheckoutView.class);

    logger.info("EXPECTED 10. User is taken to checkout screen");
    Assert.assertNotNull(checkoutView, "User does not returned to checkout screen");

    logger.info("STEP 11. Review food order details on checkout screen");
    ItemOptions plainBagel = checkoutView.getItemOptions(productName);

    logger.info(
        "EXPECTED 11. Make sure food customizations flow through correctly to checkout screen");
    SoftAssert softAssert = new SoftAssert();
    softAssert.assertTrue(plainBagel.contains("Qty: 2"), "Wrong quantity. Expected Qty:2");
    softAssert.assertTrue(
        plainBagel.contains(MobileHelper.isAndroid() ? "Shall we warm this item?: Warm" : "Warm"),
        "Wrong warm option. Expected Warm");

    softAssert.assertAll();
  }

  @Test(
      groups = {TestNGGroups.ORDER, TestNGGroups.REGRESSION},
      description = "11051166")
  public void editOrderTest() {
    logger.info(
        "User is already signed in to app\n"
            + "User is on the checkout screen\n"
            + "User continues this test case from previous test case (so user will have items added to order already)");
    Constants.UserTestData account = MyAccountTestData.CHECKOUT_ACCOUNT;
    HomeView homeView =
        testHelper.skipOnboardingAndLogin(account.getUsername(), account.getPassword());
    Assert.assertNotNull(homeView, "Home View does not displayed");

    OrderView orderView =
        homeView
            .getBottomNavigationMenuChunk()
            .tapMenu(AllowLocationServicesPopupChunk.class)
            .allowIfRequestDisplayed(NearbySelectCoffeeBarView.class)
            .search("78717")
            .openDefault();

    logger.info("STEP 1. Tap on Food category to expand");
    logger.info("STEP 2. Select sub-category Baked Goods");
    String productName = Products.ICED_CINNAMON_ROLL.getValue();
    SubCategoryView subCategoryView =
        orderView
            .selectCategory(OrderMenuCategory.FOOD)
            .selectSubCategory(OrderMenuSubCategory.BAKED_GOODS);
    ProductDetailsView productDetailsView = subCategoryView.selectProduct(productName);

    logger.info("STEP 3. Tap Add to Order button");
    orderView = productDetailsView.addToOrder(OrderView.class);
    subCategoryView =
        orderView
            .selectCategory(OrderMenuCategory.HOT_COFFEE)
            .selectSubCategory(OrderMenuSubCategory.LATTES);
    String productName2 = Products.MAPLE_LATTE.getValue();
    orderView = subCategoryView.selectProduct(productName2).addToOrder(OrderView.class);

    logger.info("Add drink #2");
    orderView.selectSubCategory(OrderMenuSubCategory.LATTES);
    String productName3 = Products.CAFFE_LATTE.getValue();
    productDetailsView = subCategoryView.selectProduct(productName3);

    logger.info("STEP 9. Tap Add to Order button");
    orderView = productDetailsView.addToOrder(OrderView.class);

    logger.info("Step Tap FAB");
    CheckoutView checkoutView = orderView.checkout();

    logger.info("STEP Click item from your order > Tap back arrow > Checkout displayed");
    productDetailsView = checkoutView.selectProduct(productName2);
    checkoutView = productDetailsView.navigateBack(CheckoutView.class);

    logger.info("STEP Click item from your order > Edit size > Update Order > price changes");
    productDetailsView = checkoutView.selectProduct(productName2);
    productDetailsView.selectSize("Large");
    checkoutView = productDetailsView.updateOrder();

    logger.info("STEP Click item from your order > Tap Garbage > Element removed");
    productDetailsView = checkoutView.selectProduct(productName);

    logger.info(
        "STEP 5. To delete item(s) from your order:\n"
            + "* Tap on item in basket to go to PDP\n"
            + "* Tap on garbage can icon\n"
            + "* Tap remove");
    checkoutView = productDetailsView.delete().remove();
    Assert.assertFalse(
        checkoutView.isProductDisplayed(productName),
        "Product remains in the cart and not deleted");

    if (!MobileHelper.isAndroid()) {
      logger.info(
          "STEP 4. To delete item(s) from your order:\n"
              + "THIS FLOW APPLICABLE ONLY ON IOS:\n"
              + "* Swipe left on the item in the basket\n"
              + "* Tap red delete button");
      checkoutView = checkoutView.deleteBySwipe(productName2);

      logger.info(
          "EXPECTED 4. \n"
              + "* User sees red delete button to the right of the item\n"
              + "* Item is removed from basket");
      Assert.assertFalse(
          checkoutView.isProductDisplayed(productName2),
          "Product Mapple latte seems does not removed");
    }

    logger.info(
        "STEP 6. To delete item(s) from your order::\n"
            + "* Tap edit link at the top right corner of the CHECKOUT screen\n"
            + "* Tap red (-) delete icon to the left of item\n"
            + "* THIS STEP IS APPLICABLE ONLY ON IOS: Tap red delete button\n"
            + "* Tap done at top right corner\n");
    checkoutView = checkoutView.deleteByEditButton(productName3);

    logger.info(
        "EXPECTED 6. "
            + "* User sees red (-) delete icon to the left of the items in basket\n"
            + "* THIS IS APPLICABLE ONLY ON IOS: User sees red delete button to the right of the item* Item is removed from basket");
    Assert.assertFalse(
        checkoutView.isProductDisplayed(productName3),
        "Product Oatmeal remains in the cart and not deleted");
  }
}
