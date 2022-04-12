package com.applause.auto.common.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum HomepageSubscriptionsModuleMenu {
  FREQUENT_BREWER(
      "Frequent Brewer",
      "Get your favorites delivered how you want, when you want. Or, take our Coffee Match Quiz to get matched.",
      0,
      "all-coffee",
      "/collections/all-coffee"),
  SINGLE_ORIGIN(
      "Single Origin Series",
      "Experience a taste of place and the nuanced flavor that origins and production methods impart into every cup.",
      1,
      "single-origin-series",
      "/products/single-origin-series-subscription"),
  SMALL_BATCH(
      "Small Batch Series",
      "Be on the cutting edge of coffee. Rare and unique finds, selectively sourced in small batches from around the world.",
      2,
      "small-batch-series",
      "/products/small-batch-series-subscription"),
  SIGNATURE_BLEND(
      "Signature Blend Series",
      "Your passport to a world of flavor and complexity. Take a trip with the curated, artful blends that made Peet’s famous.",
      3,
      "signature-blend-series",
      "/products/signature-blend-series-subscription");
  @Getter private String header;
  @Getter private String description;
  @Getter private Integer indexPosition;
  @Getter private String id;
  @Getter private String urlPart;

  public String getHeader() {
    return header.toLowerCase();
  }

  public String getDescription() {
    return description.toLowerCase();
  }

  public Integer getPosition() {
    return indexPosition;
  }

  public String getID() {
    return id;
  }

  public String getURLPart() {
    return urlPart;
  }
}
