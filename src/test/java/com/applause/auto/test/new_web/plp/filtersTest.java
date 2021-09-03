package com.applause.auto.test.new_web.plp;

import com.applause.auto.common.data.Constants;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.new_web.views.*;
import com.applause.auto.test.new_web.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class filtersTest extends BaseTest {

  @Test(
      groups = {Constants.TestNGGroups.PLP},
      description = "TBD")
  public void singleFilterTest() {

    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLP();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Apply Filter");
    int originResults = productListPage.getTotalResults();
    String filterName = productListPage.applyFilterByIndex(0, 3);
    productListPage = SdkHelper.create(ProductListPage.class);

    logger.info("3. Validating...");
    Assert.assertEquals(
        filterName,
        productListPage.getFilterAppliedPillByIndex(0),
        "Correct filter was not applied");
    Assert.assertTrue(
        originResults > productListPage.getTotalResults(),
        "Total results are not correct after filter");

    logger.info("FINISH");
  }

  @Test(
      groups = {Constants.TestNGGroups.PLP},
      description = "TBD")
  public void multipleFilterTest() {

    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLP();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Apply Filter number One");
    int originResults = productListPage.getTotalResults();
    String filterName = productListPage.applyFilterByIndex(0, 3);
    productListPage = SdkHelper.create(ProductListPage.class);

    logger.info("3. Validating Filter One...");
    int newResults = productListPage.getTotalResults();
    Assert.assertEquals(
        filterName, productListPage.getFilterAppliedPillByIndex(0), "1st filter was not applied");
    Assert.assertTrue(originResults > newResults, "Total results are not correct after 1st filter");

    logger.info("4. Apply Filter number Two");
    String filterNameTwo = productListPage.applyFilterByIndex(1, 1);
    productListPage = SdkHelper.create(ProductListPage.class);

    logger.info("5. Validating Filter Two...");
    Assert.assertEquals(
        filterNameTwo,
        productListPage.getFilterAppliedPillByIndex(1),
        "2nd filter was not applied");
    Assert.assertTrue(
        newResults > productListPage.getTotalResults(),
        "Total results are not correct after 2nd filter");

    logger.info("FINISH");
  }

  @Test(
      groups = {Constants.TestNGGroups.PLP},
      description = "TBD")
  public void clearAllFiltersTest() {

    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLP();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Apply Filter number");
    int originResults = productListPage.getTotalResults();
    String filterName = productListPage.applyFilterByIndex(7, 1);
    productListPage = SdkHelper.create(ProductListPage.class);

    logger.info("3. Validating Filter...");
    int newResults = productListPage.getTotalResults();
    Assert.assertEquals(
        filterName, productListPage.getFilterAppliedPillByIndex(0), "Filter was not applied");
    Assert.assertTrue(originResults > newResults, "Total results are not correct after filter");
    Assert.assertTrue(productListPage.isFilterPillDisplayed(), "Filter pill is not displayed");

    logger.info("4. Clear All Filters");
    productListPage = productListPage.clearAllFilters();
    Assert.assertFalse(
        productListPage.isFilterPillDisplayed(), "Filter pill should NOT be present");
    Assert.assertEquals(originResults, productListPage.getTotalResults());

    logger.info("FINISH");
  }

  @Test(
      groups = {Constants.TestNGGroups.PLP},
      description = "TBD")
  public void removeFiltersTest() {

    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLP();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Apply Filter number");
    int originResults = productListPage.getTotalResults();
    String filterName = productListPage.applyFilterByIndex(8, 1);
    productListPage = SdkHelper.create(ProductListPage.class);

    logger.info("3. Validating Filter...");
    int newResults = productListPage.getTotalResults();
    Assert.assertEquals(
        filterName, productListPage.getFilterAppliedPillByIndex(0), "Filter was not applied");
    Assert.assertTrue(originResults > newResults, "Total results are not correct after filter");
    Assert.assertTrue(productListPage.isFilterPillDisplayed(), "Filter pill is not displayed");

    logger.info("4. Remove Filter");
    productListPage = productListPage.removeFilters();
    Assert.assertFalse(
        productListPage.isFilterPillDisplayed(), "Filter pill should NOT be present");
    Assert.assertEquals(originResults, productListPage.getTotalResults());

    logger.info("FINISH");
  }
}
