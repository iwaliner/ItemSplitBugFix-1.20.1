package com.iwaliner.item_split_bug_fix;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import net.minecraftforge.registries.ForgeRegistries;


@Mod(ModCoreItemSplitBugFix.MODID)
public class ModCoreItemSplitBugFix {
    public static final String MODID = "item_split_bug_fix";

    private static List<Pattern> blacklistPattern = new ArrayList<>();
    private static Set<Item> blacklistCache = new HashSet<>();
    private static Set<Item> checkedItemsCache = new HashSet<>();

    public ModCoreItemSplitBugFix() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigItemSplitBugFix.CONFIG_SPEC,"ItemSplitBugFix.toml");
        MinecraftForge.EVENT_BUS.register(this);
    }

    /*@SubscribeEvent
     public void ItemOverEvent(ItemStackedOnOtherEvent event){
        fixBug(event.getCarriedItem());
        fixBug(event.getStackedOnItem());
     }
    @SubscribeEvent
    public void ItemCraftedEvent(PlayerEvent.ItemCraftedEvent event){
        fixBug(event.getCrafting());
    }
    @SubscribeEvent
    public void ItemSmeltedEvent(PlayerEvent.ItemSmeltedEvent event){
        fixBug(event.getSmelting());
    }

    @SubscribeEvent
    public void ItemMenuEvent(ContainerScreenEvent event){
        for(int i=0;i<event.getContainerScreen().getMenu().getItems().size();i++) {
            fixBug(event.getContainerScreen().getMenu().getItems().get(i));
        }
    }
    @SubscribeEvent
    public void ItemMenuEvent(PlayerContainerEvent event){
        for(int i=0;i<event.getContainer().getItems().size();i++) {
            fixBug(event.getContainer().getItems().get(i));
        }
    }*/

    private static boolean isItemOnBlacklist(ItemStack stack){
        if (stack == null || stack.isEmpty() || blacklistPattern.isEmpty()) {
            return true;
        }
        Item item = stack.getItem();
        if(blacklistCache.contains(item)){
            return true;
        }
        if(checkedItemsCache.contains(item)){
            return false;
        }

        ResourceLocation RLItemId = ForgeRegistries.ITEMS.getKey(item);
        if(RLItemId == null)
            return true;
        String id = RLItemId.toString();

        prepareBlacklist();
        for(Pattern p : blacklistPattern) {
            if(p.matcher(id).matches()) {
                blacklistCache.add(item);
                return true;
            }
        }
        checkedItemsCache.add(item);
        return false;
    }


    public static boolean isSplitItemStack(ItemStack stack) {
        prepareBlacklist();
        if (isItemOnBlacklist(stack))   return false;
        return stack.getTag() != null && stack.getTag().isEmpty();
    }

    public static void prepareBlacklist() {
        if(!blacklistPattern.isEmpty()){
            return;
        }

        List<? extends String> list = ConfigItemSplitBugFix.BLACKLIST_ITEMS.get();
        if(list != null) {
            for(String s : list) {
                String regex = s.replace("*", ".*");
                blacklistPattern.add(Pattern.compile(regex));
            }
            blacklistCache.clear();
            checkedItemsCache.clear();
        }
    }

    public static void fixBug(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return;
        if (isItemOnBlacklist(stack))   return;
        if (ModCoreItemSplitBugFix.isSplitItemStack(stack)) {
            stack.setTag(null);
        }
    }

    @SubscribeEvent
    public void ItemTooltipEvent(ItemTooltipEvent event) {
        if (ModCoreItemSplitBugFix.isSplitItemStack(event.getItemStack())) {
            event.getToolTip().add(Component.literal("has an empty NBT tag! This is a bug!").withStyle(ChatFormatting.LIGHT_PURPLE));
        }
    }

}
