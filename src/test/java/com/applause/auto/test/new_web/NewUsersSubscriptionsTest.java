package com.applause.auto.test.new_web;

import com.applause.auto.common.data.Constants;
import com.applause.auto.new_web.components.MiniCart;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.new_web.views.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NewUsersSubscriptionsTest extends BaseTest {

  @Test(
      groups = {Constants.TestNGGroups.NEW_WEB_CASES},
      description = "11071740")
  public void subscriptionWithDiscountAndCreditCardAsNewUserTest() {

    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");

    logger.info("2. Create new User account");
    CreateAccountPage createAccountPage =
        homePage.getHeader().clickAccountButton().clickOnCreateAccountButton();
    String mail = WebHelper.getRandomMail();
    MyAccountPage myAccountPage =
        createAccountPage.createAccount(
            Constants.WebTestData.FIRST_NAME,
            Constants.WebTestData.LAST_NAME,
            mail,
            Constants.TestData.WEB_PASSWORD,
            Constants.TestData.WEB_PASSWORD);

    Assert.assertTrue(myAccountPage.getWelcomeMessage().contains("welcome"), "User is not created");

    logger.info("3. Search for Coffee: " + Constants.WebTestData.SEARCH_COFFEE_AGED_SUMATRA);
    SearchResultsPage searchResultsPage =
        homePage
            .getHeader()
            .getSearchComponent()
            .search(Constants.WebTestData.SEARCH_COFFEE_AGED_SUMATRA);

    logger.info("4. Add subscription to MiniCart");
    int productSelected = 0;
    ProductDetailsPage productDetailsPage =
        searchResultsPage.clickOverProductByIndex(productSelected);

    String productName = productDetailsPage.getProductName();
    String grind = productDetailsPage.getGrindSelected();
    int productQuantity = 3;
    int coffeeIndex = 0;
    productDetailsPage.addMoreProducts(productQuantity);
    int expectedQuantity = productDetailsPage.getProductQuantityFromBox();
    Assert.assertEquals(
        productQuantity, expectedQuantity, "Quantity of products was not increased correctly");

    productDetailsPage.selectSubscribeType();
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();
    Assert.assertEquals(
        productName,
        miniCart.getProductNameByIndex(coffeeIndex),
        "Correct Product was not added to MiniCart");
    Assert.assertEquals(
        grind, miniCart.getGrindSelected(), "Correct Grind was not added to MiniCart");
    Assert.assertEquals(
        productQuantity,
        miniCart.getProductQuantityByIndex(coffeeIndex),
        "Correct product quantity was not added to MiniCart");

    logger.info("5. Proceed to Checkout page");
    CheckOutPage checkOutPage = miniCart.clickContinueToCheckOut();
    Assert.assertTrue(
        checkOutPage.isExistingUserMailCorrect().contains(mail),
        "Recently created user mail is NOT correct");
    checkOutPage.setCheckOutDataAsExistingUser();

    logger.info("6. Proceed to Shipping page");
    ShippingPage shippingPage = checkOutPage.clickContinueToShipping();
    String shippingMethod = shippingPage.selectShippingMethodByIndex(0);

    logger.info("7. Proceed to Payments page");
    PaymentsPage paymentsPage = shippingPage.clickContinueToPayments();
    paymentsPage.setPaymentData();
    Assert.assertTrue(
        paymentsPage.isSameAddressSelected(), "Same Address option should be Selected");
    Assert.assertTrue(
        paymentsPage.getDiscountMessage().contains("10% OFF"), "10% OFF message is not displayed");

    String totalPrice = paymentsPage.getTotalPrice();

    logger.info("8. Proceed to Acceptance page");
    AcceptancePage acceptancePage = paymentsPage.clickContinueToPayments();

    logger.info("9. Validating Product Details");
    Assert.assertTrue(acceptancePage.isOrderNumberDisplayed(), "Order number is NOT displayed");
    Assert.assertTrue(acceptancePage.isSubTotalDisplayed(), "SubTotal is NOT displayed");
    Assert.assertEquals(
        productName, acceptancePage.getOrderNameByIndex(0), "Product name does NOT matches");
    Assert.assertEquals(totalPrice, acceptancePage.getTotalPrice(), "Total price does NOT matches");

    Assert.assertEquals(
        acceptancePage.getShippingPrice(), "Free", "Free Shipping for Subscriptions was not set!");
    Assert.assertTrue(
        acceptancePage.getDiscountMessage().contains("10% OFF"),
        "10% OFF message is not displayed");

    // Todo: Optional assertions or assertions missing:
    //  Discount if any
    //  Map Location section

    if (WebHelper.isDesktop()) {
      // Todo: Only for desktop, until figure out if its a bug
      logger.info("10. Validating Download buttons");
      acceptancePage.clickOverTrackPackageButton();
      Assert.assertEquals(
          acceptancePage.getPhoneFromTrackPackageSection(),
          "+1 " + Constants.WebTestData.PHONE,
          "Phone from Track Package section is NOT correct");
    }

    acceptancePage.clickOverShippingUpdatesButton();
    Assert.assertEquals(
        acceptancePage.getPhoneFromShippingUpdatesSection(),
        "+1 " + Constants.WebTestData.PHONE,
        "Phone from Shipping Updates section is NOT correct");

    // Todo: Optional assertions or assertions missing:
    //  Submit button from trackPackage/shippingUpdates sections
    //  [user will receive a text validations]

    logger.info("11. Validating Customer Information");
    Assert.assertEquals(
        mail, acceptancePage.getCustomerMail(), "Recently created user mail does NOT matches");

    Assert.assertTrue(
        acceptancePage.getShippingAddressData().contains(Constants.WebTestData.ADDRESS),
        "Shipping Address does NOT matches");

    Assert.assertTrue(
        shippingMethod.contains(acceptancePage.getShippingMethod()),
        "Shipping Method does NOT matches");

    Assert.assertTrue(
        acceptancePage
            .getPaymentMethod()
            .contains("ending with " + Constants.WebTestData.CREDIT_CARD_NUMBER_4),
        "Payment Method does NOT matches");

    Assert.assertTrue(
        acceptancePage.getBillingAddressData().contains(Constants.WebTestData.ADDRESS),
        "Billing Address does NOT matches");

    Assert.assertTrue(
        acceptancePage.isContinueShoppingDisplayed(),
        "Continue to Shopping button is not displayed");

    logger.info("FINISH");
  }

  @Test(
      groups = {Constants.TestNGGroups.NEW_WEB_CASES},
      description = "11071741")
  public void subscriptionWithNoDiscountAndCreditCardAsNewUserTest() {

    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");

    logger.info("2. Create new User account");
    CreateAccountPage createAccountPage =
        homePage.getHeader().clickAccountButton().clickOnCreateAccountButton();
    String mail = WebHelper.getRandomMail();
    MyAccountPage myAccountPage =
        createAccountPage.createAccount(
            Constants.WebTestData.FIRST_NAME,
            Constants.WebTestData.LAST_NAME,
            mail,
            Constants.TestData.WEB_PASSWORD,
            Constants.TestData.WEB_PASSWORD);

    Assert.assertTrue(myAccountPage.getWelcomeMessage().contains("welcome"), "User is not created");

    logger.info("3. Search for Coffee: " + Constants.WebTestData.SEARCH_COFFEE_DECAF_MAJOR);
    SearchResultsPage searchResultsPage =
        homePage
            .getHeader()
            .getSearchComponent()
            .search(Constants.WebTestData.SEARCH_COFFEE_DECAF_MAJOR);

    logger.info("4. Add subscription to MiniCart");
    int productSelected = 0;
    ProductDetailsPage productDetailsPage =
        searchResultsPage.clickOverProductByIndex(productSelected);

    String productName = productDetailsPage.getProductName();
    String grind = productDetailsPage.getGrindSelected();
    int productQuantity = productDetailsPage.getProductQuantitySelected();
    int coffeeIndex = 0;
    productDetailsPage.addMoreProducts(productQuantity);

    productDetailsPage.selectSubscribeType();
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();
    Assert.assertEquals(
        productName,
        miniCart.getProductNameByIndex(coffeeIndex),
        "Correct Product was not added to MiniCart");
    Assert.assertEquals(
        grind, miniCart.getGrindSelected(), "Correct Grind was not added to MiniCart");
    Assert.assertEquals(
        productQuantity,
        miniCart.getProductQuantityByIndex(coffeeIndex),
        "Correct product quantity was not added to MiniCart");

    logger.info("5. Proceed to Checkout page");
    CheckOutPage checkOutPage = miniCart.clickContinueToCheckOut();
    Assert.assertTrue(
        checkOutPage.isExistingUserMailCorrect().contains(mail),
        "Recently created user mail is NOT correct");
    checkOutPage.setCheckOutDataAsExistingUser();

    logger.info("6. Proceed to Shipping page");
    ShippingPage shippingPage = checkOutPage.clickContinueToShipping();
    String shippingMethod = shippingPage.selectShippingMethodByIndex(0);

    logger.info("7. Proceed to Payments page");
    PaymentsPage paymentsPage = shippingPage.clickContinueToPayments();
    paymentsPage.setPaymentData();
    Assert.assertTrue(
        paymentsPage.isSameAddressSelected(), "Same Address option should be Selected");
    Assert.assertFalse(
        paymentsPage.isDiscountPresent(), "[Payments Page] - Discounts should NOT be available");

    String totalPrice = paymentsPage.getTotalPrice();

    logger.info("8. Proceed to Acceptance page");
    AcceptancePage acceptancePage = paymentsPage.clickContinueToPayments();

    logger.info("9. Validating Product Details");
    Assert.assertTrue(acceptancePage.isOrderNumberDisplayed(), "Order number is NOT displayed");
    Assert.assertTrue(acceptancePage.isSubTotalDisplayed(), "SubTotal is NOT displayed");
    Assert.assertEquals(
        productName, acceptancePage.getOrderNameByIndex(0), "Product name does NOT matches");
    Assert.assertEquals(totalPrice, acceptancePage.getTotalPrice(), "Total price does NOT matches");

    Assert.assertEquals(
        acceptancePage.getShippingPrice(), "Free", "Free Shipping for Subscriptions was not set!");
    Assert.assertFalse(
        acceptancePage.isDiscountPresent(),
        "[Acceptance Page] - Discounts should NOT be available");

    // Todo: Optional assertions or assertions missing:
    //  Discount if any
    //  Map Location section

    if (WebHelper.isDesktop()) {
      // Todo: Only for desktop, until figure out if its a bug
      logger.info("10. Validating Download buttons");
      acceptancePage.clickOverTrackPackageButton();
      Assert.assertEquals(
          acceptancePage.getPhoneFromTrackPackageSection(),
          "+1 " + Constants.WebTestData.PHONE,
          "Phone from Track Package section is NOT correct");
    }

    acceptancePage.clickOverShippingUpdatesButton();
    Assert.assertEquals(
        acceptancePage.getPhoneFromShippingUpdatesSection(),
        "+1 " + Constants.WebTestData.PHONE,
        "Phone from Shipping Updates section is NOT correct");

    // Todo: Optional assertions or assertions missing:
    //  Submit button from trackPackage/shippingUpdates sections
    //  [user will receive a text validations]

    logger.info("11. Validating Customer Information");
    Assert.assertEquals(
        mail, acceptancePage.getCustomerMail(), "Recently created user mail does NOT matches");

    Assert.assertTrue(
        acceptancePage.getShippingAddressData().contains(Constants.WebTestData.ADDRESS),
        "Shipping Address does NOT matches");

    Assert.assertTrue(
        shippingMethod.contains(acceptancePage.getShippingMethod()),
        "Shipping Method does NOT matches");

    Assert.assertTrue(
        acceptancePage
            .getPaymentMethod()
            .contains("ending with " + Constants.WebTestData.CREDIT_CARD_NUMBER_4),
        "Payment Method does NOT matches");

    Assert.assertTrue(
        acceptancePage.getBillingAddressData().contains(Constants.WebTestData.ADDRESS),
        "Billing Address does NOT matches");

    Assert.assertTrue(
        acceptancePage.isContinueShoppingDisplayed(),
        "Continue to Shopping button is not displayed");

    logger.info("FINISH");
  }
}
