package tutorial.winecraft.barrel;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBrewingStand;
import net.minecraft.world.World;
import tutorial.winecraft.BasicBlock;
import tutorial.winecraft.CommonProxy;

public class BarrelBlock extends BlockContainer{
	
	public BarrelBlock(int id, Material material){
		super(id, material);
		this.blockIndexInTexture = 3;
	}

	
	public TileEntity createNewTileEntity(World var1){
        return new BarrelTileEntity();
	}
}
