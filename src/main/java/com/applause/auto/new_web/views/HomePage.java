package com.applause.auto.new_web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;

import java.util.ArrayList;
import java.util.List;

@Implementation(is = HomePage.class, on = Platform.WEB)
@Implementation(is = HomePageMobile.class, on = Platform.WEB_MOBILE_PHONE)
public class HomePage extends Base {

  @Locate(id = "searchOverlay", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Locate(css = ".cookieconsent-wrapper .cc-allow", on = Platform.WEB)
  private Button allowCookies;

  @Locate(css = "button.launch-modal__close, #closeIconContainer", on = Platform.WEB)
  private Button closeModal;

  @Locate(css = "#header a[href*='/login']", on = Platform.WEB)
  @Locate(css = "#navMobileMain a[href*='/login']", on = Platform.WEB_MOBILE_PHONE)
  protected Button signInButton;

  @Locate(css = "div.hero__cta-wrapper a", on = Platform.WEB)
  protected Button carouselButton;

  /*
      -- Promo Tiles --
   */
  @Locate(css = "[id*='promoTile'] a[href*='/collections/all-coffee']", on = Platform.WEB)
  protected Button shopCoffeePromoTile;

  @Locate(css = "[id*='promoTile'] a[href*='/collections/all-coffee'] p.menu-category__description",
          on = Platform.WEB)
  protected Text shopCoffeePromoTileDescription;

  @Locate(css = "[id*='promoTile'] a[href*='/collections/all-tea']", on = Platform.WEB)
  protected Button shopTeaPromoTile;

  @Locate(css = "[id*='promoTile'] a[href*='/collections/all-tea'] p.menu-category__description",
          on = Platform.WEB)
  protected Text shopTeaPromoTileDescription;

  @Locate(css = "#promoTile%s h3.menu-category__title", on = Platform.WEB)
  protected Button promoTileTitle;

  @Locate(css = "#promoTile%s p.menu-category__description", on = Platform.WEB)
  protected Button promoTileDescription;

  @Locate(css = "#promoTile%s div.menu-category__actions", on = Platform.WEB)
  protected Button promoTileActions;

  /* Best Sellers */
  @Locate(css = "section#homeBestSellers a[href='/collections/coffee-best-sellers']", on = Platform.WEB)
  @Locate(css = "section#homeBestSellers a[href='/collections/coffee-best-sellers'].phone-only", on = Platform.WEB_MOBILE_PHONE)
  protected Button showAllBestSellersButton;

  @Locate(css = "section#homeBestSellers button.flickity-button.next", on = Platform.WEB)
  protected Button bestSellersNextButton;

  @Locate(css = "section#homeBestSellers button.flickity-button.previous", on = Platform.WEB)
  protected Button bestSellersPreviousButton;

  @Locate(css = "section#homeBestSellers article:not([aria-hidden]) h3.pi__title", on = Platform.WEB)
  protected List<Button> bestSellersPositionTitle;

  @Locate(css = "section#homeBestSellers button.js-quick-add", on = Platform.WEB)
  protected Button bestSellersQuickAddButton;

  @Locate(css = "div#modalQuickAdd", on = Platform.WEB)
  protected Button quickAddModalOverlay;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(mainContainer).present());
    logger.info("Peet's Home URL: " + SdkHelper.getDriver().getCurrentUrl());

