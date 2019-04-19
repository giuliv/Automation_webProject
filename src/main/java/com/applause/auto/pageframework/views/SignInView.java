package com.applause.auto.pageframework.views;

import java.lang.invoke.MethodHandles;

import com.applause.auto.framework.pageframework.device.AbstractDeviceView;
import com.applause.auto.framework.pageframework.device.DeviceViewFactory;
import com.applause.auto.framework.pageframework.device.MobileElementLocator;
import com.applause.auto.framework.pageframework.device.factory.AndroidImplementation;
import com.applause.auto.framework.pageframework.device.factory.IosImplementation;
import com.applause.auto.framework.pageframework.devicecontrols.Button;
import com.applause.auto.framework.pageframework.devicecontrols.TextBox;
import com.applause.auto.framework.pageframework.util.logger.LogController;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;

@AndroidImplementation(AndroidSignInView.class)
@IosImplementation(SignInView.class)
public class SignInView extends AbstractDeviceView {

	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	@Override
	protected void waitUntilVisible() {
		syncHelper.waitForElementToAppear(getUsernameTextBox());
	}

	/*
	 * Public Actions
	 */
	/**
	 * Get the text value of the reward title
	 * 
	 * @return
	 */
	public void setUsername(String username) {
		LOGGER.info("Set username: " + username);
		getUsernameTextBox().clearTextBox();
		getUsernameTextBox().enterText(username);
	}

	/**
	 * Sets password.
	 *
	 * @param password
	 *            the password
	 */
	public void setPassword(String password) {
		LOGGER.info("Set password: " + password);
		getPasswordTextBox().clearTextBox();
		getPasswordTextBox().enterText(password);
		syncHelper.suspend(3000);
	}

	/**
	 * Gets password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return getPasswordTextBox().getCurrentText();
	}

	/**
	 * Gets un encrypted password.
	 *
	 * @return the un encrypted password
	 */
	public String getUnEncryptedPassword() {
		return getUnEncryptedPasswordTextBox().getCurrentText();
	}

	/**
	 * Sign in.
	 */
	public DashboardView signIn() {
		LOGGER.info("Click on Sign In button");
		getSignInButton().pressButton();
		return DeviceViewFactory.create(DashboardView.class);
	}

	/**
	 * Show password.
	 */
	public void showPassword() {
		LOGGER.info("Click on Show Password button");
		getShowPasswordButton().pressButton();
	}

	/*
	 * Protected Getters
	 */

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/emailAddress", iOS = "//XCUIElementTypeTextField")
	protected TextBox getUsernameTextBox() {
		return new TextBox(getLocator(this, "getUsernameTextBox"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/loginButton", iOS = "//XCUIElementTypeButton[@name=\"Sign In\"]")
	protected Button getSignInButton() {
		return new Button(getLocator(this, "getSignInButton"));
	}

	@MobileElementLocator(android = "NA", iOS = "hide password")
	protected Button getShowPasswordButton() {
		return new Button(getLocator(this, "getShowPasswordButton"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/password", iOS = "//XCUIElementTypeSecureTextField")
	protected TextBox getPasswordTextBox() {
		return new TextBox(getLocator(this, "getPasswordTextBox"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/password", iOS = "//XCUIElementTypeButton[@name=\"reveal password\"]/preceding-sibling:: XCUIElementTypeTextField")
	protected TextBox getUnEncryptedPasswordTextBox() {
		return new TextBox(getLocator(this, "getUnEncryptedPasswordTextBox"));
	}

}

class AndroidSignInView extends SignInView {
	public void showPassword() {
		LOGGER.info("Click on Show Password button");
		MobileElement element = getPasswordTextBox().getMobileElement();
		int x = element.getCenter().getX();
		int y = element.getCenter().getY();
		int width = element.getSize().getWidth();
		(new TouchAction(getDriver())).tap(PointOption.point(x + width / 2 - 5, y)).perform();
	}

}
