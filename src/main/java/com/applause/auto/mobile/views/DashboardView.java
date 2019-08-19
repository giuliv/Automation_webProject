package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.pageframework.device.MobileElementLocator;
import com.applause.auto.mobile.components.BottomNavigationMenuChunk;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.web.components.AccountMenuMobileChunk;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import java.lang.invoke.MethodHandles;
import org.openqa.selenium.Point;

@Implementation(is = DashboardView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = DashboardView.class, on = Platform.MOBILE_IOS)
public class DashboardView extends BaseComponent {

	/**
	 * Gets account profile menu.
	 *
	 * @return the account profile menu
	 */
	public AccountMenuMobileChunk getAccountProfileMenu() {
		logger.info("Open account profile menu");
		Point elemCoord = getMoreScreenButton.getMobileElement().getCenter();
		new TouchAction(getDriver()).tap(PointOption.point(elemCoord.getX(), elemCoord.getY())).perform();
		return DeviceComponentFactory.create(AccountMenuMobileChunk.class, "");
	}

	/**
	 * Gets bottom navigation menu.
	 *
	 * @return the bottom navigation menu
	 */
	public BottomNavigationMenuChunk getBottomNavigationMenu() {
		return DeviceComponentFactory.create(BottomNavigationMenuChunk.class, "");

	}

	@Locate(id = "Your Feed", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/yourFeedTextView", on = Platform.MOBILE_ANDROID)
	protected TextBox getSignature;

	@Locate(id = "button more", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/actionMore", on = Platform.MOBILE_ANDROID)
	protected Button getMoreScreenButton;

}
