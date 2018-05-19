import objects.Product.Cargo.Cargo;
import objects.Product.Characteristics.Measure;
import objects.Product.Characteristics.Operation;
import objects.Product.Characteristics.TypeOfProduct;
import objects.Transport.Marine.Ship;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;

public class testShip {
    public static Ship ship;

    @BeforeClass
    public static void initShip() {
        ship = new Ship("1",Arrays.asList(new Cargo(null,null,0,null)),null);
    }

    @Test
    public void testLoading() {
        ship.setCurrentCargo(new Cargo(TypeOfProduct.WEED,Measure.KG,100,Operation.LOADING));
        ship.loading();
        Assert.assertEquals("Loading cargo error",ship.getCurrentCargo().getCargoParameters().getCargoCount(),100);
    }

    @Test
    public void testUnloading() {
        ship.setCurrentCargo(new Cargo(TypeOfProduct.WEED,Measure.KG,100,Operation.UNLOADING));
        ship.unloading();
        Assert.assertEquals("Loading cargo error",ship.getCurrentCargo().getCargoParameters().getCargoCount(),0);
    }
}
