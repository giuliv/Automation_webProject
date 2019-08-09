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

@Implementation(is = ShopTeaPage.class, on = Platform.WEB)
public class ShopTeaPage extends BaseComponent {

	/* -------- Elements -------- */

	/* -------- Actions -------- */


	/**
	 * Clicks a product name under the Tea Page
	 * 
	 * @return a Tea Product Page
	 */
	public TeaProductPage clickProductName(String productName) {
		logger.info(String.format("Tap on Product Name: %s", productName));
		productNameButton(productName).click();
		return ComponentFactory.create(TeaProductPage.class);
	}

	/*
	 * Protected Getters
	 */

	@Locate(xpath = "//div[@class='top-banner hero']//div[@class='text-content']/h1[contains(.,'Tea')]", on = Platform.WEB)
	protected Text getViewSignature;

	@Locate(xpath = "//ul[@class='prod-list']//li[strong[@class='product-name' and contains(.,'%s')]]", on = Platform.WEB)
	protected Button productNameButton;
}
