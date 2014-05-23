package com.agadar.bettervanilla.client;

import com.agadar.bettervanilla.CommonProxy;
import com.agadar.bettervanilla.entities.EntityPluckableChicken;
import com.agadar.bettervanilla.entities.RenderPluckableChicken;

import net.minecraft.client.model.ModelChicken;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy 
{
	@Override
	public void registerPluckableChicken() 
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityPluckableChicken.class, new RenderPluckableChicken(new ModelChicken(), 0.3F));
    }
}
