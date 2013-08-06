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

package winecraft;

import winecraft.barrel.BarrelBlock;
import winecraft.barrel.GuiBarrel;
import winecraft.barrel.TileEntityBarrel;
import winecraft.vineyard.TileEntityVineyard;
import winecraft.vineyard.VineyardBlock;
import winecraft.vineyard.network.*;
import winecraft.wine.WineItem;
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
import net.minecraft.tileentity.TileEntity;
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

@Mod(modid=Winecraft.modid, name="Winecraft", version="0.0.0")

/**
@NetworkMod(clientSideRequired=true, serverSideRequired=false,
clientPacketHandlerSpec =
@SidedPacketHandler(channels = {"TutorialMod" }, packetHandler = ClientPacketHandler.class),
serverPacketHandlerSpec =
@SidedPacketHandler(channels = {"TutorialMod" }, packetHandler = ServerPacketHandler.class))*/
@NetworkMod(channels = { "Winecraft" }, packetHandler = PacketHandlerVineyard.class, clientSideRequired = true, serverSideRequired = true)
public class Winecraft {
    	@Instance("winecraft")
    	public static Winecraft instance;
    	
    	public static final String modid = "winecraft";
    
    	private GuiHandler guiHandler = new GuiHandler();

    
		public static final Block grapeCrop = new GrapeCrop(504)
													.setUnlocalizedName("grapecrop");
		public static final Block barrelBlock = new BarrelBlock(505, Material.iron)
													.setUnlocalizedName("barrel_top");
		public static final Block vineyardBlock = new VineyardBlock(506, Material.wood)
													.setUnlocalizedName("vineyard")
													.setCreativeTab(CreativeTabs.tabBlock);
		// icon 2
		public static final GrapeSeed grapeSeeds = (GrapeSeed) new GrapeSeed(5001, grapeCrop.blockID,  Block.tilledField.blockID)
	    											.setUnlocalizedName("seeds");
	    public static final Item grapeFruit = new GrapeItem(5002).setUnlocalizedName("grape");
	    // icon 1
	    public static final Item wine = new WineItem(5003, 2, false).setUnlocalizedName("wine");
	    
       
        @SidedProxy(clientSide="tutorial.winecraft.client.ClientProxy", serverSide="tutorial.winecraft.CommonProxy")
        public static CommonProxy proxy;
       
        @PreInit
        public void preInit(FMLPreInitializationEvent event) {
        }
       
        @Init
        public void load(FMLInitializationEvent event) {
                NetworkRegistry.instance().registerGuiHandler(this, guiHandler);
                
                GameRegistry.registerTileEntity(TileEntityBarrel.class, "TileEntityBarrel");
                GameRegistry.registerTileEntity(TileEntityVineyard.class, "TileEntityVineyard");
                GameRegistry.registerTileEntity(TileEntityGrapeCrop.class, "TileEntityGrapeCrop");
                
                // Adding barrel recipe
	            TileEntityBarrel.addRecipe(300, 400, 2, this.grapeFruit, this.wine);
	            TileEntityBarrel.addRecipe(30, 40, 6, Item.appleRed, this.wine); //debug
                
                barrelBlock.setCreativeTab(CreativeTabs.tabBlock);
            
	            GameRegistry.registerBlock(barrelBlock, "Barrel");
	            barrelBlock.setUnlocalizedName("Barrel");
	            GameRegistry.registerBlock(vineyardBlock, "Vineyard Delimiter");
	            vineyardBlock.setUnlocalizedName("Vineyard Delimiter");
	            
	            
	            TileEntity.addMapping(TileEntityVineyard.class, "collector");
	            
                //Variable declarations
                ItemStack grapes = new ItemStack(grapeFruit);
                
                // Add grape seed
                MinecraftForge.addGrassSeed(new ItemStack(grapeSeeds), 10);

                //Add grape crops
                GameRegistry.registerBlock(grapeCrop, "GrapeCrop");
                
                registerNames();
                registerRecipes();
                
                proxy.registerRenderers();
        }
        
        /**
         * Method to register names in the LanguageRegistry
         */
        private void registerNames(){
            LanguageRegistry.addName(vineyardBlock, "Vineyard Delimiter");
            LanguageRegistry.addName(barrelBlock, "Barrel");
            LanguageRegistry.addName(grapeFruit, "Grape");
            LanguageRegistry.addName(grapeSeeds, "Grape Seeds");
            LanguageRegistry.addName(wine, "Wine");
        }
        
        /**
         * Method to register recipes in the CraftingManager
         */
        private void registerRecipes(){
        	/** Barrel */
        	GameRegistry.addShapedRecipe(new ItemStack(barrelBlock), 
					"xxx", 
					"xyx", 
					"xxx",
					'x', new ItemStack(Block.planks), 
					'y', new ItemStack(grapeFruit));
        	
        	/** Vineyard delimiter */
        	GameRegistry.addShapedRecipe(new ItemStack(vineyardBlock), 
					"xxx", 
					"xyx", 
					"xxx",
					'x', new ItemStack(Block.fence), 
					'y', new ItemStack(grapeFruit));
        }
}