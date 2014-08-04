package com.agadar.bettervanilla.potion;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.minecraft.potion.Potion;

/** Responsible for managing this mod's Potions. */
public class ModPotions 
{
	/** The potion that sets the affected on fire. */
	public final static Potion fire;
	/** The potion that cures all potion effects. */
	public final static Potion cure;
	/** The potion that teleports the affected to a random location. */
	public final static Potion ender;
	
	/** The next unique Potion Id. Should only be used and altered by getUniquePotionId(). */
	private static int nextPotionId = 32;
	
	static
	{
		openUpPotionTypes();		
		
		fire = new PotionBase(getUniquePotionId(), true, 0).setPotionName("potion.fire");
		cure = new PotionBase(getUniquePotionId(), false, 0).setPotionName("potion.cure");
		ender = new PotionBase(getUniquePotionId(), false, 0).setPotionName("potion.ender");
	}
	
	/** Returns the next unique Potion Id. */
	private static int getUniquePotionId()
	{
		while (nextPotionId < Potion.potionTypes.length && Potion.potionTypes[nextPotionId] != null)
		{
			nextPotionId++;
		}
		
		return nextPotionId;
	}
	
	/** Calling this method allows us to register new Potions and modify existing Potions. */
	private static void openUpPotionTypes()
	{
		Potion[] potionTypes = null;

	    for (Field f : Potion.class.getDeclaredFields()) 
	    {
	        f.setAccessible(true);
	        
	        try 
	        {
	            if (f.getName().equals("potionTypes") || f.getName().equals("field_76425_a")) 
	            {
	                Field modfield = Field.class.getDeclaredField("modifiers");
	                modfield.setAccessible(true);
	                modfield.setInt(f, f.getModifiers() & ~Modifier.FINAL);

	                potionTypes = (Potion[])f.get(null);
	                final Potion[] newPotionTypes = new Potion[256];
	                System.arraycopy(potionTypes, 0, newPotionTypes, 0, potionTypes.length);
	                f.set(null, newPotionTypes);
	            }
	        } 
	        catch (Exception e) 
	        {
	            System.err.println(e);
	        }
	    }
	}
}
