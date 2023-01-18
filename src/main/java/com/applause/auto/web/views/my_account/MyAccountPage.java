package com.applause.auto.web.views.my_account;

import com.applause.auto.common.data.Constants.TestData;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Image;
import com.applause.auto.pageobjectmodel.elements.Link;
import com.applause.auto.pageobjectmodel.elements.SelectList;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.LazyList;
import com.applause.auto.web.components.MyAccountLeftMenu;
import com.applause.auto.web.components.RegisterPeetCardComponent;
import com.applause.auto.web.components.ReloadComponent;
import com.applause.auto.web.components.UpdateSubscriptionPaymentChunk;
import com.applause.auto.web.components.my_account.MyOrderItemComponent;
import com.applause.auto.web.helpers.WebHelper;
import com.applause.auto.web.views.Base;
import com.applause.auto.web.views.GiftCardsPage;
import com.applause.auto.web.views.ProductListPage;
import io.qameta.allure.Step;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Implementation(is = MyAccountPage.class, on = Platform.WEB)
@Implementation(is = MyAccountPageMobile.class, on = Platform.WEB_MOBILE_PHONE)
public class MyAccountPage extends Base {

  /* -------- Elements -------- */

  @Locate(css = "#acDashboard h1", on = Platform.WEB)
  @Locate(css = "ac-section-header, header.ac-section-header h2", on = Platform.WEB_MOBILE_PHONE)
  private Text getViewSignature;

  @Locate(css = "header.ac-section-header h2", on = Platform.WEB)
  private Text getViewSignature2;

  @Locate(css = ".dashboard-rewards__modal--close", on = Platform.WEB)
  private Button closeBanner;

  @Locate(
      xpath = "//div[contains(@class, 'preview')]//h3[contains(@class, 'section-heading')]",
      on = Platform.WEB)
  private Text recentOrdersTitle;

  @Locate(id = "subscriptionsHeading", on = Platform.WEB)
  private Text mySubscriptionTitle;

  @Locate(xpath = "//div[contains(@class, 'preview')]/header//a", on = Platform.WEB)
  protected Button viewAllOrdersButton;

  @Locate(xpath = "//ul/li[@class='ac-table-row']", on = Platform.WEB)
  private List<MyOrderItemComponent> myOrderItemList;

  @Getter @Locate public MyAccountLeftMenu leftMenu;

  @Locate(xpath = "//a[contains(text(), 'Start Sharing')]", on = Platform.WEB)
  protected Button startSharingButton;

  @Locate(css = "#subscriptions .og-product-image", on = Platform.WEB)
  protected Image subscriptionItemImage;

  @Locate(css = "#subscriptions .og-product-name a", on = Platform.WEB)
  private Text subscriptionItemName;

  @Locate(css = "#subscriptions .og-final-unit-price", on = Platform.WEB)
  private Text subscriptionItemPrice;

  @Locate(css = ".og-item-controls-container > a", on = Platform.WEB)
  private Link addItemButton;

  @Locate(css = ".ac-subscriptions__actions > button", on = Platform.WEB)
  private Button updatePaymentInformationButton;

  @Locate(css = "div.og-shipment-info strong", on = Platform.WEB)
  private Text subscriptionScheduleDate;

  @Locate(
      css = "div.og-shipment-header-controls > div.og-send-shipment-now-button > button",
      on = Platform.WEB)
  private Button subscriptionSendNowButton;

  @Locate(
      css = "div.og-shipment-header-controls > div.og-change-shipment-date-button > button",
      on = Platform.WEB)
  private Button subscriptionChangeDateButton;

  @Locate(
      css = "div.og-shipment-header-controls > div.og-skip-shipment-button > button",
      on = Platform.WEB)
  private Button subscriptionSkipOrderButton;

  @Locate(css = "select[name=quantity]", on = Platform.WEB)
  private SelectList subscriptionQuantityBox;

  @Locate(css = "select[name=frequency]", on = Platform.WEB)
  private SelectList subscriptionFrequencyBox;

  @Locate(css = "div.og-desktop div.og-billing", on = Platform.WEB)
  @Locate(css = "details.og-mobile div.og-billing", on = Platform.WEB_MOBILE_PHONE)
  protected ContainerElement subscriptionBillingSection;

