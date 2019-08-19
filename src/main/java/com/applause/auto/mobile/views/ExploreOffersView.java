package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.pageframework.device.MobileElementLocator;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ScrollView;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import java.lang.invoke.MethodHandles;

@Implementation(is = ExploreOffersView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = ExploreOffersView.class, on = Platform.MOBILE_IOS)
public class ExploreOffersView extends BaseComponent {

	/**
	 * Swipe left on tutorial view and expect to arrive at next view
	 * 
	 * @return
	 */
	public PayFasterView swipeLeftOnScreen() {
		logger.info("Swiping left to get to next tutorial view");
		MobileHelper.swipeLeft();
		return ComponentFactory.create(PayFasterView.class);
	}

	/**
	 * Get the text vaalue of the heading
	 * 
	 * @return
	 */
	public String getHeadingTextValue() {
		return getHeadingText.getText();
	}

	@Locate(id = "Explore Offers.", on = Platform.MOBILE_IOS)
	@Locate(androidUIAutomator = "new UiSelector().textContains(\"Explore Offers\")", on = Platform.MOBILE_ANDROID)
	protected Text getHeadingText;

	@Locate(id = "Skip", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/skipTextView", on = Platform.MOBILE_ANDROID)
	protected Button getSkipButton;

	@Locate(xpath = "//XCUIElementTypeOther[1]/XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/onBoardingViewPager", on = Platform.MOBILE_ANDROID)
	protected ScrollView getViewPager;
}
