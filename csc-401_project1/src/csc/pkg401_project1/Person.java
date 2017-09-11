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

	enum BinaryGender {
		M, F
	};

	BinaryGender gender;
	int partner;

	// references/pointers
	int[] preferences;

	public Person(int id, BinaryGender gender) {
		this.id = id;
		this.gender = gender;
		name = gender.toString() + id;
		this.partner = -1;
	}

	public int getPartner() {
		return partner;
	}

	public void setPartner(int partner) {
		this.partner = partner;
	}

	public void setPreferences(ArrayList<Person> persons) {
		preferences = new int[persons.size()];
		for (int i = 0; i < persons.size(); i++) {
			preferences[i] = persons.get(i).getID();
		}
	}

	public int[] getPreferences() {
		return preferences;
	}

	public int getPreference(int man) {
		return preferences[man];
	}

	public void setPreference(int index, int value) {
		preferences[index] = value;
	}

	// Shuffle the deck
	public void shufflePreferences() {
		for (int i = 0; i < preferences.length; i++) { // For every card
			int j = (int) (Math.random() * preferences.length); // the position to be swapped
				int temp = preferences[i];
				preferences[i] = preferences[j];
				preferences[j] = temp;
		}
	}

	public int getID() {
		return id;
	}

	@Override
	public String toString() {
		return name;
	}
}