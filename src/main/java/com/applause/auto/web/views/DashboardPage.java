package com.applause.auto.web.views;

import java.lang.invoke.MethodHandles;

import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.framework.pageframework.web.AbstractPage;
import com.applause.auto.framework.pageframework.web.ChunkFactory;
import com.applause.auto.framework.pageframework.web.WebElementLocator;
import com.applause.auto.framework.pageframework.web.factory.WebDesktopImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebPhoneImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebTabletImplementation;
import com.applause.auto.framework.pageframework.webcontrols.Text;
import com.applause.auto.web.components.MainMenuChunk;
import com.applause.auto.web.helpers.WebHelper;

@WebDesktopImplementation(DashboardPage.class)
@WebTabletImplementation(DashboardPage.class)
@WebPhoneImplementation(DashboardPage.class)
public class DashboardPage extends AbstractPage {

	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	@Override
	protected void waitUntilVisible() {
		WebHelper.waitForDocument();
		syncHelper.waitForElementToAppear(getViewSignature());
	}

	/*
	 * Public Actions
	 */

	/**
	 * Gets Main Menu
	 *
	 * @return MainMenuChunk
	 */
	public MainMenuChunk getMainMenu() {
		LOGGER.info("Getting Main Menu");
		return ChunkFactory.create(MainMenuChunk.class, this, "");
	}

	/*
	 * Protected Getters
	 */

	@WebElementLocator(webDesktop = ".dashboard")
	protected Text getViewSignature() {
		return new Text(this, getLocator(this, "getViewSignature"));
	}

}
