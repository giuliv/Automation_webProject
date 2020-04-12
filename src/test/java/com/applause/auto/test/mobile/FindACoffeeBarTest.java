package com.applause.auto.test.mobile;

import com.applause.auto.common.data.Constants.MyAccountTestData;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.mobile.components.AllowLocationServicesPopupChunk;
import com.applause.auto.mobile.components.CoffeeStoreContainerChuck;
import com.applause.auto.mobile.components.MapView;
import com.applause.auto.mobile.views.DashboardView;
import com.applause.auto.mobile.views.FindACoffeeBarView;
import com.applause.auto.mobile.views.LandingView;
import com.applause.auto.mobile.views.NearbySelectCoffeeBarView;
import com.applause.auto.mobile.views.OrderView;
import com.applause.auto.mobile.views.StoreDetailsView;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FindACoffeeBarTest extends BaseTest {

  private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().getClass());

  @Test(
      groups = {TestNGGroups.ORDER},
      description = "1687260")
  public void findACoffeeBarTest() {
    logger.info(
        "PRECONDITION - User is signed in to app\n"
            + "User is on find a coffeebar screen\n"
            + "User has previously placed an order\n"
            + "User has a favorite coffeebar\n"
            + "User has no items in basket");

    logger.info("Launch the app and arrive at the first on boarding screen view");
    LandingView landingView = ComponentFactory.create(LandingView.class);
    DashboardView dashboardView =
        testHelper.signIn(
            landingView, MyAccountTestData.EMAIL, MyAccountTestData.PASSWORD, DashboardView.class);

    logger.info("STEP - Tap on any of the three tabs: Nearby, Recents, Favorites");
    AllowLocationServicesPopupChunk allowLocationServicesPopupChunk =
        dashboardView
            .getBottomNavigationMenu()
            .order(OrderView.class)
            .locateCoffeebars(AllowLocationServicesPopupChunk.class);
    NearbySelectCoffeeBarView nearbySelectCoffeeBarView =
        allowLocationServicesPopupChunk.allowIfRequestDisplayed();
    FindACoffeeBarView findACoffeeBarView = nearbySelectCoffeeBarView.openRecentTab();

    logger.info("VERIFY - User sees store location cards");
    CoffeeStoreContainerChuck store = findACoffeeBarView.getCoffeeStoreContainerChuck();
    Assert.assertTrue(store.isStorePresent(), "User does not sees store location cards");

    logger.info("STEP - Tap on a store location card to view store details");
    StoreDetailsView storeDetails = store.openStoreDetails();

    logger.info("VERIFY - User will taken to store details screen");
    Assert.assertNotNull(storeDetails, "User does not taken to store details screen");

    logger.info("VERIFY - Arrow on the left corner to navigate back to the previous screen");
    Assert.assertTrue(
        storeDetails.isNavigateBackDisplayed(),
        "Arrow on the left corner to navigate back to the previous screen does not displayed");

    logger.info("VERIFY - Header: [COFFEEBAR NAME]");
    String storeName = storeDetails.getCoffeebarHeader();
    Assert.assertTrue(storeName.length() > 0, "Header: [COFFEEBAR NAME] does not displayed");

    logger.info("VERIFY - Map view of coffeebar and a brown pin indicating location on the map");
    Assert.assertTrue(
        storeDetails.isCoffeebarPinDisplayedOnMap(),
        "Map view of coffeebar and a brown pin indicating location on the map does not displayed");

    logger.info("VERIFY - Sub header: [Coffeebar Name]");
    Assert.assertTrue(
        storeDetails.getCoffeebarSubHeaderName().length() > 0,
        "Sub header: [Coffeebar Name] does not displayed");

    logger.info(
        "VERIFY - Heart icon for user to mark favorite store at top right corner (if it's marked favorite, the heart is filled in red; if it's not marked favorite, the heart is outlined in gold)");
    Assert.assertTrue(
        storeDetails.isCoffeebarFavorite(),
        "Heart icon for user to mark favorite store does not displayed");

    logger.info("VERIFY - Address of the coffeebar and distance [x.x miles away]");
    Assert.assertTrue(
        storeDetails.getCoffeebarLocation().length() > 0,
        "Address of the coffeebar and distance [x.x miles away] field does not displayed");

    logger.info("VERIFY - Sub header: HOURS");
    Assert.assertTrue(
        storeDetails.isCoffeebarSubHeaderHoursDisplayed(), "Sub header: HOURS does not displayed");

    logger.info("VERIFY - Open until x:xx PM");
    Assert.assertTrue(
        storeDetails.isCoffeebarOpenHoursDisplayed(), "Open until x:xx PM does not displayed");

    logger.info("VERIFY - Table with columns: Day and Store Hours (or Closed if applicable)");
    Assert.assertTrue(
        storeDetails.isCoffeebarDayAndStoreHoursDisplayed(),
        "Table with columns: Day and Store Hours (or Closed if applicable) does not displayed");

    logger.info("VERIFY - Current day is highlighted in gold");
    // TODO Unable to verify colors

    logger.info("VERIFY - [Button] Directions");
    Assert.assertTrue(
        storeDetails.isDirectionsButtonDisplayed(), "[Button] Directions does not displayed");

    logger.info("VERIFY - [Button] Order");
    Assert.assertTrue(storeDetails.isOrderButtonDisplayed(), "[Button] Order does not displayed");

    logger.info("STEP - Tap Directions button");
    MapView map = storeDetails.directions();

    logger.info(
        "VERIFY - User is taken to device's native map app with directions to coffeebar from user's current location maps");
    Assert.assertNotNull(
        map,
        "User does not taken to device's native map app with directions to coffeebar from user's current location maps");

    logger.info("STEP - Tap on \"Peet's\" at the top left corner to return to Peet's app");
    storeDetails = map.returnToPeetsApp(StoreDetailsView.class);

    logger.info("VERIFY - User is taken back to store details screen");
    Assert.assertNotNull(storeDetails, "User dies not taken back to store details screen");

    logger.info("STEP - Tap Order button");
    OrderView orderView = storeDetails.order();

    logger.info("VERIFY - User will navigate to main order screen");
    Assert.assertEquals(
        orderView.getHeadingTextValue().toLowerCase().toUpperCase(),
        "ORDER",
        "User does not navigate to main order screen");

    logger.info("VERIFY - Make sure coffeebar name is correct on location field of order screen");
    Assert.assertEquals(
        orderView.getStoreName(),
        storeName,
        "Coffeebar name is correct on location field of order screen");
  }

  @Test(
      groups = {TestNGGroups.ORDER},
      description = "1687261")
  public void recentCoffeeBarTest() {
    logger.info(
        "PRECONDITION - User is signed in to app\n"
            + "User is on find a coffeebar screen\n"
            + "User has previously placed orders in at least two different coffeebars");

    logger.info("Launch the app and arrive at the first on boarding screen view");
    LandingView landingView = ComponentFactory.create(LandingView.class);
    DashboardView dashboardView =
        testHelper.signIn(
            landingView, MyAccountTestData.EMAIL, MyAccountTestData.PASSWORD, DashboardView.class);

    logger.info("STEP - Tap on Recents tab");
    AllowLocationServicesPopupChunk allowLocationServicesPopupChunk =
        dashboardView
            .getBottomNavigationMenu()
            .order(OrderView.class)
            .locateCoffeebars(AllowLocationServicesPopupChunk.class);
    NearbySelectCoffeeBarView nearbySelectCoffeeBarView =
        allowLocationServicesPopupChunk.allowIfRequestDisplayed();
    FindACoffeeBarView findACoffeeBarView = nearbySelectCoffeeBarView.openRecentTab();

    logger.info(
        "VERIFY - Coffeebars where user recently placed orders appear vertically below recents tab");
    CoffeeStoreContainerChuck recent = findACoffeeBarView.getCoffeeStoreContainerChuck();
    Assert.assertNotNull(
        recent,
        " Coffeebars where user recently placed orders does not appear vertically below recents tab");

    logger.info("VERIFY - Coffeebar location Details:");
    logger.info("VERIFY - Store name");
    Assert.assertTrue(
        recent.isCoffeebarStoreNameDisplayed("AppInt Sandbox 1"), "Store name does not displayed");

    logger.info("VERIFY - x.x Miles away on the right of the store name");
    Assert.assertTrue(recent.isCoffeebarDistanceDisplayed(), "Distance does not displayed");

    logger.info("VERIFY - Store Address");
    Assert.assertTrue(
        recent.isCoffeebarLocationDisplayed("1400 Park Avenue\nEmeryville, CA 94608"),
        "Address does not displayed");

    logger.info("VERIFY - Open Untill xx.xx AM /Pm");
    Assert.assertTrue(recent.isCoffeebarOpenHoursDisplayed(), "Open hours does not displayed");

    logger.info("VERIFY - [Button] Order");
    Assert.assertTrue(recent.isCoffeebarOrderButtonDisplayed(), "Order button does not displayed");

    logger.info("STEP - Tap Order");
    OrderView order = recent.clickOrderButton();

    logger.info("VERIFY - User will navigate to main order screen");
    Assert.assertNotNull(order, "Order vies does not displayed");

    logger.info("VERIFY - Make sure coffeebar name is correct on location field of order screen");
    Assert.assertEquals(
        order.getStoreName().toUpperCase(),
        "AppInt Sandbox 1".toUpperCase(),
        "Wrong storename displayed");
  }
}
