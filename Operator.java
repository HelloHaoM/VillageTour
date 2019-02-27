/**
 * Inspects the cable car
 * Send it to terminus form valley or to valley form terminus
 * 
 * @author haomai
 *
 */
public class Operator extends Thread{
	
	// a cable car which will be inspected
	private CableCar cableCar;
	
	Operator(CableCar cableCar){
		this.cableCar = cableCar;
	}
	
	@Override
	public void run(){
		while(!isInterrupted()){
			try{
				// send the cable car to the valley or terminus if it's unoccupied
				if(cableCar.getCarLocation().equals("TERMINUS")){
					cableCar.descends();
					
					// let the cable car to arrive valley
					sleep(Params.OPERATE_TIME);
					
				}else if(cableCar.getCarLocation().equals("VALLEY")){
					cableCar.ascends();
					
					// let the cable car to arrive terminus
					sleep(Params.OPERATE_TIME);
				}
				
				// sleep some time to wait next check
				sleep(Params.operateLapse());
				
			}catch(InterruptedException e){
				this.isInterrupted();
			}
		}
	}

}
