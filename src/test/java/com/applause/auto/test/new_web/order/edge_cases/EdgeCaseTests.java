package com.applause.auto.test.new_web.order.edge_cases;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.Constants.TestData;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.common.data.Constants.WebTestData;
import com.applause.auto.common.data.dto.BillingAddressDto;
import com.applause.auto.common.data.dto.CreditCardDto;
import com.applause.auto.new_web.components.MiniCart;
import com.applause.auto.new_web.views.AcceptancePage;
import com.applause.auto.new_web.views.CheckOutPage;
import com.applause.auto.new_web.views.PaymentsPage;
import com.applause.auto.new_web.views.ProductDetailsPage;
import com.applause.auto.new_web.views.ProductListPage;
import com.applause.auto.new_web.views.ShippingPage;
import com.applause.auto.test.new_web.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EdgeCaseTests extends BaseTest {

  @Test(
      groups = {Constants.TestNGGroups.NEW_WEB_CASES, TestNGGroups.EDGE_CASES},
      description = "11102585")
  public void userCanReturnToShipping() {
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

    logger.info("5. Click on Continue.");
    ShippingPage shippingPage = checkOutPage.clickContinueToShipping();

    logger.info("6. Click on Continue to payment.");
    PaymentsPage paymentsPage = shippingPage.clickContinueToPayments();

    logger.info("Verify that - Payment page is displayed");
    Assert.assertTrue(paymentsPage.isDisplayed(), "Payment page isn't displayed");

    logger.info("7. Click on Return To Shipping");
    shippingPage = paymentsPage.clickOnReturnToShippingButton();

    logger.info("Verify that - User Should go back to Shipping.");
    Assert.assertTrue(shippingPage.isDisplayed(), "Shipping method page didn't appear");
  }

  @Test(
      groups = {Constants.TestNGGroups.NEW_WEB_CASES, TestNGGroups.EDGE_CASES},
      description = "11102586")
  public void orderIsNotPlacedWithEmptyData() {
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

    logger.info("5. Click on Continue.");
    ShippingPage shippingPage = checkOutPage.clickContinueToShipping();

    logger.info("6. Click on Continue to payment.");
    PaymentsPage paymentsPage = shippingPage.clickContinueToPayments();

    logger.info("Verify that - Payment page is displayed");
    Assert.assertTrue(paymentsPage.isDisplayed(), "Payment page isn't displayed");

    logger.info("7. Click on Pay now without entering any data");
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
      groups = {Constants.TestNGGroups.NEW_WEB_CASES, TestNGGroups.EDGE_CASES},
      description = "11102587")
  public void orderIsNotPlacedWhenEnteringWrongCCdata() {
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

    logger.info("5. Click on Continue.");
    ShippingPage shippingPage = checkOutPage.clickContinueToShipping();

    logger.info("6. Click on Continue to payment.");
    PaymentsPage paymentsPage = shippingPage.clickContinueToPayments();

    logger.info("Verify that - Payment page is displayed");
    Assert.assertTrue(paymentsPage.isDisplayed(), "Payment page isn't displayed");

    logger.info("7. Enter an invalid cc number.");
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
      groups = {Constants.TestNGGroups.NEW_WEB_CASES, TestNGGroups.EDGE_CASES},
      description = "11102588")
  public void useDifferentBillingAddress() {
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

    logger.info("5. Click on Continue.");
    ShippingPage shippingPage = checkOutPage.clickContinueToShipping();

    logger.info("6. Click on Continue to payment.");
    PaymentsPage paymentsPage = shippingPage.clickContinueToPayments();

    logger.info("Verify that - Payment page is displayed");
    Assert.assertTrue(paymentsPage.isDisplayed(), "Payment page isn't displayed");

    logger.info("7. Enter payment information.");
    paymentsPage.setPaymentData();

    logger.info("8. Click on Use a different billing address and enter new address");
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
}
