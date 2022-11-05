package com.applause.auto.new_web.components;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.Constants.MenuOptions;
import com.applause.auto.common.data.enums.Attribute;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.new_web.views.CurrentOffersPage;
import com.applause.auto.new_web.views.FreeHomeDeliveryPage;
import com.applause.auto.new_web.views.HomePage;
import com.applause.auto.new_web.views.PeetnikRewardsPage;
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
import org.openqa.selenium.support.ui.WebDriverWait;

@Implementation(is = Header.class, on = Platform.WEB)
@Implementation(is = HeaderMobile.class, on = Platform.WEB_MOBILE_PHONE)
public class Header extends BaseComponent {

  @Locate(id = "header", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Locate(css = "i.header__icon.icon.icon--logo-dark", on = Platform.WEB)
  protected Button logoButton;

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

  @Locate(css = "a[data-id='free-shipping-nav']", on = Platform.WEB)
  @Locate(
      xpath = "//li[@class='nav__mobile-main-link']/a[contains(., 'Free')]",
      on = Platform.WEB_MOBILE_PHONE)
  protected Button freeShippingCategory;

  @Locate(css = "a[data-id='coffeebars-nav']", on = Platform.WEB)
  @Locate(css = "button[data-id='coffeebars-nav']", on = Platform.WEB_MOBILE_PHONE)
  protected Button coffeeBarsCategory;

  @Locate(css = "a[data-id='learn-nav']", on = Platform.WEB)
  @Locate(css = "button[data-id='learn-nav']", on = Platform.WEB_MOBILE_PHONE)
  protected Button learnCategory;

  @Locate(css = "#promoTile2 .menu-category__link", on = Platform.WEB)
  @Locate(css = "#promoTile2 .menu-category__link", on = Platform.WEB_MOBILE_PHONE)
  protected Button peetnikRewardsCategory;

  @Locate(css = "a[data-id='offers-nav']", on = Platform.WEB)
  @Locate(css = "button[data-id='offers-nav']", on = Platform.WEB_MOBILE_PHONE)
  protected Button offersCategory;

  @Locate(css = "#coffee-nav .nav__columns a[href*='%s']", on = Platform.WEB)
  protected Button subCategories;

  @Locate(css = ".nav__secondary-item a[href*='%s']", on = Platform.WEB)
  protected Button subCategoriesAllLinks;

  @Locate(css = ".nav__column-item a[href*='%s']", on = Platform.WEB)
  @Locate(css = ".nav__column-item a[href*='%s']", on = Platform.WEB_MOBILE_PHONE)
  private Button subCategoriesAlternative;

  @Locate(css = ".desktop-only a[href*='%s']", on = Platform.WEB)
  @Locate(css = ".hide-desktop a[href*='%s']", on = Platform.WEB_MOBILE_PHONE)
  private Button subscriptionCategories;

  @Locate(css = ".header__mobile-menu > button", on = Platform.WEB_MOBILE_PHONE)
  protected Button hamburgerButton;

  @Locate(css = ".nav__mobile-utils > button", on = Platform.WEB_MOBILE_PHONE)
  protected Button closeHamburgerMenuButton;

  @Locate(css = "#header a[href*='/login'] i", on = Platform.WEB)
  @Locate(css = "#navMobileMain a[href*='/login']", on = Platform.WEB_MOBILE_PHONE)
  protected Button signInButton;

  @Locate(css = "[aria-label=\"Log in\"]", on = Platform.WEB)
  @Locate(css = "#navMobileMain [aria-label=\"Log in\"]", on = Platform.WEB_MOBILE_PHONE)
  protected Button accountButton;

  @Locate(css = "#header button[class*='search']", on = Platform.WEB)
  protected Button searchIcon;

  @Locate(css = "i.icon.icon--cart-black", on = Platform.WEB)
  protected Button cartIcon;

  @Locate(css = "i.icon.icon--store-locator", on = Platform.WEB)
  @Locate(
      css = "div.header-utils__util-wrap.hide-desktop a i.icon.icon--store-locator",
      on = Platform.WEB_MOBILE_PHONE)
  protected Button locationIcon;

  @Locate(css = "i.icon.icon--account-black", on = Platform.WEB)
  @Locate(
      css = "div.header-utils__util-wrap.hide-desktop a i.icon--account-black",
      on = Platform.WEB_MOBILE_PHONE)
  protected Button personIcon;

  @Locate(xpath = "//header//a[./i[contains(@class, 'store')]]", on = Platform.WEB)
  @Locate(
      xpath = "//*[@id='navMobileMain']//a[./i[contains(@class, 'store')]]",
      on = Platform.WEB_MOBILE_PHONE)
  protected Button storeLocationIcon;

  @Locate(id = "searchMenu", on = Platform.WEB)
  protected ContainerElement searchComponent;

  @Locate(
      xpath =
          "//div[@id='coffee-nav']//div[contains(@class,'desktop-only')]//a[contains(@href,'%s')]",
      on = Platform.WEB)
  @Locate(
      xpath =
          "//div[@id='coffee-nav']//div[contains(@class,'hide-desktop')]//a[contains(@href,'%s')]",
      on = Platform.WEB_MOBILE_PHONE)
  protected Button anniversaryAndSumatraLinks;

  @Locate(
      xpath =
          "//div[@id='coffee-nav']//div[contains(@class,'desktop-only')]//a[contains(@href,'coffee-finder') and contains(@class,'btn btn--primary desktop-only js-nav-focusable')]",
      on = Platform.WEB)
  @Locate(
      xpath =
          "//div[@id='coffee-nav']//div[contains(@class,'desktop-only')]//a[contains(@href,'coffee-finder') and contains(@class,'nav__secondary-link')]",
      on = Platform.WEB_MOBILE_PHONE)
  protected Button findYourMatch;

  @Locate(
      xpath = "//div[@id='tea-nav']//div[contains(@class,'desktop-only')]//a[contains(@href,'%s')]",
      on = Platform.WEB)
  @Locate(
      xpath = "//div[@id='tea-nav']//div[contains(@class,'hide-desktop')]//a[contains(@href,'%s')]",
      on = Platform.WEB_MOBILE_PHONE)
  protected Button findMatchAndMightLeafLinks;

  @Locate(css = ".cookieconsent-wrapper .cc-allow", on = Platform.WEB)
  private Button allowCookies;

  @Locate(css = "nav[aria-label='main'] > .is-visible", on = Platform.WEB)
  protected ContainerElement expandedSection;

  @Locate(css = "#coffeebars-nav .nav-mobile-header__back-text", on = Platform.WEB_MOBILE_PHONE)
  protected Button backButton;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(mainContainer).present());

