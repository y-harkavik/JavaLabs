package objects.Buildings.Port;

import objects.Product.Characteristics.Operation;
import objects.Product.Characteristics.TypeOfProduct;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Class that defines port yard.
 *
 * @author Yauheni
 * @version 1.0
 */
public class PortYard {
    private ConcurrentMap<TypeOfProduct, Integer> products;

    /**
     * Initializes Map of product in port yard.
     */
    public PortYard() {
        this.products = new ConcurrentHashMap<TypeOfProduct, Integer>();
    }

    /**
     * Return Map of products in yard.
     *
     * @return Map of products in yard.
     */
    public ConcurrentMap<TypeOfProduct, Integer> getProducts() {
        return products;
    }

    /**
     * Add product in yard.
     *
     * @param typeOfProduct Type of product.
     * @param count         Count of product.
     */
    public void addProduct(TypeOfProduct typeOfProduct, int count) {
        products.put(typeOfProduct, count);
    }

    /**
     * Change count of product in yard.
     *
     * @param typeOfProduct Type of changing product.
     * @param count         Count of product that load/unload.
     * @param operation     Operation above product.
     */
    public void changeProductCount(TypeOfProduct typeOfProduct, int count, Operation operation) {
        Integer product = products.get(typeOfProduct);
        if (Operation.LOADING == operation) {
            product -= count;
        } else {
            product += count;
        }
        products.put(typeOfProduct, product);
    }
}
