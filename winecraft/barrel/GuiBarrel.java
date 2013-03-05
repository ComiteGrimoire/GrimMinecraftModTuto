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

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public class GuiBarrel extends GuiContainer {
	
	TileEntityBarrel barrelTile;
	
	public GuiBarrel(InventoryPlayer inventoryPlayer, TileEntityBarrel tileEntity) {
		super(new ContainerBarrel(inventoryPlayer, tileEntity));
		this.barrelTile = tileEntity;
	}
	@Override
    protected void drawGuiContainerForegroundLayer(int param1, int param2) {
            fontRenderer.drawString("Barrel",  (width - xSize) / 4 + 15, 6, 4210752);
            fontRenderer.drawString("Inventory",  8, this.ySize - 96 + 2, 4210752);
            //fontRenderer.drawString("Ticks: " + this.barrelTile.barrelFermentationTime, 8, 60, 4210752);
            fontRenderer.drawString(this.barrelTile.getBarrelGrapeLevel()+"%", 8, 5, 4210752);
    }
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
        int texture = mc.renderEngine.getTexture("/tutorial/winecraft/barrel.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(texture);
        
        int x = (width - xSize) / 2 - 2;
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        
        int s = 40 - (int) (this.barrelTile.getBarrelGrapeLevel()*0.4);
        int p = (int) (this.barrelTile.getBarrelPressingTime()*22/300);
        int f = (int) (this.barrelTile.getBarrelFermentationTime()*22/400);

        this.drawTexturedModalRect(x + 72, y + 25, 200, 0, 24, 40); // Gray
        this.drawTexturedModalRect(x + 72, y + 25 + s, 176, s, 24, 40 - s); // Green
        
        this.drawTexturedModalRect(x + 49, y + 35, 176, 40, p, 15); // Arrow Pressing
        this.drawTexturedModalRect(x + 101, y + 35, 176, 40, f, 15); // Arrow fermentation
	}
	
}
