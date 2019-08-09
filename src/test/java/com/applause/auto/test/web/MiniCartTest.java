package com.applause.auto.test.web;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.applause.auto.web.components.MiniCartContainerChunk;
import com.applause.auto.web.views.CoffeeProductPage;
import com.applause.auto.web.views.Landing;
import com.applause.auto.web.views.ShopCoffeePage;
import com.applause.auto.common.data.Constants.TestData;
import com.applause.auto.common.data.Constants.TestNGGroups;

public class MiniCartTest extends BaseTest {

	private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().getClass());

	@Test(groups = { TestNGGroups.MINI_CART }, description = "581728")
	public void removeReAddMiniCartTest() {

		logger.info("1. Navigate to landing page");
		Landing landing = navigateToLanding();
		Assert.assertNotNull(landing, "Failed to navigate to the landing page.");

		logger.info("2. Go to any category page and add an item to cart.");
		ShopCoffeePage shopCoffeePage = landing.clickShopCoffeeButton();
		CoffeeProductPage coffeeProductPage = shopCoffeePage.clickProductName(TestData.COFFEE_BRAND_NAME);
		coffeeProductPage.selectAGrind(TestData.GRIND);
		MiniCartContainerChunk miniCartContainer = coffeeProductPage.clickAddToCart();

		logger.info("3. Go to category page and add another item to cart");
		shopCoffeePage = coffeeProductPage.navigateBack(ShopCoffeePage.class);
		coffeeProductPage = shopCoffeePage.clickProductName(TestData.COFFEE_ARABIAN_MOCHA_JAVA_NAME);
		coffeeProductPage.selectAGrind(TestData.GRIND);
		miniCartContainer = coffeeProductPage.clickAddToCart();

		logger.info("Verify both items are added to mini cart");
		List<String> items = miniCartContainer.getItems();
		Assert.assertEquals(items.size(), 2, "Wrong items amount in cart");
		Assert.assertTrue(items.contains(TestData.COFFEE_BRAND_NAME),
				"Product not found: " + TestData.COFFEE_BRAND_NAME);
		Assert.assertTrue(items.contains(TestData.COFFEE_ARABIAN_MOCHA_JAVA_NAME),
				"Product not found: " + TestData.COFFEE_ARABIAN_MOCHA_JAVA_NAME);

		logger.info("4. Select 'remove' next to one of the items in the mini-cart");
		miniCartContainer = miniCartContainer.remove(TestData.COFFEE_BRAND_NAME);

		logger.info("Verify 're-add' is displayed next to item just removed");
		Assert.assertEquals(miniCartContainer.getReAddButtonCaption(TestData.COFFEE_BRAND_NAME), "re-add item",
				"Re-add item button not displayed");

		logger.info("5. Select 're-add' next to item just removed");
		miniCartContainer.reAdd(TestData.COFFEE_BRAND_NAME);
		logger.info("Finished");
		Assert.assertEquals(miniCartContainer.getRemoveButtonCaption(TestData.COFFEE_BRAND_NAME), "remove",
				"Remove item button not displayed");

	}

}
