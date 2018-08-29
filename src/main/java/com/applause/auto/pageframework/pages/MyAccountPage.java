package com.applause.auto.pageframework.pages;

import java.lang.invoke.MethodHandles;

import org.openqa.selenium.NoSuchElementException;

import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.framework.pageframework.web.AbstractPage;
import com.applause.auto.framework.pageframework.web.ChunkFactory;
import com.applause.auto.framework.pageframework.web.PageFactory;
import com.applause.auto.framework.pageframework.web.WebElementLocator;
import com.applause.auto.framework.pageframework.web.factory.WebDesktopImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebPhoneImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebTabletImplementation;
import com.applause.auto.framework.pageframework.webcontrols.Button;
import com.applause.auto.framework.pageframework.webcontrols.Image;
import com.applause.auto.framework.pageframework.webcontrols.Link;
import com.applause.auto.framework.pageframework.webcontrols.Text;
import com.applause.auto.pageframework.chunks.MainMenuChunk;

@WebDesktopImplementation(MyAccountPage.class)
@WebTabletImplementation(MyAccountPage.class)
@WebPhoneImplementation(MyAccountPage.class)
public class MyAccountPage extends AbstractPage {

	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	@Override
	protected void waitUntilVisible() {
		dismissPopup();
		syncHelper.waitForElementToAppear(getViewSignature());
	}

	/*
	 * Public Actions
	 */

	/**
	 * Clicks on LoginMenu
	 * 
	 * @return LoginPage
	 */
	public String getWelcomeMessage() {
		LOGGER.info("Getting welcome message for verification");
		return getViewSignature().getText();
	}

	/**
	 * Click Payment Button
	 *
	 * @return PaymentMethodsPage
	 */
	public PaymentMethodsPage clickPayment() {
		LOGGER.info("Clicking Payment button");
		getPaymentButton().click();
		return PageFactory.create(PaymentMethodsPage.class);
	}

	/**
	 * Verify My Subscription Section is Displayed
	 *
	 * @return boolean
	 */
	public boolean isMySubscriptionsCardDisplayed() {
		LOGGER.info("Verifying My Subscriptions card is present");
		return getSubscriptionsSection().isDisplayed();
	}

	/**
	 * Verify Subscriptions Manage Link is Displayed
	 *
	 * @return boolean
	 */
	public boolean isManageSubscriptionsLinkDisplayed() {
		LOGGER.info("Verifying Manage Subscriptions link is present");
		return getSubscriptionsManageLink().isDisplayed();
	}

	/**
	 * Verify Subscriptions Edit Button is Displayed
	 *
	 * @return boolean
	 */
	public boolean isEditSubscriptionsButtonDisplayed() {
		LOGGER.info("Verifying Edit Subscriptions button is present");
		return getSubscriptionsEditButton().isDisplayed();
	}

	/**
	 * Verify Order History Section is Displayed
	 *
	 * @return boolean
	 */
	public boolean isOrderHistoryCardDisplayed() {
		LOGGER.info("Verifying Order History card is present");
		return getOrderHistorySection().isDisplayed();
	}

	/**
	 * Verify View All Orders Link is Displayed
	 *
	 * @return boolean
	 */
	public boolean isViewAllOrdersLinkDisplayed() {
		LOGGER.info("Verifying View all orders link is present");
		return getViewAllOrdersLink().isDisplayed();
	}

	/**
	 * Verify Order Rating is Displayed
	 *
	 * @return boolean
	 */
	public boolean isOrderRatingDisplayed() {
		LOGGER.info("Verifying Order Rating is present");
		return getOrderRatingButton().isDisplayed();
	}

	/**
	 * Verify Payment Section is Displayed
	 *
	 * @return boolean
	 */
	public boolean isPaymentCardDisplayed() {
		LOGGER.info("Verifying Payment card is present");
		return getPaymentSection().isDisplayed();
	}

