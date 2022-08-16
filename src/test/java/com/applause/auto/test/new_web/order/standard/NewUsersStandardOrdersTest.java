package com.applause.auto.test.new_web.order.standard;

import com.applause.auto.common.data.Constants;
import com.applause.auto.new_web.components.MiniCart;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.new_web.views.AcceptancePage;
import com.applause.auto.new_web.views.CheckOutPage;
import com.applause.auto.new_web.views.HomePage;
import com.applause.auto.new_web.views.PaymentsPage;
import com.applause.auto.new_web.views.ProductDetailsPage;
import com.applause.auto.new_web.views.ShippingPage;
import com.applause.auto.new_web.views.my_account.MyAccountPage;
import com.applause.auto.test.new_web.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NewUsersStandardOrdersTest extends BaseTest {
  // Todo: Optional assertions not added:
  //  Discount if any

  @Test(
      groups = {Constants.TestNGGroups.WEB_REGRESSION, Constants.TestNGGroups.CHECKOUT},
      description = "11071610")
  public void orderCoffeeCreditCardAsNewUserTest() {

    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");

    logger.info("2. Create new User account");
    String mail = WebHelper.getRandomMail();
    MyAccountPage myAccountPage = testHelper.createAccount(homePage, mail);
    Assert.assertNotNull(myAccountPage, "Account was not created!");

    logger.info("3. Navigate to product details page");
    ProductDetailsPage productDetailsPage = navigateToPDP(coffeeSelected);
    Assert.assertNotNull(productDetailsPage, "Failed to navigate to Product Details Page");

    String productName = productDetailsPage.getProductName();
    String grind = productDetailsPage.getGrindSelected();
    int productQuantity = productDetailsPage.getProductQuantitySelected();
    int coffeeIndex = 0;

    logger.info("4. Add item to MiniCart");
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

    String totalPrice = paymentsPage.getTotalPrice();

    logger.info("8. Proceed to Acceptance page");
    AcceptancePage acceptancePage = paymentsPage.clickContinueToPayments();

    logger.info("9. Validating Product Details");
    Assert.assertTrue(acceptancePage.isOrderNumberDisplayed(), "Order number is NOT displayed");
    Assert.assertTrue(acceptancePage.isSubTotalDisplayed(), "SubTotal is NOT displayed");
    Assert.assertEquals(
        productName, acceptancePage.getOrderNameByIndex(0), "Product name does NOT matches");
    Assert.assertEquals(totalPrice, acceptancePage.getTotalPrice(), "Total price does NOT matches");
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

    // Todo: Commented on 15.07.2022 due to Peet's change on staging[Talked w/Shilpa and Bernadette]
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
