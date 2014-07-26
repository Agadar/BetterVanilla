package com.agadar.bettervanilla.dispenserbehavior;

import java.util.Iterator;

import com.agadar.bettervanilla.help.ModConfigurations;

import net.minecraft.block.BlockDispenser;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraftforge.common.IPlantable;

/** Manages all mod dispenser behaviors. */
public class ModDispenserBehaviors 
{
	/** Registers all mod dispenser behaviors. */
	@SuppressWarnings("unchecked")
	public static void registerModDispenserBehaviors()
	{
		if (ModConfigurations.Dispensers) 
		{
			BlockDispenser.dispenseBehaviorRegistry.putObject(Items.shears, new DispenserBehaviorShears());
			BlockDispenser.dispenseBehaviorRegistry.putObject(Items.reeds, new DispenserBehaviorUniversal());
			Iterator<Item> items = Item.itemRegistry.iterator();
			
			while (items.hasNext()) 
			{
				Item item = items.next();
				
				if (item instanceof ItemBlock || item instanceof IPlantable || item instanceof ItemHoe) 
					BlockDispenser.dispenseBehaviorRegistry.putObject(item, new DispenserBehaviorUniversal());
			}
		}
	}
}
