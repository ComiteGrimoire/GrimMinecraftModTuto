package tutorial.winecraft;

import tutorial.winecraft.barrel.ContainerBarrel;
import tutorial.winecraft.barrel.GuiBarrel;
import tutorial.winecraft.barrel.TileEntityBarrel;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {
    //returns our Container
    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
            TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
            if(tileEntity instanceof TileEntityBarrel){
                	System.out.println("GUI HANDLER READ CONTAINER");
                    return new ContainerBarrel(player.inventory, (TileEntityBarrel) tileEntity);
            }
            return null;
    }

    //returns our Gui
    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
            TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
            if(tileEntity instanceof TileEntityBarrel){
                	System.out.println("GUI HANDLER READ GUI");
                    return new GuiBarrel(player.inventory, (TileEntityBarrel) tileEntity);
            }
            return null;

    }
}
