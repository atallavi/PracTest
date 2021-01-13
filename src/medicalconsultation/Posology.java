package medicalconsultation;

public class Posology {

    private float dose;
    private float freq;
    private FqUnit freqUnit;

    public Posology (float d, float f, FqUnit u) {
        this.dose = d;
        this.freq = f;
        this.freqUnit = u;
    }

    public void setDose(float d){
        this.dose = d;
    }

    public void setFreq(float f){
        this.freq = f;
    }

    public void setFreqUnit(FqUnit u) {
        this.freqUnit = u;
    }

    public float getDose(){
        return dose;
    }

    public float getFreq() {
        return freq;
    }

    public FqUnit getFreqUnit(){
        return freqUnit;
    }
}
