package com.agadar.bettervanilla.potion;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.minecraft.potion.Potion;

/** Responsible for instantiating this mod's Potions. */
public class ModPotions 
{
	/** The potion that sets the player on fire. */
	public final static Potion fire;
	
	static
	{
		openUpPotionTypes();		

		/* Ensures that we start with a potion id of which the corresponding Potion is null. */
		int id = 32;
		while (id < Potion.potionTypes.length && Potion.potionTypes[id] != null) id++;
		
		fire = new PotionBase(id++, true, 0).setPotionName("potion.fire");
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
