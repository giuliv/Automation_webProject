package com.applause.auto.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.applause.auto.pageframework.chunks.MiniCartContainerChunk;
import com.applause.auto.pageframework.pages.CoffeeProductPage;
import com.applause.auto.pageframework.pages.LandingPage;
import com.applause.auto.pageframework.pages.ShopCoffeePage;
import com.applause.auto.pageframework.pages.ShoppingCartPage;
import com.applause.auto.pageframework.testdata.TestConstants.TestData;
import com.applause.auto.pageframework.testdata.TestConstants.TestNGGroups;

public class ShoppingCartTest extends BaseTest {

	@Test(groups = { TestNGGroups.CART }, description = "581729")
	public void removeFromShoppingCartTest() {

		LOGGER.info("1. Navigate to landing page");
		LandingPage landingPage = navigateToLandingPage();
		Assert.assertNotNull(landingPage, "Failed to navigate to the landing page.");

		LOGGER.info("2. Go to any category page and add an item to cart.");
		ShopCoffeePage shopCoffeePage = landingPage.clickShopCoffeeButton();
		CoffeeProductPage coffeeProductPage = shopCoffeePage.clickProductName(TestData.COFFEE_BRAND_NAME);
		coffeeProductPage.selectAGrind(TestData.GRIND);
		MiniCartContainerChunk miniCartContainer = coffeeProductPage.clickAddToCart();

		LOGGER.info("3. From mini-cart, select 'View Cart'");
		ShoppingCartPage shoppingCartPage = miniCartContainer.clickEditCart();

		LOGGER.info("Validate correct item is in cart");
		Assert.assertEquals(shoppingCartPage.getItems().size(), 1, "Wrong products count");
		Assert.assertTrue(shoppingCartPage.getItems().contains(TestData.COFFEE_BRAND_NAME));

		LOGGER.info("4. Select 'X' next to item to remove from cart");
		shoppingCartPage = shoppingCartPage.removeItem(TestData.COFFEE_BRAND_NAME);

		LOGGER.info("Verify item is remove from cart and shopping cart is empty");
		Assert.assertEquals(shoppingCartPage.getItems().size(), 0, "Wrong product count after deletion");

	}

	@Test(groups = { TestNGGroups.CART }, description = "581730")
	public void updateGrindQuantityTest() {

		LOGGER.info("1. Navigate to landing page");
		LandingPage landingPage = navigateToLandingPage();
		Assert.assertNotNull(landingPage, "Failed to navigate to the landing page.");

		LOGGER.info("2. Go to any category page and add an item to cart.");
		ShopCoffeePage shopCoffeePage = landingPage.clickShopCoffeeButton();
		CoffeeProductPage coffeeProductPage = shopCoffeePage.clickProductName(TestData.COFFEE_BRAND_NAME);
		coffeeProductPage.selectAGrind(TestData.GRIND);
		MiniCartContainerChunk miniCartContainer = coffeeProductPage.clickAddToCart();

		LOGGER.info("3. From mini-cart, select 'View Cart'");
		ShoppingCartPage shoppingCartPage = miniCartContainer.clickEditCart();

		LOGGER.info("4. Update Grind to a different grind type from the cart");
		shoppingCartPage.setGrindForItem(TestData.COFFEE_BRAND_NAME, TestData.GRIND_2);

		LOGGER.info("User is shown a message 'Grind for x was updated'");
		Assert.assertEquals(shoppingCartPage.getStatusMessage(),
				String.format("Grind for %s was updated", TestData.COFFEE_BRAND_NAME),
				String.format("Grind for %s was updated", TestData.COFFEE_BRAND_NAME) + " - message not found");

		LOGGER.info("5. Update quantity to a different amount");
		shoppingCartPage.setQuantityForItem(TestData.COFFEE_BRAND_NAME, 2);

		LOGGER.info("6. Select 'Update Cart'");
		shoppingCartPage = shoppingCartPage.updateCart();

		LOGGER.info("User is shown a 'Shopping cart is updated' message. Quantity is updated");
		Assert.assertEquals(shoppingCartPage.getStatusMessage(), "Shopping cart was updated",
				"Shopping cart was updated - message not found");
	}

}
