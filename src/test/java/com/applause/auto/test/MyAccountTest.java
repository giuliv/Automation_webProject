package com.applause.auto.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.applause.auto.framework.pageframework.util.drivers.BrowserType;
import com.applause.auto.pageframework.chunks.MiniCartContainerChunk;
import com.applause.auto.pageframework.chunks.VerifyYourAddressDetailsChunk;
import com.applause.auto.pageframework.helpers.WebHelper;
import com.applause.auto.pageframework.pages.AddBillingAddressPage;
import com.applause.auto.pageframework.pages.AddShippingAddressPage;
import com.applause.auto.pageframework.pages.AddressBookPage;
import com.applause.auto.pageframework.pages.AssociateNewCardPage;
import com.applause.auto.pageframework.pages.CheckoutConfirmationPage;
import com.applause.auto.pageframework.pages.CheckoutPage;
import com.applause.auto.pageframework.pages.CheckoutPaymentMethodPage;
import com.applause.auto.pageframework.pages.CheckoutPlaceOrderPage;
import com.applause.auto.pageframework.pages.CheckoutShippingInfoPage;
import com.applause.auto.pageframework.pages.CoffeeProductPage;
import com.applause.auto.pageframework.pages.EditAccountInformationPage;
import com.applause.auto.pageframework.pages.EditBillingAddressPage;
import com.applause.auto.pageframework.pages.EditPaymentMethodPage;
import com.applause.auto.pageframework.pages.EditShippingAddressPage;
import com.applause.auto.pageframework.pages.LandingPage;
import com.applause.auto.pageframework.pages.MyAccountManageSubscriptionPage;
import com.applause.auto.pageframework.pages.MyAccountMyOrdersPage;
import com.applause.auto.pageframework.pages.MyAccountMySuscriptionsPage;
import com.applause.auto.pageframework.pages.MyAccountOrderDetailPage;
import com.applause.auto.pageframework.pages.MyAccountPage;
import com.applause.auto.pageframework.pages.MyAccountPeetsCardPage;
import com.applause.auto.pageframework.pages.PaymentMethodsPage;
import com.applause.auto.pageframework.pages.ShopCoffeePage;
import com.applause.auto.pageframework.pages.SignInPage;
import com.applause.auto.pageframework.pages.SignUpPage;
import com.applause.auto.pageframework.testdata.TestConstants;

public class MyAccountTest extends BaseTest {
	WebHelper webHelper = new WebHelper();

	@Test(groups = { TestConstants.TestNGGroups.MY_ACCOUNT }, description = "133894")
	public void myAccountDashboard() {

		LOGGER.info("1. Navigate to landing page");
		LandingPage landingPage = navigateToLandingPage();

		LOGGER.info("2. Log In");
		SignInPage signInPage = landingPage.clickSignInButton();
		MyAccountPage myAccountPage = signInPage.mainUserLogin();
		Assert.assertNotNull(myAccountPage, "Account Dashboard did not display");

		LOGGER.info("3. Verify My Subscriptions Section");
		Assert.assertTrue(myAccountPage.isMySubscriptionsCardDisplayed(), "My Subscription section is not displayed");
		Assert.assertTrue(myAccountPage.isManageSubscriptionsLinkDisplayed(),
				"Subscription Manage link is not displayed");
		Assert.assertTrue(myAccountPage.isEditSubscriptionsButtonDisplayed(),
				"Subscription Edit button is not displayed");

		LOGGER.info("4. Verify Order History Section");
		Assert.assertTrue(myAccountPage.isOrderHistoryCardDisplayed(), "Order History section is not displayed");
		Assert.assertTrue(myAccountPage.isViewAllOrdersLinkDisplayed(), "View All Orders link is not displayed");
		Assert.assertTrue(myAccountPage.isOrderRatingDisplayed(), "Order Rating is not displayed");

		LOGGER.info("5. Verify Payment Section");
		Assert.assertTrue(myAccountPage.isPaymentCardDisplayed(), "Payment section is not displayed");
		Assert.assertTrue(myAccountPage.isPaymentManageCardsLinkDisplayed(),
				"Payment Manage Cards link is not displayed");
		Assert.assertTrue(myAccountPage.isPaymentCardImageDisplayed(), "Payment Card image is not displayed");

		LOGGER.info("6. Verify Peets Card Section");
		Assert.assertTrue(myAccountPage.isPeetsCardsCardDisplayed(), "Peets Card section is not displayed");
		Assert.assertTrue(myAccountPage.isPeetsCardManageCardsLinkDisplayed(),
				"Peets Card Manage Cards link is not displayed");

		LOGGER.info("7. Verify Settings Card Section");
		Assert.assertTrue(myAccountPage.isSettingsCardDisplayed(), "Settings section is not displayed");
		Assert.assertTrue(myAccountPage.isGoToAddressBookLinkDisplayed(), "Go To Address Book link is not displayed");

		LOGGER.info("8. Verify Billing Address Section");
		Assert.assertTrue(myAccountPage.isBillingAddressDisplayed(), "Billing Address section is not displayed");
		Assert.assertTrue(myAccountPage.isBillingAddressEditLinkDisplayed(),
				"Billing Address Edit link is not displayed");

		LOGGER.info("9. Verify Shipping Address Section");
		Assert.assertTrue(myAccountPage.isShippingAddressDisplayed(), "Shipping Address section is not displayed");
	}

