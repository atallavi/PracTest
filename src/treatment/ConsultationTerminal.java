package treatment;

import data.HealthCardID;
import exceptions.*;
import medicalconsultation.MedicalPrescription;
import medicalconsultation.ProductSpecification;
import services.HealthNationalService;
import services.ScheduledVisitAgenda;

import java.net.ConnectException;
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
        System.out.println("\tMedical Prescription downloaded from Health National Service.");
    }

    public void setScheduledVisitAgenda(ScheduledVisitAgenda sva){
        this.sva = sva;
    }

    public void setHealthNationalService(HealthNationalService hns) {
        this.hns = hns;
    }

    public void initPrescriptionEdition()
            throws AnyCurrentPrescriptionException, NotFinishedTreatmentException {
        if(medicalPrescription == null) {
            throw new AnyCurrentPrescriptionException("Medical Prescription not initialized"); }
        if(medicalPrescription.getEndDate() != null && new Date().before(medicalPrescription.getEndDate())) {
            throw new NotFinishedTreatmentException("Medical Prescription not finished");
        }
        psSearchResults = null;
        ps = null;
        System.out.println("\tPrescription edition initialized.");
    }

    public void searchForProducts(String keyWord)
            throws AnyKeyWordMedicineException, ConnectException, InvalidProductID {
        this.psSearchResults = hns.getProductByKW(keyWord);
        System.out.println("\tSearch results for key word " + keyWord  + ":");
        for(ProductSpecification p: psSearchResults) {
            System.out.println(p);
        }
    }

    public void selectProduct(int option)
            throws AnyMedicineSearchException, ConnectException {
        if (psSearchResults == null) {
            throw new AnyMedicineSearchException("Product specification search not preformed.");
        }
        this.ps = hns.getProductSpecification(option);
        System.out.println("\tSelected option number " + option + ".");
        System.out.println(ps);
    }

    public void enterMedicineGuidelines(String[] instruc)
            throws AnySelectedMedicineException, IncorrectTakingGuidelinesException {
        if (ps == null) {
            throw new AnySelectedMedicineException("Product not selected.");
        }
        medicalPrescription.addLine(ps.getProductID(), instruc);
        System.out.println("\tInstructions entered: ");
        System.out.println(instruc);
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
        hns.sendePrescription(medicalPrescription);
    }

    public void printePresc() throws PrintingException {
        System.out.println(medicalPrescription);
    }

    public HealthCardID getHcID(){
        return this.hcID;
    }

    public MedicalPrescription getMedicalPrescription() {
        return this.medicalPrescription;
    }

    public ProductSpecification getPs() {
        return ps;
    }

    public List<ProductSpecification> getPsSearchResults() {
        return psSearchResults;
    }
}
