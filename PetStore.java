import java.util.Scanner;

// Pet managment software class.
public class PetStore {

    // Method to clear screen
    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }

    // Generic method that returns free space for either Classes
    public static <A> void usedSpaces(A[] aList, String type) {
        String[] var = new String[150];
        var[0] = "No space used";
        if (type == "dog") {
            int c = 0;
            for (A i : aList) {
                if (i != null) {
                    if (c == 0) {
                        var[c] = Integer.toString(((Dog) i).getDogSpaceNumber());
                        c++;
                    } else {
                        var[c] = ", ";
                        c++;
                        String tmp = Integer.toString(((Dog) i).getDogSpaceNumber());
                        var[c] = tmp;
                        c++;
                    }
                }
            }
        } else if (type == "cat") { // loops through list if type cat, and grabs location.
            int c = 0;
            for (A i : aList) {
                if (i != null) {
                    if (c == 0) {
                        var[c] = Integer.toString(((Cat) i).getCatSpaceNumber());
                        c++;
                    } else {
                        var[c] = ", ";
                        c++;
                        String tmp = Integer.toString(((Cat) i).getCatSpaceNumber());
                        var[c] = tmp;
                        c++;
                    }
                }
            }
        } else { System.out.println("we have problems"); }
        String c = String.join("", var);
        System.out.println("\nUsed spaces:\n(" + c.replace("null", "") + ")"); // Displays used spaces to screen.
    }

    // using regex to ensure input is numeric only. Recursion allows this to loop as many times as needed to get correct user input.
    public static int getInputInt(Scanner scnr) {
        System.out.print("Enter whole number: ");
        String input = scnr.next();

        //check the input validity
        if(!input.matches("^[0-9]+$")){

            //if the input is incorrect tell the user and get the new input
            clearScreen();
            System.out.println("Invalid Input.");

            //simply return this method if the input is incorrect.
            return getInputInt(scnr);
        }

        //return the input if it is correct
        return Integer.valueOf(input);
    }

    // same method as above, just allowing only alphabetical letters.
    public static String getInputString(Scanner scnr) {
        System.out.print("Enter string: ");
        String input = scnr.next();

        //check the input validity. This is essential to ensure names don't contain any special characters.
        if(!input.matches("^([A-Za-z])+$")) {

            //if the input is incorrect tell the user and get the new input
            clearScreen();
            System.out.println("Invalid Input. No numbers or symbols allowed.");

            //simply return this method if the input is incorrect.
            return getInputString(scnr);
        }

        //return the input if it is correct
        return input;
    }

    // same princible as above, using try catch to ensure user input is correct.
    public static Double getInputDouble(Scanner scnr) {
        System.out.print("Enter string: ");
        Double input = 0.0;
        // try catch to catch any exceptions.
        try {
            input = scnr.nextDouble();
        } catch (Exception e) { System.out.println("Please enter a decimal value."); getInputDouble(scnr); } //returns method if user input in invalid.
        return input;
    }

    // get pet type.
    public static String getType(Scanner scnr) {
        String type = getInputString(scnr);
        if (!type.equals("dog") && !type.equals("cat")) { // Ensure user input equals dog or cat.
            System.out.println("please enter \"dog\" or \"cat\"");
            return getType(scnr);
        }
        return type;
    }

    // get boolean input from user.
    public static Boolean getGrooming(Scanner scnr) {
        System.out.println("Optional grooming? (y/n)");
        String grooming = getInputString(scnr);
        if (grooming.charAt(0) == 'y') {
            return true;
        } else if (grooming.charAt(0) == 'n') {
            return false;
        } else { System.out.println("Enter \"y\" or \"n\"."); return getGrooming(scnr); }
    }

    // gets pet name and checks that it alligns with specified requirements.
    public static String getName(Scanner scnr) {
        String x = getInputString(scnr);
        if (x.length() > 12) {
            System.out.println("Name must be less than 12 letters in length.");
            return getName(scnr);
        }
        return x;
    }

    // method to "check in" pets to the daycare
    public static int[] checkIn(Scanner scnr, int dSpace, int cSpace, Dog[] dogList, Cat[] catList) {
        int[] var = new int[2]; //array used for return values for integer counts of remaining dog spaces and cat spaces.

        // variables used to store user input
        String type = null;
        String name = null;
        int age = -1;
        int stay = -1;
        int dogWeight = -1;
        int dogSpace = 0;
        int catSpace = 0;
        boolean m = true;
        
        clearScreen();
        System.out.println("Enter pet type."); // user input for pet type
        type = getType(scnr);

        clearScreen();
        System.out.println("Enter pet name. (<12 characters in length)");
        name = getInputString(scnr); // user input for pet name

        clearScreen();
        System.out.println("Enter pet age.");
        age = getInputInt(scnr); // user input for age

        clearScreen();
        System.out.println("Enter pet stay."); //user input for pet stay
        stay = getInputInt(scnr);

        clearScreen();
        switch (type.toLowerCase()) { //uses switch to control logical flow. check for cat or dog.
            case "dog":
                // checks to ensure a space is free.
                if (dSpace > 0 ) {
                    
                    while (m == true) {
                        usedSpaces(dogList, "dog");
                        System.out.println("\nEnter dog location: (1-30)"); // gets pet storage location.
                        dogSpace = getInputInt(scnr);

                        for (Dog z : dogList) {
                            if (z != null && ((Dog)z).getDogSpaceNumber() == dogSpace) { // loops through current dogs in inventory, checks if selected space is already in use.
                                clearScreen();
                                System.out.println("Space already in use.");
                                break;
                            } else { m = false; break; } // breaks out of loop if input has no errors.
                        }
                    }
                    clearScreen();
                    System.out.println("Enter Dog weight."); // get dog weight from user
                    dogWeight = getInputInt(scnr);

                    clearScreen();
                    boolean groom = getGrooming(scnr); // get optional grooming
                    Dog tmp = new Dog(type, name, age, stay, dogSpace, dogWeight, groom); // create new dog object using specified user values.
                    dogList[dogSpace-1] = tmp; // add new dog object to array, in the indexed position chosen by user.
                    dSpace -= 1;
                    var[0] = dSpace; // keeping track of free space to quickly check if space is free, rather than looping more than needed. 
                    var[1] = cSpace;
                    break;
                } else { System.out.println("No more pet spaces."); return null; }
            case "cat":
                if (cSpace > 0) {
                    while (m == true) {
                        usedSpaces(catList, "cat");
                        System.out.println("\nEnter cat location: (1-12)"); // gets cat location
                        catSpace = getInputInt(scnr);
                        for (Cat z : catList) {
                            if (z != null && ((Cat)z).getCatSpaceNumber() == catSpace) { // in the same way as dogs, the cat space number chosen is checked to ensure another pet doesnt get deleted.
                                clearScreen();
                                System.out.println("Space already in use.");
                                break;
                            } else { m = false; break; }
                        }
                    }
                    Cat tmpCat = new Cat(type, name, age, stay, catSpace); // initialize new cat obejct.
                    catList[catSpace-1] = tmpCat; // store object.
                    cSpace -= 1;
                    var[0] = dSpace; // keep track of free space.
                    var[1] = cSpace;
                    break;
                } else { System.out.println("No more pet spaces."); }
        }
        return var; // returned to main method.
    }

    // calculates amount due for user based on table proved in the pet store diagram.
    public static double calcAmount(Dog dog) {
        double baseBoardingFee = 0;
        double amountDue;

        // Check if the pet type is a dog, ignoring the case
        if("Dog".equalsIgnoreCase(dog.getPetType())) { 

            // Determine the bsae boarding fee based on dog weight
            if (dog.getDogWeight() >= 30) {
                // If the dog's weight is 30 or more, set the base
                baseBoardingFee = 34.0 * dog.getDaysStay();

            } else if (dog.getDogWeight() >= 20) {
                // If the dog's weight is between 20 and 29, set the base price
                baseBoardingFee = 29.0 * dog.getDaysStay();

            } else if (dog.getDogWeight() < 20) {
                // If the dog's weight is 30 or more, set the base
                baseBoardingFee = 24.0 * dog.getDaysStay();
            }
        }
        // if grooming fee, add to total amountDue.
        if (dog.getGrooming() && dog.getDogWeight() >= 30) {
            amountDue = baseBoardingFee + 29.95;
        } else if (dog.getGrooming() && dog.getDogWeight() >= 20) {
            amountDue = baseBoardingFee + 24.95;
        } else if (dog.getGrooming() && dog.getDogWeight() < 20) {
            amountDue = baseBoardingFee + 19.95;
        } else {
            amountDue = baseBoardingFee;
        }
        return amountDue;
    }

    // method for checking out pets/
    public static void checkOut(Scanner scnr, Dog[] dogList, Cat[] catList) {
        clearScreen();
        System.out.println("Enter pet type: "); // gets user input
        String type = getType(scnr);

        clearScreen();
        System.out.println("Select pet: "); 
        switch (type) {
        case "dog":
            System.out.println("loc |name        |age");
            for (Dog i : dogList) {
                if (i != null) { // shows all pets of user inputed type, with a corresponding space number.
                        System.out.println("" + i.getDogSpaceNumber() + ".  |" + i.getPetName() + whiteSpace(i.getPetName().length()) + "|" + i.getPetAge());
                }
            }
            int selected;
            selected = getInputInt(scnr); // select pet entry to edit by space number.
            dogList[selected-1].setAmountDue(calcAmount(dogList[selected-1])); // calculate and set amountDue at checkout incase pet owner picks up pet early.
            clearScreen();
            System.out.println("Amount Due : $" + dogList[selected-1].getAmountDue()); // display amountDue
            promptEnterKey2(scnr);
            dogList[selected-1] = null; // delete object instance from array.
            break;

        case "cat":
            System.out.println("loc |name        |age");
            for (Cat i : catList) {
                if (i != null) { // shows all pets of user inputed type, with a corresponding space number.
                        System.out.println("" + i.getCatSpaceNumber() + ".  |" + i.getPetName() + whiteSpace(i.getPetName().length()) + "|" + i.getPetAge());
                }
            }
            selected = getInputInt(scnr);
            catList[selected-1].setAmountDue(catList[selected-1].getDaysStay() * 18.0); // calculate and set amountDue
            System.out.println("Amount Due : $" + catList[selected-1].getAmountDue()); // display amountDue
            catList[selected-1] = null; // delete object from array; 
            break;
        default:
            System.out.println("Invalid pet Type.");
            break;
        } 
    }

    // used to allow program flow on a key press. Ex. (Press enter to continue.)
    public static void promptEnterKey(Scanner scnr) {
        System.out.println("Thank you for using our Termianl Based Pet Management Software!\nPress enter to continue.");
        String var = scnr.nextLine(); // using nextLine to acheive this functionality.
        System.out.println(var);
        return;
    }

    // nextLine behaves unlike you would expect, using twice to acheive desired functionality.
    public static void promptEnterKey2(Scanner scnr) {
        System.out.println("Press enter to continue.");
        String var = scnr.nextLine();
        String var2 = scnr.nextLine();
        System.out.println(var+var2);
        return;
    }

    // to show animals in either Cat array, or Dog array.
    public static <T> void printAnimals(T[] array, Dog[] dogList, Cat[] catList, Scanner scnr) {
        clearScreen();
        System.out.println("loc |name        |age");
        for (T i : array) {
            // display pets to screen.
            if (i != null && array == dogList) {
                System.out.println("" + ((Dog) i).getDogSpaceNumber() + ".  |" + ((Dog) i).getPetName() + whiteSpace(((Dog) i).getPetName().length()) + "|" + ((Dog) i).getPetAge());
            } else if (i != null && array == catList) {
                System.out.println("" + ((Cat) i).getCatSpaceNumber() + ".  |" + ((Cat) i).getPetName() + whiteSpace(((Cat) i).getPetName().length()) + "|" + ((Cat) i).getPetAge());
            }
        }
    }

    // using this to format print statements to be formated the same in printAnimals with larger or smaller length names.
    public static String whiteSpace(int length) {
        String blankSpace = "";
        for (int i = 0; i < 12-length; i++) { // counts missing blankspace and fills accordingly.
            blankSpace += " ";
        }
        return blankSpace;
    }

    // modify either dog or cat entrys.
    public static <K, V, M> void modifyAminals(Scanner scnr, Dog[] dogList, Cat[] catList, String skip) {
        clearScreen();
        String input = null;
        if (skip != null) { // if method recursivly called, will skip straight to animal selcetion.
            input = skip;
        } else {
            System.out.println("Enter pet type."); // get pet type
            input = getInputString(scnr);
        }

        // check if cat or dog
        switch (input) {
            case "dog":
                clearScreen();
                System.out.println("Choose a dog.\nloc |name        |age"); // displays current dogs in inventory
                for (Dog i : dogList) {
                    if (i != null) {
                        System.out.println("" + i.getDogSpaceNumber() + ".  |" + i.getPetName() + whiteSpace(i.getPetName().length()) + "|" + i.getPetAge());
                    }
                }
                break;
            case "cat":
                clearScreen();
                System.out.println("Choose a cat.\nloc |name        |age"); // displays current cats in inventory
                for (Cat i : catList) {
                    if (i != null) {
                        System.out.println("" + i.getCatSpaceNumber() + ".  |" + i.getPetName() + whiteSpace(i.getPetName().length()) + "|" + i.getPetAge());
                    }
                }
                break;
            default:
                System.out.println("Bad input near line 350.");
                break;
        }
        int selection = getInputInt(scnr)-1;
        switch (input) {
            // both cat and dog have different types of values stored so each one will be handled seperatly.
            case "dog":
                int x = -1;
                while (x != 0){
                    String tmpS;
                    int tmpI;
                    boolean tmpB;
                    clearScreen(); // select what dog value you would like to change.
                    System.out.println("1. change type.\n2. Set name.\n3. Set age.\n4. Set days stay.\n5. Set amount due.\n6. Set dog loc.\n7. Set weight.\n8. Set grooming.\n0. Exit.");
                    x = getInputInt(scnr);
                    switch (x) {
                        case 0: return;
                        case 1:
                            clearScreen();
                            changeType(dogList, dogList, catList, dogList[selection], scnr); // changes type. this creates new cat object and deletes old dog object.
                            break;
                        case 2: //change name
                            clearScreen();
                            System.out.println("Enter name.");
                            tmpS = getInputString(scnr);
                            dogList[selection].setPetName(tmpS);
                            break;
                        case 3: // change age
                            clearScreen();
                            System.out.println("Enter age.");
                            tmpI = getInputInt(scnr);
                            dogList[selection].setPetAge(tmpI);
                            break;
                        case 4: // change days stay
                            clearScreen();
                            System.out.println("Enter days stay.");
                            tmpI = getInputInt(scnr);
                            dogList[selection].setDaysStay(tmpI);
                            break;
                        case 5: // change amount due
                            clearScreen();
                            System.out.println("Enter amount due.");
                            tmpI = getInputInt(scnr);
                            dogList[selection].setAmountDue(tmpI);
                            break;
                        case 6: // set new dog location
                            clearScreen();
                            System.out.println("Enter dog loc. (1/30)");
                            tmpI = getInputInt(scnr);
                            dogList[selection].setDogSpaceNumber(tmpI);
                            break;
                        case 7: // set dog weight
                            clearScreen();
                            System.out.println("Enter weight.");
                            tmpI = getInputInt(scnr);
                            dogList[selection].setDogWeight(tmpI);
                            break;
                        case 8: // set grooming service
                            clearScreen();
                            System.out.println("Grooming service? (y/n)");
                            String tm = getInputString(scnr);
                            if (tm.charAt(0) == 'y') {
                                tmpB = true;
                            } else { tmpB = false; }
                            dogList[selection].setGrooming(tmpB);
                            break;
                        default:
                            System.out.println("Invalid input.");
                            break;
                    }
                }
            case "cat":
                x = -1;
                while (x != 0){
                    String tmpS;
                    int tmpI;
                    clearScreen(); // same as dog, with less values that apply to the cat class.
                    System.out.println("1. Change type.\n2. Set name.\n3. Set age.\n4. Set days stay.\n5. Set amount due.\n6. Set cat loc.\n0. Exit");
                    x = getInputInt(scnr);
                    switch (x) {
                        case 0: return;
                        case 1:
                            clearScreen();
                            changeType(catList, dogList, catList, catList[selection], scnr); // change type
                            break;                          
                        case 2: // change name
                            clearScreen();
                            System.out.println("Enter name.");
                            tmpS = getInputString(scnr);
                            dogList[selection].setPetName(tmpS);
                            break;
                        case 3: // change age
                            clearScreen();
                            System.out.println("Enter age.");
                            tmpI = getInputInt(scnr);
                            dogList[selection].setPetAge(tmpI);
                            break;
                        case 4: // change days stay
                            clearScreen();
                            System.out.println("Enter days stay.");
                            tmpI = getInputInt(scnr);
                            dogList[selection].setDaysStay(tmpI);
                            break;
                        case 5: // change amount due
                            clearScreen();
                            System.out.println("Enter amount due.");
                            tmpI = getInputInt(scnr);
                            dogList[selection].setAmountDue(tmpI);
                            break;
                        case 6: // change cat location
                            clearScreen();
                            System.out.println("Enter Cat loc. (1/12)");
                            tmpI = getInputInt(scnr);
                            dogList[selection].setDogSpaceNumber(tmpI);
                            break;
                        default:
                            System.out.println("Invalid input.");
                            break;
                    }
                }
            default:
                System.out.println("Bad input near line 470.");
        }
    }

    // this method handles changing pet types. all info remains exactly the same except petType, space location, and extra values that only pertain to cat and dog.
    public static <T> void changeType(T[] animalList, Dog[] dogList, Cat[] catList, T selected, Scanner scnr) {
        if (animalList == dogList) {
            clearScreen();
            System.out.println("Enter new storage location.");
            usedSpaces(catList, "cat"); // displayed used cat spaces

            // gets new space from user
            int newSpace = getInputInt(scnr); 
            Cat tmp = new Cat("cat", ((Dog) selected).getPetName(), ((Dog) selected).getPetAge(), ((Dog) selected).getDaysStay(), newSpace); // creates new cat obj
            catList[newSpace-1] = tmp; // add new obj to catlist at specified loc
            dogList[((Dog) selected).getDogSpaceNumber()-1] = null; // deletes old obj
            modifyAminals(scnr, dogList, catList, "cat"); // goes back to modify other atributes of current new pet type.
        } else if (animalList == catList) {
            System.out.print("Enter new storage location.");
            usedSpaces(dogList, "dog"); // displayed used spaces to user
            
            // get new values from user.
            int newSpace = getInputInt(scnr);
            clearScreen();
            System.out.println("\nEnter dog weight: ");
            int weight = getInputInt(scnr);
            clearScreen();
            Boolean groom = getGrooming(scnr);
            System.out.println("poop");
            Dog tmp2 = new Dog("dog", ((Cat) selected).getPetName(), ((Cat) selected).getPetAge(), ((Cat) selected).getDaysStay(), newSpace, weight, groom); // creates new dog obj
            dogList[newSpace-1] = tmp2; // add new obj to doglist at specified loc
            catList[((Cat) selected).getCatSpaceNumber()-1] = null; // deletes old obj
            modifyAminals(scnr, dogList, catList, "dog"); // goes back to modify other atributes of current new pet type.
        }
    }

    // main
    public static void main(String[] args) {
        clearScreen();
        Dog[] dogList = new Dog[30];
        Cat[] catList = new Cat[12];
        int dogSpaceNumber = 30;
        int catSpaceNumber = 12;
        Scanner scnr = new Scanner(System.in);
        promptEnterKey(scnr);

        int x = -1;
        while (x != 0) {
            clearScreen();
            System.out.println("Terminal Based Pet Management Software.\n");
            System.out.println("1. Check in pet.");
            System.out.println("2. Check out pet.");
            System.out.println("3. List dogs.");
            System.out.println("4. List cats.");
            System.out.println("5. Modify entries.");
            System.out.println("0. Exit.\n");
            
            System.out.print("Enter selection: ");
            x = scnr.nextInt();
            switch (x) {
                case 0: return;
                case 1:
                    int[] var = checkIn(scnr, dogSpaceNumber, catSpaceNumber, dogList, catList);
                    dogSpaceNumber = var[0];
                    catSpaceNumber = var[1];
                    break;
                case 2:
                    checkOut(scnr, dogList, catList);
                    break;
                case 3:
                    printAnimals(dogList, dogList ,catList, scnr);
                    promptEnterKey2(scnr);
                    break;
                case 4:
                    printAnimals(catList, dogList ,catList, scnr);
                    promptEnterKey2(scnr);
                    break;
                case 5:
                    modifyAminals(scnr, dogList, catList, null);
                    break;
                default:
                    System.out.println("Invalid entry.");
                    break;
            }
        }
        scnr.close();
    }
}