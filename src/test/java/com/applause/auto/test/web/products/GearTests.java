package com.applause.auto.test.web.products;

import com.applause.auto.common.data.Constants.MyAccountTestData;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.common.data.enums.Filters;
import com.applause.auto.web.components.QuickViewComponent;
import com.applause.auto.web.components.plp.PlpItemComponent;
import com.applause.auto.web.views.ProductDetailsPage;
import com.applause.auto.web.views.ProductListPage;
import com.applause.auto.test.web.BaseTest;
import java.util.Arrays;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GearTests extends BaseTest {

  @Test(
      groups = {TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.PRODUCTS},
      description = "11107471")
  public void gearTest() {
    ProductsTestHelper.checkPlp(navigateToGearSectionFromHome(), softAssert);
  }

  @Test(
      groups = {TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.PRODUCTS},
      description = "11107472")
  public void gearBannerTest() {
    ProductsTestHelper.checkBestSellersBanner(
        navigateToGearSectionFromHome(), MyAccountTestData.GEAR_HEADER, softAssert);
  }

  @Test(
      groups = {TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.PRODUCTS},
      description = "11107473",
      enabled = false)
  // Todo: Testcase disabled until further notice
  public void gearFiltersTest() {
    ProductsTestHelper.checkFilters(
        navigateToPLPFromHome(),
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
      description = "11107474")
  public void gearSortingTest() {
    // Todo:Rating sorting missing [not displayed]
    ProductsTestHelper.checkSortingOptions(navigateToGearSectionFromHome(), softAssert);
  }

  @Test(
      groups = {TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.PRODUCTS},
      description = "11107476")
  public void gearNewsletterSignUpNeverMissOfferTest() {
    ProductsTestHelper.checkNewsletterSignUpNeverMissOffer(
        navigateToGearSectionFromHome(), softAssert);
  }

  @Test(
      groups = {TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.PRODUCTS},
      description = "11107475")
  public void gearItemsTest() {
    logger.info("1. Navigate to Product list page");
    ProductListPage productListPage = navigateToGearSectionFromHome();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info(
        "2. Validate Product tile should be customizable in the CMS and is shown correctly on the page");
    Assert.assertEquals(
        productListPage.getPageHeader(),
        MyAccountTestData.GEAR_HEADER,
        "Gear header isn't displayed");

    int totalProducts = productListPage.getTotalResults();
    logger.info("3. Validate item image, name, description, price and reviews are shown correctly");
    for (PlpItemComponent item : productListPage.productsList()) {
      softAssert.assertTrue(item.isNameDisplayed(), "Product name isn't displayed");
      String productName = item.getProductName();

      softAssert.assertTrue(
          item.isPriceDisplayed(),
          String.format("Product price isn't displayed for product: {%s}", productName));
      softAssert.assertTrue(
          item.isImageDisplayed(),
          String.format("Product image isn't displayed for product {%s}", productName));
    }

    logger.info("4. At Homepage --> Hover any Gear item");
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
      groups = {TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.PRODUCTS},
      description = "11107477",
      enabled = false)
  // Todo:Disable by request of Bernadette, leaving only 1 case on allCoffee section[21.06.2022]
  public void gearOutOfStockTest() {
    logger.info("1. Navigate to Product list page");
    ProductListPage productListPage = navigateToGearSectionFromHome();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    int firstItem = 0;
    logger.info("2. Review Out of Stock items");
    Assert.assertTrue(
        productListPage.isViewProductDisplayed(firstItem), "View product is not displayed");
    ProductDetailsPage productDetailsPage = productListPage.clickViewProductButton(firstItem);
    Assert.assertNotNull(productDetailsPage, "Product Details page is not displayed");
  }
}
