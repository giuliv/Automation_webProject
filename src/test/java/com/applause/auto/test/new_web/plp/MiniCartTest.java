package com.applause.auto.test.new_web.plp;

import com.applause.auto.common.data.Constants;
import com.applause.auto.new_web.components.MiniCart;
import com.applause.auto.new_web.components.QuickViewComponent;
import com.applause.auto.new_web.components.ShopRunnerComponent;
import com.applause.auto.new_web.views.ProductDetailsPage;
import com.applause.auto.test.new_web.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MiniCartTest extends BaseTest {

  @Test(
      groups = {Constants.TestNGGroups.PLP},
      description = "11101730")
  public void validateMiniCartElementsTest() {

    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDP();
    Assert.assertNotNull(productDetailsPage, "Failed to navigate to Product Details Page");

    logger.info("2. Save PDP data");
    String originName = productDetailsPage.getProductName();
    String originGrind = productDetailsPage.getGrindSelected();
    String originPrice = productDetailsPage.getProductPrice();

    logger.info("3. Add item to MiniCart");
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("4. Validating...");
    int item = 0;
    Assert.assertEquals(
        originName, miniCart.getProductNameByIndex(item), "Product name does not match");
    Assert.assertEquals(
        originGrind, miniCart.getGrindByIndex(item), "Product grind does not match");
    Assert.assertTrue(
        miniCart.getPriceByIndex(item).contains(originPrice), "Product price does not match");
    miniCart.validateGeneralMiniCartElements().assertAll();

    logger.info("FINISH");
  }

  @Test(
      groups = {Constants.TestNGGroups.PLP},
      description = "11101731")
  public void itemsRemovedFromMiniCartTest() {

    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDP();
    Assert.assertNotNull(productDetailsPage, "Failed to navigate to Product Details Page");

    logger.info("2. Add item to MiniCart");
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("3. Increase item");
    int item = 0;
    int originalQuantity = miniCart.getProductQuantityByIndex(item);
    miniCart.addOneMoreItem();

    Assert.assertNotEquals(
        originalQuantity, miniCart.getProductQuantityByIndex(item), "Item is not added correctly");

    logger.info("4. Decrease item");
    miniCart.removeOneItem();
    Assert.assertEquals(
        originalQuantity,
        miniCart.getProductQuantityByIndex(item),
        "Item is not removed correctly");

    logger.info("5. Remove Item");
    miniCart.removeItem();
    Assert.assertFalse(miniCart.isProductNameDisplayedIndex(), "Product was not removed");

    logger.info("FINISH");
  }

  @Test(
      groups = {Constants.TestNGGroups.PLP},
      description = "11101732")
  public void emptyMiniCartTest() {

    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDP();
    Assert.assertNotNull(productDetailsPage, "Failed to navigate to Product Details Page");

    logger.info("2. Add item to MiniCart");
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("3. Remove Item");
    miniCart.removeItem();

    logger.info("4. Validate Empty MiniCart Message");
    Assert.assertEquals(
        miniCart.getEmptyMiniCartMessage(),
        "Your cart is empty. Do you need any beans or tea?",
        "Empty message is not correct");

    logger.info("FINISH");
  }

  @Test(
      groups = {Constants.TestNGGroups.PLP},
      description = "11101733")
  public void progressShippingBarTest() {

    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDP();
    Assert.assertNotNull(productDetailsPage, "Failed to navigate to Product Details Page");

    logger.info("2. Add item to MiniCart");
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();
    float progressBar = miniCart.getProgressBarQuantity();

    logger.info("3. Increase item and Validate progress bar");
    miniCart.addOneMoreItem();
    Assert.assertTrue(
        progressBar < miniCart.getProgressBarQuantity(), "Progress bar is not working correctly");

    logger.info("FINISH");
  }

  @Test(
      groups = {Constants.TestNGGroups.PLP},
      description = "11101739")
  public void learnMoreFromMiniCartTest() {

    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDP();
    Assert.assertNotNull(productDetailsPage, "Failed to navigate to Product Details Page");

    logger.info("2. Add item to MiniCart");
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("3. Validate Learn more link works");
    ShopRunnerComponent shopRunnerComponent = miniCart.clickShopRunnerLinks("Learn More");
    Assert.assertNotNull(shopRunnerComponent, "Learn more was not displayed");
    Assert.assertTrue(
        shopRunnerComponent.isLearnMoreModalDisplayed(),
        "Learn more modal was not displayed correctly");

    logger.info("FINISH");
  }

  @Test(
      groups = {Constants.TestNGGroups.PLP},
      description = "11101740")
  public void signInFromMiniCartTest() {

    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDP();
    Assert.assertNotNull(productDetailsPage, "Failed to navigate to Product Details Page");

    logger.info("2. Add item to MiniCart");
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("3. Validate signIn link works");
    ShopRunnerComponent shopRunnerComponent = miniCart.clickShopRunnerLinks("Sign In");
    Assert.assertNotNull(shopRunnerComponent, "Sign In was not displayed");
    Assert.assertTrue(
        shopRunnerComponent.isSignInModalDisplayed(), "Sign In modal was not displayed correctly");

    logger.info("FINISH");
  }

  @Test(
      groups = {Constants.TestNGGroups.PLP},
      description = "11101734")
  public void recommendedItemsNavigationTest() {
    /**
     * TODO
     *
     * <p>Disabled this test for now as functionality isn't working on Stage
     */
    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDP();
    Assert.assertNotNull(productDetailsPage, "Failed to navigate to Product Details Page");

    logger.info("2. Add item to MiniCart");
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("3. Validate Recommended Items Navigation");
    long originValue = miniCart.getRecommendedItemsVisible();
    miniCart.clickNavigationArrow(Constants.NavigationArrow.NEXT);

    long newValue = miniCart.getRecommendedItemsVisible();
    Assert.assertNotEquals(originValue, newValue, "Next arrow does not work correctly");

    miniCart.clickNavigationArrow(Constants.NavigationArrow.PREV);
    Assert.assertEquals(
        originValue, miniCart.getRecommendedItemsVisible(), "Prev arrow does not work correctly");

    logger.info("FINISH");
  }

  @Test(
      groups = {Constants.TestNGGroups.PLP},
      description = "11101735",
      enabled = false)
  public void addRemoveRecommendedItemTest() {
    /**
     * TODO
     *
     * <p>Disabled this test for now as functionality isn't working on Stage
     */
    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDP();
    Assert.assertNotNull(productDetailsPage, "Failed to navigate to Product Details Page");

    logger.info("2. Add item to MiniCart");
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("3. Click on View cart.");
    int firstProduct = 0;
    String recommendedItemName = miniCart.getRecommendedItemNameByIndex(firstProduct);
    QuickViewComponent quickViewComponent =
        miniCart.clickOnRecommendedForYouAddButtonByIndex(firstProduct);
    Assert.assertNotNull(quickViewComponent, "Failed to open to QuickView popup");

    logger.info("4. Click on Add to cart");
    miniCart = quickViewComponent.clickAddToCart();

    logger.info("5. Validate Recommended Item is added correctly");
    Assert.assertEquals(
        recommendedItemName.toLowerCase(),
        miniCart.getProductNameByIndex(firstProduct),
        "Recommended Item was not added correctly");

    logger.info("6. Validate Recommended Item is removed correctly");
    miniCart.removeItem();
    Assert.assertNotEquals(
        recommendedItemName.toLowerCase(),
        miniCart.getProductNameByIndex(firstProduct),
        "Recommended Item was not removed correctly");

    logger.info("FINISH");
  }

  @Test(
      groups = {Constants.TestNGGroups.PLP},
      description = "11101737")
  public void oneTimePurchaseMiniCartTest() {

    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDP();
    Assert.assertNotNull(productDetailsPage, "Failed to navigate to Product Details Page");

    logger.info("2. Add item to MiniCart");
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("3. Select One time purchase > One time purchase should be selected");
    miniCart.clickOneTimePurchaseButton();

    logger.info("Verify that One time purchase should be selected");
    Assert.assertTrue(miniCart.isOneTimePurchaseButtonEnabled(), "One time purchase isn't enabled");

    logger.info("FINISH");
  }

  @Test(
      groups = {Constants.TestNGGroups.PLP},
      description = "11101738")
  public void subscriptionModeMiniCartTest() {

    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDP();
    Assert.assertNotNull(productDetailsPage, "Failed to navigate to Product Details Page");

    logger.info("2. Add item to MiniCart");
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("3. Select Subscribe > Subscribe should be selected.");
    miniCart.clickSubscribeButton();

    logger.info("Verify that Subscribe should be selected");
    Assert.assertTrue(miniCart.isSubscribeButtonEnabled(), "Subscribe isn't selected");

    logger.info("FINISH");
  }
}
