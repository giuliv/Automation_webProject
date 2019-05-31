package com.applause.auto.mobile.test;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.applause.auto.framework.pageframework.device.DeviceViewFactory;
import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.pageframework.chunks.mobile.PeetsCardsTransferAmountChunk;
import com.applause.auto.pageframework.chunks.mobile.PeetsCardsTransferAmountWarningChunk;
import com.applause.auto.pageframework.testdata.TestConstants;
import com.applause.auto.pageframework.views.AccountHistoryView;
import com.applause.auto.pageframework.views.CheckInView;
import com.applause.auto.pageframework.views.DashboardView;
import com.applause.auto.pageframework.views.LandingView;
import com.applause.auto.pageframework.views.PaymentMethodsView;
import com.applause.auto.pageframework.views.PeetsCardsView;

public class PeetsCardsTest extends BaseTest {

	private LogController LOGGER = new LogController(PeetsCardsTest.class);

	@Test(groups = { TestConstants.TestNGGroups.PEETS_CARDS }, description = "625910")
	public void addValueToNewDigitalCard25SavedCC() {
		LOGGER.info("Launch the app and arrive at the first on boarding screen view");
		LandingView landingView = DeviceViewFactory.create(LandingView.class);

		DashboardView dashboardView = peetsMobileHelper.signIn(landingView, TestConstants.MyAccountTestData.EMAIL,
				TestConstants.MyAccountTestData.PASSWORD, DashboardView.class);
		Assert.assertNotNull(dashboardView, "Dashboard View does not displayed");

		LOGGER.info("Tap Peet's Card icon on bottom nav bar");
		PeetsCardsView peetsCardsView = dashboardView.getBottomNavigationMenu().peetsCards();

		LOGGER.info("User should be taken to peet's card screen");
		Assert.assertNotNull(peetsCardsView, "User does not taken to Peets Cards view");
		int oldBalance = Integer.parseInt(peetsCardsView.getBalance());

		LOGGER.info("Tap Add Value button");
		peetsCardsView.addValue();

		LOGGER.info("Tap pencil icon");
		PaymentMethodsView paymentMethodsView = peetsCardsView.edit();

		LOGGER.info("User should be taken to payment method screen");
		Assert.assertNotNull(paymentMethodsView, "User does not taken to payment method view");

		LOGGER.info("Select a saved credit card");
		peetsCardsView = paymentMethodsView.clickSavedPaymentMethod2(PeetsCardsView.class);

		LOGGER.info("User should return to add value to my peet's card screen");
		Assert.assertNotNull(paymentMethodsView, "User does taken to Peets Cards screen");

		LOGGER.info("$25 tile should be default highlighted");
		int cardAmount = 25;
		Assert.assertTrue(peetsCardsView.isAmountSelected("$" + cardAmount), "$25 tile does not highlighted");

		LOGGER.info("Tap Confirm Value button");
		peetsCardsView = peetsCardsView.confirm();
		int newBalance = Integer.parseInt(peetsCardsView.getBalance());

		LOGGER.info(
				"Make sure user is able to successfully add value to card and peet's card screen shows card balance of $25.00");
		Assert.assertEquals(newBalance - oldBalance, cardAmount, "Balance does not changed properly");

		LOGGER.info("Check account history");
		dashboardView = peetsCardsView.getBottomNavigationMenu().home();
		AccountHistoryView accountHistory = dashboardView.getAccountProfileMenu().accountHistory();

		LOGGER.info("Make sure it shows Peet's Card transaction details:\n" + "\n" + "* Peet's Card Load + $25.00\n"
				+ "\n" + "* Date [Month Day, Year]\n" + "\n");
		Assert.assertEquals(accountHistory.getTransactionDate(0),
				new SimpleDateFormat("MMM d, yyyy").format(new Date()), "Incorrect transaction date");
		Assert.assertEquals(accountHistory.getTransactionAmount(0).replace(" ", ""),
				"+$" + new DecimalFormat("0.00").format(cardAmount), "Incorrect transaction amount");

	}

