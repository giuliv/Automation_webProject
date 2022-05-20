package com.applause.auto.new_web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.*;
import com.applause.auto.web.components.AccountMenuChunk;
import java.util.List;

@Implementation(is = CoffeeBarPage.class, on = Platform.WEB)
public class CoffeeBarPage extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(css = ".page-hero h1", on = Platform.WEB)
  private Text getTitleText;

  @Locate(css = ".page-hero p", on = Platform.WEB)
  private Text getDescriptionText;

  @Locate(css = ".page-hero .btn", on = Platform.WEB)
  private Button getOrderNowButton;

  @Locate(css = ".page-hero__image img", on = Platform.WEB)
  private Image getBannerImage;

  @Locate(css = "#menuFeatured h2", on = Platform.WEB)
  private Button getFeaturedMenu;

  @Locate(css = "#menuFeaturedCarousel article.is-selected", on = Platform.WEB)
  private List<ContainerElement> getSelectedArticles;

  @Locate(css = "#menuFeaturedCarousel article.is-selected h3 a", on = Platform.WEB)
  private List<Link> getTitles;

  @Locate(
      css = "#menuFeaturedCarousel article.is-selected .pi__img-wrapper a img",
      on = Platform.WEB)
  private List<Image> getImages;

  @Locate(css = "#menuFeaturedCarousel article.is-selected .pi__desc h3", on = Platform.WEB)
  private List<Text> getDesc;

  @Locate(css = "#menuFeaturedCarousel article.is-selected .pi__quick-add a", on = Platform.WEB)
  private Button featuredOrderNowHover;

  @Locate(css = "#menuFeaturedCarousel .next", on = Platform.WEB)
  private Button getNextButton;

  @Locate(css = "#menuFeaturedCarousel .previous", on = Platform.WEB)
  private Button getPreviousButton;

  @Locate(xpath = "//h3[contains(text(),'%s')]", on = Platform.WEB)
  private Link getProduct;

  @Locate(css = ".menu-category a", on = Platform.WEB)
  private List<Button> getCategoryLinks;

  @Locate(css = ".menu-category a .menu-category__img-wrap", on = Platform.WEB)
  private List<Button> getCategoryImages;

  @Locate(css = ".menu-category a .menu-category__title", on = Platform.WEB)
  private List<Text> getCategoryTitles;

  @Locate(css = ".menu-category a .menu-category__description", on = Platform.WEB)
  private List<Text> getCategoryDescriptions;

  @Locate(css = ".menu-category a .menu-category__link-text", on = Platform.WEB)
  private List<Link> getCategoryViewAll;

  @Locate(xpath = "//a[contains(text(),'Find A Coffeebar')]", on = Platform.WEB)
  private Link getACoffeebar;

  @Locate(xpath = "//a[contains(text(),'Get The App')]", on = Platform.WEB)
  private Link getTheApp;

  @Locate(xpath = "//a[contains(text(),'Sign Up')]", on = Platform.WEB)
  private Link getSignUp;

  public AccountMenuChunk getAccountMenu() {
    logger.info("Getting Account Menu");
    return SdkHelper.create(AccountMenuChunk.class);
  }

  /* -------- Actions -------- */

  /**
   * Clicks Order now
   *
   * @return a Coffee Order Page
   */
  public OrderPage clickOrderNow() {
    logger.info("Click Order Now");
    getOrderNowButton.click();
    WebHelper.getNewTab();
    return SdkHelper.create(OrderPage.class);
  }

  /**
   * Clicks sign up
   *
   * @return a Email sign up Page
   */
  public EmailSignUpPage openSignUp() {
    logger.info(String.format("Open Sign Up page"));
    getSignUp.click();
    return SdkHelper.create(EmailSignUpPage.class);
  }

  /** @return if item is on the screen */
  public Boolean validateProduct(String product) {
    logger.info(String.format("Check if " + product + " is on the screen"));
    getProduct.format(product).initialize();
    return getProduct.exists();
  }

  /** @return section title */
  public String getFeaturedMenuTitle() {
    logger.info(String.format("Get Featured Menu Title"));
    return getFeaturedMenu.getText().toUpperCase();
  }

  /** click get a coffee */
  public StoreLocatorPage openCoffeeBanner() {
    logger.info(String.format("Open Coffee Banner"));
    getACoffeebar.click();
    WebHelper.getNewTab();
    return SdkHelper.create(StoreLocatorPage.class);
  }

  /** click get the app */
  public GetAppPage openAppBanner() {
    logger.info(String.format("Open App Banner"));
    getTheApp.click();
    WebHelper.getNewTab();
    return SdkHelper.create(GetAppPage.class);
  }

  /** @return src value of image */
  public String getFeaturedImage(int i) {
    String text = getImages.get(i).getAttributeValue("srcset");
    logger.info("href found: " + i + ":" + text);
    return text;
  }

  /** @return title */
  public String getFeaturedTitle(int index) {
    String text = getTitles.get(index).getText();
    logger.info("Title found: " + index + ":" + text);
    return text.toUpperCase();
  }

  /** @return Description */
  public String getFeaturedDescription(int index) {
    String text = getDesc.get(index).getText();
    logger.info("Description found: " + index + ":" + text);
    return text;
  }

  /** @return image href */
  public String getCategoryImage(int index) {
    String text = getCategoryImages.get(index).getAttributeValue("href");
    logger.info("href found: " + index + ":" + text);
    return text;
  }

  /** @return section title */
  public String getCategoryTitle(int index) {
    String text = getCategoryTitles.get(index).getText();
    logger.info("Title found: " + index + ":" + text);
    return text;
  }

  /** @return Description */
  public String getCategoryDescription(int i) {
    String text = getCategoryDescriptions.get(i).getText();
    logger.info("Description found: " + i + ":" + text);
    return text;
  }

  /** @return Order page */
  public OrderPage clickFeaturedItemOrderNowButton() {
    logger.info("click Featured Item Order Now Button");
    WebHelper.hoverByAction(getSelectedArticles.get(0));
    featuredOrderNowHover.click();
    WebHelper.getNewTab();
    return SdkHelper.create(OrderPage.class);
  }

  /** @return Coffee Bar Page */
  public CoffeeBarPage clickNextArrow() {
    logger.info("click Next Arrow");
    getNextButton.click();
    SdkHelper.getSyncHelper().sleep(2000); // Wait for items to scroll and css values to update
    WebHelper.getNewTab();
    return SdkHelper.create(CoffeeBarPage.class);
  }

  /** @return Coffee Bar Page */
  public CoffeeBarPage clickPreviousArrow() {
    logger.info("click Previous Arrow");
    getPreviousButton.click();
    SdkHelper.getSyncHelper().sleep(2000); // Wait for items to scroll and css values to update
    WebHelper.getNewTab();
    return SdkHelper.create(CoffeeBarPage.class);
  }

  /** @return if button is on the page */
  public Boolean getOrderNowButton() {
    logger.info("Check if Order Now Button is on the screen");
    return getOrderNowButton.exists();
  }

  /** @return page description */
  public String getCoffeeBarDescription() {
    logger.info("Getting page Description");
    return getDescriptionText.getText();
  }

  /** @return page title */
  public String getCoffeeBarTitle() {
    logger.info("Getting page Title");
    return getTitleText.getText().toUpperCase();
  }

  /** @return if banner image is on the page */
  public Boolean getCoffeeBarBanner() {
    logger.info("Check if correct image is on the screen");
    return getBannerImage.exists();
  }
}