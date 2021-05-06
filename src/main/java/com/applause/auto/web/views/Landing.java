package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.helper.sync.Until;
import com.applause.auto.web.components.MainMenuChunk;

@Implementation(is = Landing.class, on = Platform.WEB)
public class Landing extends BaseComponent {

  /* -------- Lifecycle Methods -------- */

  @Override
  public void afterInit() {
    super.afterInit();
    try {
      logger.info("Attempting to dismiss popup");
      getSyncHelper().wait(Until.uiElement(dismissPopupButton).present()).click();
    } catch (Exception e) {
      logger.info("Popup not found, moving on");
    }
  }

  /* -------- Elements -------- */

  @Locate(css = "#header a[href*='/login']", on = Platform.WEB)
  private Button signInButton;

  @Locate(css = ".close-button", on = Platform.WEB)
  private Button dismissPopupButton;

  @Locate(css = "ul.shop-list li:nth-child(1)", on = Platform.WEB)
  private Button shopCoffeeButton;

  @Locate(css = "ul.shop-list li:nth-child(2)", on = Platform.WEB)
  private Button shopTeaButton;

  @Locate(css = "ul.shop-list li:nth-child(3)", on = Platform.WEB)
  private Button shopEquipmentButton;

  @Locate(css = ".skip-link.skip-search", on = Platform.WEB)
  private Button showSearchButton;

  @Locate(css = "#search", on = Platform.WEB)
  private TextBox searchTextBox;

  @Locate(css = ".button.search-button", on = Platform.WEB)
  private Button searchButton;

  /* -------- Actions -------- */

  /**
   * Taps the sign in button.
   *
   * @return a Login
   */
  public SignInPage clickSignInButton() {
    logger.info("Tap on SignIn Button");
    getSyncHelper().wait(Until.uiElement(signInButton).clickable()).click();

    return this.create(SignInPage.class);
  }

  /**
   * Click Shop Coffee Button
   *
   * @return a Shop Coffee Page
   */
  public ShopCoffeePage clickShopCoffeeButton() {
    logger.info("Tap on Shop Coffee Button");
    shopCoffeeButton.click();
    return this.create(ShopCoffeePage.class);
  }

  /**
   * Click Shop Tea Button
   *
   * @return a Shop Tea Page
   */
  public ShopTeaPage clickShopTeaButton() {
    logger.info("Tap on Shop Tea Button");
    shopTeaButton.click();
    return this.create(ShopTeaPage.class);
  }

  /**
   * Click Shop Equipment Button
   *
   * @return a Shop Equipment Page
   */
  public ShopEquipmentPage clickShopEquipmentButton() {
    logger.info("Tap on Shop Equipment Button");
    shopEquipmentButton.click();
    return this.create(ShopEquipmentPage.class);
  }

  /**
   * s Main Menu
   *
   * @return MainMenuChunk
   */
  public MainMenuChunk mainMenu() {
    logger.info("ting Main Menu");
    return this.create(MainMenuChunk.class);
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
    return this.create(SearchResultsPage.class);
  }
}
