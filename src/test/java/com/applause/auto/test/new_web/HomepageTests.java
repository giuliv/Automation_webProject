package com.applause.auto.test.new_web;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.common.data.enums.*;
import com.applause.auto.new_web.components.Header;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.new_web.views.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class HomepageTests extends BaseTest {

    @Test(
            groups = {TestNGGroups.WEB_REGRESSION},
            description = "11107418")
    public void peetsLogoandHeader() {
        logger.info("1. Navigate to landing page");
        HomePage homePage = navigateToHome();
        Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");
        Header header = homePage.getHeader();
        header.preopenMenu();

        logger.info("2. Verify links are present");
        logger.info("-- Coffee");
        softAssert.assertTrue(
                header.isCoffeeMenuItemDisplayed(),
                "Coffee menu item is not displayed.");
        logger.info("-- Tea");
        softAssert.assertTrue(
                header.isTeaMenuItemDisplayed(),
                "Tea menu item is not displayed.");
        logger.info("-- Visit Us");
        softAssert.assertTrue(
                header.isVisitUsMenuItemDisplayed(),
                "Visit Us menu item is not displayed.");
        logger.info("-- Free Home Delivery");
        softAssert.assertTrue(
                header.isFreeHomeDeliveryMenuItemDisplayed(),
                "Free Home Delivery menu item is not displayed.");
        logger.info("-- Learn");
        softAssert.assertTrue(
                header.isLearnMenuItemDisplayed(),
                "Learn menu item is not displayed.");
        logger.info("-- Offers");
        softAssert.assertTrue(
                header.isOffersMenuItemDisplayed(),
                "Offers menu item is not displayed.");
        logger.info("-- Peetnik Rewards");
        softAssert.assertTrue(
                header.isPeetnikRewardsMenuItemDisplayed(),
                "Peetnik Rewards menu item is not displayed.");

        logger.info("3. Verify logo present");
        softAssert.assertTrue(
                header.isLogoDisplayed(),
                "Logo did not display.");

        logger.info("4. Verify person icon");
        softAssert.assertTrue(
                header.isPersonIconDisplayed(),
                "Person Icon did not display.");

        logger.info("5. Verify search icon");
        softAssert.assertTrue(
                header.isSearchIconDisplayed(),
                "Search icon did not display.");

        logger.info("6. Verify location icon");
        softAssert.assertTrue(
                header.isLocationIconDisplayed(),
                "Location icon did not display.");

        logger.info("7. Verify shopping cart");
        softAssert.assertTrue(
                header.isCartIconDisplayed(),
                "Shopping Cart icon did not display");

        softAssert.assertAll();
    }

    @Test(
            groups = {TestNGGroups.WEB_REGRESSION},
            description = "11107419")
    public void headerOptions() {
        logger.info("1. Navigate to landing page");
        HomePage homePage = navigateToHome();
        Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");
        Header header = homePage.getHeader();

        logger.info("2. Hover and Verify Coffee Menu");
        header.hoverCategoryFromMenu(Constants.MenuOptions.COFFEE);
        for (CoffeeSubMenu menuItem : CoffeeSubMenu.values() ) {
            logger.info("Verifying: " + menuItem.getText());
            softAssert.assertTrue(
                    header.isSubMenuItemDisplayed(menuItem.getLink()),
                    menuItem.getText() + " did not display.");
        }

        logger.info("3. Hover and Verify Tea Menu");
        homePage = navigateToHome();
        header = homePage.getHeader();
        header.hoverCategoryFromMenu(Constants.MenuOptions.TEA);
        for (TeaSubMenu menuItem : TeaSubMenu.values() ) {
            logger.info("Verifying: " + menuItem.getText());
            softAssert.assertTrue(
                    header.isSubMenuItemDisplayed(menuItem.getLink()),
                    menuItem.getText() + " did not display.");
        }

        logger.info("4. Hover and Verify Visit Us Menu");
        homePage = navigateToHome();
        header = homePage.getHeader();
        header.hoverCategoryFromMenu(Constants.MenuOptions.VISIT_US);
        for (VisitUsSubMenu menuItem : VisitUsSubMenu.values() ) {
            logger.info("Verifying: " + menuItem.getText());
            softAssert.assertTrue(
                    header.isAlternativeSubMenuItemDisplayed(menuItem.getLink()),
                    menuItem.getText() + " did not display.");
        }

        logger.info("5. Hover and Verify Learn Menu");
        homePage = navigateToHome();
        header = homePage.getHeader();
        header.hoverCategoryFromMenu(Constants.MenuOptions.LEARN);
        for (LearnSubMenu menuItem : LearnSubMenu.values() ) {
            logger.info("Verifying: " + menuItem.getText());
            softAssert.assertTrue(
                    header.isAlternativeSubMenuItemDisplayed(menuItem.getLink()),
                    menuItem.getText() + " did not display.");
        }

        logger.info("6.  Click and Verify Free Home Delivery");
        homePage = navigateToHome();
        header = homePage.getHeader();
        FreeHomeDeliveryPage freeHomePage = header.clickFreeHomeDeliveryFromMenu();
        softAssert.assertTrue(
                freeHomePage.isPageHeadingDisplayed(),
                "Free Home Delivery did not bring us to the correct page");

        logger.info("7.  Click and Verify Peetnik Rewards");
        homePage = navigateToHome();
        header = homePage.getHeader();
        PeetnikRewardsPage peetnikRewardsPage = header.clickPeetnikRewardsFromMenu();
        softAssert.assertTrue(
                peetnikRewardsPage.isPageHeadingDisplayed(),
                "Peetnik Rewards did not bring us to the correct page");

        logger.info("8.  Click and Verify Offers");
        homePage = navigateToHome();
        header = homePage.getHeader();
        CurrentOffersPage currentOffersPage = header.clickOffersFromMenu();
        softAssert.assertTrue(
                currentOffersPage.isPageHeadingDisplayed(),
                "Offers did not bring us to the correct page");

        softAssert.assertAll();
    }

    @Test(
            groups = {TestNGGroups.WEB_REGRESSION},
            description = "11107420")
    public void peetsHomepageBannerandPromoTiles() {
        logger.info("1. Navigate to landing page");
        HomePage homePage = navigateToHome();
        Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");

        logger.info("2. Clicking Carousel Link");
        ProductDetailsPage plpPage = homePage.clickCarouselButton();
        softAssert.assertTrue(
                plpPage.getDriver().getCurrentUrl().contains(Constants.HomepageCarouselData.CAROUSEL_LINK),
                "Carousel button brought us to a page that does not match the location.  Got: " + plpPage.getDriver().getCurrentUrl() + ", expected: " + Constants.HomepageCarouselData.CAROUSEL_LINK);

        logger.info("-- navigating back to home");
        homePage = navigateToHome();

        //logger.info("3. Clicking scroll side arrow");
        // This currently is not available, so can't automate it yet.

        logger.info("4. Verifying promo tiles are displayed and Verifying promo tile text");
        for (int tile = 1; tile <= 8; tile ++) {
            softAssert.assertTrue(homePage.isPromoTileActionsVisible(tile), "Promo actions at position " + tile + " was not visible.");
            softAssert.assertTrue(homePage.isPromoTileTitleVisible(tile), "Promo title at position " + tile + " was not visible.");
            softAssert.assertTrue(homePage.isPromoTileDescriptionVisible(tile), "Promo description at position " + tile + " was not visible.");
            softAssert.assertEquals(
                    PromoTiles.values()[tile -1].getTitle().toLowerCase(),
                    homePage.getPromoTileTitleText(tile).toLowerCase(),
                    "Promo title at position " + tile + " did not match expectation.  Got " + homePage.getPromoTileTitleText(tile).toLowerCase() + ", expected " + PromoTiles.values()[tile -1].getTitle().toLowerCase());
            softAssert.assertEquals(
                    PromoTiles.values()[tile -1].getDescription().toLowerCase(),
                    homePage.getPromoTileDescriptionText(tile).toLowerCase(),
                    "Promo description at position " + tile + " did not match expectation.  Got " + homePage.getPromoTileDescriptionText(tile).toLowerCase() + ", expected " + PromoTiles.values()[tile -1].getDescription().toLowerCase());
            softAssert.assertEquals(
                    PromoTiles.values()[tile -1].getActions().toLowerCase(),
                    homePage.getPromoTileActionsText(tile).toLowerCase(),
                    "Promo title at position " + tile + " did not match expectation.  Got " + homePage.getPromoTileActionsText(tile).toLowerCase() + ", expected " + PromoTiles.values()[tile -1].getActions().toLowerCase());
        }

        // A warning to the maintainer, your most common failure might be the promo tile data in the enum section changing from month to month.
        softAssert.assertAll();
    }

    @Test(
            groups = {TestNGGroups.WEB_REGRESSION},
            description = "11107421")
    public void homepageShopCoffeeShopTea() {
        logger.info("1. Navigate to landing page");
        HomePage homePage = navigateToHome();
        Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");

        logger.info("2. Clicking shop coffee promo tile to verify link");
        ProductListPage plpPage = homePage.clickCoffeePromoTile();
        softAssert.assertTrue(
                plpPage.getDriver().getCurrentUrl().contains(Constants.HomepagePromoTileData.COFFEE_PROMO_LINK),
                "Coffee Promo tile brought us to wrong page.  Got " + plpPage.getDriver().getCurrentUrl() + ", expected: " + Constants.HomepagePromoTileData.COFFEE_PROMO_LINK);
        logger.info(" -- navigating back to home");
        homePage = navigateToHome();

        logger.info("3. Clicking shop tea promo tile to verify link");
        plpPage = homePage.clickTeaPromoTile();
        softAssert.assertTrue(
                plpPage.getDriver().getCurrentUrl().contains(Constants.HomepagePromoTileData.TEA_PROMO_LINK),
                "Tea promo tile brought us to wrong page.  Got " + plpPage.getDriver().getCurrentUrl() + ", expected: " + Constants.HomepagePromoTileData.TEA_PROMO_LINK);
        logger.info(" -- navigating back to home");
        homePage = navigateToHome();

        logger.info("4. Validate description for shop coffee section");
        softAssert.assertEquals(
                homePage.getCoffeePromoTileDescription(),
                Constants.HomepagePromoTileData.COFFEE_PROMO_DESCRIPTION,
                "Description for coffee did not match.  Got: " + homePage.getCoffeePromoTileDescription() + ", expected: " + Constants.HomepagePromoTileData.COFFEE_PROMO_DESCRIPTION);
        logger.info("5. Validate description for shop tea section");
        softAssert.assertEquals(
                homePage.getTeaPromoTileDescription(),
                Constants.HomepagePromoTileData.TEA_PROMO_DESCRIPTION,
                "Description for tea did not match.  Got: " + homePage.getTeaPromoTileDescription() + ", expected: " + Constants.HomepagePromoTileData.TEA_PROMO_DESCRIPTION);

        softAssert.assertAll();
    }

    @Test(
            groups = {TestNGGroups.WEB_REGRESSION},
            description = "11107422")
    public void homepageBestSellers() {
        logger.info("1. Navigate to landing page");
        HomePage homePage = navigateToHome();
        Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");
        ProductListPage plpPage;
        int firstPosition = 0;

        logger.info("2. Verifying show best sellers button");
        plpPage = homePage.clickShowAllBestSellersButton();
        softAssert.assertTrue(
                plpPage.getDriver().getCurrentUrl().contains(Constants.HomepageBestSellersData.BEST_SELLERS_LINK),
                "Best sellers button brought us to wrong location.  Got: " + plpPage.getDriver().getCurrentUrl() + ", expected: " + Constants.HomepageBestSellersData.BEST_SELLERS_LINK);

        logger.info("-- navigating back to homepage");
        homePage = navigateToHome();

        logger.info("3. Verifying scroll right shows new items");
        List<String> shownPositions = homePage.getBestSellersShownPositions();
        homePage.clickBestSellersCarouselNext();

        for (int i = 0; i < homePage.getBestSellersPositionLength(); i ++ ) {
            softAssert.assertNotEquals("", homePage.getBestSellersPositionTitle(firstPosition), "First Best seller was empty.");
            softAssert.assertNotEquals(shownPositions.get(i), homePage.getBestSellersPositionTitle(i), "Best Sellers did not change when next was clicked.");
        }

        logger.info("4. Verifying scroll left shows new items");
        homePage.clickBestSellersCarouselPrevious();
        for (int i = 0; i < homePage.getBestSellersPositionLength(); i ++ ) {
            softAssert.assertEquals(shownPositions.get(i), homePage.getBestSellersPositionTitle(i), "Best Sellers did not go back to the original list with previous clicked.");
        }

        logger.info("5. Verifying hover over best seller item shows add to cart button");
        homePage.hoverBestSellersPosition(firstPosition);
        softAssert.assertTrue(homePage.isBestSellersQuickAddDisplayedOnHover(), "Quick Add is not displaying on hover.");

        logger.info("6. Verifying quick add overlay happens on quickadd click");
        homePage.hoverBestSellersPosition(firstPosition);
        homePage.clickBestSellersQuickAddButton();
        softAssert.assertTrue(homePage.isQuickAddOverlayDisplayed(), "Quick Add Overlay is not displaying.");

        softAssert.assertAll();
    }
}
