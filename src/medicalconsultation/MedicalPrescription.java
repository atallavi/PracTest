package medicalconsultation;

import data.DigitalSignature;
import data.HealthCardID;
import data.ProductID;
import exceptions.IncorrectTakingGuidelinesException;
import exceptions.ProductNotInPrescription;

import java.util.Date;
import java.util.HashMap;
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
    private HashMap<ProductID, MedicalPrescriptionLine> lines;

    public MedicalPrescription() {
        lines = new HashMap<ProductID, MedicalPrescriptionLine>(  );
    }

    public void addLine(ProductID prodID, String[] instruc) throws IncorrectTakingGuidelinesException {
            lines.put( prodID, new MedicalPrescriptionLine( prodID, instruc ) );
    }

    public void modifyLine(ProductID prodID, String[] instruct)
            throws ProductNotInPrescription, IncorrectTakingGuidelinesException {
        if(!lines.containsKey(prodID )) {throw  new ProductNotInPrescription( prodID.toString() + "has no line created yet" );}
        lines.get( prodID ).setGuidelines( instruct );

    }

    public void removeLine(ProductID prodID) throws ProductNotInPrescription{
        if(!lines.containsKey(prodID )) {throw  new ProductNotInPrescription( prodID.toString() + "has no line created yet" );}
        lines.remove( prodID );
    }

    public MedicalPrescriptionLine getMedicalPrescriptionLine(ProductID pID) {
        return lines.get(pID);
    }

    public void setPrescCode(int prescCode) {
        this.prescCode = prescCode;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setPrescDate(Date prescDate) {
        this.prescDate = prescDate;
    }

    public void seteSign(DigitalSignature eSign) {
        this.eSign = eSign;
    }

    public void setHcID(HealthCardID hcID) {
        this.hcID = hcID;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Date getPrescDate() {
        return prescDate;
    }

    public DigitalSignature geteSign() {
        return eSign;
    }

    public HealthCardID getHcID() {
        return hcID;
    }

    public int getPrescCode() {
        return prescCode;
    }
}
