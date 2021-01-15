package medicalconsultation;

import data.ProductID;
import exceptions.IncorrectTakingGuidelinesException;

public class MedicalPrescriptionLine {
    private ProductID productID;
    private TakingGuideline tgl;

    public MedicalPrescriptionLine(ProductID prodID, String[] instructions) throws IncorrectTakingGuidelinesException {
        this.productID = prodID;

        dayMoment dm = obtainDayMoment( instructions[0] );
        FqUnit fqU = obtainFqUnit( instructions[4] );

        tgl = new TakingGuideline( dm,
                Float.parseFloat( instructions[1] ),
                instructions[2],
                Float.parseFloat( instructions[3] ),
                Float.parseFloat( instructions[4] ),
                fqU  );
    }

    public ProductID getProductID() {
        return productID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicalPrescriptionLine mpl = (MedicalPrescriptionLine) o;
        return this.getProductID().equals( mpl.getProductID() );
    }

    private dayMoment obtainDayMoment(String s) throws IncorrectTakingGuidelinesException{
        for (dayMoment c : dayMoment.values()) {
            if(c.name().equals( s )) return c;
        }
        throw new IncorrectTakingGuidelinesException( "" );
    }
    private FqUnit obtainFqUnit(String s) throws IncorrectTakingGuidelinesException{
        for(FqUnit f : FqUnit.values()) {
            if(f.name().equals( s )) return f;
        }
        throw new IncorrectTakingGuidelinesException( "" );
    }
}

