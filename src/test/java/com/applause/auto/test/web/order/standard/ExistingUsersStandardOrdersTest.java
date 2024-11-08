package com.applause.auto.test.web.order.standard;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.test.web.BaseTest;
import com.applause.auto.web.components.Header;
import com.applause.auto.web.components.MiniCart;
import com.applause.auto.web.helpers.WebHelper;
import com.applause.auto.web.views.AcceptancePage;
import com.applause.auto.web.views.CheckOutPage;
import com.applause.auto.web.views.HomePage;
import com.applause.auto.web.views.PayPalPage;
import com.applause.auto.web.views.PaymentsPage;
import com.applause.auto.web.views.ProductDetailsPage;
import com.applause.auto.web.views.ProductListPage;
import com.applause.auto.web.views.SearchResultsPage;
import com.applause.auto.web.views.ShippingPage;
import com.applause.auto.web.views.SignInPage;
import com.applause.auto.web.views.my_account.MyAccountPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ExistingUsersStandardOrdersTest extends BaseTest {
  // Todo: Optional assertions not added:
  //  Discount if any

  @Test(
      groups = {
        Constants.TestNGGroups.FRONT_END_REGRESSION,
        Constants.TestNGGroups.CHECKOUT,
        TestNGGroups.FE_PROD_REGRESSION
      },
      description = "11107535")
  public void orderCoffeeCreditCardAsExistingUserCheckOutTest() {

    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");
    homePage.closeInitialBannersAndModals();

    logger.info("2. Sign In user");
    SignInPage signInPage = homePage.getHeader().clickAccountButton();

    signInPage.enterEmail(mail);
    signInPage.enterPassword(Constants.TestData.WEB_PASSWORD);
    MyAccountPage myAccountPage = signInPage.clickOnSignInButton();

    Assert.assertTrue(
        myAccountPage.getWelcomeMessage().contains("welcome"),
        "User is not signed in or welcome name is wrong");

    logger.info("3. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDP(coffeeSelected);
    Assert.assertNotNull(productDetailsPage, "Failed to navigate to Product Details Page");

    String productName = productDetailsPage.getProductName();
    String grind = productDetailsPage.getGrindSelected();
    int productQuantity = productDetailsPage.getProductQuantitySelected();
    int coffeeIndex = 0;

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

    logger.info("4. Proceed to Checkout page");
    CheckOutPage checkOutPage = miniCart.clickContinueToCheckOut();
    Assert.assertTrue(
        checkOutPage.isExistingUserMailCorrect().contains(mail),
        "Existing user mail is NOT correct");
    checkOutPage.setCheckOutDataAsExistingUser();

    logger.info("5. Proceed to Shipping page");
    ShippingPage shippingPage = checkOutPage.clickContinueToShipping();
    String shippingMethod = shippingPage.selectShippingMethodByIndex(0);

    logger.info("6. Proceed to Payments page");
    PaymentsPage paymentsPage = shippingPage.clickContinueToPayments();
    paymentsPage.setPaymentData();
    Assert.assertTrue(
        paymentsPage.isSameAddressSelected(), "Same Address option should be Selected");

    if (WebHelper.getTestEnvironment().equalsIgnoreCase("production")) {
      logger.info("Testcase needs to stop, running on prod");
    } else {
      String totalPrice = paymentsPage.getTotalPrice();

      logger.info("7. Proceed to Acceptance page");
      AcceptancePage acceptancePage = paymentsPage.clickPayNow();

      logger.info("8. Validating Product Details");
      Assert.assertTrue(acceptancePage.isOrderNumberDisplayed(), "Order number is NOT displayed");
      Assert.assertTrue(acceptancePage.isSubTotalDisplayed(), "SubTotal is NOT displayed");
      Assert.assertEquals(
          productName, acceptancePage.getOrderNameByIndex(0), "Product name does NOT matches");
      Assert.assertEquals(
          totalPrice, acceptancePage.getTotalPrice(), "Total price does NOT matches");
      Assert.assertTrue(acceptancePage.isMapDisplayed(), "Map is not displayed");

      if (WebHelper.isDesktop()) {
        // Todo: Only for desktop, until figure out if its a bug
        logger.info("9. Validating Download buttons");
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

      logger.info("10. Validating Customer Information");
      Assert.assertEquals(mail, acceptancePage.getCustomerMail(), "Existing Mail does NOT matches");

      Assert.assertTrue(
          acceptancePage
              .getShippingAddressData()
              .contains(Constants.WebTestData.ADDRESS.toLowerCase()),
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
          acceptancePage
              .getBillingAddressData()
              .contains(Constants.WebTestData.ADDRESS.toLowerCase()),
          "Billing Address does NOT matches");

      Assert.assertTrue(
          acceptancePage.isContinueShoppingDisplayed(),
          "Continue to Shopping button is not displayed");

      logger.info("FINISH");
    }
  }

  @Test(
      groups = {Constants.TestNGGroups.TO_BE_RENAMED, Constants.TestNGGroups.SMOKE},
      description = "11052685")
  public void orderCoffeeCreditCardAsExistingUserTest() {

    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");
    homePage.closeInitialBannersAndModals();

    logger.info("2. Sign In user");
    SignInPage signInPage = homePage.getHeader().clickAccountButton();

    signInPage.enterEmail(mail);
    signInPage.enterPassword(Constants.TestData.WEB_PASSWORD);
    MyAccountPage myAccountPage = signInPage.clickOnSignInButton();

    Assert.assertTrue(
        myAccountPage.getWelcomeMessage().contains("welcome"),
        "User is not signed in or welcome name is wrong");

    logger.info("3. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDP(coffeeSelected);
    Assert.assertNotNull(productDetailsPage, "Failed to navigate to Product Details Page");

    String productName = productDetailsPage.getProductName();
    String grind = productDetailsPage.getGrindSelected();
    int productQuantity = productDetailsPage.getProductQuantitySelected();
    int coffeeIndex = 0;

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

    logger.info("4. Proceed to Checkout page");
    CheckOutPage checkOutPage = miniCart.clickContinueToCheckOut();
    Assert.assertTrue(
        checkOutPage.isExistingUserMailCorrect().contains(mail),
        "Existing user mail is NOT correct");
    checkOutPage.setCheckOutDataAsExistingUser();

    logger.info("5. Proceed to Shipping page");
    ShippingPage shippingPage = checkOutPage.clickContinueToShipping();
    String shippingMethod = shippingPage.selectShippingMethodByIndex(0);

    logger.info("6. Proceed to Payments page");
    PaymentsPage paymentsPage = shippingPage.clickContinueToPayments();
    paymentsPage.setPaymentData();
    Assert.assertTrue(
        paymentsPage.isSameAddressSelected(), "Same Address option should be Selected");

    if (WebHelper.getTestEnvironment().equalsIgnoreCase("production")) {
      logger.info("Testcase needs to stop, running on prod");
    } else {
      String totalPrice = paymentsPage.getTotalPrice();

      logger.info("7. Proceed to Acceptance page");
      AcceptancePage acceptancePage = paymentsPage.clickPayNow();

      logger.info("8. Validating Product Details");
      Assert.assertTrue(acceptancePage.isOrderNumberDisplayed(), "Order number is NOT displayed");
      Assert.assertTrue(acceptancePage.isSubTotalDisplayed(), "SubTotal is NOT displayed");
      Assert.assertEquals(
          productName, acceptancePage.getOrderNameByIndex(0), "Product name does NOT matches");
      Assert.assertEquals(
          totalPrice, acceptancePage.getTotalPrice(), "Total price does NOT matches");
      Assert.assertTrue(acceptancePage.isMapDisplayed(), "Map is not displayed");

      if (WebHelper.isDesktop()) {
        // Todo: Only for desktop, until figure out if its a bug
        logger.info("9. Validating Download buttons");
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

      logger.info("10. Validating Customer Information");
      Assert.assertEquals(mail, acceptancePage.getCustomerMail(), "Existing Mail does NOT matches");

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
      description = "11107536")
  // Todo:Ask Rich, is this correct?
  public void orderCoffeePayPalSubscriptionAsExistingUserTest() {

    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");
    homePage.closeInitialBannersAndModals();

    logger.info("2. Sign In user");
    SignInPage signInPage = homePage.getHeader().clickAccountButton();

    signInPage.enterEmail(mail);
    signInPage.enterPassword(Constants.TestData.WEB_PASSWORD);
    MyAccountPage myAccountPage = signInPage.clickOnSignInButton();

    Assert.assertTrue(
        myAccountPage.getWelcomeMessage().contains("welcome"),
        "User is not signed in or welcome name is wrong");

    logger.info("3. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDP(coffeeSelected);
    Assert.assertNotNull(productDetailsPage, "Failed to navigate to Product Details Page");

    String productName = productDetailsPage.getProductName();
    String grind = productDetailsPage.getGrindSelected();
    int productQuantity = productDetailsPage.getProductQuantitySelected();
    int coffeeIndex = 0;
    //    productDetailsPage.selectSubscribeType(); //Commented due to new flow on continue with
    // this susbcription? [Double check with Vaivhab/Shilpa]

    logger.info("4. Add to MiniCart");
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
        "Existing user mail is NOT correct");
    checkOutPage.setCheckOutDataAsExistingUser();

    logger.info("6. Proceed to Shipping page");
    ShippingPage shippingPage = checkOutPage.clickContinueToShipping();
    String shippingMethod = shippingPage.selectShippingMethodByIndex(0);

    logger.info("7. Proceed to Payments page");
    PaymentsPage paymentsPage = shippingPage.clickContinueToPayments();
    paymentsPage.clickPayPal();
    Assert.assertTrue(
        paymentsPage.isSameAddressSelected(), "Same Address option should be Selected");

    String totalPrice = paymentsPage.getTotalPrice();

    logger.info("8. Filling in paPal");
    PayPalPage payPalPage = paymentsPage.clickContinueToPaymentsPayPal();

    if (WebHelper.getTestEnvironment().equalsIgnoreCase("production")) {
      logger.info("Testcase needs to stop, running on prod");
    } else {
      AcceptancePage acceptancePage =
          payPalPage.fillPayPal(
              Constants.TestData.SECONDARY_PAYPAL_EMAIL,
              Constants.TestData.SECONDARY_PAYPAL_PASSWORD);

      logger.info("9. Validating Product Details");
      Assert.assertTrue(acceptancePage.isOrderNumberDisplayed(), "Order number is NOT displayed");
      Assert.assertTrue(acceptancePage.isSubTotalDisplayed(), "SubTotal is NOT displayed");
      Assert.assertEquals(
          productName, acceptancePage.getOrderNameByIndex(0), "Product name does NOT matches");
      Assert.assertEquals(
          totalPrice, acceptancePage.getTotalPrice(), "Total price does NOT matches");
      Assert.assertTrue(acceptancePage.isMapDisplayed(), "Map is not displayed");

      // Todo: Commented on 15.07.2022 due to Peet's change on staging[Talked w/Shilpa and
      // Bernadette]
      //    acceptancePage.clickOverShippingUpdatesButton();
      //    Assert.assertEquals(
      //        acceptancePage.getPhoneFromShippingUpdatesSection(),
      //        "+1 " + Constants.WebTestData.PHONE,
      //        "Phone from Shipping Updates section is NOT correct");

      logger.info("10. Validating Customer Information");
      Assert.assertEquals(mail, acceptancePage.getCustomerMail(), "Existing Mail does NOT matches");

      Assert.assertTrue(
          acceptancePage
              .getShippingAddressData()
              .contains(Constants.WebTestData.ADDRESS.toLowerCase()),
          "Shipping Address does NOT matches");

      Assert.assertTrue(
          shippingMethod.contains(acceptancePage.getShippingMethod()),
          "Shipping Method does NOT matches");

      Assert.assertTrue(
          acceptancePage
              .getBillingAddressData()
              .contains(Constants.WebTestData.ADDRESS.toLowerCase()),
          "Billing Address does NOT matches");

      logger.info("FINISH");
    }
  }

  @Test(
      groups = {Constants.TestNGGroups.TO_BE_RENAMED},
      description = "11071529")
  public void oneCoffeeOneEquipmentCreditCardAsExistingUserTest() {

    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");
    homePage.closeInitialBannersAndModals();

    logger.info("2. Sign In user");
    SignInPage signInPage = homePage.getHeader().clickAccountButton();

    signInPage.enterEmail(mail);
    signInPage.enterPassword(Constants.TestData.WEB_PASSWORD);
    MyAccountPage myAccountPage = signInPage.clickOnSignInButton();

    Assert.assertTrue(
        myAccountPage.getWelcomeMessage().contains("welcome"),
        "User is not signed in or welcome name is wrong");

    logger.info("3. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDP(coffeeSelected);
    Assert.assertNotNull(productDetailsPage, "Failed to navigate to Product Details Page");

    String coffeeName = productDetailsPage.getProductName();
    String coffeeGrind = productDetailsPage.getGrindSelected();
    int coffeeQuantity = productDetailsPage.getProductQuantitySelected();

    logger.info("4. Add to MiniCart");
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("5. Select Equipment from Gear tab");
    miniCart.closeMiniCart(ProductDetailsPage.class);

    logger.info("6. Add equipment item to MiniCart");
    productDetailsPage = navigateToPDP(Constants.TestData.HOODIE);

    String equipmentName = productDetailsPage.getProductName();
    int equipmentQuantity = productDetailsPage.getProductQuantitySelected();

    miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("7. Validate items added to Cart");
    int coffeeIndex = 1;
    int equipmentIndex = 0;

    Assert.assertEquals(
        coffeeName,
        miniCart.getProductNameByIndex(coffeeIndex),
        "Correct Coffee Product was not added to MiniCart");
    Assert.assertEquals(
        coffeeGrind,
        miniCart.getGrindByIndex(coffeeIndex),
        "Correct Coffee Grind was not added to MiniCart");
    Assert.assertEquals(
        coffeeQuantity,
        miniCart.getProductQuantityByIndex(coffeeIndex),
        "Correct Coffee product quantity was not added to MiniCart");

    Assert.assertEquals(
        equipmentName,
        miniCart.getProductNameByIndex(equipmentIndex),
        "Correct Equipment Product was not added to MiniCart");
    Assert.assertEquals(
        equipmentQuantity,
        miniCart.getProductQuantityByIndex(equipmentIndex),
        "Correct Equipment product quantity was not added to MiniCart");

    logger.info("8. Proceed to Checkout page");
    CheckOutPage checkOutPage = miniCart.clickContinueToCheckOut();
    Assert.assertTrue(
        checkOutPage.isExistingUserMailCorrect().contains(mail),
        "Existing user mail is NOT correct");
    checkOutPage.setCheckOutDataAsExistingUser();

    logger.info("9. Proceed to Shipping page");
    ShippingPage shippingPage = checkOutPage.clickContinueToShipping();
    String shippingMethod = shippingPage.selectShippingMethodByIndex(0);

    logger.info("10. Proceed to Payments page");
    PaymentsPage paymentsPage = shippingPage.clickContinueToPayments();
    // Todo:Peet's Card is not working, always [11/06.2021]
    //    paymentsPage.setPeetsCardDiscount();
    paymentsPage.setPaymentData();

    if (WebHelper.getTestEnvironment().equalsIgnoreCase("production")) {
      logger.info("Testcase needs to stop, running on prod");
    } else {
      String totalPrice = paymentsPage.getTotalPrice();

      logger.info("11. Proceed to Acceptance page");
      AcceptancePage acceptancePage = paymentsPage.clickPayNow();

      logger.info("12. Validating Product Details");
      Assert.assertTrue(acceptancePage.isOrderNumberDisplayed(), "Order number is NOT displayed");
      Assert.assertTrue(acceptancePage.isSubTotalDisplayed(), "SubTotal is NOT displayed");
      Assert.assertEquals(
          coffeeName,
          acceptancePage.getOrderNameByIndex(coffeeIndex),
          "Coffee Product name does NOT matches");
      Assert.assertEquals(
          equipmentName,
          acceptancePage.getOrderNameByIndex(equipmentIndex),
          "Equipment Product name does NOT matches");
      Assert.assertEquals(
          totalPrice, acceptancePage.getTotalPrice(), "Total price does NOT matches");
      Assert.assertTrue(acceptancePage.isMapDisplayed(), "Map is not displayed");

      if (WebHelper.isDesktop()) {
        // Todo: Only for desktop, until figure out if its a bug
        logger.info("13. Validating Download buttons");
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

      logger.info("14. Validating Customer Information");
      Assert.assertEquals(mail, acceptancePage.getCustomerMail(), "Existing Mail does NOT matches");

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
      description = "11071528")
  public void oneRegularCoffeeOneReserveCoffeePeetsCardAsExistingUserTest() {

    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");
    homePage.closeInitialBannersAndModals();

    logger.info("2. Sign In user");
    SignInPage signInPage = homePage.getHeader().clickAccountButton();

    signInPage.enterEmail(mail);
    signInPage.enterPassword(Constants.TestData.WEB_PASSWORD);
    MyAccountPage myAccountPage = signInPage.clickOnSignInButton();

    Assert.assertTrue(
        myAccountPage.getWelcomeMessage().contains("welcome"),
        "User is not signed in or welcome name is wrong");

    logger.info("3. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDP(coffeeSelected);
    Assert.assertNotNull(productDetailsPage, "Failed to navigate to Product Details Page");

    String coffeeName = productDetailsPage.getProductName();
    String coffeeGrind = productDetailsPage.getGrindSelected();
    int coffeeQuantity = productDetailsPage.getProductQuantitySelected();

    logger.info("4. Add to MiniCart");
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("5. Search for Coffee: " + Constants.WebTestData.SEARCH_COFFEE_JR_RESERVE_BLEND);
    productDetailsPage = miniCart.closeMiniCart(ProductDetailsPage.class);
    SearchResultsPage searchResultsPage =
        productDetailsPage
            .getHeader()
            .getSearchComponent()
            .search(Constants.WebTestData.SEARCH_COFFEE_JR_RESERVE_BLEND);

    logger.info("6. Add reserve coffee item to MiniCart");
    int reserveSelected = 0;
    productDetailsPage = searchResultsPage.clickOverProductByIndex(reserveSelected);

    String reserveName = productDetailsPage.getProductName();
    int reserveQuantity = productDetailsPage.getProductQuantitySelected();

    miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("7. Validate items added to Cart");
    int coffeeIndex = 1;
    int reserveIndex = 0;

    Assert.assertEquals(
        coffeeName,
        miniCart.getProductNameByIndex(coffeeIndex),
        "Correct Coffee Product was not added to MiniCart");
    Assert.assertEquals(
        coffeeGrind,
        miniCart.getGrindByIndex(coffeeIndex),
        "Correct Coffee Grind was not added to MiniCart");
    Assert.assertEquals(
        coffeeQuantity,
        miniCart.getProductQuantityByIndex(coffeeIndex),
        "Correct Coffee product quantity was not added to MiniCart");

    Assert.assertEquals(
        reserveName,
        miniCart.getProductNameByIndex(reserveIndex),
        "Correct Reserve Product was not added to MiniCart");
    Assert.assertEquals(
        reserveQuantity,
        miniCart.getProductQuantityByIndex(reserveIndex),
        "Correct Reserve product quantity was not added to MiniCart");

    logger.info("8. Proceed to Checkout page");
    CheckOutPage checkOutPage = miniCart.clickContinueToCheckOut();
    Assert.assertTrue(
        checkOutPage.isExistingUserMailCorrect().contains(mail),
        "Existing user mail is NOT correct");
    checkOutPage.setCheckOutDataAsExistingUser();

    logger.info("9. Proceed to Shipping page");
    ShippingPage shippingPage = checkOutPage.clickContinueToShipping();
    String shippingMethod = shippingPage.selectShippingMethodByIndex(0);

    logger.info("10. Proceed to Payments page");
    PaymentsPage paymentsPage = shippingPage.clickContinueToPayments();
    // Todo:Peet's Card is not working, always [11/06.2021]
    //    paymentsPage.setPeetsCardDiscount();
    paymentsPage.setPaymentData();

    if (WebHelper.getTestEnvironment().equalsIgnoreCase("production")) {
      logger.info("Testcase needs to stop, running on prod");
    } else {
      String totalPrice = paymentsPage.getTotalPrice();

      logger.info("11. Proceed to Acceptance page");
      AcceptancePage acceptancePage = paymentsPage.clickPayNow();

      logger.info("12. Validating Product Details");
      Assert.assertTrue(acceptancePage.isOrderNumberDisplayed(), "Order number is NOT displayed");
      Assert.assertTrue(acceptancePage.isSubTotalDisplayed(), "SubTotal is NOT displayed");
      Assert.assertEquals(
          coffeeName,
          acceptancePage.getOrderNameByIndex(coffeeIndex),
          "Coffee Product name does NOT matches");
      Assert.assertEquals(
          reserveName,
          acceptancePage.getOrderNameByIndex(reserveIndex),
          "Reserve Product name does NOT matches");
      Assert.assertEquals(
          totalPrice, acceptancePage.getTotalPrice(), "Total price does NOT matches");
      Assert.assertTrue(acceptancePage.isMapDisplayed(), "Map is not displayed");

      if (WebHelper.isDesktop()) {
        // Todo: Only for desktop, until figure out if its a bug
        logger.info("13. Validating Download buttons");
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

      logger.info("14. Validating Customer Information");
      Assert.assertEquals(mail, acceptancePage.getCustomerMail(), "Existing Mail does NOT matches");

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
      description = "11071530",
      enabled = false)
  // Todo:Disable for now, due to inventory issue [28.10.2021]
  public void oneTeaOneKCupsPromoCodeAsExistingUserTest() {

    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");
    homePage.closeInitialBannersAndModals();

    logger.info("2. Sign In user");
    SignInPage signInPage = homePage.getHeader().clickAccountButton();

    signInPage.enterEmail(mail);
    signInPage.enterPassword(Constants.TestData.WEB_PASSWORD);
    MyAccountPage myAccountPage = signInPage.clickOnSignInButton();

    Assert.assertTrue(
        myAccountPage.getWelcomeMessage().contains("welcome"),
        "User is not signed in or welcome name is wrong");

    logger.info("3. Select Best Sellers from Tea tab");
    Header header = homePage.getHeader();
    header.hoverCategoryFromMenu(Constants.MenuOptions.TEA);
    ProductListPage productListPage =
        header.clickOverSubCategoryFromMenu(
            ProductListPage.class, Constants.MenuSubCategories.TEA_BEST_SELLERS);

    logger.info("4. Add tea item to MiniCart");
    int teaSelected = testHelper.findInStockItemPosition(productListPage) - 1;
    ProductDetailsPage productDetailsPage = productListPage.clickOverProductByIndex(teaSelected);

    String teaName = productDetailsPage.getProductName();
    int teaQuantity = productDetailsPage.getProductQuantitySelected();

    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("5. Select K-Cup from Coffee tab");
    productDetailsPage = miniCart.closeMiniCart(ProductDetailsPage.class);
    header = productDetailsPage.getHeader();

    header.hoverCategoryFromMenu(Constants.MenuOptions.COFFEE);
    productListPage =
        header.clickOverSubCategoryFromMenu(
            ProductListPage.class, Constants.MenuSubCategories.COFFEE_K_CUPS);

    logger.info("6. Add K-cups item to MiniCart");
    int cupsSelected = 2;
    productDetailsPage = productListPage.clickOverProductByIndex(cupsSelected);

    String cupsName = productDetailsPage.getProductName();
    int cupsQuantity = productDetailsPage.getProductQuantitySelected();

    miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("7. Validate items added to Cart");
    int teaIndex = 1;
    int cupsIndex = 0;

    Assert.assertEquals(
        teaName,
        miniCart.getProductNameByIndex(teaIndex),
        "Correct Tea Product was not added to MiniCart");

    Assert.assertEquals(
        teaQuantity,
        miniCart.getProductQuantityByIndex(teaIndex),
        "Correct Tea product quantity was not added to MiniCart");

    Assert.assertEquals(
        cupsName,
        miniCart.getProductNameByIndex(cupsIndex),
        "Correct K-Cups Product was not added to MiniCart");
    Assert.assertEquals(
        cupsQuantity,
        miniCart.getProductQuantityByIndex(cupsIndex),
        "Correct K-Cups product quantity was not added to MiniCart");

    logger.info("8. Proceed to Checkout page");
    CheckOutPage checkOutPage = miniCart.clickContinueToCheckOut();
    Assert.assertTrue(
        checkOutPage.isExistingUserMailCorrect().contains(mail),
        "Existing user mail is NOT correct");
    checkOutPage.setCheckOutDataAsExistingUser();

    logger.info("9. Proceed to Shipping page");
    ShippingPage shippingPage = checkOutPage.clickContinueToShipping();
    String shippingMethod = shippingPage.selectShippingMethodByIndex(0);

    logger.info("10. Proceed to Payments page");
    PaymentsPage paymentsPage = shippingPage.clickContinueToPayments();
    paymentsPage.setFreeShippingPromoCodeDiscount();
    paymentsPage.setPaymentData();

    if (WebHelper.getTestEnvironment().equalsIgnoreCase("production")) {
      logger.info("Testcase needs to stop, running on prod");
    } else {
      String totalPrice = paymentsPage.getTotalPrice();

      logger.info("11. Proceed to Acceptance page");
      AcceptancePage acceptancePage = paymentsPage.clickPayNow();

      logger.info("12. Validating Product Details");
      Assert.assertTrue(acceptancePage.isOrderNumberDisplayed(), "Order number is NOT displayed");
      Assert.assertTrue(acceptancePage.isSubTotalDisplayed(), "SubTotal is NOT displayed");
      Assert.assertEquals(
          teaName,
          acceptancePage.getOrderNameByIndex(teaIndex),
          "Tea Product name does NOT matches");
      Assert.assertEquals(
          cupsName,
          acceptancePage.getOrderNameByIndex(cupsIndex),
          "Reserve Product name does NOT matches");
      Assert.assertEquals(
          totalPrice, acceptancePage.getTotalPrice(), "Total price does NOT matches");
      Assert.assertEquals(
          acceptancePage.getDiscountText(),
          "Free shipping",
          "Discount from promoCode was not applied");
      Assert.assertTrue(acceptancePage.isMapDisplayed(), "Map is not displayed");

      if (WebHelper.isDesktop()) {
        // Todo: Only for desktop, until figure out if its a bug
        logger.info("13. Validating Download buttons");
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

      logger.info("14. Validating Customer Information");
      Assert.assertEquals(mail, acceptancePage.getCustomerMail(), "Existing Mail does NOT matches");

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
      description = "11071611")
  public void oneEquipmentWithPeetsCardAsExistingUserTest() {

    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");
    homePage.closeInitialBannersAndModals();

    logger.info("2. Sign In user");
    SignInPage signInPage = homePage.getHeader().clickAccountButton();

    signInPage.enterEmail(mail);
    signInPage.enterPassword(Constants.TestData.WEB_PASSWORD);
    MyAccountPage myAccountPage = signInPage.clickOnSignInButton();

    Assert.assertTrue(
        myAccountPage.getWelcomeMessage().contains("welcome"),
        "User is not signed in or welcome name is wrong");

    logger.info("3. Select Equipment from Gear tab");
    ProductListPage productListPage = navigateToGearSection();

    logger.info("4. Add equipment item to MiniCart");
    ProductDetailsPage productDetailsPage = null;
    for (int equipmentSelected = 0; equipmentSelected < 3; equipmentSelected++) {
      productDetailsPage = productListPage.clickOverProductByIndex(equipmentSelected);
      if (!productDetailsPage.isItemAvailable()) {
        logger.info("Item available");
        break;
      }
      logger.info("Item not available, looking for other item: " + equipmentSelected);
      productListPage = WebHelper.navigateBack(ProductListPage.class);
    }

    String equipmentName = productDetailsPage.getProductName();
    int equipmentQuantity = productDetailsPage.getProductQuantitySelected();

    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("5. Validate items added to Cart");
    int equipmentIndex = 0;

    Assert.assertEquals(
        equipmentName,
        miniCart.getProductNameByIndex(equipmentIndex),
        "Correct Equipment Product was not added to MiniCart");
    Assert.assertEquals(
        equipmentQuantity,
        miniCart.getProductQuantityByIndex(equipmentIndex),
        "Correct Equipment product quantity was not added to MiniCart");

    logger.info("6. Proceed to Checkout page");
    CheckOutPage checkOutPage = miniCart.clickContinueToCheckOut();
    Assert.assertTrue(
        checkOutPage.isExistingUserMailCorrect().contains(mail),
        "Existing user mail is NOT correct");
    checkOutPage.setCheckOutDataAsExistingUser();

    logger.info("7. Proceed to Shipping page");
    ShippingPage shippingPage = checkOutPage.clickContinueToShipping();
    String shippingMethod = shippingPage.selectShippingMethodByIndex(0);

    logger.info("8. Proceed to Payments page");
    PaymentsPage paymentsPage = shippingPage.clickContinueToPayments();
    // Todo:Peet's Card is not working, always [11/06.2021]
    //    paymentsPage.setPeetsCardDiscount();
    paymentsPage.setPaymentData();

    if (WebHelper.getTestEnvironment().equalsIgnoreCase("production")) {
      logger.info("Testcase needs to stop, running on prod");
    } else {
      String totalPrice = paymentsPage.getTotalPrice();

      logger.info("9. Proceed to Acceptance page");
      AcceptancePage acceptancePage = paymentsPage.clickPayNow();

      logger.info("10. Validating Product Details");
      Assert.assertTrue(acceptancePage.isOrderNumberDisplayed(), "Order number is NOT displayed");
      Assert.assertTrue(acceptancePage.isSubTotalDisplayed(), "SubTotal is NOT displayed");
      Assert.assertEquals(
          equipmentName,
          acceptancePage.getOrderNameByIndex(equipmentIndex),
          "Equipment Product name does NOT matches");
      Assert.assertEquals(
          totalPrice, acceptancePage.getTotalPrice(), "Total price does NOT matches");
      Assert.assertTrue(acceptancePage.isMapDisplayed(), "Map is not displayed");

      if (WebHelper.isDesktop()) {
        // Todo: Only for desktop, until figure out if its a bug
        logger.info("11. Validating Download buttons");
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

      logger.info("12. Validating Customer Information");
      Assert.assertEquals(mail, acceptancePage.getCustomerMail(), "Existing Mail does NOT matches");

      Assert.assertTrue(
          acceptancePage
              .getShippingAddressData()
              .contains(Constants.WebTestData.ADDRESS.toLowerCase()),
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
          acceptancePage
              .getBillingAddressData()
              .contains(Constants.WebTestData.ADDRESS.toLowerCase()),
          "Billing Address does NOT matches");

      Assert.assertTrue(
          acceptancePage.isContinueShoppingDisplayed(),
          "Continue to Shopping button is not displayed");

      logger.info("FINISH");
    }
  }

  @Test(
      groups = {Constants.TestNGGroups.TO_BE_RENAMED},
      description = "11071612",
      enabled = false)
  // Todo:Disable for now, due to inventory issue [28.10.2021]
  public void oneKCupsWithPeetsCardAsExistingUserTest() {

    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");
    homePage.closeInitialBannersAndModals();

    logger.info("2. Sign In user");
    SignInPage signInPage = homePage.getHeader().clickAccountButton();

    signInPage.enterEmail(mail);
    signInPage.enterPassword(Constants.TestData.WEB_PASSWORD);
    MyAccountPage myAccountPage = signInPage.clickOnSignInButton();

    Assert.assertTrue(
        myAccountPage.getWelcomeMessage().contains("welcome"),
        "User is not signed in or welcome name is wrong");

    logger.info("3. Select K-Cup from Coffee tab");
    Header header = homePage.getHeader();
    header.hoverCategoryFromMenu(Constants.MenuOptions.COFFEE);
    ProductListPage productListPage =
        header.clickOverSubCategoryFromMenu(
            ProductListPage.class, Constants.MenuSubCategories.COFFEE_K_CUPS);

    logger.info("4. Add K-cups item to MiniCart");
    int cupsSelected = testHelper.findInStockItemPosition(productListPage) - 1;
    ProductDetailsPage productDetailsPage = productListPage.clickOverProductByIndex(cupsSelected);

    String cupsName = productDetailsPage.getProductName();
    int cupsQuantity = productDetailsPage.getProductQuantitySelected();
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("5. Validate items added to Cart");
    int cupsIndex = 0;

    Assert.assertEquals(
        cupsName,
        miniCart.getProductNameByIndex(cupsIndex),
        "Correct K-Cups Product was not added to MiniCart");
    Assert.assertEquals(
        cupsQuantity,
        miniCart.getProductQuantityByIndex(cupsIndex),
        "Correct K-Cups product quantity was not added to MiniCart");

    logger.info("6. Proceed to Checkout page");
    CheckOutPage checkOutPage = miniCart.clickContinueToCheckOut();
    Assert.assertTrue(
        checkOutPage.isExistingUserMailCorrect().contains(mail),
        "Existing user mail is NOT correct");
    checkOutPage.setCheckOutDataAsExistingUser();

    logger.info("7. Proceed to Shipping page");
    ShippingPage shippingPage = checkOutPage.clickContinueToShipping();
    String shippingMethod = shippingPage.selectShippingMethodByIndex(0);

    logger.info("8. Proceed to Payments page");
    PaymentsPage paymentsPage = shippingPage.clickContinueToPayments();
    // Todo:Peet's Card is not working, always [11/06.2021]
    //    paymentsPage.setPeetsCardDiscount();
    paymentsPage.setPaymentData();

    if (WebHelper.getTestEnvironment().equalsIgnoreCase("production")) {
      logger.info("Testcase needs to stop, running on prod");
    } else {
      String totalPrice = paymentsPage.getTotalPrice();

      logger.info("9. Proceed to Acceptance page");
      AcceptancePage acceptancePage = paymentsPage.clickPayNow();

      logger.info("10. Validating Product Details");
      Assert.assertTrue(acceptancePage.isOrderNumberDisplayed(), "Order number is NOT displayed");
      Assert.assertTrue(acceptancePage.isSubTotalDisplayed(), "SubTotal is NOT displayed");

      Assert.assertEquals(
          cupsName,
          acceptancePage.getOrderNameByIndex(cupsIndex),
          "Reserve Product name does NOT matches");
      Assert.assertEquals(
          totalPrice, acceptancePage.getTotalPrice(), "Total price does NOT matches");
      Assert.assertTrue(acceptancePage.isMapDisplayed(), "Map is not displayed");

      if (WebHelper.isDesktop()) {
        // Todo: Only for desktop, until figure out if its a bug
        logger.info("11. Validating Download buttons");
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

      logger.info("12. Validating Customer Information");
      Assert.assertEquals(mail, acceptancePage.getCustomerMail(), "Existing Mail does NOT matches");

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
