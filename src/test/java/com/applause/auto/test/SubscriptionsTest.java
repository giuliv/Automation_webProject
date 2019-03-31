package com.applause.auto.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.applause.auto.pageframework.chunks.CreateSubscriptionChunk;
import com.applause.auto.pageframework.chunks.MainMenuChunk;
import com.applause.auto.pageframework.chunks.MiniCartContainerChunk;
import com.applause.auto.pageframework.pages.CheckoutConfirmationPage;
import com.applause.auto.pageframework.pages.CheckoutPaymentMethodPage;
import com.applause.auto.pageframework.pages.CheckoutPlaceOrderPage;
import com.applause.auto.pageframework.pages.CheckoutShippingInfoPage;
import com.applause.auto.pageframework.pages.CoffeeProductPage;
import com.applause.auto.pageframework.pages.LandingPage;
import com.applause.auto.pageframework.pages.MyAccountPage;
import com.applause.auto.pageframework.pages.ShopCoffeePage;
import com.applause.auto.pageframework.pages.ShoppingCartPage;
import com.applause.auto.pageframework.pages.SignInPage;
import com.applause.auto.pageframework.testdata.TestConstants;
import com.applause.auto.pageframework.testdata.TestConstants.TestData;
import com.applause.auto.pageframework.testdata.TestConstants.TestNGGroups;

public class SubscriptionsTest extends BaseTest {

	@Test(groups = { TestNGGroups.SUBSCRIPTIONS }, description = "613496")
	public void userCreateNewSubscriptionTest() {

		LOGGER.info("1. Navigate to landing page");
		LandingPage landingPage = navigateToLandingPage();
		Assert.assertNotNull(landingPage, "Failed to navigate to the landing page.");

		LOGGER.info("2. Log in to UAT");
		SignInPage signInPage = landingPage.clickSignInButton();
		MyAccountPage myAccountPage = signInPage.mainUserLogin();
		Assert.assertTrue(myAccountPage.getWelcomeMessage().contains("Applause"),
				"User is not signed in or welcome name is wrong");

		LOGGER.info("3. Select a coffee from grid view and add to cart");
		MainMenuChunk mainMenu = myAccountPage.getMainMenu();
		while (!mainMenu.getCartItemsCount().equals("0")) {
			LOGGER.info("Cleanup cart");
			MiniCartContainerChunk miniCart = mainMenu.clickMiniCart();
			String itemName = miniCart.getItems().get(0);
			miniCart.remove(itemName);
			mainMenu.closeMiniCart(LandingPage.class);
		}
		landingPage = mainMenu.clickHeaderLogo();
		ShopCoffeePage shopCoffeePage = landingPage.clickShopCoffeeButton();
		CoffeeProductPage coffeeProductPage = shopCoffeePage.clickProductName(TestData.COFFEE_BRAND_NAME);
		coffeeProductPage.selectAGrind(TestData.GRIND);

		coffeeProductPage.selectSubscription();
		CreateSubscriptionChunk createSubscriptionChunk = coffeeProductPage.clickAddToSubscription();

		LOGGER.info("In the popup, select Create A New Subscription");
		createSubscriptionChunk.selectNewSubscription();

		LOGGER.info("Give the subscription a name");
		String subscriptionName = "sn" + System.currentTimeMillis();
		createSubscriptionChunk.setNewSubscriptionName(subscriptionName);

		LOGGER.info("Select frequency");
		createSubscriptionChunk.selectFrequency(TestConstants.SubscriptionTerm.TWO_WEEKS);

		LOGGER.info("Select Create Subscription");
		MiniCartContainerChunk miniCart = createSubscriptionChunk.createSubscription();

		LOGGER.info("From mini-cart, select View Cart");
		ShoppingCartPage cartPage = miniCart.clickEditCart();

		LOGGER.info("Verify Subscription Name");
		Assert.assertEquals(cartPage.getSubscriptionName(), subscriptionName, "Subscription name does not match");

		LOGGER.info("Verify Frequency");
		Assert.assertEquals(cartPage.getSubscriptionFrequency(), TestConstants.SubscriptionTerm.TWO_WEEKS.fullCartSpell,
				"Subscription frequency does not match");

		LOGGER.info("Verify there is a Product Discount and Shipping Discount under Order Summary");
		Assert.assertTrue(cartPage.isProductDiscountPriceDisplayed(), "Product discount does not displayed");
		Assert.assertTrue(cartPage.isShippingDiscountPriceDisplayed(), "Shipping discount does not displayed");

		LOGGER.info("Select Proceed to Checkout");
		CheckoutShippingInfoPage shippingInfoPage = cartPage.defineShippingSignedUser();

		LOGGER.info("Select Shipping Address & Shipping Method");
		CheckoutPaymentMethodPage paymentMethodPage = shippingInfoPage
				.setShippingMethod(TestConstants.TestData.SHIPPING_METHOD_GROUND);

		LOGGER.info("Select Payment Method & Billing Address");
		CheckoutPlaceOrderPage checkoutPlaceOrderPage = paymentMethodPage.continueAfterEnteringPIN();

		LOGGER.info("Place Order");
		CheckoutConfirmationPage checkoutConfirmationPage = checkoutPlaceOrderPage.placeOrder();

		LOGGER.info("On Purchase Complete screen, verify user is shown a subscription number");
		Assert.assertTrue(checkoutConfirmationPage.getSubscriptionNumber().matches("\\d+"),
				"Subscription number does not displayed");

	}
}
