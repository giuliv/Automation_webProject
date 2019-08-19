package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.pageframework.device.MobileElementLocator;
import com.applause.auto.mobile.components.AllowLocationServicesPopupChunk;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.helper.QueryHelper;
import com.applause.auto.util.helper.SyncHelper;
import com.google.common.collect.ImmutableMap;
import java.lang.invoke.MethodHandles;

@Implementation(is = AndroidSelectCoffeeBarView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = SelectCoffeeBarView.class, on = Platform.MOBILE_IOS)
public class SelectCoffeeBarView extends BaseComponent {

	/**
	 * Gets enable location description.
	 *
	 * @return the enable location description
	 */
	public String getEnableLocationDescription() {
		return getEnableLocationDescriptionText.getText();
	}

	/**
	 * Is enable location button displayed boolean.
	 *
	 * @return the boolean
	 */
	public boolean isEnableLocationButtonDisplayed() {
		return SyncHelper.isElementDisplayed(getLocator(this, "getEnableLocationButton"));
	}

	/**
	 * Enable location allow location services popup chunk.
	 *
	 * @return the allow location services popup chunk
	 */
	public AllowLocationServicesPopupChunk enableLocation() {
		logger.info("Tap enable location button");
		getEnableLocationButton.click();
		return DeviceComponentFactory.create(AllowLocationServicesPopupChunk.class, "");
	}

	/**
	 * Search.
	 *
	 * @param searchTxt
	 *            the search txt
	 */
	public void search(String searchTxt) {
		logger.info("Searching for store: " + searchTxt);
		getSearchButton.click();
		getSearchTextBox.sendKeys(searchTxt + "\n");
	}

	/**
	 * Open coffeebar from search results new order view.
	 *
	 * @param index
	 *            the index
	 * @return the new order view
	 */
	public NewOrderView openCoffeebarFromSearchResults(int index) {
		logger.info("Tap on Search result");
		getSearchResultText(index).tapCenterOfElement();
		return ComponentFactory.create(NewOrderView.class);
	}

	public boolean isStoresDisplayed() {
		boolean result = QueryHelper.doesElementExist(getLocator(this, "getSearchResultText", 1));
		return result;
	}

	@Locate(xpath = "//XCUIElementTypeNavigationBar[@name='Select Coffeebar']", on = Platform.MOBILE_IOS)
	@Locate(xpath = "//android.widget.TextView[@text='Select Coffeebar']", on = Platform.MOBILE_ANDROID)
	protected ContainerElement getSignature;

	@Locate(xpath = "//XCUIElementTypeButton[@name=\"Enable Location\"]/preceding-sibling::*", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/noStoreMessage", on = Platform.MOBILE_ANDROID)
	protected Text getEnableLocationDescriptionText;

	@Locate(id = "Enable Location", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/enableLocationServiceButton", on = Platform.MOBILE_ANDROID)
	protected Button getEnableLocationButton;

	@Locate(id = "search magnifier", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/action_search", on = Platform.MOBILE_ANDROID)
	protected Button getSearchButton;

	@Locate(id = "Search for coffeebar", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/searchField", on = Platform.MOBILE_ANDROID)
	protected TextBox getSearchTextBox;

	@Locate(xpath = "//XCUIElementTypeTable/XCUIElementTypeCell[%s]", on = Platform.MOBILE_IOS)
	@Locate(xpath = "(//android.widget.RelativeLayout[@resource-id='com.wearehathway.peets.development:id/storeDetail'])[%s]", on = Platform.MOBILE_ANDROID)
	protected TextBox getSearchResultText;

}

class AndroidSelectCoffeeBarView extends SelectCoffeeBarView {
	@Override
	public void waitUntilVisible() {
		// Workaround for Automator hang
		try {
			DeviceComponentFactory.create(AllowLocationServicesPopupChunk.class, "").notNow();
		} catch (AssertionError ase) {
			logger.warn("No popup found");
		}
		SyncHelper.waitUntilElementPresent(getSignature);
	}

	public void search(String searchTxt) {
		logger.info("Searching for store: " + searchTxt);
		getSearchButton.click();
		getSearchTextBox.sendKeys(searchTxt);
		getDriver().executeScript("mobile: performEditorAction", ImmutableMap.of("action", "search"));
	}
}