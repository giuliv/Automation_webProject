package com.applause.auto.test.web;

import com.applause.auto.common.data.Constants.TestData;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.web.components.MiniCartContainerChunk;
import com.applause.auto.web.components.VerifyYourAddressDetailsChunk;
import com.applause.auto.web.views.CheckoutConfirmationPage;
import com.applause.auto.web.views.CheckoutPage;
import com.applause.auto.web.views.CheckoutPaymentMethodPage;
import com.applause.auto.web.views.CheckoutPlaceOrderPage;
import com.applause.auto.web.views.CheckoutShippingInfoPage;
import com.applause.auto.web.views.CoffeeKCupsProductPage;
import com.applause.auto.web.views.CoffeeProductDescriptionPage;
import com.applause.auto.web.views.CoffeeProductPage;
import com.applause.auto.web.views.EquipmentProductPage;
import com.applause.auto.web.views.Landing;
import com.applause.auto.web.views.PaypalLoginPage;
import com.applause.auto.web.views.PaypalReviewYourPurchasePage;
import com.applause.auto.web.views.PeetsCardProductPage;
import com.applause.auto.web.views.SearchResultsPage;
import com.applause.auto.web.views.ShopCoffeeKCupsPage;
import com.applause.auto.web.views.ShopCoffeePage;
import com.applause.auto.web.views.ShopEquipmentPage;
import com.applause.auto.web.views.ShopTeaPage;
import com.applause.auto.web.views.ShoppingCartPage;
import com.applause.auto.web.views.SignInPage;
import com.applause.auto.web.views.SignUpPage;
import com.applause.auto.web.views.TeaProductPage;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GuestCheckoutTest extends BaseTest {

  private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().getClass());

  @Test(
      groups = {TestNGGroups.GUEST_CHECKOUT},
      description = "19500")
  public void guestCheckoutCoffeeTest() {

    logger.info("1. Navigate to landing page");
    Landing landing = navigateToLanding();
    Assert.assertNotNull(landing, "Failed to navigate to the landing page.");

    logger.info("2. Select a coffee from grid view and add to cart");
    ShopCoffeePage shopCoffeePage = landing.clickShopCoffeeButton();
    CoffeeProductPage coffeeProductPage =
        shopCoffeePage.clickProductName(TestData.COFFEE_BRAND_NAME);
    coffeeProductPage.selectAGrind(TestData.GRIND);
    MiniCartContainerChunk miniCartContainer = coffeeProductPage.clickAddToCart();

    logger.info("3. Select 'Proceed to Checkout'");
    // TODO: Implement step 3 and 4 from test case to edit cart and add a promo card
    CheckoutPage checkoutPage = miniCartContainer.clickCheckout();
    CheckoutShippingInfoPage shippingInfoPage = checkoutPage.clickContinueAsGuest();

    logger.info("4. Complete Contact Information");
    VerifyYourAddressDetailsChunk verifyAddressChunk =
        shippingInfoPage.continueAfterFillingRequiredContactInfo();
    shippingInfoPage = verifyAddressChunk.clickEnteredAddressButton();

    logger.info("5. Select ground shipping");
    CheckoutPaymentMethodPage paymentMethodPage =
        shippingInfoPage.setShippingMethod(TestData.SHIPPING_METHOD_GROUND);

    logger.info("6. Use credit card for payment");
    CheckoutPlaceOrderPage placeOrderPage =
        paymentMethodPage.continueAfterFillingRequiredBillingInfoLoggedIn();

    logger.info("7. Click 'Place Order'");
    CheckoutConfirmationPage confirmationPage = placeOrderPage.placeOrder();

    logger.info("Verify Confirmation page is displayed");
    Assert.assertTrue(
        confirmationPage.getConfirmationMessage().contains("THANK YOU FOR YOUR PURCHASE!"),
        "Order was not placed");

    logger.info("Order Placed: " + confirmationPage.getOrderNumber());
  }

  @Test(
      groups = {TestNGGroups.GUEST_CHECKOUT},
      description = "19501")
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
        paymentMethodPage.continueAfterFillingRequiredBillingInfoLoggedIn();

    logger.info("9. Click 'Place Order'");
    CheckoutConfirmationPage confirmationPage = placeOrderPage.placeOrder();

    logger.info("Verify Confirmation page is displayed");
    Assert.assertTrue(
        confirmationPage.getConfirmationMessage().contains("THANK YOU FOR YOUR PURCHASE!"),
        "Order was not placed");

    logger.info("Order Placed: " + confirmationPage.getOrderNumber());
  }

  @Test(
      groups = {TestNGGroups.GUEST_CHECKOUT},
      description = "19502")
  public void guestCheckoutEquipmentTest() {

    logger.info("1. Navigate to landing page");
    Landing landing = navigateToLanding();
    Assert.assertNotNull(landing, "Failed to navigate to the landing page.");

    logger.info("2. Select a equipment from grid view and add to cart");
    ShopEquipmentPage shopEquipmentPage = navigateToShopEquipmentPage();
    EquipmentProductPage equipmentProductPage =
        shopEquipmentPage.clickProductName(TestData.EQUIPMENT_NAME);
    MiniCartContainerChunk miniCartContainer = equipmentProductPage.clickAddToCart();

    logger.info("3. Select 'Proceed to Checkout'");
    CheckoutPage checkoutPage = miniCartContainer.clickCheckout();
    CheckoutShippingInfoPage shippingInfoPage = checkoutPage.clickContinueAsGuest();

    logger.info("4. Complete Contact Information");
    VerifyYourAddressDetailsChunk verifyAddressChunk =
        shippingInfoPage.continueAfterFillingRequiredContactInfo();
    shippingInfoPage = verifyAddressChunk.clickEnteredAddressButton();

    logger.info("5. Select ground shipping");
    CheckoutPaymentMethodPage paymentMethodPage =
        shippingInfoPage.setShippingMethod(TestData.SHIPPING_METHOD_GROUND);

    logger.info("6. Use credit card for payment");
    CheckoutPlaceOrderPage placeOrderPage =
        paymentMethodPage.continueAfterFillingPeetsAndCreditInfo();

    logger.info("7. Click 'Place Order'");
    CheckoutConfirmationPage confirmationPage = placeOrderPage.placeOrder();

    logger.info("Verify Confirmation page is displayed");
    Assert.assertTrue(
        confirmationPage.getConfirmationMessage().contains("THANK YOU FOR YOUR PURCHASE!"),
        "Order was not placed");

    logger.info("Order Placed: " + confirmationPage.getOrderNumber());
  }

  @Test(
      groups = {TestNGGroups.GUEST_CHECKOUT},
      description = "19608")
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

    logger.info("5. Fill out form to create an account");
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
      groups = {TestNGGroups.GUEST_CHECKOUT},
      description = "19503")
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
        paymentMethodPage.continueAfterFillingRequiredBillingInfoLoggedIn();

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

  @Test(
      groups = {TestNGGroups.GUEST_CHECKOUT},
      description = "137108")
  public void wednesdayRoastCoffee() {

    logger.info("1. Navigate to landing page");
    Landing landing = navigateToLanding();
    Assert.assertNotNull(landing, "Failed to navigate to the landing page.");

    logger.info("2. Navigate to Gift Subscription Shop page");
    SearchResultsPage searchResultsPage = landing.searchForProduct(TestData.WEDNES_ROAST_SEARCH);

    logger.info("3. Select Product and Add to Cart");
    CoffeeProductDescriptionPage coffeeProductDescriptionPage = searchResultsPage.clickKona();
    coffeeProductDescriptionPage.selectGrind(TestData.GRIND);
    MiniCartContainerChunk miniCartContainerChunk = coffeeProductDescriptionPage.addToCart();
    Assert.assertNotNull(miniCartContainerChunk, "Mini Cart is not displayed");

    logger.info("4. Edit Cart");
    ShoppingCartPage shoppingCartPage = miniCartContainerChunk.clickEditCart();

    logger.info("5. Add Gift Message");
    shoppingCartPage.selectOrderAsGift();
    shoppingCartPage.enterGiftMessage(TestData.GIFT_MESSAGE);

    logger.info("6. Checkout with Paypal");
    PaypalLoginPage paypalLoginPage = shoppingCartPage.clickPayWithPaypal();

    logger.info("7. Login with Paypal");
    paypalLoginPage.enterEmail(TestData.PAYPAL_EMAIL);
    paypalLoginPage.clickNext();
    paypalLoginPage.enterPassword(TestData.PAYPAL_PASSWORD);
    PaypalReviewYourPurchasePage paypalReviewYourPurchasePage = paypalLoginPage.clickLogIn();
    CheckoutPlaceOrderPage checkoutPlaceOrderPage =
        paypalReviewYourPurchasePage.clickAgreeAndContinue();

    logger.info("8. Place Order");
    Assert.assertEquals(
        checkoutPlaceOrderPage.getProductName(),
        TestData.WEDNES_ROAST_SEARCH,
        "Incorrect product being purchased");
    Assert.assertEquals(checkoutPlaceOrderPage.getGiftMessage(), TestData.GIFT_MESSAGE);
    CheckoutConfirmationPage checkoutConfirmationPage = checkoutPlaceOrderPage.placeOrder();

    logger.info("9. Verify Confirmation page is displayed");
    logger.info(checkoutConfirmationPage.getConfirmationMessage());
    Assert.assertEquals(
        checkoutConfirmationPage.getConfirmationMessage().toLowerCase(),
        TestData.PURCHASE_CONFIRMATION_TEXT.toLowerCase(),
        "Order was not placed");

    logger.info("Order Placed: " + checkoutConfirmationPage.getOrderNumber());
  }

  @Test(
      groups = {TestNGGroups.GUEST_CHECKOUT},
      description = "133887")
  public void guestCheckoutPaypalKCupTest() {

    logger.info("1. Navigate to landing page");
    Landing landing = navigateToLanding();
    Assert.assertNotNull(landing, "Failed to navigate to the landing page.");

    logger.info("2. Navigate to K-Cups Shop page");
    ShopCoffeeKCupsPage shopCoffeeKCupsPage = navigateToShopCoffeeKCupsPage();
    CoffeeKCupsProductPage coffeeKCupsProductPage =
        shopCoffeeKCupsPage.clickProductName(TestData.COFFEE_KCUP_NAME);
    coffeeKCupsProductPage.selectBoxContent(TestData.COFFEE_KCUP_COUNT);
    MiniCartContainerChunk miniCartContainer = coffeeKCupsProductPage.clickAddToCart();

    logger.info("4. Select 'Edit Cart' from mini-cart");
    ShoppingCartPage shoppingCart = miniCartContainer.clickEditCart();

    logger.info("5. Add gift message to product");
    shoppingCart.selectOrderAsGift();
    shoppingCart.enterGiftMessage(TestData.GIFT_MESSAGE);

    logger.info("6. Checkout with Paypal");
    PaypalLoginPage paypalLoginPage = shoppingCart.clickPayWithPaypal();

    logger.info("7. Login with Paypal");
    paypalLoginPage.enterEmail(TestData.PAYPAL_EMAIL);
    paypalLoginPage.clickNext();
    paypalLoginPage.enterPassword(TestData.PAYPAL_PASSWORD);
    PaypalReviewYourPurchasePage paypalReviewYourPurchasePage = paypalLoginPage.clickLogIn();
    CheckoutPlaceOrderPage checkoutPlaceOrderPage =
        paypalReviewYourPurchasePage.clickAgreeAndContinue();

    logger.info("8. Place Order");
    Assert.assertEquals(checkoutPlaceOrderPage.getGiftMessage(), TestData.GIFT_MESSAGE);
    CheckoutConfirmationPage confirmationPage = checkoutPlaceOrderPage.placeOrder();

    logger.info("Verify Confirmation page is displayed");
    Assert.assertTrue(
        confirmationPage.getConfirmationMessage().contains("THANK YOU FOR YOUR PURCHASE!"),
        "Order was not placed");

    logger.info("Order Placed: " + confirmationPage.getOrderNumber());
  }
}
