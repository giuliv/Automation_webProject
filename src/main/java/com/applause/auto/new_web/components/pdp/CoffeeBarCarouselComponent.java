package com.applause.auto.new_web.components.pdp;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.new_web.views.SeasonalPage;
import com.applause.auto.new_web.views.StoreLocatorPage;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Image;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.LazyList;
import io.qameta.allure.Step;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Implementation(is = CoffeeBarCarouselComponent.class, on = Platform.WEB)
@Implementation(is = CoffeeBarCarouselComponentMobile.class, on = Platform.WEB_MOBILE_PHONE)
public class CoffeeBarCarouselComponent extends BaseComponent {

  @Locate(
      css = "section#coffeebar button.flickity-button.flickity-prev-next-button.next",
      on = Platform.WEB)
  protected Button nextButton;

  @Locate(
      css = "section#coffeebar button.flickity-button.flickity-prev-next-button.previous",
      on = Platform.WEB)
  protected Button previousButton;

  @Locate(css = "#coffeebarCarousel article", on = Platform.WEB)
  protected List<CoffeeBarItemComponent> coffeeBarItemComponents;

  @Locate(
      css =
          "section#coffeebar div.product-carousel__actions a.btn.btn--primary.product-carousel__btn",
      on = Platform.WEB)
  protected Button seasonalButton;

  @Locate(
      css = "section#coffeebar div.product-carousel__actions a.product-carousel__secondary-link",
      on = Platform.WEB)
  protected Button findCoffeeBar;

  @Locate(css = "a.product-carousel__secondary-link", on = Platform.WEB)
  protected Button findCoffeeBarButton;

  @Locate(css = "a.btn.btn--primary.product-carousel__btn", on = Platform.WEB)
  protected Button allSeasonalBeveragesButton;

  @Locate(css = "h3.product-carousel__heading", on = Platform.WEB)
  protected Text headerText;

  @Locate(css = "h2.product-carousel__sub-heading", on = Platform.WEB)
  protected Text subHeaderText;

  @Locate(css = "p.product-carousel__description", on = Platform.WEB)
  protected Text descriptionText;

  @Locate(css = ".product-carousel__banner-wrap .desktop-only img", on = Platform.WEB)
  @Locate(css = ".product-carousel__banner-wrap .hide-desktop img", on = Platform.WEB_MOBILE_PHONE)
  protected Image image;

  @Override
  public void afterInit() {
    super.afterInit();
    WebHelper.waitForElementToAppear(headerText);
    WebHelper.scrollToElement(headerText);
  }

  @Step("Get only visible items")
  public List<CoffeeBarItemComponent> getVisibleCoffeeBarItems() {
    return getCoffeeBarItemComponents().stream()
        .filter(item -> item.isVisible())
        .collect(Collectors.toList());
  }

  @Step("Get only visible items names")
  public List<String> getVisibleCoffeeBarItemNames() {
    logger.info("Getting the visible coffee bar items");
    List<String> shownPositions = new ArrayList<>();
    for (int i = 0; i < getVisibleCoffeeBarItems().size(); i++) {
      shownPositions.add(getCoffeeBarItemAtPosition(i));
    }
    return shownPositions;
  }

  @Step("Get only visible item by index")
  public String getCoffeeBarItemAtPosition(int position) {
    logger.info("getting the " + position + " position's item in the coffeebar");
    String text = getCoffeeBarItemComponent(position).getName();
    for (int i = 0; i < 5; i++) {
      if (text == "") {
        SdkHelper.getSyncHelper()
            .wait(Until.uiElement(getCoffeeBarItemComponent(position)).visible());
        text = getCoffeeBarItemComponent(position).getName().trim();
      }
    }
    logger.info("-- found " + text + " at position " + position);
    return text;
  }

  @Step("Click the view seasonal items button in the coffee bar")
  public SeasonalPage clickSeeAllSeasonalBeveragesButton() {
    logger.info("Clicking the view seasonal items button in the coffee bar");
    WebHelper.waitForElementToAppear(headerText, 60);
    WebHelper.scrollToElement(headerText);
    WebHelper.waitForElementToAppear(seasonalButton);
    WebHelper.scrollToElement(seasonalButton);
    seasonalButton.click();
    return SdkHelper.create(SeasonalPage.class);
  }

  @Step("Click the find coffee bar button in coffee bar section")
  public StoreLocatorPage clickFindCoffeeBarButton() {
    logger.info("Clicking the find coffee bar button in coffee bar section");
    SdkHelper.getSyncHelper().wait(Until.uiElement(findCoffeeBar).clickable()).click();
    return SdkHelper.create(StoreLocatorPage.class);
  }

  @Step("Click next in coffee bar")
  public void clickCoffeeBarNext() {
    logger.info("Clicking next in coffee bar");
    SdkHelper.getSyncHelper().wait(Until.uiElement(nextButton).clickable()).click();
  }

  @Step("Click previous in coffee bar")
  public void clickCoffeeBarPrevious() {
    logger.info("Clicking previous in coffee bar");
    SdkHelper.getSyncHelper().wait(Until.uiElement(previousButton).clickable()).click();
  }

  @Step("Click  coffee bar item component by index")
  public CoffeeBarItemComponent getCoffeeBarItemComponent(int index) {
    return getCoffeeBarItemComponents().get(index);
  }

  @Step("Click all coffee bar item components")
  public List<CoffeeBarItemComponent> getCoffeeBarItemComponents() {
    ((LazyList<?>) coffeeBarItemComponents).initialize();
    return coffeeBarItemComponents;
  }

  @Step("Verify all products are displayed with name and image")
  public boolean areItemsDisplayedProperly() {
    for (CoffeeBarItemComponent item : getVisibleCoffeeBarItems()) {
      if (!item.isProductNameDisplayed()) {
        logger.error("Product name isn't displayed");
        return false;
      }

      if (!item.isProductImageDisplayed()) {
        logger.error("Product image isn't displayed for the product: {}", item.getName());
        return false;
      }
    }

    return true;
  }

  @Step("Verify section description is displayed")
  public boolean isDescriptionIsDisplayed() {
    return WebHelper.isDisplayed(descriptionText);
  }

  @Step("Verify section image is displayed")
  public boolean isImageIsDisplayed() {
    return WebHelper.isDisplayed(image);
  }

  @Step("Verify section header is displayed")
  public boolean isHeaderIsDisplayed() {
    return WebHelper.isDisplayed(headerText);
  }

  @Step("Verify section sub-header is displayed")
  public boolean isSubHeaderIsDisplayed() {
    return WebHelper.isDisplayed(subHeaderText);
  }

  @Step("Verify all unique items are displayed")
  public boolean areUniqueItemsAreDisplayed() {
    List<String> listWithoutDuplicates =
        new ArrayList<>(new HashSet<>(getVisibleCoffeeBarItemNames()));
    return listWithoutDuplicates.size() == getVisibleCoffeeBarItemNames().size();
  }
}

class CoffeeBarCarouselComponentMobile extends CoffeeBarCarouselComponent {

  @Override
  public void clickCoffeeBarNext() {
    logger.info("clicking next in coffee bar");
    SdkHelper.getSyncHelper().wait(Until.uiElement(nextButton).clickable());
    WebHelper.scrollToElement(nextButton);
    WebHelper.jsClick(nextButton.getWebElement());
  }
}
