package data;

import exceptions.InvalidHealthCard;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductIDTest {
    @Test
    void getUPC() {
        ProductID product1 = new ProductID( "0001" );
        assertEquals( product1.getUPC(), "0001"  );
    }

    @Test
    void invalidHealthCardID() throws InvalidHealthCard {


    }




}