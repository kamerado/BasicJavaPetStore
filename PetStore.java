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
                        // var.append(());
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

    public static String getInputString(Scanner scnr) {
        System.out.print("Enter string: ");
        String input = scnr.next();

        //check the input validity
        if(!input.matches("^([A-Za-z])+$")) {

            //if the input is incorrect tell the user and get the new input
            clearScreen();
            System.out.println("Invalid Input. no numbers or symbols allowed.");

            //simply return this method if the input is incorrect.
            return getInputString(scnr);
        }

        //return the input if it is correct
        return input;
    }

    public static int[] checkIn(Scanner scnr, int dSpace, int cSpace, Dog[] dogList, Cat[] catList) {
        int[] var = new int[2];

        
        if (dSpace > 0 && cSpace > 0) {
            String type = null;
            String name = null;
            int age = -1;
            int stay = -1;
            int dogWeight = -1;

            clearScreen();
            System.out.println("Enter pet type.");
            type = getInputString(scnr);

            clearScreen();
            System.out.println("Enter pet name.");
            name = getInputString(scnr);

            clearScreen();
            System.out.println("Enter pet age.");
            age = getInputInt(scnr);

            clearScreen();
            System.out.println("Enter pet stay.");
            stay = getInputInt(scnr);

            clearScreen();
            switch (type.toLowerCase()) {
                case "dog":
                    
                    
                    int dogSpace = 0;
                    String m = null;
                    while (m == null) {
                        dogSpace = -1;
                        usedSpaces(dogList, "dog");
                        System.out.println("\nEnter dog location: (1-30)");
                        dogSpace = getInputInt(scnr);

                        for (Dog z : dogList) {
                            if (z != null && ((Dog)z).getDogSpaceNumber() == dogSpace) {
                                System.out.println("Space already in use.");
                                break;
                            } else { m = ""; break; }
                        }
                    }
                    clearScreen();
                    System.out.println("Enter Dog weight.");
                    dogWeight = getInputInt(scnr);

                    clearScreen();
                    System.out.println("Optional grooming? (y/n): ");
                    String grooming = getInputString(scnr);
                    boolean groom = false;
                    if (grooming.charAt(0) == 'y') {
                        groom = true;
                    } else if (grooming.charAt(0) == 'n') {
                        groom = false;
                    }
                    Dog tmp = new Dog(type, name, age, stay, dogSpace, dogWeight, groom);
                    dogList[dogSpace-1] = tmp;
                    dSpace -= 1;
                    var[0] = dSpace;
                    var[1] = cSpace;
                    break;
                case "cat":
                    int catSpace = 0;
                    m = null;
                    while (m == null) {
                        catSpace = -1;
                        usedSpaces(catList, "cat");
                        System.out.println("\nEnter cat location: (1-12)");
                        catSpace = getInputInt(scnr);
                        for (Cat z : catList) {
                            if (z != null && ((Cat)z).getCatSpaceNumber() == catSpace) {
                                clearScreen();
                                System.out.println("Space already in use.");
                                break;
                            } else { m = "yes"; break; }
                        }
                    }
                    Cat tmpCat = new Cat(type, name, age, stay, catSpace);
                    catList[catSpace-1] = tmpCat;
                    cSpace -= 1;
                    var[0] = dSpace;
                    var[1] = cSpace;
                    break;
            }
            return var;
        } else { System.out.println("No more pet spaces."); }
        return null;
    }

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

    public static void checkOut(Scanner scnr, Dog[] dogList, Cat[] catList) {
        clearScreen();
        System.out.println("Enter pet type: ");
        String type = scnr.next();
        clearScreen();
        System.out.println("Select pet: ");
        switch (type) {
        case "dog":
            System.out.println("loc |name        |age");
            for (Dog i : dogList) {
                if (i != null) {
                        System.out.println("" + i.getDogSpaceNumber() + ".  |" + i.getPetName() + whiteSpace(i.getPetName().length()) + "|" + i.getPetAge());
                }
            }
            int selected;
            selected = scnr.nextInt();
            dogList[selected-1].setAmountDue(calcAmount(dogList[selected-1]));
            clearScreen();
            System.out.println("Amount Due : $" + dogList[selected-1].getAmountDue());
            promptEnterKey2(scnr);
            dogList[selected-1] = null;
            break;

        case "cat":
            System.out.println("loc |name        |age");
            for (Cat i : catList) {
                if (i != null) {
                        System.out.println("" + i.getCatSpaceNumber() + ".  |" + i.getPetName() + whiteSpace(i.getPetName().length()) + "|" + i.getPetAge());
                }
            }
            selected = scnr.nextInt();
            catList[selected-1].setAmountDue(catList[selected-1].getDaysStay() * 18.0);
            System.out.println("Amount Due : $" + catList[selected-1].getAmountDue());
            catList[selected-1] = null;
            break;
        default:
            System.out.println("Invalid pet Type.");
            break;
        } 
    }

    public static void promptEnterKey(Scanner scnr) {
        System.out.println("Thank you for using our Termianl Based Pet Management Software!\nPress enter to continue.");
        String var = scnr.nextLine();
        System.out.println(var);
        return;
    }

    // nextLine behaves funny, using twice to acheive desired functionality.
    public static void promptEnterKey2(Scanner scnr) {
        System.out.println("Press enter to continue.");
        String var = scnr.nextLine();
        String var2 = scnr.nextLine();
        System.out.println(var+var2);
        return;
    }

    public static <T> void printAnimals(T[] array, Dog[] dogList, Cat[] catList, Scanner scnr) {
        clearScreen();
        System.out.println("loc |name        |age");
        for (T i : array) {
            if (i != null && array == dogList) {
                System.out.println("" + ((Dog) i).getDogSpaceNumber() + ".  |" + ((Dog) i).getPetName() + whiteSpace(((Dog) i).getPetName().length()) + "|" + ((Dog) i).getPetAge());
            } else if (i != null && array == catList) {
                System.out.println("" + ((Cat) i).getCatSpaceNumber() + ".  |" + ((Cat) i).getPetName() + whiteSpace(((Cat) i).getPetName().length()) + "|" + ((Cat) i).getPetAge());
            }
        }
    }

    public static String whiteSpace(int length) {
        String blankSpace = "";
        for (int i = 0; i < 12-length; i++) {
            blankSpace += " ";
        }
        return blankSpace;
    }

    public static <K, V, M> void modifyAminals(Scanner scnr, Dog[] dogList, Cat[] catList) {
        clearScreen();
        System.out.print("Enter pet type: ");
        String input = scnr.next();
        switch (input) {
            case "dog":
                for (Dog i : dogList) {
                    if (i != null) {
                        System.out.println("loc |name        |age");
                        System.out.println("" + i.getDogSpaceNumber() + ".  |" + i.getPetName() + whiteSpace(i.getPetName().length()) + "|" + i.getPetAge());
                    }
                }
                break;
            case "cat":
                for (Cat i : catList) {
                    if (i != null) {
                        System.out.println("loc |name        |age");
                        System.out.println("" + i.getCatSpaceNumber() + ".  |" + i.getPetName() + whiteSpace(i.getPetName().length()) + "|" + i.getPetAge());
                    }
                }
                break;
            default:
                System.out.println("Bad input near line 195.");
                break;
        }
        System.out.print("Select pet: ");
        int selection = scnr.nextInt()-1;
        switch (input) {
            case "dog":
                int x = -1;
                while (x != 0){
                    String tmpS;
                    int tmpI;
                    boolean tmpB;
                    clearScreen();
                    System.out.println("1. Set type.\n2. Set name.\n3. Set age.\n4. Set days stay.\n5. Set amount due.\n6. Set dog loc.\n7. Set weight.\n8. Set grooming.\n0. Exit.");
                    x = scnr.nextInt();
                    switch (x) {
                        case 0: return;
                        case 1:
                            clearScreen();
                            changeType(dogList, dogList, catList, dogList[selection].getDogSpaceNumber(), scnr);
                            break;
                        case 2:
                            clearScreen();
                            System.out.println("Enter name.");
                            tmpS = scnr.next();
                            dogList[selection].setPetName(tmpS);
                            break;
                        case 3:
                            clearScreen();
                            System.out.println("Enter type.");
                            tmpI = scnr.nextInt();
                            dogList[selection].setPetAge(tmpI);
                            break;
                        case 4:
                            clearScreen();
                            System.out.println("Enter days stay.");
                            tmpI = scnr.nextInt();
                            dogList[selection].setDaysStay(tmpI);
                            break;
                        case 5:
                            clearScreen();
                            System.out.println("Enter amount due.");
                            tmpI = scnr.nextInt();
                            dogList[selection].setAmountDue(tmpI);
                            break;
                        case 6:
                            clearScreen();
                            System.out.println("Enter dog loc. (1/30)");
                            tmpI = scnr.nextInt();
                            dogList[selection].setDogSpaceNumber(tmpI);
                            break;
                        case 7:
                            clearScreen();
                            System.out.println("Enter type.");
                            tmpI = scnr.nextInt();
                            dogList[selection].setDogWeight(tmpI);
                            break;
                        case 8:
                            clearScreen();
                            System.out.println("Enter type.");
                            tmpB = scnr.nextBoolean();
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
                    clearScreen();
                    System.out.println("1. Change type.\n2. Set name.\n3. Set age.\n4. Set days stay.\n5. Set amount due.\n6. Set cat loc.\n0. Exit");
                    x = scnr.nextInt();
                    switch (x) {
                        case 0: return;
                        case 1:
                            clearScreen();
                            changeType(catList, dogList, catList, catList[selection].getCatSpaceNumber(), scnr);
                            break;                          
                        case 2:
                            clearScreen();
                            System.out.println("Enter name.");
                            tmpS = scnr.next();
                            dogList[selection].setPetName(tmpS);
                            break;
                        case 3:
                            clearScreen();
                            System.out.println("Enter age.");
                            tmpI = scnr.nextInt();
                            dogList[selection].setPetAge(tmpI);
                            break;
                        case 4:
                            clearScreen();
                            System.out.println("Enter days stay.");
                            tmpI = scnr.nextInt();
                            dogList[selection].setDaysStay(tmpI);
                            break;
                        case 5:
                            clearScreen();
                            System.out.println("Enter amount due.");
                            tmpI = scnr.nextInt();
                            dogList[selection].setAmountDue(tmpI);
                            break;
                        case 6:
                            clearScreen();
                            System.out.println("Enter Cat loc. (1/12)");
                            tmpI = scnr.nextInt();
                            dogList[selection].setDogSpaceNumber(tmpI);
                            break;
                        default:
                            System.out.println("Invalid input.");
                            break;
                    }
                }
            default:
                System.out.println("Bad input near line 216.");
        }
    }

    public static <T> void changeType(T[] animalList, Dog[] dogList, Cat[] catList, int space, Scanner scnr) {
        for (T i: animalList) {        
            if (i != null && animalList == dogList) {
                if (((Dog) i).getDogSpaceNumber() == space) {
                    System.out.print("Enter new storage location: ");
                    int newSpace = scnr.nextInt();
                    Cat tmp = new Cat("cat", ((Dog) i).getPetName(), ((Dog) i).getPetAge(), ((Dog) i).getDaysStay(), newSpace);
                    catList[newSpace] = tmp;
                    System.out.println("Success!");
                    dogList[space-1] = null;
                } 
            } else if (i != null && animalList == catList) {

                    System.out.print("Enter new storage location: ");
                    int newSpace = scnr.nextInt();
                    System.out.print("\nEnter dog weight: ");
                    int weight = scnr.nextInt();
                    System.out.print("\nGrooming? (y/n)");
                    String grooming = scnr.next();
                    boolean groom = false;
                    if (grooming.charAt(0) == 'y') { groom = true; }
                    Dog tmp = new Dog("dog", ((Cat) i).getPetName(), ((Cat) i).getPetAge(), ((Cat) i).getDaysStay(), newSpace, weight, groom);
                    dogList[newSpace] = tmp;
                    System.out.println("Success!");
                    catList[space-1] = null;
            }
        }
    }

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
                    modifyAminals(scnr, dogList, catList);
                    break;
                default:
                    System.out.println("Invalid entry.");
                    break;
            }
        }
        scnr.close();
    }
}