package bettervanilla.client;

import cpw.mods.fml.client.registry.RenderingRegistry;
import bettervanilla.CommonProxy;

public class ClientProxy extends CommonProxy 
{
	@Override
	public int addArmor(String armor) 
	{
		return RenderingRegistry.addNewArmourRendererPrefix(armor);
	}
}
