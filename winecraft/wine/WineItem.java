package winecraft.wine;

import winecraft.Winecraft;
import net.minecraft.client.renderer.texture.IconRegister;
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
	public void registerIcons(IconRegister iconRegister){
	    this.itemIcon = iconRegister.registerIcon(Winecraft.modid+":"+"wine");
    }
	public ItemStack onEaten( ItemStack itemStack, World world, EntityPlayer player){
		player.addPotionEffect(effect);
		
		
		return super.onEaten(itemStack, world, player);
	}
	
	public void setFoodEffect(PotionEffect effect){
		this.effect = effect;
	}
}
