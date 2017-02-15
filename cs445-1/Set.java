/**
 * Twesha Mitra - Assignment 1
 * For the two different remove methods, the add(T entry) method, and getCurrentSize method,
 * I used  Professor Garrison's lecture slides as a reference to write the methods.
 */
package cs445.a1;

import java.util.*;
import java.lang.*;

public class Set<T> implements SetInterface<T> {
	private int size=0;
	private T[] contents; 
	private int length;
	private int elements;
	
	/*
	 * This constructor sets the physical size, which is the number
	 * of entries in the set to 0.
	 * 
	 * The physical size is how many elements are in the set.
	 */
	public Set(){
		contents=(T[]) new Object[10];
		length=contents.length;
	}
	
	/*
	 * This constructor takes an integer and creates an array of specified capacity
	 * 
	 *  @param capacity the logical size/length of the set
	 */
	public Set(int capacity){
		contents=(T[]) new Object[capacity];
		length=contents.length;
	}
	
    /**
     * Determines the current number of entries in this set.
     *
     * @return  The integer number of entries currently in this set
     */
	public int getCurrentSize(){
		int count=0;
			for(int x=0; x<length; x++){
				if(contents[x]!=null)
				count++;
			}
		size=count;
		return size;
	}
	
    /**
	* This getter returns length so the Profile class can have 
	* access to the length of the set
	* @return The length of the set
	*/
	public int getLength(){
		return length;
	}
	
	 /**
     * Determines whether this set is empty.
     *
     * @return  true if this set is empty; false if not
     */
	public boolean isEmpty() {
		boolean empty=false;
		elements=this.getCurrentSize();
		if(elements==0)
			empty=true;
		return empty;
	}
	
	/**
     * Determines whether this set is full.
     *
     * @return  true if this set is full; false if not
     */			
	public boolean Full(){
		boolean result=false;
		if(size==length)
			result=true;
		return result;
	}
		
	/**
     * Adds a new entry to this set, avoiding duplicates.
     *
     * <p> If newEntry is not null, this set does not contain newEntry, and this
     * set has available capacity, then add modifies the set so that it contains
     * newEntry. All other entries remain unmodified.
     *
     * <p> If newEntry is null, then add throws
     * java.lang.IllegalArgumentException without modifying the set. If this set
     * already contains newEntry, then add returns false without modifying the
     * set. If this set has a capacity limit, and does not have available
     * capacity, then add throws SetFullException without modifying the set.
     *
     * @param newEntry  The object to be added as a new entry
     * @return  true if the addition is successful; false if the item already is
     * in this set
     * @throws SetFullException  If this set does not have the capacity to store
     * an additional entry
     * @throws java.lang.IllegalArgumentException  If newEntry is null
     */
	public boolean add(T newEntry) throws java.lang.IllegalArgumentException{
		boolean added=false;
		if(this.Full()==true){
			int newLength=2*length;
			contents=Arrays.copyOf(contents,newLength);
			contents[size]=newEntry;
			size++;
			added=true;
		}
		else if((newEntry!=null) && (this.contains(newEntry)==false) && (this.Full()==false)){
			contents[size]=newEntry;
			size++;
			added=true;
		}	
		else if(newEntry==null){	
			added=false;
			throw new IllegalArgumentException();
		}
		else if(this.contains(newEntry)==true){
			added=false;
		}
		
		return added;
		}	
	
	/**
     * Removes a specific entry from this set, if possible.
     *
     * <p> If this set contains entry, remove will modify the set so that it no
     * longer contains entry. All other entries remain unmodified.
     *
     * <p> If this set does not contain entry, remove will return false without
     * modifying the set. If entry is null, then remove throws
     * java.lang.IllegalArgumentException without modifying the set.
     *
     * @param entry  The entry to be removed
     * @return  true if the removal was successful; false if not
     * @throws java.lang.IllegalArgumentException  If entry is null
     */
	public boolean remove(T entry) throws java.lang.IllegalArgumentException{ 
		if(entry==null)
			throw new IllegalArgumentException();
			int index=getIndexOf(entry);
			T result=removeEntry(index);
			return entry==result;		
	}
	private int getIndexOf(T entry){
		int where=-1;
		boolean found=false;
		int index=0;
		
		while(!found&&(index<size)){
			if(entry==contents[index]){
				found=true;
				where=index;
				}
				index++;
			}
			return where;
		}
	private T removeEntry(int x){
		T result=null;
		if(!isEmpty() && (x>=0)){
			result=contents[x];
			int lastIndex=size-1;
			contents[x]=contents[lastIndex];
			contents[lastIndex]=null;
			size--;
			}
			return result;
		}
		
	 /**
     * Removes an unspecified entry from this set, if possible.
     *
     * <p> If this set contains at least one entry, remove will modify the set
     * so that it no longer contains one of its entries. All other entries
     * remain unmodified. The removed entry will be returned.
     *
     * <p> If this set is empty, remove will return null without modifying the
     * set. Because null cannot be added, a return value of null will never
     * indicate a successful removal.
     *
     * @return  The removed entry if the removal was successful; null otherwise
     */	
	public T remove(){
		T result;
		if(size>0){
			result= contents[size-1];
			contents[size-1]=null;
			size--;
		}
		 else
		 	result=null;
		return result;
	}
	
	/**
     * Removes all entries from this set.
     *
     * <p> If this set is already empty, clear will not modify the set.
     * Otherwise, the set will be modified so that it contains no entries.
     */
	public void clear(){
		for(int i=0; i<=length; i++)
			contents[i]=null;
			size--;
	}
	 /**
     * Tests whether this set contains a given entry.
     *
     * <p> If this set contains entry, then contains returns true. Otherwise
     * (including if this set is empty), contains returns false. If entry is
     * null, then remove throws java.lang.IllegalArgumentException without
     * modifying the set.
     *
     * @param entry  The entry to locate
     * @return  true if this set contains entry; false if not
     * @throws java.lang.IllegalArgumentException  If entry is null
     */
	public boolean contains(T entry) throws java.lang.IllegalArgumentException{
		boolean found=false;
		if(entry!=null)
		{	
			for(int i=0; ((i<=this.size) && found); i++){
				if(contents[i]==entry){
					found=true;
				}
			}
		}
		else if(entry==null){
			found=false;
			throw new IllegalArgumentException();
			}
		else 
			found=false;
		return found;
	}
	
	/**
     * Retrieves all entries that are in this set.
     *
     * <p> An array is returned that contains a reference to each of the entries
     * in this set. Its capacity will be equal to the number of elements in this
     * set, and thus the array will contain no null values.
     *
     * <p> If the implementation of set is array-backed, toArray will not return
     * the private backing array. Instead, a new array will be allocated with
     * the appropriate capacity.
     *
     * @return  A newly-allocated array of all the entries in this set
     */
	public T[] toArray() {
		T[] newContents =(T[]) new Object[size];
		System.arraycopy(contents,0,newContents,0,size);
		return newContents;
	}
	
}