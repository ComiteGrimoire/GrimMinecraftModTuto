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

package tutorial.winecraft.vineyard;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntityVineyard extends TileEntity implements IInventory {

    /** 
     * Hold the fences
     */
    private ItemStack[] vineyardItemStacks = new ItemStack[1];
	
	private boolean vineyardDelimited = false;	
	
	private int offsetX = 5;
	private int offsetY = 5;
	private int offsetZ = 0;
	
	private String error = "";
	
	 public TileEntityVineyard(){
		 super();
	 }
	 
	 public int putFence( double x, double y, double z){
		 return 0;
	 }
	 
	 public void buildFences(){
		 if(this.vineyardItemStacks[0] != null && this.vineyardItemStacks[0].getDisplayName() != "Fence"){
			 if(this.vineyardItemStacks[0].stackSize >= Math.abs(offsetX*offsetY) - (Math.abs(offsetX) - 1)*(Math.abs(offsetY) - 1) - 1){
				 error = "Not enough fences";
				 return;
			 }
			 int z = this.zCoord;
			 for(int i = 0; Math.abs(i) < Math.abs(offsetX); i += (offsetX > 0 ? 1: -1)){
				 putFence(this.xCoord + i, this.yCoord, z);
			 }
		 }
		 else{
			 error = "You need to put fences";
		 }
	 }
	 
    /**
     * This function is call after each tick
     */
    public void updateEntity(){
        super.updateEntity();
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
	public void readFromNBT(NBTTagCompound par1NBTTagCompound){
		super.readFromNBT(par1NBTTagCompound);
	}

    @Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound){
		super.writeToNBT(par1NBTTagCompound);
	}

    @Override
	public int getSizeInventory() {
		return this.vineyardItemStacks.length;
	}

	/** 
	 * We check if it's in @barrelItemStacks and return the ItemStack 
	 */
	public ItemStack getStackInSlot(int i) {
        return i >= 0 && i < this.vineyardItemStacks.length ? this.vineyardItemStacks[i] : null;
	}

	/** 
	 * Delete the @a ItemStack and return it. Parameter @b unused 
	 */
	public ItemStack decrStackSize(int a, int b) {
        if (a >= 0 && b < this.vineyardItemStacks.length){
            ItemStack newStack = this.vineyardItemStacks[a];
            this.vineyardItemStacks[a] = null;
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
        if (id >= 0 && id < this.vineyardItemStacks.length){
            this.vineyardItemStacks[id] = theItem;
        }
		
	}

	@Override
	public String getInvName() {
		return "container.vineyard";
	}

	/** 
	 * Returns the maximum stack size for a inventory slot. 
	 */
	public int getInventoryStackLimit() {
		return 64;
	}
	
	/**
	 * Setter/Getter
	 */
	public int getOffsetX() {
		return offsetX;
	}

	public void addOffsetX() {
		if(!vineyardDelimited && this.offsetX < 30)
			this.offsetX++;
	}
	public void subOffsetX() {
		if(!vineyardDelimited && this.offsetX > -30)
			this.offsetX--;
	}

	public int getOffsetY() {
		return offsetY;
	}

	public void addOffsetY() {
		if(!vineyardDelimited && this.offsetX < 30)
			this.offsetY++;
	}
	public void subOffsetY() {
		if(!vineyardDelimited && this.offsetX > -30)
			this.offsetY--;
	}

	public int getOffsetZ() {
		return offsetZ;
	}

	public void addOffsetZ() {
		if(!vineyardDelimited)
			this.offsetZ++;
	}
	public void subOffsetZ() {
		if(!vineyardDelimited)
			this.offsetZ--;
	}
	public String getErrorMessage(){
		return this.error;
	}

	public boolean isVineyardDelimited() {
		return vineyardDelimited;
	}
}
