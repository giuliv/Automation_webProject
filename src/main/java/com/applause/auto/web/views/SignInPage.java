package com.applause.auto.web.views;

import com.applause.auto.common.data.Constants;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.pageframework.util.webDrivers.BrowserType;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.helper.SyncHelper;
import com.applause.auto.web.helpers.WebHelper;

@Implementation(is = SignInPage.class, on = Platform.WEB)
public class SignInPage extends BaseComponent {

	/* -------- Elements -------- */

	/* -------- Actions -------- */


	/**
	 * Enter Text into email field
	 * 
	 * @param text
	 */
	public void enterEmail(String text) {
		logger.info("Enter email : " + text);
		getEmailTextBox.sendKeys(text);
	}

	/**
	 * Enter Text into Email Field
	 *
	 * @param email
	 * @param safariEmail
	 */
	public void enterEmailByBrowser(String email, String safariEmail) {
		logger.info("Enter email");
		if (EnvironmentHelper.isSafari(DriverManager.getDriver()))
			getEmailTextBox.sendKeys(safariEmail);
		else
			getEmailTextBox.sendKeys(email);
	}

	/**
	 * Enter Text into Password field
	 * 
	 * @param text
	 */
	public void enterPassword(String text) {
		logger.info("Enter Password : " + text);
		getPasswordTextBox.sendKeys(text);
	}

	/**
	 * Click on Login Button on Login Page
	 * 
	 * @return My Account page
	 */
	public MyAccountPage clickonSignInButton() {
		logger.info("Click on sign in button");
		getSignInButton.click();
		return ComponentFactory.create(MyAccountPage.class);
	}

	/**
	 * User Login
	 *
	 * @return MyAccountPage
	 */
	public MyAccountPage mainUserLogin() {
		logger.info("Login with main user");
		String username = (EnvironmentHelper.isSafari(DriverManager.getDriver())) ? Constants.TestData.USERNAME_SAFARI
				: Constants.TestData.USERNAME;
		enterEmail(username);
		enterPassword(Constants.TestData.PASSWORD);
		SyncHelper.sleep(5000);
		getSignInButton.click();
		return ComponentFactory.create(MyAccountPage.class);
	}

	/**
	 * User login my account page.
	 *
	 * @param email
	 *            the email
	 * @param password
	 *            the password
	 * @return the my account page
	 */
	public MyAccountPage userLogin(String email, String password) {
		logger.info("Login with main user");
		enterEmail(email);
		enterPassword(password);
		SyncHelper.sleep(5000);
		getSignInButton.click();
		return ComponentFactory.create(MyAccountPage.class);
	}

	/**
	 * Click on Create Account Button on Login Page
	 *
	 * @return SignUppage
	 */
	public SignUpPage clickonCreateAccountButton() {
		logger.info("Click on Create Account button");
		getCreateAccountButton.click();
		return ComponentFactory.create(SignUpPage.class);
	}

	/*
	 * Protected Getters
	 */

	@Locate(css = "#email", on = Platform.WEB)
	protected TextBox getEmailTextBox;

	@Locate(css = "#pass", on = Platform.WEB)
	protected TextBox getPasswordTextBox;

	@Locate(css = "#send2", on = Platform.WEB)
	protected Button getSignInButton;

	@Locate(css = ".new-users button", on = Platform.WEB)
	protected Button getCreateAccountButton;
}