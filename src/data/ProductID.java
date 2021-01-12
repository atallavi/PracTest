package data;

final public class ProductID {

    private final String UPC;

    public ProductID(String upc) { UPC = upc; }

    public String getUPC() {
        return UPC;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductID pID = (ProductID) o;
        return UPC.equals( pID.UPC );
    }

    @Override
    public int hashCode() { return UPC.hashCode(); }

    @Override
    public String toString() {
        return "ProductID{" + "product code='" + UPC + '\'' + '}';
    }
}
