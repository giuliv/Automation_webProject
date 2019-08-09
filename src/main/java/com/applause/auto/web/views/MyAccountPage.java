package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Image;
import com.applause.auto.pageobjectmodel.elements.Link;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.web.components.AccountMenuChunk;
import com.applause.auto.web.components.MainMenuChunk;
import com.applause.auto.web.helpers.WebHelper;
import java.lang.invoke.MethodHandles;

@Implementation(is = MyAccountPage.class, on = Platform.WEB_DESKTOP)
@Implementation(is = MyAccountPage.class, on = Platform.WEB_MOBILE_TABLET)
@Implementation(is = MyAccountPage.class, on = Platform.WEB_MOBILE_PHONE)
public class MyAccountPage extends BaseComponent {

	/*
	 * Public Actions
	 */

	/**
	 * Clicks on LoginMenu
	 * 
	 * @return LoginPage
	 */
	public String getWelcomeMessage() {
		logger.info("Getting welcome message for verification");
		return getViewSignature.getCurrentText();
	}

	/**
	 * Click Payment Button
	 *
	 * @return PaymentMethodsPage
	 */
	public PaymentMethodsPage clickPayment() {
		logger.info("Clicking Payment button");
		getPaymentButton.click();
		return ComponentFactory.create(PaymentMethodsPage.class);
	}

	/**
	 * Verify My Subscription Section is Displayed
	 *
	 * @return boolean
	 */
	public boolean isMySubscriptionsCardDisplayed() {
		logger.info("Verifying My Subscriptions card is present");
		return getSubscriptionsSection.isDisplayed();
	}

	/**
	 * Verify Subscriptions Manage Link is Displayed
	 *
	 * @return boolean
	 */
	public boolean isManageSubscriptionsLinkDisplayed() {
		logger.info("Verifying Manage Subscriptions link is present");
		return getSubscriptionsManageLink.isDisplayed();
	}

	/**
	 * Verify Subscriptions Edit Button is Displayed
	 *
	 * @return boolean
	 */
	public boolean isEditSubscriptionsButtonDisplayed() {
		logger.info("Verifying Edit Subscriptions button is present");
		return getSubscriptionsEditButton.isDisplayed();
	}

	/**
	 * Verify Order History Section is Displayed
	 *
	 * @return boolean
	 */
	public boolean isOrderHistoryCardDisplayed() {
		logger.info("Verifying Order History card is present");
		return getOrderHistorySection.isDisplayed();
	}

	/**
	 * Verify View All Orders Link is Displayed
	 *
	 * @return boolean
	 */
	public boolean isViewAllOrdersLinkDisplayed() {
		logger.info("Verifying View all orders link is present");
		return getViewAllOrdersLink.isDisplayed();
	}

	/**
	 * Verify Order Rating is Displayed
	 *
	 * @return boolean
	 */
	public boolean isOrderRatingDisplayed() {
		logger.info("Verifying Order Rating is present");
		return getOrderRatingButton.isDisplayed();
	}

	/**
	 * Verify Payment Section is Displayed
	 *
	 * @return boolean
	 */
	public boolean isPaymentCardDisplayed() {
		logger.info("Verifying Payment card is present");
		return getPaymentSection.isDisplayed();
	}

	/**
	 * Verify Payment Manage Cards Link is Displayed
	 *
	 * @return boolean
	 */
	public boolean isPaymentManageCardsLinkDisplayed() {
		logger.info("Verifying Payment Manage Cards link is present");
		return getPaymentManageCardsLink.isDisplayed();
	}

	/**
	 * Verify Payment Card Image is Displayed
	 *
	 * @return boolean
	 */
	public boolean isPaymentCardImageDisplayed() {
		logger.info("Verifying Payment Card image is present");
		return getPaymentDetailsImage.isDisplayed();
	}

	/**
	 * Verify Peet's Card Section is Displayed
	 *
	 * @return boolean
	 */
	public boolean isPeetsCardsCardDisplayed() {
		logger.info("Verifying Peets Cards card is present");
		return getPeetsCardSection.isDisplayed();
	}

	/**
	 * Verify Peet's Card Manage Cards Link is Displayed
	 *
	 * @return boolean
	 */
	public boolean isPeetsCardManageCardsLinkDisplayed() {
		logger.info("Verifying Peets Card Manage Cards link is present");
		return getPeetsCardManageCardsLink.isDisplayed();
	}

	/**
	 * Verify Settings Section is Displayed
	 *
	 * @return boolean
	 */
	public boolean isSettingsCardDisplayed() {
		logger.info("Verifying Settings card is present");
		return getSettingsSection.isDisplayed();
	}

