package com.applause.auto.pageframework.views;

import java.lang.invoke.MethodHandles;

import com.applause.auto.framework.pageframework.device.AbstractDeviceView;
import com.applause.auto.framework.pageframework.device.MobileElementLocator;
import com.applause.auto.framework.pageframework.device.factory.AndroidImplementation;
import com.applause.auto.framework.pageframework.device.factory.IosImplementation;
import com.applause.auto.framework.pageframework.devicecontrols.Text;
import com.applause.auto.framework.pageframework.util.logger.LogController;

@AndroidImplementation(AndroidPrivacyPolicyView.class)
@IosImplementation(PrivacyPolicyView.class)
public class PrivacyPolicyView extends AbstractDeviceView {

	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	@Override
	protected void waitUntilVisible() {
		syncHelper.waitForElementToAppear(getHeadingText(), 120000);
	}

	/*
	 * Protected Getters
	 */

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/headingText", iOS = "//XCUIElementTypeOther[contains(@name,\"PRIVACY POLICY\")][1]")
	protected Text getHeadingText() {
		return new Text(getLocator(this, "getHeadingText"));
	}

}

class AndroidPrivacyPolicyView extends PrivacyPolicyView {
	protected void waitUntilVisible() {
		throw new RuntimeException("Not Yet Implemeted. Blocked by WEB context switching issue");
		// syncHelper.waitForElementToAppear(getHeadingText(), 120000);
	}
}