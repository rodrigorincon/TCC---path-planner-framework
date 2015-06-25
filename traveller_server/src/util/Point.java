package util;

public class Point {

	private float x, y;
	
	public Point(float x, float y){
		this.x=x;
		this.y=y;
	}
	
	public float getX(){
		return x;
	}
	public float getY(){
		return y;
	}
	public void setX(float new_x){
		this.x = new_x;
	}
	public void setY(float new_y){
		this.y = new_y;
	}
	
	public String toString(){
		return ((int)y)+","+((int)x);
	}
	public float dist(Point pt){
		return (float)Math.sqrt(Math.pow(pt.getX()-x,2)+Math.pow(pt.getY()-y,2));
	}

	public String toFloatString() {
		return ((float)y)+","+((float)x);
	}

}
