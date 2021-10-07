package com.applause.auto.test.web;

import com.applause.auto.common.data.Constants.TestData;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.web.components.MiniCartContainerChunk;
import com.applause.auto.web.components.VerifyYourAddressDetailsChunk;
import com.applause.auto.web.views.*;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CheckoutDemoTest extends BaseTest {

  private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().getClass());

  @Test(
      groups = {TestNGGroups.DEMO_CHECKOUT},
      description = "19501",
      enabled = false)
  public void guestCheckoutTeaTest() {

    logger.info("1. Navigate to landing page");
    Landing landing = navigateToLanding();
    Assert.assertNotNull(landing, "Failed to navigate to the landing page.");

    logger.info("2. Select a tea from grid view and add to cart");
    ShopTeaPage shopTeaPage = navigateToShopTeaPage();
    TeaProductPage teaProductPage = shopTeaPage.clickProductName(TestData.TEA_NAME);
    MiniCartContainerChunk miniCartContainer = teaProductPage.clickAddToCart();

    logger.info("3. Select 'Edit Cart' from mini-cart");
    ShoppingCartPage shoppingCart = miniCartContainer.clickEditCart();

    logger.info("4. Add gift message to product");
    shoppingCart.selectOrderAsGift();
    shoppingCart.enterGiftMessage(TestData.GIFT_MESSAGE);

    logger.info("5. Select 'Proceed to Checkout'");
    CheckoutPage checkoutPage = shoppingCart.clickProceedToCheckout();
    CheckoutShippingInfoPage shippingInfoPage = checkoutPage.clickContinueAsGuest();

    logger.info("6. Complete Contact Information");
    VerifyYourAddressDetailsChunk verifyAddressChunk =
        shippingInfoPage.continueAfterFillingRequiredContactInfo();
    shippingInfoPage = verifyAddressChunk.clickEnteredAddressButton();

    logger.info("7. Select ground shipping");

    // TODO: investigate potential bug with gift message not persisting
    // Assert.assertEquals(
    //    shippingInfoPage.getGiftMessage(),
    //    TestData.GIFT_MESSAGE,
    //    "Gift message entered previously was not fetched correctly");

    CheckoutPaymentMethodPage paymentMethodPage =
        shippingInfoPage.setShippingMethod(TestData.SHIPPING_METHOD_GROUND);

    logger.info("8. Use credit card for payment");
    // Cant use Peets Card as it is limited to $50 so wont be a stable test
    CheckoutPlaceOrderPage placeOrderPage =
        paymentMethodPage.continueAfterFillingRequiredBillingInfo();

    logger.info("9. Click 'Place Order'");
    CheckoutConfirmationPage confirmationPage = placeOrderPage.placeOrder();

    logger.info("Verify Confirmation page is displayed");
    Assert.assertTrue(
        confirmationPage.getConfirmationMessage().contains("THANK YOU FOR YOUR PURCHASE!"),
        "Order was not placed");

    logger.info("Order Placed: " + confirmationPage.getOrderNumber());
  }

  @Test(
      groups = {TestNGGroups.DEMO_CHECKOUT},
      description = "19608",
      enabled = false)
  public void guestCheckoutPeetsCardTest() {

    logger.info("1. Navigate to landing page");
    Landing landing = navigateToLanding();
    Assert.assertNotNull(landing, "Failed to navigate to the landing page.");

    logger.info("2. Select a Peet's Card and add to cart from the product description page.");
    PeetsCardProductPage peetsCardPage = navigateToShopPeetsCardPage();
    peetsCardPage.selectCardAmount(TestData.PEETS_CARD_BUY_AMOUNT);
    MiniCartContainerChunk miniCartContainer = peetsCardPage.clickAddToCart();

    logger.info("3. Select 'Checkout' in mini-cart.");
    SignInPage signInPage = miniCartContainer.checkoutPeetsCard();

    logger.info("4. Select 'Create an Account'");
    SignUpPage signUpPage = signInPage.clickonCreateAccountButton();

    logger.info("5. Fill out form to SdkHelper.create an account");
    CheckoutShippingInfoPage shippingInfoPage =
        signUpPage.submitSignUpInfo(CheckoutShippingInfoPage.class);

    logger.info("6. Go back to mini-cart and select Checkout");
    // DashboardPage dashboardPage = dashboardModal.clickCloseModal();
    // MainMenuChunk mainMenu = dashboardPage.getMainMenu();
    // miniCartContainer = mainMenu.clickMiniCart();
    // CheckoutShippingInfoPage shippingInfoPage = miniCartContainer.clickSignedInCheckout();

    logger.info("7. Complete Contact Information");
    VerifyYourAddressDetailsChunk verifyAddressChunk =
        shippingInfoPage.continueAfterFillingRequiredContactInfo();
    shippingInfoPage = verifyAddressChunk.clickEnteredAddressButton();

    logger.info("7. Select ground shipping");
    CheckoutPaymentMethodPage paymentMethodPage =
        shippingInfoPage.setShippingMethod(TestData.SHIPPING_METHOD_GROUND);

    logger.info("8. Use credit card for payment");
    CheckoutPlaceOrderPage placeOrderPage = paymentMethodPage.continueAfterCrediCardBillingInfo();

    logger.info("9. Click 'Place Order'");
    CheckoutConfirmationPage confirmationPage = placeOrderPage.placeOrder();

    logger.info("Verify Confirmation page is displayed");
    Assert.assertTrue(
        confirmationPage.getConfirmationMessage().contains("THANK YOU FOR YOUR PURCHASE!"),
        "Order was not placed");

    logger.info("Order Placed: " + confirmationPage.getOrderNumber());
  }

  @Test(
      groups = {TestNGGroups.DEMO_CHECKOUT},
      description = "19503",
      enabled = false)
  public void guestCheckoutTour() {

    logger.info("1. Navigate to landing page");
    Landing landing = navigateToLanding();
    Assert.assertNotNull(landing, "Failed to navigate to the landing page.");

    logger.info("2. Navigate to Gift Subscription Shop page");
    SearchResultsPage searchResultsPage = landing.searchForProduct(TestData.TOUR_SEARCH_TERMS);

    logger.info("3. Select Product and Add to Cart");
    CoffeeProductDescriptionPage coffeeProductDescriptionPage =
        searchResultsPage.clickFirstProduct();
    coffeeProductDescriptionPage.selectGrind(TestData.GRIND);
    MiniCartContainerChunk miniCartContainerChunk = coffeeProductDescriptionPage.addToCart();
    Assert.assertNotNull(miniCartContainerChunk, "Mini Cart is not displayed");

    // TODO: Commenting code out due to Safari hover issue. Will revisit when issue is fixed.
    // logger.info("2. Navigate to Gift Subscription Shop page");
    // MainMenuChunk mainMenuChunk = landing.getMainMenu();
    // ShopGiftSubscriptionsPage shopGiftSubscriptionsPage =
    // mainMenuChunk.accessShopGiftSubscriptions();

    // logger.info("3. Select Product and Add to Cart");
    // CoffeeProductDescriptionPage coffeeProductDescriptionPage =
    // shopGiftSubscriptionsPage.clickFirstProduct();
    // coffeeProductDescriptionPage.selectGrind(TestData.GRIND);
    // MiniCartContainerChunk miniCartContainerChunk = coffeeProductDescriptionPage.addToCart();
    // Assert.assertNotNull(miniCartContainerChunk, "Mini Cart is not displayed");

    logger.info("4. Select Checkout");
    CheckoutPage checkoutPage = miniCartContainerChunk.clickCheckout();

    logger.info("5. Checkout as Guest");
    CheckoutShippingInfoPage checkoutShippingInfoPage = checkoutPage.clickContinueAsGuest();
    Assert.assertNotNull(checkoutShippingInfoPage, "Checkout Shipping page is not displayed");

    logger.info("6. Complete Contact Information");
    VerifyYourAddressDetailsChunk verifyYourAddressDetailsChunk =
        checkoutShippingInfoPage.continueAfterFillingRequiredContactInfo();
    checkoutShippingInfoPage = verifyYourAddressDetailsChunk.clickEnteredAddressButton();

    logger.info("7. Select ground shipping");
    CheckoutPaymentMethodPage paymentMethodPage =
        checkoutShippingInfoPage.setShippingMethod(TestData.SHIPPING_METHOD_GROUND);

    logger.info("8. Use credit card for payment");
    CheckoutPlaceOrderPage placeOrderPage =
        paymentMethodPage.continueAfterFillingRequiredBillingInfo();

    logger.info("9. Click 'Place Order'");
    CheckoutConfirmationPage confirmationPage = placeOrderPage.placeOrder();

    logger.info("10. Verify Confirmation page is displayed");
    logger.info(confirmationPage.getConfirmationMessage());
    Assert.assertEquals(
        confirmationPage.getConfirmationMessage().toLowerCase(),
        TestData.PURCHASE_CONFIRMATION_TEXT.toLowerCase(),
        "Order was not placed");

    logger.info("Order Placed: " + confirmationPage.getOrderNumber());
  }
}
