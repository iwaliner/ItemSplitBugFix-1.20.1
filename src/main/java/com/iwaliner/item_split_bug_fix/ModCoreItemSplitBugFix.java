package com.iwaliner.item_split_bug_fix;

import com.mojang.logging.LogUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ContainerScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.ItemStackedOnOtherEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;


@Mod(ModCoreItemSplitBugFix.MODID)
public class ModCoreItemSplitBugFix
{
     public static final String MODID = "item_split_bug_fix";

    public ModCoreItemSplitBugFix() {
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
    public static boolean isSplitItemStack(ItemStack stack){
        return stack.getTag()!=null&&stack.getTag().isEmpty();
    }
    public static void fixBug(ItemStack stack){
        if(ModCoreItemSplitBugFix.isSplitItemStack(stack)){
           stack.setTag(null);
        }
    }

    @SubscribeEvent
    public void ItemTooltipEvent(ItemTooltipEvent event){
        if(ModCoreItemSplitBugFix.isSplitItemStack(event.getItemStack())){
               event.getToolTip().add(Component.literal("has an empty NBT tag! This is a bug!").withStyle(ChatFormatting.LIGHT_PURPLE));
        }
    }

}
