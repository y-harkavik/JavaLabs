package objects.Product.Cargo;

import objects.Product.Characteristics.Measure;
import objects.Product.Characteristics.Operation;
import objects.Product.Characteristics.TypeOfProduct;

/**
 * Class that defines ship cargo.
 *
 * @author Yauheni
 * @version 1.0
 */
public class Cargo {
    private Parameters cargoParameters;
    private Operation cargoOperation;

    /**
     * Creates cargo.
     *
     * @param typeOfProduct  Type of product.
     * @param cargoMeasure   Measure of product.
     * @param cargoCount     Count of product.
     * @param cargoOperation Operation above product.
     */
    public Cargo(TypeOfProduct typeOfProduct, Measure cargoMeasure, int cargoCount, Operation cargoOperation) {
        this.cargoParameters = new Parameters(typeOfProduct, cargoMeasure, cargoCount);
        this.cargoOperation = cargoOperation;
    }

    /**
     * Return cargo parameters.
     *
     * @return cargo parameters.
     */
    public Parameters getCargoParameters() {
        return cargoParameters;
    }

    /**
     * Return operation above cargo.
     *
     * @return operation above cargo.
     */
    public Operation getCargoOperation() {
        return cargoOperation;
    }

    /**
     * Class that cargo parameters: type, measure, count.
     *
     * @author Yauheni
     * @version 1.0
     */
    public class Parameters {
        private TypeOfProduct typeOfProduct;
        private Measure cargoMeasure;
        private int cargoCount;

        Parameters(TypeOfProduct typeOfProduct, Measure cargoMeasure, int cargoCount) {
            this.typeOfProduct = typeOfProduct;
            this.cargoMeasure = cargoMeasure;
            this.cargoCount = cargoCount;
        }

        public TypeOfProduct getTypeOfProduct() {
            return typeOfProduct;
        }

        public void setTypeOfProduct(TypeOfProduct typeOfProduct) {
            this.typeOfProduct = typeOfProduct;
        }

        public Measure getCargoMeasure() {
            return cargoMeasure;
        }

        public void setCargoMeasure(Measure cargoMeasure) {
            this.cargoMeasure = cargoMeasure;
        }

        public int getCargoCount() {
            return cargoCount;
        }

        public void setCargoCount(int cargoCount) {
            this.cargoCount = cargoCount;
        }
    }
}
