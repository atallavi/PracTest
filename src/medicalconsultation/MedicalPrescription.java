package medicalconsultation;

import data.DigitalSignature;
import data.HealthCardID;
import data.ProductID;
import exceptions.IncorrectTakingGuidelinesException;
import exceptions.ProductNotInPrescription;

import java.util.Date;
import java.util.HashSet;

/**
 * Package for the classes involved in the use case Suply next dispensing
 */

public class MedicalPrescription {
    private int prescCode;
    private Date prescDate;
    private Date endDate;
    private HealthCardID hcID; // the healthcard ID of the patient
    private DigitalSignature eSign; // the eSignature of the doctor
    private HashSet<MedicalPrescriptionLine> lines;

    public MedicalPrescription(int prescCode, Date prescDate, Date endDate,
                               HealthCardID hcID, DigitalSignature eSign) {
        this. prescCode = prescCode;
        this.prescDate = prescDate;
        this.endDate = endDate;
        this.hcID = hcID;
        this.eSign = eSign;
    }

    public void addLine(ProductID prodID, String[] instruc) throws IncorrectTakingGuidelinesException {
            lines.add( new MedicalPrescriptionLine( prodID, instruc ) );
    }

    public void modifyLine(ProductID prodID, String[] instruct)
            throws ProductNotInPrescription, IncorrectTakingGuidelinesException {

    }

    public void removeLine(ProductID prodID) throws ProductNotInPrescription{

    }

}
