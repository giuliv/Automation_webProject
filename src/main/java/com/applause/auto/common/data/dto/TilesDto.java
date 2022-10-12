package com.applause.auto.common.data.dto;

public class TilesDto {
  String title;
  String description;
  String action;

  public TilesDto(String title, String description, String action) {
    this.title = title;
    this.description = description;
    this.action = action;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public String getAction() {
    return action;
  }
}
