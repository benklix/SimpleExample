package model;

import java.util.ArrayList;

public class PendingTasks {
	

	private ArrayList<Assignment> pendingTasks = new ArrayList<Assignment>(); 
	private ArrayList<Tool> pendingSources = new ArrayList<Tool>();
	private ArrayList<Tool> pendingDestinations = new ArrayList<Tool>();
	
	
	public PendingTasks() {
		
	}
	
	public PendingTasks(PendingTasks pTasks) {
		
		for(int i=0; i<pTasks.size(); i++) {
			this.pendingTasks.add(pTasks.getTask(i));
		}
	}


	public void addTask(Assignment newTask) {

		pendingTasks.add(newTask);
		pendingSources.add(newTask.getSource());
		pendingDestinations.add(newTask.getDestination());
	}


	public int size() {
		
		return pendingTasks.size();
	}
	
	public ArrayList<Tool> getPendingSources() {return pendingSources;	}
	
	public ArrayList<Tool> getPendingDestinations() {return pendingDestinations;}

	/*
	 * returns the unique task ID, NOT the position (index) in the ArrayList
	 */
	public int getTaskId(int index) {
		
		return pendingTasks.get(index).getIndex();
	}


	public Assignment getTask(int index) {
		
		return pendingTasks.get(index);
	}


	public void removeTask(int index) {
		
		pendingTasks.remove(index);
	}


	public boolean isEmpty() {
		
		return pendingTasks.isEmpty();
	}

}
