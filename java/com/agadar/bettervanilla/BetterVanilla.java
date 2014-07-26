package com.agadar.bettervanilla;

import com.agadar.bettervanilla.CommonProxy;
import com.agadar.bettervanilla.help.ModConfigurations;
import com.agadar.bettervanilla.help.ModRecipes;
import com.agadar.bettervanilla.help.References;
import com.agadar.bettervanilla.block.ModBlocks;
import com.agadar.bettervanilla.dispenserbehavior.ModDispenserBehaviors;
import com.agadar.bettervanilla.entity.ModEntities;
import com.agadar.bettervanilla.eventhandler.ModEventHandlers;
import com.agadar.bettervanilla.item.ModItems;
import com.agadar.bettervanilla.tileentity.ModTileEntities;

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
		
		ModBlocks.registerModBlocks();
		
		ModTileEntities.registerModTileEntities();
		
		ModItems.registerModItems();
		
		ModRecipes.registerModRecipes();
		
		ModDispenserBehaviors.registerModDispenserBehaviors();
		
		ModEntities.registerModEntities();
		
		ModEventHandlers.registerModEventHandlers();
	}
}
