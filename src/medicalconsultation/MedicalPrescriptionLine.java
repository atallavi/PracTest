package medicalconsultation;

import data.ProductID;

public class MedicalPrescriptionLine {
    private ProductID productID;
    private TakingGuideline tgl;

    public MedicalPrescriptionLine(ProductID prodID, String[] instruc) {
        this.productID = prodID;
        dayMoment dm = obtainDayMoment( instruc[0] );
        FqUnit fqU = obtainFqUnit( instruc[4] );
        tgl = new TakingGuideline( dm, Float.parseFloat( instruc[1] ),instruc[2], Float.parseFloat( instruc[3] ),
                Float.parseFloat( instruc[4] ), fqU  );
    }

    private dayMoment obtainDayMoment(String s) {
        for (dayMoment c : dayMoment.values()) {
            if(c.name().equals( s )) return c;
        }
        return dayMoment.AFTERBREAKFAST;
    }
    private FqUnit obtainFqUnit(String s){
        for(FqUnit f : FqUnit.values()) {
            if(f.name().equals( s )) return f;
        }
        return FqUnit.DAY;
    }
}

