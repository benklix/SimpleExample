package model;

import java.util.ArrayList;

public class Vehicle extends Tool {
	
	final private int capa = 4; // available capacity
	private int load; // used capacity
	private PendingTasks pendingTasks = new PendingTasks();
	private Route vehicleRoute = new Route();
		
	public Vehicle(String name_, int id_, Position pos_) {
		super(name_, id_, pos_.getX(), pos_.getY());
		load=0;
	}
	
//	public Vehicle(Vehicle veh) {
//		pendingTasks = veh.cloneList(veh.getPendingTasks());
//	}
	
	public Vehicle() {
	}
	
	
	public ArrayList<Assignment> cloneList(ArrayList<Assignment> taskList) {
		ArrayList<Assignment> clonedList = new ArrayList<Assignment>(taskList.size());
	    for (Assignment task : taskList) {
	        clonedList.add(new Assignment(task.getIndex(), task.getSource(), task.getDestination(), task.getPrio()));
	    }
	    
	    return clonedList;
	}
	

	public void assignTask(Assignment newTask) {
		
		pendingTasks.addTask(newTask);
	}

	public void printTasks() {
		System.out.print(this.getName()+": ");
		for(int i=0; i<pendingTasks.size(); i++) {
			System.out.print(pendingTasks.getTaskId(i)+"--> ");
		}
		System.out.println("finished");
	}

	/*
	 * getter methods
	 */
	public PendingTasks getPendingTasks() {return pendingTasks;}
		
	public int getLoad() {return load;}
	
	public Route getRoute() {return vehicleRoute;}
	
	public double getPendingWeight() {
		
		double pendingWeight = 0.0;
		
		for(int i=0; i<pendingTasks.size(); i++){
			pendingWeight += pendingTasks.getTask(i).getWeight();
		}
		
		return pendingWeight;
	}
	
	public double getPendingCost() {
		
		double pendingCost = 0.0;
		
		for(int i=0; i<pendingTasks.size(); i++){
			pendingCost += pendingTasks.getTask(i).getTaskCost();
		}
		
		return pendingCost;
	}
	

	/*
	 * raise or lower the transported load
	 */
	public void incrementLoad() {
		load++;		
	}
	
	public void decrementLoad() {
		load--;		
	}

	
	public void assignRoute(Route copyOfVehicleRoute) {
		
		this.vehicleRoute = copyOfVehicleRoute;
	}	
}