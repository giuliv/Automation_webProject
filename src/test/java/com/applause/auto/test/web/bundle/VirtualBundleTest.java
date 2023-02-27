package com.applause.auto.test.web.bundle;

import com.applause.auto.common.data.Constants.TestData;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.test.web.BaseTest;
import com.applause.auto.web.helpers.WebHelper;
import com.applause.auto.web.views.AcceptancePage;
import com.applause.auto.web.views.CartPage;
import com.applause.auto.web.views.CheckOutPage;
import com.applause.auto.web.views.PaymentsPage;
import org.testng.annotations.Test;

public class VirtualBundleTest extends BaseTest {

  @Test(
      groups = {
        TestNGGroups.FRONT_END_REGRESSION,
        TestNGGroups.VIRTUAL_BUNDLE,
        TestNGGroups.FE_PROD_REGRESSION
      },
      description = "11127070")
  public void happyCheckoutFlowTest() {
    CartPage cartPage =
        BundleTestHelper.addBundleToCartAndValidateDiscount(
            navigateToPDPFromHome(TestData.BUNDLE_PRODUCT_URL_PARAMETER), softAssert);

    logger.info("5. Place order");
    int itemsNumber = cartPage.getBundles().size();
    if (!WebHelper.getTestEnvironment().equalsIgnoreCase("production")) {
      AcceptancePage acceptancePage = BundleTestHelper.checkoutProcess(cartPage, softAssert);

      logger.info("6. Validate bundle items are displayed");
      softAssert.assertEquals(
          acceptancePage.getBundlesDiscounts().size(),
          itemsNumber,
          "Not correct number of bundles is displayed");
    } else {
      logger.info("Click checkout button");
      CheckOutPage checkOutPage = cartPage.clickContinueToCheckOut();

      logger.info("Enter Contact information and Shipping address.");
      checkOutPage.setCheckOutData();

      logger.info("Click on Continue.");
      PaymentsPage paymentsPage = checkOutPage.clickContinueToShipping().clickContinueToPayments();

      logger.info("6. Validate bundle items are displayed");
      softAssert.assertEquals(
          paymentsPage.getBundlesDiscounts().size(),
          itemsNumber,
          "Not correct number of bundles is displayed");
    }
    softAssert.assertAll();
  }

  @Test(
      groups = {
        TestNGGroups.FRONT_END_REGRESSION,
        TestNGGroups.VIRTUAL_BUNDLE,
        TestNGGroups.FE_PROD_REGRESSION
      },
      description = "11127071")
  public void brokenBundleCheckoutFlowTest() {
    CartPage cartPage =
        BundleTestHelper.addBundleToCartAndValidateDiscount(
            navigateToPDPFromHome(TestData.BUNDLE_PRODUCT_URL_PARAMETER), softAssert);

    logger.info("5. Remove 1 item from Minicart/cart");
    double totalPriceBeforeRemove = cartPage.getDoubleSubTotal();
    cartPage.decreaseQuantity(1);
    cartPage = WebHelper.refreshMe(CartPage.class);

    logger.info(
        "6. Validate the bundle discount should no longer apply but the other bundled products should remain in cart until removed");
    softAssert.assertTrue(cartPage.getDiscountValues().isEmpty(), "The discount is still present");
    softAssert.assertNotEquals(
        totalPriceBeforeRemove, cartPage.getDoubleSubTotal(), "The discount is still applied");

    BundleTestHelper.checkoutProcess(cartPage, softAssert);

    softAssert.assertAll();
  }

  @Test(
      groups = {
        TestNGGroups.FRONT_END_REGRESSION,
        TestNGGroups.VIRTUAL_BUNDLE,
        TestNGGroups.FE_PROD_REGRESSION
      },
      description = "11127072")
  public void nonBundleItemCheckoutFlowTest() {
    CartPage cartPage =
        BundleTestHelper.addBundleToCartAndValidateDiscount(
            navigateToPDPFromHome(TestData.BUNDLE_PRODUCT_URL_PARAMETER), softAssert);

    logger.info(
        "5. Search regular item (french Roast),  add to cart and continue to checkout  with CC");
    int itemsCount = cartPage.getProductNames().size();
    cartPage =
        navigateToPDPFromHome(TestData.NEW_FRENCH_ROAST_URL_PARAMETER)
            .clickAddToMiniCart()
            .clickViewCartButton();

    logger.info("6. Validate regular item and Bundle item is added to cart ");
    softAssert.assertTrue(
        cartPage.getProductLinks().get(0).contains(TestData.NEW_FRENCH_ROAST_URL_PARAMETER),
        "Regular item is not added to the cart");
    softAssert.assertTrue(
        itemsCount < cartPage.getProductNames().size(), "Bundle item is not displayed in the cart");

    logger.info(
        "7. Validate 10% bundle discount is applied for bundle items only and not for regular item");
    softAssert.assertTrue(
        cartPage.getProductNames().size() > cartPage.getBundles().size(),
        "All products have discounts");

    BundleTestHelper.checkoutProcess(cartPage, softAssert);

    softAssert.assertAll();
  }

  @Test(
      groups = {
        TestNGGroups.FRONT_END_REGRESSION,
        TestNGGroups.VIRTUAL_BUNDLE,
        TestNGGroups.FE_PROD_REGRESSION
      },
      description = "11127073")
  public void increaseBundleItemsDuringCheckoutFlowTest() {
    CartPage cartPage =
        BundleTestHelper.addBundleToCartAndValidateDiscount(
            navigateToPDPFromHome(TestData.BUNDLE_PRODUCT_URL_PARAMETER), softAssert);

    logger.info("5. At minicart  click on '+' at any one item to increase the qty of bundle item");
    int itemsCount = cartPage.getProductNames().size();
    cartPage.increaseQuantity(0);
    cartPage = WebHelper.refreshMe(CartPage.class);

    logger.info(
        "6. Validate the added product should not be applied a bundled discount and should appear as a second item in the cart");
    softAssert.assertEquals(
        cartPage.getProductNames().size(), itemsCount + 1, "A new bundle item is not displayed");
    softAssert.assertTrue(
        cartPage.getProductNames().size() != cartPage.getDiscountValues().size(),
        "The latest added bundle item is unexpected displayed discount");

    BundleTestHelper.checkoutProcess(cartPage, softAssert);

    softAssert.assertAll();
  }
}
