package model;

public class Tool {
	
	String name;
	int id;
	double x, y;
	
	public Tool() {
	}

	public Tool(String name_, int id_, double xCoord, double yCoord) {
		
		name=name_;
		id=id_;
		x=xCoord;
		y=yCoord;
	}
	
//	public Tool(Vehicle veh) {
//		name=veh.getName();
//		id=veh.getIndex();
//		x=veh.getX();
//		y=veh.getY();
//	}
	
	public String printPosition() {return ("( "+getX()+", "+getY()+")");}
	
	/*
	 * getter methods
	 */
	public int getIndex() {return id;}
	
	public double getX() {return x;}
	
	public double getY() {return y;}
	
	public Position getPos() {return new Position(x, y);}
	
	public String getName() {return name;}
	
	/*
	 * setter methods
	 */
	public void setPosition(Position pos) {
		
		this.x = pos.getX();
		this.y = pos.getY();
	}
}