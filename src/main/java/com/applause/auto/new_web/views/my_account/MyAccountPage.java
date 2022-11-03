package com.applause.auto.new_web.views.my_account;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.new_web.components.MyAccountLeftMenu;
import com.applause.auto.new_web.components.RegisterPeetCardComponent;
import com.applause.auto.new_web.components.UpdateSubscriptionPaymentChunk;
import com.applause.auto.new_web.components.my_account.MyOrderItemComponent;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.new_web.views.Base;
import com.applause.auto.new_web.views.GiftCardsPage;
import com.applause.auto.new_web.views.ProductListPage;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Image;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.LazyList;
import io.qameta.allure.Step;
import java.time.Duration;
import java.util.List;
import lombok.Getter;

@Implementation(is = MyAccountPage.class, on = Platform.WEB)
@Implementation(is = MyAccountPageMobile.class, on = Platform.WEB_MOBILE_PHONE)
public class MyAccountPage extends Base {

  /* -------- Elements -------- */

  @Locate(css = "#acDashboard h1", on = Platform.WEB)
  @Locate(css = ".ac-section-header", on = Platform.WEB_MOBILE_PHONE)
  private Text getViewSignature;

  @Locate(className = "dashboard-rewards__modal--close", on = Platform.WEB)
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
  private Button addItemButton;

  @Locate(css = ".ac-subscriptions__actions > button", on = Platform.WEB)
  private Button updatePaymentInformationButton;

  @Locate(css = "div.og-shipment-info strong", on = Platform.WEB)
  private Text subscriptionScheduleDate;

  @Locate(
      css = "div.og-shipment-header-controls > div.og-send-shipment-now-button > button",
      on = Platform.WEB)
  @Locate(
      css = "div.og-mobile > div.og-send-shipment-now-button > button.og-button",
      on = Platform.WEB_MOBILE_PHONE)
  private Button subscriptionSendNowButton;

  @Locate(
      css = "div.og-shipment-header-controls > div.og-change-shipment-date-button > button",
      on = Platform.WEB)
  @Locate(
      css = "div.og-mobile > div.og-change-shipment-date-button > button.og-button",
      on = Platform.WEB_MOBILE_PHONE)
  private Button subscriptionChangeDateButton;

  @Locate(
      css = "div.og-shipment-header-controls > div.og-skip-shipment-button > button",
      on = Platform.WEB)
  @Locate(
      css = "div.og-mobile > div.og-skip-shipment-button > button.og-button",
      on = Platform.WEB_MOBILE_PHONE)
  private Button subscriptionSkipOrderButton;

  @Locate(css = "select[name=quantity]", on = Platform.WEB)
  private ContainerElement subscriptionQuantityBox;

  @Locate(css = "select[name=frequency]", on = Platform.WEB)
  private ContainerElement subscriptionFrequencyBox;

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

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(getViewSignature).visible().setTimeout(Duration.ofSeconds(40)));
    //    WebHelper.clickButtonOverIFrame(newBannerIFrame, dismissBanner);

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

  public boolean isSubscriptionFrequencyDisplayed() {
    logger.info("Checking subscription product frequency is displayed");
    return subscriptionFrequencyBox.isDisplayed();
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

  @Step("Click on the 'Add item' button")
  public ProductListPage clickAddItem() {
    logger.info("Clicking on the 'Add item' button");
    addItemButton.click();
    return SdkHelper.create(ProductListPage.class);
  }

  @Step("Click 'Update Payment Information' button")
  public UpdateSubscriptionPaymentChunk clickUpdatePaymentInformation() {
    logger.info("Clicking 'Update Payment Information' button");
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
}

class MyAccountPageMobile extends MyAccountPage {

  @Override
  @Step("Click in view all orders")
  public OrderHistoryPage viewAllOrders() {
    logger.info("Clicking in 'View all orders' button");
    WebHelper.clickOnElementAndScrollUpIfNeeded(viewAllOrdersButton, -110);
    return SdkHelper.create(OrderHistoryPage.class);
  }

  @Override
  @Step("Click Start sharing")
  public ReferralsPage clickOnStartSharingButton() {
    logger.info("Clicking in 'Start sharing' button");
    WebHelper.clickOnElementAndScrollUpIfNeeded(startSharingButton, -110);
    return SdkHelper.create(ReferralsPage.class);
  }

  @Override
  @Step("Verify subscription product image is displayed")
  public boolean isSubscriptionImageDisplayed() {
    logger.info("Checking subscription product image is displayed");
    WebHelper.scrollToElement(subscriptionItemImage);
    return subscriptionItemImage.exists();
  }

  @Override
  public boolean isSubscriptionBillingSectionDisplayed() {
    logger.info("Checking subscription Billing section is displayed");
    return subscriptionBillingSection.exists();
  }

  @Override
  public boolean isSubscriptionShippingSectionDisplayed() {
    logger.info("Checking subscription Shipping section is displayed");
    return subscriptionShippingSection.exists();
  }

  @Override
  public boolean isSubscriptionTotalSectionDisplayed() {
    logger.info("Checking subscription Total section is displayed");
    return subscriptionTotalSection.exists();
  }
}
