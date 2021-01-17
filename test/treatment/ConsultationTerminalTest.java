package treatment;

import data.DigitalSignature;
import data.HealthCardID;
import data.ProductID;
import exceptions.*;
import medicalconsultation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.HealthNationalService;
import services.ScheduledVisitAgenda;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ConsultationTerminalTest {

    ConsultationTerminal consultationTerminal;
    int defaultPrescCode = 1;
    String defaultPersonalID = "BBBBBBAA111111111111111111";
    String validKeyWord = "Corona";
    ProductID validProductID, validProductID2;
    {
        try {
            validProductID = new ProductID("9234567890123");
            validProductID2 = new ProductID("1234567890123");
        } catch (InvalidProductID invalidProductID) {
            invalidProductID.printStackTrace();
        }
    }


    ProductSpecification ps1 = new ProductSpecification(
            validProductID,
            new BigDecimal(10),
            "Desc 1"
    );

    ProductSpecification ps2 = new ProductSpecification(
            validProductID2,
            new BigDecimal(20),
            "Desc 2"
    );


    public class HealthNationalServiceImp implements HealthNationalService {

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
        public ProductSpecification getProductSpecification(int opt) throws AnyMedicineSearchException {
            if (opt == 3) {
                throw new AnyMedicineSearchException("Not valid option.");
            }
            return ps1;
        }

        @Override
        public MedicalPrescription sendePrescription(MedicalPrescription ePresc) {
            byte[] digitalSignature = new byte[10];
            ePresc.seteSign(new DigitalSignature(digitalSignature));
            return ePresc;
        }
    }

    public class ScheduledVisitAgendaImpl implements ScheduledVisitAgenda {

        @Override
        public HealthCardID getHealthCardID() throws HealthCardException {
            return new HealthCardID(defaultPersonalID);
        }
    }

    @BeforeEach
    void setup(){
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
        assertThrows(AnyCurrentPrescriptionException.class,
                () -> consultationTerminal.initPrescriptionEdition());
    }


    @Test
    void initPrescriptionEditionTest()
            throws NotFinishedTreatmentException, AnyCurrentPrescriptionException,
            NotValidePrescription, HealthCardException, ConnectException {
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
    void selectProductTest()
            throws AnyMedicineSearchException, ConnectException, InvalidProductID, AnyKeyWordMedicineException {
        consultationTerminal.searchForProducts(validKeyWord);
        consultationTerminal.selectProduct(1);

        assertEquals(ps1, consultationTerminal.getPs());
    }

    @Test
    void selectProductOutOfRangeTest() throws ConnectException, InvalidProductID, AnyKeyWordMedicineException {
        consultationTerminal.searchForProducts(validKeyWord);
        assertThrows(AnyMedicineSearchException.class,
                () -> consultationTerminal.selectProduct(3));
    }

    @Test
    void enterMedicineGuidelinesTest()
            throws ConnectException, InvalidProductID, AnyKeyWordMedicineException,
            AnyMedicineSearchException, AnySelectedMedicineException, IncorrectTakingGuidelinesException,
            NotFinishedTreatmentException, AnyCurrentPrescriptionException, HealthCardException, NotValidePrescription {

        consultationTerminal.initRevision();
        consultationTerminal.initPrescriptionEdition();
        consultationTerminal.searchForProducts(validKeyWord);
        consultationTerminal.selectProduct(1);

        String[] instructions = new String[6];
        instructions[0] = "BEFOREDINNER";
        instructions[1] = "10";
        instructions[2] = "instructions text";
        instructions[3] = "20";
        instructions[4] = "30";
        instructions[5] = "DAY";

        consultationTerminal.enterMedicineGuidelines(instructions);
        ProductID pID = consultationTerminal.getPs().getProductID();
        MedicalPrescriptionLine mpl = consultationTerminal.getMedicalPrescription().getMedicalPrescriptionLine(pID);
        TakingGuideline tgl = mpl.getTakingGuideline();

        assertEquals(dayMoment.valueOf(instructions[0]), tgl.getDayMoment());
        assertEquals(Float.parseFloat(instructions[1]), tgl.getDuration());
        assertEquals(instructions[2], tgl.getInstructions());
        assertEquals(Float.parseFloat(instructions[3]), tgl.getDose());
        assertEquals(Float.parseFloat(instructions[4]), tgl.getFreq());
        assertEquals(FqUnit.valueOf(instructions[5]), tgl.getFreqUnit());
    }

    @Test
    void enterMedicineGuidelinesWithoutProductSelectedTest()
            throws ConnectException, InvalidProductID, AnyKeyWordMedicineException,
            AnyMedicineSearchException, AnySelectedMedicineException, IncorrectTakingGuidelinesException,
            NotFinishedTreatmentException, AnyCurrentPrescriptionException, HealthCardException, NotValidePrescription {
        consultationTerminal.initRevision();
        consultationTerminal.initPrescriptionEdition();
        consultationTerminal.searchForProducts(validKeyWord);

        String[] instructions = new String[6];
        instructions[0] = "BEFOREDINNER";
        instructions[1] = "10";
        instructions[2] = "instructions text";
        instructions[3] = "20";
        instructions[4] = "30";
        instructions[5] = "DAY";

        assertThrows(AnySelectedMedicineException.class,
                ()->consultationTerminal.enterMedicineGuidelines(instructions));
    }

    @Test
    void enterTreatmentEndingDateTest()
            throws NotFinishedTreatmentException, AnyCurrentPrescriptionException, NotValidePrescription,
            HealthCardException, ConnectException, IncorrectEndingDateException {
        consultationTerminal.initRevision();
        consultationTerminal.initPrescriptionEdition();
        Date date = new Date();
        consultationTerminal.enterTreatmentEndingDate(date);

        assertEquals(date, consultationTerminal.getMedicalPrescription().getEndDate());

    }

    @Test
    void enterTreatmentEndingDatePastDayTest()
            throws NotFinishedTreatmentException, AnyCurrentPrescriptionException, NotValidePrescription,
            HealthCardException, ConnectException {
        consultationTerminal.initRevision();
        consultationTerminal.initPrescriptionEdition();
        assertThrows(IncorrectEndingDateException.class,
                () -> consultationTerminal.enterTreatmentEndingDate(new Date(0)));

    }

    @Test
    void sendePrescriptionTest()
            throws NotFinishedTreatmentException, AnyCurrentPrescriptionException, NotValidePrescription,
            HealthCardException, ConnectException, eSignatureException, NotCompletedMedicalPrescription {
        consultationTerminal.initRevision();
        consultationTerminal.initPrescriptionEdition();
        consultationTerminal.sendePrescription();
    }

}
