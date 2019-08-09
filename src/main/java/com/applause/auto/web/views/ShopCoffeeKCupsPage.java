package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.web.helpers.WebHelper;
import java.lang.invoke.MethodHandles;

@Implementation(is = ShopCoffeeKCupsPage.class, on = Platform.WEB)
public class ShopCoffeeKCupsPage extends BaseComponent {

	/* -------- Elements -------- */

	/* -------- Actions -------- */


	/**
	 * Taps the sign in button.
	 * 
	 * @return a Coffee K-Cups Product Page
	 */
	public CoffeeKCupsProductPage clickProductName(String productName) {
		logger.info(String.format("Tap on Product Name: %s", productName));
		productNameButton(productName).click();
		return ComponentFactory.create(CoffeeKCupsProductPage.class);
	}

	/*
	 * Protected Getters
	 */

	@Locate(css = "div.top-banner div.text-content h1", on = Platform.WEB)
	protected Text getViewSignature;

	@Locate(css = "ul.products-grid h2.product-name a[title*='%s']", on = Platform.WEB)
	protected Button productNameButton;
}
