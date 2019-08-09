package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.pageframework.util.webDrivers.BrowserType;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Checkbox;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.SelectList;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.helper.QueryHelper;
import com.applause.auto.util.helper.SyncHelper;
import com.applause.auto.web.components.ShopRunnerChunk;
import com.applause.auto.web.helpers.WebHelper;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.WebElement;

@Implementation(is = ShoppingCartPage.class, on = Platform.WEB)
public class ShoppingCartPage extends BaseComponent {

	/* -------- Elements -------- */

	/* -------- Actions -------- */

	WebHelper webHelper = new WebHelper();

	/*
	 * Public Actions
	 */

	/**
	 * Select Order is a Gift Checkbox
	 * 
	 * @return CheckoutPage
	 */
	public void selectOrderAsGift() {
		logger.info("Check the Order is a Gift");
		SyncHelper.waitUntilElementPresent(getLocator(this, "getOrderAsGiftCheckCheckbox"));
		getOrderAsGiftCheckCheckbox.click();
	}

	/**
	 * Enter Gift Message
	 * 
	 */
	public void enterGiftMessage(String giftMessage) {
		logger.info("Enter a Gift Message");
		SyncHelper.waitUntilElementPresent(getGiftMessageText.getAbsoluteSelector());
		getGiftMessageText.sendKeys(giftMessage);
	}

	/**
	 * Click Proceed to Checkout button
	 * 
	 * @return CheckoutPage
	 */
	public CheckoutPage clickProceedToCheckout() {
		logger.info("Click Proceed to Checkout button");
		getProceedToCheckoutButton.click();
		SyncHelper.sleep(2000);
		webHelper.jsClick(getProceedToCheckoutButton.getWebElement());
		return ComponentFactory.create(CheckoutPage.class);
	}

	/**
	 * Click Proceed to Checkout button for a signed user
	 *
	 * @return CheckoutPlaceOrderPage
	 */
	public CheckoutPlaceOrderPage checkoutSignedUser() {
		logger.info("Click Proceed to Checkout button");
		getProceedToCheckoutButton.click();
		SyncHelper.sleep(2000);
		webHelper.jsClick(getProceedToCheckoutButton.getWebElement());
		return ComponentFactory.create(CheckoutPlaceOrderPage.class);
	}

	/**
	 * Proceed to Shipping page via Checkout button for a signed user
	 *
	 * @return CheckoutShippingInfoPage
	 */
	public CheckoutShippingInfoPage defineShippingSignedUser() {
		logger.info("Click Proceed to Checkout button");
		getProceedToCheckoutButton.click();
		SyncHelper.sleep(2000);
		// webHelper.jsClick(getProceedToCheckoutButton.getWebElement());
		return ComponentFactory.create(CheckoutShippingInfoPage.class);
	}

	/**
	 * Click Pay with Paypal Button
	 *
	 * @return PaypalLoginPage
	 */
	public PaypalLoginPage clickPayWithPaypal() {
		logger.info("Clicking Pay with Paypal");
		getPaypalButton.click();
		SyncHelper.sleep(5000);
		try {
			getPaypalButton.click();
		} catch (Exception ex) {
			logger.info("Already clicked Paypal button");
		}
		return ComponentFactory.create(PaypalLoginPage.class);
	}

	/**
	 * Click Pay with Paypal Button for a signed user
	 *
	 * @return CheckoutPlaceOrderPage
	 */
	public CheckoutPlaceOrderPage clickPayWithPaypalSignedUser() {
		logger.info("Clicking Pay with Paypal for Signed User");
		getPaypalButton.click();
		SyncHelper.sleep(5000); // Required due a change of focus after leaving gift-message
		if (QueryHelper.doesElementExist(getPaypalButton.getAbsoluteSelector())) {
			webHelper.jsClick(getPaypalButton.getWebElement());
		}
		return ComponentFactory.create(CheckoutPlaceOrderPage.class);
	}

