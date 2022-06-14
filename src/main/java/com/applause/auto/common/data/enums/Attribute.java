package com.applause.auto.common.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Attribute {
  CLASS("class"),
  COLOR("color"),
  VALUE("value"),
  HREF("href"),
  INNER_HTML("innerHTML"),
  NAME("name"),
  ARIA_EXPANDED("aria-expanded"),
  STYLE("style"),
  TEXT("text"),
  TRANSFORM("transform"),
  PLACEHOLDER("placeholder"),
  BORDER_RADIUS("border-radius"),
  BACKGROUND_COLOR("background-color"),
  SRCSET("srcset"),
  ID("id"),
  TITLE("title");

  @Getter private String value;
}
