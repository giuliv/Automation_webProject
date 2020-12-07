package com.applause.auto.web.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.web.views.DashboardPage;

@Implementation(is = DashboardModalChunk.class, on = Platform.WEB)
public class DashboardModalChunk extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(css = "div#modal-new-message-2018 a", on = Platform.WEB)
  private Button getExploreDashboardButton;

  @Locate(css = "#modal-new-message-2018 .close-button", on = Platform.WEB)
  private Button getCloseDashboardModalButton;

  /* -------- Actions -------- */

  /**
   * Click Explore Dashboard Button
   *
   * @return DashboardPage
   */
  public DashboardPage clickExploreDashboard() {
    logger.info("Clicking Explore Dashboard Button");
    getExploreDashboardButton.click();
    return this.create(DashboardPage.class);
  }

  /**
   * Click Close Button on explore-dashboard-modal
   *
   * @return DashboardPage
   */
  public DashboardPage clickCloseModal() {
    logger.info("Clicking Close button on Dashboard Modal");
    getCloseDashboardModalButton.click();
    return this.create(DashboardPage.class);
  }
}
