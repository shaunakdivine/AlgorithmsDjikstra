/*
Shaunak Divine
jsd2672
 */

// Implement your heap here
// Private methods may be added to this file, but don't remove anything
// Include this file in your final submission

import java.util.ArrayList;
import java.util.Queue;

public class Heap implements HeapInterface {
    private ArrayList<City> minHeap;

    public Heap() {
        minHeap = new ArrayList<City>();
    }

    /**
     * buildHeap(ArrayList<City> cities)
     * Given an ArrayList of Cities, build a min-heap keyed on each City's minDist
     * Time Complexity - O(nlog(n)) or O(n)
     *
     * Should assign cities to the minHeap instance variable
     *
     *
     * @param cities
     */
    public void buildHeap(ArrayList<City> cities) {

        for (int i = (cities.size()/2)-1; i >= 0; i--){
            heapifyDown(cities, i);
        }

        minHeap = cities;
        // TODO: implement this method
    }

    private void heapifyUp(ArrayList<City> h, int index){
        int j = 0;
        if (index > 0){
            j = (index-1)/2;
            if (h.get(index).getMinDist() < h.get(j).getMinDist()){
                City temp = h.get(j);
                h.set(j, h.get(index));
                h.get(j).setIndex(j);
                h.set(index, temp);
                h.get(index).setIndex(index);
                heapifyUp(h, j);
            }
            if (h.get(index).getMinDist() == h.get(j).getMinDist()){
                if (h.get(index).getName() < h.get(j).getName()){
                    City temp = h.get(j);
                    h.set(j, h.get(index));
                    h.get(j).setIndex(j);
                    h.set(index, temp);
                    h.get(index).setIndex(index);
                    heapifyUp(h, j);
                }
            }
        }

    }

    private void heapifyDown(ArrayList<City> h, int index){
        int n = h.size()-1;
        int j = 0;
        if (n == 0){
            h.get(n).setIndex(0);
            return;
        }
        if (2*index > n){
            return;
        }
        else if (2*index < n){
            int left = 2*index + 1;
            int right = 2*index + 2;
            if (right > n){
                j = left;
            }
            else if (h.get(left).getMinDist() > h.get(right).getMinDist()){
                j = right;
            }
            else {
                j = left;
            }
        }
        else if (2*index == n){
            j = 2*index;
        }

        if (h.get(j).getMinDist() < h.get(index).getMinDist()){
            City temp = h.get(j);
            h.set(j, h.get(index));
            h.set(index, temp);
            h.get(j).setIndex(j);
            h.get(index).setIndex(index);
            heapifyDown(h, j);
        }
        if (h.get(index).getMinDist() == h.get(j).getMinDist()){
            if (h.get(j).getName() < h.get(index).getName()) {
                City temp = h.get(j);
                h.set(j, h.get(index));
                h.set(index, temp);
                h.get(j).setIndex(j);
                h.get(index).setIndex(index);
                heapifyDown(h, j);
            }
        }
    }

    /**
     * insertNode(City in)
     * Insert a City into the heap.
     * Time Complexity - O(log(n))
     *
     * @param in - the City to insert.
     */
    public void insertNode(City in) {
        minHeap.add(in);
        minHeap.get(minHeap.size()-1).setIndex(minHeap.size()-1);
        heapifyUp(minHeap, minHeap.size()-1); //might need to be size-1

        // TODO: implement this method
    }

    /**
     * findMin()
     * Time Complexity - O(1)
     *
     * @return the minimum element of the heap.
     */
    public City findMin() {
        City minCity = minHeap.get(0);
        return minCity;
    }

    /**
     * extractMin()
     * Time Complexity - O(log(n))
     *
     * @return the minimum element of the heap, AND removes the element from said heap.
     */
    public City extractMin() {
        City minCity = minHeap.get(0);
        minHeap.set(0,minHeap.get(minHeap.size()-1));
        minHeap.get(0).setIndex(0);
        minHeap.remove(minHeap.size()-1);
        heapifyDown(minHeap, 0);

        return minCity;
    }

    /**
     * delete(int index)
     * Deletes an element in the min-heap given an index to delete at.
     * Time Complexity - O(log(n))
     *
     * @param index - the index of the item to be deleted in the min-heap.
     */
    public void delete(int index) {
        if (minHeap.size() == 0){
            return;
        }
        if (minHeap.size() == 1){
            minHeap.remove(0);
            return;
        }
        int tempIndex = minHeap.get(minHeap.size()-1).getIndex();
        minHeap.set(index, minHeap.get(minHeap.size()-1));
        minHeap.get(index).setIndex(index);
        minHeap.remove(minHeap.size()-1);

        if (minHeap.size() == 1){
            minHeap.get(0).setIndex(0);
            return;
        }

        if (minHeap.size() == 2){
            if (minHeap.get(0).getMinDist() > minHeap.get(1).getMinDist()){
                heapifyDown(minHeap, 0);
            }
            return;
        }


        if (minHeap.get(index).getMinDist() < minHeap.get(index/2).getMinDist()){
            heapifyUp(minHeap, index);
        }

        else if (minHeap.get(index).getMinDist() > minHeap.get((index*2) + 1).getMinDist() || minHeap.get(index).getMinDist() > minHeap.get((index*2) + 2).getMinDist()){
            heapifyDown(minHeap, index);
        }
        // TODO: implement this method
    }

    /**
     * changeKey(City r, int newDist)
     * Changes minDist of City s to newDist and updates the heap.
     * Time Complexity - O(log(n))
     *
     * @param r       - the City in the heap that needs to be updated.
     * @param newDist - the new cost of City r in the heap (note that the heap is keyed on the values of minDist)
     */
    public void changeKey(City r, int newDist) {
        if (newDist > r.getMinDist()){
            r.setMinDist(newDist);
            heapifyDown(minHeap, r.getIndex());
        }
        else if (newDist < r.getMinDist()){
            r.setMinDist(newDist);
            heapifyUp(minHeap, r.getIndex());
        }
        else {
            r.setMinDist(newDist);
        }
        // TODO: implement this method
    }

    public String toString() {
        String output = "";
        for (int i = 0; i < minHeap.size(); i++) {
            output += minHeap.get(i).getName() + " ";
        }
        return output;
    }

///////////////////////////////////////////////////////////////////////////////
//                           DANGER ZONE                                     //
//                everything below is used for grading                       //
//                      please do not change :)                              //
///////////////////////////////////////////////////////////////////////////////

    public ArrayList<City> toArrayList() {
        return minHeap;
    }
}
