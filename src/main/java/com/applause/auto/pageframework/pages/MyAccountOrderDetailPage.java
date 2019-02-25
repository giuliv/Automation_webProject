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
import com.applause.auto.pageframework.helpers.WebHelper;

@WebDesktopImplementation(MyAccountOrderDetailPage.class)
@WebTabletImplementation(MyAccountOrderDetailPage.class)
@WebPhoneImplementation(MyAccountOrderDetailPage.class)
public class MyAccountOrderDetailPage extends AbstractPage {
	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	@Override
	protected void waitUntilVisible() {
		WebHelper.waitForDocument();
		syncHelper.waitForElementToAppear(getViewSignature());
	}

	// Public actions

	/**
	 * Verify Order's Detail, product is Displayed
	 *
	 * @return boolean
	 */
	public boolean isProductDisplayed() {
		LOGGER.info("Verifying Order's Detail, Product is displayed");
		return getDetailsProductText().isDisplayed();
	}

	/**
	 * Verify Order's Detail, Reorder Button is Displayed
	 *
	 * @return boolean
	 */
	public boolean isReorderButtonDisplayed() {
		LOGGER.info("Verifying Order's Detail, Reorder Button is displayed");
		return getDetailsReorderButton().isDisplayed();
	}

	/**
	 * Verify Order's Detail, Shipping Method is Displayed
	 *
	 * @return boolean
	 */
	public boolean isShippingMethodDisplayed() {
		LOGGER.info("Verifying Order's Detail, Shipping Method is displayed");
		return getDetailsShippingMethodText().isDisplayed();
	}

	/**
	 * Verify Order's Detail, Payment Method is Displayed
	 *
	 * @return boolean
	 */
	public boolean isPaymentMethodDisplayed() {
		LOGGER.info("Verifying Order's Detail, Payment Method is displayed");
		return getDetailsPaymentMethodText().isDisplayed();
	}

	/**
	 * Click Back to My Orders
	 *
	 * @return MyAccountMyOrdersPage
	 */
	public MyAccountMyOrdersPage clickBackToMyOrders() {
		LOGGER.info("Clicking back to my orders button");
		getBackToMyOrdersButton().focus();
		getBackToMyOrdersButton().click();
		return PageFactory.create(MyAccountMyOrdersPage.class);
	}

	// Protected getters
	@WebElementLocator(webDesktop = "body > div.wrapper > div > div.main-container.col2-left-layout > div > div.col-main > div > div.page-title > h1")
	protected Text getViewSignature() {
		return new Text(this, getLocator(this, "getViewSignature"));
	}

	@WebElementLocator(webDesktop = "h3.product-name")
	protected Text getDetailsProductText() {
		return new Text(this, getLocator(this, "getDetailsProductText"));
	}

	@WebElementLocator(webDesktop = "a.button.link-cart")
	protected Button getDetailsReorderButton() {
		return new Button(this, getLocator(this, "getDetailsReorderButton"));
	}

	@WebElementLocator(webDesktop = "div.wrapper-items > div.content-info.left > div:nth-child(2) > div > div:nth-child(2) > p")
	protected Text getDetailsShippingMethodText() {
		return new Text(this, getLocator(this, "getDetailsShippingMethodText"));
	}

	@WebElementLocator(webDesktop = "div.wrapper-items > div.content-info.left > div:nth-child(3) > div > div:nth-child(2) > div:nth-child(2) > p")
	protected Text getDetailsPaymentMethodText() {
		return new Text(this, getLocator(this, "getDetailsPaymentMethodText"));
	}

	@WebElementLocator(webDesktop = "a.back-link")
	protected Button getBackToMyOrdersButton() {
		return new Button(this, getLocator(this, "getBackToMyOrdersButton"));
	}

}
