package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.pageframework.device.MobileElementLocator;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.helper.SyncHelper;
import io.appium.java_client.MobileElement;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.stream.Collectors;

@Implementation(is = NewOrderView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = NewOrderView.class, on = Platform.MOBILE_IOS)
public class NewOrderView extends BaseComponent {

	/**
	 * Get the text vaalue of the heading
	 * 
	 * @return
	 */
	public String getHeadingTextValue() {
		return getHeadingText.getText();
	}

	/**
	 * Select category.
	 *
	 * @param category
	 *            the category
	 */
	public void selectCategory(String category) {
		logger.info("Select category: " + category);
		getCategoryItem(category).tapCenterOfElement();
		SyncHelper.sleep(1000);
	}

	/**
	 * Select product product details view.
	 *
	 * @param category
	 *            the category
	 * @return the product details view
	 */
	public ProductDetailsView selectProduct(String category) {
		logger.info("Select product: " + category);
		getCategoryItem(category).tapCenterOfElement();
		return ComponentFactory.create(ProductDetailsView.class);
	}

	/**
	 * Gets category items.
	 *
	 * @param category
	 *            the category
	 * @return the category items
	 */
	public List<String> getCategoryItems(String category) {
		logger.info("Select category: " + category);
		return getCategoryItemsElements(category).stream().filter(item -> item.isDisplayed())
				.map(item -> item.getCurrentText()).collect(Collectors.toList());
	}

	/**
	 * Select sub category.
	 *
	 * @param category
	 *            the category
	 * @param subcategory
	 *            the subcategory
	 */
	public void selectSubCategory(String category, String subcategory) {
		logger.info(String.format("Select subcategory: %s %s", category, subcategory));
		getSubCategoryItem(category, subcategory).tapCenterOfElement();
	}

	/**
	 * Search search results view.
	 *
	 * @param searchItem
	 *            the search item
	 * @return the search results view
	 */
	public SearchResultsView search(String searchItem) {
		logger.info("Searching for: " + searchItem);
		getSearchMagnifierButton.click();
		getSearchMenuTextBox.sendKeys(searchItem);
		return ComponentFactory.create(SearchResultsView.class);
	}

	@Locate(xpath = "//XCUIElementTypeNavigationBar[@name=\"New Order\"]", on = Platform.MOBILE_IOS)
	@Locate(xpath = "//android.widget.TextView[@text='New Order']", on = Platform.MOBILE_ANDROID)
	protected Text getHeadingText;

	@Locate(xpath = "//XCUIElementTypeStaticText[@name=\"%s\"]", on = Platform.MOBILE_IOS)
	@Locate(xpath = "//android.widget.TextView[@text=\"%s\"]", on = Platform.MOBILE_ANDROID)
	protected ContainerElement getCategoryItem;

	@Locate(xpath = "//XCUIElementTypeStaticText[@name=\"%s\"]/preceding-sibling::XCUIElementTypeStaticText", on = Platform.MOBILE_IOS)
	@Locate(xpath = "//android.widget.TextView[@text=\"%s\"]/../following-sibling::android.support.v7.widget.RecyclerView/android.support.v7.widget.LinearLayoutCompat/android.widget.TextView", on = Platform.MOBILE_ANDROID)
	protected List<MobileElement> getCategoryItemsElements;

	@Locate(xpath = "//XCUIElementTypeStaticText[@name=\"%s\"]/preceding-sibling::XCUIElementTypeStaticText[@name=\"%s\"]", on = Platform.MOBILE_IOS)
	@Locate(xpath = "//android.widget.TextView[@text=\"%s\"]/../following-sibling::android.support.v7.widget.RecyclerView/android.support.v7.widget.LinearLayoutCompat/android.widget.TextView[@text=\"%s\"]", on = Platform.MOBILE_ANDROID)
	protected ContainerElement getSubCategoryItem;

	@Locate(id = "Menu", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/searchContainer", on = Platform.MOBILE_ANDROID)
	protected Button getSearchMagnifierButton;

	@Locate(xpath = "//XCUIElementTypeSearchField[@name=\"Search Menu\"]", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/search_src_text", on = Platform.MOBILE_ANDROID)
	protected TextBox getSearchMenuTextBox;

}
