package traveller.communication;

import java.io.IOException;
import javax.bluetooth.RemoteDevice;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;
import traveller.util.CommunicationException;

public class NXTCommunication extends EmbeddedCommunication{

	private BTConnection conn;
	
	public NXTCommunication(String pc_name){
		super(pc_name);
	}
	
	@Override
	public void connect() throws CommunicationException{
		String name = pc_name;
		RemoteDevice device = Bluetooth.getKnownDevice(name);
	    if (device == null) 
	    	throw new CommunicationException("failed in get the Bluetooth device: ");
		conn = Bluetooth.connect(device);
		if (conn == null) 
			throw new CommunicationException("failed in connect to server: ");
		dis = conn.openDataInputStream();
	    dos = conn.openDataOutputStream();
	}
	
	@Override
	public void close() throws CommunicationException{
		super.close();
	    conn.close();
	}
	
	@Override
	protected void sendMatrix(int size, boolean[][] map) throws CommunicationException {
	    try {
			dos.writeInt(size);
			dos.flush(); 
			for(int i=0; i<size; i++){
				for(int j=0; j<size; j++){
					dos.writeBoolean(map[i][j]);
					dos.flush(); 
				}
			}
		} catch (IOException e) {
			throw new CommunicationException("failed in send a map to server: ");
		}
	}
	
	@Override
	protected void sendString(String str) throws CommunicationException {
		int size = str.length();
		try { 
			dos.writeInt(size);
			dos.flush();
			byte[] dados = str.getBytes();
			dos.write(dados);
	    } catch (IOException ioe) {
	        throw new CommunicationException("failed in send a string to server: ");
	    }
	}

	@Override
	protected void sendPoint(int init_pos_x, int init_pos_y) throws CommunicationException{
		try {
			dos.flush();
			dos.writeInt(init_pos_x);
			dos.flush();
			dos.writeInt(init_pos_y);
		} catch (IOException e) {
	        throw new CommunicationException("failed in send a point to server: ");
		}
	}
	
	@Override
	protected void sendBoolean(boolean free_value_map) throws CommunicationException {
		try {
			dos.flush();
			dos.writeBoolean(free_value_map);
		} catch (IOException e) {
			throw new CommunicationException("failed in send a boolean to server: ");
		}
	}
	
	@Override
	protected void sendFloat(float value) throws CommunicationException {
		try {
			dos.flush();
			dos.writeFloat(value);
		} catch (IOException e) {
			throw new CommunicationException("failed in send a float to server: ");
		}
	}
	
	@Override
	protected float receiveFloat() throws CommunicationException{
		float value = 0;
		try {
			value = dis.readFloat();
		} catch (IOException e) {
			throw new CommunicationException("failed in receive a float to server: ");
		}
		return value;
	}
	
	@Override
	protected int receiveInt() throws CommunicationException{
		int value = 0;
		try {
			dis.skipBytes(2);
			value = dis.readInt();
		} catch (IOException e) {
			throw new CommunicationException("failed in receive a int to server: ");
		}
		return value;
	}

}
