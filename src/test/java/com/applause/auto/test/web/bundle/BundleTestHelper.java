package com.applause.auto.test.web.bundle;

import com.applause.auto.common.data.Constants.TestData;
import com.applause.auto.test.web.BaseTest;
import com.applause.auto.web.helpers.WebHelper;
import com.applause.auto.web.views.AcceptancePage;
import com.applause.auto.web.views.CartPage;
import com.applause.auto.web.views.CheckOutPage;
import com.applause.auto.web.views.PaymentsPage;
import com.applause.auto.web.views.ProductDetailsPage;
import com.applause.auto.web.views.ShippingPage;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class BundleTestHelper extends BaseTest {

  /**
   * Place order from the Cart page
   *
   * @param cartPage
   * @param softAssert
   * @return AcceptancePage
   */
  public static AcceptancePage checkoutProcess(CartPage cartPage, SoftAssert softAssert) {
    logger.info("Click checkout button");
    CheckOutPage checkOutPage = cartPage.clickContinueToCheckOut();

    logger.info("Enter Contact information and Shipping address.");
    checkOutPage.setCheckOutData();

    logger.info("Click on Continue.");
    ShippingPage shippingPage = checkOutPage.clickContinueToShipping();

    logger.info("Click on Continue to payment.");
    PaymentsPage paymentsPage = shippingPage.clickContinueToPayments();

    logger.info("Verify that - Payment page is displayed");
    Assert.assertTrue(paymentsPage.isDisplayed(), "Payment page isn't displayed");

    logger.info("Place order");
    AcceptancePage acceptancePage = null;
    paymentsPage = paymentsPage.setPaymentData(TestData.VISA_CC);
    if (!WebHelper.getTestEnvironment().equalsIgnoreCase("production")) {
      acceptancePage = paymentsPage.clickPayNow();

      logger.info("Validate the order id is displayed");
      softAssert.assertTrue(acceptancePage.isOrderNumberDisplayed(), "Order id is not displayed");
    }

    return acceptancePage;
  }

  /**
   * Add bundle item to the cart and check all items are added with 10% discount
   *
   * @param detailsPage
   * @param softAssert
   * @return CartPage
   */
  public static CartPage addBundleToCartAndValidateDiscount(
      ProductDetailsPage detailsPage, SoftAssert softAssert) {
    logger.info("1. Add bundle product to the cart");
    String productName = detailsPage.getProductName();
    CartPage cartPage = detailsPage.clickAddToMiniCart().clickViewCartButton();

    logger.info("2. Validate all items contains bundles");
    softAssert.assertTrue(
        cartPage.getProductNames().size() > 1, "The only one product bundle is added to the cart");
    softAssert.assertEquals(
        cartPage.getBundles().size(),
        cartPage.getProductNames().size(),
        "Not all products contain bundles");

    logger.info("3. Validate Bundle Items is added to cart");
    softAssert.assertTrue(
        cartPage.getBundles().stream()
            .allMatch(item -> StringUtils.containsIgnoreCase(item, productName)),
        "Wrong bundle item is added to the cart");

    logger.info("4. Validate 10% bundle discount is applied for all items in the bundle");
    softAssert.assertEquals(
        cartPage.getProductNames().size(),
        cartPage.getDiscountValues().size(),
        "Not all products contain discounts");

    if (WebHelper.isDesktop()) {
      softAssert.assertTrue(
          cartPage.getDiscountValues().stream().allMatch(item -> item.contains("10%")),
          "Not all items contain 10% discount");
    }

    return cartPage;
  }
}
