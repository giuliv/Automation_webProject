package com.applause.auto.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.applause.auto.framework.pageframework.util.TestHelper;
import com.applause.auto.pageframework.chunks.DatePickerChunk;
import com.applause.auto.pageframework.chunks.MainMenuChunk;
import com.applause.auto.pageframework.chunks.MiniCartContainerChunk;
import com.applause.auto.pageframework.pages.CheckoutConfirmationPage;
import com.applause.auto.pageframework.pages.CheckoutPaymentMethodPage;
import com.applause.auto.pageframework.pages.CheckoutPlaceOrderPage;
import com.applause.auto.pageframework.pages.CheckoutShippingInfoPage;
import com.applause.auto.pageframework.pages.CoffeeKCupsProductPage;
import com.applause.auto.pageframework.pages.CoffeeProductDescriptionPage;
import com.applause.auto.pageframework.pages.CoffeeProductPage;
import com.applause.auto.pageframework.pages.EquipmentProductPage;
import com.applause.auto.pageframework.pages.LandingPage;
import com.applause.auto.pageframework.pages.MyAccountPage;
import com.applause.auto.pageframework.pages.PeetsCardProductPage;
import com.applause.auto.pageframework.pages.SearchResultsPage;
import com.applause.auto.pageframework.pages.ShopCoffeeKCupsPage;
import com.applause.auto.pageframework.pages.ShopCoffeePage;
import com.applause.auto.pageframework.pages.ShopEquipmentPage;
import com.applause.auto.pageframework.pages.ShopTeaPage;
import com.applause.auto.pageframework.pages.ShoppingCartPage;
import com.applause.auto.pageframework.pages.SignInPage;
import com.applause.auto.pageframework.pages.TeaProductPage;
import com.applause.auto.pageframework.testdata.TestConstants;
import com.applause.auto.pageframework.testdata.TestConstants.TestData;
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

		LOGGER.info("3. Select a coffee from grid view and add to cart");
		MainMenuChunk mainMenu = myAccountPage.getMainMenu();
		landingPage = mainMenu.clickHeaderLogo();
		ShopCoffeePage shopCoffeePage = landingPage.clickShopCoffeeButton();
		CoffeeProductPage coffeeProductPage = shopCoffeePage.clickProductName(TestConstants.TestData.COFFEE_BRAND_NAME);
		coffeeProductPage.selectAGrind(TestConstants.TestData.GRIND);
		MiniCartContainerChunk miniCartContainer = coffeeProductPage.clickAddToCart();

		LOGGER.info("4. Select 'Proceed to Checkout'");
		// TODO: Implement step 3 and 4 from test case to edit cart and add a promo card
		CheckoutShippingInfoPage shippingInfoPage = miniCartContainer.clickSignedInCheckout();
		CheckoutPaymentMethodPage paymentMethodPage = shippingInfoPage
				.setShippingMethod(TestConstants.TestData.SHIPPING_METHOD_GROUND);
		CheckoutPlaceOrderPage placeOrderPage = paymentMethodPage.continueAfterEnteringPIN();

		LOGGER.info("5. Click 'Place Order'");
		CheckoutConfirmationPage confirmationPage = placeOrderPage.placeOrder();

		LOGGER.info("Verify Confirmation page is displayed");
		Assert.assertTrue(confirmationPage.getConfirmationMessage().contains("THANK YOU FOR YOUR PURCHASE!"),
				"Order was not placed");

		LOGGER.info("Order Placed: " + confirmationPage.getOrderNumber());
	}

	@Test(groups = { TestNGGroups.EXISTING_USER_CHECKOUT }, description = "133889")
	public void userCheckoutTeaTest() {

		LOGGER.info("1. Navigate to landing page");
		LandingPage landingPage = navigateToLandingPage();
		Assert.assertNotNull(landingPage, "Failed to navigate to the landing page.");

		LOGGER.info("2. Log in to UAT");
		SignInPage signInPage = landingPage.clickSignInButton();
		MyAccountPage myAccountPage = signInPage.mainUserLogin();
		Assert.assertTrue(myAccountPage.getWelcomeMessage().contains("Applause"),
				"User is not signed in or welcome name is wrong");

		LOGGER.info("3. Select a tea from grid view and add to cart");
		MainMenuChunk mainMenu = myAccountPage.getMainMenu();
		landingPage = mainMenu.clickHeaderLogo();
		ShopTeaPage shopTeaPage = navigateToShopTeaPage();
		TeaProductPage teaProductPage = shopTeaPage.clickProductName(TestConstants.TestData.TEA_NAME);
		MiniCartContainerChunk miniCartContainer = teaProductPage.clickAddToCart();

		LOGGER.info("4. Select 'Edit Cart' from mini-cart");
		ShoppingCartPage shoppingCart = miniCartContainer.clickEditCart();

		LOGGER.info("5. Add gift message to product");
		shoppingCart.selectOrderAsGift();
		shoppingCart.enterGiftMessage(TestConstants.TestData.GIFT_MESSAGE);

		LOGGER.info("6. Select 'Proceed to Checkout'");
		CheckoutShippingInfoPage shippingInfoPage = shoppingCart.defineShippingSignedUser();
		CheckoutPaymentMethodPage paymentMethodPage = shippingInfoPage
				.setShippingMethod(TestConstants.TestData.SHIPPING_METHOD_GROUND);
		CheckoutPlaceOrderPage placeOrderPage = paymentMethodPage.continueAfterEnteringPIN();

		LOGGER.info("7. Verify gift message");
		Assert.assertEquals(placeOrderPage.getGiftMessage(), TestConstants.TestData.GIFT_MESSAGE,
				"Gift message entered previously was not fetched correctly");

		LOGGER.info("8. Click 'Place Order'");
		CheckoutConfirmationPage confirmationPage = placeOrderPage.placeOrder();

		LOGGER.info("Verify Confirmation page is displayed");
		Assert.assertTrue(confirmationPage.getConfirmationMessage().contains("THANK YOU FOR YOUR PURCHASE!"),
				"Order was not placed");

		LOGGER.info("Order Placed: " + confirmationPage.getOrderNumber());
	}

	@Test(groups = { TestNGGroups.EXISTING_USER_CHECKOUT }, description = "133890")
	public void userCheckoutEquipmentTest() {

		LOGGER.info("1. Navigate to landing page");
		LandingPage landingPage = navigateToLandingPage();
		Assert.assertNotNull(landingPage, "Failed to navigate to the landing page.");

		LOGGER.info("2. Log in to UAT");
		SignInPage signInPage = landingPage.clickSignInButton();
		MyAccountPage myAccountPage = signInPage.mainUserLogin();
		Assert.assertTrue(myAccountPage.getWelcomeMessage().contains("Applause"),
				"User is not signed in or welcome name is wrong");

		LOGGER.info("3. Select a equipment from grid view and add to cart");
		MainMenuChunk mainMenu = myAccountPage.getMainMenu();
		landingPage = mainMenu.clickHeaderLogo();
		ShopEquipmentPage shopEquipmentPage = navigateToShopEquipmentPage();
		EquipmentProductPage equipmentProductPage = shopEquipmentPage
				.clickProductName(TestConstants.TestData.EQUIPMENT_NAME);
		MiniCartContainerChunk miniCartContainer = equipmentProductPage.clickAddToCart();

		LOGGER.info("4. Select 'Proceed to Checkout'");
		CheckoutShippingInfoPage shippingInfoPage = miniCartContainer.clickSignedInCheckout();
		CheckoutPaymentMethodPage paymentMethodPage = shippingInfoPage
				.setShippingMethod(TestConstants.TestData.SHIPPING_METHOD_GROUND);
		CheckoutPlaceOrderPage placeOrderPage = paymentMethodPage.continueAfterEnteringPIN();

		LOGGER.info("5. Click 'Place Order'");
		CheckoutConfirmationPage confirmationPage = placeOrderPage.placeOrder();

		LOGGER.info("Verify Confirmation page is displayed");
		Assert.assertTrue(confirmationPage.getConfirmationMessage().contains("THANK YOU FOR YOUR PURCHASE!"),
				"Order was not placed");

		LOGGER.info("Order Placed: " + confirmationPage.getOrderNumber());
	}

	@Test(groups = { TestNGGroups.EXISTING_USER_CHECKOUT }, description = "133892")
	public void userCheckoutPeetsCardTest() {

		LOGGER.info("1. Navigate to landing page");
		LandingPage landingPage = navigateToLandingPage();
		Assert.assertNotNull(landingPage, "Failed to navigate to the landing page.");

		LOGGER.info("2. Log in to UAT");
		SignInPage signInPage = landingPage.clickSignInButton();
		MyAccountPage myAccountPage = signInPage.mainUserLogin();
		Assert.assertTrue(myAccountPage.getWelcomeMessage().contains("Applause"),
				"User is not signed in or welcome name is wrong");

		LOGGER.info("3. Select a Peet's Card and add to cart from the product description page.");
		MainMenuChunk mainMenu = myAccountPage.getMainMenu();
		landingPage = mainMenu.clickHeaderLogo();
		PeetsCardProductPage peetsCardPage = navigateToShopPeetsCardPage();
		peetsCardPage.selectCardAmount(TestData.PEETS_CARD_BUY_AMOUNT);
		MiniCartContainerChunk miniCartContainer = peetsCardPage.clickAddToCart();

		LOGGER.info("4. Select 'Proceed to Checkout'");
		CheckoutShippingInfoPage shippingInfoPage = miniCartContainer.clickSignedInCheckout();
		CheckoutPaymentMethodPage paymentMethodPage = shippingInfoPage
				.setShippingMethod(TestConstants.TestData.SHIPPING_METHOD_GROUND);
		CheckoutPlaceOrderPage placeOrderPage = paymentMethodPage.continueAfterEnteringPIN();

		LOGGER.info("5. Click 'Place Order'");
		CheckoutConfirmationPage confirmationPage = placeOrderPage.placeOrder();

		LOGGER.info("Verify Confirmation page is displayed");
		Assert.assertTrue(confirmationPage.getConfirmationMessage().contains("THANK YOU FOR YOUR PURCHASE!"),
				"Order was not placed");

		LOGGER.info("Order Placed: " + confirmationPage.getOrderNumber());
	}

	@Test(groups = { TestNGGroups.EXISTING_USER_CHECKOUT }, description = "133893")
	public void userCheckoutPaypalKCupTest() {

		LOGGER.info("1. Navigate to landing page");
		LandingPage landingPage = navigateToLandingPage();
		Assert.assertNotNull(landingPage, "Failed to navigate to the landing page.");

		LOGGER.info("2. Log in to UAT");
		SignInPage signInPage = landingPage.clickSignInButton();
		MyAccountPage myAccountPage = signInPage.mainUserLogin();
		Assert.assertTrue(myAccountPage.getWelcomeMessage().contains("Applause"),
				"User is not signed in or welcome name is wrong");

		LOGGER.info("3. Select a tea from grid view and add to cart");
		MainMenuChunk mainMenu = myAccountPage.getMainMenu();
		landingPage = mainMenu.clickHeaderLogo();
		ShopCoffeeKCupsPage shopCoffeeKCupsPage = navigateToShopCoffeeKCupsPage();
		CoffeeKCupsProductPage coffeeKCupsProductPage = shopCoffeeKCupsPage
				.clickProductName(TestConstants.TestData.COFFEE_KCUP_NAME);
		coffeeKCupsProductPage.selectBoxContent(TestConstants.TestData.COFFEE_KCUP_COUNT);
		MiniCartContainerChunk miniCartContainer = coffeeKCupsProductPage.clickAddToCart();

		LOGGER.info("4. Select 'Edit Cart' from mini-cart");
		ShoppingCartPage shoppingCart = miniCartContainer.clickEditCart();

		LOGGER.info("5. Add gift message to product");
		shoppingCart.selectOrderAsGift();
		shoppingCart.enterGiftMessage(TestConstants.TestData.GIFT_MESSAGE);

		LOGGER.info("6. Checkout with Paypal");
		CheckoutPlaceOrderPage placeOrderPage = shoppingCart.clickPayWithPaypalSignedUser();

		LOGGER.info("7. Verify gift message");
		Assert.assertEquals(placeOrderPage.getGiftMessage(), TestConstants.TestData.GIFT_MESSAGE,
				"Gift message entered previously was not fetched correctly");

		LOGGER.info("8. Click 'Place Order'");
		CheckoutConfirmationPage confirmationPage = placeOrderPage.placeOrder();

		LOGGER.info("Verify Confirmation page is displayed");
		Assert.assertTrue(confirmationPage.getConfirmationMessage().contains("THANK YOU FOR YOUR PURCHASE!"),
				"Order was not placed");

		LOGGER.info("Order Placed: " + confirmationPage.getOrderNumber());
	}

	@Test(groups = { TestNGGroups.EXISTING_USER_CHECKOUT }, description = "137109")
	public void userCheckoutPaypalWednesdayRoastCoffee() {

		LOGGER.info("1. Navigate to landing page");
		LandingPage landingPage = navigateToLandingPage();
		Assert.assertNotNull(landingPage, "Failed to navigate to the landing page.");

		LOGGER.info("2. Log in to UAT");
		SignInPage signInPage = landingPage.clickSignInButton();
		MyAccountPage myAccountPage = signInPage.mainUserLogin();
		Assert.assertTrue(myAccountPage.getWelcomeMessage().contains("Applause"),
				"User is not signed in or welcome name is wrong");

		LOGGER.info("3. Navigate to Kona Coffee page");
		MainMenuChunk mainMenu = myAccountPage.getMainMenu();
		landingPage = mainMenu.clickHeaderLogo();
		SearchResultsPage searchResultsPage = landingPage.searchForProduct(TestData.WEDNES_ROAST_SEARCH);

		LOGGER.info("4. Select Product and Add to Cart");
		CoffeeProductDescriptionPage coffeeProductDescriptionPage = searchResultsPage.clickKona();
		coffeeProductDescriptionPage.selectGrind(TestData.GRIND);
		MiniCartContainerChunk miniCartContainer = coffeeProductDescriptionPage.addToCart();

		LOGGER.info("4. Select 'Edit Cart' from mini-cart");
		ShoppingCartPage shoppingCart = miniCartContainer.clickEditCart();

		LOGGER.info("5. Add gift message to product");
		shoppingCart.selectOrderAsGift();
		shoppingCart.enterGiftMessage(TestConstants.TestData.GIFT_MESSAGE);

		LOGGER.info("6. Checkout with Paypal");
		CheckoutPlaceOrderPage placeOrderPage = shoppingCart.clickPayWithPaypalSignedUser();

		LOGGER.info("7. Verify gift message");
		Assert.assertEquals(placeOrderPage.getGiftMessage(), TestConstants.TestData.GIFT_MESSAGE,
				"Gift message entered previously was not fetched correctly");

		LOGGER.info("8. Click 'Place Order'");
		CheckoutConfirmationPage confirmationPage = placeOrderPage.placeOrder();

		LOGGER.info("Verify Confirmation page is displayed");
		Assert.assertTrue(confirmationPage.getConfirmationMessage().contains("THANK YOU FOR YOUR PURCHASE!"),
				"Order was not placed");

		LOGGER.info("Order Placed: " + confirmationPage.getOrderNumber());
	}

	@Test(groups = { TestNGGroups.EXISTING_USER_CHECKOUT }, description = "133891")
	public void userCheckoutTour() {

		LOGGER.info("1. Navigate to landing page");
		LandingPage landingPage = navigateToLandingPage();
		Assert.assertNotNull(landingPage, "Failed to navigate to the landing page.");

		LOGGER.info("2. Log in to UAT");
		SignInPage signInPage = landingPage.clickSignInButton();
		MyAccountPage myAccountPage = signInPage.mainUserLogin();
		Assert.assertTrue(myAccountPage.getWelcomeMessage().contains("Applause"),
				"User is not signed in or welcome name is wrong");

		LOGGER.info("3. Navigate to Kona Coffee page");
		MainMenuChunk mainMenu = myAccountPage.getMainMenu();
		landingPage = mainMenu.clickHeaderLogo();
		SearchResultsPage searchResultsPage = landingPage.searchForProduct(TestData.TOUR_SEARCH_TERMS);

		LOGGER.info("4. Select Product and Add to Cart");
		CoffeeProductDescriptionPage coffeeProductDescriptionPage = searchResultsPage.clickFirstProduct();
		coffeeProductDescriptionPage.selectGrind(TestData.GRIND);
		MiniCartContainerChunk miniCartContainer = coffeeProductDescriptionPage.addToCart();

		LOGGER.info("5. Select 'Proceed to Checkout'");
		CheckoutShippingInfoPage shippingInfoPage = miniCartContainer.clickSignedInCheckout();
		CheckoutPaymentMethodPage paymentMethodPage = shippingInfoPage
				.setShippingMethod(TestConstants.TestData.SHIPPING_METHOD_GROUND);
		CheckoutPlaceOrderPage placeOrderPage = paymentMethodPage.continueAfterEnteringPIN();

		LOGGER.info("6. Click 'Place Order'");
		CheckoutConfirmationPage confirmationPage = placeOrderPage.placeOrder();

		LOGGER.info("Verify Confirmation page is displayed");
		Assert.assertTrue(confirmationPage.getConfirmationMessage().contains("THANK YOU FOR YOUR PURCHASE!"),
				"Order was not placed");

		LOGGER.info("Order Placed: " + confirmationPage.getOrderNumber());
	}

	@Test(groups = { TestNGGroups.EXISTING_USER_CHECKOUT }, description = "613495")
	public void userCheckoutEditShippingInformationTest() {

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
		landingPage = mainMenu.clickHeaderLogo();
		ShopCoffeePage shopCoffeePage = landingPage.clickShopCoffeeButton();
		CoffeeProductPage coffeeProductPage = shopCoffeePage.clickProductName(TestConstants.TestData.COFFEE_BRAND_NAME);
		coffeeProductPage.selectAGrind(TestConstants.TestData.GRIND);
		MiniCartContainerChunk miniCartContainer = coffeeProductPage.clickAddToCart();

		LOGGER.info("5. Select 'Proceed to Checkout'");
		CheckoutShippingInfoPage shippingInfoPage = miniCartContainer.clickSignedInCheckout();

		LOGGER.info("6. Under Shipping Address on Checkout page, select 'Add New Address'");
		shippingInfoPage.addNewAddress();

		LOGGER.info("7. Complete information to add new address. Click Continue");
		String uniqPrefix1 = "" + TestHelper.getCurrentGMT6Time();
		shippingInfoPage.fillUniqueShippingInfo(uniqPrefix1);
		shippingInfoPage.continueAddNewAddress();

		LOGGER.info("New address is added and selected on Checkout page");
		Assert.assertTrue(shippingInfoPage.getAddresses().stream().anyMatch(address -> address.contains(uniqPrefix1)),
				"New address not found");

		LOGGER.info("Select 'edit' next to newly added address");
		shippingInfoPage.editAddress(uniqPrefix1);

		LOGGER.info("Modify shipping address. Click Update");
		String delta = "" + TestHelper.getCurrentGMT6Time();
		shippingInfoPage.modifyShippingInfoByDelta(delta);
		shippingInfoPage = shippingInfoPage.updateAfterShippingInfoModification();

		LOGGER.info("Address is updated and selected on Checkout page");
		Assert.assertTrue(shippingInfoPage.getAddresses().stream().anyMatch(address -> address.contains(delta)),
				"New address not found");

		LOGGER.info("Select 'edit' next to shipping date");
		String oldDate = shippingInfoPage.getShippingDate();
		DatePickerChunk datePicker = shippingInfoPage.editShippingDate();

		LOGGER.info("Select a new date");
		datePicker.selectDate(CheckoutShippingInfoPage.class, 3);

		LOGGER.info("Shipping date is updated on Checkout page");
		String newDate = shippingInfoPage.getShippingDate();
		Assert.assertNotEquals(newDate, oldDate, "Shipping Date field does not updated");

	}
}
