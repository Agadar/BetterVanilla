package bettervanilla.events;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import bettervanilla.entities.PluckableChicken;

public class EntityJoinWorldHook 
{
	@ForgeSubscribe
	public void onEntityJoinWorld(EntityJoinWorldEvent event)
	{
		// Intercept chickens spawning and substitute the chicken with our pluckable chicken.
		if (event.entity.getClass() == EntityChicken.class)
		{
			PluckableChicken chicken = new PluckableChicken(event.world);
			chicken.setLocationAndAngles(event.entity.posX, event.entity.posY, event.entity.posZ, event.entity.rotationYaw, event.entity.rotationPitch);
			chicken.setHealth(((EntityLivingBase) event.entity).getHealth());
			chicken.renderYawOffset = ((EntityLivingBase) event.entity).renderYawOffset;
	        event.world.spawnEntityInWorld(chicken);
			event.setCanceled(true);
		}
	}
}
