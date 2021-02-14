package com.applause.auto.mobile.helpers;

import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.LazyList;

import java.util.List;
import java.util.stream.Collectors;

public class ItemOptions {
  private List<String> options;
  private boolean isAndroid = true;

  public ItemOptions(List<String> androidOptions) {
    this.options = androidOptions;
  }

  public ItemOptions(LazyList<Text> item) {
    this.options = item.stream().map(i -> i.getText()).collect(Collectors.toList());
  }

  public boolean contains(String item) {
    return options.contains(item);
  }
}
