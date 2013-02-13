package tutorial.winecraft;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BasicBlock extends Block{

	public BasicBlock(int id, int texture, Material material) {
		super(id, texture, material);
	}
	
	 @Override
     public String getTextureFile () {
             return CommonProxy.BLOCK_PNG;
     }
}
