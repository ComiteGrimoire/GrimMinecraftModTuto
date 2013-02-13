package tutorial.winecraft;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class WinecraftItem extends Item {

	public WinecraftItem (int id) {
        super(id);

        // Constructor Configuration
        maxStackSize = 64;
        setCreativeTab(CreativeTabs.tabMisc);
        setIconIndex(0);
        setItemName("genericItem");
    }

    public WinecraftItem (int id, int maxStackSize, CreativeTabs tab, int texture, String name) {
        super(id);
        setMaxStackSize(maxStackSize);
        setCreativeTab(tab);
        setIconIndex(texture);
        setItemName(name);
    }
	
	public String getTextureFile() {
        return CommonProxy.ITEMS_PNG;
	}
}
