package com.agadar.bettervanilla.events;

import com.agadar.bettervanilla.entities.EntityPluckableChicken;
import com.agadar.bettervanilla.handlers.ModConfigurations;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class EventEntityJoinWorld 
{
	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event)
	{		
		// Intercept chickens spawning and substitute the chicken with our pluckable chicken.
		if (ModConfigurations.PluckableChickens && event.entity.getClass() == EntityChicken.class)
		{
			EntityPluckableChicken chicken = new EntityPluckableChicken(event.world);
			chicken.setLocationAndAngles(event.entity.posX, event.entity.posY, event.entity.posZ, event.entity.rotationYaw, event.entity.rotationPitch);
			chicken.setHealth(((EntityLivingBase) event.entity).getHealth());
			chicken.renderYawOffset = ((EntityLivingBase) event.entity).renderYawOffset;
	        event.world.spawnEntityInWorld(chicken);
			event.setCanceled(true);
		}
		
		// Intercept all spawning and filter the mobs in the filter list. 
		if (ModConfigurations.MobFilter && event.entity instanceof EntityLiving)
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
