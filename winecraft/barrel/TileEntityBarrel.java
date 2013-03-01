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
import tutorial.winecraft.wine.WineItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
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
        
        if(this.barrelGrapeLevel == 100){
        	this.barrelFermentationTime++;
        	if(this.barrelFermentationTime >= 400){
        		this.barrelFermentationTime = 0;
        		this.barrelGrapeLevel = 0;
        		WineItem brew = (WineItem) Winecraft.wine;
        		
        		if(Math.random() > 0.7)
        			brew.setFoodEffect(new PotionEffect(Potion.confusion.getId(),200,10));
        		else
        			brew.setFoodEffect(new PotionEffect(Potion.hunger.getId(),200,10));
        			
        		if(this.barrelItemStacks[1] == null )
        			this.barrelItemStacks[1] = new ItemStack(brew);
        		else if(this.barrelItemStacks[1].getItemName() == Winecraft.wine.getItemName()){
        			int s = this.barrelItemStacks[1].stackSize + 1;
        			this.barrelItemStacks[1] = new ItemStack(brew);
        			this.barrelItemStacks[1].stackSize = s;
        		}
        	}
        }
        	
        
        this.barrelPressing = (
        		this.barrelItemStacks[0] !=null &&
        		this.barrelItemStacks[0].getItemName() == Winecraft.grapeFruit.getItemName() &&
        		this.barrelGrapeLevel < 100);
        if(this.barrelPressing){
        	this.barrelPressingTime++;
        	if(this.barrelPressingTime >= 300){
        		this.barrelPressingTime = 0;
        		this.barrelGrapeLevel += 50;
        		this.barrelGrapeLevel = this.barrelGrapeLevel > 100 ? 100 :this. barrelGrapeLevel;
        		if(this.barrelItemStacks[0].stackSize > 1)
        			this.barrelItemStacks[0].stackSize = this.barrelItemStacks[0].stackSize - 1;
        		else
        			this.barrelItemStacks[0] = null;
        	}
        }
        else
        	this.barrelPressingTime = 0;
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
                    if (slot >= 0 && slot < this.barrelItemStacks.length) {
                    	this.barrelItemStacks[slot] = ItemStack.loadItemStackFromNBT(tag);
                    }
            }
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
            super.writeToNBT(tagCompound);
                           
            NBTTagList itemList = new NBTTagList();
            for (int i = 0; i < this.barrelItemStacks.length; i++) {
                    ItemStack stack = this.barrelItemStacks[i];
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
