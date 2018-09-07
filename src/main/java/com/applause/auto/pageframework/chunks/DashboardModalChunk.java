package com.applause.auto.pageframework.chunks;

import java.lang.invoke.MethodHandles;

import com.applause.auto.framework.pageframework.UIData;
import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.framework.pageframework.web.AbstractPageChunk;
import com.applause.auto.framework.pageframework.web.PageFactory;
import com.applause.auto.framework.pageframework.web.WebElementLocator;
import com.applause.auto.framework.pageframework.web.factory.WebDesktopImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebPhoneImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebTabletImplementation;
import com.applause.auto.framework.pageframework.webcontrols.Button;
import com.applause.auto.framework.pageframework.webcontrols.Text;
import com.applause.auto.pageframework.pages.DashboardPage;

@WebDesktopImplementation(DashboardModalChunk.class)
@WebTabletImplementation(DashboardModalChunk.class)
@WebPhoneImplementation(DashboardModalChunk.class)
public class DashboardModalChunk extends AbstractPageChunk {
	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	/**
	 * Constructor.
	 *
	 * @param selector
	 *            the selector of the chunk
	 */
	public DashboardModalChunk(UIData parent, String selector) {
		super(parent, selector);
	}

	@Override
	protected void waitUntilVisible() {
		syncHelper.waitForElementToAppear(getViewSignature());
	}

	/*
	 * Public actions
	 */

	/**
	 * Click Explore Dashboard Button
	 *
	 * @return DashboardPage
	 */
	public DashboardPage clickExploreDashboard() {
		LOGGER.info("Clicking Explore Dashboard Button");
		getExploreDashboardButton().click();
		return PageFactory.create(DashboardPage.class);
	}

	/**
	 * Click Close Button on explore-dashboard-modal
	 *
	 * @return DashboardPage
	 */
	public DashboardPage clickCloseModal() {
		LOGGER.info("Clicking Close button on Dashboard Modal");
		getCloseDashboardModalButton().click();
		return PageFactory.create(DashboardPage.class);
	}

	/*
	 * Protected Getters
	 */

	@WebElementLocator(webDesktop = "div#modal-new-message-2018 a")
	protected Text getViewSignature() {
		return new Text(this, getLocator(this, "getViewSignature"));
	}

	@WebElementLocator(webDesktop = "div#modal-new-message-2018 a")
	protected Button getExploreDashboardButton() {
		return new Button(this, getLocator(this, "getExploreDashboardButton"));
	}

	@WebElementLocator(webDesktop = "#modal-new-message-2018 .close-button")
	protected Button getCloseDashboardModalButton() {
		return new Button(this, getLocator(this, "getCloseDashboardModalButton"));
	}

}