	/**
	 * Verify Payment Manage Cards Link is Displayed
	 *
	 * @return boolean
	 */
	public boolean isPaymentManageCardsLinkDisplayed() {
		LOGGER.info("Verifying Payment Manage Cards link is present");
		return getPaymentManageCardsLink().isDisplayed();
	}

	/**
	 * Verify Payment Card Image is Displayed
	 *
	 * @return boolean
	 */
	public boolean isPaymentCardImageDisplayed() {
		LOGGER.info("Verifying Payment Card image is present");
		return getPaymentDetailsImage().isDisplayed();
	}

	/**
	 * Verify Peet's Card Section is Displayed
	 *
	 * @return boolean
	 */
	public boolean isPeetsCardsCardDisplayed() {
		LOGGER.info("Verifying Peets Cards card is present");
		return getPeetsCardSection().isDisplayed();
	}

	/**
	 * Verify Peet's Card Manage Cards Link is Displayed
	 *
	 * @return boolean
	 */
	public boolean isPeetsCardManageCardsLinkDisplayed() {
		LOGGER.info("Verifying Peets Card Manage Cards link is present");
		return getPeetsCardManageCardsLink().isDisplayed();
	}

	/**
	 * Verify Settings Section is Displayed
	 *
	 * @return boolean
	 */
	public boolean isSettingsCardDisplayed() {
		LOGGER.info("Verifying Settings card is present");
		return getSettingsSection().isDisplayed();
	}

	/**
	 * Verifying Go To Address Book Link is Displayed
	 *
	 * @return boolean
	 */
	public boolean isGoToAddressBookLinkDisplayed() {
		LOGGER.info("Verifying Go To Address Book link is present");
		return getGoToAddressBookLink().isDisplayed();
	}

	/**
	 * Verify Billing Address Section is Displayed
	 *
	 * @return boolean
	 */
	public boolean isBillingAddressDisplayed() {
		LOGGER.info("Verifying Billing Address is present");
		return getBillingAddressSection().isDisplayed();
	}

	/**
	 * Verify Billing Address Edit Link is Displayed
	 *
	 * @return boolean
	 */
	public boolean isBillingAddressEditLinkDisplayed() {
		LOGGER.info("Verifying Billing Address Edit link is present");
		return getBillingAddressEditLink().isDisplayed();
	}

	/**
	 * Click Edit Billing Address
	 *
	 * @return EditBillingAddressPage
	 */
	public EditBillingAddressPage clickEditBillingAddress() {
		LOGGER.info("Clicking Edit Billing Address");
		getBillingAddressEditLink().click();
		return PageFactory.create(EditBillingAddressPage.class);
	}

	/**
	 * Verify Shipping Address Section is Displayed
	 *
	 * @return boolean
	 */
	public boolean isShippingAddressDisplayed() {
		LOGGER.info("Verifying Shipping Address is present");
		return getShippingAddressSection().isDisplayed();
	}

	/**
	 * Gets Main Menu
	 *
	 * @return MainMenuChunk
	 */
	public MainMenuChunk getMainMenu() {
		LOGGER.info("Getting Main Menu");
		return ChunkFactory.create(MainMenuChunk.class, this, "");
	}

	/*
	 * Protected Getters
	 */

	@WebElementLocator(webDesktop = ".welcome-msg .title")
	protected Text getViewSignature() {
		return new Text(this, getLocator(this, "getViewSignature"));
	}

	@WebElementLocator(webDesktop = "body > div.wrapper > div > div.main-container.col2-left-layout > div > div.col-left.sidebar.col-left-first > div.left-nav.left-nav-account > ul > li:nth-child(4) > a")
	protected Button getPaymentButton() {
		return new Button(this, getLocator(this, "getPaymentButton"));
	}

	@WebElementLocator(webDesktop = ".close-button")
	protected Button getDismissPopupButton() {
		return new Button(this, getLocator(this, "getDismissPopupButton"));
	}

