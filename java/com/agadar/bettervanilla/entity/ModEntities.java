package com.agadar.bettervanilla.entity;

import com.agadar.bettervanilla.BetterVanilla;
import com.agadar.bettervanilla.help.ModConfigurations;

import cpw.mods.fml.common.registry.EntityRegistry;

/** Manages all mod entities. */
public class ModEntities 
{
	/** Registers this mod's entities and their renderers. */
	public static void registerModEntities()
	{
		if (ModConfigurations.PluckableChickens)
		{
			EntityRegistry.registerGlobalEntityID(EntityPluckableChicken.class, "Pluckable Chicken", EntityRegistry.findGlobalUniqueEntityId());
			BetterVanilla.proxy.registerPluckableChicken();
		}
	}
}
