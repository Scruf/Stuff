import java.util.*;
/**
 * This class is essentially a puzzle hence why it extends 
 * the puzzle class, within the main method,
 * the user will enter in the amount of sand they are dealing with,
 * followed by the number of buckets specified by their capacities.
 * The class will then use it's methods along with methods from the 
 * Solver class to find the shortest number of steps to get from the 
 * initial state (all buckets empty) to the first bucket containing 
 * the desired amount.
 * @author User1
 *
 */
public class Sand implements Puzzle<Sand.BucketState> {
	
	// array to hold the number of buckets specified by their capacities
	private final int[] capacityOfBuckets;
	// a BucketState object to identify the starting state
	private final BucketState start;
	// a BucketState object to identify the goal state 
	private final BucketState goal;
	
	/**
	 * Constructor method that creates a new sand object
	 * @param amountOfSand - the amount of sand that is intended to
	 * go in the first bucket
	 * @param capacityOfBuckets - number of buckets specified by their individual 
	 * max capacities 
	 */
	//Varargs.. notation so that you can pass individual int[] objects to this method,
	//without having to worrying about the number of arguments.
	public Sand(int amountOfSand, int... capacityOfBuckets){
		this.capacityOfBuckets = capacityOfBuckets;
		this.start = new BucketState(new int [capacityOfBuckets.length]);
		this.goal = new BucketState(true, amountOfSand);
	}
	/**
	 * Method for obtaining the starting state for this sand puzzle
	 * Returns the starting state of the sand puzzle
	 * Overrides method "getStart" declared in the superclass  
	 */
	@Override
	public BucketState getStart(){
		return start;
	}
	/**
	 * Method for obtaining the goal state for this sand puzzle
	 * Returns the goal state for this sand puzzle
	 * Overrides the method "getGoal" declared in the superclass
	 */
	@Override
	public BucketState getGoal(){
		return goal;
	}
	/**
	 * Takes in a State, and finds all of the direct neighbors to that
	 * given state
	 * BucketState config - state being passed in
	 * Returns - an arraylist of BucketState objects which are the neighbors
	 * to the state being passed into the method
	 * Overrides the method "getNeighbors" declared in the superclass 
	 */
	@Override
	public ArrayList<BucketState> getNeighbors(BucketState config){
		//create a new ArrayList of BucketState object that will hold
		//the neighbors of the state being passed in
		ArrayList<BucketState> neighbors = new ArrayList<BucketState>();
		//create a copy of the buckets array to avoid unwanted data manipulation 
		int[] bucketsCopy;
		//for loop to iterate through the array of buckets that exists within 
		//the state that is being passed in 
		for(int i = 0; i < config.buckets.length; i++){
			//If the current bucket which in this case is "i", is not equal to 
			//the capacityOfBuckets which is the buckets specified by their max capacity
			//So essentially if the current bucket isn't full to it's maximum capacity 
			if (config.buckets[i] != capacityOfBuckets[i]){
				//make a copy of all the buckets in their current state in which was 
				//passed into the method 
				bucketsCopy = config.duplicateBuckets();
				//we are now going to fill the current bucket in the local copy of all 
				//buckets to it's maximum capacity
				bucketsCopy[i] = capacityOfBuckets[i];
				//add to the arraylist called "neighbors" a new BucketState object that
				//was created from the current configuration of the bucketsCopy array 
				neighbors.add(new BucketState(bucketsCopy));
			}
			//now we are going to check to see if the current bucket from the array 
			//of buckets associated with the state that was passed in, is not empty
			if (config.buckets[i]!= 0){
				//make a copy of all the buckets in their current state in which was 
				//passed into the method 
				bucketsCopy = config.duplicateBuckets();
				//empty the partially filled bucket in the bucketsCopy array 
				bucketsCopy[i] = 0;
				//add to the arraylist called "neighbors" a new BucketState object that
				//was created from the current configuration of the bucketsCopy array 
				neighbors.add(new BucketState(bucketsCopy));
			
				//for loop to iterate through the local copy of the array of buckets,
				//and the array of buckets associated with the state passed in 
				for(int j = 0; j < config.buckets.length; j++){
					//if the bucket from the first for loop is not equal to the current bucket 
					//inside this for loop
					if(i != j){
						//if the bucket inside this for loop is not full
						if(config.buckets[j] != capacityOfBuckets[j]){
							//after we make it through this if statement, we know two things,
							//one that the bucket from the first for loop specified by "i",is not 
							//empty. We also know that the bucket from this for loop specified by "j" is 
							//not full. We can then proceed to pour the bucket at "j" into the bucket at "i"
							
							//create a new copy of the buckets in the state that was passed in
							bucketsCopy = config.duplicateBuckets();
							
							//we want to pour this bucket into the other bucket, but we cannot pour more sand than 
							//we have, and we also cannot over fill a bucket 
						
							//create variable to hold the maximum amount we can pour
							//into a given bucket
							int maxAmountAbleToPour;
							//create a variable to hold the difference in volume between 
							//the bucket being examined, and how much it can hold
							int temp = (capacityOfBuckets[j] - config.buckets[j]);
							//essentially whichever integer is the smaller value, becomes the maxAmountAbleToPour
							if((temp - Integer.MIN_VALUE) < (config.buckets[i] - Integer.MIN_VALUE)){
								//set maxAmountToPour equal to capacityOfBuckets[j] - config.buckets[j]
								maxAmountAbleToPour = temp;
							}
							//otherwise 
							else{
								//config.buckets[i] must be the smaller value, so set that equal 
								//to maxAmountAbleToPour
								maxAmountAbleToPour = config.buckets[i];
							}
							//remove the sand that was poured from the first bucket 
							bucketsCopy[i] -= maxAmountAbleToPour;
							//add that sand to the other bucket
							bucketsCopy[j] += maxAmountAbleToPour;
							//now we can now add the new BucketState object which 
							//encompasses the bucketsCopy array to the neighbors ArrayList 
							neighbors.add(new BucketState(bucketsCopy));
						}
					}
				}
			}
		}
		//return the finalized ArrayList of neighbor states
		return neighbors;
	}
	/**
	 * this class will help represent the state of multiple buckets
	 * @author User1
	 *
	 */
	
public class BucketState{
		
