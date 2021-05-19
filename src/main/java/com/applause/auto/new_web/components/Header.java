package com.applause.auto.new_web.components;

import com.applause.auto.common.data.Constants;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.integrations.helpers.SdkHelper;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.helper.sync.Until;

@Implementation(is = Header.class, on = Platform.WEB)
@Implementation(is = Header.class, on = Platform.WEB_MOBILE_PHONE)
public class Header extends BaseComponent {

  @Locate(id = "header", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Locate(css = "a[data-id='coffee-nav']", on = Platform.WEB)
  private Button coffeeCategory;

  @Locate(css = ".nav__columns a[href*='%s']", on = Platform.WEB)
  private Button subCategories;

  @Override
  public void afterInit() {
    getSyncHelper().wait(Until.uiElement(mainContainer).present());
  }

  /* -------- Actions -------- */

  public void hoverCategoryFromMenu(Constants.MenuOptions menuOptions) {
    logger.info("Tab selected: " + menuOptions.name());
    if (menuOptions.equals(Constants.MenuOptions.COFFEE)) {
      WebHelper.hoverByAction(coffeeCategory);
    }
  }

  public <T extends BaseComponent> T clickOverSubCategoryFromMenu(
      Class<T> clazz, Constants.MenuSubCategories menuSubCategories) {
    logger.info("SubCategory selected " + (menuSubCategories.name()));
    subCategories.format(menuSubCategories.getMenuSubCategories()).initialize();

    getSyncHelper().wait(Until.uiElement(subCategories).visible());
    subCategories.click();

    return SdkHelper.create(clazz);
  }
}
