package com.applause.auto.test.web.products;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.Constants.TestData;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.test.web.BaseTest;
import com.applause.auto.web.components.QuickViewComponent;
import com.applause.auto.web.components.plp.PlpItemComponent;
import com.applause.auto.web.helpers.WebHelper;
import com.applause.auto.web.views.ProductListPage;
import java.util.Collections;
import java.util.stream.Collectors;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TeaBestSellersTests extends BaseTest {

  @Test(
      groups = {
        TestNGGroups.FRONT_END_REGRESSION,
        TestNGGroups.PRODUCTS,
        TestNGGroups.FE_PROD_REGRESSION
      },
      description = "11107522")
  public void teaBestSellersTest() {
    ProductsTestHelper.checkPlp(navigateToPLPFromHome(TestData.TEA_BEST_SELLERS_URL), softAssert);
  }

  @Test(
      groups = {
        TestNGGroups.FRONT_END_REGRESSION,
        TestNGGroups.PRODUCTS,
        TestNGGroups.FE_PROD_REGRESSION
      },
      description = "11107523")
  public void teaBestSellersBannerTest() {
    ProductsTestHelper.checkBestSellersBanner(
        navigateToPLPFromHome(TestData.TEA_BEST_SELLERS_URL),
        TestData.TEA_BEST_SELLERS_HEADER,
        softAssert);
  }

  @Test(
      groups = {
        TestNGGroups.FRONT_END_REGRESSION,
        TestNGGroups.PRODUCTS,
        TestNGGroups.FE_PROD_REGRESSION
      },
      description = "11107525")
  public void teaBestSellersSortingTest() {
    ProductsTestHelper.checkSortingOptions(
        navigateToPLPFromHome(TestData.TEA_BEST_SELLERS_URL), softAssert);
  }

  @Test(
      groups = {
        TestNGGroups.FRONT_END_REGRESSION,
        TestNGGroups.PRODUCTS,
        TestNGGroups.FE_PROD_REGRESSION
      },
      description = "11107527")
  public void teaBestSellersNewsletterSignUpNeverMissOfferTest() {
    ProductsTestHelper.checkNewsletterSignUpNeverMissOffer(
        navigateToPLPFromHome(TestData.TEA_BEST_SELLERS_URL), softAssert);
  }

  @Test(
      groups = {
        TestNGGroups.FRONT_END_REGRESSION,
        TestNGGroups.PRODUCTS,
        TestNGGroups.FE_PROD_REGRESSION
      },
      description = "11107526")
  public void teaBestSellersItemsTest() {
    logger.info("1. Navigate to Product list page");
    ProductListPage productListPage = navigateToPLPFromHome(TestData.TEA_BEST_SELLERS_URL);
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info(
        "2. Validate Product tile should be customizable in the CMS and is shown correctly on the page");
    Assert.assertEquals(
        productListPage.getPageHeader(),
        Constants.MyAccountTestData.BESTSELLER_TEA_HEADER,
        "BestSeller Tea header isn't displayed");

    int totalProducts = productListPage.getTotalResults();
    logger.info("3. Validate item image, name, description, price and reviews are shown correctly");
    for (PlpItemComponent item :
        productListPage.productsList().stream().limit(10).collect(Collectors.toList())) {
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
      productListPage.clickLoadMore();
      Assert.assertTrue(
          totalProducts < productListPage.getTotalResults(), "Load more results does not work");
    }

    softAssert.assertAll();
  }

  @Test(
      groups = {
        TestNGGroups.FRONT_END_REGRESSION,
        TestNGGroups.PRODUCTS,
        TestNGGroups.FE_PROD_REGRESSION
      },
      description = "11107524",
      enabled = false)
  public void teaBestSellersFiltersTest() {
    ProductsTestHelper.checkFilters(
        navigateToPLPFromHome(TestData.TEA_BEST_SELLERS_URL), Collections.emptyList(), softAssert);
  }
}
