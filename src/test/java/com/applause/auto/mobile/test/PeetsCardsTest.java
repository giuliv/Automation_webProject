package com.applause.auto.mobile.test;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.applause.auto.framework.pageframework.device.DeviceViewFactory;
import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.pageframework.testdata.TestConstants;
import com.applause.auto.pageframework.views.AccountHistoryView;
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
		Assert.assertEquals(accountHistory.getTransactionAmount(0), "+$" + new DecimalFormat("0.00").format(cardAmount),
				"Incorrect transaction amount");

	}

}
