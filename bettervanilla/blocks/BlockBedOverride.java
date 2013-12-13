package bettervanilla.blocks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import bettervanilla.BetterVanilla;
import bettervanilla.tileentities.BedColor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumStatus;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Direction;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class BlockBedOverride extends BlockDirectional implements ITileEntityProvider
{
	/** Maps the foot-of-bed block to the head-of-bed block. */
    public static final int[][] footBlockToHeadBlockMap = new int[][] {{0, 1}, { -1, 0}, {0, -1}, {1, 0}};
	@SideOnly(Side.CLIENT)
    private Icon[][] field_94472_b;
    @SideOnly(Side.CLIENT)
    private Icon[][] bedSideIcons;
    @SideOnly(Side.CLIENT)
    private Icon[][] bedTopIcons;
    
	public BlockBedOverride(int par1) 
	{
		super(par1, Material.cloth);
        this.setBounds();
		this.disableStats();
	}
	
	@Override
	public int getRenderType()
    {
        return 14;
    }
	
	@Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
	
	@Override
    public boolean isOpaqueCube()
    {
        return false;
    }
	
	@Override
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        this.setBounds();
    }
	
	@Override
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        int i1 = par1World.getBlockMetadata(par2, par3, par4);
        int j1 = getDirection(i1);

        if (isBlockHeadOfBed(i1))
        {
            if (par1World.getBlockId(par2 - footBlockToHeadBlockMap[j1][0], par3, par4 - footBlockToHeadBlockMap[j1][1]) != this.blockID)
            {
                par1World.setBlockToAir(par2, par3, par4);
            }
        }
        else if (par1World.getBlockId(par2 + footBlockToHeadBlockMap[j1][0], par3, par4 + footBlockToHeadBlockMap[j1][1]) != this.blockID)
        {
            par1World.setBlockToAir(par2, par3, par4);

            if (!par1World.isRemote)
            {
                this.dropBlockAsItem(par1World, par2, par3, par4, i1, 0);
            }
        }
    }

	@Override
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return 0;
    }

    private void setBounds()
    {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5625F, 1.0F);
    }

    public static boolean isBlockHeadOfBed(int par0)
    {
        return (par0 & 8) != 0;
    }

    public static boolean isBedOccupied(int par0)
    {
        return (par0 & 4) != 0;
    }

    public static void setBedOccupied(World par0World, int par1, int par2, int par3, boolean par4)
    {
        int l = par0World.getBlockMetadata(par1, par2, par3);

        if (par4)
        {
            l |= 4;
        }
        else
        {
            l &= -5;
        }

        par0World.setBlockMetadataWithNotify(par1, par2, par3, l, 4);
    }

    public static ChunkCoordinates getNearestEmptyChunkCoordinates(World par0World, int par1, int par2, int par3, int par4)
    {
        int i1 = par0World.getBlockMetadata(par1, par2, par3);
        int j1 = BlockDirectional.getDirection(i1);

        for (int k1 = 0; k1 <= 1; ++k1)
        {
            int l1 = par1 - footBlockToHeadBlockMap[j1][0] * k1 - 1;
            int i2 = par3 - footBlockToHeadBlockMap[j1][1] * k1 - 1;
            int j2 = l1 + 2;
            int k2 = i2 + 2;

            for (int l2 = l1; l2 <= j2; ++l2)
            {
                for (int i3 = i2; i3 <= k2; ++i3)
                {
                    if (par0World.doesBlockHaveSolidTopSurface(l2, par2 - 1, i3) && !par0World.getBlockMaterial(l2, par2, i3).isOpaque() && !par0World.getBlockMaterial(l2, par2 + 1, i3).isOpaque())
                    {
                        if (par4 <= 0)
                        {
                            return new ChunkCoordinates(l2, par2, i3);
                        }

                        --par4;
                    }
                }
            }
        }

        return null;
    }

    @Override
    public int getMobilityFlag()
    {
        return 1;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int idPicked(World par1World, int par2, int par3, int par4)
    {
        return BetterVanilla.itemBedOverride.itemID;
    }
    
    @Override
    public void onBlockHarvested(World par1World, int par2, int par3, int par4, int par5, EntityPlayer par6EntityPlayer)
    {
        if (par6EntityPlayer.capabilities.isCreativeMode && isBlockHeadOfBed(par5))
        {
            int i1 = getDirection(par5);
            par2 -= footBlockToHeadBlockMap[i1][0];
            par4 -= footBlockToHeadBlockMap[i1][1];

            if (par1World.getBlockId(par2, par3, par4) == this.blockID)
            {
                par1World.setBlockToAir(par2, par3, par4);
            }
        }
    }
	
    @Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        if (par1World.isRemote)
        {
            return true;
        }
        else
        {
            int i1 = par1World.getBlockMetadata(par2, par3, par4);

            if (!isBlockHeadOfBed(i1))
            {
                int j1 = getDirection(i1);
                par2 += footBlockToHeadBlockMap[j1][0];
                par4 += footBlockToHeadBlockMap[j1][1];

                if (par1World.getBlockId(par2, par3, par4) != this.blockID)
                {
                    return true;
                }

                i1 = par1World.getBlockMetadata(par2, par3, par4);
            }

            if (par1World.provider.canRespawnHere() && par1World.getBiomeGenForCoords(par2, par4) != BiomeGenBase.hell)
            {
                if (isBedOccupied(i1))
                {
                    EntityPlayer entityplayer1 = null;
                    Iterator iterator = par1World.playerEntities.iterator();

                    while (iterator.hasNext())
                    {
                        EntityPlayer entityplayer2 = (EntityPlayer)iterator.next();

                        if (entityplayer2.isPlayerSleeping())
                        {
                            ChunkCoordinates chunkcoordinates = entityplayer2.playerLocation;

                            if (chunkcoordinates.posX == par2 && chunkcoordinates.posY == par3 && chunkcoordinates.posZ == par4)
                            {
                                entityplayer1 = entityplayer2;
                            }
                        }
                    }

                    if (entityplayer1 != null)
                    {
                        par5EntityPlayer.addChatMessage("tile.bed.occupied");
                        return true;
                    }

                    setBedOccupied(par1World, par2, par3, par4, false);
                }

                EnumStatus enumstatus = par5EntityPlayer.sleepInBedAt(par2, par3, par4);

                if (enumstatus == EnumStatus.OK)
                {
                    setBedOccupied(par1World, par2, par3, par4, true);
                    return true;
                }
                else
                {
                    if (enumstatus == EnumStatus.NOT_POSSIBLE_NOW)
                    {
                        par5EntityPlayer.addChatMessage("tile.bed.noSleep");
                    }
                    else if (enumstatus == EnumStatus.NOT_SAFE)
                    {
                        par5EntityPlayer.addChatMessage("tile.bed.notSafe");
                    }

                    return true;
                }
            }
            else
            {
                double d0 = (double)par2 + 0.5D;
                double d1 = (double)par3 + 0.5D;
                double d2 = (double)par4 + 0.5D;
                par1World.setBlockToAir(par2, par3, par4);
                int k1 = getDirection(i1);
                par2 += footBlockToHeadBlockMap[k1][0];
                par4 += footBlockToHeadBlockMap[k1][1];

                if (par1World.getBlockId(par2, par3, par4) == this.blockID)
                {
                    par1World.setBlockToAir(par2, par3, par4);
                    d0 = (d0 + (double)par2 + 0.5D) / 2.0D;
                    d1 = (d1 + (double)par3 + 0.5D) / 2.0D;
                    d2 = (d2 + (double)par4 + 0.5D) / 2.0D;
                }

                par1World.newExplosion((Entity)null, (double)((float)par2 + 0.5F), (double)((float)par3 + 0.5F), (double)((float)par4 + 0.5F), 5.0F, true, true);
                return true;
            }
        }
    }
	
	@SideOnly(Side.CLIENT)
	@Override
	public Icon getBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
		int meta = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
		int color = 0;
		
		BedColor tileEntity = (BedColor) par1IBlockAccess.getBlockTileEntity(par2, par3, par4);
		if (tileEntity != null)
		{
			color = tileEntity.getColor();
		}

        if (par5 == 0)
        {
            return Block.planks.getBlockTextureFromSide(par5);
        }
        else
        {
            int k = getDirection(meta);
            int l = Direction.bedDirection[k][par5];
            int i1 = isBlockHeadOfBed(meta) ? 1 : 0;
            return (i1 != 1 || l != 2) && (i1 != 0 || l != 3) ? (l != 5 && l != 4 ? this.bedTopIcons[color][i1] : this.bedSideIcons[color][i1]) : this.field_94472_b[color][i1];
        }
    }
	
	@SideOnly(Side.CLIENT)
	@Override
    public void registerIcons(IconRegister par1IconRegister)
    {
    	Icon head_end = par1IconRegister.registerIcon(this.getTextureName() + "_head_end");
        this.bedTopIcons = new Icon[][] { 
        		new Icon[] { par1IconRegister.registerIcon(this.getTextureName() + "_feet_top_white"), par1IconRegister.registerIcon(this.getTextureName() + "_head_top_white")},
        		new Icon[] { par1IconRegister.registerIcon(this.getTextureName() + "_feet_top_orange"), par1IconRegister.registerIcon(this.getTextureName() + "_head_top_orange")},
        		new Icon[] { par1IconRegister.registerIcon(this.getTextureName() + "_feet_top_magenta"), par1IconRegister.registerIcon(this.getTextureName() + "_head_top_magenta")},
        		new Icon[] { par1IconRegister.registerIcon(this.getTextureName() + "_feet_top_light_blue"), par1IconRegister.registerIcon(this.getTextureName() + "_head_top_light_blue")},
        		new Icon[] { par1IconRegister.registerIcon(this.getTextureName() + "_feet_top_yellow"), par1IconRegister.registerIcon(this.getTextureName() + "_head_top_yellow")},
        		new Icon[] { par1IconRegister.registerIcon(this.getTextureName() + "_feet_top_lime"), par1IconRegister.registerIcon(this.getTextureName() + "_head_top_lime")},
        		new Icon[] { par1IconRegister.registerIcon(this.getTextureName() + "_feet_top_pink"), par1IconRegister.registerIcon(this.getTextureName() + "_head_top_pink")},
        		new Icon[] { par1IconRegister.registerIcon(this.getTextureName() + "_feet_top_gray"), par1IconRegister.registerIcon(this.getTextureName() + "_head_top_gray")},
        		new Icon[] { par1IconRegister.registerIcon(this.getTextureName() + "_feet_top_silver"), par1IconRegister.registerIcon(this.getTextureName() + "_head_top_silver")},
        		new Icon[] { par1IconRegister.registerIcon(this.getTextureName() + "_feet_top_cyan"), par1IconRegister.registerIcon(this.getTextureName() + "_head_top_cyan")},
        		new Icon[] { par1IconRegister.registerIcon(this.getTextureName() + "_feet_top_purple"), par1IconRegister.registerIcon(this.getTextureName() + "_head_top_purple")},
        		new Icon[] { par1IconRegister.registerIcon(this.getTextureName() + "_feet_top_blue"), par1IconRegister.registerIcon(this.getTextureName() + "_head_top_blue")},
        		new Icon[] { par1IconRegister.registerIcon(this.getTextureName() + "_feet_top_brown"), par1IconRegister.registerIcon(this.getTextureName() + "_head_top_brown")},
        		new Icon[] { par1IconRegister.registerIcon(this.getTextureName() + "_feet_top_green"), par1IconRegister.registerIcon(this.getTextureName() + "_head_top_green")},
        		new Icon[] { par1IconRegister.registerIcon(this.getTextureName() + "_feet_top_red"), par1IconRegister.registerIcon(this.getTextureName() + "_head_top_red")},
        		new Icon[] { par1IconRegister.registerIcon(this.getTextureName() + "_feet_top_black"), par1IconRegister.registerIcon(this.getTextureName() + "_head_top_black")}};
        this.field_94472_b = new Icon[][] {
        		new Icon[] { par1IconRegister.registerIcon(this.getTextureName() + "_feet_end_white"), head_end},
        		new Icon[] { par1IconRegister.registerIcon(this.getTextureName() + "_feet_end_orange"), head_end},
        		new Icon[] { par1IconRegister.registerIcon(this.getTextureName() + "_feet_end_magenta"), head_end},
        		new Icon[] { par1IconRegister.registerIcon(this.getTextureName() + "_feet_end_light_blue"), head_end},
        		new Icon[] { par1IconRegister.registerIcon(this.getTextureName() + "_feet_end_yellow"), head_end},
        		new Icon[] { par1IconRegister.registerIcon(this.getTextureName() + "_feet_end_lime"), head_end},
        		new Icon[] { par1IconRegister.registerIcon(this.getTextureName() + "_feet_end_pink"), head_end},
        		new Icon[] { par1IconRegister.registerIcon(this.getTextureName() + "_feet_end_gray"), head_end},
        		new Icon[] { par1IconRegister.registerIcon(this.getTextureName() + "_feet_end_silver"), head_end},
        		new Icon[] { par1IconRegister.registerIcon(this.getTextureName() + "_feet_end_cyan"), head_end},
        		new Icon[] { par1IconRegister.registerIcon(this.getTextureName() + "_feet_end_purple"), head_end},
        		new Icon[] { par1IconRegister.registerIcon(this.getTextureName() + "_feet_end_blue"), head_end},
        		new Icon[] { par1IconRegister.registerIcon(this.getTextureName() + "_feet_end_brown"), head_end},
        		new Icon[] { par1IconRegister.registerIcon(this.getTextureName() + "_feet_end_green"), head_end},
        		new Icon[] { par1IconRegister.registerIcon(this.getTextureName() + "_feet_end_red"), head_end},
        		new Icon[] { par1IconRegister.registerIcon(this.getTextureName() + "_feet_end_black"), head_end}};
        this.bedSideIcons = new Icon[][] {
        		new Icon[] { par1IconRegister.registerIcon(this.getTextureName() + "_feet_side_white"), par1IconRegister.registerIcon(this.getTextureName() + "_head_side_white")},
        		new Icon[] { par1IconRegister.registerIcon(this.getTextureName() + "_feet_side_orange"), par1IconRegister.registerIcon(this.getTextureName() + "_head_side_orange")},
        		new Icon[] { par1IconRegister.registerIcon(this.getTextureName() + "_feet_side_magenta"), par1IconRegister.registerIcon(this.getTextureName() + "_head_side_magenta")},
        		new Icon[] { par1IconRegister.registerIcon(this.getTextureName() + "_feet_side_light_blue"), par1IconRegister.registerIcon(this.getTextureName() + "_head_side_light_blue")},
        		new Icon[] { par1IconRegister.registerIcon(this.getTextureName() + "_feet_side_yellow"), par1IconRegister.registerIcon(this.getTextureName() + "_head_side_yellow")},
        		new Icon[] { par1IconRegister.registerIcon(this.getTextureName() + "_feet_side_lime"), par1IconRegister.registerIcon(this.getTextureName() + "_head_side_lime")},
        		new Icon[] { par1IconRegister.registerIcon(this.getTextureName() + "_feet_side_pink"), par1IconRegister.registerIcon(this.getTextureName() + "_head_side_pink")},
        		new Icon[] { par1IconRegister.registerIcon(this.getTextureName() + "_feet_side_gray"), par1IconRegister.registerIcon(this.getTextureName() + "_head_side_gray")},
        		new Icon[] { par1IconRegister.registerIcon(this.getTextureName() + "_feet_side_silver"), par1IconRegister.registerIcon(this.getTextureName() + "_head_side_silver")},
        		new Icon[] { par1IconRegister.registerIcon(this.getTextureName() + "_feet_side_cyan"), par1IconRegister.registerIcon(this.getTextureName() + "_head_side_cyan")},
        		new Icon[] { par1IconRegister.registerIcon(this.getTextureName() + "_feet_side_purple"), par1IconRegister.registerIcon(this.getTextureName() + "_head_side_purple")},
        		new Icon[] { par1IconRegister.registerIcon(this.getTextureName() + "_feet_side_blue"), par1IconRegister.registerIcon(this.getTextureName() + "_head_side_blue")},
        		new Icon[] { par1IconRegister.registerIcon(this.getTextureName() + "_feet_side_brown"), par1IconRegister.registerIcon(this.getTextureName() + "_head_side_brown")},
        		new Icon[] { par1IconRegister.registerIcon(this.getTextureName() + "_feet_side_green"), par1IconRegister.registerIcon(this.getTextureName() + "_head_side_green")},
        		new Icon[] { par1IconRegister.registerIcon(this.getTextureName() + "_feet_side_red"), par1IconRegister.registerIcon(this.getTextureName() + "_head_side_red")}, new Icon[] { par1IconRegister.registerIcon(this.getTextureName() + "_feet_side_black"), par1IconRegister.registerIcon(this.getTextureName() + "_head_side_black")}};
    }

	@Override
	public TileEntity createNewTileEntity(World world) 
	{
		return new BedColor();
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, int i, int j) 
	{
		if (!world.isRemote && !isBlockHeadOfBed(j) && world.getGameRules().getGameRuleBooleanValue("doTileDrops"))
        {
			int color = 0;			
			BedColor tileEntity = (BedColor) world.getBlockTileEntity(x, y, z);
			if (tileEntity != null)
			{
				color = tileEntity.getColor();
			}
			
			float f = 0.7F;
            double d0 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            double d1 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            double d2 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            EntityItem entityitem = new EntityItem(world, (double)x + d0, (double)y + d1, (double)z + d2, 
            		new ItemStack(BetterVanilla.itemBedOverride, 1, color));
            entityitem.delayBeforeCanPickup = 10;
            world.spawnEntityInWorld(entityitem);
        }
		
		super.breakBlock(world, x, y, z, i, j);
		world.removeBlockTileEntity(x, y, z);
	}
}

