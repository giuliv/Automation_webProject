package com.applause.auto.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.applause.auto.pageframework.chunks.MiniCartContainerChunk;
import com.applause.auto.pageframework.chunks.VerifyYourAddressDetailsChunk;
import com.applause.auto.pageframework.pages.CheckoutConfirmationPage;
import com.applause.auto.pageframework.pages.CheckoutPage;
import com.applause.auto.pageframework.pages.CheckoutPaymentMethodPage;
import com.applause.auto.pageframework.pages.CheckoutPlaceOrderPage;
import com.applause.auto.pageframework.pages.CheckoutShippingInfoPage;
import com.applause.auto.pageframework.pages.CoffeeProductPage;
import com.applause.auto.pageframework.pages.LandingPage;
import com.applause.auto.pageframework.pages.ShopCoffeePage;
import com.applause.auto.pageframework.pages.ShopTeaPage;
import com.applause.auto.pageframework.pages.ShoppingCartPage;
import com.applause.auto.pageframework.pages.TeaProductPage;
import com.applause.auto.pageframework.testdata.TestConstants;
import com.applause.auto.pageframework.testdata.TestConstants.TestNGGroups;

public class GuestCheckoutTest extends BaseTest {

	@Test(groups = { TestNGGroups.GUEST_CHECKOUT }, description = "19500")
	public void guestCheckoutCoffeeTest() {

		LOGGER.info("1. Navigate to landing page");
		LandingPage landingPage = navigateToLandingPage();
		Assert.assertNotNull(landingPage, "Failed to navigate to the landing page.");

		LOGGER.info("2. Select a coffee from grid view and add to cart");
		ShopCoffeePage shopCoffeePage = landingPage.clickShopCoffeeButton();
		CoffeeProductPage coffeeProductPage = shopCoffeePage.clickProductName(TestConstants.TestData.COFFEE_BRAND_NAME);
		coffeeProductPage.selectAGrind(TestConstants.TestData.GRIND);
		MiniCartContainerChunk miniCartContainer = coffeeProductPage.clickAddToCart();

		LOGGER.info("3. Select 'Proceed to Checkout'");
		// TODO: Implement step 3 and 4 from test case to edit cart and add a promo card
		CheckoutPage checkoutPage = miniCartContainer.clickCheckout();
		CheckoutShippingInfoPage shippingInfoPage = checkoutPage.clickContinueAsGuest();

		LOGGER.info("4. Complete Contact Information");
		VerifyYourAddressDetailsChunk verifyAddressChunk = shippingInfoPage.continueAfterFillingRequiredContactInfo();
		shippingInfoPage = verifyAddressChunk.clickEnteredAddressButton();

		LOGGER.info("5. Select ground shipping");
		CheckoutPaymentMethodPage paymentMethodPage = shippingInfoPage
				.setShippingMethod(TestConstants.TestData.SHIPPING_METHOD_GROUND);

		LOGGER.info("6. Use credit card for payment");
		CheckoutPlaceOrderPage placeOrderPage = paymentMethodPage.continueAfterFillingRequiredBillingInfo();

		LOGGER.info("7. Click 'Place Order'");
		CheckoutConfirmationPage confirmationPage = placeOrderPage.placeOrder();

		LOGGER.info("Verify Confirmation page is displayed");
		Assert.assertTrue(confirmationPage.getConfirmationMessage().contains("THANK YOU FOR YOUR PURCHASE!"),
				"Order was not placed");

		LOGGER.info("Order Placed: " + confirmationPage.getOrderNumber());
	}

	@Test(groups = { TestNGGroups.GUEST_CHECKOUT }, description = "19501")
	public void guestCheckoutTeaTest() {

		LOGGER.info("1. Navigate to landing page");
		LandingPage landingPage = navigateToLandingPage();
		Assert.assertNotNull(landingPage, "Failed to navigate to the landing page.");

		LOGGER.info("2. Select a tea from grid view and add to cart");
		ShopTeaPage shopTeaPage = navigateToShopTeaPage();
		TeaProductPage teaProductPage = shopTeaPage.clickProductName(TestConstants.TestData.TEA_NAME);
		MiniCartContainerChunk miniCartContainer = teaProductPage.clickAddToCart();

		LOGGER.info("3. Select 'Edit Cart' from mini-cart");
		ShoppingCartPage shoppingCart = miniCartContainer.clickEditCart();

		LOGGER.info("4. Add gift message to product");
		shoppingCart.selectOrderAsGift();
		shoppingCart.enterGiftMessage(TestConstants.TestData.GIFT_MESSAGE);

		LOGGER.info("5. Select 'Proceed to Checkout'");
		CheckoutPage checkoutPage = shoppingCart.clickProceedToCheckout();
		CheckoutShippingInfoPage shippingInfoPage = checkoutPage.clickContinueAsGuest();

		LOGGER.info("6. Complete Contact Information");
		VerifyYourAddressDetailsChunk verifyAddressChunk = shippingInfoPage.continueAfterFillingRequiredContactInfo();
		shippingInfoPage = verifyAddressChunk.clickEnteredAddressButton();

		LOGGER.info("7. Select ground shipping");
		Assert.assertEquals(shippingInfoPage.getGiftMessage(), TestConstants.TestData.GIFT_MESSAGE,
				"Gift message entered previously was not fetched correctly");
		CheckoutPaymentMethodPage paymentMethodPage = shippingInfoPage
				.setShippingMethod(TestConstants.TestData.SHIPPING_METHOD_GROUND);

		LOGGER.info("8. Use credit card for payment");
		// Cant use Peets Card as it is limited to $50 so wont be a stable test
		CheckoutPlaceOrderPage placeOrderPage = paymentMethodPage.continueAfterFillingRequiredBillingInfo();

		LOGGER.info("9. Click 'Place Order'");
		CheckoutConfirmationPage confirmationPage = placeOrderPage.placeOrder();

		LOGGER.info("Verify Confirmation page is displayed");
		Assert.assertTrue(confirmationPage.getConfirmationMessage().contains("THANK YOU FOR YOUR PURCHASE!"),
				"Order was not placed");

		LOGGER.info("Order Placed: " + confirmationPage.getOrderNumber());
	}

}
