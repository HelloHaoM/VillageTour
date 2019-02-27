import java.util.HashSet;
import java.util.Set;

/**
 * The driver for the simulation
 * 
 * Consists of
 *  - generating all components of the system 
 *  - starting all threads
 *  - supervising processes to ensure all are not interrupt
 *  
 * @author haomai
 * 
 */ 

public class Main {

	/**
	 * The driver of the mountain railway system:
	 */

	// a set that storage all departed group
	// used to avoid group id in waiting thread is not destroyed
	protected static Set<Group> departedGroup = new HashSet<>();
	
	public static void main(String [] args) {
		int n = Params.VILLAGES;

		// generate the cable car
		CableCar cableCar = new CableCar();

		// create an array to hold the villages
		Village[] village = new Village[n];

		// generate the individual villages
		for (int i = 0; i < n; i++) {
			village[i] = new Village(i);
		}

		// generate the producer, the consumer and the operator
		Producer producer = new Producer(cableCar);
		Consumer consumer = new Consumer(cableCar);
		Operator operator = new Operator(cableCar);

		// create an array trains to hold the trains
		Train[] train = new Train[n-1];

		// generate the individual trains
		for (int i = 0; i < n-1; i++) {
			train[i] = new Train(village[i], village[i+1]);
			train[i].start();
		}

		// generate trains that pick up and deliver to the cable car terminus
		FirstTrain firstTrain = new FirstTrain(cableCar, village[0]);
		LastTrain lastTrain = new LastTrain(village[n-1], cableCar);

		//start up all the components
		firstTrain.start();
		lastTrain.start();
		producer.start();
		consumer.start();
		operator.start();

		//regularly check on the status of threads
		boolean trains_alive = true;
		for (int i = 0; i < n-1; i++) {
			trains_alive = trains_alive && train[i].isAlive();
		}
		while (producer.isAlive() && consumer.isAlive()
				&& operator.isAlive() && trains_alive) {
			try {
				Thread.sleep(Params.MAIN_INTERVAL);
			}
			catch (InterruptedException e) {
				System.out.println("Main was interrupted");
				break;
			}
			for (int i = 0; i < n-1; i++) {
				trains_alive = trains_alive && train[i].isAlive();
			}
		}

		//if some thread died, interrupt all other threads and halt
		producer.interrupt();    
		consumer.interrupt();
		operator.interrupt();

		for (int i = 0; i < n-1; i++) {
			train[i].interrupt();
		}
		firstTrain.interrupt();
		lastTrain.interrupt();

		System.out.println("Main terminates, all threads terminated");
		System.exit(0);
	}
}
