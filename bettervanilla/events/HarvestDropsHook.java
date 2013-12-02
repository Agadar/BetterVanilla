package bettervanilla.events;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import bettervanilla.BetterVanilla;

public class HarvestDropsHook {
	
	@ForgeSubscribe
	public void onHarvestDrops(HarvestDropsEvent event) 
	{
		if ((event.block.blockID == Block.enderChest.blockID && BetterVanilla.EnderChests) ||
				((event.block.blockID == Block.bookShelf.blockID && BetterVanilla.BookShelves))) 
		{
			event.drops.clear();
			event.drops.add(new ItemStack(event.block));
			event.dropChance = 1.0f;
		}
	}
}
