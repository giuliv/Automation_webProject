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

@WebDesktopImplementation(DesktopLandingPage.class)
@WebTabletImplementation(TabletLandingPage.class)
@WebPhoneImplementation(PhoneLandingPage.class)
public abstract class LandingPage extends AbstractPage {

	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	@Override
	protected void waitUntilVisible() {
		syncHelper.waitForElementToAppear(getViewSignature());
	}

	/*
	 * Public Actions
	 */

	/**
	 * Taps the skip button.
	 * 
	 * @return a SchedulePage
	 */
	public SchedulePage tapSkipButton() {
		getSkipButton().click();
		LOGGER.info("Tap on Skip Button");
		return PageFactory.create(SchedulePage.class);
	}

	/*
	 * Protected Getters
	 */

	@WebElementLocator(webDesktop = ".slide-title:contains('Welcome')", webTablet = ".slide-title", webPhone = ".slide-zoom > h2.slide-title > b")
	protected Text getViewSignature() {
		return new Text(this, getLocator(this, "getViewSignature"));
	}

	@WebElementLocator(webDesktop = "button[color='primary']", webTablet = "button[color='primary']", webPhone = "button[color='primary']")
	protected Button getSkipButton() {
		return new Button(this, getLocator(this, "getSkipButton"));
	}
}

/**
 * Desktop Implementation for LandingPage
 */
class DesktopLandingPage extends LandingPage {

}

/**
 * Tablet Implementation for LandingPage
 */
class TabletLandingPage extends LandingPage {
}

/**
 * Phone Implementation for LandingPage
 */
class PhoneLandingPage extends LandingPage {

}