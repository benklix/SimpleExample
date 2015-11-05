/**
 * @author Benjamin Pietke
 */

import java.util.ArrayList;

import model.*;

public class Optimizer {
	
	public static void main(String[] args) {
		
		/*
		 * create all tools
		 */
		Tool[] toolList = {
				new Tool("t0", 0,  0, 0),
				new Tool("t1", 1,  0,10),
				new Tool("t2", 2, 10, 0),
				new Tool("t3", 3, 20,10),
				new Tool("t4", 4, 20,20),
				new Tool("t5", 5, 10,20)};
		/*
		 * create all vehicles
		 */
		ArrayList<Vehicle> vehicleList = new ArrayList<Vehicle>();
		vehicleList.add(new Vehicle("k0", 0, toolList[1].getPos()));
		vehicleList.add(new Vehicle("k1", 1, toolList[4].getPos()));
		vehicleList.add(new Vehicle("k2", 2, toolList[2].getPos()));
		
		// Kopie der ursprünglichen Fahrzeugliste
		ArrayList<Vehicle> copyOfVehicleList = new ArrayList<Vehicle>(vehicleList);
		
		/*
		 * create all assignments
		 */
		ArrayList<Assignment> taskList = new ArrayList<Assignment>();
		taskList.add(new Assignment(0, toolList[1], toolList[2], 40));
		taskList.add(new Assignment(1, toolList[0], toolList[2], 50));
		taskList.add(new Assignment(2, toolList[2], toolList[0], 40));
		taskList.add(new Assignment(3, toolList[1], toolList[0], 40));
		taskList.add(new Assignment(4, toolList[1], toolList[2], 50));
		taskList.add(new Assignment(5, toolList[3], toolList[5], 40));
		taskList.add(new Assignment(6, toolList[4], toolList[3], 30));
		taskList.add(new Assignment(7, toolList[3], toolList[4], 50));
		taskList.add(new Assignment(8, toolList[4], toolList[5], 40));
		taskList.add(new Assignment(9, toolList[3], toolList[5], 20));
		
		// Kopie der ursprünglichen Auftragsliste
		ArrayList<Assignment> copyOfTaskList = new ArrayList<Assignment>(taskList);
		
		/*
		 * create sorted tasklist by task cost (high to low)
		 */
		TaskListSorter sorter = new TaskListSorter();
		sorter.sort(taskList);
		sorter.printSortedList();

		ArrayList<Assignment> unassignedTaskList = new ArrayList<Assignment>(sorter.getSortedList());
		
		/*******************************
		 * minimal possible cost
		 * definition: at least the most urgent task has to be finished
		 * that means bestVehicle has to pickup and deliver that task
		 * all other tasks hypothetically can be done by the other vehicle(s)
		 ******************************/

		
		/**************************************
		 * random disposition cost
		 * assign tasks to a vehicle by random
		 *************************************/	
		int iterations = 2;
		randomDisposition(iterations, taskList, vehicleList);
		
		
		/******************************
		 * most optimized cost
		 * find a most optimal routing
		 ******************************/
		
		double routeCost0, routeCost1, routeCost2 = 0.0;

		/*
		 * ALLGEMEIN:
		 * - ordne den Fahrzeugen die jeweils schwersten Aufträge zu, bis alle verteilt sind
		 * - verteile Aufträge so, dass deren Gewichte "ähnlich" sind
		 * - vertasuche innerhalb der Routen Knoten, damit Gewicht weiter sinkt
		 * 
		 * FRAGE: Welchen vertausche ich und wohin?
		 */

		/*
		 * Step 1: get an opening solution
		 * assign the most urgent tasks to the respective vehicle^*
		 * the deference between each route should be as small as possible
		 * TODO: check options: savings, weight oriented nearest neighbor^*, weight oriented sweep, best/cheapest insertion 
		 */
				
		for(int i=0; i<unassignedTaskList.size(); i++) {
			
			assignTaskByBestTotalCostToVehicle(unassignedTaskList.get(i), vehicleList);
			
			if(i == 0) {
				System.out.println("Minimal possible Cost: "+mimimalPossibleCost(vehicleList));
			}
		}
		
//		vehicleList.get(0).printTasks();
//		vehicleList.get(1).printTasks();
//		vehicleList.get(2).printTasks();
		
		routeCost0 = calculateRouteCostForVehicle(vehicleList.get(0));
		System.out.println(routeCost0);
		routeCost1 = calculateRouteCostForVehicle(vehicleList.get(1));
		System.out.println(routeCost1);
		routeCost2 = calculateRouteCostForVehicle(vehicleList.get(2));
		System.out.println(routeCost2);
		System.out.println(routeCost0+routeCost1+routeCost2);
		
		/*
		 * Step 2: reassign the tasks betwenn the vehicles
		 * maybe together with Step 1
		 * improve the total route
		 */
		
//		vehicleList.get(0).getPendingTasks().add(vehicleList.get(1).getPendingTasks().get(vehicleList.get(1).getPendingTasks().size()-1));
//		vehicleList.get(1).getPendingTasks().remove(vehicleList.get(1).getPendingTasks().size()-1);
//		
//		vehicleList.get(0).printTasks();
//		vehicleList.get(1).printTasks();
//		
//		routeCost0 = calculateRouteCostForVehicle(vehicleList.get(0));
//		System.out.println(routeCost0);
//		routeCost1 = calculateRouteCostForVehicle(vehicleList.get(1));
//		System.out.println(routeCost1);
//		System.out.println(routeCost0+routeCost1);
		
//		LIEBER GLEICH BEI AUFTRAGSVERTEILUNG BEACHTEN!!!
//		
//		/*
//		 * Schleife: nimm den letzten Aufrag der längeren Liste und füge diesen am Ende der kürzeren hinzu
//		 * ist das Gesamtgewicht kleiner nimm noch einen Auftrag der längeren Liste
//		 * fahre fort "bis die längere, die kürzere wird"
//		 */
//		
//		double totalCost = routeCost0+routeCost1;
//		double difR0R1 = routeCost0-routeCost1;
//		double costLastTask = 0.0;
//		double saving = 1.0;
//		System.out.println("Gesamtkosten: "+totalCost);
//
//				
//		if(difR0R1 > 0) {
//			Assignment lastTask = vehicleList.get(0).getPendingTasks().get(vehicleList.get(0).getPendingTasks().size()-1);
//			costLastTask += getDistance(vehicleList.get(1).getPendingTasks().get(vehicleList.get(1).getPendingTasks().size()-1).getDestination(), lastTask.getSource())*lastTask.getWeight();
//			costLastTask += getDistance(lastTask.getSource(), lastTask.getDestination())*lastTask.getWeight();
//		}
//		else {
//			Assignment lastTask = vehicleList.get(1).getPendingTasks().get(vehicleList.get(1).getPendingTasks().size()-1);
//			costLastTask += getDistance(lastTask.getSource(), lastTask.getDestination())*lastTask.getWeight();
//			costLastTask += getDistance(vehicleList.get(0).getPendingTasks().get(vehicleList.get(0).getPendingTasks().size()-1).getDestination(), lastTask.getSource())*lastTask.getWeight();
//			costLastTask += getDistance(lastTask.getSource(), lastTask.getDestination())*lastTask.getWeight();
//		}
//		
//		costLastTask = 0.0;
//		
//		while((Math.abs(difR0R1) > costLastTask) && (saving > 0)) {
//			if(difR0R1 > 0) {
//				Assignment switchTask = vehicleList.get(0).getPendingTasks().get(vehicleList.get(0).getPendingTasks().size()-1);
//				vehicleList.get(0).getPendingTasks().remove(vehicleList.get(0).getPendingTasks().size()-1);
//				vehicleList.get(1).getPendingTasks().add(switchTask);
//				costLastTask += getDistance(vehicleList.get(1).getPendingTasks().get(vehicleList.get(1).getPendingTasks().size()-1).getDestination(), switchTask.getSource())*switchTask.getWeight();
//				costLastTask += getDistance(switchTask.getSource(), switchTask.getDestination())*switchTask.getWeight();
//			}
//			else {
//				Assignment switchTask = vehicleList.get(1).getPendingTasks().get(vehicleList.get(1).getPendingTasks().size()-1);
//				vehicleList.get(1).getPendingTasks().remove(vehicleList.get(1).getPendingTasks().size()-1);
//				vehicleList.get(0).getPendingTasks().add(switchTask);
//				costLastTask += getDistance(vehicleList.get(0).getPendingTasks().get(vehicleList.get(0).getPendingTasks().size()-1).getDestination(), switchTask.getSource())*switchTask.getWeight();
//				costLastTask += getDistance(switchTask.getSource(), switchTask.getDestination())*switchTask.getWeight();
//			}
//			routeCost0 = calculateRouteCostForVehicle(vehicleList.get(0));
//			routeCost1 = calculateRouteCostForVehicle(vehicleList.get(1));
//			difR0R1 = routeCost0-routeCost1;
//			saving = totalCost-(routeCost0+routeCost1); // neue Route ist länger als alte also nicht tauschen -> Tausch verhindern!
//			totalCost = routeCost0+routeCost1;
//			
//			System.out.println("RouteDifferenz - Entstehende Kosten: "+(Math.abs(difR0R1)-costLastTask));
//			System.out.println("Saving: "+saving);
//			System.out.println("Gesamtkosten: "+totalCost);
//		}
		
		
		/*
		 * Step 3: improve each vehicle route
		 * TODO check options: node insertion move, node exchange, k-opt-heuristik
		 */
						
	}


	

	
	

	

