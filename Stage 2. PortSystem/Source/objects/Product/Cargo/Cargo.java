package objects.Product.Cargo;

import objects.Product.Characteristics.Measure;
import objects.Product.Characteristics.Operation;
import objects.Product.Characteristics.TypeOfProduct;

public class Cargo {
    private Parameters cargoParameters;
    private Operation cargoOperation;

    public Cargo(TypeOfProduct typeOfProduct, Measure cargoMeasure, int cargoCount, Operation cargoOperation) {
        this.cargoParameters = new Parameters(typeOfProduct, cargoMeasure, cargoCount);
        this.cargoOperation = cargoOperation;
    }

    public Parameters getCargoParameters() {
        return cargoParameters;
    }

    public Operation getCargoOperation() {
        return cargoOperation;
    }

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
