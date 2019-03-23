package com.applause.auto.pageframework.chunks;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.stream.Collectors;

import com.applause.auto.framework.pageframework.UIData;
import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.framework.pageframework.web.AbstractPageChunk;
import com.applause.auto.framework.pageframework.web.PageFactory;
import com.applause.auto.framework.pageframework.web.WebElementLocator;
import com.applause.auto.framework.pageframework.web.factory.WebDesktopImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebPhoneImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebTabletImplementation;
import com.applause.auto.framework.pageframework.webcontrols.Button;
import com.applause.auto.framework.pageframework.webcontrols.Link;
import com.applause.auto.framework.pageframework.webcontrols.Text;
import com.applause.auto.pageframework.helpers.WebHelper;
import com.applause.auto.pageframework.pages.CheckoutPage;
import com.applause.auto.pageframework.pages.CheckoutPlaceOrderPage;
import com.applause.auto.pageframework.pages.CheckoutShippingInfoPage;
import com.applause.auto.pageframework.pages.ShoppingCartPage;
import com.applause.auto.pageframework.pages.SignInPage;

@WebDesktopImplementation(MiniCartContainerChunk.class)
@WebTabletImplementation(MiniCartContainerChunk.class)
@WebPhoneImplementation(MiniCartContainerChunk.class)
public class MiniCartContainerChunk extends AbstractPageChunk {
	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	/**
	 * Constructor.
	 *
	 * @param selector
	 *            the selector of the chunk
	 */
	public MiniCartContainerChunk(UIData parent, String selector) {
		super(parent, selector);
	}

	@Override
	protected void waitUntilVisible() {
		WebHelper.waitForDocument();
		syncHelper.waitForElementToAppear(getViewSignature());
	}

	/*
	 * Public actions
	 */

	/**
	 * Click Checkout Button
	 *
	 * @return CheckoutPage
	 */
	public CheckoutPage clickCheckout() {
		LOGGER.info("Clicking Checkout Button");
		getCheckoutButton().click();
		return PageFactory.create(CheckoutPage.class);
	}

	/**
	 * Click Checkout Button with signed in user
	 *
	 * @return CheckoutPlaceOrderPage
	 */
	public CheckoutPlaceOrderPage checkoutSignedInUser() {
		LOGGER.info("Clicking Checkout Button");
		getCheckoutButton().click();
		return PageFactory.create(CheckoutPlaceOrderPage.class);
	}

	/**
	 * Click Checkout Button after user signed
	 *
	 * @return CheckoutShippingInfoPage
	 */
	public CheckoutShippingInfoPage clickSignedInCheckout() {
		LOGGER.info("Clicking Checkout Button");
		getCheckoutButton().click();
		return PageFactory.create(CheckoutShippingInfoPage.class);
	}

	/**
	 * Click Checkout Peets Card Button
	 *
	 * @return SignInPage
	 */
	public SignInPage checkoutPeetsCard() {
		LOGGER.info("Clicking Checkout Button on Peets Card Page");
		getCheckoutButton().click();
		return PageFactory.create(SignInPage.class);
	}

	/**
	 * Click Edit Cart link
	 *
	 * @return ShoppingCartPage
	 */
	public ShoppingCartPage clickEditCart() {
		LOGGER.info("Clicking Edit Button");
		getEditCartLink().click();
		return PageFactory.create(ShoppingCartPage.class);
	}

	/**
	 * Gets items.
	 *
	 * @return the items
	 */
	public List<String> getItems() {
		LOGGER.info("Obtaining items from mini-cart");
		return queryHelper.findElementsByExtendedCss(getLocator(this, "getMinicartItems")).stream().map(item -> {
			String result = item.getText();
			LOGGER.info("Found item: " + result);
			return result;
		}).collect(Collectors.toList());
	}

	/**
	 * Remove mini cart container chunk.
	 *
	 * @param itemName
	 *            the item name
	 * @return the mini cart container chunk
	 */
	public MiniCartContainerChunk remove(String itemName) {
		LOGGER.info("Remove from cart item: " + itemName);
		getRemoveItemButton(itemName).click();
		getDriver().switchTo().alert().accept();
		syncHelper.waitForElementToAppear(getReAddItemButton(itemName));
		return this;
	}

	/**
	 * Gets re add button caption.
	 *
	 * @param itemName
	 *            the item name
	 * @return the re add button caption
	 */
	public String getReAddButtonCaption(String itemName) {
		LOGGER.info("Getting Re add button caption");
		return getReAddItemButton(itemName).getText();
	}

	/**
	 * Gets remove button caption.
	 *
	 * @param itemName
	 *            the item name
	 * @return the remove button caption
	 */
	public String getRemoveButtonCaption(String itemName) {
		LOGGER.info("Getting Remove button caption");
		return getRemoveItemButton(itemName).getText();
	}

	/**
	 * Re add mini cart container chunk.
	 *
	 * @param itemName
	 *            the item name
	 * @return the mini cart container chunk
	 */
	public MiniCartContainerChunk reAdd(String itemName) {
		LOGGER.info("Clicking re-add button");
		getReAddItemButton(itemName).click();
		syncHelper.waitForElementToAppear(getRemoveItemButton(itemName));
		return this;
	}

	/*
	 * Protected Getters
	 */
	@WebElementLocator(webDesktop = "#minicart-container")
	protected Text getViewSignature() {
		return new Text(this, getLocator(this, "getViewSignature"));
	}

	@WebElementLocator(webDesktop = "#minicart-container .product-name")
	protected Text getMinicartItems() {
		return new Text(this, getLocator(this, "getMinicartItems"));
	}

	@WebElementLocator(webDesktop = "#minicart-container > div.block-subtitle > a.cart-link")
	protected Link getEditCartLink() {
		return new Link(this, getLocator(this, "getEditCartLink"));
	}

	@WebElementLocator(webDesktop = "a[title='Checkout']")
	protected Button getCheckoutButton() {
		return new Button(this, getLocator(this, "getCheckoutButton"));
	}

	@WebElementLocator(webDesktop = "//*[@id='cart-sidebar']//a[starts-with(@title,'%s')]/..//a[@class='remove']")
	protected Button getRemoveItemButton(String itemName) {
		return new Button(this, String.format(getLocator(this, "getRemoveItemButton"), itemName));
	}

	@WebElementLocator(webDesktop = "//*[@id='cart-sidebar']//a[starts-with(@title,'%s')]/..//a[@class='re-add']")
	protected Button getReAddItemButton(String itemName) {
		return new Button(this, String.format(getLocator(this, "getReAddItemButton"), itemName));
	}

}
