package com.applause.auto.new_web.views;

import com.applause.auto.common.data.Constants;
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
import com.applause.auto.pageobjectmodel.elements.Text;
import com.google.common.collect.Ordering;
import io.qameta.allure.Step;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.WebDriverException;
import org.testng.Assert;

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

  @Locate(className = "collection-sort__select", on = Platform.WEB)
  protected Button sortingBox;

  @Locate(css = "#listOptions #ss_price_asc", on = Platform.WEB)
  @Locate(css = "option[value*='Low to High']", on = Platform.WEB_MOBILE_PHONE)
  protected Button lowToHigh;

  @Locate(css = "#listOptions #ss_price_desc", on = Platform.WEB)
  @Locate(css = "option[value*='High to Low']", on = Platform.WEB_MOBILE_PHONE)
  protected Button highToLow;

  @Locate(className = "pi__price", on = Platform.WEB)
  private List<Text> priceList;

  @Locate(css = "h1.page-hero__heading", on = Platform.WEB)
  private Text getPageHeader;

  @Locate(
      xpath = "//ul[@class='product-list']/li[not(.//div[contains(@class, 'pi__badge')])]",
      on = Platform.WEB)
  private List<PlpItemComponent> productsList;

  @Override
  public void afterInit() {
    if (WebHelper.isSafari()) {
      SdkHelper.getSyncHelper().wait(Until.uiElement(mainContainer).present());
    } else {
      SdkHelper.getSyncHelper().wait(Until.uiElement(mainContainer).visible());
    }

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
      // Todo:Try/Catch added to prevent chromedriver issue[Temp fix]
      try {
        productsImageList.get(index).click();
      } catch (WebDriverException e) {
        logger.info("Frame detached issue seen");
      }
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

  @Step("Select sorting by type")
  public ProductListPage selectSortingByType(Constants.SortType type) {
    logger.info("Click over sorting box");
    WebHelper.scrollToElement(sortingBox);
    sortingBox.click();

    logger.info("Select Sorting option: " + type);
    if (type.equals(Constants.SortType.HIGH_TO_LOW)) {
      highToLow.click();
    } else if (type.equals(Constants.SortType.LOW_TO_HIGH)) {
      lowToHigh.click();
    }
    SdkHelper.getSyncHelper().sleep(2000); // Wait for action
    return SdkHelper.create(ProductListPage.class);
  }

  @Step("Get sorting prices")
  public boolean validateSortingPrices(Constants.SortType sortType) {
    logger.info("Validating sorting...");
    boolean sortedPrices;
    if (sortType.equals(Constants.SortType.HIGH_TO_LOW)) {
      sortedPrices = Ordering.natural().reverse().isOrdered(getProductListPrices());
    } else if (sortType.equals(Constants.SortType.LOW_TO_HIGH)) {
      sortedPrices = Ordering.natural().isOrdered(getProductListPrices());
    } else {
      Assert.fail("Sorting Option is not recognized");
      return false;
    }
    return sortedPrices;
  }

  @Step("Get product prices")
  public List<Double> getProductListPrices() {
    List<Double> prices = new ArrayList<>();
    logger.info("Getting product prices");

    String priceClean;
    for (Text price : priceList) {
      priceClean = price.getText().split(" ")[0].replace(",", ".").replace("$", "").trim();

      logger.info("Prices " + priceClean);
      prices.add(Double.parseDouble(priceClean));
    }

    return prices;
  }

  @Step("Get page header")
  public String getPageHeader() {
    return getPageHeader.getText().trim();
  }

  @Step("Get Product On Position")
  public PlpItemComponent getProductOnPosition(int position) {
    return productsList.get(position - 1);
  }
}

class ProductListPageMobile extends ProductListPage {
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
}
