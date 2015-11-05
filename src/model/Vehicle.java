package model;

import java.util.ArrayList;

public class Vehicle extends Tool {
	
	final private int capa = 4; // available capacity
	private int load; // used capacity
	private ArrayList<Assignment> pendingTasks = new ArrayList<Assignment>();
	private ArrayList<Tool> pendingSources = new ArrayList<Tool>();
	private ArrayList<Tool> pendingDestinations = new ArrayList<Tool>();
	private Route vehicleRoute = new Route();
		
	public Vehicle(String name_, int id_, Position pos_) {
		super(name_, id_, pos_.getX(), pos_.getY());
		load=0;
	}
	
	public Vehicle(Vehicle veh) {
		pendingTasks = veh.cloneList(veh.getPendingTasks());
	}
	
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
		pendingTasks.add(newTask);
		pendingSources.add(newTask.getSource());
		pendingDestinations.add(newTask.getDestination());
	}

	public void printTasks() {
		System.out.print(this.getName()+": ");
		for(int i=0; i<pendingTasks.size(); i++) {
			System.out.print(pendingTasks.get(i).getIndex()+"--> ");
		}
		System.out.println("finished");
	}

	/*
	 * getter methods
	 */
	public ArrayList<Assignment> getPendingTasks() {return pendingTasks;}
	
	public ArrayList<Tool> getPendingSources() {return pendingSources;	}
	
	public ArrayList<Tool> getPendingDestinations() {return pendingDestinations;}
	
	public int getLoad() {return load;}
	
	public Route getRoute() {return vehicleRoute;}
	
	public double getPendingWeight() {
		double pendingWeight = 0.0;
		
		for(int i=0; i<pendingTasks.size(); i++){
			pendingWeight += pendingTasks.get(i).getWeight();
		}
		return pendingWeight;
	}
	
	public double getPendingCost() {
		double pendingCost = 0.0;
		
		for(int i=0; i<pendingTasks.size(); i++){
			pendingCost += pendingTasks.get(i).getTaskCost();
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
}