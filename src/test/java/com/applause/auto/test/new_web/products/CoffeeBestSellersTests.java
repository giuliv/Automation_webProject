package com.applause.auto.test.new_web.products;

import com.applause.auto.common.data.Constants.MyAccountTestData;
import com.applause.auto.common.data.Constants.TestData;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.common.data.enums.Filters;
import com.applause.auto.new_web.components.plp.PlpItemComponent;
import com.applause.auto.new_web.views.ProductListPage;
import com.applause.auto.test.new_web.BaseTest;
import java.util.Arrays;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CoffeeBestSellersTests extends BaseTest {

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.FIND_STORE, TestNGGroups.SMOKE},
      description = "")
  public void coffeeBestSellersTest() {
    ProductsTestHelper.checkPlp(navigateToPLP(TestData.COFFEE_BEST_SELLERS_URL), softAssert);
  }

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.FIND_STORE, TestNGGroups.SMOKE},
      description = "")
  public void coffeeBestSellersBannerTest() {
    ProductsTestHelper.checkBestSellersBanner(
        navigateToPLP(TestData.COFFEE_BEST_SELLERS_URL),
        TestData.COFFEE_BEST_SELLERS_HEADER,
        softAssert);
  }

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.FIND_STORE, TestNGGroups.SMOKE},
      description = "",
      enabled = false)
  public void coffeeBestSellersFiltersTest() {
    ProductsTestHelper.checkFilters(
        navigateToPLP(TestData.COFFEE_BEST_SELLERS_URL),
        Arrays.asList(
            Filters.ROAST,
            Filters.BREWING_METHOD,
            Filters.CAFFEINE,
            Filters.FLAVOR,
            Filters.REGION,
            Filters.FEATURED),
        softAssert);
  }

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.FIND_STORE, TestNGGroups.SMOKE},
      description = "")
  public void coffeeBestSellersSortingTest() {
    ProductsTestHelper.checkSortingOptions(
        navigateToPLP(TestData.COFFEE_BEST_SELLERS_URL), softAssert);
  }

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.FIND_STORE, TestNGGroups.SMOKE},
      description = "")
  public void coffeeBestSellersNewsletterSignUpNeverMissOfferTest() {
    ProductsTestHelper.checkNewsletterSignUpNeverMissOffer(
        navigateToPLP(TestData.COFFEE_BEST_SELLERS_URL), softAssert);
  }

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.FIND_STORE, TestNGGroups.SMOKE},
      description = "",
      enabled = false)
  public void coffeeBestSellersItemsTest() {
    logger.info("1. Navigate to Product list page");
    ProductListPage productListPage = navigateToPLP();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info(
        "2. Validate Product tile should be customizable in the CMS and is shown correctly on the page");
    Assert.assertEquals(
        productListPage.getPageHeader(),
        MyAccountTestData.ALL_COFFEE_HEADER,
        "All Coffee header isn't displayed");

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
  public void coffeeBestSellersJoinClubTest() {
    logger.info("1. Navigate to Product list page");
    ProductListPage productListPage = navigateToPLP();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    // TODO there is not Join the Club section
  }
}
