package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.web.components.MiniCartContainerChunk;
import com.applause.auto.web.helpers.WebHelper;
import java.lang.invoke.MethodHandles;

@Implementation(is = TopSellersTeaPage.class, on = Platform.WEB)
public class TopSellersTeaPage extends BaseComponent {

	/* -------- Elements -------- */

	/* -------- Actions -------- */


	/**
	 * Add product to cart mini cart container chunk.
	 *
	 * @param productName
	 *            the product name
	 * @return the mini cart container chunk
	 */
	public MiniCartContainerChunk addProductToCart(String productName) {
		logger.info("Adding to cart product: " + productName);
		getAddProductToCartButton(productName).click();
		getAddProductToCart2Button(productName).click();
		return ComponentFactory.create(MiniCartContainerChunk.class);
	}

	/*
	 * Protected Getters
	 */

	@Locate(xpath = "//h1[contains(.,'Top Sellers')]", on = Platform.WEB)
	protected Text getViewSignature;

	@Locate(xpath = "//a[@title='%s']/../..//a[contains(@class,'btn-cart')]", on = Platform.WEB)
	protected Button getAddProductToCartButton;

	@Locate(xpath = "//a[@title='%s']/../..//button[contains(@class,'btn-cart')]", on = Platform.WEB)
	protected Button getAddProductToCart2Button;

}
