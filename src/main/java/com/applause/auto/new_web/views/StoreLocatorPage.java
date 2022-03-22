package com.applause.auto.new_web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.new_web.components.StockResultFilterComponent;
import com.applause.auto.new_web.components.StockResultItemComponent;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.factory.LazyList;
import io.qameta.allure.Step;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Implementation(is = StoreLocatorPage.class, on = Platform.WEB)
@Implementation(is = StoreLocatorPagePhone.class, on = Platform.WEB_MOBILE_PHONE)
public class StoreLocatorPage extends Base {

  @Locate(id = "storeLocator", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Locate(xpath = "//div[contains(@class, 'intro-header')]/h2", on = Platform.WEB)
  protected Text mainHeader;

  @Locate(
      xpath =
          "//form[contains(@class, 'stockist-search-form')]//input[contains(@class, 'stockist-search-field')]",
      on = Platform.WEB)
  protected TextBox searchByZipCodeField;

  @Locate(
      xpath = "//form[contains(@class, 'stockist-search-form')]//button[@aria-label='Search']",
      on = Platform.WEB)
  protected Button searchButton;

  @Locate(
      xpath = "//li[contains(@class, 'stockist-list-result') and contains(@class, 'is-visible')]",
      on = Platform.WEB)
  protected List<StockResultItemComponent> stockResultList;

  @Locate(xpath = "//div[@class='stockist-actions']/button", on = Platform.WEB)
  protected Button loadMoreButton;

  @Locate(xpath = "//div[contains(@class, 'loading')]", on = Platform.WEB)
  protected Button loadingAnimation;

  @Locate(xpath = "//button[contains(@class, 'filters-button')]", on = Platform.WEB)
  protected Button filterByButton;

  @Locate(xpath = "//h2[contains(@class, 'list-title')]", on = Platform.WEB)
  protected Text resultTitle;

  @Locate(xpath = "//button[contains(@class, 'js-store-locator-findloc')]", on = Platform.WEB)
  protected Button useMyCurrentLocationButton;

  @Locate(xpath = "//button[@aria-label='Zoom in']", on = Platform.WEB)
  protected Button zoomInButton;

  @Locate(
      xpath = "//div[contains(@class, 'is-visible')]/span[@class='stockist-result-message-text']",
      on = Platform.WEB)
  protected Text resultMessageText;

  @Locate(
      xpath = "//div[contains(@class, 'is-visible')]/a[contains(@class, 'no-result-link')]",
      on = Platform.WEB)
  protected Button shopOnlineButton;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(mainContainer).visible());
  }

  @Step("Type Zip Code and search")
  public StoreLocatorPage searchByZipCode(String zipCode) {
    logger.info("Typing [{}] zip code", zipCode);
    searchByZipCodeField.clearText();
    searchByZipCodeField.sendKeys(zipCode);

    logger.info("Clicking on Search");
    searchButton.click();
    WebHelper.waitForElementToDisappear(loadingAnimation, 10);
    return this;
  }

  public List<StockResultItemComponent> getStockResultList() {
    ((LazyList<?>) stockResultList).initialize();
    return stockResultList;
  }

  public List<String> getListOfStoreNames() {
    return getStockResultList().stream().map(item -> item.getName()).collect(Collectors.toList());
  }

  @Step("Load More Results")
  public StoreLocatorPage loadMoreResults() {
    logger.info("Clicking on 'Load More'");
    loadMoreButton.click();
    WebHelper.waitForElementToDisappear(loadingAnimation, 5);
    return SdkHelper.create(StoreLocatorPage.class);
  }

  @Step("Check if 'Search By Zip Code' Field is Displayed")
  public boolean isSearchByZipCodeFieldDisplayed() {
    return WebHelper.isDisplayed(searchByZipCodeField);
  }

  @Step("Check if 'Search' Button is Displayed")
  public boolean isSearchButtonDisplayed() {
    return WebHelper.isDisplayed(searchButton);
  }

  @Step("Check if 'Filter' Button is Displayed")
  public boolean isFilterButtonDisplayed() {
    return WebHelper.isDisplayed(filterByButton);
  }

  @Step("Get filter title")
  public String getFilterTitle() {
    String selectedFilter = WebHelper.cleanString(filterByButton.getText().replaceAll("\n", " "));
    logger.info("Selected filter is [{}]", selectedFilter);
    return selectedFilter;
  }

  @Step("Get Count Of Items From Result Title")
  public int getCountOfItemsFromResultTitle() {
    String selectedFilter = WebHelper.cleanString(resultTitle.getText());
    logger.info("Title is - [{}]", selectedFilter);
    return WebHelper.getNumberFromString(selectedFilter);
  }

  @Step("Check If All Stores are Fully Displayed")
  public boolean checkIfAllStoresFullyDisplayed() {
    return getStockResultList().stream().allMatch(item -> item.checkIfShopItemIsFullyDisplayed());
  }

  @Step("Open filters")
  public StockResultFilterComponent openFilters() {
    logger.info("Clicking on filter by amenities.");
    filterByButton.click();
    return SdkHelper.create(StockResultFilterComponent.class);
  }

  @Step("Click on use my current location ")
  public StoreLocatorPage clickUseMyCurrentLocation() {
    logger.info("Clicking on 'Use my current location' button");
    useMyCurrentLocationButton.click();
    return this;
  }

  @Step("Click on Zoom in button")
  public StoreLocatorPage clickOnZoomInButton(int countOfZoom) {
    IntStream.range(0, countOfZoom)
        .forEach(
            action -> {
              // Scrolling to the element places it behind the floating shopping cart in mobile,
              // so scrolling back to the top of the page fixes that.
              logger.info("Scrolling to top of page");
              WebHelper.scrollToPageTop();
              logger.info("Clicking on 'Zoom In' button");
              zoomInButton.click();
              WebHelper.waitForElementToDisappear(loadingAnimation, 5);
            });
    return this;
  }

  @Step("Get Result message text")
  public String getResultMessage() {
    String message = WebHelper.cleanString(resultMessageText.getText());
    logger.info("Message is - [{}]", message);
    return message;
  }

  @Step("Check If 'Shop Online' is Displayed")
  public boolean isShopOnlineDisplayed() {
    return WebHelper.isDisplayed(shopOnlineButton);
  }
}

class StoreLocatorPagePhone extends StoreLocatorPage {

  @Override
  @Step("Type Zip Code and search")
  public StoreLocatorPage searchByZipCode(String zipCode) {
    logger.info("Typing [{}] zip code", zipCode);
    searchByZipCodeField.clearText();
    searchByZipCodeField.sendKeys(zipCode);

    logger.info("Clicking on main header to hide suggestions");
    mainHeader.click();

    logger.info("Clicking on Search");
    searchButton.click();
    return this;
  }
}
