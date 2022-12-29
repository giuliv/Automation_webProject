package com.applause.auto.test.web.products;

import com.applause.auto.common.data.Constants.MyAccountTestData;
import com.applause.auto.common.data.Constants.TestData;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.common.data.enums.Filters;
import com.applause.auto.web.components.QuickViewComponent;
import com.applause.auto.web.components.plp.PlpItemComponent;
import com.applause.auto.web.helpers.WebHelper;
import com.applause.auto.web.views.ProductDetailsPage;
import com.applause.auto.web.views.ProductListPage;
import com.applause.auto.test.web.BaseTest;
import java.util.Arrays;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TeaAllTeaTests extends BaseTest {

  @Test(
      groups = {TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.PRODUCTS},
      description = "11107514")
  public void teaAllTeaTest() {
    logger.info("1. Navigate to Product list page");
    ProductsTestHelper.checkPlp(navigateToPLPFromHome(TestData.TEA_PLP_URL), softAssert);
  }

  @Test(
      groups = {TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.PRODUCTS},
      description = "11107515")
  public void teaBannerTest() {
    ProductsTestHelper.checkBestSellersBanner(
        navigateToPLPFromHome(TestData.TEA_PLP_URL), TestData.ALL_TEA_HEADER, softAssert);
  }

  @Test(
      groups = {TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.PRODUCTS},
      description = "11107518")
  public void teaSortingTest() {
    ProductsTestHelper.checkSortingOptions(navigateToPLPFromHome(TestData.TEA_PLP_URL), softAssert);
  }

  @Test(
      groups = {TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.PRODUCTS},
      description = "11107520")
  public void teaNewsletterSignUpNeverMissOfferTest() {
    ProductsTestHelper.checkNewsletterSignUpNeverMissOffer(
        navigateToPLPFromHome(TestData.TEA_PLP_URL), softAssert);
  }

  @Test(
      groups = {TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.PRODUCTS},
      description = "11107519")
  public void teaItemsTest() {
    logger.info("1. Navigate to Product list page");
    ProductListPage productListPage = navigateToPLPFromHome(TestData.TEA_PLP_URL);
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info(
        "2. Validate Product tile should be customizable in the CMS and is shown correctly on the page");
    Assert.assertEquals(
        productListPage.getPageHeader(),
        MyAccountTestData.TEA_HEADER,
        "All Tea header isn't displayed");

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
    WebHelper.scrollToPageTop();
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
      description = "11107517",
      enabled = false)
  // Todo: Testcase disabled until further notice
  public void teaFiltersTest() {
    ProductsTestHelper.checkFilters(
        navigateToPLPFromHome(TestData.TEA_PLP_URL),
        Arrays.asList(
            Filters.TEA_TYPE, Filters.TEA_BREWING_METHOD, Filters.TEA_CAFFEINE, Filters.TEA_REGION),
        softAssert);
  }

  @Test(
      groups = {TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.PRODUCTS},
      description = "11107521",
      enabled = false)
  // Todo:Disable by request of Bernadette, leaving only 1 case on allCoffee section[21.06.2022]
  public void teaOutOfStockTest() {
    logger.info("1. Navigate to Product list page");
    ProductListPage productListPage = navigateToPLPFromHome(TestData.TEA_PLP_URL);
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    int firstItem = 0;
    logger.info("2. Review Out of Stock items");
    Assert.assertTrue(
        productListPage.isViewProductDisplayed(firstItem), "View product is not displayed");
    ProductDetailsPage productDetailsPage = productListPage.clickViewProductButton(firstItem);
    Assert.assertNotNull(productDetailsPage, "Product Details page is not displayed");
  }
}
