package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.pageframework.device.MobileElementLocator;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import io.appium.java_client.MobileElement;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.stream.Collectors;

@Implementation(is = SearchResultsView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = SearchResultsView.class, on = Platform.MOBILE_IOS)
public class SearchResultsView extends BaseComponent {

	@Locate(xpath = "//XCUIElementTypeImage[@name=\"arrow-right\"]/preceding-sibling::XCUIElementTypeStaticText", on = Platform.MOBILE_IOS)
	@Locate(xpath = "//android.support.v7.widget.LinearLayoutCompat[@resource-id='com.wearehathway.peets.development:id/productContainer']/android.widget.TextView", on = Platform.MOBILE_ANDROID)
	protected List<MobileElement> getSearchResultsElements;

	@Locate(xpath = "//XCUIElementTypeSearchField[@name=\"Search Menu\"]", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/search_src_text", on = Platform.MOBILE_ANDROID)
	protected TextBox getSearchMenuTextBox;

	public ProductDetailsView selectSearchResultByIndex(int index) {
		logger.info("Select search result");
		getSearchResultsElements.get(index).click();
		return ComponentFactory.create(ProductDetailsView.class);
	}

	public List<String> getResults() {
		return getSearchResultsElements.stream().map(item -> item.getCurrentText()).collect(Collectors.toList());
	}
}
