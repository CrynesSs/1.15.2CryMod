package com.CrynesSs.RedstoneEnhancement.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ItemNBT extends Item {
    public ItemNBT(Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public ActionResultType onItemUse(@Nonnull ItemUseContext context) {
        CompoundNBT nbt = new CompoundNBT();
        if (context.getItem().hasTag()) {
            nbt = context.getItem().getTag();
        }

        if (nbt != null && nbt.contains("Uses")) {
            nbt.putInt("Uses", nbt.getInt("Uses") + 1);
        } else if (nbt != null) {
            nbt.putInt("Uses", 1);
        }
        context.getItem().setTag(nbt);
        return ActionResultType.PASS;
    }

    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flagIn) {
        if (stack.hasTag() && stack.getTag() != null && stack.getTag().contains("Uses")) {
            tooltip.add(new StringTextComponent(String.valueOf(stack.getTag().getInt("Uses"))));
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

}
