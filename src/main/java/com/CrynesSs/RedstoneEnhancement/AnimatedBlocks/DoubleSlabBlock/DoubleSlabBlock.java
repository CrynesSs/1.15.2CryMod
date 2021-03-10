package com.CrynesSs.RedstoneEnhancement.AnimatedBlocks.DoubleSlabBlock;

import com.CrynesSs.RedstoneEnhancement.RedstoneEnhancement;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = RedstoneEnhancement.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DoubleSlabBlock extends Block {
    private SlabBlock bottomSlab = null;

    public SlabBlock getBottomSlab() {
        return bottomSlab;
    }

    public SlabBlock getTopSlab() {
        return topSlab;
    }

    public SlabType getType() {
        return type;
    }

    private SlabBlock topSlab = null;
    private SlabType type = null;

    public DoubleSlabBlock() {
        super(Properties.create(Material.WOOD));
    }

    public DoubleSlabBlock(SlabBlock block, SlabType type) {
        super(Properties.create(Material.WOOD));
        if (type == SlabType.BOTTOM) {
            this.bottomSlab = block;
        } else {
            this.topSlab = block;
        }
        this.type = type;
    }

    public void setSlab(DoubleSlabBlock slabBlock, SlabBlock block, SlabType type) {
        if (type == SlabType.BOTTOM && slabBlock.type == SlabType.TOP) {
            slabBlock.bottomSlab = block;
            slabBlock.type = SlabType.DOUBLE;
        }
        if (type == SlabType.TOP && slabBlock.type == SlabType.BOTTOM) {
            slabBlock.bottomSlab = block;
            slabBlock.type = SlabType.DOUBLE;
        }
    }

    @Override
    public void onReplaced(@Nonnull BlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        if (bottomSlab != null) {
            if (this.bottomSlab.equals(this.topSlab)) {
                this.dropItems(pos, worldIn, new ItemStack(this.bottomSlab, 2));
            } else {
                this.dropItems(pos, worldIn, new ItemStack(this.bottomSlab, 1));
            }

        }
        if (this.topSlab != null && !this.topSlab.equals(this.bottomSlab)) {
            this.dropItems(pos, worldIn, new ItemStack(this.topSlab, 1));
        }
        super.onReplaced(state, worldIn, pos, newState, isMoving);
    }

    private void dropItems(BlockPos pos, World worldIn, ItemStack stack) {
        double d0 = EntityType.ITEM.getWidth();
        double d1 = 1.0D - d0;
        double d2 = d0 / 2.0D;
        double d3 = Math.floor(pos.getX()) + RANDOM.nextDouble() * d1 + d2;
        double d4 = Math.floor(pos.getY()) + RANDOM.nextDouble() * d1;
        double d5 = Math.floor(pos.getZ()) + RANDOM.nextDouble() * d1 + d2;
        ItemEntity itementity = new ItemEntity(worldIn, d3, d4, d5, stack);
        itementity.setMotion(RANDOM.nextGaussian() * (double) 0.05F, RANDOM.nextGaussian() * (double) 0.05F + (double) 0.2F, RANDOM.nextGaussian() * (double) 0.05F);
        worldIn.addEntity(itementity);
    }

    @SubscribeEvent
    public static void onBlockPlaceEvent(BlockEvent.EntityPlaceEvent event) {
        if (event.getPlacedBlock().getBlock() instanceof SlabBlock) {
            event.setCanceled(true);
            Block placedAgainst = event.getPlacedAgainst().getBlock();
            if (placedAgainst instanceof DoubleSlabBlock && event.getPlacedAgainst().get(SlabBlock.TYPE) != SlabType.DOUBLE) {
                ((DoubleSlabBlock) placedAgainst).setSlab((DoubleSlabBlock) placedAgainst, (SlabBlock) event.getPlacedBlock().getBlock(), event.getPlacedBlock().get(SlabBlock.TYPE));
            } else {
                BlockState state = event.getPlacedBlock();
                SlabBlock block = (SlabBlock) event.getPlacedBlock().getBlock();
                event.getWorld().setBlockState(event.getPos(), new DoubleSlabBlock(block, state.get(SlabBlock.TYPE)).getDefaultState(), 3);
                state.get(SlabBlock.TYPE);
            }

        }
    }

    @Nonnull
    public BlockRenderType getRenderType(@Nonnull BlockState state) {
        return BlockRenderType.MODEL;
    }

}
