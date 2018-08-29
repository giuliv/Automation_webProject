package com.applause.auto.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.applause.auto.pageframework.chunks.MainMenuChunk;
import com.applause.auto.pageframework.chunks.MiniCartContainerChunk;
import com.applause.auto.pageframework.pages.CheckoutConfirmationPage;
import com.applause.auto.pageframework.pages.CheckoutPlaceOrderPage;
import com.applause.auto.pageframework.pages.CoffeeProductPage;
import com.applause.auto.pageframework.pages.LandingPage;
import com.applause.auto.pageframework.pages.MyAccountPage;
import com.applause.auto.pageframework.pages.ShopCoffeePage;
import com.applause.auto.pageframework.pages.SignInPage;
import com.applause.auto.pageframework.testdata.TestConstants;
import com.applause.auto.pageframework.testdata.TestConstants.TestNGGroups;

public class ExistingUserCheckoutTest extends BaseTest {

	@Test(groups = { TestNGGroups.EXISTING_USER_CHECKOUT }, description = "133888")
	public void userCheckoutCoffeeTest() {

		LOGGER.info("1. Navigate to landing page");
		LandingPage landingPage = navigateToLandingPage();
		Assert.assertNotNull(landingPage, "Failed to navigate to the landing page.");

		LOGGER.info("2. Log in to UAT");
		SignInPage signInPage = landingPage.clickSignInButton();
		MyAccountPage myAccountPage = signInPage.mainUserLogin();
		Assert.assertTrue(myAccountPage.getWelcomeMessage().contains("Applause"),
				"User is not signed in or welcome name is wrong");

		LOGGER.info("2. Select a coffee from grid view and add to cart");
		MainMenuChunk mainMenu = myAccountPage.getMainMenu();
		landingPage = mainMenu.clickHeaderLogo();
		ShopCoffeePage shopCoffeePage = landingPage.clickShopCoffeeButton();
		CoffeeProductPage coffeeProductPage = shopCoffeePage.clickProductName(TestConstants.TestData.COFFEE_BRAND_NAME);
		coffeeProductPage.selectAGrind(TestConstants.TestData.GRIND);
		MiniCartContainerChunk miniCartContainer = coffeeProductPage.clickAddToCart();

		LOGGER.info("3. Select 'Proceed to Checkout'");
		// TODO: Implement step 3 and 4 from test case to edit cart and add a promo card
		CheckoutPlaceOrderPage placeOrderPage = miniCartContainer.checkoutSignedInUser();

		LOGGER.info("4. Click 'Place Order'");
		CheckoutConfirmationPage confirmationPage = placeOrderPage.placeOrder();

		LOGGER.info("Verify Confirmation page is displayed");
		Assert.assertTrue(confirmationPage.getConfirmationMessage().contains("THANK YOU FOR YOUR PURCHASE!"),
				"Order was not placed");

		LOGGER.info("Order Placed: " + confirmationPage.getOrderNumber());
	}
}
