package medicalconsultation;

public class TakingGuideline {

    private dayMoment dMoment;
    private float duration;
    private String instructions;
    private Posology posology;


    public TakingGuideline(dayMoment dM, float du, String i, float d, float f, FqUnit u) {
        this.dMoment = dM;
        this.duration = du;
        this.instructions = i;
        this.posology = new Posology(d, f, u);
    }

    public void setDayMoment(dayMoment dM) {
        this.dMoment = dM;
    }

    public void setDuration(float du) {
        this.duration = du;
    }

    public void setInstructions(String i){
        this.instructions = i;
    }

    public void setDose(float d){
        posology.setDose(d);
    }

    public void setFreq(float f){
        posology.setFreq(f);
    }

    public void setFreqUnit(FqUnit u) {
        posology.setFreqUnit(u);
    }

    public dayMoment getDayMoment(){
        return this.dMoment;
    }

    public float getDuration(){
        return this.duration;
    }

    public String getInstructions(){
        return this.instructions;
    }

    public float getDose(){
        return posology.getDose();
    }

    public float getFreq() {
        return posology.getFreq();
    }

    public FqUnit getFreqUnit(){
        return posology.getFreqUnit();
    }
}
