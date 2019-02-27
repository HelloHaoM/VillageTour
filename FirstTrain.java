/**
 * The train that run from terminus to village 0
 * 
 * @author haomai
 *
 */
public class FirstTrain extends Thread{
	
	// arrived village
	private Village arrVillage;
	
	// the cable car connect to the train
	private CableCar cableCar;
	
	// create a train to village from cable car
	FirstTrain(CableCar cableCar, Village arrVillage){
		this.cableCar = cableCar;
		this.arrVillage = arrVillage;
	}
	
	// run the first train to take group to first village from cable car
	@Override
	public void run(){
		while(!isInterrupted()){
			try{
				
				// take tourist group from cable car
				Group group = cableCar.getGroup();
				cableCar.toFirstTrain();
				
				// let the train take group to village 0
				sleep(Params.JOURNEY_TIME);
				
				// tourist group arrive the village 0
				arrVillage.arrive(group);
				
				//let the train back to the terminus
				sleep(Params.JOURNEY_TIME);
				
			}catch(InterruptedException e){
				this.interrupt();
			}
		}
		
	}
	

}
