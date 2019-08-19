package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.pageframework.device.MobileElementLocator;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Text;
import java.lang.invoke.MethodHandles;

@Implementation(is = CompleteAccountView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = CompleteAccountView.class, on = Platform.MOBILE_IOS)
public class CompleteAccountView extends BaseComponent {

	@Locate(xpath = "//XCUIElementTypeOther[@name=\"COMPLETE ACCOUNT\"]", on = Platform.MOBILE_IOS)
	@Locate(xpath = "//android.widget.TextView[@text='COMPLETE ACCOUNT']", on = Platform.MOBILE_ANDROID)
	protected Text getSignature;

}
