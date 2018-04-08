package objects.Buildings.Port;

import objects.Product.Characteristics.Operation;
import objects.Product.Characteristics.TypeOfProduct;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class PortYard {
    private ConcurrentMap<TypeOfProduct, Integer> products;

    public PortYard() {
        this.products = new ConcurrentHashMap<TypeOfProduct, Integer>();
    }

    public ConcurrentMap<TypeOfProduct, Integer> getProducts() {
        return products;
    }

    public void addProduct(TypeOfProduct typeOfProduct, int count) {
        products.put(typeOfProduct, count);
    }

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
