package com.applause.auto.test.new_web.plp;

import com.applause.auto.common.data.Constants;
import com.applause.auto.new_web.components.MiniCart;
import com.applause.auto.new_web.components.QuickViewComponent;
import com.applause.auto.new_web.views.CartPage;
import com.applause.auto.new_web.views.CheckOutPage;
import com.applause.auto.new_web.views.ProductDetailsPage;
import com.applause.auto.new_web.views.ProductListPage;
import com.applause.auto.test.new_web.BaseTest;
import com.applause.auto.web.components.NeverMissAnOfferChunk;
import com.applause.auto.web.components.OtherPurchasedItemChunk;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CartTests extends BaseTest {

  @Test(
      groups = {Constants.TestNGGroups.PLP},
      description = "11101745")
  public void cartCanCheckoutFromCartTest() {

    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLP();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Add an item");
    int firstProduct = 0;
    ProductDetailsPage productDetailsPage = productListPage.clickOverProductByIndex(firstProduct);
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("3. Click on View cart");
    CartPage cartPage = miniCart.clickViewCartButton();

    logger.info("4. Click on Checkout > Checkout page should display.");
    CheckOutPage checkOutPage = cartPage.clickContinueToCheckOut();

    logger.info("Verify 4: Checkout page should display.");
    Assert.assertNotNull(checkOutPage, "Failed to navigate to Checkout Page");
  }

  @Test(
      groups = {Constants.TestNGGroups.PLP},
      description = "11101742")
  public void itemQuantityCanBeIncreasedDecreaseAndRemovedTest() {

    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLP();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Add an item");
    int firstProduct = 0;
    ProductDetailsPage productDetailsPage = productListPage.clickOverProductByIndex(firstProduct);
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("3. Click on View cart.");
    QuickViewComponent quickViewComponent =
        miniCart.clickOnRecommendedForYouAddButtonByIndex(firstProduct);
    Assert.assertNotNull(quickViewComponent, "Failed to open to Quickview popup");

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
      groups = {Constants.TestNGGroups.PLP},
      description = "11101741")
  public void reviewCartPageUiElementsTest() {
    SoftAssert softAssert = new SoftAssert();

    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLP();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Add an item");
    ProductDetailsPage productDetailsPage = productListPage.clickOverProductByIndex(0);
    String productName = productDetailsPage.getProductName();
    String productGrind = productDetailsPage.getGrindSelected();
    String productPrice = productDetailsPage.getProductPrice();
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("3. Click on View cart.");
    CartPage cartPage = miniCart.clickViewCartButton();
    int firstProduct = 1;
    softAssert.assertEquals(
        cartPage.getProductNameByIndex(firstProduct), productName, "Product name doesn't match");
    softAssert.assertEquals(
        cartPage.getGrindSelectedByIndex(firstProduct),
        productGrind,
        "Product grind doesn't match");
    softAssert.assertEquals(
        cartPage.getProductPriceByIndex(firstProduct), productPrice, "Product price doesn't match");
    softAssert.assertTrue(
        cartPage.productImageIsDisplayed(firstProduct), "Product image is not displayed");
    softAssert.assertTrue(
        cartPage.progressShippingBarIsDisplayed(), "Progress shipping bar is not displayed");
    softAssert.assertTrue(cartPage.thisIsGiftIsDisplayed(), "This is a gift is not displayed");
    softAssert.assertTrue(cartPage.subTotalIsDisplayed(), "Subtotal displays is not displayed");
    softAssert.assertTrue(
        cartPage.estimatedShipDateIsDisplayed(), "Estimated ship date is not displayed");
    softAssert.assertTrue(cartPage.checkOutButtonIsDisplayed(), "Checkout button is not displayed");
    softAssert.assertTrue(
        cartPage.otherPurchasedIsDisplayed(), "Others purchased is not displayed");

    softAssert.assertAll();
  }

  @Test(
      groups = {Constants.TestNGGroups.PLP},
      description = "11101742")
  public void cartReviewOneTimePurchase() {

    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLP();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Add an item");
    int firstProduct = 0;
    ProductDetailsPage productDetailsPage = productListPage.clickOverProductByIndex(firstProduct);
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("3. Click on View cart.");
    QuickViewComponent quickViewComponent =
        miniCart.clickOnRecommendedForYouAddButtonByIndex(firstProduct);
    Assert.assertNotNull(quickViewComponent, "Failed to open to Quickview popup");

    logger.info("4. Click on Add to cart");
    miniCart = quickViewComponent.clickAddToCart();

    logger.info("5. Click on View cart");
    CartPage cartPage = miniCart.clickViewCartButton();

    logger.info("6. Select One time purchase > One time purchase should be selected");
    cartPage.clickOneTimePurchaseButton();

    logger.info("Verify that One time purchase should be selected");
    Assert.assertTrue(cartPage.isOneTimePurchaseButtonEnabled(), "One time purchase isn't enabled");
  }

  @Test(
      groups = {Constants.TestNGGroups.PLP},
      description = "11101744")
  public void reviewSubscription() {
    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLP();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Add an item");
    int firstProduct = 0;
    ProductDetailsPage productDetailsPage = productListPage.clickOverProductByIndex(firstProduct);
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("3. Click on View cart.");
    QuickViewComponent quickViewComponent =
        miniCart.clickOnRecommendedForYouAddButtonByIndex(firstProduct);
    Assert.assertNotNull(quickViewComponent, "Failed to open to Quickview popup");

    logger.info("4. Click on Add to cart");
    miniCart = quickViewComponent.clickAddToCart();

    logger.info("5. Click on View cart");
    CartPage cartPage = miniCart.clickViewCartButton();

    logger.info("6. Select Subscribe > Subscribe should be selected.");
    cartPage.clickSubscribeButton();

    logger.info("Verify that Subscribe should be selected");
    Assert.assertTrue(cartPage.isSubscribeButtonEnabled(), "Subscribe isn't selected");
  }

  @Test(
      groups = {Constants.TestNGGroups.PLP},
      description = "11101746",
      enabled = false)
  public void canAddOthersPurchased() {
    /**
     * TODO
     *
     * <p>Disabled this test for now as functionality isn't working on Stage
     */
    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLP();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Add an item");
    ProductDetailsPage productDetailsPage = productListPage.clickOverProductByIndex(1);
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("3. Click on View cart.");
    CartPage cartPage = miniCart.clickViewCartButton();

    logger.info("4. Click on Add Others purchased");
    OtherPurchasedItemChunk purchasedItemOnPosition = cartPage.getPurchasedItemOnPosition(1);
    String purchasedItemName = purchasedItemOnPosition.getName();
    QuickViewComponent quickViewComponent = purchasedItemOnPosition.clickAddToCartButton();

    logger.info("Verify that product name is correct");
    Assert.assertEquals(
        quickViewComponent.getProductName(),
        purchasedItemName,
        "Product name in the Quick View is wrong");
  }

  @Test(
      groups = {Constants.TestNGGroups.PLP},
      description = "11101747")
  public void canSignUpForNeverMissAnOffer() {
    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLP();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Add an item");
    ProductDetailsPage productDetailsPage = productListPage.clickOverProductByIndex(1);
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("3. Click on View cart.");
    CartPage cartPage = miniCart.clickViewCartButton();

    logger.info("4. Click on type an emain in Never miss an offer.");
    NeverMissAnOfferChunk neverMissAnOfferChunk = cartPage.getNeverMissAnOfferChunk();
    String randomEmail = RandomStringUtils.random(10, true, true) + "@gmail.com";
    neverMissAnOfferChunk = neverMissAnOfferChunk.signUp(randomEmail);

    logger.info("Verify that Successful message is displayed");
    Assert.assertEquals(
        neverMissAnOfferChunk.getMessage().replaceAll(" ", ""),
        Constants.TestData.SUBSCRIBING_MESSAGE.replaceAll(" ", ""),
        "Successful message didn't appear");
  }

  @Test(
      groups = {Constants.TestNGGroups.PLP},
      description = "11101748")
  public void canCheckThisIsGift() {
    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLP();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Add an item");
    ProductDetailsPage productDetailsPage = productListPage.clickOverProductByIndex(1);
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("3. Click on View cart.");
    CartPage cartPage = miniCart.clickViewCartButton();

    logger.info("4. Click on This is a gift > TextBox should display.");
    cartPage = cartPage.clickOnThisIsGift();

    logger.info("5. Type any message");
    String message = "Applause Automation message 1";
    cartPage = cartPage.typePersonalMessage(message);

    logger.info("Verify that message is present inside the field");
    Assert.assertEquals(
        cartPage.getAddPersonalMessageFieldText(), message, "Message isn't correct");
  }
}
