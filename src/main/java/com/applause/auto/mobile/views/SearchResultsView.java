package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.factory.LazyList;
import java.util.List;
import java.util.stream.Collectors;

@Implementation(is = SearchResultsView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = SearchResultsView.class, on = Platform.MOBILE_IOS)
public class SearchResultsView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(
      iOSClassChain =
          "**/XCUIElementTypeCell[$name == 'product-default-small'$]/XCUIElementTypeStaticText",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//*[contains(@resource-id, 'id/productsRecyclerView')]/*[contains(@resource-id, 'id/productContainer')]",
      on = Platform.MOBILE_ANDROID)
  protected LazyList<Text> getSearchResultsElements;

  @Locate(xpath = "//XCUIElementTypeSearchField[@name=\"Search Menu\"]", on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/search_src_text\")",
      on = Platform.MOBILE_ANDROID)
  protected TextBox searchField;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeButton[`name == 'Cancel' and visible = 1`]",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/action_cancel\")",
      on = Platform.MOBILE_ANDROID)
  protected Button cancelButton;

  /* -------- Actions -------- */

  public ProductDetailsView selectSearchResultByIndex(int index) {
    logger.info("Select search result - [{}]", index);
    getSearchResultsElements.get(index - 1).click();
    return SdkHelper.create(ProductDetailsView.class);
  }

  public List<String> getResults() {
    return getSearchResultsElements
        .stream()
        .map(item -> item.getText())
        .collect(Collectors.toList());
  }

  public SearchResultsView search(String searchItem) {
    logger.info("Searching for: [{}]", searchItem);
    searchField.clearText();
    searchField.sendKeys(searchItem);
    MobileHelper.hideKeyboard();
    return SdkHelper.create(SearchResultsView.class);
  }

  public <T extends BaseComponent> T cancel(Class<T> clazz) {
    logger.info("Tapping on 'Cancel' button");
    cancelButton.click();
    return SdkHelper.create(clazz);
  }
}
