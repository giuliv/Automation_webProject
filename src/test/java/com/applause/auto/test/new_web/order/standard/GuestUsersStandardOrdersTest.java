package com.applause.auto.test.new_web.order.standard;

import com.applause.auto.common.data.Constants;
import com.applause.auto.new_web.components.Header;
import com.applause.auto.new_web.components.MiniCart;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.new_web.views.AcceptancePage;
import com.applause.auto.new_web.views.CheckOutPage;
import com.applause.auto.new_web.views.HomePage;
import com.applause.auto.new_web.views.PaymentsPage;
import com.applause.auto.new_web.views.ProductDetailsPage;
import com.applause.auto.new_web.views.ProductListPage;
import com.applause.auto.new_web.views.SearchResultsPage;
import com.applause.auto.new_web.views.ShippingPage;
import com.applause.auto.test.new_web.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GuestUsersStandardOrdersTest extends BaseTest {
  // Todo: Optional assertions not added:
  //  Discount if any

  @Test(
      groups = {
        Constants.TestNGGroups.TO_BE_RENAMED,
        Constants.TestNGGroups.WEB_PROD_MONITORING,
        Constants.TestNGGroups.SMOKE
      },
      description = "11052681")
  public void orderCoffeeCreditCardAsGuestUserTest() {

    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");
    homePage.closeInitialBannersAndModals();

    int productSelected = 1;
    if (WebHelper.getTestEnvironment().equalsIgnoreCase("production")) {
      homePage = WebHelper.refreshMe(HomePage.class);
    }

    logger.info("2. Select Best Sellers from Coffee tab");
    Header header = homePage.getHeader();
    header.hoverCategoryFromMenu(Constants.MenuOptions.COFFEE);
    ProductListPage productListPage =
        header.clickOverSubCategoryFromMenu(
            ProductListPage.class, Constants.MenuSubCategories.COFFEE_BEST_SELLERS);

    logger.info("3. Add first item to MiniCart");
    ProductDetailsPage productDetailsPage =
        productListPage.clickOverProductByIndex(productSelected);

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
    checkOutPage.setCheckOutData();

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

      // Todo: Commented due to Peet's change on staging[Talked w/Shilpa and Bernadette]
      //      acceptancePage.clickOverShippingUpdatesButton();
      //      Assert.assertEquals(
      //          acceptancePage.getPhoneFromShippingUpdatesSection(),
      //          "+1 " + Constants.WebTestData.PHONE,
      //          "Phone from Shipping Updates section is NOT correct");

      // Todo: Optional assertions or assertions missing:
      //  Submit button from trackPackage/shippingUpdates sections
      //  [user will receive a text validations]

      logger.info("10. Validating Customer Information");
      Assert.assertEquals(
          Constants.WebTestData.EMAIL, acceptancePage.getCustomerMail(), "Mail does NOT matches");

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
    }

    logger.info("FINISH");
  }

  @Test(
      groups = {Constants.TestNGGroups.TO_BE_RENAMED},
      description = "11052683")
  public void oneCoffeeOneEquipmentCreditCardAsGuestUserTest() {

    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDPFromHome(coffeeSelected);
    Assert.assertNotNull(productDetailsPage, "Failed to navigate to Product Details Page");

    String coffeeName = productDetailsPage.getProductName();
    String coffeeGrind = productDetailsPage.getGrindSelected();
    int coffeeQuantity = productDetailsPage.getProductQuantitySelected();

    logger.info("2. Add coffee item to MiniCart");
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("3. Select Equipment from Gear tab");
    productDetailsPage = miniCart.closeMiniCart(ProductDetailsPage.class);
    ProductListPage productListPage = navigateToGearSection();

    logger.info("4. Add equipment item to MiniCart");
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

    miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("5. Validate items added to Cart");
    int coffeeIndex = 1;
    int equipmentIndex = 0;

    Assert.assertEquals(
        coffeeName,
        miniCart.getProductNameByIndex(coffeeIndex),
        "Correct Coffee Product was not added to MiniCart");
    // Use 0 index, since equipment does not have Grind
    Assert.assertEquals(
        coffeeGrind, miniCart.getGrindByIndex(0), "Correct Coffee Grind was not added to MiniCart");
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

    logger.info("6. Proceed to Checkout page");
    CheckOutPage checkOutPage = miniCart.clickContinueToCheckOut();
    checkOutPage.setCheckOutData();

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
        logger.info("11. Validating Download buttons");
        acceptancePage.clickOverTrackPackageButton();
        Assert.assertEquals(
            acceptancePage.getPhoneFromTrackPackageSection(),
            "+1 " + Constants.WebTestData.PHONE,
            "Phone from Track Package section is NOT correct");
      }

      // Todo: Commented due to Peet's change on staging[Talked w/Shilpa and Bernadette]
      //    acceptancePage.clickOverShippingUpdatesButton();
      //    Assert.assertEquals(
      //        acceptancePage.getPhoneFromShippingUpdatesSection(),
      //        "+1 " + Constants.WebTestData.PHONE,
      //        "Phone from Shipping Updates section is NOT correct");

      // Todo: Optional assertions or assertions missing:
      //  Submit button from trackPackage/shippingUpdates sections
      //  [user will receive a text validations]

      logger.info("12. Validating Customer Information");
      Assert.assertEquals(
          Constants.WebTestData.EMAIL, acceptancePage.getCustomerMail(), "Mail does NOT matches");

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
      description = "11052682")
  public void oneRegularCoffeeOneReserveCoffeePeetsCardAsGuestUserTest() {

    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDPFromHome(coffeeSelected);
    Assert.assertNotNull(productDetailsPage, "Failed to navigate to Product Details Page");

    String coffeeName = productDetailsPage.getProductName();
    String coffeeGrind = productDetailsPage.getGrindSelected();
    int coffeeQuantity = productDetailsPage.getProductQuantitySelected();

    logger.info("2. Add coffee item to MiniCart");
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("3. Search for Coffee: " + Constants.WebTestData.SEARCH_COFFEE_JR_RESERVE_BLEND);
    productDetailsPage = miniCart.closeMiniCart(ProductDetailsPage.class);
    SearchResultsPage searchResultsPage =
        productDetailsPage
            .getHeader()
            .getSearchComponent()
            .search(Constants.WebTestData.SEARCH_COFFEE_JR_RESERVE_BLEND);

    logger.info("4. Add reserve coffee item to MiniCart");
    int reserveSelected = 0;
    productDetailsPage = searchResultsPage.clickOverProductByIndex(reserveSelected);

    String reserveName = productDetailsPage.getProductName();
    int reserveQuantity = productDetailsPage.getProductQuantitySelected();

    miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("5. Validate items added to Cart");
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

    logger.info("6. Proceed to Checkout page");
    CheckOutPage checkOutPage = miniCart.clickContinueToCheckOut();
    checkOutPage.setCheckOutData();

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
      Assert.assertEquals(
          Constants.WebTestData.EMAIL, acceptancePage.getCustomerMail(), "Mail does NOT matches");

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
      groups = {Constants.TestNGGroups.TO_BE_RENAMED, Constants.TestNGGroups.SANITY},
      description = "11052684",
      enabled = false)
  // Todo:Disable for now, due to inventory issue [28.10.2021]
  public void oneTeaOneKCupsPromoCodeAsGuestUserTest() {

    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");
    homePage.closeInitialBannersAndModals();

    logger.info("2. Select Best Sellers from Tea tab");
    Header header = homePage.getHeader();
    header.hoverCategoryFromMenu(Constants.MenuOptions.TEA);
    ProductListPage productListPage =
        header.clickOverSubCategoryFromMenu(
            ProductListPage.class, Constants.MenuSubCategories.TEA_BEST_SELLERS);

    logger.info("3. Add tea item to MiniCart");
    int teaSelected = 3;
    ProductDetailsPage productDetailsPage = productListPage.clickOverProductByIndex(teaSelected);

    String teaName = productDetailsPage.getProductName();
    int teaQuantity = productDetailsPage.getProductQuantitySelected();

    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("4. Select K-Cup from Coffee tab");
    productDetailsPage = miniCart.closeMiniCart(ProductDetailsPage.class);
    header = productDetailsPage.getHeader();

    header.hoverCategoryFromMenu(Constants.MenuOptions.COFFEE);
    productListPage =
        header.clickOverSubCategoryFromMenu(
            ProductListPage.class, Constants.MenuSubCategories.COFFEE_K_CUPS);

    logger.info("5. Add K-cups item to MiniCart");
    int cupsSelected = 2;
    productDetailsPage = productListPage.clickOverProductByIndex(cupsSelected);

    String cupsName = productDetailsPage.getProductName();
    int cupsQuantity = productDetailsPage.getProductQuantitySelected();

    miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("6. Validate items added to Cart");
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

    logger.info("7. Proceed to Checkout page");
    CheckOutPage checkOutPage = miniCart.clickContinueToCheckOut();
    checkOutPage.setCheckOutData();

    logger.info("8. Proceed to Shipping page");
    ShippingPage shippingPage = checkOutPage.clickContinueToShipping();
    String shippingMethod = shippingPage.selectShippingMethodByIndex(0);

    logger.info("9. Proceed to Payments page");
    PaymentsPage paymentsPage = shippingPage.clickContinueToPayments();
    paymentsPage.setFreeShippingPromoCodeDiscount();
    paymentsPage.setPaymentData();

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
        logger.info("12. Validating Download buttons");
        acceptancePage.clickOverTrackPackageButton();
        Assert.assertEquals(
            acceptancePage.getPhoneFromTrackPackageSection(),
            "+1 " + Constants.WebTestData.PHONE,
            "Phone from Track Package section is NOT correct");
      }

      // Todo: Commented due to Peet's change on staging[Talked w/Shilpa and Bernadette]
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
          Constants.WebTestData.EMAIL, acceptancePage.getCustomerMail(), "Mail does NOT matches");

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
      description = "11071613")
  public void freeShippingByMultipleCoffeeOrderAsGuestUserTest() {

    logger.info("1. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDPFromHome(coffeeSelected);
    Assert.assertNotNull(productDetailsPage, "Failed to navigate to Product Details Page");

    String productName = productDetailsPage.getProductName();
    String grind = productDetailsPage.getGrindSelected();
    int productQuantity = 5;
    int coffeeIndex = 0;

    logger.info("2. Add first item to MiniCart");
    productDetailsPage.addMoreProducts(productQuantity);
    int expectedQuantity = productDetailsPage.getProductQuantityFromBox();
    Assert.assertEquals(
        productQuantity, expectedQuantity, "Quantity of products was not increased correctly");

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

    logger.info("3. Proceed to Checkout page");
    CheckOutPage checkOutPage = miniCart.clickContinueToCheckOut();
    checkOutPage.setCheckOutData();

    logger.info("4. Proceed to Shipping page");
    ShippingPage shippingPage = checkOutPage.clickContinueToShipping();
    String shippingMethod = shippingPage.selectShippingMethodByIndex(0);

    logger.info("5. Proceed to Payments page");
    PaymentsPage paymentsPage = shippingPage.clickContinueToPayments();
    paymentsPage.setPaymentData();
    Assert.assertTrue(
        paymentsPage.isSameAddressSelected(), "Same Address option should be Selected");

    String totalPrice = paymentsPage.getTotalPrice();
    Float price = WebHelper.cleanPrice(totalPrice);
    Assert.assertTrue(
        price > 50, "Even after adding " + productQuantity + " products, price is not over $50");

    if (WebHelper.getTestEnvironment().equalsIgnoreCase("production")) {
      logger.info("Testcase needs to stop, running on prod");
    } else {
      logger.info("6. Proceed to Acceptance page");
      AcceptancePage acceptancePage = paymentsPage.clickPayNow();

      logger.info("7. Validating Product Details");
      Assert.assertTrue(acceptancePage.isOrderNumberDisplayed(), "Order number is NOT displayed");
      Assert.assertTrue(acceptancePage.isSubTotalDisplayed(), "SubTotal is NOT displayed");
      Assert.assertEquals(
          productName, acceptancePage.getOrderNameByIndex(0), "Product name does NOT matches");
      Assert.assertEquals(
          totalPrice, acceptancePage.getTotalPrice(), "Total price does NOT matches");
      Assert.assertEquals(
          acceptancePage.getShippingPrice(),
          "Free",
          "Free Shipping for ORDERS above $50USD was not set!");
      Assert.assertTrue(acceptancePage.isMapDisplayed(), "Map is not displayed");

      if (WebHelper.isDesktop()) {
        // Todo: Only for desktop, until figure out if its a bug
        logger.info("8. Validating Download buttons");
        acceptancePage.clickOverTrackPackageButton();
        Assert.assertEquals(
            acceptancePage.getPhoneFromTrackPackageSection(),
            "+1 " + Constants.WebTestData.PHONE,
            "Phone from Track Package section is NOT correct");
      }

      // Todo: Commented due to Peet's change on staging[Talked w/Shilpa and Bernadette]
      //    acceptancePage.clickOverShippingUpdatesButton();
      //    Assert.assertEquals(
      //        acceptancePage.getPhoneFromShippingUpdatesSection(),
      //        "+1 " + Constants.WebTestData.PHONE,
      //        "Phone from Shipping Updates section is NOT correct");

      // Todo: Optional assertions or assertions missing:
      //  Submit button from trackPackage/shippingUpdates sections
      //  [user will receive a text validations]

      logger.info("9. Validating Customer Information");
      Assert.assertEquals(
          Constants.WebTestData.EMAIL, acceptancePage.getCustomerMail(), "Mail does NOT matches");

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
      description = "11071614")
  public void freeShippingByLimitedCoffeeOrderAsGuestUserTest() {

    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");
    homePage.closeInitialBannersAndModals();

    logger.info("2. Select Limited Releases From Coffee tab");
    Header header = homePage.getHeader();
    header.hoverCategoryFromMenu(Constants.MenuOptions.COFFEE);
    ProductListPage productListPage = navigateToPLP();

    logger.info("3. Add Limited Release item to MiniCart");
    int limitedCoffeeSelected = 3;
    ProductDetailsPage productDetailsPage =
        productListPage.clickOverProductByIndex(limitedCoffeeSelected);

    String limitedCoffeeName = productDetailsPage.getProductName();
    int teaQuantity = productDetailsPage.getProductQuantitySelected();

    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("4. Select Coffee from Coffee tab");
    productDetailsPage = miniCart.closeMiniCart(ProductDetailsPage.class);
    header = productDetailsPage.getHeader();

    header.hoverCategoryFromMenu(Constants.MenuOptions.COFFEE);
    productListPage =
        header.clickOverSubCategoryFromMenu(
            ProductListPage.class, Constants.MenuSubCategories.COFFEE_BEST_SELLERS);

    logger.info("5. Add Coffee item to MiniCart");
    int coffeeSelected = 1;
    productDetailsPage = productListPage.clickOverProductByIndex(coffeeSelected);

    String cupsName = productDetailsPage.getProductName();
    int coffeeQuantity = 2;

    productDetailsPage.addMoreProducts(coffeeQuantity);
    int expectedCupsQuantity = productDetailsPage.getProductQuantitySelected();
    Assert.assertEquals(
        coffeeQuantity, expectedCupsQuantity, "Quantity of products was not increased correctly");

    miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("6. Validate items added to Cart");
    int limitedCoffeeIndex = 1;
    int cupsIndex = 0;

    Assert.assertEquals(
        limitedCoffeeName,
        miniCart.getProductNameByIndex(limitedCoffeeIndex),
        "Correct Limited Coffee Product was not added to MiniCart");

    Assert.assertEquals(
        teaQuantity,
        miniCart.getProductQuantityByIndex(limitedCoffeeIndex),
        "Correct Limited Coffee Product quantity was not added to MiniCart");

    Assert.assertEquals(
        cupsName,
        miniCart.getProductNameByIndex(cupsIndex),
        "Correct Product was not added to MiniCart");
    Assert.assertEquals(
        coffeeQuantity,
        miniCart.getProductQuantityByIndex(cupsIndex),
        "Correct product quantity was not added to MiniCart");

    logger.info("7. Proceed to Checkout page");
    CheckOutPage checkOutPage = miniCart.clickContinueToCheckOut();
    checkOutPage.setCheckOutData();

    logger.info("8. Proceed to Shipping page");
    ShippingPage shippingPage = checkOutPage.clickContinueToShipping();
    String shippingMethod = shippingPage.selectShippingMethodByIndex(0);

    logger.info("9. Proceed to Payments page");
    PaymentsPage paymentsPage = shippingPage.clickContinueToPayments();
    paymentsPage.setPaymentData();

    String totalPrice = paymentsPage.getTotalPrice();
    Float price = WebHelper.cleanPrice(totalPrice);
    Assert.assertTrue(
        price > 29, "Even after adding limited release coffee, price is not over $29");

    if (WebHelper.getTestEnvironment().equalsIgnoreCase("production")) {
      logger.info("Testcase needs to stop, running on prod");
    } else {
      logger.info("10. Proceed to Acceptance page");
      AcceptancePage acceptancePage = paymentsPage.clickPayNow();

      logger.info("11. Validating Product Details");
      Assert.assertTrue(acceptancePage.isOrderNumberDisplayed(), "Order number is NOT displayed");
      Assert.assertTrue(acceptancePage.isSubTotalDisplayed(), "SubTotal is NOT displayed");
      Assert.assertEquals(
          limitedCoffeeName,
          acceptancePage.getOrderNameByIndex(limitedCoffeeIndex),
          "Limited Coffee name does NOT matches");

      Assert.assertEquals(
          cupsName, acceptancePage.getOrderNameByIndex(cupsIndex), "Product name does NOT matches");
      Assert.assertEquals(
          totalPrice, acceptancePage.getTotalPrice(), "Total price does NOT matches");
      Assert.assertEquals(
          acceptancePage.getShippingPrice(),
          "Free",
          "Free Shipping for ORDERS above $29USD[Limited Releases] was not set!");
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

      // Todo: Commented due to Peet's change on staging[Talked w/Shilpa and Bernadette]
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
          Constants.WebTestData.EMAIL, acceptancePage.getCustomerMail(), "Mail does NOT matches");

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
