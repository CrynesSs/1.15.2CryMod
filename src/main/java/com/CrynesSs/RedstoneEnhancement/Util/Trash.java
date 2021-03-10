package com.CrynesSs.RedstoneEnhancement.Util;

public class Trash {
    /*
    public BlockState updatePipesConnected(World w, BlockPos pos, BlockState stateIn,boolean init) {
        boolean north = w.getBlockState(pos.north()).getBlock() instanceof AbstractTransportPipe;
        boolean south = w.getWorld().getBlockState(pos.south()).getBlock() instanceof AbstractTransportPipe;
        boolean west = w.getWorld().getBlockState(pos.west()).getBlock() instanceof AbstractTransportPipe;
        boolean east = w.getWorld().getBlockState(pos.east()).getBlock() instanceof AbstractTransportPipe;
        boolean up = w.getWorld().getBlockState(pos.up()).getBlock() instanceof AbstractTransportPipe;
        boolean down = w.getWorld().getBlockState(pos.down()).getBlock() instanceof AbstractTransportPipe;

        System.out.println(stateIn.get(ClearPipe.COLOR) + " " + pos);
        if (w.getBlockState(pos.north()).getBlock() instanceof ClearPipe) {
            north = stateIn.get(ClearPipe.COLOR) == w.getBlockState(pos.north()).get(ClearPipe.COLOR);
        }
        if (w.getBlockState(pos.south()).getBlock() instanceof ClearPipe) {
            south = stateIn.get(ClearPipe.COLOR) == w.getBlockState(pos.south()).get(ClearPipe.COLOR);
        }
        if (w.getBlockState(pos.east()).getBlock() instanceof ClearPipe) {
            east = stateIn.get(ClearPipe.COLOR) == w.getBlockState(pos.east()).get(ClearPipe.COLOR);
        }
        if (w.getBlockState(pos.west()).getBlock() instanceof ClearPipe) {
            west = stateIn.get(ClearPipe.COLOR) == w.getBlockState(pos.west()).get(ClearPipe.COLOR);
        }
        if (w.getBlockState(pos.up()).getBlock() instanceof ClearPipe) {
            up = stateIn.get(ClearPipe.COLOR) == w.getBlockState(pos.up()).get(ClearPipe.COLOR);
        }
        if (w.getBlockState(pos.down()).getBlock() instanceof ClearPipe) {
            down = stateIn.get(ClearPipe.COLOR) == w.getBlockState(pos.down()).get(ClearPipe.COLOR);
        }
        boolean[] bools = {north, south, east, west, up, down};
        int i = 0;
        for (boolean b : bools) {
            if (b) {
                i++;
            }
        }
        if (i == 0 && !init) {
            north = stateIn.get(NORTH);
            south = stateIn.get(SOUTH);
            west = stateIn.get(WEST);
            east = stateIn.get(EAST);
            up = stateIn.get(UP);
            down = stateIn.get(DOWN);
        }else{

        }
        if (i == 1) {
            if (north) {
                south = true;
            }
            if (south) {
                north = true;
            }
            if (west) {
                east = true;
            }
            if (east) {
                west = true;
            }
            if (up) {
                down = true;
            }
            if (down) {
                up = true;
            }
        }
        return stateIn.with(NORTH, north).with(SOUTH, south).with(WEST, west).with(EAST, east).with(UP, up).with(DOWN, down);



if(slot1.getHasStack() && slot1.getStack().getItem().equals(stack.getItem()) && slot1.getStack().getCount() < slot1.getStack().getItem().getMaxStackSize() && slot2.getStack().getItem().equals(stack.getItem()) && slot2.getHasStack() && slot2.getStack().getItem().getMaxStackSize() > slot2.getStack().getItem().getMaxStackSize()){
                    //Trying to fill Both up to around equal height
                    int slot1count = slot1.getStack().getCount();
                    int slot2count = slot2.getStack().getCount();
                    int slotdifference = Math.abs(slot1count - slot2count);
                    //Slot 2 has less Items and also slotcount is greater than SlotDifference
                    if(slotdifference > 0 && slot1count > slot2count && slotdifference < slot.getStack().getCount()){
                        slot2.putStack(new ItemStack(slot2.getStack().getItem(),slot2count+slotdifference));
                        slot2count = slot2count + slotdifference;
                        slotStack.setCount(slotStack.getCount() - slotdifference);

                    }else if(slotdifference > 0 && slot1count > slot2count && slotdifference > slot.getStack().getCount()){
                        mergeItemStack(new ItemStack(slot2.getStack().getItem(),slot2count+slot.getStack().getCount()),29,40,false);
                    }



                    Class<?>[] memberClasses = this.dataClass.getClasses();
                    if (memberClasses.length > 0) {
                        try {
                            Field field = memberClasses[0].getDeclaredField("FIELD");
                            field.setAccessible(true);
                            String fieldValue = (String) field.get(null);
                            System.out.println(fieldValue);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
     */
}
