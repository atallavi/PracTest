package data;

import exceptions.InvalidHealthCard;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;



class HealthCardIDTest {
    @Test
    void nullHealthCardFormat() throws InvalidHealthCard {
        Throwable exception = assertThrows(InvalidHealthCard.class,
                () -> {
                    HealthCardID hID = new HealthCardID( null );
                });
    }
    @Test
    void invalidHealthCardFormat() throws InvalidHealthCard {
        Throwable exception = assertThrows(InvalidHealthCard.class,
                () -> {
                    HealthCardID hID = new HealthCardID( "BADCODE01" );
                });
    }
    @Test
    void correctHealthCardFormat() throws InvalidHealthCard {
        HealthCardID hID = new HealthCardID( "BBBBBBAA123456789012345678" );
    }
}