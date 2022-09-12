package com.applause.auto.test.new_web.order.edge_cases;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.Constants.TestData;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.common.data.Constants.WebTestData;
import com.applause.auto.common.data.dto.BillingAddressDto;
import com.applause.auto.common.data.dto.CreditCardDto;
import com.applause.auto.new_web.components.MiniCart;
import com.applause.auto.new_web.components.ShipDateInfoComponent;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.new_web.views.*;
import com.applause.auto.test.new_web.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EdgeCaseTests extends BaseTest {

  @Test(
      groups = {TestNGGroups.TO_BE_RENAMED, TestNGGroups.EDGE_CASES},
      description = "11102585")
  public void userCanReturnToShipping() {
    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDPFromHome(coffeeSelected);
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

    logger.info("6. Click on Return To Shipping");
    shippingPage = paymentsPage.clickOnReturnToShippingButton();

    logger.info("Verify that - User Should go back to Shipping.");
    Assert.assertTrue(shippingPage.isDisplayed(), "Shipping method page didn't appear");
  }

  @Test(
      groups = {TestNGGroups.TO_BE_RENAMED, TestNGGroups.EDGE_CASES},
      description = "11102586")
  public void orderIsNotPlacedWithEmptyData() {
    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDPFromHome(coffeeSelected);
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

    logger.info("6. Click on Pay now without entering any data");
    paymentsPage = paymentsPage.clickOnPayNowButton(PaymentsPage.class);

    logger.info(
        "Verify that - Check your card details and try again message displays and mandatory fields highlights");
    Assert.assertEquals(
        paymentsPage.getErrorMessage(), TestData.PAYMENT_ERROR_MESSAGE, "Error message is wrong");
    Assert.assertEquals(
        paymentsPage.getListOfErrorMessageForMandatoryFields(),
        TestData.PAYMENT_PAGE_VALIDATION_ERRORS,
        "Not all mandatory fields were highlighted");
  }

  @Test(
      groups = {TestNGGroups.TO_BE_RENAMED, TestNGGroups.EDGE_CASES},
      description = "11102587")
  public void orderIsNotPlacedWhenEnteringWrongCCdata() {
    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDPFromHome(coffeeSelected);
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

    logger.info("6. Enter an invalid cc number.");
    CreditCardDto card =
        new CreditCardDto(
            WebTestData.INVALID_CREDIT_CARD,
            WebTestData.CREDIT_CARD_NAME,
            WebTestData.CREDIT_CARD_EXPIRATION_MONTH,
            WebTestData.CREDIT_CARD_EXPIRATION_YEAR,
            WebTestData.CREDIT_CARD_CVV);
    paymentsPage = paymentsPage.setPaymentData(card).clickOnPayNowButton(PaymentsPage.class);

    logger.info("Verify that - Invalid account number message displays.");
    Assert.assertEquals(
        paymentsPage.getErrorMessage(), TestData.PAYMENT_ERROR_MESSAGE, "Error message is wrong");
    Assert.assertEquals(
        paymentsPage.getListOfErrorMessageForMandatoryFields(),
        TestData.INVALID_CC_VALIDATION_ERRORS,
        "Card number error didn't appear");
  }

  @Test(
      groups = {TestNGGroups.TO_BE_RENAMED, TestNGGroups.EDGE_CASES},
      description = "11102588")
  public void useDifferentBillingAddress() {
    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDPFromHome(coffeeSelected);
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

    logger.info("6. Enter payment information.");
    paymentsPage.setPaymentData();

    logger.info("7. Click on Use a different billing address and enter new address");
    BillingAddressDto addressDto =
        BillingAddressDto.builder()
            .firstName("Applause")
            .lastName("Test")
            .streetAddress("144 Water St")
            .city("New York")
            .phoneNumber("646-759-4934")
            .country("United States")
            .state("New York")
            .zipCode("11202")
            .build();
    AcceptancePage acceptancePage =
        paymentsPage.typeBillingAddress(addressDto).clickContinueToPayments();

    logger.info("Verify that - Order should be placed.");
    Assert.assertTrue(acceptancePage.isOrderNumberDisplayed(), "Order number is NOT displayed");
  }

  @Test(
      groups = {Constants.TestNGGroups.TO_BE_RENAMED, Constants.TestNGGroups.EDGE_CASES},
      description = "11102575")
  public void userCantContinueWithoutData() {
    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDPFromHome(coffeeSelected);
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
      groups = {Constants.TestNGGroups.TO_BE_RENAMED, Constants.TestNGGroups.EDGE_CASES},
      description = "11102576")
  public void userCanReturnToCart() {
    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDPFromHome(coffeeSelected);
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
      groups = {Constants.TestNGGroups.TO_BE_RENAMED, Constants.TestNGGroups.EDGE_CASES},
      description = "11102577")
  public void invalidPromoCode() {
    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDPFromHome(coffeeSelected);
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
      groups = {Constants.TestNGGroups.TO_BE_RENAMED, Constants.TestNGGroups.EDGE_CASES},
      description = "11102578")
  public void userInformationIsSavedForNextTime() {
    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDPFromHome(coffeeSelected);
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
      groups = {Constants.TestNGGroups.TO_BE_RENAMED, Constants.TestNGGroups.EDGE_CASES},
      description = "11102579")
  public void userUpdatesAddress() {
    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDPFromHome(coffeeSelected);
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
      groups = {Constants.TestNGGroups.TO_BE_RENAMED, Constants.TestNGGroups.EDGE_CASES},
      description = "11102580")
  public void userIsAbleToChangeContactShipTo() {
    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDPFromHome(coffeeSelected);
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
      groups = {Constants.TestNGGroups.TO_BE_RENAMED, Constants.TestNGGroups.EDGE_CASES},
      description = "11102581")
  public void userCanReturnToInformation() {
    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDPFromHome(coffeeSelected);
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
      groups = {Constants.TestNGGroups.TO_BE_RENAMED, Constants.TestNGGroups.EDGE_CASES},
      description = "11102582")
  public void userCanVisitFAQPageFromShipping() {
    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDPFromHome(coffeeSelected);
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
      groups = {Constants.TestNGGroups.TO_BE_RENAMED, Constants.TestNGGroups.EDGE_CASES},
      description = "11102583")
  public void userCanChangeMethod() {
    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDPFromHome(coffeeSelected);
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
      groups = {Constants.TestNGGroups.TO_BE_RENAMED, Constants.TestNGGroups.EDGE_CASES},
      description = "11102584")
  public void wrongGiftCard() {
    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDPFromHome(coffeeSelected);
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
