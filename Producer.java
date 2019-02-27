/**
 * Produces new tour groups at the mountain park
 *
 * @author haomai
 *
 */

public class Producer extends Thread{
	
	//the cable car to which the groups will be sent
	private CableCar cableCar;
	
	//create a new producer
	Producer(CableCar cableCar){
		this.cableCar = cableCar;
	}
	
	//group will be sent to cable car at random intervals
	@Override
	public void run(){
		while(!isInterrupted()){
			try{
				//send a new group to the cable car and go to the terminus
				Group group = Group.getNewGroup();
				cableCar.arrive(group);
				
				//sleep for the cable car to operate
				sleep(Params.JOURNEY_TIME + Params.OPERATE_TIME);
				
				//let some time pass before the next group arrives
				sleep(Params.arrivalLapse());
			}catch(InterruptedException e){
				this.interrupt();
			}
		}
	}

}
