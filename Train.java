/**
 * The trains between each village
 * Carrying tourist group to next village
 * Returning back without tourist
 *
 * @author haomai
 *
 */
public class Train extends Thread{
	
	// departure village
	private Village depVillage;
	
	// arrived village
	private Village arrVillage;
	
	// crate a train between each village
	Train(Village depVillage, Village arrVillage){
		this.depVillage = depVillage;
		this.arrVillage = arrVillage;
	}
	
	@Override
	public void run(){
		while(!isInterrupted()){
			try{
				
				// departure form departure village, set it to not occupied
				Group group = depVillage.getGroup();
				depVillage.depart();
				
				// wait for a train for a single journey time
				sleep(Params.JOURNEY_TIME);
				
				// arrive at arrived village, set it to occupied
				arrVillage.arrive(group);
				
				// wait for a train to go back to the departure village
				sleep(Params.JOURNEY_TIME);
				
			}catch(InterruptedException e){
				this.interrupt();
			}
		}
	}
	

}
