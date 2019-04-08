package com.applause.auto.pageframework.pages;

import com.applause.auto.framework.pageframework.util.drivers.BrowserType;
import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.framework.pageframework.web.AbstractPage;
import com.applause.auto.framework.pageframework.web.PageFactory;
import com.applause.auto.framework.pageframework.web.WebElementLocator;
import com.applause.auto.framework.pageframework.web.factory.WebDesktopImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebPhoneImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebTabletImplementation;
import com.applause.auto.framework.pageframework.webcontrols.Button;
import com.applause.auto.framework.pageframework.webcontrols.EditField;
import com.applause.auto.pageframework.helpers.WebHelper;
import com.applause.auto.pageframework.testdata.TestConstants;

@WebDesktopImplementation(SignInPage.class)
@WebTabletImplementation(SignInPage.class)
@WebPhoneImplementation(SignInPage.class)
public class SignInPage extends AbstractPage {

	public static final LogController LOGGER = new LogController(SignInPage.class);

	@Override
	protected void waitUntilVisible() {
		WebHelper.waitForDocument();
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
	 * Enter Text into Email Field
	 *
	 * @param email
	 * @param safariEmail
	 */
	public void enterEmailByBrowser(String email, String safariEmail) {
		LOGGER.info("Enter email");
		if (env.getBrowserType() == BrowserType.SAFARI)
			getEmailEditField().setText(safariEmail);
		else
			getEmailEditField().setText(email);
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

	/**
	 * User Login
	 *
	 * @return MyAccountPage
	 */
	public MyAccountPage mainUserLogin() {
		LOGGER.info("Login with main user");
		String username = (env.getBrowserType() == BrowserType.SAFARI) ? TestConstants.TestData.USERNAME_SAFARI
				: TestConstants.TestData.USERNAME;
		enterEmail(username);
		enterPassword(TestConstants.TestData.PASSWORD);
		syncHelper.suspend(5000);
		getSignInButton().click();
		return PageFactory.create(MyAccountPage.class);
	}

	public MyAccountPage userLogin(String email, String password) {
		LOGGER.info("Login with main user");
		enterEmail(email);
		enterPassword(password);
		syncHelper.suspend(5000);
		getSignInButton().click();
		return PageFactory.create(MyAccountPage.class);
	}

	/**
	 * Click on Create Account Button on Login Page
	 *
	 * @return SignUppage
	 */
	public SignUpPage clickonCreateAccountButton() {
		LOGGER.info("Click on Create Account button");
		getCreateAccountButton().click();
		return PageFactory.create(SignUpPage.class);
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

	@WebElementLocator(webDesktop = ".new-users button")
	protected Button getCreateAccountButton() {
		return new Button(this, getLocator(this, "getCreateAccountButton"));
	}
}