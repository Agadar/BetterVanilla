package com.agadar.bettervanilla.entities;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderChicken;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.relauncher.Side;

@SideOnly(Side.CLIENT)
public class RenderPluckableChicken extends RenderChicken
{
	private static final ResourceLocation chickenTextures = new ResourceLocation("textures/entity/chicken.png");
    private static final ResourceLocation pluckedChickenTextures = new ResourceLocation("bettervanilla:textures/entity/plucked_chicken.png");

    public RenderPluckableChicken(ModelBase par1ModelBase, float par2)
    {
        super(par1ModelBase, par2);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
    	if (!((PluckableChicken)par1Entity).getSheared()) return chickenTextures;
    	return pluckedChickenTextures;
    }
}
