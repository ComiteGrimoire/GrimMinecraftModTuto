package winecraft;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemSeeds;

public class GrapeSeed extends ItemSeeds {

	public GrapeSeed(int par1, int par2, int par3) {
		super(par1, par2, par3);
	}
	public void registerIcons(IconRegister iconRegister){
	    this.itemIcon = iconRegister.registerIcon(Winecraft.modid+":"+"seeds");
    }
}
