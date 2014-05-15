package com.agadar.bettervanilla.misc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

public class FakeEntityPlayer extends EntityPlayer {

	public FakeEntityPlayer(World par1World, String par2Str) { super(par1World, par2Str); }

	@Override
	public void sendChatToPlayer(ChatMessageComponent chatmessagecomponent) { }

	@Override
	public boolean canCommandSenderUseCommand(int i, String s) { return false; }

	@Override
	public ChunkCoordinates getPlayerCoordinates() { return null; }
}
