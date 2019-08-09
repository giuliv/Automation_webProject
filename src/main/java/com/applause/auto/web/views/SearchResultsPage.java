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

@Implementation(is = SearchResultsPage.class, on = Platform.WEB_DESKTOP)
@Implementation(is = SearchResultsPage.class, on = Platform.WEB_MOBILE_TABLET)
@Implementation(is = SearchResultsPage.class, on = Platform.WEB_MOBILE_PHONE)
public class SearchResultsPage extends BaseComponent {

	// Public actions

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

	/**
	 * Click Kona Product
	 *
	 * @return CoffeeProductDescriptionPage
	 */
	public CoffeeProductDescriptionPage clickKona() {
		logger.info("Clicking First Product");
		getKonaProduct.click();
		return ComponentFactory.create(CoffeeProductDescriptionPage.class);
	}

	// Protected getters
	@Locate(jQuery = ".shown-results", on = Platform.WEB_DESKTOP)
	protected Text getResultsText;

	@Locate(jQuery = "#product_addtocart_form10995 > div.image-holder > div > a > picture > img", on = Platform.WEB_DESKTOP)
	protected Button getFirstProduct;

	@Locate(jQuery = "#product_addtocart_form10008 > div.image-holder > div.product-image.americas > a > picture > img", on = Platform.WEB_DESKTOP)
	protected Button getKonaProduct;

}
