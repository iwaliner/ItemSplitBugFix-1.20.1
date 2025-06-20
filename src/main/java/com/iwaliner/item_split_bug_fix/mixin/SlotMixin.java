package com.iwaliner.item_split_bug_fix.mixin;

import com.iwaliner.item_split_bug_fix.ModCoreItemSplitBugFix;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Slot.class)

public abstract class SlotMixin {
    @Shadow @Final public Container container;

    @Shadow @Final private int slot;

    @Inject(method = "getItem",at = @At("HEAD"), cancellable = true)
    private void getItemInject(CallbackInfoReturnable<ItemStack> cir){
        if(this.container!=null) {
            ItemStack stack = container.getItem(this.slot);
            if (ModCoreItemSplitBugFix.isSplitItemStack(stack)) {
                stack.setTag(null);
                cir.setReturnValue(stack);
            }

        }
    }
    @Inject(method = "set",at = @At("HEAD"), cancellable = true)
    private void setInject(ItemStack stack, CallbackInfo ci){
        ModCoreItemSplitBugFix.fixBug(stack);
    }

    @Inject(method = "onQuickCraft",at = @At("HEAD"), cancellable = true)
    private void onQuickCraftInject(ItemStack stack1, ItemStack stack2,CallbackInfo ci){
        ModCoreItemSplitBugFix.fixBug(stack1);
        ModCoreItemSplitBugFix.fixBug(stack2);
    }
    @Inject(method = "onTake",at = @At("HEAD"), cancellable = true)
    private void onTakeInject(Player player, ItemStack stack, CallbackInfo ci){
        ModCoreItemSplitBugFix.fixBug(stack);
    }
    @Inject(method = "mayPlace",at = @At("HEAD"), cancellable = true)
    private void mayPlaceInject(ItemStack stack, CallbackInfoReturnable<Boolean> cir){
        ModCoreItemSplitBugFix.fixBug(stack);
    }
    @Inject(method = "setByPlayer",at = @At("HEAD"), cancellable = true)
    private void setByPlayerInject(ItemStack stack, CallbackInfo ci){
        ModCoreItemSplitBugFix.fixBug(stack);
    }
}
