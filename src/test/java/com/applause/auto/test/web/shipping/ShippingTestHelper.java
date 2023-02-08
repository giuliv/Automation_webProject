package com.applause.auto.test.web.shipping;

import com.applause.auto.common.data.Constants.TestData;
import com.applause.auto.common.data.dto.RecipientAddress;
import com.applause.auto.test.web.BaseTest;
import com.applause.auto.web.components.MiniCart;
import com.applause.auto.web.helpers.TestHelper;
import com.applause.auto.web.helpers.WebHelper;
import com.applause.auto.web.views.AcceptancePage;
import com.applause.auto.web.views.CheckOutPage;
import com.applause.auto.web.views.HomePage;
import com.applause.auto.web.views.PaymentsPage;
import com.applause.auto.web.views.ProductDetailsPage;
import com.applause.auto.web.views.ShippingPage;
import com.applause.auto.web.views.my_account.MyAccountPage;
import org.testng.Assert;

public class ShippingTestHelper extends BaseTest {

  /**
   * Place order from the Checkout page
   *
   * @param homePage
   */
  public static void checkoutProcess(
      HomePage homePage, String product, int quantity, String shippingMethod) {
    logger.info("1. Navigate to landing page");
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");
    homePage.closeInitialBannersAndModals();

    logger.info("2. Create new User account");
    String mail = WebHelper.getRandomMail();
    MyAccountPage myAccountPage = TestHelper.createAccount(homePage, mail);
    Assert.assertNotNull(myAccountPage, "Account was not created!");

    logger.info("3. Navigate to PDP");
    ProductDetailsPage productDetailsPage = navigateToPDP(product);
    Assert.assertNotNull(productDetailsPage, "PDP page is not displayed");

    if (quantity > 1) {
      if (productDetailsPage.isItemBagQuantityType()) {
        productDetailsPage.addMoreProducts(quantity);
      } else {
        productDetailsPage.incrementProductQuantity(quantity);
      }
    }

    logger.info("4. Add item to the cart");
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("5. Continue to checkout and place order");
    CheckOutPage checkOutPage = miniCart.clickContinueToCheckOut();
    checkOutPage.setCheckOutData(RecipientAddress.builder().build());
    ShippingPage shippingPage = checkOutPage.clickContinueToShipping();

    logger.info("6. On Web, At checkout, Shipping method, Validate “{}” is shown", shippingMethod);
    Assert.assertTrue(
        shippingPage.isShippingMethodDisplayed(shippingMethod), "Shipping method is not displayed");

    logger.info("7. Click on Continue to payment.");
    PaymentsPage paymentsPage = shippingPage.clickContinueToPayments();

    logger.info("8. Verify that - Payment page is displayed");
    Assert.assertTrue(paymentsPage.isDisplayed(), "Payment page isn't displayed");

    logger.info("9. Place order");
    paymentsPage = paymentsPage.setPaymentData(TestData.VISA_CC);
    if (!WebHelper.getTestEnvironment().equalsIgnoreCase("production")) {
      AcceptancePage acceptancePage = paymentsPage.clickPayNow();

      logger.info("10. Validate the order id is displayed");
      Assert.assertTrue(acceptancePage.isOrderNumberDisplayed(), "Order id is not displayed");
    }
  }
}
