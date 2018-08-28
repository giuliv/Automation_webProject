package com.applause.auto.test;

import com.applause.auto.pageframework.pages.AssociateNewCardPage;
import com.applause.auto.pageframework.pages.EditPaymentMethodPage;
import com.applause.auto.pageframework.pages.LandingPage;
import com.applause.auto.pageframework.pages.MyAccountPage;
import com.applause.auto.pageframework.pages.PaymentMethodsPage;
import com.applause.auto.pageframework.pages.SignInPage;
import com.applause.auto.pageframework.testdata.TestConstants;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MyAccountTest extends BaseTest {

    @Test(groups = { TestConstants.TestNGGroups.MY_ACCOUNT }, description = "133894")
    public void myAccountDashboard() {

        LOGGER.info("1. Navigate to landing page");
        LandingPage landingPage = navigateToLandingPage();

        LOGGER.info("2. Log In");
        SignInPage signInPage = landingPage.clickSignInButton();
        signInPage.enterEmail(TestConstants.MyAccountTestData.EMAIL);
        signInPage.enterPassword(TestConstants.MyAccountTestData.PASSWORD);
        MyAccountPage myAccountPage = signInPage.clickonSignInButton();
        Assert.assertNotNull(myAccountPage, "Account Dashboard did not display");

        LOGGER.info("3. Verify My Subscriptions Section");
        Assert.assertTrue(myAccountPage.isMySubscriptionsCardDisplayed(), "My Subscription section is not displayed");
        Assert.assertTrue(myAccountPage.isManageSubscriptionsLinkDisplayed(), "Subscription Manage link is not displayed");
        Assert.assertTrue(myAccountPage.isEditSubscriptionsButtonDisplayed(), "Subscription Edit button is not displayed");

        LOGGER.info("4. Verify Order History Section");
        Assert.assertTrue(myAccountPage.isOrderHistoryCardDisplayed(), "Order History section is not displayed");
        Assert.assertTrue(myAccountPage.isViewAllOrdersLinkDisplayed(), "View All Orders link is not displayed");
        Assert.assertTrue(myAccountPage.isOrderRatingDisplayed(), "Order Rating is not displayed");

        LOGGER.info("5. Verify Payment Section");
        Assert.assertTrue(myAccountPage.isPaymentCardDisplayed(), "Payment section is not displayed");
        Assert.assertTrue(myAccountPage.isPaymentManageCardsLinkDisplayed(), "Payment Manage Cards link is not displayed");
        Assert.assertTrue(myAccountPage.isPaymentCardImageDisplayed(), "Payment Card image is not displayed");

        LOGGER.info("6. Verify Peets Card Section");
        Assert.assertTrue(myAccountPage.isPeetsCardsCardDisplayed(), "Peets Card section is not displayed");
        Assert.assertTrue(myAccountPage.isPeetsCardManageCardsLinkDisplayed(), "Peets Card Manage Cards link is not displayed");

        LOGGER.info("7. Verify Settings Card Section");
        Assert.assertTrue(myAccountPage.isSettingsCardDisplayed(), "Settings section is not displayed");
        Assert.assertTrue(myAccountPage.isGoToAddressBookLinkDisplayed(), "Go To Address Book link is not displayed");

        LOGGER.info("8. Verify Billing Address Section");
        Assert.assertTrue(myAccountPage.isBillingAddressDisplayed(), "Billing Address section is not displayed");
        Assert.assertTrue(myAccountPage.isBillingAddressEditLinkDisplayed(), "Billing Address Edit link is not displayed");

        LOGGER.info("9. Verify Shipping Address Section");
        Assert.assertTrue(myAccountPage.isShippingAddressDisplayed(), "Shipping Address section is not displayed");
    }

    @Test(groups = { TestConstants.TestNGGroups.MY_ACCOUNT }, description = "133897")
    public void myAccountPaymentMethod() {

        LOGGER.info("1. Navigate to landing page");
        LandingPage landingPage = navigateToLandingPage();

        LOGGER.info("2. Log In");
        SignInPage signInPage = landingPage.clickSignInButton();
        signInPage.enterEmail(TestConstants.MyAccountTestData.EMAIL);
        signInPage.enterPassword(TestConstants.MyAccountTestData.PASSWORD);
        MyAccountPage myAccountPage = signInPage.clickonSignInButton();
        Assert.assertNotNull(myAccountPage, "Account Dashboard did not display");

        LOGGER.info("3. Select Payment");
        PaymentMethodsPage paymentMethodsPage = myAccountPage.clickPayment();
        Assert.assertNotNull(paymentMethodsPage, "Payment Methods page did not display");

        LOGGER.info("4. Verify Payment Method Elements");
        Assert.assertTrue(paymentMethodsPage.isCreditCardDisplayed(), "Credit Card is not displayed");
        Assert.assertTrue(paymentMethodsPage.isPeetsCardDisplayed(), "Peets Card is not displayed");
        Assert.assertTrue(paymentMethodsPage.isAddCreditCardButtonDisplayed(), "Add Credit Card button is not displayed");
        Assert.assertTrue(paymentMethodsPage.isAddPaypalAccountButtonDisplayed(), "Add Paypal Account button is not displayed");
        Assert.assertTrue(paymentMethodsPage.isAddPeetsCardButtonDisplayed(), "Add Peets Card button is not displayed");

        LOGGER.info("5. Edit Saved Payment Method");
        EditPaymentMethodPage editPaymentMethodPage = paymentMethodsPage.clickEditCreditCard();
        String updatedName = editPaymentMethodPage.enterNameOnCard(TestConstants.TestData.LAST_NAME);
        paymentMethodsPage = editPaymentMethodPage.clickSavePaymentMethod();
        Assert.assertEquals(paymentMethodsPage.getNameOnCreditCard(), updatedName, "Name on Credit Card was not updated");

        LOGGER.info("6. Delete Payment Method");
        paymentMethodsPage.clickDeletePeetsCard();
        Assert.assertTrue(paymentMethodsPage.didPeetsCardDelete(), "Peets Card did not delete");

        LOGGER.info("7. Add New Payment Method");
        AssociateNewCardPage associateNewCardPage = paymentMethodsPage.addPeetsCard();
        associateNewCardPage.enterCardNumber(TestConstants.TestData.PEETS_CARD_NUMBER_2);
        associateNewCardPage.enterPinNumber(TestConstants.TestData.PEETS_CARD_PIN_2);
        paymentMethodsPage = associateNewCardPage.clickAssociateCard();

        LOGGER.info("8. Verify New Payment Method");
        Assert.assertTrue(paymentMethodsPage.isPeetsCardDisplayed(), "Peets Card is not displayed");
    }
}
