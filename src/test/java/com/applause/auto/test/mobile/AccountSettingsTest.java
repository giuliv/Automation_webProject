package com.applause.auto.test.mobile;

import static com.applause.auto.common.data.Constants.MobileTestData.CC_MASTER_NAME;
import static com.applause.auto.common.data.Constants.MobileTestData.CC_VISA_NAME;

import java.lang.invoke.MethodHandles;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.Constants.MobileTestData;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.integrations.annotation.testidentification.ApplauseTestCaseId;
import com.applause.auto.mobile.components.AccountMenuMobileChunk;
import com.applause.auto.mobile.views.*;

public class AccountSettingsTest extends BaseTest {
	private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().getClass());

	@Test(groups = { TestNGGroups.ACCOUNT_SETTINGS, TestNGGroups.REGRESSION }, description = "625928")
	@ApplauseTestCaseId({ "674509", "674508" })
	public void paymentMethodsTest() {

		logger.info("Launch the app and arrive at the first on boarding screen view");
		LandingView landingView = this.create(LandingView.class);

		DashboardView dashboardView = testHelper.createNewAccountWithDefaults(landingView);

		logger.info("Navigate to Payment Methods");
		AccountMenuMobileChunk accountProfileMenu = dashboardView.getAccountProfileMenu();
		PaymentMethodsView paymentMethodsView = accountProfileMenu.clickPaymentMethods();

		softAssert.assertEquals(paymentMethodsView.getPeetsCardHeader(), MobileTestData.PEETS_CARD_HEADER,
				"Peets Card header is not displayed");

		String savedPaymentMethodsHeaderText = MobileTestData.SAVED_PAYMENT_HEADER;
		softAssert.assertEquals(paymentMethodsView.getSavedPaymentHeader(), savedPaymentMethodsHeaderText,
				"Saved Payment Methods header is not displayed");

		logger.info("Click Peets Card");
		PeetsCardSettingsView peetsCardSettingsView = paymentMethodsView.clickPeetsCard();
		softAssert.assertNotNull(peetsCardSettingsView, "Peets Card Settings view is not displayed");

		logger.info("Go Back to Payment Methods");
		paymentMethodsView = peetsCardSettingsView.clickBackButton();

		// disabled because new account created
		// logger.info("Delete Test card if it was already added");
		// paymentMethodsView =
		// testHelper.deletePaymentMethodTestCardIfAdded(paymentMethodsView, CC_VISA_NAME);
		// paymentMethodsView =
		// testHelper.deletePaymentMethodTestCardIfAdded(paymentMethodsView, CC_AMEX_NAME);
		// paymentMethodsView =
		// testHelper.deletePaymentMethodTestCardIfAdded(paymentMethodsView, CC_DISCO_NAME);
		// paymentMethodsView =
		// testHelper.deletePaymentMethodTestCardIfAdded(paymentMethodsView, CC_MASTER_NAME);

		logger.info("Add New Payment Method VISA");
		AddNewCardView addNewCardView = paymentMethodsView.clickAddNewPayment();
		paymentMethodsView = addNewCardView.addNewCard(Constants.TestData.VISA_CC_NUMBER,
				Constants.TestData.VISA_CC_SECURITY_CODE, CC_VISA_NAME, Constants.MobileTestData.CC_EXP_DATE,
				MobileTestData.CC_ZIP);
		// TODO - Revert back to swiping and figure out why it's not working
		// for now, go back, and then forward

		paymentMethodsView.clickBackButton();
		// accountProfileMenu.clickPaymentMethods();

		// TODO - AMEX Cards are not working, review with Jyothi
		// logger.info("Add New Payment Method AMEX");
		// addNewCardView = paymentMethodsView.clickAddNewPayment();
		// paymentMethodsView =
		// addNewCardView.addNewCard(
		// Constants.TestData.AMEX_CC_NUM,
		// Constants.TestData.AMEX_CC_CODE,
		// MobileTestData.CC_AMEX_NAME,
		// Constants.MobileTestData.CC_EXP_DATE,
		// MobileTestData.CC_ZIP);

		logger.info("Add New Payment Method DISCO");
		addNewCardView = paymentMethodsView.clickAddNewPayment();
		paymentMethodsView = addNewCardView.addNewCard(Constants.TestData.DISCOVERY_CC_NUM,
				Constants.TestData.DISCOVERY_CC_CODE, MobileTestData.CC_DISCO_NAME,
				Constants.MobileTestData.CC_EXP_DATE, Constants.TestData.DISCOVERY_CC_ZIP);

		logger.info("Add New Payment Method MASTER");
		addNewCardView = paymentMethodsView.clickAddNewPayment();
		paymentMethodsView = addNewCardView.addNewCard(Constants.TestData.MASTER_CC_NUM,
				Constants.TestData.MASTER_CC_CODE, MobileTestData.CC_MASTER_NAME, Constants.MobileTestData.CC_EXP_DATE,
				MobileTestData.CC_ZIP);
		// TODO - Revert back to swiping and figure out why it's not working
		// for now, go back, and then forward
		// getDeviceControl().swipeAcrossScreenWithDirection(SwipeDirection.DOWN);
		paymentMethodsView.clickBackButton();
		accountProfileMenu.clickPaymentMethods();

		logger.info("Select a payment method (any card from step 6) to view card details");
		CreditCardDetailsView creditCardDetailsView = paymentMethodsView
				.clickSavedPaymentMethod(CreditCardDetailsView.class, CC_MASTER_NAME);

		softAssert.assertEquals(creditCardDetailsView.getHeader(), CC_MASTER_NAME, "CC Name does not match title");
		softAssert.assertEquals(creditCardDetailsView.getCreditCardName(), CC_MASTER_NAME, "CC Name does not match");
		softAssert.assertTrue(creditCardDetailsView.isDefaultCardTextPresent(), "Default Card text is not displayed");

		logger.info("Switch toggle to set as default payment on");
		creditCardDetailsView.setDefault();
		paymentMethodsView = creditCardDetailsView.saveCard();

		logger.info("The set as default setting is saved");
		creditCardDetailsView = paymentMethodsView.clickSavedPaymentMethod(CreditCardDetailsView.class, CC_MASTER_NAME);
		softAssert.assertTrue(creditCardDetailsView.isDefaultSelected(), "Card does not selected as default");

		logger.info("Tap back arrow to return to Payment Methods screen");
		paymentMethodsView = creditCardDetailsView.clickBackButton();

		logger.info("Select credit card that was added from step 5 to view card details");
		creditCardDetailsView = paymentMethodsView.clickSavedPaymentMethod(CreditCardDetailsView.class, CC_VISA_NAME);

		logger.info("VERIFY - The set as default toggle is off and shows it is no longer the default payment method");
		softAssert.assertFalse(creditCardDetailsView.isDefaultSelected(),
				CC_VISA_NAME + "Card remains selected as default");

		logger.info("Delete and Cancel Payment Method");
		creditCardDetailsView.clickDeleteCard();
		creditCardDetailsView = creditCardDetailsView.clickDeleteNo();
		softAssert.assertNotNull(creditCardDetailsView, "Did not remain on Credit Card Details view");

		logger.info("Delete Payment Method");
		creditCardDetailsView.clickDeleteCard();
		paymentMethodsView = creditCardDetailsView.clickDeleteYes();
		paymentMethodsView.clickBackButton();
		paymentMethodsView = accountProfileMenu.clickPaymentMethods();
		// softAssert.assertNotNull(paymentMethodsView, "Did not delete payment method");
		softAssert.assertFalse(paymentMethodsView.isPaymentMethodTestCardAdded(CC_VISA_NAME),
				"Did not delete payment method");

		softAssert.assertAll();
	}

	@Test(groups = { TestNGGroups.ACCOUNT_SETTINGS, TestNGGroups.REGRESSION,
			TestNGGroups.WEB_UI }, description = "625939")
	@ApplauseTestCaseId({ "625939", "674528" })
	public void socialEngagementTest() {
		logger.info("Launch the app and arrive at the first on boarding screen view");
		LandingView landingView = this.create(LandingView.class);

		DashboardView dashboardView = testHelper.signIn(landingView);

		logger.info("Navigate to Social Media icons and click Facebook icon");
		AccountMenuMobileChunk accountProfileMenu = dashboardView.getAccountProfileMenu();
		AccountMenuMobileChunk socialMedia = accountProfileMenu.clickFacebookIcon();
		Assert.assertTrue(socialMedia.isOnFacebookPage(), "Not On social Media URL");
		socialMedia.clickDoneButton();

		logger.info("Navigate to Social Media icons and click Instagram icon");
		socialMedia = accountProfileMenu.clickInstagramIcon();
		Assert.assertTrue(socialMedia.isOnInstagramPage(), "Not On social Media URL");
		socialMedia.clickDoneButton();

		logger.info("Navigate to Social Media icons and click Twitter icon");
		socialMedia = accountProfileMenu.clickTwitterIcon();
		Assert.assertTrue(socialMedia.isOnTwitterPage(), "Not On social Media URL");
		socialMedia.clickDoneButton();
	}
}
