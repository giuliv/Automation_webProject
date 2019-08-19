package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.pageframework.device.MobileElementLocator;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.util.helper.SyncHelper;
import java.lang.invoke.MethodHandles;

@Implementation(is = AndroidPrivacyPolicyView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = PrivacyPolicyView.class, on = Platform.MOBILE_IOS)
public class PrivacyPolicyView extends BaseComponent {

	@Locate(xpath = "//XCUIElementTypeOther[contains(@name,\"PRIVACY POLICY\")][1]", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/headingText", on = Platform.MOBILE_ANDROID)
	protected Text getHeadingText;

}

class AndroidPrivacyPolicyView extends PrivacyPolicyView {
	public void waitUntilVisible() {
		SyncHelper.sleep(5000);
		throw new RuntimeException("Not Yet Implemeted. Blocked by WEB context switching issue");
		// SyncHelper.waitUntilElementPresent(getHeadingText, 120000);
	}
}