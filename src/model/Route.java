package model;

import java.util.ArrayList;

public class Route {
	
	private ArrayList<Tool> route = new ArrayList<Tool>();
	
	
	public void addTask(Tool newTask) {
		route.add(newTask);
	}


	public int size() {
		
		return route.size();
	}


	public Tool getTask(int index) {
		
		return route.get(index);
	}
}
