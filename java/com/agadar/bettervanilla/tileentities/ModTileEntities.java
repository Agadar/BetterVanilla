package com.agadar.bettervanilla.tileentities;

import com.agadar.bettervanilla.handlers.ModConfigurations;
import com.agadar.bettervanilla.help.References;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModTileEntities 
{
	public static void registerTileEntities()
	{
		if (ModConfigurations.ColoredBeds)
		{
			// Register the tile entity that is responsible for storing the bed's direction.
			GameRegistry.registerTileEntity(TileEntityBedColor.class, References.MODID + "_TileEntityBedColor");
		}
	}
}
