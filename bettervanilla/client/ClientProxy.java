package bettervanilla.client;

import net.minecraft.client.model.ModelChicken;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.RenderingRegistry;
import bettervanilla.CommonProxy;
import bettervanilla.entities.PluckedChicken;
import bettervanilla.entities.RenderPluckedChicken;

public class ClientProxy extends CommonProxy 
{
	@Override
	public int addArmor(String armor) 
	{
		return RenderingRegistry.addNewArmourRendererPrefix(armor);
	}
	
	@Override
	public void registerRenderThings() 
	{
		RenderingRegistry.registerEntityRenderingHandler(PluckedChicken.class, new RenderPluckedChicken(new ModelChicken(), 0.3F));
    }
    
	@Override
    public void registerSound() 
    {
    }
}
