package com.applause.auto.test.web.plp;

import com.applause.auto.common.data.Constants;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.web.views.ProductListPage;
import com.applause.auto.test.web.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SortingTest extends BaseTest {

  @Test(
      groups = {Constants.TestNGGroups.PLP},
      description = "11101528",
      enabled = false)
  public void sortingByPriceTest() {

    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLPFromHome();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Validate High To Low Sorting option");
    productListPage = productListPage.selectSortingByType(Constants.SortType.PRICE_HIGH_TO_LOW);
    Assert.assertTrue(
        productListPage.validateSortingOptionResults(Constants.SortType.PRICE_HIGH_TO_LOW),
        "High to Low Sorting is NOT working");

    logger.info("3. Validate Low To High Sorting option");
    productListPage = productListPage.selectSortingByType(Constants.SortType.PRICE_LOW_TO_HIGH);
    Assert.assertTrue(
        productListPage.validateSortingOptionResults(Constants.SortType.PRICE_LOW_TO_HIGH),
        "Low To High Sorting is NOT working");

    logger.info("FINISH");
  }

  @Test(
      groups = {Constants.TestNGGroups.PLP, Constants.TestNGGroups.SANITY},
      description = "11101529",
      enabled = false)
  public void filterAndSortingAppliedTest() {

    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLPFromHome();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Apply Filter");
    int originResults = productListPage.getTotalResults();
    productListPage.applyFilterByIndex(0, 4);
    productListPage = SdkHelper.create(ProductListPage.class);

    logger.info("3. Validating...");
    Assert.assertTrue(
        originResults > productListPage.getTotalResults(),
        "Total results are not correct after filter");

    logger.info("4. Validate High To Low Sorting option");
    productListPage = productListPage.selectSortingByType(Constants.SortType.PRICE_HIGH_TO_LOW);
    Assert.assertTrue(
        productListPage.validateSortingOptionResults(Constants.SortType.PRICE_HIGH_TO_LOW),
        "High to Low Sorting is NOT working");

    logger.info("FINISH");
  }
}
