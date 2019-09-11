package com.applause.auto.pageframework.views;

import java.lang.invoke.MethodHandles;

import com.applause.auto.framework.pageframework.device.AbstractDeviceView;
import com.applause.auto.framework.pageframework.device.MobileElementLocator;
import com.applause.auto.framework.pageframework.device.factory.AndroidImplementation;
import com.applause.auto.framework.pageframework.device.factory.IosImplementation;
import com.applause.auto.framework.pageframework.devicecontrols.Button;
import com.applause.auto.framework.pageframework.devicecontrols.Text;
import com.applause.auto.framework.pageframework.util.logger.LogController;

import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;

@AndroidImplementation(AuthenticationView.class)
@IosImplementation(AuthenticationView.class)
public class AuthenticationView extends AbstractDeviceView {

	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	@Override
	protected void waitUntilVisible() {
		if (!getCreateAccountButton().isDisplayed()) {
			LOGGER.info("View does not visible trying to set focus");
			(new TouchAction(getDriver())).tap(PointOption.point(20, 200)).perform();
		}
		syncHelper.waitForElementToAppear(getCreateAccountButton());
	}

	/*
	 * Public Actions
	 */
	/**
	 * Get the text value of the reward title
	 * 
	 * @return
	 */
	public String getRewardTitleTextValue() {
		return getRewardTitleText().getStringValue();
	}

	/*
	 * Protected Getters
	 */

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/signUp", iOS = "//XCUIElementTypeButton[@name='Create Account']")
	protected Button getCreateAccountButton() {
		return new Button(getLocator(this, "getCreateAccountButton"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/rewardTitle", iOS = "Peetnik Rewards")
	protected Text getRewardTitleText() {
		return new Text(getLocator(this, "getRewardTitleText"));
	}
}
