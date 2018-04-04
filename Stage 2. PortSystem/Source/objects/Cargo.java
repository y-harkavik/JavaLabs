package objects;

public class Cargo {
    TypeOfProduct typeOfProduct;
    Measure measure;
    int count;

    Cargo(TypeOfProduct typeOfProduct, Measure measure, int count) {
        this.typeOfProduct = typeOfProduct;
        this.measure = measure;
        this.count = count;
    }
}
