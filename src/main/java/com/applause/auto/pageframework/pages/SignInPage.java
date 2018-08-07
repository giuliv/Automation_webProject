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

@WebDesktopImplementation(SignInPage.class)
@WebTabletImplementation(SignInPage.class)
@WebPhoneImplementation(SignInPage.class)
public class SignInPage extends AbstractPage {

	public static final LogController LOGGER = new LogController(SignInPage.class);

	@Override
	protected void waitUntilVisible() {
		syncHelper.waitForElementToAppear(getSignInButton());
	}

	/*
	 * Public Actions
	 */

	/**
	 * Enter Text into email field
	 * 
	 * @param text
	 */
	public void enterEmail(String text) {
		LOGGER.info("Enter email : " + text);
		getEmailEditField().setText(text);
	}

	/**
	 * Enter Text into Password field
	 * 
	 * @param text
	 */
	public void enterPassword(String text) {
		LOGGER.info("Enter Password : " + text);
		getPasswordEditField().setText(text);
	}

	/**
	 * Click on Login Button on Login Page
	 * 
	 * @return My Account page
	 */
	public MyAccountPage clickonSignInButton() {
		LOGGER.info("Click on sign in button");
		getSignInButton().click();
		return PageFactory.create(MyAccountPage.class);
	}

	/*
	 * Protected Getters
	 */

	@WebElementLocator(webDesktop = "#email")
	protected EditField getEmailEditField() {
		return new EditField(this, getLocator(this, "getEmailEditField"));
	}

	@WebElementLocator(webDesktop = "#pass")
	protected EditField getPasswordEditField() {
		return new EditField(this, getLocator(this, "getPasswordEditField"));
	}

	@WebElementLocator(webDesktop = "#send2")
	protected Button getSignInButton() {
		return new Button(this, getLocator(this, "getSignInButton"));
	}

}