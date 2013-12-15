package bettervanilla.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;

public class CauldronContents extends TileEntity
{
	private static final String VARIABLENAME = "_contents";
	private String _contents = "empty";

	public String getContents() 
	{
		return _contents;
	}

	public void setContents(String contents) 
	{
		_contents = contents;
	}

	@Override
	public void writeToNBT(NBTTagCompound var1)
	{
		var1.setString(VARIABLENAME, _contents);
		super.writeToNBT(var1);
	}

	@Override
	public void readFromNBT(NBTTagCompound var1)
	{
		_contents = var1.getString(VARIABLENAME);
		super.readFromNBT(var1);
	}

	@Override
	public Packet getDescriptionPacket() 
	{
		NBTTagCompound tileTag = new NBTTagCompound();
		this.writeToNBT(tileTag);
		return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 0, tileTag);
	}

	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) 
	{
		this.readFromNBT(pkt.data);
	}
}
