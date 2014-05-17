package com.agadar.bettervanilla.renderers;

import com.agadar.bettervanilla.BetterVanilla;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class CauldronWaterRenderer implements ISimpleBlockRenderingHandler 
{
	private final int renderId = RenderingRegistry.getNextAvailableRenderId();
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) 
	{		
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) 
	{
		renderer.renderStandardBlock(block, x, y, z);
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z));
        float f = 1.0F;
        int l = block.colorMultiplier(renderer.blockAccess, x, y, z);
        float f1 = (float)(l >> 16 & 255) / 255.0F;
        float f2 = (float)(l >> 8 & 255) / 255.0F;
        float f3 = (float)(l & 255) / 255.0F;
        float f4;

        if (EntityRenderer.anaglyphEnable)
        {
            float f5 = (f1 * 30.0F + f2 * 59.0F + f3 * 11.0F) / 100.0F;
            f4 = (f1 * 30.0F + f2 * 70.0F) / 100.0F;
            float f6 = (f1 * 30.0F + f3 * 70.0F) / 100.0F;
            f1 = f5;
            f2 = f4;
            f3 = f6;
        }

        tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
        IIcon icon = block.getBlockTextureFromSide(2);
        f4 = 0.125F;
        renderer.renderFaceXPos(block, (double)((float)x - 1.0F + f4), (double)y, (double)z, icon);
        renderer.renderFaceXNeg(block, (double)((float)x + 1.0F - f4), (double)y, (double)z, icon);
        renderer.renderFaceZPos(block, (double)x, (double)y, (double)((float)z - 1.0F + f4), icon);
        renderer.renderFaceZNeg(block, (double)x, (double)y, (double)((float)z + 1.0F - f4), icon);
        IIcon icon1 = BetterVanilla.cauldronWater.getCauldronIcon("inner");
        renderer.renderFaceYPos(block, (double)x, (double)((float)y - 1.0F + 0.25F), (double)z, icon1);
        renderer.renderFaceYNeg(block, (double)x, (double)((float)y + 1.0F - 0.75F), (double)z, icon1);
        int i1 = renderer.blockAccess.getBlockMetadata(x, y, z);

        if (i1 > 0)
        {
        	IIcon icon2;
        	
        	if (block == BetterVanilla.cauldronLava) icon2 = BlockLiquid.getLiquidIcon("lava_still");
        	else icon2 = BlockLiquid.getLiquidIcon("water_still");
        	
            if (i1 > 3)
            {
                i1 = 3;
            }

            renderer.renderFaceYPos(block, (double)x, (double)((float)y - 1.0F + (6.0F + (float)i1 * 3.0F) / 16.0F), (double)z, icon2);
        }

        return true;
	}


	@Override
	public int getRenderId() 
	{
		return renderId;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) 
	{
		return false;
	}
}
