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

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerBarrel extends Container {
	private TileEntityBarrel barrel;

    /** The number of grape in the barrel */
    public int lastGrapeLevel = 0;
    
    /** The number of ticks since a grape start to be press */
    public int lastPressingTime = 0;

    /** The number of ticks that the current item has been fermenting for */
    public int lastFermentationTime = 0;
    
    /** 
     * By the way, the default parameters of the Slot object are 
     * Slot(IInventory inventory, int slotIndex, int xDisplayPosition, int yDisplayPosition)
     */
    public ContainerBarrel(InventoryPlayer inventoryPlayer, TileEntityBarrel tileBarrel){
    	this.barrel = tileBarrel;

    	addSlotToContainer(new Slot(tileBarrel, 0, 23, 34));
    	addSlotToContainer(new Slot(tileBarrel, 1, 135, 34));
    	
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

	
    public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, this.barrel.barrelGrapeLevel);
        par1ICrafting.sendProgressBarUpdate(this, 1, this.barrel.barrelPressingTime);
        par1ICrafting.sendProgressBarUpdate(this, 2, this.barrel.barrelFermentationTime);
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); i++){
            ICrafting var2 = (ICrafting)this.crafters.get(i);

            if (this.lastGrapeLevel != this.barrel.barrelGrapeLevel){
                var2.sendProgressBarUpdate(this, 0, this.barrel.barrelGrapeLevel);
            }

            if (this.lastPressingTime != this.barrel.barrelPressingTime){
                var2.sendProgressBarUpdate(this, 1, this.barrel.barrelPressingTime);
            }

            if (this.lastFermentationTime != this.barrel.barrelFermentationTime)
            {
                var2.sendProgressBarUpdate(this, 2, this.barrel.barrelFermentationTime);
            }
        }

        this.lastGrapeLevel = this.barrel.barrelGrapeLevel;
        this.lastPressingTime = this.barrel.barrelPressingTime;
        this.lastFermentationTime = this.barrel.barrelFermentationTime;
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int value){
        if (id == 0){
            this.barrel.barrelGrapeLevel = value;
        }
        else if (id == 1){
            this.barrel.barrelPressingTime = value;
        }
        else if (id == 2){
            this.barrel.barrelFermentationTime = value;
        }
    }
	 
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.barrel.isUseableByPlayer(player);
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
