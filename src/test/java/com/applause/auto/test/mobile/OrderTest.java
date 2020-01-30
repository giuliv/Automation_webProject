package com.applause.auto.test.mobile;

import com.applause.auto.common.data.Constants.MyAccountTestData;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.mobile.components.AllowLocationServicesPopupChunk;
import com.applause.auto.mobile.components.CoffeeStoreContainerChuck;
import com.applause.auto.mobile.views.CheckoutView;
import com.applause.auto.mobile.views.DashboardView;
import com.applause.auto.mobile.views.LandingView;
import com.applause.auto.mobile.views.NearbySelectCoffeeBarView;
import com.applause.auto.mobile.views.NewOrderView;
import com.applause.auto.mobile.views.OrderConfirmationView;
import com.applause.auto.mobile.views.OrderView;
import com.applause.auto.mobile.views.ProductDetailsView;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.test.mobile.helpers.TestHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.invoke.MethodHandles;

import static com.applause.auto.test.mobile.helpers.TestHelper.openOrderMenuForRecentCoffeeBar;

public class OrderTest extends BaseTest {

  private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().getClass());

  // updated according to https://appauto.testrail.net/index.php?/cases/view/625889
  @Test(
      enabled = true,
      groups = {TestNGGroups.ORDER},
      description = "625889")
  public void locationServicesNotEnabled() {
    logger.info("Launch the app and arrive at the first on boarding screen view");
    LandingView landingView = ComponentFactory.create(LandingView.class);

    // since autoGrantPermissions are set to ture in capabilities, we have to deny location in this
    // test method to get Allow location pop up
    TestHelper.denyLocationServices();

    DashboardView dashboardView =
        testHelper.signIn(
            landingView, MyAccountTestData.EMAIL, MyAccountTestData.PASSWORD, DashboardView.class);
    Assert.assertNotNull(dashboardView, "Dashboard View does not displayed");

    logger.info("Tap Order icon on the bottom nav bar");
    OrderView orderView = dashboardView.getBottomNavigationMenu().order();

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
        "Allow Location Services to help you find nearby Peet's Coffeebars",
        "'Allow Location Services to help you find nearby Peet's Coffeebars' title does not found");

    logger.info(
        "Text: Location Services will:\n"
            + "\n"
            + "* Only use your location while using the app\n"
            + "\n"
            + "* Not share your locations or information\n"
            + "\n"
            + "* Pinpoint the coffeebars closest to you");
    Assert.assertTrue(
        allowLocationServicesPopupChunk
            .getFormattedMessage()
            .matches(
                "Location Services will: Allow Location Services to help you find nearby Peet(’|')s Coffeebars Only use your location while using th(e|is) app Not share your locations? or information Pinpoint the coffeebars closest to you"),
        "Unexpected text: ");
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
    Assert.assertEquals(
        orderView.getHeadingTextValue().toLowerCase().toUpperCase(), "ORDER", "Incorrect header");
  }

  // TODO should be rewritten due to test case/UI changes
  @Test(
      groups = {TestNGGroups.ORDER},
      description = "625890")
  public void browseTheMenu() {
    logger.info("Launch the app and arrive at the first on boarding screen view");
    LandingView landingView = ComponentFactory.create(LandingView.class);
    DashboardView dashboardView =
        testHelper.signIn(
            landingView, MyAccountTestData.EMAIL, MyAccountTestData.PASSWORD, DashboardView.class);
    Assert.assertNotNull(dashboardView, "Dashboard View does not displayed");

    logger.info("Tap Order icon on the bottom nav bar");
    OrderView orderView = dashboardView.getBottomNavigationMenu().order();

    logger.info("Header: Order");
    Assert.assertEquals(orderView.getHeadingTextValue().toUpperCase(), "ORDER", "Incorrect header");

    logger.info("[Pin] Locate Coffeebars");
    Assert.assertTrue(
        orderView.isLocateCoffeeBarsDisplayed(), "Locate Coffeebars pin does not displayed");

    logger.info("Sub-header: Seasonal Favorites");
    Assert.assertEquals(
        orderView.getOrderMenuChunck().getSeasonalFavoritesSubHeaderTextValue(),
        "Seasonal Favorites",
        "Incorrect Favorites sub-header");

    logger.info("Sub-header: Menu");
    Assert.assertEquals(
        orderView.getOrderMenuChunck().getMenuSubheaderTextValue(),
        "Menu",
        "Incorrect Menu sub-header");
    //
    //      logger.info("Tap Locate Coffeebars");
    //      NearbySelectCoffeeBarView nearbySelectCoffeeBarView =
    // orderView.locateCoffeebars(NearbySelectCoffeeBarView.class);
    //
    //      logger.info(
    //          "Select a store from:\n"
    //              + "\n"
    //              + "Nearby\n"
    //              + "\n"
    //              + "Recents\n"
    //              + "\n"
    //              + "Favorites\n"
    //              + "\n"
    //              + "OR\n"
    //              + "\n"
    //              + "by using search function\n");
    //      nearbySelectCoffeeBarView.search("94608");
    //      NewOrderView newOrderView = nearbySelectCoffeeBarView.openCoffeebarFromSearchResults(1);
    //
    //      logger.info("Tap a category");
    //      newOrderView.selectCategory("Espresso Beverages");
    //
    //      logger.info("Sub-categories should expand downward");
    //      List<String> items = newOrderView.getCategoryItems("Espresso Beverages");
    //      Assert.assertTrue(items.size() > 0, "Sub categories does not expand");
    //
    //      logger.info("Select a sub-category");
    //      newOrderView.selectSubCategory("Espresso Beverages", items.get(0));
    //
    //      logger.info("Select a product");
    //      ProductDetailsView productDetail = newOrderView.selectProduct("Iced Espresso");
    //
    //      logger.info("User should be taken to product details page");
    //      Assert.assertNotNull(productDetail, "User des not taken to product detail page");
    //
    //      logger.info("Scroll down PDP and select a modifiers");
    //      productDetail = productDetail.selectModifiers("Ice", "Light Ice");
    //
    //      logger.info("Return to main order menu screen");
    //      newOrderView = productDetail.navigateBack(NewOrderView.class);
    //
    //      logger.info("Tap on category header again");
    //      newOrderView.selectCategory("Espresso Beverages");
    //
    //      logger.info("Category should collapse");
    //      items = newOrderView.getCategoryItems("Espresso Beverages");
    //      Assert.assertTrue(items.size() == 0, "Categories does not collapsed");
    //
    //      logger.info("Tap on search icon");
    //      logger.info(
    //          "Search menu field should appear at top of screen\n"
    //              + "\n"
    //              + "User should see a list of recent products (if applicable) populate below
    // search
    // field\n");
    //      logger.info("Tap into search field and manually enter a search term (i.e. mocha)");
    //      SearchResultsView searchResultsView = newOrderView.search("mocha");
    //
    //      logger.info("Make sure items appear");
    //      Assert.assertTrue(
    //          searchResultsView.getResults().get(0).toLowerCase().contains("mocha"),
    //          "No relevant search results");
    //
    //      logger.info("Tap on an item");
    //      ProductDetailsView productDetailsView = searchResultsView.selectSearchResultByIndex(0);
    //
    //      logger.info("User should be taken to product details page:");
    //      Assert.assertNotNull(productDetailsView, "Product detail view does not displayed");
    //
    //      logger.info("Tap back arrow on PDP");
    //      searchResultsView = productDetailsView.navigateBack(SearchResultsView.class);
    //
    //      logger.info("User should be taken back to search menu screen");
    //      Assert.assertNotNull(searchResultsView, "User does not taken back to search menu
    // screen");
  }

  @Test(
      groups = {TestNGGroups.ORDER},
      description = "625897")
  public void checkoutTest() throws InterruptedException {
    logger.info("Launch the app and arrive at the first on boarding screen view");
    LandingView landingView = ComponentFactory.create(LandingView.class);
    DashboardView dashboardView =
        testHelper.signIn(
            landingView, MyAccountTestData.EMAIL, MyAccountTestData.PASSWORD, DashboardView.class);

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
}
