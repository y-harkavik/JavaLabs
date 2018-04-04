package objects;

public class Cargo {
    private TypeOfProduct typeOfProduct;
    private Measure measure;
    private int count;

    Cargo(TypeOfProduct typeOfProduct, Measure measure, int count) {
        this.typeOfProduct = typeOfProduct;
        this.measure = measure;
        this.count = count;
    }


    public TypeOfProduct getTypeOfProduct() {
        return typeOfProduct;
    }

    public void setTypeOfProduct(TypeOfProduct typeOfProduct) {
        this.typeOfProduct = typeOfProduct;
    }

    public Measure getMeasure() {
        return measure;
    }

    public void setMeasure(Measure measure) {
        this.measure = measure;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
