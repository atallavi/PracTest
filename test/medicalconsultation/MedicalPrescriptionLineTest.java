package medicalconsultation;

import data.ProductID;
import exceptions.IncorrectTakingGuidelinesException;
import exceptions.InvalidProductID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MedicalPrescriptionLineTest {

    @Test
    void creationPrescriptionLine() throws InvalidProductID, IncorrectTakingGuidelinesException {
        ProductID pID1 = new ProductID( "1234567890111" );
        String[] instructions = {"AFTERMEALS", "1", " ", "1", "1", "DAY"};
        MedicalPrescriptionLine mpl = new MedicalPrescriptionLine( pID1, instructions );
    }
    @Test
    void nullValueThrowsException () throws IncorrectTakingGuidelinesException, InvalidProductID {
        ProductID pID1 = new ProductID( "1234567890111" );
        String[] instructions = {"AFTERMEALS", null, " ", "1", "1", "DAY"};
        Throwable exc = assertThrows(
                IncorrectTakingGuidelinesException.class,
                () -> {MedicalPrescriptionLine mpl = new MedicalPrescriptionLine( pID1, instructions );} );
    }
    @Test
    void incorrectNumberOfArgumentsThrowsException () throws InvalidProductID, IncorrectTakingGuidelinesException {
        ProductID pID1 = new ProductID( "1234567890111" );
        String[] instructions = {"AFTERMEALS", null, " ", "1", "1", "DAY", ""};
        Throwable exc = assertThrows(
                IncorrectTakingGuidelinesException.class,
                () -> {MedicalPrescriptionLine mpl = new MedicalPrescriptionLine( pID1, instructions );} );
    }
    @Test
    void setGuidelines() throws InvalidProductID, IncorrectTakingGuidelinesException {
        ProductID pID1 = new ProductID( "1234567890111" );
        String[] instructionsBefore = {"AFTERMEALS", "1", " ", "1", "1", "DAY"};
        String[] instructionsAfter = {"BEFOREMEALS", "2", "THIS IS A TEST", "2", "2", "WEEK"};
        MedicalPrescriptionLine mpl = new MedicalPrescriptionLine( pID1, instructionsBefore );
        mpl.setGuidelines( instructionsAfter );
    }

    @Test
    void setNullGuidelines() throws InvalidProductID, IncorrectTakingGuidelinesException {
        ProductID pID1 = new ProductID( "1234567890111" );
        String[] instructionsBefore = {"AFTERMEALS", "1", " ", "1", "1", "DAY"};
        String[] instructionsAfter = {null, "2", "THIS IS A TEST", "2", "2", "WEEK"};
        MedicalPrescriptionLine mpl = new MedicalPrescriptionLine( pID1, instructionsBefore );
        Throwable exc = assertThrows(
                IncorrectTakingGuidelinesException.class,
                () -> {mpl.setGuidelines( instructionsAfter );} );

    }
    @Test
    void setIncorrectNumberOfArgumentsThrowsException ()throws InvalidProductID, IncorrectTakingGuidelinesException {
        ProductID pID1 = new ProductID( "1234567890111" );
        String[] instructionsBefore = {"AFTERMEALS", "1", " ", "1", "1", "DAY"};
        String[] instructionsAfter = {"AFTERMEALS", "2", "THIS IS A TEST", "2", "2", "WEEK", "This test will fail"};
        MedicalPrescriptionLine mpl = new MedicalPrescriptionLine( pID1, instructionsBefore );
        Throwable exc = assertThrows(
                IncorrectTakingGuidelinesException.class,
                () -> {mpl.setGuidelines( instructionsAfter );} );

    }


}