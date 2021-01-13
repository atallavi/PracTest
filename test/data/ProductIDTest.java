package data;

import exceptions.InvalidProductID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductIDTest {
    @Test
    void nullProductFormat() throws InvalidProductID {
        Throwable exception = assertThrows(InvalidProductID.class,
                () -> {
                    ProductID pID = new ProductID( null );
                });
    }
    @Test
    void invalidProductFormat() throws InvalidProductID {
        Throwable exception1 = assertThrows(InvalidProductID.class,
                () -> {
                    ProductID pID = new ProductID( "as" );
                });
        Throwable exception2 = assertThrows(InvalidProductID.class,
                () -> {
                    ProductID pID = new ProductID( "123" );
                });

    }

    @Test
    void correctHealthCardFormat() throws InvalidProductID {
        ProductID pID = new ProductID( "1234567890123" );
    }
}