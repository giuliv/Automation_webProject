package com.applause.auto.web.components;

import java.lang.invoke.MethodHandles;

import com.applause.auto.framework.pageframework.UIData;
import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.framework.pageframework.web.AbstractPage;
import com.applause.auto.framework.pageframework.web.AbstractPageChunk;
import com.applause.auto.framework.pageframework.web.PageFactory;
import com.applause.auto.framework.pageframework.web.WebElementLocator;
import com.applause.auto.framework.pageframework.web.factory.WebDesktopImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebPhoneImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebTabletImplementation;
import com.applause.auto.framework.pageframework.webcontrols.BaseHtmlElement;
import com.applause.auto.framework.pageframework.webcontrols.Button;
import com.applause.auto.framework.pageframework.webcontrols.EditField;
import com.applause.auto.web.helpers.WebHelper;

@WebDesktopImplementation(ShopRunnerChunk.class)
@WebTabletImplementation(ShopRunnerChunk.class)
@WebPhoneImplementation(ShopRunnerChunk.class)
public class ShopRunnerChunk extends AbstractPageChunk {
	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	/**
	 * Constructor.
	 *
	 * @param selector
	 *            the selector of the chunk
	 */
	public ShopRunnerChunk(UIData parent, String selector) {
		super(parent, selector);
	}

	@Override
	protected void waitUntilVisible() {
		WebHelper.waitForDocument();
		syncHelper.waitForElementToAppear(getSignature().getAbsoluteSelector());
		syncHelper.suspend(5000);
	}

	/**
	 * Sign in.
	 *
	 * @param email
	 *            the email
	 * @param password
	 *            the password
	 */
	public void signIn(String email, String password) {
		LOGGER.info("Clicking Sign In");
		getEmailEditField().setText(email);
		getPasswordEditField().setText(password);
		getSignInButton().click();
	}

	/**
	 * Continue shopping t.
	 *
	 * @param <T>
	 *            the type parameter
	 * @param clazz
	 *            the clazz
	 * @return the t
	 */
	public <T extends AbstractPage> T continueShopping(Class<T> clazz) {
		LOGGER.info("Clicking Continue Shopping button");
		getContinueShoppingButton().click();
		syncHelper.suspend(6000);
		return PageFactory.create(clazz);
	}

	/*
	 * Public actions
	 */

	@WebElementLocator(webDesktop = "input#sr_sign_in_button")
	protected Button getSignInButton() {
		return new Button(this, getLocator(this, "getSignInButton"));
	}

	@WebElementLocator(webDesktop = "#sr_close")
	protected Button getContinueShoppingButton() {
		return new Button(this, getLocator(this, "getContinueShoppingButton"));
	}

	@WebElementLocator(webDesktop = "#sr_signin_email")
	protected EditField getEmailEditField() {
		return new EditField(this, getLocator(this, "getEmailEditField"));
	}

	@WebElementLocator(webDesktop = "#sr_signin_password")
	protected EditField getPasswordEditField() {
		return new EditField(this, getLocator(this, "getPasswordEditField"));
	}

	@WebElementLocator(webDesktop = "#sr_modal_inner")
	protected BaseHtmlElement getSignature() {
		return new BaseHtmlElement(this, getLocator(this, "getSignature"));
	}

}
