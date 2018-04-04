package objects;

public enum Measure {
    KG("kg"), FUTS("fut(s)"), METR("metr(s)"), POUND("pound"), PEOPLE("people");

    String measure;

    Measure(String measure) {
        this.measure = measure;
    }

    public String getMeasure() {
        return measure;
    }
}
