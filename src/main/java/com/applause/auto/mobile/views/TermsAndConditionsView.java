package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;

import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Text;

@Implementation(is = TermsAndConditionsView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = TermsAndConditionsView.class, on = Platform.MOBILE_IOS)
public class TermsAndConditionsView extends BaseComponent {

	/* -------- Elements -------- */

	@Locate(xpath = "//XCUIElementTypeOther[@name=\"THE PEETNIK REWARDS PROGRAM TERMS AND CONDITIONS\"]", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/headingText", on = Platform.MOBILE_ANDROID)
	protected Text getHeadingText;

	/* -------- Actions -------- */

	/**
	 * Get the text vaalue of the heading
	 * 
	 * @return
	 */
	public String getHeadingTextValue() {
		return getHeadingText.getText();
	}
}
