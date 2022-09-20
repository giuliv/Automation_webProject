package com.applause.auto.new_web.views;

import com.applause.auto.common.data.enums.HomepageSubscriptionsModuleMenu;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;

import java.time.Duration;
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

  /* -- Our Coffee Revolution locators -- */
  @Locate(css = "h2.ankle-tout__heading", on = Platform.WEB)
  protected Text revolutionHeader;

  @Locate(css = "p.ankle-tout__description", on = Platform.WEB)
  protected Text revolutionDescription;

  @Locate(css = "a.ankle-tout__cta.btn.btn--primary", on = Platform.WEB)
  protected Button revolutionLearnMoreButton;

  /* -- Covid Response locators -- */
  @Locate(css = "h3.home-banner__heading", on = Platform.WEB)
  protected Text covidResponseHeader;

  @Locate(css = "p.home-banner__description", on = Platform.WEB)
  protected Text covidResponseDescription;

  @Locate(css = "a.home-banner__cta.home-banner__cta--text", on = Platform.WEB)
  protected Button covidResponseStoreHoursButton;

  @Locate(css = "a.home-banner__cta.home-banner__cta--text span.screenreader", on = Platform.WEB)
  protected Text covidResponseStoreHoursButtonScreenreader;

  @Locate(css = "a.home-banner__cta.home-banner__cta--btn.btn.btn--primary", on = Platform.WEB)
  protected Button covidResponseLearnMoreButton;

  /* -- Freshness Stamp Locators -- */

  @Locate(css = "div#iconTouts h2.icon-touts__title", on = Platform.WEB)
  protected Text freshnessStampSectionTitle;

  @Locate(css = "div#iconTouts p.icon-touts__subtitle", on = Platform.WEB)
  protected Text freshnessStampSectionDescription;

  @Locate(css = "div.icon-touts__carousel.carousel h3.icon-tout__title", on = Platform.WEB)
  @Locate(
      css =
          "div.icon-touts__carousel.carousel div.icon-tout.js-carousel-item.is-selected h3.icon-tout__title",
      on = Platform.WEB_MOBILE_PHONE)
  protected List<Text> freshnessStampTitles;

  @Locate(css = "div.icon-touts__carousel.carousel p.icon-tout__description", on = Platform.WEB)
  @Locate(
      css =
          "div.icon-touts__carousel.carousel div.icon-tout.js-carousel-item.is-selected p.icon-tout__description",
      on = Platform.WEB_MOBILE_PHONE)
  protected List<Text> freshnessStampDescriptions;

  @Locate(
      css = "div.icon-touts__carousel.carousel span.js-index-label",
      on = Platform.WEB_MOBILE_PHONE)
  protected Text freshnessStampCurrentPage;

  @Locate(
      css =
          "div.icon-touts__carousel.carousel button.flickity-button.flickity-prev-next-button.next",
      on = Platform.WEB_MOBILE_PHONE)
  protected Button freshnessStampCarouselNextButton;

  @Locate(css = "div.hero__cta-wrapper a", on = Platform.WEB)
  protected Button carouselButton;

  /*
     -- Promo Tiles --
  */
  @Locate(css = "[id*='promoTile'] a[href*='/collections/all-coffee']", on = Platform.WEB)
  protected Button shopCoffeePromoTile;

  @Locate(
      css = "[id*='promoTile'] a[href*='/collections/all-coffee'] p.menu-category__description",
      on = Platform.WEB)
  protected Text shopCoffeePromoTileDescription;

  @Locate(css = "[id*='promoTile'] a[href*='/collections/all-tea']", on = Platform.WEB)
  protected Button shopTeaPromoTile;

  @Locate(
      css = "[id*='promoTile'] a[href*='/collections/all-tea'] p.menu-category__description",
      on = Platform.WEB)
  protected Text shopTeaPromoTileDescription;

  @Locate(css = "#promoTile%s h3.menu-category__title", on = Platform.WEB)
  protected Button promoTileTitle;

  @Locate(css = "#promoTile%s p.menu-category__description", on = Platform.WEB)
  protected Button promoTileDescription;

  @Locate(css = "#promoTile%s div.menu-category__actions", on = Platform.WEB)
  protected Button promoTileActions;

  /* Best Sellers */
  @Locate(
      css = "section#homeBestSellers a[href='/collections/coffee-best-sellers']",
      on = Platform.WEB)
  @Locate(
      css = "section#homeBestSellers a[href='/collections/coffee-best-sellers'].phone-only",
      on = Platform.WEB_MOBILE_PHONE)
  protected Button showAllBestSellersButton;

  @Locate(css = "section#homeBestSellers button.flickity-button.next", on = Platform.WEB)
  protected Button bestSellersNextButton;

  @Locate(css = "section#homeBestSellers button.flickity-button.previous", on = Platform.WEB)
  protected Button bestSellersPreviousButton;

  @Locate(
      css = "section#homeBestSellers article:not([aria-hidden]) h3.pi__title",
      on = Platform.WEB)
  protected List<Button> bestSellersPositionTitle;

  @Locate(css = "section#homeBestSellers button.js-quick-add", on = Platform.WEB)
  protected Button bestSellersQuickAddButton;

  @Locate(css = "div#modalQuickAdd", on = Platform.WEB)
  protected Button quickAddModalOverlay;

  /* subscription module section */
  @Locate(
      css = "h3.timed-carousel__content-hdg.timed-carousel__content-hdg--large",
      on = Platform.WEB)
  protected Text subscriptionModuleHeader;

  @Locate(css = "p.timed-carousel__content-copy", on = Platform.WEB)
  protected Text subscriptionModuleDescription;

  @Locate(css = "button.timed-carousel__copy-btn.js-copy-btn", on = Platform.WEB)
  protected Button subscriptionModuleCopyButton;

  @Locate(css = "p.timed-carousel__slide-title", on = Platform.WEB)
  protected List<Text> subscriptionModuleSubHeader;

  @Locate(
      css = "p.timed-carousel__content-copy.timed-carousel__content-copy--slide",
      on = Platform.WEB)
  protected List<Text> subscriptionModuleSubDescription;

  @Locate(css = "a.timed-carousel__cta-text", on = Platform.WEB)
  protected List<Button> subscriptionModuleMenuLinks;

  @Locate(css = "#%s", on = Platform.WEB)
  protected ContainerElement subscriptionModuleLinkHelper;

  @Locate(id = "closeIconContainer", on = Platform.WEB)
  protected Button closeSpecialOfferButton;

  @Locate(id = "attentive_creative", on = Platform.WEB)
  protected ContainerElement specialOfferFrame;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(mainContainer).present());
    logger.info("Peet's Home URL: " + SdkHelper.getDriver().getCurrentUrl());
  }

  /* -------- Actions -------- */

  public void closeInitialBannersAndModals() {
    logger.info("Need to close sometimes modals, cookies/offers banners [First Time]");
    //    if (!WebHelper.getTestExecution().equals("local")) {

    // Todo:Commented: Seems is not needed anymore [09.09.2022]
    //    if (WebHelper.exists(closeModal, 7)) {
    //      logger.info("Close peets.com Modal");
    //      SdkHelper.getSyncHelper().wait(Until.uiElement(closeModal).clickable());
    //      closeModal.click();
    //    }

    if (!WebHelper.isDesktop() && WebHelper.exists(allowCookies, 10)) {
      logger.info("Accept Cookies");
      WebHelper.jsClick(allowCookies.getWebElement());
    }

    WebHelper.clickButtonOverIFrame(specialOfferFrame, closeSpecialOfferButton);
    SdkHelper.getDriver().navigate().refresh();
    WebHelper.clickButtonOverIFrame(newBannerIFrame, dismissBanner);
    // }
  }

  /**
   * Taps the sign in button.
   *
   * @return a Login
   */
  public SignInPage clickSignInButton() {
    logger.info("Tap on SignIn Button");
    SdkHelper.getSyncHelper().sleep(3000);
    logger.info("DEBUG DATA -> " + SdkHelper.getDriver().getPageSource());
    SdkHelper.getSyncHelper().wait(Until.uiElement(signInButton).clickable()).click();

    return SdkHelper.create(SignInPage.class);
  }

  /* -- Coffee Revolution Actions -- */
  public String getRevolutionHeader() {
    logger.info("Reading revolution header");
    SdkHelper.getSyncHelper().wait(Until.uiElement(revolutionHeader).clickable());
    logger.info("-- found " + revolutionHeader.getText().trim().toLowerCase());
    return revolutionHeader.getText().trim().toLowerCase();
  }

  public String getRevolutionDescription() {
    logger.info("Reading revolution description");
    SdkHelper.getSyncHelper().wait(Until.uiElement(revolutionDescription).clickable());
    logger.info("-- found " + revolutionDescription.getText().trim().toLowerCase());
    return revolutionDescription.getText().trim().toLowerCase();
  }

  public TimelinePage clickRevolutionLearnMoreButton() {
    logger.info("Clicking coffee revolution section's learn more button");
    SdkHelper.getSyncHelper().wait(Until.uiElement(revolutionLearnMoreButton).clickable()).click();
    return SdkHelper.create(TimelinePage.class);
  }

  /* -- Covid Response Actions -- */
  public String getCovidResponseHeader() {
    logger.info("getting covid response title");
    SdkHelper.getSyncHelper().wait(Until.uiElement(covidResponseHeader).clickable());
    logger.info("------ got " + covidResponseHeader.getText().trim());
    return covidResponseHeader.getText().trim();
  }

  public String getCovidResponseDescription() {
    logger.info("getting covid response description");
    SdkHelper.getSyncHelper().wait(Until.uiElement(covidResponseDescription).clickable());
    logger.info("------ got " + covidResponseDescription.getText().trim());
    return covidResponseDescription.getText().trim();
  }

  public String getCovidResponseStoreHoursLinkText() {
    logger.info("getting covid response store hours link text");
    SdkHelper.getSyncHelper().wait(Until.uiElement(covidResponseStoreHoursButton).clickable());
    logger.info(
        "------ got "
            + covidResponseStoreHoursButton
                .getText()
                .replace(covidResponseStoreHoursButtonScreenreader.getText(), "")
                .trim());
    return covidResponseStoreHoursButton
        .getText()
        .replace(covidResponseStoreHoursButtonScreenreader.getText(), "")
        .trim();
  }

  public CovidPage clickCovidResponseLearnMoreButton() {
    logger.info("clicking learn more button");
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(covidResponseLearnMoreButton).clickable())
        .click();
    return SdkHelper.create(CovidPage.class);
  }

  /* -- Freshness Stamp Actions -- */
  public String getFreshnessCoffeeStampTitle() {
    int coffeeIndex = 0;
    logger.info(
        "-- coffee's freshness stamp title is: "
            + freshnessStampTitles.get(coffeeIndex).getText().trim());
    return freshnessStampTitles.get(coffeeIndex).getText().trim();
  }

  public String getFreshnessCoffeeStampDescription() {
    int coffeeIndex = 0;
    logger.info(
        "-- coffee's freshness stamp description is: "
            + freshnessStampDescriptions.get(coffeeIndex).getText().trim());
    return freshnessStampDescriptions.get(coffeeIndex).getText().trim();
  }

  public String getFreshnessSealedStampTitle() {
    int sealedLocation = 1;
    logger.info(
        "-- sealed to order's freshness stamp title is: "
            + freshnessStampTitles.get(sealedLocation).getText().trim());
    return freshnessStampTitles.get(sealedLocation).getText().trim();
  }

  public String getFreshnessSealedStampDescription() {
    int sealedLocation = 1;
    logger.info(
        "-- sealed to order's freshness stamp description is: "
            + freshnessStampDescriptions.get(sealedLocation).getText().trim());
    return freshnessStampDescriptions.get(sealedLocation).getText().trim();
  }

  public String getFreshnessDeliverStampTitle() {
    int deliverLocation = 2;
    logger.info(
        "-- deliver's freshness stamp title is: "
            + freshnessStampTitles.get(deliverLocation).getText().trim());
    return freshnessStampTitles.get(deliverLocation).getText().trim();
  }

  public String getFreshnessDeliverStampDescription() {
    int deliverLocation = 2;
    logger.info(
        "-- deliver's freshness stamp description is: "
            + freshnessStampDescriptions.get(deliverLocation).getText().trim());
    return freshnessStampDescriptions.get(deliverLocation).getText().trim();
  }

  public String getFreshnessStampSectionTitle() {
    logger.info(
        "Getting Freshness Stamp Section Title: " + freshnessStampSectionTitle.getText().trim());
    return freshnessStampSectionTitle.getText().trim();
  }

  public String getFreshnessStampSectionDescription() {
    logger.info(
        "Getting freshness stamp section description: "
            + freshnessStampSectionDescription.getText().trim());
    return freshnessStampSectionDescription.getText().trim();
  }

  public ProductListPage clickCarouselButton() {
    logger.info("Clicking carousel shop now button");
    SdkHelper.getSyncHelper().wait(Until.uiElement(carouselButton).clickable()).click();
    return SdkHelper.create(ProductListPage.class);
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
    for (int i = 0; i < getBestSellersPositionLength(); i++) {
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
    logger.info(
        "-- found "
            + bestSellersPositionTitle.get(position).getText().trim()
            + " at position "
            + position);
    return bestSellersPositionTitle.get(position).getText().trim();
  }

  public int getBestSellersPositionLength() {
    logger.info(
        "found that the length of displayed best sellers is:  " + bestSellersPositionTitle.size());
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

  /*-- Subscription Module actions --*/
  public String getSubscriptionModuleHeader() {
    logger.info("Getting the subscription module header");
    SdkHelper.getSyncHelper().wait(Until.uiElement(subscriptionModuleHeader).visible());
    logger.info("---- found " + subscriptionModuleHeader.getText().trim());
    return subscriptionModuleHeader.getText().trim().toLowerCase();
  }

  public String getSubscriptionModuleDescription() {
    logger.info("Getting the subscription module description");
    SdkHelper.getSyncHelper().wait(Until.uiElement(subscriptionModuleDescription).visible());
    String text =
        subscriptionModuleDescription
            .getText()
            .replace(subscriptionModuleCopyButton.getText(), "")
            .trim();
    logger.info("---- found " + text);
    return text.toLowerCase();
  }

  public void clickSubscriptionModuleCopyButton() {
    logger.info("Clicking the copy button in the subscription module");
    SdkHelper.getSyncHelper().wait(Until.uiElement(subscriptionModuleCopyButton).clickable());
    WebHelper.scrollToElement(subscriptionModuleCopyButton.getWebElement());
    SdkHelper.getSyncHelper().sleep(1000); // Wait for action
    subscriptionModuleCopyButton.click();
  }

  public String getSubscriptionModuleCopyButtonText() {
    logger.info("Reading the copy button's text in the subscription module");
    subscriptionModuleCopyButton.initialize();
    SdkHelper.getSyncHelper().wait(Until.uiElement(subscriptionModuleCopyButton).clickable());
    logger.info("---- found " + subscriptionModuleCopyButton.getText().trim());
    return subscriptionModuleCopyButton.getText().trim().toLowerCase();
  }

  public String getSubscriptionModuleSubHeader(HomepageSubscriptionsModuleMenu menuItem) {
    logger.info("Getting the subscription module sub header at position " + menuItem.getPosition());
    SdkHelper.getSyncHelper()
        .wait(
            Until.uiElement(subscriptionModuleSubHeader.get(menuItem.getPosition()))
                .present()
                .setTimeout(Duration.ofSeconds(60)));
    subscriptionModuleSubHeader.get(menuItem.getPosition()).scrollToElement();
    logger.info("MenuItem: {}", menuItem.getHeader());
    Text element = subscriptionModuleSubHeader.get(menuItem.getPosition());
    element.initialize();
    element.scrollToElement();
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(element).visible().setTimeout(Duration.ofSeconds(60)));
    logger.info("---- found " + element.getText().trim());
    return element.getText().trim().toLowerCase();
  }

  public String getSubscriptionModuleSubDescription(HomepageSubscriptionsModuleMenu menuItem) {
    logger.info(
        "Getting the subscription module sub description at position " + menuItem.getPosition());
    SdkHelper.getSyncHelper()
        .wait(
            Until.uiElement(subscriptionModuleSubDescription.get(menuItem.getPosition()))
                .visible()
                .setTimeout(Duration.ofSeconds(60)));
    subscriptionModuleSubDescription.get(menuItem.getPosition()).scrollToElement();

    Text element = subscriptionModuleSubDescription.get(menuItem.getPosition());
    element.initialize();
    element.scrollToElement();
    SdkHelper.getSyncHelper().wait(Until.uiElement(element).visible());
    logger.info("---- found " + element.getText().trim());
    return element.getText().trim().toLowerCase();
  }

  public void clickSubscriptionModuleMenuLink(HomepageSubscriptionsModuleMenu menuItem) {
    logger.info("Clicking the link in the menu for " + menuItem.getHeader());
    Button element = subscriptionModuleMenuLinks.get(menuItem.getPosition());
    ContainerElement pageLoadHelper = subscriptionModuleLinkHelper;
    element.initialize();
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(element).clickable().setTimeout(Duration.ofSeconds(60)))
        .click();
    pageLoadHelper.format(menuItem.getID());
    pageLoadHelper.initialize();
    SdkHelper.getSyncHelper().wait(Until.uiElement(pageLoadHelper).present());
  }

  public Boolean isSubscriptionModuleSubDescriptionHidden(
      HomepageSubscriptionsModuleMenu menuItem) {
    logger.info(
        "Verifying the subscription module sub description at position "
            + menuItem.getPosition()
            + " is hidden.");
    SdkHelper.getSyncHelper().sleep(2000); // Wait for action
    Text element = subscriptionModuleSubDescription.get(menuItem.getPosition());
    element.initialize();
    SdkHelper.getSyncHelper().wait(Until.uiElement(element).notVisible());
    logger.info("---- found that element is hidden: " + !element.isDisplayed());
    return !element.isDisplayed();
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

  /* -- Freshness Stamp Actions -- */

  public void scrollFreshnessCarouseltoPage(int page) {
    logger.info("on page:  " + freshnessStampCurrentPage.getText().trim());
    int totalPages = 3;
    for (int i = 1; i <= totalPages; i++) {
      if (page != Integer.parseInt(freshnessStampCurrentPage.getText().trim())) {
        logger.info("clicking next!");
        SdkHelper.getSyncHelper()
            .wait(Until.uiElement(freshnessStampCarouselNextButton).clickable());
        WebHelper.scrollToElement(freshnessStampCarouselNextButton);
        WebHelper.jsClick(freshnessStampCarouselNextButton.getWebElement());
        SdkHelper.getSyncHelper().wait(Until.uiElement(freshnessStampTitles.get(0)).notVisible());
        freshnessStampTitles.get(0).initialize();
      }
    }
  }

  @Override
  public String getFreshnessCoffeeStampTitle() {
    int onlyItem = 0;
    int coffeePage = 1;
    logger.info("----- scrolling to the correct carousel page -----");
    scrollFreshnessCarouseltoPage(coffeePage);
    freshnessStampTitles.get(onlyItem).initialize();
    logger.info(
        "-- coffee's freshness stamp title is: "
            + freshnessStampTitles.get(onlyItem).getText().trim());
    return freshnessStampTitles.get(onlyItem).getText().trim();
  }

  @Override
  public String getFreshnessCoffeeStampDescription() {
    int onlyItem = 0;
    int coffeePage = 1;
    logger.info("----- scrolling to the correct carousel page -----");
    scrollFreshnessCarouseltoPage(coffeePage);
    freshnessStampDescriptions.get(onlyItem).initialize();
    logger.info(
        "-- coffee's freshness stamp description is: "
            + freshnessStampDescriptions.get(onlyItem).getText().trim());
    return freshnessStampDescriptions.get(onlyItem).getText().trim();
  }

  @Override
  public String getFreshnessSealedStampTitle() {
    int onlyItem = 0;
    int sealedPage = 2;
    logger.info("----- scrolling to the correct carousel page -----");
    scrollFreshnessCarouseltoPage(sealedPage);
    freshnessStampTitles.get(onlyItem).initialize();
    logger.info(
        "-- sealed to order's freshness stamp title is: "
            + freshnessStampTitles.get(onlyItem).getText().trim());
    return freshnessStampTitles.get(onlyItem).getText().trim();
  }

  @Override
  public String getFreshnessSealedStampDescription() {
    int onlyItem = 0;
    int sealedPage = 2;
    logger.info("----- scrolling to the correct carousel page -----");
    scrollFreshnessCarouseltoPage(sealedPage);
    freshnessStampDescriptions.get(onlyItem).initialize();
    logger.info(
        "-- sealed to order's freshness stamp description is: "
            + freshnessStampDescriptions.get(onlyItem).getText().trim());
    return freshnessStampDescriptions.get(onlyItem).getText().trim();
  }

  @Override
  public String getFreshnessDeliverStampTitle() {
    int onlyItem = 0;
    int deliverPage = 3;
    logger.info("----- scrolling to the correct carousel page -----");
    scrollFreshnessCarouseltoPage(deliverPage);
    freshnessStampTitles.get(onlyItem).initialize();
    logger.info(
        "-- deliver's freshness stamp title is: "
            + freshnessStampTitles.get(onlyItem).getText().trim());
    return freshnessStampTitles.get(onlyItem).getText().trim();
  }

  @Override
  public String getFreshnessDeliverStampDescription() {
    int onlyItem = 0;
    int deliverPage = 3;
    logger.info("----- scrolling to the correct carousel page -----");
    scrollFreshnessCarouseltoPage(deliverPage);
    freshnessStampDescriptions.get(onlyItem).initialize();
    logger.info(
        "-- deliver's freshness stamp description is: "
            + freshnessStampDescriptions.get(onlyItem).getText().trim());
    return freshnessStampDescriptions.get(onlyItem).getText().trim();
  }

  @Override
  public void clickSubscriptionModuleMenuLink(HomepageSubscriptionsModuleMenu menuItem) {
    logger.info("Clicking the link in the menu for " + menuItem.getHeader());
    subscriptionModuleMenuLinks.get(menuItem.getPosition()).scrollToElement();
    SdkHelper.getSyncHelper()
        .wait(
            Until.uiElement(subscriptionModuleMenuLinks.get(menuItem.getPosition()))
                .present()
                .setTimeout(Duration.ofSeconds(40)));
    Button element = subscriptionModuleMenuLinks.get(menuItem.getPosition());
    ContainerElement pageLoadHelper = subscriptionModuleLinkHelper;
    element.initialize();
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(element).visible().setTimeout(Duration.ofSeconds(40)));
    SdkHelper.getSyncHelper().wait(Until.uiElement(element).clickable());
    WebHelper.scrollToElement(element);
    WebHelper.jsClick(element.getWebElement());
    pageLoadHelper.format(menuItem.getID());
    pageLoadHelper.initialize();
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(pageLoadHelper).present().setTimeout(Duration.ofSeconds(40)));

    SdkHelper.getSyncHelper().wait(Until.uiElement(pageLoadHelper).present());
  }
}
