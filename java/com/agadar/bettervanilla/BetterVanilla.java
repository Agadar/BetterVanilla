package com.agadar.bettervanilla;

import com.agadar.bettervanilla.CommonProxy;
import com.agadar.bettervanilla.handlers.ModConfigurations;
import com.agadar.bettervanilla.handlers.ModRecipes;
import com.agadar.bettervanilla.help.References;
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
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = References.MODID, version = References.VERSION, name = References.NAME, useMetadata = true)
public class BetterVanilla 
{		
	@Instance(value = References.NAME)
	public static BetterVanilla instance;

	@SidedProxy(clientSide = References.CLIENTSIDE, serverSide = References.SERVERSIDE)
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
}
