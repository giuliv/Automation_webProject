package com.applause.auto.web.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.factory.LazyList;
import com.applause.auto.web.helpers.WebHelper;
import com.applause.auto.web.views.SearchResultsPage;
import io.qameta.allure.Step;
import java.util.List;

@Implementation(is = SearchComponent.class, on = Platform.WEB)
@Implementation(is = SearchComponentMobile.class, on = Platform.WEB_MOBILE_PHONE)
public class SearchComponent extends BaseComponent {

  @Locate(id = "searchMenu", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Locate(id = "searchInput", on = Platform.WEB)
  protected TextBox searchBox;

  @Locate(css = ".search__label-text", on = Platform.WEB)
  protected Text searchLabel;

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
    if (SdkHelper.getEnvironmentHelper().isMobileIOS()) {
      SdkHelper.getSyncHelper().wait(Until.uiElement(mainContainer).present());
    } else {
      SdkHelper.getSyncHelper().wait(Until.uiElement(mainContainer).visible());
    }
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
    ((LazyList<?>) searchBoxItemComponents).initialize();
    return searchBoxItemComponents
        .stream()
        .allMatch(SearchBoxItemComponent::isAutocompleteResultDisplayedCorrectly);
  }

  @Step("Get search box item by index")
  public SearchBoxItemComponent getSearchBoxItemComponentByIndex(int index) {
    SdkHelper.getSyncHelper().wait(Until.uiElement(searchResultsContainer).present());
    return searchBoxItemComponents.get(index - 1);
  }
}

class SearchComponentMobile extends SearchComponent {

  @Override
  @Step("Enter product name into the search field")
  public void enterSearchTerm(String product) {
    logger.info("Searching for: " + product);
    searchBox.sendKeys(product);
    SdkHelper.getSyncHelper().sleep(1000);
    if (WebHelper.getDriverConfig().toLowerCase().contains("landscape")
        && SdkHelper.getEnvironmentHelper().isAndroid()) {
      logger.info("Android Landscape hide keyboard");
      SdkHelper.getDeviceControl().pressAndroidKeyBack();
      SdkHelper.getSyncHelper().sleep(500); // Wait for keyboard to be hidden
      //      WebHelper.hideKeyboard(); // This is only required in landscape mode
    }

    SdkHelper.getSyncHelper().wait(Until.uiElement(searchLabel).present());
    SdkHelper.getBrowserControl().jsClick(searchLabel);
    SdkHelper.getSyncHelper().sleep(2000); // Wait for action
  }
}
