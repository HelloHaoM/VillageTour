/**
 * Cable car will carry tourist group between valley and terminus
 * Simply conduct valley, terminus and cable car is a single section
 * 
 * @author haomai
 *
 */

public class CableCar {
	
	// indicate that the cable car is occupied or not
	private boolean isOccupied;
	
	// indicate that where the cable car in
	// two value: TERMINUS or VALLEY
	private String carLocation;
	
	// group in the cable car
	private Group group;
	
	// create a cable car
	CableCar(){
		isOccupied = false;
		carLocation = "VALLY";
		group = null;
	}
	
	// get the cable car location
	public synchronized Group getGroup(){
		return this.group;
	}

	// get the cable car location
	public synchronized String getCarLocation(){
		return this.carLocation;
	}
	
	// send the car to the terminus if it's unoccupied
	public synchronized void ascends(){
		while(isOccupied || carLocation.equals("TERMINUS")){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// cable car arrive terminus and others can operate cable car
		carLocation = "TERMINUS";
		this.group = null;
		System.out.println("Cable car ascend");
		notifyAll();
		
	}
	
	//send the car to the valley if it's unoccupied
	public synchronized void descends(){
		while(isOccupied || carLocation.equals("VALLEY")){
			try{
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// cable car arrive valley and others can operate cable car
		carLocation = "VALLEY";
		this.group = null;
		System.out.println("Cable car descend");
		notifyAll();
	}
	
	// take the group from the last train
	public synchronized void fromLastTrain(Group group){
		
		// if the cable car is occupied or in the valley
		// wait for it until it can take group form last train
		while(isOccupied || carLocation.equals("VALLEY")){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// take the group and cable car arrive at valley
		if(group != null){
			this.group = group;
			isOccupied = true;
			carLocation = "VALLEY";
			System.out.println(this.group.toString() 
					+ " enter cable car to go down");
		}
		
	}
	
	// send the group to the first train
	public synchronized void toFirstTrain(){
		
		// send the group to first train 
		if(isOccupied && carLocation.equals("TERMINUS")){
			System.out.println(this.group.toString() 
					+ " leave cable car");
			isOccupied = false;
			this.group = null;
			//carLocation = "TERMINUS";
			notifyAll();
		}
		
	}
	
	// send the group to the cable car
	public synchronized void arrive(Group group){
		
		//if cable car is occupied or it is located in valley or not a new group
		// wait for it until it is located in terminus and not occupied
		while(isOccupied || carLocation.equals("TERMINUS")){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// send the group to the cable car
		if(group != null && !Main.departedGroup.contains(group)){
			this.group = group;
			isOccupied = true;
			carLocation = "TERMINUS";
			System.out.println(this.group.toString() 
					+ " enter cable car to go up");
		}
		
	}
	
	// let the group leave
	public synchronized void depart(){
		
		// car return to valley and not occupied
		if(carLocation.equals("VALLEY") && isOccupied){
			System.out.println(this.group.toString() 
					+ " depart");
			Main.departedGroup.add(this.group);
			isOccupied = false;
			this.group = null;
			
			// notify other waiting cable car process
			notifyAll();
		}
		
	}

}