	@Test(groups = { TestConstants.TestNGGroups.PEETS_CARDS }, description = "625916")
	public void reloadDigitalCard25SavedCC() {
		LOGGER.info("Launch the app and arrive at the first on boarding screen view");
		LandingView landingView = DeviceViewFactory.create(LandingView.class);

		DashboardView dashboardView = peetsMobileHelper.signIn(landingView, TestConstants.MyAccountTestData.EMAIL,
				TestConstants.MyAccountTestData.PASSWORD, DashboardView.class);
		Assert.assertNotNull(dashboardView, "Dashboard View does not displayed");

		LOGGER.info("Tap Check In icon on bottom nav bar");
		CheckInView checkInView = dashboardView.getBottomNavigationMenu().checkIn();

		LOGGER.info("User should be taken to check in screen");
		Assert.assertNotNull(checkInView, "User does not taken to Check In view");
		int oldBalance = Integer.parseInt(checkInView.getBalance());

		LOGGER.info("Tap Add Value button from Peet's Card Balance tile");
		checkInView.addValue();

		LOGGER.info("Tap pencil icon to view Payment Method screen");
		PaymentMethodsView paymentMethodsView = checkInView.edit();

		LOGGER.info("User should be taken to payment method screen");
		Assert.assertNotNull(paymentMethodsView, "User does not taken to payment method view");

		LOGGER.info("Select a saved credit card");
		checkInView = paymentMethodsView.clickSavedPaymentMethod2(CheckInView.class);

		LOGGER.info("User should return to add value to my peet's card screen");
		Assert.assertNotNull(paymentMethodsView, "User does taken to Peets Cards screen");

		LOGGER.info("$25 tile should be default highlighted");
		int cardAmount = 25;
		Assert.assertTrue(checkInView.isAmountSelected("$" + cardAmount), "$25 tile does not highlighted");

		LOGGER.info("Tap Confirm Value button");
		checkInView = checkInView.confirm();
		int newBalance = Integer.parseInt(checkInView.getBalance());

		LOGGER.info(
				"Make sure user is able to successfully add value to card and peet's card screen shows card balance of $25.00");
		Assert.assertEquals(newBalance - oldBalance, cardAmount, "Balance does not changed properly");

		LOGGER.info("Check account history");
		dashboardView = checkInView.getBottomNavigationMenu().home();
		AccountHistoryView accountHistory = dashboardView.getAccountProfileMenu().accountHistory();

		LOGGER.info("Make sure it shows Peet's Card transaction details:\n" + "\n" + "* Peet's Card Load + $25.00\n"
				+ "\n" + "* Date [Month Day, Year]\n" + "\n");
		Assert.assertEquals(accountHistory.getTransactionDate(0),
				new SimpleDateFormat("MMM d, yyyy").format(new Date()), "Incorrect transaction date");
		Assert.assertEquals(accountHistory.getTransactionAmount(0).replace(" ", ""),
				"+$" + new DecimalFormat("0.00").format(cardAmount), "Incorrect transaction amount");

	}

