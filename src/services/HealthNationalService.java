package services;

import data.HealthCardID;
import exceptions.*;
import medicalconsultation.MedicalPrescription;
import medicalconsultation.ProductSpecification;

import java.net.ConnectException;
import java.util.List;

public interface HealthNationalService {

    MedicalPrescription getePrescription(HealthCardID hcID)
        throws HealthCardException, NotValidePrescription, ConnectException;

    List<ProductSpecification> getProductByKW (String keyWord)
        throws AnyKeyWordMedicineException, ConnectException;

    ProductSpecification getProductSpecification(int opt)
        throws AnyMedicineSearchException, ConnectException;

    MedicalPrescription sendePrescription (MedicalPrescription ePresc)
        throws ConnectException, NotValidePrescription, eSignatureException,
            NotCompletedMedicalPrescription;

}
