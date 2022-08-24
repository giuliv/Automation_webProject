package com.applause.auto.new_web.views;

import com.applause.auto.common.data.Constants.WebTestData;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.new_web.components.QuickViewComponent;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Image;
import com.applause.auto.pageobjectmodel.elements.Text;
import io.qameta.allure.Step;
import java.util.List;

@Implementation(is = SearchResultsPage.class, on = Platform.WEB)
@Implementation(is = SearchResultsPage.class, on = Platform.WEB_MOBILE_PHONE)
public class SearchResultsPage extends Base {

  @Locate(id = "searchOverlay", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Locate(css = ".collection__grid li a.pi__link", on = Platform.WEB)
  private List<Image> productsImageList;

  @Locate(css = "h1.collection-search__hero-heading", on = Platform.WEB)
  private Text searchResultTitle;

  @Locate(css = "h2.collection__empty-heading", on = Platform.WEB)
  private Text emptySearchResultMessage;

  @Locate(
      xpath =
          "//h3/a[contains(@href, \"/products/%s\")]/ancestor::li//div[@class='pi__quick-add']/button",
      on = Platform.WEB)
  private Button quickViewButton;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(mainContainer).present());
    WebHelper.clickButtonOverIFrame(newBannerIFrame, dismissBanner);
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
    quickViewButton.format(coffeeName).initialize();
    WebHelper.scrollToElement(quickViewButton);
    SdkHelper.getSyncHelper().sleep(1000); // Wait for action

    WebHelper.hoverByAction(quickViewButton);
    SdkHelper.getSyncHelper().sleep(1000); // Wait for action

    logger.info("Clicking QuickView button");
    quickViewButton.click();
    return SdkHelper.create(QuickViewComponent.class);
  }
}
