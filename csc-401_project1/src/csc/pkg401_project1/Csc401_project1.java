package csc.pkg401_project1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

/**
 *
 * @author Kris Giddens and Tony Mendoza
 */
public class Csc401_project1 {

    // structures to hold are person objects
    static ArrayList<Person> men;
    static ArrayList<Person> women;
    // data used to hold 
    static int unmatchedMen = 0;
    static int proposalCount;
    static HashMap<String, Integer> frequencyMap = new HashMap<String, Integer>();

    public static void main(String[] args) {
        System.out.println("=> Application: gale-sharpley-algorithm");
        Scanner input = new Scanner(System.in);
        System.out.println("=? How many pairs should be matched?");
        Initialize(input.nextInt());
        // TEST_PrintPreferences();
        System.out.println("=> Every man will have a chance to propose first. Printing matching results.");
        for (int m = 0; m < men.size(); m++) {
            gsAlgorithm(m);
            printMatches(m);
            calculateProposalCount();
            resetMatches();
        }

        System.out.println("\nThe following numbers are the total number of proposal counts each \n"
                + "time through the algorithm folowed by the frequency of that proposal count\n"
                + "Example (total proposal count : frequency of proposal count)");
        for (Entry<String, Integer> entry : frequencyMap.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    // Initialize the people to match. 
    static void Initialize(int size) {
        System.out.println("=> Initializing the people to match.");
        men = new ArrayList<>(size); // construct structure for the men
        women = new ArrayList<>(size); // construct structure for the women

        // System.out.println("\t=> Adding the men. " + size);
        for (int m = 0; m < size; m++) { // for every man
            Person man = new Person(m, Person.BinaryGender.M); // create a new man
            men.add(man); // add the man
            // System.out.println("\t\t=> Added: " + man);
        }

        // System.out.println("\t=> Adding the women. " + size);
        for (int f = 0; f < size; f++) { // for every woman
            Person woman = new Person(f, Person.BinaryGender.F); // create a new woman
            women.add(woman); // add the woman
            // System.out.println("\t\t=> Added: " + woman);
        }

        // Create the individualized preference lists for men
        System.out.println("\t=> Creating preference lists for men. " + size);
        for (int m = 0; m < size; m++) { // for every man
            men.get(m).setPreferences(women); // set the preferences for a man
            men.get(m).shufflePreferences(); // shuffle the preferences

            // Print the preferences
            System.out.print("\t\t=> " + men.get(m).toString() + ": ");
            for (int i = 0; i < men.get(m).getPreferences().length; i++) {
                System.out.print("F" + men.get(m).getPreferences()[i] + " ");
            }
            System.out.println();

        }

        // Create the individualized preference lists for women
        System.out.println("\t=> Creating preference lists for women. " + size);
        for (int f = 0; f < size; f++) { // for every woman
            women.get(f).setPreferences(men); // set the preferences of a woman
            women.get(f).shufflePreferences(); // shuffle the preferences
            // Print the preferences
            System.out.print("\t\t=> " + women.get(f).toString() + ": ");
            for (int i = 0; i < women.get(f).getPreferences().length; i++) {
                System.out.print("M" + women.get(f).getPreferences()[i] + " ");
            }
            System.out.println();
        }
    }

    // determines which man the woman prefers
    static boolean WPrefersMOverM1(Person woman, Person m, Person m1) {
        // 
        for (int i = 0; i < woman.getPreferences().length; i++) {
            if (woman.getPreferences()[i] == m.getID()) {
                return true;
            }
            if (woman.getPreferences()[i] == m1.getID()) {
                return false;
            }
        }
        return false;
    }

    // the algorith mthat matches the pairs
    static void gsAlgorithm(int startingMan) {
        // reset stuff
        int count = startingMan;
        unmatchedMen = men.size();
        // while there is a man that has not been matched
        while (unmatchedMen != 0) {
            Person man = men.get(count);
            
            // If the man does not have a partner,
            if (man.getPartner() == -1) {
                // then check through the man's preference list
                for (int f = man.getPreferenceCounter(); f < women.size() && man.getPartner() == -1; f++) {
                    // Propose to the woman
                    Person woman = women.get(man.getPreferences()[f]);
                    proposalCount++;
                    if (woman.getPartner() == -1) { // if the woman does not have a partner, then this man is her partner
                        woman.setPartner(man.getID());
                        man.setPartner(woman.getID());
                        unmatchedMen--; // one less unmatched man
                    } else {
                        proposalCount++;
                        Person oldMan = men.get(woman.getPartner());
                        // else if the woman prefers this man over her new partner, then time to change up.
                        if (WPrefersMOverM1(woman, man, oldMan)) {
                            oldMan.setPartner(-1); // i pity this person
                            woman.setPartner(man.getID());
                            man.setPartner(woman.getID());
                        }
                    }
                    man.setPreferenceCounter(1); // prepare for a next preference.
                }
            }
            // prepare to iterate to the next man.
            count++;
            if (count == men.size()) {
                count = 0;
            }
        }
    }

    // Print stable matchings
    static void printMatches(int startingMan) {
        // Print who proposed first
        System.out.println("\t=> M" + startingMan + " proposes. It takes " + proposalCount
                + " proposals to reach the final stable marriage.");
        // Print every matching.
        for (int i = 0; i < men.size(); i++) {
            System.out.println("\t\t=> M" + men.get(i).getID() + " - " + "F" + men.get(i).getPartner());
        }
    }

    static void calculateProposalCount() {
        String str = Integer.toString(proposalCount);
        if (frequencyMap.get(str) == null) {
            frequencyMap.put(str, 1);
        } else {
            frequencyMap.put(str, (frequencyMap.get(str)) + 1);
        }
    }

    // resets all marriages to singles. i pity the lonely persons.
    static void resetMatches() {
        for (int i = 0; i < men.size(); i++) { // for every iteration
            men.get(i).setPartner(-1); // reset the partner of the man
            men.get(i).resetPreferenceCounter();
            women.get(i).setPartner(-1); // reset the partner of the woman
            // reset the matching list with original preferences
        }
        proposalCount = 0;
    }
}
