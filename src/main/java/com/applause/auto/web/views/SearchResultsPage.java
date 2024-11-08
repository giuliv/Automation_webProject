package com.applause.auto.web.views;

import com.applause.auto.common.data.Constants.WebTestData;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Image;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.LazyList;
import com.applause.auto.web.components.QuickViewComponent;
import com.applause.auto.web.components.plp.PlpItemComponent;
import com.applause.auto.web.helpers.WebHelper;
import io.qameta.allure.Step;
import java.util.List;

@Implementation(is = SearchResultsPage.class, on = Platform.WEB)
@Implementation(is = SearchResultsPageMobile.class, on = Platform.WEB_MOBILE_PHONE)
public class SearchResultsPage extends Base {

  @Locate(id = "searchOverlay", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Locate(
      css = ".collection__grid li a.pi__link, .collection__grid article a.pi__link",
      on = Platform.WEB)
  private List<Image> productsImageList;

  @Locate(css = "h1.collection-search__hero-heading", on = Platform.WEB)
  private Text searchResultTitle;

  @Locate(css = "h2.collection__empty-heading", on = Platform.WEB)
  private Text emptySearchResultMessage;

  @Locate(xpath = ".//a[normalize-space()='View Product']", on = Platform.WEB)
  protected Button viewProductButton;

  @Locate(
      xpath =
          "(//h3/a[contains(@href,\"/products/%s\")]/ancestor::li//div[@class='pi__quick-add']/button | //h3/a[contains(@href,\"/products/%s\")]/ancestor::article//div[@class='pi__quick-add']/button)",
      on = Platform.WEB)
  protected Button quickViewButton;

  @Locate(
      xpath =
          "(//h3/a[@href=\"/products/%s\"]/ancestor::li//div[@class='pi__quick-add']/button | //h3/a[@href=\"/products/%s\"]/ancestor::article//div[@class='pi__quick-add']/button)",
      on = Platform.WEB)
  protected Button quickViewButtonByName;

  @Locate(css = ".pi__quick-add button", on = Platform.WEB)
  public List<Button> quickViewButtonList;

  @Locate(
      xpath =
          "//*[@class='product-list']/*[not(.//div[contains(@class, 'pi__badge')]) and not(@class='collection__load-more') and not(contains(@class,'collection-tout'))]",
      on = Platform.WEB)
  private List<PlpItemComponent> productsList;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(mainContainer).present());
    //    WebHelper.clickButtonOverIFrame(newBannerIFrame, dismissBanner);
    logger.info("Search Results List Page URL: " + getDriver().getCurrentUrl());
  }

  @Step("Click on product")
  public ProductDetailsPage clickOverProductByIndex(int index) {
    logger.info("Click over product with index: " + index);
    WebHelper.scrollToElement(productsImageList.get(index));
    SdkHelper.getSyncHelper().sleep(1000); // Wait for scroll

    SdkHelper.getSyncHelper().wait(Until.uiElement(productsImageList.get(index)).visible());
    WebHelper.scrollToElement(productsImageList.get(index));

    productsImageList.get(index).click();
    return SdkHelper.create(ProductDetailsPage.class);
  }

  @Step("Verify result list is displayed")
  public boolean isResultListDisplayed() {
    return !productsImageList.isEmpty();
  }

  @Step("Verify search result title is displayed")
  public boolean isSearchResultTitleDisplayed() {
    return searchResultTitle.isDisplayed();
  }

  @Step("Verify empty search result message is displayed")
  public boolean isEmptySearchResultMessageDisplayed(String searchTerm) {
    if (!emptySearchResultMessage.isDisplayed()) {
      logger.info("The empty search result message isn't displayed");
      return false;
    }

    String actualMessage = emptySearchResultMessage.getText().trim();
    String expectedMessage =
        String.format(WebTestData.EMPTY_SEARCH_RESULT_MESSAGE_TEMPLATE, searchTerm.toUpperCase());
    if (!actualMessage.equals(expectedMessage)) {
      logger.info(
          "Wrong message is displayed. Expected - [{}], Actual - [{}],",
          expectedMessage,
          actualMessage);
      return false;
    }

    return true;
  }

  @Step("Click quick view")
  public QuickViewComponent clickOverQuickViewByProduct(String coffeeName) {
    quickViewButton.format(coffeeName, coffeeName).initialize();
    WebHelper.scrollToElement(quickViewButton);
    SdkHelper.getSyncHelper().sleep(1000); // Wait for action

    WebHelper.hoverByAction(quickViewButton);
    SdkHelper.getSyncHelper().sleep(1000); // Wait for action

    logger.info("Clicking QuickView button");
    quickViewButton.click();

    return SdkHelper.create(QuickViewComponent.class);
  }

  @Step("Click quick view")
  public QuickViewComponent clickOverQuickViewByEqualsProduct(String coffeeName) {
    quickViewButtonByName.format(coffeeName, coffeeName).initialize();
    WebHelper.scrollToElement(quickViewButtonByName);
    SdkHelper.getSyncHelper().sleep(1000); // Wait for action

    WebHelper.hoverByAction(quickViewButtonByName);
    SdkHelper.getSyncHelper().sleep(1000); // Wait for action

    logger.info("Clicking QuickView button");
    quickViewButtonByName.click();
    //    SdkHelper.getSyncHelper().wait(Until.uiElement(quickViewButtonByName).notVisible());

    return SdkHelper.create(QuickViewComponent.class);
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

  @Step("Click on the View product button")
  public ProductDetailsPage clickViewProduct() {
    logger.info("Clicking on the 'View product' button");
    WebHelper.hoverByAction(viewProductButton);
    SdkHelper.getSyncHelper().sleep(500);
    viewProductButton.click();
    return SdkHelper.create(ProductDetailsPage.class);
  }

  public List<PlpItemComponent> productsList() {
    ((LazyList<?>) productsList).initialize();
    return productsList;
  }
}

class SearchResultsPageMobile extends SearchResultsPage {

  @Override
  public QuickViewComponent clickOverQuickViewByProduct(String coffeeName) {
    quickViewButton.format(coffeeName, coffeeName).initialize();
    WebHelper.scrollToElement(quickViewButton);
    SdkHelper.getSyncHelper().sleep(1000); // Wait for action

    logger.info("Clicking QuickView button");
    quickViewButton.click();
    SdkHelper.getSyncHelper().sleep(2000); // Need to wait until UI updates

    return SdkHelper.create(QuickViewComponent.class);
  }

  @Step("Click quick view")
  public QuickViewComponent clickOverQuickViewByEqualsProduct(String coffeeName) {
    quickViewButtonByName.format(coffeeName, coffeeName).initialize();
    WebHelper.scrollToElement(quickViewButtonByName);
    SdkHelper.getSyncHelper().sleep(1000); // Wait for action

    logger.info("Clicking QuickView button");
    quickViewButtonByName.click();
    return SdkHelper.create(QuickViewComponent.class);
  }

  @Override
  @Step("Click on the View product button")
  public ProductDetailsPage clickViewProduct() {
    logger.info("Clicking on the 'View product' button");
    viewProductButton.click();
    return SdkHelper.create(ProductDetailsPage.class);
  }

  @Override
  @Step("Click quick view")
  public QuickViewComponent clickOverFirstQuickViewButton() {
    WebHelper.scrollToElement(quickViewButtonList.get(0));
    SdkHelper.getSyncHelper().sleep(1000); // Wait for action

    logger.info("Clicking QuickView button");
    quickViewButtonList.get(0).click();
    return SdkHelper.create(QuickViewComponent.class);
  }
}
