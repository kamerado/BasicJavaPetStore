public class Dog extends Pet{
    private int dogSpaceNumber;
    private int dogWeight;
    private boolean grooming;

    public Dog() {
        super();
        this.dogSpaceNumber = -1;
        this.dogWeight = -1;
        this.grooming = false;

    }
    
    // Constructor for Dog class
    public Dog(String petType, String petName, int petAge, int daysStay, int dogSpaceNumber, int dogWeigt, boolean grooming) {
        super(petType, petName, petAge, daysStay);
        setDogSpaceNumber(dogSpaceNumber);
        setDogWeight(dogWeight);
        setGrooming(grooming);
    }

    // accessors and mutators for all dog attributes.
    public int getDogSpaceNumber() { return this.dogSpaceNumber; }
    public void setDogSpaceNumber(int dogSpaceNumber) { this.dogSpaceNumber = dogSpaceNumber; }
    public int getDogWeight() {return this.dogWeight; }
    public void setDogWeight(int dogWeight) { this.dogWeight = dogWeight; }
    public boolean getGrooming() { return this.grooming; }
    public void setGrooming(boolean grooming) { this.grooming = grooming; }
}