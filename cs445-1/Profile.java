/**
 * Twesha Mitra - Assignment 1
 */
package cs445.a1;
import java.lang.*;
import java.io.Serializable;
import java.util.*;

public class Profile extends Set implements ProfileInterface {
	private String name;
	private String about;
	private Set<ProfileInterface> set1= new Set<ProfileInterface>(5);
	private int arraysize=set1.getCurrentSize();
	private int arraylength=set1.getLength();
	
	/*
	 *	This default constructor initializes name and about to empty strings
	 */
	public Profile(){
		name="";
		about="";
	}
	
	/*
	 * This constructor takes the name and about from the user and sets it to 
	 * the name and about of this class
	 * @param name
	 * @param about
	 */
	public Profile(String name, String about){	
		this.name=name;
		this.about=about;
		set1=new Set<ProfileInterface>(5);
	}	
		
	/**
     * Sets this profile's name.
     *
     * <p> If newName is not null, then setName modifies this profile so that
     * its name is newName. If newName is null, then setName throws
     * java.lang.IllegalArgumentException without modifying the profile.
     *
     * @param newName  The new name
     * @throws java.lang.IllegalArgumentException  If newEntry is null
     */
	public void setName(String newName) throws java.lang.IllegalArgumentException{
		if(newName==null)
			throw new IllegalArgumentException();
		else name=newName;
	}
		
	/**
     * Gets this profile's name.
     *
     * @return  The name
     */
	public String getName(){
		return this.name;	
	}	
	
	/**
     * Sets this profile's "about me" blurb.
     *
     * <p> If newAbout is not null, then setAbout modifies this profile so that
     * its about blurb is newAbout. If newAbout is null, then setAbout throws
     * java.lang.IllegalArgumentException without modifying the profile.
     *
     * @param newAbout  The new blurb
     * @throws java.lang.IllegalArgumentException  If newAbout is null
     */
	public void setAbout(String newAbout) throws java.lang.IllegalArgumentException{
		if(newAbout==null)
			throw new IllegalArgumentException();
		else 
			about=newAbout;
	}
	
    /**
     * Gets this profile's "about me" blurb
     *
     * @return  The blurb
     */
	public String getAbout(){
		return this.about;
	}
	
	 /**
     * Adds another profile to this profile's following set.
     *
     * <p> If this profile's following set is at capacity, or if other is null,
     * then follow returns false without modifying the profile. Otherwise, this
     * profile is modified in such a way that other is added to this profile's
     * following set.
     *
     * @param other  The profile to follow
     * @return  True if successful, false otherwise
     */
	public boolean follow(ProfileInterface other) {
		boolean result=true;
		if(other==null || arraysize==arraylength)
			result=false;
		else{
			set1.add(other);
			result=true;
			}
		return result;	
	}
	
 	/**
     * Removes the specified profile from this profile's following set.
     *
     * <p> If this profile's following set does not contain other, or if other
     * is null, then unfollow returns false without modifying the profile.
     * Otherwise, this profile in modified in such a way that other is removed
     * from this profile's following set.
     *
     * @param other  The profile to follow
     * @return  True if successful, false otherwise
     */
	public boolean unfollow(ProfileInterface other) {
		boolean result=false;
		if((!set1.contains(other))||other==null)
			result=false;
		else 
			{
			set1.remove(other);
			result=true;
			}
		return result;		
	}
	
	/**
     * Returns a preview of this profile's following set.
     *
     * <p> The howMany parameter is a maximum size. The returned array may be
     * less than the requested size if this profile is following fewer than
     * howMany other profiles. Clients of this method must be careful to check
     * the size of the returned array to avoid
     * java.lang.ArrayIndexOutOfBoundsException.
     *
     * <p> Specifically, following returns an array of size max(howMany,
     * &lt;number of profiles that this profile is following&gt;). This array is
     * populated with arbitrary profiles that this profile follows.
     *
     * @param howMany  The maximum number of profiles to return
     * @return  An array of size &le;howMany, containing profiles that this
     * profile follows
     */
  
	public ProfileInterface[] following(int howMany){
		Object[] o1= new Object[arraysize];
		o1=set1.toArray();
		if (set1.getCurrentSize()> howMany)
		{
			ProfileInterface[] p1= new ProfileInterface[howMany];
			int i=0;
			while(i<howMany) {
				p1[i] = (ProfileInterface)o1[i];
				i++;
			}
			return p1;
		}
		else
		{
			ProfileInterface[] p1 = new ProfileInterface[set1.getCurrentSize()];
			int x=0; 
			while(x<set1.getCurrentSize()){
				p1[x] = (ProfileInterface)o1[x];
				x++;
			}
			return p1;
		}
	}
	
	/**
     * Recommends a profile for this profile to follow. This returns a profile
     * followed by one of this profile's followed profiles. Should not recommend
     * this profile to follow someone they already follow, and should not
     * recommend to follow oneself.
     *
     * <p> For example, if this profile is Alex, and Alex follows Bart, and Bart
     * follows Crissy, this method might return Crissy.
     *
     * @return  The profile to suggest, or null if no suitable profile is
     * possible.
     */
	public ProfileInterface recommend(){
		Random r = new Random(arraysize);
		Object [] o2= new Object[arraysize];
		o2=set1.toArray();
		return (ProfileInterface) o2[r.nextInt()];
	}
}