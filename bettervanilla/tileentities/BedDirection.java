package bettervanilla.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public class BedDirection extends TileEntity {
	private static final String VARIABLENAME = "_direction";
	private int _direction;
	
	public int getDirection() {
		return _direction;
	}
	
	public void setDirection(int direction) {
		_direction = direction;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound var1)
	{
		var1.setInteger(VARIABLENAME, _direction);
	    super.writeToNBT(var1);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound var1)
	{
		_direction = var1.getInteger(VARIABLENAME);
	    super.readFromNBT(var1);
	}
	
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound tileTag = new NBTTagCompound();
		this.writeToNBT(tileTag);
		return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 0, tileTag);
	}
	
	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
		this.readFromNBT(pkt.data);
	}
}