	/**************************************
	 * 
	 * general methods
	 * @param für Javadoc vorgesehen
	 * @param bei Zeit mal rumspielen
	 * 
	 **************************************/
	
//	/*
//	 * clones the vehicle list and all it's content
//	 */
//	public static ArrayList<Vehicle> cloneList(ArrayList<Vehicle> vehList) {
//		ArrayList<Vehicle> clonedList = new ArrayList<Vehicle>(vehList.size());
//	    for (Vehicle veh : vehList) {
//	        clonedList.add(new Vehicle(veh));
//	    }
//	    return clonedList;
//	}
	
	/*
	 * calculates the distance between two nodes
	 */
	private static double getDistance(Tool toolA, Tool toolB) {
		
		double distX = toolA.getX()-toolB.getX();
		double distY = toolA.getY()-toolB.getY();
		
		return Math.sqrt(Math.pow(distX, 2)+Math.pow(distY, 2));
	}


	/*
	 * assign most urgent task to respective vehicle
	 */
	private static void assignTaskByBestTotalCostToVehicle(Assignment task, ArrayList<Vehicle> vehList) {
		
		double bestCost = Double.POSITIVE_INFINITY;
		double routeCost = 0.0;
		int bestVehicleIndex = 0;
		
		// assign task to a vehicle so the difference between the routes is minimized
		for(int i=0; i<vehList.size(); i++) {
		
			vehList.get(i).assignTask(task);
			routeCost = calculateRouteCostForVehicle(vehList.get(i));
			vehList.get(i).getPendingTasks().remove(vehList.get(i).getPendingTasks().size()-1);
			
			if(routeCost < bestCost) {
				bestVehicleIndex = i;
				bestCost = routeCost;
			}
		}
		
		vehList.get(bestVehicleIndex).assignTask(task);
		
		// TODO Position wird nur in der lokalen Variable geändert!!!
		vehList.get(bestVehicleIndex).setPosition(task.getDestination().getPos());
	}
	
