package com.applause.auto.pageframework.pages;

import java.lang.invoke.MethodHandles;

import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.framework.pageframework.web.AbstractPage;
import com.applause.auto.framework.pageframework.web.ChunkFactory;
import com.applause.auto.framework.pageframework.web.WebElementLocator;
import com.applause.auto.framework.pageframework.web.factory.WebDesktopImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebPhoneImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebTabletImplementation;
import com.applause.auto.framework.pageframework.webcontrols.BaseHtmlElement;
import com.applause.auto.framework.pageframework.webcontrols.Button;
import com.applause.auto.framework.pageframework.webcontrols.Dropdown;
import com.applause.auto.framework.pageframework.webcontrols.Text;
import com.applause.auto.pageframework.chunks.MiniCartContainerChunk;
import com.applause.auto.pageframework.helpers.WebHelper;

@WebDesktopImplementation(CoffeeProductPage.class)
@WebTabletImplementation(CoffeeProductPage.class)
@WebPhoneImplementation(CoffeeProductPage.class)
public class CoffeeProductPage extends AbstractPage {

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
	 * Select a Grind
	 * 
	 */
	public void selectAGrind(String grind) {
		LOGGER.info(String.format("Selecting a Grind: %s", grind));
		webHelper.jsSelect(getSelectGrindDropdown().getWebElement(), grind);
	}

	/**
	 * Click Add to Cart Button
	 * 
	 * @return MiniCartContainerChunk
	 */
	public MiniCartContainerChunk clickAddToCart() {
		LOGGER.info("Tap on Shop Coffee Button");
		syncHelper.suspend(5000);
		getAddToCartButton().click();
		waitForAddingToCartSpinner();
		return ChunkFactory.create(MiniCartContainerChunk.class, this, "");
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

	/*
	 * Protected Getters
	 */

	@WebElementLocator(webDesktop = ".product-shop-holder .product-name")
	protected Text getViewSignature() {
		return new Text(this, getLocator(this, "getViewSignature"));
	}

	@WebElementLocator(webDesktop = ".product-shop-holder .add-to-cart button")
	protected Button getAddToCartButton() {
		return new Button(this, getLocator(this, "getAddToCartButton"));
	}

	@WebElementLocator(webDesktop = "#shopping-cart-please-wait")
	protected BaseHtmlElement getAddingToCartSpinner() {
		return new BaseHtmlElement(this, getLocator(this, "getAddingToCartSpinner"));
	}

	@WebElementLocator(webDesktop = "#attribute198")
	protected Dropdown getSelectGrindDropdown() {
		return new Dropdown(this, getLocator(this, "getSelectGrindDropdown"));
	}

}
