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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerBarrel extends Container {
	private TileEntityBarrel tileBarrel;

    /** Instance of Slot. */
    //private final Slot resultSlot;
    
    /** 
     * By the way, the default parameters of the Slot object are 
     * Slot(IInventory inventory, int slotIndex, int xDisplayPosition, int yDisplayPosition)
     */
    public ContainerBarrel(InventoryPlayer inventoryPlayer, TileEntityBarrel tileBarrel){
    	this.tileBarrel = tileBarrel;
    	//this.resultSlot = this.addSlotToContainer(new Slot(inventoryPlayer, 0, 79, 17));
    	addSlotToContainer(new Slot(tileBarrel, 0, 54, 34));
    	addSlotToContainer(new Slot(tileBarrel, 1, 114, 34));
    	
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
        // Need to add something to increment the progress bar here
    }
	
	/**
	 *  Need to edit that for multiplayer
	 */
	public void detectAndSendChanges(){
	      super.detectAndSendChanges();
	}
	 
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.tileBarrel.isUseableByPlayer(player);
	}
	
	/** 
	 * This method is call when you use the shift key
	 */
	@Override
	 public ItemStack transferStackInSlot(EntityPlayer player, int slot){
		ItemStack stack = null;
        Slot slotObject = (Slot) inventorySlots.get(slot);

        //null checks and checks if the item can be stacked (maxStackSize > 1)
        if (slotObject != null && slotObject.getHasStack()) {
                ItemStack stackInSlot = slotObject.getStack();
                stack = stackInSlot.copy();

                //merges the item into player inventory since its in the tileEntity
                if (slot < 9) {
                        if (!this.mergeItemStack(stackInSlot, 9, 45, true)) {
                                return null;
                        }
                }
                //places it into the tileEntity is possible since its in the player inventory
                else if (!this.mergeItemStack(stackInSlot, 0, 9, false)) {
                        return null;
                }

                if (stackInSlot.stackSize == 0) {
                        slotObject.putStack(null);
                } else {
                        slotObject.onSlotChanged();
                }

                if (stackInSlot.stackSize == stack.stackSize) {
                        return null;
                }
                slotObject.onPickupFromSlot(player, stackInSlot);
        }
        return stack;
	 }
}