  @Locate(css = "div.og-desktop div.og-shipping", on = Platform.WEB)
  @Locate(css = "details.og-mobile div.og-shipping", on = Platform.WEB_MOBILE_PHONE)
  protected ContainerElement subscriptionShippingSection;

  @Locate(css = "div.og-desktop div.og-total-table", on = Platform.WEB)
  @Locate(css = "details.og-mobile div.og-total-table", on = Platform.WEB_MOBILE_PHONE)
  protected ContainerElement subscriptionTotalSection;

  @Locate(css = ".my-cards__actions a[href*='register']", on = Platform.WEB)
  protected Button registerNewPeetsCardButton;

  @Locate(css = ".my-cards__actions a[href*='gift']", on = Platform.WEB)
  private Button buyNewPeetsCardButton;

  @Locate(css = "h4.og-product-display-name", on = Platform.WEB)
  private Text mySubscriptionGrind;

  @Locate(
      css = ".og-payment-shipping.og-desktop tr.og-shipment-total td.og-total-value",
      on = Platform.WEB)
  private Text mySubscriptionTotalAmount;

  @Locate(
      css = ".og-mobile-payment-shipping tr.og-shipment-total td.og-total-value",
      on = Platform.WEB)
  private Text mySubscriptionTotalAmountMobile;

  @Locate(
      css = ".og-payment-shipping.og-desktop .og-change-shipment-address-button > a",
      on = Platform.WEB)
  private Link editShippingAddressLink;

  @Locate(
      css = ".og-mobile-payment-shipping .og-change-shipment-address-button > a",
      on = Platform.WEB)
  protected Link editShippingAddressLinkMobile;

  @Locate(css = "dialog[open]>form[data-og-action-key=change_shipping_address]", on = Platform.WEB)
  protected ContainerElement editShippingAddressModalContainer;

  @Locate(css = "dialog[open] button.og-button-close", on = Platform.WEB)
  protected Link closeShippingAddressLinkMobile;

  @Locate(css = "summary > div", on = Platform.WEB)
  protected Button expandDetailsSection;

  @Locate(css = ".og-cancel-subscription-button > a", on = Platform.WEB)
  protected Button cancelSubscriptionButton;

  // PEET'S CARDS section

  @Locate(css = "span.ac-cards__price", on = Platform.WEB)
  protected Text cardsBalanceText;

  @Locate(css = ".ac-cards__reload label", on = Platform.WEB)
  protected SelectList cardsAutoReloadCheckbox;

  @Locate(css = ".ac-cards__actions button", on = Platform.WEB)
  protected Button cardsReloadCardButton;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper()
        .wait(
            Until.oneOf(getViewSignature, closeBanner)
                .visible()
                .setTimeout(Duration.ofSeconds(40)));

    try {
      if (WebHelper.exists(closeBanner, 7)) {
        closeBanner.click();
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    }

    logger.info("My Account Page URL: " + getDriver().getCurrentUrl());
  }

  /* -------- Actions -------- */

