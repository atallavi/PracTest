package treatment;

import data.DigitalSignature;
import data.HealthCardID;
import data.ProductID;
import exceptions.*;
import medicalconsultation.MedicalPrescription;
import medicalconsultation.ProductSpecification;
import org.junit.jupiter.api.Test;
import services.HealthNationalService;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

public class ConsultationTerminalTest {

    public class HealthNationalServiceImp implements HealthNationalService {

        private List<ProductSpecification> prodList;

        @Override
        public MedicalPrescription getePrescription(HealthCardID hcID)
                throws HealthCardException, NotValidePrescription, ConnectException {
            if(hcID.getPersonalID() == null) {
                throw new HealthCardException("Health Card ID code can not be null.");
            }
            if(!HealthCardID.isValid(hcID.getPersonalID())) {
                throw new HealthCardException("Format expected.");
            }
            MedicalPrescription medicalPrescription = new MedicalPrescription();
            medicalPrescription.setHcID(hcID);
            medicalPrescription.setPrescCode(1); // ?
            return medicalPrescription;
        }

        @Override
        public List<ProductSpecification> getProductByKW(String keyWord)
                throws AnyKeyWordMedicineException, ConnectException, InvalidProductID {
            if(keyWord.equals("dummy keyword without results")) {
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
}
