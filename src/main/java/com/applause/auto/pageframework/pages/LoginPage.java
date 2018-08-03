package com.applause.auto.pageframework.pages;

import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.framework.pageframework.web.AbstractPage;
import com.applause.auto.framework.pageframework.web.PageFactory;
import com.applause.auto.framework.pageframework.web.WebElementLocator;
import com.applause.auto.framework.pageframework.web.factory.WebDesktopImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebPhoneImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebTabletImplementation;
import com.applause.auto.framework.pageframework.webcontrols.Button;
import com.applause.auto.framework.pageframework.webcontrols.EditField;

@WebDesktopImplementation(DesktopLoginPage.class)
@WebTabletImplementation(TabletLoginPage.class)
@WebPhoneImplementation(PhoneLoginPage.class)
public abstract class LoginPage extends AbstractPage {

	public static final LogController LOGGER = new LogController(LoginPage.class);

	@Override
	protected void waitUntilVisible() {
		syncHelper.waitForElementToAppear(getLoginButton());
	}

	/*
	 * Public Actions
	 */

	/**
	 * Enter Text into username field
	 * 
	 * @param text
	 */
	public void enterUsername(String text) {
		LOGGER.info("Enter Username : " + text);
		getuserName().setText(text);
	}

	/**
	 * Enter Text into Password field
	 * 
	 * @param text
	 */
	public void enterPassword(String text) {
		LOGGER.info("Enter Password : " + text);
		getPassword().setText(text);
	}

	/**
	 * Click on Login Button on Login Page
	 * 
	 * @return SchedulePage
	 */
	public SchedulePage clickonLoginButton() {
		LOGGER.info("Cliked on login button");
		getLoginButton().click();
		return PageFactory.create(SchedulePage.class);
	}

	/*
	 * Protected Getters
	 */

	@WebElementLocator(webDesktop = "input[name='username']", webTablet = "input[name='username']", webPhone = "input[name='username']")
	protected EditField getuserName() {
		return new EditField(this, getLocator(this, "getuserName"));
	}

	@WebElementLocator(webDesktop = "input[name='password']", webTablet = "input[name='password']", webPhone = "input[name='password']")
	protected EditField getPassword() {
		return new EditField(this, getLocator(this, "getPassword"));
	}

	@WebElementLocator(webDesktop = "button[type='submit']", webTablet = "button[type='submit']", webPhone = "button[type='submit']")
	protected Button getLoginButton() {
		return new Button(this, getLocator(this, "getLoginButton"));
	}

}

/**
 * Desktop Login Implementation
 */
class DesktopLoginPage extends LoginPage {

}

/**
 * Tablet Login Page Implementation
 * 
 */
class TabletLoginPage extends LoginPage {

}

/**
 * Mobile LoginPage Implementation
 */
class PhoneLoginPage extends LoginPage {

}