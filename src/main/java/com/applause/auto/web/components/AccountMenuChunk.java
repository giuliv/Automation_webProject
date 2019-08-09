package com.applause.auto.web.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.web.views.Landing;

@Implementation(is = AccountMenuChunk.class, on = Platform.WEB)
public class AccountMenuChunk extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(css = "a[href='#header-account']", on = Platform.WEB)
  private Button getExpandMenuButton;

  @Locate(css = "#header-account a[title='Log Out']", on = Platform.WEB)
  private Button getSignOutButton;

  /* -------- Actions -------- */

  /**
   * Click Close Button on explore-dashboard-modal
   *
   * @return DashboardPage
   */
  public Landing signOut() {
    logger.info("Expanding menu");
    getExpandMenuButton.click();
    logger.info("Click on Sign Out button");
    getSignOutButton.click();
    return ComponentFactory.create(Landing.class);
  }
}
