package com.applause.auto.pageframework.views;

import com.google.common.collect.ImmutableMap;

import com.applause.auto.framework.pageframework.device.AbstractDeviceView;
import com.applause.auto.framework.pageframework.device.DeviceChunkFactory;
import com.applause.auto.framework.pageframework.device.DeviceViewFactory;
import com.applause.auto.framework.pageframework.device.MobileElementLocator;
import com.applause.auto.framework.pageframework.device.factory.AndroidImplementation;
import com.applause.auto.framework.pageframework.device.factory.IosImplementation;
import com.applause.auto.framework.pageframework.devicecontrols.BaseDeviceControl;
import com.applause.auto.framework.pageframework.devicecontrols.Button;
import com.applause.auto.framework.pageframework.devicecontrols.Text;
import com.applause.auto.framework.pageframework.devicecontrols.TextBox;
import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.pageframework.chunks.mobile.AllowLocationServicesPopupChunk;
import com.applause.auto.pageframework.helpers.MobileHelper;

import java.lang.invoke.MethodHandles;

@AndroidImplementation(AndroidSelectCoffeeBarView.class)
@IosImplementation(SelectCoffeeBarView.class)
public class SelectCoffeeBarView extends AbstractDeviceView {

    protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

    @Override
    protected void waitUntilVisible() {
        if (!getSignature().exists()) {
            throw new AssertionError("Select coffeebar view not displayed");
        }
    }

    /*
     * Public Actions
     */

    /**
     * Gets enable location description.
     *
     * @return the enable location description
     */
    public String getEnableLocationDescription() {
        return getEnableLocationDescriptionText().getStringValue();
    }

    /**
     * Is enable location button displayed boolean.
     *
     * @return the boolean
     */
    public boolean isEnableLocationButtonDisplayed() {
        return syncHelper.isElementDisplayed(getLocator(this, "getEnableLocationButton"));
    }

    /**
     * Enable location allow location services popup chunk.
     *
     * @return the allow location services popup chunk
     */
    public AllowLocationServicesPopupChunk enableLocation() {
        LOGGER.info("Tap enable location button");
        return DeviceChunkFactory.create(AllowLocationServicesPopupChunk.class, "");
    }

    /**
     * Search.
     *
     * @param searchTxt the search txt
     */
    public void search(String searchTxt) {
        LOGGER.info("Searching for store: " + searchTxt);
        try {
            getChangeStoreButton().pressButton();
        } catch (Throwable throwable) {

        }
        getSearchButton().pressButton();
        getSearchTextBox().enterText(searchTxt + "\n");
    }

    /**
     * Open coffeebar from search results new order view.
     *
     * @param index the index
     * @return the new order view
     */
    public NewOrderView openCoffeebarFromSearchResults(int index) {
        LOGGER.info("Tap on Search result" + ">>>>>>>>>>>" + getDriver().getPageSource());
        int counter = 5;
        while (counter-- != 0) {
            if (getSearchResultText(index).isDisplayed()) {
                getSearchResultText(index).tapCenterOfElement();
            } else {
                MobileHelper.getSnapshotManager().takeRemoteDeviceScreenshot("" + System.currentTimeMillis());
                MobileHelper.swipeAcrossScreenCoordinates(0.8, 0.8,
                        0.2, 0.8, 2000);
                MobileHelper.getSnapshotManager().takeRemoteDeviceScreenshot("" + System.currentTimeMillis());
            }
        }
        return DeviceViewFactory.create(NewOrderView.class);
    }

    public boolean isStoresDisplayed() {
        boolean result = queryHelper.doesElementExist(getLocator(this, "getSearchResultText", 1));
        return result;
    }

    /*
     * Protected Getters
     */

    @MobileElementLocator(android = "com.wearehathway.peets.development:id/storeContainer", iOS = "//XCUIElementTypeOther[@name='FIND A COFFEEBAR']")
    protected BaseDeviceControl getSignature() {
        return new BaseDeviceControl(getLocator(this, "getSignature"));
    }

    @MobileElementLocator(android = "com.wearehathway.peets.development:id/noStoreMessage", iOS = "//XCUIElementTypeButton[@name=\"Enable Location\"]/preceding-sibling::*")
    protected Text getEnableLocationDescriptionText() {
        return new Text(getLocator(this, "getEnableLocationDescriptionText"));
    }

    @MobileElementLocator(android = "com.wearehathway.peets.development:id/enableLocationServiceButton", iOS = "Enable Location")
    protected Button getEnableLocationButton() {
        return new Button(getLocator(this, "getEnableLocationButton"));
    }

    @MobileElementLocator(android = "com.wearehathway.peets.development:id/changeTextView", iOS = "TODO")
    protected Button getChangeStoreButton() {
        return new Button(getLocator(this, "getChangeStoreButton"));
    }

    @MobileElementLocator(android = "com.wearehathway.peets.development:id/storeSearchIcon", iOS = "Enter Zip or City, State")
    protected Button getSearchButton() {
        return new Button(getLocator(this, "getSearchButton"));
    }

    @MobileElementLocator(android = "com.wearehathway.peets.development:id/searchField", iOS = "//XCUIElementTypeSearchField[@name=\"Enter Zip or City, State\"]")
    protected TextBox getSearchTextBox() {
        return new TextBox(getLocator(this, "getSearchTextBox"));
    }

    @MobileElementLocator(android = "(//android.widget.RelativeLayout[@resource-id='com.wearehathway.peets.development:id/storeDetail'])[%s]", iOS = "(//XCUIElementTypeStaticText[@value='AppInt Sandbox 1']/..//XCUIElementTypeButton[@name=\"Order\"])[%s]")
    protected TextBox getSearchResultText(int index) {
        return new TextBox(getLocator(this, "getSearchResultText", index));
    }

}

class AndroidSelectCoffeeBarView extends SelectCoffeeBarView {
    @Override
    protected void waitUntilVisible() {
        // Workaround for Automator hang
        try {
            DeviceChunkFactory.create(AllowLocationServicesPopupChunk.class, "").notNow();
        } catch (AssertionError ase) {
            LOGGER.warn("No popup found");
        }
        syncHelper.waitForElementToAppear(getSignature());
    }

    public void search(String searchTxt) {
        LOGGER.info("Searching for store: " + searchTxt);
        getChangeStoreButton().pressButton();
        getSearchButton().pressButton();
        getSearchTextBox().enterText(searchTxt);
        getDriver().executeScript("mobile: performEditorAction", ImmutableMap.of("action", "search"));
    }

    @Override
    public AllowLocationServicesPopupChunk enableLocation() {
        LOGGER.info("Tap enable location button");
        getEnableLocationButton().pressButton();
        return DeviceChunkFactory.create(AllowLocationServicesPopupChunk.class, "");
    }

}