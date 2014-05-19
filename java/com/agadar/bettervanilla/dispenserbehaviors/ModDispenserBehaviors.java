package com.agadar.bettervanilla.dispenserbehaviors;

import java.util.Iterator;

import com.agadar.bettervanilla.handlers.ModConfigurations;

import net.minecraft.block.BlockDispenser;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraftforge.common.IPlantable;

public class ModDispenserBehaviors 
{
	public static void setDispenserBehaviors()
	{
		if (ModConfigurations.Dispensers) 
		{
			// Register our dispenser behaviors
			BlockDispenser.dispenseBehaviorRegistry.putObject(Items.shears, new DispenserBehaviorShears());
			BlockDispenser.dispenseBehaviorRegistry.putObject(Items.reeds, new DispenserBehaviorUniversal());

			@SuppressWarnings("rawtypes")
			Iterator items = Item.itemRegistry.iterator();
			while (items.hasNext()) 
			{
				Item item = (Item) items.next();
				if (item instanceof ItemBlock || item instanceof IPlantable || item instanceof ItemHoe) {
					BlockDispenser.dispenseBehaviorRegistry.putObject(item, new DispenserBehaviorUniversal());
				}
			}
		}
	}
}
