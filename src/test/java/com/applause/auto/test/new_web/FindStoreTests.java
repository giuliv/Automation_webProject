package com.applause.auto.test.new_web;

import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.common.data.dto.StoreDetailsDto;
import com.applause.auto.new_web.components.StockResultFilterComponent;
import com.applause.auto.new_web.components.StockResultItemComponent;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.new_web.views.GoogleMapPage;
import com.applause.auto.new_web.views.ProductListPage;
import com.applause.auto.new_web.views.StoreDetailsPage;
import com.applause.auto.new_web.views.StoreLocatorPage;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FindStoreTests extends BaseTest {

  @Test(
      groups = {TestNGGroups.NEW_WEB_CASES, TestNGGroups.FIND_STORE},
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
      groups = {TestNGGroups.NEW_WEB_CASES, TestNGGroups.FIND_STORE},
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
      groups = {TestNGGroups.NEW_WEB_CASES, TestNGGroups.FIND_STORE},
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
      groups = {TestNGGroups.NEW_WEB_CASES, TestNGGroups.FIND_STORE},
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
}
