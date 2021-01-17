package treatment;

import data.DigitalSignature;
import data.HealthCardID;
import data.ProductID;
import exceptions.*;
import medicalconsultation.MedicalPrescription;
import medicalconsultation.ProductSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import services.HealthNationalService;
import services.ScheduledVisitAgenda;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ConsultationTerminalTest {

    static ConsultationTerminal consultationTerminal;
    static int defaultPrescCode = 1;
    static String defaultPersonalID = "BBBBBBAA111111111111111111";
    static String validKeyWord = "Corona";
    static ProductID validProductID, validProductID2;

    static {
        try {
            validProductID = new ProductID("9234567890123");
            validProductID2 = new ProductID("1234567890123");
        } catch (InvalidProductID invalidProductID) {
            invalidProductID.printStackTrace();
        }
    }

    static ProductSpecification ps1 = new ProductSpecification(
            validProductID,
            new BigDecimal(10),
            "Desc 1"
    );

    static ProductSpecification ps2 = new ProductSpecification(
            validProductID2,
            new BigDecimal(20),
            "Desc 2"
    );


    public static class HealthNationalServiceImp implements HealthNationalService {

        private List<ProductSpecification> prodList;

        @Override
        public MedicalPrescription getePrescription(HealthCardID hcID)
                throws HealthCardException, NotValidePrescription, ConnectException {
            if (hcID.getPersonalID() == null) {
                throw new HealthCardException("Health Card ID code can not be null.");
            }
            if (!HealthCardID.isValid(hcID.getPersonalID())) {
                throw new HealthCardException("Format expected.");
            }
            MedicalPrescription medicalPrescription = new MedicalPrescription();
            medicalPrescription.setHcID(hcID);
            medicalPrescription.setPrescCode(defaultPrescCode); // ?
            return medicalPrescription;
        }

        @Override
        public List<ProductSpecification> getProductByKW(String keyWord)
                throws AnyKeyWordMedicineException, ConnectException {
            if (!keyWord.equals(validKeyWord)) {
                throw new AnyKeyWordMedicineException("No results found for \'" + keyWord + "\' key word.");
            }
            //Create a list with 2 Product Specification dummies
            prodList = new ArrayList<>();
            prodList.add(ps1);
            prodList.add(ps2);
            return prodList;
        }

        @Override
        public ProductSpecification getProductSpecification(int opt) {
            return ps1;
        }

        @Override
        public MedicalPrescription sendePrescription(MedicalPrescription ePresc) {
            byte[] digitalSignature = new byte[10];
            ePresc.seteSign(new DigitalSignature(digitalSignature));
            return ePresc;
        }
    }

    public static class ScheduledVisitAgendaImpl implements ScheduledVisitAgenda {

        @Override
        public HealthCardID getHealthCardID() throws HealthCardException {
            return new HealthCardID(defaultPersonalID);
        }
    }

    @BeforeAll
    static void setup(){
        consultationTerminal = new ConsultationTerminal();
        consultationTerminal.setHealthNationalService(new HealthNationalServiceImp());
        consultationTerminal.setScheduledVisitAgenda(new ScheduledVisitAgendaImpl());
    }


    @Test
    void initRevisionTest() throws NotValidePrescription, HealthCardException, ConnectException {
        consultationTerminal.initRevision();

        int prescCode = consultationTerminal.getMedicalPrescription().getPrescCode();
        HealthCardID hcID = consultationTerminal.getHcID();

        assertEquals(defaultPrescCode, prescCode);
        assertEquals(defaultPersonalID, hcID.getPersonalID());
    }

    @Test
    void initPrescriptionEditionBeforeInitRevisionTest() {
        ConsultationTerminal ct = new ConsultationTerminal();
        assertThrows(AnyCurrentPrescriptionException.class, () -> ct.initPrescriptionEdition());
    }


    @Test
    void initPrescriptionEditionTest() throws NotFinishedTreatmentException, AnyCurrentPrescriptionException, NotValidePrescription, HealthCardException, ConnectException {
        consultationTerminal.initRevision();
        consultationTerminal.initPrescriptionEdition();
        assertEquals(defaultPrescCode, consultationTerminal.getMedicalPrescription().getPrescCode());
        assertNull(consultationTerminal.getPs());
        assertNull(consultationTerminal.getPsSearchResults());
    }

    @Test
    void searchForProductsTest() throws ConnectException, InvalidProductID, AnyKeyWordMedicineException {
        consultationTerminal.searchForProducts(validKeyWord);
        List<ProductSpecification> expectedList = new ArrayList<>();
        expectedList.add(ps1);
        expectedList.add(ps2);

        assertEquals(expectedList, consultationTerminal.getPsSearchResults());
    }

    @Test
    void selectProductWithoutSearchTest() {
        ConsultationTerminal ct = new ConsultationTerminal();
        assertThrows(AnyMedicineSearchException.class, () -> ct.selectProduct(1));
    }

    @Test
    void selectProductTest() throws AnyMedicineSearchException, ConnectException, InvalidProductID, AnyKeyWordMedicineException {
        consultationTerminal.searchForProducts(validKeyWord);
        consultationTerminal.selectProduct(1);
        assertEquals(ps1, consultationTerminal.getPs());
    }

}
