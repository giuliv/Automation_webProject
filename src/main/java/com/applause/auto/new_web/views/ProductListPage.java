package com.applause.auto.new_web.views;

import com.applause.auto.common.data.Constants.SortType;
import com.applause.auto.common.data.enums.Filters;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.new_web.components.QuickViewComponent;
import com.applause.auto.new_web.components.plp.PlpItemComponent;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Image;
import com.applause.auto.pageobjectmodel.elements.Link;
import com.applause.auto.pageobjectmodel.elements.SelectList;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.LazyList;
import com.google.common.collect.Ordering;
import io.qameta.allure.Step;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Implementation(is = ProductListPage.class, on = Platform.WEB)
@Implementation(is = ProductListPageMobile.class, on = Platform.WEB_MOBILE_PHONE)
public class ProductListPage extends Base {

  @Locate(className = "collection__app", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Locate(
      css =
          ".collection__grid li a.pi__link > div:nth-child(2):not(.pi__badge), .collection__grid article",
      on = Platform.WEB)
  private List<Image> productsImageList;

  @Locate(
      css = ".collection__grid li a.pi__link[href*='reserve'] > div:nth-child(2):not(.pi__badge)",
      on = Platform.WEB)
  private List<Image> reserveProductsImageList;

  @Locate(
      css = ".collection__grid li a.pi__link > div.pi__badge i.icon--out-of-stock",
      on = Platform.WEB)
  private List<Image> outOfStockItemList;

  @Locate(css = "li[class*='collection-facets'] button", on = Platform.WEB)
  protected List<Button> filtersList;

  @Locate(css = "li[class*='collection-facet__opt'] label", on = Platform.WEB)
  protected List<Text> filterOptions;

  @Locate(css = "button.applied-filters__clear", on = Platform.WEB)
  private List<Button> removeFilterList;

  @Locate(css = "button.applied-filters__clear", on = Platform.WEB)
  private Button removeFilter;

  @Locate(css = "button.applied-filters__button", on = Platform.WEB)
  private Button clearAllFilters;

  @Locate(className = "collection-filters__results-count", on = Platform.WEB)
  private Text totalResults;

  @Locate(className = "collection-facets__mobile-btn", on = Platform.WEB)
  protected Button filtersSectionMobile;

  @Locate(className = "collection-facets__apply", on = Platform.WEB)
  protected Button applyFilter;

  @Locate(css = ".pi__quick-add button", on = Platform.WEB)
  private List<Button> quickViewButtonList;

  @Locate(
      xpath =
          "//div[@class=\"pi__essentials\"]//a[contains(text(),\"%s\")]/ancestor::div[@class=\"pi__desc\"]//button",
      on = Platform.WEB)
  private Button quickViewPaperFiltersButton;

  @Locate(css = ".pi__quick-add a", on = Platform.WEB)
  private List<Button> viewProductButtonList;

  @Locate(className = "collection-sort__select", on = Platform.WEB)
  protected Button sortingBox;

  @Locate(css = "#listOptions #ss_price_asc", on = Platform.WEB)
  protected Button priceLowToHigh;

  @Locate(css = "#listOptions #ss_price_desc", on = Platform.WEB)
  protected Button priceHighToLow;

  @Locate(css = "#listOptions #ss_days_since_published_asc", on = Platform.WEB)
  protected Button newestSortType;

  @Locate(css = "#listOptions #title_asc", on = Platform.WEB)
  protected Button titleAscSortType;

  @Locate(css = "#listOptions #title_desc", on = Platform.WEB)
  protected Button titleDescSortType;

  @Locate(className = "pi__price", on = Platform.WEB)
  private List<Text> priceList;

  @Locate(css = "h1.page-hero__heading", on = Platform.WEB)
  private Text getPageHeader;

  @Locate(css = ".collection__load-more > button", on = Platform.WEB)
  private Button loadMoreButton;

  @Locate(
      xpath =
          "//*[@class='product-list']/*[not(.//div[contains(@class, 'pi__badge')]) and not(@class='collection__load-more') and not(@class='collection-tout')]",
      on = Platform.WEB)
  private List<PlpItemComponent> productsList;

  @Locate(css = ".footer__newsletter a[href='/pages/email-signup']", on = Platform.WEB)
  private Button neverMissOfferSignUpButton;

  @Locate(css = ".footer__newsletter h4", on = Platform.WEB)
  private Button neverMissOfferTitleText;

  @Locate(css = ".footer__newsletter p.footer__newsletter-copy", on = Platform.WEB)
  private Button neverMissOfferContentText;

  @Locate(css = ".page-hero__description-text", on = Platform.WEB)
  private Text bannerContent;

  @Locate(className = "ankle-tout__items", on = Platform.WEB)
  private ContainerElement homeDeliveryContainer;

  @Locate(css = ".ankle-tout__items img", on = Platform.WEB)
  private Image homeDeliveryImage;

  @Locate(css = ".ankle-tout__items h2", on = Platform.WEB)
  private Text homeDeliveryTitle;

  @Locate(css = ".ankle-tout__items a", on = Platform.WEB)
  private Image getStartedHomeDeliveryButton;

  @Locate(className = "collection-filters__breadcrumbs", on = Platform.WEB)
  private Text fullBreadCrumbs;

  @Locate(css = ".collection-filters__breadcrumbs a", on = Platform.WEB)
  private Link linkBreadCrumbs;

  @Locate(css = ".pv-flavor-profile__subtitle", on = Platform.WEB)
  private Text flavorSubtitleText;

  @Locate(
      xpath = "//p[span[@class='pv-flavor-profile__attribute-label' and contains(text(),'Type:')]]",
      on = Platform.WEB)
  private Text flavorAttributesTypeText;

  @Locate(
      xpath =
          "//p[span[@class='pv-flavor-profile__attribute-label' and contains(text(),'Origin:')]]",
      on = Platform.WEB)
  private Text flavorAttributesOriginText;

  @Locate(
      xpath =
          "//p[span[@class='pv-flavor-profile__attribute-label' and contains(text(),'Process:')]]",
      on = Platform.WEB)
  private Text flavorAttributesProcessText;

  @Locate(
      xpath = "//p[contains(text(),'Roast')]/following-sibling::div[@class='progress-bar'][1]",
      on = Platform.WEB)
  private ContainerElement flavorRoastProgressBar;

  @Locate(
      xpath = "//p[contains(text(),'Brightness')]/following-sibling::div[@class='progress-bar'][1]",
      on = Platform.WEB)
  private ContainerElement flavorBrightnessProgressBar;

  @Locate(
      xpath = "//p[contains(text(),'Body')]/following-sibling::div[@class='progress-bar'][1]",
      on = Platform.WEB)
  private ContainerElement flavorBodyProgressBar;

  @Override
  public void afterInit() {
    if (WebHelper.isSafari()) {
      SdkHelper.getSyncHelper().wait(Until.uiElement(mainContainer).present());
    } else {
      SdkHelper.getSyncHelper().wait(Until.uiElement(mainContainer).visible());
    }

    //    WebHelper.clickButtonOverIFrame(newBannerIFrame, dismissBanner);
    logger.info("Products List Page URL: " + getDriver().getCurrentUrl());
  }

  /* -------- Actions -------- */

  @Step("Select product")
  public ProductDetailsPage clickOverProductByIndex(int index) {
    logger.info("Click over product with index: " + index);
    WebHelper.scrollToElement(productsImageList.get(index));
    SdkHelper.getSyncHelper().sleep(1000); // Wait for scroll

    SdkHelper.getSyncHelper().wait(Until.uiElement(productsImageList.get(index)).visible());
    WebHelper.scrollToElement(productsImageList.get(index));
    SdkHelper.getSyncHelper().sleep(1000); // Wait for scroll

    if (WebHelper.isSafari() && !WebHelper.isMobile()) {
      logger.info("Safari Desktop");
      WebHelper.jsClick(productsImageList.get(index).getWebElement());
    } else {
      productsImageList.get(index).click();
    }

    return SdkHelper.create(ProductDetailsPage.class);
  }

  @Step("Reserve product")
  public ProductDetailsPage clickOverReserveProductByIndex(int index) {
    logger.info("Click over reserve product with index: " + index);
    WebHelper.scrollToElement(reserveProductsImageList.get(index));
    SdkHelper.getSyncHelper().sleep(1000); // Wait for scroll

    SdkHelper.getSyncHelper().wait(Until.uiElement(reserveProductsImageList.get(index)).visible());
    WebHelper.scrollToElement(reserveProductsImageList.get(index));
    SdkHelper.getSyncHelper().sleep(1000); // Wait for scroll

    if (WebHelper.isSafari() && !WebHelper.isMobile()) {
      logger.info("Safari Desktop");
      WebHelper.jsClick(reserveProductsImageList.get(index).getWebElement());
    } else {
      reserveProductsImageList.get(index).click();
    }

    return SdkHelper.create(ProductDetailsPage.class);
  }

  @Step("Click on filter")
  public String applyFilterByIndex(int filter, int option) {
    logger.info("Clicking on filter: " + filter + " " + filtersList.get(filter).getText().trim());
    filtersList.get(filter).click();

    SdkHelper.getSyncHelper().wait(Until.uiElement(filterOptions.get(option)).visible());
    logger.info("Selecting option: " + option + " " + filterOptions.get(option).getText().trim());
    filterOptions.get(option).click();
    SdkHelper.getSyncHelper().sleep(2000); // Wait for action

    return filterOptions.get(option).getText().trim();
  }

  @Step("Click on filter")
  public ProductListPage applyFilterByName(Filters filter, String filterOption) {
    logger.info("Selecting filter option [{}] under filter [{}]", filterOption, filter.getName());
    filtersList.stream()
        .filter(f -> f.getText().trim().equals(filter.getName()))
        .findFirst()
        .get()
        .click();

    SdkHelper.getSyncHelper().wait(Until.uiElement(filterOptions.get(0)).visible());
    logger.info("Selecting option: [{}]", filterOption);
    filterOptions.stream()
        .filter(opt -> opt.getText().trim().equals(filterOption))
        .findFirst()
        .get()
        .click();
    SdkHelper.getSyncHelper().sleep(2000); // Wait for action
    return this;
  }

  @Step("Apply filter")
  public String getFilterAppliedPillByIndex(int index) {
    SdkHelper.getSyncHelper().wait(Until.uiElement(removeFilterList.get(index)).visible());
    logger.info("Filter applied: " + index + " " + removeFilterList.get(index).getText());
    WebHelper.scrollToElement(removeFilterList.get(index));

    return removeFilterList.get(index).getText();
  }

  @Step("Get filter pill")
  public boolean isFilterPillDisplayed() {
    return removeFilter.exists();
  }

  public int getTotalResults() {
    totalResults.initialize();
    int total = Integer.parseInt(totalResults.getText().replace("Results", "").trim());
    logger.info("Total Results: " + total);

    return total;
  }

  @Step("Clear all filters")
  public ProductListPage clearAllFilters() {
    logger.info("Click Clear All Filters");
    clearAllFilters.click();
    SdkHelper.getSyncHelper().sleep(2000); // Wait for action

    return SdkHelper.create(ProductListPage.class);
  }

  @Step("Remove filters")
  public ProductListPage removeFilters() {
    logger.info("Click filter pill");
    removeFilter.click();
    SdkHelper.getSyncHelper().sleep(2000); // Wait for action

    return SdkHelper.create(ProductListPage.class);
  }

  @Step("Click quick view")
  public QuickViewComponent clickOverFirstQuickViewButton() {
    WebHelper.scrollToElement(quickViewButtonList.get(0));
    SdkHelper.getSyncHelper().sleep(1000); // Wait for action

    WebHelper.hoverByAction(quickViewButtonList.get(0));
    SdkHelper.getSyncHelper().sleep(1000); // Wait for action

    logger.info("Clicking QuickView button");
    quickViewButtonList.get(0).click();
    return SdkHelper.create(QuickViewComponent.class);
  }

  @Step("Click quick view")
  public QuickViewComponent clickOverPaperFiltersProductQuickViewButton(String product) {
    WebHelper.scrollToElement(quickViewPaperFiltersButton.format(product));
    SdkHelper.getSyncHelper().sleep(1000); // Wait for action

    WebHelper.hoverByAction(quickViewPaperFiltersButton.format(product));
    SdkHelper.getSyncHelper().sleep(1000); // Wait for action

    logger.info("Clicking QuickView button");
    quickViewPaperFiltersButton.format(product).click();
    return SdkHelper.create(QuickViewComponent.class);
  }

  public void hoverOutOfStockItemByIndex(int index) {
    logger.info("Hover Out of Stock item in position: " + index);
    WebHelper.scrollToElement(outOfStockItemList.get(index));
    SdkHelper.getSyncHelper().sleep(1000); // Wait for scroll

    WebHelper.hoverByAction(outOfStockItemList.get(index));
  }

  public boolean isViewProductDisplayed(int index) {
    hoverOutOfStockItemByIndex(index);
    return viewProductButtonList.get(index).isDisplayed();
  }

  public ProductDetailsPage clickViewProductButton(int index) {
    hoverOutOfStockItemByIndex(index);

    logger.info("Click over View Product");
    if (WebHelper.isMobile()) {
      WebHelper.jsClick(viewProductButtonList.get(index).getWebElement());
    } else {
      viewProductButtonList.get(index).click();
    }

    return SdkHelper.create(ProductDetailsPage.class);
  }

  @Step("Select sorting by type")
  public ProductListPage selectSortingByType(SortType type) {
    logger.info("Click over sorting box");
    WebHelper.scrollToElement(sortingBox);
    sortingBox.click();

    logger.info("Select Sorting option: " + type);
    if (type.equals(SortType.PRICE_HIGH_TO_LOW)) {
      priceHighToLow.click();
    } else if (type.equals(SortType.PRICE_LOW_TO_HIGH)) {
      priceLowToHigh.click();
    } else if (type.equals(SortType.NAME_A_Z)) {
      titleAscSortType.click();
    } else if (type.equals(SortType.NAME_Z_A)) {
      titleDescSortType.click();
    } else if (type.equals(SortType.NEWEST)) {
      newestSortType.click();
    }
    SdkHelper.getSyncHelper().sleep(2000); // Wait for action
    return SdkHelper.create(ProductListPage.class);
  }

  @Step("Get sorting prices")
  public boolean validateSortingOptionResults(SortType sortType) {
    logger.info("Validating sorting: {}", sortType.getName());
    boolean isSortedProperly = true;
    switch (sortType) {
      case PRICE_HIGH_TO_LOW:
        isSortedProperly =
            Ordering.natural()
                .reverse()
                .isOrdered(getProductListPrices(SortType.PRICE_HIGH_TO_LOW));
        break;
      case PRICE_LOW_TO_HIGH:
        isSortedProperly =
            Ordering.natural().isOrdered(getProductListPrices(SortType.PRICE_LOW_TO_HIGH));
        break;
      case NAME_A_Z:
        isSortedProperly = Ordering.natural().isOrdered(getProductListNames());
        break;
      case NAME_Z_A:
        isSortedProperly = Ordering.natural().reverse().isOrdered(getProductListNames());
        break;
      case NEWEST:
        // TODO There are no ways to check if the list of the products is sorted by NEWEST option
        break;
    }

    if (!isSortedProperly) {
      List<String> sortedList = new ArrayList(getProductListNames());
      List<Boolean> sortedPricesList = new ArrayList(getProductListPrices(sortType));
      System.out.println("SORTED LIST: " + sortedPricesList);
      System.out.println("SORTED LIST: " + getProductListPrices(sortType));

      if (sortType.equals(SortType.NAME_A_Z)) {
        Collections.sort(sortedList);
      } else if (sortType.equals(SortType.NAME_Z_A)) {
        Collections.reverse(sortedList);
      } else if (sortType.equals(SortType.PRICE_LOW_TO_HIGH)) {
        Collections.sort(sortedPricesList);
      } else if (sortType.equals(SortType.PRICE_HIGH_TO_LOW)) {
        Collections.sort(sortedPricesList);
      }

      logger.info("Expected Names: {}; Actual Names: {};", sortedList, getProductListNames());
      logger.info(
          "Expected Prices: {}; Actual Prices: {};",
          sortedPricesList,
          getProductListPrices(sortType));
    }

    return isSortedProperly;
  }

  public List<String> getProductListNames() {
    return productsList().stream()
        .map(
            item -> {
              String name = item.getProductName();
              logger.info("Name: " + name);
              return name;
            })
        .collect(Collectors.toList());
  }

  @Step("Get product prices")
  public List<Double> getProductListPrices(SortType sortType) {
    return productsList().stream()
        .map(product -> product.getProductDoublePrice(sortType))
        .collect(Collectors.toList());
  }

  @Step("Get page header")
  public String getPageHeader() {
    return getPageHeader.getText().trim().toUpperCase();
  }

  @Step("Get Product On Position")
  public PlpItemComponent getProductOnPosition(int position) {
    return productsList.get(position - 1);
  }

  public List<PlpItemComponent> productsList() {
    ((LazyList<?>) productsList).initialize();
    return productsList;
  }

  @Step("Verify filters are displayed")
  public boolean areFilterDisplayed(Filters... filters) {
    boolean allFiltersAreDisplayed = true;
    for (Filters filter : filters) {
      logger.info("Checking filter [{}] is displayed", filter.getName());

      if (filtersList.stream().noneMatch(option -> option.getText().equals(filter.getName()))) {
        logger.error("Filter [{}] isn't displayed", filter.getName());
        allFiltersAreDisplayed = false;
      }
    }

    return allFiltersAreDisplayed;
  }

  @Step("Verify all filter options are displayed")
  public boolean areFilterOptionsDisplayed(Filters filter) {
    logger.info("Checking all filter options are displayed for [{}]", filter.getName());
    logger.info("Clicking on filter: [{}]", filter.getName());
    filtersList.stream()
        .filter(f -> f.getText().equals(filter.getName()))
        .findFirst()
        .get()
        .click();

    boolean allOptionsAreDisplayed = true;
    for (String option : filter.getOptions()) {
      logger.info("Checking filter option [{}] is displayed", option);

      if (filterOptions.stream().noneMatch(opt -> opt.getText().equals(option))) {
        logger.error(
            "Filter option [{}] isn't displayed under filter [{}]", option, filter.getName());
        allOptionsAreDisplayed = false;
      }
    }

    return allOptionsAreDisplayed;
  }

  @Step("Get number of the selected filter options")
  public int getNumberOfSelectedFilters() {
    try {
      ((LazyList<?>) removeFilterList).initialize();
      return removeFilterList.size();
    } catch (Exception e) {
      logger.info("There are no selected filters");
      return 0;
    }
  }

  @Step("Remove selected filter option by index")
  public ProductListPage removeSelectedFilterByIndex(int index) {
    logger.info("Removing selected filter option by index [{}]", index);
    removeFilterList.get(index).click();
    return this;
  }

  @Step("Get selected sorting type")
  public String getSelectedSortingType() {
    WebHelper.scrollToElement(sortingBox);
    String selectedSortingType = sortingBox.getText().split(":")[1].trim();
    logger.info("Selected sorting type: {}", selectedSortingType);
    return selectedSortingType;
  }

  @Step("Click Load more button")
  public ProductListPage loadMore() {
    logger.info("Clicking on the Load more button");
    int currentNumberOfItems = getTotalResults();
    WebHelper.scrollToPageBottom();
    WebHelper.scrollToElement(loadMoreButton);
    loadMoreButton.click();
    SdkHelper.getSyncHelper().waitUntil(totalResult -> getTotalResults() > currentNumberOfItems);
    return this;
  }

  @Step("Get out of scope item")
  public PlpItemComponent getOutOfScopePlpItemComponent() {
    logger.info("Getting out of scope item");
    loadMore();
    return productsList().stream()
        .filter(PlpItemComponent::isOutOfScope)
        .findFirst()
        .orElseThrow(() -> new RuntimeException("There are no items in out of scope"));
  }

  @Step("Verify Sign Up button is displayed in the NEVER MISS AN OFFER section")
  public boolean isNeverMissOfferSignUpButtonDisplayed() {
    logger.info("Checking Sign Up button is displayed in the NEVER MISS AN OFFER section");
    WebHelper.scrollToPageBottom();
    WebHelper.scrollToElement(neverMissOfferSignUpButton);
    return WebHelper.isDisplayed(neverMissOfferSignUpButton);
  }

  @Step("Verify title is displayed in the NEVER MISS AN OFFER section")
  public boolean isNeverMissOfferTitleDisplayed() {
    logger.info("Checking title is displayed in the NEVER MISS AN OFFER section");
    return WebHelper.isDisplayed(neverMissOfferTitleText);
  }

  @Step("Verify content is displayed in the NEVER MISS AN OFFER section")
  public boolean isNeverMissOfferContentDisplayed() {
    logger.info("Checking content is displayed in the NEVER MISS AN OFFER section");
    return WebHelper.isDisplayed(neverMissOfferContentText);
  }

  @Step("Click on the Sign Up button in the NEVER MISS AN OFFER section")
  public SignUpPage clickNeverMissOfferSignUpButton() {
    logger.info("Clicking on the Sign Up button in the NEVER MISS AN OFFER section");
    neverMissOfferSignUpButton.click();
    return SdkHelper.create(SignUpPage.class);
  }

  @Step("Verify banner content is displayed")
  public boolean isBannerContentDisplayed() {
    logger.info("Checking banner content is displayed");
    return WebHelper.isDisplayed(bannerContent);
  }

  public boolean isLoadButtonDisplayed() {
    logger.info("Checking load more content is displayed");
    return WebHelper.exists(loadMoreButton, 5);
  }

  public boolean isHomeDeliverySectionDisplayed() {
    logger.info("Checking Home Delivery content is displayed");
    WebHelper.scrollToElement(homeDeliveryContainer);
    SdkHelper.getSyncHelper().wait(Until.uiElement(homeDeliveryImage).visible());
    return WebHelper.isDisplayed(homeDeliveryImage);
  }

  public String getHomeDeliveryTitle() {
    String title = homeDeliveryTitle.getText().trim();

    logger.info("Home Delivery title: {}", title);
    return title;
  }

  public String getBreadCrumbs() {
    String breadCrumb = fullBreadCrumbs.getText().trim();

    logger.info("BreadCrumb: {}", breadCrumb);
    return breadCrumb;
  }

  public FreeHomeDeliveryPage clickGetStartedHomeDelivery() {
    logger.info("Click over Get Started button [Home Delivery]");
    WebHelper.scrollToElement(getStartedHomeDeliveryButton);
    getStartedHomeDeliveryButton.click();

    return SdkHelper.create(FreeHomeDeliveryPage.class);
  }

  public ProductListPage clickOverAllCoffeeFromBreadCrumbs() {
    logger.info("Click over All Coffee from BreadCrumb");
    linkBreadCrumbs.click();

    return this;
  }
}

class ProductListPageMobile extends ProductListPage {

