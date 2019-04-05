package com.applause.auto.pageframework.views;

import java.lang.invoke.MethodHandles;

import org.openqa.selenium.Dimension;

import com.applause.auto.framework.pageframework.device.AbstractDeviceView;
import com.applause.auto.framework.pageframework.device.DeviceViewFactory;
import com.applause.auto.framework.pageframework.device.MobileElementLocator;
import com.applause.auto.framework.pageframework.device.factory.AndroidImplementation;
import com.applause.auto.framework.pageframework.device.factory.IosImplementation;
import com.applause.auto.framework.pageframework.devicecontrols.Button;
import com.applause.auto.framework.pageframework.devicecontrols.Text;
import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.pageframework.helpers.MobileHelper;

@AndroidImplementation(AndroidCreateAccountView.class)
@IosImplementation(CreateAccountView.class)
public class CreateAccountView extends AbstractDeviceView {

	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	@Override
	protected void waitUntilVisible() {
		syncHelper.waitForElementToAppear(getHeadingText());
	}

	public PrivacyPolicyView privacyPolicy() {
		LOGGER.info("Tap on Privacy Policy");
		getPrivacyPolicyButton().pressButton();
		return DeviceViewFactory.create(PrivacyPolicyView.class);
	}

	public TermsAndConditionsView termsAndConditions() {
		LOGGER.info("Tap on Terms and Conditions");
		getTermsAndConditionsButton().pressButton();
		return DeviceViewFactory.create(TermsAndConditionsView.class);
	}

	/*
	 * Protected Getters
	 */

	@MobileElementLocator(android = "//*[contains(@text,'Privacy Policy')]", iOS = "Privacy Policy")
	protected Button getPrivacyPolicyButton() {
		return new Button(getLocator(this, "getPrivacyPolicyButton"));
	}

	@MobileElementLocator(android = "//*[contains(@text,'Conditions')]", iOS = "Terms & Conditions")
	protected Button getTermsAndConditionsButton() {
		return new Button(getLocator(this, "getTermsAndConditionsButton"));
	}

	@MobileElementLocator(android = "//android.widget.TextView[@text='Create Account']", iOS = "//XCUIElementTypeNavigationBar[@name=\"Create Account\"]")
	protected Text getHeadingText() {
		return new Text(getLocator(this, "getHeadingText"));
	}

}

class AndroidCreateAccountView extends CreateAccountView {
	public TermsAndConditionsView termsAndConditions() {
		LOGGER.info("Tap on Terms and Conditions");
		Dimension size = getTermsAndConditionsButton().getMobileElement().getSize();
		MobileHelper.tapOnElementWithOffset(getTermsAndConditionsButton(), size.getWidth() / 3, 0);
		return DeviceViewFactory.create(TermsAndConditionsView.class);
	}

}
