package com.applause.auto.common.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum LearnSubMenu {
  COFFEE_REVOLUTION("Our Coffee Revolution", "/pages/timeline"),
  COMMITMENT_CRAFT("Commitment to Craft", "/pages/commitment-to-craft"),
  SOCIAL_RESPONSIBILITY("Social Responsibility", "/pages/social-responsibility"),
  BREW_GUIDES("Brew Guides", "/pages/brew-guides"),
  CUPPING_ROOM("The Cupping Room", "/blogs/peets");

  @Getter private String text;
  @Getter private String link;

  public String getText() {
    return text;
  }

  public String getLink() {
    return link;
  }
}
