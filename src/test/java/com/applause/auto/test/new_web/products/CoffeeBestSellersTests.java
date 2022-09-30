package com.applause.auto.test.new_web.products;

import com.applause.auto.common.data.Constants.MyAccountTestData;
import com.applause.auto.common.data.Constants.TestData;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.common.data.enums.Filters;
import com.applause.auto.new_web.components.QuickViewComponent;
import com.applause.auto.new_web.components.plp.PlpItemComponent;
import com.applause.auto.new_web.views.ProductDetailsPage;
import com.applause.auto.new_web.views.ProductListPage;
import com.applause.auto.test.new_web.BaseTest;
import java.util.Arrays;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CoffeeBestSellersTests extends BaseTest {

  @Test(
      groups = {TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.PRODUCTS},
      description = "11107498")
  public void coffeeBestSellersTest() {
    ProductsTestHelper.checkPlp(
        navigateToPLPFromHome(TestData.COFFEE_BEST_SELLERS_URL), softAssert);
  }

  @Test(
      groups = {TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.PRODUCTS},
      description = "11107499")
  public void coffeeBestSellersBannerTest() {
    ProductsTestHelper.checkBestSellersBanner(
        navigateToPLPFromHome(TestData.COFFEE_BEST_SELLERS_URL),
        TestData.COFFEE_BEST_SELLERS_HEADER,
        softAssert);
  }

  @Test(
      groups = {TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.PRODUCTS},
      description = "11107500",
      enabled = false)
  // Todo: Testcase disabled until further notice
  public void coffeeBestSellersFiltersTest() {
    ProductsTestHelper.checkFilters(
        navigateToPLPFromHome(TestData.COFFEE_BEST_SELLERS_URL),
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
      groups = {TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.PRODUCTS},
      description = "11107501")
  public void coffeeBestSellersSortingTest() {
    ProductsTestHelper.checkNewSortingOptions(
        navigateToPLPFromHome(TestData.COFFEE_BEST_SELLERS_URL), softAssert);
  }

  @Test(
      groups = {TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.PRODUCTS},
      description = "11107503")
  public void coffeeBestSellersNewsletterSignUpNeverMissOfferTest() {
    ProductsTestHelper.checkNewsletterSignUpNeverMissOffer(
        navigateToPLPFromHome(TestData.COFFEE_BEST_SELLERS_URL), softAssert);
  }

  @Test(
      groups = {TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.PRODUCTS},
      description = "11107502")
  public void coffeeBestSellersItemsTest() {
    logger.info("1. Navigate to Product list page");
    ProductListPage productListPage = navigateToPLPFromHome(TestData.COFFEE_BEST_SELLERS_URL);
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info(
        "2. Validate Product tile should be customizable in the CMS and is shown correctly on the page");
    Assert.assertEquals(
        productListPage.getPageHeader(),
        MyAccountTestData.BESTSELLER_HEADER,
        "BestSellers header isn't displayed");

    int totalProducts = productListPage.getTotalResults();
    logger.info("3. Validate item image, name, description, price and reviews are shown correctly");
    for (PlpItemComponent item : productListPage.productsList()) {
      softAssert.assertTrue(item.isNameDisplayed(), "Product name isn't displayed");
      String productName = item.getProductName();
      softAssert.assertTrue(
          item.isPriceDisplayed(),
          String.format("Product price isn't displayed for product [{%s}]", productName));
      softAssert.assertTrue(
          item.isImageDisplayed(),
          String.format("Product image isn't displayed for product [{%s}]", productName));
    }

    logger.info("4. At Homepage --> Hover any Coffee item");
    PlpItemComponent itemComponent = productListPage.getProductOnPosition(1);
    softAssert.assertTrue(
        itemComponent.isQuickViewButtonDisplayed(), "Quick view button isn't displayed");

    logger.info("5. Click on 'Quick view' > Review modal > Close modal");
    QuickViewComponent quickViewComponent = itemComponent.clickQuickView();
    Assert.assertNotNull(quickViewComponent, "QuickView modal is not displayed");
    quickViewComponent.closeQuickView();

    logger.info("6. Load More Results");
    if (productListPage.isLoadButtonDisplayed()) {
      productListPage.loadMore();
      Assert.assertTrue(
          totalProducts < productListPage.getTotalResults(), "Load more results does not work");
    }

    softAssert.assertAll();
  }

  @Test(
      groups = {TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.PRODUCTS},
      description = "11107504",
      enabled = false)
  // Todo:Disable by request of Bernadette, leaving only 1 case on allCoffee section[21.06.2022]
  public void coffeeBestSellersOutOfStockTest() {
    logger.info("1. Navigate to Product list page");
    ProductListPage productListPage = navigateToPLPFromHome(TestData.COFFEE_BEST_SELLERS_URL);
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    int firstItem = 0;
    logger.info("2. Review Out of Stock items");
    Assert.assertTrue(
        productListPage.isViewProductDisplayed(firstItem), "View product is not displayed");
    ProductDetailsPage productDetailsPage = productListPage.clickViewProductButton(firstItem);
    Assert.assertNotNull(productDetailsPage, "Product Details page is not displayed");
  }
}
