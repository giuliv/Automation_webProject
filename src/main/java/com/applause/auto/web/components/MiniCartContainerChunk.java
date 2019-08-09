package com.applause.auto.web.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.pageframework.UIData;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Link;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.helper.QueryHelper;
import com.applause.auto.util.helper.SyncHelper;
import com.applause.auto.web.helpers.WebHelper;
import com.applause.auto.web.views.CheckoutPage;
import com.applause.auto.web.views.CheckoutPlaceOrderPage;
import com.applause.auto.web.views.CheckoutShippingInfoPage;
import com.applause.auto.web.views.ShoppingCartPage;
import com.applause.auto.web.views.SignInPage;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.stream.Collectors;

@Implementation(is = MiniCartContainerChunk.class, on = Platform.WEB_DESKTOP)
@Implementation(is = MiniCartContainerChunk.class, on = Platform.WEB_MOBILE_TABLET)
@Implementation(is = MiniCartContainerChunk.class, on = Platform.WEB_MOBILE_PHONE)
public class MiniCartContainerChunk extends BaseComponent {

	/**
	 * Constructor.
	 *
	 * @param selector
	 *            the selector of the chunk
	 */
	public MiniCartContainerChunk(UIData parent, String selector) {
		super(parent, selector);
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
		logger.info("Clicking Checkout Button");
		getCheckoutButton.click();
		return ComponentFactory.create(CheckoutPage.class);
	}

	/**
	 * Click Checkout Button with signed in user
	 *
	 * @return CheckoutPlaceOrderPage
	 */
	public CheckoutPlaceOrderPage checkoutSignedInUser() {
		logger.info("Clicking Checkout Button");
		getCheckoutButton.click();
		return ComponentFactory.create(CheckoutPlaceOrderPage.class);
	}

	/**
	 * Click Checkout Button after user signed
	 *
	 * @return CheckoutShippingInfoPage
	 */
	public CheckoutShippingInfoPage clickSignedInCheckout() {
		logger.info("Clicking Checkout Button");
		getCheckoutButton.click();
		return ComponentFactory.create(CheckoutShippingInfoPage.class);
	}

	/**
	 * Click Checkout Peets Card Button
	 *
	 * @return SignInPage
	 */
	public SignInPage checkoutPeetsCard() {
		logger.info("Clicking Checkout Button on Peets Card Page");
		getCheckoutButton.click();
		return ComponentFactory.create(SignInPage.class);
	}

	/**
	 * Click Edit Cart link
	 *
	 * @return ShoppingCartPage
	 */
	public ShoppingCartPage clickEditCart() {
		logger.info("Clicking Edit Button");
		getEditCartLink.click();
		return ComponentFactory.create(ShoppingCartPage.class);
	}

	/**
	 * Gets items.
	 *
	 * @return the items
	 */
	public List<String> getItems() {
		logger.info("Obtaining items from mini-cart");
		return QueryHelper.findElementsByExtendedCss(getLocator(this, "getMinicartItems")).stream().map(item -> {
			String result = item.getCurrentText();
			logger.info("Found item: " + result);
			return result.trim();
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
		logger.info("Remove from cart item: " + itemName);
		getRemoveItemButton(itemName).click();
		getDriver().switchTo().alert().accept();
		SyncHelper.waitUntilElementPresent(getReAddItemButton(itemName));
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
		logger.info("Getting Re add button caption");
		return getReAddItemButton(itemName).getCurrentText().trim();
	}

	/**
	 * Gets remove button caption.
	 *
	 * @param itemName
	 *            the item name
	 * @return the remove button caption
	 */
	public String getRemoveButtonCaption(String itemName) {
		logger.info("Getting Remove button caption");
		return getRemoveItemButton(itemName).getCurrentText().trim();
	}

	/**
	 * Re add mini cart container chunk.
	 *
	 * @param itemName
	 *            the item name
	 * @return the mini cart container chunk
	 */
	public MiniCartContainerChunk reAdd(String itemName) {
		logger.info("Clicking re-add button");
		getReAddItemButton(itemName).click();
		SyncHelper.waitUntilElementPresent(getRemoveItemButton(itemName));
		return this;
	}

	/*
	 * Protected Getters
	 */
	@Locate(jQuery = "#minicart-container", on = Platform.WEB_DESKTOP)
	protected Text getViewSignature;

	@Locate(jQuery = "#minicart-container .product-name", on = Platform.WEB_DESKTOP)
	protected Text getMinicartItems;

	@Locate(jQuery = "#minicart-container > div.block-subtitle > a.cart-link", on = Platform.WEB_DESKTOP)
	protected Link getEditCartLink;

	@Locate(jQuery = "a[title='Checkout']", on = Platform.WEB_DESKTOP)
	protected Button getCheckoutButton;

	@Locate(xpath = "//*[@id='cart-sidebar']//a[starts-with(@title,'%s')]/..//a[@class='remove']", on = Platform.WEB_DESKTOP)
	protected Button getRemoveItemButton;

	@Locate(xpath = "//*[@id='cart-sidebar']//a[starts-with(@title,'%s')]/..//a[@class='re-add']", on = Platform.WEB_DESKTOP)
	protected Button getReAddItemButton;

}
