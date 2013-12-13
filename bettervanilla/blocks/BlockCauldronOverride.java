package bettervanilla.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import bettervanilla.BetterVanilla;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCauldron;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;

public class BlockCauldronOverride extends BlockCauldron 
{
	@SideOnly(Side.CLIENT)
    public Icon field_94378_a;
    @SideOnly(Side.CLIENT)
    private Icon cauldronTopIcon;
    @SideOnly(Side.CLIENT)
    private Icon cauldronBottomIcon;
	
	public BlockCauldronOverride(int par1) 
	{
		super(par1);
	}
	
	@Override
	public int getRenderType()
    {
        return BetterVanilla.blockCauldronOverrideRenderer.getRenderId();
    }
	
	
	@Override
	public Icon getIcon(int par1, int par2)
    {
        return par1 == 1 ? this.cauldronTopIcon : (par1 == 0 ? this.cauldronBottomIcon : this.blockIcon);
    }
	
	@Override
	public void registerIcons(IconRegister par1IconRegister)
    {
		this.field_94378_a = par1IconRegister.registerIcon(this.getTextureName() + "_" + "inner");
        this.cauldronTopIcon = par1IconRegister.registerIcon(this.getTextureName() + "_top");
        this.cauldronBottomIcon = par1IconRegister.registerIcon(this.getTextureName() + "_" + "bottom");
        this.blockIcon = par1IconRegister.registerIcon(this.getTextureName() + "_side");
    }
	
	@SideOnly(Side.CLIENT)
    public static Icon getCauldronIcon(String par0Str)
    {
		return par0Str.equals("inner") ? BetterVanilla.cauldronOverride.field_94378_a : (par0Str.equals("bottom") ? BetterVanilla.cauldronOverride.cauldronBottomIcon : null);
    }
}
