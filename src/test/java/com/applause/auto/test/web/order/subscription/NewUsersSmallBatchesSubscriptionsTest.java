package com.applause.auto.test.web.order.subscription;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.enums.SubscriptionType;
import com.applause.auto.web.components.MiniCart;
import com.applause.auto.web.helpers.WebHelper;
import com.applause.auto.web.views.AcceptancePage;
import com.applause.auto.web.views.CheckOutPage;
import com.applause.auto.web.views.HomePage;
import com.applause.auto.web.views.PaymentsPage;
import com.applause.auto.web.views.ProductDetailsPage;
import com.applause.auto.web.views.ShippingPage;
import com.applause.auto.web.views.my_account.MyAccountPage;
import com.applause.auto.test.web.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NewUsersSmallBatchesSubscriptionsTest extends BaseTest {
  // Todo: Optional assertions not added:
  //  Discount if any

  @Test(
      groups = {Constants.TestNGGroups.TO_BE_RENAMED},
      description = "11070177")
  public void smallBatchDiscountAt5forNewUserTest() {

    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");
    homePage.closeInitialBannersAndModals();

    logger.info("2. Create new User account");
    String mail = WebHelper.getRandomMail();
    MyAccountPage myAccountPage = testHelper.createAccount(homePage, mail);
    Assert.assertNotNull(myAccountPage, "Account was not created!");

    //    logger.info("3. Select Small Batches From Subscription tab");
    //    Header header = homePage.getHeader();
    //    header.hoverCategoryFromMenu(Constants.MenuOptions.SUBSCRIPTION);
    //    ProductDetailsPage productDetailsPage =
    //        header.clickOverSubCategoryFromMenu(
    //            ProductDetailsPage.class,
    // Constants.MenuSubCategories.SUBSCRIPTIONS_SMALL_BATCHES);
    ProductDetailsPage productDetailsPage =
        navigateToSubscriptionPage(SubscriptionType.SMALL_BATCHES);

    logger.info("4. Add subscription to MiniCart");
    String productName = productDetailsPage.getProductName();
    String grind = productDetailsPage.getGrindSelected();
    int batchIndex = 0;
    int productQuantity = 2;
    productDetailsPage.addMoreProducts(productQuantity);
    int expectedQuantity = productDetailsPage.getProductQuantitySelected();
    Assert.assertEquals(
        productQuantity, expectedQuantity, "Quantity of products was not increased correctly");

    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();
    Assert.assertEquals(
        productName,
        miniCart.getProductNameByIndex(batchIndex),
        "Correct Product was not added to MiniCart");
    Assert.assertEquals(
        grind, miniCart.getGrindByIndex(batchIndex), "Correct Grind was not added to MiniCart");
    Assert.assertEquals(
        productQuantity,
        miniCart.getProductQuantityByIndex(batchIndex),
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
        paymentsPage.getDiscountMessage().contains("5% OFF"), "5% OFF message is not displayed");

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
          acceptancePage.getDiscountMessage().contains("5% OFF"),
          "5% OFF message is not displayed");
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
      description = "11071659")
  public void smallBatchDiscountAt10forNewUserTest() {

    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");
    homePage.closeInitialBannersAndModals();

    logger.info("2. Create new User account");
    String mail = WebHelper.getRandomMail();
    MyAccountPage myAccountPage = testHelper.createAccount(homePage, mail);
    Assert.assertNotNull(myAccountPage, "Account was not created!");

    logger.info("3. Select Small Batches From Subscription tab");
    //    Header header = homePage.getHeader();
    //    header.hoverCategoryFromMenu(Constants.MenuOptions.SUBSCRIPTION);
    //    ProductDetailsPage productDetailsPage =
    //        header.clickOverSubCategoryFromMenu(
    //            ProductDetailsPage.class,
    // Constants.MenuSubCategories.SUBSCRIPTIONS_SMALL_BATCHES);
    ProductDetailsPage productDetailsPage =
        navigateToSubscriptionPage(SubscriptionType.SMALL_BATCHES);

    logger.info("4. Add subscription to MiniCart");
    String productName = productDetailsPage.getProductName();
    String grind = productDetailsPage.getGrindSelected();
    int batchIndex = 0;
    int productQuantity = 3;
    productDetailsPage.addMoreProducts(productQuantity);
    int expectedQuantity = productDetailsPage.getProductQuantitySelected();
    Assert.assertEquals(
        productQuantity, expectedQuantity, "Quantity of products was not increased correctly");

    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();
    Assert.assertEquals(
        productName,
        miniCart.getProductNameByIndex(batchIndex),
        "Correct Product was not added to MiniCart");
    Assert.assertEquals(
        grind, miniCart.getGrindByIndex(batchIndex), "Correct Grind was not added to MiniCart");
    Assert.assertEquals(
        productQuantity,
        miniCart.getProductQuantityByIndex(batchIndex),
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
}
