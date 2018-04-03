package objects;

public enum Cargo {
    GOLD("Gold", 5, "kg"),
    COAL("Coal", 100, "kg"),
    WEED("Weed", 40, "kg"),
    WOOD("Wood", 50, "kg"),
    SLAVE("Slave", 10, " штук");

    private String type;
    private long count;
    private String measure;

    Cargo(String type, long count, String measure) {
        this.count = count;
        this.type = type;
        this.measure = measure;
    }

    public long getCount() {
        return count;
    }

    public String getMeasure() {
        return measure;
    }

    public String getType() {
        return type;
    }
}
