package com.agadar.bettervanilla.blocks;

import com.agadar.bettervanilla.handlers.ModConfigurations;
import com.agadar.bettervanilla.help.RegisterHelper;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;

public class ModBlocks 
{
	// Blocks
	public static Block colored_bed;
	public static Block water_cauldron;
	public static Block lava_cauldron;
	public static Block cactus;
	
	// Renderers
	public static ISimpleBlockRenderingHandler renderWaterCauldron;
	
	public static void loadBlocks()
	{
		if (ModConfigurations.ColoredBeds)
		{
			colored_bed = new BlockColoredBed();
			RegisterHelper.registerBlock(colored_bed);
		}
		
		if (ModConfigurations.Cacti)
		{
			cactus = new BlockCactus2();
			RegisterHelper.registerBlock(cactus);
		}
		
		if (ModConfigurations.CauldronsLava)
		{
			water_cauldron = new BlockWaterCauldron().setBlockName("water_cauldron");
			RegisterHelper.registerBlock(water_cauldron);
			
			lava_cauldron = new BlockLavaCauldron().setBlockName("lava_cauldron");
			RegisterHelper.registerBlock(lava_cauldron);
			
			renderWaterCauldron = new RenderWaterCauldron();
			RenderingRegistry.registerBlockHandler(renderWaterCauldron);
		}
	}
}