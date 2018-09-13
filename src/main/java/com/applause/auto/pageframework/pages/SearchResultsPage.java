package com.applause.auto.pageframework.pages;

import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.framework.pageframework.web.AbstractPage;
import com.applause.auto.framework.pageframework.web.PageFactory;
import com.applause.auto.framework.pageframework.web.WebElementLocator;
import com.applause.auto.framework.pageframework.web.factory.WebDesktopImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebPhoneImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebTabletImplementation;
import com.applause.auto.framework.pageframework.webcontrols.Button;
import com.applause.auto.framework.pageframework.webcontrols.Text;

import java.lang.invoke.MethodHandles;

@WebDesktopImplementation(SearchResultsPage.class)
@WebTabletImplementation(SearchResultsPage.class)
@WebPhoneImplementation(SearchResultsPage.class)
public class SearchResultsPage extends AbstractPage {
    protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

    @Override
    protected void waitUntilVisible() {
        syncHelper.waitForElementToAppear(getResultsText());
    }

    // Public actions

    /**
     * Click First Product
     *
     * @return TourProductDescriptionPage
     */
    public TourProductDescriptionPage clickFirstProduct() {
        LOGGER.info("Clicking First Product");
        getFirstProduct().click();
        return PageFactory.create(TourProductDescriptionPage.class);
    }

    // Protected getters
    @WebElementLocator(webDesktop = ".shown-results")
    protected Text getResultsText() { return new Text(this, getLocator(this, "getResultsText")); }

    @WebElementLocator(webDesktop = "#product_addtocart_form10995 > div.image-holder > div > a > picture > img")
    protected Button getFirstProduct() { return new Button(this, getLocator(this, "getFirstProduct")); }

}
