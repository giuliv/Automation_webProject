package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Link;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;

@Implementation(is = MyAccountMyOrdersPage.class, on = Platform.WEB)
public class MyAccountMyOrdersPage extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(css = "div.col-main > div > div.page-title > h1", on = Platform.WEB)
  private Text getViewSignature;

  @Locate(css = ".orders-history-holder", on = Platform.WEB)
  private ContainerElement getOrdersPlacedSection;

  @Locate(css = "#my-orders-table tr:nth-child(1) td.td-bold span", on = Platform.WEB)
  private Text getOrdersDateText;

  @Locate(css = "#my-orders-table > tbody > tr:nth-child(1) > td.td-id > span > a", on = Platform.WEB)
  private Link getOrdersNumberLink;

  @Locate(css = "#my-orders-table > tbody > tr:nth-child(1) > td.td-items", on = Platform.WEB)
  private Text getOrdersItemText;

  @Locate(css = "#my-orders-table tr:nth-child(1) td.td-price span.price", on = Platform.WEB)
  private Text getOrdersTotalText;

  @Locate(css = "#my-orders-table tr:nth-child(1) div.status span", on = Platform.WEB)
  private Text getOrdersStatusText;

  @Locate(xpath = "//table[@id='my-orders-table']//tr[1]//a[contains(.,'View')]", on = Platform.WEB)
  private Button getOrdersViewButton;

  @Locate(
      xpath = "//a[contains(.,'Reorder')]",
      on = Platform.WEB)
  private Button getOrdersReorderButton;

  /* -------- Actions -------- */

  /**
   * Verify Orders-Placed Section is Displayed
   *
   * @return boolean
   */
  public boolean isOrdersPlacedSectionDisplayed() {
    logger.info("Verifying Orders-Placed section is displayed");
    return getOrdersPlacedSection.isDisplayed();
  }

  /**
   * Verify Order's Date is Displayed
   *
   * @return boolean
   */
  public boolean isOrdersDateDisplayed() {
    logger.info("Verifying Order's Date is displayed");
    return getOrdersDateText.isDisplayed();
  }

  /**
   * Verify Order's Number is Displayed
   *
   * @return boolean
   */
  public boolean isOrdersNumberDisplayed() {
    logger.info("Verifying Order's Number is displayed");
    return getOrdersNumberLink.isDisplayed();
  }

  /**
   * Click Order's Number
   *
   * @return MyAccountOrderDetailPage
   */
  public MyAccountOrderDetailPage clickOrderNumber() {
    logger.info("Clicking first Order's Number");
    getOrdersNumberLink.click();
    return ComponentFactory.create(MyAccountOrderDetailPage.class);
  }

  /**
   * Verify Order's Item is Displayed
   *
   * @return boolean
   */
  public boolean isOrdersItemDisplayed() {
    logger.info("Verifying Order's Item is displayed");
    return getOrdersItemText.isDisplayed();
  }

  /**
   * Verify Order's Total is Displayed
   *
   * @return boolean
   */
  public boolean isOrdersTotalDisplayed() {
    logger.info("Verifying Order's Total is displayed");
    return getOrdersTotalText.isDisplayed();
  }

  /**
   * Verify Order's Status is Displayed
   *
   * @return boolean
   */
  public boolean isOrdersStatusDisplayed() {
    logger.info("Verifying Order's Status is displayed");
    return getOrdersStatusText.isDisplayed();
  }

  /**
   * Verify Order's View button is Displayed
   *
   * @return boolean
   */
  public boolean isViewButtonDisplayed() {
    logger.info("Verifying Order's View button is displayed");
    return getOrdersViewButton.isDisplayed();
  }

  /**
   * Verify Order's Reorder button is Displayed
   *
   * @return boolean
   */
  public boolean isReorderButtonDisplayed() {
    logger.info("Verifying Order's Reorder button is displayed");
    return getOrdersReorderButton.isDisplayed();
  }
}
