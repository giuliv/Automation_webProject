package com.applause.auto.test.mobile.production_monitoring;

import java.lang.invoke.MethodHandles;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.Constants.MyAccountTestData;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.mobile.components.AllowLocationServicesPopupChunk;
import com.applause.auto.mobile.views.*;
import com.applause.auto.test.mobile.BaseTest;

public class ProductionOrderTest extends BaseTest {

	private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().getClass());

	@Test(groups = { TestNGGroups.ORDER, TestNGGroups.REGRESSION }, description = "11051488", enabled = true)
	public void placeOrderWithSVCTest() {
		logger.info("User is already signed in to app\n" + "User is on the checkout screen\n"
				+ "User continues this test case from previous test case (so user will have items added to order already)");

		LandingView landingView = this.create(LandingView.class);
		// TODO Change to prod account
		Constants.UserTestData account = MyAccountTestData.CHECKOUT_ACCOUNT;
		DashboardView dashboardView = testHelper.signIn(landingView, account.getUsername(), account.getPassword(),
				DashboardView.class);

		NearbySelectCoffeeBarView nearbySelectCoffeeBarView = dashboardView.getBottomNavigationMenu()
				.order(AllowLocationServicesPopupChunk.class)
				// TODO change to 99723 on prod
				.allowIfRequestDisplayed(NearbySelectCoffeeBarView.class).search("78717");
		String storeName = nearbySelectCoffeeBarView.getCoffeeStoreContainerChuck().getStoreName();
		// TODO fill store name on prod
		Assert.assertEquals("", "", "Incorrect store selected");
		NewOrderView orderView = nearbySelectCoffeeBarView.openDefault();

		// Ensure that correct store opened

		logger.info("Tap on Hot Coffee button from menu bar > All Hot coffee options are displayed");
		logger.info("Tap on Lattes under hot coffee bar");
		orderView.selectCategoryAndSubCategory("Hot Coffee", "Lattes");
		ProductDetailsView productDetailsView = orderView.selectProduct("Caffe Latte");

		logger.info("Verify: User is taken to Caffe Latte PDP page ");
		Assert.assertNotNull(productDetailsView, "User does not taken to Caffe latte PDP page");

		logger.info("Tab on Add to order button");
		orderView = productDetailsView.addToOrder(NewOrderView.class);

		logger.info("Verify: User is taken to order page");
		Assert.assertNotNull(orderView, "User does not taken to order page");

		logger.info(
				"Tap on FAB button on right bottom > Confirm coffee bar card displays [User sees confirm and change button]");
		CheckoutView checkoutView = orderView.checkout();

		logger.info("Verify: Checkout page is displays");
		Assert.assertNotNull(checkoutView, "Checkout page does nt displayed");

		logger.info("Scroll down checkout page > User sees place order option are highlighted. Tap Place order button");
		OrderConfirmationView orderConfirmaion = checkoutView.placeOrder(OrderConfirmationView.class);

		logger.info("Verify: User sees order confirmation page");
		Assert.assertNotNull(orderConfirmaion, "User does not see order confirmation view");

	}
}
