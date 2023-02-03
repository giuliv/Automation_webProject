package com.applause.auto.test.web.ppd;

import com.applause.auto.common.data.Constants.MyAccountLeftMenuOption;
import com.applause.auto.common.data.Constants.TestData;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.common.data.enums.GrindDropdown;
import com.applause.auto.test.web.BaseTest;
import com.applause.auto.test.web.my_account.MyAccountTestsHelper;
import com.applause.auto.web.helpers.WebHelper;
import com.applause.auto.web.views.CheckOutPage;
import com.applause.auto.web.views.HomePage;
import com.applause.auto.web.views.ProductDetailsPage;
import com.applause.auto.web.views.my_account.MyAccountPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PpdTests extends BaseTest {

  @Test(
      groups = {
        TestNGGroups.PPD,
        TestNGGroups.FRONT_END_REGRESSION,
        TestNGGroups.FE_PROD_REGRESSION
      },
      description = "11124758")
  public void plpAndPdpPageValidationsTest() {
    logger.info("1. Navigate to Home");
    HomePage homePage = navigateToHome();
    homePage.closeInitialBannersAndModals();

    PpdTestHelper.plpAndPdpPageValidationsTest(
        homePage,
        TestData.PRODUCT_LUMINOSA_BREAKFAST_BLEND_GIFT_SUBSCRIPTION,
        TestData.PRODUCT_LUMINOSA_BREAKFAST_BLEND_GIFT_SUBSCRIPTION_NAME,
        true);
  }

  @Test(
      groups = {
        TestNGGroups.PPD,
        TestNGGroups.FRONT_END_REGRESSION,
        TestNGGroups.FE_PROD_REGRESSION
      },
      description = "11124759")
  public void checkoutAndOrderValidationTest() {
    logger.info("1. Navigate to PDP");
    ProductDetailsPage productDetailsPage =
        navigateToPDPFromHome(TestData.PRODUCT_LUMINOSA_BREAKFAST_BLEND_GIFT_SUBSCRIPTION);

    PpdTestHelper.checkoutAndOrderValidationGiftSubscriptionPpd(productDetailsPage, softAssert);

    String cartPageUrl = WebHelper.getCurrentUrl();
    productDetailsPage = navigateToPDPFromHome(coffeeSelected);

    PpdTestHelper.checkNoItemsOtherThanPrepaidCanBeAddedToCart(productDetailsPage, softAssert);

    logger.info("15. Go back to Homepage -> Search for PPD item ->Add prepay items to the cart");
    productDetailsPage =
        navigateToPDP(TestData.PRODUCT_LUMINOSA_BREAKFAST_BLEND_K_CUP_PODS_GIFT_SUBSCRIPTION);
    CheckOutPage checkOutPage = productDetailsPage.clickBuyNow();

    logger.info(
        "Verify if a prepay item is in the cart the customer should be able to add another prepay item");
    softAssert.assertTrue(checkOutPage.isDisplayed(), "User was not able to add item to the cart");

    logger.info("16. Add any non prepaid item to cart if a prepaid item is in the cart");
    productDetailsPage = navigateToPDP(coffeeSelected);

    PpdTestHelper.checkSubscriptionItemCanBeRemoved(productDetailsPage, cartPageUrl, softAssert);
    softAssert.assertAll();
  }

  @Test(
      groups = {
        TestNGGroups.PPD,
        TestNGGroups.FRONT_END_REGRESSION,
        TestNGGroups.FE_PROD_REGRESSION
      },
      description = "11124761")
  public void plpAndPpdPageValidations() {
    logger.info("1. Navigate to PDP");
    HomePage homePage = navigateToHome();
    homePage.closeInitialBannersAndModals();

    PpdTestHelper.plpAndPdpPageValidationsTest(
        homePage,
        TestData.PRODUCT_LUMINOSA_BREAKFAST_BLEND_K_CUP_PODS_GIFT_SUBSCRIPTION,
        TestData.PRODUCT_LUMINOSA_BREAKFAST_BLEND_K_CUP_PODS_GIFT_SUBSCRIPTION_NAME,
        false);
  }

  @Test(
      groups = {
        TestNGGroups.PPD,
        TestNGGroups.FRONT_END_REGRESSION,
        TestNGGroups.FE_PROD_REGRESSION
      },
      description = "11124762")
  public void checkoutAndOrderValidationGiftSubscriptionPpdTest() {
    logger.info("1. Navigate to PDP");
    ProductDetailsPage productDetailsPage =
        navigateToPDPFromHome(
            TestData.PRODUCT_LUMINOSA_BREAKFAST_BLEND_K_CUP_PODS_GIFT_SUBSCRIPTION);
    PpdTestHelper.checkoutAndOrderValidationGiftSubscriptionPpd(productDetailsPage, softAssert);

    String cartPageUrl = WebHelper.getCurrentUrl();
    productDetailsPage = navigateToPDPFromHome(coffeeSelected);
    PpdTestHelper.checkNoItemsOtherThanPrepaidCanBeAddedToCart(productDetailsPage, softAssert);

    logger.info("15. Go back to Homepage -> Search for PPD item ->Add prepay items to the cart");
    productDetailsPage = navigateToPDP(TestData.PRODUCT_LUMINOSA_BREAKFAST_BLEND_GIFT_SUBSCRIPTION);
    CheckOutPage checkOutPage = productDetailsPage.clickBuyNow();

    logger.info(
        "Verify if a prepay item is in the cart the customer should be able to add another prepay item");
    softAssert.assertTrue(checkOutPage.isDisplayed(), "User was not able to add item to the cart");

    logger.info("16. Add any non prepaid item to cart if a prepaid item is in the cart");
    productDetailsPage = navigateToPDP(coffeeSelected);

    PpdTestHelper.checkSubscriptionItemCanBeRemoved(productDetailsPage, cartPageUrl, softAssert);
    softAssert.assertAll();
  }

  @Test(
      groups = {TestNGGroups.PPD, TestNGGroups.FRONT_END_REGRESSION},
      description = "11124775")
  public void prePaidSubscriptionMyAccountMySubscriptionValidationTest() {
    MyAccountPage myAccountPage =
        MyAccountTestsHelper.navigateToMyAccountPage(
            navigateToSignInPage(),
            TestData.USER_EMAIL_WITH_PREPAID_SUBSCRIPTIONS,
            TestData.USER_PASSWORD_WITH_PREPAID_SUBSCRIPTIONS);

    logger.info("4. Click in My subscription");
    int pageCoordinatesBefore = WebHelper.getPagePositionY();
    myAccountPage =
        myAccountPage.getLeftMenu().clickMenuOption(MyAccountLeftMenuOption.MY_SUBSCRIPTIONS);

    logger.info("Verify page is scrolled to My subscriptions in Dashboard");
    Assert.assertTrue(
        pageCoordinatesBefore < WebHelper.getPagePositionY(),
        "Page is not scrolled to My subscriptions in Dashboard");

    logger.info(
        "Verify list of subscriptions should display with Schedule Date, Send Now, Change Date, Skip Order");
    softAssert.assertTrue(
        myAccountPage.isSubscriptionScheduleDateDisplayed(),
        "Subscription Schedule Date isn't displayed");
    softAssert.assertTrue(
        myAccountPage.isSubscriptionSendNowButtonDisplayed(),
        "Subscription Send Now isn't displayed");
    softAssert.assertTrue(
        myAccountPage.isSubscriptionChangeDateDisplayed(),
        "Subscription Change Date isn't displayed");
    softAssert.assertTrue(
        myAccountPage.isSubscriptionSkipOrderDisplayed(),
        "Subscription Skip Order isn't displayed");

    logger.info("Verify Product name is shown correctly");
    softAssert.assertTrue(
        myAccountPage.isSubscriptionNameDisplayed(), "Subscription name isn't displayed");

    logger.info("Verify Grind is shown correctly");
    softAssert.assertEquals(
        myAccountPage.getMySubscriptionGrind(),
        GrindDropdown.WHOLE_BEAN.getValue(),
        "Grind value is wrong");

    logger.info(
        "Verify Sending dropdown shows qty of item but is disabled to update  for PPD items");
    softAssert.assertTrue(
        myAccountPage.isSubscriptionQuantityDisplayed(),
        "Subscription quantity dropdown isn't displayed");
    softAssert.assertFalse(
        myAccountPage.isSubscriptionQuantityEnabled(),
        "Subscription quantity dropdown isn't disabled");

    logger.info("Verify Every dropdown shows # of months but is disabled to update for PPD items");
    softAssert.assertTrue(
        myAccountPage.isSubscriptionFrequencyDisplayed(),
        "Subscription Every dropdown isn't displayed");
    softAssert.assertFalse(
        myAccountPage.isSubscriptionFrequencyEnabled(),
        "Subscription Every dropdown isn't disabled");

    logger.info("Verify Billing is shown correctly");
    softAssert.assertTrue(
        myAccountPage.isSubscriptionBillingSectionDisplayed(),
        "Subscription Billing section isn't displayed");

    logger.info("Verify Total amounts (it is $0 since its PPD subscription)");
    softAssert.assertEquals(
        myAccountPage.expandDetailsSectionOnMobile().getMySubscriptionTotalAmount(),
        "$0.00",
        "Total amount is not 0");

    logger.info("Verify on the no banner to 'ADD ITEM' to PPD");
    softAssert.assertTrue(myAccountPage.isAddItemButtonDisplayed(), "Add Item isn't displayed");

    logger.info("Verify Shipping is shown correctly");
    softAssert.assertTrue(
        myAccountPage.isSubscriptionShippingSectionDisplayed(),
        "Subscription Shipping section isn't displayed");

    logger.info("Verify click on 'EDIT', Shows Change shipping address window");
    softAssert.assertTrue(
        myAccountPage.isEditShippingAddressModalDisplayed(), "Edit Shipping modal isn't displayed");

    softAssert.assertAll();
  }

  @Test(
      groups = {TestNGGroups.PPD, TestNGGroups.FRONT_END_REGRESSION},
      description = "11124774")
  public void regularSubscriptionMyAccountMySubscriptionValidationTest() {
    MyAccountPage myAccountPage =
        MyAccountTestsHelper.navigateToMyAccountPage(
            navigateToSignInPage(), TestData.USER_EMAIL_WITH_SUBSCRIPTIONS, TestData.WEB_PASSWORD);

    logger.info("4. Click in My subscription");
    int pageCoordinatesBefore = WebHelper.getPagePositionY();
    myAccountPage =
        myAccountPage.getLeftMenu().clickMenuOption(MyAccountLeftMenuOption.MY_SUBSCRIPTIONS);

    logger.info("Verify page is scrolled to My subscriptions in Dashboard");
    Assert.assertTrue(
        pageCoordinatesBefore < WebHelper.getPagePositionY(),
        "Page is not scrolled to My subscriptions in Dashboard");

    logger.info(
        "Verify list of subscriptions should display with Schedule Date, Send Now, Change Date, Skip Order");
    softAssert.assertTrue(
        myAccountPage.isSubscriptionScheduleDateDisplayed(),
        "Subscription Schedule Date isn't displayed");
    softAssert.assertTrue(
        myAccountPage.isSubscriptionSendNowButtonDisplayed(),
        "Subscription Send Now isn't displayed");
    softAssert.assertTrue(
        myAccountPage.isSubscriptionChangeDateDisplayed(),
        "Subscription Change Date isn't displayed");
    softAssert.assertTrue(
        myAccountPage.isSubscriptionSkipOrderDisplayed(),
        "Subscription Skip Order isn't displayed");

    logger.info("Verify Product name is shown correctly");
    softAssert.assertTrue(
        myAccountPage.isSubscriptionNameDisplayed(), "Subscription name isn't displayed");

    logger.info("Verify Grind is shown correctly");
    softAssert.assertEquals(
        myAccountPage.getMySubscriptionGrind(),
        GrindDropdown.WHOLE_BEAN.getValue(),
        "Grind value is wrong");

    logger.info(
        "Verify Sending dropdown shows qty values from 1-20 and able to update to desired qty");
    softAssert.assertTrue(
        myAccountPage.isSubscriptionQuantityDisplayed(),
        "Subscription quantity dropdown isn't displayed");
    softAssert.assertFalse(
        myAccountPage.areSendingQuantityDropdownValuesDisplayed(),
        "Sending dropdown shows wrong quantity values");

    logger.info("Verify Every dropdown shows 1-8 weeks and able to update to desired no of weeks");
    softAssert.assertTrue(
        myAccountPage.isSubscriptionFrequencyDisplayed(),
        "Subscription Every dropdown isn't displayed");
    softAssert.assertFalse(
        myAccountPage.areSubscriptionFrequencyDropdownValuesDisplayed(),
        "Subscription Every dropdown doesn't show 1-8 weeks");

    logger.info("Verify Billing is shown correctly");
    softAssert.assertTrue(
        myAccountPage.isSubscriptionBillingSectionDisplayed(),
        "Subscription Billing section isn't displayed");

    logger.info("Verify Total amounts");
    softAssert.assertFalse(
        myAccountPage.expandDetailsSectionOnMobile().getMySubscriptionTotalAmount().isEmpty(),
        "Total amount is empty");

    logger.info(
        "Verify User can click on Cancel subscription -> once clicked -> user can cancel the subscription successfully");
    softAssert.assertTrue(
        myAccountPage.isCancelSubscriptionDisplayed(),
        "Cancel subscription button is not displayed");

    logger.info(
        "Verify on the banner, there is ADD ITEM CTA which direct user to https://peets-coffee-staging.myshopify.com/collections/all");
    softAssert.assertTrue(myAccountPage.isAddItemButtonDisplayed(), "Add Item isn't displayed");

    logger.info("Verify Shipping is shown correctly");
    softAssert.assertTrue(
        myAccountPage.isSubscriptionShippingSectionDisplayed(),
        "Subscription Shipping section isn't displayed");

    logger.info("Verify click on 'EDIT', Shows Change shipping address window");
    softAssert.assertTrue(
        myAccountPage.isEditShippingAddressModalDisplayed(), "Edit Shipping modal isn't displayed");

    softAssert.assertAll();
  }
}
