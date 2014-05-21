package com.agadar.bettervanilla;

import com.agadar.bettervanilla.CommonProxy;
import com.agadar.bettervanilla.handlers.ModConfigurations;
import com.agadar.bettervanilla.handlers.ModRecipes;
import com.agadar.bettervanilla.help.Reference;
import com.agadar.bettervanilla.blocks.ModBlocks;
import com.agadar.bettervanilla.dispenserbehaviors.ModDispenserBehaviors;
import com.agadar.bettervanilla.entities.ModEntities;
import com.agadar.bettervanilla.events.ModEvents;
import com.agadar.bettervanilla.items.ModItems;
import com.agadar.bettervanilla.tileentities.ModTileEntities;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MODID, version = Reference.VERSION)
public class BetterVanilla 
{		
	@Instance(value = "BetterVanilla")
	public static BetterVanilla instance;

	@SidedProxy(clientSide = "com.agadar.bettervanilla.client.ClientProxy", serverSide = "com.agadar.bettervanilla.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) 
	{		
		ModConfigurations.loadConfigurations(event.getSuggestedConfigurationFile());
		
		ModBlocks.loadBlocks();
		
		ModTileEntities.registerTileEntities();
		
		ModItems.loadItems();
		
		ModRecipes.addRecipes();
		
		ModDispenserBehaviors.setDispenserBehaviors();
		
		ModEntities.registerEntities();
		
		ModEvents.subscribeEvents();
	}
	
	@EventHandler
	public void load(FMLInitializationEvent event) 
	{	
		/*if (MoreStairs)
		{
			for (String blockName : StairsMaterials) 
			{
				for (Block block : Blocks.blocksList)
				{
					if (block != null && block.getLocalizedName().equals(blockName)) 
					{
						Block stairs = (new BlockStairs2(MoreStairsID++, block, 0)).setBlockName(blockName + "Stairs");
						GameRegistry.registerBlock(stairs, blockName + "Stairs");
						GameRegistry.addRecipe(new ItemStack(stairs, 4), "x  ", "xx ", "xxx", 'x', block);
					}
				}
			}
		}*/
	}
}