    if (!WebHelper.getTestExecution().equals("local")) {
      if (closeModal.exists()) {
        logger.info("Close peets.com Modal");
        SdkHelper.getSyncHelper().wait(Until.uiElement(closeModal).clickable());
        closeModal.click();
      }

      if (!WebHelper.isDesktop() && allowCookies.exists()) {
        logger.info("Accept Cookies");
        WebHelper.jsClick(allowCookies.getWebElement());
      }
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

  public ProductDetailsPage clickCarouselButton() {
    logger.info("Clicking carousel shop now button");
    SdkHelper.getSyncHelper().wait(Until.uiElement(carouselButton).clickable()).click();
    return SdkHelper.create(ProductDetailsPage.class);
  }

  public ProductListPage clickShowAllBestSellersButton() {
    logger.info("Clicking to show all best sellers");
    SdkHelper.getSyncHelper().wait(Until.uiElement(showAllBestSellersButton).clickable()).click();
    return SdkHelper.create(ProductListPage.class);
  }

  /* -- Best Sellers actions -- */
  public List<String> getBestSellersShownPositions() {
    logger.info("Getting shown best seller positions");
    List<String> shownPositions = new ArrayList<>();
    for (int i = 0; i < getBestSellersPositionLength(); i++ ) {
      shownPositions.add(getBestSellersPositionTitle(i));
    }
    return shownPositions;
  }
  public void clickBestSellersCarouselNext() {
    logger.info("clicking next in best sellers carousel");
    SdkHelper.getSyncHelper().wait(Until.uiElement(bestSellersNextButton).clickable()).click();
    SdkHelper.getSyncHelper().wait(Until.uiElement(bestSellersPositionTitle.get(0)).notVisible());
  }

  public void clickBestSellersCarouselPrevious() {
    logger.info("clicking previous in best sellers carousel");
    SdkHelper.getSyncHelper().wait(Until.uiElement(bestSellersPreviousButton).clickable()).click();
    SdkHelper.getSyncHelper().wait(Until.uiElement(bestSellersPositionTitle.get(0)).notVisible());
  }

  public String getBestSellersPositionTitle(int position) {
    logger.info("getting " + position + " position's title in best sellers carousel");
    bestSellersPositionTitle.get(position).initialize();
    logger.info("-- found " + bestSellersPositionTitle.get(position).getText().trim() + " at position " + position);
    return bestSellersPositionTitle.get(position).getText().trim();
  }

  public int getBestSellersPositionLength() {
    logger.info("found that the length of displayed best sellers is:  " + bestSellersPositionTitle.size());
    return bestSellersPositionTitle.size();
  }

  public void hoverBestSellersPosition(int position) {
    logger.info("hovering over item at position " + position);
    bestSellersPositionTitle.get(position).initialize();
    WebHelper.hoverByAction(bestSellersPositionTitle.get(position));
  }

  public void clickBestSellersQuickAddButton() {
    logger.info("-- clicking the quick add button on the best seller");
    SdkHelper.getSyncHelper().wait(Until.uiElement(bestSellersQuickAddButton).clickable()).click();
  }

  public Boolean isBestSellersQuickAddDisplayedOnHover() {
    logger.info("-- determining if the quick add button is being displayed");
    SdkHelper.getSyncHelper().wait(Until.uiElement(bestSellersQuickAddButton).clickable());
    logger.info("---- it is displayed:" + bestSellersQuickAddButton.isDisplayed());
    return bestSellersQuickAddButton.isDisplayed();
  }

  public Boolean isQuickAddOverlayDisplayed() {
    logger.info("-- determining if the quickadd overlay is being displayed");
    SdkHelper.getSyncHelper().wait(Until.uiElement(quickAddModalOverlay).clickable());
    logger.info("---- it is displayed:" + quickAddModalOverlay.isDisplayed());
    return quickAddModalOverlay.isDisplayed();
  }

  /*  -- promo tile actions -- */
  public ProductListPage clickCoffeePromoTile() {
    logger.info("Clicking shop coffee promo tile");
    SdkHelper.getSyncHelper().wait(Until.uiElement(shopCoffeePromoTile).clickable()).click();
    return SdkHelper.create(ProductListPage.class);
  }

  public ProductListPage clickTeaPromoTile() {
    logger.info("Clicking shop tea promo tile");
    SdkHelper.getSyncHelper().wait(Until.uiElement(shopTeaPromoTile).clickable()).click();
    return SdkHelper.create(ProductListPage.class);
  }

  public String getCoffeePromoTileDescription() {
    logger.info("Reading Coffee Promo Tile's Description");
    SdkHelper.getSyncHelper().wait(Until.uiElement(shopCoffeePromoTileDescription).visible());
    logger.info("-- found " + shopCoffeePromoTileDescription.getText().trim());
    return shopCoffeePromoTileDescription.getText().trim();
  }

  public String getTeaPromoTileDescription() {
    logger.info("Reading Tea Promo Tile's Description");
    SdkHelper.getSyncHelper().wait(Until.uiElement(shopTeaPromoTileDescription).visible());
    logger.info("-- found " + shopTeaPromoTileDescription.getText().trim());
    return shopTeaPromoTileDescription.getText().trim();
  }

  public Boolean isPromoTileTitleVisible(int tile) {
    logger.info("Checking the " + tile + " number promo tile title is displayed.");
    Button element = promoTileTitle;
    element.format(tile).initialize();
    SdkHelper.getSyncHelper().wait(Until.uiElement(element).visible());
    return element.isDisplayed();
  }

  public Boolean isPromoTileDescriptionVisible(int tile) {
    logger.info("Checking the " + tile + " number promo tile description is displayed.");
    Button element = promoTileDescription;
    element.format(tile).initialize();
    SdkHelper.getSyncHelper().wait(Until.uiElement(element).visible());
    return element.isDisplayed();
  }

  public Boolean isPromoTileActionsVisible(int tile) {
    logger.info("Checking the " + tile + " number promo tile actions is displayed.");
    Button element = promoTileActions;
    element.format(tile).initialize();
    SdkHelper.getSyncHelper().wait(Until.uiElement(element).visible());
    return element.isDisplayed();
  }

  public String getPromoTileTitleText(int tile) {
    logger.info("Getting the " + tile + " number promo tile's title text.");
    Button element = promoTileTitle;
    element.format(tile).initialize();
    SdkHelper.getSyncHelper().wait(Until.uiElement(element).visible());
    logger.info("---- found " + element.getText().trim());
    return element.getText().trim();
  }

  public String getPromoTileDescriptionText(int tile) {
    logger.info("Getting the " + tile + " number promo tile's description text.");
    Button element = promoTileDescription;
    element.format(tile).initialize();
    SdkHelper.getSyncHelper().wait(Until.uiElement(element).visible());
    logger.info("---- found " + element.getText().trim());
    return element.getText().trim();
  }

  public String getPromoTileActionsText(int tile) {
    logger.info("Getting the " + tile + " number promo tile's actions text.");
    Button element = promoTileActions;
    element.format(tile).initialize();
    SdkHelper.getSyncHelper().wait(Until.uiElement(element).visible());
    logger.info("---- found " + element.getText().trim());
    return element.getText().trim();
  }
}

class HomePageMobile extends HomePage {

  /**
   * Taps the sign in button.
   *
   * @return a Login
   */
  public SignInPage clickSignInButton() {
    getHeader().openHamburgerMenu();
    logger.info("Tap on SignIn Button");
    SdkHelper.getSyncHelper().wait(Until.uiElement(signInButton).clickable()).click();

    return SdkHelper.create(SignInPage.class);
  }

  @Override
  public void clickBestSellersCarouselNext() {
    logger.info("clicking next in best sellers carousel");
    SdkHelper.getSyncHelper().wait(Until.uiElement(bestSellersNextButton).clickable());
    WebHelper.scrollToElement(bestSellersNextButton);
    WebHelper.jsClick(bestSellersNextButton.getWebElement());
  }

  @Override
  public void clickBestSellersCarouselPrevious() {
    logger.info("clicking previous in best sellers carousel");
    SdkHelper.getSyncHelper().wait(Until.uiElement(bestSellersPreviousButton).clickable()).click();
  }
}
