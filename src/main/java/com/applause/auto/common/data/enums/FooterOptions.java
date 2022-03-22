package com.applause.auto.common.data.enums;

import com.applause.auto.new_web.views.CommonWebPage;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum FooterOptions {

  // HELP CENTER
  CONTACT_US(
      "Help center",
      "Contact Us",
      "https://peets-coffee-staging.myshopify.com/pages/contact-us",
      CommonWebPage.class),
  HELP_CENTER("Help center", "Help Center", "https://faq.peets.com/hc/en-us", CommonWebPage.class),
  SHIPPING_AND_RETURNS(
      "Help center",
      "Shipping & Returns",
      "https://peets-coffee-staging.myshopify.com/pages/shipping",
      CommonWebPage.class),

  // Company
  CAREERS(
      "Company",
      "Careers",
      "https://peets-coffee-staging.myshopify.com/pages/join-peets",
      CommonWebPage.class),
  OUR_PEOPLE(
      "Company",
      "Our People",
      "https://peets-coffee-staging.myshopify.com/pages/our-people",
      CommonWebPage.class),
  NEWSROOM(
      "Company",
      "Newsroom",
      "https://peets-coffee-staging.myshopify.com/pages/newsroom",
      CommonWebPage.class),
  CODE_OF_ETHICS(
      "Company",
      "Code of Ethics",
      "https://peets-coffee-staging.myshopify.com/pages/code-of-ethics",
      CommonWebPage.class),
  SUPPLY_CHAIN_TRANSPARENCY(
      "Company",
      "Supply Chain Transparency",
      "https://peets-coffee-staging.myshopify.com/pages/supply-chain-transparency",
      CommonWebPage.class),

  // Gift cards
  SHOP_GIFTS_CARDS(
      "Gift cards",
      "Shop Gift Cards",
      "https://peets-coffee-staging.myshopify.com/pages/giftcards",
      CommonWebPage.class),

  // OFFERS
  CURRENT_OFFERS(
      "Offers",
      "Current Offers",
      "https://peets-coffee-staging.myshopify.com/pages/current-offers",
      CommonWebPage.class),
  GET_FREE_COFFEE(
      "Offers",
      "GET FREE COFFEE",
      "https://peets-coffee-staging.myshopify.com/pages/refer?traffic_source=site_footer",
      CommonWebPage.class);

  @Getter private String category;
  @Getter private String option;
  @Getter private String url;
  @Getter private Class clazz;

  public static List<FooterOptions> getHelpCenterOptions() {
    return getOptions("Help center");
  }

  public static List<FooterOptions> getCompanyOptions() {
    return getOptions("Company");
  }

  public static List<FooterOptions> getGiftsOptions() {
    return getOptions("Gift cards");
  }

  public static List<FooterOptions> getOffersOptions() {
    return getOptions("Offers");
  }

  private static List<FooterOptions> getOptions(String category) {
    return Arrays.stream(FooterOptions.values())
        .filter(v -> v.getCategory().equals(category))
        .collect(Collectors.toList());
  }
}
