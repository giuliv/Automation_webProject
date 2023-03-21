package com.applause.auto.test.web.plp;

import static com.applause.auto.common.data.Constants.WebTestData.PLP_SHOPABBLE_ITEMS;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.Constants.TestData;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.common.data.enums.FooterOptions;
import com.applause.auto.test.web.BaseTest;
import com.applause.auto.web.components.MiniCart;
import com.applause.auto.web.components.QuickViewComponent;
import com.applause.auto.web.components.ShopRunnerComponent;
import com.applause.auto.web.components.plp.PlpLearnMoreOverlappingComponent;
import com.applause.auto.web.components.plp.PlpSignInOverlappingComponent;
import com.applause.auto.web.helpers.WebHelper;
import com.applause.auto.web.views.FreeHomeDeliveryPage;
import com.applause.auto.web.views.HomePage;
import com.applause.auto.web.views.ProductDetailsPage;
import com.applause.auto.web.views.ProductListPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MiniCartTest extends BaseTest {

  @Test(
      groups = {Constants.TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.FE_PROD_REGRESSION},
      description = "11109579")
  public void addToMiniCartTest() {

    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDPFromHome(coffeeSelected);
    Assert.assertNotNull(productDetailsPage, "Failed to navigate to Product Details Page");

    logger.info("2. Add item to MiniCart");
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();
    Assert.assertNotNull(miniCart, "Item was not added to miniCart");

    logger.info("FINISH");
  }

  @Test(
      groups = {Constants.TestNGGroups.TO_BE_RENAMED, Constants.TestNGGroups.SMOKE},
      description = "11101730")
  public void validateMiniCartElementsTest() {

    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDPFromHome(coffeeSelected);
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
      groups = {Constants.TestNGGroups.TO_BE_RENAMED},
      description = "11101731")
  public void itemsRemovedFromMiniCartTest() {

    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDPFromHome();
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
      groups = {Constants.TestNGGroups.TO_BE_RENAMED},
      description = "11101732")
  public void emptyMiniCartTest() {

    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDPFromHome();
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
      groups = {Constants.TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.FE_PROD_REGRESSION},
      description = "11101733")
  public void progressShippingBarTest() {

    logger.info("1. Navigate to PDP > Select One Time Purchase > Add to miniCart");
    ProductDetailsPage productDetailsPage = navigateToPDPFromHome(coffeeSelected);
    productDetailsPage.selectOneTimePurchase();
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("2. Validate message and icon");
    String originalShippingAway = miniCart.getShippingAwayMessage();
    Assert.assertTrue(
        originalShippingAway.contains("away from free shipping!"),
        "Shipping away message is not displayed");
    Assert.assertTrue(miniCart.isTruckIconDisplayed(), "Truck icon is not displayed");

    logger.info("3. Increase item and Validate progress bar");
    float progressBar = miniCart.getProgressBarQuantity();
    miniCart.addOneMoreItem();
    Assert.assertTrue(
        progressBar < miniCart.getProgressBarQuantity(), "Progress bar is not working correctly");

    logger.info("FINISH");
  }

  @Test(
      groups = {Constants.TestNGGroups.TO_BE_RENAMED},
      description = "11101739")
  public void learnMoreFromMiniCartTest() {

    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDPFromHome();
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
      groups = {Constants.TestNGGroups.TO_BE_RENAMED, Constants.TestNGGroups.SMOKE},
      description = "11101740")
  public void signInFromMiniCartTest() {

    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDPFromHome(coffeeSelected);
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
      groups = {Constants.TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.FE_PROD_REGRESSION},
      description = "11101734")
  public void recommendedItemsNavigationTest() {

    logger.info("1. Navigate to product details page");
    MiniCart miniCart = navigateToPDPFromHome(coffeeSelected).clickAddToMiniCart();

    logger.info("2. Validate Recommended Items Navigation");
    long originValue = miniCart.getRecommendedItemsVisible();
    Assert.assertTrue(originValue > 1, "Total Recommended items is not correct");
    miniCart.clickNavigationArrow(Constants.NavigationArrow.NEXT);

    long newValue = miniCart.getRecommendedItemsVisible();
    Assert.assertNotEquals(originValue, newValue, "Next arrow does not work correctly");

    miniCart.clickNavigationArrow(Constants.NavigationArrow.PREV);
    Assert.assertEquals(
        originValue, miniCart.getRecommendedItemsVisible(), "Prev arrow does not work correctly");

    logger.info("3. Click recommended item > Validate");
    int firstProduct = 0;
    miniCart.getRecommendedItemNameByIndex(firstProduct);
    QuickViewComponent quickViewComponent =
        miniCart.clickOnRecommendedForYouAddButtonByIndex(firstProduct);
    Assert.assertNotNull(quickViewComponent, "Failed to open to QuickView popup");

    logger.info("FINISH");
  }

  @Test(
      groups = {Constants.TestNGGroups.TO_BE_RENAMED, Constants.TestNGGroups.SANITY},
      description = "11101735",
      enabled = false)
  public void addRemoveRecommendedItemTest() {
    /**
     * TODO
     *
     * <p>Disabled this test for now as functionality isn't working on Stage
     */
    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDPFromHome();
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
      groups = {Constants.TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.FE_PROD_REGRESSION},
      description = "11101737")
  public void oneTimePurchaseMiniCartTest() {

    logger.info("1. Navigate to product details page");
    MiniCart miniCart = navigateToPDPFromHome(coffeeSelected).clickAddToMiniCart();
    Assert.assertTrue(
        miniCart.isSubscribeButtonEnabled(), "Subscription should be enable by default");

    logger.info("2. Select One time purchase > One time purchase should be selected");
    miniCart.clickOneTimePurchaseButton();
    // Todo: Add to cart validation does not apply, we are already on the miniCart

    logger.info("Verify that One time purchase should be selected");
    Assert.assertTrue(miniCart.isOneTimePurchaseButtonEnabled(), "One time purchase isn't enabled");
    Assert.assertFalse(
        miniCart.isShipEveryDropdownDisplayed(), "Ship every dropdown should not be displayed");

    logger.info("FINISH");
  }

  @Test(
      groups = {Constants.TestNGGroups.TO_BE_RENAMED},
      description = "11101738")
  public void subscriptionModeMiniCartTest() {

    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDPFromHome();
    Assert.assertNotNull(productDetailsPage, "Failed to navigate to Product Details Page");

    logger.info("2. Add item to MiniCart");
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("3. Select Subscribe > Subscribe should be selected.");
    miniCart.clickSubscribeButton();

    logger.info("Verify that Subscribe should be selected");
    Assert.assertTrue(miniCart.isSubscribeButtonEnabled(), "Subscribe isn't selected");

    logger.info("FINISH");
  }

  @Test(
      groups = {Constants.TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.FE_PROD_REGRESSION},
      description = "11107530")
  public void miniCartCheckoutBypassesCartTest() {

    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDPFromHome(coffeeSelected);
    Assert.assertNotNull(productDetailsPage, "Failed to navigate to Product Details Page");

    logger.info("2. Add something to mini cart");
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("3. Verify checkout button goes directly to checkout page.");
    miniCart.clickContinueToCheckOut();
    Assert.assertTrue(
        WebHelper.getCurrentUrl()
            .toLowerCase()
            .contains(Constants.CheckoutPageMiscData.CHECKOUT_PAGE_URL_PART),
        "Checkout page URL does not appear to be what we expect;  expected "
            + Constants.CheckoutPageMiscData.CHECKOUT_PAGE_URL_PART
            + " to be a part of "
            + WebHelper.getCurrentUrl().toLowerCase());
  }

  @Test(
      groups = {Constants.TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.FE_PROD_REGRESSION},
      description = "11107531")
  public void miniCartViewCartGoesToCartTest() {

    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDPFromHome(coffeeSelected);
    Assert.assertNotNull(productDetailsPage, "Failed to navigate to Product Details Page");

    logger.info("2. Add something to mini cart");
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("3. Verify mini cart view cart button navigates user to cart");
    miniCart.clickViewCartButton();
    Assert.assertTrue(
        WebHelper.getCurrentUrl()
            .toLowerCase()
            .contains(Constants.CartPageMiscData.CART_PAGE_URL_PART),
        "Cart page URL does not appear to be what we expect;  expected "
            + Constants.CartPageMiscData.CART_PAGE_URL_PART
            + " to be a part of "
            + WebHelper.getCurrentUrl().toLowerCase());
  }

  @Test(
      groups = {Constants.TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.FE_PROD_REGRESSION},
      description = "11107533")
  public void miniCartSubscribeSetupTest() {
    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDPFromHome(coffeeSelected);
    Assert.assertNotNull(productDetailsPage, "Failed to navigate to Product Details Page");

    logger.info("2. Add something as one time purchase to mini cart");
    productDetailsPage.selectOneTimePurchase();
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("3. Verify one time purchase is default selection");
    softAssert.assertTrue(
        miniCart.isOneTimePurchaseButtonEnabled(),
        "One time purchase is not the default selection, and it was expected to be.");
    miniCart.removeItem();
    miniCart.closeMiniCart(ProductDetailsPage.class);

    logger.info("4. Verify subscribe purchase is default selection");
    productDetailsPage.selectSubscribeType();
    miniCart = productDetailsPage.clickAddToMiniCart();
    softAssert.assertTrue(
        miniCart.isSubscribeButtonEnabled(),
        "Subscribe is not the default selection, and it was expected to be.");

    softAssert.assertAll();
  }

  @Test(
      groups = {Constants.TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.FE_PROD_REGRESSION},
      description = "11107532")
  public void miniCartShopRunner2DayShippingTest() {

    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDPFromHome(coffeeSelected);
    Assert.assertNotNull(productDetailsPage, "Failed to navigate to Product Details Page");

    logger.info("2. Add something to mini cart");
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("3. Verify 2 day shipping is there");
    softAssert.assertTrue(
        miniCart.getShopRunnerMessage().contains(Constants.MiniCartMiscTestData.SHOPRUNNER_MESSAGE),
        "ShopRunner text does not appear to match.  Expected "
            + Constants.MiniCartMiscTestData.SHOPRUNNER_MESSAGE
            + " to be a part of "
            + miniCart.getShopRunnerMessage());

    logger.info("4. Click Learn More and Verify Learn More overlapping Modal Free shipping Text ");
    PlpLearnMoreOverlappingComponent learMoreOverlapping = miniCart.clickLearnMoreLink();
    Assert.assertEquals(
        learMoreOverlapping.getShippingText(),
        TestData.SHOP_RUNNER_FREE_TEXT,
        "FREE 2-Day Shipping & Free Returns Text is not displayed");
    miniCart = learMoreOverlapping.clickCloseButton(MiniCart.class);

    logger.info("5. Click Sign In Link and Verify Sign In overlapping modal");
    PlpSignInOverlappingComponent signInOverlapping = miniCart.clickSignInLink();
    signInOverlapping.validateShopRunnerSignInModalUIElements().assertAll();

    softAssert.assertAll();
  }

  @Test(
      groups = {Constants.TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.FE_PROD_REGRESSION},
      description = "11109580")
  public void emptyMiniCart2Test() {

    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    homePage.closeInitialBannersAndModals();

    logger.info("2. Open MiniCart");
    MiniCart miniCart = homePage.getHeader().clickCartIcon();

    logger.info("3. Validate Empty MiniCart Message");
    Assert.assertEquals(
        miniCart.getEmptyMiniCartMessage(),
        "Your cart is empty. Do you need any beans or tea?",
        "Empty message is not correct");

    logger.info("4. Validating...");
    int total = miniCart.getTotalShopabbleItems();
    Assert.assertEquals(total, 4, "Total Shopabble items does not match");
    for (int item = 0; item < total; item++) {
      Assert.assertTrue(miniCart.isShopabbleItemDisplayed(item), "Shopabble item not displayed");
      if (item == total - 1) {
        Assert.assertTrue(
            miniCart
                .openShopabbleItemsByIndex(FreeHomeDeliveryPage.class, item)
                .isPageHeadingDisplayed(),
            "Title does not matches");
      } else {
        Assert.assertEquals(
            miniCart.openShopabbleItemsByIndex(ProductListPage.class, item).getPageHeader(),
            PLP_SHOPABBLE_ITEMS.get(item),
            "Title does not matches");
      }

      homePage = navigateToHome();
      miniCart = homePage.getHeader().clickCartIcon();
    }

    logger.info("FINISH");
  }

  @Test(
      groups = {Constants.TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.FE_PROD_REGRESSION},
      description = "11109581",
      enabled = false)
  // Todo:EstimatedShipDate not longer displayed[21.03.2023]
  public void estimatedShipDateTest() {

    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDPFromHome(coffeeSelected);
    Assert.assertNotNull(productDetailsPage, "Failed to navigate to Product Details Page");

    logger.info("2. Add item to MiniCart");
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();
    Assert.assertNotNull(miniCart, "Item was not added to miniCart");

    logger.info("3. Validating Estimated Date Tooltip");
    Assert.assertTrue(
        miniCart.estimatedShipDateIsDisplayed(), "Estimated ship date is not displayed");
    Assert.assertTrue(
        miniCart.getEstimatedShipDate().contains(Constants.WebTestData.SHIP_ON_MESSAGE),
        "Ship on or before text does not match");
    // Todo:What is the logic of the message and value, we need some clarifications here [Ask]

    miniCart.openEstimatedTooltip();
    Assert.assertEquals(
        miniCart.getEstimatedDateTooltipText(),
        Constants.WebTestData.FAQ_MESSAGE,
        "Estimated tooltip text does not matches");
    miniCart.openEstimatedFAQLink();
    Assert.assertTrue(
        WebHelper.getCurrentUrl().contains(FooterOptions.HELP_CENTER.getUrl()),
        "FAQ URL does not matches");

    logger.info("FINISH");
  }
}
