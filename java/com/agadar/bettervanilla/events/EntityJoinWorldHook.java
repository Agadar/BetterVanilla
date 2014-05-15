package com.agadar.bettervanilla.events;

import com.agadar.bettervanilla.BetterVanilla;
import com.agadar.bettervanilla.entities.PluckableChicken;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class EntityJoinWorldHook 
{
	@ForgeSubscribe
	public void onEntityJoinWorld(EntityJoinWorldEvent event)
	{		
		// Intercept chickens spawning and substitute the chicken with our pluckable chicken.
		if (BetterVanilla.PluckableChickens && event.entity.getClass() == EntityChicken.class)
		{
			PluckableChicken chicken = new PluckableChicken(event.world);
			chicken.setLocationAndAngles(event.entity.posX, event.entity.posY, event.entity.posZ, event.entity.rotationYaw, event.entity.rotationPitch);
			chicken.setHealth(((EntityLivingBase) event.entity).getHealth());
			chicken.renderYawOffset = ((EntityLivingBase) event.entity).renderYawOffset;
	        event.world.spawnEntityInWorld(chicken);
			event.setCanceled(true);
		}
		
		// Intercept all spawning and filter the mobs in the filter list. 
		if (BetterVanilla.MobFilter && event.entity instanceof EntityLiving)
		{
			for (String name : BetterVanilla.MobFilterList)
			{
				if (event.entity.getEntityName().equals(name))
				{
					event.setCanceled(true);
					break;
				}
			}
		}
	}
}
