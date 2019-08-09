package com.applause.auto.pageframework.pages;

import java.lang.invoke.MethodHandles;

import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.framework.pageframework.web.AbstractPage;
import com.applause.auto.framework.pageframework.web.PageFactory;
import com.applause.auto.framework.pageframework.web.WebElementLocator;
import com.applause.auto.framework.pageframework.web.factory.WebDesktopImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebPhoneImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebTabletImplementation;
import com.applause.auto.framework.pageframework.webcontrols.BaseHtmlElement;
import com.applause.auto.framework.pageframework.webcontrols.Button;
import com.applause.auto.framework.pageframework.webcontrols.Link;
import com.applause.auto.framework.pageframework.webcontrols.Text;
import com.applause.auto.pageframework.helpers.WebHelper;

@WebDesktopImplementation(MyAccountMyOrdersPage.class)
@WebTabletImplementation(MyAccountMyOrdersPage.class)
@WebPhoneImplementation(MyAccountMyOrdersPage.class)
public class MyAccountMyOrdersPage extends AbstractPage {
	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	@Override
	protected void waitUntilVisible() {
		WebHelper.waitForDocument();
		syncHelper.waitForElementToAppear(getViewSignature());
	}

	// Public actions

	/**
	 * Verify Orders-Placed Section is Displayed
	 *
	 * @return boolean
	 */
	public boolean isOrdersPlacedSectionDisplayed() {
		LOGGER.info("Verifying Orders-Placed section is displayed");
		return getOrdersPlacedSection().isDisplayed();
	}

	/**
	 * Verify Order's Date is Displayed
	 *
	 * @return boolean
	 */
	public boolean isOrdersDateDisplayed() {
		LOGGER.info("Verifying Order's Date is displayed");
		return getOrdersDateText().isDisplayed();
	}

	/**
	 * Verify Order's Number is Displayed
	 *
	 * @return boolean
	 */
	public boolean isOrdersNumberDisplayed() {
		LOGGER.info("Verifying Order's Number is displayed");
		return getOrdersNumberLink().isDisplayed();
	}

	/**
	 * Click Order's Number
	 *
	 * @return MyAccountOrderDetailPage
	 */
	public MyAccountOrderDetailPage clickOrderNumber() {
		LOGGER.info("Clicking first Order's Number");
		getOrdersNumberLink().click();
		return PageFactory.create(MyAccountOrderDetailPage.class);
	}

	/**
	 * Verify Order's Item is Displayed
	 *
	 * @return boolean
	 */
	public boolean isOrdersItemDisplayed() {
		LOGGER.info("Verifying Order's Item is displayed");
		return getOrdersItemText().isDisplayed();
	}

	/**
	 * Verify Order's Total is Displayed
	 *
	 * @return boolean
	 */
	public boolean isOrdersTotalDisplayed() {
		LOGGER.info("Verifying Order's Total is displayed");
		return getOrdersTotalText().isDisplayed();
	}

	/**
	 * Verify Order's Status is Displayed
	 *
	 * @return boolean
	 */
	public boolean isOrdersStatusDisplayed() {
		LOGGER.info("Verifying Order's Status is displayed");
		return getOrdersStatusText().isDisplayed();
	}

	/**
	 * Verify Order's View button is Displayed
	 *
	 * @return boolean
	 */
	public boolean isViewButtonDisplayed() {
		LOGGER.info("Verifying Order's View button is displayed");
		return getOrdersViewButton().isDisplayed();
	}

	/**
	 * Verify Order's Reorder button is Displayed
	 *
	 * @return boolean
	 */
	public boolean isReorderButtonDisplayed() {
		LOGGER.info("Verifying Order's Reorder button is displayed");
		return getOrdersReorderButton().isDisplayed();
	}

	// Protected getters
	@WebElementLocator(webDesktop = "div.col-main > div > div.page-title > h1")
	protected Text getViewSignature() {
		return new Text(this, getLocator(this, "getViewSignature"));
	}

	@WebElementLocator(webDesktop = ".orders-history-holder")
	protected BaseHtmlElement getOrdersPlacedSection() {
		return new BaseHtmlElement(this, getLocator(this, "getOrdersPlacedSection"));
	}

	@WebElementLocator(webDesktop = "#my-orders-table tr:nth-child(1) td.td-bold span")
	protected Text getOrdersDateText() {
		return new Text(this, getLocator(this, "getOrdersDateText"));
	}

	@WebElementLocator(webDesktop = "#my-orders-table tr:nth-child(6) td.td-id span")
	protected Link getOrdersNumberLink() {
		return new Link(this, getLocator(this, "getOrdersNumberLink"));
	}

	@WebElementLocator(webDesktop = "#my-orders-table > tbody > tr:nth-child(1) > td.td-items")
	protected Text getOrdersItemText() {
		return new Text(this, getLocator(this, "getOrdersItemText"));
	}

	@WebElementLocator(webDesktop = "#my-orders-table tr:nth-child(1) td.td-price span.price")
	protected Text getOrdersTotalText() {
		return new Text(this, getLocator(this, "getOrdersTotalText"));
	}

	@WebElementLocator(webDesktop = "#my-orders-table tr:nth-child(1) div.status span")
	protected Text getOrdersStatusText() {
		return new Text(this, getLocator(this, "getOrdersStatusText"));
	}

	@WebElementLocator(webDesktop = "//table[@id='my-orders-table']//tr[1]//a[contains(.,'View')]")
	protected Button getOrdersViewButton() {
		return new Button(this, getLocator(this, "getOrdersViewButton"));
	}

	@WebElementLocator(webDesktop = "//table[@id='my-orders-table']//tr[6]//a[contains(.,'Reorder')]")
	protected Button getOrdersReorderButton() {
		return new Button(this, getLocator(this, "getOrdersReorderButton"));
	}

}
