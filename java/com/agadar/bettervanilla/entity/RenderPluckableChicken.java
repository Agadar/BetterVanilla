package com.agadar.bettervanilla.entity;

import com.agadar.bettervanilla.help.References;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderChicken;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.relauncher.Side;

@SideOnly(Side.CLIENT)
public class RenderPluckableChicken extends RenderChicken
{
	private static final ResourceLocation chickenTextures = new ResourceLocation("textures/entity/chicken.png");
    private static final ResourceLocation pluckedChickenTextures = new ResourceLocation(References.MODID + ":textures/entities/plucked_chicken.png");

    public RenderPluckableChicken(ModelBase par1ModelBase, float par2)
    {
        super(par1ModelBase, par2);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
    	if (!((EntityPluckableChicken)par1Entity).getSheared()) return chickenTextures;
    	return pluckedChickenTextures;
    }
}
