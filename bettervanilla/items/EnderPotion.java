package bettervanilla.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;

public class EnderPotion extends Item
{
	public EnderPotion(int par1) 
	{
		super(par1);
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.tabBrewing);
	}
	
	@Override
	public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		if (!par2World.isRemote) this.teleportPlayerRandomly(par2World, par3EntityPlayer);
		
        if (!par3EntityPlayer.capabilities.isCreativeMode)
        {
        	--par1ItemStack.stackSize;
        	
        	if (par1ItemStack.stackSize <= 0) return new ItemStack(Item.glassBottle);
        	
            if (!par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Item.glassBottle)))
	            par3EntityPlayer.dropPlayerItem(new ItemStack(Item.glassBottle));
        }       
                
        return par1ItemStack;
    }

	@Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 32;
    }

	@Override
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.drink;
    }

	@Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        return par1ItemStack;
    }
	
	private void teleportPlayerRandomly(World par1World, EntityPlayer par2EntityPlayer)
	{       
		double d3 = par2EntityPlayer.posX;
        double d4 = par2EntityPlayer.posY;
        double d5 = par2EntityPlayer.posZ;
        boolean flag = false;
        
        while (!flag)
        {
        	par2EntityPlayer.posX = d3;
        	par2EntityPlayer.posY = d4;
        	par2EntityPlayer.posZ = d5;

        	double par1 = par2EntityPlayer.posX + (par1World.rand.nextDouble() - 0.5D) * 64.0D;
        	double par3 = par2EntityPlayer.posY + (double)(par1World.rand.nextInt(64) - 32);
        	double par5 = par2EntityPlayer.posZ + (par1World.rand.nextDouble() - 0.5D) * 64.0D;  

        	par2EntityPlayer.posX = par1;
        	par2EntityPlayer.posY = par3;
        	par2EntityPlayer.posZ = par5;

        	int i = MathHelper.floor_double(par2EntityPlayer.posX);
        	int j = MathHelper.floor_double(par2EntityPlayer.posY);
        	int k = MathHelper.floor_double(par2EntityPlayer.posZ);
        	int l;

        	if (par1World.blockExists(i, j, k))
        	{
        		boolean flag1 = false;

        		while (!flag1 && j > 0)
        		{
        			l = par1World.getBlockId(i, j - 1, k);

        			if (l != 0 && Block.blocksList[l].blockMaterial.blocksMovement())
        			{
        				flag1 = true;
        			}
        			else
        			{
        				--par2EntityPlayer.posY;
        				--j;
        			}
        		}

        		if (flag1)
        		{
        			par2EntityPlayer.setPositionAndUpdate(par2EntityPlayer.posX, par2EntityPlayer.posY, par2EntityPlayer.posZ);

        			if (par1World.getCollidingBoundingBoxes(par2EntityPlayer, par2EntityPlayer.boundingBox).isEmpty())// && !par1World.isAnyLiquid(par2EntityPlayer.boundingBox))
        			{
        				flag = true;
        			}
        		}
        	}
        }
	}

}
