package com.applause.auto.test.new_web.products;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.Constants.MyAccountTestData;
import com.applause.auto.common.data.Constants.TestData;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.new_web.components.QuickViewComponent;
import com.applause.auto.new_web.components.plp.PlpItemComponent;
import com.applause.auto.new_web.views.FreeHomeDeliveryPage;
import com.applause.auto.new_web.views.ProductListPage;
import com.applause.auto.test.new_web.BaseTest;
import java.util.Collections;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CoffeeDarkRoastTests extends BaseTest {

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.PRODUCTS},
      description = "11107505")
  public void coffeeDarkRoastTest() {
    ProductsTestHelper.checkPlp(navigateToPLP(TestData.COFFEE_DARK_ROAST_URL), softAssert);
  }

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.PRODUCTS},
      description = "11107506")
  public void coffeeDarkRoastBannerTest() {
    ProductsTestHelper.checkBestSellersBanner(
        navigateToPLP(TestData.COFFEE_DARK_ROAST_URL), TestData.DARK_ROAST_HEADER, softAssert);
  }

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.PRODUCTS},
      description = "11107509")
  public void coffeeDarkRoastSortingTest() {
    ProductsTestHelper.checkSortingOptions(
        navigateToPLP(TestData.COFFEE_DARK_ROAST_URL), softAssert);
  }

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.PRODUCTS},
      description = "11107511")
  public void coffeeDarkRoastNewsletterSignUpNeverMissOfferTest() {
    ProductsTestHelper.checkNewsletterSignUpNeverMissOffer(
        navigateToPLP(TestData.COFFEE_DARK_ROAST_URL), softAssert);
  }

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.PRODUCTS},
      description = "11107507")
  public void coffeeDarkRoastBreadcrumbsPathTest() {
    logger.info("1. Navigate to Product list page");
    ProductListPage productListPage = navigateToPLP(TestData.COFFEE_DARK_ROAST_URL);
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    int totalResults = productListPage.getTotalResults();
    Assert.assertEquals(
        productListPage.getBreadCrumbs(),
        Constants.WebTestData.DARK_ROAST_BREADCRUMBS,
        "BreadCrumb does not match");

    logger.info("2. Review breadcrumbs feature");
    productListPage = productListPage.clickOverAllCoffeeFromBreadCrumbs();
    Assert.assertNotEquals(
        totalResults, productListPage.getTotalResults(), "BreadCrumbs link does not work");
  }

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.PRODUCTS},
      description = "11107510")
  public void coffeeDarkRoastItemsTest() {
    logger.info("1. Navigate to Product list page");
    ProductListPage productListPage = navigateToPLP(TestData.COFFEE_DARK_ROAST_URL);
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info(
        "2. Validate Product tile should be customizable in the CMS and is shown correctly on the page");
    Assert.assertEquals(
        productListPage.getPageHeader(),
        MyAccountTestData.DARK_ROAST_HEADER,
        "Dark Roast header isn't displayed");

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
    productListPage.loadMore();
    Assert.assertTrue(
        totalProducts < productListPage.getTotalResults(), "Load more results does not work");

    softAssert.assertAll();
  }

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.PRODUCTS},
      description = "11107513")
  public void coffeeDarkRoastJoinClubTest() {
    logger.info("1. Navigate to Product list page");
    ProductListPage productListPage = navigateToPLP(TestData.COFFEE_DARK_ROAST_URL);
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Validating Home Delivery...");
    Assert.assertTrue(
        productListPage.isHomeDeliverySectionDisplayed(), "Home Delivery section is not displayed");
    Assert.assertEquals(
        productListPage.getHomeDeliveryTitle(),
        Constants.WebTestData.HOME_DELIVERY_TITLE,
        "Title does not match");

    FreeHomeDeliveryPage freeHomeDeliveryPage = productListPage.clickGetStartedHomeDelivery();
    Assert.assertTrue(
        freeHomeDeliveryPage.isPageHeadingDisplayed(), "Subscription page is not displayed");
  }

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.PRODUCTS},
      description = "11107508",
      enabled = false)
  // Todo: Testcase disabled until further notice
  public void coffeeDarkRoastFiltersTest() {
    ProductsTestHelper.checkFilters(
        navigateToPLP(TestData.COFFEE_DARK_ROAST_URL), Collections.emptyList(), softAssert);
  }
}
