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

@WebDesktopImplementation(ShopTeaPage.class)
@WebTabletImplementation(ShopTeaPage.class)
@WebPhoneImplementation(ShopTeaPage.class)
public class ShopTeaPage extends AbstractPage {

	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	@Override
	protected void waitUntilVisible() {
		syncHelper.waitForElementToAppear(getViewSignature());
	}

	/*
	 * Public Actions
	 */

	/**
	 * Clicks a product name under the Tea Page
	 * 
	 * @return a Tea Product Page
	 */
	public TeaProductPage clickProductName(String productName) {
		LOGGER.info(String.format("Tap on Product Name: %s", productName));
		productNameButton(productName).click();
		return PageFactory.create(TeaProductPage.class);
	}

	/*
	 * Protected Getters
	 */

	@WebElementLocator(webDesktop = "//div[@class='top-banner hero']//div[@class='text-content']/h1[contains(.,'Tea')]")
	protected Text getViewSignature() {
		return new Text(this, getLocator(this, "getViewSignature"));
	}

	@WebElementLocator(webDesktop = "//ul[@class='prod-list']//li[strong[@class='product-name' and contains(.,'%s')]]")
	protected Button productNameButton(String productName) {
		return new Button(this, String.format(getLocator(this, "productNameButton"), productName));
	}
}
