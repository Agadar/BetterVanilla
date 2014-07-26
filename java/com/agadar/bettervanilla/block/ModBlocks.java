package com.agadar.bettervanilla.block;

import com.agadar.bettervanilla.help.ModConfigurations;
import com.agadar.bettervanilla.help.RegisterHelper;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

/** Manages all mod blocks. */
public class ModBlocks 
{
	// Blocks
	public static Block colored_bed;
	public static Block water_cauldron;
	public static Block lava_cauldron;
	public static Block milk_cauldron;
	public static Block cactus;
	public static Block milk_block;
	
	// Fluids
	public static Fluid milk;
	
	// Renderers
	public static ISimpleBlockRenderingHandler renderWaterCauldron;
	
	/** Instantiates and registers all mod blocks. */
	public static void registerModBlocks()
	{	
		if (ModConfigurations.Cacti)
		{
			cactus = new BlockCactus2();
			RegisterHelper.registerBlock(cactus);
		}
		
		if (ModConfigurations.ColoredBeds)
		{
			colored_bed = new BlockColoredBed();
			RegisterHelper.registerBlock(colored_bed);
		}
		
		if (ModConfigurations.CauldronsLava)
		{
			/** Instantiate the milk fluid and its block and register them. */
			milk = new Fluid("milk");
			FluidRegistry.registerFluid(milk);
			milk_block = new BlockFluidMilk(milk, Material.water);
			RegisterHelper.registerBlock(milk_block);
			milk.setUnlocalizedName(milk_block.getUnlocalizedName());
			
			/** Instantiate the cauldrons and register them.  */
			water_cauldron = new BlockWaterCauldron().setBlockName("water_cauldron_block");
			lava_cauldron = new BlockLavaCauldron().setBlockName("lava_cauldron");
			milk_cauldron = new BlockMilkCauldron().setBlockName("milk_cauldron");
			RegisterHelper.registerBlock(water_cauldron);
			RegisterHelper.registerBlock(lava_cauldron);
			RegisterHelper.registerBlock(milk_cauldron);
			
			/** Set up the renderer for the cauldrons. */
			renderWaterCauldron = new RenderWaterCauldron();
			RenderingRegistry.registerBlockHandler(renderWaterCauldron);
		}
	}
}