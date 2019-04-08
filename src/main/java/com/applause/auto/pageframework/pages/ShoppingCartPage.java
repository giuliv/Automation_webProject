package com.applause.auto.pageframework.pages;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebElement;

import com.applause.auto.framework.pageframework.util.drivers.BrowserType;
import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.framework.pageframework.web.AbstractPage;
import com.applause.auto.framework.pageframework.web.ChunkFactory;
import com.applause.auto.framework.pageframework.web.PageFactory;
import com.applause.auto.framework.pageframework.web.WebElementLocator;
import com.applause.auto.framework.pageframework.web.factory.WebDesktopImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebPhoneImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebTabletImplementation;
import com.applause.auto.framework.pageframework.webcontrols.BaseHtmlElement;
import com.applause.auto.framework.pageframework.webcontrols.Button;
import com.applause.auto.framework.pageframework.webcontrols.Checkbox;
import com.applause.auto.framework.pageframework.webcontrols.Dropdown;
import com.applause.auto.framework.pageframework.webcontrols.EditField;
import com.applause.auto.framework.pageframework.webcontrols.Text;
import com.applause.auto.pageframework.chunks.ShopRunnerChunk;
import com.applause.auto.pageframework.helpers.WebHelper;

@WebDesktopImplementation(ShoppingCartPage.class)
@WebTabletImplementation(ShoppingCartPage.class)
@WebPhoneImplementation(ShoppingCartPage.class)
public class ShoppingCartPage extends AbstractPage {

	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());
	WebHelper webHelper = new WebHelper();

	@Override
	protected void waitUntilVisible() {
		WebHelper.waitForDocument();
		syncHelper.waitForElementToAppear(getViewSignature());
	}

	/*
	 * Public Actions
	 */

	/**
	 * Select Order is a Gift Checkbox
	 * 
	 * @return CheckoutPage
	 */
	public void selectOrderAsGift() {
		LOGGER.info("Check the Order is a Gift");
		syncHelper.waitForElementToAppear(getLocator(this, "getOrderAsGiftCheckCheckbox"));
		getOrderAsGiftCheckCheckbox().check();
	}

	/**
	 * Enter Gift Message
	 * 
	 */
	public void enterGiftMessage(String giftMessage) {
		LOGGER.info("Enter a Gift Message");
		syncHelper.waitForElementToAppear(getGiftMessageText().getAbsoluteSelector());
		getGiftMessageText().setText(giftMessage);
	}

	/**
	 * Click Proceed to Checkout button
	 * 
	 * @return CheckoutPage
	 */
	public CheckoutPage clickProceedToCheckout() {
		LOGGER.info("Click Proceed to Checkout button");
		getProceedToCheckoutButton().click();
		syncHelper.suspend(2000);
		webHelper.jsClick(getProceedToCheckoutButton().getWebElement());
		return PageFactory.create(CheckoutPage.class);
	}

	/**
	 * Click Proceed to Checkout button for a signed user
	 *
	 * @return CheckoutPlaceOrderPage
	 */
	public CheckoutPlaceOrderPage checkoutSignedUser() {
		LOGGER.info("Click Proceed to Checkout button");
		getProceedToCheckoutButton().click();
		syncHelper.suspend(2000);
		webHelper.jsClick(getProceedToCheckoutButton().getWebElement());
		return PageFactory.create(CheckoutPlaceOrderPage.class);
	}

	/**
	 * Proceed to Shipping page via Checkout button for a signed user
	 *
	 * @return CheckoutShippingInfoPage
	 */
	public CheckoutShippingInfoPage defineShippingSignedUser() {
		LOGGER.info("Click Proceed to Checkout button");
		getProceedToCheckoutButton().click();
		syncHelper.suspend(2000);
		// webHelper.jsClick(getProceedToCheckoutButton().getWebElement());
		return PageFactory.create(CheckoutShippingInfoPage.class);
	}

	/**
	 * Click Pay with Paypal Button
	 *
	 * @return PaypalLoginPage
	 */
	public PaypalLoginPage clickPayWithPaypal() {
		LOGGER.info("Clicking Pay with Paypal");
		getPaypalButton().click();
		syncHelper.suspend(5000);
		getPaypalButton().click();
		return PageFactory.create(PaypalLoginPage.class);
	}

	/**
	 * Click Pay with Paypal Button for a signed user
	 *
	 * @return CheckoutPlaceOrderPage
	 */
	public CheckoutPlaceOrderPage clickPayWithPaypalSignedUser() {
		LOGGER.info("Clicking Pay with Paypal for Signed User");
		getPaypalButton().click();
		syncHelper.suspend(5000); // Required due a change of focus after leaving gift-message
		if (queryHelper.doesElementExist(getPaypalButton().getAbsoluteSelector())) {
			webHelper.jsClick(getPaypalButton().getWebElement());
		}
		return PageFactory.create(CheckoutPlaceOrderPage.class);
	}

	/**
	 * Gets items.
	 *
	 * @return the items
	 */
	public List<String> getItems() {
		LOGGER.info("Obtaining items from mini-cart");
		List<WebElement> result = queryHelper.findElementsByExtendedCss(getLocator(this, "getCartItemsText"));
		return result.stream().map(item -> {
			String _result = item.getText();
			LOGGER.info("Found item: " + _result);
			return _result.trim();
		}).collect(Collectors.toList());

	}

	/**
	 * Remove item shopping cart page.
	 *
	 * @param itemName
	 *            the item name
	 * @return the shopping cart page
	 */
	public ShoppingCartPage removeItem(String itemName) {
		LOGGER.info("Removing item: " + itemName);
		if (env.getBrowserType() == BrowserType.SAFARI) {
			webHelper.jsClick(getRemoveItemButton(itemName).getWebElement());
		} else {
			getRemoveItemButton(itemName).click();
		}
		waitForAddingToCartSpinner();
		syncHelper.suspend(5000);
		return this;
	}

	/**
	 * Click Add to Cart Button
	 *
	 */
	public void waitForAddingToCartSpinner() {
		LOGGER.info("Adding item to Shopping Cart...");
		syncHelper.waitForElementToAppear(getLocator(this, "getAddingToCartSpinner"));
		syncHelper.waitForElementToDisappear(getLocator(this, "getAddingToCartSpinner"));
	}

	/**
	 * Sets grind for item.
	 *
	 * @param itemName
	 *            the item name
	 * @param grind
	 *            the grind 2
	 * @return the grind for item
	 */
	public ShoppingCartPage setGrindForItem(String itemName, String grind) {
		LOGGER.info("Change grind value");
		if (env.getBrowserType() == BrowserType.SAFARI) {
			getGrindForItemDropdown(itemName).getWebElement().sendKeys(grind + "\n");
		} else {
			getGrindForItemDropdown(itemName).select(grind);
		}
		waitForAddingToCartSpinner();
		return this;
	}

	/**
	 * Gets status message.
	 *
	 * @return the status message
	 */
	public String getStatusMessage() {
		syncHelper.waitForElementToAppear(getStatusMessageText());
		return getStatusMessageText().getText().replace("  ", " ");
	}

	/**
	 * Sets quantity for item.
	 *
	 * @param itemName
	 *            the item name
	 * @param quantity
	 *            the quantity
	 * @return the quantity for item
	 */
	public ShoppingCartPage setQuantityForItem(String itemName, int quantity) {
		LOGGER.info("Change quantity value");
		getQuantityForItemEditField(itemName).setText("" + quantity);
		return this;
	}

	/**
	 * Update cart shopping cart page.
	 *
	 * @return the shopping cart page
	 */
	public ShoppingCartPage updateCart() {
		LOGGER.info("Click Update cart button");
		getUpdateCartButton().click();
		waitForAddingToCartSpinner();
		return PageFactory.create(ShoppingCartPage.class);
	}

	/**
	 * Select shipping method shopping cart page.
	 *
	 * @param method
	 *            the method
	 * @return the shopping cart page
	 */
	public ShoppingCartPage selectShippingMethod(String method) {
		LOGGER.info("Select shipping method: " + method);
		if (env.getBrowserType() == BrowserType.SAFARI) {
			getShippingMethodDropdown().click();
			getShippingMethodDropdown().getWebElement().sendKeys(method + "\n");
		} else {
			getShippingMethodDropdown().select(method);
		}
		waitForAddingToCartSpinner();
		syncHelper.suspend(5000);
		return PageFactory.create(ShoppingCartPage.class);
	}

	public String getShippingMethod() {
		return getShippingMethodDropdown().getSelectedOption().getText();
	}

	public String getEstimatedShippingPrice() {
		return getEstimatedShippingPriceText().getText();
	}

	public String getOrderSummaryPrice() {
		return getOrderSummaryPriceText().getText();
	}

	public String getSubscriptionName() {
		return getSubscriptionNameText().getText().trim();
	}

	public String getSubscriptionFrequency() {
		return getSubscriptionFrequencyText().getText().trim();
	}

	public boolean isProductDiscountPriceDisplayed() {
		return syncHelper.isCurrentlyVisible(getProductDiscountPriceText().getAbsoluteSelector(), getDriver());
	}

	public boolean isShippingDiscountPriceDisplayed() {
		return syncHelper.isCurrentlyVisible(getShippingDiscountPriceText().getAbsoluteSelector(), getDriver());
	}

	public ShopRunnerChunk signInShopRunner() {
		LOGGER.info("Click on Sign In shop runner");
		syncHelper.suspend(30000);
		getSignInShopRunnerButton().click();
		return ChunkFactory.create(ShopRunnerChunk.class, this, "");
	}

	/*
	 * Protected Getters
	 */

	@WebElementLocator(webDesktop = "div.cart.display-single-price div.page-title h1")
	protected Text getViewSignature() {
		return new Text(this, getLocator(this, "getViewSignature"));
	}

	@WebElementLocator(webDesktop = "//div[@class='shoprunner-cart-header']//a[text()='sign in']")
	protected Button getSignInShopRunnerButton() {
		return new Button(this, getLocator(this, "getSignInShopRunnerButton"));
	}

	@WebElementLocator(webDesktop = "[value='update_qty']")
	protected Text getUpdateCartButton() {
		return new Text(this, getLocator(this, "getUpdateCartButton"));
	}

	@WebElementLocator(webDesktop = "#subscription_interval")
	protected Text getSubscriptionFrequencyText() {
		return new Text(this, getLocator(this, "getSubscriptionFrequencyText"));
	}

	@WebElementLocator(webDesktop = "#subscription-name-view > strong")
	protected Text getSubscriptionNameText() {
		return new Text(this, getLocator(this, "getSubscriptionNameText"));
	}

	@WebElementLocator(webDesktop = "#shopping-cart-please-wait")
	protected BaseHtmlElement getAddingToCartSpinner() {
		return new BaseHtmlElement(this, getLocator(this, "getAddingToCartSpinner"));
	}

	@WebElementLocator(webDesktop = "#shopping-cart-messages")
	protected Text getStatusMessageText() {
		return new Text(this, getLocator(this, "getStatusMessageText"));
	}

	@WebElementLocator(webDesktop = "//h3[contains(.,'%s')]/../../..//select[@title='Grind']")
	protected Dropdown getGrindForItemDropdown(String itemName) {
		return new Dropdown(this, String.format(getLocator(this, "getGrindForItemDropdown"), itemName));
	}

	@WebElementLocator(webDesktop = "//select[@id='shipping_method']")
	protected Dropdown getShippingMethodDropdown() {
		return new Dropdown(this, getLocator(this, "getShippingMethodDropdown"));
	}

	@WebElementLocator(webDesktop = "//h3[contains(.,'%s')]/../../..//input[@title='Qty']")
	protected EditField getQuantityForItemEditField(String itemName) {
		return new EditField(this, String.format(getLocator(this, "getQuantityForItemEditField"), itemName));
	}

	@WebElementLocator(webDesktop = ".add-gift-message input")
	protected Checkbox getOrderAsGiftCheckCheckbox() {
		return new Checkbox(this, getLocator(this, "getOrderAsGiftCheckCheckbox"));
	}

	@WebElementLocator(webDesktop = "#gift-message-whole-message")
	protected EditField getGiftMessageText() {
		return new EditField(this, getLocator(this, "getGiftMessageText"));
	}

	@WebElementLocator(webDesktop = "#action-checkout")
	protected Button getProceedToCheckoutButton() {
		return new Button(this, getLocator(this, "getProceedToCheckoutButton"));
	}

	@WebElementLocator(webDesktop = "div#shopping-cart-actions-additional img[alt='Checkout with PayPal']")
	protected Button getPaypalButton() {
		return new Button(this, getLocator(this, "getPaypalButton"));
	}

	@WebElementLocator(webDesktop = "//h3[contains(.,'%s')]/../../..//a[@class='btn-remove']")
	protected Button getRemoveItemButton(String productName) {
		return new Button(this, String.format(getLocator(this, "getRemoveItemButton"), productName));
	}

	@WebElementLocator(webDesktop = "h3.product-name")
	protected Text getCartItemsText() {
		return new Text(this, getLocator(this, "getCartItemsText"));
	}

	@WebElementLocator(webDesktop = "//tr[td[contains(text(),'Estimated Shipping')]]//*[@class='price']")
	protected Text getEstimatedShippingPriceText() {
		return new Text(this, getLocator(this, "getEstimatedShippingPriceText"));
	}

	@WebElementLocator(webDesktop = "//tr[th[contains(text(),'Product Discount')]]//*[@class='price']")
	protected Text getProductDiscountPriceText() {
		return new Text(this, getLocator(this, "getProductDiscountPriceText"));
	}

	@WebElementLocator(webDesktop = "//tr[th[contains(text(),'Shipping Discount')]]//*[@class='price']")
	protected Text getShippingDiscountPriceText() {
		return new Text(this, getLocator(this, "getShippingDiscountPriceText"));
	}

	@WebElementLocator(webDesktop = "strong.total-price")
	protected Text getOrderSummaryPriceText() {
		return new Text(this, getLocator(this, "getOrderSummaryPriceText"));
	}
}
