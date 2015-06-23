package robotCommunication;

import java.io.*;
import java.util.List;

import javax.bluetooth.*;
import javax.microedition.io.*;

import util.Path;
import util.Point;

public class BluetoothCommunicator {

	private final UUID uuid = new UUID("27012f0c68af4fbf8dbe6bbaf7aa432a", false);
    private final String name = "Echo Server";
    private final String url  =  "btspp://localhost:" + uuid+ ";name=" + name+ ";authenticate=false;encrypt=false;";
    
    private StreamConnectionNotifier server = null;
    private StreamConnection conn = null;
    private DataInputStream input = null;
    private DataOutputStream output = null;
    
    public void open() throws IOException{
    	LocalDevice device = null;
    	System.out.println("Initializing server...");
        device = LocalDevice.getLocalDevice();
        device.setDiscoverable(DiscoveryAgent.GIAC);
        server = (StreamConnectionNotifier)Connector.open(url);
    }
    
    public void connect() throws IOException{
        System.out.println("Waiting for incoming connection...");
        conn = server.acceptAndOpen();
        input = new DataInputStream(conn.openInputStream());
        output = new DataOutputStream(conn.openOutputStream());
        System.out.println("Client Connected...");   
    }
    
	public void close() {
		try {
			input.close();
			output.close();
			conn.close();
		} catch (IOException e) {
			System.out.println("close falied: "+e.getMessage());
		}
	}
    
    public boolean[][] getMap() throws IOException{
    	int size = input.readInt();
    	boolean[][] map = new boolean[size][size]; 
        for(int line=0; line<size; line++){
        	for(int col=0; col<size; col++){
        		input.skipBytes(2);
        		map[line][col] = input.readBoolean(); 
    	    }
    	}
        return map;
    }
    
    public String getString() throws IOException{
    	int size = input.readInt();
    	input.skipBytes(2);
    	byte[] bytes = new byte[size];
    	input.read(bytes);
    	return new String(bytes);
    }
    
    public int[] getPoint() throws IOException{
    	input.skipBytes(2);
    	int x = input.readInt();
    	input.skipBytes(2);
    	int y = input.readInt();
    	return new int[]{x, y}; 
    }

	public boolean getBoolean() throws IOException {
		input.skipBytes(2);
    	return input.readBoolean();
	}
	
	public float getFloat() throws IOException {
		input.skipBytes(2);
    	return input.readFloat();
	}

	public void sendPathSize(float size) throws IOException{
		output.flush();
		output.writeFloat(size);
	}

	public void sendPath(Path path) throws IOException {
		List<Point> list = path.getRoute();
		int size_msg = list.size();
		output.writeInt(size_msg);
		output.flush(); 
		for(int i=0; i<size_msg; i++){
			output.writeFloat(list.get(i).getX());
			output.flush(); 
			output.writeFloat(list.get(i).getY());
			output.flush();
		}
	}

	public void sendFail() throws IOException {
		output.flush();
		output.writeInt(-1);
	}

}