  /**
   * Clicks on LoginMenu
   *
   * @return LoginPage
   */
  @Step("Get welcome message")
  public String getWelcomeMessage() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(getViewSignature).visible());
    logger.info("Welcome message: " + getViewSignature.getText().toLowerCase());

    if (WebHelper.getDriverConfig().toLowerCase().contains("landscape")
        && SdkHelper.getEnvironmentHelper().isAndroid()) {
      logger.info("Welcome message2: " + getViewSignature2.getText().toLowerCase());
      return getViewSignature2.getText().toLowerCase();
    }

    return getViewSignature.getText().toLowerCase();
  }

  @Step("Get Recent Orders Title")
  public String getRecentOrdersTitle() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(recentOrdersTitle).visible());
    String title = WebHelper.cleanString(recentOrdersTitle.getText().toLowerCase());
    logger.info("Title - [{}]", title);
    return title;
  }

  public String getMySubscriptionTitle() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(mySubscriptionTitle).present());
    WebHelper.scrollToElement(mySubscriptionTitle.getWebElement());

    String title = WebHelper.cleanString(mySubscriptionTitle.getText().toLowerCase());
    logger.info("Title - [{}]", title);
    return title;
  }

  @Step("Click in view all orders")
  public OrderHistoryPage viewAllOrders() {
    logger.info("Clicking in 'View all orders' button");
    viewAllOrdersButton.click();
    return SdkHelper.create(OrderHistoryPage.class);
  }

  @Step("Get My Order Item List")
  public List<MyOrderItemComponent> getMyOrderItemList() {
    ((LazyList<?>) myOrderItemList).initialize();
    return myOrderItemList;
  }

  @Step("Click Start sharing")
  public ReferralsPage clickOnStartSharingButton() {
    logger.info("Clicking in 'Start sharing' button");
    startSharingButton.click();
    return SdkHelper.create(ReferralsPage.class);
  }

  @Step("Verify subscription product image is displayed")
  public boolean isSubscriptionImageDisplayed() {
    logger.info("Checking subscription product image is displayed");
    return WebHelper.isDisplayed(subscriptionItemImage);
  }

  @Step("Verify subscription product name is displayed")
  public boolean isSubscriptionNameDisplayed() {
    logger.info("Checking subscription product name is displayed");
    return subscriptionItemName.isDisplayed() && !subscriptionItemName.getText().isEmpty();
  }

  @Step("Verify subscription product price is displayed")
  public boolean isSubscriptionPriceDisplayed() {
    logger.info("Checking subscription product price is displayed");
    return subscriptionItemPrice.isDisplayed() && !subscriptionItemPrice.getText().isEmpty();
  }

  public boolean isSubscriptionQuantityDisplayed() {
    logger.info("Checking subscription product quantity is displayed");
    return subscriptionQuantityBox.isDisplayed();
  }

  public boolean isSubscriptionQuantityEnabled() {
    logger.info("Checking subscription product quantity is enabled");
    return subscriptionQuantityBox.isEnabled();
  }

  @Step("Verify Sending dropdown shows qty values from 1-20 and able to update to desired qty")
  public boolean areSendingQuantityDropdownValuesDisplayed() {
    return subscriptionQuantityBox.getOptions().stream()
        .map(option -> Integer.parseInt(option.getText().trim()))
        .collect(Collectors.toList())
        .equals(TestData.SUBSCRIPTION_QUANTITY_OPTIONS);
  }

  public boolean isSubscriptionFrequencyDisplayed() {
    logger.info("Checking subscription product frequency is displayed");
    return subscriptionFrequencyBox.isDisplayed();
  }

  public boolean isSubscriptionFrequencyEnabled() {
    logger.info("Checking subscription product frequency is enabled");
    return subscriptionFrequencyBox.isEnabled();
  }

  @Step("Verify Sending dropdown shows qty values from 1-20 and able to update to desired qty")
  public boolean areSubscriptionFrequencyDropdownValuesDisplayed() {
    return subscriptionFrequencyBox.getOptions().stream()
        .map(option -> option.getText().trim())
        .collect(Collectors.toList())
        .equals(TestData.SUBSCRIPTION_FREQUENCY_OPTIONS);
  }

  public boolean isSubscriptionScheduleDateDisplayed() {
    logger.info("Checking subscription Schedule Date is displayed");
    return subscriptionScheduleDate.isDisplayed() && !subscriptionScheduleDate.getText().isEmpty();
  }

  public boolean isSubscriptionSendNowButtonDisplayed() {
    logger.info("Checking subscription Send Now is displayed");
    return subscriptionSendNowButton.isDisplayed()
        && !subscriptionSendNowButton.getText().isEmpty();
  }

  public boolean isSubscriptionChangeDateDisplayed() {
    logger.info("Checking subscription Change Date is displayed");
    return subscriptionChangeDateButton.isDisplayed()
        && !subscriptionChangeDateButton.getText().isEmpty();
  }

  public boolean isSubscriptionSkipOrderDisplayed() {
    logger.info("Checking subscription Skip Order is displayed");
    return subscriptionSkipOrderButton.isDisplayed()
        && !subscriptionSkipOrderButton.getText().isEmpty();
  }

  public boolean isSubscriptionBillingSectionDisplayed() {
    logger.info("Checking subscription Billing section is displayed");
    return subscriptionBillingSection.isDisplayed();
  }

  public boolean isSubscriptionShippingSectionDisplayed() {
    logger.info("Checking subscription Shipping section is displayed");
    return subscriptionShippingSection.isDisplayed();
  }

  public boolean isSubscriptionTotalSectionDisplayed() {
    logger.info("Checking subscription Total section is displayed");
    return subscriptionTotalSection.isDisplayed();
  }

  public boolean isSubscriptionUpdatePaymentSectionDisplayed() {
    logger.info("Checking subscription Update Payment section is displayed");
    WebHelper.scrollToElement(updatePaymentInformationButton);
    return updatePaymentInformationButton.isDisplayed();
  }

  @Step("Verify subscription Add Item is displayed")
  public boolean isAddItemButtonDisplayed() {
    WebHelper.scrollToElement(addItemButton);
    if (!WebHelper.isDisplayed(addItemButton)) {
      logger.info("Subscription Add Item is not displayed");
      return false;
    }

    if (!addItemButton.getLinkURL().contains(TestData.COLLECTION_ALL_PART_URL)) {
      logger.info("Subscription Add Item link doesn't contain expected URL");
      return false;
    }

    return true;
  }

  @Step("Click on the 'Add item' button")
  public ProductListPage clickAddItem() {
    logger.info("Clicking on the 'Add item' button");
    addItemButton.click();
    return SdkHelper.create(ProductListPage.class);
  }

  @Step("Click 'Update Payment Information' button")
  public UpdateSubscriptionPaymentChunk clickUpdatePaymentInformation() {
    logger.info("Clicking 'Update Payment Information' button");
    WebHelper.scrollToElement(updatePaymentInformationButton);
    updatePaymentInformationButton.click();
    return SdkHelper.create(UpdateSubscriptionPaymentChunk.class);
  }

  public RegisterPeetCardComponent clickRegisterPeetsCard() {
    logger.info("Clicking 'Register Peet's Card' button");
    registerNewPeetsCardButton.click();
    return SdkHelper.create(RegisterPeetCardComponent.class);
  }

  public GiftCardsPage clickBuyPeetsCard() {
    logger.info("Clicking 'Buy Peet's Card' button");
    buyNewPeetsCardButton.click();
    return SdkHelper.create(GiftCardsPage.class);
  }

  @Step("Get my subscription product grind value")
  public String getMySubscriptionGrind() {
    return mySubscriptionGrind.getText().trim();
  }

  @Step("Get My Subscription total amount")
  public String getMySubscriptionTotalAmount() {
    if (WebHelper.isDisplayed(mySubscriptionTotalAmount)) {
      SdkHelper.getSyncHelper().waitUntil(text -> !mySubscriptionTotalAmount.getText().isEmpty());
      return mySubscriptionTotalAmount.getText().trim();
    } else {
      SdkHelper.getSyncHelper()
          .waitUntil(text -> !mySubscriptionTotalAmountMobile.getText().isEmpty());
      return mySubscriptionTotalAmountMobile.getText().trim();
    }
  }

  @Step("Verify Edit Shipping modal is displayed after click Edit link")
  public boolean isEditShippingAddressModalDisplayed() {
    if (WebHelper.isDisplayed(editShippingAddressLink)) {
      WebHelper.scrollToElement(editShippingAddressLink);
      editShippingAddressLink.click();
    } else {
      WebHelper.scrollToElement(editShippingAddressLinkMobile);
      editShippingAddressLinkMobile.click();
    }

    return WebHelper.isDisplayed(editShippingAddressModalContainer, 10);
  }

  @Step("Click on the close icon for Edit shipping address modal")
  public MyAccountPage closeEditShippingAddressModal() {
    logger.info("Clicking on the close icon for Edit shipping address modal");
    closeShippingAddressLinkMobile.click();
    return this;
  }

  @Step("Expand details section for mobile")
  public MyAccountPage expandDetailsSectionOnMobile() {
    if (!WebHelper.isDesktopSiteVersion()) {
      logger.info("Expanding subscription details section");
      WebHelper.scrollToElement(expandDetailsSection);
      WebHelper.jsClick(expandDetailsSection.getWebElement());
      return this;
    }
    return this;
  }

  @Step("Verify Cancel Subscription button is displayed")
  public boolean isCancelSubscriptionDisplayed() {
    return WebHelper.isDisplayed(cancelSubscriptionButton);
  }

  @Step("Verify Peets Gift card balance is displayed and is not empty")
  public boolean isPeetsGiftCardBalanceDisplayed() {
    return WebHelper.isDisplayed(cardsBalanceText) && !cardsBalanceText.getText().isEmpty();
  }

  @Step("Verify Auto Reload ON/OFF button is displayed")
  public boolean isAutoReloadOnOffDisplayed() {
    WebHelper.scrollToPageBottom();
    WebHelper.scrollToElement(cardsAutoReloadCheckbox);
    return WebHelper.isDisplayed(cardsAutoReloadCheckbox);
  }

  @Step("Verify Auto Reload ON/OFF button is displayed")
  public boolean isReloadButtonDisplayed() {
    return WebHelper.isDisplayed(cardsReloadCardButton);
  }

  @Step("Click Reload button")
  public ReloadComponent clickReloadButton() {
    WebHelper.scrollToElement(cardsReloadCardButton);
    cardsReloadCardButton.click();
    return SdkHelper.create(ReloadComponent.class);
  }

  @Step("Click 'View all orders' button")
  public OrderHistoryPage clickViewAllOrders() {
    WebHelper.scrollToElement(viewAllOrdersButton);
    viewAllOrdersButton.click();
    return SdkHelper.create(OrderHistoryPage.class);
  }
}