	@Test(groups = { TestConstants.TestNGGroups.MY_ACCOUNT }, description = "133897")
	public void myAccountPaymentMethod() {

		LOGGER.info("1. Navigate to landing page");
		LandingPage landingPage = navigateToLandingPage();

		LOGGER.info("2. Log In");
		SignInPage signInPage = landingPage.clickSignInButton();
		MyAccountPage myAccountPage = signInPage.mainUserLogin();
		Assert.assertNotNull(myAccountPage, "Account Dashboard did not display");

		LOGGER.info("3. Select Payment");
		PaymentMethodsPage paymentMethodsPage = myAccountPage.clickPayment();
		Assert.assertNotNull(paymentMethodsPage, "Payment Methods page did not display");

		LOGGER.info("4. Verify Payment Method Elements");
		Assert.assertTrue(paymentMethodsPage.isCreditCardDisplayed(), "Credit Card is not displayed");
		Assert.assertTrue(paymentMethodsPage.isPeetsCardDisplayed(), "Peets Card is not displayed");
		Assert.assertTrue(paymentMethodsPage.isAddCreditCardButtonDisplayed(),
				"Add Credit Card button is not displayed");
		Assert.assertTrue(paymentMethodsPage.isAddPaypalAccountButtonDisplayed(),
				"Add Paypal Account button is not displayed");
		Assert.assertTrue(paymentMethodsPage.isAddPeetsCardButtonDisplayed(), "Add Peets Card button is not displayed");

		LOGGER.info("5. Edit Saved Payment Method");
		EditPaymentMethodPage editPaymentMethodPage = paymentMethodsPage.clickEditCreditCard();
		String updatedName = editPaymentMethodPage.enterNameOnCard(TestConstants.TestData.LAST_NAME);
		paymentMethodsPage = editPaymentMethodPage.clickSavePaymentMethod();
		Assert.assertEquals(paymentMethodsPage.getNameOnCreditCard(), updatedName,
				"Name on Credit Card was not updated");

		LOGGER.info("6. Delete Payment Method");
		paymentMethodsPage.clickDeletePeetsCard();
		Assert.assertTrue(paymentMethodsPage.didPeetsCardDelete(), "Peets Card did not delete");

		LOGGER.info("7. Add New Payment Method");
		AssociateNewCardPage associateNewCardPage = paymentMethodsPage.addPeetsCard();
		String alternatePeetsCardNumber = (env.getBrowserType() == BrowserType.SAFARI)
				? TestConstants.TestData.PEETS_CARD_NUMBER_SAFARI_2 : TestConstants.TestData.PEETS_CARD_NUMBER_CHROME_2;
		associateNewCardPage.enterCardNumber(alternatePeetsCardNumber);
		String alternatePeetsCardPin = (env.getBrowserType() == BrowserType.SAFARI)
				? TestConstants.TestData.PEETS_CARD_PIN_SAFARI_2 : TestConstants.TestData.PEETS_CARD_PIN_CHROME_2;
		associateNewCardPage.enterPinNumber(alternatePeetsCardPin);
		paymentMethodsPage = associateNewCardPage.clickAssociateCard();

		LOGGER.info("8. Verify New Payment Method");
		Assert.assertTrue(paymentMethodsPage.isPeetsCardDisplayed(), "Peets Card is not displayed");
	}

