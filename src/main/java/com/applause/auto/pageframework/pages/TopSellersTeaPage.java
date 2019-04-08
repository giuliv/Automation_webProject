package com.applause.auto.pageframework.pages;

import java.lang.invoke.MethodHandles;

import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.framework.pageframework.web.AbstractPage;
import com.applause.auto.framework.pageframework.web.ChunkFactory;
import com.applause.auto.framework.pageframework.web.WebElementLocator;
import com.applause.auto.framework.pageframework.web.factory.WebDesktopImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebPhoneImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebTabletImplementation;
import com.applause.auto.framework.pageframework.webcontrols.Button;
import com.applause.auto.framework.pageframework.webcontrols.Text;
import com.applause.auto.pageframework.chunks.MiniCartContainerChunk;
import com.applause.auto.pageframework.helpers.WebHelper;

@WebDesktopImplementation(TopSellersTeaPage.class)
@WebTabletImplementation(TopSellersTeaPage.class)
@WebPhoneImplementation(TopSellersTeaPage.class)
public class TopSellersTeaPage extends AbstractPage {

	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	@Override
	protected void waitUntilVisible() {
		WebHelper.waitForDocument();
		syncHelper.waitForElementToAppear(getViewSignature());
	}

	/*
	 * Public Actions
	 */

	public MiniCartContainerChunk addProductToCart(String productName) {
		LOGGER.info("Adding to cart product: " + productName);
		getAddProductToCartButton(productName).click();
		getAddProductToCart2Button(productName).click();
		return ChunkFactory.create(MiniCartContainerChunk.class, this, "");
	}

	/*
	 * Protected Getters
	 */

	@WebElementLocator(webDesktop = "//h1[contains(.,'Top Sellers')]")
	protected Text getViewSignature() {
		return new Text(this, getLocator(this, "getViewSignature"));
	}

	@WebElementLocator(webDesktop = "//a[@title='%s']/../..//a[contains(@class,'btn-cart')]")
	protected Button getAddProductToCartButton(String productName) {
		return new Button(this, String.format(getLocator(this, "getAddProductToCartButton"), productName));
	}

	@WebElementLocator(webDesktop = "//a[@title='%s']/../..//button[contains(@class,'btn-cart')]")
	protected Button getAddProductToCart2Button(String productName) {
		return new Button(this, String.format(getLocator(this, "getAddProductToCart2Button"), productName));
	}

}
