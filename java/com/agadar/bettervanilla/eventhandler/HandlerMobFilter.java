package com.agadar.bettervanilla.eventhandler;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import com.agadar.bettervanilla.help.ModConfigurations;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class HandlerMobFilter 
{
	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event)
	{		
		// Intercept all spawning and filter the mobs in the filter list. 
		if (event.entity instanceof EntityLiving)
		{
			for (String name : ModConfigurations.MobFilterList)
			{
				if (EntityList.getEntityString(event.entity).equals(name))
				{
					event.setCanceled(true);
					break;
				}
			}
		}
	}
}
