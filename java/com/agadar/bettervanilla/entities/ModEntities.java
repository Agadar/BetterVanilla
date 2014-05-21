package com.agadar.bettervanilla.entities;

import com.agadar.bettervanilla.BetterVanilla;
import com.agadar.bettervanilla.handlers.ModConfigurations;

import cpw.mods.fml.common.registry.EntityRegistry;

public class ModEntities 
{
	public static void registerEntities()
	{
		if (ModConfigurations.PluckableChickens)
		{
			// Register our pluckable chicken and its renderer.
			EntityRegistry.registerGlobalEntityID(EntityPluckableChicken.class, "Pluckable Chicken", EntityRegistry.findGlobalUniqueEntityId());
			BetterVanilla.proxy.registerPluckableChicken();
		}
	}
}
