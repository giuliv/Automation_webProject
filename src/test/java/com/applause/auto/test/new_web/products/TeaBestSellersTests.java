package com.applause.auto.test.new_web.products;

import com.applause.auto.common.data.Constants.TestData;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.new_web.components.plp.PlpItemComponent;
import com.applause.auto.new_web.views.ProductListPage;
import com.applause.auto.test.new_web.BaseTest;
import java.util.Collections;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TeaBestSellersTests extends BaseTest {

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.FIND_STORE, TestNGGroups.SMOKE},
      description = "")
  public void teaBestSellersTest() {
    ProductsTestHelper.checkPlp(navigateToPLP(TestData.TEA_BEST_SELLERS_URL), softAssert);
  }

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.FIND_STORE, TestNGGroups.SMOKE},
      description = "")
  public void teaBestSellersBannerTest() {
    ProductsTestHelper.checkBestSellersBanner(
        navigateToPLP(TestData.TEA_BEST_SELLERS_URL), TestData.TEA_BEST_SELLERS_HEADER, softAssert);
  }

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.FIND_STORE, TestNGGroups.SMOKE},
      description = "")
  public void teaBestSellersSortingTest() {
    ProductsTestHelper.checkSortingOptions(
        navigateToPLP(TestData.TEA_BEST_SELLERS_URL), softAssert);
  }

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.FIND_STORE, TestNGGroups.SMOKE},
      description = "")
  public void teaBestSellersNewsletterSignUpNeverMissOfferTest() {
    ProductsTestHelper.checkNewsletterSignUpNeverMissOffer(
        navigateToPLP(TestData.TEA_BEST_SELLERS_URL), softAssert);
  }

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.FIND_STORE, TestNGGroups.SMOKE},
      description = "",
      enabled = false)
  public void teaBestSellersItemsTest() {
    logger.info("1. Navigate to Product list page");
    ProductListPage productListPage = navigateToPLP();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info(
        "2. Validate Product tile should be customizable in the CMS and is shown correctly on the page");
    Assert.assertEquals(
        productListPage.getPageHeader(), TestData.ALL_TEA_HEADER, "All Tea header isn't displayed");

    logger.info("3. Validate item image, name, description, price and reviews are shown correctly");
    for (PlpItemComponent item : productListPage.productsList()) {
      softAssert.assertTrue(item.isNameDisplayed(), "Product name isn't displayed");
      String productName = item.getProductName();
      softAssert.assertTrue(
          item.isPriceDisplayed(),
          String.format("Product price isn't displayed for product [{}]", productName));
      softAssert.assertTrue(
          item.isImageDisplayed(),
          String.format("Product image isn't displayed for product [{}]", productName));
    }

    // TODO The items on the PLP are displayed with Quick View button instead of Add to cart button
  }

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.FIND_STORE, TestNGGroups.SMOKE},
      description = "",
      enabled = false)
  public void teaBestSellersJoinClubTest() {
    logger.info("1. Navigate to Product list page");
    ProductListPage productListPage = navigateToPLP();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    // TODO there is not Join the Club section
  }

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.FIND_STORE, TestNGGroups.SMOKE},
      description = "",
      enabled = false)
  public void teaBestSellersFiltersTest() {
    ProductsTestHelper.checkFilters(
        navigateToPLP(TestData.TEA_BEST_SELLERS_URL), Collections.emptyList(), softAssert);
  }
}
