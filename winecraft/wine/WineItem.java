package tutorial.winecraft.wine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class WineItem extends ItemFood {
	
	
	private PotionEffect effect = new PotionEffect(Potion.poison.getId(),200,10);
	
	public WineItem(int ID, int healAmount, boolean IsWolfFood) {
		super(ID, healAmount, IsWolfFood);
		this.setAlwaysEdible();
	}
	
	public ItemStack onFoodEaten( ItemStack itemStack, World world, EntityPlayer player){
		player.addPotionEffect(effect);
		
		return super.onFoodEaten(itemStack, world, player);
	}
	
	public void setFoodEffect(PotionEffect effect){
		this.effect = effect;
	}
}
