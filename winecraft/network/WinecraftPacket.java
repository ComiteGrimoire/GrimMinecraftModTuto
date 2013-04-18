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

package tutorial.winecraft.network;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;

public class WinecraftPacket {
	protected String channel = "Winecraft";

	private int id;

	public int posX;
	public int posY;
	public int posZ;
	
	public int[] payload = new int[0];
	public String[] payloadStr = new String[0];

	public WinecraftPacket(){
		
	}
	
	public WinecraftPacket(int id, int x, int y, int z, int[] payload){
		this.id = id;
		
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		
		this.payload = payload;
		this.payloadStr = new String[0];
	}
	
	public WinecraftPacket(int id, int x, int y, int z, int[] payloadInt, String[] payloadStr){
		this.id = id;
		
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		
		this.payload = payloadInt;
		this.payloadStr = payloadStr;
	}
	
	public Packet getPacket() {
		
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		DataOutputStream data = new DataOutputStream(bytes);
		try {
			data.writeByte(getID());
			writeData(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = channel;
		packet.data = bytes.toByteArray();
		packet.length = packet.data.length;
		
		return packet;
	}

	
	public void writeData(DataOutputStream data) throws IOException {
	
		data.writeInt(posX);
		data.writeInt(posY);
		data.writeInt(posZ);
		
		/** Empty payload */
		
		data.writeInt(payload.length);
		data.writeInt(payloadStr.length);
		
		for (int i = 0; i < payload.length; i++) {
			data.writeInt(payload[i]);
		}
		
		for (int i = 0; i < payloadStr.length; i++) {
			data.writeUTF(payloadStr[i]);
		}
	}
	
	public void readData(DataInputStream data) throws IOException {
	
		posX = data.readInt();
		posY = data.readInt();
		posZ = data.readInt();
		
		payload = new int[data.readInt()];
		payloadStr = new String[data.readInt()];
		
		for (int i = 0; i < payload.length; i++) {
			payload[i] = data.readInt();
		}
		
		
		for (int i = 0; i < payloadStr.length; i++) {
			payloadStr[i] = data.readUTF();
		}
	}
	
	public int getID() {
		return id;
	}
}