		//boolean value for if we have reached our goal or not
		private final boolean goal;
		//array to represent the buckets and their current sand amounts
		private final int[] buckets;
		
		/**
		 * Constructor for creating a new state of buckets 
		 * Varargs.. notation so that you can pass individual int[] objects to this method,
		 * without having to worrying about the number of arguments.
		 * @param buckets - the given amounts of sand in the given number of buckets
		 */
		public BucketState(int... buckets){
			this(false, buckets);
		}
		/**
		 * Constructor for creating a new state of buckets
		 * @param goal - specifies if the bucket state is a desired goal, since
		 * we only want to fill the first bucket with the given amount of sand,
		 * that is the bucket that will get compared 
		 * @param buckets - the given amounts of sand in the given number of buckets
		 */
		public BucketState(boolean goal, int... buckets) {
			this.goal = goal;
			this.buckets = buckets;
		}
		/**
		 * Makes a copy of the current amount of sand in the given buckets
		 * @return - a copy of the buckets and their current amounts of sand
		 * when this method is called 
		 */
		public int[] duplicateBuckets(){ 
			//creates a new array the size of the array buckets which
			//is what is intended to be copied 
			int[] copyOfBuckets = new int[this.buckets.length];
			//for loop to loop throuhg the buckets array
			for (int i = 0; i < buckets.length; i++) {
				//copying elements from the original buckets 
				//array to the new copy array
	            copyOfBuckets[i] = buckets[i];
	        }
			//returns the copied array
			return copyOfBuckets;
		}
		/**
		 * This method compares one bucket state to another
		 * o - is the object being passed in
		 * Returns true or false 
		 * Overrides the equals method in the superclass
		 */
		@Override
		public boolean equals(Object o){
			//creates a new BucketStateObject
			final BucketState other = (BucketState) o;
			//if there is nothing in the BucketState
			if(o == null){
				//return false 
				return false;
			}
			//when you use the getClass approach, you have the restriction that 
			//objects are only equal to other objects of the same class, the same run time type.
			//SO essentially if the object are not of the same type 
			if (getClass() != o.getClass()){
				//return false
				return false;
			}
			//if goal is equal to true, or the other goal is equal to true,
			//AND the element at location 0 of both bucket arrays is equal 
			if(goal == true || other.goal == true && this.buckets[0] == other.buckets[0]){
				//return true
				return true;
			}
			//otherwise if the two arrays of buckets are not equal
			else if (Arrays.equals(this.buckets, other.buckets) != true){
				//return false
				return false;
			}
			//return true
			return true;
		}
		/**
		 * This method creates and returns a string to display the current buckets and 
		 * how much sand they have in them
		 * Overrides the "toString" method declared in the SuperClass
		 */
		@Override 
		public String toString(){
			//create new StringBuilder object called "str"
			StringBuilder str = new StringBuilder();
			//if the length of the buckets array is greater than 0
			if(buckets.length > 0){
				//for loop for iterating through the array of buckets 
				for(int bucket : buckets){
					//Appends the string representation of the char argument to this sequence.
					str.append(bucket).append(' ');
				}
				//Removes the char at the specified position in this sequence.
				str.deleteCharAt(str.length() - 1);
			}
			//Returns a string representing the data in this sequence.
			return str.toString();
		}
		/**
		 * This method creates and returns a hash code 
		 * Overrides the "hashCode" method declared in the SuperClass
		 */
		@Override
		public int hashCode(){
			//starting integer used in creating the final hash code
			int hash = 7;
			//temp variable used to modify the hash code depending on 
			//whether goal is true or false 
			int temp;
			//if goal is equal to true
			if(this.goal == true){
				//set temp equal to 1
				temp = 1;
			}
			//otherwise
			else{
				//set temp equal to 0
				temp = 0;
			}
			//creating the hash code by using a large prime number "97" and multiplying times
			//the also prime number "7" and then adding to the temp value
			hash = 97 * hash + temp;
			//modifying the hash code by again using a large prime number "97" and multiplying 
			//times the current hash code, then adding a hash code based on the contents of 
			//the specified array, which in this case is "buckets." This should insure that
			//no collisions occur when attempting to add values to a HashSet. 
			hash = 97 * hash + Arrays.hashCode(this.buckets);
			//returns the created hash code
			return hash;
		}
	}
	/**
	 * This is the main method, this method takes in in the form of command line arguments,
	 * a given amount of sand, followed by a number of buckets specified by their 
	 * maximum holding capacity.
	 * @param args - the command line arguments
	 */
	public static void main(String[] args){
		
	// if the program has passed in less than 2 arguments	
	if(args.length < 2){
		//tell the user how to use the program
		System.out.println("You must run with at least two arguments, the ammount of sand, and the"
				+ "capacity of the first bucket. "); 
		//tell the user how to enter in multiple buckets via command line arguments 
		System.out.println("Additional bucket capacities can be specified afterwards.");
		//break out of the method because the program cannot continue correctly
		//if there is not the proper amount of arguments passed into it
		return;
	}
	//variable to hold the given amount of sand, in this case it should be the 
	//first command line argument
	int amountOfSand;
	//create and array of integers to hold the number of buckets which are identified 
	//by the maximum amount of sand that they can hold. In this case they should be all
	//command line arguments following the first one
	int[] buckets = new int[args.length - 1];
	//set the given amount of sand equal to the first command line argument 
	amountOfSand = Integer.parseInt(args[0]);
	//create a for loop to iterate through the command line arguments
	for (int i = 1; i < args.length; i++){
		//excluding the first argument, hence why i starts at 1, set the given 
		//argument to the position (i-1) in the array buckets. We must do this because
		//the first argument is the amount of sand which dose not belong in the 
		//array of buckets.
		buckets[i - 1] = Integer.parseInt(args[i]);
	}
	
	//create a new Sand object call "sand" passing the constructor 
	//the given amount of sand, and all the given buckets and their
	//maximum holding capacities 
	Sand sand = new Sand(amountOfSand, buckets);
	//create a new Solver object call "solver" which will be used to solve the 
	//given "sand puzzle"
	Solver solver = new Solver();
	//create a new ArrayList of BucketState Objects call "result" and set it equal 
	//to the recently created "solver" object which will then call the method "solver"
	//from the Solver class, passing it the current given "sand puzzle"
	ArrayList<BucketState> result = solver.solver(sand);
	//If there is nothing in the ArrayList "result"
	if (result != null){
		//create for loop for displaying steps
		for (int i = 0; i < result.size(); i++){
			//print out each step
			System.out.println("Step " + i + ": " + result.get(i));
		}
	}
	//otherwise
	else{
		//print out no solution 
		System.out.println("No Solution.");
	}
	
	
	}
	
}