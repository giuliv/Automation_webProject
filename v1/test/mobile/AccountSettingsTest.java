package com.applause.auto.test.mobile;

import org.testng.annotations.Test;

import com.applause.auto.framework.pageframework.device.DeviceViewFactory;
import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.pageframework.chunks.AccountMenuMobileChunk;
import com.applause.auto.pageframework.helpers.MobileHelper;
import com.applause.auto.pageframework.testdata.TestConstants;
import com.applause.auto.pageframework.views.AddNewCardView;
import com.applause.auto.pageframework.views.CreditCardDetailsView;
import com.applause.auto.pageframework.views.DashboardView;
import com.applause.auto.pageframework.views.LandingView;
import com.applause.auto.pageframework.views.PaymentMethodsView;
import com.applause.auto.pageframework.views.PeetsCardSettingsView;

public class AccountSettingsTest extends BaseTest {
	private LogController LOGGER = new LogController(OnboardingSlidesTest.class);

	@Test(groups = { TestConstants.TestNGGroups.ACCOUNT_SETTINGS }, description = "625928")
	public void paymentMethodsTest() {

		LOGGER.info("Launch the app and arrive at the first on boarding screen view");
		LandingView landingView = DeviceViewFactory.create(LandingView.class);

		DashboardView dashboardView = peetsMobileHelper.signIn(landingView);

		LOGGER.info("Navigate to Payment Methods");
		AccountMenuMobileChunk accountProfileMenu = dashboardView.getAccountProfileMenu();
		PaymentMethodsView paymentMethodsView = accountProfileMenu.clickPaymentMethods();
		softAssert.assertEquals(paymentMethodsView.getPeetsCardHeader(), TestConstants.MobileTestData.PEETS_CARD_HEADER,
				"Peets Card header is not displayed");
		softAssert.assertEquals(paymentMethodsView.getSavedPaymentHeader(),
				TestConstants.MobileTestData.SAVED_PAYMENT_HEADER, "Saved Payment Methods header is not displayed");

		LOGGER.info("Click Peets Card");
		PeetsCardSettingsView peetsCardSettingsView = paymentMethodsView.clickPeetsCard();
		softAssert.assertNotNull(peetsCardSettingsView, "Peets Card Settings view is not displayed");

		LOGGER.info("Go Back to Payment Methods");
		paymentMethodsView = peetsCardSettingsView.clickBackButton();

		LOGGER.info("Add New Payment Method");
		AddNewCardView addNewCardView = paymentMethodsView.clickAddNewPayment();
		addNewCardView.enterCardNumber(TestConstants.MobileTestData.CC_NUM);
		addNewCardView.enterExpDate(TestConstants.MobileTestData.CC_EXP_DATE);
		addNewCardView.enterCvvCode(TestConstants.MobileTestData.CC_CVV);
		addNewCardView.enterZipCode(TestConstants.MobileTestData.CC_ZIP);
		addNewCardView.enterCardName(TestConstants.MobileTestData.CC_NAME);
		addNewCardView.selectMakeDefault();
		paymentMethodsView = addNewCardView.saveCard();
		MobileHelper.scrollUp(1);

		LOGGER.info("Click Payment Method");
		CreditCardDetailsView creditCardDetailsView = paymentMethodsView
				.clickSavedPaymentMethod(CreditCardDetailsView.class);
		softAssert.assertEquals(creditCardDetailsView.getHeader(), TestConstants.MobileTestData.CC_NAME,
				"CC Name does not match title");
		softAssert.assertEquals(creditCardDetailsView.getCreditCardName(), TestConstants.MobileTestData.CC_NAME,
				"CC Name does not match");
		softAssert.assertTrue(creditCardDetailsView.isDefaultCardTextPresent(), "Default Card text is not displayed");

		LOGGER.info("Edit Payment Method");
		creditCardDetailsView.enterExpDate(TestConstants.MobileTestData.CC_MODIFIED_EXP_DATE);
		paymentMethodsView = creditCardDetailsView.saveCard();

		LOGGER.info("Delete and Cancel Payment Method");
		creditCardDetailsView = paymentMethodsView.clickSavedPaymentMethod(CreditCardDetailsView.class);
		softAssert.assertEquals(creditCardDetailsView.getExpDate(), TestConstants.MobileTestData.CC_MODIFIED_EXP_DATE,
				"Exp date did not update");
		creditCardDetailsView.clickDeleteCard();
		creditCardDetailsView = creditCardDetailsView.clickDeleteNo();
		softAssert.assertNotNull(creditCardDetailsView, "Did not remain on Credit Card Details view");

		LOGGER.info("Delete Payment Method");
		creditCardDetailsView.clickDeleteCard();
		paymentMethodsView = creditCardDetailsView.clickDeleteYes();
		softAssert.assertNotNull(paymentMethodsView, "Did not delete payment method");

		softAssert.assertAll();
	}
}
