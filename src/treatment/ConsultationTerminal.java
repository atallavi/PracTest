package treatment;

import data.HealthCardID;
import exceptions.*;
import medicalconsultation.MedicalPrescription;
import services.HealthNationalService;
import services.ScheduledVisitAgenda;

import java.net.ConnectException;
import java.util.Date;

public class ConsultationTerminal {
    private ScheduledVisitAgenda sva;
    private HealthNationalService hns;

    public void initRevision()
            throws HealthCardException, NotValidePrescription, ConnectException {
        HealthCardID hcID = sva.getHealthCardID();
        MedicalPrescription medicalPrescription = hns.getePrescription(hcID);
        System.out.println(medicalPrescription);
    }

    public void setScheduledVisitAgenda(ScheduledVisitAgenda sva){
        this.sva = sva;
    }

    public void setHealthNationalService(HealthNationalService hns) {
        this.hns = hns;
    }

    public void initPrescriptionEdition()
            throws AnyCurrentPrescriptionException, NotFinishedTreatmentException {
        
    }

    public void searchForProducts(String keyWord)
            throws AnyKeyWordMedicineException, ConnectException {

    }

    public void selectProduct(int option)
            throws AnyMedicineSearchException, ConnectException {

    }

    public void enterMedicineGuidelines(String[] instruc)
            throws AnySelectedMedicineException, IncorrectTakingGuidelinesException {

    }

    public void enterTreatmentEndingDate(Date date)
            throws IncorrectEndingDateException {

    }

    public void sendePrescription()
            throws ConnectException, NotValidePrescription,
            eSignatureException, NotCompletedMedicalPrescription {

    }

    public void printePresc() throws PrintingException {

    }
}
