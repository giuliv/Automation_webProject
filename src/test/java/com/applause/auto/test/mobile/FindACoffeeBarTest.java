package com.applause.auto.test.mobile;

import com.applause.auto.common.data.Constants.MyAccountTestData;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.mobile.components.AllowLocationServicesPopupChunk;
import com.applause.auto.mobile.components.CoffeeStoreContainerChuck;
import com.applause.auto.mobile.views.FindACoffeeBarView;
import com.applause.auto.mobile.views.HomeView;
import com.applause.auto.mobile.views.NearbySelectCoffeeBarView;
import com.applause.auto.mobile.views.OrderView;
import com.applause.auto.mobile.views.StoreDetailsView;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class FindACoffeeBarTest extends BaseTest {

  @Test(
      groups = {TestNGGroups.REGRESSION},
      description = "1687260")
  public void findACoffeeBarTest() {
    HomeView homeView =
        testHelper.skipOnboardingAndLogin(MyAccountTestData.EMAIL, MyAccountTestData.PASSWORD);
    Assert.assertNotNull(homeView, "Home view is not displayed");

    logger.info("STEP - Tap Menu");
    AllowLocationServicesPopupChunk allowLocationServicesPopupChunk =
        homeView
            .getBottomNavigationMenuChunk()
            .tapMenu(AllowLocationServicesPopupChunk.class)
            .allowIfRequestDisplayed(OrderView.class)
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

    if (storeDetails.isCoffeebarFavorite()) {
      logger.info("Add store to favorites");
      storeDetails.tapFavorite();
    }

    logger.info("Add store to favorites");
    storeDetails.tapFavorite();

    logger.info(
        "VERIFY - Heart icon for user to mark favorite store at top right corner (if it's marked favorite, the heart is filled in red; if it's not marked favorite, the heart is outlined in gold)");
    Assert.assertTrue(
        storeDetails.isCoffeebarFavorite(),
        "Heart icon for user to mark favorite store does not displayed");

    logger.info("Add store to favorites again");
    storeDetails.tapFavorite();

    logger.info("VERIFY - Heart icon is disabled");
    Assert.assertFalse(storeDetails.isCoffeebarFavorite(), "Heart icon isn't disabled");

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

    logger.info("VERIFY - [Button] Directions");
    Assert.assertTrue(
        storeDetails.isDirectionsButtonDisplayed(), "[Button] Directions does not displayed");

    logger.info("VERIFY - [Button] Order");
    Assert.assertTrue(storeDetails.isOrderButtonDisplayed(), "[Button] Order does not displayed");

    logger.info("STEP - Tap Order button");
    OrderView orderView = storeDetails.order();

    logger.info("VERIFY - User will navigate to main order screen");
    Assert.assertEquals(
        orderView.getHeadingTextValue().toLowerCase().toUpperCase(),
        "ORDER",
        "User does not navigate to main order screen");

    logger.info("STEP - Tap back arrow");
    homeView =
        orderView
            .back(HomeView.class)
            .getSwipeTooltipComponent()
            .closeAnyTooltipIfDisplayed(2, HomeView.class);

    logger.info("STEP - Tap Menu again");
    orderView = homeView.getBottomNavigationMenuChunk().tapMenu(OrderView.class);

    logger.info("STEP - Tap on Recents tab");
    orderView.getOrderMenuChunk().tapOnRecents();

    logger.info("VERIFY - Recents tab is highlighted");
    Assert.assertTrue(
        orderView.getOrderMenuChunk().isRecentsHighlighted(), "Recents tab isn't highlighted");

    logger.info("STEP - Tap on Favorites tab");
    orderView.getOrderMenuChunk().tapOnFavorites();

    logger.info("VERIFY - Favorites tab is highlighted");
    Assert.assertTrue(
        orderView.getOrderMenuChunk().isFavoritesHighlighted(), "Favorites tab isn't highlighted");
  }

  @Test(
      groups = {TestNGGroups.REGRESSION},
      description = "1687261")
  public void recentCoffeeBarTest() {
    HomeView homeView =
        testHelper.skipOnboardingAndLogin(MyAccountTestData.EMAIL, MyAccountTestData.PASSWORD);

    logger.info("STEP - Tap on Recents tab");
    AllowLocationServicesPopupChunk allowLocationServicesPopupChunk =
        homeView
            .getBottomNavigationMenuChunk()
            .tapMenu(AllowLocationServicesPopupChunk.class)
            .allowIfRequestDisplayed(OrderView.class)
            .locateCoffeebars(AllowLocationServicesPopupChunk.class);
    NearbySelectCoffeeBarView nearbySelectCoffeeBarView =
        allowLocationServicesPopupChunk.allowIfRequestDisplayed();

    String storeName;
    String storeAddress;
    storeName = "Lab 3979";
    storeAddress = "1402 Park Avenue\nEmeryville, CA 94608";

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
        recent.isCoffeebarStoreNameDisplayed(storeName), "Store name does not displayed");

    logger.info("VERIFY - Store Address");
    Assert.assertTrue(
        recent.isCoffeebarLocationDisplayed(storeAddress), "Address does not displayed");

    logger.info("VERIFY - Open Untill xx.xx AM /Pm");
    Assert.assertTrue(recent.isCoffeebarOpenHoursDisplayed(), "Open hours does not displayed");

    logger.info("VERIFY - [Button] Order");
    Assert.assertTrue(recent.isCoffeebarOrderButtonDisplayed(), "Order button does not displayed");

    // storeName = "AppInt Sandbox 2";
    logger.info("STEP - Tap Order");
    OrderView order;
    if (SdkHelper.getEnvironmentHelper().isAndroid()) {
      order = recent.clickOrderButton(storeName);
    } else {
      order = recent.clickOrderButton();
    }

    logger.info("VERIFY - User will navigate to main order screen");
    Assert.assertNotNull(order, "Order view does not displayed");

    logger.info("VERIFY - Make sure coffee bar name is correct on location field of order screen");
    Assert.assertEquals(
        order.getStoreName().toUpperCase(), storeName.toUpperCase(), "Wrong store name displayed");
  }

  @Test(
      groups = {TestNGGroups.REGRESSION},
      description = "1687262")
  public void favoriteCoffeeBarsTest() {
    logger.info("PRECONDITION - User is on find a coffeeBar screen");
    logger.info("Launch the app and arrive at the first on boarding screen view");
    HomeView homeView =
        testHelper.skipOnboardingAndLogin(MyAccountTestData.EMAIL, MyAccountTestData.PASSWORD);
    Assert.assertNotNull(homeView, "Home view is not displayed");

    AllowLocationServicesPopupChunk allowLocationServicesPopupChunk =
        homeView
            .getBottomNavigationMenuChunk()
            .tapMenu(AllowLocationServicesPopupChunk.class)
            .allowIfRequestDisplayed(OrderView.class)
            .locateCoffeebars(AllowLocationServicesPopupChunk.class);
    NearbySelectCoffeeBarView nearbySelectCoffeeBarView =
        allowLocationServicesPopupChunk.allowIfRequestDisplayed();

    logger.info("STEP - Search for any store either by nearby, recent tabs, or by zip code");
    nearbySelectCoffeeBarView.search("94538");

    logger.info("STEP - Tap on the store location card to view store details screen");
    CoffeeStoreContainerChuck coffeeStore =
        nearbySelectCoffeeBarView.getCoffeeStoreContainerChuck();
    StoreDetailsView storeDetailsView = coffeeStore.openStoreDetails();
    String storeName = storeDetailsView.getCoffeebarSubHeaderName();

    if (storeDetailsView.isCoffeebarFavorite()) {
      logger.info("PRECONDITION INIT");
      storeDetailsView.tapFavorite();
    }

    logger.info("STEP - Tap on the gold outline heart icon to the right of the coffeeBar name");
    storeDetailsView.tapFavorite();

    logger.info(
        "VERIFY - User sees a loading dial and then the gold heart icon changes to a red filled in heart icon");
    logger.info("VERIFY - The brown pin on the map also changes to a red and white heart pin");
    Assert.assertTrue(
        storeDetailsView.isCoffeebarFavorite(), "Favorite was not enabled for coffee store");

    logger.info("STEP - Tap on back arrow to return to find a coffeeBar screen");
    nearbySelectCoffeeBarView = storeDetailsView.navigateBack(NearbySelectCoffeeBarView.class);
    nearbySelectCoffeeBarView.cancelSearch();

    logger.info("STEP - Tap on Favorites tab");
    FindACoffeeBarView findACoffeeBarView = nearbySelectCoffeeBarView.openFavoritesTab();

    logger.info(
        "VERIFY - The coffeeBar that was favorited in step 3 should appear in the list of favorite stores");
    CoffeeStoreContainerChuck favStore = findACoffeeBarView.getCoffeeStoreContainerChuck();

    String currentName = favStore.getStoreName();
    List<String> storeNames = favStore.getStoreNameList();

    Assert.assertTrue(
        storeNames.contains(storeName),
        "Wrong store shown under favorites tab: Expected: "
            + storeName
            + " but found: "
            + currentName);

    SoftAssert softAssert = new SoftAssert();

    logger.info("STEP - Select the same store just favorited to view store details screen");
    if (SdkHelper.getEnvironmentHelper().isAndroid()) {
      storeDetailsView = favStore.openStoreDetails(storeName);

    } else {
      storeDetailsView = favStore.openStoreDetails();
    }

    logger.info("VERIFY - Heart icon should still be filled in red since it's been favorited");
    softAssert.assertTrue(
        storeDetailsView.isCoffeebarFavorite(), "Favorite was not enabled for coffee store");

    logger.info("STEP - Tap on the red filled in heart icon next to the coffeeBar name");
    storeDetailsView.tapFavorite();

    logger.info(
        "VERIFY - User sees a loading dial and then the red heart icon changes to the gold outline heart icon");
    logger.info("VERIFY - The red and white heart icon pin on the map changes to a brown pin");
    softAssert.assertFalse(
        storeDetailsView.isCoffeebarFavorite(), "Favorite was not disabled for coffee store");

    logger.info("STEP - Tap on back arrow to return to find a coffeeBar screen");
    findACoffeeBarView = storeDetailsView.navigateBack(FindACoffeeBarView.class);

    logger.info(
        "VERIFY - The coffeeBar that was un-favorited in step 7 should no longer appear in the list of favorite stores");
    softAssert.assertFalse(
        findACoffeeBarView.getCoffeeStoreContainerChuck().isStorePresent()
            && favStore.getStoreName().equals(storeName),
        "The coffeeBar that was un-favorited in step 7 still remains in the favorite stores");

    softAssert.assertAll();
  }
}
