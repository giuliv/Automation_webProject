package com.applause.auto.new_web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.new_web.components.Header;
import com.applause.auto.new_web.components.MyAccountLeftMenu;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import io.qameta.allure.Step;

@Implementation(is = Base.class, on = Platform.WEB)
@Implementation(is = Base.class, on = Platform.WEB_MOBILE_PHONE)
public class Base extends BaseComponent {

  @Step("Get header")
  public Header getHeader() {
    logger.info("Getting the header");
    return SdkHelper.create(Header.class);
  }

  @Step("Get Left menu")
  public MyAccountLeftMenu getLeftMenu() {
    logger.info("Getting the left menu");
    return SdkHelper.create(MyAccountLeftMenu.class);
  }
}
