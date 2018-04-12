import objects.Buildings.Port.PortYard;
import objects.Product.Characteristics.Operation;
import objects.Product.Characteristics.TypeOfProduct;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class testPortYard {
    public static PortYard portYard;

    @BeforeClass
    public static void init() {
        portYard = new PortYard();
        portYard.addProduct(TypeOfProduct.WEED,500);
        portYard.addProduct(TypeOfProduct.WOOD,500);
        portYard.addProduct(TypeOfProduct.CLOTH,500);
        portYard.addProduct(TypeOfProduct.COAL,500);
        portYard.addProduct(TypeOfProduct.GOLD,500);
    }

    @Test
    public void testChangeCountProduct() {
        portYard.changeProductCount(TypeOfProduct.WEED,100,Operation.UNLOADING);
        Assert.assertEquals("Change error",portYard.getProducts().get(TypeOfProduct.WEED).intValue(),600);
        portYard.changeProductCount(TypeOfProduct.WOOD,200,Operation.LOADING);
        Assert.assertEquals("Change error",portYard.getProducts().get(TypeOfProduct.WOOD).intValue(),300);
        portYard.changeProductCount(TypeOfProduct.COAL,300,Operation.LOADING);
        Assert.assertEquals("Change error",portYard.getProducts().get(TypeOfProduct.COAL).intValue(),200);
        portYard.changeProductCount(TypeOfProduct.CLOTH,1000,Operation.UNLOADING);
        Assert.assertEquals("Change error",portYard.getProducts().get(TypeOfProduct.CLOTH).intValue(),1500);
        portYard.changeProductCount(TypeOfProduct.GOLD,500,Operation.LOADING);
        Assert.assertEquals("Change error",portYard.getProducts().get(TypeOfProduct.GOLD).intValue(),0);
    }
}
