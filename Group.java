/**
 * Tourist group traveling to each villages class. 
 * Each will have a unique id
 * 
 * @author haomai
 *
 */

public class Group {
	
	// unique id for each tourist group
	protected int id;
	
	//the next group id to e allocated
	protected static int nextId = 1;
	
	//generate a new ground with given id
	protected Group(int id){
		this.id = id;
	}
	
	//get a new group with a unique id
	public static Group getNewGroup(){
		return new Group(nextId++);
	}
	
	//get the group's id
	public int getId(){
		return id;
	}
	
	//produce an identifying string fot the group
	public String toString(){
		return "group [" + id +"]";
	}

}