    // Todo: Commented on 08.09.2022 [Review if still needed]
    //    if (!WebHelper.isDesktop() && allowCookies.exists()) {
    //      logger.info("Accept Cookies");
    //      WebHelper.jsClick(allowCookies.getWebElement());
    //    }
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
    } else if (menuOptions.equals(Constants.MenuOptions.COFFEE_BARS)) {
      WebHelper.hoverByAction(coffeeBarsCategory);
    } else if (menuOptions.equals(Constants.MenuOptions.LEARN)) {
      WebHelper.hoverByAction(learnCategory);
    } else if (menuOptions.equals(MenuOptions.FREE_HOME_DELIVERY)) {
      WebHelper.hoverByAction(freeShippingCategory);
    } else if (menuOptions.equals(MenuOptions.PEETNIK_REWARDS)) {
      WebHelper.hoverByAction(peetnikRewardsCategory);
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

  @Step("Select Free Home Delivery from Menu")
  public FreeHomeDeliveryPage clickFreeHomeDeliveryFromMenu() {
    logger.info("Tab selected: Free Home Delivery");
    freeShippingCategory.click();
    return SdkHelper.create(FreeHomeDeliveryPage.class);
  }

  @Step("Select Peetnik Rewards from Menu")
  public PeetnikRewardsPage clickPeetnikRewardsFromMenu() {
    logger.info("Tab selected: Peetnik Rewards");

    peetnikRewardsCategory.click();
    return SdkHelper.create(PeetnikRewardsPage.class);
  }

  @Step("Select Offers from Menu")
  public CurrentOffersPage clickOffersFromMenu() {
    logger.info("Tab selected: Offers");
    offersCategory.click();
    return SdkHelper.create(CurrentOffersPage.class);
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
      try {
        subCategories.format(menuSubCategories.getMenuSubCategories()).initialize();
        SdkHelper.getSyncHelper().wait(Until.uiElement(subCategories).visible());
        subCategories.click();
      } catch (Exception e) {
        // use alternative category locator
        subCategoriesAlternative.format(menuSubCategories.getMenuSubCategories()).initialize();
        SdkHelper.getSyncHelper().wait(Until.uiElement(subCategoriesAlternative).visible());
        subCategoriesAlternative.click();
      }
    }

    return SdkHelper.create(clazz);
  }

  @Step("Click Account Button")
  public SignInPage clickAccountButton() {
    logger.info("Tap on Account Button");
    SdkHelper.getSyncHelper().wait(Until.uiElement(signInButton).clickable()).click();

    return SdkHelper.create(SignInPage.class);
  }

  @Step("Verify submenu item displays")
  public boolean isSubMenuItemDisplayed(String linkHref) {
    SdkHelper.getSyncHelper().wait(Until.uiElement(subCategories.format(linkHref)).visible());
    return subCategories.format(linkHref).isDisplayed();
  }

  @Step("Verify submenu item displays")
  public boolean isAlternativeSubMenuItemDisplayed(String linkHref) {
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(subCategoriesAlternative.format(linkHref)).visible());
    return subCategoriesAlternative.format(linkHref).isDisplayed();
  }

  @Step("Verify search icon displays")
  public boolean isSearchIconDisplayed() {
    return searchIcon.isDisplayed();
  }

  @Step("Verify person icon displays")
  public boolean isPersonIconDisplayed() {
    logger.info("Checking person icon is displayed");
    return personIcon.isDisplayed();
  }

  @Step("Verify location icon displays")
  public boolean isLocationIconDisplayed() {
    return locationIcon.isDisplayed();
  }

  @Step("Verify shopping cart icon displays")
  public boolean isCartIconDisplayed() {
    return cartIcon.isDisplayed();
  }

  public MiniCart clickCartIcon() {
    logger.info("Click over miniCart icon");
    cartIcon.click();
    return SdkHelper.create(MiniCart.class);
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

  @Step("Verify Logo is displayed")
  public boolean isLogoDisplayed() {
    return logoButton.isDisplayed();
  }

  @Step("Verify coffee menu item is displayed")
  public boolean isCoffeeMenuItemDisplayed() {
    return coffeeCategory.isDisplayed();
  }

  @Step("Verify tea menu item is displayed")
  public boolean isTeaMenuItemDisplayed() {
    return teaCategory.isDisplayed();
  }

  @Step("Verify visit us menu item is displayed")
  public boolean isVisitUsMenuItemDisplayed() {
    return coffeeBarsCategory.isDisplayed();
  }

  @Step("Verify free shipping menu item is displayed")
  public boolean isFreeShippingItemDisplayed() {
    return freeShippingCategory.isDisplayed();
  }

  @Step("Verify learn menu item is displayed")
  public boolean isLearnMenuItemDisplayed() {
    return learnCategory.isDisplayed();
  }

  @Step("Verify peetnik menu item is displayed")
  public boolean isPeetnikRewardsMenuItemDisplayed() {
    return peetnikRewardsCategory.isDisplayed();
  }

  @Step("Verify offers menu item is displayed")
  public boolean isOffersMenuItemDisplayed() {
    return offersCategory.isDisplayed();
  }

  @Step("Open Hamburger Menu")
  public void openHamburgerMenu() {
    throw new NotImplementedException("Hamburger Menu isn't present on desktop");
  }

  @Step("Close Hamburger Menu")
  public void closeHamburgerMenu() {
    logger.info("Hamburger Menu isn't present on desktop");
  }

  public void preopenMenu() {
    logger.info("Opening Hamburger Menu -> Do nothing, on desktop.");
  }

  @Step("Click submenu item")
  public void clickSubMenuItem(String linkHref) {
    logger.info("Click SubMenu Each Link (" + linkHref + ")");
    subCategories.format(linkHref).initialize();
    logger.info("-- initialized");
    SdkHelper.getSyncHelper().wait(Until.uiElement(subCategories.format(linkHref)).visible());
    logger.info("-- (" + linkHref + ") is visible, clicking it now");
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(subCategories.format(linkHref)).clickable())
        .click();
    logger.info("-- Waiting for navigation");
    WebHelper.slowNavigationHelper(linkHref, 5);
  }

  @Step("Click submenu Alllinks")
  public void clickSubMenuItemAllLinks(String linkHref) {
    logger.info("Click SubMenu AllLinks Link");
    subCategoriesAllLinks.format(linkHref).initialize();
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(subCategoriesAllLinks.format(linkHref)).visible());
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(subCategoriesAllLinks.format(linkHref)).clickable())
        .click();
    logger.info("-- Waiting for navigation");
    WebHelper.slowNavigationHelper(linkHref, 5);
  }

  @Step("Click Coffee submenu Anaversiry Blend,Sumatra Batak Links")
  public void clickCoffeeSubMenuAnnaversiryAndSumatraLinks(String linkHref) {
    anniversaryAndSumatraLinks.format(linkHref).initialize();
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(anniversaryAndSumatraLinks.format(linkHref)).visible());
    WebHelper.jsClick(anniversaryAndSumatraLinks.getWebElement());
    try {
      new WebDriverWait(SdkHelper.getDriver(), 20)
          .until(p -> WebHelper.getCurrentUrl().contains(linkHref));
    } catch (Exception e) {
      throw new RuntimeException("New Page is Not opened.");
    }
  }

  @Step("Click Coffe submenu FindYourMatch Link")
  public void clickCoffeeFindYourMatch(String linkHref) {
    SdkHelper.getSyncHelper().wait(Until.uiElement(findYourMatch).visible());
    WebHelper.jsClick(findYourMatch.getWebElement());
    logger.info("-- Waiting for navigation");
    WebHelper.slowNavigationHelper(linkHref, 5);
  }

  @Step("Click Tea submenu Find your Tea Match and About Mighty Leaf Links")
  public void clickTeaSubMenuTeaMatchAndMightyLeafLinks(String linkHref) {
    findMatchAndMightLeafLinks.format(linkHref).initialize();
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(findMatchAndMightLeafLinks.format(linkHref)).visible());
    WebHelper.jsClick(findMatchAndMightLeafLinks.getWebElement());
    logger.info("-- Waiting for navigation");
    WebHelper.slowNavigationHelper(linkHref, 5);
  }

  /**
   * Verify menu section is expanded when hovering over menu link
   *
   * @return boolean
   */
  public boolean isMenuSectionExpanded() {
    return WebHelper.isDisplayed(expandedSection);
  }

  @Step("Verify user is logged out")
  public boolean isUserLoggedOut() {
    logger.info("Checking user is logged out");
    return WebHelper.isDisplayed(accountButton, 5);
  }

  @Step("Click Logo Button")
  public HomePage clickLogoButton() {
    logger.info("Tap on Logo Button");
    SdkHelper.getSyncHelper().wait(Until.uiElement(logoButton).clickable()).click();
    SdkHelper.getSyncHelper().sleep(1000); // Sometimes, it takes time to change

    return SdkHelper.create(HomePage.class);
  }

  @Step("Click back button")
  public void clickBackButton() {
    try {
      if (backButton.isDisplayed()) logger.info("Click on back button");
      backButton.click();
    } catch (Exception e) {
      logger.info("No back button is displayed");
    }
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
    } else if (menuOptions.equals(Constants.MenuOptions.COFFEE_BARS)) {
      coffeeBarsCategory.click();
    } else if (menuOptions.equals(Constants.MenuOptions.LEARN)) {
      learnCategory.click();
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
  public void preopenMenu() {
    openHamburgerMenu();
  }

  @Override
  public SignInPage clickAccountButton() {
    openHamburgerMenu();

    logger.info("Tap on SignIn Button");
    SdkHelper.getSyncHelper().wait(Until.uiElement(signInButton).clickable()).click();

    return SdkHelper.create(SignInPage.class);
  }

  @Override
  @Step("Select Free Home Delivery from Menu")
  public FreeHomeDeliveryPage clickFreeHomeDeliveryFromMenu() {
    openHamburgerMenu();

    logger.info("Tab selected: Free Home Delivery");
    SdkHelper.getSyncHelper().wait(Until.uiElement(freeShippingCategory).clickable()).click();

    return SdkHelper.create(FreeHomeDeliveryPage.class);
  }

  @Override
  @Step("Select Peetnik Rewards from Menu")
  public PeetnikRewardsPage clickPeetnikRewardsFromMenu() {
    logger.info("Tab selected: Peetnik Rewards");
    peetnikRewardsCategory.scrollToElement();
    SdkHelper.getSyncHelper().wait(Until.uiElement(peetnikRewardsCategory).clickable());
    WebHelper.jsClick(peetnikRewardsCategory.getWebElement());
    return SdkHelper.create(PeetnikRewardsPage.class);
  }

  @Override
  @Step("Select Offers from Menu")
  public CurrentOffersPage clickOffersFromMenu() {
    openHamburgerMenu();

    logger.info("Tab selected: Offers");
    SdkHelper.getSyncHelper().wait(Until.uiElement(offersCategory).clickable()).click();
    return SdkHelper.create(CurrentOffersPage.class);
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

  @Override
  @Step("Click submenu Alllinks")
  public void clickSubMenuItemAllLinks(String linkHref) {
    logger.info("Click SubMenu AllLinks Link");
    subCategoriesAllLinks.format(linkHref).initialize();
    WebHelper.scrollToElement(subCategoriesAllLinks.getWebElement());
    SdkHelper.getBrowserControl().jsClick(subCategoriesAllLinks);
    logger.info("-- Waiting for navigation");
    WebHelper.slowNavigationHelper(linkHref, 5);
  }

  @Override
  public void closeHamburgerMenu() {
    logger.info("Close Hamburger Menu [Mobile]");
    SdkHelper.getSyncHelper().wait(Until.uiElement(closeHamburgerMenuButton).clickable());
    closeHamburgerMenuButton.click();
  }

  @Override
  @Step("Verify user is logged out")
  public boolean isUserLoggedOut() {
    logger.info("Checking user is logged out");
    openHamburgerMenu();
    boolean isUserLoggedOut = WebHelper.isDisplayed(accountButton, 5);
    closeHamburgerMenu();
    return isUserLoggedOut;
  }

  @Override
  @Step("Verify person icon displays")
  public boolean isPersonIconDisplayed() {
    logger.info("Checking person icon is displayed");
    if (!WebHelper.isDisplayed(closeHamburgerMenuButton)) {
      openHamburgerMenu();
    }
    boolean isIconDisplayed = WebHelper.isDisplayed(personIcon);
    closeHamburgerMenu();
    return isIconDisplayed;
  }

  @Override
  @Step("Verify location icon displays")
  public boolean isLocationIconDisplayed() {
    openHamburgerMenu();
    return locationIcon.isDisplayed();
  }
}
