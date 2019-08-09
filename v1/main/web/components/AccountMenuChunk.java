package com.applause.auto.web.components;

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
import com.applause.auto.web.helpers.WebHelper;
import com.applause.auto.web.views.LandingPage;

@WebDesktopImplementation(AccountMenuChunk.class)
@WebTabletImplementation(AccountMenuChunk.class)
@WebPhoneImplementation(AccountMenuChunk.class)
public class AccountMenuChunk extends AbstractPageChunk {
	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	/**
	 * Constructor.
	 *
	 * @param selector
	 *            the selector of the chunk
	 */
	public AccountMenuChunk(UIData parent, String selector) {
		super(parent, selector);
	}

	@Override
	protected void waitUntilVisible() {
		WebHelper.waitForDocument();
	}

	/*
	 * Public actions
	 */

	/**
	 * Click Close Button on explore-dashboard-modal
	 *
	 * @return DashboardPage
	 */
	public LandingPage signOut() {
		LOGGER.info("Expanding menu");
		getExpandMenuButton().click();
		LOGGER.info("Click on Sign Out button");
		getSignOutButton().click();
		return PageFactory.create(LandingPage.class);
	}

	/*
	 * Protected Getters
	 */

	@WebElementLocator(webDesktop = "a[href='#header-account']")
	protected Button getExpandMenuButton() {
		return new Button(this, getLocator(this, "getExpandMenuButton"));
	}

	@WebElementLocator(webDesktop = "#header-account a[title='Log Out']")
	protected Button getSignOutButton() {
		return new Button(this, getLocator(this, "getSignOutButton"));
	}

}
