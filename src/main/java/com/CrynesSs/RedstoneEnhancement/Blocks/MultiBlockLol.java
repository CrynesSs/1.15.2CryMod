package com.CrynesSs.RedstoneEnhancement.Blocks;

import com.CrynesSs.RedstoneEnhancement.RedstoneEnhancement;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

@Mod.EventBusSubscriber(modid = RedstoneEnhancement.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MultiBlockLol extends Block {
    int[] dimensions = new int[3];

    public MultiBlockLol(int sizeX, int sizeY, int sizeZ) {
        super(Properties.create(Material.ANVIL));
        dimensions[0] = sizeX;
        dimensions[1] = sizeY;
        dimensions[2] = sizeZ;
    }

    public MultiBlockLol() {
        this(3, 3, 3);
    }


    @SubscribeEvent
    public static void playerInteract(PlayerInteractEvent.RightClickBlock event) {
        System.out.println(event.getItemStack().getItem().getRegistryName() + "lol1");
        if (Objects.requireNonNull(event.getItemStack().getItem().getRegistryName()).toString().equals("redsenhance:lol")) {
            if (!MultiBlockLol.checkIfSpace(null, new MultiBlockLol(3, 3, 3), event.getWorld(), event.getPos(), event.getFace())) {
                if (event.isCancelable()) {
                    event.setCanceled(true);
                    if (event.getWorld().isRemote) {
                        event.getEntity().sendMessage(new StringTextComponent("Placement Cancelled, no Space bruh"));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onBlockPlaceEvent(BlockEvent.EntityPlaceEvent event) {
        if (event.getPlacedBlock().getBlock() instanceof MultiBlockLol && event.getEntity() instanceof PlayerEntity) {
            System.out.println("Placing lol");
            if (!checkIfSpace(event, (MultiBlockLol) event.getPlacedBlock().getBlock(), null, null, getFacing(event.getEntity(), event.getPos()))) {
                if (event.isCancelable()) {
                    PlayerEntity entity = (PlayerEntity) event.getEntity();
                    //entity.inventory.placeItemBackInInventory((World) event.getWorld(), new ItemStack(entity.getHeldItemMainhand().getItem(), 1));
                    entity.sendMessage(new StringTextComponent("Placement Cancelled, no Space bruh"));
                    System.out.println("Cancelling lol");
                    event.setCanceled(true);
                }
            }
        }
    }

    public static Direction getFacing(Entity placer, BlockPos pos) {
        return Direction.getFacingFromVector(placer.getPosX() - pos.getX(), placer.getPosY() - pos.getY(), placer.getPosZ() - pos.getZ()).getOpposite();
    }

    @Override
    public void onBlockPlacedBy(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nullable LivingEntity placer, @Nonnull ItemStack stack) {
        //check here if your structure has a specific direction.
        if (placer != null && checkIfSpace(null, this, worldIn, pos, getFacing(placer, pos))) {
            spawnBlock(worldIn, pos, getFacing(placer, pos));
        }
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }

    public static boolean checkIfSpace(@Nullable BlockEvent.EntityPlaceEvent event, MultiBlockLol lol, @Nullable World w, @Nullable BlockPos pos, Direction d) {
        for (int i = 0; i < lol.dimensions[0]; i++) {
            for (int j = 0; j < lol.dimensions[1]; j++) {
                for (int k = 0; k < lol.dimensions[2]; k++) {
                    if (i + j + k == 0) {
                        break;
                    }
                    if (event != null) {
                        System.out.println("i" + i + "k" + k + "j" + j + "pos " + event.getPos().add(i, j, k));
                    }
                    if (w != null && pos != null) {
                        switch (d) {
                            case EAST:
                            case UP:
                            case DOWN: {
                                if (!Objects.requireNonNull(w.getBlockState(pos.add(i, j, k)).getBlock().getRegistryName()).toString().equals("minecraft:air")) {
                                    return false;
                                }
                                break;
                            }
                            case WEST: {
                                if (!Objects.requireNonNull(w.getBlockState(pos.add(-i, j, -k)).getBlock().getRegistryName()).toString().equals("minecraft:air")) {
                                    return false;
                                }
                                break;
                            }
                            case SOUTH: {
                                if (!Objects.requireNonNull(w.getBlockState(pos.add(-i, j, k)).getBlock().getRegistryName()).toString().equals("minecraft:air")) {
                                    return false;
                                }
                                break;
                            }
                            case NORTH: {
                                if (!Objects.requireNonNull(w.getBlockState(pos.add(i, j, -k)).getBlock().getRegistryName()).toString().equals("minecraft:air")) {
                                    return false;
                                }
                                break;
                            }
                        }
                    } else if (event != null) {
                        switch (d) {
                            case UP:
                            case DOWN:
                            case EAST: {
                                if (!Objects.requireNonNull(event.getWorld().getBlockState(event.getPos().add(i, j, k)).getBlock().getRegistryName()).toString().equals("minecraft:air")) {
                                    return false;
                                }
                                break;
                            }
                            case WEST: {
                                if (!Objects.requireNonNull(event.getWorld().getBlockState(event.getPos().add(-i, j, -k)).getBlock().getRegistryName()).toString().equals("minecraft:air")) {
                                    return false;
                                }
                                break;
                            }
                            case SOUTH: {
                                if (!Objects.requireNonNull(event.getWorld().getBlockState(event.getPos().add(-i, j, k)).getBlock().getRegistryName()).toString().equals("minecraft:air")) {
                                    return false;
                                }
                                break;
                            }
                            case NORTH: {
                                if (!Objects.requireNonNull(event.getWorld().getBlockState(event.getPos().add(i, j, -k)).getBlock().getRegistryName()).toString().equals("minecraft:air")) {
                                    return false;
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    void spawnBlock(World w, BlockPos pos, Direction d) {
        for (int i = 0; i < this.dimensions[0]; i++) {
            for (int j = 0; j < this.dimensions[1]; j++) {
                for (int k = 0; k < this.dimensions[2]; k++) {
                    switch (d) {
                        case UP:
                        case DOWN:
                        case EAST: {
                            w.setBlockState(pos.add(i, j, k), Blocks.IRON_BLOCK.getDefaultState(), 3);
                            break;
                        }
                        case WEST: {
                            w.setBlockState(pos.add(-i, j, -k), Blocks.IRON_BLOCK.getDefaultState(), 3);
                            break;
                        }
                        case SOUTH: {
                            w.setBlockState(pos.add(-i, j, k), Blocks.IRON_BLOCK.getDefaultState(), 3);
                            break;
                        }
                        case NORTH: {
                            w.setBlockState(pos.add(i, j, -k), Blocks.IRON_BLOCK.getDefaultState(), 3);
                            break;
                        }
                    }

                }
            }
        }
    }
}
