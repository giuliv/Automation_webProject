package com.applause.auto.web.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.web.helpers.WebHelper;
import com.applause.auto.web.views.Landing;
import lombok.Getter;

@Implementation(is = AccountMenuChunk.class, on = Platform.WEB)
public class AccountMenuChunk extends BaseComponent {

  /* -------- Elements -------- */

  @Getter
  @Locate(css = "a[href='#header-account']", on = Platform.WEB)
  private Button getExpandMenuButton;

  @Getter
  @Locate(css = "#header-account a[title='Log Out']", on = Platform.WEB)
  private Button getSignOutButton;

  @Getter
  @Locate(css = ".desktop-only .icon--account-black", on = Platform.WEB)
  private Button getAccountButton;

  @Getter
  @Locate(css = ".header .icon--search-black", on = Platform.WEB)
  private Button getSearchButton;

  @Getter
  @Locate(css = ".header .icon--store-locator", on = Platform.WEB)
  private Button getStoreLocatorButton;

  @Getter
  @Locate(css = ".header .icon--cart-black", on = Platform.WEB)
  private Button getCartButton;

  /* -------- Actions -------- */

  /**
   * Click Close Button on explore-dashboard-modal
   *
   * @return DashboardPage
   */
  public Landing signOut() {
    logger.info("Expanding menu");
    WebHelper.jsClick(getExpandMenuButton.getWebElement());
    logger.info("Click on Sign Out button");
    WebHelper.jsClick(getSignOutButton.getWebElement());
    return SdkHelper.create(Landing.class);
  }
}
