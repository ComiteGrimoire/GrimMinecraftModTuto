package tutorial.winecraft.barrel;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public class BarrelGui extends GuiContainer {
	
	public BarrelGui(InventoryPlayer inventoryPlayer, BarrelTileEntity tileEntity) {
		super(new BarrelContainer(inventoryPlayer, tileEntity));
	}
	@Override
    protected void drawGuiContainerForegroundLayer(int param1, int param2) {
	       System.out.println("DRAW GUI FOREGROUND LAYER");
            fontRenderer.drawString("Test", 8, 6, 4210752);
    }
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
	       System.out.println("DRAW GUI BACKGROUND LAYER");
        int texture = mc.renderEngine.getTexture("/tutorial/winecraft/barrel.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(texture);
        
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}
	
}
