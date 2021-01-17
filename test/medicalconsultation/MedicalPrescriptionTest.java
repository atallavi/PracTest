package medicalconsultation;

import data.DigitalSignature;
import data.HealthCardID;
import data.ProductID;
import exceptions.HealthCardException;
import exceptions.IncorrectTakingGuidelinesException;
import exceptions.InvalidProductID;
import exceptions.ProductNotInPrescription;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class MedicalPrescriptionTest {
    MedicalPrescription mp;
    ProductID pid;

    @Test
    void addLine() throws HealthCardException, InvalidProductID, IncorrectTakingGuidelinesException {
        mp = new MedicalPrescription();
        mp.setPrescCode( 1 );
        mp.setPrescDate( new Date( 2020,1,1 ));
        mp.setEndDate( new Date( 2021, 1,1 ) );
        mp.setHcID( new HealthCardID( "BBBBBBAA123456789012345678" ) );
        mp.seteSign( new DigitalSignature( new byte[10] ));pid = new ProductID( "1234567890111" );
        mp.addLine( new ProductID( "1234567890111" ),new String[]{"AFTERMEALS", "1" , " ", "1", "1", "DAY"} );
    }

    @Test
    void modifyLine() throws HealthCardException, InvalidProductID, IncorrectTakingGuidelinesException, ProductNotInPrescription {
        mp = new MedicalPrescription();
        mp.setPrescCode( 1 );
        mp.setPrescDate( new Date( 2020,1,1 ));
        mp.setEndDate( new Date( 2021, 1,1 ) );
        mp.setHcID( new HealthCardID( "BBBBBBAA123456789012345678" ) );
        mp.seteSign( new DigitalSignature( new byte[10] ));pid = new ProductID( "1234567890111" );
        mp.addLine( new ProductID( "1234567890111" ),new String[]{"AFTERMEALS", "1" , " ", "1", "1", "DAY"} );
        mp.modifyLine( new ProductID( "1234567890111" ), new String[]{"BEFOREMEALS", "2" , " This is a modification", "2", "2", "HOUR"});
    }

    @Test
    void removeLine() throws HealthCardException, InvalidProductID, IncorrectTakingGuidelinesException, ProductNotInPrescription {
        ProductID pid = new ProductID( "1234567890111" );
        mp = new MedicalPrescription();
        mp.setPrescCode( 1 );
        mp.setPrescDate( new Date( 2020,1,1 ));
        mp.setEndDate( new Date( 2021, 1,1 ) );
        mp.setHcID( new HealthCardID( "BBBBBBAA123456789012345678" ) );
        mp.seteSign( new DigitalSignature( new byte[10] ));pid = new ProductID( "1234567890111" );
        mp.addLine( pid ,new String[]{"AFTERMEALS", "1" , " ", "1", "1", "DAY"} );
        mp.removeLine( pid );
    }

    @Test
    void productNotInPrescription() throws InvalidProductID, HealthCardException, IncorrectTakingGuidelinesException {
        ProductID pid = new ProductID( "1234567890111" );
        ProductID pidWrong = new ProductID( "1234567890222" );
        mp = new MedicalPrescription();
        mp.setPrescCode( 1 );
        mp.setPrescDate( new Date( 2020,1,1 ));
        mp.setEndDate( new Date( 2021, 1,1 ) );
        mp.setHcID( new HealthCardID( "BBBBBBAA123456789012345678" ) );
        mp.seteSign( new DigitalSignature( new byte[10] ));pid = new ProductID( "1234567890111" );
        mp.addLine( pid ,new String[]{"AFTERMEALS", "1" , " ", "1", "1", "DAY"} );
        assertThrows(
                ProductNotInPrescription.class,
                () -> {mp.removeLine( pidWrong);} );



    }


}