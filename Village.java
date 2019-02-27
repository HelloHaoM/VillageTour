/**
 * The place that tourist group will go
 * Each Village can only be occupied by one tourist group
 * Village will be act as monitor
 *
 * @author haomai
 *
 */
public class Village {
	
	// unique id for each village
	private int id;
	
	// indicating whether a village is occupied or not
	private boolean isOccupied;
	
	//the tourist group in the village
	private Group group;
	
	// create a new village with id
	Village(int id){
		this.id = id;
		isOccupied = false;
		group = null;
	}
	
	// get tourist group in the village
	public synchronized Group getGroup() {
		return group;
	}
	
	// accept the group from the train
	public synchronized void arrive(Group group){
		while(isOccupied){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// the tourist group arrive the village which will be occupied
		if(group != null && !Main.departedGroup.contains(group)){
			this.group = group;
			isOccupied = true;
			System.out.println(this.group.toString() 
					+ " enter village " + this.id);
		}
	}
	
	// group leave the village
	public synchronized void depart(){
		
		// the village will be not occupied
		if(isOccupied){
			System.out.println(this.group.toString() 
					+ " leave village " + this.id);
			isOccupied = false;
			this.group = null;
			
			// notify other waiting village process 
			notifyAll();
		}

	}
	
	
}
