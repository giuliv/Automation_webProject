package com.applause.auto.mobile.components.tooltips;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.base.BaseComponent;

import io.qameta.allure.Step;

@Implementation(is = SwipeTooltipComponent.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = SwipeTooltipComponent.class, on = Platform.MOBILE_IOS)
public class SwipeTooltipComponent extends BaseTooltipComponent {

  @Step("Close Swipe Tooltip")
  public <T extends BaseComponent> T closeTooltipIfDisplayed(Class<T> clazz) {
		return closeAnyTooltipIfDisplayed(2, clazz);
  }
}
