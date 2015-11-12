import java.util.ArrayList;
import java.util.HashSet;
//You will write, from scratch, a Solver class. 
//This class takes your Mobius object, as a Puzzle interface, 
//and uses it to solve the puzzle using the algorithm described below.
public class Solver {
	/**
	 * This method solves a given puzzle
	 * And finally Static method always needs explicit <T> declaration; 
	 * It wont derive from class level Class<T>. This is because of Class level T is
	 * bound with instance.
	 * A generic type is a generic class or interface that is parameterized over types.
	 * @param puzzle
	 * @return
	 */
    public <T> ArrayList<T> solver(Puzzle<T> puzzle) {
    	
    	//create an empty HashSet to keep track of what places I have visited
    	//Using generic T because I don't always know what type will be passed in and used 
    	HashSet<T> beenVisited = new HashSet<T>();
    	//create an empty queue as an ArrayList<T>
        ArrayList<ArrayList<T>> queue = new ArrayList<ArrayList<T>>();
        //create an ArrayList<T> of one element from the starting config and enqueue it
        ArrayList<T> current = new ArrayList<T>();
        //set found to whether the starting config is the goal config, or not
        boolean found = false;
        if(puzzle.getStart() == puzzle.getGoal()){
        	found = true;
        }
        
        current.add(puzzle.getStart());  
        queue.add(current);
        
        //while the queue is not empty and not found:
        while (queue.isEmpty() == false && found == false){
        	//dequeue the front element from the queue and set it to current
            current = queue.remove(0);
            
            T target = current.get(current.size() - 1);
            //avoid looping by checking the hashset which stores all the previously 
            //visited nodes
            if(beenVisited.contains(target)){
            	continue;
            }
            else{
            	beenVisited.add(target);
            }
            //  for each neighbor of the last element in current:
            for (T neighbor : puzzle.getNeighbors(target)){
            	// create an ArrayList<Integer> for the next config and make it a copy of current
                ArrayList<T> nextConfig = new ArrayList<T>(current);
                //append the neighbor to the next config
                nextConfig.add(neighbor);
                //if the next config is the goal:
                if (neighbor.equals(puzzle.getGoal())){
                	//set current to the next config
                	current = nextConfig;
                	//set found to true
                	found = true;
                	//break out of the for loop
                	break;
                } 
                //enqueue the next config
                else{
                    queue.add(nextConfig);
                }
            }
        }
        //if found: current is the solution
        if (found == true){
        	return current;
        }
        //else: there is no solution
        else{
        	return null;
        }
    }
}