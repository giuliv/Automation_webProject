package com.applause.auto.web.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.pageframework.UIData;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.helper.SyncHelper;
import com.applause.auto.web.helpers.WebHelper;
import java.lang.invoke.MethodHandles;

@Implementation(is = ShopRunnerChunk.class, on = Platform.WEB_DESKTOP)
@Implementation(is = ShopRunnerChunk.class, on = Platform.WEB_MOBILE_TABLET)
@Implementation(is = ShopRunnerChunk.class, on = Platform.WEB_MOBILE_PHONE)
public class ShopRunnerChunk extends BaseComponent {

	/**
	 * Constructor.
	 *
	 * @param selector
	 *            the selector of the chunk
	 */
	public ShopRunnerChunk(UIData parent, String selector) {
		super(parent, selector);
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
		logger.info("Clicking Sign In");
		getEmailTextBox.sendKeys(email);
		getPasswordTextBox.sendKeys(password);
		getSignInButton.click();
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
	public <T extends BaseComponent> T continueShopping(Class<T> clazz) {
		logger.info("Clicking Continue Shopping button");
		getContinueShoppingButton.click();
		SyncHelper.sleep(6000);
		return ComponentFactory.create(clazz);
	}

	/*
	 * Public actions
	 */

	@Locate(jQuery = "input#sr_sign_in_button", on = Platform.WEB_DESKTOP)
	protected Button getSignInButton;

	@Locate(jQuery = "#sr_close", on = Platform.WEB_DESKTOP)
	protected Button getContinueShoppingButton;

	@Locate(jQuery = "#sr_signin_email", on = Platform.WEB_DESKTOP)
	protected TextBox getEmailTextBox;

	@Locate(jQuery = "#sr_signin_password", on = Platform.WEB_DESKTOP)
	protected TextBox getPasswordTextBox;

	@Locate(jQuery = "#sr_modal_inner", on = Platform.WEB_DESKTOP)
	protected ContainerElement getSignature;

}
