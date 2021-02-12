package com.applause.auto.mobile.helpers;

import com.applause.auto.pageobjectmodel.base.ComponentContext;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.factory.LazyWebElement;

public class CustomContainerElement extends ContainerElement {
  public CustomContainerElement(LazyWebElement element, ComponentContext context) {
    super(element, context);
  }

  @Override
  public boolean isDisplayed() {
    return this.element.getAttribute("visible").equalsIgnoreCase("true");
  }
}
