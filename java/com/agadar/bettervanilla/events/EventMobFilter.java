package com.agadar.bettervanilla.events;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import com.agadar.bettervanilla.handlers.ModConfigurations;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EventMobFilter 
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
