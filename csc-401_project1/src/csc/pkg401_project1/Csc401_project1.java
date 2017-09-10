package csc.pkg401_project1;

import java.util.ArrayList;

/**
 *
 * @author Kris Giddons and Tony Mendoza
 */

public class Csc401_project1 {
    static ArrayList<Person> men;
    static ArrayList<Person> women;
    static ArrayList<Person> unmatchedMen;
    static final int TOTAL_PEOPLE = 20;

    public static void main(String[] args) {
        System.out.println("=> Application: gale-sharpley-algorithm");
        Initialize();
        TEST_PrintPreferences();
    }
    
    // Determine who is admitted in which years
    static void Initialize(){
        int halfSize = TOTAL_PEOPLE / 2;
        System.out.println("=> Initializing the people to match.");
        men = new ArrayList<>(halfSize); // construct structure for the men
        women = new ArrayList<>(halfSize); // construct structure for the women
        unmatchedMen = new ArrayList<>(); // construct structure for unmatched men
        
        System.out.println("\t=> Adding the men. " + halfSize);
        for (int m = 0; m < halfSize; m++) { // for every man
            Person man = new Person(m, Person.BinaryGender.M);
            men.add(man);
            //System.out.println("\t\t=> Added: " + man);
        }
                
        System.out.println("\t=> Adding the women. " + halfSize);
        for (int f = 0; f < halfSize; f++) { // for every woman
            Person woman = new Person(f, Person.BinaryGender.F);
            women.add(woman);
            //System.out.println("\t\t=> Added: " + woman);
        }
        
        // Create the individualized preference lists for men
        System.out.println("\t=> Creating preference lists for men. " + halfSize);
        for (int m = 0; m < halfSize; m++) { // for every man
            men.get(m).setPreferences(women);
            men.get(m).shufflePreferences();
        }
        
        
        // Create the individualized preference lists for women
        System.out.println("\t=> Creating preference lists for women. " + halfSize);
        for (int f = 0; f < halfSize; f++) { // for every woman
            women.get(f).setPreferences(men);
            women.get(f).shufflePreferences();
        }
    }
    
    public static void TEST_PrintPreferences(){
        System.out.println("\t=> Printing preferences for men. ");
        for(int m = 0; m < men.size(); m++){
            System.out.println("\t=> " + men.get(m));
            for(int i = 0; i < men.get(m).getPreferences().length; i++)
                System.out.println("\t\t=> " + men.get(m).getPreferences()[i]);
        }
        
        System.out.println("\t=> Printing preferences for women. ");
        for(int f = 0; f < women.size(); f++){
            System.out.println("\t=> " + women.get(f));
            for(int i = 0; i < women.get(f).getPreferences().length; i++)
                System.out.println("\t\t=> " + women.get(f).getPreferences()[i]);
        }
    }

    // Create a random number
    static int RandomInt(int length) {
        return (int) (Math.random() * length);
    }
}