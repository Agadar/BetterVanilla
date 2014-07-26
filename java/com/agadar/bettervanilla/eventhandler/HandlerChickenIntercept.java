package com.agadar.bettervanilla.eventhandler;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import com.agadar.bettervanilla.entity.EntityPluckableChicken;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class HandlerChickenIntercept 
{
	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event)
	{		
		// Intercept chickens spawning and substitute the chicken with our pluckable chicken.
		if (event.entity.getClass() == EntityChicken.class)
		{
			EntityPluckableChicken chicken = new EntityPluckableChicken(event.world);
			chicken.setLocationAndAngles(event.entity.posX, event.entity.posY, event.entity.posZ, event.entity.rotationYaw, event.entity.rotationPitch);
			chicken.setHealth(((EntityLivingBase) event.entity).getHealth());
			chicken.renderYawOffset = ((EntityLivingBase) event.entity).renderYawOffset;
	        event.world.spawnEntityInWorld(chicken);
			event.setCanceled(true);
		}
	}
}
