package com.applause.auto.pageframework.chunks;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.pageframework.device.MobileElementLocator;
import com.applause.auto.pageframework.helpers.MobileHelper;
import com.applause.auto.pageframework.views.AccountHistoryView;
import com.applause.auto.pageframework.views.AuthenticationView;
import com.applause.auto.pageframework.views.GeneralSettingsView;
import com.applause.auto.pageframework.views.PaymentMethodsView;
import com.applause.auto.pageframework.views.ProfileDetailsView;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import java.lang.invoke.MethodHandles;

@Implementation(is = AndroidAccountMenuMobileChunk.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = AccountMenuMobileChunk.class, on = Platform.MOBILE_IOS)
public class AccountMenuMobileChunk extends BaseComponent {

    /**
     * Constructor.
     *
     * @param selector
     *            the selector of the chunk
     */
    public AccountMenuMobileChunk(String selector) {
        super(selector);
    }

    /*
     * Public actions
     */

    /**
     * Profile details profile details view.
     *
     * @return the profile details view
     */
    public ProfileDetailsView profileDetails() {
        logger.info("Click on Profile Details button");
        getProfileDetailsButton().click();
        return ComponentFactory.create(ProfileDetailsView.class);
    }

    /**
     * General settings general settings view.
     *
     * @return the general settings view
     */
    public GeneralSettingsView generalSettings() {
        logger.info("Click on General Settings button");
        getGeneralSettingsButton().click();
        return ComponentFactory.create(GeneralSettingsView.class);
    }

    /**
     * Sign out authentication view.
     *
     * @return the authentication view
     */
    public AuthenticationView signOut() {
        logger.info("Click on Sign Out button");
        MobileHelper.scrollToBottom(5);
        getSignOutButton().click();
        getDriver().switchTo().alert().accept();
        return ComponentFactory.create(AuthenticationView.class);
    }

    /**
     * Click Payment Methods
     *
     * @return PaymentMethodsView
     */
    public PaymentMethodsView clickPaymentMethods() {
        logger.info("Click Payment Methods");
        getPaymentMethodsButton().click();
        return ComponentFactory.create(PaymentMethodsView.class);
    }

    /**
     * Sign out t.
     *
     * @param <T>
     *            the type parameter
     * @param clazz
     *            the clazz
     * @return the t
     */
    public <T extends BaseComponent> T signOut(Class<T> clazz) {
        logger.info("Click on Sign Out button");
        MobileHelper.scrollToBottom(5);
        getSignOutButton().click();
        return ComponentFactory.create(clazz);
    }

    /**
     * Account history account history view.
     *
     * @return the account history view
     */
    public AccountHistoryView accountHistory() {
        logger.info("Click Account History");
        getAccountHistoryButton().click();
        return ComponentFactory.create(AccountHistoryView.class);
    }

    /*
     * Protected Getters
     */

    @Locate(xpath = "//XCUIElementTypeStaticText[@name=\"Profile Details\" and @visible='true']", on = Platform.MOBILE_IOS)
    @Locate(id = "com.wearehathway.peets.development:id/profileDetails", on = Platform.MOBILE_ANDROID)
protected    protected Button getProfileDetailsButton;

    @Locate(id = "Sign Out", on = Platform.MOBILE_IOS)
    @Locate(id = "com.wearehathway.peets.development:id/logoutButton", on = Platform.MOBILE_ANDROID)
protected    protected Button getSignOutButton;

    @Locate(xpath = "//XCUIElementTypeButton[@value='Log Out']", on = Platform.MOBILE_IOS)
    @Locate(id = "android:id/button1", on = Platform.MOBILE_ANDROID)
protected    protected Button getLogOutButton;

    @Locate(xpath = "//XCUIElementTypeStaticText[@name=\"General Settings\"]", on = Platform.MOBILE_IOS)
    @Locate(id = "com.wearehathway.peets.development:id/generalSettings", on = Platform.MOBILE_ANDROID)
protected    protected Button getGeneralSettingsButton;

    @Locate(xpath = "//XCUIElementTypeStaticText[@name=\"Account History\"]", on = Platform.MOBILE_IOS)
    @Locate(id = "com.wearehathway.peets.development:id/accountActivity", on = Platform.MOBILE_ANDROID)
protected    protected Button getAccountHistoryButton;

    @Locate(id = "Payment Methods", on = Platform.MOBILE_IOS)
    @Locate(id = "com.wearehathway.peets.development:id/paymentMethods", on = Platform.MOBILE_ANDROID)
protected    protected Button getPaymentMethodsButton;
}

class AndroidAccountMenuMobileChunk extends AccountMenuMobileChunk {

    /**
     * Constructor.
     *
     * @param selector
     *            the selector of the chunk
     */
    public AndroidAccountMenuMobileChunk(String selector) {
        super(selector);
    }

    public AuthenticationView signOut() {
        logger.info("Click on Sign Out button");
        MobileHelper.scrollToBottom(5);
        getSignOutButton().click();
        getLogOutButton().click();
        return ComponentFactory.create(AuthenticationView.class);
    }

    /**
     * Sign out t.
     *
     * @param <T>
     *            the type parameter
     * @param clazz
     *            the clazz
     * @return the t
     */
    public <T extends BaseComponent> T signOut(Class<T> clazz) {
        logger.info("Click on Sign Out button");
        MobileHelper.scrollToBottom(5);
        getSignOutButton().click();
        getLogOutButton().click();
        return ComponentFactory.create(clazz);
    }

}