  @Locate(css = ".collection-sort select", on = Platform.WEB)
  protected SelectList sortingDropdown;

  @Override
  public String applyFilterByIndex(int filter, int option) {
    SdkHelper.getSyncHelper().wait(Until.uiElement(filtersSectionMobile).visible());
    filtersSectionMobile.click();

    logger.info("Clicking on filter: " + filter + " " + filtersList.get(filter).getText().trim());
    filtersList.get(filter).click();

    SdkHelper.getSyncHelper().wait(Until.uiElement(filterOptions.get(option)).visible());
    String filterSelected = filterOptions.get(option).getText().trim();
    logger.info("Selecting option: " + option + " " + filterSelected);
    filterOptions.get(option).click();
    SdkHelper.getSyncHelper().sleep(1000); // Wait for action

    applyFilter.click();
    SdkHelper.getSyncHelper().sleep(2000); // Wait for action

    return filterSelected;
  }

  @Override
  @Step("Verify filters are displayed")
  public boolean areFilterDisplayed(Filters... filters) {
    SdkHelper.getSyncHelper().wait(Until.uiElement(filtersSectionMobile).visible());
    filtersSectionMobile.click();

    boolean allFiltersAreDisplayed = true;
    for (Filters filter : filters) {
      logger.info("Checking filter [{}] is displayed", filter.getName());

      if (filtersList.stream()
          .noneMatch(option -> option.getText().trim().equals(filter.getName()))) {
        logger.error("Filter [{}] isn't displayed", filter.getName());
        allFiltersAreDisplayed = false;
      }
    }

    return allFiltersAreDisplayed;
  }

