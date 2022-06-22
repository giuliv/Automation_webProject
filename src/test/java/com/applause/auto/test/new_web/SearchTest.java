package com.applause.auto.test.new_web;

import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.common.data.Constants.WebTestData;
import com.applause.auto.new_web.components.Header;
import com.applause.auto.new_web.components.SearchBoxItemComponent;
import com.applause.auto.new_web.components.SearchComponent;
import com.applause.auto.new_web.views.HomePage;
import com.applause.auto.new_web.views.ProductDetailsPage;
import com.applause.auto.new_web.views.SearchResultsPage;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SearchTest extends BaseTest {

  private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().getClass());

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.SEARCH, TestNGGroups.SMOKE},
      description = "11102893")
  public void searchWithAutocompleteAndCloseTest() {
    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");

    logger.info("2. Click on magnifying glass from header.");
    Header header = homePage.getHeader();
    SearchComponent searchComponent = header.getSearchComponent();
    searchComponent.enterSearchTerm(WebTestData.SEARCH_COFFEE_AGED_SUMATRA);
    Assert.assertTrue(searchComponent.resultIsDisplayed(), "Result is not displayed");

    logger.info("3. Click on close (X)");
    searchComponent.closeSearch();
    Assert.assertFalse(header.searchComponentIsOpened(), "Search component isn't closed");
  }

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.SEARCH, TestNGGroups.SANITY},
      description = "11102894")
  public void searchResultsTest() {
    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");

    logger.info("2. Click on magnifying glass from header.");
    SearchResultsPage searchResultsPage =
        homePage.getHeader().getSearchComponent().search(WebTestData.SEARCH_COFFEE_AGED_SUMATRA);
    Assert.assertNotNull(searchResultsPage, "Search result page isn't displayed");
    Assert.assertTrue(searchResultsPage.isResultListDisplayed(), "result list isn't displayed");
  }

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.SEARCH},
      description = "11102895")
  public void validateUiAutocompleteOptionsTest() {
    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");

    logger.info("2. Click on magnifying glass from header.");
    Header header = homePage.getHeader();
    SearchComponent searchComponent = header.getSearchComponent();
    searchComponent.enterSearchTerm(WebTestData.SEARCH_COFFEE_AGED_SUMATRA);
    Assert.assertTrue(
        searchComponent.areAllSearchBoxItemsDisplayedCorrectly(),
        "Autocomplete result isn't displayed properly");
  }

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.SEARCH},
      description = "11102896")
  public void validateAutocompleteSuggestionTest() {
    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");

    logger.info("2. Click on magnifying glass from header.");
    Header header = homePage.getHeader();
    SearchComponent searchComponent = header.getSearchComponent();
    searchComponent.enterSearchTerm(WebTestData.SEARCH_COFFEE_AGED_SUMATRA);
    SearchBoxItemComponent itemComponent = searchComponent.getSearchBoxItemComponentByIndex(1);
    String productName = itemComponent.getName();
    String productImage = itemComponent.getImagePngName();
    String productPrice = itemComponent.getPrice();
    ProductDetailsPage detailsPage = itemComponent.clickOnItem();

    Assert.assertTrue(
        detailsPage.getProductName().equalsIgnoreCase(productName), "Wrong name is displayed");
    Assert.assertTrue(
        detailsPage.isExpectedImageDisplayed(productImage), "Wrong image is displayed");

    if (productPrice.contains("-")) {
      Assert.assertTrue(
          productPrice.contains(detailsPage.getProductPrice()), "Wrong price is displayed");
    } else {
      Assert.assertEquals(detailsPage.getProductPrice(), productPrice, "Wrong price is displayed");
    }
  }

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.SEARCH},
      description = "11102897")
  public void noSearchResultsTest() {
    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");

    logger.info("2. Click on magnifying glass from header.");
    SearchComponent searchComponent = homePage.getHeader().getSearchComponent();
    SearchResultsPage searchResultsPage = searchComponent.search(WebTestData.NOT_EXIST_SEARCH_TERM);
    Assert.assertTrue(
        searchResultsPage.isSearchResultTitleDisplayed(),
        "The search result header isn't displayed");
    Assert.assertTrue(
        searchResultsPage.isEmptySearchResultMessageDisplayed(WebTestData.NOT_EXIST_SEARCH_TERM),
        "Empty search result message isn't displayed correct");
  }
}
