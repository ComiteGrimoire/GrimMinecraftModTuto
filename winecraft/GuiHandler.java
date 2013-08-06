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

package winecraft;

import winecraft.barrel.ContainerBarrel;
import winecraft.barrel.GuiBarrel;
import winecraft.barrel.TileEntityBarrel;
import winecraft.vineyard.ContainerVineyard;
import winecraft.vineyard.GuiVineyard;
import winecraft.vineyard.TileEntityVineyard;
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
                    return new ContainerBarrel(player.inventory, (TileEntityBarrel) tileEntity);
            }
            if(tileEntity instanceof TileEntityVineyard){
                return new ContainerVineyard(player.inventory, (TileEntityVineyard) tileEntity);
            }
            return null;
    }

    //returns our Gui
    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
            TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
            if(tileEntity instanceof TileEntityBarrel){
                    return new GuiBarrel(player.inventory, (TileEntityBarrel) tileEntity);
            }
            if(tileEntity instanceof TileEntityVineyard){
                return new GuiVineyard(player.inventory, (TileEntityVineyard) tileEntity);
            }
            return null;

    }
}
