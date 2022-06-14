package com.applause.auto.test.new_web.plp;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.Constants.TestData;
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
    ProductListPage productListPage = navigateToPLP();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Validate UI Elements of QuickView Modal");
    QuickViewComponent quickViewComponent = productListPage.clickOverFirstQuickViewButton();
    quickViewComponent.validateMainUIElements().assertAll();

    logger.info("FINISH");
  }

  @Test(
      groups = {Constants.TestNGGroups.PLP, Constants.TestNGGroups.WEB_REGRESSION},
      description = "11107487")
  public void shoprunnerTest() {

    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLP(TestData.COFFEE_BEST_SELLERS_URL);
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Open QuickView Modal");
    QuickViewComponent quickViewComponent = productListPage.clickOverFirstQuickViewButton();

    logger.info("3. Validate Shop Runner UI Elements of ShopRuuner");
    quickViewComponent.validatShopRunnerUIElements().assertAll();

    logger.info("4. Click Learnmore and Verify Learnmore overlapping Modal Free shipping Text ");
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
}
