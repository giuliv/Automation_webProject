package com.applause.auto.test.web;

import com.applause.auto.common.data.Constants.TestData;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.web.components.MiniCartContainerChunk;
import com.applause.auto.web.views.CoffeeProductPage;
import com.applause.auto.web.views.Landing;
import com.applause.auto.web.views.ShopCoffeePage;
import com.applause.auto.web.views.ShoppingCartPage;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ShoppingCartTest extends BaseTest {

  private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().getClass());

  @Test(
      groups = {TestNGGroups.CART},
      description = "581729")
  public void removeFromShoppingCartTest() {

    logger.info("1. Navigate to landing page");
    Landing landing = navigateToLanding();
    Assert.assertNotNull(landing, "Failed to navigate to the landing page.");

    logger.info("2. Go to any category page and add an item to cart.");
    ShopCoffeePage shopCoffeePage = landing.clickShopCoffeeButton();
    CoffeeProductPage coffeeProductPage =
        shopCoffeePage.clickProductName(TestData.COFFEE_BRAND_NAME);
    coffeeProductPage.selectAGrind(TestData.GRIND);
    MiniCartContainerChunk miniCartContainer = coffeeProductPage.clickAddToCart();

    logger.info("3. From mini-cart, select 'View Cart'");
    ShoppingCartPage shoppingCartPage = miniCartContainer.clickEditCart();

    logger.info("Validate correct item is in cart");
    Assert.assertEquals(shoppingCartPage.getItems().size(), 1, "Wrong products count");
    Assert.assertTrue(shoppingCartPage.getItems().contains(TestData.COFFEE_BRAND_NAME));

    logger.info("4. Select 'X' next to item to remove from cart");
    shoppingCartPage = shoppingCartPage.removeItem(TestData.COFFEE_BRAND_NAME);

    logger.info("Verify item is remove from cart and shopping cart is empty");
    Assert.assertEquals(
        shoppingCartPage.getItems().size(), 0, "Wrong product count after deletion");
  }

  @Test(
      groups = {TestNGGroups.CART},
      description = "581730")
  public void updateGrindQuantityTest() {

    logger.info("1. Navigate to landing page");
    Landing landing = navigateToLanding();
    Assert.assertNotNull(landing, "Failed to navigate to the landing page.");

    logger.info("2. Go to any category page and add an item to cart.");
    ShopCoffeePage shopCoffeePage = landing.clickShopCoffeeButton();
    CoffeeProductPage coffeeProductPage =
        shopCoffeePage.clickProductName(TestData.COFFEE_BRAND_NAME);
    coffeeProductPage.selectAGrind(TestData.GRIND);
    MiniCartContainerChunk miniCartContainer = coffeeProductPage.clickAddToCart();

    logger.info("3. From mini-cart, select 'View Cart'");
    ShoppingCartPage shoppingCartPage = miniCartContainer.clickEditCart();

    logger.info("4. Update Grind to a different grind type from the cart");
    shoppingCartPage.setGrindForItem(TestData.COFFEE_BRAND_NAME, TestData.GRIND_2);

    logger.info("User is shown a message 'Grind for x was updated'");
    Assert.assertEquals(
        shoppingCartPage.getStatusMessage(),
        String.format("Grind for %s was updated", TestData.COFFEE_BRAND_NAME),
        String.format("Grind for %s was updated", TestData.COFFEE_BRAND_NAME)
            + " - message not found");

    logger.info("5. Update quantity to a different amount");
    shoppingCartPage.setQuantityForItem(TestData.COFFEE_BRAND_NAME, 2);

    logger.info("6. Select 'Update Cart'");
    shoppingCartPage = shoppingCartPage.updateCart();

    logger.info("User is shown a 'Shopping cart is updated' message. Quantity is updated");
    Assert.assertEquals(
        shoppingCartPage.getStatusMessage(),
        "Shopping cart was updated",
        "Shopping cart was updated - message not found");
  }

  @Test(
      groups = {TestNGGroups.CART},
      description = "581731")
  public void updateShippingMethodTest() {

    logger.info("1. Navigate to landing page");
    Landing landing = navigateToLanding();
    Assert.assertNotNull(landing, "Failed to navigate to the landing page.");

    logger.info("2. Go to any category page and add an item to cart.");
    ShopCoffeePage shopCoffeePage = landing.clickShopCoffeeButton();
    CoffeeProductPage coffeeProductPage =
        shopCoffeePage.clickProductName(TestData.COFFEE_BRAND_NAME);
    coffeeProductPage.selectAGrind(TestData.GRIND);
    MiniCartContainerChunk miniCartContainer = coffeeProductPage.clickAddToCart();

    logger.info("3. From mini-cart, select 'View Cart'");
    ShoppingCartPage shoppingCartPage = miniCartContainer.clickEditCart();

    logger.info(
        "4. From shopping cart, select the 'Select Shipping Method' dropdown and select a new shipping method");
    String oldSelectedMethod = shoppingCartPage.getShippingMethod();
    String oldEstimatedPrice = shoppingCartPage.getEstimatedShippingPrice();
    String oldOrderTotal = shoppingCartPage.getOrderSummaryPrice();
    shoppingCartPage.selectShippingMethod(TestData.SHIPPING_METHOD_AIR_2ND_DAY);
    String newSelectedMethod = shoppingCartPage.getShippingMethod();
    String newEstimatedPrice = shoppingCartPage.getEstimatedShippingPrice();
    String newOrderTotal = shoppingCartPage.getOrderSummaryPrice();

    logger.info("Shipping method is updated");
    Assert.assertNotEquals(
        newSelectedMethod, oldSelectedMethod, "Shipping method does not updated");

    logger.info("Estimated Shipping price is updated in Order Summary");
    Assert.assertNotEquals(
        newEstimatedPrice, oldEstimatedPrice, "Estimated price does not updated");

    logger.info("Total is updated in Order Summary");
    Assert.assertNotEquals(newOrderTotal, oldOrderTotal, "Total does not updated");
  }
}
