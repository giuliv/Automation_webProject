package com.applause.auto.new_web.views.my_account;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.new_web.components.MyAccountLeftMenu;
import com.applause.auto.new_web.components.my_account.MyOrderItemComponent;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.new_web.views.Base;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Button;
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

  @Locate(xpath = "//div[contains(@class, 'preview')]/header//a", on = Platform.WEB)
  protected Button viewAllOrdersButton;

  @Locate(xpath = "//ul/li[@class='ac-table-row']", on = Platform.WEB)
  private List<MyOrderItemComponent> myOrderItemList;

  @Getter @Locate public MyAccountLeftMenu leftMenu;

  @Locate(xpath = "//a[contains(text(), 'Start Sharing')]", on = Platform.WEB)
  protected Button startSharingButton;


  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(getViewSignature).visible().setTimeout(Duration.ofSeconds(40)));
    if (closeBanner.exists()) {
      closeBanner.click();
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
    String title = WebHelper.cleanString(recentOrdersTitle.getText());
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
  public ReferralsPage clickOnStartSharingButton () {
    logger.info("Clicking in 'Start sharing' button");
    startSharingButton.click();
    return SdkHelper.create(ReferralsPage.class);
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
}
