package com.applause.auto.pageframework.views;

import com.applause.auto.framework.pageframework.device.AbstractDeviceView;
import com.applause.auto.framework.pageframework.device.DeviceViewFactory;
import com.applause.auto.framework.pageframework.device.MobileElementLocator;
import com.applause.auto.framework.pageframework.device.factory.AndroidImplementation;
import com.applause.auto.framework.pageframework.device.factory.IosImplementation;
import com.applause.auto.framework.pageframework.devicecontrols.BaseDeviceControl;
import com.applause.auto.framework.pageframework.devicecontrols.Button;
import com.applause.auto.framework.pageframework.devicecontrols.Text;
import com.applause.auto.framework.pageframework.util.logger.LogController;

import java.lang.invoke.MethodHandles;

@IosImplementation(ProductDetailsView.class)
@AndroidImplementation(AndroidProductDetailsView.class)
public class ProductDetailsView extends AbstractDeviceView {

    protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

    @Override
    protected void waitUntilVisible() {
        syncHelper.waitForElementToAppear(getHeadingText(), 120000);
    }

    /**
     * Get the text vaalue of the heading
     *
     * @return
     */
    public String getHeadingTextValue() {
        return getHeadingText().getStringValue();
    }

    /**
     * Select modifiers product details view.
     *
     * @param category    the category
     * @param subCategory the sub category
     * @return the product details view
     */
    public ProductDetailsView selectModifiers(String category, String subCategory) {
        LOGGER.info("Select category: " + category + " | " + subCategory);
        getCategoryItem(category).tapCenterOfElement();
        getCategoryItem(subCategory).tapCenterOfElement();
        getSaveChangesButton().pressButton();
        return DeviceViewFactory.create(ProductDetailsView.class);
    }

    /**
     * Navigate back t.
     *
     * @param <T>   the type parameter
     * @param clazz the clazz
     * @return the t
     */
    public <T extends AbstractDeviceView> T navigateBack(Class<T> clazz) {
        LOGGER.info("Navigate Back");
        getBackButton().pressButton();
        if (!clazz.equals(SearchResultsView.class)) {
            getBackButton().pressButton();
        }
        return DeviceViewFactory.create(clazz);
    }

    /**
     * Add to order t.
     *
     * @param <T>   the type parameter
     * @param clazz the clazz
     * @return the t
     */
    public <T extends AbstractDeviceView> T addToOrder(Class<T> clazz) {
        LOGGER.info("Tap Add to Order");
        getAddToOrderButton().pressButton();
        return DeviceViewFactory.create(clazz);
    }

    /*
     * Protected Getters
     */

    @MobileElementLocator(android = "com.wearehathway.peets.development:id/productCalories", iOS = "//XCUIElementTypeStaticText[contains(@name,'Calories')]")
    protected Text getHeadingText() {
        return new Text(getLocator(this, "getHeadingText"));
    }

    @MobileElementLocator(android = "//android.widget.TextView[@text=\"%s\"]", iOS = "//XCUIElementTypeStaticText[@name=\"%s\"]")
    protected BaseDeviceControl getCategoryItem(String category) {
        return new BaseDeviceControl(getLocator(this, "getCategoryItem", category));
    }

    @MobileElementLocator(android = "com.wearehathway.peets.development:id/saveChangesButton", iOS = "Save Changes")
    protected Button getSaveChangesButton() {
        return new Button(getLocator(this, "getSaveChangesButton"));
    }

    @MobileElementLocator(android = "com.wearehathway.peets.development:id/addOrUpdateProductButton", iOS = "Add to Order")
    protected Button getAddToOrderButton() {
        return new Button(getLocator(this, "getAddToOrderButton"));
    }

    @MobileElementLocator(android = "//android.widget.ImageButton[@content-desc='Navigate up']", iOS = "button back")
    protected Button getBackButton() {
        return new Button(getLocator(this, "getBackButton"));
    }

}

class AndroidProductDetailsView extends ProductDetailsView {
    @Override
    protected void waitUntilVisible() {
        syncHelper.waitForElementToAppear(getHeadingText(), 120000);
    }
}