package com.applause.auto.test.new_web.products;

import com.applause.auto.common.data.Constants.MyAccountTestData;
import com.applause.auto.common.data.Constants.TestData;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.new_web.components.plp.PlpItemComponent;
import com.applause.auto.new_web.views.ProductListPage;
import com.applause.auto.test.new_web.BaseTest;
import java.util.Collections;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CoffeeDarkRoastTests extends BaseTest {

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.FIND_STORE, TestNGGroups.SMOKE},
      description = "")
  public void coffeeDarkRoastTest() {
    ProductsTestHelper.checkPlp(navigateToPLP(TestData.COFFEE_DARK_ROAST_URL), softAssert);
  }

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.FIND_STORE, TestNGGroups.SMOKE},
      description = "")
  public void coffeeDarkRoastBannerTest() {
    ProductsTestHelper.checkBestSellersBanner(
        navigateToPLP(TestData.COFFEE_DARK_ROAST_URL), TestData.DARK_ROAST_HEADER, softAssert);
  }

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.FIND_STORE, TestNGGroups.SMOKE},
      description = "")
  public void coffeeDarkRoastSortingTest() {
    ProductsTestHelper.checkSortingOptions(
        navigateToPLP(TestData.COFFEE_DARK_ROAST_URL), softAssert);
  }

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.FIND_STORE, TestNGGroups.SMOKE},
      description = "")
  public void coffeeDarkRoastNewsletterSignUpNeverMissOfferTest() {
    ProductsTestHelper.checkNewsletterSignUpNeverMissOffer(
        navigateToPLP(TestData.COFFEE_DARK_ROAST_URL), softAssert);
  }

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.FIND_STORE, TestNGGroups.SMOKE},
      description = "",
      enabled = false)
  public void coffeeDarkRoastBreadcrumbsPathTest() {
    // TODO Home breadcrumb element isn't displayed. Need to update test case
  }

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.FIND_STORE, TestNGGroups.SMOKE},
      description = "",
      enabled = false)
  public void coffeeDarkRoastItemsTest() {
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
  public void coffeeDarkRoastJoinClubTest() {
    logger.info("1. Navigate to Product list page");
    ProductListPage productListPage = navigateToPLP();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    // TODO there is not Join the Club section
  }

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION, TestNGGroups.FIND_STORE, TestNGGroups.SMOKE},
      description = "",
      enabled = false)
  public void coffeeDarkRoastFiltersTest() {
    ProductsTestHelper.checkFilters(
        navigateToPLP(TestData.COFFEE_DARK_ROAST_URL), Collections.emptyList(), softAssert);
  }
}
