package com.applause.auto.web.views;

import java.lang.invoke.MethodHandles;

import com.applause.auto.framework.pageframework.util.drivers.BrowserType;
import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.framework.pageframework.web.AbstractPage;
import com.applause.auto.framework.pageframework.web.ChunkFactory;
import com.applause.auto.framework.pageframework.web.WebElementLocator;
import com.applause.auto.framework.pageframework.web.factory.WebDesktopImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebPhoneImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebTabletImplementation;
import com.applause.auto.framework.pageframework.webcontrols.Button;
import com.applause.auto.framework.pageframework.webcontrols.Dropdown;
import com.applause.auto.web.components.MiniCartContainerChunk;
import com.applause.auto.web.helpers.WebHelper;

@WebDesktopImplementation(CoffeeProductDescriptionPage.class)
@WebTabletImplementation(CoffeeProductDescriptionPage.class)
@WebPhoneImplementation(CoffeeProductDescriptionPage.class)
public class CoffeeProductDescriptionPage extends AbstractPage {
	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());
	WebHelper webHelper = new WebHelper();

	@Override
	protected void waitUntilVisible() {
		WebHelper.waitForDocument();
		syncHelper.waitForElementToAppear(getAddToCartButton());
	}

	// Public actions

	/**
	 * Select Grind Option
	 *
	 * @param grind
	 */
	public void selectGrind(String grind) {
		LOGGER.info("Select Grind");
		if (env.getBrowserType() == BrowserType.SAFARI)
			webHelper.jsSelect(getGrindDropdown().getWebElement(), grind);
		else
			getGrindDropdown().select(grind);
	}

	/**
	 * Click Add To Cart
	 *
	 * @return MiniCartContainerChunk
	 */
	public MiniCartContainerChunk addToCart() {
		LOGGER.info("Clicking Add To Cart");
		syncHelper.suspend(5000);
		getAddToCartButton().click();
		return ChunkFactory.create(MiniCartContainerChunk.class, this, "");
	}

	// Protected getters
	@WebElementLocator(webDesktop = ".button.btn-cart.btn-dark")
	protected Button getAddToCartButton() {
		return new Button(this, getLocator(this, "getAddToCartButton"));
	}

	@WebElementLocator(webDesktop = "#attribute198")
	protected Dropdown getGrindDropdown() {
		return new Dropdown(this, getLocator(this, "getGrindDropdown"));
	}
}
