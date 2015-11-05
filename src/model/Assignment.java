package model;

public class Assignment {
	int id;
	Tool source, destination;
	int prio;
	int state = 0; // wartend / geladen / fertig
	boolean assigned = false; // zugeordnet ja/nein
	
	// Bearbeitungsstatus: true=in Bearbeitung
	// TODO vielleicht mit enum arbeiten: pending, active, finished
//	public enum State{PENDING, ACTIVE, FINISHED}
	
	public Assignment(int id_, Tool source_, Tool destination_, int prio_) {
		try {
			if(source_.getName().equals(destination_.getName()))
				throw new IllegalArgumentException();
		} catch(IllegalArgumentException ex) {
			System.out.println("ERROR: task source and destination are equal!");
			ex.printStackTrace();
			System.exit(0);
		}
		id=id_;
		source=source_;
		destination=destination_;
		prio=prio_;
	}
	
	public Assignment(Assignment task) {
		id=task.getIndex();
		source=task.getSource();
		destination=task.getDestination();
		prio=task.getPrio();
	}
	
	/*
	 * getter methods
	 */
	public Tool getSource() {return source;}
	
	public Tool getDestination() {return destination;}
	
	public double getTaskDistance() {
		
		double distX = getSource().getX()-getDestination().getX();
		double distY = getSource().getY()-getDestination().getY();
		
		return Math.sqrt(Math.pow(distX, 2)+Math.pow(distY, 2));
	}
	
	public double getWeight() {return Math.pow(2, prio/10);}
	
	public double getTaskCost() {return getTaskDistance()*getWeight();}
	
	public int getPrio() {return prio;}
	
	public int getIndex() {return id;}
	
	public int getState() {return state;}
	
	public boolean isAssigned() {return assigned;}
	
	/*
	 * setter methods
	 */	
	public void setState(int state) {
		this.state=state;
	}
	
	public void setAssigned(boolean value) {
		assigned=value; // TODO Macht false überhaupt Sinn? Dann kein Übergabeparameter...
	}
}