	@Test(groups = { TestConstants.TestNGGroups.MY_ACCOUNT }, description = "133898")
	public void myAccountBillingAddress() {

		LOGGER.info("1. Navigate to landing page");
		LandingPage landingPage = navigateToLandingPage();

		LOGGER.info("2. Log In");
		SignInPage signInPage = landingPage.clickSignInButton();
		signInPage.enterEmailByBrowser(TestConstants.MyAccountTestData.EMAIL,
				TestConstants.MyAccountTestData.SAFARI_BILLING_EMAIL);
		signInPage.enterPassword(TestConstants.MyAccountTestData.PASSWORD);
		MyAccountPage myAccountPage = signInPage.clickonSignInButton();
		Assert.assertNotNull(myAccountPage, "Account Dashboard did not display");

		LOGGER.info("3. Edit Billing Address");
		EditBillingAddressPage editBillingAddressPage = myAccountPage.clickEditBillingAddress();
		Assert.assertNotNull(editBillingAddressPage, "Edit Billing Address page is not displayed");
		String address = editBillingAddressPage.enterAddress(TestConstants.TestData.ADDRESS,
				TestConstants.MyAccountTestData.ADDRESS_LINE_2);
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

	@Test(groups = { TestConstants.TestNGGroups.MY_ACCOUNT }, description = "133899")
	public void myAccountShippingAddress() {

		LOGGER.info("1. Navigate to landing page");
		LandingPage landingPage = navigateToLandingPage();

		LOGGER.info("2. Log In");
		SignInPage signInPage = landingPage.clickSignInButton();
		signInPage.enterEmailByBrowser(TestConstants.MyAccountTestData.EMAIL,
				TestConstants.MyAccountTestData.SAFARI_SHIPPING_EMAIL);
		signInPage.enterPassword(TestConstants.MyAccountTestData.PASSWORD);
		MyAccountPage myAccountPage = signInPage.clickonSignInButton();
		Assert.assertNotNull(myAccountPage, "Account Dashboard did not display");

		LOGGER.info("3. Edit Shipping Address");
		EditShippingAddressPage editShippingAddressPage = myAccountPage.clickEditShippingAddress();
		Assert.assertNotNull(editShippingAddressPage, "Edit Shipping Address page is not displayed");
		String address = editShippingAddressPage.enterAddress(TestConstants.TestData.ADDRESS,
				TestConstants.MyAccountTestData.ADDRESS_LINE_2);
		AddressBookPage addressBookPage = editShippingAddressPage.clickSaveAddress();

		LOGGER.info("4. Verify Shipping Address Change");
		Assert.assertTrue(addressBookPage.isAddressSavedTextDisplayed(), "Address Saved text is not displayed");
		Assert.assertTrue(addressBookPage.getShippingAddress().contains(address));

		LOGGER.info(" 5. Delete Shipping Address");
		addressBookPage.deleteShippingAddress();
		Assert.assertTrue(addressBookPage.isShippingAddressDeleted(), "Shipping Address was not deleted");

		LOGGER.info("6. Add New Shipping Address");
		AddShippingAddressPage addShippingAddressPage = addressBookPage.clickAddNewShippingAddress();
		addShippingAddressPage.enterAddressLine1(TestConstants.TestData.ADDRESS);
		addShippingAddressPage.enterZipCode(TestConstants.TestData.ZIP_CODE);
		addShippingAddressPage.selectState(TestConstants.TestData.STATE);
		addShippingAddressPage.enterCity(TestConstants.TestData.CITY);
		addShippingAddressPage.enterPhoneNumber(TestConstants.TestData.PHONE);
		addressBookPage = addShippingAddressPage.clickSaveAddress();

		LOGGER.info("7. Verify Address was Added");
		Assert.assertTrue(addressBookPage.getShippingAddress().contains(TestConstants.TestData.ADDRESS));

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
		Assert.assertTrue(peetsCardsPage.isBuyPeetsCardDescriptionDisplayed(),
				"Buy Peet's Card Description is not displayed");
		Assert.assertTrue(peetsCardsPage.isBuyPeetsCardLinkDisplayed(), "Buy Peet's Card Link is not displayed");
		Assert.assertTrue(peetsCardsPage.isBuyPeetsCardImageDisplayed(), "Buy Peet's Card Image is not displayed");

		LOGGER.info("5. Verify Peets Cards sections, Check Balance");
		Assert.assertTrue(peetsCardsPage.isCheckBalanceSectionDisplayed(), "Check Balance Section is not displayed");
		Assert.assertTrue(peetsCardsPage.isCheckBalanceCardNumberDisplayed(),
				"Check Balance Card Number is not displayed");
		Assert.assertTrue(peetsCardsPage.isCheckBalancePinNumberDisplayed(),
				"Check Balance Pin Number is not displayed");
		Assert.assertTrue(peetsCardsPage.isCheckBalanceButtonDisplayed(), "Check Balance Button is not displayed");

		LOGGER.info("6. Verify Peets Cards sections, Register Peet's Card");
		Assert.assertTrue(peetsCardsPage.isRegisterPeetsCardSectionDisplayed(),
				"Register Peet's Card Section is not displayed");
		Assert.assertTrue(peetsCardsPage.isRegisterPeetsCardDescriptionDisplayed(),
				"Register Peet's Card Description is not displayed");
		Assert.assertTrue(peetsCardsPage.isRegisterPeetsCardLinkDisplayed(),
				"Register Peet's Card Link is not displayed");

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
		Assert.assertTrue(ordersDetailPage.isReorderButtonDisplayed(),
				"Reorder button on Order Detail is not displayed");
		Assert.assertTrue(ordersDetailPage.isShippingMethodDisplayed(),
				"Shipping Method on Order Detail is not displayed");
		Assert.assertTrue(ordersDetailPage.isPaymentMethodDisplayed(),
				"Payment Method on Order Detail is not displayed");

		LOGGER.info("6. Return to My Orders page");
		myOrdersPage = ordersDetailPage.clickBackToMyOrders();
		Assert.assertNotNull(myOrdersPage, "My Orders page did not display");

	}

	@Test(groups = { TestConstants.TestNGGroups.MY_ACCOUNT }, description = "133900")
	public void myAccountSettings() {

		LOGGER.info("1. Navigate to landing page");
		LandingPage landingPage = navigateToLandingPage();

		LOGGER.info("2. Log In");
		SignInPage signInPage = landingPage.clickSignInButton();
		signInPage.enterEmailByBrowser(TestConstants.MyAccountTestData.MODIFY_ACCOUNT_EMAIL,
				TestConstants.MyAccountTestData.SAFARI_ACCOUNT_EMAIL);
		signInPage.enterPassword(TestConstants.MyAccountTestData.PASSWORD);
		MyAccountPage myAccountPage = signInPage.clickonSignInButton();
		Assert.assertNotNull(myAccountPage, "Account Dashboard did not display");

		LOGGER.info("3. Select Settings");
		EditAccountInformationPage editAccountInformationPage = myAccountPage.clickSettings();
		Assert.assertNotNull(editAccountInformationPage, "Edit Account Information page is not displayed");

		LOGGER.info("4. Edit Information");
		editAccountInformationPage.enterFirstName(TestConstants.MyAccountTestData.FIRST_NAME);
		String email = String.format(TestConstants.TestData.EMAIL, webHelper.returnTimestamp());
		editAccountInformationPage.enterEmail(email);
		editAccountInformationPage.enterCurrentPassword(TestConstants.MyAccountTestData.PASSWORD);
		myAccountPage = editAccountInformationPage.clickSave();

		LOGGER.info("5. Verify Information Changed");
		Assert.assertTrue(myAccountPage.getCustomerName().contains(TestConstants.MyAccountTestData.FIRST_NAME));
		Assert.assertTrue(myAccountPage.getCustomerEmail().contains(email));

		LOGGER.info("6. Click Edit Contact Information");
		editAccountInformationPage = myAccountPage.clickEditContactInformation();

		LOGGER.info("7. Revert Data");
		editAccountInformationPage.enterEmailByBrowser(TestConstants.MyAccountTestData.MODIFY_ACCOUNT_EMAIL,
				TestConstants.MyAccountTestData.SAFARI_ACCOUNT_EMAIL);
		editAccountInformationPage.enterFirstName(TestConstants.TestData.FIRST_NAME);
		editAccountInformationPage.enterCurrentPassword(TestConstants.MyAccountTestData.PASSWORD);
		editAccountInformationPage.clickSave();
	}

	@Test(groups = { TestConstants.TestNGGroups.MY_ACCOUNT }, description = "133896")
	public void myAccountSubscriptionsTest() {

		LOGGER.info("1. Navigate to landing page");
		LandingPage landingPage = navigateToLandingPage();

		LOGGER.info("2. Log In");
		SignInPage signInPage = landingPage.clickSignInButton();
		MyAccountPage myAccountPage = signInPage.mainUserLogin();
		Assert.assertNotNull(myAccountPage, "Account Dashboard did not display");

		LOGGER.info("3. My Subscriptions");
		MyAccountMySuscriptionsPage mySuscriptionsPage = myAccountPage.clickMySuscriptions();
		Assert.assertTrue(mySuscriptionsPage.isSubscriptionNameDisplayed(), "Subscription Name is not displayed");
		Assert.assertTrue(mySuscriptionsPage.isNextShipmentDateDisplayed(), "Next Shipment Date is not displayed");
		Assert.assertTrue(mySuscriptionsPage.isSubscriptionFrequencyDateDisplayed(),
				"Subscription Frequency Date is not displayed");
		Assert.assertTrue(mySuscriptionsPage.isManageSubscriptionButtonDisplayed(),
				"Manage Subscription button is not displayed");
		Assert.assertTrue(mySuscriptionsPage.isShippingAddressDisplayed(), "Shipping Address is not displayed");
		Assert.assertTrue(mySuscriptionsPage.isShippingMethodDisplayed(), "Shipping Method is not displayed");
		Assert.assertTrue(mySuscriptionsPage.isSubscribedProductDisplayed(), "Subscribed Product is not displayed");

		LOGGER.info("5. My Subscriptions Details");
		MyAccountManageSubscriptionPage suscriptionsDetailPage = mySuscriptionsPage.clickManageSubscription();
		Assert.assertTrue(suscriptionsDetailPage.isPauseCancelSubscriptionButtonDisplayed(),
				"Pause/Cancel Subscription button is not displayed");
		Assert.assertTrue(suscriptionsDetailPage.isManageShipmentButtonDisplayed(),
				"PManage Shipment button is not displayed");
		Assert.assertTrue(suscriptionsDetailPage.isBillingAddressDisplayed(), "Billing Address is not displayed");
	}

	@Test(groups = { TestConstants.TestNGGroups.MY_ACCOUNT }, description = "627698")
	public void myAccountCreateNewAccountTest() {

		LOGGER.info("1. Navigate to landing page");
		LandingPage landingPage = navigateToLandingPage();

		LOGGER.info("2. Click Sign In");
		SignInPage signInPage = landingPage.clickSignInButton();

		LOGGER.info("3. Click Create An Account");
		SignUpPage signUpPage = signInPage.clickonCreateAccountButton();

		LOGGER.info("4. Fill out new account information");
		LOGGER.info("5. Click Submit");
		MyAccountPage myAccountPage = signUpPage.submitSignUpInfo(MyAccountPage.class);

		LOGGER.info("Verify user is logged in");
		Assert.assertNotNull(myAccountPage, "User does not signed in");
	}

	@Test(groups = { TestConstants.TestNGGroups.MY_ACCOUNT }, description = "627699")
	public void myAccountCreateNewAccountAfterGuestCheckoutTest() {
		LOGGER.info("1. Navigate to landing page");
		LandingPage landingPage = navigateToLandingPage();
		Assert.assertNotNull(landingPage, "Failed to navigate to the landing page.");

		LOGGER.info("2. Select a coffee from grid view and add to cart");
		ShopCoffeePage shopCoffeePage = landingPage.clickShopCoffeeButton();
		CoffeeProductPage coffeeProductPage = shopCoffeePage.clickProductName(TestConstants.TestData.COFFEE_BRAND_NAME);
		coffeeProductPage.selectAGrind(TestConstants.TestData.GRIND);
		MiniCartContainerChunk miniCartContainer = coffeeProductPage.clickAddToCart();

		LOGGER.info("3. Select 'Proceed to Checkout'");
		CheckoutPage checkoutPage = miniCartContainer.clickCheckout();
		CheckoutShippingInfoPage shippingInfoPage = checkoutPage.clickContinueAsGuest();

		LOGGER.info("4. Complete Contact Information");
		VerifyYourAddressDetailsChunk verifyAddressChunk = shippingInfoPage.continueAfterFillingRequiredContactInfo();
		shippingInfoPage = verifyAddressChunk.clickEnteredAddressButton();

		LOGGER.info("5. Select ground shipping");
		CheckoutPaymentMethodPage paymentMethodPage = shippingInfoPage
				.setShippingMethod(TestConstants.TestData.SHIPPING_METHOD_GROUND);

		LOGGER.info("6. Use credit card for payment");
		CheckoutPlaceOrderPage placeOrderPage = paymentMethodPage.continueAfterFillingRequiredBillingInfo();

		LOGGER.info("7. Click 'Place Order'");
		CheckoutConfirmationPage confirmationPage = placeOrderPage.placeOrder();

		LOGGER.info("Verify Confirmation page is displayed");
		Assert.assertTrue(confirmationPage.getConfirmationMessage().contains("THANK YOU FOR YOUR PURCHASE!"),
				"Order was not placed");

		LOGGER.info("Order Placed: " + confirmationPage.getOrderNumber());

		LOGGER.info(
				"On Order confirmation page, create a new account for new customers. Enter email address, password, confirm password. ");
		confirmationPage.enterPassword(TestConstants.MyAccountTestData.PASSWORD);
		confirmationPage.enterConfirmPassword(TestConstants.MyAccountTestData.PASSWORD);

		LOGGER.info("Select Create Account");
		MyAccountPage myAccountPage = confirmationPage.createAccount();

		LOGGER.info("Verify account was created");
		Assert.assertNotNull(myAccountPage, "My Account page not found, account does not created");
	}

	@Test(groups = { TestConstants.TestNGGroups.MY_ACCOUNT }, description = "627700")
	public void myAccountChangePasswordTest() {

		LOGGER.info("1. Navigate to landing page");
		LandingPage landingPage = navigateToLandingPage();

		LOGGER.info("2. Click Sign In");
		SignInPage signInPage = landingPage.clickSignInButton();

		LOGGER.info("3. Click Create An Account");
		SignUpPage signUpPage = signInPage.clickonCreateAccountButton();

		LOGGER.info("4. Fill out new account information");
		LOGGER.info("5. Click Submit");
		MyAccountPage myAccountPage = signUpPage.submitSignUpInfo(MyAccountPage.class);
		String newAccountEmail = signUpPage.email;

		LOGGER.info("Verify user is logged in");
		Assert.assertNotNull(myAccountPage, "User does not signed in");

		LOGGER.info("6. From My Dashboard, click Settings");
		EditAccountInformationPage editAccountInformationPage = myAccountPage.clickSettings();

		LOGGER.info("7. Enter current password. Select checkbox for Change Password.");
		editAccountInformationPage.enterCurrentPassword(TestConstants.TestData.PASSWORD);
		editAccountInformationPage.changeCurrentPassword();

		LOGGER.info("8. Enter new password, Confirm password, Click Save");
		editAccountInformationPage.enterNewPassword("new" + TestConstants.MyAccountTestData.PASSWORD);
		editAccountInformationPage.enterConfirmPassword("new" + TestConstants.MyAccountTestData.PASSWORD);
		myAccountPage = editAccountInformationPage.clickSave();

		LOGGER.info("9. Log Out");
		landingPage = myAccountPage.getAccountMenu().signOut();

		LOGGER.info("10. Log back in using new password");
		signInPage = landingPage.clickSignInButton();
		MyAccountPage myAccountPageNew = signInPage.userLogin(newAccountEmail,
				"new" + TestConstants.MyAccountTestData.PASSWORD);

		LOGGER.info("User should be able to log in");
		Assert.assertNotNull(myAccountPageNew, "Use was not able to log in using new password");
	}

}
