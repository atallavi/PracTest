package data;

import exceptions.InvalidProductID;

final public class ProductID {

    private final String UPC;

    public ProductID(String upc) throws InvalidProductID {
        if(upc == null) throw new InvalidProductID("Product ID can not be null");
        if(!isValid(upc)) throw new InvalidProductID("Product ID must be 13 digits");
        UPC = upc; }

    private boolean isValid(String upc) { return upc.matches( "[0-9]{13}" ); }

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
