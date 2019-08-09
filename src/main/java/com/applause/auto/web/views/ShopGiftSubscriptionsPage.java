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

@Implementation(is = ShopGiftSubscriptionsPage.class, on = Platform.WEB)
public class ShopGiftSubscriptionsPage extends BaseComponent {

	/* -------- Elements -------- */

	/* -------- Actions -------- */


	/**
	 * Click First Product
	 *
	 * @return CoffeeProductDescriptionPage
	 */
	public CoffeeProductDescriptionPage clickFirstProduct() {
		logger.info("Clicking First Product");
		getFirstProduct.click();
		return ComponentFactory.create(CoffeeProductDescriptionPage.class);
	}

	// Protected getters
	@Locate(css = "body > div.wrapper > div > div.main-container.full-width > div > div > div.top-banner.wide-content > div.text-holder > div > h1", on = Platform.WEB)
	protected Text getViewSignature;

	@Locate(css = "#product_addtocart_form10995 > div.image-holder > div > a > picture > img", on = Platform.WEB)
	protected Button getFirstProduct;
}
