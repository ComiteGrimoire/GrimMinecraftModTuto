package tutorial.winecraft.wine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class WineItem extends ItemFood {

	public WineItem(int ID, int healAmount, boolean IsWolfFood) {
		super(ID, healAmount, IsWolfFood);
		// TODO Auto-generated constructor stub
		this.setAlwaysEdible();
	}
	
}
