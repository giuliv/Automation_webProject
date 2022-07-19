package com.applause.auto.test.new_web.order.edge_cases;

import com.applause.auto.common.data.Constants;
import com.applause.auto.new_web.components.MiniCart;
import com.applause.auto.new_web.components.ShipDateInfoComponent;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.new_web.views.CartPage;
import com.applause.auto.new_web.views.CheckOutPage;
import com.applause.auto.new_web.views.PaymentsPage;
import com.applause.auto.new_web.views.ProductDetailsPage;
import com.applause.auto.new_web.views.ProductListPage;
import com.applause.auto.new_web.views.ShippingPage;
import com.applause.auto.test.new_web.BaseTest;
import com.applause.auto.web.views.FAQPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class StandardOrdersTest extends BaseTest {

  @Test(
      groups = {Constants.TestNGGroups.WEB_REGRESSION, Constants.TestNGGroups.STANDARD},
      description = "11102575")
  public void userCantContinueWithoutData() {
    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDP(coffeeSelected);
    Assert.assertNotNull(productDetailsPage, "Failed to navigate to Product Details Page");

    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("2. Click on Checkout.");
    CheckOutPage checkOutPage = miniCart.clickContinueToCheckOut();

    logger.info("3. Click on Continue to shipping without entering any data");
    checkOutPage = checkOutPage.clickOnContinueButton(CheckOutPage.class);

    logger.info(
        "Verify that - mandatory fields are highlighted and user is not able to continue to shipping.");
    Assert.assertEquals(
        checkOutPage.getListOfErrorMessages(),
        Constants.TestData.CHECKOUT_PAGE_VALIDATION_ERRORS,
        "Not all error messages appear");
  }

  @Test(
      groups = {Constants.TestNGGroups.WEB_REGRESSION, Constants.TestNGGroups.STANDARD},
      description = "11102576")
  public void userCanReturnToCart() {
    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDP(coffeeSelected);
    Assert.assertNotNull(productDetailsPage, "Failed to navigate to Product Details Page");

    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("2. Click on Checkout.");
    CheckOutPage checkOutPage = miniCart.clickContinueToCheckOut();

    logger.info("3. Click on Return to cart");
    CartPage cartPage = checkOutPage.clickOnReturnToCartButton();

    logger.info("Verify that - User should return to cart page.");
    Assert.assertTrue(cartPage.isDisplayed(), "Cart page didn't appear");
  }

  @Test(
      groups = {Constants.TestNGGroups.WEB_REGRESSION, Constants.TestNGGroups.STANDARD},
      description = "11102577")
  public void invalidPromoCode() {
    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDP(coffeeSelected);
    Assert.assertNotNull(productDetailsPage, "Failed to navigate to Product Details Page");

    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("2. Click on Checkout.");
    CheckOutPage checkOutPage = miniCart.clickContinueToCheckOut();

    logger.info("3. Type a wrong promo code and apply.");
    checkOutPage = checkOutPage.applyDiscountCode("WrongCode");

    logger.info(
        "Verify that - Enter a valid discount code or gift card message should display and box should be highlight.");
    Assert.assertEquals(
        checkOutPage.getDiscountCodeError(),
        "Enter a valid discount code or gift card",
        "Error message is wrong");
  }

  @Test(
      groups = {Constants.TestNGGroups.WEB_REGRESSION, Constants.TestNGGroups.STANDARD},
      description = "11102578")
  public void userInformationIsSavedForNextTime() {
    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDP(coffeeSelected);
    Assert.assertNotNull(productDetailsPage, "Failed to navigate to Product Details Page");

    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("2. Click on Checkout.");
    CheckOutPage checkOutPage = miniCart.clickContinueToCheckOut();

    logger.info("3. Enter Contact information and Shipping address.");
    checkOutPage.setCheckOutData();

    logger.info("4. Check Save this information for next time checkbox and click on Continue.");
    checkOutPage.clickOnSaveThisInformationForTheNextTime().clickContinueToShipping();

    logger.info("5. Navigate to PDP > Add an item");
    productDetailsPage = navigateToPDP(coffeeSelected);
    miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("6. Click on Checkout.");
    ShippingPage shippingPage = miniCart.clickOnContinueToCheckOutButton(ShippingPage.class);

    logger.info("Verify that - user is on the shipping page");
    Assert.assertTrue(shippingPage.isDisplayed(), "User isn't on the shipping page");
  }

  @Test(
      groups = {Constants.TestNGGroups.WEB_REGRESSION, Constants.TestNGGroups.STANDARD},
      description = "11102579")
  public void userUpdatesAddress() {
    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDP(coffeeSelected);
    Assert.assertNotNull(productDetailsPage, "Failed to navigate to Product Details Page");

    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("2. Click on Checkout.");
    CheckOutPage checkOutPage = miniCart.clickContinueToCheckOut();

    logger.info("3. Enter Contact information and Shipping address.");
    checkOutPage.setCheckOutData();

    logger.info("4. Check Save this information for next time checkbox and click on Continue.");
    ShippingPage shippingPage = checkOutPage.clickContinueToShipping();

    logger.info("5. Click on 'Return to information'");
    checkOutPage = shippingPage.clickOnReturnToInformation();

    logger.info("Verify that - user is on the Information page");
    Assert.assertTrue(checkOutPage.isDisplayed(), "User isn't on the Information page");
  }

  @Test(
      groups = {Constants.TestNGGroups.WEB_REGRESSION, Constants.TestNGGroups.STANDARD},
      description = "11102580")
  public void userIsAbleToChangeContactShipTo() {
    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDP(coffeeSelected);
    Assert.assertNotNull(productDetailsPage, "Failed to navigate to Product Details Page");

    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("2. Click on Checkout.");
    CheckOutPage checkOutPage = miniCart.clickContinueToCheckOut();

    logger.info("3. Enter Contact information and Shipping address.");
    checkOutPage.setCheckOutData();

    logger.info("4. Click on Continue.");
    ShippingPage shippingPage = checkOutPage.clickContinueToShipping();

    logger.info("5. Click on Change on Contact.");
    checkOutPage = shippingPage.clickOnChangeContactInformation();

    logger.info("Verify that - Should go back to shipping address.");
    Assert.assertTrue(checkOutPage.isDisplayed(), "User isn't on CheckOut page");

    logger.info("6. Change email value");
    String randomEmail = WebHelper.getRandomMail();
    checkOutPage.typeEmail(randomEmail);

    logger.info("7. Click on Continue.");
    shippingPage = checkOutPage.clickContinueToShipping();

    logger.info("Verify that - new email appear");
    Assert.assertEquals(
        shippingPage.getContactEmail(), randomEmail, "Contact email wasn't updated");

    logger.info("8. Click on Change on Ship to.");
    checkOutPage = shippingPage.clickOnChangeShipTo();

    logger.info("Verify that - Should go back to shipping address.");
    Assert.assertTrue(checkOutPage.isDisplayed(), "User isn't on CheckOut page");

    logger.info("9. Change Address value");
    String newStreet = "143 Water St";
    checkOutPage.typeAddress(newStreet);

    logger.info("10. Click on Continue.");
    shippingPage = checkOutPage.clickContinueToShipping();

    logger.info("Verify that - new address appear");
    Assert.assertTrue(
        shippingPage.getShipToAddress().contains(newStreet), "Ship To wasn't updated");
  }

  @Test(
      groups = {Constants.TestNGGroups.WEB_REGRESSION, Constants.TestNGGroups.STANDARD},
      description = "11102581")
  public void userCanReturnToInformation() {
    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDP(coffeeSelected);
    Assert.assertNotNull(productDetailsPage, "Failed to navigate to Product Details Page");

    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("2. Click on Checkout.");
    CheckOutPage checkOutPage = miniCart.clickContinueToCheckOut();

    logger.info("3. Enter Contact information and Shipping address.");
    checkOutPage.setCheckOutData();

    logger.info("4. Click on Continue.");
    ShippingPage shippingPage = checkOutPage.clickContinueToShipping();

    logger.info("5. Click on 'Return to information'");
    checkOutPage = shippingPage.clickOnReturnToInformation();

    logger.info("Verify that - user is on the Information page");
    Assert.assertTrue(checkOutPage.isDisplayed(), "User isn't on the Information page");
  }

  @Test(
      groups = {Constants.TestNGGroups.WEB_REGRESSION, Constants.TestNGGroups.STANDARD},
      description = "11102582")
  public void userCanVisitFAQPageFromShipping() {
    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDP(coffeeSelected);
    Assert.assertNotNull(productDetailsPage, "Failed to navigate to Product Details Page");

    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("2. Click on Checkout.");
    CheckOutPage checkOutPage = miniCart.clickContinueToCheckOut();

    logger.info("3. Enter Contact information and Shipping address.");
    checkOutPage.setCheckOutData();

    logger.info("4. Click on Continue.");
    ShippingPage shippingPage = checkOutPage.clickContinueToShipping();

    logger.info("5. Click on info icon.");
    ShipDateInfoComponent shipDateInfoComponent = shippingPage.clickOnInfoButton();

    logger.info("6. Click on FAQ link from tooltip.");
    FAQPage faqPage = shipDateInfoComponent.clickOnFaqButton();

    logger.info("Verify that - FAQ page should open in a different tab.");
    Assert.assertEquals(faqPage.getTitle(), "When will my order ship?", "FAQ page title is wrong");
  }

  @Test(
      groups = {Constants.TestNGGroups.WEB_REGRESSION, Constants.TestNGGroups.STANDARD},
      description = "11102583")
  public void userCanChangeMethod() {
    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDP(coffeeSelected);
    Assert.assertNotNull(productDetailsPage, "Failed to navigate to Product Details Page");

    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("2. Click on Checkout.");
    CheckOutPage checkOutPage = miniCart.clickContinueToCheckOut();

    logger.info("3. Enter Contact information and Shipping address.");
    checkOutPage.setCheckOutData();

    logger.info("4. Click on Continue.");
    ShippingPage shippingPage = checkOutPage.clickContinueToShipping();

    logger.info("5. Click on Continue to payment.");
    PaymentsPage paymentsPage = shippingPage.clickContinueToPayments();

    logger.info("Verify that - Payment page is displayed");
    Assert.assertTrue(paymentsPage.isDisplayed(), "Payment page isn't displayed");

    logger.info("6. Click on Change on Method");
    shippingPage = paymentsPage.clickOnChangeShippingMethod();

    logger.info("Verify that - User Should go back to Shipping.");
    Assert.assertTrue(shippingPage.isDisplayed(), "Shipping method page didn't appear");

    logger.info("7. Change Shipping Method");
    String selectedMethod = shippingPage.selectShippingMethodByIndex(2).split("\\)")[0];

    logger.info("8. Click on Continue to payment.");
    paymentsPage = shippingPage.clickContinueToPayments();

    logger.info("Verify that - Method should be updated.");
    String currentMethod = paymentsPage.getShippingMethod().split("\\)")[0];
    Assert.assertTrue(
        currentMethod.contains(selectedMethod),
        String.format(
            "Shipping method wasn't updated. Expected [%s] but found - [%s]",
            selectedMethod, currentMethod));
  }

  @Test(
      groups = {Constants.TestNGGroups.WEB_REGRESSION, Constants.TestNGGroups.STANDARD},
      description = "11102584")
  public void wrongGiftCard() {
    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDP(coffeeSelected);
    Assert.assertNotNull(productDetailsPage, "Failed to navigate to Product Details Page");

    logger.info("2. Select One Time Purchase > Add to MiniCart");
    productDetailsPage.selectOneTimePurchase();
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("3. Click on Checkout.");
    CheckOutPage checkOutPage = miniCart.clickContinueToCheckOut();

    logger.info("4. Enter Contact information and Shipping address.");
    checkOutPage.setCheckOutData();

    logger.info("5. Click on Continue.");
    ShippingPage shippingPage = checkOutPage.clickContinueToShipping();

    logger.info("6. Click on Continue to payment.");
    PaymentsPage paymentsPage = shippingPage.clickContinueToPayments();

    logger.info("7. Type wrong gift card");
    paymentsPage.enterGiftCard("Wrong");

    logger.info("Verify that - Apply button should be disabled.");
    Assert.assertFalse(
        paymentsPage.isPeetsCardApplyButtonEnabled(),
        "Peet's Card / Gift Card 'Apply' button isn't disabled");
  }
}
