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

package winecraft.vineyard.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import winecraft.network.WinecraftPacket;
import winecraft.vineyard.TileEntityVineyard;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class PacketHandlerVineyard implements IPacketHandler {

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(packet.data));
		try {
			int packetID = data.read();
			/** Client to Server */
			if(packetID == 22) {
				WinecraftPacket p = new WinecraftPacket();
				p.readData(data);

				/** We load the TileEntity instance on the server */
				TileEntity tile = ((EntityPlayer) player).worldObj.getBlockTileEntity(p.posX, p.posY, p.posZ);
				if (tile instanceof TileEntityVineyard) {
					/** We build the fence */
					((TileEntityVineyard) tile).buildFences(((EntityPlayer) player).worldObj, p.payload[0], p.payload[1]);
					
					/** If an error has occurred we send it to the client. */
					if(((TileEntityVineyard) tile).getErrorMessage() != ""){
				     	String[] payload = new String[1];
				     	payload[0] = ((TileEntityVineyard) tile).getErrorMessage();
				     	WinecraftPacket packetError = new WinecraftPacket( 21,p.posX, p.posY, p.posZ, new int[0], payload);
				     	
				     	PacketDispatcher.sendPacketToPlayer(packetError.getPacket(), player);
					}
				}
			}
			/** Server Error to Client */
			if(packetID == 21) {
				WinecraftPacket p = new WinecraftPacket();
				p.readData(data);
				
				TileEntity tile = ((EntityPlayer) player).worldObj.getBlockTileEntity(p.posX, p.posY, p.posZ);
				if (tile instanceof TileEntityVineyard) {
					((TileEntityVineyard) tile).setErrorMessage(p.payloadStr[0]);
				}
			}
			/** Server to Client */
			if(packetID == 20) {
				WinecraftPacket p = new WinecraftPacket();
				p.readData(data);
				/** We convert Player (the Network instance) to EntityPlayer (the usable instance) */
				TileEntity tile = ((EntityPlayer) player).worldObj.getBlockTileEntity(p.posX, p.posY, p.posZ);
				if (tile instanceof TileEntityVineyard) {
					((TileEntityVineyard) tile).setOffsetY(p.payload[0]);
					((TileEntityVineyard) tile).setVineyardDelimited(true);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}


	}

}
