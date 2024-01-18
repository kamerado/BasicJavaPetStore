public class Pet {
    private String petType;
    private String petName;
    private int petAge;
    private int dogSpaces;
    private int catSpaces;
    private int daysStay;
    private double amountDue;

    // Constructors for pet class
    public Pet() {
        this.petType = null;
        this.petName = null;
        this.petAge = -1;
        this.dogSpaces = 30;
        this.catSpaces = 12;
        this.daysStay = -1;
        this.amountDue = -1.0;
    }

    public Pet(String petType, String petName, int petAge, int daysStay) {
        setPetType(petType);
        setPetName(petName);
        setPetAge(petAge);
        setDogSpaces(30);
        setCatSpaces(12);
        setDaysStay(daysStay);
    }
    // accessors and mutators for pet class
    public String getPetType() { return this.petType; }
    public void setPetType(String petType) { this.petType = petType; }
    public String getPetName() { return this.petName; }
    public void setPetName(String petName) { this.petName = petName; }
    public int getPetAge() { return this.petAge; }
    public void setPetAge(int petAge) { this.petAge = petAge; }
    public int getDogSpaces() { return this.dogSpaces; }
    public void setDogSpaces(int dogSpaces) { this.dogSpaces = dogSpaces; }
    public int getCatSpaces() { return this.catSpaces; }
    public void setCatSpaces(int catSpaces) { this.catSpaces = catSpaces; }
    public int getDaysStay() { return this.daysStay; }
    public void setDaysStay(int daysStay) { this.daysStay = daysStay; }
    public double getAmountDue() { return amountDue; }
    public void setAmountDue(double amountDue) { this.amountDue = amountDue; }
}