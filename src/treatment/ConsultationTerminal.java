package treatment;

import data.HealthCardID;
import exceptions.*;
import medicalconsultation.MedicalPrescription;
import medicalconsultation.ProductSpecification;
import services.HealthNationalService;
import services.ScheduledVisitAgenda;

import java.net.ConnectException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class ConsultationTerminal {
    private ScheduledVisitAgenda sva;
    private HealthNationalService hns;
    private HealthCardID hcID;
    private MedicalPrescription medicalPrescription;
    private List<ProductSpecification> psSearchResults;
    private ProductSpecification ps;


    public void initRevision()
            throws HealthCardException, NotValidePrescription, ConnectException {
        this.hcID = sva.getHealthCardID();
        this.medicalPrescription = hns.getePrescription(hcID);
        System.out.println("Medical Prescription downloaded from Health National Service.");
    }

    public void setScheduledVisitAgenda(ScheduledVisitAgenda sva){
        this.sva = sva;
    }

    public void setHealthNationalService(HealthNationalService hns) {
        this.hns = hns;
    }

    public void initPrescriptionEdition()
            throws AnyCurrentPrescriptionException, NotFinishedTreatmentException {
        psSearchResults = null;
        ps = null;

    }

    public void searchForProducts(String keyWord)
            throws AnyKeyWordMedicineException, ConnectException {
        this.psSearchResults = hns.getProductByKW(keyWord);
        System.out.println("Search results for key word " + keyWord  + ":");
        for( ProductSpecification p: psSearchResults) {
            System.out.println(p);
        }
    }

    public void selectProduct(int option)
            throws AnyMedicineSearchException, ConnectException {
        if (psSearchResults == null) {
            throw new AnyMedicineSearchException("Product specification search not preformed.");
        }
        this.ps = hns.getProductSpecification(option);
    }

    public void enterMedicineGuidelines(String[] instruc)
            throws AnySelectedMedicineException, IncorrectTakingGuidelinesException {
        if (ps == null) {
            throw new AnySelectedMedicineException("Product not selected.");
        }
        medicalPrescription.addLine(ps.getProductID(), instruc);
    }

    public void enterTreatmentEndingDate(Date date)
            throws IncorrectEndingDateException {
        Date currentDate = new Date();
        if (date.before(currentDate)) {
            throw new IncorrectEndingDateException("Ending date can not be before current date");
        }
        medicalPrescription.setPrescDate(currentDate);
        medicalPrescription.setEndDate(date);
    }

    public void sendePrescription()
            throws ConnectException, NotValidePrescription,
            eSignatureException, NotCompletedMedicalPrescription {

    }

    public void printePresc() throws PrintingException {

    }
}
