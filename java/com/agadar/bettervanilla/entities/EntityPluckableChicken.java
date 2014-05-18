package com.agadar.bettervanilla.entities;

import java.util.ArrayList;

import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

public class EntityPluckableChicken extends EntityChicken implements IShearable
{
	/** The time until the feathers grow back. */
	public int timeUntilFeathers;
	
	public EntityPluckableChicken(World par1World) 
	{
		super(par1World);
		this.timeUntilFeathers = this.rand.nextInt(3000) + 3000;
	}
	
	@Override
	public void onLivingUpdate()
	{		
		super.onLivingUpdate();
		if (!this.isChild() && !this.worldObj.isRemote && this.getSheared() && --this.timeUntilFeathers <= 0)
        {
			this.setSheared(false);
            this.timeUntilFeathers = this.rand.nextInt(3000) + 3000;
        }
	}
	
	@Override
	protected void dropFewItems(boolean par1, int par2)
    {
        int j = this.rand.nextInt(3) + this.rand.nextInt(1 + par2);

        if (!this.getSheared())	
        {
        	for (int k = 0; k < j; ++k)
        	{
        		this.dropItem(Items.feather, 1);
        	}
        }

        if (this.isBurning())
        {
            this.dropItem(Items.cooked_chicken, 1);
        }
        else
        {
            this.dropItem(Items.chicken, 1);
        }
    }
	
	@Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setBoolean("Sheared", this.getSheared());
    }

	@Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.setSheared(par1NBTTagCompound.getBoolean("Sheared"));
    }
	
	@Override
	protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte((byte)0));
    }
    
    public boolean getSheared()
    {
        return (this.dataWatcher.getWatchableObjectByte(16) & 16) != 0;
    }

    public void setSheared(boolean par1)
    {
        byte b0 = this.dataWatcher.getWatchableObjectByte(16);

        if (par1)
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 | 16)));
        }
        else
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 & -17)));
        }
    }


    @Override
	public boolean isShearable(ItemStack item, IBlockAccess world, int x, int y, int z) 
    {
    	return !getSheared() && !isChild();
	}
    

	@Override
	public ArrayList<ItemStack> onSheared(ItemStack item, IBlockAccess world, int x, int y, int z, int fortune) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        setSheared(true);
        int i = rand.nextInt(3) + 2;
        for (int j = 0; j < i; j++)
        {
            ret.add(new ItemStack(Items.feather));
        }
        this.worldObj.playSoundAtEntity(this, "mob.sheep.shear", 1.0F, 1.0F);
        return ret;
	}
}
