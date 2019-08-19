package com.applause.auto.mobile.views;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.stream.Collectors;

import com.applause.auto.framework.pageframework.device.AbstractDeviceView;
import com.applause.auto.framework.pageframework.device.DeviceViewFactory;
import com.applause.auto.framework.pageframework.device.MobileElementLocator;
import com.applause.auto.framework.pageframework.device.factory.AndroidImplementation;
import com.applause.auto.framework.pageframework.device.factory.IosImplementation;
import com.applause.auto.framework.pageframework.devicecontrols.TextBox;
import com.applause.auto.framework.pageframework.util.logger.LogController;

import io.appium.java_client.MobileElement;

@AndroidImplementation(SearchResultsView.class)
@IosImplementation(SearchResultsView.class)
public class SearchResultsView extends AbstractDeviceView {

	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	@Override
	protected void waitUntilVisible() {
		syncHelper.waitForElementToAppear(getSearchMenuEditField(), 120000);
	}

	@MobileElementLocator(android = "//android.support.v7.widget.LinearLayoutCompat[@resource-id='com.wearehathway.peets.development:id/productContainer']/android.widget.TextView", iOS = "//XCUIElementTypeImage[@name=\"arrow-right\"]/preceding-sibling::XCUIElementTypeStaticText")
	protected List<MobileElement> getSearchResultsElements() {
		return queryHelper.findElements(getLocator(this, "getSearchResultsElements"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/search_src_text", iOS = "//XCUIElementTypeSearchField[@name=\"Search Menu\"]")
	protected TextBox getSearchMenuEditField() {
		return new TextBox(getLocator(this, "getSearchMenuEditField"));
	}

	public ProductDetailsView selectSearchResultByIndex(int index) {
		LOGGER.info("Select search result");
		getSearchResultsElements().get(index).click();
		return DeviceViewFactory.create(ProductDetailsView.class);
	}

	public List<String> getResults() {
		return getSearchResultsElements().stream().map(item -> item.getText()).collect(Collectors.toList());
	}
}
