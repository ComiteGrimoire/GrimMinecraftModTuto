/**
 * Copyright (C) 2013  Philippe Babin<philippe.babin@gmail.com> and François Drouin-Morin<>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package tutorial.winecraft.vineyard.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import tutorial.winecraft.network.WinecraftPacket;
import tutorial.winecraft.vineyard.TileEntityVineyard;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandlerVineyard implements IPacketHandler {

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(packet.data));
		try {
			int packetID = data.read();
			if(packetID == 22) {
				WinecraftPacket p = new WinecraftPacket();
				p.readData(data);
				// We convert Player (the Network instance) to EntityPlayer (the usable instance)
				TileEntity tile = ((EntityPlayer) player).worldObj.getBlockTileEntity(p.posX, p.posY, p.posZ);
				if (tile instanceof TileEntityVineyard) {
					((TileEntityVineyard) tile).buildFences(((EntityPlayer) player).worldObj, p.payload[0], p.payload[1]);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}


	}

}
