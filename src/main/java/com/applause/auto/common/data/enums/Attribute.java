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
  TITLE("title"),
  DISABLED("disabled"),
  ARIA_LABEL("aria-label"),
  DATA_VALUE("data-value");

  @Getter private String value;
}
