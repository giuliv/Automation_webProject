package com.applause.auto.web.views;

import java.lang.invoke.MethodHandles;

import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.framework.pageframework.web.AbstractPage;
import com.applause.auto.framework.pageframework.web.PageFactory;
import com.applause.auto.framework.pageframework.web.WebElementLocator;
import com.applause.auto.framework.pageframework.web.factory.WebDesktopImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebPhoneImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebTabletImplementation;
import com.applause.auto.framework.pageframework.webcontrols.Button;
import com.applause.auto.framework.pageframework.webcontrols.Text;
import com.applause.auto.web.helpers.WebHelper;

@WebDesktopImplementation(ShopGiftSubscriptionsPage.class)
@WebTabletImplementation(ShopGiftSubscriptionsPage.class)
@WebPhoneImplementation(ShopGiftSubscriptionsPage.class)
public class ShopGiftSubscriptionsPage extends AbstractPage {
	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	@Override
	protected void waitUntilVisible() {
		WebHelper.waitForDocument();
		syncHelper.waitForElementToAppear(getViewSignature());
	}

	// Public actions

	/**
	 * Click First Product
	 *
	 * @return CoffeeProductDescriptionPage
	 */
	public CoffeeProductDescriptionPage clickFirstProduct() {
		LOGGER.info("Clicking First Product");
		getFirstProduct().click();
		return PageFactory.create(CoffeeProductDescriptionPage.class);
	}

	// Protected getters
	@WebElementLocator(webDesktop = "body > div.wrapper > div > div.main-container.full-width > div > div > div.top-banner.wide-content > div.text-holder > div > h1")
	protected Text getViewSignature() {
		return new Text(this, getLocator(this, "getViewSignature"));
	}

	@WebElementLocator(webDesktop = "#product_addtocart_form10995 > div.image-holder > div > a > picture > img")
	protected Button getFirstProduct() {
		return new Button(this, getLocator(this, "getFirstProduct"));
	}
}
