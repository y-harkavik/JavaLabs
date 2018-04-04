package objects;

public class Cargo {
    private Parameters parameters;
    private Operation operation;

    Cargo(TypeOfProduct typeOfProduct, Measure measure, int count, Operation operation) {
        this.parameters = new Parameters(typeOfProduct, measure, count);
        this.operation = operation;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public Operation getOperation() {
        return operation;
    }

    public class Parameters {
        private TypeOfProduct typeOfProduct;
        private Measure measure;
        private int count;

        Parameters(TypeOfProduct typeOfProduct, Measure measure, int count) {
            this.typeOfProduct = typeOfProduct;
            this.measure = measure;
            this.count = count;
        }

        public TypeOfProduct getTypeOfProduct() {
            return typeOfProduct;
        }

        public void setTypeOfProduct(TypeOfProduct typeOfProduct) {
            this.typeOfProduct = typeOfProduct;
        }

        public Measure getMeasure() {
            return measure;
        }

        public void setMeasure(Measure measure) {
            this.measure = measure;
        }

        public int getCount() {
            return count;
        }

        public void addCount(int addedCount) {
            this.count += addedCount;
        }

        public void subCount(int subCount) {
            this.count -= subCount;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
