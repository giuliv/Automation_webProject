package com.applause.auto.test;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.applause.auto.pageframework.chunks.MiniCartContainerChunk;
import com.applause.auto.pageframework.pages.CoffeeProductPage;
import com.applause.auto.pageframework.pages.LandingPage;
import com.applause.auto.pageframework.pages.ShopCoffeePage;
import com.applause.auto.pageframework.testdata.TestConstants.TestData;
import com.applause.auto.pageframework.testdata.TestConstants.TestNGGroups;

public class MiniCartTest extends BaseTest {

	@Test(groups = { TestNGGroups.MINI_CART }, description = "581728")
	public void removeReAddMiniCartTest() {

		LOGGER.info("1. Navigate to landing page");
		LandingPage landingPage = navigateToLandingPage();
		Assert.assertNotNull(landingPage, "Failed to navigate to the landing page.");

		LOGGER.info("2. Go to any category page and add an item to cart.");
		ShopCoffeePage shopCoffeePage = landingPage.clickShopCoffeeButton();
		CoffeeProductPage coffeeProductPage = shopCoffeePage.clickProductName(TestData.COFFEE_BRAND_NAME);
		coffeeProductPage.selectAGrind(TestData.GRIND);
		MiniCartContainerChunk miniCartContainer = coffeeProductPage.clickAddToCart();

		LOGGER.info("3. Go to category page and add another item to cart");
		shopCoffeePage = coffeeProductPage.navigateBack(ShopCoffeePage.class);
		coffeeProductPage = shopCoffeePage.clickProductName(TestData.COFFEE_ARABIAN_MOCHA_JAVA_NAME);
		coffeeProductPage.selectAGrind(TestData.GRIND);
		miniCartContainer = coffeeProductPage.clickAddToCart();

		LOGGER.info("Verify both items are added to mini cart");
		List<String> items = miniCartContainer.getItems();
		Assert.assertEquals(items.size(), 2, "Wrong items amount in cart");
		Assert.assertTrue(items.contains(TestData.COFFEE_BRAND_NAME),
				"Product not found: " + TestData.COFFEE_BRAND_NAME);
		Assert.assertTrue(items.contains(TestData.COFFEE_ARABIAN_MOCHA_JAVA_NAME),
				"Product not found: " + TestData.COFFEE_ARABIAN_MOCHA_JAVA_NAME);

		LOGGER.info("4. Select 'remove' next to one of the items in the mini-cart");
		miniCartContainer = miniCartContainer.remove(TestData.COFFEE_BRAND_NAME);

		LOGGER.info("Verify 're-add' is displayed next to item just removed");
		Assert.assertEquals(miniCartContainer.getReAddButtonCaption(TestData.COFFEE_BRAND_NAME), "re-add item",
				"Re-add item button not displayed");

		LOGGER.info("5. Select 're-add' next to item just removed");
		miniCartContainer.reAdd(TestData.COFFEE_BRAND_NAME);
		LOGGER.info("Finished");
		Assert.assertEquals(miniCartContainer.getRemoveButtonCaption(TestData.COFFEE_BRAND_NAME), "remove",
				"Remove item button not displayed");

	}

}