class MyAccountPageMobile extends MyAccountPage {

  /*
  We have a bit different locators for mobile devices with width less 768.
  These are locators for Mobile devices with screen width less 768.
  */

  @Locate(
      css = ".og-mobile-payment-shipping tr.og-shipment-total td.og-total-value",
      on = Platform.WEB)
  private Text mySubscriptionTotalAmount;

  @Locate(css = "div.og-mobile > div.og-skip-shipment-button > button.og-button", on = Platform.WEB)
  private Button subscriptionSkipOrderButtonMobile;

  @Locate(
      css = "div.og-mobile > div.og-send-shipment-now-button > button.og-button",
      on = Platform.WEB_MOBILE_PHONE)
  private Button subscriptionSendNowButtonMobile;

  @Locate(
      css = "div.og-mobile > div.og-change-shipment-date-button > button.og-button",
      on = Platform.WEB_MOBILE_PHONE)
  private Button subscriptionChangeDateButtonMobile;

  @Override
  @Step("Click in view all orders")
  public OrderHistoryPage viewAllOrders() {
    WebHelper.clickOnElementAndScrollUpIfNeeded(viewAllOrdersButton, -110);
    return SdkHelper.create(OrderHistoryPage.class);
  }

  @Override
  @Step("Click Start sharing")
  public ReferralsPage clickOnStartSharingButton() {
    WebHelper.clickOnElementAndScrollUpIfNeeded(startSharingButton, -110);
    return SdkHelper.create(ReferralsPage.class);
  }

