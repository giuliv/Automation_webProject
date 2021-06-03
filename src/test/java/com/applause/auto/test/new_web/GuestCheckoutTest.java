package com.applause.auto.test.new_web;

import com.applause.auto.common.data.Constants;
import com.applause.auto.new_web.components.Header;
import com.applause.auto.new_web.components.MiniCart;
import com.applause.auto.new_web.views.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GuestCheckoutTest extends BaseTest {

  @Test()
  public void guestCheckoutTest() {

    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");

    logger.info("2. Select Best Sellers from Coffee tab");
    Header header = homePage.getHeader();
    header.hoverCategoryFromMenu(Constants.MenuOptions.COFFEE);
    ProductListPage productListPage =
        header.clickOverSubCategoryFromMenu(
            ProductListPage.class, Constants.MenuSubCategories.COFFEE_BEST_SELLERS);

    logger.info("3. Add first item to MiniCart");
    int productSelected = 3;
    ProductDetailsPage productDetailsPage =
        productListPage.clickOverProductByIndex(productSelected);

    String productName = productDetailsPage.getProductName();
    String grind = productDetailsPage.getGrindSelected();
    int productQuantity = productDetailsPage.getProductQuantity();

    MiniCart miniCart = productDetailsPage.clickAddToCart();
    Assert.assertEquals(
        productName, miniCart.getProductName(), "Correct Product was not added to MiniCart");
    Assert.assertEquals(
        grind, miniCart.getGrindSelected(), "Correct Grind was not added to MiniCart");
    Assert.assertEquals(
        productQuantity,
        miniCart.getProductQuantity(),
        "Correct product quantity was not added to MiniCart");

    logger.info("4. Proceed to Checkout page");
    CheckOutPage checkOutPage = miniCart.clickContinueToCheckOut();
    checkOutPage.setCheckOutData();

    logger.info("5. Proceed to Shipping page");
    ShippingPage shippingPage = checkOutPage.clickContinueToShipping();
    shippingPage.selectShippingMethodByIndex(0);

    logger.info("6. Proceed to Payments page");
    PaymentsPage paymentsPage = shippingPage.clickContinueToPayments();
    paymentsPage.setPaymentData();
    Assert.assertTrue(
        paymentsPage.isSameAddressSelected(), "Same Address option should be Selected");

    String totalPrice = paymentsPage.getTotalPrice();

    logger.info("6. Proceed to Acceptance page");
    AcceptancePage acceptancePage = paymentsPage.clickContinueToPayments();

    logger.info("7. Validating Product Details");
    Assert.assertTrue(acceptancePage.isOrderNumberDisplayed(), "Order number is NOT displayed");
    Assert.assertTrue(acceptancePage.isSubTotalDisplayed(), "SubTotal is NOT displayed");
    Assert.assertEquals(
        productName, acceptancePage.getOrderName(), "Product name does NOT matches");
    Assert.assertEquals(totalPrice, acceptancePage.getTotalPrice(), "Total price does NOT matches");

    // Todo: Optional assertions or assertions missing:
    //  Discount if any
    //  Map Location section

    logger.info("8. Validating Download buttons");
    acceptancePage.clickOverTrackPackageButton();
    Assert.assertEquals(
        acceptancePage.getPhoneFromTrackPackageSection(),
        "+1 " + Constants.WebTestData.PHONE,
        "Phone from Track Package section is NOT correct");

    acceptancePage.clickOverShippingUpdatesButton();
    Assert.assertEquals(
        acceptancePage.getPhoneFromShippingUpdatesSection(),
        "+1 " + Constants.WebTestData.PHONE,
        "Phone from Shipping Updates section is NOT correct");

    // Todo: Optional assertions or assertions missing:
    //  Submit button from trackPackage/shippingUpdates sections
    //  [user will receive a text validations]

    logger.info("FINISH");
  }
}
