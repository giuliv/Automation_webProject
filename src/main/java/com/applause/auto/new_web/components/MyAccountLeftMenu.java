package com.applause.auto.new_web.components;

import com.applause.auto.common.data.Constants.MyAccountLeftMenuOption;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.new_web.views.HomePage;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Link;
import com.applause.auto.pageobjectmodel.elements.SelectList;
import io.qameta.allure.Step;

@Implementation(is = MyAccountLeftMenu.class, on = Platform.WEB)
@Implementation(is = PhoneMyAccountLeftMenu.class, on = Platform.WEB_MOBILE_PHONE)
public class MyAccountLeftMenu extends BaseComponent {

  @Locate(id = "acNav", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Locate(xpath = "//a[./span[normalize-space()=\"%s\"]]", on = Platform.WEB)
  private Link getMenuOptionButton;

  @Locate(css = ".ac-nav__logout-btn", on = Platform.WEB)
  private Link getLogoutButton;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(mainContainer).present());
  }

  @Step("Verify all left menu options are displayed")
  public boolean menuDisplaysAllOptions() {
    for (MyAccountLeftMenuOption option : MyAccountLeftMenuOption.values()) {

      if (!getMenuOptionButton(option).exists()) {
        logger.error("Left menu option [{}] isn't displayed", option.getValue());
        return false;
      }
    }
    return true;
  }

  @Step("Verify Logout button is displayed")
  public boolean isLogoutButtonDisplayed() {
    return getLogoutButton.isDisplayed();
  }

  @Step("Click Logout button")
  public HomePage clickLogoutButton() {
    logger.info("Clicking Logout button");
    SdkHelper.getSyncHelper().wait(Until.uiElement(getLogoutButton).present());
    SdkHelper.getBrowserControl().jsClick(getLogoutButton);
    return SdkHelper.create(HomePage.class);
  }

  @Step("Click left menu option")
  public <T extends BaseComponent> T clickMenuOption(MyAccountLeftMenuOption option) {
    logger.info("Clicking menu option [{}]", option.getValue());
    getMenuOptionButton(option).click();
    return (T) SdkHelper.create(option.getClazz());
  }

  private Link getMenuOptionButton(MyAccountLeftMenuOption option) {
    getMenuOptionButton.format(option.getValue()).initialize();
    return getMenuOptionButton;
  }
}

class PhoneMyAccountLeftMenu extends MyAccountLeftMenu {

  @Step("Click left menu option")
  public <T extends BaseComponent> T clickMenuOption(MyAccountLeftMenuOption option) {
    logger.info("Clicking menu option [{}]", option.getValue());
    getAccountDropdown.select(option.getValue());
    return (T) SdkHelper.create(option.getClazz());
  }

  @Locate(id = "accountDropdown", on = Platform.WEB)
  private SelectList getAccountDropdown;
}
