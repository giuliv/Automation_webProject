package com.applause.auto.test.web;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.Constants.TestData;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.util.helper.SyncHelper;
import com.applause.auto.web.components.CreateSubscriptionChunk;
import com.applause.auto.web.components.MainMenuChunk;
import com.applause.auto.web.components.MiniCartContainerChunk;
import com.applause.auto.web.views.CheckoutConfirmationPage;
import com.applause.auto.web.views.CheckoutPaymentMethodPage;
import com.applause.auto.web.views.CheckoutPlaceOrderPage;
import com.applause.auto.web.views.CheckoutShippingInfoPage;
import com.applause.auto.web.views.CoffeeProductPage;
import com.applause.auto.web.views.Landing;
import com.applause.auto.web.views.MyAccountPage;
import com.applause.auto.web.views.ShopCoffeePage;
import com.applause.auto.web.views.ShoppingCartPage;
import com.applause.auto.web.views.SignInPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.invoke.MethodHandles;

public class SubscriptionsTest extends BaseTest {

  private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().getClass());

  @Test(
      groups = {TestNGGroups.SUBSCRIPTIONS},
      description = "613496")
  public void userCreateNewSubscriptionTest() {

    logger.info("1. Navigate to landing page");
    Landing landing = navigateToLanding();
    Assert.assertNotNull(landing, "Failed to navigate to the landing page.");

    logger.info("2. Log in to UAT");
    SignInPage signInPage = landing.clickSignInButton();
    MyAccountPage myAccountPage = signInPage.mainUserLogin();
    Assert.assertTrue(
        myAccountPage.getWelcomeMessage().contains("Applause"),
        "User is not signed in or welcome name is wrong");

    logger.info("3. Select a coffee from grid view and add to cart");
    MainMenuChunk mainMenu = myAccountPage.getMainMenu();
    landing = mainMenu.clickHeaderLogo();
    ShopCoffeePage shopCoffeePage = landing.clickShopCoffeeButton();
    CoffeeProductPage coffeeProductPage =
        shopCoffeePage.clickProductName(TestData.COFFEE_BRAND_NAME);
    coffeeProductPage.selectAGrind(TestData.GRIND);

    coffeeProductPage.selectSubscription();
    CreateSubscriptionChunk createSubscriptionChunk = coffeeProductPage.clickAddToSubscription();

    logger.info("In the popup, select Create A New Subscription");
    createSubscriptionChunk.selectNewSubscription();

    logger.info("Give the subscription a name");
    String subscriptionName = "sn" + System.currentTimeMillis();
    createSubscriptionChunk.setNewSubscriptionName(subscriptionName);

    logger.info("Select frequency");
    createSubscriptionChunk.selectFrequency(Constants.SubscriptionTerm.TWO_WEEKS);

    logger.info("Select Create Subscription");
    MiniCartContainerChunk miniCart = createSubscriptionChunk.createSubscription();

    logger.info("From mini-cart, select View Cart");
    SyncHelper.sleep(10000);
    ShoppingCartPage cartPage = miniCart.clickEditCart();

    logger.info("Verify Subscription Name");
    Assert.assertEquals(
        cartPage.getSubscriptionName(), subscriptionName, "Subscription name does not match");

    logger.info("Verify Frequency");
    Assert.assertEquals(
        cartPage.getSubscriptionFrequency(),
        Constants.SubscriptionTerm.TWO_WEEKS.fullCartSpell,
        "Subscription frequency does not match");

    logger.info("Verify there is a Product Discount and Shipping Discount under Order Summary");
    Assert.assertTrue(
        cartPage.isProductDiscountPriceDisplayed(), "Product discount does not displayed");
    Assert.assertTrue(
        cartPage.isShippingDiscountPriceDisplayed(), "Shipping discount does not displayed");

    logger.info("Select Proceed to Checkout");
    CheckoutShippingInfoPage shippingInfoPage = cartPage.defineShippingSignedUser();

    logger.info("Select Shipping Address & Shipping Method");
    CheckoutPaymentMethodPage paymentMethodPage =
        shippingInfoPage.setShippingMethod(TestData.SHIPPING_METHOD_GROUND);

    logger.info("Select Payment Method & Billing Address");
    CheckoutPlaceOrderPage checkoutPlaceOrderPage = paymentMethodPage.continueAfterEnteringPIN();

    logger.info("Place Order");
    CheckoutConfirmationPage checkoutConfirmationPage = checkoutPlaceOrderPage.placeOrder();

    logger.info("On Purchase Complete screen, verify user is shown a subscription number");
    Assert.assertTrue(
        checkoutConfirmationPage.getSubscriptionNumber().matches("\\d+"),
        "Subscription number does not displayed");
  }

  @Test(
      groups = {TestNGGroups.SUBSCRIPTIONS},
      description = "613497")
  public void userCreateNewSubscriptionWithPayPalTest() {

    logger.info("1. Navigate to landing page");
    Landing landing = navigateToLanding();
    Assert.assertNotNull(landing, "Failed to navigate to the landing page.");

    logger.info("2. Log in to UAT");
    SignInPage signInPage = landing.clickSignInButton();
    MyAccountPage myAccountPage = signInPage.mainUserLogin();
    Assert.assertTrue(
        myAccountPage.getWelcomeMessage().contains("Applause"),
        "User is not signed in or welcome name is wrong");

    logger.info("3. Select a coffee from grid view and add to cart");
    MainMenuChunk mainMenu = myAccountPage.getMainMenu();
    landing = mainMenu.clickHeaderLogo();
    ShopCoffeePage shopCoffeePage = landing.clickShopCoffeeButton();
    CoffeeProductPage coffeeProductPage =
        shopCoffeePage.clickProductName(TestData.COFFEE_BRAND_NAME);
    coffeeProductPage.selectAGrind(TestData.GRIND);

    coffeeProductPage.selectSubscription();
    CreateSubscriptionChunk createSubscriptionChunk = coffeeProductPage.clickAddToSubscription();

    logger.info("In the popup, select Create A New Subscription");
    createSubscriptionChunk.selectNewSubscription();

    logger.info("Give the subscription a name");
    String subscriptionName = "sn" + System.currentTimeMillis();
    createSubscriptionChunk.setNewSubscriptionName(subscriptionName);

    logger.info("Select frequency");
    createSubscriptionChunk.selectFrequency(Constants.SubscriptionTerm.TWO_WEEKS);

    logger.info("Select Create Subscription");
    MiniCartContainerChunk miniCart = createSubscriptionChunk.createSubscription();

    logger.info("From mini-cart, select View Cart");
    SyncHelper.sleep(10000);
    ShoppingCartPage cartPage = miniCart.clickEditCart();

    logger.info("Verify Subscription Name");
    Assert.assertEquals(
        cartPage.getSubscriptionName(), subscriptionName, "Subscription name does not match");

    logger.info("Verify Frequency");
    Assert.assertEquals(
        cartPage.getSubscriptionFrequency(),
        Constants.SubscriptionTerm.TWO_WEEKS.fullCartSpell,
        "Subscription frequency does not match");

    logger.info("Verify there is a Product Discount and Shipping Discount under Order Summary");
    Assert.assertTrue(
        cartPage.isProductDiscountPriceDisplayed(), "Product discount does not displayed");
    Assert.assertTrue(
        cartPage.isShippingDiscountPriceDisplayed(), "Shipping discount does not displayed");

    logger.info("Select Checkout with Paypal");
    CheckoutPlaceOrderPage checkoutPlaceOrderPage = cartPage.clickPayWithPaypalSignedUser();

    logger.info("Place Order");
    CheckoutConfirmationPage checkoutConfirmationPage = checkoutPlaceOrderPage.placeOrder();

    logger.info("On Purchase Complete screen, verify user is shown a subscription number");
    Assert.assertTrue(
        checkoutConfirmationPage.getSubscriptionNumber().matches("\\d+"),
        "Subscription number does not displayed");
  }
}
