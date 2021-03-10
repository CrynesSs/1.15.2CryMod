package com.CrynesSs.RedstoneEnhancement.Util.Packages;

import com.CrynesSs.RedstoneEnhancement.Machines.AlloyFurnace.AlloyFurnaceContainer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

public class ChangeRecipePackage {
    private final int id;
    private final int recipeID;

    public ChangeRecipePackage(PacketBuffer buf) {
        this.id = buf.readInt();
        this.recipeID = buf.readInt();
    }

    public ChangeRecipePackage(int id, int recipeID) {
        this.id = id;
        this.recipeID = recipeID;
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (Objects.requireNonNull(ctx.get().getSender()).openContainer != null && Objects.requireNonNull(ctx.get().getSender()).openContainer instanceof AlloyFurnaceContainer) {
                AlloyFurnaceContainer con = (AlloyFurnaceContainer) Objects.requireNonNull(ctx.get().getSender()).openContainer;
                con.tile.setRecipeIn(con.tile.recipeFromId(recipeID));
            }
        });
        return true;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeInt(id);
        buf.writeInt(recipeID);
    }
}
