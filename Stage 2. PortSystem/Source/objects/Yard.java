package objects;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Yard {
    private ConcurrentMap<TypeOfProduct, Integer> products;

    public Yard() {
        this.products = new ConcurrentHashMap<TypeOfProduct, Integer>();
    }

    public ConcurrentMap<TypeOfProduct, Integer> getProducts() {
        return products;
    }

    public void addProduct(TypeOfProduct typeOfProduct, int count) {
        products.put(typeOfProduct, count);
    }

    synchronized public void changeProductCount(TypeOfProduct typeOfProduct, int count, Operation operation) {
        Integer product = products.get(typeOfProduct);
        if (Operation.LOADNIG == operation) {
            product -= count;
        } else {
            product += count;
        }
        products.put(typeOfProduct, product);
    }
}
