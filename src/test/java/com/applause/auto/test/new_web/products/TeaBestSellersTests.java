package com.applause.auto.test.new_web.products;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.Constants.TestData;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.new_web.components.QuickViewComponent;
import com.applause.auto.new_web.components.plp.PlpItemComponent;
import com.applause.auto.new_web.views.ProductListPage;
import com.applause.auto.test.new_web.BaseTest;
import java.util.Collections;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TeaBestSellersTests extends BaseTest {

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.PRODUCTS},
      description = "11107522")
  public void teaBestSellersTest() {
    ProductsTestHelper.checkPlp(navigateToPLP(TestData.TEA_BEST_SELLERS_URL), softAssert);
  }

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.PRODUCTS},
      description = "11107523")
  public void teaBestSellersBannerTest() {
    ProductsTestHelper.checkBestSellersBanner(
        navigateToPLP(TestData.TEA_BEST_SELLERS_URL), TestData.TEA_BEST_SELLERS_HEADER, softAssert);
  }

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.PRODUCTS},
      description = "11107525")
  public void teaBestSellersSortingTest() {
    ProductsTestHelper.checkSortingOptions(
        navigateToPLP(TestData.TEA_BEST_SELLERS_URL), softAssert);
  }

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.PRODUCTS},
      description = "11107527")
  public void teaBestSellersNewsletterSignUpNeverMissOfferTest() {
    ProductsTestHelper.checkNewsletterSignUpNeverMissOffer(
        navigateToPLP(TestData.TEA_BEST_SELLERS_URL), softAssert);
  }

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.PRODUCTS},
      description = "11107526")
  public void teaBestSellersItemsTest() {
    logger.info("1. Navigate to Product list page");
    ProductListPage productListPage = navigateToPLP(TestData.TEA_BEST_SELLERS_URL);
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info(
        "2. Validate Product tile should be customizable in the CMS and is shown correctly on the page");
    Assert.assertEquals(
        productListPage.getPageHeader(),
        Constants.MyAccountTestData.BESTSELLER_TEA_HEADER,
        "BestSeller Tea header isn't displayed");

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

    logger.info("4. At Homepage --> Hover any Tea item");
    PlpItemComponent itemComponent = productListPage.getProductOnPosition(1);
    softAssert.assertTrue(
        itemComponent.isQuickViewButtonDisplayed(), "Quick view button isn't displayed");

    logger.info("5. Click on 'Quick view' > Review modal > Close modal");
    QuickViewComponent quickViewComponent = itemComponent.clickQuickView();
    Assert.assertNotNull(quickViewComponent, "QuickView modal is not displayed");
    quickViewComponent.closeQuickView();

    logger.info("6. Load More Results");
    productListPage.loadMore();
    Assert.assertTrue(
        totalProducts < productListPage.getTotalResults(), "Load more results does not work");

    softAssert.assertAll();
  }

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.PRODUCTS},
      description = "11107524",
      enabled = false)
  public void teaBestSellersFiltersTest() {
    ProductsTestHelper.checkFilters(
        navigateToPLP(TestData.TEA_BEST_SELLERS_URL), Collections.emptyList(), softAssert);
  }
}