	@Test(groups = { TestConstants.TestNGGroups.PEETS_CARDS }, description = "625913")
	public void transferBalanceFromPhysicalCardDigitalCardWithNoBalance() {
		LOGGER.info("Launch the app and arrive at the first on boarding screen view");
		LandingView landingView = DeviceViewFactory.create(LandingView.class);

		DashboardView dashboardView = peetsMobileHelper.signIn(landingView, TestConstants.MyAccountTestData.EMAIL,
				TestConstants.MyAccountTestData.PASSWORD, DashboardView.class);
		softAssert.assertNotNull(dashboardView, "Dashboard View does not displayed");

		LOGGER.info("Tap Peet's Card icon from bottom nav bar");
		PeetsCardsView peetsCardsView = dashboardView.getBottomNavigationMenu().peetsCards();

		LOGGER.info("User should be taken to peet's card screen");
		softAssert.assertNotNull(peetsCardsView, "User does not taken to Peets card screen");

		LOGGER.info("Tap Transfer Value button");
		PeetsCardsTransferAmountChunk peetsCardsTransferAmountChunk = peetsCardsView.transferValue();

		LOGGER.info("User should be taken to transfer value overlay that appears from bottom");
		softAssert.assertNotNull(peetsCardsTransferAmountChunk,
				"User does not taken to transfer value overlay that appears from bottom");

		LOGGER.info("Tap into Card Number field");
		LOGGER.info("Enter invalid peet's card number");
		peetsCardsTransferAmountChunk.enterCardNumber("12341234123412");
		peetsCardsTransferAmountChunk.enterCardPin("9967");

		LOGGER.info("Tap Transfer Value button");
		PeetsCardsTransferAmountWarningChunk peetsCardsTransferAmountWarningChunk = peetsCardsTransferAmountChunk
				.transfer();

		LOGGER.info("Branded UI alert should display:\n" + "\n" + "Title: One last thing\n" + "\n"
				+ "When you transfer a card into the app, you will:\n" + "\n"
				+ "* Not be able to transfer the value back to the original card\n" + "\n"
				+ "* No longer be able to add funds to your physical card\n" + "\n"
				+ "* Be able to access the new value with your digital Peet's Card located in the app.\n" + "\n"
				+ "[Button] Cancel [Button] Continue\n");
		softAssert.assertNotNull(peetsCardsTransferAmountWarningChunk, "Branded UI alert does not display");
		softAssert.assertTrue(peetsCardsTransferAmountWarningChunk.getFormattedMessage().contains(TestConstants.MobileTestData.TRANSFER_ERROR), "Wrong warning message displayed");
		softAssert.assertTrue(peetsCardsTransferAmountWarningChunk.isContinueButtonDisplayed(),
				"Continue button does not displayed");
		softAssert.assertTrue(peetsCardsTransferAmountWarningChunk.isCancelButtonDisplayed(),
				"Cancel button does not displayed");

		LOGGER.info("Tap Continue button");
		peetsCardsTransferAmountWarningChunk.tapContinue();

		LOGGER.info("User should see branded UI error alert:\n" + "\n" + "Title: We couldn't process your transfer.\n"
				+ "\n" + "* Please check your card number and pin code and try again\n" + "\n"
				+ "* If there's no value remaining on the card you are trying to transfer, you won't be able to transfer value\n"
				+ "\n"
				+ "* If this issue persists, please contact Peet's customer service at cs@peets.com <mailto:cs@peets.com>\n"
				+ "\n" + "[Button] Cancel [Button] Try Again\n");
		softAssert.assertTrue(peetsCardsTransferAmountWarningChunk.getFormattedMessageCouldNotProcess().contains(TestConstants.MobileTestData.TRANSFER_PROCESS_ERROR), "Wrong process message displayed");
		softAssert.assertTrue(peetsCardsTransferAmountWarningChunk.isTryAgainButtonCouldNotProcessDisplayed(),
				"Try again button does not displayed");
		softAssert.assertTrue(peetsCardsTransferAmountWarningChunk.isCancelButtonCouldNotProcessDisplayed(),
				"Cancel button does not displayed");

		LOGGER.info("Tap Try Again button");
		peetsCardsTransferAmountChunk = peetsCardsTransferAmountWarningChunk
				.tapTryAgain(PeetsCardsTransferAmountChunk.class);

		LOGGER.info(
				"User should be directed to transfer value screen with card number and pin number fields still filled in");
		softAssert.assertNotNull(peetsCardsTransferAmountChunk, "User does not navigated to Transfer value screen");
		softAssert.assertEquals(peetsCardsTransferAmountChunk.getCardNumber(), "12341234123412",
				"Card number field not filled in");
		softAssert.assertEquals(peetsCardsTransferAmountChunk.getPinNumber(), "9967", "Pin number field not filled in");

		LOGGER.info("Enter valid peet's card number\n" + "\n" + "Tap done on numeric keypad\n");
		peetsCardsTransferAmountChunk.enterCardNumber("81001000000581");

		LOGGER.info("Tap Transfer Value button");
		peetsCardsTransferAmountWarningChunk = peetsCardsTransferAmountChunk.transfer();

		LOGGER.info("Tap Continue button");
		peetsCardsTransferAmountWarningChunk.tapContinue();

		// TODO Cannot be completed without new card used every run
		LOGGER.info("User should see branded UI loading alert:\n" + "\n"
				+ "Title: Transferring the value to your digital Peet's card...\n" + "\n"
				+ "* Digital peet's card image\n" + "\n" + "* Loading animation\n" + "\n"
				+ "* Gold check mark with text \"Done!\" should appear after successful value transfer\n" + "\n");

		LOGGER.info("Tap Done!");

		LOGGER.info("User should see main Peet's card screen with card balance reflecting amount that was transferred");

		LOGGER.info("Check account history");

		LOGGER.info("Make sure it shows Peet's Card transaction details:\n" + "\n" + "* Check No: web\n" + "\n"
				+ "* Transaction Type: Transfer Balances to Card\n" + "\n" + "* Activity: Added xx.00\n" + "\n"
				+ "* Balance: xx.xx\n" + "\n");

		softAssert.assertAll();
	}