	/*
	 * calculate the minimal possible cost
	 * TODO not totally sure if the minimal tour can be higher for sure
	 */
	private static double mimimalPossibleCost(ArrayList<Vehicle> vehList) {

		double minPosCost = Double.POSITIVE_INFINITY;
		
		for(int i=0; i<vehList.size(); i++) {
			if(!vehList.get(i).getPendingTasks().isEmpty())
				minPosCost = calculateRouteCostForVehicle(vehList.get(i));
		}
		
		return minPosCost;
	}
	
//	private static ArrayList<Vehicle> assignTaskByUrgencyToVehicle(ArrayList<Assignment> taskList, ArrayList<Vehicle> vehicleList) {
//		
//		ArrayList<Assignment> unassignedTaskList = new ArrayList<Assignment>(taskList);
//		
//		while(!unassignedTaskList.isEmpty()) {
//
//			double mostUrgentCost = Double.POSITIVE_INFINITY;
//			double taskCost = 0.0;
//			int mostUrgentTaskIndex = -1;
//			int bestVehicleIndex = 0;
//			
//			// welcher Auftrag passt am besten zum ersten Fahrzeug?
//			// welches Fahrzeug wird bei gleicher Entfernung bevorzugt?
//			for(int j=0; j<unassignedTaskList.size(); j++) {
//				// eventuell mit der kombinierten Entfernung arbeiten!!!
//				taskCost = (1+getDistance(vehicleList.get(0), unassignedTaskList.get(j).getSource()))/unassignedTaskList.get(j).getWeight();
//				
//				if(taskCost < mostUrgentCost) {
//					mostUrgentTaskIndex = j;
//					mostUrgentCost = taskCost;
//				}
//			}
//			
//			// passt dieser Auftrag zu einem anderen Fahrzeug noch besser?
//			for(int j=0; j<vehicleList.size(); j++) {
//				taskCost = (1+getDistance(vehicleList.get(j), unassignedTaskList.get(mostUrgentTaskIndex).getSource()))/unassignedTaskList.get(mostUrgentTaskIndex).getWeight();
//				
//				if(taskCost < mostUrgentCost) {
//					bestVehicleIndex = j;
//					mostUrgentCost = taskCost;
//				}
//			}
//			
////			System.out.println("Task "+taskList.get(mostUrgentTaskIndex).getIndex()+" is assigned to "+vehicleList.get(bestVehicleIndex).getName());
//			
//			// assign most urgent task to respective vehicle
//			vehicleList.get(bestVehicleIndex).assignTask(unassignedTaskList.get(mostUrgentTaskIndex));
//			// set new vehicle position
//			vehicleList.get(bestVehicleIndex).setPosition(unassignedTaskList.get(mostUrgentTaskIndex).getDestination().getPos());
//			// tag task assigned and add to assigned tasks
//			unassignedTaskList.get(mostUrgentTaskIndex).setAssigned(true);
//			unassignedTaskList.remove(unassignedTaskList.get(mostUrgentTaskIndex));
//
//		}
//		return vehicleList;
//	}
	
	
	/*
	 * create sequential route for each vehicle
	 * TODO eigentliche Routenbildung sollte woanders bzw. nicht strickt sequentiell erfolgen!!!
	 */
	private static double calculateRouteCostForVehicle(Vehicle vehicle) {
		
		double routeCost = 0.0;
		Tool sourceTool = vehicle;
		ArrayList<Assignment> copyOfPendingTasks = new ArrayList<Assignment>(vehicle.getPendingTasks());
		
//		System.out.print("\n"+vehicle.getName()+":  ");
		
		while(!vehicle.getPendingTasks().isEmpty()) {
			
			vehicle.getRoute().addTask(vehicle.getPendingTasks().get(0).getSource()); //add task source to route
			vehicle.getPendingTasks().get(0).setState(1); // task is processing
			vehicle.incrementLoad(); //raise load of vehicle by one

			routeCost += getDistance(sourceTool, vehicle.getRoute().getTask(vehicle.getRoute().size()-1))*vehicle.getPendingWeight();
			sourceTool = vehicle.getRoute().getTask(vehicle.getRoute().size()-1);
//			System.out.print(vehicle.getLoad()+"->\t");	
			
			vehicle.getRoute().addTask(vehicle.getPendingTasks().get(0).getDestination()); //add task destination to route
			vehicle.decrementLoad(); //lower load of vehicle by one
			
			routeCost += getDistance(sourceTool, vehicle.getRoute().getTask(vehicle.getRoute().size()-1))*vehicle.getPendingWeight();
			vehicle.getPendingTasks().remove(0);
			sourceTool = vehicle.getRoute().getTask(vehicle.getRoute().size()-1);
//			System.out.print(vehicle.getLoad()+"->\t");		
		}		
		
//		/*
//		 * print sequential routes
//		 */
//		System.out.print("\n"+vehicle.getName()+":  ");
//		for(int j=0; j<seqWeightedRoute.size(); j++) {
//			
//			System.out.print(seqWeightedRoute.get(j).getName()+"->\t");
//		}
//		System.out.println();
//		
//		System.out.println(vehicle.getName()+": Sequential weighted route: "+routeCost);
		
		for(int i=0; i<copyOfPendingTasks.size(); i++) {
			vehicle.assignTask(copyOfPendingTasks.get(i));
		}
		
		return routeCost;
	}
	
