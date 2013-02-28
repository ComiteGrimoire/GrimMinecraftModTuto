/**
 * Copyright (C) 2013  Philippe Babin<philippe.babin@gmail.com> and François Drouin-Morin<>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package tutorial.winecraft;

import tutorial.winecraft.barrel.BlockBarrel;
import tutorial.winecraft.barrel.GuiBarrel;
import tutorial.winecraft.barrel.TileEntityBarrel;
import tutorial.winecraft.wine.WineItem;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.src.ModLoader;
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
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid="Winecraft", name="Winecraft", version="0.0.0")

/**
@NetworkMod(clientSideRequired=true, serverSideRequired=false,
clientPacketHandlerSpec =
@SidedPacketHandler(channels = {"TutorialMod" }, packetHandler = ClientPacketHandler.class),
serverPacketHandlerSpec =
@SidedPacketHandler(channels = {"TutorialMod" }, packetHandler = ServerPacketHandler.class))*/
@NetworkMod(clientSideRequired=true, serverSideRequired=false)
public class Winecraft {
    	@Instance("Winecraft")
    	public static Winecraft instance;
    
    	private GuiHandler guiHandler = new GuiHandler();

    
		public static final Block grapeCrop = new GrapeCrop(504);
		public static final Block barrelBlock = new BlockBarrel(505, Material.iron);
		public static final ItemSeeds grapeSeeds = (ItemSeeds) new ItemSeeds(5001,
	            									grapeCrop.blockID, 
	            									Block.tilledField.blockID)
	    											.setIconIndex(2)
	    											.setItemName("seeds.grape")
	    											.setTextureFile(CommonProxy.ITEMS_PNG);
	    public static final Item grapeFruit = new WinecraftItem(5002);
	    public static final Item wine = new WineItem(5003, 2, false).setItemName("drink.wine")
	    		.setIconIndex(1)
	    		.setTextureFile(CommonProxy.ITEMS_PNG);
	    
       
        @SidedProxy(clientSide="tutorial.winecraft.client.ClientProxy", serverSide="tutorial.winecraft.CommonProxy")
        public static CommonProxy proxy;
       
        @PreInit
        public void preInit(FMLPreInitializationEvent event) {
                // Stub Method
        }
       
        @Init
        public void load(FMLInitializationEvent event) {
                
               //Barrel stuffs

                NetworkRegistry.instance().registerGuiHandler(this, guiHandler);
                
                GameRegistry.registerTileEntity(TileEntityBarrel.class, "TileEntityBarrel");
                
                barrelBlock
            	.setTextureFile(CommonProxy.BLOCK_PNG)
            	.setCreativeTab(CreativeTabs.tabBlock);
            
	            GameRegistry.registerBlock(barrelBlock, "Barrel");
                
	            
                //Variable declarations
                ItemStack grapes = new ItemStack(grapeFruit);
                
                // Add grape seed
                MinecraftForge.addGrassSeed(new ItemStack(grapeSeeds), 10);

                //Add grape fruits

                //Add grape crops
                GameRegistry.registerBlock(grapeCrop, "GrapeCrop");
                
                //Add wine
         
                //Recipes go here
                //Four grapes yield 1 wine
                GameRegistry.addShapelessRecipe(new ItemStack(wine), grapes, grapes, grapes, grapes);
                registerNames();
                
                proxy.registerRenderers();
        }
       
        @PostInit
        public void postInit(FMLPostInitializationEvent event) {
                // Stub Method
        }
        
        /**
         * Method to register names in the LanguageRegistry
         */
        private void registerNames()
        {
            LanguageRegistry.addName(barrelBlock, "Barrel");
            LanguageRegistry.addName(grapeFruit, "Grape");
            LanguageRegistry.addName(grapeSeeds, "Grape Seeds");
            LanguageRegistry.addName(wine, "Wine");
        }
        
        /**
         * Method to register recipes in the CraftingManager
         */
        private void registerRecipes()
        {
        	//Variable declarations
            ItemStack grapes = new ItemStack(grapeFruit);
            
        	//Shapeless recipes
            GameRegistry.addShapelessRecipe(new ItemStack(grapeSeeds, 4), new ItemStack(grapeFruit));
            
            //Shaped recipes
            
            //Debug stuff
            //Four grapes yield 1 wine
            GameRegistry.addShapelessRecipe(new ItemStack(wine), grapes, grapes, grapes, grapes);
        }
}