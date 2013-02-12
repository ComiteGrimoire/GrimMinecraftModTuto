package tutorial.winecraft;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
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

@Mod(modid="Winecraft", name="Winecraft", version="0.0.0")
@NetworkMod(clientSideRequired=true, serverSideRequired=false)
public class Winecraft {

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
                
                
                ItemStack dirtStack = new ItemStack(Block.dirt);
                ItemStack diamondsStack = new ItemStack(Item.diamond, 64);

                GameRegistry.addShapelessRecipe(diamondsStack, dirtStack);
        }
       
        @PostInit
        public void postInit(FMLPostInitializationEvent event) {
                // Stub Method
        }
}