	/**
	 * Verifying Go To Address Book Link is Displayed
	 *
	 * @return boolean
	 */
	public boolean isGoToAddressBookLinkDisplayed() {
		logger.info("Verifying Go To Address Book link is present");
		return getGoToAddressBookLink.isDisplayed();
	}

	/**
	 * Verify Billing Address Section is Displayed
	 *
	 * @return boolean
	 */
	public boolean isBillingAddressDisplayed() {
		logger.info("Verifying Billing Address is present");
		return getBillingAddressSection.isDisplayed();
	}

	/**
	 * Verify Billing Address Edit Link is Displayed
	 *
	 * @return boolean
	 */
	public boolean isBillingAddressEditLinkDisplayed() {
		logger.info("Verifying Billing Address Edit link is present");
		return getBillingAddressEditLink.isDisplayed();
	}

	/**
	 * Click Edit Billing Address
	 *
	 * @return EditBillingAddressPage
	 */
	public EditBillingAddressPage clickEditBillingAddress() {
		logger.info("Clicking Edit Billing Address");
		getBillingAddressEditLink.click();
		return ComponentFactory.create(EditBillingAddressPage.class);
	}

	/**
	 * Verify Shipping Address Section is Displayed
	 *
	 * @return boolean
	 */
	public boolean isShippingAddressDisplayed() {
		logger.info("Verifying Shipping Address is present");
		return getShippingAddressSection.isDisplayed();
	}

	/**
	 * Click Edit Shipping Address
	 *
	 * @return EditShippingAddressPage
	 */
	public EditShippingAddressPage clickEditShippingAddress() {
		logger.info("Clicking Edit Shipping Address");
		getShippingAddressEditLink.click();
		return ComponentFactory.create(EditShippingAddressPage.class);
	}

	/**
	 * Gets Main Menu
	 *
	 * @return MainMenuChunk
	 */
	public MainMenuChunk getMainMenu() {
		logger.info("Getting Main Menu");
		return ComponentFactory.create(MainMenuChunk.class, this, "");
	}

	/**
	 * Click My Suscriptions
	 *
	 * @return MyAccountMySuscriptionsPage
	 */
	public MyAccountMySuscriptionsPage clickMySuscriptions() {
		logger.info("Clicking My Suscriptions");
		getMySuscriptionsLink.click();
		return ComponentFactory.create(MyAccountMySuscriptionsPage.class);
	}

	/**
	 * Click Peets Card Tab
	 *
	 * @return MyAccountPeetsCardPage
	 */
	public MyAccountPeetsCardPage clickPeetsCardsTab() {
		logger.info("Clicking Peet's Cards button");
		getPeetsCardButton.click();
		return ComponentFactory.create(MyAccountPeetsCardPage.class);
	}

	/**
	 * Click My Orders Tab
	 *
	 * @return MyAccountMyOrdersPage
	 */
	public MyAccountMyOrdersPage clickMyOrdersTab() {
		logger.info("Clicking My Orders button");
		getMyOrdersButton.click();
		return ComponentFactory.create(MyAccountMyOrdersPage.class);
	}

	/**
	 * Click Settings
	 *
	 * @return EditAccountInformationPage
	 */
	public EditAccountInformationPage clickSettings() {
		logger.info("Clicking Settings");
		getSettingButton.click();
		return ComponentFactory.create(EditAccountInformationPage.class);
	}

	/**
	 * Get Customer Name
	 *
	 * @return String
	 */
	public String getCustomerName() {
		logger.info("Getting Customer Name");
		return getCustomerNameText.getText();
	}

	/**
	 * Get Customer Email
	 *
	 * @return String
	 */
	public String getCustomerEmail() {
		logger.info("Getting Customer Email");
		return getCustomerEmailText.getText();
	}

	/**
	 * Click Edit Contact Information
	 *
	 * @return EditAccountInformationPage
	 */
	public EditAccountInformationPage clickEditContactInformation() {
		logger.info("Clicking Edit Contact Information");
		getContactInformationEditLink.click();
		return ComponentFactory.create(EditAccountInformationPage.class);
	}

	/**
	 * Gets account menu.
	 *
	 * @return the account menu
	 */
	public AccountMenuChunk getAccountMenu() {
		return ComponentFactory.create(AccountMenuChunk.class, this, "");
	}

	/**
	 * Dismiss popup.
	 */
	public void dismissPopup() {
		try {
			logger.info("Attempting to dismiss popup");
			getDismissPopupButton.click();
		} catch (Exception e) {
			logger.info("Popup not found, moving on");
		}
	}

	/*
	 * Protected Getters
	 */

	@Locate(jQuery = ".welcome-msg .title", on = Platform.WEB_DESKTOP)
	protected Text getViewSignature;

