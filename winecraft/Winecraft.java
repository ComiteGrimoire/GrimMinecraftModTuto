package tutorial.winecraft;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid="Winecraft", name="Winecraft", version="0.0.0")
@NetworkMod(clientSideRequired=true, serverSideRequired=false)
public class Winecraft {
		public static final Block grapeCrop = new GrapeCrop(504);
	    public static final ItemSeeds grapeSeeds = (ItemSeeds) new ItemSeeds(5001,
	            grapeCrop.blockID, Block.tilledField.blockID).setIconIndex(2).setItemName("seeds.grape").setTextureFile(CommonProxy.ITEMS_PNG);
	    public static final Item grapeFruit = new WinecraftItem(5002);
	    
        // The instance of your mod that Forge uses.
        @Instance("Winecraft")
        public static Winecraft instance;
       
        // Says where the client and server 'proxy' code is loaded.
        @SidedProxy(clientSide="tutorial.winecraft.client.ClientProxy", serverSide="tutorial.winecraft.CommonProxy")
        public static CommonProxy proxy;
       
        @PreInit
        public void preInit(FMLPreInitializationEvent event) {
                // Stub Method
        }
       
        @Init
        public void load(FMLInitializationEvent event) {
                proxy.registerRenderers();
                
                // Add grape seed
                LanguageRegistry.addName(grapeSeeds, "Grape Seeds");
                MinecraftForge.addGrassSeed(new ItemStack(grapeSeeds), 10);

                LanguageRegistry.addName(grapeFruit, "Grape");
                GameRegistry.addShapelessRecipe(new ItemStack(grapeSeeds, 4),
                        new ItemStack(grapeFruit));

                GameRegistry.registerBlock(grapeCrop, "GrapeCrop");
        }
       
        @PostInit
        public void postInit(FMLPostInitializationEvent event) {
                // Stub Method
        }
}