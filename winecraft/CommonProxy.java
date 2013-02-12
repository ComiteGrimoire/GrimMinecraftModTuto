package tutorial.winecraft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler {
	public static String ITEMS_PNG = "/tutorial/winecraft/items.png";
	public static String BLOCK_PNG = "/tutorial/winecraft/block.png";
	
	// Client stuff
	public void registerRenderers () {
		// Nothing here as this is the server side proxy
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
        
        return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
	    
	    return null;
	}
}