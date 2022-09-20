package com.applause.auto.common.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum LearnSubMenu {
  SOURCES_WITH_IMPACT("Sourcing with Impact","/pages/impact"),
  RECIPES_BLOG("Recipes + Blog", "/blogs/peets"),
  WHY_PEETS("Why Peet’s?", "/pages/commitment-to-craft"),
  BREW_GUIDES("Brew Guides", "/pages/brew-guides"),
  PEETS_HISTORY("Peet’s History", "/pages/timeline");
  //SOCIAL_RESPONSIBILITY("Social Responsibility", "/pages/social-responsibility");

  @Getter private String text;
  @Getter private String link;

  public String getText() {
    return text;
  }

  public String getLink() {
    return link;
  }
}
