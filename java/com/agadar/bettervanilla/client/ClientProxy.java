package com.agadar.bettervanilla.client;

import com.agadar.bettervanilla.CommonProxy;
import com.agadar.bettervanilla.entities.PluckableChicken;
import com.agadar.bettervanilla.entities.RenderPluckableChicken;

import net.minecraft.client.model.ModelChicken;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy 
{
	@Override
	public int addArmor(String armor) 
	{
		return RenderingRegistry.addNewArmourRendererPrefix(armor);
	}
	
	@Override
	public void registerPluckableChicken() 
	{
		RenderingRegistry.registerEntityRenderingHandler(PluckableChicken.class, new RenderPluckableChicken(new ModelChicken(), 0.3F));
    }
}
