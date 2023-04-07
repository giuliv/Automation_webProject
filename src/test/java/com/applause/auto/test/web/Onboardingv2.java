package com.applause.auto.test.web;

import com.applause.auto.common.data.Constants;
import com.applause.auto.web.components.pdp.PdpStickyNavDetailsComponent;
import com.applause.auto.web.views.HomePage;
import com.applause.auto.web.views.ProductListPage;
import org.testng.Assert;
import org.testng.annotations.Test;



public class Onboardingv2 extends BaseTest{
    @Test(
            groups = {
                    Constants.TestNGGroups.SEARCH,
                    Constants.TestNGGroups.SMOKE
            },
            description = "11102893")
    public void test1() {
        logger.info("1. Navigate to Home page");
        HomePage homePage = navigateToHome();
        Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");

        logger.info("2. Navigate to Product list page");
        ProductListPage productListPage = navigateToPLPFromHome();
        Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

        logger.info("3. Click on the second Coffee item");
        String coffeeName = productListPage.getProductOnPosition(1).getProductName2();
        String coffeePrice = productListPage.getProductOnPosition(1).getProductPrice();
        productListPage.clickProductWrapperByIndex(1);

        logger.info("4. The coffe name is " + coffee_name + " and the price is "+coffee_price);

        logger.info("5. Scroll down to see the header displayed at the top");
        PdpStickyNavDetailsComponent pdpStickyNavDetailsComponent = productListPage.scrollDownToSeeNewHeaderOnTop();
        String coffeeNavName = pdpStickyNavDetailsComponent.getProductName();
        String coffeeNavPrice = pdpStickyNavDetailsComponent.getProductPrice();
        logger.info("The Nav Bar displays coffee name: "+ coffeeNavName+ " and price: "+coffeeNavPrice);

        logger.info("6. Validate the names are matching");
        Assert.assertEquals(coffeeName,coffeeNavName, "The names don't match");

        logger.info("7. Validate the prices are matching");
        Assert.assertEquals(coffeePrice, coffeeNavPrice, "The prices don't match");

        logger.info("8. Validate the Add to cart button is displayed");
        Assert.assertTrue(productListPage.isStickyBtnDisplayed(),"The button Add to Cart is not displayed");

    }
}
