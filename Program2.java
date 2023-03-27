/*
 * Name: Shaunak Divine
 * EID: jsd2672
 */

// Implement your algorithms here
// Methods may be added to this file, but don't remove anything
// Include this file in your final submission

import java.sql.Array;
import java.util.ArrayList;

public class Program2 implements ProgramTwoInterface {


    // additional constructor fields may be added, but don't delete or modify anything already here


    /**
     * findMinimumRouteDistance(Problem problem)
     *
     *  @param problem - the problem to solve.
     *
     *  @param problem  - contains the cities, start, dest, and heap.
     *
     * @return the minimum distance possible to get from start to dest.
     * Assume the given graph is always connected.
     */
    public int findMinimumRouteDistance(Problem problem) {

        // Some code to get you started
        City start = problem.getStart();
        City dest = problem.getDest();
        //ArrayList<City> visited = new ArrayList<>();



        HeapInterface heap = problem.getHeap();     // get the heap
        heap.buildHeap(problem.getCities());    // build the heap
//
        heap.changeKey(start, 0);
        int newDist = 0;
        int size = heap.toArrayList().size();

        while(size != 0){
            City temp = heap.extractMin();
            size--;
            //visited.add(temp);
            temp.setIndex(-1);
            for (int i = 0; i < temp.getNeighbors().size(); i++) {
//                if (visited.contains(temp.getNeighbors().get(i))){
//                    continue;
//                }
                if (temp.getNeighbors().get(i).getIndex() == -1){
                    continue;
                }
                newDist = relax(temp, temp.getNeighbors().get(i), temp.getWeights().get(i));
                heap.changeKey(temp.getNeighbors().get(i), newDist);
            }

        }
        return dest.getMinDist();


        // TODO: implement this function



        //return -1;
    }

   private int relax(City u, City v, int w){
        int currDist = v.getMinDist();
        if (currDist > u.getMinDist() + w){
            currDist = (u.getMinDist() + w);
        }
        return currDist;
   }

    /**
     * findMinimumLength()
     *
     * @return The minimum total optical line length required to connect (span) each city on the given graph.
     * Assume the given graph is always connected.
     */
    public int findMinimumLength(Problem problem) {
        HeapInterface heap = problem.getHeap();     // get the heap
        heap.buildHeap(problem.getCities());

        int totalDist = 0;
        //ArrayList<City> visited = new ArrayList<>();

        City start = problem.getCities().get(0);
        start.setMinDist(0);

        while(heap.toArrayList().size()!=0){
            City temp = heap.extractMin();
            totalDist+=temp.getMinDist();
            temp.setIndex(-1);
            for (int i = 0; i < temp.getNeighbors().size(); i++) {
//                if (heap.toArrayList().contains(temp.getNeighbors().get(i)) && (temp.getWeights().get(i) < temp.getNeighbors().get(i).getMinDist())){
//                    heap.changeKey(temp.getNeighbors().get(i), temp.getWeights().get(i));
//                }
                if ((temp.getNeighbors().get(i).getIndex() != -1) && (temp.getWeights().get(i) < temp.getNeighbors().get(i).getMinDist())){
                    heap.changeKey(temp.getNeighbors().get(i), temp.getWeights().get(i));
                }
            }
        }


        return totalDist;
    }

    //returns edges and weights in a string.
    public String toString(Problem problem){
        String o = "";
        for (City v : problem.getCities()) {
            boolean first = true;
            o += "City ";
            o += v.getName();
            o += " has neighbors ";
            ArrayList<City> ngbr = v.getNeighbors();
            for (City n : ngbr) {
                o += first ? n.getName() : ", " + n.getName();
                first = false;
            }
            first = true;
            o += " with distances ";
            ArrayList<Integer> wght = v.getWeights();
            for (Integer i : wght) {
                o += first ? i : ", " + i;
                first = false;
            }
            o += System.getProperty("line.separator");

        }

        return o;
    }

}
