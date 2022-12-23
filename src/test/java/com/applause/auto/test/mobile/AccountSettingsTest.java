package com.applause.auto.test.mobile;

import com.applause.auto.common.data.Constants.MobileTestData;
import com.applause.auto.common.data.Constants.MyAccountTestData;
import com.applause.auto.common.data.Constants.TestData;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.mobile.views.AddNewCardView;
import com.applause.auto.mobile.views.CreditCardDetailsView;
import com.applause.auto.mobile.views.HomeView;
import com.applause.auto.mobile.views.MoreOptionsView;
import com.applause.auto.mobile.views.PaymentMethodsView;
import com.applause.auto.mobile.views.PeetsCardSettingsView;
import com.applause.auto.test.mobile.helpers.TestHelper;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class AccountSettingsTest extends BaseTest {

  @Test(
      groups = {TestNGGroups.REGRESSION},
      description = "625928")
  public void paymentMethodsTest() {
    SoftAssert softAssert = new SoftAssert();
    logger.info("Launch the app and arrive at the first on boarding screen view");
    HomeView homeView = TestHelper.openAppAndCreateNewAccountWithDefaults();
    Assert.assertNotNull(homeView, "Home View does not displayed");

    logger.info("STEP - Navigate to Payment Methods");
    PaymentMethodsView paymentMethodsView = homeView.getAccountProfileMenu().clickPaymentMethods();

    logger.info("PRECONDITION - Add a new CC");
    AddNewCardView addNewCardView = paymentMethodsView.clickAddNewPayment();
    paymentMethodsView =
        addNewCardView.addNewCard(
            TestData.VISA_CC_NUMBER,
            TestData.VISA_CC_SECURITY_CODE,
            TestData.VISA_CC_NAME,
            TestData.VISA_CC_EXP_DATE,
            TestData.VISA_CC_ZIP,
            true);

    logger.info("VERIFY - PAYMENT METHODS header is displayed");
    softAssert.assertTrue(
        paymentMethodsView.isHeaderDisplayed(), "PAYMENT METHODS header is not displayed");

    logger.info("VERIFY - Back arrow button is displayed");
    softAssert.assertTrue(
        paymentMethodsView.isBackButtonDisplayed(), "Back arrow button is not displayed");

    logger.info("VERIFY - Sub-header: Your Peet's Card is displayed");
    softAssert.assertEquals(
        paymentMethodsView.getPeetsCardHeader(),
        MobileTestData.PEETS_CARD_HEADER,
        "Peets Card header is not displayed");

    logger.info("VERIFY - Sub-header: Saved Payment Methods is displayed");
    softAssert.assertEquals(
        paymentMethodsView.getSavedPaymentHeader(),
        MobileTestData.SAVED_PAYMENT_HEADER,
        "Saved Payment Methods header is not displayed");

    logger.info("VERIFY - (+) Add new payment button is displayed");
    softAssert.assertTrue(
        paymentMethodsView.isAddNewPaymentButtonDisplayed(),
        "Add new payment button is not displayed");

    logger.info("VERIFY - Peet's Card image is displayed properly");
    softAssert.assertTrue(
        paymentMethodsView.isPeetsCardDisplayed(), "Peet's Card isn't displayed properly");

    logger.info("VERIFY - Saved card is displayed properly");
    softAssert.assertTrue(
        paymentMethodsView.isSavedPaymentCardDisplayed(TestData.VISA_CC_NUMBER),
        "Saved card isn't displayed properly");

    logger.info("STEP - Tap on Peet's Card field");
    PeetsCardSettingsView peetsCardSettingsView = paymentMethodsView.clickPeetsCard();
    softAssert.assertNotNull(peetsCardSettingsView, "Peets Card Settings view is not displayed");

    logger.info("STEP - Tap back arrow to return to Payment Methods screen");
    paymentMethodsView = peetsCardSettingsView.clickBackButton();

    logger.info("STEP - Tap on any saved payment method");
    CreditCardDetailsView creditCardDetailsView =
        paymentMethodsView.clickSavedPaymentMethod(
            CreditCardDetailsView.class, TestData.VISA_CC_NAME);

    logger.info("VERIFY - ");
    softAssert.assertEquals(
        creditCardDetailsView.getHeader(),
        TestData.VISA_CC_NAME.toUpperCase(),
        "CC Name does not match title");
    softAssert.assertEquals(
        creditCardDetailsView.getCreditCardName(), TestData.VISA_CC_NAME, "CC Name does not match");
    softAssert.assertTrue(
        creditCardDetailsView.isCCLogoImageDisplayed(), "CC logo image isn't displayed");
    softAssert.assertTrue(
        creditCardDetailsView.isCCNumberDisplayed(TestData.VISA_CC_NUMBER),
        "CC number isn't displayed");
    softAssert.assertTrue(
        creditCardDetailsView.isExpDateDisplayed(), "Expiration date isn't displayed");
    softAssert.assertTrue(
        creditCardDetailsView.isDeleteCardLinkIsDisplayed(), "Delete card link isn't displayed");
    softAssert.assertTrue(
        creditCardDetailsView.isDefaultCardTextPresent(), "Default Card text is not displayed");

    logger.info("STEP - Edit expiration date");
    String expDare = TestData.RANDOM_EXPIRATION_DATE;
    creditCardDetailsView.enterExpDate(expDare);

    logger.info("STEP - Tap Save Card button");
    paymentMethodsView = creditCardDetailsView.saveCard();

    logger.info("STEP - Tap on credit card with edited expiration date to view card details");
    creditCardDetailsView =
        paymentMethodsView.clickSavedPaymentMethod(
            CreditCardDetailsView.class, TestData.VISA_CC_NAME);

    logger.info("VERIFY - The edited expiration date is correct");
    softAssert.assertEquals(
        creditCardDetailsView.getExpDate(), expDare, "The edited expiration date isn't correct");

    logger.info("STEP - Tap back arrow to return to Payment Methods screen");
    paymentMethodsView = creditCardDetailsView.clickBackButton();

    logger.info("STEP - Tap (+) Add New Payment field");
    addNewCardView = paymentMethodsView.clickAddNewPayment();
    paymentMethodsView =
        addNewCardView.addNewCard(
            TestData.VISA_NUMBER,
            TestData.VISA_SECURITY_CODE,
            TestData.VISA_NAME,
            TestData.VISA_EXP_DATE,
            TestData.VISA_ZIP,
            true);

    logger.info(
        "STEP - Select a different card (credit card that is not set as default) to view card details");
    creditCardDetailsView =
        paymentMethodsView.clickSavedPaymentMethod(
            CreditCardDetailsView.class, TestData.VISA_CC_NAME);

    logger.info("STEP - Switch toggle to set as default payment on");
    creditCardDetailsView.setDefault();

    logger.info("STEP - Tap Save Card button");
    paymentMethodsView = creditCardDetailsView.saveCard();

    logger.info("STEP - Select card from step 8 to view card details");
    creditCardDetailsView =
        paymentMethodsView.clickSavedPaymentMethod(CreditCardDetailsView.class, TestData.VISA_NAME);

    logger.info(
        "VERIFY -The previous card that was set as default now shows it is no longer the default");
    softAssert.assertFalse(
        creditCardDetailsView.isDefaultSelected(),
        "The previous card is still selected as default");

    logger.info("Delete and Cancel Payment Method");
    creditCardDetailsView.clickDeleteCard();
    creditCardDetailsView = creditCardDetailsView.clickDeleteNo();
    softAssert.assertNotNull(creditCardDetailsView, "Did not remain on Credit Card Details view");

    logger.info("Delete Payment Method");
    creditCardDetailsView.clickDeleteCard();
    paymentMethodsView = creditCardDetailsView.clickDeleteYes();
    softAssert.assertFalse(
        paymentMethodsView.isPaymentMethodTestCardAdded(TestData.VISA_NAME),
        "Did not delete payment method");

    softAssert.assertAll();
  }

  @Test(
      groups = {TestNGGroups.REGRESSION},
      description = "625939")
  public void socialEngagementTest() {
    HomeView homeView =
        TestHelper.skipOnboardingAndLogin(MyAccountTestData.EMAIL, MyAccountTestData.PASSWORD);
    Assert.assertNotNull(homeView, "Home View does not displayed");

    logger.info("Navigate to Social Media icons and click Facebook icon");
    MoreOptionsView accountProfileMenu = homeView.getAccountProfileMenu();
    MoreOptionsView socialMedia = accountProfileMenu.clickFacebookIcon();
    Assert.assertTrue(socialMedia.isOnFacebookPage(), "Peet's Facebook page isn't opened");
    socialMedia.clickDoneButton();

    logger.info("Navigate to Social Media icons and click Instagram icon");
    socialMedia = accountProfileMenu.clickInstagramIcon();
    Assert.assertTrue(socialMedia.isOnInstagramPage(), "Peet's Instagram page isn't opened");
    socialMedia.clickDoneButton();

    logger.info("Navigate to Social Media icons and click Twitter icon");
    socialMedia = accountProfileMenu.clickTwitterIcon();
    Assert.assertTrue(socialMedia.isOnTwitterPage(), "Peet's Twitter page isn't opened");
    socialMedia.clickDoneButton();
  }
}
