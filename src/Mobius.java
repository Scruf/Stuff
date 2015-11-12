import java.util.ArrayList;
//You will write, from scratch, a Mobius class. It has the main function.
//In addition, it implements the Puzzle interface routines.
public class Mobius implements Puzzle<Integer> {

	public int range = 0;
	public int start = 0;
	public int goal = 0;

	//constructor for the Mobius class 
	//allows for the creation of new Mobius objects
	//or new "Mobius Puzzles"
	public Mobius(int r, int s, int g){
		range = r;
		start = s;
		goal = g;
	}
	
	public static void main (String args[]){
		
		int r = 0; //hold initial input for the range 
		int s = 0; //hold initial input for the start
		int g = 0; //hold initial input for the goal
		
		//need to have the range, start, and goal to run 
		 if (args.length < 3) {
	            System.out.println("Usage: java Mobius range start goal");
	            return;
	        }
		 //arguments must be integers
		 //Guaranteed to be positive numbers if there are 3 arguments
		 //according to the lab info 
	        try {
	            r = Integer.parseInt(args[0]);
	            s = Integer.parseInt(args[1]);
	            g = Integer.parseInt(args[2]);
	        } catch (NumberFormatException e) {
	            System.out.println("Usage: java Mobius range start goal");
	            return;
	        }
	        //creating a new Mobius object or "Mobius Puzzle" and passing it
	        //the range, start, and goal
	        Solver solver = new Solver();
	        Mobius mobiusPuzzle = new Mobius(r, s, g);
	        ArrayList<Integer> results = solver.solver(mobiusPuzzle);
	        
	        int counter = 0;
	        while(results != null && counter < results.size()){
	        	
	        	System.out.println("Step " + counter + ": " + results.get(counter));
	        	counter++;
	        }
	}

	@Override
	public Integer getStart() {
		return start;
	}

	@Override
	public ArrayList<Integer> getNeighbors(Integer config){
		//the config cannot be a negative number or 0 
		//because the list goes from 1 to the range
		if (config < 1){
			return null;
		}
		//the config also cannot be greater than the range,
		//else it would not be in the list
		if (config > range){
			return null;
		}
		
		ArrayList<Integer> neighborsOfConfig = new ArrayList<Integer>();
		
		//the list starts at 1 and ends at the range
		//so if config is the range, one neighbor will be 1
		if(config == range){
			neighborsOfConfig.add(1);
		}
		else{
			neighborsOfConfig.add(config + 1);
		}
		//if config = 1, then one neighbor will be the end of the list
		//or the range.
		if(config == 1){
			neighborsOfConfig.add(range);
		}
		else{
			neighborsOfConfig.add(config - 1);
		}
		return neighborsOfConfig;
	}

	@Override
	public Integer getGoal() {
		return goal;
	}

}