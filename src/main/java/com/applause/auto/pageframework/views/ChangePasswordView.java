package com.applause.auto.pageframework.views;

import java.lang.invoke.MethodHandles;

import com.applause.auto.framework.pageframework.device.AbstractDeviceView;
import com.applause.auto.framework.pageframework.device.DeviceViewFactory;
import com.applause.auto.framework.pageframework.device.MobileElementLocator;
import com.applause.auto.framework.pageframework.device.factory.AndroidImplementation;
import com.applause.auto.framework.pageframework.device.factory.IosImplementation;
import com.applause.auto.framework.pageframework.devicecontrols.Button;
import com.applause.auto.framework.pageframework.devicecontrols.Text;
import com.applause.auto.framework.pageframework.devicecontrols.TextBox;
import com.applause.auto.framework.pageframework.util.logger.LogController;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;

@AndroidImplementation(AndroidChangePasswordView.class)
@IosImplementation(ChangePasswordView.class)
public class ChangePasswordView extends AbstractDeviceView {

	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	@Override
	protected void waitUntilVisible() {
		syncHelper.waitForElementToAppear(getSignature());
	}

	public void setCurrentPassword(String password) {
		LOGGER.info("Set current password to: " + password);
		// workaroud for password cleanup
		getNewPasswordTextBox().clickTextBox();
		getOldPasswordTextBox().clearTextBox();
		getOldPasswordTextBox().enterText(password);
	}

	public String getMessage() {
		return getMessageText().getStringValue();
	}

	public void showPassword() {
		LOGGER.info("Click on Show Password button");
		getShowPasswordButton().pressButton();
	}

	public <T extends AbstractDeviceView> T dismissMessage(Class<T> clazz) {
		LOGGER.info("Tap on OKAY to dismiss message");
		getMessageOkButton().pressButton();
		return DeviceViewFactory.create(clazz);
	}

	/**
	 * Set new pas ósword.
	 *
	 * @param password
	 *            the password
	 */
	public void setNewPassword(String password) {
		LOGGER.info("Set new password to: " + password);
		getNewPasswordTextBox().clearTextBox();
		getNewPasswordTextBox().enterText(password);
	}

	/**
	 * Change password t.
	 *
	 * @param <T>
	 *            the type parameter
	 * @param clazz
	 *            the clazz
	 * @return the t
	 */
	public <T extends AbstractDeviceView> T changePassword(Class<T> clazz) {
		LOGGER.info("Tap on change password button");
		getChangePasswordButton().pressButton();
		return DeviceViewFactory.create(clazz);
	}

	public String getCurrentPasswordUnhide() {
		return getOldPasswordUnhiddenTextBox().getCurrentText();
	}

	/*
	 * Protected Getters
	 */

	@MobileElementLocator(android = "//android.widget.TextView[@text='Change Password']|//*[@text='OKAY']", iOS = "")
	protected TextBox getSignature() {
		return new TextBox(getLocator(this, "getSignature"));
	}

	@MobileElementLocator(android = "NA", iOS = "hide password")
	protected Button getShowPasswordButton() {
		return new Button(getLocator(this, "getShowPasswordButton"));
	}

	@MobileElementLocator(android = "//*[@resource-id='android:id/message']|//*[@resource-id='com.wearehathway.peets.development:id/md_content']", iOS = "//XCUIElementTypeCell[@name=\"Location\"]")
	protected Text getMessageText() {
		return new Text(getLocator(this, "getMessageText"));
	}

	@MobileElementLocator(android = "//*[@text='Okay']", iOS = "//XCUIElementTypeCell[@name=\"Location\"]")
	protected Button getMessageOkButton() {
		return new Button(getLocator(this, "getMessageOkButton"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/oldPassword", iOS = "//XCUIElementTypeCell[@name=\"Location\"]")
	protected TextBox getOldPasswordTextBox() {
		return new TextBox(getLocator(this, "getOldPasswordTextBox"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/oldPassword", iOS = "//XCUIElementTypeCell[@name=\"Location\"]")
	protected TextBox getOldPasswordUnhiddenTextBox() {
		return new TextBox(getLocator(this, "getOldPasswordUnhiddenTextBox"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/newPassword", iOS = "//XCUIElementTypeCell[@name=\"Location\"]")
	protected TextBox getNewPasswordTextBox() {
		return new TextBox(getLocator(this, "getNewPasswordTextBox"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/updateButton", iOS = "//XCUIElementTypeCell[@name=\"Never\"]")
	protected Button getChangePasswordButton() {
		return new Button(getLocator(this, "getChangePasswordButton"));
	}
}

class AndroidChangePasswordView extends ChangePasswordView {
	@Override
	public void showPassword() {
		LOGGER.info("Click on Show Password button");
		MobileElement element = getOldPasswordTextBox().getMobileElement();
		int x = element.getCenter().getX();
		int y = element.getCenter().getY();
		int width = element.getSize().getWidth();
		(new TouchAction(getDriver())).tap(PointOption.point(x + width / 2 - 5, y)).perform();
	}
}