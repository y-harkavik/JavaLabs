package objects.Product.Characteristics;

public enum TypeOfProduct {
    COAL("Coal"), WEED("Weed"), WOOD("Wood"), GOLD("Gold"), SLAVES("Slaves"), CLOTH("Cloth");

    String type;

    TypeOfProduct(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
