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

@Implementation(is = ShopCoffeePage.class, on = Platform.WEB_DESKTOP)
@Implementation(is = ShopCoffeePage.class, on = Platform.WEB_MOBILE_TABLET)
@Implementation(is = ShopCoffeePage.class, on = Platform.WEB_MOBILE_PHONE)
public class ShopCoffeePage extends BaseComponent {

	/*
	 * Public Actions
	 */

	/**
	 * Taps the sign in button.
	 * 
	 * @return a Coffee Product Page
	 */
	public CoffeeProductPage clickProductName(String productName) {
		logger.info(String.format("Tap on Product Name: %s", productName));
		productNameButton(productName).click();
		return ComponentFactory.create(CoffeeProductPage.class);
	}

	/*
	 * Protected Getters
	 */

	@Locate(jQuery = "div.top-banner div.text-content h1", on = Platform.WEB_DESKTOP)
	protected Text getViewSignature;

	@Locate(xpath = "//ul[@class='prod-list']//li[strong[@class='product-name' and contains(.,'%s')]]", on = Platform.WEB_DESKTOP)
	protected Button productNameButton;
}
