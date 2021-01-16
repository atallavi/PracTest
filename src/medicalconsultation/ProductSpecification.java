package medicalconsultation;

import data.ProductID;

import java.math.BigDecimal;

public class ProductSpecification {

    private ProductID upcCode;
    private BigDecimal price;
    private String description;

    public ProductSpecification(ProductID pID, BigDecimal price, String description) {
        this.upcCode = pID;
        this.price = price;
        this.description = description;
    }
    public ProductID getProductID() {
        return this.upcCode;
    }

    public String getDescription() {
        return this.description;
    }

    public BigDecimal getPrice(){
        return this.price;
    }

    public void setProductID (ProductID pID) {
        this.upcCode = pID;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return upcCode.toString() + "\nDescription: " + description + "\nPrice: " + price.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductSpecification ps = (ProductSpecification) o;
        return ps.getProductID().equals(upcCode)
                && ps.getDescription().equals(description)
                && ps.getPrice().equals(price);
    }
}
