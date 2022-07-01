package com.applause.auto.test.new_web.plp;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.Constants.TestData;
import com.applause.auto.new_web.components.MiniCart;
import com.applause.auto.new_web.components.QuickViewComponent;
import com.applause.auto.new_web.components.plp.PlpLearnMoreOverlappingComponent;
import com.applause.auto.new_web.components.plp.PlpSignInOverlappingComponent;
import com.applause.auto.new_web.views.ProductListPage;
import com.applause.auto.test.new_web.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class quickViewTest extends BaseTest {

  @Test(
      groups = {Constants.TestNGGroups.PLP, Constants.TestNGGroups.WEB_REGRESSION},
      description = "11101530")
  public void reviewQuickViewUITest() {

    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLP(TestData.COFFEE_BEST_SELLERS_URL);
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Validate UI Elements of QuickView Modal");
    QuickViewComponent quickViewComponent = productListPage.clickOverFirstQuickViewButton();
    quickViewComponent.validateMainUIElements().assertAll();

    logger.info("FINISH");
  }

  @Test(
      groups = {Constants.TestNGGroups.PLP, Constants.TestNGGroups.WEB_REGRESSION},
      description = "11110671")
  public void coffeeItemGrindTest() {

    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLP(TestData.COFFEE_BEST_SELLERS_URL);
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Select new Grind > Review Grind element");
    QuickViewComponent quickViewComponent = productListPage.clickOverFirstQuickViewButton();
    String originalGrind = quickViewComponent.getGrind();

    quickViewComponent.selectGrindByIndex(3);
    String newGrind = quickViewComponent.getGrind();
    Assert.assertNotEquals(originalGrind, newGrind, "Grind element was not updated");
    Assert.assertEquals(
        quickViewComponent.getGrindOptions(), 5, "Total Grind options is not correct");

    logger.info("FINISH");
  }

  @Test(
      groups = {Constants.TestNGGroups.PLP, Constants.TestNGGroups.WEB_REGRESSION},
      description = "11110672")
  public void itemQuantityTest() {

    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLP(TestData.COFFEE_BEST_SELLERS_URL);
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Select new Quantity > Review Quantity element");
    QuickViewComponent quickViewComponent = productListPage.clickOverFirstQuickViewButton();
    quickViewComponent.validateQuantityElements().assertAll();

    logger.info("FINISH");
  }

  @Test(
      groups = {Constants.TestNGGroups.PLP, Constants.TestNGGroups.WEB_REGRESSION},
      description = "11110673")
  public void addToCartTest() {

    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLP(TestData.COFFEE_BEST_SELLERS_URL);
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Reviewing adding to cart feature");
    QuickViewComponent quickViewComponent = productListPage.clickOverFirstQuickViewButton();
    String productName = quickViewComponent.getProductName();

    MiniCart miniCart = quickViewComponent.clickAddToCart();
    int firstProduct = 0;
    Assert.assertNotNull(miniCart, "Product was not added to miniCart");
    Assert.assertEquals(
        productName.toLowerCase(),
        miniCart.getProductNameByIndex(firstProduct),
        "Product added is not correct");

    logger.info("FINISH");
  }

  @Test(
      groups = {Constants.TestNGGroups.PLP, Constants.TestNGGroups.WEB_REGRESSION},
      description = "11107487")
  public void shopRunnerTest() {

    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLP(TestData.COFFEE_BEST_SELLERS_URL);
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Open QuickView Modal");
    QuickViewComponent quickViewComponent = productListPage.clickOverFirstQuickViewButton();

    logger.info("3. Validate Shop Runner UI Elements of ShopRunner");
    quickViewComponent.validatShopRunnerUIElements().assertAll();

    logger.info("4. Click LearnMore and Verify LearnMore overlapping Modal Free shipping Text ");
    PlpLearnMoreOverlappingComponent learMoreOverlapping = quickViewComponent.clickLearnMoreLink();
    softAssert.assertEquals(
        learMoreOverlapping.getShippingText(),
        TestData.SHOP_RUNNER_FREE_TEXT,
        "FREE 2-Day Shipping & Free Returns Text is not displayed");
    quickViewComponent = learMoreOverlapping.clickCloseButton(QuickViewComponent.class);

    logger.info("5. Click Sign In Link and Verify SignIn overlapping Modal");
    PlpSignInOverlappingComponent signInOverlapping = quickViewComponent.clickSignInLink();
    signInOverlapping.validateShopRunnerSignInModalUIElements().assertAll();
  }

  @Test(
      groups = {Constants.TestNGGroups.PLP, Constants.TestNGGroups.WEB_REGRESSION},
      description = "11107488")
  public void teaItemTest() {

    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLP();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Open QuickView Modal");
    QuickViewComponent quickViewComponent = productListPage.clickOverFirstQuickViewButton();

    logger.info("3. Select the alternate option and verify the selected option");
    quickViewComponent.selectOption(1);
    Assert.assertTrue(
        quickViewComponent.selectedOptionText().contains(TestData.GRIND_NEXT_TEXT),
        "Selected Drop Down Value Mismatches" + quickViewComponent.selectedOptionText());

    quickViewComponent.selectOption(0);
    Assert.assertTrue(
        quickViewComponent.selectedOptionText().contains(TestData.GRIND_FIRST_TEXT),
        "Selected Drop Down Value Mismatches" + quickViewComponent.selectedOptionText());

    logger.info("4. Decrease the Quantity value using Minus Button and check InActive state");
    int initialQuantity;
    int AfterQuantity;
    initialQuantity = Integer.parseInt(quickViewComponent.getQuantity());
    quickViewComponent.clickMinusButton();
    AfterQuantity = Integer.parseInt(quickViewComponent.getQuantity());
    Assert.assertEquals(initialQuantity, AfterQuantity, "Quantity increased.");

    logger.info(
        "4. Increase the Quantity value using Plus Button and verify the Quantity is Increased");
    initialQuantity = Integer.parseInt(quickViewComponent.getQuantity());
    quickViewComponent.clickPLusButton();
    AfterQuantity = Integer.parseInt(quickViewComponent.getQuantity());
    Assert.assertNotEquals(initialQuantity, AfterQuantity, "Quantity not Increased");
  }

  @Test(
      groups = {Constants.TestNGGroups.PLP, Constants.TestNGGroups.WEB_REGRESSION},
      description = "11107486")
  public void itemTypeTest() {

    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLP();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Open QuickView Modal");
    QuickViewComponent quickViewComponent = productListPage.clickOverFirstQuickViewButton();

    logger.info("3. Select the alternate option and verify the selected option");
  }

  @Test(
      groups = {Constants.TestNGGroups.PLP, Constants.TestNGGroups.WEB_REGRESSION},
      description = "11107489")
  public void equipmentTest() {

    logger.info("1. Navigate to Product list page");
    ProductListPage productListPage = navigateToGearSection();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Open QuickView Modal");
    QuickViewComponent quickViewComponent = productListPage.clickOverFirstQuickViewButton();

    logger.info("3. Review Grind");
    Assert.assertFalse(
        quickViewComponent.isGrindDisplayed(),
        "Grind option should not be available for Gear items");
  }
}
