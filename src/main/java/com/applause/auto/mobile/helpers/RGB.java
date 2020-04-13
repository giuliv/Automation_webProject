package com.applause.auto.mobile.helpers;

public class RGB {
  RGB(int color) {
    this.color = color;
  }

  private int color;

  public int getRed() {
    return (this.color & 0x00ff0000) >> 16;
  };

  public int getBlue() {
    return this.color & 0x000000ff;
  };

  public int getGreen() {
    return (this.color & 0x0000ff00) >> 8;
  };
}
