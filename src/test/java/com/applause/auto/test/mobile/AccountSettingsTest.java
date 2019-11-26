package com.applause.auto.test.mobile;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.Constants.MobileTestData;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.mobile.components.AccountMenuMobileChunk;
import com.applause.auto.mobile.views.AddNewCardView;
import com.applause.auto.mobile.views.CreditCardDetailsView;
import com.applause.auto.mobile.views.DashboardView;
import com.applause.auto.mobile.views.LandingView;
import com.applause.auto.mobile.views.PaymentMethodsView;
import com.applause.auto.mobile.views.PeetsCardSettingsView;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import java.lang.invoke.MethodHandles;

public class AccountSettingsTest extends BaseTest {
  private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().getClass());

  @Test(
      groups = {TestNGGroups.ACCOUNT_SETTINGS},
      description = "625928")
  public void paymentMethodsTest() {

    logger.info("Launch the app and arrive at the first on boarding screen view");
    LandingView landingView = ComponentFactory.create(LandingView.class);

    DashboardView dashboardView = testHelper.signIn(landingView);

    logger.info("Navigate to Payment Methods");
    AccountMenuMobileChunk accountProfileMenu = dashboardView.getAccountProfileMenu();
    PaymentMethodsView paymentMethodsView = accountProfileMenu.clickPaymentMethods();

    softAssert.assertEquals(
        paymentMethodsView.getPeetsCardHeader(),
        MobileTestData.PEETS_CARD_HEADER,
        "Peets Card header is not displayed");
    softAssert.assertEquals(
        paymentMethodsView.getSavedPaymentHeader(),
        MobileTestData.SAVED_PAYMENT_HEADER,
        "Saved Payment Methods header is not displayed");

    logger.info("Click Peets Card");
    PeetsCardSettingsView peetsCardSettingsView = paymentMethodsView.clickPeetsCard();
    softAssert.assertNotNull(peetsCardSettingsView, "Peets Card Settings view is not displayed");

    logger.info("Go Back to Payment Methods");
    paymentMethodsView = peetsCardSettingsView.clickBackButton();

    logger.info("Delete Test card if it was already added");
    paymentMethodsView = testHelper.deletePaymentMethodTestCardIfAdded(paymentMethodsView);

    logger.info("Add New Payment Method");
    AddNewCardView addNewCardView = paymentMethodsView.clickAddNewPayment();
    addNewCardView.enterCardNumber(Constants.TestData.AMEX_CC_NUM);
    addNewCardView.enterExpDate(MobileTestData.CC_EXP_DATE);
    addNewCardView.enterCvvCode(Constants.TestData.AMEX_CC_CODE);
    addNewCardView.enterZipCode(MobileTestData.CC_ZIP);
    addNewCardView.enterCardName(MobileTestData.CC_NAME);
    addNewCardView.selectMakeDefault();
    paymentMethodsView = addNewCardView.saveCard();
    // TODO - Revert back to swiping and figure out why it's not working
    // for now, go back, and then forward
    // DeviceControl.swipeAcrossScreenWithDirection(SwipeDirection.DOWN);
    paymentMethodsView.clickBackButton();
    accountProfileMenu.clickPaymentMethods();

    logger.info("Click Payment Method");
    CreditCardDetailsView creditCardDetailsView =
        paymentMethodsView.clickSavedPaymentMethod(CreditCardDetailsView.class);

    softAssert.assertEquals(
        creditCardDetailsView.getHeader(), MobileTestData.CC_NAME, "CC Name does not match title");
    softAssert.assertEquals(
        creditCardDetailsView.getCreditCardName(),
        MobileTestData.CC_NAME,
        "CC Name does not match");
    softAssert.assertTrue(
        creditCardDetailsView.isDefaultCardTextPresent(), "Default Card text is not displayed");

    logger.info("Edit Payment Method");
    creditCardDetailsView.enterExpDate(MobileTestData.CC_MODIFIED_EXP_DATE);
    paymentMethodsView = creditCardDetailsView.saveCard();
    paymentMethodsView.clickBackButton();
    accountProfileMenu.clickPaymentMethods();

    logger.info("Delete and Cancel Payment Method");
    creditCardDetailsView = paymentMethodsView.clickSavedPaymentMethod(CreditCardDetailsView.class);
    softAssert.assertEquals(
        creditCardDetailsView.getExpDate(),
        MobileTestData.CC_MODIFIED_EXP_DATE,
        "Exp date did not update");
    creditCardDetailsView.clickDeleteCard();
    creditCardDetailsView = creditCardDetailsView.clickDeleteNo();
    softAssert.assertNotNull(creditCardDetailsView, "Did not remain on Credit Card Details view");

    logger.info("Delete Payment Method");
    creditCardDetailsView.clickDeleteCard();
    paymentMethodsView = creditCardDetailsView.clickDeleteYes();
    // softAssert.assertNotNull(paymentMethodsView, "Did not delete payment method");
    softAssert.assertFalse(
        paymentMethodsView.isPaymentMethodTestCardAdded(), "Did not delete payment method");

    softAssert.assertAll();
  }
}
