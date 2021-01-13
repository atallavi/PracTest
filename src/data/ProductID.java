package data;


public class ProductID {

    private final String universalPC;

    public ProductID(String code) {
        this.universalPC = code;
    }

    public String getUniversalPC() {
        return universalPC;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductID productID = (ProductID) o;
        return universalPC.equals(productID.getUniversalPC());
    }

    @Override
    public int hashCode() { return universalPC.hashCode(); }

    @Override
    public String toString() {
        return "ProductID{" + "universal product code='" + universalPC + '\'' + '}';
    }
}
