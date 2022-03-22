package com.applause.auto.common.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum TeaSubMenu {

    BEST_SELLERS("Best Sellers", "Featured", "tea-best-sellers"),
    SIGNATURE_TEA_BLENDS("Signature Tea Blends", "Featured", "signature-tea-blends"),
    TEA_FINDER("Tea Finder", "Featured", "tea-finder"),

    GREEN("Green", "Type", "green-tea"),
    HERBAL("Herbal", "Type", "herbal-tea"),
    MATCHA("Matcha", "Type", "matcha-tea"),
    OOLONG("Oolong", "Type", "oolong-tea"),
    WHITE("White", "Type", "white-tea"),
    BLACK("Black", "Type", "black-tea"),

    LOOSE_LEAF("Loose Leaf", "Format", "loose-leaf"),
    TEA_BAGS("Tea Bags", "Format", "tea-bags"),
    ICED_TEA("Iced Tea", "Format", "iced-tea"),

    ALL_TEA("All Tea", "None", "all-tea");
    @Getter private String text;
    @Getter private String menuLocation;
    @Getter private String link;

    public String getText() {
        return text;
    }

    public String getMenuLocation() {
        return menuLocation;
    }

    public String getLink() {
        return link;
    }
}