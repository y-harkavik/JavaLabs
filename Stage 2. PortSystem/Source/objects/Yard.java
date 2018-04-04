package objects;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Yard {
    private ConcurrentMap<TypeOfProduct, Integer> products;

    Yard() {
        this.products = new ConcurrentHashMap<TypeOfProduct, Integer>();
    }

    public ConcurrentMap<TypeOfProduct, Integer> getProducts() {
        return products;
    }

    public void addProduct(TypeOfProduct typeOfProduct, int count) {
        products.put(typeOfProduct,count);
    }
}
