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

package winecraft.vineyard;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import winecraft.CommonProxy;
import winecraft.Winecraft;
import winecraft.barrel.TileEntityBarrel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class VineyardBlock extends BlockContainer{
	// Texture 6
	
	public VineyardBlock(int id, Material material) {
		super(id, material);
	}
	

    public void registerIcons(IconRegister iconRegister){
	    this.blockIcon = iconRegister.registerIcon(Winecraft.modid+":"+"vineyard");
    }
	
	public void onBlockAdded(World par1World, int par2, int par3, int par4){
		par1World.setBlockTileEntity(par2, par3, par4, this.createNewTileEntity(par1World));
		super.onBlockAdded(par1World, par2, par3, par4);
	}
	
	public TileEntity createNewTileEntity(World var1){
        return new TileEntityVineyard();
	}
	
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int idk, float wtf, float are, float you_doing){
		if (world.isRemote){
        	TileEntityVineyard tile = (TileEntityVineyard)world.getBlockTileEntity(x, y, z);
            System.out.println("[client]"+tile.rainCooldown);
            return true;
        }
        else{
        	TileEntityVineyard tile = (TileEntityVineyard)world.getBlockTileEntity(x, y, z);

        	if (tile == null){
                return false;
        	}
            System.out.println("[server]"+tile.rainCooldown);
	    	player.openGui(Winecraft.instance, 1, world, x, y, z);
	        return true;
        }
	}
	 
	 @Override
    public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
            dropItems(world, x, y, z);
            super.breakBlock(world, x, y, z, par5, par6);
    }
	 
	 private void dropItems(World world, int x, int y, int z){
		 
	 }
}
