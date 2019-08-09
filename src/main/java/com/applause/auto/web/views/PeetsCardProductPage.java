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
import com.applause.auto.web.components.MiniCartContainerChunk;
import com.applause.auto.web.helpers.WebHelper;
import java.lang.invoke.MethodHandles;

@Implementation(is = PeetsCardProductPage.class, on = Platform.WEB_DESKTOP)
@Implementation(is = PeetsCardProductPage.class, on = Platform.WEB_MOBILE_TABLET)
@Implementation(is = PeetsCardProductPage.class, on = Platform.WEB_MOBILE_PHONE)
public class PeetsCardProductPage extends BaseComponent {

	WebHelper webHelper = new WebHelper();

	/*
	 * Public Actions
	 */

	/**
	 * Select an Amount for the Card
	 * 
	 */
	public void selectCardAmount(String amount) {
		logger.info(String.format("Selecting a Grind: %s", amount));
		webHelper.jsSelect(getSelectCardAmountSelectList.getWebElement(), amount);
	}

	/**
	 * Click Add to Cart Button
	 * 
	 * @return MiniCartContainerChunk
	 */
	public MiniCartContainerChunk clickAddToCart() {
		logger.info("Tap on Shop Coffee Button");
		getAddToCartButton.click();
		waitForAddingToCartSpinner();
		return ComponentFactory.create(MiniCartContainerChunk.class, this, "");
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

	/*
	 * Protected Getters
	 */

	@Locate(jQuery = ".product-shop .product-name", on = Platform.WEB_DESKTOP)
	protected Text getViewSignature;

	@Locate(jQuery = ".product-shop .add-to-cart button", on = Platform.WEB_DESKTOP)
	protected Button getAddToCartButton;

	@Locate(jQuery = "#shopping-cart-please-wait", on = Platform.WEB_DESKTOP)
	protected ContainerElement getAddingToCartSpinner;

	@Locate(jQuery = "#select_1", on = Platform.WEB_DESKTOP)
	protected SelectList getSelectCardAmountSelectList;

}
