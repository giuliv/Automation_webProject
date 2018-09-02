package com.applause.auto.test;

import com.applause.auto.pageframework.pages.AddBillingAddressPage;
import com.applause.auto.pageframework.pages.AddressBookPage;
import com.applause.auto.pageframework.pages.AssociateNewCardPage;
import com.applause.auto.pageframework.pages.EditBillingAddressPage;
import com.applause.auto.pageframework.pages.EditPaymentMethodPage;
import com.applause.auto.pageframework.pages.LandingPage;
import com.applause.auto.pageframework.pages.MyAccountMyOrdersPage;
import com.applause.auto.pageframework.pages.MyAccountOrderDetailPage;
import com.applause.auto.pageframework.pages.MyAccountPage;
import com.applause.auto.pageframework.pages.MyAccountPeetsCardPage;
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
        signInPage.enterEmail(TestConstants.TestData.USERNAME);
        signInPage.enterPassword(TestConstants.TestData.PASSWORD);
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

    @Test(groups = {TestConstants.TestNGGroups.MY_ACCOUNT }, description = "133898")
    public void myAccountBillingAddress() {

        LOGGER.info("1. Navigate to landing page");
        LandingPage landingPage = navigateToLandingPage();

        LOGGER.info("2. Log In");
        SignInPage signInPage = landingPage.clickSignInButton();
        signInPage.enterEmail(TestConstants.MyAccountTestData.EMAIL);
        signInPage.enterPassword(TestConstants.MyAccountTestData.PASSWORD);
        MyAccountPage myAccountPage = signInPage.clickonSignInButton();
        Assert.assertNotNull(myAccountPage, "Account Dashboard did not display");

        LOGGER.info("3. Edit Billing Address");
        EditBillingAddressPage editBillingAddressPage = myAccountPage.clickEditBillingAddress();
        Assert.assertNotNull(editBillingAddressPage, "Edit Billing Address page is not displayed");
        String address = editBillingAddressPage.enterAddress(TestConstants.TestData.ADDRESS, TestConstants.MyAccountTestData.ADDRESS_LINE_2);
        AddressBookPage addressBookPage = editBillingAddressPage.clickSaveAddress();

        LOGGER.info("4. Verify Billing Address Change");
        Assert.assertTrue(addressBookPage.isAddressSavedTextDisplayed(), "Address Saved text is not displayed");
        Assert.assertTrue(addressBookPage.getBillingAddress().contains(address));

        LOGGER.info("5. Delete Billing Address");
        addressBookPage.deleteBillingAddress();
        Assert.assertTrue(addressBookPage.isBillingAddressDeleted(), "Billing Address was not deleted");

        LOGGER.info("6. Add New Billing Address");
        AddBillingAddressPage addBillingAddressPage = addressBookPage.clickAddNewBillingAddress();
        addBillingAddressPage.enterAddressLine1(TestConstants.TestData.ADDRESS);
        addBillingAddressPage.enterZipCode(TestConstants.TestData.ZIP_CODE);
        addBillingAddressPage.selectState(TestConstants.TestData.STATE);
        addBillingAddressPage.enterCity(TestConstants.TestData.CITY);
        addBillingAddressPage.enterPhoneNumber(TestConstants.TestData.PHONE);
        addressBookPage = addBillingAddressPage.clickSaveAddress();

        LOGGER.info("7. Verify Address is Added");
        Assert.assertTrue(addressBookPage.getBillingAddress().contains(TestConstants.TestData.ADDRESS));

    }

	@Test(groups = { TestConstants.TestNGGroups.MY_ACCOUNT }, description = "133901")
	public void myAccountPeetsCards() {

		LOGGER.info("1. Navigate to landing page");
		LandingPage landingPage = navigateToLandingPage();

		LOGGER.info("2. Log In");
		SignInPage signInPage = landingPage.clickSignInButton();
		MyAccountPage myAccountPage = signInPage.mainUserLogin();
		Assert.assertNotNull(myAccountPage, "Account Dashboard did not display");

		LOGGER.info("3. Select 'Peets Cards' from Dashboard navigation bar");
		MyAccountPeetsCardPage peetsCardsPage = myAccountPage.clickPeetsCardsTab();
		Assert.assertNotNull(peetsCardsPage, "Peets Cards page did not display");

		LOGGER.info("4. Verify Peets Cards sections, Buy Peet's Card");
		Assert.assertTrue(peetsCardsPage.isBuyPeetsCardSectionDisplayed(), "Buy Peet's Card Section is not displayed");
		Assert.assertTrue(peetsCardsPage.isBuyPeetsCardDescriptionDisplayed(), "Buy Peet's Card Description is not displayed");
		Assert.assertTrue(peetsCardsPage.isBuyPeetsCardLinkDisplayed(),"Buy Peet's Card Link is not displayed");
		Assert.assertTrue(peetsCardsPage.isBuyPeetsCardImageDisplayed(),"Buy Peet's Card Image is not displayed");

		LOGGER.info("5. Verify Peets Cards sections, Check Balance");
		Assert.assertTrue(peetsCardsPage.isCheckBalanceSectionDisplayed(), "Check Balance Section is not displayed");
		Assert.assertTrue(peetsCardsPage.isCheckBalanceCardNumberDisplayed(), "Check Balance Card Number is not displayed");
		Assert.assertTrue(peetsCardsPage.isCheckBalancePinNumberDisplayed(), "Check Balance Pin Number is not displayed");
		Assert.assertTrue(peetsCardsPage.isCheckBalanceButtonDisplayed(), "Check Balance Button is not displayed");


		LOGGER.info("6. Verify Peets Cards sections, Register Peet's Card");
		Assert.assertTrue(peetsCardsPage.isRegisterPeetsCardSectionDisplayed(), "Register Peet's Card Section is not displayed");
		Assert.assertTrue(peetsCardsPage.isRegisterPeetsCardDescriptionDisplayed(), "Register Peet's Card Description is not displayed");
		Assert.assertTrue(peetsCardsPage.isRegisterPeetsCardLinkDisplayed(),"Register Peet's Card Link is not displayed");

		LOGGER.info("6. Verify Peets Cards sections, FAQ");
		Assert.assertTrue(peetsCardsPage.isFAQLinkDisplayed(), "FAQ Link is not displayed");
	}

	@Test(groups = { TestConstants.TestNGGroups.MY_ACCOUNT }, description = "133895")
	public void myAccountMyOrdersTest() {

		LOGGER.info("1. Navigate to landing page");
		LandingPage landingPage = navigateToLandingPage();

		LOGGER.info("2. Log In");
		SignInPage signInPage = landingPage.clickSignInButton();
		MyAccountPage myAccountPage = signInPage.mainUserLogin();
		Assert.assertNotNull(myAccountPage, "Account Dashboard did not display");

		LOGGER.info("3. Select 'My Orders' from Dashboard navigation bar");
		MyAccountMyOrdersPage myOrdersPage = myAccountPage.clickMyOrdersTab();
		Assert.assertNotNull(myOrdersPage, "My Orders page did not display");

		LOGGER.info("4. Verify My Orders section, First Order");
		Assert.assertTrue(myOrdersPage.isOrdersPlacedSectionDisplayed(), "Orders Placed Section is not displayed");
		Assert.assertTrue(myOrdersPage.isOrdersDateDisplayed(), "Order's Date is not displayed");
		Assert.assertTrue(myOrdersPage.isOrdersNumberDisplayed(), "Order's Number is not displayed");
		Assert.assertTrue(myOrdersPage.isOrdersItemDisplayed(), "Order's Item is not displayed");
		Assert.assertTrue(myOrdersPage.isOrdersTotalDisplayed(), "Order's Total is not displayed");
		Assert.assertTrue(myOrdersPage.isOrdersStatusDisplayed(), "Order's Status is not displayed");
		Assert.assertTrue(myOrdersPage.isViewButtonDisplayed(), "Order's View button is not displayed");
		Assert.assertTrue(myOrdersPage.isReorderButtonDisplayed(), "Order's View button is not displayed");

		LOGGER.info("5. Access first Oder and Verify Detail");
		MyAccountOrderDetailPage ordersDetailPage = myOrdersPage.clickOrderNumber();
		Assert.assertTrue(ordersDetailPage.isProductDisplayed(), "Product on Order Detail is not displayed");
		Assert.assertTrue(ordersDetailPage.isReorderButtonDisplayed(), "Reorder button on Order Detail is not displayed");
		Assert.assertTrue(ordersDetailPage.isShippingMethodDisplayed(), "Shipping Method on Order Detail is not displayed");
		Assert.assertTrue(ordersDetailPage.isPaymentMethodDisplayed(), "Payment Method on Order Detail is not displayed");

		LOGGER.info("6. Return to My Orders page");
		myOrdersPage = ordersDetailPage.clickBackToMyOrders();
		Assert.assertNotNull(myOrdersPage, "My Orders page did not display");

	}
}
