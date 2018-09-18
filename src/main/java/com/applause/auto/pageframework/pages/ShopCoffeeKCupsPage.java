package com.applause.auto.pageframework.pages;

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

@WebDesktopImplementation(ShopCoffeeKCupsPage.class)
@WebTabletImplementation(ShopCoffeeKCupsPage.class)
@WebPhoneImplementation(ShopCoffeeKCupsPage.class)
public class ShopCoffeeKCupsPage extends AbstractPage {

	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	@Override
	protected void waitUntilVisible() {
		syncHelper.waitForElementToAppear(getViewSignature());
	}

	/*
	 * Public Actions
	 */

	/**
	 * Taps the sign in button.
	 * 
	 * @return a Coffee K-Cups Product Page
	 */
	public CoffeeKCupsProductPage clickProductName(String productName) {
		LOGGER.info(String.format("Tap on Product Name: %s", productName));
		productNameButton(productName).click();
		return PageFactory.create(CoffeeKCupsProductPage.class);
	}

	/*
	 * Protected Getters
	 */

	@WebElementLocator(webDesktop = "//div[@class='top-banner']//div[@class='text-content']/h1[contains(.,'Coffee')]")
	protected Text getViewSignature() {
		return new Text(this, getLocator(this, "getViewSignature"));
	}

	@WebElementLocator(webDesktop = "//ul[@class='prod-list']//li[strong[@class='product-name' and contains(.,'%s')]]")
	protected Button productNameButton(String productName) {
		return new Button(this, String.format(getLocator(this, "productNameButton"), productName));
	}
}
