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

package winecraft.barrel;

import java.util.ArrayList;
import winecraft.Winecraft;
import winecraft.wine.WineItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;


public class TileEntityBarrel extends TileEntity  implements IInventory{
	public class Recipe {
		int pressingTime = 1;
		int fermentationTime = 1;
		int nbrFillBarrel = 1;
		Item in;
		Item out;
		
		Recipe(){}
		Recipe(int pressingTime, int fermentationTime,	int nbrFillBarrel, Item in, Item out){
			this.pressingTime = pressingTime;
			this.fermentationTime = fermentationTime;
			this.nbrFillBarrel = nbrFillBarrel;
			this.in = in;
			this.out = out;
		}
	}
    /** 
     * Hold the currently placed items in the slots of the barrel 
     */
    private ItemStack[] barrelItemStacks = new ItemStack[4];
    
    /**
     * Hold add the recipe
     */
    static private ArrayList<Recipe> recipes = new ArrayList<Recipe>();
    

    /** The number of grape in the barrel */
    private int barrelGrapeLevel = 0;
    
    /** Is it currently pressing grape */
    private boolean barrelPressing = false;
    
    /** The number of ticks since a grape start to be press */
    private int barrelPressingTime = 0;

    /** The number of ticks that the current item has been fermenting for */
    private int barrelFermentationTime = 0;
    
    /** The id of the current recipe */
    private int barrelCurrentRecipeId = -1;
    


	static public void addRecipe(int pressingTime, int fermentationTime,	int nbrFillBarrel, Item in, Item out){
    	/** 
    	 * We need an instance of the current class before creating an instance of the inner class
    	 * yeah it's horrible..
    	*/
    	TileEntityBarrel wtf_im_doing = new TileEntityBarrel();
    	recipes.add(wtf_im_doing.new Recipe(pressingTime, fermentationTime,	nbrFillBarrel, in, out));
    }
    
	/**
     * This function is call after each tick
     */
    public void updateEntity(){
        super.updateEntity();
        
        if(this.barrelCurrentRecipeId == -1){
        	if(this.barrelItemStacks[0] != null){
        		for(int h = 0; h < recipes.size(); h++){
        			if(this.barrelItemStacks[0].getItem() == recipes.get(h).in)
        				this.barrelCurrentRecipeId = h;
        		}
        	}
        }
        
        if(this.barrelGrapeLevel == 100){
        	this.barrelFermentationTime++;
        	if(this.barrelFermentationTime >= recipes.get(barrelCurrentRecipeId).fermentationTime){
        		this.barrelFermentationTime = 0;
        		this.barrelGrapeLevel = 0;
        		WineItem brew = (WineItem) recipes.get(barrelCurrentRecipeId).out;
        		this.barrelCurrentRecipeId = -1;
        		
        		if(Math.random() > 0.7)
        			brew.setFoodEffect(new PotionEffect(Potion.confusion.getId(),200,10));
        		else
        			brew.setFoodEffect(new PotionEffect(Potion.hunger.getId(),200,10));
        			
        		if(this.barrelItemStacks[1] == null )
        			this.barrelItemStacks[1] = new ItemStack(brew);
        		else if(this.barrelItemStacks[1].getItemName() == brew.getUnlocalizedName()){
        			int s = this.barrelItemStacks[1].stackSize + 1;
        			this.barrelItemStacks[1] = new ItemStack(brew);
        			this.barrelItemStacks[1].stackSize = s;
        		}
        		
        	}
        }
        	
        
        this.barrelPressing = (
        		this.barrelItemStacks[0] !=null &&
        		this.barrelCurrentRecipeId != -1 &&
        		this.barrelGrapeLevel < 100);
        if(this.barrelPressing){
        	Recipe r = recipes.get(barrelCurrentRecipeId);
        	this.barrelPressingTime++;
        	if(this.barrelPressingTime >= r.pressingTime){
        		this.barrelPressingTime = 0;
        		this.barrelGrapeLevel += 100 / r.nbrFillBarrel;
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
	 * We check if it's in @barrelItemStacks and return the ItemStack 
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
            this.barrelPressingTime = tagCompound.getShort("PressTime");
            this.barrelGrapeLevel = tagCompound.getShort("GrapeLvl");
            this.barrelFermentationTime = tagCompound.getShort("FermTime");
            this.barrelCurrentRecipeId = tagCompound.getShort("RecipeId");
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
            super.writeToNBT(tagCompound);
            tagCompound.setShort("PressTime", (short)this.barrelPressingTime);
            tagCompound.setShort("GrapeLvl", (short)this.barrelGrapeLevel);
            tagCompound.setShort("FermTime", (short)this.barrelFermentationTime);
            tagCompound.setShort("RecipeId", (short)this.barrelCurrentRecipeId);
                           
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
    
    
    public int getBarrelGrapeLevel() {
		return barrelGrapeLevel;
	}

	public void setBarrelGrapeLevel(int barrelGrapeLevel) {
		this.barrelGrapeLevel = barrelGrapeLevel;
	}

	public int getBarrelPressingTime() {
		return barrelPressingTime;
	}

	public void setBarrelPressingTime(int barrelPressingTime) {
		this.barrelPressingTime = barrelPressingTime;
	}

	public int getBarrelFermentationTime() {
		return barrelFermentationTime;
	}

	public void setBarrelFermentationTime(int barrelFermentationTime) {
		this.barrelFermentationTime = barrelFermentationTime;
	}

    public Recipe getCurrentRecipe() {
    	if( barrelCurrentRecipeId != -1)
    		return recipes.get(barrelCurrentRecipeId);
    	else
    		return new Recipe();
	}
    
    public int getBarrelCurrentRecipeId() {
		return barrelCurrentRecipeId;
	}

	public void setBarrelCurrentRecipeId(int barrelCurrentRecipeId) {
		this.barrelCurrentRecipeId = barrelCurrentRecipeId;
	}

	@Override
	public boolean isInvNameLocalized() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		// TODO Auto-generated method stub
		return false;
	}
}
