package com.agadar.bettervanilla.events;

import com.agadar.bettervanilla.handlers.ModConfigurations;

import net.minecraftforge.common.MinecraftForge;

public class ModEvents 
{
	/**
     * Subscribes all events.
     * Called in the preInit
     */
	public static void subscribeEvents() 
	{
		// Register the event hook for using clay and wool on a cauldron, and for using a glass bottle on lava.
		// It is checked in the hook itself whether these modules are enabled. 
		MinecraftForge.EVENT_BUS.register(new PlayerInteractHook());
		
		// Register the event hook for intercepting chickens spawning for the pluckable chickens module, as well
		// as ALL spawning for the mob filter module. It is checked in the hook itself whether these modules are enabled.
		MinecraftForge.EVENT_BUS.register(new EntityJoinWorldHook());
		
		if (ModConfigurations.Apples || ModConfigurations.Ice) {
			// Register the event hook for increasing the drop rate of apples from leaves and altering the ice block's item drop behavior.
			// It is checked in the hook itself whether these modules are enabled. 
			MinecraftForge.EVENT_BUS.register(new BreakHook());
		}
	}
}