	@WebElementLocator(webDesktop = "div#peets-dashboard-subscription.box-account.peets-account-block")
	protected Text getSubscriptionsSection() {
		return new Text(this, getLocator(this, "getSubscriptionsSection"));
	}

	@WebElementLocator(webDesktop = "#peets-dashboard-subscription > div.box-head > a")
	protected Link getSubscriptionsManageLink() {
		return new Link(this, getLocator(this, "getSubscriptionsManageLink"));
	}

	@WebElementLocator(webDesktop = "//a[text()='Edit']")
	protected Button getSubscriptionsEditButton() {
		return new Button(this, getLocator(this, "getSubscriptionsEditButton"));
	}

	@WebElementLocator(webDesktop = "div#cutsomer-dashboard-recent-orders.box-account.box-recent.peets-account-block")
	protected Text getOrderHistorySection() {
		return new Text(this, getLocator(this, "getOrderHistorySection"));
	}

	@WebElementLocator(webDesktop = "#cutsomer-dashboard-recent-orders > div.box-head > a")
	protected Link getViewAllOrdersLink() {
		return new Link(this, getLocator(this, "getViewAllOrdersLink"));
	}

	@WebElementLocator(webDesktop = "td.review-col")
	protected Button getOrderRatingButton() {
		return new Button(this, getLocator(this, "getOrderRatingButton"));
	}

	@WebElementLocator(webDesktop = "div#customer-dashboard-payments.box-account.box-recent.peets-account-block")
	protected Text getPaymentSection() {
		return new Text(this, getLocator(this, "getPaymentSection"));
	}

	@WebElementLocator(webDesktop = "#customer-dashboard-payments > div.box-head > a")
	protected Link getPaymentManageCardsLink() {
		return new Link(this, getLocator(this, "getPaymentManageCardsLink"));
	}

	@WebElementLocator(webDesktop = "div.details")
	protected Image getPaymentDetailsImage() {
		return new Image(this, getLocator(this, "getPaymentDetailsImage"));
	}

	@WebElementLocator(webDesktop = "div#customer-dashboard-peets-cards.box-account.box-recent.peets-account-block")
	protected Text getPeetsCardSection() {
		return new Text(this, getLocator(this, "getPeetsCardSection"));
	}

	@WebElementLocator(webDesktop = "#customer-dashboard-peets-cards > div.box-head > a")
	protected Link getPeetsCardManageCardsLink() {
		return new Link(this, getLocator(this, "getPeetsCardManageCardsLink"));
	}

	@WebElementLocator(webDesktop = "div#customer-dashboard-settings.box-account")
	protected Text getSettingsSection() {
		return new Text(this, getLocator(this, "getSettingsSection"));
	}

	@WebElementLocator(webDesktop = "#customer-dashboard-settings > div.box-head > a")
	protected Link getGoToAddressBookLink() {
		return new Link(this, getLocator(this, "getGoToAddressBookLink"));
	}

	@WebElementLocator(webDesktop = "#customer-dashboard-settings > div.box-content > div > div:nth-child(2)")
	protected Text getBillingAddressSection() {
		return new Text(this, getLocator(this, "getBillingAddressSection"));
	}

	@WebElementLocator(webDesktop = "#customer-dashboard-settings > div.box-content > div > div:nth-child(2) > div.head-block > a")
	protected Link getBillingAddressEditLink() {
		return new Link(this, getLocator(this, "getBillingAddressEditLink"));
	}

	@WebElementLocator(webDesktop = "#customer-dashboard-settings > div.box-content > div > div:nth-child(3)")
	protected Text getShippingAddressSection() {
		return new Text(this, getLocator(this, "getShippingAddressSection"));
	}

	private void dismissPopup() {
		try {
			LOGGER.info("Attempting to dismiss popup");
			syncHelper.waitForElementToAppear(getDismissPopupButton());
			getDismissPopupButton().click();
		} catch (NoSuchElementException e) {
			LOGGER.info("Popup not found, moving on");
		}
	}
}
