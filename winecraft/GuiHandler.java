package tutorial.winecraft;

import tutorial.winecraft.barrel.BarrelContainer;
import tutorial.winecraft.barrel.BarrelGui;
import tutorial.winecraft.barrel.BarrelTileEntity;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {
    //returns an instance of the Container you made earlier
    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
            TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
            if(tileEntity instanceof BarrelTileEntity){
                    return new BarrelContainer(player.inventory, (BarrelTileEntity) tileEntity);
            }
            return null;
    }

    //returns our Gui
    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
            TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
            if(tileEntity instanceof BarrelTileEntity){
                    return new BarrelGui(player.inventory, (BarrelTileEntity) tileEntity);
            }
            return null;

    }
}
