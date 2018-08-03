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
import com.applause.auto.pageframework.Chunks.AccountChunk;

@WebDesktopImplementation(DesktopSchedulePage.class)
@WebTabletImplementation(TabletSchedulePage.class)
@WebPhoneImplementation(PhoneSchedulePage.class)
public abstract class SchedulePage extends AbstractPage {

	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	@Override
	protected void waitUntilVisible() {
		syncHelper.waitForElementToAppear(getViewSignature());
	}

	/*
	 * Public Actions
	 */

	/**
	 * Clicks on LoginMenu
	 * 
	 * @return LoginPage
	 */
	public abstract LoginPage clickonLoginMenu();

	/**
	 * Click on AccountMenu
	 * 
	 * @return AccountChunk
	 */
	public abstract AccountChunk clickonAccountMenu();

	/*
	 * Protected Getters
	 */

	@WebElementLocator(webDesktop = "body > ion-app > ng-component > ion-split-pane > ion-nav > ng-component", webTablet = "body > ion-app > ng-component > ion-split-pane > ion-nav > ng-component", webPhone = "body > ion-app > ng-component > ion-split-pane > ion-nav > ng-component")
	protected Text getViewSignature() {
		return new Text(this, getLocator(this, "getViewSignature"));
	}

	@WebElementLocator(webDesktop = "//ion-icon[@aria-label='log in']/..", webTablet = "//ion-icon[@aria-label='log in']/..", webPhone = "//ion-icon[@aria-label='log in']/..")
	protected Button getLoginMenuButton() {
		return new Button(this, getLocator(this, "getLoginMenuButton"));
	}

	@WebElementLocator(webDesktop = "//ion-icon[@aria-label='person']/..", webTablet = "//ion-icon[@aria-label='person']/..", webPhone = "//ion-icon[@aria-label='person']/..")
	protected Button getAccountMenuButton() {
		return new Button(this, getLocator(this, "getAccountMenuButton"));
	}

	@WebElementLocator(webPhone = "//page-schedule/ion-header/ion-navbar/button[2]")
	protected Button getMenuButton() {
		return new Button(this, getLocator(this, "getMenuButton"));
	}
}

/**
 * Desktop Implementation for SchedulePage
 */
class DesktopSchedulePage extends SchedulePage {

	@Override
	public LoginPage clickonLoginMenu() {
		LOGGER.info("Clik on Login Menu button");
		getLoginMenuButton().click();
		return PageFactory.create(LoginPage.class);
	}

	@Override
	public AccountChunk clickonAccountMenu() {
		LOGGER.info("Clicked on Account Menu");
		getAccountMenuButton().click();
		return PageFactory.create(AccountChunk.class);

	}

}

/**
 * Tablet Implementation for SchedulePage
 */
class TabletSchedulePage extends SchedulePage {

	@Override
	public LoginPage clickonLoginMenu() {
		LOGGER.info("Clik on Login Menu button");
		getLoginMenuButton().click();
		return PageFactory.create(LoginPage.class);
	}

	@Override
	public AccountChunk clickonAccountMenu() {
		LOGGER.info("Clicked on Account Menu");
		getAccountMenuButton().click();
		return PageFactory.create(AccountChunk.class);

	}

}

/**
 * Phone Implementation for SchedulePage
 */
class PhoneSchedulePage extends SchedulePage {

	@Override
	public LoginPage clickonLoginMenu() {
		LOGGER.info("Click on Menu button");

		getMenuButton().click();
		LOGGER.info("Clik on Login Menu button");

		/*
		 * LoginMenu Button click working while debugging but while running the code not working as
		 * expected so adding 0.5 sec wait and then to perform click action
		 */
		syncHelper.suspend(500);
		getLoginMenuButton().click();

		return PageFactory.create(LoginPage.class);
	}

	@Override
	public AccountChunk clickonAccountMenu() {
		LOGGER.info("Click on Menu button");
		getMenuButton().click();
		LOGGER.info("Clicked on Account Menu");

		/*
		 * LoginMenu Button click working while debugging but while running the code not working as
		 * expected so adding 0.5 sec to wait for the action to complete
		 */
		syncHelper.suspend(500);
		getAccountMenuButton().click();
		return PageFactory.create(AccountChunk.class);

	}
}