package csc.pkg401_project1;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Tony Mendoza
 */
public class Person {
	// personal data for Person
	int id;
	String name;
        // determines the gender of the person
	enum BinaryGender {
		M, F
	};
	BinaryGender gender;
        
        // matching relevant data
	int partner;
	// references for the preferences. Stuctures store them for the program.
	int[] preferences;
        int preferenceCounter;

        // Person constructor
	public Person(int id, BinaryGender gender) {
		this.id = id;
		this.gender = gender;
		name = gender.toString() + id;
		this.partner = -1;
                preferenceCounter = 0;
	}

        // gets partner
	public int getPartner() {
		return partner;
	}

        // sets current partner
	public void setPartner(int partner) {
		this.partner = partner;
	}

        // sets preferences, typically used for initialization
	public void setPreferences(ArrayList<Person> persons) {
		preferences = new int[persons.size()];
		for (int i = 0; i < persons.size(); i++) {
			preferences[i] = persons.get(i).getID();
		}
	}

        // returns prefernece list
	public int[] getPreferences() {
		return preferences;
	}
        
        public void setPreferenceCounter(int value){
            preferenceCounter += value;
        }
        public void resetPreferenceCounter(){
            preferenceCounter = 0;
        }
        
        public int getPreferenceCounter(){
            return preferenceCounter;
        }
 
	// Shuffle the preferences, used during initialization
	public void shufflePreferences() {
		for (int i = 0; i < preferences.length; i++) { // For every possible preference
			int j = (int) (Math.random() * preferences.length); // randomly swap between two preferences
				int temp = preferences[i];
				preferences[i] = preferences[j];
				preferences[j] = temp;
		}
	}

        // return id
	public int getID() {
		return id;
	}

        // return name
	@Override
	public String toString() {
		return name;
	}
}