package com.iwaliner.item_split_bug_fix.mixin;

import com.iwaliner.item_split_bug_fix.ModCoreItemSplitBugFix;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)

public abstract class ItemStackMixin {


    @Inject(method = "split",at = @At("HEAD"), cancellable = true)
    private void splitInject(CallbackInfoReturnable<ItemStack> cir){
        ModCoreItemSplitBugFix.fixBug(((ItemStack) (Object)this));
    }
    @Inject(method = "copy",at = @At("HEAD"), cancellable = true)
    private void copyInject(CallbackInfoReturnable<ItemStack> cir){
        ModCoreItemSplitBugFix.fixBug(((ItemStack) (Object)this));
    }
    @Inject(method = "isSameItemSameTags",at = @At("HEAD"), cancellable = true)
    private static void isSameItemSameTagsInject(ItemStack stack1, ItemStack stack2, CallbackInfoReturnable<Boolean> cir){
        ModCoreItemSplitBugFix.fixBug(stack1);
        ModCoreItemSplitBugFix.fixBug(stack2);
    }
    @Inject(method = "isSameItem",at = @At("HEAD"), cancellable = true)
    private static void isSameItemInject(ItemStack stack1, ItemStack stack2, CallbackInfoReturnable<Boolean> cir){
        ModCoreItemSplitBugFix.fixBug(stack1);
        ModCoreItemSplitBugFix.fixBug(stack2);
    }
    @Inject(method = "getCount",at = @At("HEAD"), cancellable = true)
    private void getCountInject(CallbackInfoReturnable<Integer> cir){
        ModCoreItemSplitBugFix.fixBug(((ItemStack) (Object)this));
    }
    @Inject(method = "setCount",at = @At("HEAD"), cancellable = true)
    private void setCountInject(int p_41765_, CallbackInfo ci){
        ModCoreItemSplitBugFix.fixBug(((ItemStack) (Object)this));
    }


}