	/**
	 * Gets items.
	 *
	 * @return the items
	 */
	public List<String> getItems() {
		logger.info("Obtaining items from mini-cart");
		List<WebElement> result = QueryHelper.findElementsByExtendedCss(getLocator(this, "getCartItemsText"));
		return result.stream().map(item -> {
			String _result = item.getCurrentText();
			logger.info("Found item: " + _result);
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
		logger.info("Removing item: " + itemName);
		if (EnvironmentHelper.isSafari(DriverManager.getDriver())) {
			webHelper.jsClick(getRemoveItemButton(itemName).getWebElement());
		} else {
			getRemoveItemButton(itemName).click();
		}
		waitForAddingToCartSpinner();
		SyncHelper.sleep(5000);
		return this;
	}

	/**
	 * Click Add to Cart Button
	 *
	 */
	public void waitForAddingToCartSpinner() {
		logger.info("Adding item to Shopping Cart...");
		SyncHelper.waitUntilElementPresent(getLocator(this, "getAddingToCartSpinner"));
		SyncHelper.waitUntilElementNotPresent(getLocator(this, "getAddingToCartSpinner"));
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
		logger.info("Change grind value");
		if (EnvironmentHelper.isSafari(DriverManager.getDriver())) {
			getGrindForItemSelectList(itemName).getWebElement().sendKeys(grind + "\n");
		} else {
			getGrindForItemSelectList(itemName).select(grind);
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
		SyncHelper.waitUntilElementPresent(getStatusMessageText);
		return getStatusMessageText.getCurrentText().replace("  ", " ");
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
		logger.info("Change quantity value");
		getQuantityForItemTextBox(itemName).sendKeys("" + quantity);
		return this;
	}

	/**
	 * Update cart shopping cart page.
	 *
	 * @return the shopping cart page
	 */
	public ShoppingCartPage updateCart() {
		logger.info("Click Update cart button");
		getUpdateCartButton.click();
		waitForAddingToCartSpinner();
		return ComponentFactory.create(ShoppingCartPage.class);
	}

	/**
	 * Select shipping method shopping cart page.
	 *
	 * @param method
	 *            the method
	 * @return the shopping cart page
	 */
	public ShoppingCartPage selectShippingMethod(String method) {
		logger.info("Select shipping method: " + method);
		if (EnvironmentHelper.isSafari(DriverManager.getDriver())) {
			getShippingMethodSelectList.click();
			getShippingMethodSelectList.getWebElement().sendKeys(method + "\n");
		} else {
			getShippingMethodSelectList.select(method);
		}
		waitForAddingToCartSpinner();
		SyncHelper.sleep(5000);
		return ComponentFactory.create(ShoppingCartPage.class);
	}

	/**
	 * Gets shipping method.
	 *
	 * @return the shipping method
	 */
	public String getShippingMethod() {
		return getShippingMethodSelectList.getSelectedOption().getCurrentText();
	}

	/**
	 * Gets estimated shipping price.
	 *
	 * @return the estimated shipping price
	 */
	public String getEstimatedShippingPrice() {
		return getEstimatedShippingPriceText.getCurrentText();
	}

	/**
	 * Gets order summary price.
	 *
	 * @return the order summary price
	 */
	public String getOrderSummaryPrice() {
		return getOrderSummaryPriceText.getCurrentText();
	}

	/**
	 * Gets subscription name.
	 *
	 * @return the subscription name
	 */
	public String getSubscriptionName() {
		return getSubscriptionNameText.getCurrentText().trim();
	}

	/**
	 * Gets subscription frequency.
	 *
	 * @return the subscription frequency
	 */
	public String getSubscriptionFrequency() {
		return getSubscriptionFrequencyText.getCurrentText().trim();
	}

	/**
	 * Is product discount price displayed boolean.
	 *
	 * @return the boolean
	 */
	public boolean isProductDiscountPriceDisplayed() {
		return SyncHelper.isCurrentlyVisible(getProductDiscountPriceText.getAbsoluteSelector(), getDriver());
	}

	/**
	 * Is shipping discount price displayed boolean.
	 *
	 * @return the boolean
	 */
	public boolean isShippingDiscountPriceDisplayed() {
		return SyncHelper.isCurrentlyVisible(getShippingDiscountPriceText.getAbsoluteSelector(), getDriver());
	}

	/**
	 * Sign in shop runner shop runner chunk.
	 *
	 * @return the shop runner chunk
	 */
	public ShopRunnerChunk signInShopRunner() {
		logger.info("Click on Sign In shop runner");
		getSignInShopRunnerButton.click();
		return ComponentFactory.create(ShopRunnerChunk.class);
	}

	/*
	 * Protected Getters
	 */

	@Locate(css = "div.cart.display-single-price div.page-title h1", on = Platform.WEB)
	protected Text getViewSignature;

	@Locate(xpath = "//div[@class='shoprunner-cart-header']//a[text()='sign in']", on = Platform.WEB)
	protected Button getSignInShopRunnerButton;

	@Locate(css = "[value='update_qty']", on = Platform.WEB)
	protected Text getUpdateCartButton;

	@Locate(css = "#subscription_interval", on = Platform.WEB)
	protected Text getSubscriptionFrequencyText;

	@Locate(css = "#subscription-name-view > strong", on = Platform.WEB)
	protected Text getSubscriptionNameText;

	@Locate(css = "#shopping-cart-please-wait", on = Platform.WEB)
	protected ContainerElement getAddingToCartSpinner;

	@Locate(css = "#shopping-cart-messages", on = Platform.WEB)
	protected Text getStatusMessageText;

	@Locate(xpath = "//h3[contains(.,'%s')]/../../..//select[@title='Grind']", on = Platform.WEB)
	protected SelectList getGrindForItemSelectList;

	@Locate(xpath = "//select[@id='shipping_method']", on = Platform.WEB)
	protected SelectList getShippingMethodSelectList;

	@Locate(xpath = "//h3[contains(.,'%s')]/../../..//input[@title='Qty']", on = Platform.WEB)
	protected TextBox getQuantityForItemTextBox;

	@Locate(css = ".add-gift-message input", on = Platform.WEB)
	protected Checkbox getOrderAsGiftCheckCheckbox;

	@Locate(css = "#gift-message-whole-message", on = Platform.WEB)
	protected TextBox getGiftMessageText;

	@Locate(css = "#action-checkout", on = Platform.WEB)
	protected Button getProceedToCheckoutButton;

	@Locate(css = "div#shopping-cart-actions-additional img[alt='Checkout with PayPal']", on = Platform.WEB)
	protected Button getPaypalButton;

	@Locate(xpath = "//h3[contains(.,'%s')]/../../..//a[@class='btn-remove']", on = Platform.WEB)
	protected Button getRemoveItemButton;

	@Locate(css = "h3.product-name", on = Platform.WEB)
	protected Text getCartItemsText;

	@Locate(xpath = "//tr[td[contains(text(),'Estimated Shipping')]]//*[@class='price']", on = Platform.WEB)
	protected Text getEstimatedShippingPriceText;

	@Locate(xpath = "//tr[th[contains(text(),'Product Discount')]]//*[@class='price']", on = Platform.WEB)
	protected Text getProductDiscountPriceText;

	@Locate(xpath = "//tr[th[contains(text(),'Shipping Discount')]]//*[@class='price']", on = Platform.WEB)
	protected Text getShippingDiscountPriceText;

	@Locate(css = "strong.total-price", on = Platform.WEB)
	protected Text getOrderSummaryPriceText;
}
