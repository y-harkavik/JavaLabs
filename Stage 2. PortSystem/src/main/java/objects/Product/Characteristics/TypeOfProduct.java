package objects.Product.Characteristics;

/**
 * Class that defines type of product.
 *
 * @author Yauheni
 * @version 1.0
 */
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
