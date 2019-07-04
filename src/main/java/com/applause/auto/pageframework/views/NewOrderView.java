package com.applause.auto.pageframework.views;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.stream.Collectors;

import com.applause.auto.framework.pageframework.device.AbstractDeviceView;
import com.applause.auto.framework.pageframework.device.DeviceViewFactory;
import com.applause.auto.framework.pageframework.device.MobileElementLocator;
import com.applause.auto.framework.pageframework.device.factory.AndroidImplementation;
import com.applause.auto.framework.pageframework.device.factory.IosImplementation;
import com.applause.auto.framework.pageframework.devicecontrols.BaseDeviceControl;
import com.applause.auto.framework.pageframework.devicecontrols.Button;
import com.applause.auto.framework.pageframework.devicecontrols.Text;
import com.applause.auto.framework.pageframework.devicecontrols.TextBox;
import com.applause.auto.framework.pageframework.util.logger.LogController;

import io.appium.java_client.MobileElement;

@AndroidImplementation(NewOrderView.class)
@IosImplementation(NewOrderView.class)
public class NewOrderView extends AbstractDeviceView {

	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	@Override
	protected void waitUntilVisible() {
		syncHelper.waitForElementToAppear(getHeadingText(), 120000);
	}

	/**
	 * Get the text vaalue of the heading
	 * 
	 * @return
	 */
	public String getHeadingTextValue() {
		return getHeadingText().getStringValue();
	}

	/*
	 * Protected Getters
	 */

	@MobileElementLocator(android = "//android.widget.TextView[@text='New Order']", iOS = "//XCUIElementTypeNavigationBar[@name=\"New Order\"]")
	protected Text getHeadingText() {
		return new Text(getLocator(this, "getHeadingText"));
	}

	@MobileElementLocator(android = "//android.widget.TextView[@text=\"%s\"]", iOS = "//XCUIElementTypeStaticText[@name=\"%s\"]")
	protected BaseDeviceControl getCategoryItem(String category) {
		return new BaseDeviceControl(getLocator(this, "getCategoryItem", category));
	}

	@MobileElementLocator(android = "//android.widget.TextView[@text=\"%s\"]/../following-sibling::android.support.v7.widget.RecyclerView/android.support.v7.widget.LinearLayoutCompat/android.widget.TextView", iOS = "//XCUIElementTypeStaticText[@name=\"%s\"]/preceding-sibling::XCUIElementTypeStaticText")
	protected List<MobileElement> getCategoryItemsElements(String category) {
		return queryHelper.findElements(getLocator(this, "getCategoryItemsElements", category));
	}

	// @MobileElementLocator(android = "TODO", iOS =
	// "//XCUIElementTypeImage[@name=\"arrow-right\"]/preceding-sibling::XCUIElementTypeStaticText")
	// protected List<MobileElement> getSearchResultsElements() {
	// return queryHelper.findElements(getLocator(this, "getSearchResultsElements"));
	// }

	@MobileElementLocator(android = "//android.widget.TextView[@text=\"%s\"]/../following-sibling::android.support.v7.widget.RecyclerView/android.support.v7.widget.LinearLayoutCompat/android.widget.TextView[@text=\"%s\"]", iOS = "//XCUIElementTypeStaticText[@name=\"%s\"]/preceding-sibling::XCUIElementTypeStaticText[@name=\"%s\"]")
	protected BaseDeviceControl getSubCategoryItem(String category, String subCategory) {
		return new BaseDeviceControl(getLocator(this, "getSubCategoryItem", category, subCategory));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/searchContainer", iOS = "Menu")
	protected Button getSearchMagnifierButton() {
		return new Button(getLocator(this, "getSearchMagnifierButton"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/search_src_text", iOS = "//XCUIElementTypeSearchField[@name=\"Search Menu\"]")
	protected TextBox getSearchMenuEditField() {
		return new TextBox(getLocator(this, "getSearchMenuEditField"));
	}

	public void selectCategory(String category) {
		LOGGER.info("Select category: " + category);
		getCategoryItem(category).tapCenterOfElement();
		syncHelper.suspend(1000);
	}

	public ProductDetailsView selectProduct(String category) {
		LOGGER.info("Select product: " + category);
		getCategoryItem(category).tapCenterOfElement();
		return DeviceViewFactory.create(ProductDetailsView.class);
	}

	public List<String> getCategoryItems(String category) {
		LOGGER.info("Select category: " + category);
		return getCategoryItemsElements(category).stream().filter(item -> item.isDisplayed())
				.map(item -> item.getText()).collect(Collectors.toList());
	}

	public void selectSubCategory(String category, String subcategory) {
		LOGGER.info(String.format("Select subcategory: %s %s", category, subcategory));
		getSubCategoryItem(category, subcategory).tapCenterOfElement();
	}

	public SearchResultsView search(String searchItem) {
		LOGGER.info("Searching for: " + searchItem);
		getSearchMagnifierButton().pressButton();
		getSearchMenuEditField().enterText(searchItem);
		return DeviceViewFactory.create(SearchResultsView.class);
	}

}
