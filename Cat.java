public class Cat extends Pet{
    private int catSpaceNumber;

    public Cat(String petType, String petName, int petAge, int daysStay, int catSpaceNumber) {
        super(petType, petName, petAge, daysStay);
        setCatSpaceNumber(catSpaceNumber);
    }
    public int getCatSpaceNumber() { return this.catSpaceNumber; }
    public void setCatSpaceNumber(int catSpaceNumber) { this.catSpaceNumber = catSpaceNumber; }
}
