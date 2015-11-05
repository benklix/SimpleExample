
import java.util.ArrayList;
import java.util.Collection;

import model.Assignment;


public class TaskListSorter {
	
	private ArrayList<Assignment> taskList;// = new ArrayList<Type>();
	private int n;
	
	public void sort(ArrayList<Assignment> taskL) {
		
		this.taskList= new ArrayList<Assignment>(taskL);
		n=taskL.size();
		quicksort(0, n-1);
	}

	private void quicksort(int lo, int hi) {
		
		int i=lo, j=hi;
		
		// element to compare
		double c = taskList.get((lo+hi)/2).getTaskCost();
		
		while(i <= j) {
			while(taskList.get(i).getTaskCost() > c) i++;
			while(taskList.get(j).getTaskCost() < c) j--;
			if(i <= j) {
				exchange(i, j);
				i++;
				j--;
			}
		}
		
		// recursion
		if(lo < j) quicksort(lo, j);
		if(i < hi) quicksort(i, hi);
	}

	private void exchange(int i, int j) {
		
		Assignment a=taskList.get(i);
		taskList.set(i, taskList.get(j));
		taskList.set(j, a);
	}

	public void printSortedList() {
		
		for(int i=0; i<taskList.size(); i++) {
			System.out.println(taskList.get(i).getIndex()+": "+taskList.get(i).getTaskCost());
		}
		
	}

	public ArrayList<Assignment> getSortedList() {

		return taskList;
	}
	
}