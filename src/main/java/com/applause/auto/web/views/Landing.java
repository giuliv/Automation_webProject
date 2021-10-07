package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.TextBox;

@Implementation(is = Landing.class, on = Platform.WEB)
@Implementation(is = LandingMobile.class, on = Platform.WEB_MOBILE_PHONE)
public class Landing extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(css = "#header a[href*='/login']", on = Platform.WEB)
  @Locate(css = "#navMobileMain a[href*='/login']", on = Platform.WEB_MOBILE_PHONE)
  protected Button signInButton;

  @Locate(css = ".close-button", on = Platform.WEB)
  private Button dismissPopupButton;

  @Locate(css = "ul.shop-list li:nth-child(1)", on = Platform.WEB)
  private Button shopCoffeeButton;

  @Locate(css = ".skip-link.skip-search", on = Platform.WEB)
  private Button showSearchButton;

  @Locate(css = "#search", on = Platform.WEB)
  private TextBox searchTextBox;

  @Locate(css = ".button.search-button", on = Platform.WEB)
  private Button searchButton;

  @Locate(className = "header__mobile-menu", on = Platform.WEB_MOBILE_PHONE)
  protected Button hamburgerMenu;

  @Override
  public void afterInit() {
    super.afterInit();
    try {
      logger.info("Attempting to dismiss popup");
      SdkHelper.getSyncHelper().wait(Until.uiElement(dismissPopupButton).present()).click();
    } catch (Exception e) {
      logger.info("Popup not found, moving on");
    }
  }

  /* -------- Actions -------- */

  /**
   * Taps the sign in button.
   *
   * @return a Login
   */
  public SignInPage clickSignInButton() {
    logger.info("Tap on SignIn Button");
    SdkHelper.getSyncHelper().wait(Until.uiElement(signInButton).clickable()).click();

    return SdkHelper.create(SignInPage.class);
  }

  /**
   * Click Shop Coffee Button
   *
   * @return a Shop Coffee Page
   */
  public ShopCoffeePage clickShopCoffeeButton() {
    logger.info("Tap on Shop Coffee Button");
    shopCoffeeButton.click();
    return SdkHelper.create(ShopCoffeePage.class);
  }

  /**
   * Search for Product
   *
   * @param searchTerms
   * @return SearchResultsPage
   */
  public SearchResultsPage searchForProduct(String searchTerms) {
    logger.info("Searching for " + searchTerms);
    showSearchButton.click();
    searchTextBox.sendKeys(searchTerms);
    searchButton.click();
    return SdkHelper.create(SearchResultsPage.class);
  }
}

class LandingMobile extends Landing {
  @Override
  public SignInPage clickSignInButton() {
    logger.info("Tap on Hamburger Menu");
    hamburgerMenu.click();

    logger.info("Tap on SignIn Button");
    SdkHelper.getSyncHelper().wait(Until.uiElement(signInButton).clickable()).click();

    return SdkHelper.create(SignInPage.class);
  }
}
