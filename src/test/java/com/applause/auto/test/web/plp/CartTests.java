package com.applause.auto.test.web.plp;

import static com.applause.auto.common.data.Constants.WebTestData.PLP_SHOPABBLE_ITEMS;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.common.data.enums.FooterOptions;
import com.applause.auto.test.web.BaseTest;
import com.applause.auto.web.components.MiniCart;
import com.applause.auto.web.components.NeverMissAnOfferChunk;
import com.applause.auto.web.components.OtherPurchasedItemChunk;
import com.applause.auto.web.components.QuickViewComponent;
import com.applause.auto.web.helpers.WebHelper;
import com.applause.auto.web.views.CartPage;
import com.applause.auto.web.views.CheckOutPage;
import com.applause.auto.web.views.FreeHomeDeliveryPage;
import com.applause.auto.web.views.ProductDetailsPage;
import com.applause.auto.web.views.ProductListPage;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CartTests extends BaseTest {

  @Test(
      groups = {Constants.TestNGGroups.TO_BE_RENAMED, Constants.TestNGGroups.SANITY},
      description = "11101745")
  public void cartCanCheckoutFromCartTest() {

    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDPFromHome(coffeeSelected);
    Assert.assertNotNull(productDetailsPage, "Failed to navigate to Product Details Page");

    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("2. Click on View cart");
    CartPage cartPage = miniCart.clickViewCartButton();

    logger.info("3. Click on Checkout > Checkout page should display.");
    CheckOutPage checkOutPage = cartPage.clickContinueToCheckOut();

    logger.info("Verify 4: Checkout page should display.");
    Assert.assertNotNull(checkOutPage, "Failed to navigate to Checkout Page");
  }

  @Test(
      groups = {Constants.TestNGGroups.TO_BE_RENAMED},
      description = "11101742",
      enabled = false)
  /**
   * TODO
   *
   * <p>Disabled this test for now as functionality isn't working on Stage
   */
  public void itemQuantityCanBeIncreasedDecreaseAndRemovedTest() {

    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDPFromHome(coffeeSelected);
    Assert.assertNotNull(productDetailsPage, "Failed to navigate to Product Details Page");

    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("3. Click on View cart.");
    int firstProduct = 0;
    QuickViewComponent quickViewComponent =
        miniCart.clickOnRecommendedForYouAddButtonByIndex(firstProduct);
    Assert.assertNotNull(quickViewComponent, "Failed to open to QuickView popup");

    logger.info("4. Click on Add to cart");
    miniCart = quickViewComponent.clickAddToCart();

    logger.info("5. Click on View cart");
    CartPage cartPage = miniCart.clickViewCartButton();

    logger.info("6. Click on (+) > Number should increase +1.");
    int productQuantity = cartPage.getProductQuantityByIndex(firstProduct);
    cartPage.increaseQuantity(firstProduct);
    Assert.assertEquals(
        cartPage.getProductQuantityByIndex(firstProduct),
        productQuantity + 1,
        "Quantity does not increase +1");
    cartPage.decreaseQuantity(firstProduct);
    Assert.assertEquals(
        cartPage.getProductQuantityByIndex(firstProduct),
        productQuantity,
        "Quantity does not decrease -1");
  }

  @Test(
      groups = {
        Constants.TestNGGroups.TO_BE_RENAMED,
        Constants.TestNGGroups.SMOKE,
      },
      description = "11101741")
  public void reviewCartPageUiElementsTest() {
    SoftAssert softAssert = new SoftAssert();

    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDPFromHome(coffeeSelected);

    String productName = productDetailsPage.getProductName();
    String productGrind = productDetailsPage.getGrindSelected();
    String productPrice = productDetailsPage.getProductPrice();
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("2. Click on View cart.");
    CartPage cartPage = miniCart.clickViewCartButton();
    int firstProduct = 1;
    softAssert.assertEquals(
        cartPage.getProductNameByIndex(firstProduct), productName, "Product name doesn't match");
    softAssert.assertEquals(
        cartPage.getGrindSelectedByIndex(firstProduct),
        productGrind,
        "Product grind doesn't match");
    softAssert.assertTrue(
        cartPage.getProductPriceByIndex(firstProduct).contains(productPrice),
        "Product price doesn't match");
    softAssert.assertTrue(
        cartPage.productImageIsDisplayed(firstProduct), "Product image is not displayed");
    softAssert.assertTrue(
        cartPage.progressShippingBarIsDisplayed(), "Progress shipping bar is not displayed");
    softAssert.assertTrue(cartPage.thisIsGiftIsDisplayed(), "This is a gift is not displayed");
    softAssert.assertTrue(cartPage.subTotalIsDisplayed(), "Subtotal displays is not displayed");
    // Todo: Estimated message has been removed[20.03.2023]
    //    softAssert.assertTrue(
    //        cartPage.estimatedShipDateIsDisplayed(), "Estimated ship date is not displayed");
    softAssert.assertTrue(cartPage.checkOutButtonIsDisplayed(), "Checkout button is not displayed");
    softAssert.assertTrue(
        cartPage.otherPurchasedIsDisplayed(), "Others purchased is not displayed");

    softAssert.assertAll();
  }

  @Test(
      groups = {Constants.TestNGGroups.TO_BE_RENAMED},
      description = "11101743",
      enabled = false)
  public void cartReviewOneTimePurchase() {
    /**
     * TODO
     *
     * <p>Disabled this test for now as functionality isn't working on Stage
     */
    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDPFromHome(coffeeSelected);
    Assert.assertNotNull(productDetailsPage, "Failed to navigate to Product Details Page");

    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("2. Click on View cart.");
    int firstProduct = 0;
    QuickViewComponent quickViewComponent =
        miniCart.clickOnRecommendedForYouAddButtonByIndex(firstProduct);
    Assert.assertNotNull(quickViewComponent, "Failed to open to QuickView popup");

    logger.info("3. Click on Add to cart");
    miniCart = quickViewComponent.clickAddToCart();

    logger.info("4. Click on View cart");
    CartPage cartPage = miniCart.clickViewCartButton();

    logger.info("5. Select One time purchase > One time purchase should be selected");
    cartPage.clickOneTimePurchaseButton();

    logger.info("6. Verify that One time purchase should be selected");
    Assert.assertTrue(
        cartPage.isOneTimePurchaseButtonSelected(), "One time purchase isn't selected");
  }

  @Test(
      groups = {Constants.TestNGGroups.TO_BE_RENAMED},
      description = "11101744",
      enabled = false)
  /**
   * TODO
   *
   * <p>Disabled this test for now as functionality isn't working on Stage
   */
  public void reviewSubscription() {
    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDPFromHome(coffeeSelected);
    Assert.assertNotNull(productDetailsPage, "Failed to navigate to Product Details Page");

    int firstProduct = 0;
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("2. Click on View cart.");
    QuickViewComponent quickViewComponent =
        miniCart.clickOnRecommendedForYouAddButtonByIndex(firstProduct);
    Assert.assertNotNull(quickViewComponent, "Failed to open to QuickView popup");

    logger.info("3. Click on Add to cart");
    miniCart = quickViewComponent.clickAddToCart();

    logger.info("4. Click on View cart");
    CartPage cartPage = miniCart.clickViewCartButton();

    logger.info("5. Select Subscribe > Subscribe should be selected.");
    cartPage.clickSubscribeButton();

    logger.info("6. Verify that Subscribe should be selected");
    Assert.assertTrue(cartPage.isSubscribeButtonEnabled(), "Subscribe isn't selected");
  }

  @Test(
      groups = {Constants.TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.FE_PROD_REGRESSION},
      description = "11101746")
  public void canAddOthersPurchased() {

    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDPFromHome(coffeeSelected);
    Assert.assertNotNull(productDetailsPage, "Failed to navigate to Product Details Page");

    logger.info("2. Click on View cart.");
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();
    CartPage cartPage = miniCart.clickViewCartButton();
    Assert.assertEquals(
        cartPage.getTotalPurchasedItems(), 4, "Default Total Other purchased items does not match");

    logger.info("3. Click on Add Others purchased");
    OtherPurchasedItemChunk purchasedItemOnPosition = cartPage.getPurchasedItemOnPosition(1);
    String purchasedItemName = purchasedItemOnPosition.getName();
    QuickViewComponent quickViewComponent = purchasedItemOnPosition.clickAddToCartButton();

    logger.info("4. Verify that product name is correct");
    Assert.assertEquals(
        quickViewComponent.getProductName(),
        purchasedItemName,
        "Product name in the Quick View is wrong");
  }

  @Test(
      groups = {Constants.TestNGGroups.TO_BE_RENAMED},
      description = "11101747",
      enabled = false)
  public void canSignUpForNeverMissAnOffer() {
    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDPFromHome(coffeeSelected);
    Assert.assertNotNull(productDetailsPage, "Failed to navigate to Product Details Page");

    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("2. Click on View cart.");
    CartPage cartPage = miniCart.clickViewCartButton();

    logger.info("3. Click on type an email in Never miss an offer.");
    NeverMissAnOfferChunk neverMissAnOfferChunk = cartPage.getNeverMissAnOfferChunk();
    String randomEmail = RandomStringUtils.random(10, true, true) + "@gmail.com";
    neverMissAnOfferChunk = neverMissAnOfferChunk.signUp(randomEmail);

    logger.info("4. Verify that Successful message is displayed");
    Assert.assertEquals(
        neverMissAnOfferChunk.getMessage().replaceAll(" ", ""),
        Constants.TestData.SUBSCRIBING_MESSAGE.replaceAll(" ", ""),
        "Successful message didn't appear");
  }

  @Test(
      groups = {
        Constants.TestNGGroups.FRONT_END_REGRESSION,
        Constants.TestNGGroups.WEB_CART,
        TestNGGroups.FE_PROD_REGRESSION
      },
      description = "11101748")
  public void canCheckThisIsGift() {
    logger.info("1. Navigate to PDP > Add to miniCart");
    MiniCart miniCart = navigateToPDPFromHome(coffeeSelected).clickAddToMiniCart();

    logger.info("2. Click on View cart.");
    CartPage cartPage = miniCart.clickViewCartButton();

    logger.info("3. Click on This is a gift > TextBox should display.");
    cartPage = cartPage.clickOnThisIsGift();
    Assert.assertTrue(
        cartPage.getPersonalMessageTitle().contains(Constants.TestData.PERSONAL_MESSAGE),
        "Personal message title is not displayed");

    logger.info("4. Type any message");
    String message = "Applause Automation message 1";
    cartPage = cartPage.typePersonalMessage(message);

    // Todo:Missing 180 characters validation
    logger.info("5. Verify that message is present inside the field");
    Assert.assertEquals(
        cartPage.getAddPersonalMessageFieldText(), message, "Message isn't correct");
  }

  @Test(
      groups = {
        Constants.TestNGGroups.FRONT_END_REGRESSION,
        Constants.TestNGGroups.WEB_CART,
        TestNGGroups.FE_PROD_REGRESSION
      },
      description = "11108611")
  public void addItemToCart() {
    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDPFromHome(coffeeSelected);
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("2. Click on View cart.");
    CartPage cartPage = miniCart.clickViewCartButton();
    Assert.assertNotNull(cartPage, "Cart is not displayed");
  }

  @Test(
      groups = {
        Constants.TestNGGroups.FRONT_END_REGRESSION,
        Constants.TestNGGroups.WEB_CART,
        TestNGGroups.FE_PROD_REGRESSION
      },
      description = "11107534")
  public void reviewEmptyCartTest() {
    logger.info("1. Navigate to product details page");
    CartPage cartPage =
        navigateToPDPFromHome(coffeeSelected).clickAddToMiniCart().clickViewCartButton();

    logger.info("3. Make Cart empty");
    int firstItem = 0;
    cartPage = cartPage.decreaseQuantity(firstItem);

    logger.info("4. Validating...");
    int total = cartPage.getTotalShopabbleItems();
    Assert.assertEquals(
        cartPage.getEmptyCartMessage(),
        Constants.WebTestData.EMPTY_CART_MESSAGE,
        "Empty cart message is not correct");
    Assert.assertEquals(total, 4, "Total Shopabble items does not match");
    for (int item = 0; item < total; item++) {
      Assert.assertTrue(cartPage.isShopabbleItemDisplayed(item), "Shopabble item not displayed");
      if (item == total - 1) {
        Assert.assertTrue(
            cartPage
                .openShopabbleItemsByIndex(FreeHomeDeliveryPage.class, item)
                .isPageHeadingDisplayed(),
            "Title does not matches");
      } else {
        Assert.assertEquals(
            cartPage.openShopabbleItemsByIndex(ProductListPage.class, item).getPageHeader(),
            PLP_SHOPABBLE_ITEMS.get(item),
            "Title does not matches");
      }
      cartPage = WebHelper.navigateBack(CartPage.class);
      cartPage.closeBannerButton(CartPage.class);
    }
  }

  @Test(
      groups = {
        Constants.TestNGGroups.FRONT_END_REGRESSION,
        Constants.TestNGGroups.WEB_CART,
        TestNGGroups.FE_PROD_REGRESSION
      },
      description = "11108612")
  public void shippingCartTest() {
    logger.info("1. Navigate to PDP > Add to miniCart");
    MiniCart miniCart = navigateToPDPFromHome(coffeeSelected).clickAddToMiniCart();

    logger.info("2. Click on View cart.");
    miniCart.clickOneTimePurchaseButton();
    CartPage cartPage = miniCart.clickViewCartButton();
    String originalShippingAway = cartPage.getShippingAwayMessage();
    Assert.assertTrue(
        originalShippingAway.contains("away from free shipping!"),
        "Shipping away message is not displayed");

    logger.info("3. Increase order so Shipping away is updated");
    int product = 0;
    cartPage = cartPage.increaseQuantity(product);

    Assert.assertNotEquals(
        originalShippingAway,
        cartPage.getShippingAwayMessage(),
        "Shipping away message should have updated");
  }

  @Test(
      groups = {
        Constants.TestNGGroups.FRONT_END_REGRESSION,
        Constants.TestNGGroups.WEB_CART,
        TestNGGroups.FE_PROD_REGRESSION
      },
      description = "11109098")
  public void singeStandardItemTest() {
    // ToDo: "Get free ground shipping on subscriptions" (this message changes frequently)
    // Estimated ship date logic
    SoftAssert softAssert = new SoftAssert();

    logger.info("1. Navigate to PDP > Add to miniCart");
    ProductDetailsPage productDetailsPage = navigateToPDPFromHome(coffeeSelected);
    String productName = productDetailsPage.getProductName();
    String productGrind = productDetailsPage.getGrindSelected();
    String productPrice = productDetailsPage.getProductPrice();

    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("2. Click on View cart.");
    int firstProduct = 1;
    CartPage cartPage = miniCart.clickViewCartButton();

    logger.info("3. Validating Heading, Product image and name");
    softAssert.assertEquals(cartPage.getCartPageTitle(), "CART", "Cart page title does not match");
    softAssert.assertTrue(
        cartPage.productImageIsDisplayed(firstProduct), "Product image is not displayed");
    softAssert.assertEquals(
        cartPage.getProductNameByIndex(firstProduct), productName, "Product name doesn't match");

    logger.info("4. Select Subscribe > Validate Subscription Elements");
    softAssert.assertTrue(
        cartPage.isOneTimePurchaseButtonSelected(), "One time purchase isn't selected");
    cartPage.clickSubscribeButton();
    softAssert.assertTrue(
        cartPage.isFourWeeksSelectedByDefault(), "4 Weeks option is not by default");
    softAssert.assertTrue(
        cartPage.subscriptionOptionsAvailable() > 1, "There should be more subscription options");

    logger.info("5. Validating grind, quantity and price values");
    softAssert.assertEquals(
        cartPage.getGrindSelectedByIndex(firstProduct),
        productGrind,
        "Product grind doesn't match");
    softAssert.assertTrue(
        cartPage.getProductPriceByIndex(firstProduct).contains(productPrice),
        "Product price doesn't match");
    softAssert.assertTrue(cartPage.subTotalIsDisplayed(), "Subtotal displays is not displayed");

    int productQuantity = cartPage.getProductQuantityByIndex(firstProduct);
    cartPage = cartPage.increaseQuantity(firstProduct - 1);
    softAssert.assertEquals(
        cartPage.getProductQuantityByIndex(firstProduct),
        productQuantity + 1,
        "Quantity does not increase +1");
    cartPage = cartPage.decreaseQuantity(firstProduct - 1);
    softAssert.assertEquals(
        cartPage.getProductQuantityByIndex(firstProduct),
        productQuantity,
        "Quantity does not decrease -1");

    // Todo:Commented, no longer needed estimated date has been removed[20.03.2023]
    //    logger.info("5. Validating Estimated Date Tooltip");
    //    softAssert.assertTrue(
    //        cartPage.estimatedShipDateIsDisplayed(), "Estimated ship date is not displayed");
    //    cartPage.openEstimatedTooltip();
    //    softAssert.assertEquals(
    //        cartPage.getEstimatedDateTooltipText(),
    //        Constants.WebTestData.FAQ_MESSAGE,
    //        "Estimated tooltip text does not matches");
    //    cartPage.openEstimatedFAQLink();
    //    softAssert.assertTrue(
    //        WebHelper.getCurrentUrl().contains(FooterOptions.HELP_CENTER.getUrl()),
    //        "FAQ URL does not matches");
    // Todo:Ask if the FAQ, should be opened on a new tab

    softAssert.assertAll();

    CheckOutPage checkOutPage = cartPage.clickContinueToCheckOut();
    Assert.assertNotNull(checkOutPage, "CheckOut view was not displayed");
  }

  @Test(
      groups = {
        Constants.TestNGGroups.FRONT_END_REGRESSION,
        Constants.TestNGGroups.WEB_CART,
        TestNGGroups.FE_PROD_REGRESSION
      },
      description = "11109099")
  public void multipleStandardItemTest() {
    // ToDo:
    // Estimated ship date logic
    // Subtotal validation
    SoftAssert softAssert = new SoftAssert();

    logger.info("1. Navigate to PDP > Add to miniCart product1");
    ProductDetailsPage productDetailsPage =
        navigateToPDPFromHome(Constants.StandardCoffeeInventory.Coffee1.getValue());
    String productName = productDetailsPage.getProductName();
    String productGrind = productDetailsPage.getGrindSelected();
    String productPrice = productDetailsPage.getProductPrice();

    productDetailsPage.clickAddToMiniCart();

    logger.info("2. Navigate to PDP > Add to miniCart product2");
    productDetailsPage = navigateToPDP(Constants.StandardCoffeeInventory.Coffee3.getValue());
    String productName2 = productDetailsPage.getProductName();
    String productGrind2 = productDetailsPage.getGrindSelected();
    String productPrice2 = productDetailsPage.getProductPrice();
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("3. Click on View cart.");
    int firstProduct = 1;
    int secondProduct = 2;
    CartPage cartPage = miniCart.clickViewCartButton();

    logger.info("4. Validating Heading, Product image and name");
    softAssert.assertEquals(cartPage.getCartPageTitle(), "CART", "Cart page title does not match");
    softAssert.assertTrue(
        cartPage.productImageIsDisplayed(firstProduct), "Product image[1st] is not displayed");
    softAssert.assertTrue(
        cartPage.productImageIsDisplayed(secondProduct), "Product image[2nd] is not displayed");
    softAssert.assertEquals(
        cartPage.getProductNameByIndex(firstProduct), productName2, "Product name doesn't match");
    softAssert.assertEquals(
        cartPage.getProductNameByIndex(secondProduct),
        productName,
        "Product name[2nd] doesn't match");

    logger.info("5. Select Subscribe > Validate Subscription Elements");
    softAssert.assertTrue(cartPage.isSubscribeButtonSelected(), "Subscribe isn't selected");
    softAssert.assertTrue(
        cartPage.isFourWeeksSelectedByDefault(), "4 Weeks option is not by default");
    softAssert.assertTrue(
        cartPage.subscriptionOptionsAvailable() > 1, "There should be more subscription options");

    logger.info("6. Validating grind, quantity and price values");
    softAssert.assertEquals(
        cartPage.getGrindSelectedByIndex(firstProduct),
        productGrind2,
        "Product grind doesn't match");
    softAssert.assertEquals(
        cartPage.getGrindSelectedByIndex(secondProduct),
        productGrind,
        "Product grind[2nd] doesn't match");

    softAssert.assertTrue(
        cartPage.getProductTotalPriceByIndex(firstProduct).contains(productPrice2),
        "Product price doesn't match");
    softAssert.assertTrue(
        cartPage.getProductTotalPriceByIndex(secondProduct).contains(productPrice),
        "Product price[2nd] doesn't match");

    softAssert.assertTrue(cartPage.subTotalIsDisplayed(), "Subtotal displays is not displayed");

    int productQuantity = cartPage.getProductQuantityByIndex(firstProduct);
    cartPage = cartPage.increaseQuantity(firstProduct - 1);
    softAssert.assertEquals(
        cartPage.getProductQuantityByIndex(firstProduct),
        productQuantity + 1,
        "Quantity does not increase +1");
    cartPage = cartPage.decreaseQuantity(firstProduct - 1);
    softAssert.assertEquals(
        cartPage.getProductQuantityByIndex(firstProduct),
        productQuantity,
        "Quantity does not decrease -1");

    softAssert.assertAll();

    CheckOutPage checkOutPage = cartPage.clickContinueToCheckOut();
    Assert.assertNotNull(checkOutPage, "CheckOut view was not displayed");
  }

  @Test(
      groups = {
        Constants.TestNGGroups.FRONT_END_REGRESSION,
        Constants.TestNGGroups.WEB_CART,
        TestNGGroups.FE_PROD_REGRESSION
      },
      description = "11109100")
  public void oneSubscriptionOneStandardItemTest() {
    // ToDo:
    // Estimated ship date logic
    // Subtotal validation
    SoftAssert softAssert = new SoftAssert();

    logger.info("1. Navigate to PDP > Add to miniCart product1");
    ProductDetailsPage productDetailsPage =
        navigateToPDPFromHome(Constants.StandardCoffeeInventory.Coffee1.getValue());
    String productName = productDetailsPage.getProductName();
    String productGrind = productDetailsPage.getGrindSelected();
    String productPrice = productDetailsPage.getProductPrice();

    productDetailsPage.clickAddToMiniCart();

    logger.info("2. Navigate to PDP > Add to miniCart product2");
    productDetailsPage = navigateToPDP(Constants.StandardCoffeeInventory.Coffee3.getValue());
    String productName2 = productDetailsPage.getProductName();
    String productGrind2 = productDetailsPage.getGrindSelected();
    String productPrice2 = productDetailsPage.getProductPrice();
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("3. Click on View cart.");
    int firstProduct = 1;
    int secondProduct = 2;
    miniCart.clickOneTimePurchaseButton();
    CartPage cartPage = miniCart.clickViewCartButton();

    logger.info("4. Validating Heading, Product image and name");
    softAssert.assertEquals(cartPage.getCartPageTitle(), "CART", "Cart page title does not match");
    softAssert.assertTrue(
        cartPage.productImageIsDisplayed(firstProduct), "Product image[1st] is not displayed");
    softAssert.assertTrue(
        cartPage.productImageIsDisplayed(secondProduct), "Product image[2nd] is not displayed");
    softAssert.assertEquals(
        cartPage.getProductNameByIndex(firstProduct), productName2, "Product name doesn't match");
    softAssert.assertEquals(
        cartPage.getProductNameByIndex(secondProduct),
        productName,
        "Product name[2nd] doesn't match");

    logger.info("5. Select Subscribe > Validate Subscription Elements");
    softAssert.assertTrue(cartPage.isSubscribeButtonSelected(), "Subscribe isn't selected");
    softAssert.assertTrue(
        cartPage.isFourWeeksSelectedByDefault(), "4 Weeks option is not by default");
    softAssert.assertTrue(
        cartPage.subscriptionOptionsAvailable() > 1, "There should be more subscription options");

    logger.info("6. Validating grind, quantity and price values");
    softAssert.assertEquals(
        cartPage.getGrindSelectedByIndex(firstProduct),
        productGrind2,
        "Product grind doesn't match");
    softAssert.assertEquals(
        cartPage.getGrindSelectedByIndex(secondProduct),
        productGrind,
        "Product grind[2nd] doesn't match");

    softAssert.assertTrue(
        cartPage.getProductTotalPriceByIndex(firstProduct).contains(productPrice2),
        "Product price doesn't match");
    softAssert.assertTrue(
        cartPage.getProductTotalPriceByIndex(secondProduct).contains(productPrice),
        "Product price[2nd] doesn't match");

    softAssert.assertTrue(cartPage.subTotalIsDisplayed(), "Subtotal displays is not displayed");

    int productQuantity = cartPage.getProductQuantityByIndex(firstProduct);
    cartPage = cartPage.increaseQuantity(firstProduct - 1);
    softAssert.assertEquals(
        cartPage.getProductQuantityByIndex(firstProduct),
        productQuantity + 1,
        "Quantity does not increase +1");
    cartPage = cartPage.decreaseQuantity(firstProduct - 1);
    softAssert.assertEquals(
        cartPage.getProductQuantityByIndex(firstProduct),
        productQuantity,
        "Quantity does not decrease -1");

    softAssert.assertAll();

    CheckOutPage checkOutPage = cartPage.clickContinueToCheckOut();
    Assert.assertNotNull(checkOutPage, "CheckOut view was not displayed");
  }
}
