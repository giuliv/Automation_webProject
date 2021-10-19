package com.applause.auto.test.new_web;

import com.applause.auto.common.data.Constants;
import com.applause.auto.new_web.components.MiniCart;
import com.applause.auto.new_web.views.CartPage;
import com.applause.auto.new_web.views.CheckOutPage;
import com.applause.auto.new_web.views.ProductDetailsPage;
import com.applause.auto.new_web.views.ProductListPage;
import com.applause.auto.new_web.views.ShippingPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class StandardOrdersTest extends BaseTest {

  @Test(
      groups = {Constants.TestNGGroups.NEW_WEB_CASES, Constants.TestNGGroups.STANDARD},
      description = "11102575")
  public void userCantContinueWithoutData() {
    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLP();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Add an item");
    ProductDetailsPage productDetailsPage = productListPage.clickOverProductByIndex(0);
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("3. Click on Checkout.");
    CheckOutPage checkOutPage = miniCart.clickContinueToCheckOut();

    logger.info("4. Click on Continue to shipping without entering any data");
    checkOutPage = checkOutPage.clickOnContinueButton(CheckOutPage.class);

    logger.info(
        "Verify that - mandatory fields are highlighted and user is not able to continue to shipping.");
    Assert.assertEquals(
        checkOutPage.getListOfErrorMessages(),
        Constants.TestData.CHECKOUT_PAGE_VALIDATION_ERRORS,
        "Not all error messages appear");
  }

  @Test(
      groups = {Constants.TestNGGroups.NEW_WEB_CASES, Constants.TestNGGroups.STANDARD},
      description = "11102576")
  public void userCanReturnToCart() {
    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLP();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Add an item");
    ProductDetailsPage productDetailsPage = productListPage.clickOverProductByIndex(0);
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("3. Click on Checkout.");
    CheckOutPage checkOutPage = miniCart.clickContinueToCheckOut();

    logger.info("4. Click on Return to cart");
    CartPage cartPage = checkOutPage.clickOnReturnToCartButton();

    logger.info("Verify that - User should return to cart page.");
    Assert.assertTrue(cartPage.isDisplayed(), "Cart page didn't appear");
  }

  @Test(
      groups = {Constants.TestNGGroups.NEW_WEB_CASES, Constants.TestNGGroups.STANDARD},
      description = "11102577")
  public void invalidPromoCode() {
    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLP();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Add an item");
    ProductDetailsPage productDetailsPage = productListPage.clickOverProductByIndex(0);
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("3. Click on Checkout.");
    CheckOutPage checkOutPage = miniCart.clickContinueToCheckOut();

    logger.info("4. Type a wrong promo code and apply.");
    checkOutPage = checkOutPage.applyDiscountCode("WrongCode");

    logger.info(
        "Verify that - Enter a valid discount code or gift card message should display and box should be highlight.");
    Assert.assertEquals(
        checkOutPage.getDiscountCodeError(),
        "Enter a valid discount code or gift card",
        "Error message is wrong");
  }

  @Test(
      groups = {Constants.TestNGGroups.NEW_WEB_CASES, Constants.TestNGGroups.STANDARD},
      description = "11102578")
  public void userInformationIsSavedForNextTime() {
    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLP();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Add an item");
    ProductDetailsPage productDetailsPage = productListPage.clickOverProductByIndex(0);
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("3. Click on Checkout.");
    CheckOutPage checkOutPage = miniCart.clickContinueToCheckOut();

    logger.info("4. Enter Contact information and Shipping address.");
    checkOutPage.setCheckOutData();

    logger.info("5. Check Save this information for next time checkbox and click on Continue.");
    ShippingPage shippingPage =
        checkOutPage.clickOnSaveThisInformationForTheNextTime().clickContinueToShipping();

    logger.info("6. Navigate to landing page");
    productListPage = navigateToPLP();

    logger.info("7. Add an item");
    productDetailsPage = productListPage.clickOverProductByIndex(0);
    miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("8. Click on Checkout.");
    shippingPage = miniCart.clickOnContinueToCheckOutButton(ShippingPage.class);

    logger.info("Verify that - user is on the shipping page");
    Assert.assertTrue(shippingPage.isDisplayed(), "User isn't on the shipping page");
  }

  @Test(
      groups = {Constants.TestNGGroups.NEW_WEB_CASES, Constants.TestNGGroups.STANDARD},
      description = "11102579")
  public void userUpdatesAddress() {
    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLP();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Add an item");
    ProductDetailsPage productDetailsPage = productListPage.clickOverProductByIndex(0);
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("3. Click on Checkout.");
    CheckOutPage checkOutPage = miniCart.clickContinueToCheckOut();

    logger.info("4. Enter Contact information and Shipping address.");
    checkOutPage.setCheckOutData();

    logger.info("5. Check Save this information for next time checkbox and click on Continue.");
    ShippingPage shippingPage = checkOutPage.clickContinueToShipping();

    logger.info("6. Click on 'Return to information'");
    checkOutPage = shippingPage.clickOnReturnToInformation();

    logger.info("Verify that - user is on the Information page");
    Assert.assertTrue(checkOutPage.isDisplayed(), "User isn't on the Information page");
  }
}