	/*
	 * create some random routes
	 */
	private static void randomDisposition(int iterations, ArrayList<Assignment> taskList, ArrayList<Vehicle> vehicleList) {
		// TODO hängt bei vielen Iterationen!!! 
		
		double totalCost = 0.0;
		ArrayList<Assignment> copyOfTaskList = new ArrayList<Assignment>(taskList);
		
		for(int k=0; k<iterations; k++) {
			
			/*
			 * initialize
			 */
			taskList = new ArrayList<Assignment>(copyOfTaskList);
			
			/*
			 * assign random task to random vehicle
			 */
			while(!taskList.isEmpty()) {
				
				int randVehicle = (int)(Math.random()*vehicleList.size());
				int randTask = (int)(Math.random()*taskList.size());
				
				vehicleList.get(randVehicle).assignTask(taskList.get(randTask));
				taskList.get(randTask).setAssigned(true);
				taskList.remove(randTask);
			}
			
	//		// print the assigned tasks
	//		for(int i=0; i<vehicleList.size(); i++) {
	//			
	//			vehicle.printTasks();
	//		}
			
			/*
			 * create a random route for each vehicle
			 * watch all side constraints
			 * regard loaded goods and the S->D order
			 */
			for(int i=0; i<vehicleList.size(); i++) {
				
				double routeCost = 0.0;
				Tool sourceTool = vehicleList.get(i);
				ArrayList<Tool> randRoute = new ArrayList<Tool>();
				
//				System.out.print("\n"+vehicleList.get(i).getName()+":  ");
				
				while(!vehicleList.get(i).getPendingTasks().isEmpty()) {
					
					int randTask = (int)(Math.random()*vehicleList.get(i).getPendingSources().size());
					
					if((vehicleList.get(i).getPendingTasks().get(randTask).getState() == 0)
							&& (vehicleList.get(i).getLoad() < 4)) {
						
						randRoute.add(vehicleList.get(i).getPendingTasks().get(randTask).getSource()); //add task source to route
						vehicleList.get(i).getPendingSources().remove(randTask);
						vehicleList.get(i).getPendingTasks().get(randTask).setState(1); // task is processing
						vehicleList.get(i).incrementLoad(); //raise load of vehicle by one
						
						routeCost += getDistance(sourceTool, randRoute.get(randRoute.size()-1))*vehicleList.get(i).getPendingWeight();
						sourceTool = randRoute.get(randRoute.size()-1);
//						System.out.print(vehicleList.get(i).getLoad()+"->\t");		
					}
					else if(vehicleList.get(i).getPendingTasks().get(randTask).getState() == 1) {
						
						randRoute.add(vehicleList.get(i).getPendingTasks().get(randTask).getDestination()); //add task destination to route
						vehicleList.get(i).getPendingDestinations().remove(randTask);
						vehicleList.get(i).getPendingTasks().get(randTask).setState(0); // task is finished
						vehicleList.get(i).getPendingTasks().get(randTask).setAssigned(false); // task is finished
						vehicleList.get(i).getPendingTasks().remove(randTask);
						vehicleList.get(i).decrementLoad(); //lower load of vehicle by one
						
						routeCost += getDistance(sourceTool, randRoute.get(randRoute.size()-1))*vehicleList.get(i).getPendingWeight();
						sourceTool = randRoute.get(randRoute.size()-1);
//						System.out.print(vehicleList.get(i).getLoad()+"->\t");		
					}		
				}
				
				totalCost += routeCost;
				
				
	//			/*
	//			 * print random routes
	//			 */
	//			System.out.print("---"+vehicleList.get(i).getName()+"-route: "+routeCost);
	//			System.out.print("\n"+vehicleList.get(i).getName()+":  ");
	//			for(int j=0; j<randRoute.size(); j++) {
	//				
	//				System.out.print(randRoute.get(j).getName()+"->\t");
	//			}
	//			System.out.println();
			}			
		}
		
		System.out.println("\nRandom Route: "+(totalCost/iterations)+" with "+iterations+" iterations");
		
	}

}


//	---> add under tasklist when needed
///*
//* initialize the distance matrix
//*/
//WeightedUndirectedGraph distanceMatrix = new WeightedUndirectedGraph(toolList.length);
//
//for(int i=0; i<toolList.length; i++) {
//	for(int j=i+1; j<toolList.length; j++) {
//		
//		distanceMatrix.setWeight(toolList[i].getIndex(), toolList[j].getIndex(), getDistance(toolList[i], toolList[j]));
//	}
//}		