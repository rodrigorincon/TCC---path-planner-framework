package traveller.communication;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import traveller.util.CommunicationException;
import traveller.util.Path;
import traveller.util.Point;

public abstract class EmbeddedCommunication {
	
	protected String pc_name;
	protected DataInputStream dis;
	protected DataOutputStream dos;
	
	public EmbeddedCommunication(String pc_name){
		this.pc_name = pc_name;
	}
	
	public abstract void connect() throws CommunicationException;
	
	public void close() throws CommunicationException{
		try {
			dis.close();
		} catch (IOException ioe){}
		try {
			dos.close();
		} catch (IOException e) {}
	}
	
	protected abstract void sendMatrix(int size, boolean[][] map) throws CommunicationException;
	
	protected abstract void sendString(String str) throws CommunicationException;

	protected abstract void sendPoint(int init_pos_x, int init_pos_y) throws CommunicationException;
	
	protected abstract void sendBoolean(boolean free_value_map) throws CommunicationException;
	
	protected abstract void sendFloat(float value) throws CommunicationException;
	
	protected abstract float receiveFloat() throws CommunicationException;
	
	protected abstract int receiveInt() throws CommunicationException;
		
	public void sendData(boolean[][] map, String path_planner,String best_path, 
			float robot_width, float cell_width,int init_pos_x, int init_pos_y, 
			int goal_pos_x, int goal_pos_y, boolean free_value_map, 
			boolean expand_obstacles) throws CommunicationException {	
		
		sendPoint(init_pos_x, init_pos_y);
		sendPoint(goal_pos_x, goal_pos_y);
		sendString(path_planner);
		sendString(best_path);
		sendMatrix(map.length, map);
		sendBoolean(free_value_map);
		sendFloat(robot_width);
		sendFloat(cell_width);
		sendBoolean(expand_obstacles);
		sendBoolean(expand_obstacles);
	}

	public Path receivePath() throws CommunicationException {
		int size_msg = receiveInt();
		if(size_msg<0)
			throw new CommunicationException("path null");
		List<Point> route = new LinkedList<Point>();
		for(int i=0; i<size_msg; i++){
			float x = receiveFloat();
			float y = receiveFloat();
			Point p = new Point(x, y);
			route.add(p);
		}
		float size = receiveFloat();
		return new Path(route, size);
	}
	
}
