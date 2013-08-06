package winecraft;

import net.minecraft.client.renderer.texture.IconRegister;

public class GrapeItem extends WinecraftItem {

	public GrapeItem(int id) {
		super(id);
	}
	public void registerIcons(IconRegister iconRegister){
	    this.itemIcon = iconRegister.registerIcon(Winecraft.modid+":"+"grape");
    }
}
