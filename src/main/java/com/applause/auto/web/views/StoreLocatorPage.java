package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.factory.LazyList;
import com.applause.auto.web.components.StockResultFilterComponent;
import com.applause.auto.web.components.StockResultItemComponent;
import com.applause.auto.web.helpers.WebHelper;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.remote.SupportsContextSwitching;
import io.qameta.allure.Step;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Implementation(is = StoreLocatorPage.class, on = Platform.WEB)
@Implementation(is = StoreLocatorAndroidPage.class, on = Platform.WEB_MOBILE_PHONE)
@Implementation(is = StoreLocatorIOSPage.class, on = Platform.WEB_IOS_PHONE)
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

  @Locate(xpath = "//h2[@class='store-locator__intro-title']", on = Platform.WEB)
  protected Text findPeetsNearYouTitle;

  @Locate(xpath = "//p[@class='store-locator__intro-subtitle']", on = Platform.WEB)
  protected Text findPeetsNearYouDescription;

  @Locate(xpath = "//button[contains(@class,'store-locator__filters-button')]", on = Platform.WEB)
  protected Button amenitiesFilterButton;

  @Locate(xpath = "//label[input[contains(@id,'amenitiesCheckbox')]]", on = Platform.WEB)
  protected List<Button> amenitiesOptions;

  @Locate(css = ".dwmodal-box .dwmodal-close-button", on = Platform.WEB)
  protected Button closePopUp;

  @Locate(css = "#usi_display #usi_close", on = Platform.WEB)
  private Button closeBannerButton;

  @Override
  public void afterInit() {
    if (WebHelper.exists(closePopUp, 5)) {
      if (closePopUp.isDisplayed()) {
        closePopUp.click();
      }
    }

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
    if (WebHelper.isMobile()) {
      WebHelper.jsClick(filterByButton.getWebElement());
    } else {
      filterByButton.click();
    }

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

  @Step("Verify 'FIND A PEET’S NEAR YOU' is displayed")
  public boolean isPageTitleDisplayed() {
    return WebHelper.isDisplayed(findPeetsNearYouTitle)
        && findPeetsNearYouTitle.getText().trim().equalsIgnoreCase("FIND A PEET’S NEAR YOU");
  }

  @Step("Verify 'FIND A PEET’S NEAR YOU' description is displayed")
  public boolean isPageDescriptionDisplayed() {
    return WebHelper.isDisplayed(findPeetsNearYouDescription)
        && findPeetsNearYouDescription
            .getText()
            .trim()
            .equalsIgnoreCase(
                "The freshest coffee is just a click away. Find a location, order ahead, and earn points.");
  }

  @Step("Verify FILTER BY AMENITIES is displayed")
  public boolean isAmenitiesFilterDisplayed() {
    return WebHelper.isDisplayed(amenitiesFilterButton);
  }

  @Step("Get Amenities options ")
  public List<String> getAmenitiesOptions() {
    amenitiesFilterButton.click();
    SdkHelper.getSyncHelper().sleep(1000); // wait for section with options opens
    List<String> amenities =
        amenitiesOptions.stream().map(Button::getText).collect(Collectors.toList());
    logger.info("Amenities list: {}", amenities);
    amenitiesFilterButton.click();
    return amenities;
  }

  @Step("Select amenities option")
  public StoreLocatorPage selectAmenities(String option) {
    logger.info("Clicking on the Amenities dropdown button");
    amenitiesFilterButton.click();
    SdkHelper.getSyncHelper().sleep(1000); // wait for section with options opens

    logger.info("Selecting amenities option: {}", option);
    amenitiesOptions
        .stream()
        .filter(item -> item.getText().equalsIgnoreCase(option))
        .findFirst()
        .get()
        .click();

    amenitiesFilterButton.click();
    return this;
  }

  public <T extends BaseComponent> T closeBannerButton(Class<T> clazz) {
    if (closeBannerButton.isDisplayed()) {
      try {
        logger.info("Closing the banner");
        closeBannerButton.click();
        return WebHelper.navigateBack(clazz);
      } catch (Exception e) {
        logger.info("Banner is not displayed");
        return SdkHelper.create(clazz);
      }
    } else {
      return SdkHelper.create(clazz);
    }
  }
}

class StoreLocatorAndroidPage extends StoreLocatorPage {

  @Locate(xpath = "//*[@text=\"Allow\"]")
  protected Button allowLocationButton;

  @Locate(xpath = "//android.widget.Button[@text='Allow only while using the app']")
  protected Button allowLocationToBrowser;

  @Override
  @Step("Type Zip Code and search")
  public StoreLocatorPage searchByZipCode(String zipCode) {
    logger.info("Typing [{}] zip code", zipCode);
    searchByZipCodeField.clearText();
    searchByZipCodeField.sendKeys(zipCode);

    logger.info("Clicking on main header to hide suggestions");
    if (WebHelper.getDriverConfig().toLowerCase().contains("landscape")
        && SdkHelper.getEnvironmentHelper().isAndroid()) {
      logger.info("Android Landscape hide keyboard");
      SdkHelper.getDeviceControl().pressAndroidKey(AndroidKey.ESCAPE);
      SdkHelper.getSyncHelper().sleep(500); // Wait for keyboard to be hidden

      logger.info("Clicking on Search");
      SdkHelper.getBrowserControl().jsClick(searchButton);
    } else {
      mainHeader.click();

      logger.info("Clicking on Search");
      searchButton.click();
    }

    return this;
  }

  @Override
  public StoreLocatorPage clickUseMyCurrentLocation() {
    logger.info("Clicking on 'Use my current location' button");
    useMyCurrentLocationButton.click();

    String oldContext = SdkHelper.getDeviceControl().getContext();
    logger.info("Switching to native context");
    ((SupportsContextSwitching) SdkHelper.getDriver()).context("NATIVE_APP");
    SdkHelper.getSyncHelper().sleep(1000); // wait for switch context to native

    if (MobileHelper.isElementDisplayed(allowLocationButton, 10)) {
      allowLocationButton.click();
    } else {
      logger.info("No location popup overlay didn't found");
    }

    if (MobileHelper.isElementDisplayed(allowLocationToBrowser, 20)) {
      allowLocationToBrowser.click();
    } else {
      logger.info("No location popup 2 overlay didn't  found");
    }

    SdkHelper.getDeviceControl().changeContext(oldContext);
    return this;
  }
}

class StoreLocatorIOSPage extends StoreLocatorPage {
  @Locate(xpath = "//XCUIElementTypeButton[@name=\"Allow Once\"]", on = Platform.WEB)
  protected Button allowOnceButton;

  @Locate(xpath = "//XCUIElementTypeButton[@name=\"Allow\"]", on = Platform.WEB)
  protected Button allowButton;

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

  @Override
  public StoreLocatorPage clickUseMyCurrentLocation() {
    logger.info("Clicking on 'Use my current location' button");
    useMyCurrentLocationButton.click();
    WebHelper.allowLocationOnceIOS(allowOnceButton, allowButton);
    return this;
  }
}
