/**
 * Copyright (C) 2013  Philippe Babin<philippe.babin@gmail.com> and François Drouin-Morin<>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package tutorial.winecraft.barrel;

import tutorial.winecraft.Winecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;

public class TileEntityBarrel extends TileEntity  implements IInventory/**, ISidedInventory*/{

    /** 
     * Hold the currently placed items in the slots of the barrel 
     */
    private ItemStack[] barrelItemStacks = new ItemStack[4];
    

    /** The number of grape in the barrel */
    public int barrelGrapeLevel = 0;
    
    /** Is it currently pressing grape */
    public boolean barrelPressing = false;
    
    /** The number of ticks since a grape start to be press */
    public int barrelPressingTime = 0;

    /** The number of ticks that the current item has been fermenting for */
    public int barrelFermentationTime = 0;
    
    
    /**
     * This function is call after each tick
     */
    public void updateEntity(){
        super.updateEntity();
        
        
        
        /**
        if( barrelItemStacks[0] !=null){
        	if(barrelItemStacks[0].getItemName() == Winecraft.grapeFruit.getItemName()){
            	barrelGrapeLevel += 25 * barrelItemStacks[0].stackSize;
            	barrelItemStacks[0] = null;
        	}
        }
        
        if(barrelGrapeLevel >= 100){
        	if(barrelItemStacks[1] != null && barrelItemStacks[1].getItemName() == Winecraft.wine.getItemName())
        		barrelItemStacks[1].stackSize += (barrelGrapeLevel-(barrelGrapeLevel % 100))/100;
        	else{
        		barrelItemStacks[1] = new ItemStack(Winecraft.wine);
        		barrelItemStacks[1].stackSize = (barrelGrapeLevel-(barrelGrapeLevel % 100))/100;
        	}
        	
        	barrelGrapeLevel = barrelGrapeLevel % 100;
        }
        */
        
        if(barrelGrapeLevel == 100){
        	barrelFermentationTime++;
        	if(barrelFermentationTime >= 400){
        		if(barrelItemStacks[1] == null )
        			barrelItemStacks[1] = new ItemStack(Winecraft.wine);
        		else if(barrelItemStacks[1].getItemName() == Winecraft.wine.getItemName())
        			barrelItemStacks[1].stackSize++;
        		
        		barrelFermentationTime = 0;
        		barrelGrapeLevel = 0;
        	}
        }
        	
        
        barrelPressing = (
        		barrelItemStacks[0] !=null &&
        		barrelItemStacks[0].getItemName() == Winecraft.grapeFruit.getItemName() &&
        		barrelGrapeLevel < 100);
        if(barrelPressing){
        	barrelPressingTime++;
        	if(barrelPressingTime >= 300){
        		barrelPressingTime = 0;
        		barrelGrapeLevel += 50;
        		if(barrelItemStacks[0].stackSize > 1)
        			barrelItemStacks[0].stackSize = barrelItemStacks[0].stackSize - 1;
        		else
        			barrelItemStacks[0] = null;
        	}
        }
        else
        	barrelPressingTime = 0;
        	
        	
        	
    }
    
	@Override
	public int getSizeInventory() {
		return this.barrelItemStacks.length;
	}

	/** 
	 * We check if it"s in @barrelItemStacks and return the ItemStack 
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
