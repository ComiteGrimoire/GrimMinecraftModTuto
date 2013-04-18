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

import org.bouncycastle.asn1.crmf.Controls;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;

import tutorial.winecraft.CommonProxy;
import tutorial.winecraft.client.ClientProxy;
import tutorial.winecraft.network.WinecraftPacket;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public class GuiVineyard extends GuiContainer {
	
	TileEntityVineyard tile;
	private String error = "";
	
	public GuiVineyard(InventoryPlayer inventoryPlayer, TileEntityVineyard tileEntity) {
		super(new ContainerVineyard(inventoryPlayer, tileEntity));
		this.tile = tileEntity;
	}
	@Override
    protected void drawGuiContainerForegroundLayer(int param1, int param2) {
            fontRenderer.drawString("Vineyard",  90, 6, 4210752);
            fontRenderer.drawString("X: " + tile.getOffsetX(), 5, 18, 4210752);
            fontRenderer.drawString("Z: " + tile.getOffsetZ(), 5, 38, 4210752);
            
            if(!tile.isVineyardDelimited()){
                fontRenderer.drawString(tile.getErrorMessage(),  90, 30, 4210752);
                fontRenderer.drawString("Need " + tile.getPerimeter() + " fences", 90, 18, 4210752);
            }
            else{
            	fontRenderer.drawString("COMPLETED", 90, 18, 4210752);
            	fontRenderer.drawString("Y: " + tile.getOffsetY(), 5, 58, 4210752);
            	fontRenderer.drawString("Angle: " + tile.getAngle(), 50, 58, 4210752);
            	controlList.clear();
            }
    }
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
        int texture = mc.renderEngine.getTexture("/tutorial/winecraft/vineyard.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(texture);
        
        int x = (width - xSize) / 2 - 2;
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}
	
	@Override
    public void initGui() {
            super.initGui();
            int xw = 150;
            int x = (width - xSize) / 2 - 2;
            int y = (height - ySize) / 2;// 27 - 35

            if(!tile.isVineyardDelimited()){
	            controlList.add(new GuiButton(1, x + 42, y + 10, 15, 20, "+")); // 15 45
	            controlList.add(new GuiButton(2, x + 62, y + 10, 15, 20, "-"));
	            
	            controlList.add(new GuiButton(3, x + 42, y + 37, 15, 20, "+"));
	            controlList.add(new GuiButton(4, x + 62, y + 37, 15, 20, "-"));
	            
	            controlList.add(new GuiButton(5, x + 77, y + 60, 40, 20, "Build"));
            }
    }
	
    protected void actionPerformed(GuiButton guibutton) {
            switch(guibutton.id) {
	            case 1:
                    tile.addOffsetX();
                    break;
	            case 2:
                    tile.subOffsetX();
                    break;
	            case 3:
                    tile.addOffsetZ();
                    break;
	            case 4:
	                tile.subOffsetZ();
                    break;
	            case 5:
	            	//tile.buildFences();
	            	int[] payload = new int[2];
	            	payload[0] = tile.getOffsetX();
	    	        payload[1] = tile.getOffsetZ();
	            	WinecraftPacket packet = new WinecraftPacket( 22,tile.xCoord, tile.yCoord, tile.zCoord, payload);
	            	FMLClientHandler.instance().getClient().getSendQueue().addToSendQueue(packet.getPacket());
            }
    }
}
