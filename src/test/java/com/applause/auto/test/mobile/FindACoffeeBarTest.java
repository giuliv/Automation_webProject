package com.applause.auto.test.mobile;

import com.applause.auto.common.data.Constants.MyAccountTestData;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.mobile.components.AllowLocationServicesPopupChunk;
import com.applause.auto.mobile.components.CoffeeStoreContainerChuck;
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
    NearbySelectCoffeeBarView nearbySelectCoffeeBarView = allowLocationServicesPopupChunk.allow();
    FindACoffeeBarView findACoffeeBarView = nearbySelectCoffeeBarView.openRecentTab();

    logger.info("VERIFY - User sees store location cards");
    CoffeeStoreContainerChuck store = findACoffeeBarView.getCoffeeStoreContainerChuck();
    Assert.assertTrue(store.isStorePresent(), "User does not sees store location cards");

    logger.info("STEP - Tap on a store location card to view store details");
    StoreDetailsView storeDetails = store.openStoreDetails();

    logger.info("VERIFY - User will taken to store details screen");
    Assert.assertNotNull(storeDetails, "User does not taken to store details screen");

    logger.info("VERIFY - Arrow on the left corner to navigate back to the previous screen");
    storeDetails.isNavigateBackDisplayed();

    logger.info("VERIFY - Header: [COFFEEBAR NAME]");
    storeDetails.getCoffeebarHeader();

    logger.info("VERIFY - Map view of coffeebar and a brown pin indicating location on the map");
    storeDetails.isCoffeebarPinDisplayedOnMap();

    logger.info("VERIFY - Sub header: [Coffeebar Name]");
    storeDetails.getCoffeebarSubHeaderName();

    logger.info(
        "VERIFY - Heart icon for user to mark favorite store at top right corner (if it's marked favorite, the heart is filled in red; if it's not marked favorite, the heart is outlined in gold)");
    storeDetails.isCoffeebarFavorite();

    logger.info("VERIFY - Address of the coffeebar and distance [x.x miles away]");
    storeDetails.getCoffeebarLocation();

    logger.info("VERIFY - Sub header: HOURS");
    storeDetails.isCoffeebarSubHeaderHoursDisplayed();

    logger.info("VERIFY - Open until x:xx PM");
    storeDetails.isCoffeebarOpenHoursDisplayed();

    logger.info("VERIFY - Table with columns: Day and Store Hours (or Closed if applicable)");
    storeDetails.isCoffeebarDayAndStoreHoursDisplayed();

    logger.info("VERIFY - Current day is highlighted in gold");

    logger.info("VERIFY - [Button] Directions");
    storeDetails.isDirectionsButtonDisplayed();

    logger.info("VERIFY - [Button] Order");
    storeDetails.isOrderButtonDisplayed();

    logger.info("STEP - Tap Directions button");
    storeDetails.directions();

    logger.info(
        "VERIFY - User is taken to device's native map app with directions to coffeebar from user's current location maps");
    logger.info("STEP - Tap on \"Peet's\" at the top left corner to return to Peet's app");
    logger.info("VERIFY - User is taken back to store details screen");
    logger.info("STEP - Tap Order button");
    logger.info("VERIFY - User will navigate to main order screen");
    logger.info("VERIFY - Make sure coffeebar name is correct on location field of order screen");
  }
}
