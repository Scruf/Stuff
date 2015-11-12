import java.util.ArrayList;
//An interface to a Puzzle. 
//It contains the routines necessary for accessing the start and goal configs, 
//as well as generating new neighboring configs.
public interface Puzzle<E> {

	//Get the goal config for this puzzle.
    public E getStart();
    //For an incoming config, generate and return all direct neighbors to this config.
    public ArrayList<E> getNeighbors(E config);
    // Get the starting config for this puzzle.
    public E getGoal();
}