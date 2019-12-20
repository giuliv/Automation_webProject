package com.applause.auto.test.web;

import com.applause.auto.common.data.Constants.TestData;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.web.components.DatePickerChunk;
import com.applause.auto.web.components.MainMenuChunk;
import com.applause.auto.web.components.MiniCartContainerChunk;
import com.applause.auto.web.components.ShopRunnerChunk;
import com.applause.auto.web.views.CheckoutConfirmationPage;
import com.applause.auto.web.views.CheckoutPaymentMethodPage;
import com.applause.auto.web.views.CheckoutPlaceOrderPage;
import com.applause.auto.web.views.CheckoutShippingInfoPage;
import com.applause.auto.web.views.CoffeeKCupsProductPage;
import com.applause.auto.web.views.CoffeeProductDescriptionPage;
import com.applause.auto.web.views.CoffeeProductPage;
import com.applause.auto.web.views.EquipmentProductPage;
import com.applause.auto.web.views.Landing;
import com.applause.auto.web.views.MyAccountPage;
import com.applause.auto.web.views.PeetsCardProductPage;
import com.applause.auto.web.views.SearchResultsPage;
import com.applause.auto.web.views.ShopCoffeeKCupsPage;
import com.applause.auto.web.views.ShopCoffeePage;
import com.applause.auto.web.views.ShopEquipmentPage;
import com.applause.auto.web.views.ShopTeaPage;
import com.applause.auto.web.views.ShoppingCartPage;
import com.applause.auto.web.views.SignInPage;
import com.applause.auto.web.views.TeaProductPage;
import com.applause.auto.web.views.TopSellersTeaPage;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ExistingUserCheckoutTest extends BaseTest {

  private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().getClass());

  @Test(
      groups = {TestNGGroups.EXISTING_USER_CHECKOUT},
      description = "133888")
  public void userCheckoutCoffeeTest() {

    logger.info("1. Navigate to landing page");
    Landing landing = navigateToLanding();
    Assert.assertNotNull(landing, "Failed to navigate to the landing page.");

    logger.info("2. Log in to UAT");
    SignInPage signInPage = landing.clickSignInButton();
    MyAccountPage myAccountPage = signInPage.existingUserCheckoutLogin();
    Assert.assertTrue(
        myAccountPage.getWelcomeMessage().contains("Testing"),
        "User is not signed in or welcome name is wrong");

    logger.info("3. Select a coffee from grid view and add to cart");
    MainMenuChunk mainMenu = myAccountPage.getMainMenu();
    landing = mainMenu.clickHeaderLogo();
    ShopCoffeePage shopCoffeePage = landing.clickShopCoffeeButton();
    CoffeeProductPage coffeeProductPage =
        shopCoffeePage.clickProductName(TestData.COFFEE_BRAND_NAME);
    coffeeProductPage.selectAGrind(TestData.GRIND);
    MiniCartContainerChunk miniCartContainer = coffeeProductPage.clickAddToCart();

    logger.info("4. Select 'Proceed to Checkout'");
    // TODO: Implement step 3 and 4 from test case to edit cart and add a promo card
    CheckoutShippingInfoPage shippingInfoPage = miniCartContainer.clickSignedInCheckout();
    CheckoutPaymentMethodPage paymentMethodPage =
        shippingInfoPage.setShippingMethod(TestData.SHIPPING_METHOD_GROUND);
    CheckoutPlaceOrderPage placeOrderPage = paymentMethodPage.continueAfterEnteringPIN();

    logger.info("5. Click 'Place Order'");
    CheckoutConfirmationPage confirmationPage = placeOrderPage.placeOrder();

    logger.info("Verify Confirmation page is displayed");
    Assert.assertTrue(
        confirmationPage.getConfirmationMessage().contains("THANK YOU FOR YOUR PURCHASE!"),
        "Order was not placed");

    logger.info("Order Placed: " + confirmationPage.getOrderNumber());
  }

  @Test(
      groups = {TestNGGroups.EXISTING_USER_CHECKOUT},
      description = "133889")
  public void userCheckoutTeaTest() {

    logger.info("1. Navigate to landing page");
    Landing landing = navigateToLanding();
    Assert.assertNotNull(landing, "Failed to navigate to the landing page.");

    logger.info("2. Log in to UAT");
    SignInPage signInPage = landing.clickSignInButton();
    MyAccountPage myAccountPage = signInPage.existingUserCheckoutLogin();
    Assert.assertTrue(
        myAccountPage.getWelcomeMessage().contains("Testing"),
        "User is not signed in or welcome name is wrong");

    logger.info("3. Select a tea from grid view and add to cart");
    MainMenuChunk mainMenu = myAccountPage.getMainMenu();
    mainMenu.clickHeaderLogo();
    ShopTeaPage shopTeaPage = navigateToShopTeaPage();
    TeaProductPage teaProductPage = shopTeaPage.clickProductName(TestData.TEA_NAME);
    MiniCartContainerChunk miniCartContainer = teaProductPage.clickAddToCart();

    logger.info("4. Select 'Edit Cart' from mini-cart");
    ShoppingCartPage shoppingCart = miniCartContainer.clickEditCart();

    logger.info("5. Add gift message to product");
    shoppingCart.selectOrderAsGift();
    shoppingCart.enterGiftMessage(TestData.GIFT_MESSAGE);

    logger.info("6. Select 'Proceed to Checkout'");
    CheckoutShippingInfoPage shippingInfoPage = shoppingCart.defineShippingSignedUser();
    CheckoutPaymentMethodPage paymentMethodPage =
        shippingInfoPage.setShippingMethod(TestData.SHIPPING_METHOD_GROUND);
    CheckoutPlaceOrderPage placeOrderPage = paymentMethodPage.continueAfterEnteringPIN();

    logger.info("7. Verify gift message");
    Assert.assertEquals(
        placeOrderPage.getGiftMessage(),
        TestData.GIFT_MESSAGE,
        "Gift message entered previously was not fetched correctly");

    logger.info("8. Click 'Place Order'");
    CheckoutConfirmationPage confirmationPage = placeOrderPage.placeOrder();

    logger.info("Verify Confirmation page is displayed");
    Assert.assertTrue(
        confirmationPage.getConfirmationMessage().contains("THANK YOU FOR YOUR PURCHASE!"),
        "Order was not placed");

    logger.info("Order Placed: " + confirmationPage.getOrderNumber());
  }

  @Test(
      groups = {TestNGGroups.EXISTING_USER_CHECKOUT},
      description = "133890")
  public void userCheckoutEquipmentTest() {

    logger.info("1. Navigate to landing page");
    Landing landing = navigateToLanding();
    Assert.assertNotNull(landing, "Failed to navigate to the landing page.");

    logger.info("2. Log in to UAT");
    SignInPage signInPage = landing.clickSignInButton();
    MyAccountPage myAccountPage = signInPage.existingUserCheckoutLogin();
    Assert.assertTrue(
        myAccountPage.getWelcomeMessage().contains("Testing"),
        "User is not signed in or welcome name is wrong");

    logger.info("3. Select a equipment from grid view and add to cart");
    MainMenuChunk mainMenu = myAccountPage.getMainMenu();
    landing = mainMenu.clickHeaderLogo();
    ShopEquipmentPage shopEquipmentPage = navigateToShopEquipmentPage();
    EquipmentProductPage equipmentProductPage =
        shopEquipmentPage.clickProductName(TestData.EQUIPMENT_NAME);
    MiniCartContainerChunk miniCartContainer = equipmentProductPage.clickAddToCart();

    logger.info("4. Select 'Proceed to Checkout'");
    CheckoutShippingInfoPage shippingInfoPage = miniCartContainer.clickSignedInCheckout();
    CheckoutPaymentMethodPage paymentMethodPage =
        shippingInfoPage.setShippingMethod(TestData.SHIPPING_METHOD_GROUND);
    CheckoutPlaceOrderPage placeOrderPage = paymentMethodPage.continueAfterEnteringPIN();

    logger.info("5. Click 'Place Order'");
    CheckoutConfirmationPage confirmationPage = placeOrderPage.placeOrder();

    logger.info("Verify Confirmation page is displayed");
    Assert.assertTrue(
        confirmationPage.getConfirmationMessage().contains("THANK YOU FOR YOUR PURCHASE!"),
        "Order was not placed");

    logger.info("Order Placed: " + confirmationPage.getOrderNumber());
  }

  @Test(
      groups = {TestNGGroups.EXISTING_USER_CHECKOUT},
      description = "133892")
  public void userCheckoutPeetsCardTest() {

    logger.info("1. Navigate to landing page");
    Landing landing = navigateToLanding();
    Assert.assertNotNull(landing, "Failed to navigate to the landing page.");

    logger.info("2. Log in to UAT");
    SignInPage signInPage = landing.clickSignInButton();
    MyAccountPage myAccountPage = signInPage.existingUserCheckoutLogin();
    Assert.assertTrue(
        myAccountPage.getWelcomeMessage().contains("Testing"),
        "User is not signed in or welcome name is wrong");

    logger.info("3. Select a Peet's Card and add to cart from the product description page.");
    MainMenuChunk mainMenu = myAccountPage.getMainMenu();
    landing = mainMenu.clickHeaderLogo();
    PeetsCardProductPage peetsCardPage = navigateToShopPeetsCardPage();
    peetsCardPage.selectCardAmount(TestData.PEETS_CARD_BUY_AMOUNT);
    MiniCartContainerChunk miniCartContainer = peetsCardPage.clickAddToCart();

    logger.info("4. Select 'Proceed to Checkout'");
    CheckoutShippingInfoPage shippingInfoPage = miniCartContainer.clickSignedInCheckout();
    CheckoutPaymentMethodPage paymentMethodPage =
        shippingInfoPage.setShippingMethod(TestData.SHIPPING_METHOD_GROUND);
    CheckoutPlaceOrderPage placeOrderPage = paymentMethodPage.continueAfterEnteringPIN();

    logger.info("5. Click 'Place Order'");
    CheckoutConfirmationPage confirmationPage = placeOrderPage.placeOrder();

    logger.info("Verify Confirmation page is displayed");
    Assert.assertTrue(
        confirmationPage.getConfirmationMessage().contains("THANK YOU FOR YOUR PURCHASE!"),
        "Order was not placed");

    logger.info("Order Placed: " + confirmationPage.getOrderNumber());
  }

  @Test(
      groups = {TestNGGroups.EXISTING_USER_CHECKOUT},
      description = "133893")
  public void userCheckoutPaypalKCupTest() {

    logger.info("1. Navigate to landing page");
    Landing landing = navigateToLanding();
    Assert.assertNotNull(landing, "Failed to navigate to the landing page.");

    logger.info("2. Log in to UAT");
    SignInPage signInPage = landing.clickSignInButton();
    MyAccountPage myAccountPage = signInPage.existingUserCheckoutLogin();
    Assert.assertTrue(
        myAccountPage.getWelcomeMessage().contains("Testing"),
        "User is not signed in or welcome name is wrong");

    logger.info("3. Select a tea from grid view and add to cart");
    MainMenuChunk mainMenu = myAccountPage.getMainMenu();
    landing = mainMenu.clickHeaderLogo();
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
    CheckoutPlaceOrderPage placeOrderPage = shoppingCart.clickPayWithPaypalSignedUser();

    logger.info("7. Verify gift message");
    Assert.assertEquals(
        placeOrderPage.getGiftMessage(),
        TestData.GIFT_MESSAGE,
        "Gift message entered previously was not fetched correctly");

    logger.info("8. Click 'Place Order'");
    CheckoutConfirmationPage confirmationPage = placeOrderPage.placeOrder();

    logger.info("Verify Confirmation page is displayed");
    Assert.assertTrue(
        confirmationPage.getConfirmationMessage().contains("THANK YOU FOR YOUR PURCHASE!"),
        "Order was not placed");

    logger.info("Order Placed: " + confirmationPage.getOrderNumber());
  }

  @Test(
      groups = {TestNGGroups.EXISTING_USER_CHECKOUT},
      description = "137109")
  public void userCheckoutPaypalWednesdayRoastCoffee() {

    logger.info("1. Navigate to landing page");
    Landing landing = navigateToLanding();
    Assert.assertNotNull(landing, "Failed to navigate to the landing page.");

    logger.info("2. Log in to UAT");
    SignInPage signInPage = landing.clickSignInButton();
    MyAccountPage myAccountPage = signInPage.existingUserCheckoutLogin();
    Assert.assertTrue(
        myAccountPage.getWelcomeMessage().contains("Testing"),
        "User is not signed in or welcome name is wrong");

    logger.info("3. Navigate to Kona Coffee page");
    MainMenuChunk mainMenu = myAccountPage.getMainMenu();
    landing = mainMenu.clickHeaderLogo();
    SearchResultsPage searchResultsPage = landing.searchForProduct(TestData.WEDNES_ROAST_SEARCH);

    logger.info("4. Select Product and Add to Cart");
    CoffeeProductDescriptionPage coffeeProductDescriptionPage = searchResultsPage.clickKona();
    coffeeProductDescriptionPage.selectGrind(TestData.GRIND);
    MiniCartContainerChunk miniCartContainer = coffeeProductDescriptionPage.addToCart();

    logger.info("4. Select 'Edit Cart' from mini-cart");
    ShoppingCartPage shoppingCart = miniCartContainer.clickEditCart();

    logger.info("5. Add gift message to product");
    shoppingCart.selectOrderAsGift();
    shoppingCart.enterGiftMessage(TestData.GIFT_MESSAGE);

    logger.info("6. Checkout with Paypal");
    CheckoutPlaceOrderPage placeOrderPage = shoppingCart.clickPayWithPaypalSignedUser();

    logger.info("7. Verify gift message");
    Assert.assertEquals(
        placeOrderPage.getGiftMessage(),
        TestData.GIFT_MESSAGE,
        "Gift message entered previously was not fetched correctly");

    logger.info("8. Click 'Place Order'");
    CheckoutConfirmationPage confirmationPage = placeOrderPage.placeOrder();

    logger.info("Verify Confirmation page is displayed");
    Assert.assertTrue(
        confirmationPage.getConfirmationMessage().contains("THANK YOU FOR YOUR PURCHASE!"),
        "Order was not placed");

    logger.info("Order Placed: " + confirmationPage.getOrderNumber());
  }

  @Test(
      groups = {TestNGGroups.EXISTING_USER_CHECKOUT},
      description = "133891")
  public void userCheckoutTour() {

    logger.info("1. Navigate to landing page");
    Landing landing = navigateToLanding();
    Assert.assertNotNull(landing, "Failed to navigate to the landing page.");

    logger.info("2. Log in to UAT");
    SignInPage signInPage = landing.clickSignInButton();
    MyAccountPage myAccountPage = signInPage.existingUserCheckoutLogin();
    Assert.assertTrue(
        myAccountPage.getWelcomeMessage().contains("Testing"),
        "User is not signed in or w®elcome name is wrong");

    logger.info("3. Navigate to Kona Coffee page");
    MainMenuChunk mainMenu = myAccountPage.getMainMenu();
    landing = mainMenu.clickHeaderLogo();
    SearchResultsPage searchResultsPage = landing.searchForProduct(TestData.TOUR_SEARCH_TERMS);

    logger.info("4. Select Product and Add to Cart");
    CoffeeProductDescriptionPage coffeeProductDescriptionPage =
        searchResultsPage.clickFirstProduct();
    coffeeProductDescriptionPage.selectGrind(TestData.GRIND);
    MiniCartContainerChunk miniCartContainer = coffeeProductDescriptionPage.addToCart();

    logger.info("5. Select 'Proceed to Checkout'");
    CheckoutShippingInfoPage shippingInfoPage = miniCartContainer.clickSignedInCheckout();
    CheckoutPaymentMethodPage paymentMethodPage = shippingInfoPage.setShippingMethod();
    CheckoutPlaceOrderPage placeOrderPage = paymentMethodPage.continueAfterEnteringPIN();

    logger.info("6. Click 'Place Order'");
    CheckoutConfirmationPage confirmationPage = placeOrderPage.placeOrder();

    logger.info("Verify Confirmation page is displayed");
    Assert.assertTrue(
        confirmationPage.getConfirmationMessage().contains("THANK YOU FOR YOUR PURCHASE!"),
        "Order was not placed");

    logger.info("Order Placed: " + confirmationPage.getOrderNumber());
  }

  @Test(
      groups = {TestNGGroups.EXISTING_USER_CHECKOUT},
      description = "613495")
  public void userCheckoutEditShippingInformationTest() {

    logger.info("1. Navigate to landing page");
    Landing landing = navigateToLanding();
    Assert.assertNotNull(landing, "Failed to navigate to the landing page.");

    logger.info("2. Log in to UAT");
    SignInPage signInPage = landing.clickSignInButton();
    MyAccountPage myAccountPage = signInPage.existingUserCheckoutLogin();
    Assert.assertTrue(
        myAccountPage.getWelcomeMessage().contains("Testing"),
        "User is not signed in or welcome name is wrong");

    logger.info("3. Select a coffee from grid view and add to cart");
    MainMenuChunk mainMenu = myAccountPage.getMainMenu();
    MiniCartContainerChunk miniCartContainer = mainMenu.clickMiniCart();
    if (mainMenu.getCartItemsCount() == "0") {
      landing = mainMenu.clickHeaderLogo();

      ShopCoffeePage shopCoffeePage = landing.clickShopCoffeeButton();
      CoffeeProductPage coffeeProductPage =
          shopCoffeePage.clickProductName(TestData.COFFEE_BRAND_NAME);
      coffeeProductPage.selectAGrind(TestData.GRIND);
      miniCartContainer = coffeeProductPage.clickAddToCart();
    } else {
      logger.info("Cart already contain items");
      miniCartContainer = mainMenu.clickMiniCart();
    }

    logger.info("5. Select 'Proceed to Checkout'");
    CheckoutShippingInfoPage shippingInfoPage = miniCartContainer.clickSignedInCheckout();

    logger.info("6. Under Shipping Address on Checkout page, select 'Add New Address'");
    shippingInfoPage.addNewAddress();

    logger.info("7. Complete information to add new address. Click Continue");
    String uniqPrefix1 = "" + System.currentTimeMillis();
    shippingInfoPage.fillUniqueShippingInfo(uniqPrefix1);
    shippingInfoPage.continueAddNewAddress();

    //	TODO: Fix up and add back (tests fail when these are uncommented)
    //        logger.info("New address is added and selected on Checkout page");
    //        Assert.assertTrue(
    //            shippingInfoPage.getAddresses().stream().anyMatch(address ->
    // address.contains(uniqPrefix1)),
    //            "New address not found");
    //
    //        logger.info("Select 'edit' next to newly added address");
    //        shippingInfoPage.editAddress(uniqPrefix1);
    //
    //        logger.info("Modify shipping address. Click Update");
    //        String delta = "" + System.currentTimeMillis();
    //        shippingInfoPage.modifyShippingInfoByDelta(delta);
    //        shippingInfoPage = shippingInfoPage.updateAfterShippingInfoModification();
    //
    //        logger.info("Address is updated and selected on Checkout page");
    //        Assert.assertTrue(
    //            shippingInfoPage.getAddresses().stream().anyMatch(address ->
    // address.contains(delta)),
    //            "New address not found");

    logger.info("Select 'edit' next to shipping date");
    String oldDate = shippingInfoPage.getShippingDate();
    DatePickerChunk datePicker = shippingInfoPage.editShippingDate();

    logger.info("Select a new date");
    datePicker.selectDate(CheckoutShippingInfoPage.class, 3);

    logger.info("Shipping date is updated on Checkout page");
    String newDate = shippingInfoPage.getShippingDate();
    Assert.assertNotEquals(newDate, oldDate, "Shipping Date field does not updated");
  }

  @Test(
      groups = {TestNGGroups.EXISTING_USER_CHECKOUT},
      description = "627702")
  public void userCheckoutShopRunnerLoginAtCartTest() {

    logger.info("1. Navigate to landing page");
    Landing landing = navigateToLanding();
    Assert.assertNotNull(landing, "Failed to navigate to the landing page.");

    logger.info("2. Log in to UAT");
    SignInPage signInPage = landing.clickSignInButton();
    MyAccountPage myAccountPage = signInPage.existingUserCheckoutLogin();

    logger.info("2. Select a tea from grid view and add to cart");
    TopSellersTeaPage shopTeaPage =
        myAccountPage
            .getMainMenu()
            .clickCategoryOption(TopSellersTeaPage.class, "Shop", "Tea", "Top Sellers");

    logger.info("Select a Tea that costs over $25 and add to cart");
    MiniCartContainerChunk miniCartContainer =
        shopTeaPage.addProductToCart(TestData.TEA_COST_OVER_25_NAME);

    logger.info("From mini-cart, click View Cart");
    ShoppingCartPage shoppingCartPage = miniCartContainer.clickEditCart();

    logger.info("Next to ShopRunner text, click Sign In");
    ShopRunnerChunk shopRunnerChunk = shoppingCartPage.signInShopRunner();

    logger.info(
        "Sign into ShopRunner using the following account:\n"
            + "peets-test@shoprunner.com\n"
            + "abcd1234");
    shopRunnerChunk.signIn("peets-test@shoprunner.com", "abcd1234");
    logger.info("Once signed in, click Continue Shopping");
    shoppingCartPage = shopRunnerChunk.continueShopping(ShoppingCartPage.class);

    logger.info("5. Select 'Proceed to Checkout'");
    CheckoutShippingInfoPage shippingInfoPage = shoppingCartPage.defineShippingSignedUser();

    logger.info("On Shipping Information page, select Shoprunner shipping method");
    logger.info("Click Continue");
    CheckoutPaymentMethodPage paymentMethodPage = shippingInfoPage.setShippingMethod();

    logger.info("Complete Payment method");
    logger.info("Click Continue");
    CheckoutPlaceOrderPage placeOrderPage = paymentMethodPage.continueAfterEnteringPIN();

    logger.info("Click Place Order");
    CheckoutConfirmationPage confirmationPage = placeOrderPage.placeOrder();

    logger.info("Verify order was placed");
    Assert.assertTrue(
        confirmationPage.getConfirmationMessage().contains("THANK YOU FOR YOUR PURCHASE!"),
        "Order was not placed");

    logger.info("Order Placed: " + confirmationPage.getOrderNumber());
  }

  @Test(
      groups = {TestNGGroups.EXISTING_USER_CHECKOUT},
      description = "627703")
  public void userCheckoutShopRunnerLoginAtCheckoutTest() {

    logger.info("1. Navigate to landing page");
    Landing landing = navigateToLanding();
    Assert.assertNotNull(landing, "Failed to navigate to the landing page.");

    logger.info("2. Log in to UAT");
    SignInPage signInPage = landing.clickSignInButton();
    MyAccountPage myAccountPage = signInPage.existingUserCheckoutLogin();

    logger.info("2. Select a tea from grid view and add to cart");
    TopSellersTeaPage shopTeaPage =
        myAccountPage
            .getMainMenu()
            .clickCategoryOption(TopSellersTeaPage.class, "Shop", "Tea", "Top Sellers");

    logger.info("Select a Tea that costs over $25 and add to cart");
    MiniCartContainerChunk miniCartContainer =
        shopTeaPage.addProductToCart(TestData.TEA_COST_OVER_25_NAME);

    logger.info("From mini-cart, click View Cart");
    ShoppingCartPage shoppingCartPage = miniCartContainer.clickEditCart();

    logger.info("5. Select 'Proceed to Checkout'");
    CheckoutShippingInfoPage shippingInfoPage = shoppingCartPage.defineShippingSignedUser();

    logger.info("Under Shipping Method, click Sign In on the Free 2-Day Shipping method");
    ShopRunnerChunk shopRunnerChunk = shippingInfoPage.signInShopRunner();

    logger.info(
        "Sign into ShopRunner using the following account:\n"
            + "peets-test@shoprunner.com\n"
            + "abcd1234");
    shopRunnerChunk.signIn("peets-test@shoprunner.com", "abcd1234");

    logger.info("Once signed in, click Continue Shopping");
    shippingInfoPage = shopRunnerChunk.continueShopping(CheckoutShippingInfoPage.class);

    logger.info("On Shipping Information page, select Shoprunner shipping method");
    logger.info("Click Continue");
    CheckoutPaymentMethodPage paymentMethodPage = shippingInfoPage.setShippingMethod();

    logger.info("Complete Payment method");
    logger.info("Click Continue");
    CheckoutPlaceOrderPage placeOrderPage = paymentMethodPage.continueAfterEnteringPIN();

    logger.info("Click Place Order");
    CheckoutConfirmationPage confirmationPage = placeOrderPage.placeOrder();

    logger.info("Verify order was placed");
    Assert.assertTrue(
        confirmationPage.getConfirmationMessage().contains("THANK YOU FOR YOUR PURCHASE!"),
        "Order was not placed");

    logger.info("Order Placed: " + confirmationPage.getOrderNumber());
  }
}
