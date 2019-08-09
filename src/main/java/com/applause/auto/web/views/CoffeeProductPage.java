package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.SelectList;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.helper.SyncHelper;
import com.applause.auto.web.components.CreateSubscriptionChunk;
import com.applause.auto.web.components.MiniCartContainerChunk;
import com.applause.auto.web.helpers.WebHelper;
import java.lang.invoke.MethodHandles;

@Implementation(is = CoffeeProductPage.class, on = Platform.WEB_DESKTOP)
@Implementation(is = CoffeeProductPage.class, on = Platform.WEB_MOBILE_TABLET)
@Implementation(is = CoffeeProductPage.class, on = Platform.WEB_MOBILE_PHONE)
public class CoffeeProductPage extends BaseComponent {

	WebHelper webHelper = new WebHelper();

	/*
	 * Public Actions
	 */

	/**
	 * Select a Grind
	 * 
	 */
	public void selectAGrind(String grind) {
		logger.info(String.format("Selecting a Grind: %s", grind));
		webHelper.jsSelect(getSelectGrindSelectList.getWebElement(), grind);
	}

	/**
	 * Click Add to Cart Button
	 * 
	 * @return MiniCartContainerChunk
	 */
	public MiniCartContainerChunk clickAddToCart() {
		logger.info("Tap on Shop Coffee Button");
		WebHelper.waitForElementToBeClickable(getAddToCartButton.getWebElement());
		getAddToCartButton.click();
		waitForAddingToCartSpinner();
		return ComponentFactory.create(MiniCartContainerChunk.class, this, "");
	}

	public CreateSubscriptionChunk clickAddToSubscription() {
		logger.info("Tap on Add To Subscription Button");
		WebHelper.waitForElementToBeClickable(getAddToSubscriptionCart.getWebElement());
		getAddToSubscriptionCart.click();
		waitForAddingToCartSpinner();
		return ComponentFactory.create(CreateSubscriptionChunk.class, this, "");
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
	 * Navigate back t.
	 *
	 * @param <T>
	 *            the type parameter
	 * @param clazz
	 *            the clazz
	 * @return the t
	 */
	public <T extends BaseComponent> T navigateBack(Class<T> clazz) {
		logger.info("Navigate back");
		getDriver().navigate().back();
		return ComponentFactory.create(clazz);
	}

	public void selectSubscription() {
		logger.info("Click on subscription");
		getSubscriptionButton.click();

	}

	/*
	 * Protected Getters
	 */

	@Locate(jQuery = ".product-shop-holder .product-name", on = Platform.WEB_DESKTOP)
	protected Text getViewSignature;

	@Locate(jQuery = ".product-shop-holder .add-to-cart button", on = Platform.WEB_DESKTOP)
	protected Button getAddToSubscriptionCart;

	@Locate(jQuery = ".product-shop-holder .add-to-cart button", on = Platform.WEB_DESKTOP)
	protected Button getAddToCartButton;

	@Locate(jQuery = "label[for='option-subscription']", on = Platform.WEB_DESKTOP)
	protected Button getSubscriptionButton;

	@Locate(jQuery = "#shopping-cart-please-wait", on = Platform.WEB_DESKTOP)
	protected ContainerElement getAddingToCartSpinner;

	@Locate(jQuery = "#attribute198", on = Platform.WEB_DESKTOP)
	protected SelectList getSelectGrindSelectList;
}
