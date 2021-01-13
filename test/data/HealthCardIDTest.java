package data;

import exceptions.HealthCardException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;



class HealthCardIDTest {
    @Test
    void nullHealthCardFormat() throws HealthCardException {
        Throwable exception = assertThrows(HealthCardException.class,
                () -> {
                    HealthCardID hID = new HealthCardID( null );
                });
    }
    @Test
    void invalidHealthCardFormat() throws HealthCardException {
        Throwable exception = assertThrows(HealthCardException.class,
                () -> {
                    HealthCardID hID = new HealthCardID( "BADCODE01" );
                });
    }
    @Test
    void correctHealthCardFormat() throws HealthCardException {
        HealthCardID hID = new HealthCardID( "BBBBBBAA123456789012345678" );
    }
}