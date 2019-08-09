package com.applause.auto.web.components;

import com.applause.auto.common.data.Constants;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.DriverManager;
import com.applause.auto.util.helper.EnvironmentHelper;
import com.applause.auto.web.views.Landing;
import com.applause.auto.web.views.PeetsCardProductPage;
import com.applause.auto.web.views.ShopEquipmentPage;
import com.applause.auto.web.views.ShopGiftSubscriptionsPage;
import com.applause.auto.web.views.ShopTeaPage;
import org.openqa.selenium.interactions.Actions;

@Implementation(is = MainMenuChunk.class, on = Platform.WEB)
public class MainMenuChunk extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(css = ".page-header-container", on = Platform.WEB)
  protected Text getViewSignature;

  @Locate(xpath = "//li[contains(.,'%s')]", on = Platform.WEB)
  protected Button getMainMenuCategoryButton;

  @Locate(xpath = "//a[@class='drop-link' and contains(.,'%s')]", on = Platform.WEB)
  protected Button getCategorySubmenuButton;

  @Locate(xpath = "//a[contains(.,'%s')]", on = Platform.WEB)
  protected Button getSubcategoryButton;

  @Locate(css = ".logo", on = Platform.WEB)
  protected Button getHeaderLogo;

  @Locate(xpath = "//div[@class='mobile-slide']//a[contains(.,'%s')]", on = Platform.WEB)
  protected Button getCategoryOptionButton;

  @Locate(
      xpath = "//li[a[@class='drop-link' and contains(text(),'%s')]]//a[contains(text(),'%s')]",
      on = Platform.WEB)
  protected Button getCategoryColumnOptionButton;

  @Locate(css = "#top-cart-container", on = Platform.WEB)
  protected Button getHeaderMinicart;

  @Locate(css = "span.count", on = Platform.WEB)
  protected Button getCartItemsText;

  /* -------- Actions -------- */

  /** Hover over main menu category */
  public void hoverCategory(String category) {
    logger.info("Hover a category in the main menu");
    getMainMenuCategoryButton.initializeWithFormat(category);
    Actions actions = new Actions(DriverManager.getDriver());
    actions.moveToElement(getMainMenuCategoryButton.getWebElement()).build().perform();
  }

  /** Click a Sub-Menu under a category */
  public void clickCategorySubmenu(String category, String subMenu) {
    logger.info(String.format("Accessing Sub-Menu '%s' under category '%s'", subMenu, category));
    hoverCategory(category);
    getCategorySubmenuButton.initializeWithFormat(subMenu);
    getCategorySubmenuButton.click();
  }

  /** Click an option under a category */
  public void clickCategoryOption(String category, String option) {
    logger.info(String.format("Accessing option '%s' under category '%s'", option, category));
    hoverCategory(category);
    getCategoryOptionButton.initializeWithFormat(option);
    getCategoryOptionButton.click();
  }

  /**
   * Click category option t.
   *
   * @param <T> the type parameter
   * @param clazz the clazz
   * @param category the category
   * @param column the column
   * @param option the option
   * @return the t
   */
  public <T extends BaseComponent> T clickCategoryOption(
      Class<T> clazz, String category, String column, String option) {
    logger.info(
        String.format(
            "Accessing category [%s] subcategory [%s] option [%s]", category, column, option));
    if (EnvironmentHelper.isSafari(DriverManager.getDriver())) {
      hoverCategory(category);
      getCategoryColumnOptionButton.initializeWithFormat(column, option);
      getCategoryColumnOptionButton.click();
    } else {
      getCategoryColumnOptionButton.initializeWithFormat(column, option);
      DriverManager.getDriver().get(getCategoryColumnOptionButton.getAttributeValue("href"));
    }
    return ComponentFactory.create(clazz);
  }

  /** Access Sub-Menu Tea under Category Shop from main menu */
  public ShopTeaPage accessShopTea() {
    logger.info("Accessing Shop-Tea");
    clickCategorySubmenu(
        Constants.TestMainMenu.NAV_CATEGORY_SHOP, Constants.TestMainMenu.NAV_SUBMENU_TEA);
    return ComponentFactory.create(ShopTeaPage.class);
  }

  /**
   * Access Gift Subscriptions under Shop from Main Menu
   *
   * @return ShopGiftSubscriptionsPage
   */
  public ShopGiftSubscriptionsPage accessShopGiftSubscriptions() {
    logger.info("Accessing Shop Gift Subscriptions");
    hoverCategory(Constants.TestMainMenu.NAV_CATEGORY_SHOP);
    getSubcategoryButton.initializeWithFormat(
        Constants.TestMainMenu.NAV_SUBMENU_GIFT_SUBSCRIPTIONS);
    getSubcategoryButton.click();
    return ComponentFactory.create(ShopGiftSubscriptionsPage.class);
  }

  /** Access Sub-Menu Equipment under Category Shop from main menu */
  public ShopEquipmentPage accessShopEquipment() {
    logger.info("Accessing Shop-Equipment");
    clickCategorySubmenu(
        Constants.TestMainMenu.NAV_CATEGORY_SHOP, Constants.TestMainMenu.NAV_SUBMENU_EQUIPMENT);
    return ComponentFactory.create(ShopEquipmentPage.class);
  }

  /**
   * Click Peets Coffee logo
   *
   * @return Landing
   */
  public Landing clickHeaderLogo() {
    logger.info("Click Peets Coffee logo");
    getHeaderLogo.click();
    return ComponentFactory.create(Landing.class);
  }

  /** Access Sub-Menu Equipment under Category Shop from main menu */
  public PeetsCardProductPage accessCardsByMail() {
    logger.info("Accessing Shop-Equipment");
    clickCategorySubmenu(
        Constants.TestMainMenu.NAV_CATEGORY_SHOP, Constants.TestMainMenu.NAV_OPTION_CARDS_BY_MAIL);
    return ComponentFactory.create(PeetsCardProductPage.class);
  }

  /**
   * Click Mini-Cart icon
   *
   * @return MiniCartContainerChunk
   */
  public MiniCartContainerChunk clickMiniCart() {
    logger.info("Click mini-cart icon");
    getHeaderMinicart.click();
    return ComponentFactory.create(MiniCartContainerChunk.class);
  }

  /**
   * Close mini cart t.
   *
   * @param <T> the type parameter
   * @param clazz the clazz
   * @return the t
   */
  public <T extends BaseComponent> T closeMiniCart(Class<T> clazz) {
    logger.info("Click mini-cart icon");
    getHeaderMinicart.click();
    return ComponentFactory.create(clazz);
  }

  /**
   * Gets the count of items in the cart
   *
   * @return
   */
  public String getCartItemsCount() {
    logger.info("Reading GrtCArtItemsCount");
    if (!getCartItemsText.exists()) {
      return "0";
    } else {
      return getCartItemsText.getText();
    }
  }
}
