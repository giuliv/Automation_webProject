package com.applause.auto.pageframework.views;

import java.lang.invoke.MethodHandles;

import com.applause.auto.framework.pageframework.device.AbstractDeviceView;
import com.applause.auto.framework.pageframework.device.DeviceViewFactory;
import com.applause.auto.framework.pageframework.device.MobileElementLocator;
import com.applause.auto.framework.pageframework.device.factory.AndroidImplementation;
import com.applause.auto.framework.pageframework.device.factory.IosImplementation;
import com.applause.auto.framework.pageframework.devicecontrols.Button;
import com.applause.auto.framework.pageframework.devicecontrols.Text;
import com.applause.auto.framework.pageframework.util.logger.LogController;

@AndroidImplementation(CreateAccountView.class)
@IosImplementation(CreateAccountView.class)
public class CreateAccountView extends AbstractDeviceView {

	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	@Override
	protected void waitUntilVisible() {
		syncHelper.waitForElementToAppear(getHeadingText());
	}

	/**
	 * Get the text vaalue of the heading
	 * 
	 * @return
	 */
	public String getHeadingTextValue() {
		return getHeadingText().getStringValue();
	}

	public PrivacyPolicyView privacyPolicy() {
		LOGGER.info("Tap on Privacy Policy");
		getPrivacyPolicyButton().pressButton();
		return DeviceViewFactory.create(PrivacyPolicyView.class);
	}

	public TermsAndConditionsView termsAndConditions() {
		LOGGER.info("Tap on Terms and Conditions");
		getPrivacyPolicyButton().pressButton();
		return DeviceViewFactory.create(TermsAndConditionsView.class);
	}

	/*
	 * Protected Getters
	 */

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/headingText", iOS = "Privacy Policy")
	protected Button getPrivacyPolicyButton() {
		return new Button(getLocator(this, "getPrivacyPolicyButton"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/headingText", iOS = "Terms and Conditions")
	protected Button getTermsAndConditionsButton() {
		return new Button(getLocator(this, "getTermsAndConditionsButton"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/headingText", iOS = "//XCUIElementTypeNavigationBar[@name=\"Create Account\"]")
	protected Text getHeadingText() {
		return new Text(getLocator(this, "getHeadingText"));
	}

}
