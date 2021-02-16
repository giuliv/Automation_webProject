package com.applause.auto.mobile.helpers;

import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.LazyList;

import java.util.List;
import java.util.stream.Collectors;

public class ItemOptions {
  private List<String> options;
  private String iosOptions;
  private boolean isAndroid = true;

  public ItemOptions(List<String> androidOptions) {
    this.options = androidOptions;
  }

  public ItemOptions(LazyList<Text> item) {
    this.iosOptions = item.stream().map(i -> i.getText()).collect(Collectors.joining());
  }

  public boolean contains(String item) {
    return isAndroid ? options.contains(item) : iosOptions.contains(item);
  }
}
