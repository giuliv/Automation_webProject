package com.applause.auto.test.new_web.ppd;

import com.applause.auto.common.data.Constants.TestData;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.new_web.views.CheckOutPage;
import com.applause.auto.new_web.views.HomePage;
import com.applause.auto.new_web.views.ProductDetailsPage;
import com.applause.auto.test.new_web.BaseTest;
import org.testng.annotations.Test;

public class DevelopmentPpdCasesTests extends BaseTest {

  @Test(
      groups = {TestNGGroups.PPD, TestNGGroups.FRONT_END_REGRESSION},
      description = "11124758")
  public void plpAndPdpPageValidationsTest() {
    logger.info("1. Navigate to Home");
    HomePage homePage = navigateToHome();
    homePage.closeInitialBannersAndModals();

    PpdTestHelper.plpAndPdpPageValidationsTest(
        homePage,
        TestData.PRODUCT_LUMINOSA_BREAKFAST_BLEND_GIFT_SUBSCRIPTION,
        TestData.PRODUCT_LUMINOSA_BREAKFAST_BLEND_GIFT_SUBSCRIPTION_NAME,
        true);
  }

  @Test(
      groups = {TestNGGroups.PPD, TestNGGroups.FRONT_END_REGRESSION},
      description = "11124759")
  public void checkoutAndOrderValidationTest() {
    logger.info("1. Navigate to PDP");
    ProductDetailsPage productDetailsPage =
        navigateToPDPFromHome(TestData.PRODUCT_LUMINOSA_BREAKFAST_BLEND_GIFT_SUBSCRIPTION);

    PpdTestHelper.checkoutAndOrderValidationGiftSubscriptionPpd(productDetailsPage, softAssert);

    String cartPageUrl = WebHelper.getCurrentUrl();
    productDetailsPage = navigateToPDPFromHome(coffeeSelected);

    PpdTestHelper.checkNoItemsOtherThanPrepaidCanBeAddedToCart(productDetailsPage, softAssert);

    logger.info("15. Go back to Homepage -> Search for PPD item ->Add prepay items to the cart");
    productDetailsPage =
        navigateToPDP(TestData.PRODUCT_LUMINOSA_BREAKFAST_BLEND_K_CUP_PODS_GIFT_SUBSCRIPTION);
    CheckOutPage checkOutPage = productDetailsPage.clickBuyNow();

    logger.info(
        "Verify if a prepay item is in the cart the customer should be able to add another prepay item");
    softAssert.assertTrue(checkOutPage.isDisplayed(), "User was not able to add item to the cart");

    logger.info("16. Add any non prepaid item to cart if a prepaid item is in the cart");
    productDetailsPage = navigateToPDP(coffeeSelected);

    PpdTestHelper.checkSubscriptionItemCanBeRemoved(productDetailsPage, cartPageUrl, softAssert);
    softAssert.assertAll();
  }

  @Test(
      groups = {TestNGGroups.PPD, TestNGGroups.FRONT_END_REGRESSION},
      description = "11124761")
  public void plpAndPpdPageValidations() {
    logger.info("1. Navigate to PDP");
    HomePage homePage = navigateToHome();
    homePage.closeInitialBannersAndModals();

    PpdTestHelper.plpAndPdpPageValidationsTest(
        homePage,
        TestData.PRODUCT_LUMINOSA_BREAKFAST_BLEND_K_CUP_PODS_GIFT_SUBSCRIPTION,
        TestData.PRODUCT_LUMINOSA_BREAKFAST_BLEND_K_CUP_PODS_GIFT_SUBSCRIPTION_NAME,
        false);
  }

  @Test(
      groups = {TestNGGroups.PPD, TestNGGroups.FRONT_END_REGRESSION},
      description = "11124762")
  public void checkoutAndOrderValidationGiftSubscriptionPpdTest() {
    logger.info("1. Navigate to PDP");
    ProductDetailsPage productDetailsPage =
        navigateToPDPFromHome(
            TestData.PRODUCT_LUMINOSA_BREAKFAST_BLEND_K_CUP_PODS_GIFT_SUBSCRIPTION);
    PpdTestHelper.checkoutAndOrderValidationGiftSubscriptionPpd(productDetailsPage, softAssert);

    String cartPageUrl = WebHelper.getCurrentUrl();
    productDetailsPage = navigateToPDPFromHome(coffeeSelected);
    PpdTestHelper.checkNoItemsOtherThanPrepaidCanBeAddedToCart(productDetailsPage, softAssert);

    logger.info("15. Go back to Homepage -> Search for PPD item ->Add prepay items to the cart");
    productDetailsPage = navigateToPDP(TestData.PRODUCT_LUMINOSA_BREAKFAST_BLEND_GIFT_SUBSCRIPTION);
    CheckOutPage checkOutPage = productDetailsPage.clickBuyNow();

    logger.info(
        "Verify if a prepay item is in the cart the customer should be able to add another prepay item");
    softAssert.assertTrue(checkOutPage.isDisplayed(), "User was not able to add item to the cart");

    logger.info("16. Add any non prepaid item to cart if a prepaid item is in the cart");
    productDetailsPage = navigateToPDP(coffeeSelected);

    PpdTestHelper.checkSubscriptionItemCanBeRemoved(productDetailsPage, cartPageUrl, softAssert);
    softAssert.assertAll();
  }
}
