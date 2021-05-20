package com.applause.auto.test.new_web;

import com.applause.auto.common.data.Constants;
import com.applause.auto.new_web.components.Header;
import com.applause.auto.new_web.components.MiniCart;
import com.applause.auto.new_web.views.CheckOutPage;
import com.applause.auto.new_web.views.HomePage;
import com.applause.auto.new_web.views.ProductDetailsPage;
import com.applause.auto.new_web.views.ProductListPage;
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
    int productSelected = 0;
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

    logger.info("4. Select 'Proceed to Checkout'");
    CheckOutPage checkOutPage = miniCart.clickContinueToCheckOut();

    logger.info("FINISH");
    //    ShopCoffeePage shopCoffeePage = home.clickShopCoffeeButton();
    //    CoffeeProductPage coffeeProductPage =
    //        shopCoffeePage.clickProductName(TestData.COFFEE_BRAND_NAME);
    //    coffeeProductPage.selectAGrind(TestData.GRIND);
    //    MiniCartContainerChunk miniCartContainer = coffeeProductPage.clickAddToCart();

    //    logger.info("3. Select 'Proceed to Checkout'");
    //    // TODO: Implement step 3 and 4 from test case to edit cart and add a promo card
    //    CheckoutPage checkoutPage = miniCartContainer.clickCheckout();
    //    CheckoutShippingInfoPage shippingInfoPage = checkoutPage.clickContinueAsGuest();
    //
    //    logger.info("4. Complete Contact Information");
    //    VerifyYourAddressDetailsChunk verifyAddressChunk =
    //        shippingInfoPage.continueAfterFillingRequiredContactInfo();
    //    shippingInfoPage = verifyAddressChunk.clickEnteredAddressButton();
    //
    //    logger.info("5. Select ground shipping");
    //    CheckoutPaymentMethodPage paymentMethodPage =
    //        shippingInfoPage.setShippingMethod(TestData.SHIPPING_METHOD_GROUND);
    //
    //    logger.info("6. Use credit card for payment");
    //    CheckoutPlaceOrderPage placeOrderPage =
    //        paymentMethodPage.continueAfterFillingRequiredBillingInfoLoggedIn();
    //
    //    logger.info("7. Click 'Place Order'");
    //    CheckoutConfirmationPage confirmationPage = placeOrderPage.placeOrder();
    //
    //    logger.info("Verify Confirmation page is displayed");
    //    Assert.assertTrue(
    //        confirmationPage.getConfirmationMessage().contains("THANK YOU FOR YOUR PURCHASE!"),
    //        "Order was not placed");
    //
    //    logger.info("Order Placed: " + confirmationPage.getOrderNumber());
  }
}