  @Step("Verify all filter options are displayed")
  public boolean areFilterOptionsDisplayed(Filters filter) {
    logger.info("Checking all filter options are displayed for [{}]", filter.getName());

    SdkHelper.getSyncHelper().wait(Until.uiElement(filtersSectionMobile).visible());
    filtersSectionMobile.click();

    logger.info("Clicking on filter: [{}]", filter.getName());
    filtersList.stream()
        .filter(f -> f.getText().trim().equals(filter.getName()))
        .findFirst()
        .get()
        .click();

    boolean allOptionsAreDisplayed = true;
    for (String option : filter.getOptions()) {
      logger.info("Checking filter option [{}] is displayed", option);

      if (filterOptions.stream().noneMatch(opt -> opt.getText().trim().equals(option))) {
        logger.error(
            "Filter option [{}] isn't displayed under filter [{}]", option, filter.getName());
        allOptionsAreDisplayed = false;
      }
    }

    return allOptionsAreDisplayed;
  }

  @Step("Click on filter")
  public ProductListPage applyFilterByName(Filters filter, String filterOption) {
    logger.info("Selecting filter option [{}] under filter [{}]", filterOption, filter.getName());

    SdkHelper.getSyncHelper().wait(Until.uiElement(filtersSectionMobile).visible());
    filtersSectionMobile.click();

    filtersList.stream()
        .filter(f -> f.getText().trim().equals(filter.getName()))
        .findFirst()
        .get()
        .click();

    SdkHelper.getSyncHelper().wait(Until.uiElement(filterOptions.get(0)).visible());
    logger.info("Selecting option: [{}]", filterOption);
    filterOptions.stream()
        .filter(opt -> opt.getText().trim().equals(filterOption))
        .findFirst()
        .get()
        .click();
    SdkHelper.getSyncHelper().sleep(2000); // Wait for action
    return this;
  }

  @Override
  @Step("Select sorting by type")
  public ProductListPage selectSortingByType(SortType type) {
    logger.info("Click over sorting box");
    WebHelper.scrollToElement(sortingDropdown);
    sortingDropdown.select(type.getMobileName());
    SdkHelper.getSyncHelper().sleep(2000); // Wait for action
    return SdkHelper.create(ProductListPage.class);
  }

  @Override
  @Step("Get selected sorting type")
  public String getSelectedSortingType() {
    WebHelper.scrollToElement(sortingDropdown);
    String selectedSortingType = sortingDropdown.getSelectedOption().getText();
    logger.info("Selected sorting type: {}", selectedSortingType);
    return selectedSortingType;
  }
}