  @Override
  @Step("Verify subscription product image is displayed")
  public boolean isSubscriptionImageDisplayed() {
    WebHelper.scrollToElement(subscriptionItemImage);
    return subscriptionItemImage.exists();
  }

  @Override
  @Step("Verify subscription Billing section is displayed")
  public boolean isSubscriptionBillingSectionDisplayed() {
    return subscriptionBillingSection.exists();
  }

  @Override
  @Step("Verify subscription Shipping section is displayed")
  public boolean isSubscriptionShippingSectionDisplayed() {
    return subscriptionShippingSection.exists();
  }

  @Override
  @Step("Verify subscription Total section is displayed")
  public boolean isSubscriptionTotalSectionDisplayed() {
    return subscriptionTotalSection.exists();
  }

  @Override
  @Step("Verify subscription Skip Order is displayed")
  public boolean isSubscriptionSkipOrderDisplayed() {
    if (super.isSubscriptionSkipOrderDisplayed()) {
      return true;
    }

    return subscriptionSkipOrderButtonMobile.isDisplayed()
        && !subscriptionSkipOrderButtonMobile.getText().isEmpty();
  }

  @Override
  @Step("Verify subscription Send Now is displayed")
  public boolean isSubscriptionSendNowButtonDisplayed() {
    if (super.isSubscriptionSendNowButtonDisplayed()) {
      return true;
    }

    return subscriptionSendNowButtonMobile.isDisplayed()
        && !subscriptionSendNowButtonMobile.getText().isEmpty();
  }

  @Override
  @Step("Verify subscription Change Date is displayed")
  public boolean isSubscriptionChangeDateDisplayed() {
    if (super.isSubscriptionChangeDateDisplayed()) {
      return true;
    }

    return subscriptionChangeDateButtonMobile.isDisplayed()
        && !subscriptionChangeDateButtonMobile.getText().isEmpty();
  }
}
