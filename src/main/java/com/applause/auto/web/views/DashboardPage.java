package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.web.components.MainMenuChunk;

@Implementation(is = DashboardPage.class, on = Platform.WEB)
public class DashboardPage extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(css = ".dashboard", on = Platform.WEB)
  private Text getViewSignature;

  /* -------- Actions -------- */

  /**
   * Gets Main Menu
   *
   * @return MainMenuChunk
   */
  public MainMenuChunk getMainMenu() {
    logger.info("Getting Main Menu");
    SdkHelper.getSyncHelper().wait(Until.uiElement(getViewSignature).present());
    return SdkHelper.create(MainMenuChunk.class);
  }
}
