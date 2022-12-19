package com.applause.auto.test.new_web.ppd;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.Constants.TestData;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.common.data.Constants.WebTestData;
import com.applause.auto.new_web.views.CartPage;
import com.applause.auto.new_web.views.CheckOutPage;
import com.applause.auto.new_web.views.HomePage;
import com.applause.auto.new_web.views.PaymentsPage;
import com.applause.auto.new_web.views.ProductDetailsPage;
import com.applause.auto.new_web.views.SignInPage;
import com.applause.auto.test.new_web.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NegativePPDTests extends BaseTest {

  @Test(
      groups = {TestNGGroups.PPD, Constants.TestNGGroups.FRONT_END_REGRESSION},
      description = "11124763")
  public void onlyCcPaymentMethodTest() {
    logger.info("1. Add a PPD subscription item");
    CheckOutPage checkOutPage =
        navigateToPDPFromHome(TestData.PRODUCT_LUMINOSA_BREAKFAST_BLEND_GIFT_SUBSCRIPTION)
            .clickBuyNow();
    checkOutPage.enterRecipientInformation(Constants.WebTestData.FIRST_NAME, WebTestData.EMAIL);
    checkOutPage.setCheckOutData();
    PaymentsPage paymentsPage = checkOutPage.clickContinueToShipping().clickContinueToPayments();

    logger.info(
        "Verify a user will only be able to checkout with CC. PayPal (Subscriptions) is disabled.");
    softAssert.assertTrue(
        paymentsPage.isCreditCardMethodDisplayed(), "CC payment method isn't displayed");
    softAssert.assertFalse(
        paymentsPage.isPayPalMethodDisplayed(), "CC payment method is unexpected displayed");
    softAssert.assertAll();
  }

  @Test(
      groups = {TestNGGroups.PPD, Constants.TestNGGroups.FRONT_END_REGRESSION},
      description = "11124764")
  public void notAutoPopulateRecipientDetailsTest() {
    CheckOutPage checkOutPage =
        navigateToPPDProductAndLogin(TestData.PRODUCT_LUMINOSA_BREAKFAST_BLEND_GIFT_SUBSCRIPTION);

    logger.info("Verify not autopopulate any of the recipient details");
    checkOutPage.validateRecipientFieldsAreNotPopulated().assertAll();
  }

  @Test(
      groups = {TestNGGroups.PPD, Constants.TestNGGroups.FRONT_END_REGRESSION},
      description = "11124765")
  public void alwaysGoToInformationStepTest() {
    CheckOutPage checkOutPage =
        navigateToPPDProductAndLogin(TestData.PRODUCT_LUMINOSA_BREAKFAST_BLEND_GIFT_SUBSCRIPTION);

    logger.info("Verify the information step as the recipient details section is displayed");
    Assert.assertTrue(
        checkOutPage.isContactInformationSectionDisplayed(),
        "The information step as the recipient details section is not displayed");
  }

  @Test(
      groups = {TestNGGroups.PPD, Constants.TestNGGroups.FRONT_END_REGRESSION},
      description = "11124766")
  public void requireRecipientNameAndEmailStepTest() {
    logger.info("1. Navigate to the Checkout page");
    CheckOutPage checkOutPage =
        navigateToPDPFromHome(TestData.PRODUCT_LUMINOSA_BREAKFAST_BLEND_GIFT_SUBSCRIPTION)
            .clickBuyNow();

    logger.info("Verify the information step as the recipient details section is displayed");
    checkOutPage.setCheckOutData();
    Assert.assertFalse(
        checkOutPage.isContinueToShippingButtonEnable(),
        "Continue to Shipping button is unexpected enabled without Recipient Information");

    logger.info("2. Fill in Recipient Information with not valid email");
    checkOutPage.enterRecipientInformation(
        WebTestData.FIRST_NAME, Constants.WebTestData.INVALID_EMAIL);

    logger.info("Verify Continue to Shipping button is not enabled");
    Assert.assertFalse(
        checkOutPage.isContinueToShippingButtonEnable(),
        "Continue to Shipping button is unexpected enabled with invalid email");

    logger.info("3. Fill in Recipient Information with valid email");
    checkOutPage.enterRecipientInformation(WebTestData.FIRST_NAME, WebTestData.EMAIL);

    logger.info("Verify Continue to Shipping button is enabled");
    Assert.assertTrue(
        checkOutPage.isContinueToShippingButtonEnable(),
        "Continue to Shipping button is disabled with valid information");
  }

  @Test(
      groups = {TestNGGroups.PPD, Constants.TestNGGroups.FRONT_END_REGRESSION},
      description = "11124767")
  public void personalizedMessageIsOptionalTest() {
    logger.info("1. Navigate to the Checkout page");
    CheckOutPage checkOutPage =
        navigateToPDPFromHome(TestData.PRODUCT_LUMINOSA_BREAKFAST_BLEND_GIFT_SUBSCRIPTION)
            .clickBuyNow();

    logger.info("Verify the message field is optional and does not need to be entered");
    checkOutPage.setCheckOutData();
    checkOutPage.enterRecipientInformation(WebTestData.FIRST_NAME, WebTestData.EMAIL);
    softAssert.assertTrue(
        checkOutPage.isContinueToShippingButtonEnable(),
        "The message field is unexpected required");

    logger.info("2. Enter the message field");
    checkOutPage.enterRecipientMessage(WebTestData.PERSONALIZED_MESSAGE);

    logger.info("Verify the message field is not required");
    softAssert.assertTrue(
        checkOutPage.isContinueToShippingButtonEnable(),
        "The message field is unexpected required");
    softAssert.assertAll();
  }

  @Test(
      groups = {TestNGGroups.PPD, Constants.TestNGGroups.FRONT_END_REGRESSION},
      description = "11124768")
  public void noTieredDiscountAppliedTest() {
    logger.info("1. Add two PPD subscription items");
    navigateToPDPFromHome(TestData.PRODUCT_LUMINOSA_BREAKFAST_BLEND_GIFT_SUBSCRIPTION)
        .clickBuyNow();
    CheckOutPage checkOutPage =
        navigateToPDP(TestData.PRODUCT_LUMINOSA_BREAKFAST_BLEND_K_CUP_PODS_GIFT_SUBSCRIPTION)
            .clickBuyNow();

    logger.info("Verify never apply the tiered discount % to a prepaid subscription");
    Assert.assertFalse(checkOutPage.isDiscountDisplayed(), "Discount is unexpected displayed");
  }

  @Test(
      groups = {TestNGGroups.PPD, Constants.TestNGGroups.FRONT_END_REGRESSION},
      description = "11124769")
  public void noPromoCodesAppliedTest() {
    logger.info("1. Add a PPD subscription item");
    CheckOutPage checkOutPage =
        navigateToPDPFromHome(TestData.PRODUCT_LUMINOSA_BREAKFAST_BLEND_GIFT_SUBSCRIPTION)
            .clickBuyNow();

    logger.info("2. Apply promo code");
    checkOutPage = checkOutPage.applyDiscountCode(TestData.PROMO_CODE_NEW_SUB_30);

    logger.info("Verify never apply the promo code to a prepaid subscription");
    Assert.assertFalse(checkOutPage.isDiscountDisplayed(), "Promo code is unexpected displayed");
  }

  @Test(
      groups = {TestNGGroups.PPD, Constants.TestNGGroups.FRONT_END_REGRESSION},
      description = "11124770",
      enabled = false)
  public void holidayDiscountsSetupShouldBeAppliedTest() {
    logger.info("1. Add a PPD subscription items");
    CheckOutPage checkOutPage =
        navigateToPDPFromHome(TestData.PRODUCT_LUMINOSA_BREAKFAST_BLEND_GIFT_SUBSCRIPTION)
            .clickBuyNow();

    // TODO The OTP discount is not implemented.
  }

  @Test(
      groups = {TestNGGroups.PPD, Constants.TestNGGroups.FRONT_END_REGRESSION},
      description = "11124771")
  public void buyNowCtaValidationTest() {
    logger.info("1. Navigate to PPD details page");
    ProductDetailsPage productDetailsPage =
        navigateToPDPFromHome(TestData.PRODUCT_LUMINOSA_BREAKFAST_BLEND_GIFT_SUBSCRIPTION);

    logger.info("Verify the text is displayed as \"COFFEE SHIPS ONCE PER MONTH\"");
    Assert.assertTrue(
        productDetailsPage.isCoffeeShipsOncePerMonthTextDisplayed(),
        "Coffee ships once per month message is not displayed");

    logger.info(
        "Verify the CTA to purchase should always read 'Buy Now' and take customers directly to checkout.");
    Assert.assertTrue(
        productDetailsPage.isBuyNowButtonDisplayed(), "Buy Now button is not displayed");
    CheckOutPage checkOutPage = productDetailsPage.clickBuyNow();
    Assert.assertTrue(checkOutPage.isDisplayed(), "Checkout page is not displayed");
  }

  @Test(
      groups = {TestNGGroups.PPD, Constants.TestNGGroups.FRONT_END_REGRESSION},
      description = "11124772")
  public void invalidCartMessageOnMiniCartAndCartTest() {
    logger.info("1. Add a PPD subscription item");
    CheckOutPage checkOutPage =
        navigateToPDPFromHome(TestData.PRODUCT_LUMINOSA_BREAKFAST_BLEND_GIFT_SUBSCRIPTION)
            .clickBuyNow();

    logger.info(
        "Verify cart should always present an invalid cart message that reads '{}'",
        TestData.GIFT_EXIST_IN_YOUR_CART_MESSAGE);
    CartPage cartPage = checkOutPage.clickOnReturnToCartButton();
    Assert.assertTrue(
        cartPage.isGiftExistsInYourCartMessageDisplayed(),
        "An invalid cart message is not displayed");
  }

  @Test(
      groups = {TestNGGroups.PPD, Constants.TestNGGroups.FRONT_END_REGRESSION},
      description = "11124773")
  public void isThisGiftCheckboxIsHiddenTest() {
    logger.info("1. Add a PPD subscription item");
    CheckOutPage checkOutPage =
        navigateToPDPFromHome(TestData.PRODUCT_LUMINOSA_BREAKFAST_BLEND_GIFT_SUBSCRIPTION)
            .clickBuyNow();

    logger.info(
        "Verify always hide the 'Is this a gift' checkbox in the cart when a prepaid item is in cart");
    CartPage cartPage = checkOutPage.clickOnReturnToCartButton();
    Assert.assertFalse(
        cartPage.thisIsGiftIsDisplayed(), "This is a gift checkbox is unexpected displayed");
  }

  private CheckOutPage navigateToPPDProductAndLogin(String productName) {
    logger.info("1. Navigate to Home");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");
    homePage.closeInitialBannersAndModals();

    logger.info("2. Sign In user");
    SignInPage signInPage = homePage.getHeader().clickAccountButton();

    signInPage.enterEmail(mail);
    signInPage.enterPassword(Constants.TestData.WEB_PASSWORD);
    signInPage.clickOnSignInButton();

    logger.info("3. Navigate to the PPD: {}", productName);
    return navigateToPDP(productName).clickBuyNow();
  }
}
