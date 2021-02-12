package com.applause.auto.mobile.helpers;

import java.util.List;

public class ItemOptions {
  private String iosOptions;
  private String iosQty;
  private List<String> androidOptions;
  private boolean isAndroid = true;

  public ItemOptions(List<String> androidOptions) {
    this.androidOptions = androidOptions;
    this.isAndroid = true;
  }

  public ItemOptions(String iosOptions, String iosQty) {
    this.iosOptions = iosOptions;
    this.iosQty = iosQty;
    isAndroid = false;
  }

  public boolean contains(String item) {
    if (isAndroid) {
      return this.androidOptions.contains(item);
    } else {
      return (this.iosOptions.contains(item) || this.iosQty.contains(item));
    }
  }
}
