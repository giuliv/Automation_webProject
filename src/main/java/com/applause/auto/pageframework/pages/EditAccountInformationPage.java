package com.applause.auto.pageframework.pages;

import java.lang.invoke.MethodHandles;

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
import com.applause.auto.framework.pageframework.webcontrols.Text;
import com.applause.auto.pageframework.helpers.WebHelper;

@WebDesktopImplementation(EditAccountInformationPage.class)
@WebTabletImplementation(EditAccountInformationPage.class)
@WebPhoneImplementation(EditAccountInformationPage.class)
public class EditAccountInformationPage extends AbstractPage {
	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	@Override
	protected void waitUntilVisible() {
		WebHelper.waitForDocument();
		syncHelper.waitForElementToAppear(getViewSignature());
	}

	// Public actions

	/**
	 * Enter First Name
	 *
	 * @param firstName
	 */
	public void enterFirstName(String firstName) {
		LOGGER.info("Entering First Name");
		getFirstNameField().clearText();
		getFirstNameField().setText(firstName);
	}

	/**
	 * Enter Email
	 *
	 * @param email
	 */
	public void enterEmail(String email) {
		LOGGER.info("Entering Email");
		getEmailField().clearText();
		getEmailField().setText(email);
	}

	/**
	 * Enter Email By Browser
	 *
	 * @param email
	 * @param safariEmail
	 */
	public void enterEmailByBrowser(String email, String safariEmail) {
		LOGGER.info("Entering Email");
		if (env.getBrowserType() == BrowserType.SAFARI) {
			enterEmail(safariEmail);
		} else {
			enterEmail(email);
		}
	}

	/**
	 * Enter Password
	 *
	 * @param password
	 */
	public void enterCurrentPassword(String password) {
		LOGGER.info("Entering Current Password");
		getCurrentPasswordField().setText(password);
	}

	/**
	 * Click Save Button
	 *
	 * @return MyAccountPage
	 */
	public MyAccountPage clickSave() {
		LOGGER.info("Clicking Save");
		getSaveButton().click();
		return PageFactory.create(MyAccountPage.class);
	}

	// Protected getters
	@WebElementLocator(webDesktop = "body > div.wrapper > div > div.main-container.col2-left-layout > div > div.col-main > div > div.page-title > h1")
	protected Text getViewSignature() {
		return new Text(this, getLocator(this, "getViewSignature"));
	}

	@WebElementLocator(webDesktop = "#firstname")
	protected EditField getFirstNameField() {
		return new EditField(this, getLocator(this, "getFirstNameField"));
	}

	@WebElementLocator(webDesktop = "#email")
	protected EditField getEmailField() {
		return new EditField(this, getLocator(this, "getEmailField"));
	}

	@WebElementLocator(webDesktop = "button.button.btn-save")
	protected Button getSaveButton() {
		return new Button(this, getLocator(this, "getSaveButton"));
	}

	@WebElementLocator(webDesktop = "#current_password")
	protected EditField getCurrentPasswordField() {
		return new EditField(this, getLocator(this, "getCurrentPasswordField"));
	}
}
