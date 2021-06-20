package com.applause.auto.test.new_web;

import com.applause.auto.common.data.Constants;
import com.applause.auto.new_web.components.Header;
import com.applause.auto.new_web.components.MiniCart;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.new_web.views.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class StandardOrdersTest extends BaseTest {

  @Test(
      groups = {Constants.TestNGGroups.NEW_WEB_CASES},
      description = "11052681")
  public void orderCoffeeCreditCardAsGuestUserTest() {

    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");

    logger.info("2. Select Best Sellers from Coffee tab");
    Header header = homePage.getHeader();
    header.hoverCategoryFromMenu(Constants.MenuOptions.COFFEE);
    ProductListPage productListPage =
        header.clickOverSubCategoryFromMenu(
            ProductListPage.class, Constants.MenuSubCategories.COFFEE_BEST_SELLERS);

    logger.info("3. Add first item to MiniCart");
    int productSelected = 3;
    ProductDetailsPage productDetailsPage =
        productListPage.clickOverProductByIndex(productSelected);

    String productName = productDetailsPage.getProductName();
    String grind = productDetailsPage.getGrindSelected();
    int productQuantity = productDetailsPage.getProductQuantity();

    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();
    Assert.assertEquals(
        productName, miniCart.getProductName(), "Correct Product was not added to MiniCart");
    Assert.assertEquals(
        grind, miniCart.getGrindSelected(), "Correct Grind was not added to MiniCart");
    Assert.assertEquals(
        productQuantity,
        miniCart.getProductQuantity(),
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

    String totalPrice = paymentsPage.getTotalPrice();

    logger.info("6. Proceed to Acceptance page");
    AcceptancePage acceptancePage = paymentsPage.clickContinueToPayments();

    logger.info("7. Validating Product Details");
    Assert.assertTrue(acceptancePage.isOrderNumberDisplayed(), "Order number is NOT displayed");
    Assert.assertTrue(acceptancePage.isSubTotalDisplayed(), "SubTotal is NOT displayed");
    Assert.assertEquals(
        productName, acceptancePage.getOrderNameByIndex(0), "Product name does NOT matches");
    Assert.assertEquals(totalPrice, acceptancePage.getTotalPrice(), "Total price does NOT matches");

    // Todo: Optional assertions or assertions missing:
    //  Discount if any
    //  Map Location section

    if (WebHelper.isDesktop()) {
      // Todo: Only for desktop, until figure out if its a bug
      logger.info("8. Validating Download buttons");
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

  @Test(
      groups = {Constants.TestNGGroups.NEW_WEB_CASES},
      description = "11052683")
  public void oneCoffeeOneEquipmentCreditCardAsGuestUserTest() {

    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");

    logger.info("2. Select Best Sellers from Coffee tab");
    Header header = homePage.getHeader();
    header.hoverCategoryFromMenu(Constants.MenuOptions.COFFEE);
    ProductListPage productListPage =
        header.clickOverSubCategoryFromMenu(
            ProductListPage.class, Constants.MenuSubCategories.COFFEE_BEST_SELLERS);

    logger.info("3. Add coffee item to MiniCart");
    int coffeeSelected = 3;
    ProductDetailsPage productDetailsPage = productListPage.clickOverProductByIndex(coffeeSelected);

    String coffeeName = productDetailsPage.getProductName();
    String coffeeGrind = productDetailsPage.getGrindSelected();
    int coffeeQuantity = productDetailsPage.getProductQuantity();

    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("4. Select Equipment from Gear tab");
    productDetailsPage = miniCart.closeMiniCart(ProductDetailsPage.class);
    header = productDetailsPage.getHeader();

    productListPage = header.clickCategoryFromMenu(Constants.MenuOptions.GEAR);

    logger.info("5. Add equipment item to MiniCart");
    int equipmentSelected = 4;
    productDetailsPage = productListPage.clickOverProductByIndex(equipmentSelected);

    String equipmentName = productDetailsPage.getProductName();
    int equipmentQuantity = productDetailsPage.getProductQuantity();

    CartPage cartPage = productDetailsPage.clickAddToCartPage();

    logger.info("6. Validate items added to Cart");
    int coffeeIndex = 1;
    int equipmentIndex = 0;

    Assert.assertEquals(
        coffeeName,
        cartPage.getProductNameByIndex(coffeeIndex),
        "Correct Coffee Product was not added to MiniCart");
    Assert.assertEquals(
        coffeeGrind,
        cartPage.getGrindSelectedByIndex(coffeeIndex),
        "Correct Coffee Grind was not added to MiniCart");
    Assert.assertEquals(
        coffeeQuantity,
        cartPage.getProductQuantityByIndex(coffeeIndex),
        "Correct Coffee product quantity was not added to MiniCart");

    Assert.assertEquals(
        equipmentName,
        cartPage.getProductNameByIndex(equipmentIndex),
        "Correct Equipment Product was not added to MiniCart");
    Assert.assertEquals(
        equipmentQuantity,
        cartPage.getProductQuantityByIndex(equipmentIndex),
        "Correct Equipment product quantity was not added to MiniCart");

    logger.info("4. Proceed to Checkout page");
    CheckOutPage checkOutPage = cartPage.clickContinueToCheckOut();
    checkOutPage.setCheckOutData();

    logger.info("5. Proceed to Shipping page");
    ShippingPage shippingPage = checkOutPage.clickContinueToShipping();
    String shippingMethod = shippingPage.selectShippingMethodByIndex(0);

    logger.info("6. Proceed to Payments page");
    PaymentsPage paymentsPage = shippingPage.clickContinueToPayments();
    // Todo:Peet's Card is not working, always [11/06.2021]
    //    paymentsPage.setPeetsCardDiscount();
    paymentsPage.setPaymentData();

    String totalPrice = paymentsPage.getTotalPrice();

    logger.info("6. Proceed to Acceptance page");
    AcceptancePage acceptancePage = paymentsPage.clickContinueToPayments();

    logger.info("7. Validating Product Details");
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
    Assert.assertEquals(totalPrice, acceptancePage.getTotalPrice(), "Total price does NOT matches");

    // Todo: Optional assertions or assertions missing:
    //  Discount if any
    //  Map Location section

    if (WebHelper.isDesktop()) {
      // Todo: Only for desktop, until figure out if its a bug
      logger.info("8. Validating Download buttons");
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

  @Test(
      groups = {Constants.TestNGGroups.NEW_WEB_CASES},
      description = "11052682")
  public void oneRegularCoffeeOneReserveCoffeePeetsCardAsGuestUserTest() {

    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");

    logger.info("2. Select Best Sellers from Coffee tab");
    Header header = homePage.getHeader();
    header.hoverCategoryFromMenu(Constants.MenuOptions.COFFEE);
    ProductListPage productListPage =
        header.clickOverSubCategoryFromMenu(
            ProductListPage.class, Constants.MenuSubCategories.COFFEE_BEST_SELLERS);

    logger.info("3. Add coffee item to MiniCart");
    int coffeeSelected = 3;
    ProductDetailsPage productDetailsPage = productListPage.clickOverProductByIndex(coffeeSelected);

    String coffeeName = productDetailsPage.getProductName();
    String coffeeGrind = productDetailsPage.getGrindSelected();
    int coffeeQuantity = productDetailsPage.getProductQuantity();

    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("4. Select Coffee Beans from Coffee tab");
    productDetailsPage = miniCart.closeMiniCart(ProductDetailsPage.class);
    header = productDetailsPage.getHeader();

    header.hoverCategoryFromMenu(Constants.MenuOptions.COFFEE);
    productListPage =
        header.clickOverSubCategoryFromMenu(
            ProductListPage.class, Constants.MenuSubCategories.COFFEE_BEANS);

    logger.info("5. Add equipment item to MiniCart");
    int equipmentSelected = 4;
    productDetailsPage = productListPage.clickOverProductByIndex(equipmentSelected);

    String equipmentName = productDetailsPage.getProductName();
    int equipmentQuantity = productDetailsPage.getProductQuantity();

    CartPage cartPage = productDetailsPage.clickAddToCartPage();

    logger.info("6. Validate items added to Cart");
    int coffeeIndex = 1;
    int equipmentIndex = 0;

    Assert.assertEquals(
        coffeeName,
        cartPage.getProductNameByIndex(coffeeIndex),
        "Correct Coffee Product was not added to MiniCart");
    Assert.assertEquals(
        coffeeGrind,
        cartPage.getGrindSelectedByIndex(coffeeIndex),
        "Correct Coffee Grind was not added to MiniCart");
    Assert.assertEquals(
        coffeeQuantity,
        cartPage.getProductQuantityByIndex(coffeeIndex),
        "Correct Coffee product quantity was not added to MiniCart");

    Assert.assertEquals(
        equipmentName,
        cartPage.getProductNameByIndex(equipmentIndex),
        "Correct Equipment Product was not added to MiniCart");
    Assert.assertEquals(
        equipmentQuantity,
        cartPage.getProductQuantityByIndex(equipmentIndex),
        "Correct Equipment product quantity was not added to MiniCart");

    logger.info("4. Proceed to Checkout page");
    CheckOutPage checkOutPage = cartPage.clickContinueToCheckOut();
    checkOutPage.setCheckOutData();

    logger.info("5. Proceed to Shipping page");
    ShippingPage shippingPage = checkOutPage.clickContinueToShipping();
    String shippingMethod = shippingPage.selectShippingMethodByIndex(0);

    logger.info("6. Proceed to Payments page");
    PaymentsPage paymentsPage = shippingPage.clickContinueToPayments();
    // Todo:Peet's Card is not working, always [11/06.2021]
    //    paymentsPage.setPeetsCardDiscount();
    paymentsPage.setPaymentData();

    String totalPrice = paymentsPage.getTotalPrice();

    logger.info("6. Proceed to Acceptance page");
    AcceptancePage acceptancePage = paymentsPage.clickContinueToPayments();

    logger.info("7. Validating Product Details");
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
    Assert.assertEquals(totalPrice, acceptancePage.getTotalPrice(), "Total price does NOT matches");

    // Todo: Optional assertions or assertions missing:
    //  Discount if any
    //  Map Location section

    if (WebHelper.isDesktop()) {
      // Todo: Only for desktop, until figure out if its a bug
      logger.info("8. Validating Download buttons");
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
