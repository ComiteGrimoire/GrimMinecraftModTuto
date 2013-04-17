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

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerVineyard extends Container {
	private TileEntityVineyard tile;
    
    /** 
     * By the way, the default parameters of the Slot object are 
     * Slot(IInventory inventory, int slotIndex, int xDisplayPosition, int yDisplayPosition)
     */
    public ContainerVineyard(InventoryPlayer inventoryPlayer, TileEntityVineyard tile){
    	this.tile = tile;
    	addSlotToContainer(new Slot(tile, 0, 110, 34));
    	
    	bindPlayerInventory(inventoryPlayer);
    }
    
    /**
     * This function initiate the slot for the inventory of the player
     */
    protected void bindPlayerInventory(InventoryPlayer player_inventory){
    	for(int i = 0; i < 3; i++){
	    	for(int j = 0; j < 9; j++){
	    		addSlotToContainer(new Slot(player_inventory, j + i * 9 + 9, 6 + j * 18, 84 + i * 18));
	    	}
    	}

    	for(int i = 0; i < 9; i++){
    		addSlotToContainer(new Slot(player_inventory, i, 6 + i * 18, 142));
    	}
    }

    
	
    public void addCraftingToCrafters(ICrafting par1ICrafting){
        super.addCraftingToCrafters(par1ICrafting);
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();
    }

	 
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.tile.isUseableByPlayer(player);
	}
	
	/** 
	 * Need this or it crashes
	 */
	@Override
	 public ItemStack transferStackInSlot(EntityPlayer player, int i){
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(i);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (i < 64) {
				if (!mergeItemStack(itemstack1, 64, inventorySlots.size(), true))
					return null;
				} 
				else if (!mergeItemStack(itemstack1, 0, 64, false))
					return null;
				if (itemstack1.stackSize == 0) {
					slot.putStack(null);
				} 
				else {
					slot.onSlotChanged();
				}
			}
			return itemstack;
	 	}
	}