	@Locate(jQuery = "[title='Payment']", on = Platform.WEB_DESKTOP)
	protected Button getPaymentButton;

	@Locate(jQuery = ".close-button", on = Platform.WEB_DESKTOP)
	protected Button getDismissPopupButton;

	@Locate(jQuery = "div#peets-dashboard-subscription.box-account.peets-account-block", on = Platform.WEB_DESKTOP)
	protected Text getSubscriptionsSection;

	@Locate(jQuery = "#peets-dashboard-subscription > div.box-head > a", on = Platform.WEB_DESKTOP)
	protected Link getSubscriptionsManageLink;

	@Locate(xpath = "//a[text()='Edit']", on = Platform.WEB_DESKTOP)
	protected Button getSubscriptionsEditButton;

	@Locate(jQuery = "div#cutsomer-dashboard-recent-orders.box-account.box-recent.peets-account-block", on = Platform.WEB_DESKTOP)
	protected Text getOrderHistorySection;

	@Locate(jQuery = "#cutsomer-dashboard-recent-orders > div.box-head > a", on = Platform.WEB_DESKTOP)
	protected Link getViewAllOrdersLink;

	@Locate(jQuery = "td.review-col", on = Platform.WEB_DESKTOP)
	protected Button getOrderRatingButton;

	@Locate(jQuery = "div#customer-dashboard-payments.box-account.box-recent.peets-account-block", on = Platform.WEB_DESKTOP)
	protected Text getPaymentSection;

	@Locate(jQuery = "#customer-dashboard-payments > div.box-head > a", on = Platform.WEB_DESKTOP)
	protected Link getPaymentManageCardsLink;

	@Locate(jQuery = "div.details", on = Platform.WEB_DESKTOP)
	protected Image getPaymentDetailsImage;

	@Locate(jQuery = "div#customer-dashboard-peets-cards.box-account.box-recent.peets-account-block", on = Platform.WEB_DESKTOP)
	protected Text getPeetsCardSection;

	@Locate(jQuery = "#customer-dashboard-peets-cards > div.box-head > a", on = Platform.WEB_DESKTOP)
	protected Link getPeetsCardManageCardsLink;

	@Locate(jQuery = "div#customer-dashboard-settings.box-account", on = Platform.WEB_DESKTOP)
	protected Text getSettingsSection;

	@Locate(jQuery = "#customer-dashboard-settings > div.box-head > a", on = Platform.WEB_DESKTOP)
	protected Link getGoToAddressBookLink;

	@Locate(jQuery = "#customer-dashboard-settings > div.box-content > div > div:nth-child(2)", on = Platform.WEB_DESKTOP)
	protected Text getBillingAddressSection;

	@Locate(jQuery = "#customer-dashboard-settings > div.box-content > div > div:nth-child(2) > div.head-block > a", on = Platform.WEB_DESKTOP)
	protected Link getBillingAddressEditLink;

	@Locate(jQuery = "#customer-dashboard-settings > div.box-content > div > div:nth-child(3)", on = Platform.WEB_DESKTOP)
	protected Text getShippingAddressSection;

	@Locate(jQuery = "[title='My Subscriptions']", on = Platform.WEB_DESKTOP)
	protected Link getMySuscriptionsLink;

	@Locate(jQuery = "#customer-dashboard-settings > div.box-content > div > div:nth-child(3) > div.head-block > a", on = Platform.WEB_DESKTOP)
	protected Link getShippingAddressEditLink;

	@Locate(xpath = "//*[contains(@class,\"sidebar\")]//*[contains(@title,\"Peet's Cards\")]", on = Platform.WEB_DESKTOP)
	protected Button getPeetsCardButton;

	@Locate(jQuery = ".sidebar [title='Order History']", on = Platform.WEB_DESKTOP)
	protected Button getMyOrdersButton;

	@Locate(jQuery = ".sidebar [title='Settings']", on = Platform.WEB_DESKTOP)
	protected Button getSettingButton;

	@Locate(jQuery = "#customer-dashboard-settings > div.box-content > div > div:nth-child(1) > div.details > p:nth-child(1)", on = Platform.WEB_DESKTOP)
	protected Text getCustomerNameText;

	@Locate(jQuery = "#customer-dashboard-settings > div.box-content > div > div:nth-child(1) > div.head-block > a", on = Platform.WEB_DESKTOP)
	protected Link getContactInformationEditLink;

	@Locate(jQuery = "#customer-dashboard-settings > div.box-content > div > div:nth-child(1) > div.details > p:nth-child(2)", on = Platform.WEB_DESKTOP)
	protected Text getCustomerEmailText;

}
