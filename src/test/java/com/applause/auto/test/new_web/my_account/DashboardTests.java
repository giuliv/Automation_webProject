package com.applause.auto.test.new_web.my_account;

import com.applause.auto.common.data.Constants.CheckoutUserTestData;
import com.applause.auto.common.data.Constants.DashboardTestData;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.common.data.dto.MyOrderDto;
import com.applause.auto.new_web.components.my_account.MyOrderItemComponent;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.new_web.views.AcceptancePage;
import com.applause.auto.new_web.views.CartPage;
import com.applause.auto.new_web.views.ProductDetailsPage;
import com.applause.auto.new_web.views.SignInPage;
import com.applause.auto.new_web.views.my_account.MyAccountPage;
import com.applause.auto.new_web.views.my_account.OrderHistoryPage;
import com.applause.auto.new_web.views.my_account.ReferralsPage;
import com.applause.auto.test.new_web.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DashboardTests extends BaseTest {

  @Test(
      groups = {TestNGGroups.NEW_WEB_CASES, TestNGGroups.DASHBOARD},
      description = "11102918")
  public void dashboardUIelementsTest() {
    logger.info("1. Navigate to Sign in page");
    SignInPage signInPage = navigateToSignInPage();
    Assert.assertNotNull(signInPage, "Failed to navigate to the Sign in page.");

    logger.info("2. Enter valid credentials.");
    signInPage.enterEmail(CheckoutUserTestData.USERNAME);
    signInPage.enterPassword(CheckoutUserTestData.PASSWORD);

    logger.info("3. Click on Sign in");
    MyAccountPage myAccountPage = signInPage.clickOnSignInButton();
    softAssert.assertNotNull(myAccountPage, "My account page isn't displayed");
    softAssert.assertTrue(
        myAccountPage.getWelcomeMessage().contains("welcome"),
        "User is not signed in or welcome name is wrong");

    logger.info("Verify table title");
    softAssert.assertEquals(
        myAccountPage.getRecentOrdersTitle().toLowerCase(),
        DashboardTestData.RECENT_ORDERS_HEADER.toLowerCase(),
        "Table title isn't correct");

    logger.info("4. Click in view all orders");
    OrderHistoryPage orderHistoryPage = myAccountPage.viewAllOrders();

    logger.info("Verify that order history should display");
    softAssert.assertTrue(orderHistoryPage.isDisplayed(), "Order history page didn't appear");

    logger.info("Verify that Order #, Date order, Order status and Total should display");
    if (WebHelper.isDesktop()) {
      softAssert.assertEquals(
          orderHistoryPage.getTableTitleList(),
          DashboardTestData.MY_ORDERS_TABLE_TITLES,
          "Table titles are not correct");
    }

    logger.info("Verify that each of orders are fully displayed");
    softAssert.assertTrue(
        orderHistoryPage.getMyOrderItemList().size() > 0, "My orders table is empty");
    softAssert.assertTrue(
        orderHistoryPage.isMyOrdersTableFullyDisplayed(), "My orders table is n't fully displayed");

    softAssert.assertAll();
  }

  @Test(
      groups = {TestNGGroups.NEW_WEB_CASES, TestNGGroups.DASHBOARD},
      description = "11102919")
  public void dashboardDetailsTest() {
    logger.info("1. Navigate to Sign in page");
    SignInPage signInPage = navigateToSignInPage();
    Assert.assertNotNull(signInPage, "Failed to navigate to the Sign in page.");

    logger.info("2. Enter valid credentials.");
    signInPage.enterEmail(CheckoutUserTestData.USERNAME);
    signInPage.enterPassword(CheckoutUserTestData.PASSWORD);

    logger.info("3. Click on Sign in");
    MyAccountPage myAccountPage = signInPage.clickOnSignInButton();
    softAssert.assertNotNull(myAccountPage, "My account page isn't displayed");
    softAssert.assertTrue(
        myAccountPage.getWelcomeMessage().contains("welcome"),
        "User is not signed in or welcome name is wrong");

    logger.info("4. Click on Details");
    MyOrderItemComponent myOrderItemComponent =
        myAccountPage.viewAllOrders().getMyOrderItemList().get(0);
    myOrderItemComponent = myOrderItemComponent.clickOnDetails();

    logger.info("Verify that Oder is expanded");
    softAssert.assertTrue(myOrderItemComponent.isExpanded(), "Order isn't expanded");

    logger.info(
        "Verify that details with product image, product name, grind, quantity, price and details button should display.");
    softAssert.assertTrue(myOrderItemComponent.isOrderImageDisplayed(), "Image isn't displayed");
    softAssert.assertTrue(myOrderItemComponent.isOrderNameDisplayed(), "Name isn't displayed");
    softAssert.assertTrue(myOrderItemComponent.isOrderInfoDisplayed(), "Info isn't displayed");
    softAssert.assertTrue(
        myOrderItemComponent.isOrderQuantityDisplayed(), "Quantity isn't displayed");
    softAssert.assertTrue(myOrderItemComponent.isOrderPriceDisplayed(), "Price isn't displayed");
    softAssert.assertTrue(
        myOrderItemComponent.isViewProductButtonDisplayed(),
        "'View Product' button isn't displayed");

    logger.info("5. Click on Details again");
    myOrderItemComponent = myOrderItemComponent.clickOnDetails();

    logger.info("Verify that Oder is collapsed");
    softAssert.assertFalse(myOrderItemComponent.isExpanded(), "Order isn't collapsed");

    softAssert.assertAll();
  }

  @Test(
      groups = {TestNGGroups.NEW_WEB_CASES, TestNGGroups.DASHBOARD},
      description = "11102920")
  public void dashboardViewProductTest() {
    logger.info("1. Navigate to Sign in page");
    SignInPage signInPage = navigateToSignInPage();
    Assert.assertNotNull(signInPage, "Failed to navigate to the Sign in page.");

    logger.info("2. Enter valid credentials.");
    signInPage.enterEmail(CheckoutUserTestData.USERNAME);
    signInPage.enterPassword(CheckoutUserTestData.PASSWORD);

    logger.info("3. Click on Sign in");
    MyAccountPage myAccountPage = signInPage.clickOnSignInButton();

    logger.info("4. Click on Details");
    MyOrderItemComponent myOrderItemComponent =
        myAccountPage.viewAllOrders().getMyOrderItemList().get(0);
    myOrderItemComponent = myOrderItemComponent.clickOnDetails();
    MyOrderDto orderDTO = myOrderItemComponent.getOrderDTO();

    logger.info("5. Click on view product");
    ProductDetailsPage productDetailsPage = myOrderItemComponent.clickOnViewProduct();

    logger.info("Verify that PDP displays and product name, image and price should match.");
    softAssert.assertEquals(
        productDetailsPage.getProductName().toLowerCase(),
        orderDTO.getName().toLowerCase(),
        "Product name didn't match");
    softAssert.assertEquals(
        productDetailsPage.getGrindSelected().toLowerCase(),
        orderDTO.getInfo().toLowerCase(),
        "Product Grind didn't match");
    softAssert.assertEquals(
        productDetailsPage.getProductPrice(), orderDTO.getPrice(), "Product Price didn't match");
    softAssert.assertEquals(
        productDetailsPage.getProductQuantitySelected(),
        orderDTO.getQuantity(),
        "Product quantity didn't match");

    softAssert.assertAll();
  }

  @Test(
      groups = {TestNGGroups.NEW_WEB_CASES, TestNGGroups.DASHBOARD},
      description = "11102921")
  public void dashboardOrderTrackingTest() {
    logger.info("1. Navigate to Sign in page");
    SignInPage signInPage = navigateToSignInPage();
    Assert.assertNotNull(signInPage, "Failed to navigate to the Sign in page.");

    logger.info("2. Enter valid credentials.");
    signInPage.enterEmail(CheckoutUserTestData.USERNAME);
    signInPage.enterPassword(CheckoutUserTestData.PASSWORD);

    logger.info("3. Click on Sign in");
    MyAccountPage myAccountPage = signInPage.clickOnSignInButton();

    logger.info("4. Click on Details");
    MyOrderItemComponent myOrderItemComponent =
        myAccountPage.viewAllOrders().getMyOrderItemList().get(0);
    myOrderItemComponent = myOrderItemComponent.clickOnDetails();
    MyOrderDto orderDTO = myOrderItemComponent.getOrderDTO();

    logger.info("5. Click on order tracking");
    AcceptancePage acceptancePage = myOrderItemComponent.clickOnOrderTrackingButton();

    logger.info(
        "Verify that your order details should display and product name, image and price should match.");
    softAssert.assertEquals(
        acceptancePage.getOrderNameByIndex(0).toLowerCase(),
        orderDTO.getName().toLowerCase(),
        "Product name didn't match on Confirmation page");
    softAssert.assertEquals(
        acceptancePage.getOrderGrind().toLowerCase(),
        orderDTO.getInfo().toLowerCase(),
        "Product Grind didn't match on Confirmation page");
    softAssert.assertEquals(
        acceptancePage.getTotalPrice(),
        orderDTO.getPrice(),
        "Product Price didn't match on Confirmation page");

    softAssert.assertAll();
  }

  @Test(
      groups = {TestNGGroups.NEW_WEB_CASES, TestNGGroups.DASHBOARD},
      description = "11102922")
  public void dashboardReorderTest() {
    logger.info("1. Navigate to Sign in page");
    SignInPage signInPage = navigateToSignInPage();
    Assert.assertNotNull(signInPage, "Failed to navigate to the Sign in page.");

    logger.info("2. Enter valid credentials.");
    signInPage.enterEmail(CheckoutUserTestData.USERNAME);
    signInPage.enterPassword(CheckoutUserTestData.PASSWORD);

    logger.info("3. Click on Sign in");
    MyAccountPage myAccountPage = signInPage.clickOnSignInButton();

    logger.info("4. Click on Details");
    MyOrderItemComponent myOrderItemComponent =
        myAccountPage.viewAllOrders().getMyOrderItemList().get(0);
    myOrderItemComponent = myOrderItemComponent.clickOnDetails();
    MyOrderDto orderDTO = myOrderItemComponent.getOrderDTO();

    logger.info("5. Click on reorder");
    CartPage cartPage = myOrderItemComponent.clickOnReorderButton();

    logger.info(
        "Verify that cart page should display and product name, image and price should match.");
    softAssert.assertEquals(
        cartPage.getProductNameByIndex(1).toLowerCase(),
        orderDTO.getName().toLowerCase(),
        "Product name didn't match on Cart page");
    softAssert.assertEquals(
        cartPage.getGrindSelectedByIndex(1).toLowerCase(),
        orderDTO.getInfo().toLowerCase(),
        "Product Grind didn't match on Cart page");
    softAssert.assertEquals(
        cartPage.getProductPriceByIndex(1),
        orderDTO.getPrice(),
        "Product Price didn't match on Cart page");

    softAssert.assertAll();
  }

  @Test(
      groups = {TestNGGroups.NEW_WEB_CASES, TestNGGroups.DASHBOARD},
      description = "11102923")
  public void dashboardStartSharingTest() {
    logger.info("1. Navigate to Sign in page");
    SignInPage signInPage = navigateToSignInPage();
    Assert.assertNotNull(signInPage, "Failed to navigate to the Sign in page.");

    logger.info("2. Enter valid credentials.");
    signInPage.enterEmail(CheckoutUserTestData.USERNAME);
    signInPage.enterPassword(CheckoutUserTestData.PASSWORD);

    logger.info("3. Click on Sign in");
    MyAccountPage myAccountPage = signInPage.clickOnSignInButton();

    logger.info("4. Scroll down and click on Start sharing");
    ReferralsPage referralsPage = myAccountPage.clickOnStartSharingButton();

    logger.info("Verify that Referrals page displays.");
    Assert.assertTrue(referralsPage.isDisplayed(), "Referrals page isn't displayed");
  }
}
