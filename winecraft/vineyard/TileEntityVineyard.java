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

import java.util.Random;

import tutorial.winecraft.TileEntityGrapeCrop;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntityVineyard extends TileEntity implements IInventory {

    /** Hold the fences */
    private ItemStack[] vineyardItemStacks = new ItemStack[1];
	
	private boolean vineyardDelimited = false;	

	private int offsetX = 5;
	private int offsetY = 0;
	private int offsetZ = 5;
	private int angle = 0;

	private String error = "";
	
	 public TileEntityVineyard(){
		 super();
	 }

	 /**
	  * A block is empty if it's a flower(37 and 38), a mushroom(39), grass(31) or air(0)
	  */
	 public boolean isFencable(int id){
		if(id == 0 || id == 31 || id == 37 || id == 38 || id == 39)
			return true;
		else
			return false;
	 }
	 
	 public int putFence(World world, int x, int y, int z, boolean isADoor){
		 /** 107 = fence door ; 85 = fence */
		 int idBlock = isADoor ?  107 : 85;
		 
		 /** We check if the block under where we want to put a fence is empty. */
		 if(!isFencable(world.getBlockId(x, y - 1, z)) && isFencable(world.getBlockId(x, y, z))){
			 world.setBlock(x, y, z, idBlock);
			 return y;
		 }
		 else if(!isFencable(world.getBlockId(x, y - 2, z)) && isFencable(world.getBlockId(x, y - 1, z))){
			 world.setBlock(x, y-1, z, idBlock);
			 return y - 1;
		 }
		 else if(!isFencable(world.getBlockId(x, y, z)) && isFencable(world.getBlockId(x, y + 1, z))){
			 world.setBlock(x, y+1, z, idBlock);
			 return y + 1;
		 }
		 else{
			 /** It's impossible to build a continuous fence  */
			 error = "The fence perimeter isn't continuous";
			 System.out.println("The fence perimeter isn't continuous");
			 return 0;
		 }
	 }
	 
	 public int getPerimeter(){
		 //return(Math.abs(offsetX) - 1)*2 + (Math.abs(offsetZ) - 1)*2 - 1;
		 //return Math.abs(offsetX) * Math.abs(offsetY) - (Math.abs(offsetX) - 1) * (Math.abs(offsetY) - 1) - 1;
		 return 2*Math.abs(this.offsetX) + 2*Math.abs(this.offsetZ) - 1;
	 }
	 
	 public void buildFences(World world, int offsetX, int offsetZ){
		 this.offsetX = offsetX;
		 this.offsetZ = offsetZ;
		 
		 if(this.vineyardItemStacks[0] != null && this.vineyardItemStacks[0].getDisplayName() != "Fence"){
			 error = "You need to put fences in the slot";
			 System.out.println("You need to put fences in the slot");
			 return;
		 }
		 if(this.vineyardItemStacks[0].stackSize < getPerimeter()){
			 error = "Not enough fences";
			 System.out.println("Not enough fences");
			 return;
		 }
		 if(this.offsetX == 0 || this.offsetZ == 0){
			 error = "Invalid offset";
			 System.out.println("Invalid offset");
			 return;
		 }
		 int y = this.yCoord;
		 int maxY = 0, minY = 0;
		 
		 /** Delete the fence that we will be using from the slot */
		 int newStackSize = this.vineyardItemStacks[0].stackSize - getPerimeter();
		 if(newStackSize > 0)
			 this.vineyardItemStacks[0] = new ItemStack(Block.fence, newStackSize);
		 else
			 this.vineyardItemStacks[0] = null;
		 
		 
		 /** Generate the first "x" (east-west) side */
		 for(int i = (offsetX > 0 ? 1: -1); Math.abs(i) < Math.abs(offsetX); i += (offsetX > 0 ? 1: -1)){
			 /** If this the middle block of the row, we put a door here */
			 y = putFence(world, this.xCoord + i, y, this.zCoord, Math.abs(offsetX) > 3 && i == (int)(offsetX / 2));
			 if(y == 0)
				 return;
			 if(y - this.yCoord > maxY)
				 maxY = y - this.yCoord;
			 if(y - this.yCoord < minY)
				 minY = y - this.yCoord;
		 }

		 /** Generate the first "z" (north-south) side */
		 for(int k = 0; Math.abs(k) < Math.abs(offsetZ); k += (offsetZ > 0 ? 1: -1)){
			 y = putFence(world, this.xCoord + this.offsetX, y, this.zCoord + k, false);
			 if(y == 0)
				 return;
			 if(y - this.yCoord > maxY)
				 maxY = y - this.yCoord;
			 if(y - this.yCoord < minY)
				 minY = y - this.yCoord;
		 }
		 
		 /** Generate the second "x" (east-west) side */
		 for(int i = 0; Math.abs(i) < Math.abs(offsetX); i += (offsetX > 0 ? 1: -1)){
			 /** If this the middle block of the row, we put a door here */
			 y = putFence(world, this.xCoord + this.offsetX - i, y, this.zCoord + this.offsetZ, Math.abs(offsetX) > 3 && i == (int)(offsetX / 2 ) - 1);
			 if(y == 0)
				 return;
			 if(y - this.yCoord > maxY)
				 maxY = y - this.yCoord;
			 if(y - this.yCoord < minY)
				 minY = y - this.yCoord;
		 }
		 
		 /** Generate the first "z" (north-south) side */
		 for(int k = 0; Math.abs(k) < Math.abs(offsetZ); k += (offsetZ > 0 ? 1: -1)){
			 y = putFence(world, this.xCoord, y, this.zCoord + this.offsetZ - k, false);
			 if(y == 0)
				 return;
			 if(y - this.yCoord > maxY)
				 maxY = y - this.yCoord;
			 if(y - this.yCoord < minY)
				 minY = y - this.yCoord;
		 }
		 
		 /** We check if the last fence is at the same height as the vineyard delimiter */
		 if(y - this.yCoord > 1 || y - this.yCoord < -1){
			 error = "The fence perimeter isn't continuous";
			 System.out.println("The fence perimeter isn't continuous");
			 return;
		 }
			 
		 if(Math.abs(minY) > maxY)
			 this.offsetY = minY;
		 else
			 this.offsetY = maxY;
		 
		 this.vineyardDelimited = true;
		 updateAngle();
	 }
	 
    /**
     * This function is call after each tick
     */
    public void updateEntity(){
        super.updateEntity();
        
		if(this.isVineyardDelimited() && (new Random()).nextInt(20) == 0&& !worldObj.isRemote){
			//this.setVineyardDelimited(true);
			TileEntity t;
			 for(int i = (this.getOffsetX() > 0 ? 1: -1); Math.abs(i) < Math.abs(this.getOffsetX()); i += (this.getOffsetX() > 0 ? 1: -1)){
				 for(int j = 0; Math.abs(j) <= Math.abs(this.offsetY); j += (this.offsetY > 0 ? 1: -1)){
					 for(int k = (this.getOffsetZ() > 0 ? 1: -1); Math.abs(k) < Math.abs(this.getOffsetZ()); k += (this.getOffsetZ() > 0 ? 1: -1)){
						 //worldObj.setBlock(this.xCoord + i, this.yCoord + j, this.zCoord + k, 5);
						 t = (worldObj.getBlockTileEntity(this.xCoord + i, this.yCoord + j, this.zCoord + k));
						 if(worldObj.getBlockId(this.xCoord + i, this.yCoord + j, this.zCoord + k) == 504 && t instanceof TileEntityGrapeCrop){
							 //worldObj.setBlock(this.xCoord + i, this.yCoord + j, this.zCoord + k, 5);
							 if(!(((TileEntityGrapeCrop) t).isInVineyard())){
								 ((TileEntityGrapeCrop) t).setInVineyard(true);
								 ((TileEntityGrapeCrop) t).setAngle(angle);
									System.out.println("x y z "+ this.offsetY);
							 }
						 }
					 }
				}
			 }
        }
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
                    if (slot >= 0 && slot < this.vineyardItemStacks.length) {
                    	this.vineyardItemStacks[slot] = ItemStack.loadItemStackFromNBT(tag);
                    }
            }
            this.offsetX = tagCompound.getShort("offsetX");
            this.offsetY = tagCompound.getShort("offsetY");
            this.offsetZ = tagCompound.getShort("offsetZ");
            if(tagCompound.getShort("completed") == 1)
            	this.vineyardDelimited = true;
            else
            	this.vineyardDelimited = false;
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
            super.writeToNBT(tagCompound);
            tagCompound.setShort("offsetX", (short)this.offsetX);
            tagCompound.setShort("offsetY", (short)this.offsetY);
            tagCompound.setShort("offsetZ", (short)this.offsetZ);
            tagCompound.setShort("completed", (short)(this.isVineyardDelimited()? 1:0));
                           
            NBTTagList itemList = new NBTTagList();
            for (int i = 0; i < this.vineyardItemStacks.length; i++) {
                    ItemStack stack = this.vineyardItemStacks[i];
                    if (stack != null) {
                            NBTTagCompound tag = new NBTTagCompound();
                            tag.setByte("Slot", (byte) i);
                            stack.writeToNBT(tag);
                            itemList.appendTag(tag);
                    }
            }
            tagCompound.setTag("Inventory", itemList);
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

	public int getOffsetZ() {
		return offsetZ;
	}
	
	public int getOffsetY() {
		return offsetY;
	}

	public void addOffsetZ() {
		if(!vineyardDelimited && this.offsetZ < 30)
			this.offsetZ++;
	}
	public void subOffsetZ() {
		if(!vineyardDelimited && this.offsetZ > -30)
			this.offsetZ--;
	}
	public String getErrorMessage(){
		return this.error;
	}

	public boolean isVineyardDelimited() {
		return vineyardDelimited;
	}
	
	public void setVineyardDelimited(boolean vineyardDelimited) {
		this.vineyardDelimited = vineyardDelimited;
	}
	
	public void updateAngle(){
		angle = (int) (180/Math.PI* Math.atan(this.offsetY/Math.sqrt(this.offsetX*this.offsetX + this.offsetZ*this.offsetZ)));
	}
	
	public int getAngle() {
		return angle;
	}

	public void setAngle(int angle) {
		this.angle = angle;
	}
}
