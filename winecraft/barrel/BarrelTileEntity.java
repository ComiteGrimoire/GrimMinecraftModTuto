package tutorial.winecraft.barrel;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;

public class BarrelTileEntity extends TileEntity  implements IInventory/**, ISidedInventory*/{

    /** 
     * Hold the currently placed items in the slots of the barrel 
     */
    private ItemStack[] barrelItemStacks = new ItemStack[4];
    
    public void updateEntity(){

        super.updateEntity();
    }
    
	@Override
	public int getSizeInventory() {
		return this.barrelItemStacks.length;
	}

	/** 
	 * We check if i is in @barrelItemStacks and return the ItemStack 
	 */
	public ItemStack getStackInSlot(int i) {
        return i >= 0 && i < this.barrelItemStacks.length ? this.barrelItemStacks[i] : null;
	}

	/** 
	 * Delete the @a ItemStack and return it. Parameter @b unused 
	 */
	public ItemStack decrStackSize(int a, int b) {
        if (a >= 0 && b < this.barrelItemStacks.length){
            ItemStack newStack = this.barrelItemStacks[a];
            this.barrelItemStacks[a] = null;
            return newStack;
        }

		return null;
	}

	/** 
	 * This method is call for each slot when a event cause the closing of the container
	 	(for example :the GUI is closed) 
	 */
	public ItemStack getStackInSlotOnClosing(int a) {
		return decrStackSize( a, 0);
	}

	/** 
	 * Set the given Inventory Slot 
	 */
	public void setInventorySlotContents(int id, ItemStack theItem) {
        if (id >= 0 && id < this.barrelItemStacks.length){
            this.barrelItemStacks[id] = theItem;
        }
		
	}

	@Override
	public String getInvName() {
		return "container.barrel";
	}

	/** 
	 * Returns the maximum stack size for a inventory slot. 
	 */
	public int getInventoryStackLimit() {
		return 64;
	}

	/** 
	 * Interact if at a distance of at least 8.0 
	 */
	public boolean isUseableByPlayer(EntityPlayer entityPlayer) {
		if(this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) == this)
			return entityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
		else
			return false;
	}

	public void openChest() {}

	public void closeChest() {}

	/** 
	 * I have no idea what going on here... Just copy-past the BrewingStand source code 
	 
	@Override
	public int getStartInventorySide(ForgeDirection side) {
		return (side == ForgeDirection.UP ? 3 : 0);
	}

	@Override
	public int getSizeInventorySide(ForgeDirection side) {
		return (side == ForgeDirection.UP ? 1 : 3);
	}
	*/
	@Override
    public void readFromNBT(NBTTagCompound tagCompound) {
            super.readFromNBT(tagCompound);
           
            NBTTagList tagList = tagCompound.getTagList("Inventory");
            for (int i = 0; i < tagList.tagCount(); i++) {
                    NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
                    byte slot = tag.getByte("Slot");
                    if (slot >= 0 && slot < barrelItemStacks.length) {
                    	barrelItemStacks[slot] = ItemStack.loadItemStackFromNBT(tag);
                    }
            }
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
            super.writeToNBT(tagCompound);
                           
            NBTTagList itemList = new NBTTagList();
            for (int i = 0; i < barrelItemStacks.length; i++) {
                    ItemStack stack = barrelItemStacks[i];
                    if (stack != null) {
                            NBTTagCompound tag = new NBTTagCompound();
                            tag.setByte("Slot", (byte) i);
                            stack.writeToNBT(tag);
                            itemList.appendTag(tag);
                    }
            }
            tagCompound.setTag("Inventory", itemList);
    }
}
