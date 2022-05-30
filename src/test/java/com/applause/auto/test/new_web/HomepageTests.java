package com.applause.auto.test.new_web;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.common.data.enums.CoffeeSubMenu;
import com.applause.auto.common.data.enums.LearnSubMenu;
import com.applause.auto.common.data.enums.TeaSubMenu;
import com.applause.auto.common.data.enums.VisitUsSubMenu;
import com.applause.auto.new_web.components.Header;
import com.applause.auto.new_web.views.*;
import org.testng.Assert;
import org.testng.annotations.Test;

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
}
