package com.applause.auto.test.new_web.plp;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.Constants.TestData;
import com.applause.auto.new_web.components.MiniCart;
import com.applause.auto.new_web.components.QuickViewComponent;
import com.applause.auto.new_web.components.plp.PlpLearnMoreOverlappingComponent;
import com.applause.auto.new_web.components.plp.PlpSignInOverlappingComponent;
import com.applause.auto.new_web.views.HomePage;
import com.applause.auto.new_web.views.ProductDetailsPage;
import com.applause.auto.new_web.views.ProductListPage;
import com.applause.auto.new_web.views.SearchResultsPage;
import com.applause.auto.test.new_web.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class QuickViewTest extends BaseTest {

  @Test(
      groups = {Constants.TestNGGroups.TO_BE_RENAMED},
      description = "11101530")
  public void reviewQuickViewUITest() {

    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLPFromHome(TestData.COFFEE_BEST_SELLERS_URL);
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Validate UI Elements of QuickView Modal");
    QuickViewComponent quickViewComponent = productListPage.clickOverFirstQuickViewButton();
    quickViewComponent.validateMainUIElements().assertAll();

    logger.info("FINISH");
  }

  @Test(
      groups = {Constants.TestNGGroups.FRONT_END_REGRESSION},
      description = "11110671")
  public void coffeeItemGrindTest() {

    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLPFromHome(TestData.COFFEE_BEST_SELLERS_URL);
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
      groups = {Constants.TestNGGroups.FRONT_END_REGRESSION},
      description = "11110672")
  public void itemQuantityTest() {

    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLPFromHome(TestData.COFFEE_BEST_SELLERS_URL);
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Select new Quantity > Review Quantity element");
    QuickViewComponent quickViewComponent = productListPage.clickOverFirstQuickViewButton();
    quickViewComponent.validateQuantityElements().assertAll();

    logger.info("FINISH");
  }

  @Test(
      groups = {Constants.TestNGGroups.FRONT_END_REGRESSION},
      description = "11110673")
  public void addToCartTest() {

    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLPFromHome(TestData.COFFEE_BEST_SELLERS_URL);
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
      groups = {Constants.TestNGGroups.FRONT_END_REGRESSION},
      description = "11107487")
  public void shopRunnerTest() {
    logger.info("1. Navigate to Home");
    HomePage homePage = navigateToHome();
    homePage.closeInitialBannersAndModals();

    logger.info("2. Search for the product: {}", coffeeSelected);
    SearchResultsPage searchResultsPage =
        homePage.getHeader().getSearchComponent().search(coffeeSelected);

    logger.info("3. Open QuickView Modal");
    QuickViewComponent quickViewComponent =
        searchResultsPage.clickOverQuickViewByProduct(coffeeSelected);

    logger.info("4. Validate Shop Runner UI Elements of ShopRunner");
    quickViewComponent.validateShopRunnerUIElements().assertAll();

    logger.info("5. Click LearnMore and Verify LearnMore overlapping Modal Free shipping Text");
    PlpLearnMoreOverlappingComponent learMoreOverlapping = quickViewComponent.clickLearnMoreLink();
    softAssert.assertEquals(
        learMoreOverlapping.getShippingText(),
        TestData.SHOP_RUNNER_FREE_TEXT,
        "FREE 2-Day Shipping & Free Returns Text is not displayed");
    quickViewComponent = learMoreOverlapping.clickCloseButton(QuickViewComponent.class);

    logger.info("6. Click Sign In Link and Verify SignIn overlapping Modal");
    PlpSignInOverlappingComponent signInOverlapping = quickViewComponent.clickSignInLink();
    signInOverlapping.validateShopRunnerSignInModalUIElements().assertAll();
  }

  @Test(
      groups = {Constants.TestNGGroups.FRONT_END_REGRESSION},
      description = "11107488")
  public void teaItemTest() {

    logger.info("1. Navigate to Home");
    HomePage homePage = navigateToHome();
    homePage.closeInitialBannersAndModals();

    logger.info("2. Search for the product: {}", TestData.TEA_JASMINE);
    SearchResultsPage searchResultsPage =
        homePage.getHeader().getSearchComponent().search(TestData.TEA_JASMINE);

    logger.info("3. Open QuickView Modal");
    QuickViewComponent quickViewComponent =
        searchResultsPage.clickOverQuickViewByProduct(TestData.TEA_JASMINE);

    // Todo:Need to improve this way to validate this things, it sucks
    logger.info("3. Select the alternate option and verify the selected option");
    quickViewComponent.selectOption(1);
    Assert.assertTrue(
        quickViewComponent.selectedOptionText().contains(TestData.GRIND_NEXT_TEXT),
        "Selected Drop Down Value Mismatches " + quickViewComponent.selectedOptionText());

    quickViewComponent.selectOption(0);
    Assert.assertTrue(
        quickViewComponent.selectedOptionText().contains(TestData.GRIND_FIRST_TEXT),
        "Selected Drop Down Value Mismatches " + quickViewComponent.selectedOptionText());

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
      groups = {Constants.TestNGGroups.FRONT_END_REGRESSION},
      description = "11107486")
  public void itemTypeTest() {

    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLPFromHome();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Open QuickView Modal");
    QuickViewComponent quickViewComponent = productListPage.clickOverFirstQuickViewButton();

    logger.info("3. Select the alternate option and verify the selected option");
  }

  @Test(
      groups = {Constants.TestNGGroups.FRONT_END_REGRESSION},
      description = "11107489")
  public void equipmentTest() {

    logger.info("1. Navigate to Product list page");
    ProductListPage productListPage = navigateToGearSectionFromHome();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Open QuickView Modal");
    QuickViewComponent quickViewComponent = productListPage.clickOverFirstQuickViewButton();

    logger.info("3. Review Grind");
    Assert.assertFalse(
        quickViewComponent.isGrindDisplayed(),
        "Grind option should not be available for Gear items");
  }

  @Test(
      groups = {Constants.TestNGGroups.FRONT_END_REGRESSION},
      description = "11107490")
  public void quickViewKCupTest() {
    logger.info("1. Navigate to Home");
    HomePage homePage = navigateToHome();
    homePage.closeInitialBannersAndModals();

    logger.info("2. Search for the product: {}", TestData.K_CUP);
    SearchResultsPage searchResultsPage =
        homePage.getHeader().getSearchComponent().search(TestData.K_CUP);

    logger.info("3. Open QuickView Modal");
    QuickViewComponent quickViewComponent = searchResultsPage.clickOverFirstQuickViewButton();

    logger.info("4. Review Grind and Quantity elements");
    Assert.assertFalse(
        quickViewComponent.isGrindDisplayed(),
        "Grind option should not be available for Gear items");
    Assert.assertTrue(
        quickViewComponent.isGeneralQuantityBoxDisplayed(), "Quantity Section is not displayed");
  }

  @Test(
      groups = {Constants.TestNGGroups.FRONT_END_REGRESSION},
      description = "11107491")
  public void guestSubscribeAndShipTest() {
    SoftAssert softAssert = new SoftAssert();

    logger.info("1. Navigate to Home");
    HomePage homePage = navigateToHome();
    homePage.closeInitialBannersAndModals();

    logger.info("2. Search for the product: {}", coffeeSelected);
    SearchResultsPage searchResultsPage =
        homePage.getHeader().getSearchComponent().search(coffeeSelected);

    logger.info("3. Open QuickView Modal");
    QuickViewComponent quickViewComponent =
        searchResultsPage.clickOverQuickViewByProduct(coffeeSelected);

    logger.info("4. Review Subscribe type/weeks is displayed by default");
    softAssert.assertTrue(
        quickViewComponent.isSubscribeTypeAsDefault(), "Subscribe is not selected by default");
    softAssert.assertTrue(
        quickViewComponent.isSubscriptionWeeksBoxDisplayed(), "Subscribe weeks is not displayed");
    softAssert.assertTrue(
        quickViewComponent.subscriptionOptionsAvailable() > 1, "Weeks cannot be less than 1");

    logger.info("5. Review Subscribe/Add to Cart button");
    softAssert.assertTrue(
        quickViewComponent.isAddToCartButtonDisplayed(),
        "Add to cart/Subscribe button is not displayed");
    softAssert.assertEquals(
        quickViewComponent.getAddToCartButtonText(),
        "SUBSCRIBE & SHIP FREE",
        "Subscribe button is not named properly");
    softAssert.assertAll();
  }

  @Test(
      groups = {Constants.TestNGGroups.FRONT_END_REGRESSION},
      description = "11107492")
  public void otpAddToCartTest() {

    logger.info("1. Navigate to Product list page");
    ProductListPage productListPage = navigateToGearSectionFromHome();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Open QuickView Modal");
    QuickViewComponent quickViewComponent = productListPage.clickOverFirstQuickViewButton();

    logger.info("3. Review Add to Cart button");
    Assert.assertTrue(
        quickViewComponent.isAddToCartButtonDisplayed(), "Add to cart button is not displayed");
    Assert.assertEquals(
        quickViewComponent.getAddToCartButtonText(),
        "ADD TO CART",
        "ADD TO CART button is not named properly");
    Assert.assertFalse(
        quickViewComponent.areSubscribeOrOneTimePurchaseDisplayed(),
        "Subscribe/OneTimePurchase should not be visible");
  }

  @Test(
      groups = {Constants.TestNGGroups.FRONT_END_REGRESSION},
      description = "11118050")
  public void coffeePCPPageTest() {
    logger.info("1. Navigate to Home");
    HomePage homePage = navigateToHome();
    homePage.closeInitialBannersAndModals();

    logger.info("2. Search for the product: {}", coffeeSelected);
    SearchResultsPage searchResultsPage =
        homePage.getHeader().getSearchComponent().search(coffeeSelected);

    logger.info("3. Open QuickView Modal");
    QuickViewComponent quickViewComponent =
        searchResultsPage.clickOverQuickViewByProduct(coffeeSelected);
    Assert.assertNotNull(quickViewComponent, "QuickView is not displayed");

    logger.info("FINISH");
  }

  @Test(
      groups = {Constants.TestNGGroups.FRONT_END_REGRESSION},
      description = "11118051")
  public void coffeeItemDetailsTest() {
    logger.info("1. Navigate to Home");
    HomePage homePage = navigateToHome();
    homePage.closeInitialBannersAndModals();

    logger.info(
        "2. Search for the product: {}", Constants.StandardCoffeeInventory.Coffee1.getValue());
    SearchResultsPage searchResultsPage =
        homePage
            .getHeader()
            .getSearchComponent()
            .search(Constants.StandardCoffeeInventory.Coffee1.getValue());

    logger.info("3. Open QuickView Modal");
    QuickViewComponent quickViewComponent =
        searchResultsPage.clickOverQuickViewByProduct(
            Constants.StandardCoffeeInventory.Coffee1.getValue());
    quickViewComponent.validateMainAndSecondaryUIElements().assertAll();

    logger.info("FINISH");
  }

  @Test(
      groups = {Constants.TestNGGroups.FRONT_END_REGRESSION},
      description = "11118052")
  public void viewProductTest() {
    logger.info("1. Navigate to PDP");
    HomePage homePage = navigateToHome();
    homePage.closeInitialBannersAndModals();

    logger.info("2. Search for the product: {}", TestData.EQUIPMENT_NAME_OOO);
    SearchResultsPage searchResultsPage =
        homePage.getHeader().getSearchComponent().search(TestData.EQUIPMENT_NAME_OOO);
    ProductDetailsPage productDetailsPage = searchResultsPage.clickViewProduct();

    logger.info("3. Validating...");
    Assert.assertNotNull(productDetailsPage, "PDP was not opened");
    Assert.assertTrue(productDetailsPage.isProductNameDisplayed(), "Product name is not displayed");
    logger.info("FINISH");
  }
}
