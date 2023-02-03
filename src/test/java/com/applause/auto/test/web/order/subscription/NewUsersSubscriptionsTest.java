package com.applause.auto.test.web.order.subscription;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.test.web.BaseTest;
import com.applause.auto.web.components.MiniCart;
import com.applause.auto.web.helpers.WebHelper;
import com.applause.auto.web.views.AcceptancePage;
import com.applause.auto.web.views.CheckOutPage;
import com.applause.auto.web.views.HomePage;
import com.applause.auto.web.views.PaymentsPage;
import com.applause.auto.web.views.ProductDetailsPage;
import com.applause.auto.web.views.SearchResultsPage;
import com.applause.auto.web.views.ShippingPage;
import com.applause.auto.web.views.my_account.MyAccountPage;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NewUsersSubscriptionsTest extends BaseTest {
  // Todo: Optional assertions not added:
  //  Discount if any

  @Test(
      groups = {Constants.TestNGGroups.TO_BE_RENAMED, Constants.TestNGGroups.SANITY},
      description = "11071740")
  public void subscriptionDiscountAt10AsNewUserTest() {

    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");
    homePage.closeInitialBannersAndModals();

    logger.info("2. Create new User account");
    String mail = WebHelper.getRandomMail();
    MyAccountPage myAccountPage = testHelper.createAccount(homePage, mail);
    Assert.assertNotNull(myAccountPage, "Account was not created!");

    logger.info("3. Search for Coffee: " + Constants.WebTestData.SEARCH_COFFEE_AGED_SUMATRA);
    SearchResultsPage searchResultsPage =
        homePage
            .getHeader()
            .getSearchComponent()
            .search(Constants.WebTestData.SEARCH_COFFEE_AGED_SUMATRA);

    logger.info("4. Add subscription to MiniCart");
    int productSelected = 1;
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
        grind, miniCart.getGrindByIndex(coffeeIndex), "Correct Grind was not added to MiniCart");
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

    if (WebHelper.getTestEnvironment().equalsIgnoreCase("production")) {
      logger.info("Testcase needs to stop, running on prod");
    } else {
      String totalPrice = paymentsPage.getTotalPrice();

      logger.info("8. Proceed to Acceptance page");
      AcceptancePage acceptancePage = paymentsPage.clickPayNow();

      logger.info("9. Validating Product Details");
      Assert.assertTrue(acceptancePage.isOrderNumberDisplayed(), "Order number is NOT displayed");
      Assert.assertTrue(acceptancePage.isSubTotalDisplayed(), "SubTotal is NOT displayed");
      Assert.assertEquals(
          productName, acceptancePage.getOrderNameByIndex(0), "Product name does NOT matches");
      Assert.assertEquals(
          totalPrice, acceptancePage.getTotalPrice(), "Total price does NOT matches");

      Assert.assertEquals(
          acceptancePage.getShippingPrice(),
          "Free",
          "Free Shipping for Subscriptions was not set!");
      Assert.assertTrue(
          acceptancePage.getDiscountMessage().contains("10% OFF"),
          "10% OFF message is not displayed");
      Assert.assertTrue(acceptancePage.isMapDisplayed(), "Map is not displayed");

      if (WebHelper.isDesktop()) {
        // Todo: Only for desktop, until figure out if its a bug
        logger.info("10. Validating Download buttons");
        acceptancePage.clickOverTrackPackageButton();
        Assert.assertEquals(
            acceptancePage.getPhoneFromTrackPackageSection(),
            "+1 " + Constants.WebTestData.PHONE,
            "Phone from Track Package section is NOT correct");
      }

      // Todo: Commented on 15.07.2022 due to Peet's change on staging[Talked w/Shilpa and
      // Bernadette]
      //    acceptancePage.clickOverShippingUpdatesButton();
      //    Assert.assertEquals(
      //        acceptancePage.getPhoneFromShippingUpdatesSection(),
      //        "+1 " + Constants.WebTestData.PHONE,
      //        "Phone from Shipping Updates section is NOT correct");

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

  @Test(
      groups = {
        Constants.TestNGGroups.FRONT_END_REGRESSION,
        Constants.TestNGGroups.CHECKOUT,
        TestNGGroups.FE_PROD_REGRESSION
      },
      description = "11071741")
  public void subscriptionWithNoDiscountAndCreditCardAsNewUserTest() {

    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");
    homePage.closeInitialBannersAndModals();

    logger.info("2. Create new User account");
    String mail = WebHelper.getRandomMail();
    MyAccountPage myAccountPage = testHelper.createAccount(homePage, mail);
    Assert.assertNotNull(myAccountPage, "Account was not created!");

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

    productDetailsPage.selectSubscribeType();
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();
    Assert.assertEquals(
        productName,
        miniCart.getProductNameByIndex(coffeeIndex),
        "Correct Product was not added to MiniCart");
    Assert.assertEquals(
        grind, miniCart.getGrindByIndex(coffeeIndex), "Correct Grind was not added to MiniCart");
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

    if (WebHelper.getTestEnvironment().equalsIgnoreCase("production")) {
      logger.info("Testcase needs to stop, running on prod");
    } else {
      String totalPrice = paymentsPage.getTotalPrice();

      logger.info("8. Proceed to Acceptance page");
      AcceptancePage acceptancePage = paymentsPage.clickPayNow();

      logger.info("9. Validating Product Details");
      Assert.assertTrue(acceptancePage.isOrderNumberDisplayed(), "Order number is NOT displayed");
      Assert.assertTrue(acceptancePage.isSubTotalDisplayed(), "SubTotal is NOT displayed");
      Assert.assertEquals(
          productName, acceptancePage.getOrderNameByIndex(0), "Product name does NOT matches");
      Assert.assertEquals(
          totalPrice, acceptancePage.getTotalPrice(), "Total price does NOT matches");

      Assert.assertEquals(
          acceptancePage.getShippingPrice(),
          "Free",
          "Free Shipping for Subscriptions was not set!");
      Assert.assertFalse(
          acceptancePage.isDiscountPresent(),
          "[Acceptance Page] - Discounts should NOT be available");
      Assert.assertTrue(acceptancePage.isMapDisplayed(), "Map is not displayed");

      if (WebHelper.isDesktop()) {
        // Todo: Only for desktop, until figure out if its a bug
        logger.info("10. Validating Download buttons");
        acceptancePage.clickOverTrackPackageButton();
        Assert.assertEquals(
            acceptancePage.getPhoneFromTrackPackageSection(),
            "+1 " + Constants.WebTestData.PHONE,
            "Phone from Track Package section is NOT correct");
      }

      // Todo: Commented on 15.07.2022 due to Peet's change on staging[Talked w/Shilpa and
      // Bernadette]
      //    acceptancePage.clickOverShippingUpdatesButton();
      //    Assert.assertEquals(
      //        acceptancePage.getPhoneFromShippingUpdatesSection(),
      //        "+1 " + Constants.WebTestData.PHONE,
      //        "Phone from Shipping Updates section is NOT correct");

      // Todo: Optional assertions or assertions missing:
      //  Submit button from trackPackage/shippingUpdates sections
      //  [user will receive a text validations]

      logger.info("11. Validating Customer Information");
      Assert.assertEquals(
          mail, acceptancePage.getCustomerMail(), "Recently created user mail does NOT matches");

      Assert.assertTrue(
          StringUtils.containsIgnoreCase(
              acceptancePage.getShippingAddressData(), Constants.WebTestData.ADDRESS),
          "Shipping Address does NOT matches");

      Assert.assertTrue(
          StringUtils.containsIgnoreCase(shippingMethod, acceptancePage.getShippingMethod()),
          "Shipping Method does NOT matches");

      Assert.assertTrue(
          acceptancePage
              .getPaymentMethod()
              .contains("ending with " + Constants.WebTestData.CREDIT_CARD_NUMBER_4),
          "Payment Method does NOT matches");

      Assert.assertTrue(
          StringUtils.containsIgnoreCase(
              acceptancePage.getBillingAddressData(), Constants.WebTestData.ADDRESS),
          "Billing Address does NOT matches");

      Assert.assertTrue(
          acceptancePage.isContinueShoppingDisplayed(),
          "Continue to Shopping button is not displayed");

      logger.info("FINISH");
    }
  }

  @Test(
      groups = {Constants.TestNGGroups.TO_BE_RENAMED},
      description = "11071742")
  public void multipleSubscriptionDiscountAt10AsNewUserTest() {

    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");
    homePage.closeInitialBannersAndModals();

    logger.info("2. Create new User account");
    String mail = WebHelper.getRandomMail();
    MyAccountPage myAccountPage = testHelper.createAccount(homePage, mail);
    Assert.assertNotNull(myAccountPage, "Account was not created!");

    logger.info(
        "3. Search for 1st Type of Coffee: " + Constants.WebTestData.SEARCH_COFFEE_DECAF_MAJOR);
    SearchResultsPage searchResultsPage =
        homePage
            .getHeader()
            .getSearchComponent()
            .search(Constants.WebTestData.SEARCH_COFFEE_DECAF_MAJOR);

    logger.info("4. Add 1st subscription to MiniCart");
    int firstProductSelected = 0;
    ProductDetailsPage productDetailsPage =
        searchResultsPage.clickOverProductByIndex(firstProductSelected);

    String firstProductName = productDetailsPage.getProductName();
    String firstGrind = productDetailsPage.getGrindSelected();
    int firstProductQuantity = 2;
    int firstCoffeeIndex = 1;
    productDetailsPage.addMoreProducts(firstProductQuantity);
    int expectedQuantity = productDetailsPage.getProductQuantitySelected();
    Assert.assertEquals(
        firstProductQuantity, expectedQuantity, "Quantity of products was not increased correctly");

    productDetailsPage.selectSubscribeType();
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();
    productDetailsPage = miniCart.closeMiniCart(ProductDetailsPage.class);

    logger.info(
        "5. Search for 2nd Type of Coffee: " + Constants.WebTestData.SEARCH_COFFEE_AGED_SUMATRA);
    searchResultsPage =
        productDetailsPage
            .getHeader()
            .getSearchComponent()
            .search(Constants.WebTestData.SEARCH_COFFEE_AGED_SUMATRA);

    logger.info("6. Add 2nd subscription to MiniCart");
    int secondProductSelected = 1;
    productDetailsPage = searchResultsPage.clickOverProductByIndex(secondProductSelected);

    String secondProductName = productDetailsPage.getProductName();
    String secondGrind = productDetailsPage.getGrindSelected();
    int secondProductQuantity = productDetailsPage.getProductQuantitySelected();
    int secondCoffeeIndex = 0;

    productDetailsPage.selectSubscribeType();
    miniCart = productDetailsPage.clickAddToMiniCart();
    Assert.assertEquals(
        firstProductName,
        miniCart.getProductNameByIndex(firstCoffeeIndex),
        "Correct Product[1st] was not added to MiniCart");
    Assert.assertEquals(
        firstGrind,
        miniCart.getGrindByIndex(firstCoffeeIndex),
        "Correct Grind[1st] was not added to MiniCart");
    Assert.assertEquals(
        firstProductQuantity,
        miniCart.getProductQuantityByIndex(firstCoffeeIndex),
        "Correct product quantity[1st] was not added to MiniCart");

    Assert.assertEquals(
        secondProductName,
        miniCart.getProductNameByIndex(secondCoffeeIndex),
        "Correct Product[2nd] was not added to MiniCart");
    Assert.assertEquals(
        secondGrind,
        miniCart.getGrindByIndex(secondCoffeeIndex),
        "Correct Grind[2nd] was not added to MiniCart");
    Assert.assertEquals(
        secondProductQuantity,
        miniCart.getProductQuantityByIndex(secondCoffeeIndex),
        "Correct product quantity[2nd] was not added to MiniCart");

    logger.info("7. Proceed to Checkout page");
    CheckOutPage checkOutPage = miniCart.clickContinueToCheckOut();
    Assert.assertTrue(
        checkOutPage.isExistingUserMailCorrect().contains(mail),
        "Recently created user mail is NOT correct");
    checkOutPage.setCheckOutDataAsExistingUser();

    logger.info("8. Proceed to Shipping page");
    ShippingPage shippingPage = checkOutPage.clickContinueToShipping();
    String shippingMethod = shippingPage.selectShippingMethodByIndex(0);

    logger.info("9. Proceed to Payments page");
    PaymentsPage paymentsPage = shippingPage.clickContinueToPayments();
    paymentsPage.setPaymentData();
    Assert.assertTrue(
        paymentsPage.isSameAddressSelected(), "Same Address option should be Selected");
    Assert.assertTrue(
        paymentsPage.getDiscountMessageByIndex(firstCoffeeIndex).contains("10% OFF"),
        "10% OFF message [1st] is not displayed");
    Assert.assertTrue(
        paymentsPage.getDiscountMessageByIndex(secondCoffeeIndex).contains("10% OFF"),
        "10% OFF message[2nd] is not displayed");

    if (WebHelper.getTestEnvironment().equalsIgnoreCase("production")) {
      logger.info("Testcase needs to stop, running on prod");
    } else {
      String totalPrice = paymentsPage.getTotalPrice();

      logger.info("10. Proceed to Acceptance page");
      AcceptancePage acceptancePage = paymentsPage.clickPayNow();

      logger.info("11. Validating Product Details");
      Assert.assertTrue(acceptancePage.isOrderNumberDisplayed(), "Order number is NOT displayed");
      Assert.assertTrue(acceptancePage.isSubTotalDisplayed(), "SubTotal is NOT displayed");
      Assert.assertEquals(
          firstProductName,
          acceptancePage.getOrderNameByIndex(firstCoffeeIndex),
          "Product name[1st] does NOT matches");

      Assert.assertEquals(
          secondProductName,
          acceptancePage.getOrderNameByIndex(secondCoffeeIndex),
          "Product name[2nd] does NOT matches");
      Assert.assertEquals(
          totalPrice, acceptancePage.getTotalPrice(), "Total price does NOT matches");

      Assert.assertEquals(
          acceptancePage.getShippingPrice(),
          "Free",
          "Free Shipping for Subscriptions was not set!");
      Assert.assertTrue(
          acceptancePage.getDiscountMessageByIndex(firstCoffeeIndex).contains("10% OFF"),
          "10% OFF message[1st] is not displayed");

      Assert.assertTrue(
          acceptancePage.getDiscountMessageByIndex(secondCoffeeIndex).contains("10% OFF"),
          "10% OFF message[2nd] is not displayed");
      Assert.assertTrue(acceptancePage.isMapDisplayed(), "Map is not displayed");

      if (WebHelper.isDesktop()) {
        // Todo: Only for desktop, until figure out if its a bug
        logger.info("12. Validating Download buttons");
        acceptancePage.clickOverTrackPackageButton();
        Assert.assertEquals(
            acceptancePage.getPhoneFromTrackPackageSection(),
            "+1 " + Constants.WebTestData.PHONE,
            "Phone from Track Package section is NOT correct");
      }

      // Todo: Commented on 15.07.2022 due to Peet's change on staging[Talked w/Shilpa and
      // Bernadette]
      //    acceptancePage.clickOverShippingUpdatesButton();
      //    Assert.assertEquals(
      //        acceptancePage.getPhoneFromShippingUpdatesSection(),
      //        "+1 " + Constants.WebTestData.PHONE,
      //        "Phone from Shipping Updates section is NOT correct");

      // Todo: Optional assertions or assertions missing:
      //  Submit button from trackPackage/shippingUpdates sections
      //  [user will receive a text validations]

      logger.info("13. Validating Customer Information");
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

  @Test(
      groups = {Constants.TestNGGroups.TO_BE_RENAMED},
      description = "11071743")
  public void multipleItemsSubscriptionDiscountAt5AsNewUserTest() {

    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");
    homePage.closeInitialBannersAndModals();

    logger.info("2. Create new User account");
    String mail = WebHelper.getRandomMail();
    MyAccountPage myAccountPage = testHelper.createAccount(homePage, mail);
    Assert.assertNotNull(myAccountPage, "Account was not created!");

    logger.info(
        "3. Search for 1st Type of Coffee: " + Constants.WebTestData.SEARCH_COFFEE_DECAF_MAJOR);
    SearchResultsPage searchResultsPage =
        homePage
            .getHeader()
            .getSearchComponent()
            .search(Constants.WebTestData.SEARCH_COFFEE_DECAF_MAJOR);

    logger.info("4. Add 1st subscription to MiniCart");
    int firstProductSelected = 0;
    ProductDetailsPage productDetailsPage =
        searchResultsPage.clickOverProductByIndex(firstProductSelected);

    String firstProductName = productDetailsPage.getProductName();
    String firstGrind = productDetailsPage.getGrindSelected();
    int firstProductQuantity = 2;
    int firstCoffeeIndex = 1;
    productDetailsPage.addMoreProducts(firstProductQuantity);
    int expectedQuantity = productDetailsPage.getProductQuantitySelected();
    Assert.assertEquals(
        firstProductQuantity, expectedQuantity, "Quantity of products was not increased correctly");

    productDetailsPage.selectSubscribeType();
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();
    productDetailsPage = miniCart.closeMiniCart(ProductDetailsPage.class);

    logger.info(
        "5. Search for 2nd Type of Coffee: " + Constants.WebTestData.SEARCH_COFFEE_AGED_SUMATRA);
    searchResultsPage =
        productDetailsPage
            .getHeader()
            .getSearchComponent()
            .search(Constants.WebTestData.SEARCH_COFFEE_AGED_SUMATRA);

    logger.info("6. Add 2nd subscription to MiniCart");
    int secondProductSelected = 1;
    productDetailsPage = searchResultsPage.clickOverProductByIndex(secondProductSelected);

    String secondProductName = productDetailsPage.getProductName();
    String secondGrind = productDetailsPage.getGrindSelected();
    int secondProductQuantity = productDetailsPage.getProductQuantitySelected();
    int secondCoffeeIndex = 0;

    miniCart = productDetailsPage.clickAddToMiniCart();
    Assert.assertEquals(
        firstProductName,
        miniCart.getProductNameByIndex(firstCoffeeIndex),
        "Correct Product[1st] was not added to MiniCart");
    Assert.assertEquals(
        firstGrind,
        miniCart.getGrindByIndex(firstCoffeeIndex),
        "Correct Grind[1st] was not added to MiniCart");
    Assert.assertEquals(
        firstProductQuantity,
        miniCart.getProductQuantityByIndex(firstCoffeeIndex),
        "Correct product quantity[1st] was not added to MiniCart");

    Assert.assertEquals(
        secondProductName,
        miniCart.getProductNameByIndex(secondCoffeeIndex),
        "Correct Product[2nd] was not added to MiniCart");
    Assert.assertEquals(
        secondGrind,
        miniCart.getGrindByIndex(secondCoffeeIndex),
        "Correct Grind[2nd] was not added to MiniCart");
    Assert.assertEquals(
        secondProductQuantity,
        miniCart.getProductQuantityByIndex(secondCoffeeIndex),
        "Correct product quantity[2nd] was not added to MiniCart");

    logger.info("7. Proceed to Checkout page");
    CheckOutPage checkOutPage = miniCart.clickContinueToCheckOut();
    Assert.assertTrue(
        checkOutPage.isExistingUserMailCorrect().contains(mail),
        "Recently created user mail is NOT correct");
    checkOutPage.setCheckOutDataAsExistingUser();

    logger.info("8. Proceed to Shipping page");
    ShippingPage shippingPage = checkOutPage.clickContinueToShipping();
    String shippingMethod = shippingPage.selectShippingMethodByIndex(0);

    logger.info("9. Proceed to Payments page");
    PaymentsPage paymentsPage = shippingPage.clickContinueToPayments();
    paymentsPage.setPaymentData();
    Assert.assertTrue(
        paymentsPage.isSameAddressSelected(), "Same Address option should be Selected");

    Assert.assertTrue(
        paymentsPage.getDiscountMessageByIndex(secondCoffeeIndex).contains("10% OFF"),
        "10% OFF message[2nd] is not displayed");

    if (WebHelper.getTestEnvironment().equalsIgnoreCase("production")) {
      logger.info("Testcase needs to stop, running on prod");
    } else {
      String totalPrice = paymentsPage.getTotalPrice();

      logger.info("10. Proceed to Acceptance page");
      AcceptancePage acceptancePage = paymentsPage.clickPayNow();

      logger.info("11. Validating Product Details");
      Assert.assertTrue(acceptancePage.isOrderNumberDisplayed(), "Order number is NOT displayed");
      Assert.assertTrue(acceptancePage.isSubTotalDisplayed(), "SubTotal is NOT displayed");
      Assert.assertEquals(
          firstProductName,
          acceptancePage.getOrderNameByIndex(firstCoffeeIndex),
          "Product name[1st] does NOT matches");

      Assert.assertEquals(
          secondProductName,
          acceptancePage.getOrderNameByIndex(secondCoffeeIndex),
          "Product name[2nd] does NOT matches");
      Assert.assertEquals(
          totalPrice, acceptancePage.getTotalPrice(), "Total price does NOT matches");

      Assert.assertEquals(
          acceptancePage.getShippingPrice(),
          "Free",
          "Free Shipping for Subscriptions was not set!");

      Assert.assertTrue(
          acceptancePage.getDiscountMessageByIndex(secondCoffeeIndex).contains("10% OFF"),
          "10% OFF message[2nd] is not displayed");
      Assert.assertTrue(acceptancePage.isMapDisplayed(), "Map is not displayed");

      if (WebHelper.isDesktop()) {
        // Todo: Only for desktop, until figure out if its a bug
        logger.info("12. Validating Download buttons");
        acceptancePage.clickOverTrackPackageButton();
        Assert.assertEquals(
            acceptancePage.getPhoneFromTrackPackageSection(),
            "+1 " + Constants.WebTestData.PHONE,
            "Phone from Track Package section is NOT correct");
      }

      // Todo: Commented on 15.07.2022 due to Peet's change on staging[Talked w/Shilpa and
      // Bernadette]
      //    acceptancePage.clickOverShippingUpdatesButton();
      //    Assert.assertEquals(
      //        acceptancePage.getPhoneFromShippingUpdatesSection(),
      //        "+1 " + Constants.WebTestData.PHONE,
      //        "Phone from Shipping Updates section is NOT correct");

      // Todo: Optional assertions or assertions missing:
      //  Submit button from trackPackage/shippingUpdates sections
      //  [user will receive a text validations]

      logger.info("13. Validating Customer Information");
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

  @Test(
      groups = {Constants.TestNGGroups.TO_BE_RENAMED},
      description = "11071744")
  public void subscriptionAndPurchaseDiscountAt10AsNewUserTest() {

    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");
    homePage.closeInitialBannersAndModals();

    logger.info("2. Create new User account");
    String mail = WebHelper.getRandomMail();
    MyAccountPage myAccountPage = testHelper.createAccount(homePage, mail);
    Assert.assertNotNull(myAccountPage, "Account was not created!");

    logger.info(
        "3. Search for 1st Type of Coffee: " + Constants.WebTestData.SEARCH_COFFEE_DECAF_MAJOR);
    SearchResultsPage searchResultsPage =
        homePage
            .getHeader()
            .getSearchComponent()
            .search(Constants.WebTestData.SEARCH_COFFEE_DECAF_MAJOR);

    logger.info("4. Add 1st subscription to MiniCart");
    int firstProductSelected = 0;
    ProductDetailsPage productDetailsPage =
        searchResultsPage.clickOverProductByIndex(firstProductSelected);

    String firstProductName = productDetailsPage.getProductName();
    String firstGrind = productDetailsPage.getGrindSelected();
    int firstProductQuantity = 3;
    int firstCoffeeIndex = 1;
    productDetailsPage.addMoreProducts(firstProductQuantity);
    int expectedQuantity = productDetailsPage.getProductQuantityFromBox();
    Assert.assertEquals(
        firstProductQuantity, expectedQuantity, "Quantity of products was not increased correctly");

    productDetailsPage.selectSubscribeType();
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();
    productDetailsPage = miniCart.closeMiniCart(ProductDetailsPage.class);

    logger.info(
        "5. Search for 2nd Type of Coffee: " + Constants.WebTestData.SEARCH_COFFEE_AGED_SUMATRA);
    searchResultsPage =
        productDetailsPage
            .getHeader()
            .getSearchComponent()
            .search(Constants.WebTestData.SEARCH_COFFEE_AGED_SUMATRA);

    logger.info("6. Add 2nd item [Purchase Type] to MiniCart");
    int secondProductSelected = 1;
    productDetailsPage = searchResultsPage.clickOverProductByIndex(secondProductSelected);

    String secondProductName = productDetailsPage.getProductName();
    String secondGrind = productDetailsPage.getGrindSelected();
    int secondProductQuantity = productDetailsPage.getProductQuantitySelected();
    int secondCoffeeIndex = 0;

    miniCart = productDetailsPage.clickAddToMiniCart();
    Assert.assertEquals(
        firstProductName,
        miniCart.getProductNameByIndex(firstCoffeeIndex),
        "Correct Product[1st] was not added to MiniCart");
    Assert.assertEquals(
        firstGrind,
        miniCart.getGrindByIndex(firstCoffeeIndex),
        "Correct Grind[1st] was not added to MiniCart");
    Assert.assertEquals(
        firstProductQuantity,
        miniCart.getProductQuantityByIndex(firstCoffeeIndex),
        "Correct product quantity[1st] was not added to MiniCart");

    Assert.assertEquals(
        secondProductName,
        miniCart.getProductNameByIndex(secondCoffeeIndex),
        "Correct Product[2nd] was not added to MiniCart");
    Assert.assertEquals(
        secondGrind,
        miniCart.getGrindByIndex(secondCoffeeIndex),
        "Correct Grind[2nd] was not added to MiniCart");
    Assert.assertEquals(
        secondProductQuantity,
        miniCart.getProductQuantityByIndex(secondCoffeeIndex),
        "Correct product quantity[2nd] was not added to MiniCart");

    logger.info("7. Proceed to Checkout page");
    CheckOutPage checkOutPage = miniCart.clickContinueToCheckOut();
    Assert.assertTrue(
        checkOutPage.isExistingUserMailCorrect().contains(mail),
        "Recently created user mail is NOT correct");
    checkOutPage.setCheckOutDataAsExistingUser();

    logger.info("8. Proceed to Shipping page");
    ShippingPage shippingPage = checkOutPage.clickContinueToShipping();
    String shippingMethod = shippingPage.selectShippingMethodByIndex(0);

    logger.info("9. Proceed to Payments page");
    PaymentsPage paymentsPage = shippingPage.clickContinueToPayments();
    paymentsPage.setPaymentData();
    Assert.assertTrue(
        paymentsPage.isSameAddressSelected(), "Same Address option should be Selected");

    Assert.assertTrue(
        paymentsPage.getDiscountMessageByIndex(secondCoffeeIndex).contains("10% OFF"),
        "10% OFF message[2nd] is not displayed");

    if (WebHelper.getTestEnvironment().equalsIgnoreCase("production")) {
      logger.info("Testcase needs to stop, running on prod");
    } else {
      String totalPrice = paymentsPage.getTotalPrice();

      logger.info("10. Proceed to Acceptance page");
      AcceptancePage acceptancePage = paymentsPage.clickPayNow();

      logger.info("11. Validating Product Details");
      Assert.assertTrue(acceptancePage.isOrderNumberDisplayed(), "Order number is NOT displayed");
      Assert.assertTrue(acceptancePage.isSubTotalDisplayed(), "SubTotal is NOT displayed");
      Assert.assertEquals(
          firstProductName,
          acceptancePage.getOrderNameByIndex(firstCoffeeIndex),
          "Product name[1st] does NOT matches");

      Assert.assertEquals(
          secondProductName,
          acceptancePage.getOrderNameByIndex(secondCoffeeIndex),
          "Product name[2nd] does NOT matches");
      Assert.assertEquals(
          totalPrice, acceptancePage.getTotalPrice(), "Total price does NOT matches");

      Assert.assertEquals(
          acceptancePage.getShippingPrice(),
          "Free",
          "Free Shipping for Subscriptions was not set!");

      Assert.assertTrue(
          acceptancePage.getDiscountMessageByIndex(secondCoffeeIndex).contains("10% OFF"),
          "10% OFF message[2nd] is not displayed");
      Assert.assertTrue(acceptancePage.isMapDisplayed(), "Map is not displayed");

      if (WebHelper.isDesktop()) {
        // Todo: Only for desktop, until figure out if its a bug
        logger.info("12. Validating Download buttons");
        acceptancePage.clickOverTrackPackageButton();
        Assert.assertEquals(
            acceptancePage.getPhoneFromTrackPackageSection(),
            "+1 " + Constants.WebTestData.PHONE,
            "Phone from Track Package section is NOT correct");
      }

      // Todo: Commented on 15.07.2022 due to Peet's change on staging[Talked w/Shilpa and
      // Bernadette]
      //    acceptancePage.clickOverShippingUpdatesButton();
      //    Assert.assertEquals(
      //        acceptancePage.getPhoneFromShippingUpdatesSection(),
      //        "+1 " + Constants.WebTestData.PHONE,
      //        "Phone from Shipping Updates section is NOT correct");

      // Todo: Optional assertions or assertions missing:
      //  Submit button from trackPackage/shippingUpdates sections
      //  [user will receive a text validations]

      logger.info("13. Validating Customer Information");
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

  @Test(
      groups = {Constants.TestNGGroups.TO_BE_RENAMED},
      description = "11071745")
  public void subscriptionAndPurchaseNoDiscountAsNewUserTest() {

    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");
    homePage.closeInitialBannersAndModals();

    logger.info("2. Create new User account");
    String mail = WebHelper.getRandomMail();
    MyAccountPage myAccountPage = testHelper.createAccount(homePage, mail);
    Assert.assertNotNull(myAccountPage, "Account was not created!");

    logger.info(
        "3. Search for 1st Type of Coffee: " + Constants.WebTestData.SEARCH_COFFEE_DECAF_MAJOR);
    SearchResultsPage searchResultsPage =
        homePage
            .getHeader()
            .getSearchComponent()
            .search(Constants.WebTestData.SEARCH_COFFEE_DECAF_MAJOR);

    logger.info("4. Add 1st subscription to MiniCart");
    int firstProductSelected = 0;
    ProductDetailsPage productDetailsPage =
        searchResultsPage.clickOverProductByIndex(firstProductSelected);

    String firstProductName = productDetailsPage.getProductName();
    String firstGrind = productDetailsPage.getGrindSelected();
    int firstProductQuantity = productDetailsPage.getProductQuantitySelected();
    int firstCoffeeIndex = 1;

    productDetailsPage.selectSubscribeType();
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();
    productDetailsPage = miniCart.closeMiniCart(ProductDetailsPage.class);

    logger.info(
        "5. Search for 2nd Type of Coffee: " + Constants.WebTestData.SEARCH_COFFEE_BRAZILIAN_K_CUP);
    searchResultsPage =
        productDetailsPage
            .getHeader()
            .getSearchComponent()
            .search(Constants.WebTestData.SEARCH_COFFEE_BRAZILIAN_K_CUP);

    logger.info("6. Add 2nd item [Purchase Type] to MiniCart");
    int secondProductSelected = 0;
    productDetailsPage = searchResultsPage.clickOverProductByIndex(secondProductSelected);

    String secondProductName = productDetailsPage.getProductName();
    int secondProductQuantity = productDetailsPage.getProductQuantitySelected();
    int secondCoffeeIndex = 0;

    miniCart = productDetailsPage.clickAddToMiniCart();
    Assert.assertEquals(
        firstProductName,
        miniCart.getProductNameByIndex(firstCoffeeIndex),
        "Correct Product[1st] was not added to MiniCart");

    Assert.assertEquals(
        firstProductQuantity,
        miniCart.getProductQuantityByIndex(firstCoffeeIndex),
        "Correct product quantity[1st] was not added to MiniCart");

    Assert.assertEquals(
        secondProductName,
        miniCart.getProductNameByIndex(secondCoffeeIndex),
        "Correct Product[2nd] was not added to MiniCart");
    Assert.assertEquals(
        secondProductQuantity,
        miniCart.getProductQuantityByIndex(secondCoffeeIndex),
        "Correct product quantity[2nd] was not added to MiniCart");

    logger.info("7. Proceed to Checkout page");
    CheckOutPage checkOutPage = miniCart.clickContinueToCheckOut();
    Assert.assertTrue(
        checkOutPage.isExistingUserMailCorrect().contains(mail),
        "Recently created user mail is NOT correct");
    checkOutPage.setCheckOutDataAsExistingUser();

    logger.info("8. Proceed to Shipping page");
    ShippingPage shippingPage = checkOutPage.clickContinueToShipping();
    String shippingMethod = shippingPage.selectShippingMethodByIndex(0);

    logger.info("9. Proceed to Payments page");
    PaymentsPage paymentsPage = shippingPage.clickContinueToPayments();
    paymentsPage.setPaymentData();
    Assert.assertTrue(
        paymentsPage.isSameAddressSelected(), "Same Address option should be Selected");
    Assert.assertFalse(
        paymentsPage.isDiscountPresent(), "[Payments Page] - Discounts should NOT be available");

    if (WebHelper.getTestEnvironment().equalsIgnoreCase("production")) {
      logger.info("Testcase needs to stop, running on prod");
    } else {
      String totalPrice = paymentsPage.getTotalPrice();

      logger.info("10. Proceed to Acceptance page");
      AcceptancePage acceptancePage = paymentsPage.clickPayNow();

      logger.info("11. Validating Product Details");
      Assert.assertTrue(acceptancePage.isOrderNumberDisplayed(), "Order number is NOT displayed");
      Assert.assertTrue(acceptancePage.isSubTotalDisplayed(), "SubTotal is NOT displayed");
      Assert.assertEquals(
          firstProductName,
          acceptancePage.getOrderNameByIndex(firstCoffeeIndex),
          "Product name[1st] does NOT matches");

      Assert.assertEquals(
          secondProductName,
          acceptancePage.getOrderNameByIndex(secondCoffeeIndex),
          "Product name[2nd] does NOT matches");
      Assert.assertEquals(
          totalPrice, acceptancePage.getTotalPrice(), "Total price does NOT matches");

      Assert.assertEquals(
          acceptancePage.getShippingPrice(),
          "Free",
          "Free Shipping for Subscriptions was not set!");
      Assert.assertFalse(
          acceptancePage.isDiscountPresent(),
          "[Acceptance Page] - Discounts should NOT be available");
      Assert.assertTrue(acceptancePage.isMapDisplayed(), "Map is not displayed");

      if (WebHelper.isDesktop()) {
        // Todo: Only for desktop, until figure out if its a bug
        logger.info("12. Validating Download buttons");
        acceptancePage.clickOverTrackPackageButton();
        Assert.assertEquals(
            acceptancePage.getPhoneFromTrackPackageSection(),
            "+1 " + Constants.WebTestData.PHONE,
            "Phone from Track Package section is NOT correct");
      }

      // Todo: Commented on 15.07.2022 due to Peet's change on staging[Talked w/Shilpa and
      // Bernadette]
      //    acceptancePage.clickOverShippingUpdatesButton();
      //    Assert.assertEquals(
      //        acceptancePage.getPhoneFromShippingUpdatesSection(),
      //        "+1 " + Constants.WebTestData.PHONE,
      //        "Phone from Shipping Updates section is NOT correct");

      // Todo: Optional assertions or assertions missing:
      //  Submit button from trackPackage/shippingUpdates sections
      //  [user will receive a text validations]

      logger.info("13. Validating Customer Information");
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

  @Test(
      groups = {Constants.TestNGGroups.TO_BE_RENAMED},
      description = "11071753")
  public void subscriptionDiscountAt5AndPromoCodeAsNewUserTest() {

    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");
    homePage.closeInitialBannersAndModals();

    logger.info("2. Create new User account");
    String mail = WebHelper.getRandomMail();
    MyAccountPage myAccountPage = testHelper.createAccount(homePage, mail);
    Assert.assertNotNull(myAccountPage, "Account was not created!");

    logger.info("3. Search for Coffee: " + Constants.WebTestData.SEARCH_COFFEE_AGED_SUMATRA);
    SearchResultsPage searchResultsPage =
        homePage
            .getHeader()
            .getSearchComponent()
            .search(Constants.WebTestData.SEARCH_COFFEE_AGED_SUMATRA);

    logger.info("4. Add subscription to MiniCart");
    int productSelected = 1;
    ProductDetailsPage productDetailsPage =
        searchResultsPage.clickOverProductByIndex(productSelected);

    String productName = productDetailsPage.getProductName();
    String grind = productDetailsPage.getGrindSelected();
    int productQuantity = 2;
    int coffeeIndex = 0;
    productDetailsPage.addMoreProducts(productQuantity);
    int expectedQuantity = productDetailsPage.getProductQuantitySelected();
    Assert.assertEquals(
        productQuantity, expectedQuantity, "Quantity of products was not increased correctly");

    productDetailsPage.selectSubscribeType();
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();
    Assert.assertEquals(
        productName,
        miniCart.getProductNameByIndex(coffeeIndex),
        "Correct Product was not added to MiniCart");
    Assert.assertEquals(
        grind, miniCart.getGrindByIndex(coffeeIndex), "Correct Grind was not added to MiniCart");
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
    paymentsPage.setSubscriptionPromoCodeDiscount();
    paymentsPage.setPaymentData();
    Assert.assertTrue(
        paymentsPage.isSameAddressSelected(), "Same Address option should be Selected");
    Assert.assertTrue(
        paymentsPage.getDiscountMessage().contains("30% OFF"), "30% OFF message is not displayed");

    if (WebHelper.getTestEnvironment().equalsIgnoreCase("production")) {
      logger.info("Testcase needs to stop, running on prod");
    } else {
      String totalPrice = paymentsPage.getTotalPrice();

      logger.info("8. Proceed to Acceptance page");
      AcceptancePage acceptancePage = paymentsPage.clickPayNow();

      logger.info("9. Validating Product Details");
      Assert.assertTrue(acceptancePage.isOrderNumberDisplayed(), "Order number is NOT displayed");
      Assert.assertTrue(acceptancePage.isSubTotalDisplayed(), "SubTotal is NOT displayed");
      Assert.assertEquals(
          productName, acceptancePage.getOrderNameByIndex(0), "Product name does NOT matches");
      Assert.assertEquals(
          totalPrice, acceptancePage.getTotalPrice(), "Total price does NOT matches");

      Assert.assertEquals(
          acceptancePage.getShippingPrice(),
          "Free",
          "Free Shipping for Subscriptions was not set!");
      Assert.assertTrue(
          acceptancePage.getDiscountMessage().contains("30% OFF"),
          "30% OFF message is not displayed");
      Assert.assertTrue(acceptancePage.isMapDisplayed(), "Map is not displayed");

      if (WebHelper.isDesktop()) {
        // Todo: Only for desktop, until figure out if its a bug
        logger.info("10. Validating Download buttons");
        acceptancePage.clickOverTrackPackageButton();
        Assert.assertEquals(
            acceptancePage.getPhoneFromTrackPackageSection(),
            "+1 " + Constants.WebTestData.PHONE,
            "Phone from Track Package section is NOT correct");
      }

      // Todo: Commented on 15.07.2022 due to Peet's change on staging[Talked w/Shilpa and
      // Bernadette]
      //    acceptancePage.clickOverShippingUpdatesButton();
      //    Assert.assertEquals(
      //        acceptancePage.getPhoneFromShippingUpdatesSection(),
      //        "+1 " + Constants.WebTestData.PHONE,
      //        "Phone from Shipping Updates section is NOT correct");

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

  @Test(
      groups = {Constants.TestNGGroups.TO_BE_RENAMED},
      description = "11071754")
  public void subscriptionDiscountAt10AndPromoCodeAsNewUserTest() {

    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");
    homePage.closeInitialBannersAndModals();

    logger.info("2. Create new User account");
    String mail = WebHelper.getRandomMail();
    MyAccountPage myAccountPage = testHelper.createAccount(homePage, mail);
    Assert.assertNotNull(myAccountPage, "Account was not created!");

    logger.info("3. Search for Coffee: " + Constants.WebTestData.SEARCH_COFFEE_AGED_SUMATRA);
    SearchResultsPage searchResultsPage =
        homePage
            .getHeader()
            .getSearchComponent()
            .search(Constants.WebTestData.SEARCH_COFFEE_AGED_SUMATRA);

    logger.info("4. Add subscription to MiniCart");
    int productSelected = 1;
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
        grind, miniCart.getGrindByIndex(coffeeIndex), "Correct Grind was not added to MiniCart");
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
    paymentsPage.setSubscriptionPromoCodeDiscount();
    paymentsPage.setPaymentData();
    Assert.assertTrue(
        paymentsPage.isSameAddressSelected(), "Same Address option should be Selected");
    Assert.assertTrue(
        paymentsPage.getDiscountMessage().contains("30% OFF"), "30% OFF message is not displayed");

    if (WebHelper.getTestEnvironment().equalsIgnoreCase("production")) {
      logger.info("Testcase needs to stop, running on prod");
    } else {
      String totalPrice = paymentsPage.getTotalPrice();

      logger.info("8. Proceed to Acceptance page");
      AcceptancePage acceptancePage = paymentsPage.clickPayNow();

      logger.info("9. Validating Product Details");
      Assert.assertTrue(acceptancePage.isOrderNumberDisplayed(), "Order number is NOT displayed");
      Assert.assertTrue(acceptancePage.isSubTotalDisplayed(), "SubTotal is NOT displayed");
      Assert.assertEquals(
          productName, acceptancePage.getOrderNameByIndex(0), "Product name does NOT matches");
      Assert.assertEquals(
          totalPrice, acceptancePage.getTotalPrice(), "Total price does NOT matches");

      Assert.assertEquals(
          acceptancePage.getShippingPrice(),
          "Free",
          "Free Shipping for Subscriptions was not set!");
      Assert.assertTrue(
          acceptancePage.getDiscountMessage().contains("30% OFF"),
          "30% OFF message is not displayed");
      Assert.assertTrue(acceptancePage.isMapDisplayed(), "Map is not displayed");

      if (WebHelper.isDesktop()) {
        // Todo: Only for desktop, until figure out if its a bug
        logger.info("10. Validating Download buttons");
        acceptancePage.clickOverTrackPackageButton();
        Assert.assertEquals(
            acceptancePage.getPhoneFromTrackPackageSection(),
            "+1 " + Constants.WebTestData.PHONE,
            "Phone from Track Package section is NOT correct");
      }

      // Todo: Commented on 15.07.2022 due to Peet's change on staging[Talked w/Shilpa and
      // Bernadette]
      //    acceptancePage.clickOverShippingUpdatesButton();
      //    Assert.assertEquals(
      //        acceptancePage.getPhoneFromShippingUpdatesSection(),
      //        "+1 " + Constants.WebTestData.PHONE,
      //        "Phone from Shipping Updates section is NOT correct");

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
}
