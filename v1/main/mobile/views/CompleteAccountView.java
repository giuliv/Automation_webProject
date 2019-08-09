package com.applause.auto.mobile.views;

import java.lang.invoke.MethodHandles;

import com.applause.auto.framework.pageframework.device.AbstractDeviceView;
import com.applause.auto.framework.pageframework.device.MobileElementLocator;
import com.applause.auto.framework.pageframework.device.factory.AndroidImplementation;
import com.applause.auto.framework.pageframework.device.factory.IosImplementation;
import com.applause.auto.framework.pageframework.devicecontrols.Text;
import com.applause.auto.framework.pageframework.util.logger.LogController;

@AndroidImplementation(CompleteAccountView.class)
@IosImplementation(CompleteAccountView.class)
public class CompleteAccountView extends AbstractDeviceView {

	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	@Override
	protected void waitUntilVisible() {
		syncHelper.waitForElementToAppear(getSignature(), 120000);
	}

	/*
	 * Protected Getters
	 */

	@MobileElementLocator(android = "//android.widget.TextView[@text='COMPLETE ACCOUNT']", iOS = "//XCUIElementTypeOther[@name=\"COMPLETE ACCOUNT\"]")
	protected Text getSignature() {
		return new Text(getLocator(this, "getSignature"));
	}

}
