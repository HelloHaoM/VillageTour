/**
 * The train that run from last village to terminus
 * 
 * @author haomai
 *
 */
public class LastTrain extends Thread{
	
	// departure village
	private Village depVillage;
	
	// the cable car connect to the train
	private CableCar cableCar;
	
	// create a train to cable car from village
	LastTrain(Village depVillage, CableCar cableCar){
		this.depVillage = depVillage;
		this.cableCar = cableCar;
	}
	
	// run the last train to take group to cable car from last village
	@Override
	public void run(){
		while(!isInterrupted()){
			try{
				// take the group from last village
				Group group = depVillage.getGroup();
				depVillage.depart();
				
				// let the train take group to cable car
				sleep(Params.JOURNEY_TIME);
				
				// tourist group arrive cable car
				cableCar.fromLastTrain(group);
				
				//let the train back to the terminus
				sleep(Params.JOURNEY_TIME);
				
				
			}catch(InterruptedException e){
				this.interrupt();
			}
		}
	}

}
