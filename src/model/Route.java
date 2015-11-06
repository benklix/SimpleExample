package model;

import java.util.ArrayList;

public class Route {
	
	
	private ArrayList<Tool> route = new ArrayList<Tool>();
	
	
	
	public Route() {
		// TODO Auto-generated constructor stub
	}
	
	public Route(Route route_) {
		
		for(int i=0; i<route_.size(); i++) {
			this.route.add(route_.getStep(i));
		}
	}


	public void addStep(Tool newTask) {
		
		route.add(newTask);
	}
	
	public void removeStep(int index) {
		
		this.route.remove(index);
	}


	public int size() {
		
		return route.size();
	}


	public Tool getStep(int index) {
		
		return route.get(index);
	}

	public boolean isEmpty() {
		
		return route.isEmpty();
	}

	public void addStepAt(int insertionIndex, Tool tool) {
		
		route.add(insertionIndex, tool);
	}
}
