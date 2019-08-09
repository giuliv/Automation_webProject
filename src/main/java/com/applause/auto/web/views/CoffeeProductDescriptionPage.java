package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.pageframework.util.webDrivers.BrowserType;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.SelectList;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.helper.SyncHelper;
import com.applause.auto.web.components.MiniCartContainerChunk;
import com.applause.auto.web.helpers.WebHelper;
import java.lang.invoke.MethodHandles;

@Implementation(is = CoffeeProductDescriptionPage.class, on = Platform.WEB_DESKTOP)
@Implementation(is = CoffeeProductDescriptionPage.class, on = Platform.WEB_MOBILE_TABLET)
@Implementation(is = CoffeeProductDescriptionPage.class, on = Platform.WEB_MOBILE_PHONE)
public class CoffeeProductDescriptionPage extends BaseComponent {
	WebHelper webHelper = new WebHelper();

	// Public actions

	/**
	 * Select Grind Option
	 *
	 * @param grind
	 */
	public void selectGrind(String grind) {
		logger.info("Select Grind");
		if (env.getBrowserType() == BrowserType.SAFARI)
			webHelper.jsSelect(getGrindSelectList.getWebElement(), grind);
		else
			getGrindSelectList.select(grind);
	}

	/**
	 * Click Add To Cart
	 *
	 * @return MiniCartContainerChunk
	 */
	public MiniCartContainerChunk addToCart() {
		logger.info("Clicking Add To Cart");
		SyncHelper.sleep(5000);
		getAddToCartButton.click();
		return ComponentFactory.create(MiniCartContainerChunk.class, this, "");
	}

	// Protected getters
	@Locate(jQuery = ".button.btn-cart.btn-dark", on = Platform.WEB_DESKTOP)
	protected Button getAddToCartButton;

	@Locate(jQuery = "#attribute198", on = Platform.WEB_DESKTOP)
	protected SelectList getGrindSelectList;
}
