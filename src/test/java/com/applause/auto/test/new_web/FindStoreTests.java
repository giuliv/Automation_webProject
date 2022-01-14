package com.applause.auto.test.new_web;

import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.common.data.Constants.WebTestData;
import com.applause.auto.common.data.dto.StoreDetailsDto;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.new_web.components.StockResultFilterComponent;
import com.applause.auto.new_web.components.StockResultItemComponent;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.new_web.views.GoogleMapPage;
import com.applause.auto.new_web.views.MenuPage;
import com.applause.auto.new_web.views.ProductListPage;
import com.applause.auto.new_web.views.StoreDetailsPage;
import com.applause.auto.new_web.views.StoreLocatorPage;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FindStoreTests extends BaseTest {

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.FIND_STORE, TestNGGroups.SMOKE},
      description = "11102898")
  public void validateResultsListUiAndLoadMore() {
    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLP();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Click on location from header.");
    StoreLocatorPage storeLocatorPage = productListPage.getHeader().clickOnStoreLocator();

    logger.info("3. Type any zip code (95032)");
    storeLocatorPage = storeLocatorPage.searchByZipCode("95032");

    logger.info("4. Click on load more");
    List<StockResultItemComponent> stockResultListBefore = storeLocatorPage.getStockResultList();
    storeLocatorPage = storeLocatorPage.loadMoreResults();
    List<StockResultItemComponent> stockResultListAfter = storeLocatorPage.getStockResultList();

    logger.info("Verify that more stores should display.");
    Assert.assertTrue(
        stockResultListAfter.size() > stockResultListBefore.size(), "More Stores isn't displayed");

    logger.info("Verify that Search field is displayed");
    Assert.assertTrue(
        storeLocatorPage.isSearchByZipCodeFieldDisplayed(), "Search field isn't displayed");

    logger.info("Verify that Search button is displayed");
    Assert.assertTrue(storeLocatorPage.isSearchButtonDisplayed(), "Search button isn't displayed");

    logger.info("Verify that 'Filter' button is displayed");
    Assert.assertTrue(storeLocatorPage.isFilterButtonDisplayed(), "Filter button isn't displayed");

    logger.info("Verify that 'Filter' button is displayed");
    Assert.assertTrue(storeLocatorPage.isFilterButtonDisplayed(), "Filter button isn't displayed");

    if (WebHelper.isDesktop()) {
      logger.info("Verify that Filter title is correct");
      Assert.assertEquals(
          storeLocatorPage.getFilterTitle().toLowerCase(),
          "FILTER BY AMENITIES".toLowerCase(),
          "Filter button isn't displayed");
    }

    logger.info("Verify that Results (n) quantity matches with list.");
    int countOfResults = storeLocatorPage.getCountOfItemsFromResultTitle();
    Assert.assertTrue(
        countOfResults >= stockResultListAfter.size(),
        String.format(
            "Results (n) quantity matches with list. Expected [%s] >= [%s]",
            countOfResults, stockResultListAfter.size()));

    logger.info(
        "Verify that Stores should display with no., name, store, address schedule and start order button.");
    Assert.assertTrue(
        storeLocatorPage.checkIfAllStoresFullyDisplayed(), "Not all Stores are fully displayed");
  }

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.FIND_STORE},
      description = "11102899")
  public void validateFilterTest() {
    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLP();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Click on location from header.");
    StoreLocatorPage storeLocatorPage = productListPage.getHeader().clickOnStoreLocator();

    logger.info("3. Type any zip code (95032)");
    storeLocatorPage = storeLocatorPage.searchByZipCode("95032");

    logger.info("4. Click on filter by amenities.");
    List<String> stockResultListBefore = storeLocatorPage.getListOfStoreNames();
    StockResultFilterComponent stockResultFilterComponent = storeLocatorPage.openFilters();

    logger.info("5. Select any filter.");
    storeLocatorPage = stockResultFilterComponent.selectFiltersOnPosition(1, 2).close();

    logger.info("Verify that new results appear");
    Assert.assertNotEquals(
        storeLocatorPage.getListOfStoreNames(),
        stockResultListBefore,
        "New results didn't appear after filtering");
  }

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.FIND_STORE, TestNGGroups.SANITY},
      description = "11102900")
  public void validateStoreDetails() {
    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLP();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Click on location from header.");
    StoreLocatorPage storeLocatorPage = productListPage.getHeader().clickOnStoreLocator();

    logger.info("3. Type any zip code (95032)");
    storeLocatorPage = storeLocatorPage.searchByZipCode("95032");

    logger.info("4. Click on a store.");
    StockResultItemComponent stockResultItemComponent =
        storeLocatorPage.getStockResultList().get(0);
    StoreDetailsDto storeDetailsDto = stockResultItemComponent.getStoreDetailsDto();
    StoreDetailsPage storeDetailsPage = stockResultItemComponent.click();

    logger.info("Verify that store type, name and address should match.");
    Assert.assertEquals(
        storeDetailsPage.getType(), storeDetailsDto.getType(), "Store type isn't correct");
    Assert.assertEquals(
        storeDetailsPage.getName(), storeDetailsDto.getName(), "Store Name isn't correct");
    Assert.assertTrue(
        StringUtils.containsIgnoreCase(storeDetailsPage.getAddress(), storeDetailsDto.getAddress()),
        String.format(
            "Store Address isn't. Expected [%s] but found [%s]",
            storeDetailsDto.getAddress(), storeDetailsPage.getAddress()));

    logger.info(
        "Verify that Back to results, start order and get directions buttons should display.");
    Assert.assertTrue(storeDetailsPage.isBackButtonDisplayed(), "'Back' button isn't displayed");
    Assert.assertTrue(
        storeDetailsPage.isStartOrderButtonDisplayed(), "'Start Order' button isn't displayed");
    Assert.assertTrue(
        storeDetailsPage.isGetDirectionsButtonDisplayed(),
        "'Get Directions' button isn't displayed");

    logger.info("Verify that Schedule and amenities should display.");
    Assert.assertEquals(
        storeDetailsPage.getSchedule().replaceAll(":00", "").toLowerCase(),
        storeDetailsDto.getSchedule().toLowerCase(),
        "Store Schedule isn't correct");
    Assert.assertTrue(storeDetailsPage.isAmenitiesButtonDisplayed(), "'Amenities' isn't displayed");
  }

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.FIND_STORE},
      description = "11102901")
  public void validateGetDirections() {
    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLP();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Click on location from header.");
    StoreLocatorPage storeLocatorPage = productListPage.getHeader().clickOnStoreLocator();

    logger.info("3. Type any zip code (95032)");
    storeLocatorPage = storeLocatorPage.searchByZipCode("95032");

    logger.info("4. Click on a store.");
    StoreDetailsPage storeDetailsPage = storeLocatorPage.getStockResultList().get(0).click();

    logger.info("5. Click on Get directions.");
    GoogleMapPage googleMapPage = storeDetailsPage.clickOnGetDirectionsButton();

    logger.info("Verify that map should display.");
    Assert.assertTrue(googleMapPage.isDisplayed(), "Map isn't display");
  }

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.FIND_STORE},
      description = "11102902")
  public void validateStartOrder() {
    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLP();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Click on location from header.");
    StoreLocatorPage storeLocatorPage = productListPage.getHeader().clickOnStoreLocator();

    logger.info("3. Type any zip code (95032)");
    storeLocatorPage = storeLocatorPage.searchByZipCode("95032");

    logger.info("4. Click on Start order");
    String currentWindow = SdkHelper.getDriver().getWindowHandle();
    MenuPage menuPage = storeLocatorPage.getStockResultList().get(0).startOrder();

    logger.info("Verify that menu page should open.");
    Assert.assertTrue(menuPage.isDisplayed(), "Menu page wasn't display");

    logger.info("5. Go back to find store.");
    SdkHelper.getDriver().close();
    SdkHelper.getDriver().switchTo().window(currentWindow);

    logger.info("6. Click on a store.");
    StoreDetailsPage storeDetailsPage = storeLocatorPage.getStockResultList().get(0).click();

    logger.info("7. Click on Start order");
    menuPage = storeDetailsPage.startOrder();

    logger.info("Verify that menu page should open.");
    Assert.assertTrue(menuPage.isDisplayed(), "Menu page wasn't display");
  }

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.FIND_STORE},
      description = "11102903")
  public void backToResults() {
    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLP();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Click on location from header.");
    StoreLocatorPage storeLocatorPage = productListPage.getHeader().clickOnStoreLocator();

    logger.info("3. Type any zip code (95032)");
    storeLocatorPage = storeLocatorPage.searchByZipCode("95032");

    logger.info("4. Click on a store.");
    List<String> listOfStoreNames = storeLocatorPage.getListOfStoreNames();
    StoreDetailsPage storeDetailsPage = storeLocatorPage.getStockResultList().get(0).click();

    logger.info("5. Click on Back to results.");
    storeLocatorPage = storeDetailsPage.backToResults();

    logger.info("Verify that results list should display.");
    Assert.assertEquals(
        storeLocatorPage.getListOfStoreNames(), listOfStoreNames, "Results list isn't the same");
  }

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.FIND_STORE},
      description = "11102904")
  public void byCurrentLocation() {
    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLP();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Click on location from header.");
    StoreLocatorPage storeLocatorPage = productListPage.getHeader().clickOnStoreLocator();

    logger.info("3. Click on use my current location");
    storeLocatorPage = storeLocatorPage.clickUseMyCurrentLocation();

    logger.info("Verify that closest store should display.");
    Assert.assertTrue(storeLocatorPage.getListOfStoreNames().size() > 0, "Stores didn't appear");
  }

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.FIND_STORE},
      description = "11102905")
  public void zoomMapTest() {
    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLP();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Click on location from header.");
    StoreLocatorPage storeLocatorPage = productListPage.getHeader().clickOnStoreLocator();

    logger.info("3. Zoom any part on the map");
    storeLocatorPage = storeLocatorPage.clickOnZoomInButton(2);

    logger.info("Verify that list of stores within the map should display.");
    Assert.assertTrue(storeLocatorPage.getListOfStoreNames().size() > 0, "Stores didn't appear");
  }

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.FIND_STORE},
      description = "11102906")
  public void thereAreNoStoresNearby() {
    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLP();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Click on location from header.");
    StoreLocatorPage storeLocatorPage = productListPage.getHeader().clickOnStoreLocator();

    logger.info("3. Type any country without stores");
    storeLocatorPage = storeLocatorPage.searchByZipCode("France");

    logger.info("4. Zoom any part on the map");
    storeLocatorPage = storeLocatorPage.clickOnZoomInButton(3);

    logger.info("Verify that correct message and link is displayed");
    Assert.assertEquals(
        storeLocatorPage.getCountOfItemsFromResultTitle(),
        0,
        "Count of results from the title is wrong");
    Assert.assertEquals(
        storeLocatorPage.getResultMessage(),
        WebTestData.STORES_NO_RESULTS_MESSAGE,
        "'No results' message is wrong");
    Assert.assertTrue(storeLocatorPage.isShopOnlineDisplayed(), "'Shop Online' isn't displayed");
  }
}