	@Test(groups = { TestConstants.TestNGGroups.PEETS_CARDS }, description = "1026052")
	public void negativeTestTransferBalance() {
		LOGGER.info("Launch the app and arrive at the first on boarding screen view");
		LandingView landingView = DeviceViewFactory.create(LandingView.class);

		DashboardView dashboardView = peetsMobileHelper.signIn(landingView, TestConstants.MyAccountTestData.EMAIL,
				TestConstants.MyAccountTestData.PASSWORD, DashboardView.class);
		Assert.assertNotNull(dashboardView, "Dashboard View does not displayed");

		LOGGER.info("Tap Peet's Card icon from bottom nav bar");
		PeetsCardsView peetsCardsView = dashboardView.getBottomNavigationMenu().peetsCards();

		LOGGER.info("User should be taken to peet's card screen");
		Assert.assertNotNull(peetsCardsView, "User does not taken to Peets card screen");

		LOGGER.info("Tap Transfer Value button");
		PeetsCardsTransferAmountChunk peetsCardsTransferAmountChunk = peetsCardsView.transferValue();

		LOGGER.info("User should be taken to transfer value overlay that appears from bottom");
		Assert.assertNotNull(peetsCardsTransferAmountChunk,
				"User does not taken to transfer value overlay that appears from bottom");

		LOGGER.info("Tap into Card Number field");
		LOGGER.info("Enter valid peet's card number\n" + "\n" + "Tap done on numeric keypad\n");
		peetsCardsTransferAmountChunk.enterCardNumber("81001000000584");

		LOGGER.info("Enter invalid peet's card pin number");
		peetsCardsTransferAmountChunk.enterCardPin("1111");

		PeetsCardsTransferAmountWarningChunk peetsCardsTransferAmountWarningChunk = peetsCardsTransferAmountChunk
				.transfer();

		LOGGER.info("Branded UI alert should display:\n" + "\n" + "Title: One last thing\n" + "\n"
				+ "When you transfer a card into the app, you will:\n" + "\n"
				+ "* Not be able to transfer the value back to the original card\n" + "\n"
				+ "* No longer be able to add funds to your physical card\n" + "\n"
				+ "* Be able to access the new value with your digital Peet's Card located in the app.\n" + "\n"
				+ "[Button] Cancel [Button] Continue\n");
		Assert.assertNotNull(peetsCardsTransferAmountWarningChunk, "Branded UI alert does not display");
		Assert.assertEquals(peetsCardsTransferAmountWarningChunk.getFormattedMessage(),
				peetsCardsTransferAmountWarningChunk.getValidMessage(), "Wrong message displayed");
		Assert.assertTrue(peetsCardsTransferAmountWarningChunk.isContinueButtonDisplayed(),
				"Continue button does not displayed");
		Assert.assertTrue(peetsCardsTransferAmountWarningChunk.isCancelButtonDisplayed(),
				"Cancel button does not displayed");

		LOGGER.info("Tap Continue button");
		peetsCardsTransferAmountWarningChunk.tapContinue();

		LOGGER.info("User should see branded UI error alert:\n" + "\n" + "Title: We couldn't process your transfer\n"
				+ "\n" + "* Please check your card number and pin code and try again\n" + "\n"
				+ "* If there's no value remaining on the card you are trying to transfer, you won't be able to transfer value\n"
				+ "\n"
				+ "* If this issue persists, please contact Peet's customer service at cs@peets.com <mailto:cs@peets.com>\n"
				+ "\n" + "[Button] Cancel [Button] Try Again\n");
		Assert.assertEquals(peetsCardsTransferAmountWarningChunk.getFormattedMessageCouldNotProcess(),
				peetsCardsTransferAmountWarningChunk.getValidMessageCouldNotProcess(), "Wrong message displayed");
		Assert.assertTrue(peetsCardsTransferAmountWarningChunk.isTryAgainButtonCouldNotProcessDisplayed(),
				"Try again button does not displayed");
		Assert.assertTrue(peetsCardsTransferAmountWarningChunk.isCancelButtonCouldNotProcessDisplayed(),
				"Cancel button does not displayed");

		LOGGER.info("Tap Try Again button");
		peetsCardsTransferAmountChunk = peetsCardsTransferAmountWarningChunk
				.tapTryAgain(PeetsCardsTransferAmountChunk.class);

		LOGGER.info("Enter Card number of already added card with invalid PIN");
		peetsCardsTransferAmountChunk.enterCardNumber("81001000000581");

		peetsCardsTransferAmountWarningChunk = peetsCardsTransferAmountChunk.transfer();

		LOGGER.info("Branded UI alert should display:\n" + "\n" + "Title: One last thing\n" + "\n"
				+ "When you transfer a card into the app, you will:\n" + "\n"
				+ "* Not be able to transfer the value back to the original card\n" + "\n"
				+ "* No longer be able to add funds to your physical card\n" + "\n"
				+ "* Be able to access the new value with your digital Peet's Card located in the app.\n" + "\n"
				+ "[Button] Cancel [Button] Continue\n");
		Assert.assertNotNull(peetsCardsTransferAmountWarningChunk, "Branded UI alert does not display");
		Assert.assertEquals(peetsCardsTransferAmountWarningChunk.getFormattedMessage(),
				peetsCardsTransferAmountWarningChunk.getValidMessage(), "Wrong message displayed");
		Assert.assertTrue(peetsCardsTransferAmountWarningChunk.isContinueButtonDisplayed(),
				"Continue button does not displayed");
		Assert.assertTrue(peetsCardsTransferAmountWarningChunk.isCancelButtonDisplayed(),
				"Cancel button does not displayed");

		LOGGER.info("Tap Continue button");
		peetsCardsTransferAmountWarningChunk.tapContinue();

		LOGGER.info("User should see branded UI error alert:\n" + "\n" + "Title: We couldn't process your transfer\n"
				+ "\n" + "* Please check your card number and pin code and try again\n" + "\n"
				+ "* If there's no value remaining on the card you are trying to transfer, you won't be able to transfer value\n"
				+ "\n"
				+ "* If this issue persists, please contact Peet's customer service at cs@peets.com <mailto:cs@peets.com>\n"
				+ "\n" + "[Button] Cancel [Button] Try Again\n");
		Assert.assertEquals(peetsCardsTransferAmountWarningChunk.getFormattedMessageCouldNotProcess(),
				peetsCardsTransferAmountWarningChunk.getValidMessageCouldNotProcess(), "Wrong message displayed");
		Assert.assertTrue(peetsCardsTransferAmountWarningChunk.isTryAgainButtonCouldNotProcessDisplayed(),
				"Try again button does not displayed");
		Assert.assertTrue(peetsCardsTransferAmountWarningChunk.isCancelButtonCouldNotProcessDisplayed(),
				"Cancel button does not displayed");

	}

}
