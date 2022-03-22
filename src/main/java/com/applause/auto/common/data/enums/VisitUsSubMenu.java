package com.applause.auto.common.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum VisitUsSubMenu {

    FIND_COFFEE_BAR("Find a Coffeebar", "/pages/store-locator"),
    VIEW_MENU_ORDER("View Menu and Order", "/pages/menu"),
    PEETS_APP("The Peet's App", "/pages/peetnik-rewards"),
    ORDER_NOW("Order Now", "peets.app.link/");

    @Getter private String text;
    @Getter private String link;

    public String getText() {
        return text;
    }

    public String getLink() {
        return link;
    }
}