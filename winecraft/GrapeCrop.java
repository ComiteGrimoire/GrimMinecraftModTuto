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

package tutorial.winecraft;

import java.util.Random;

import tutorial.winecraft.barrel.TileEntityBarrel;

import net.minecraft.block.material.Material;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class GrapeCrop extends BlockContainer {
	
    public GrapeCrop (int id) {
        super(id, 32, Material.plants);
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.5F, 1.0F);
        setTickRandomly(true);
    }
    
	public TileEntity createNewTileEntity(World var1){
        return new TileEntityGrapeCrop();
	}

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool (World world, int x,
            int y, int z) {
        return null;
    }

    /**
     *  6 = Render as crop (http://www.minecraftforum.net/topic/241903-creating-mods-mcp-getrendertype/)
     */
    @Override
    public int getRenderType () {
        return 6;
    }
    
    //Is the block opaque?
    @Override
    public boolean isOpaqueCube () {
        return false;
    }
    
    
    @Override
    public int getBlockTextureFromSideAndMetadata (int side, int metadata) {
        return 32 + metadata;
    }

    /***
     * Is called every Minecraft Tick (20 times/second.)
     */
    @Override
    public void updateTick(World world, int x, int y, int z, Random random) {
        if (world.getBlockMetadata(x, y, z) == 1) {
            return;
        }

        if (random.nextInt(isFertile(world, x, y - 1, z) ? 12 : 25) != 0) {
            return;
        }

        world.setBlockMetadataWithNotify(x, y, z, 1);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z,
            int neighborId) {
        if (!canBlockStay(world, x, y, z)) {
            dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
            world.setBlockWithNotify(x, y, z, 0);
        }
    }

    @Override
    public boolean canBlockStay(World world, int x, int y, int z) {
        Block soil = blocksList[world.getBlockId(x, y - 1, z)];
        return (world.getFullBlockLightValue(x, y, z) >= 8 || world
                .canBlockSeeTheSky(x, y, z))
                && (soil != null && soil.canSustainPlant(world, x, y - 1, z,
                        ForgeDirection.UP, Winecraft.grapeSeeds));
    }

    /***
     * Decide what to drop when destroy
     */
    @Override
    public int idDropped(int metadata, Random random, int par2) {
        
    	switch (metadata) {
        case 0:
            return Winecraft.grapeSeeds.itemID;
        case 1:
            return Winecraft.grapeFruit.itemID;
        default:
            return -1; // air
        }
    }

    @Override
    public int idPicked(World world, int x, int y, int z) {
        return Winecraft.grapeSeeds.itemID;
    }
	
	@Override
    public String getTextureFile () {
            return CommonProxy.BLOCK_PNG;
    }
}
