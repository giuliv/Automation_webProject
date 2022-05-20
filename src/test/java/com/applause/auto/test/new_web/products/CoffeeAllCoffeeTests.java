package com.applause.auto.test.new_web.products;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.Constants.MyAccountTestData;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.common.data.enums.Filters;
import com.applause.auto.new_web.components.QuickViewComponent;
import com.applause.auto.new_web.components.plp.PlpItemComponent;
import com.applause.auto.new_web.views.FreeHomeDeliveryPage;
import com.applause.auto.new_web.views.ProductListPage;
import com.applause.auto.test.new_web.BaseTest;
import java.util.Arrays;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CoffeeAllCoffeeTests extends BaseTest {

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.PRODUCTS},
      description = "11107493")
  public void coffeeAllCoffeeTest() {
    ProductsTestHelper.checkPlp(navigateToPLP(), softAssert);
  }

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.PRODUCTS},
      description = "11107494")
  public void allCoffeeBannerTest() {
    ProductsTestHelper.checkBestSellersBanner(
        navigateToPLP(), MyAccountTestData.ALL_COFFEE_HEADER, softAssert);
  }

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.PRODUCTS},
      description = "11108847",
      enabled = false)
  // Todo: Testcase disabled until further notice
  public void allCoffeeFiltersTest() {
    ProductsTestHelper.checkFilters(
        navigateToPLP(),
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
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.PRODUCTS},
      description = "11108848")
  public void allCoffeeSortingTest() {
    ProductsTestHelper.checkSortingOptions(navigateToPLP(), softAssert);
  }

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.PRODUCTS},
      description = "11107495")
  public void allCoffeeNewsletterSignUpNeverMissOfferTest() {
    ProductsTestHelper.checkNewsletterSignUpNeverMissOffer(navigateToPLP(), softAssert);
  }

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.PRODUCTS},
      description = "11108849")
  public void allCoffeeItemsTest() {
    logger.info("1. Navigate to Product list page");
    ProductListPage productListPage = navigateToPLP();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info(
        "2. Validate Product tile should be customizable in the CMS and is shown correctly on the page");
    Assert.assertEquals(
        productListPage.getPageHeader(),
        MyAccountTestData.ALL_COFFEE_HEADER,
        "All Coffee header isn't displayed");

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
      description = "11107497")
  public void allCoffeeJoinClubTest() {
    logger.info("1. Navigate to Product list page");
    ProductListPage productListPage = navigateToPLP();
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
}
