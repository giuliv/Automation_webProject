package com.applause.auto.new_web.components;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.enums.Attribute;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.new_web.views.ProductListPage;
import com.applause.auto.new_web.views.SignInPage;
import com.applause.auto.new_web.views.StoreLocatorPage;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import io.qameta.allure.Step;
import org.apache.commons.lang3.NotImplementedException;

@Implementation(is = Header.class, on = Platform.WEB)
@Implementation(is = HeaderMobile.class, on = Platform.WEB_MOBILE_PHONE)
public class Header extends BaseComponent {

  @Locate(id = "header", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Locate(css = "a[data-id='coffee-nav']", on = Platform.WEB)
  @Locate(css = "button[data-id='coffee-nav']", on = Platform.WEB_MOBILE_PHONE)
  protected Button coffeeCategory;

  @Locate(css = "a[data-id='gear-nav']", on = Platform.WEB)
  @Locate(css = "li a[href*='equipment']", on = Platform.WEB_MOBILE_PHONE)
  protected Button gearCategory;

  @Locate(css = "a[data-id='tea-nav']", on = Platform.WEB)
  @Locate(css = "button[data-id='tea-nav']", on = Platform.WEB_MOBILE_PHONE)
  protected Button teaCategory;

  @Locate(css = "a[data-id='join-the-club-nav']", on = Platform.WEB)
  @Locate(css = "button[data-id='join-the-club-nav']", on = Platform.WEB_MOBILE_PHONE)
  protected Button subscriptionTabCategory;

  @Locate(css = ".nav__columns a[href*='%s']", on = Platform.WEB)
  private Button subCategories;

  @Locate(css = ".desktop-only a[href*='%s']", on = Platform.WEB)
  @Locate(css = ".hide-desktop a[href*='%s']", on = Platform.WEB_MOBILE_PHONE)
  private Button subscriptionCategories;

  @Locate(css = ".header__mobile-menu > button", on = Platform.WEB_MOBILE_PHONE)
  protected Button hamburgerButton;

  @Locate(css = "#header a[href*='/login'] i", on = Platform.WEB)
  @Locate(css = "#navMobileMain a[href*='/login']", on = Platform.WEB_MOBILE_PHONE)
  protected Button signInButton;

  @Locate(css = "[aria-label=\"Log in\"]", on = Platform.WEB)
  protected Button accountButton;

  @Locate(css = "#header button[class*='search']", on = Platform.WEB)
  protected Button searchIcon;

  @Locate(xpath = "//header//a[./i[contains(@class, 'store')]]", on = Platform.WEB)
  @Locate(
      xpath = "//*[@id='navMobileMain']//a[./i[contains(@class, 'store')]]",
      on = Platform.WEB_MOBILE_PHONE)
  protected Button storeLocationIcon;

  @Locate(id = "searchMenu", on = Platform.WEB)
  protected ContainerElement searchComponent;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(mainContainer).present());
  }

  /* -------- Actions -------- */

  @Step("Hover category from Menu")
  public void hoverCategoryFromMenu(Constants.MenuOptions menuOptions) {
    logger.info("Tab selected: " + menuOptions.name());

    if (menuOptions.equals(Constants.MenuOptions.COFFEE)) {
      WebHelper.hoverByAction(coffeeCategory);
    } else if (menuOptions.equals(Constants.MenuOptions.GEAR)) {
      gearCategory.click();
    } else if (menuOptions.equals(Constants.MenuOptions.TEA)) {
      WebHelper.hoverByAction(teaCategory);
    } else if (menuOptions.equals(Constants.MenuOptions.SUBSCRIPTION)) {
      WebHelper.hoverByAction(subscriptionTabCategory);
    }
  }

  @Step("Select Category from Menu")
  public ProductListPage clickCategoryFromMenu(Constants.MenuOptions menuOptions) {
    logger.info("Tab selected: " + menuOptions.name());

    if (menuOptions.equals(Constants.MenuOptions.GEAR)) {
      gearCategory.click();
    }
    return SdkHelper.create(ProductListPage.class);
  }

  @Step("Select SubCategory from Menu")
  public <T extends BaseComponent> T clickOverSubCategoryFromMenu(
      Class<T> clazz, Constants.MenuSubCategories menuSubCategories) {
    logger.info("SubCategory selected " + menuSubCategories.name());
    if (menuSubCategories.name().contains("SUBSCRIPTIONS")) {
      subscriptionCategories.format(menuSubCategories.getMenuSubCategories()).initialize();
      SdkHelper.getSyncHelper().wait(Until.uiElement(subscriptionCategories).visible());
      subscriptionCategories.click();
    } else {
      subCategories.format(menuSubCategories.getMenuSubCategories()).initialize();
      SdkHelper.getSyncHelper().wait(Until.uiElement(subCategories).visible());
      subCategories.click();
    }

    return SdkHelper.create(clazz);
  }

  @Step("Click Account Button")
  public SignInPage clickAccountButton() {
    logger.info("Tap on Account Button");
    SdkHelper.getSyncHelper().wait(Until.uiElement(signInButton).clickable()).click();

    return SdkHelper.create(SignInPage.class);
  }

  @Step("Get Search component")
  public SearchComponent getSearchComponent() {
    logger.info("Get Search Component");
    searchIcon.click();
    return SdkHelper.create(SearchComponent.class);
  }

  @Step("Verify search component is opened")
  public boolean searchComponentIsOpened() {
    return searchComponent.getAttributeValue(Attribute.CLASS.getValue()).contains("is-open");
  }

  @Step("Click on 'Store Locator' button")
  public StoreLocatorPage clickOnStoreLocator() {
    logger.info("Clicking on 'Store Locator' button");
    storeLocationIcon.click();
    return SdkHelper.create(StoreLocatorPage.class);
  }

  @Step("Verify Sign in button is displayed")
  public boolean isSignInButtonDisplayed() {
    return signInButton.isDisplayed();
  }

  @Step("Open Hamburger Menu")
  public void openHamburgerMenu() {
    throw new NotImplementedException("Hamburger Menu isn't present on desktop");
  }
}

