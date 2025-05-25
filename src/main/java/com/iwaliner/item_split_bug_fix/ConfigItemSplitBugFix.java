package com.iwaliner.item_split_bug_fix;

import org.apache.commons.lang3.tuple.Pair;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class ConfigItemSplitBugFix {
    public static ConfigItemSplitBugFix CONFIG;
    public static ForgeConfigSpec CONFIG_SPEC;

    public static ForgeConfigSpec.ConfigValue<List<? extends String>> BLACKLIST_ITEMS;

    static {
        Pair<ConfigItemSplitBugFix, ForgeConfigSpec> pair =
            new ForgeConfigSpec.Builder().configure(ConfigItemSplitBugFix::new);
        //Store the resulting values
        CONFIG = pair.getLeft();
        CONFIG_SPEC = pair.getRight();
    }


    private ConfigItemSplitBugFix(ForgeConfigSpec.Builder builder) {
        builder.comment("Config file for ItemSplitBugFix");

        builder.push("Blacklist Settings");

        BLACKLIST_ITEMS = builder
            .comment("Items in this list will not be modified by ItemSplitBugFix.",
                "Supports wildcard '*'. For example:",
                "- 'minecraft:diamond' matches exactly 'minecraft:diamond'",
                "- '*:diamond*' matches any item from any mod that contains 'diamond'",
                "- 'minecraft:*' matches all items from minecraft namespace",
                "- '*nec*' matches any item that contains 'nec', eg. 'necromancy:abc', 'my_mod:necklace'")
            // .translation("config.itemsplitbugfix.blacklist_items")   // @debug: no need for translation files
            .define("Blacklist Items", List.of(
                "alexscaves:sack_of_sating",
                "hexerei:*",
                "spelunkery:*",
                "minecraft:spyglass",
                "minecraft:air"
            ));

        builder.pop();

        CONFIG_SPEC = builder.build();
    }
}
