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
    enum BinaryGender { M, F };
    BinaryGender gender;
    
    // references/pointers
   int[] preferences; 
    
   public Person(int id, BinaryGender gender){
       this.id = id;
       this.gender = gender;
       name = gender.toString() + id;
   }
      
   public void setPreferences(ArrayList<Person> persons){
       preferences = new int[persons.size()];
       for(int i = 0; i < persons.size(); i++){
           preferences[i] = persons.get(i).getID();
       }
   }
   
   public int[] getPreferences(){
       return preferences;
   }
   
    // Shuffle the deck
    public void shufflePreferences() {
        for (int i = 0; i < preferences.length; i++) { // For every card
            int j = (int) (Math.random() * preferences.length); // the position to be swapped
            if (i != j) { // if the card is not in the position to be swapped, then swap
                int temp = preferences[i];
                preferences[i] = preferences[j];
                preferences[j] = temp;
            } else { // else try again
                i--;
            }
        }
    }
    
    public int getID(){
        return id;
    }
   
   @Override
   public String toString(){
     return name;  
   }
}