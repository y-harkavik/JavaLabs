public class Data {
    public static final String[] vegetables = {"Apple", "Broccoli", "Kale", "Orache", "Kuka", "Avocado",};

    private String currentVegetable = vegetables[0];

    public static String getRandomVegetable() {
        return vegetables[((int) (Math.random() * vegetables.length))];
    }

    public String getCurrentVegetable() {
        return currentVegetable;
    }

    public void setCurrentVegetable() {
        this.currentVegetable = getRandomVegetable();
    }
}
