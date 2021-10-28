package com.applause.auto.new_web.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.new_web.views.SearchResultsPage;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import io.qameta.allure.Step;
import java.util.List;

@Implementation(is = SearchComponent.class, on = Platform.WEB)
@Implementation(is = SearchComponent.class, on = Platform.WEB_MOBILE_PHONE)
public class SearchComponent extends BaseComponent {

  @Locate(id = "searchMenu", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Locate(id = "searchInput", on = Platform.WEB)
  private TextBox searchBox;

  @Locate(id = "searchMenuBtn", on = Platform.WEB)
  private Button searchButton;

  @Locate(id = "searchClose", on = Platform.WEB)
  private Button closeSearch;

  @Locate(css = "#autocompleteWrapper.is-visible", on = Platform.WEB)
  private ContainerElement searchResultsContainer;

  @Locate(css = "#autocompleteItems .search-item", on = Platform.WEB)
  private List<SearchBoxItemComponent> searchBoxItemComponents;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(mainContainer).visible());
  }

  /* -------- Actions -------- */

  @Step("Click Search")
  public SearchResultsPage search(String product) {
    enterSearchTerm(product);

    searchButton.click();
    return SdkHelper.create(SearchResultsPage.class);
  }

  @Step("Click close button")
  public void closeSearch() {
    logger.info("Clicking on the close button");
    closeSearch.click();
  }

  @Step("Enter product name into the search field")
  public void enterSearchTerm(String product) {
    logger.info("Searching for: " + product);
    searchBox.sendKeys(product);
    SdkHelper.getSyncHelper().sleep(3000); // Wait for action
  }

  @Step("Verify autocomplete result are displayed")
  public boolean resultIsDisplayed() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(searchResultsContainer).present());
    return !searchBoxItemComponents.isEmpty();
  }

  @Step("Verify result items are displayed with name, price, image")
  public boolean areAllSearchBoxItemsDisplayedCorrectly() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(searchResultsContainer).present());
    return searchBoxItemComponents.stream()
        .allMatch(SearchBoxItemComponent::isAutocompleteResultDisplayedCorrectly);
  }

  @Step("Get search box item by index")
  public SearchBoxItemComponent getSearchBoxItemComponentBuIndex(int index) {
    SdkHelper.getSyncHelper().wait(Until.uiElement(searchResultsContainer).present());
    return searchBoxItemComponents.get(index - 1);
  }
}
