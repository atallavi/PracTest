package medicalconsultation;

import data.ProductID;
import exceptions.IncorrectTakingGuidelinesException;

public class MedicalPrescriptionLine {
    private ProductID productID;
    private TakingGuideline tgl;

    public MedicalPrescriptionLine(ProductID prodID, String[] instructions) throws IncorrectTakingGuidelinesException {
        this.productID = prodID;
        if(checkInstructions( instructions ))
        tgl = new TakingGuideline(dayMoment.valueOf( instructions[0] ),
                Float.parseFloat(instructions[1]),
                instructions[2],
                Float.parseFloat( instructions[3] ),
                Float.parseFloat( instructions[4] ),
                FqUnit.valueOf( instructions[5] ) );
    }

    public ProductID getProductID() {
        return productID;
    }

    public TakingGuideline getTakingGuideline(){
        return tgl;
    }

    public void setGuidelines(String[] s) throws IncorrectTakingGuidelinesException {
        if (checkInstructions( s ))
        tgl.setDayMoment( dayMoment.valueOf( s[0]  ) );
        tgl.setDuration( Float.parseFloat( s[1] ) );
        tgl.setInstructions( s[2] );
        tgl.setDose( Float.parseFloat( s[3] ) );
        tgl.setFreq( Float.parseFloat( s[4] ) );
        tgl.setFreqUnit( FqUnit.valueOf( s[5] ) );
    }

    private boolean checkInstructions(String[] instructions) throws IncorrectTakingGuidelinesException {
        if ((6 < instructions.length) && instructions.length> 6) { throw new IncorrectTakingGuidelinesException("Instructions should be {'Daymoment', 'Duration, 'Instructions','Dose', 'Frequency', 'Frequency unit'}");}
        for(String s : instructions ) { if (s == null) throw new IncorrectTakingGuidelinesException("Instructions can not be null"); }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicalPrescriptionLine mpl = (MedicalPrescriptionLine) o;
        return this.getProductID().equals( mpl.getProductID() );
    }



}

