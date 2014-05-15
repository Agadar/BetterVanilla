package com.agadar.bettervanilla.events;

import net.minecraft.block.Block;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.BonemealEvent;

public class BonemealHook {

	@ForgeSubscribe
	public void onBoneMealUse(BonemealEvent event) {
		int blockID = event.world.getBlockId(event.X, event.Y, event.Z);
		if (blockID == Block.reed.blockID || blockID == Block.cactus.blockID) {
			if (event.world.isAirBlock(event.X, event.Y + 1, event.Z)) {
				if (event.world.getBlockId(event.X, event.Y - 2, event.Z) != blockID) {
					event.world.setBlock(event.X, event.Y + 1, event.Z, blockID);
					event.world.setBlockMetadataWithNotify(event.X, event.Y, event.Z, 0, 4);
					event.setResult(Result.ALLOW);
				}
			} else if (event.world.isAirBlock(event.X, event.Y + 2, event.Z)) {
				if (event.world.getBlockId(event.X, event.Y + 1, event.Z) == blockID
						&& event.world.getBlockId(event.X, event.Y - 1, event.Z) != blockID) {
					event.world.setBlock(event.X, event.Y + 2, event.Z, blockID);
					event.world.setBlockMetadataWithNotify(event.X, event.Y + 1, event.Z, 0, 4);
					event.setResult(Result.ALLOW);
				}
			}
		} else if (blockID == Block.netherStalk.blockID) {
			int l = event.world.getBlockMetadata(event.X, event.Y, event.Z);
	        if (l < 3)
	        {
	            ++l;
	            event.world.setBlockMetadataWithNotify(event.X, event.Y, event.Z, l, 2);
	            event.setResult(Result.ALLOW);
	        }
		}
	}
}