class HeaderMobile extends Header {

  @Override
  public void hoverCategoryFromMenu(Constants.MenuOptions menuOptions) {
    openHamburgerMenu();

    logger.info("Tab selected: " + menuOptions.name());
    if (menuOptions.equals(Constants.MenuOptions.COFFEE)) {
      coffeeCategory.click();
    } else if (menuOptions.equals(Constants.MenuOptions.GEAR)) {
      gearCategory.click();
    } else if (menuOptions.equals(Constants.MenuOptions.TEA)) {
      teaCategory.click();
    } else if (menuOptions.equals(Constants.MenuOptions.SUBSCRIPTION)) {
      subscriptionTabCategory.click();
    }
  }

  @Override
  public ProductListPage clickCategoryFromMenu(Constants.MenuOptions menuOptions) {
    openHamburgerMenu();

    logger.info("Tab selected: " + menuOptions.name());
    if (menuOptions.equals(Constants.MenuOptions.GEAR)) {
      gearCategory.click();
    }
    return SdkHelper.create(ProductListPage.class);
  }

  @Override
  @Step("Open Hamburger Menu")
  public void openHamburgerMenu() {
    logger.info("Open Hamburger Menu [Mobile]");
    SdkHelper.getSyncHelper().wait(Until.uiElement(hamburgerButton).clickable());
    hamburgerButton.click();

    SdkHelper.getSyncHelper().wait(Until.uiElement(coffeeCategory).visible());
  }

  @Override
  public SignInPage clickAccountButton() {
    openHamburgerMenu();

    logger.info("Tap on SignIn Button");
    SdkHelper.getSyncHelper().wait(Until.uiElement(signInButton).clickable()).click();

    return SdkHelper.create(SignInPage.class);
  }

  @Step("Click on 'Store Locator' button")
  public StoreLocatorPage clickOnStoreLocator() {
    openHamburgerMenu();

    logger.info("Clicking on 'Store Locator' button");
    storeLocationIcon.click();
    return SdkHelper.create(StoreLocatorPage.class);
  }

  @Step("Verify Sign in button is displayed")
  public boolean isSignInButtonDisplayed() {
    openHamburgerMenu();
    return signInButton.isDisplayed();
  }
}
