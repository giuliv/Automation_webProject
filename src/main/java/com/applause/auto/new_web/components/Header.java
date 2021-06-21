package com.applause.auto.new_web.components;

import com.applause.auto.common.data.Constants;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.new_web.views.ProductListPage;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.new_web.views.SignInPage;

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

  @Locate(css = ".nav__columns a[href*='%s']", on = Platform.WEB)
  private Button subCategories;

  @Locate(css = ".header__mobile-menu", on = Platform.WEB_MOBILE_PHONE)
  protected Button hamburgerButton;

  @Locate(css = "#header a[href*='/login'] i", on = Platform.WEB)
  @Locate(css = "#navMobileMain a[href*='/login']", on = Platform.WEB_MOBILE_PHONE)
  protected Button signInButton;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(mainContainer).present());
  }

  /* -------- Actions -------- */

  public void hoverCategoryFromMenu(Constants.MenuOptions menuOptions) {
    logger.info("Tab selected: " + menuOptions.name());

    if (menuOptions.equals(Constants.MenuOptions.COFFEE)) {
      WebHelper.hoverByAction(coffeeCategory);
    } else if (menuOptions.equals(Constants.MenuOptions.GEAR)) {
      gearCategory.click();
    } else if (menuOptions.equals(Constants.MenuOptions.TEA)) {
      WebHelper.hoverByAction(teaCategory);
    }
  }

  public ProductListPage clickCategoryFromMenu(Constants.MenuOptions menuOptions) {
    logger.info("Tab selected: " + menuOptions.name());

    if (menuOptions.equals(Constants.MenuOptions.GEAR)) {
      gearCategory.click();
    }
    return SdkHelper.create(ProductListPage.class);
  }

  public <T extends BaseComponent> T clickOverSubCategoryFromMenu(
      Class<T> clazz, Constants.MenuSubCategories menuSubCategories) {
    logger.info("SubCategory selected " + (menuSubCategories.name()));
    subCategories.format(menuSubCategories.getMenuSubCategories()).initialize();

    SdkHelper.getSyncHelper().wait(Until.uiElement(subCategories).visible());
    subCategories.click();

    return SdkHelper.create(clazz);
  }

  public SignInPage clickSignInButton() {
    logger.info("Tap on SignIn Button");
    SdkHelper.getSyncHelper().wait(Until.uiElement(signInButton).clickable()).click();

    return SdkHelper.create(SignInPage.class);
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

  public void openHamburgerMenu() {
    logger.info("Open Hamburger Menu [Mobile]");
    SdkHelper.getSyncHelper().wait(Until.uiElement(hamburgerButton).visible());
    hamburgerButton.click();

    SdkHelper.getSyncHelper().wait(Until.uiElement(coffeeCategory).visible());
  }

  @Override
  public SignInPage clickSignInButton() {
    openHamburgerMenu();

    logger.info("Tap on SignIn Button");
    SdkHelper.getSyncHelper().wait(Until.uiElement(signInButton).clickable()).click();

    return SdkHelper.create(SignInPage.class);
  }
}
