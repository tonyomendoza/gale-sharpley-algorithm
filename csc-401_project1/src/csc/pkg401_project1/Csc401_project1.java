package csc.pkg401_project1;

import java.util.ArrayList;
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
            resetMatches();
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
            System.out.print("\t\t=>" + men.get(m).toString() + ": ");
            for (int i = 0; i < men.get(m).getPreferences().length; i++) {
                System.out.print("F" + men.get(m).getPreferences()[i] + " ");
            }
            System.out.println();
            
            // reset the matching list with original preferences
            men.get(m).resetMatchings();
        }

        // Create the individualized preference lists for women
        System.out.println("\t=> Creating preference lists for women. " + size);
        for (int f = 0; f < size; f++) { // for every woman
            women.get(f).setPreferences(men); // set the preferences of a woman
            women.get(f).shufflePreferences(); // shuffle the preferences
            // Print the preferences
            System.out.print("\t\t=>" + women.get(f).toString() + ": ");
            for (int i = 0; i < women.get(f).getPreferences().length; i++) {
                System.out.print("M" + women.get(f).getPreferences()[i] + " ");
            }
            System.out.println();
        
            // reset the matching list copy with original preferences
            women.get(f).resetMatchings();
        }
    }

    // determines which man the woman prefers
    static boolean WPrefersMOverM1(Person woman, Person m, Person m1) {
        for (int i = 0; i < women.size(); i++) {
            // if the man proposing is higher on the woman's preference list return true
            // els return false
            if (woman.getPreference(i) == m.getID()) {
                return true;
            }
            if (woman.getPreference(i) == m1.getID()) {
                return false;
            }
        }
        return false;
    }

    // The gale-shapely algorithm used to complete the project. It matches up the couples.
    static void gsAlgorithm(int startingMan) {
        int count = startingMan;
        unmatchedMen = men.size();
        // while there is still a free man
        while (unmatchedMen != 0) {
            // if the selected man is free continue
            // else move to next man in list
            if (men.get(count).getPartner() == -1) {
                Person m1 = men.get(count);
                for (int i = 0; i < men.size() && m1.getPartner() == -1;) {
                    // if the chosen man has already proposed and been rejected move to
                    // the next choice id preference list
                    if (men.get(count).getPreference(i) == -1) {
                        i++;
                    } else {
                        // if the chosen woman is free get engaged
                        if (women.get(m1.getPreference(i)).getPartner() == -1) {
                            women.get(m1.getPreference(i)).setPartner(m1.getID());
                            men.get(count).setPartner(m1.getPreference(i));
                            proposalCount++;
                            unmatchedMen--;
                        } else {
                            // propose and ask woman who she prefers. if it is the current man switch
                            // else move on
                            Person womanTemp = women.get(m1.getPreference(i));
                            Person womanTemp2 = women.get(m1.getPreference(i));
                            Person man1 = men.get(count);
                            Person man2 = men.get(womanTemp.getPartner());
                            if (WPrefersMOverM1(womanTemp, man1, man2)) {
                                men.get(count).setPartner(womanTemp.getID());
                                men.get(womanTemp.getPartner()).setPartner(-1);
                                women.get(m1.getPreference(i)).setPartner(men.get(count).getID());
                                proposalCount++;
                                // mark the woman who rejected the man as taken in selected mans preference list
                                // so he does not propose to here twice.
                                for (int j = 0; j < men.size(); j++) {
                                    if (men.get(womanTemp2.getPartner()).getPreference(j) == -1) {
                                        continue;
                                    } else {
                                        men.get(womanTemp2.getPartner()).setPreference(j, -1);
                                        break;
                                    }
                                }
                            } else {
                                proposalCount++;
                                // mark the woman who rejected the man as taken in selected mans preference list
                                // so he does not propose to here twice.
                                for (int k = 0; k < men.size(); k++) {
                                    if (men.get(count).getPreference(k) == -1) {
                                        continue;
                                    } else {
                                        men.get(count).setPreference(k, -1);
                                        break;
                                    }
                                }
                            }
                        }
                        break;
                    }
                }
            } else if (count == men.size() - 1) {
                count = 0;
            } else {
                count++;
            }
        }
    }

    // Print stable matchings
    static void printMatches(int startingMan) {
        // Print who proposed first
        System.out.println("\t=>M" + startingMan + " proposes. It takes " + proposalCount
                + " proposals to reach the final stable marriage.");
        // Print every matching.
        for (int i = 0; i < men.size(); i++) {
            System.out.println("\t\t=>M" + men.get(i).getID() + " - " + "F" + men.get(i).getPartner());
        }
    }

    // resets all marriages to singles. i pity the lonely persons.
    static void resetMatches() {
        for (int i = 0; i < men.size(); i++) { // for every iteration
            men.get(i).setPartner(-1); // reset the partner of the man
            women.get(i).setPartner(-1); // reset the partner of the woman
            // reset the matching list with original preferences
            men.get(i).resetMatchings();
            women.get(i).resetMatchings();
        }
        proposalCount = 0;
    }
}
