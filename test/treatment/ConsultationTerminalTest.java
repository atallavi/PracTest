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
                throws AnyKeyWordMedicineException, ConnectException, InvalidProductID {
            if (!keyWord.equals(validKeyWord)) {
                throw new AnyKeyWordMedicineException("No results found for \'" + keyWord + "\' key word.");
            }
            //Create 2 Product Specification dummies
            ProductSpecification ps1, ps2;
            ps1 = new ProductSpecification(
                    new ProductID("1234567890123"),
                    new BigDecimal(10),
                    "Desc 1");
            ps2 = new ProductSpecification(
                    new ProductID("9234567890123"),
                    new BigDecimal(20),
                    "Desc 2");
            prodList = new ArrayList<>();
            prodList.add(ps1);
            prodList.add(ps2);
            return prodList;
        }

        @Override
        public ProductSpecification getProductSpecification(int opt)
                throws AnyMedicineSearchException, ConnectException {
            if (prodList == null) {
                throw new AnyMedicineSearchException("Search not preformed.");
            }

            return prodList.get(opt);
        }

        @Override
        public MedicalPrescription sendePrescription(MedicalPrescription ePresc)
                throws ConnectException, NotValidePrescription, eSignatureException, NotCompletedMedicalPrescription {
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
    void initPrescriptionEditionTest() throws NotFinishedTreatmentException, AnyCurrentPrescriptionException {
        consultationTerminal.initPrescriptionEdition();
        assertEquals(defaultPrescCode, consultationTerminal.getMedicalPrescription().getPrescCode());
        assertNull(consultationTerminal.getPs());
        assertNull(consultationTerminal.getPsSearchResults());
    }

    @Test
    void searchForProductsTest() throws ConnectException, InvalidProductID, AnyKeyWordMedicineException {
        consultationTerminal.searchForProducts(validKeyWord);
        List<ProductSpecification> expectedList = new ArrayList<>();
        ProductSpecification ps1, ps2;
        ps1 = new ProductSpecification(
                new ProductID("1234567890123"),
                new BigDecimal(10),
                "Desc 1");
        ps2 = new ProductSpecification(
                new ProductID("9234567890123"),
                new BigDecimal(20),
                "Desc 2");
        expectedList.add(ps1);
        expectedList.add(ps2);

        assertEquals(expectedList, consultationTerminal.getPsSearchResults());
    }

}
