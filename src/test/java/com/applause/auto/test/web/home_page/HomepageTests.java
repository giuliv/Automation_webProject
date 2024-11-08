package com.applause.auto.test.web.home_page;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.Constants.MenuOptions;
import com.applause.auto.common.data.Constants.MenuSubCategories;
import com.applause.auto.common.data.Constants.TestData;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.common.data.dto.TilesDto;
import com.applause.auto.common.data.enums.CoffeeSubMenu;
import com.applause.auto.common.data.enums.HomepageFreshnessStamp;
import com.applause.auto.common.data.enums.HomepageSubscriptionsModuleMenu;
import com.applause.auto.common.data.enums.LearnSubMenu;
import com.applause.auto.common.data.enums.TeaSubMenu;
import com.applause.auto.common.data.enums.VisitUsSubMenu;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.test.web.BaseTest;
import com.applause.auto.web.components.FooterComponent;
import com.applause.auto.web.components.Header;
import com.applause.auto.web.components.NeverMissOfferComponent;
import com.applause.auto.web.components.pdp.CoffeeBarCarouselComponent;
import com.applause.auto.web.components.pdp.CoffeeBarItemComponent;
import com.applause.auto.web.helpers.WebHelper;
import com.applause.auto.web.views.BrewGuidesPage;
import com.applause.auto.web.views.CoffeeBarMenuPage;
import com.applause.auto.web.views.CoffeeRevolutionPage;
import com.applause.auto.web.views.CommitmentToCraftPage;
import com.applause.auto.web.views.CovidPage;
import com.applause.auto.web.views.CuppingRoomPage;
import com.applause.auto.web.views.CurrentOffersPage;
import com.applause.auto.web.views.FindACoffeeBarPage;
import com.applause.auto.web.views.FreeHomeDeliveryPage;
import com.applause.auto.web.views.HomePage;
import com.applause.auto.web.views.OrderDeliveryAddressPage;
import com.applause.auto.web.views.PeetnikRewardsPage;
import com.applause.auto.web.views.ProductListPage;
import com.applause.auto.web.views.SignUpPage;
import com.applause.auto.web.views.SourcingWithImpactPage;
import com.applause.auto.web.views.StoreLocatorPage;
import com.applause.auto.web.views.TimelinePage;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HomepageTests extends BaseTest {

  @Test(
      groups = {
        TestNGGroups.FRONT_END_REGRESSION,
        TestNGGroups.HOME_PAGE,
        TestNGGroups.FE_PROD_REGRESSION
      },
      description = "11107418")
  public void peetsLogoAndHeader() {
    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");
    homePage.closeInitialBannersAndModals();

    Header header = homePage.getHeader();
    header.preOpenMenu();

    logger.info("2. Verify links are present");
    logger.info("-- Coffee");
    softAssert.assertTrue(header.isCoffeeMenuItemDisplayed(), "Coffee menu item is not displayed.");

    logger.info("-- CoffeeBars");
    softAssert.assertTrue(
        header.isCoffeeBarsMenuItemDisplayed(), "CoffeeBars menu item is not displayed.");

    logger.info("-- Free Shipping");
    softAssert.assertTrue(
        header.isFreeShippingItemDisplayed(), "Free Shipping menu item is not displayed.");

    logger.info("-- Learn");
    softAssert.assertTrue(header.isLearnMenuItemDisplayed(), "Learn menu item is not displayed.");

    logger.info("-- Shop");
    softAssert.assertTrue(header.isShopMenuItemDisplayed(), "Shop menu item is not displayed.");

    logger.info("-- Offers");
    softAssert.assertTrue(header.isOffersMenuItemDisplayed(), "Offers menu item is not displayed.");

    logger.info("3. Verify logo present");
    softAssert.assertTrue(header.isLogoDisplayed(), "Logo did not display.");

    logger.info("4. Verify person icon");
    softAssert.assertTrue(header.isPersonIconDisplayed(), "Person Icon did not display.");

    logger.info("5. Verify search icon");
    softAssert.assertTrue(header.isSearchIconDisplayed(), "Search icon did not display.");

    logger.info("6. Verify location icon");
    softAssert.assertTrue(header.isLocationIconDisplayed(), "Location icon did not display.");

    logger.info("7. Verify shopping cart");
    softAssert.assertTrue(header.isCartIconDisplayed(), "Shopping Cart icon did not display");

    softAssert.assertAll();
  }

  @Test(
      groups = {
        TestNGGroups.FRONT_END_REGRESSION,
        TestNGGroups.HOME_PAGE,
        TestNGGroups.FE_PROD_REGRESSION
      },
      description = "11107419")
  public void headerOptions() {
    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");
    homePage.closeInitialBannersAndModals();

    Header header = homePage.getHeader();

    logger.info("2. Hover and Verify Coffee Menu");
    header.hoverCategoryFromMenu(Constants.MenuOptions.COFFEE);
    for (CoffeeSubMenu menuItem : CoffeeSubMenu.values()) {
      logger.info("Verifying: " + menuItem.getText());
      softAssert.assertTrue(
          header.isSubMenuItemDisplayed(menuItem.getLink()),
          menuItem.getText() + " did not display.");
    }

    logger.info("3. Hover and Verify Visit Us Menu");
    homePage = navigateToHome();
    header = homePage.getHeader();
    header.hoverCategoryFromMenu(Constants.MenuOptions.COFFEE_BARS);
    for (VisitUsSubMenu menuItem : VisitUsSubMenu.values()) {
      logger.info("Verifying: " + menuItem.getText());
      softAssert.assertTrue(
          header.isAlternativeSubMenuItemDisplayed(menuItem.getLink()),
          menuItem.getText() + " did not display.");
    }

    logger.info("4. Hover and Verify Learn Menu");
    homePage = navigateToHome();
    header = homePage.getHeader();
    header.hoverCategoryFromMenu(Constants.MenuOptions.LEARN);
    for (LearnSubMenu menuItem : LearnSubMenu.values()) {
      logger.info("Verifying: " + menuItem.getText());
      softAssert.assertTrue(
          header.isAlternativeSubMenuItemDisplayed(menuItem.getLink()),
          menuItem.getText() + " did not display.");
    }

    logger.info("5.  Click and Verify Free Home Delivery");
    homePage = navigateToHome();
    header = homePage.getHeader();
    FreeHomeDeliveryPage freeHomePage = header.clickFreeHomeDeliveryFromMenu();
    softAssert.assertTrue(
        freeHomePage.isPageHeadingDisplayed(),
        "Free Home Delivery did not bring us to the correct page");

    logger.info("6.  Click and Verify Offers");
    homePage = navigateToHome();
    header = homePage.getHeader();
    CurrentOffersPage currentOffersPage = header.clickOffersFromMenu();
    softAssert.assertTrue(
        currentOffersPage.areMainOffersDisplayed(), "Offers did not bring us to the correct page");

    logger.info("7.  Verify Free Shipping");
    homePage = navigateToHome();
    header = homePage.getHeader();
    header.hoverCategoryFromMenu(MenuOptions.FREE_HOME_DELIVERY);
    softAssert.assertFalse(
        header.isSubMenuContainerDisplayed(),
        "SubMenu container should not be displayed under Free Shipping");

    softAssert.assertAll();
  }

  @Test(
      groups = {
        TestNGGroups.FRONT_END_REGRESSION,
        TestNGGroups.HOME_PAGE,
        TestNGGroups.FE_PROD_REGRESSION
      },
      description = "11107424")
  public void freshnessStampVerificationTest() {
    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");
    homePage.closeInitialBannersAndModals();

    logger.info("2. Verify freshness stamp is available and descriptions match");
    softAssert.assertEquals(
        homePage.getFreshnessStampSectionTitle(),
        HomepageFreshnessStamp.FRESHNESS_STAMP_HEADER.getTitle(),
        "Freshness Stamp Section Title did not match, got: "
            + homePage.getFreshnessStampSectionTitle()
            + ", expected: "
            + HomepageFreshnessStamp.FRESHNESS_STAMP_HEADER.getTitle());

    softAssert.assertEquals(
        homePage.getFreshnessStampSectionDescription(),
        HomepageFreshnessStamp.FRESHNESS_STAMP_HEADER.getDescription(),
        "Freshness Stamp Section Description did not match, got: "
            + homePage.getFreshnessStampSectionDescription()
            + ", expected: "
            + HomepageFreshnessStamp.FRESHNESS_STAMP_HEADER.getDescription());

    softAssert.assertEquals(
        homePage.getFreshnessCoffeeStampTitle(),
        HomepageFreshnessStamp.COMMITTED_TO_CRAFT.getTitle(),
        "Coffee Stamp Section Title did not match, got: "
            + homePage.getFreshnessCoffeeStampTitle()
            + ", expected: "
            + HomepageFreshnessStamp.COMMITTED_TO_CRAFT.getTitle());

    softAssert.assertEquals(
        homePage.getFreshnessCoffeeStampDescription(),
        HomepageFreshnessStamp.COMMITTED_TO_CRAFT.getDescription(),
        "Coffee Stamp Section Description did not match, got: "
            + homePage.getFreshnessCoffeeStampDescription()
            + ", expected: "
            + HomepageFreshnessStamp.COMMITTED_TO_CRAFT.getDescription());

    softAssert.assertEquals(
        homePage.getFreshnessSealedStampTitle(),
        HomepageFreshnessStamp.SEALED_FOR_FRESHNESS.getTitle(),
        "Sealed For Freshness Section Title did not match, got: "
            + homePage.getFreshnessSealedStampTitle()
            + ", expected: "
            + HomepageFreshnessStamp.SEALED_FOR_FRESHNESS.getTitle());

    softAssert.assertEquals(
        homePage.getFreshnessSealedStampDescription(),
        HomepageFreshnessStamp.SEALED_FOR_FRESHNESS.getDescription(),
        "Sealed For Freshness Stamp Section Description did not match, got: "
            + homePage.getFreshnessSealedStampDescription()
            + ", expected: "
            + HomepageFreshnessStamp.SEALED_FOR_FRESHNESS.getDescription());

    softAssert.assertEquals(
        homePage.getFreshnessDeliverStampTitle(),
        HomepageFreshnessStamp.DELIVERED_TO_YOU.getTitle(),
        "Deliver to you Section Title did not match, got: "
            + homePage.getFreshnessDeliverStampTitle()
            + ", expected: "
            + HomepageFreshnessStamp.DELIVERED_TO_YOU.getTitle());

    softAssert.assertEquals(
        homePage.getFreshnessDeliverStampDescription(),
        HomepageFreshnessStamp.DELIVERED_TO_YOU.getDescription(),
        "Deliver to you Stamp Section Description did not match, got: "
            + homePage.getFreshnessDeliverStampDescription()
            + ", expected: "
            + HomepageFreshnessStamp.DELIVERED_TO_YOU.getDescription());

    softAssert.assertAll();
  }

  @Test(
      groups = {TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.HOME_PAGE},
      description = "11107420")
  public void peetsHomepageBannerAndPromoTiles() {
    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToProd();

    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");
    homePage.closeInitialBannersAndModals();

    List<TilesDto> tilesDtoInfoFromProd = homePage.getTalesInfo();

    logger.info("-- navigating back to home");
    homePage = navigateToHome();

    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");
    homePage.closeInitialBannersAndModals();

    // logger.info("3. Clicking scroll side arrow");
    // This currently is not available, so can't automate it yet.

    logger.info("2. Verifying promo tiles are displayed and Verifying promo tile text");
    for (int tile = 1; tile <= 8; tile++) {
      softAssert.assertTrue(
          homePage.isPromoTileActionsVisible(tile),
          "Promo actions at position " + tile + " was not visible.");
      softAssert.assertTrue(
          homePage.isPromoTileTitleVisible(tile),
          "Promo title at position " + tile + " was not visible.");
      softAssert.assertTrue(
          homePage.isPromoTileDescriptionVisible(tile),
          "Promo description at position " + tile + " was not visible.");
      softAssert.assertEquals(
          homePage.getPromoTileTitleText(tile).toLowerCase(),
          tilesDtoInfoFromProd.get(tile - 1).getTitle().toLowerCase(),
          "Promo title at position "
              + tile
              + " did not match expectation.  Got "
              + homePage.getPromoTileTitleText(tile).toLowerCase()
              + ", expected "
              + tilesDtoInfoFromProd.get(tile - 1).getTitle().toLowerCase());
      softAssert.assertEquals(
          homePage.getPromoTileDescriptionText(tile).toLowerCase(),
          tilesDtoInfoFromProd.get(tile - 1).getDescription().toLowerCase(),
          "Promo description at position "
              + tile
              + " did not match expectation.  Got "
              + homePage.getPromoTileDescriptionText(tile).toLowerCase()
              + ", expected "
              + tilesDtoInfoFromProd.get(tile - 1).getDescription().toLowerCase());
      softAssert.assertEquals(
          homePage.getPromoTileActionsText(tile).toLowerCase(),
          tilesDtoInfoFromProd.get(tile - 1).getAction().toLowerCase(),
          "Promo title at position "
              + tile
              + " did not match expectation.  Got "
              + homePage.getPromoTileActionsText(tile).toLowerCase().trim()
              + ", expected "
              + tilesDtoInfoFromProd.get(tile - 1).getAction().toLowerCase().trim());
    }

    // A warning to the maintainer, your most common failure might be the promo tile
    // data in the enum section changing from month to month.
    softAssert.assertAll();
  }

  @Test(
      groups = {
        TestNGGroups.FRONT_END_REGRESSION,
        TestNGGroups.HOME_PAGE,
        TestNGGroups.FE_PROD_REGRESSION
      },
      description = "11107421")
  public void homepageShopCoffeeShopTea() {
    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");
    homePage.closeInitialBannersAndModals();

    logger.info("2. Clicking shop coffee promo tile to verify link");
    ProductListPage plpPage = homePage.clickCoffeePromoTile();
    softAssert.assertTrue(
        plpPage
            .getDriver()
            .getCurrentUrl()
            .contains(Constants.HomepagePromoTileData.COFFEE_PROMO_LINK),
        "Coffee Promo tile brought us to wrong page.  Got "
            + plpPage.getDriver().getCurrentUrl()
            + ", expected: "
            + Constants.HomepagePromoTileData.COFFEE_PROMO_LINK);
    logger.info(" -- navigating back to home");
    homePage = navigateToHome();

    logger.info("3. Clicking shop tea promo tile to verify link");
    plpPage = homePage.clickTeaPromoTile();
    softAssert.assertTrue(
        plpPage
            .getDriver()
            .getCurrentUrl()
            .contains(Constants.HomepagePromoTileData.TEA_PROMO_LINK),
        "Tea promo tile brought us to wrong page.  Got "
            + plpPage.getDriver().getCurrentUrl()
            + ", expected: "
            + Constants.HomepagePromoTileData.TEA_PROMO_LINK);
    logger.info(" -- navigating back to home");
    homePage = navigateToHome();

    logger.info("4. Validate description for shop coffee section");
    softAssert.assertEquals(
        homePage.getCoffeePromoTileDescription(),
        Constants.HomepagePromoTileData.COFFEE_PROMO_DESCRIPTION,
        "Description for coffee did not match.  Got: "
            + homePage.getCoffeePromoTileDescription()
            + ", expected: "
            + Constants.HomepagePromoTileData.COFFEE_PROMO_DESCRIPTION);
    logger.info("5. Validate description for shop tea section");
    softAssert.assertEquals(
        homePage.getTeaPromoTileDescription(),
        Constants.HomepagePromoTileData.TEA_PROMO_DESCRIPTION,
        "Description for tea did not match.  Got: "
            + homePage.getTeaPromoTileDescription()
            + ", expected: "
            + Constants.HomepagePromoTileData.TEA_PROMO_DESCRIPTION);

    softAssert.assertAll();
  }

  @Test(
      groups = {
        TestNGGroups.FRONT_END_REGRESSION,
        TestNGGroups.HOME_PAGE,
        TestNGGroups.FE_PROD_REGRESSION
      },
      description = "11107428")
  public void homepageNeverMissOfferVerificationTest() {
    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");
    homePage.closeInitialBannersAndModals();

    logger.info("2. Verifying offer heading");
    NeverMissOfferComponent neverMissOfferComponent = homePage.getNeverMissOfferComponent();
    softAssert.assertEquals(
        Constants.HomepageNeverMissOffer.OFFER_TITLE.toLowerCase(),
        neverMissOfferComponent.getNeverMissOfferTitle().toLowerCase(),
        "Never Miss an offer section titles did not match; got: "
            + neverMissOfferComponent.getNeverMissOfferTitle().toLowerCase()
            + " expected: "
            + Constants.HomepageNeverMissOffer.OFFER_TITLE.toLowerCase());

    logger.info("3. Verifying offer description");
    softAssert.assertEquals(
        Constants.HomepageNeverMissOffer.OFFER_DESCRIPTION.toLowerCase(),
        neverMissOfferComponent.getNeverMissOfferDescription().toLowerCase(),
        "Never Miss an offer section descriptions did not match; got: "
            + neverMissOfferComponent.getNeverMissOfferDescription().toLowerCase()
            + " expected: "
            + Constants.HomepageNeverMissOffer.OFFER_DESCRIPTION.toLowerCase());

    logger.info("4. Verifying sign up button");
    SignUpPage suPage = neverMissOfferComponent.clickNeverMissOfferSignup();
    softAssert.assertTrue(
        suPage
            .getDriver()
            .getCurrentUrl()
            .contains(Constants.HomepageNeverMissOffer.OFFER_URL_PART),
        "URL did not match;  expected "
            + Constants.HomepageNeverMissOffer.OFFER_URL_PART
            + " to be a part of"
            + suPage.getDriver().getCurrentUrl());

    softAssert.assertAll();
  }

  @Test(
      groups = {
        TestNGGroups.FRONT_END_REGRESSION,
        TestNGGroups.HOME_PAGE,
        TestNGGroups.FE_PROD_REGRESSION
      },
      description = "11107425",
      enabled = false)
  // Feature no longer valid [08.02.2023]
  public void respondingToCovidVerificationTest() {
    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");
    homePage.closeInitialBannersAndModals();

    logger.info("2. Verifying covid heading");
    softAssert.assertEquals(
        Constants.HomepageCovidResponse.HEADER.toLowerCase(),
        homePage.getCovidResponseHeader().toLowerCase(),
        "Covid Response section titles did not match; got: "
            + homePage.getCovidResponseHeader().toLowerCase()
            + " expected: "
            + Constants.HomepageCovidResponse.HEADER.toLowerCase());

    logger.info("3. Verifying covid description");
    softAssert.assertEquals(
        Constants.HomepageCovidResponse.DESCRIPTION.toLowerCase(),
        homePage.getCovidResponseDescription().toLowerCase(),
        "Covid Response section descriptions did not match; got: "
            + homePage.getCovidResponseDescription().toLowerCase()
            + " expected: "
            + Constants.HomepageCovidResponse.DESCRIPTION.toLowerCase());

    logger.info("4. Verifying store hours link");
    softAssert.assertEquals(
        Constants.HomepageCovidResponse.STORE_HOURS_TEXT.toLowerCase(),
        homePage.getCovidResponseStoreHoursLinkText().toLowerCase(),
        "Store Hours Link Text was wrong -- Got: "
            + homePage.getCovidResponseStoreHoursLinkText().toLowerCase()
            + " expected: "
            + Constants.HomepageCovidResponse.STORE_HOURS_TEXT.toLowerCase());
    logger.info("5. Verifying learn more button");
    CovidPage cPage = homePage.clickCovidResponseLearnMoreButton();
    softAssert.assertTrue(
        WebHelper.getCurrentUrl().contains(Constants.HomepageCovidResponse.LEARN_MORE_URL_PART),
        "Covid Response Learn More page URL is incorrect - expected "
            + Constants.HomepageCovidResponse.LEARN_MORE_URL_PART
            + " to be inside "
            + WebHelper.getCurrentUrl());

    softAssert.assertAll();
  }

  @Test(
      groups = {
        TestNGGroups.FRONT_END_REGRESSION,
        TestNGGroups.HOME_PAGE,
        TestNGGroups.FE_PROD_REGRESSION
      },
      description = "11107427")
  public void ourCoffeeRevolutionVerificationTest() {
    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");
    homePage.closeInitialBannersAndModals();

    logger.info("2. Verifying revolution heading");
    softAssert.assertEquals(
        Constants.HomepageCoffeeRevolution.HEADER.toLowerCase(),
        homePage.getRevolutionHeader().toLowerCase(),
        "Our Coffee Revolution section titles did not match; got: "
            + homePage.getRevolutionHeader().toLowerCase()
            + " expected: "
            + Constants.HomepageCoffeeRevolution.HEADER.toLowerCase());

    logger.info("3. Verifying revolution description");
    softAssert.assertEquals(
        Constants.HomepageCoffeeRevolution.DESCRIPTION.toLowerCase(),
        homePage.getRevolutionDescription().toLowerCase(),
        "Our Coffee Revolution section descriptions did not match; got: "
            + homePage.getRevolutionDescription().toLowerCase()
            + " expected: "
            + Constants.HomepageCoffeeRevolution.DESCRIPTION.toLowerCase());

    logger.info("4. Verifying learn more button");
    TimelinePage timePage = homePage.clickRevolutionLearnMoreButton();
    softAssert.assertTrue(
        timePage
            .getDriver()
            .getCurrentUrl()
            .contains(Constants.HomepageCoffeeRevolution.LEARN_MORE_URL_PART),
        "Our Coffee Revolution's Learn More page URL is incorrect - expected "
            + Constants.HomepageCoffeeRevolution.LEARN_MORE_URL_PART
            + " to be inside "
            + timePage.getDriver().getCurrentUrl());

    softAssert.assertAll();
  }

  @Test(
      groups = {
        TestNGGroups.FRONT_END_REGRESSION,
        TestNGGroups.HOME_PAGE,
        TestNGGroups.FE_PROD_REGRESSION
      },
      description = "11107423")
  public void homepageSubscriptionModuleVerificationTest() {
    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");
    homePage.closeInitialBannersAndModals();

    logger.info("2. Verify section header");
    softAssert.assertEquals(
        homePage.getSubscriptionModuleHeader(),
        Constants.HomepageSubscriptionsModule.HEADER.toLowerCase(),
        "The subscription module header did not match -    Got: "
            + homePage.getSubscriptionModuleHeader()
            + " Expected: "
            + Constants.HomepageSubscriptionsModule.HEADER.toLowerCase());
    logger.info("3. Verify section description");
    softAssert.assertEquals(
        homePage.getSubscriptionModuleDescription(),
        Constants.HomepageSubscriptionsModule.DESCRIPTION.toLowerCase(),
        "The subscription module description did not match -    Got: "
            + homePage.getSubscriptionModuleDescription()
            + " Expected: "
            + Constants.HomepageSubscriptionsModule.DESCRIPTION.toLowerCase());

    logger.info("4. Verify timed subsection headers and descriptions");
    for (HomepageSubscriptionsModuleMenu menuItem : HomepageSubscriptionsModuleMenu.values()) {
      String menuHeader = homePage.getSubscriptionModuleSubHeader(menuItem);
      softAssert.assertEquals(
          menuHeader,
          menuItem.getHeader(),
          "The subscriptions module menu did not match;  Found: "
              + menuHeader
              + " expected: "
              + menuItem.getHeader());

      String menuDescription = homePage.getSubscriptionModuleSubDescription(menuItem);

      softAssert.assertEquals(
          menuDescription,
          menuItem.getDescription(),
          "The subscriptions module menu descriptions did not match;  Found: "
              + menuDescription
              + " expected: "
              + menuItem.getDescription());

      Boolean menuDescriptionHides = homePage.isSubscriptionModuleSubDescriptionHidden(menuItem);

      softAssert.assertTrue(
          menuDescriptionHides, "The descriptions do not seem to hide in the timed carousel.");
    }
    logger.info("5. Verify copy code");

    homePage.clickSubscriptionModuleCopyButton();
    softAssert.assertEquals(
        Constants.HomepageSubscriptionsModule.COPY_BUTTON_CLICKED_TEXT,
        homePage.getSubscriptionModuleCopyButtonText(),
        "Copy button's text did not change as anticipated;  got "
            + homePage.getSubscriptionModuleCopyButtonText()
            + " and expected "
            + Constants.HomepageSubscriptionsModule.COPY_BUTTON_CLICKED_TEXT);

    logger.info("6. Verify links for subsections");
    for (HomepageSubscriptionsModuleMenu menuItem : HomepageSubscriptionsModuleMenu.values()) {
      homePage = navigateToHome();
      homePage.clickSubscriptionModuleMenuLink(menuItem);
      softAssert.assertTrue(
          homePage.getDriver().getCurrentUrl().contains(menuItem.getURLPart()),
          "The link for "
              + menuItem.getHeader()
              + " does not go to the correct url - got "
              + homePage.getDriver().getCurrentUrl()
              + " and expected "
              + menuItem.getURLPart());
    }
    softAssert.assertAll();
  }

  @Test(
      groups = {
        TestNGGroups.FRONT_END_REGRESSION,
        TestNGGroups.HOME_PAGE,
        TestNGGroups.FE_PROD_REGRESSION
      },
      description = "11107426")
  public void homepageCoffeeBarVerificationTest() {
    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");
    homePage.closeInitialBannersAndModals();

    logger.info(
        "2. Verify left and right arrow scrolls the bar, and that you can get to initial position after scroll");
    CoffeeBarCarouselComponent carouselComponent = homePage.getCoffeeBarCarouselComponent();

    if ((WebHelper.isDesktop() && carouselComponent.getCoffeeBarItemComponents().size() < 4)) {
      List<String> initialPositions = carouselComponent.getVisibleCoffeeBarItemNames();
      int firstPosition = 0;
      carouselComponent.clickCoffeeBarNext();
      List<String> nextPositions = carouselComponent.getVisibleCoffeeBarItemNames();

      softAssert.assertNotEquals(
          initialPositions.get(0),
          nextPositions.get(0),
          "The coffee bar items did not change when the next arrow was clicked.");

      carouselComponent.clickCoffeeBarPrevious();
      nextPositions = carouselComponent.getVisibleCoffeeBarItemNames();

      softAssert.assertEquals(
          initialPositions.get(0),
          nextPositions.get(0),
          "The coffee bar did not go back to the original list with previous clicked.");

      if (WebHelper.isDesktop()) {
        logger.info("3. Verify hover shows Order Now");
        CoffeeBarItemComponent itemComponent =
            carouselComponent.getCoffeeBarItemComponent(firstPosition);
        itemComponent.hoverOver();
        softAssert.assertTrue(itemComponent.isOrderNowDisplayed(), "Order Now is not displayed");
      }
    }

    logger.info("4. Verify links take you places");
    carouselComponent.clickSeeAllSeasonalBeveragesButton();
    softAssert.assertTrue(
        WebHelper.getCurrentUrl().contains(Constants.HomepageCoffeeBar.SEASONAL_URL_PART),
        "The  seasonal button brought us to a wrong location.  Got "
            + WebHelper.getCurrentUrl()
            + " and we expected "
            + Constants.HomepageCoffeeBar.SEASONAL_URL_PART);

    homePage = navigateToHome();
    StoreLocatorPage storeLocatorPage =
        homePage.getCoffeeBarCarouselComponent().clickFindCoffeeBarButton();
    softAssert.assertTrue(
        WebHelper.getCurrentUrl().contains(Constants.HomepageCoffeeBar.FIND_COFFEE_BAR),
        "The find coffee bar button brought us to a wrong page, we got "
            + storeLocatorPage.getDriver().getCurrentUrl()
            + " and we expected "
            + Constants.HomepageCoffeeBar.FIND_COFFEE_BAR);

    softAssert.assertAll();
  }

  @Test(
      groups = {
        TestNGGroups.FRONT_END_REGRESSION,
        TestNGGroups.HOME_PAGE,
        TestNGGroups.FE_PROD_REGRESSION
      },
      description = "11107422")
  public void homepageBestSellers() {
    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");
    homePage.closeInitialBannersAndModals();

    ProductListPage plpPage;
    int firstPosition = 0;

    logger.info("2. Verifying show best sellers button");
    plpPage = homePage.clickShowAllBestSellersButton();
    softAssert.assertTrue(
        plpPage
            .getDriver()
            .getCurrentUrl()
            .contains(Constants.HomepageBestSellersData.BEST_SELLERS_LINK),
        "Best sellers button brought us to wrong location.  Got: "
            + plpPage.getDriver().getCurrentUrl()
            + ", expected: "
            + Constants.HomepageBestSellersData.BEST_SELLERS_LINK);

    logger.info("-- navigating back to homepage");
    homePage = navigateToHome();

    logger.info("3. Verifying scroll right shows new items");
    List<String> shownPositions = homePage.getBestSellersShownPositions();
    homePage.clickBestSellersCarouselNext();

    for (int i = 0; i < homePage.getBestSellersPositionLength(); i++) {
      softAssert.assertNotEquals(
          "", homePage.getBestSellersPositionTitle(firstPosition), "First Best seller was empty.");
      softAssert.assertNotEquals(
          shownPositions.get(i),
          homePage.getBestSellersPositionTitle(i),
          "Best Sellers did not change when next was clicked.");
    }

    logger.info("4. Verifying scroll left shows new items");
    homePage.clickBestSellersCarouselPrevious();
    for (int i = 0; i < homePage.getBestSellersPositionLength(); i++) {
      softAssert.assertEquals(
          shownPositions.get(i),
          homePage.getBestSellersPositionTitle(i),
          "Best Sellers did not go back to the original list with previous clicked.");
    }

    if (WebHelper.isDesktop()) {
      logger.info("5. Verifying hover over best seller item shows add to cart button");
      homePage.hoverBestSellersPosition(firstPosition);
      softAssert.assertTrue(
          homePage.isBestSellersQuickAddDisplayedOnHover(),
          "Quick Add is not displaying on hover.");

      logger.info("6. Verifying quick add overlay happens on quick add click");
      homePage.hoverBestSellersPosition(firstPosition);
      homePage.clickBestSellersQuickAddButton();
      softAssert.assertTrue(
          homePage.isQuickAddOverlayDisplayed(), "Quick Add Overlay is not displaying.");
    }

    softAssert.assertAll();
  }

  @Test(
      groups = {
        TestNGGroups.FRONT_END_REGRESSION,
        TestNGGroups.HOME_PAGE,
        TestNGGroups.FE_PROD_REGRESSION
      },
      description = "11107442")
  public void homepageFreeHomeDelivery() {
    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");
    homePage.closeInitialBannersAndModals();

    Header header = homePage.getHeader();

    logger.info("2. Verify the user has No Hover and only a click");
    header.hoverCategoryFromMenu(MenuOptions.FREE_HOME_DELIVERY);
    Assert.assertFalse(header.isMenuSectionExpanded(), "Menu section is unexpected expanded");

    logger.info("3. Click on Free Shipping");
    FreeHomeDeliveryPage freeHomePage =
        navigateToHome().getHeader().clickFreeHomeDeliveryFromMenu();
    Assert.assertTrue(
        freeHomePage.isPageHeadingDisplayed(),
        "Free Shipping did not bring us to the correct page");
  }

  @Test(
      groups = {
        TestNGGroups.FRONT_END_REGRESSION,
        TestNGGroups.HOME_PAGE,
        TestNGGroups.FE_PROD_REGRESSION
      },
      description = "11107443")
  public void homepageVisitUs() {
    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");
    homePage.closeInitialBannersAndModals();

    Header header = homePage.getHeader();
    header.clickBackButton();
    logger.info("8. Click on Order From a Coffee Bar");
    header.hoverCategoryFromMenu(MenuOptions.COFFEE_BARS);
    header.clickOverSubCategoryFromMenu(
        OrderDeliveryAddressPage.class, MenuSubCategories.ORDER_FROM_A_COFFEE_BAR);

    logger.info("9. Verify page is opened with expected page");
    softAssert.assertTrue(
        SdkHelper.getDriver().getCurrentUrl().contains(TestData.ORDER_PEETS_URL),
        "Order delivery address page is opened with a wrong URL");

    header = WebHelper.navigateBack(HomePage.class).getHeader();
    logger.info("2. Click on Find a CoffeeBar");
    header.clickBackButton();
    header.closeHamburgerMenu();
    header.hoverCategoryFromMenu(MenuOptions.COFFEE_BARS);
    FindACoffeeBarPage findACoffeeBarPage =
        header.clickOverSubCategoryFromMenu(
            FindACoffeeBarPage.class, MenuSubCategories.FIND_COFFEEBAR);

    logger.info("3. Verify the user is directed to correct Find a coffee bar");
    softAssert.assertNotNull(
        findACoffeeBarPage, "The user isn't directed to correct Find a coffeeBar");

    header = findACoffeeBarPage.getHeader();
    logger.info("4. Click on View Menu");
    header.clickBackButton();
    header.hoverCategoryFromMenu(MenuOptions.COFFEE_BARS);
    CoffeeBarMenuPage coffeeBarMenuPage =
        header.clickOverSubCategoryFromMenu(CoffeeBarMenuPage.class, MenuSubCategories.VIEW_MENU);

    logger.info("5. Verify the user is directed to correct CoffeeBar Menu");
    softAssert.assertNotNull(
        coffeeBarMenuPage, "The user isn't directed to correct CoffeeBar Menu");

    header = coffeeBarMenuPage.getHeader();
    logger.info("6. Click on Peetnik Rewards");
    header.clickBackButton();
    header.hoverCategoryFromMenu(MenuOptions.COFFEE_BARS);
    PeetnikRewardsPage peetnikRewardsPage =
        header.clickOverSubCategoryFromMenu(
            PeetnikRewardsPage.class, MenuSubCategories.PEETNIK_REWARDS);

    logger.info("7. Verify the user is directed to correct Peetnik Rewards App");
    softAssert.assertNotNull(
        peetnikRewardsPage, "The user isn't directed to correct Peetnik Rewards App");

    softAssert.assertAll();
  }

  @Test(
      groups = {
        TestNGGroups.FRONT_END_REGRESSION,
        TestNGGroups.HOME_PAGE,
        TestNGGroups.FE_PROD_REGRESSION
      },
      description = "11107444")
  public void homepageLearn() {
    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");
    homePage.closeInitialBannersAndModals();

    Header header = homePage.getHeader();

    logger.info("2. Click on Our Coffee Resolution");
    header.hoverCategoryFromMenu(MenuOptions.LEARN);
    CoffeeRevolutionPage coffeeRevolutionPage =
        header.clickOverSubCategoryFromMenu(
            CoffeeRevolutionPage.class, MenuSubCategories.OUR_COFFEE_REVOLUTION);

    logger.info("3. Verify the user is directed to correct Our Coffee Resolution");
    softAssert.assertNotNull(
        coffeeRevolutionPage, "The user isn't directed to correct Our Coffee Resolution");

    logger.info("4. Click on Commitment to Craft");
    header = navigateToHome().getHeader();
    header.hoverCategoryFromMenu(MenuOptions.LEARN);
    CommitmentToCraftPage commitmentToCraftPage =
        header.clickOverSubCategoryFromMenu(
            CommitmentToCraftPage.class, MenuSubCategories.COMMITMENT_TO_CRAFT);

    logger.info("5. Verify the user is directed to correct Commitment to Craft");
    softAssert.assertNotNull(
        commitmentToCraftPage, "The user isn't directed to correct Commitment to Craft");

    logger.info("6. Click on Sourcing with Impact");
    header = navigateToHome().getHeader();
    header.hoverCategoryFromMenu(MenuOptions.LEARN);
    SourcingWithImpactPage sourcingWithImpactPage =
        header.clickOverSubCategoryFromMenu(
            SourcingWithImpactPage.class, MenuSubCategories.SOURCING_WITH_IMPACT);

    logger.info("7. Verify the user is directed to correct Sourcing with Impact");
    softAssert.assertNotNull(
        sourcingWithImpactPage, "The user isn't directed to correct Sourcing with Impact");

    logger.info("8. Click on Brew Guides");
    header = navigateToHome().getHeader();
    header.hoverCategoryFromMenu(MenuOptions.LEARN);
    BrewGuidesPage brewGuidesPage =
        header.clickOverSubCategoryFromMenu(BrewGuidesPage.class, MenuSubCategories.BREW_GUIDES);

    logger.info("9. Verify the user is directed to correct Brew Guides");
    softAssert.assertNotNull(brewGuidesPage, "The user isn't directed to correct Brew Guides");

    logger.info("10. Click on The Cupping Room");
    header = navigateToHome().getHeader();
    header.hoverCategoryFromMenu(MenuOptions.LEARN);
    header.clickOverSubCategoryFromMenu(CuppingRoomPage.class, MenuSubCategories.THE_CUPPING_ROOM);

    logger.info("11. Verify page is opened with expected page");
    Assert.assertTrue(
        SdkHelper.getDriver().getCurrentUrl().contains(TestData.BLOG_PEETS_URL_PARAMETER),
        "The Cupping Room page is opened with a wrong URL");
    softAssert.assertAll();
  }

  @Test(
      groups = {
        TestNGGroups.FRONT_END_REGRESSION,
        TestNGGroups.HOME_PAGE,
        TestNGGroups.FE_PROD_REGRESSION
      },
      description = "11107445",
      enabled = false)
  // Todo: Marked as disabled by Tony, seems feature removed, double check with B
  public void homepagePeetnikRewards() {
    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");
    homePage.closeInitialBannersAndModals();

    Header header = homePage.getHeader();

    logger.info("2. Verify the user has No Hover and only a click");
    header.hoverCategoryFromMenu(MenuOptions.PEETNIK_REWARDS);
    Assert.assertFalse(header.isMenuSectionExpanded(), "Menu section is unexpected expanded");

    logger.info("3. Click on Peetnik Rewards");
    PeetnikRewardsPage peetnikRewardsPage =
        navigateToHome().getHeader().clickPeetnikRewardsFromMenu();
    Assert.assertTrue(
        peetnikRewardsPage.isPageHeadingDisplayed(),
        "Peetnik Rewards did not bring us to the correct page");
  }

  @Test(
      groups = {
        TestNGGroups.FRONT_END_REGRESSION,
        TestNGGroups.HOME_PAGE,
        TestNGGroups.FE_PROD_REGRESSION
      },
      description = "11107446")
  public void homepageOffers() {
    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");
    homePage.closeInitialBannersAndModals();

    Header header = homePage.getHeader();

    logger.info("2. Verify the user has No Hover and only a click");
    header.hoverCategoryFromMenu(MenuOptions.OFFERS);
    Assert.assertFalse(header.isMenuSectionExpanded(), "Menu section is unexpected expanded");

    logger.info("3. Click on Offers");
    CurrentOffersPage currentOffersPage = navigateToHome().getHeader().clickOffersFromMenu();
    Assert.assertTrue(
        currentOffersPage.areMainOffersDisplayed(), "Offers did not bring us to the correct page");
  }

  @Test(
      groups = {
        TestNGGroups.FRONT_END_REGRESSION,
        TestNGGroups.HOME_PAGE,
        TestNGGroups.FE_PROD_REGRESSION
      },
      description = "11107439")
  public void homepageEndOfPageDescriptionTest() {
    logger.info("1. Navigate to Home page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the Home page.");
    homePage.closeInitialBannersAndModals();

    logger.info("Verify End of the page Description");
    FooterComponent footer = homePage.getFooterComponent();

    Assert.assertEquals(
        footer.getEndOfThePageDescription(),
        Constants.TestData.END_OF_PAGE_DESCRIPTION,
        "End of the page Description Mismatches");
  }

  @Test(
      groups = {
        TestNGGroups.FRONT_END_REGRESSION,
        TestNGGroups.HOME_PAGE,
        TestNGGroups.FE_PROD_REGRESSION
      },
      description = "11107440")
  public void hoverCoffeeOptions() {
    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");
    homePage.closeInitialBannersAndModals();

    Header header = homePage.getHeader();

    logger.info("2. Hover and Verify Coffee Menu and All SubMenu Items");
    header.hoverCategoryFromMenu(Constants.MenuOptions.COFFEE);
    for (CoffeeSubMenu menuItem : CoffeeSubMenu.values()) {
      logger.info("Verifying: " + menuItem.getText());
      softAssert.assertTrue(
          header.isSubMenuItemDisplayed(menuItem.getLink()),
          menuItem.getText() + " did not display.");
    }

    // Todo: Online Exclusives is not displayed on the
    logger.info(
        "3. Hover and Click Coffee Hover Each SubMenu Item and Verify it is redirected to the Corresponding Item page.");
    for (CoffeeSubMenu menuItem : CoffeeSubMenu.values()) {
      logger.info("Click MenuItem: " + menuItem.getText());
      if (menuItem.getText().contains(TestData.ALL_COFFEE_HOVER)) {
        header.clickSubMenuItemAllLinks(menuItem.getLink());
      } else {
        header.clickSubMenuItem(menuItem.getLink());
      }
      softAssert.assertTrue(
          WebHelper.getCurrentUrl().contains(menuItem.getLink()),
          "New Page is not redirected: " + WebHelper.getCurrentUrl());
      homePage = navigateToHome();
      header = homePage.getHeader();
      header.hoverCategoryFromMenu(Constants.MenuOptions.COFFEE);
    }

    // Todo: Find your Match is displaying for only in Desktop
    if (WebHelper.isDesktop()) {
      logger.info(
          "4. Hover and Click Coffee Hover Find Your Match and Verify it is redirected to the Corresponding Item page.");
      homePage = navigateToHome();
      header = homePage.getHeader();
      header.hoverCategoryFromMenu(Constants.MenuOptions.COFFEE);
      header.clickCoffeeFindYourMatch(TestData.COFFEE_HOVER_FINDER);
      softAssert.assertTrue(
          WebHelper.getCurrentUrl().contains(TestData.COFFEE_HOVER_FINDER),
          "New Page is not redirected: " + WebHelper.getCurrentUrl());
    }
    softAssert.assertAll();
  }

  @Test(
      groups = {
        TestNGGroups.FRONT_END_REGRESSION,
        TestNGGroups.HOME_PAGE,
        TestNGGroups.FE_PROD_REGRESSION
      },
      description = "11107441",
      enabled = false)
  public void hoverTeaOptions() {
    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");
    homePage.closeInitialBannersAndModals();

    Header header = homePage.getHeader();

    header.hoverCategoryFromMenu(Constants.MenuOptions.TEA);
    for (TeaSubMenu menuItem : TeaSubMenu.values()) {
      logger.info("Verifying: " + menuItem.getText());
      softAssert.assertTrue(
          header.isSubMenuItemDisplayed(menuItem.getLink()),
          menuItem.getText() + " did not display.");
    }
    // Todo: Gift Sets,Gifts,Equipment,Online Exclusives are not displayed on the
    logger.info(
        "3. Hover and Click Tead Menu Each SubMenu Items and Verify it is redirected to the Corresponding Item page.");
    for (TeaSubMenu menuItem : TeaSubMenu.values()) {
      logger.info("Click MenuItem: " + menuItem.getText());
      if (menuItem.getText().contains(TestData.ALL_TEA_HOVER)) {
        header.clickSubMenuItemAllLinks(menuItem.getLink());
      } else {
        header.clickSubMenuItem(menuItem.getLink());
      }
      softAssert.assertTrue(
          WebHelper.getCurrentUrl().contains(menuItem.getLink()),
          "New Page is not redirected: " + WebHelper.getCurrentUrl());
      homePage = navigateToHome();
      header = homePage.getHeader();
      header.hoverCategoryFromMenu(Constants.MenuOptions.TEA);
    }

    logger.info(
        "4. Hover and Click Tea Hover Find Your Tea Match and Verify it is redirected to the Corresponding Item page.");
    header.clickTeaSubMenuTeaMatchAndMightyLeafLinks(TestData.TEA_HOVER_FINDER);
    softAssert.assertTrue(
        WebHelper.getCurrentUrl().contains(TestData.TEA_HOVER_FINDER),
        "New Page is not redirected: " + WebHelper.getCurrentUrl());

    logger.info(
        "5. Hover and Click Tea Hover Mighty Leaf and Verify it is redirected to the Corresponding Item page.");
    homePage = navigateToHome();
    header = homePage.getHeader();
    header.hoverCategoryFromMenu(Constants.MenuOptions.TEA);
    header.clickTeaSubMenuTeaMatchAndMightyLeafLinks(TestData.TEA_HOVER_MIGHTY_LEAF);

    softAssert.assertTrue(
        WebHelper.getCurrentUrl().contains(TestData.TEA_HOVER_MIGHTY_LEAF),
        "New Page is not redirected: " + WebHelper.getCurrentUrl());

    softAssert.assertAll();
  }
}
